public class ClassroomChangeNotification extends Notification {
    private static final String BODY_FORMAT = "в аудиторию %d";

    private final int classroom;

    public ClassroomChangeNotification(String title, int classroom) {
        super(title);
        this.classroom = classroom;
    }

    @Override
    protected String formatBody() {
        return String.format(BODY_FORMAT, classroom);
    }
}
