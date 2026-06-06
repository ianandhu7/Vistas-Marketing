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
 * @author NaveenKumar, vk
 * 
 **/

@Entity
@Table(name = "STUDENT_SUBSCRIPTION")
public class StudentSubscription extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscription;

	@Column(name = "idSTUDENT", nullable = false)
	private Long idStudent;

	@Column(name = "idBATCH")
	private Long idBatch;

	@Column(name = "idPRODUCT")
	private Long idProduct;

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

	@Column(name = "idPRODUCT_GROUP")
	private Long idProductGroup;

	@Column(name = "idPRODUCT_LINE")
	private Long idproductLine;

	@Column(name = "PURCHASE_TYPE")
	private String purchaseType;

	@Column(name = "idSTUDENT_ORDER")
	private Long idStudentOrder;

	@Column(name = "FREE_FLAG", columnDefinition = "boolean default false")
	private Boolean freeFlag;

	/*
	 * @author Ahmed
	 * 
	 */
	@Column(name = "SPECIAL_OFFER_FLAG")
	private Boolean specialOfferFlag;

	@Column(name = "ID_SPECIAL_OFFER")
	private Long idSpecialOffer;
	
	@Column(name = "idVL_USER")
	private Long userSurId;

	/**
	 * @param idStudent
	 * @param idBatch
	 * @param idProduct
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param idProductGroup
	 * @param idproductLine
	 * @param purchaseType
	 * @param idStudentOrder
	 */

	/**
	 * 
	 */
	public StudentSubscription() {
		super();
	}

	/**
	 * @param idStudentSubscription
	 * @param idStudent
	 * @param idBatch
	 * @param idProduct
	 * @param purchaseDate
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param activeFlag
	 * @param purchaseAmount
	 * @param subscriptionType
	 * @param purchaseLevel
	 * @param idProductGroup
	 * @param idproductLine
	 * @param purchaseType
	 * @param idStudentOrder
	 * @param freeFlag
	 * @param specialOfferFlag
	 * @param idSpecialOffer
	 */
	public StudentSubscription(Long idStudentSubscription, Long idStudent, Long idBatch, Long idProduct,
			Instant purchaseDate, Instant nextPaymentDate, Instant lastPaymentDate, Instant subscriptionEndDate,
			Boolean activeFlag, String purchaseAmount, String subscriptionType, String purchaseLevel,
			Long idProductGroup, Long idproductLine, String purchaseType, Long idStudentOrder, Boolean freeFlag,
			Boolean specialOfferFlag, Long idSpecialOffer, Long userSurId) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idStudent = idStudent;
		this.idBatch = idBatch;
		this.idProduct = idProduct;
		this.purchaseDate = purchaseDate;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.activeFlag = activeFlag;
		this.purchaseAmount = purchaseAmount;
		this.subscriptionType = subscriptionType;
		this.purchaseLevel = purchaseLevel;
		this.idProductGroup = idProductGroup;
		this.idproductLine = idproductLine;
		this.purchaseType = purchaseType;
		this.idStudentOrder = idStudentOrder;
		this.freeFlag = freeFlag;
		this.specialOfferFlag = specialOfferFlag;
		this.idSpecialOffer = idSpecialOffer;
		this.userSurId = userSurId;
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

	public Boolean getFreeFlag() {
		return freeFlag;
	}

	public void setFreeFlag(Boolean freeFlag) {
		this.freeFlag = freeFlag;
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
	
}
