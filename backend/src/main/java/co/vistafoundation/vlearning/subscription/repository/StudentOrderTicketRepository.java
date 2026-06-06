/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketResponseDTO;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.model.StudentOrderTicket;

/**
 * @author vk
 *
 */
public interface StudentOrderTicketRepository extends JpaRepository<StudentOrderTicket, Long>{
   
	
	@Query(value = "select new  co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketResponseDTO(sor.idStudentOrderTicket,"
			+ "sor.idStudentOrder,sor.userSurId,sor.userEmail,sor.userMobile,sor.ticketStatus,sor.refundStatus,"
			+ "sor.ticketCreateDate,sor.ticketCloseDate,sor.studentRemarks,sor.adminRemarks,sor.ticketStatusUpdateDate,s.orderId) "
			+ "from StudentOrderTicket sor "
			+ "inner join StudentOrder s on sor.idStudentOrder = s.idStudentOrder where sor.ticketStatus = :ticketStatus")
	List<StudentOrderTicketResponseDTO> findByTicketStatus(OrderTicketStatus ticketStatus);
	
	List<StudentOrderTicket> findByUserSurId(Long userSurId);
	
	StudentOrderTicket findByIdStudentOrderAndUserSurId(Long idStudentOrder, Long userSurId);
	
//	@Query(value="select * from student_order_ticket sot order by sot.ticket_status_update_date desc limit 1" , nativeQuery=true)
//	StudentOrderTicket findFirstByIdStudentOrderAndUserSurIdOrderByTicketStatusUpdateDateDesc(Long idStudentOrder, Long userSurId);
	
	
	StudentOrderTicket findFirstByIdStudentOrderAndTicketStatusNot(Long idStudentOrder,OrderTicketStatus status);
	
	StudentOrderTicket findByIdStudentOrderTicket(Long idStudentOrderTicket);
	
	@Query(value = "select new  co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketResponseDTO(sor.idStudentOrderTicket,"
			+ "sor.idStudentOrder,sor.userSurId,sor.userEmail,sor.userMobile,sor.ticketStatus,sor.refundStatus,"
			+ "sor.ticketCreateDate,sor.ticketCloseDate,sor.studentRemarks,sor.adminRemarks,sor.ticketStatusUpdateDate,s.orderId) "
			+ "from StudentOrderTicket sor "
			+ "inner join StudentOrder s on sor.idStudentOrder = s.idStudentOrder")
	List<StudentOrderTicketResponseDTO> findAllStudentTicketOrder();
	
	List<StudentOrderTicket> findAllByIdStudentOrder(Long idStudentOrder);
}
