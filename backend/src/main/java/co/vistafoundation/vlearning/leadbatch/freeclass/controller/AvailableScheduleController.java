package co.vistafoundation.vlearning.leadbatch.freeclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.AvailableScheduleService;

@RestController
@RequestMapping("/api/v1/availableschedule")
public class AvailableScheduleController {

	@Autowired
	AvailableScheduleService availableScheduleService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAllSchedules() {
		Document<?> document = availableScheduleService.getAllSchedules();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

}
