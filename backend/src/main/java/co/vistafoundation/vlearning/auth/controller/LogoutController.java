/**
 * 
 */
package co.vistafoundation.vlearning.auth.controller;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author NAVEEN
 *
 */
@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {

	@Autowired
	AuthService authService;

	@PostMapping("")
	public ResponseEntity<?> logout(@RequestParam String secret, Device device) {
		Document<String> reponses = authService.logoutUser(secret, device);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/devices")
	public ResponseEntity<?> logoutFromDevices(@RequestParam @Nullable Long idUserDevices, @RequestParam boolean allDevices) {
		Document<String> reponses = authService.logoutFromDevices(idUserDevices, allDevices);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}
