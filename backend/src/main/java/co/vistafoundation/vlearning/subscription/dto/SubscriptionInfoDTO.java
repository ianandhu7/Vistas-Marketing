package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

public class SubscriptionInfoDTO {
	private long userSurId;
	public long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(long userSurId) {
		this.userSurId = userSurId;
	}

	private String name;
	private String mobile;
	private String email;
	private String classStandard;
	private String syllabus;
	private String state;
	private String subscriptionStatus;
	private Instant createdDate;
	private Instant endDate;
	private String subscriptionType;
	private int daysPending;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public int getDaysPending() {
		return daysPending;
	}

	public void setDaysPending(int daysPending) {
		this.daysPending = daysPending;
	}

	public SubscriptionInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubscriptionInfoDTO(String name, String mobile, String email, String classStandard, String syllabus,
			String state, String subscriptionStatus, Instant createdDate, Instant endDate,
			String subscriptionType, int daysPending) {
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.classStandard = classStandard;
		this.syllabus = syllabus;
		this.state = state;
		this.subscriptionStatus = subscriptionStatus;
		this.createdDate = createdDate;
		this.endDate = endDate;
		this.subscriptionType = subscriptionType;
		this.daysPending = daysPending;
	}

	@Override
	public String toString() {
		return "SubscriptionInfoDTO [name=" + name + ", mobile=" + mobile + ", email=" + email + ", classStandard="
				+ classStandard + ", syllabus=" + syllabus + ", state=" + state 
				+ ", subscriptionStatus=" + subscriptionStatus + ", createdDate=" + createdDate + ", endDate=" + endDate
				+ ", subscriptionType=" + subscriptionType + ", daysPending=" + daysPending + "]";
	}

	
}
