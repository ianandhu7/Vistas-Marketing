package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

public class UserInfoDto {
	private long userSurId;
	private String name;
	private String mobile;
	private String email;
	private String classStandard;
	private String syllabus;
	private String state;
	private String subscriptionStatus;
	private Instant createdDate;
	public long getUserSurId() {
		return userSurId;
	}
	public void setUserSurId(long userSurId) {
		this.userSurId = userSurId;
	}
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
	public UserInfoDto(long userSurId, String name, String mobile, String email, String classStandard, String syllabus,
			String state, String subscriptionStatus, Instant createdDate) {
		super();
		this.userSurId = userSurId;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.classStandard = classStandard;
		this.syllabus = syllabus;
		this.state = state;
		this.subscriptionStatus = subscriptionStatus;
		this.createdDate = createdDate;
	}
	public UserInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
