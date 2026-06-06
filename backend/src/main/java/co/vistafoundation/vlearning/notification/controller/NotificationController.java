package co.vistafoundation.vlearning.notification.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.notification.model.Notification;
import co.vistafoundation.vlearning.notification.service.NotificationService;

@RestController
@RequestMapping("api/v1/notification/")
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping(value = "saveNotification")
	public ResponseEntity<?> saveNotification(@RequestBody @Valid Notification notification) {
		Document<?> reponses = notificationService.saveNotification(notification);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "getStudents/{idBatch}")
	public ResponseEntity<?> getStudents(@PathVariable Long idBatch) {
		Document<?> reponses = notificationService.getListofStudents(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "getNotifications/{userId}")
	public ResponseEntity<?> getNotifications(@PathVariable Long userId) {
		Document<?> reponses = notificationService.getAllNotifications(userId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "updateNotificationReadStatus/{idNotification}")
	public ResponseEntity<?> updateNotificationReadStatus(@PathVariable Long idNotification) {
		Document<?> reponses = notificationService.updateNotificationReadStatus(idNotification);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "getAllunreadNotification/{idUser}")
	public ResponseEntity<?> getAllunreadNotification(@PathVariable Long idUser) {
		Document<?> reponses = notificationService.getUnreadNotifications(idUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "getSentNotificationForTeacher/{idUser}")
	public ResponseEntity<?> getSentNotificationForTeacher(@PathVariable Long idUser) {
		Document<?> reponses = notificationService.getsentNotificationOfTeacher(idUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

}
