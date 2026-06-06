/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketAdminDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketRequestDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentOrderTicketResponseDTO;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.model.StudentOrderTicket;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderTicketRepository;

/**
 * @author vk
 *
 */
@Service
public class StudentOrderTicketServiceImpl implements StudentOrderTicketService{
	
	@Autowired
	private StudentOrderTicketRepository studentOrderTicketRepository;
	
	@Autowired
	private StudentOrderRepository studentOrderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	

	@Override
	public Document<StudentOrderTicket> create(StudentOrderTicketRequestDTO studentOrderTicketRequest) {
		Document<StudentOrderTicket> document = new Document<>();
		try {
			
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
			
			if (!studentOrderTicketRequest.getUserSurId().equals(loggedInUser.getUserSurId()))
			{
				document.setData(null);
				document.setMessage("User details not matching with this order Id");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
				
			}
			
			User user = userRepository.findByUserSurId(studentOrderTicketRequest.getUserSurId());
			if (user==null) {
				document.setData(null);
				document.setMessage("User details not matching with this order Id");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}
			
			StudentOrder so = studentOrderRepository.findByIdStudentOrder(studentOrderTicketRequest.getIdStudentOrder());
			if (so==null) {
				document.setData(null);
				document.setMessage("Order details not found with this order Id");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}
			
			StudentOrderTicket sotValidate = 
					studentOrderTicketRepository.findFirstByIdStudentOrderAndTicketStatusNot(studentOrderTicketRequest.getIdStudentOrder(), OrderTicketStatus.Closed);
			if (sotValidate!=null) {
				if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Open)) {					
					document.setData(null);
					document.setMessage("Order ticket has been already created, it is in-progress");
					document.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
					return document;
				} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Complete)) {
					document.setData(null);
					document.setMessage("Your refund is completed");
					document.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
					return document;
				} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Initiated)) {
					document.setData(null);
					document.setMessage("Intiated for refund");
					document.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
					return document;
				}
			} 
			
			StudentOrderTicket sot = new StudentOrderTicket();
			BeanUtils.copyProperties(studentOrderTicketRequest, sot);
			sot.setTicketCreateDate(Instant.now());
			sot.setTicketStatus(OrderTicketStatus.Open);
			sot.setTicketStatusUpdateDate(Instant.now());
			sot.setRefundStatus(null);
			sot.setTicketCloseDate(null);
			sot.setAdminRemarks(null);
			sot = studentOrderTicketRepository.save(sot);
			if(sot !=null) {
				document.setData(sot);
				document.setMessage("Order ticket raised successfully, we'll get back to you with update status");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}else {
				document.setData(null);
				document.setMessage("Something went wrong while creating ticket, please try again");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
		} catch (Exception e) {
			document.setData(null);
			document.setMessage("Something went wrong while creating ticket, please try again");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}
		
	}
	
	@Override
	public Document<StudentOrderTicket> updateByAdmin(StudentOrderTicketAdminDTO studentOrderTicketAdminDTO) {
		Document<StudentOrderTicket> document = new Document<>();
		try {
			StudentOrderTicket sot = studentOrderTicketRepository.findByIdStudentOrderTicket(studentOrderTicketAdminDTO.getIdStudentOrderTicket());
			if (sot==null) {
				document.setData(null);
				document.setMessage("Ticket details not found with this order Id");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			} 
			
			if (sot.getTicketStatus().equals(OrderTicketStatus.Closed)) {
				document.setData(null);
				document.setMessage("This ticket is already closed");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}
//			else if (sot.getTicketStatus().equals(OrderTicketStatus.Refund_Initiated)) {
//				document.setData(null);
//				document.setMessage("This ticket refund is already initiated");
//				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//				return document;
//			}
			sot.setAdminRemarks(studentOrderTicketAdminDTO.getAdminRemarks());
			sot.setCreatedBy(sot.getCreatedBy());
			sot.setTicketStatus(studentOrderTicketAdminDTO.getTicketStatus());
			sot.setTicketStatusUpdateDate(Instant.now());
			if(studentOrderTicketAdminDTO.getTicketStatus().equals(OrderTicketStatus.Closed))
				sot.setTicketCloseDate(Instant.now());
			sot = studentOrderTicketRepository.save(sot);
			User u =  userRepository.findByUserSurId(studentOrderTicketAdminDTO.getUserSurId());
			
			if (u != null) {
				
				emailService.sendTicketResponseEmailNotification(u.getEmail(),u.getFirstName(),studentOrderTicketAdminDTO.getIdStudentOrderTicket(),studentOrderTicketAdminDTO.getAdminRemarks(), studentOrderTicketAdminDTO.getTicketStatus());
			}
			
			if(sot !=null) {
				document.setData(sot);
				document.setMessage("Order ticket updated successfully");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}else {
				document.setData(null);
				document.setMessage("Something went wrong while updating ticket, please try again");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
		} catch (Exception e) {
			document.setData(null);
			document.setMessage("Something went wrong while updating ticket, please try again");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}
		
	}
	
	@Override
	public Document<Boolean> validateOrderTicket(Long idStudentOrder, Long userSurId) {
		Document<Boolean> document = new Document<>();
		try {
			StudentOrderTicket sotValidate = 
					studentOrderTicketRepository.findFirstByIdStudentOrderAndTicketStatusNot(idStudentOrder, OrderTicketStatus.Closed);
			if (sotValidate==null) {
				document.setData(Boolean.FALSE);
				document.setMessage("Order ticket details not found");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
			
			if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Open)) {					
				document.setData(Boolean.TRUE);
				document.setMessage("Order ticket has been already created, it is in-progress");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Complete)) {
				document.setData(Boolean.TRUE);
				document.setMessage("Your refund is completed");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Initiated)) {
				document.setData(Boolean.TRUE);
				document.setMessage("Intiated for refund");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Other)) {
				document.setData(Boolean.TRUE);
				document.setMessage("Status is changed to Others");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}  else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Closed)) {
				document.setData(Boolean.FALSE);
				document.setMessage("Your ticket is closed");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
		} catch (Exception e) {
			document.setData(null);
			document.setMessage("Something went wrong while fetching order ticket details");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		}
		return document;
	}
	
	@Override
	public Document<StudentOrderTicket> getTicketDetails(Long idStudentOrderTicket) {
		Document<StudentOrderTicket> document = new Document<>();
		try {			
			StudentOrderTicket sot = studentOrderTicketRepository.findByIdStudentOrderTicket(idStudentOrderTicket);
			if (sot==null) {
				document.setData(null);
				document.setMessage("Ticket details not found with this order Id");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}
			document.setData(sot);
			document.setMessage("Order ticket details fetching successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}catch (Exception e) {
			document.setData(null);
			document.setMessage("Something went wrong while updating ticket, please try again");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}
	}

	@Override
	public Document<List<StudentOrderTicketResponseDTO>> findListBySatus(OrderTicketStatus Status) {
		Document<List<StudentOrderTicketResponseDTO>> document = new Document<List<StudentOrderTicketResponseDTO>>();
		List<StudentOrderTicketResponseDTO> list = studentOrderTicketRepository.findByTicketStatus(Status);
		if (!list.isEmpty()) {
			document.setData(list);
			document.setMessage("Student order tickets fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}else {
			document.setData(null);
			document.setMessage("No tickets available for the selected status.");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
	}

	@Override
	public Document<List<StudentOrderTicket>> findListByUser(Long userSurId) {
		Document<List<StudentOrderTicket>> document = new Document<List<StudentOrderTicket>>();
		
		User user = userRepository.findByUserSurId(userSurId);
		if (user==null) {
			document.setData(null);
			document.setMessage("User details not matching with this order Id");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
		
		List<StudentOrderTicket> list = studentOrderTicketRepository.findByUserSurId(userSurId);
		if (!list.isEmpty()) {
			document.setData(list);
			document.setMessage("Student order tickets fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}else {
			document.setData(null);
			document.setMessage("Student order tickets data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
	}

	@Override
	public Document<List<StudentOrderTicketResponseDTO>> findAll() {
		Document<List<StudentOrderTicketResponseDTO>> document = new Document<List<StudentOrderTicketResponseDTO>>();
		List<StudentOrderTicketResponseDTO> list = studentOrderTicketRepository.findAllStudentTicketOrder();
		if (!list.isEmpty()) {
			document.setData(list);
			document.setMessage("Student order tickets fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}else {
			document.setData(null);
			document.setMessage("Student order tickets data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
	}

	@Override
	public Document<List<StudentOrderTicket>> findListByIdStudentOrder(Long idStudentOrder) {
		// TODO Auto-generated method stub
		Document<List<StudentOrderTicket>> document=new Document<List<StudentOrderTicket>>();
		List<StudentOrderTicket> studentOrderList=studentOrderTicketRepository.findAllByIdStudentOrder(idStudentOrder);
		
		if (!studentOrderList.isEmpty()) {
			document.setData(studentOrderList);
			document.setMessage("Student order tickets fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}else {
			document.setData(null);
			document.setMessage("Student order tickets data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
	}

}
