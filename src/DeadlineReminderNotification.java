public class DeadlineReminderNotification extends Notification {
    private final String workName;
    private final String deadline;

    public DeadlineReminderNotification(String title, String workName, String deadline) {
        super(title);
        this.workName = workName;
        this.deadline = deadline;
    }

    @Override
    protected String formatBody() {
        return workName + " до " + deadline;
    }
}
