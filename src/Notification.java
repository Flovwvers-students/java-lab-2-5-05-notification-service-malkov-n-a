public abstract class Notification {
    private static final String FULL_MESSAGE_FORMAT = "%s %s";

    private final String title;

    protected Notification(String title) {
        this.title = title;
    }

    public String format() {
        return String.format(FULL_MESSAGE_FORMAT, title, formatBody());
    }

    protected abstract String formatBody();
}
