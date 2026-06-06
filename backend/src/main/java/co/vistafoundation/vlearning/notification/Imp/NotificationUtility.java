package co.vistafoundation.vlearning.notification.Imp;


import org.springframework.stereotype.Component;

import com.google.firebase.messaging.Notification;

@Component
public class NotificationUtility {

	
public Notification buildNotification(String title,String body) {
		
		Notification notification = Notification.builder()
	            .setTitle(title)
	            .setBody(body)
	            .build();
		return notification;
	}
}
