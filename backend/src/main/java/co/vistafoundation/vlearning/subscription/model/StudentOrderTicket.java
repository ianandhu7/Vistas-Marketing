/**
 * 
 */
package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.enums.RefundStatus;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STUDENT_ORDER_TICKET")
public class StudentOrderTicket extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_ORDER_TICKET", nullable = false)
	private Long idStudentOrderTicket;

	@Column(name = "idSTUDENT_ORDER", nullable = false)
	private Long idStudentOrder;
	
	@Column(name = "idVL_USER", nullable = false)
	private Long userSurId;
	
	@Column(name = "USER_EMAIL")
	private String userEmail;
	
	@Column(name = "USER_MOBILE", nullable = false)
	private String userMobile;
	
	@Column(name = "TICKET_STATUS")
	private OrderTicketStatus ticketStatus;
	
	@Column(name = "REFUND_STATUS")
	private RefundStatus refundStatus;
	
	@Column(name = "TICKET_CREATE_DATE")
	private Instant ticketCreateDate;
	
	@Column(name = "TICKET_CLOSE_DATE")
	private Instant ticketCloseDate;
	
	@Column(name = "STUDENT_REMARKS", length = 200)
	private String studentRemarks;
	
	@Column(name = "ADMIN_REMARKS", length = 200)
	private String adminRemarks;
	
	@Column(name = "TICKET_STATUS_UPDATE_DATE")
	private Instant ticketStatusUpdateDate;

	/**
	 * 
	 */
	public StudentOrderTicket() {
		super();
	}

	/**
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
	 */
	public StudentOrderTicket(Long idStudentOrder, Long userSurId, String userEmail, String userMobile,
			OrderTicketStatus ticketStatus, RefundStatus refundStatus, Instant ticketCreateDate, Instant ticketCloseDate,
			String studentRemarks, String adminRemarks, Instant ticketStatusUpdateDate) {
		super();
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
	}

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

}
