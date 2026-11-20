public abstract class Notification {
    private final String title;

    protected Notification(String title) {
        this.title = title;
    }

    public String format() {
        return title + " " + formatBody();
    }

    protected abstract String formatBody();
}
