import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        User anna = new User("1", "Анна", "anna@example.com", null,
                Set.of("email", "sms"));
        User boris = new User("2", "Борис", "boris@example.com", "+79991234567",
                Set.of("sms", "push"));
        List<User> users = List.of(anna, boris);

        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel push = new PushChannel();
        NotificationService service = new NotificationService();

        System.out.println("=== Перенос пары ===");
        service.broadcast(users, new ClassroomChangeNotification("Пара перенесена", 304),
                List.of(email, sms));

        System.out.println("\n=== Напоминание о дедлайне ===");
        service.broadcast(users, new DeadlineReminderNotification("Напоминание о сдаче",
                "лабораторной № 5", "13 ноября"), List.of(email, sms, push));
    }
}
