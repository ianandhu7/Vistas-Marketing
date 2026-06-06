/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;

/**
 * @author vk
 *
 */
public class StudentOrderTicketAdminDTO {

	@NotNull
	private Long idStudentOrderTicket;

	@NotNull
	private Long idStudentOrder;
	
	@NotNull
	private Long userSurId;
	
	private String userEmail;
	
	@NotNull
	private String userMobile;
	
	@NotNull
	private OrderTicketStatus ticketStatus;
	
	@NotNull
	private String adminRemarks;
	
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
	
}
