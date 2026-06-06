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

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STUDENT_ORDER")
public class StudentOrder extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_ORDER", nullable = false)
	private Long idStudentOrder;

	@Column(name = "ORDER_ID")
	private String orderId;
	
	@Column(name = "idVL_USER")
	private Long userSurId;
	
	@Column(name = "ORDER_DATE")
	private Instant orderDate;
	
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	@Column(name = "SECONDARY_STATUS")
	private String secondaryStatus;
	
	@Column(name = "DISPUTE_FLAG", columnDefinition="tinyint(1) default 0")
	private Boolean disputeFlag = false;
	
	@Column(name = "NET_AMOUNT")
	private Float netAmount;
	
	@Column(name = "GST_AMOUNT")
	private Float gstAmount;
	
	@Column(name = "TOTAL_AMOUNT")
	private Float totalAmount;
	
	@Column(name = "REMARKS")
    private String remarks;
	
	@Column(name = "COUPON_CODE")
    private String couponCode;
	
	@Column(name = "ACTUAL_AMOUNT")
    private Float actualAmount;
	
	
	
	/**
	 * 
	 */
	public StudentOrder() {
		super();
	}

	/**
	 * @param orderId
	 * @param userSurId
	 * @param orderDate
	 * @param orderStatus
	 * @param secondarySstatus
	 * @param disputeFlag
	 * @param netAmount
	 * @param gstAmount
	 * @param totalAmount
	 */
	public StudentOrder(String orderId, Long userSurId, Instant orderDate, String orderStatus, String secondaryStatus,
			Boolean disputeFlag, Float netAmount, Float gstAmount, Float totalAmount) {
		super();
		this.orderId = orderId;
		this.userSurId = userSurId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.secondaryStatus = secondaryStatus;
		this.disputeFlag = disputeFlag;
		this.netAmount = netAmount;
		this.gstAmount = gstAmount;
		this.totalAmount = totalAmount;
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
	 * @return the orderDate
	 */
	public Instant getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	/**
	 * @return the secondaryStatus
	 */
	public String getSecondaryStatus() {
		return secondaryStatus;
	}

	/**
	 * @param secondaryStatus the secondaryStatus to set
	 */
	public void setSecondaryStatus(String secondaryStatus) {
		this.secondaryStatus = secondaryStatus;
	}

	/**
	 * @return the disputeFlag
	 */
	public Boolean getDisputeFlag() {
		return disputeFlag;
	}

	/**
	 * @param disputeFlag the disputeFlag to set
	 */
	public void setDisputeFlag(Boolean disputeFlag) {
		this.disputeFlag = disputeFlag;
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

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the actualAmount
	 */
	public Float getActualAmount() {
		return actualAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(Float actualAmount) {
		this.actualAmount = actualAmount;
	}

	public StudentOrder(String orderId, Long userSurId, Instant orderDate, String orderStatus, String secondaryStatus,
			Boolean disputeFlag, Float netAmount, Float gstAmount, Float totalAmount, String remarks) {
		super();
		this.orderId = orderId;
		this.userSurId = userSurId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.secondaryStatus = secondaryStatus;
		this.disputeFlag = disputeFlag;
		this.netAmount = netAmount;
		this.gstAmount = gstAmount;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
	}

	/**
	 * @param idStudentOrder
	 * @param orderId
	 * @param userSurId
	 * @param orderDate
	 * @param orderStatus
	 * @param secondaryStatus
	 * @param disputeFlag
	 * @param netAmount
	 * @param gstAmount
	 * @param totalAmount
	 * @param remarks
	 * @param couponCode
	 * @param actualAmount
	 */
	public StudentOrder(Long idStudentOrder, String orderId, Long userSurId, Instant orderDate, String orderStatus,
			String secondaryStatus, Boolean disputeFlag, Float netAmount, Float gstAmount, Float totalAmount,
			String remarks, String couponCode, Float actualAmount) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.orderId = orderId;
		this.userSurId = userSurId;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.secondaryStatus = secondaryStatus;
		this.disputeFlag = disputeFlag;
		this.netAmount = netAmount;
		this.gstAmount = gstAmount;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
		this.couponCode = couponCode;
		this.actualAmount = actualAmount;
	}

	
	
	
}
