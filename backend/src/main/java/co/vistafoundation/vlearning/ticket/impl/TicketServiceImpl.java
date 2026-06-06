package co.vistafoundation.vlearning.ticket.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.ticket.dto.TicketDTO;
import co.vistafoundation.vlearning.ticket.model.IssueCategory;
import co.vistafoundation.vlearning.ticket.model.Ticket;
import co.vistafoundation.vlearning.ticket.repository.IssueCategoryRepository;
import co.vistafoundation.vlearning.ticket.repository.TicketRepository;
import co.vistafoundation.vlearning.ticket.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	IssueCategoryRepository issueCategoryRepository;
	
	@Override
	public Document<String> saveTicket(TicketDTO ticketDTO) {

		Document<String> doc = new Document<>();

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
			
			if(ticketDTO.getIdIssueCategory()==null)
				throw new AppException("Please select category");

			Ticket ticket = new Ticket();
			
			ticket.setComment(ticketDTO.getComment());
			ticket.setIdIssueCategory(ticketDTO.getIdIssueCategory());
			ticket.setIdSubjectChapter(ticketDTO.getIdSubjectChapter());
			ticket.setVideoEnLink(ticketDTO.getVideoEnLink());
			ticket.setIdVlUser(userPrincipal.getUserSurId());

			ticketRepository.save(ticket);
			doc.setData("Saved Successfully");
			doc.setMessage("Saved Successfully");
			doc.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
		return doc;
	}

	@Override
	public Document<List<IssueCategory>> getIssueCategory() {

		Document<List<IssueCategory>> doc = new Document<>();

		try {
					
			List<IssueCategory> issueList = issueCategoryRepository.findAll();
			doc.setData(issueList);
			doc.setMessage("Request Successfull");
			doc.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
		return doc;
	}

}
