import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NotificationServiceTest {
    private static final String SECTION_HEADING_PREFIX = "===";
    private static final String OUTPUT_ERROR_FORMAT = "Неверная последовательность: %s";
    private static final String FORMAT_ERROR_MESSAGE = "Текст уведомления сформирован не один раз";
    private static final String MISSING_EXCEPTION_FORMAT = "Для канала %s ожидалось исключение";
    private static final String TEST_NOTIFICATION_TITLE = "Проверка";
    private static final String TEST_NOTIFICATION_BODY = "тело";
    private static final String TEST_USER_NAME = "Тест";
    private static final String TEST_MESSAGE = "Проверка доставки";
    private static final String EMPTY_EMAIL = " ";
    private static final String SUCCESS_MESSAGE = "Все проверки пройдены.";
    private static final String[] EMPTY_ARGUMENTS = {};
    private static final List<String> EXPECTED_OUTPUT = List.of(
            "Email -> Анна (anna@example.com): Пара перенесена в аудиторию 304",
            "Ошибка доставки: Анна, sms: Отсутствует телефон",
            "Пропущено: Борис, email",
            "SMS -> Борис (+79991234567): Пара перенесена в аудиторию 304",
            "Email -> Анна (anna@example.com): Напоминание о сдаче лабораторной № 5 до 13 ноября",
            "Ошибка доставки: Анна, sms: Отсутствует телефон",
            "Пропущено: Анна, push",
            "Пропущено: Борис, email",
            "SMS -> Борис (+79991234567): Напоминание о сдаче лабораторной № 5 до 13 ноября",
            "Push -> Борис (ID 2): Напоминание о сдаче лабораторной № 5 до 13 ноября"
    );

    public static void main(String[] args) throws Exception {
        testMainOutput();
        testSingleFormatting();
        testMissingContacts();
        System.out.println(SUCCESS_MESSAGE);
    }

    private static void testMainOutput() throws Exception {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        try (PrintStream capturedOutput = new PrintStream(outputBuffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOutput);
            Main.main(EMPTY_ARGUMENTS);
        } finally {
            System.setOut(originalOutput);
        }

        List<String> actualOutput = outputBuffer.toString(StandardCharsets.UTF_8).lines()
                .filter(line -> !line.isBlank() && !line.startsWith(SECTION_HEADING_PREFIX))
                .collect(Collectors.toList());

        if (!EXPECTED_OUTPUT.equals(actualOutput)) {
            throw new AssertionError(String.format(OUTPUT_ERROR_FORMAT, actualOutput));
        }
    }

    private static void testSingleFormatting() {
        CountingNotification notification = new CountingNotification(TEST_NOTIFICATION_TITLE);
        List<User> users = List.of();
        List<NotificationChannel> channels = List.of();
        NotificationService service = new NotificationService();
        int expectedCalls = 1;

        service.broadcast(users, notification, channels);

        if (notification.getFormatCallCount() != expectedCalls) {
            throw new AssertionError(FORMAT_ERROR_MESSAGE);
        }
    }

    private static void testMissingContacts() {
        Set<String> enabledChannels = Set.of(
                EmailChannel.CHANNEL_ID, SmsChannel.CHANNEL_ID, PushChannel.CHANNEL_ID
        );
        User user = new User(null, TEST_USER_NAME, EMPTY_EMAIL, null, enabledChannels);
        List<NotificationChannel> channels = List.of(new EmailChannel(), new SmsChannel(), new PushChannel());

        for (NotificationChannel channel : channels) {
            boolean exceptionThrown = false;

            try {
                channel.send(user, TEST_MESSAGE);
            } catch (NotificationDeliveryException exception) {
                exceptionThrown = true;
            }

            if (!exceptionThrown) {
                throw new AssertionError(String.format(MISSING_EXCEPTION_FORMAT, channel.id()));
            }
        }
    }

    private static class CountingNotification extends Notification {
        private int formatCallCount;

        public CountingNotification(String title) {
            super(title);
        }

        public int getFormatCallCount() {
            return formatCallCount;
        }

        @Override
        protected String formatBody() {
            formatCallCount++;
            return TEST_NOTIFICATION_BODY;
        }
    }
}
