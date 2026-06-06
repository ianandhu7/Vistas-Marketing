package co.vistafoundation.vlearning.ticket.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.ticket.dto.TicketDTO;
import co.vistafoundation.vlearning.ticket.model.IssueCategory;

public interface TicketService {

	public Document<String> saveTicket(TicketDTO ticketDTO);

	public Document<List<IssueCategory>> getIssueCategory();
}
