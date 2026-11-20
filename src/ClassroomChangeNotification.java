public class ClassroomChangeNotification extends Notification {
    private final int classroom;

    public ClassroomChangeNotification(String title, int classroom) {
        super(title);
        this.classroom = classroom;
    }

    @Override
    protected String formatBody() {
        return "в аудиторию " + classroom;
    }
}
