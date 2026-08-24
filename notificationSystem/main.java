import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class main{
    public static void main(String[] args) {
        
        //only main and half is implemented

    }
  
}

class UserValidation{
    public boolean validateUser(User user){
        return user.userStatus != UserStatus.ACTIVE;
    }

    public boolean validateUserForNotificationType(User user, NotificationType notificationType){
        HashSet<NotificationType> userPrefrences = user.userPrefrence.getNotificationPrefernce();

        return userPrefrences.contains(notificationType);
    
    }
}

class NotificationSystem{
    List<User> users = new ArrayList<>();
    HashMap<NotificationType, NotificationStartegy> notificationsTypeToStarategyMap = new HashMap<>();
    RetrySystem retrySystem;
    UserValidation userValidation;

    public void sendNotification(Notificaion notificaion){
        for(User user : users){

            if (!userValidation.validateUser(user)){
                continue;
            }

            if (!userValidation.validateUserForNotificationType(user, notificaion.notificationType)){
                continue;
            }
            NotificationStartegy notificationStartegy = notificationsTypeToStarategyMap.get(notificaion.notificationType);

            NotificationStatus notificationStatus =  notificationStartegy.sendNotification(user);

            if (notificationStatus == NotificationStatus.FAILED){
                retrySystem.retryQueue.offer(new UserNotification());
            }


        }
    }
}

class Notificaion {
    String header;
    String body;
    NotificationType notificationType;
}


class User {
    int id;
    UserPrefrence userPrefrence;
    UserStatus userStatus;
}

interface NotificationStartegy{
    public NotificationStatus sendNotification(User u);
}

class EmailNotificationStartegy implements  NotificationStartegy{
    public NotificationStatus sendNotification(User u){
        System.out.println("Email sent");
        return NotificationStatus.SENT;
    }
}
class PushAppNotificationStartegy implements  NotificationStartegy{
    public NotificationStatus sendNotification(User u){
        System.out.println("Push App Notification sent");
        return NotificationStatus.SENT;
    }
}

enum NotificationType {
    EMAIL, 
    PUSHAPP, 
    INAPP
}

class UserPrefrence{
    HashSet<NotificationType> notificationPrefernce;
    public HashSet<NotificationType> getNotificationPrefernce(){
        return this.notificationPrefernce;
    }
}

enum UserStatus{
    DISABLED, 
    ACTIVE, 
    BLOCKED
}

enum NotificationStatus{
    SENT, 
    FAILED,
    MAX_RETRY_REACHED
}
class UserNotification{
    User user;
    NotificationStartegy notification;
    NotificationStatus NotificationStatus;
    int count;

}
class RetrySystem{
    Queue<UserNotification> retryQueue = new LinkedList<>();

    public void runRetry(){
        while(!retryQueue.isEmpty()){
            UserNotification UserNotification = retryQueue.poll();

            if (UserNotification.NotificationStatus != NotificationStatus.FAILED){
                continue;
            }

            if (UserNotification.count >3) {
                UserNotification.NotificationStatus = NotificationStatus.MAX_RETRY_REACHED;
                /// log or add in some place for later analytics 
                continue;
            }

            NotificationStatus status = UserNotification.notification.sendNotification(UserNotification.user);

            if (status == NotificationStatus.SENT){
                UserNotification.NotificationStatus = NotificationStatus.SENT;

            } else {
                UserNotification.count = UserNotification.count+1;
                retryQueue.offer(UserNotification);


            }


        }
    }
    
}