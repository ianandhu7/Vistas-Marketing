package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

public class KidsSubscriptionDTO {

	private Long idStudentSubscription;
	
	private Long idBatch;
	
	private Long idProductGroup;
	
	private Long idProduct;
	
	private Instant nextPaymentDate;
	
	private Instant lastPaymentDate;
	
	private Instant subscriptionEndDate;
	
	private String purchaseAmount;
	
	/**
	 * @param idStudentSubscription
	 * @param idBatch
	 * @param idProductGroup
	 * @param idProduct
	 * @param nextPaymentDate
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param purchaseAmount
	 * @param productCategory
	 */
	public KidsSubscriptionDTO(Long idStudentSubscription, Long idBatch, Long idProductGroup, Long idProduct,
			Instant nextPaymentDate, Instant lastPaymentDate, Instant subscriptionEndDate, String purchaseAmount) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idBatch = idBatch;
		this.idProductGroup = idProductGroup;
		this.idProduct = idProduct;
		this.nextPaymentDate = nextPaymentDate;
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.purchaseAmount = purchaseAmount;
	}

	/**
	 * 
	 */
	public KidsSubscriptionDTO() {
		
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
	
}
