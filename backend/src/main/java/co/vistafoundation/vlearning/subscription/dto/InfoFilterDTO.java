package co.vistafoundation.vlearning.subscription.dto;

import java.util.Date;
import java.util.List;

/**
 * @author Abdul Elahi
 */

public class InfoFilterDTO {
	
	private Long userSurId;
	
	private String email;

	private String mobile;

	private List<Boolean> subscribedStatus;
	
	private String subscriptionMode;

	private Date fromDate;

	private Date toDate;

	public InfoFilterDTO() {
		super();
	}

	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Boolean> getSubscribedStatus() {
		return subscribedStatus;
	}

	public void setSubscribedStatus(List<Boolean> subscribedStatus) {
		this.subscribedStatus = subscribedStatus;
	}
	
	

	public String getSubscriptionMode() {
		return subscriptionMode;
	}

	public void setSubscriptionMode(String subscriptionMode) {
		this.subscriptionMode = subscriptionMode;
	}

	public InfoFilterDTO(Long userSurId, String email, String mobile, List<Boolean> subscribedStatus,
			String subscriptionMode, Date fromDate, Date toDate) {
		super();
		this.userSurId = userSurId;
		this.email = email;
		this.mobile = mobile;
		this.subscribedStatus = subscribedStatus;
		this.subscriptionMode = subscriptionMode;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}


	
}
