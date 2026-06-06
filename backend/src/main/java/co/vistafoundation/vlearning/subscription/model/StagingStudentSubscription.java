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
@Table(name = "STAGING_STUDENT_SUBSCRIPTION")
public class StagingStudentSubscription extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTAGING_STUDENT_SUBSCRIPTION", nullable = false)
	private Long idStagingStudentSubscription;

	@Column(name = "idSTUDENT_SUBSCR")
	private Long idStudentSubscription;
	
	@Column(name = "idSTUDENT", nullable = false)
	private Long idStudent;
	
	@Column(name = "idBATCH")
	private Long idBatch;

	@Column(name = "idPRODUCT")
	private Long idProduct;
	
	@Column(name = "idPRODUCT_GROUP")
	private Long idProductGroup;	
	
	@Column(name = "idPRODUCT_LINE")
	private Long idproductLine;
	
	@Column(name = "idSTUDENT_ORDER")
	private Long idStudentOrder;

	@Column(name = "PURCHASE_DT")
	private Instant purchaseDate;

	@Column(name = "NEXT_PMT_DT")
	private Instant nextPaymentDate;

	@Column(name = "LAST_PMT_DT")
	private Instant lastPaymentDate;

	@Column(name = "SUBSCR_END_DT")
	private Instant subscriptionEndDate;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;

	@Column(name = "PURCHASE_AMT")
	private String purchaseAmount;

	@Column(name = "SUBSCRIPTION_TYPE")
	private String subscriptionType;

	@Column(name = "PURCHASE_LEVEL")
	private String purchaseLevel;
	
	@Column(name = "PURCHASE_TYPE")
	private String purchaseType;

	@Column(name = "BANK_NAME")
	private String bankName;

	@Column(name = "BANK_TXNID")
	private String bankTransactionId;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "PAYMENT_MODE")
	private String paymentMode;

	@Column(name = "PAYMENT_STATUS")
	private String paymentStatus;

	@Column(name = "TXN_AMOUNT")
	private Float transactionAmount;

	@Column(name = "TXN_DATE")
	private Instant transactionDate;
	
	@Column(name = "idVL_USER")
	private Long userSurId;
	
	@Column(name = "SPECIAL_OFFER_FLAG")
	private Boolean specialOfferFlag;

	@Column(name = "ID_SPECIAL_OFFER")
	private Long idSpecialOffer;

	/**
	 * @param idStudentSubscription
	 * @param idStudent
	 * @param idBatch
	 * @param idProduct
	 * @param idProductGroup
	 * @param idproductLine
	 * @param idStudentOrder
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param purchaseType
	 * @param bankName
	 * @param bankTransactionId
	 * @param orderId
	 * @param paymentMode
	 * @param paymentStatus
	 * @param transactionAmount
	 * @param transactionDate
	 * @param userSurId
	 */
	public StagingStudentSubscription(Long idStudentSubscription, Long idStudent, Long idBatch, Long idProduct,
			Long idProductGroup, Long idproductLine, Long idStudentOrder, Instant purchaseDate, Instant nextPaymentDate,
			Instant lastPaymentDate, Instant subscriptionEndDate, Boolean activeFlag, String purchaseAmount,
			String subscriptionType, String purchaseLevel, String purchaseType, String bankName,
			String bankTransactionId, String orderId, String paymentMode, String paymentStatus, Float transactionAmount,
			Instant transactionDate, Long userSurId) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idStudent = idStudent;
		this.idBatch = idBatch;
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.idproductLine = idproductLine;
		this.idStudentOrder = idStudentOrder;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.purchaseType = purchaseType;
		this.bankName = bankName;
		this.bankTransactionId = bankTransactionId;
		this.orderId = orderId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.userSurId = userSurId;
	}

	/**
	 * 
	 */
	public StagingStudentSubscription() {
		super();
	}

	/**
	 * @return the idStagingStudentSubscription
	 */
	public Long getIdStagingStudentSubscription() {
		return idStagingStudentSubscription;
	}

	/**
	 * @param idStagingStudentSubscription the idStagingStudentSubscription to set
	 */
	public void setIdStagingStudentSubscription(Long idStagingStudentSubscription) {
		this.idStagingStudentSubscription = idStagingStudentSubscription;
	}

	/**
	 * @return the idStudentSubscription
	 */
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the idProductGroup
	 */
	public Long getIdProductGroup() {
		return idProductGroup;
	}

	/**
	 * @param idProductGroup the idProductGroup to set
	 */
	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}

	/**
	 * @return the idproductLine
	 */
	public Long getIdproductLine() {
		return idproductLine;
	}

	/**
	 * @param idproductLine the idproductLine to set
	 */
	public void setIdproductLine(Long idproductLine) {
		this.idproductLine = idproductLine;
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
	 * @return the purchaseDate
	 */
	public Instant getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Instant purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the nextPaymentDate
	 */
	public Instant getNextPaymentDate() {
		return nextPaymentDate;
	}

	/**
	 * @param nextPaymentDate the nextPaymentDate to set
	 */
	public void setNextPaymentDate(Instant nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Instant getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Instant lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public Instant getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Instant subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the purchaseAmount
	 */
	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	/**
	 * @return the subscriptionType
	 */
	public String getSubscriptionType() {
		return subscriptionType;
	}

	/**
	 * @param subscriptionType the subscriptionType to set
	 */
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	/**
	 * @return the purchaseLevel
	 */
	public String getPurchaseLevel() {
		return purchaseLevel;
	}

	/**
	 * @param purchaseLevel the purchaseLevel to set
	 */
	public void setPurchaseLevel(String purchaseLevel) {
		this.purchaseLevel = purchaseLevel;
	}

	/**
	 * @return the purchaseType
	 */
	public String getPurchaseType() {
		return purchaseType;
	}

	/**
	 * @param purchaseType the purchaseType to set
	 */
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankTransactionId
	 */
	public String getBankTransactionId() {
		return bankTransactionId;
	}

	/**
	 * @param bankTransactionId the bankTransactionId to set
	 */
	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
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
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the transactionAmount
	 */
	public Float getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the transactionDate
	 */
	public Instant getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Instant transactionDate) {
		this.transactionDate = transactionDate;
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
	 * @return the specialOfferFlag
	 */
	public Boolean getSpecialOfferFlag() {
		return specialOfferFlag;
	}

	/**
	 * @param specialOfferFlag the specialOfferFlag to set
	 */
	public void setSpecialOfferFlag(Boolean specialOfferFlag) {
		this.specialOfferFlag = specialOfferFlag;
	}

	/**
	 * @return the idSpecialOffer
	 */
	public Long getIdSpecialOffer() {
		return idSpecialOffer;
	}

	/**
	 * @param idSpecialOffer the idSpecialOffer to set
	 */
	public void setIdSpecialOffer(Long idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
	}
	
	
	
}
