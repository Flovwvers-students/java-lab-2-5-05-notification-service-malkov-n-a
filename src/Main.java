import java.util.List;
import java.util.Set;

public class Main {
    private static final String CLASSROOM_CHANGE_TITLE = "Пара перенесена";
    private static final String DEADLINE_REMINDER_TITLE = "Напоминание о сдаче";
    private static final String CLASSROOM_CHANGE_HEADING = "=== Перенос пары ===";
    private static final String DEADLINE_REMINDER_HEADING = "\n=== Напоминание о дедлайне ===";

    public static void main(String[] args) {
        String annaId = "1";
        String annaName = "Анна";
        String annaEmail = "anna@example.com";
        String annaPhone = null;
        Set<String> annaEnabledChannels = Set.of(EmailChannel.CHANNEL_ID, SmsChannel.CHANNEL_ID);
        User anna = new User(annaId, annaName, annaEmail, annaPhone, annaEnabledChannels);

        String borisId = "2";
        String borisName = "Борис";
        String borisEmail = "boris@example.com";
        String borisPhone = "+79991234567";
        Set<String> borisEnabledChannels = Set.of(SmsChannel.CHANNEL_ID, PushChannel.CHANNEL_ID);
        User boris = new User(borisId, borisName, borisEmail, borisPhone, borisEnabledChannels);

        List<User> users = List.of(anna, boris);
        NotificationChannel emailChannel = new EmailChannel();
        NotificationChannel smsChannel = new SmsChannel();
        NotificationChannel pushChannel = new PushChannel();
        NotificationService service = new NotificationService();

        int classroomNumber = 304;
        Notification classroomChange = new ClassroomChangeNotification(CLASSROOM_CHANGE_TITLE, classroomNumber);
        List<NotificationChannel> firstChannels = List.of(emailChannel, smsChannel);
        System.out.println(CLASSROOM_CHANGE_HEADING);
        service.broadcast(users, classroomChange, firstChannels);

        String laboratoryWorkName = "лабораторной № 5";
        String submissionDate = "13 ноября";
        Notification deadlineReminder = new DeadlineReminderNotification(
                DEADLINE_REMINDER_TITLE, laboratoryWorkName, submissionDate
        );
        List<NotificationChannel> secondChannels = List.of(emailChannel, smsChannel, pushChannel);
        System.out.println(DEADLINE_REMINDER_HEADING);
        service.broadcast(users, deadlineReminder, secondChannels);
    }
}
