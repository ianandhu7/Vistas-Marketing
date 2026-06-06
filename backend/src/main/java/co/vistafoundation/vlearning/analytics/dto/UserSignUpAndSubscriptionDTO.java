package co.vistafoundation.vlearning.analytics.dto;

import java.io.Serializable;
import java.util.List;

public class UserSignUpAndSubscriptionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511007499717243751L;
	private List<String> labels;
	private List<Integer> userSignUpData;
	private List<Integer> userSubscriptionData;
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<Integer> getUserSignUpData() {
		return userSignUpData;
	}
	public void setUserSignUpData(List<Integer> userSignUpData) {
		this.userSignUpData = userSignUpData;
	}
	public List<Integer> getUserSubscriptionData() {
		return userSubscriptionData;
	}
	public void setUserSubscriptionData(List<Integer> userSubscriptionData) {
		this.userSubscriptionData = userSubscriptionData;
	}
	

}
