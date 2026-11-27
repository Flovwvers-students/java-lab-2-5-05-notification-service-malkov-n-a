public class DeadlineReminderNotification extends Notification {
    private static final String BODY_FORMAT = "%s до %s";

    private final String workName;
    private final String deadline;

    public DeadlineReminderNotification(String title, String workName, String deadline) {
        super(title);
        this.workName = workName;
        this.deadline = deadline;
    }

    @Override
    protected String formatBody() {
        return String.format(BODY_FORMAT, workName, deadline);
    }
}
