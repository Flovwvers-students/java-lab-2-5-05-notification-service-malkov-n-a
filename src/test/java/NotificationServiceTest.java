import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

public class NotificationServiceTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            Main.main(new String[0]);
        } finally {
            System.setOut(original);
        }
        List<String> lines = buffer.toString(StandardCharsets.UTF_8).lines()
                .filter(line -> !line.isBlank() && !line.startsWith("===")).collect(java.util.stream.Collectors.toList());
        String lesson = "Пара перенесена в аудиторию 304";
        String deadline = "Напоминание о сдаче лабораторной № 5 до 13 ноября";
        List<String> expected = List.of(
                "Email -> Анна (anna@example.com): " + lesson,
                "Ошибка доставки: Анна, sms: Отсутствует телефон",
                "Пропущено: Борис, email",
                "SMS -> Борис (+79991234567): " + lesson,
                "Email -> Анна (anna@example.com): " + deadline,
                "Ошибка доставки: Анна, sms: Отсутствует телефон",
                "Пропущено: Анна, push",
                "Пропущено: Борис, email",
                "SMS -> Борис (+79991234567): " + deadline,
                "Push -> Борис (ID 2): " + deadline);
        if (!lines.equals(expected)) throw new AssertionError("Неверная последовательность: " + lines);

        int[] calls = {0};
        Notification notification = new Notification("Тест") {
            @Override protected String formatBody() { calls[0]++; return "тело"; }
        };
        new NotificationService().broadcast(List.of(new User("1", "Тест", null, null,
                Set.of())), notification, List.of());
        if (calls[0] != 1) throw new AssertionError("format() должен вызываться один раз");

        User missing = new User(null, "Тест", " ", null, Set.of("email", "sms", "push"));
        for (NotificationChannel channel : List.of(new EmailChannel(), new SmsChannel(), new PushChannel())) {
            try {
                channel.send(missing, "Тест");
                throw new AssertionError("Ожидалось исключение: " + channel.id());
            } catch (NotificationDeliveryException expectedError) {
            }
        }
        System.out.println("Все проверки пройдены.");
    }
}
