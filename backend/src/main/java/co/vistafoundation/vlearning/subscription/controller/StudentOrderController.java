package co.vistafoundation.vlearning.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;

/**
 * @author Mohan Kumar K M
 **/
@RestController
@RequestMapping("/api/v1/student-order")
public class StudentOrderController {
	
	@Autowired
	StudentSubscriptionService  studentSubscriptionService;

	
	@GetMapping(value = "/payment-order-status")
	public ResponseEntity<Document<StudentOrder>> paymentRefresh(@RequestParam String idStudentOrder) {
		Document<StudentOrder> responses = studentSubscriptionService.paymentRefresh(idStudentOrder);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
}
