import java.util.List;

public class NotificationService {
    private static final String SKIPPED_CHANNEL_FORMAT = "Пропущено: %s, %s%n";
    private static final String DELIVERY_ERROR_FORMAT = "Ошибка доставки: %s, %s: %s%n";

    public void broadcast(List<User> users, Notification notification, List<NotificationChannel> channels) {
        String message = notification.format();

        for (User user : users) {
            for (NotificationChannel channel : channels) {
                if (!user.isNotificationEnabled(channel.id())) {
                    System.out.printf(SKIPPED_CHANNEL_FORMAT, user.getName(), channel.id());
                    continue;
                }

                try {
                    channel.send(user, message);
                } catch (NotificationDeliveryException exception) {
                    System.out.printf(DELIVERY_ERROR_FORMAT, user.getName(), channel.id(), exception.getMessage());
                }
            }
        }
    }
}
