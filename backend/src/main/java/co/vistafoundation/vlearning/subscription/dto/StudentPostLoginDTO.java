/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

/**
 * @author NAVEEN
 *
 */
public class StudentPostLoginDTO {
	
	
	private Boolean  subscribedFlag;
	
	private Boolean  trialFlag ;

	/**
	 * @return the subscribedFlag
	 */
	public Boolean getSubscribedFlag() {
		return subscribedFlag;
	}

	/**
	 * @param subscribedFlag the subscribedFlag to set
	 */
	public void setSubscribedFlag(Boolean subscribedFlag) {
		this.subscribedFlag = subscribedFlag;
	}

	/**
	 * @return the trialFlag
	 */
	public Boolean getTrialFlag() {
		return trialFlag;
	}

	/**
	 * @param trialFlag the trialFlag to set
	 */
	public void setTrialFlag(Boolean trialFlag) {
		this.trialFlag = trialFlag;
	}
	

}
