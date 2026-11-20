public class SmsChannel implements NotificationChannel {
    @Override
    public String id() { return "sms"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getPhone() == null || user.getPhone().isBlank()) {
            throw new NotificationDeliveryException("Отсутствует телефон");
        }
        System.out.println("SMS -> " + user.getName() + " (" + user.getPhone() + "): " + message);
    }
}
