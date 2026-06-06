package co.vistafoundation.vlearning.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.ticket.dto.TicketDTO;
import co.vistafoundation.vlearning.ticket.model.IssueCategory;
import co.vistafoundation.vlearning.ticket.model.Ticket;
import co.vistafoundation.vlearning.ticket.service.TicketService;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

	
	@Autowired 
	TicketService ticketService;
	
	@PostMapping(value = "/save-ticket")
	public ResponseEntity<Document<String>> saveTicket(@RequestBody  TicketDTO ticketDTO) {
		Document<String> responses = ticketService.saveTicket(ticketDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}
	
	@GetMapping(value = "/get-issue-category")
	public ResponseEntity<Document<List<IssueCategory>>> getIssueCategory() {
		Document<List<IssueCategory>> responses = ticketService.getIssueCategory();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}
	
}
