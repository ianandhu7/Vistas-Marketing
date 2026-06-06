package co.vistafoundation.vlearning.email.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;

@Controller
@RequestMapping("/api/v1/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@Autowired
	UserRepository userRepo;

	
	@PostMapping(value = "/sendForgotPasswordEmail")
	public ResponseEntity<?> sendForgotPasswordEmail(@RequestParam(name = "username") String username,
			@RequestParam(name = "userSurId") Long userSurId) {
		String randomString = RandomStringUtils.random(10, true, true);
		User userData = userRepo.findByUsername(username);
		if(userData == null) throw new AppException("invalid user id");
		
		Document<?> reponses = emailService.sendForgotPasswordEmail(userData.getEmail(), userData.getUsername(),
				randomString, userData.getFirstName() + " " + userData.getLastName(), userSurId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

	@SuppressWarnings("rawtypes")
	@PostMapping("/invoice")
	public ResponseEntity<Document> sendEmailInvoice(@RequestParam("orderId") String orderId) {

		Document invoice = emailService.sendInvoceThroughEmail(orderId);

		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}
	
}
