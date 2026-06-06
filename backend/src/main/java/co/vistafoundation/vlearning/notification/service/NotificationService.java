package co.vistafoundation.vlearning.notification.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.notification.model.Notification;

public interface NotificationService {
	
	@SuppressWarnings("rawtypes")
	public Document saveNotification(Notification notification);
	
	@SuppressWarnings("rawtypes")
	public Document getListofStudents(Long idBatch);
	
	@SuppressWarnings("rawtypes")
	Document getAllNotifications(Long userId);
	
	@SuppressWarnings("rawtypes")
	public Document updateNotificationReadStatus(Long idNotification);
	
	@SuppressWarnings("rawtypes")
	public Document getUnreadNotifications(Long userId);
	
	@SuppressWarnings("rawtypes")
	public Document getsentNotificationOfTeacher(Long userId);
	
	@SuppressWarnings("rawtypes")
	public Document sendNotification(List<String> deviceRegIdList,String message,String flag);

	Document<?> sendNotificationByTopic(String topic, String message, String title, String imageURL);
}

