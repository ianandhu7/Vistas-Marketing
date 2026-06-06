/**
 * 
 */
package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

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
@Table(name = "STAGING_USER_SUBSCR_PMT")
public class StagingUserSubscriptionPayment extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSTAGING_USER_SUBSCR_PMT", nullable = false)
	private Long idStagingUserSubscriptionPayment;
	
	@Column(name = "idUSER_SUBSCR_PMT", nullable = false)
	private Long idUserSubscriptionPayment;
	
	@Column(name = "idSUBSCR_AMT_MASTER", nullable = false)
	private Long idSubscriptionAmountMaster;
	
	@Column(name = "idVL_USER")
	private Long userSurId;
	
	@Column(name = "PMT_DT", nullable = false)
	private LocalDate paymentDate;
	
	@Column(name = "SUBSCR_END_DT", nullable = false)
	private LocalDate subscriptionEndDate;

	@Column(name = "ORDER_ID", nullable = false)
	private String orderId;
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	@Column(name = "PMT_AMT", nullable = false)
	private String paymentAmount;
	
	@Column(name = "SUBSCR_INTERVAL", nullable = false)
	private String subscriptionInterval;
	
	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BANK_TXNID")
	private String bankTransactionId;

	@Column(name = "PAYMENT_MODE")
	private String paymentMode;

	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name = "TXN_AMOUNT")
	private Float transactionAmount;

	@Column(name = "TXN_DATE")
	private Instant transactionDate;

	/**
	 * 
	 */
	public StagingUserSubscriptionPayment() {
		super();
	}

	/**
	 * @param idUserSubscriptionPayment
	 * @param idSubscriptionAmountMaster
	 * @param userSurId
	 * @param paymentDate
	 * @param subscriptionEndDate
	 * @param orderId
	 * @param activeFlag
	 * @param paymentAmount
	 * @param subscriptionInterval
	 * @param bankName
	 * @param bankTransactionId
	 * @param paymentMode
	 * @param paymentStatus
	 * @param transactionAmount
	 * @param transactionDate
	 */
	public StagingUserSubscriptionPayment(Long idUserSubscriptionPayment, Long idSubscriptionAmountMaster,
			Long userSurId, LocalDate paymentDate, LocalDate subscriptionEndDate, String orderId, Boolean activeFlag,
			String paymentAmount, String subscriptionInterval, String bankName, String bankTransactionId,
			String paymentMode, String paymentStatus, Float transactionAmount, Instant transactionDate) {
		super();
		this.idUserSubscriptionPayment = idUserSubscriptionPayment;
		this.idSubscriptionAmountMaster = idSubscriptionAmountMaster;
		this.userSurId = userSurId;
		this.paymentDate = paymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.orderId = orderId;
		this.activeFlag = activeFlag;
		this.paymentAmount = paymentAmount;
		this.subscriptionInterval = subscriptionInterval;
		this.bankName = bankName;
		this.bankTransactionId = bankTransactionId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the idStagingUserSubscriptionPayment
	 */
	public Long getIdStagingUserSubscriptionPayment() {
		return idStagingUserSubscriptionPayment;
	}

	/**
	 * @return the idUserSubscriptionPayment
	 */
	public Long getIdUserSubscriptionPayment() {
		return idUserSubscriptionPayment;
	}

	/**
	 * @return the idSubscriptionAmountMaster
	 */
	public Long getIdSubscriptionAmountMaster() {
		return idSubscriptionAmountMaster;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @return the paymentDate
	 */
	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public LocalDate getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @return the paymentAmount
	 */
	public String getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @return the subscriptionInterval
	 */
	public String getSubscriptionInterval() {
		return subscriptionInterval;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return the bankTransactionId
	 */
	public String getBankTransactionId() {
		return bankTransactionId;
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @return the transactionAmount
	 */
	public Float getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @return the transactionDate
	 */
	public Instant getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param idStagingUserSubscriptionPayment the idStagingUserSubscriptionPayment to set
	 */
	public void setIdStagingUserSubscriptionPayment(Long idStagingUserSubscriptionPayment) {
		this.idStagingUserSubscriptionPayment = idStagingUserSubscriptionPayment;
	}

	/**
	 * @param idUserSubscriptionPayment the idUserSubscriptionPayment to set
	 */
	public void setIdUserSubscriptionPayment(Long idUserSubscriptionPayment) {
		this.idUserSubscriptionPayment = idUserSubscriptionPayment;
	}

	/**
	 * @param idSubscriptionAmountMaster the idSubscriptionAmountMaster to set
	 */
	public void setIdSubscriptionAmountMaster(Long idSubscriptionAmountMaster) {
		this.idSubscriptionAmountMaster = idSubscriptionAmountMaster;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(LocalDate subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @param subscriptionInterval the subscriptionInterval to set
	 */
	public void setSubscriptionInterval(String subscriptionInterval) {
		this.subscriptionInterval = subscriptionInterval;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @param bankTransactionId the bankTransactionId to set
	 */
	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Instant transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}
