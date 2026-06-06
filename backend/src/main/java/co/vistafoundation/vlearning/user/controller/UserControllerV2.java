/**
 * Test
 */
package co.vistafoundation.vlearning.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.service.UserService2;

/**
 *@author Abdul Elahi
 */


@RestController
@RequestMapping("/api/v2/user")
public class UserControllerV2 {
	
	@Autowired
	UserService2 userService;

	@GetMapping("/status")
	public ResponseEntity<?> getActiveUserStatus(@RequestParam String jwt,Device device,Boolean isBrowser) {
		Document<Object> reponses = userService.getUserActiveStatus(jwt,device,isBrowser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
}
