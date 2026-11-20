public class EmailChannel implements NotificationChannel {
    @Override
    public String id() { return "email"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new NotificationDeliveryException("Отсутствует email");
        }
        System.out.println("Email -> " + user.getName() + " (" + user.getEmail() + "): " + message);
    }
}
