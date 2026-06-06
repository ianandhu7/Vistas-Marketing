/**
 * 
 */
package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.subscription.enums.RefundStatus;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STUDENT_ORDER_REFUND")
public class StudentOrderRefund extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_ORDER_REFUND", nullable = false)
	private Long idStudentOrderRefund;
	
	@Column(name = "idSTUDENT_ORDER", nullable = false)
	private Long idStudentOrder;
	
	@Column(name = "idSTUDENT_ORDER_TICKET", nullable = false)
	private Long idStudentOrderTicket;
	
	@Column(name = "idVL_USER", nullable = false)
	private Long userSurId;
	
	@Column(name = "REFUND_ID", nullable = false)
	private String refundId;
	
	@Column(name = "NET_AMOUNT", nullable = false)
	private Float netAmount;
	
	@Column(name = "GST_AMOUNT")
	private Float gstAmount;
	
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private Float totalAmount;
	
	@Column(name = "REFUND_STATUS")
	private RefundStatus refundStatus;
	
	@Column(name = "REFUND_INITIATE_DATE", nullable = false)
	private LocalDateTime refundInitiateDate;
	
	@Column(name = "REFUND_COMPLETE_DATE")
	private LocalDateTime refundCompleteDate;
	
	@Column(name = "REMARKS", length = 200)
	private String remarks;

	/**
	 * 
	 */
	public StudentOrderRefund() {
		super();
	}

	/**
	 * @param idStudentOrder
	 * @param idStudentOrderTicket
	 * @param userSurId
	 * @param refundId
	 * @param netAmount
	 * @param gstAmount
	 * @param totalAmount
	 * @param refundStatus
	 * @param refundInitiateDate
	 * @param refundCompleteDate
	 * @param remarks
	 */
	public StudentOrderRefund(Long idStudentOrder, Long idStudentOrderTicket, Long userSurId, String refundId,
			Float netAmount, Float gstAmount, Float totalAmount, RefundStatus refundStatus,
			LocalDateTime refundInitiateDate, LocalDateTime refundCompleteDate, String remarks) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.idStudentOrderTicket = idStudentOrderTicket;
		this.userSurId = userSurId;
		this.refundId = refundId;
		this.netAmount = netAmount;
		this.gstAmount = gstAmount;
		this.totalAmount = totalAmount;
		this.refundStatus = refundStatus;
		this.refundInitiateDate = refundInitiateDate;
		this.refundCompleteDate = refundCompleteDate;
		this.remarks = remarks;
	}

	/**
	 * @return the idStudentOrderRefund
	 */
	public Long getIdStudentOrderRefund() {
		return idStudentOrderRefund;
	}

	/**
	 * @param idStudentOrderRefund the idStudentOrderRefund to set
	 */
	public void setIdStudentOrderRefund(Long idStudentOrderRefund) {
		this.idStudentOrderRefund = idStudentOrderRefund;
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
	 * @return the refundId
	 */
	public String getRefundId() {
		return refundId;
	}

	/**
	 * @param refundId the refundId to set
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/**
	 * @return the netAmount
	 */
	public Float getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Float netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the gstAmount
	 */
	public Float getGstAmount() {
		return gstAmount;
	}

	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(Float gstAmount) {
		this.gstAmount = gstAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public Float getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
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
	 * @return the refundInitiateDate
	 */
	public LocalDateTime getRefundInitiateDate() {
		return refundInitiateDate;
	}

	/**
	 * @param refundInitiateDate the refundInitiateDate to set
	 */
	public void setRefundInitiateDate(LocalDateTime refundInitiateDate) {
		this.refundInitiateDate = refundInitiateDate;
	}

	/**
	 * @return the refundCompleteDate
	 */
	public LocalDateTime getRefundCompleteDate() {
		return refundCompleteDate;
	}

	/**
	 * @param refundCompleteDate the refundCompleteDate to set
	 */
	public void setRefundCompleteDate(LocalDateTime refundCompleteDate) {
		this.refundCompleteDate = refundCompleteDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
