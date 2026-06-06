/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketAdminDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketRequestDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketResponseDTO;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.model.StudentOrderTicket;

/**
 * @author vk
 *
 */
public interface StudentOrderTicketService {
	
	public Document<StudentOrderTicket> create(StudentOrderTicketRequestDTO studentOrderTicketRequest);
	
	public Document<StudentOrderTicket> updateByAdmin(StudentOrderTicketAdminDTO studentOrderTicketAdminDTO);
	
	public Document<Boolean> validateOrderTicket(Long idStudentOrder, Long userSurId);
	
	public Document<StudentOrderTicket> getTicketDetails(Long idStudentOrderTicket);
	
	public Document<List<StudentOrderTicketResponseDTO>> findListBySatus(OrderTicketStatus Status);
	
	public Document<List<StudentOrderTicket>> findListByUser(Long userSurId);
	
	public Document<List<StudentOrderTicketResponseDTO>> findAll();
	
	public Document<List<StudentOrderTicket>> findListByIdStudentOrder(Long idStudentOrder);
	
	
	
}
