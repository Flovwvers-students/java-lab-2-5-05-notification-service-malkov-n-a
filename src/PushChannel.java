public class PushChannel implements NotificationChannel {
    @Override
    public String id() { return "push"; }

    @Override
    public void send(User user, String message) throws NotificationDeliveryException {
        if (user.getId() == null || user.getId().isBlank()) {
            throw new NotificationDeliveryException("Отсутствует ID");
        }
        System.out.println("Push -> " + user.getName() + " (ID " + user.getId() + "): " + message);
    }
}
