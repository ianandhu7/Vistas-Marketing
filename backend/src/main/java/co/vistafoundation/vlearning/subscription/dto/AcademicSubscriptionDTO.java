/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

/**
 * @author vk
 *
 */
public class AcademicSubscriptionDTO {

	private Long idProduct;
	
	private Long idProductGroup;
	
	private Long idStudentSubscription;

	private String productName;

	private Instant lastPaymentDate;

	private Instant nextPaymentDate;

	private Instant subscriptionEndDate;

	private String purchaseAmount;

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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
