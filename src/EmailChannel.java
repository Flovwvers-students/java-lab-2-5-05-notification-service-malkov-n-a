public class EmailChannel implements NotificationChannel {
    public static final String CHANNEL_ID = "email";

    private static final String MISSING_EMAIL_MESSAGE = "Отсутствует email";
    private static final String DELIVERY_FORMAT = "Email -> %s (%s): %s%n";

    @Override
    public String id() {
        return CHANNEL_ID;
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new NotificationDeliveryException(MISSING_EMAIL_MESSAGE);
        }

        System.out.printf(DELIVERY_FORMAT, user.getName(), user.getEmail(), message);
    }
}
