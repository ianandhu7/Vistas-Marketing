/**
 * 
 */
package co.vistafoundation.vlearning.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessagingException;

import co.vistafoundation.vlearning.notification.dto.FcmDTO;
import co.vistafoundation.vlearning.notification.service.FirebaseMessagingService;

/**
 * @author vk
 *
 */

@RestController
@RequestMapping("api/v1/fcm-notification")
public class FcmController {
	
	@Autowired
	FirebaseMessagingService firebaseService;
	
	@PostMapping(value = "/send")
	public String sendNotification(@RequestBody FcmDTO fcmDTO,
	                               @RequestParam String token) throws FirebaseMessagingException, FirebaseAuthException {
	    return firebaseService.sendNotification(fcmDTO, token);
	}

}
