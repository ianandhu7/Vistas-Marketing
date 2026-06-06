package co.vistafoundation.vlearning.batch.dto;

/**
 * @author Meghana, vk
 *
 */
public class BatchSubscriptionExpiryDTO {

	private boolean expiry;

	private boolean subscriptionEnded;

	private String expirymessage;

	/**
	 * @return the expiry
	 */
	public boolean isExpiry() {
		return expiry;
	}

	/**
	 * @param expiry the expiry to set
	 */
	public void setExpiry(boolean expiry) {
		this.expiry = expiry;
	}

	/**
	 * @return the subscriptionEnded
	 */
	public boolean isSubscriptionEnded() {
		return subscriptionEnded;
	}

	/**
	 * @param subscriptionEnded the subscriptionEnded to set
	 */
	public void setSubscriptionEnded(boolean subscriptionEnded) {
		this.subscriptionEnded = subscriptionEnded;
	}

	/**
	 * @return the expirymessage
	 */
	public String getExpirymessage() {
		return expirymessage;
	}

	/**
	 * @param expirymessage the expirymessage to set
	 */
	public void setExpirymessage(String expirymessage) {
		this.expirymessage = expirymessage;
	}

}
