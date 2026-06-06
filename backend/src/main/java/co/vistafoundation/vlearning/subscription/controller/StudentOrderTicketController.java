/**
 * 
 */
package co.vistafoundation.vlearning.subscription.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketAdminDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketRequestDTO;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.service.StudentOrderTicketService;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/studentorderticket")
public class StudentOrderTicketController {
	
	@Autowired
	StudentOrderTicketService studentOrderTicketService;

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> saveStudentOrderTicket(@RequestBody @Valid StudentOrderTicketRequestDTO sotrDTO) {
		Document<?> document = studentOrderTicketService.create(sotrDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PostMapping("/updateby-admin")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateStudentOrderTicket(@RequestBody @Valid StudentOrderTicketAdminDTO sotaDTO) {
		Document<?> document = studentOrderTicketService.updateByAdmin(sotaDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/get-ticket-details")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> validateOrderTicket(@RequestParam Long idStudentOrder, @RequestParam Long userSurId ) {
		Document<?> document = studentOrderTicketService.validateOrderTicket(idStudentOrder, userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/get-ticket")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> getStudentOrderTicket(@RequestParam Long idStudentOrderTicket ) {
		Document<?> document = studentOrderTicketService.getTicketDetails(idStudentOrderTicket);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/listbystatus")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> findListByStatus(@RequestParam OrderTicketStatus status) {
		Document<?> document = studentOrderTicketService.findListBySatus(status);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/listbyuser")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT', 'ROLE_ADMIN')")
	public ResponseEntity<?> findListByUser(@RequestParam Long userSurId) {
		Document<?> document = studentOrderTicketService.findListByUser(userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/list-all")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> findAll() {
		Document<?> document = studentOrderTicketService.findAll();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@GetMapping("/issue-history")
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	public ResponseEntity<?> getIssueHistory(@RequestParam Long idStudentOrder) {
		Document<?> document = studentOrderTicketService.findListByIdStudentOrder(idStudentOrder);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
}
