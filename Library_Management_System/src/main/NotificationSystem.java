package main;

import java.util.ArrayList;
import java.util.List;

public class NotificationSystem {

	 private final List<String> notifications;

	    public NotificationSystem() {
	        this.notifications = new ArrayList<>();
	    }

	    public void notifyPatron(Patron patron, String message) {
	        String notification = "Notification for " + patron.getName() + ": " + message;
	        
	        notifications.add(notification);
	        System.out.println(notification);
	        
	    }

	    public List<String> getNotifications() {
	        return new ArrayList<>(notifications);
	    }
}
