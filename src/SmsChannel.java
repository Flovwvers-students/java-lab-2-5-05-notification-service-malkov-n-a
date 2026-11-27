public class SmsChannel implements NotificationChannel {
    private static final String MISSING_PHONE_MESSAGE = "Отсутствует телефон";
    private static final String DELIVERY_FORMAT = "SMS -> %s (%s): %s%n";

    public static final String CHANNEL_ID = "sms";

    @Override
    public String id() {
        return CHANNEL_ID;
    }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            throw new NotificationDeliveryException(MISSING_PHONE_MESSAGE);
        }

        System.out.printf(DELIVERY_FORMAT, user.getName(), user.getPhone(), message);
    }
}
