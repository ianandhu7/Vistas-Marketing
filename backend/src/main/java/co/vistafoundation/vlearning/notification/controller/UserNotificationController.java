/**
 * 
 */
package co.vistafoundation.vlearning.notification.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.notification.dto.GlobalNotifcationDTO;
import co.vistafoundation.vlearning.notification.model.UserNotification;
import co.vistafoundation.vlearning.notification.service.UserNotificationService;

/**
 * @author Naveen Kumar
 *
 */
@RestController
@RequestMapping("api/v1/user-notification/")
public class UserNotificationController {

	@Autowired
	UserNotificationService userNotificationService;
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "")
	public ResponseEntity<?> getAllNotificationForUser() {
		Document<List<UserNotification>> reponses = userNotificationService.getUserAllNotification();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PutMapping(value = "")
	public ResponseEntity<?> updateNotificationForUser(@RequestParam Long idUserNotification ) {
		
		UserPrincipal loggedInUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			loggedInUser = (UserPrincipal) authentication.getPrincipal();
		} else {
			Document<String> response = new Document<>();
			 response.setData("invalid user!");
			 response.setStatusCode(HttpStatus.OK.value());
			 response.setMessage("Request Sucessfull");
			return ResponseEntity.status(response.getStatusCode()).body(response);
		}
		
		 userNotificationService.updateNotifcation(idUserNotification,loggedInUser.getUserSurId());
		 Document<String> response = new Document<>();
		 response.setData("Notification Updated.");
		 response.setStatusCode(HttpStatus.OK.value());
		 response.setMessage("Request Sucessfull");
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@DeleteMapping(value = "")
	public ResponseEntity<?> deleteNotificationForUser(@RequestParam Long idUserNotification ) {
		
		UserPrincipal loggedInUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			loggedInUser = (UserPrincipal) authentication.getPrincipal();
		} else {
			Document<String> response = new Document<>();
			 response.setData("invalid user!");
			 response.setStatusCode(HttpStatus.OK.value());
			 response.setMessage("Request Sucessfull");
			return ResponseEntity.status(response.getStatusCode()).body(response);
		}
		
		 userNotificationService.deleteNotifcation(idUserNotification,loggedInUser.getUserSurId());
		 Document<String> response = new Document<>();
		 response.setData("Notification Deleted.");
		 response.setStatusCode(HttpStatus.OK.value());
		 response.setMessage("Request Sucessfull");
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "global")
	public ResponseEntity<?> sendGlobalNotification(@Valid @RequestBody GlobalNotifcationDTO request) {
		Document<?> reponses = userNotificationService.sendGlobalNotification(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
}
