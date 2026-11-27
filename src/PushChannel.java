public class PushChannel implements NotificationChannel {
    public static final String CHANNEL_ID = "push";

    private static final String MISSING_ID_MESSAGE = "Отсутствует ID";
    private static final String DELIVERY_FORMAT = "Push -> %s (ID %s): %s%n";

    @Override
    public String id() {
        return CHANNEL_ID;
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getId() == null || user.getId().isBlank()) {
            throw new NotificationDeliveryException(MISSING_ID_MESSAGE);
        }

        System.out.printf(DELIVERY_FORMAT, user.getName(), user.getId(), message);
    }
}
