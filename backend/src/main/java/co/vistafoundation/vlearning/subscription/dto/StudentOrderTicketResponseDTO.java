/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.enums.RefundStatus;

/**
 * @author NAVEEN
 *
 */
public class StudentOrderTicketResponseDTO {

	private Long idStudentOrderTicket;

	private Long idStudentOrder;

	private Long userSurId;

	private String userEmail;

	private String userMobile;

	private OrderTicketStatus ticketStatus;

	private RefundStatus refundStatus;

	private Instant ticketCreateDate;

	private Instant ticketCloseDate;

	private String studentRemarks;

	private String adminRemarks;

	private Instant ticketStatusUpdateDate;

	private String orderId;

	/**
	 * @return the idStudentOrderTicket
	 */
	public Long getIdStudentOrderTicket() {
		return idStudentOrderTicket;
	}

	/**
	 * @param idStudentOrderTicket the idStudentOrderTicket to set
	 */
	public void setIdStudentOrderTicket(Long idStudentOrderTicket) {
		this.idStudentOrderTicket = idStudentOrderTicket;
	}

	/**
	 * @return the idStudentOrder
	 */
	public Long getIdStudentOrder() {
		return idStudentOrder;
	}

	/**
	 * @param idStudentOrder the idStudentOrder to set
	 */
	public void setIdStudentOrder(Long idStudentOrder) {
		this.idStudentOrder = idStudentOrder;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the ticketStatus
	 */
	public OrderTicketStatus getTicketStatus() {
		return ticketStatus;
	}

	/**
	 * @param ticketStatus the ticketStatus to set
	 */
	public void setTicketStatus(OrderTicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	/**
	 * @return the refundStatus
	 */
	public RefundStatus getRefundStatus() {
		return refundStatus;
	}

	/**
	 * @param refundStatus the refundStatus to set
	 */
	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * @return the ticketCreateDate
	 */
	public Instant getTicketCreateDate() {
		return ticketCreateDate;
	}

	/**
	 * @param ticketCreateDate the ticketCreateDate to set
	 */
	public void setTicketCreateDate(Instant ticketCreateDate) {
		this.ticketCreateDate = ticketCreateDate;
	}

	/**
	 * @return the ticketCloseDate
	 */
	public Instant getTicketCloseDate() {
		return ticketCloseDate;
	}

	/**
	 * @param ticketCloseDate the ticketCloseDate to set
	 */
	public void setTicketCloseDate(Instant ticketCloseDate) {
		this.ticketCloseDate = ticketCloseDate;
	}

	/**
	 * @return the studentRemarks
	 */
	public String getStudentRemarks() {
		return studentRemarks;
	}

	/**
	 * @param studentRemarks the studentRemarks to set
	 */
	public void setStudentRemarks(String studentRemarks) {
		this.studentRemarks = studentRemarks;
	}

	/**
	 * @return the adminRemarks
	 */
	public String getAdminRemarks() {
		return adminRemarks;
	}

	/**
	 * @param adminRemarks the adminRemarks to set
	 */
	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}

	/**
	 * @return the ticketStatusUpdateDate
	 */
	public Instant getTicketStatusUpdateDate() {
		return ticketStatusUpdateDate;
	}

	/**
	 * @param ticketStatusUpdateDate the ticketStatusUpdateDate to set
	 */
	public void setTicketStatusUpdateDate(Instant ticketStatusUpdateDate) {
		this.ticketStatusUpdateDate = ticketStatusUpdateDate;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param idStudentOrderTicket
	 * @param idStudentOrder
	 * @param userSurId
	 * @param userEmail
	 * @param userMobile
	 * @param ticketStatus
	 * @param refundStatus
	 * @param ticketCreateDate
	 * @param ticketCloseDate
	 * @param studentRemarks
	 * @param adminRemarks
	 * @param ticketStatusUpdateDate
	 * @param orderId
	 */
	public StudentOrderTicketResponseDTO(Long idStudentOrderTicket, Long idStudentOrder, Long userSurId,
			String userEmail, String userMobile, OrderTicketStatus ticketStatus, RefundStatus refundStatus,
			Instant ticketCreateDate, Instant ticketCloseDate, String studentRemarks, String adminRemarks,
			Instant ticketStatusUpdateDate, String orderId) {
		super();
		this.idStudentOrderTicket = idStudentOrderTicket;
		this.idStudentOrder = idStudentOrder;
		this.userSurId = userSurId;
		this.userEmail = userEmail;
		this.userMobile = userMobile;
		this.ticketStatus = ticketStatus;
		this.refundStatus = refundStatus;
		this.ticketCreateDate = ticketCreateDate;
		this.ticketCloseDate = ticketCloseDate;
		this.studentRemarks = studentRemarks;
		this.adminRemarks = adminRemarks;
		this.ticketStatusUpdateDate = ticketStatusUpdateDate;
		this.orderId = orderId;
	}

	/**
	 * 
	 */
	public StudentOrderTicketResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
