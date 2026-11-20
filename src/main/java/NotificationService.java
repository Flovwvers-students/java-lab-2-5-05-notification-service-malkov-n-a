import java.util.List;

public class NotificationService {
    public void broadcast(List<User> users, Notification notification,
                          List<NotificationChannel> channels) {
        String message = notification.format();

        for (User user : users) {
            for (NotificationChannel channel : channels) {
                if (!user.isNotificationEnabled(channel.id())) {
                    System.out.println("Пропущено: " + user.getName() + ", " + channel.id());
                    continue;
                }

                try {
                    channel.send(user, message);
                } catch (NotificationDeliveryException e) {
                    System.out.println("Ошибка доставки: " + user.getName() + ", "
                            + channel.id() + ": " + e.getMessage());
                }
            }
        }
    }
}
