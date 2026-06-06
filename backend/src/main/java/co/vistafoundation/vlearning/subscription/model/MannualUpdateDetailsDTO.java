package co.vistafoundation.vlearning.subscription.model;

import java.time.Instant;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;

public class MannualUpdateDetailsDTO {
	private Long userSurId;
	private String name;
	private String phone;
	private Long classStandard;
	private Instant lastSubscriptionEndDate;
	private Integer noOfMonths;
	private Instant nextSubscriptionEndDate;
	private String type;
	private Boolean isSubscriptionExists;
	private String planType;
	private String message;
	private Float plan;
	private Boolean isValid = true;
	private UserPrincipal userPrincipal;
	private Long idStudent;
	private Long idProduct;
	private String purchaseLevel;
	

	public Long getUserSurId() {
		return userSurId;
	}

	public boolean getisValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(Long classStandard) {
		this.classStandard = classStandard;
	}

	public Instant getLastSubscriptionEndDate() {
		return lastSubscriptionEndDate;
	}

	public void setLastSubscriptionEndDate(Instant lastSubscriptionEndDate) {
		this.lastSubscriptionEndDate = lastSubscriptionEndDate;
	}

	public Integer getNoOfMonths() {
		return noOfMonths;
	}

	public void setNoOfMonths(Integer noOfMonths) {
		this.noOfMonths = noOfMonths;
	}

	public Instant getNextSubscriptionEndDate() {
		return nextSubscriptionEndDate;
	}

	public void setNextSubscriptionEndDate(Instant nextSubscriptionEndDate) {
		this.nextSubscriptionEndDate = nextSubscriptionEndDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsSubscriptionExists() {
		return isSubscriptionExists;
	}

	public void setIsSubscriptionExists(Boolean isSubscriptionExists) {
		this.isSubscriptionExists = isSubscriptionExists;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Float getPlan() {
		return plan;
	}

	public void setPlan(Float plan) {
		this.plan = plan;
	}
	

	
//	/**
//	 * @return the isValid
//	 */
//	public Boolean getIsValid() {
//		return isValid;
//	}
//
//	/**
//	 * @param isValid the isValid to set
//	 */
//	public void setIsValid(Boolean isValid) {
//		this.isValid = isValid;
//	}

	/**
	 * @return the userPrincipal
	 */
	public UserPrincipal getUserPrincipal() {
		return userPrincipal;
	}

	/**
	 * @param userPrincipal the userPrincipal to set
	 */
	public void setUserPrincipal(UserPrincipal userPrincipal) {
		this.userPrincipal = userPrincipal;
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

	
	
	
	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getPurchaseLevel() {
		return purchaseLevel;
	}

	public void setPurchaseLevel(String purchaseLevel) {
		this.purchaseLevel = purchaseLevel;
	}

	public MannualUpdateDetailsDTO(Long userSurId, String name, String phone, Long classStandard,
			Instant lastSubscriptionEndDate, Integer noOfMonths, Instant nextSubscriptionEndDate, String type,
			Boolean isSubscriptionExists, String planType, String message, Float plan, boolean isValid) {
		super();
		this.userSurId = userSurId;
		this.name = name;
		this.phone = phone;
		this.classStandard = classStandard;
		this.lastSubscriptionEndDate = lastSubscriptionEndDate;
		this.noOfMonths = noOfMonths;
		this.nextSubscriptionEndDate = nextSubscriptionEndDate;
		this.type = type;
		this.isSubscriptionExists = isSubscriptionExists;
		this.planType = planType;
		this.message = message;
		this.plan = plan;
		this.isValid = isValid;
	}
	
	

	/**
	 * @param userSurId
	 * @param name
	 * @param phone
	 * @param classStandard
	 * @param lastSubscriptionEndDate
	 * @param noOfMonths
	 * @param nextSubscriptionEndDate
	 * @param type
	 * @param isSubscriptionExists
	 * @param planType
	 * @param message
	 * @param plan
	 * @param isValid
	 * @param userPrincipal
	 * @param idStudent
	 */
	public MannualUpdateDetailsDTO(Long userSurId, String name, String phone, Long classStandard,
			Instant lastSubscriptionEndDate, Integer noOfMonths, Instant nextSubscriptionEndDate, String type,
			Boolean isSubscriptionExists, String planType, String message, Float plan, Boolean isValid,
			UserPrincipal userPrincipal, Long idStudent) {
		super();
		this.userSurId = userSurId;
		this.name = name;
		this.phone = phone;
		this.classStandard = classStandard;
		this.lastSubscriptionEndDate = lastSubscriptionEndDate;
		this.noOfMonths = noOfMonths;
		this.nextSubscriptionEndDate = nextSubscriptionEndDate;
		this.type = type;
		this.isSubscriptionExists = isSubscriptionExists;
		this.planType = planType;
		this.message = message;
		this.plan = plan;
		this.isValid = isValid;
		this.userPrincipal = userPrincipal;
		this.idStudent = idStudent;
	}

	public MannualUpdateDetailsDTO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MannualUpdateDetailsDTO [userSurId=" + userSurId + ", name=" + name + ", phone=" + phone
				+ ", classStandard=" + classStandard + ", lastSubscriptionEndDate=" + lastSubscriptionEndDate
				+ ", noOfMonths=" + noOfMonths + ", nextSubscriptionEndDate=" + nextSubscriptionEndDate + ", type="
				+ type + ", isSubscriptionExists=" + isSubscriptionExists + ", planType=" + planType + ", message="
				+ message + ", plan=" + plan + ", isValid=" + isValid + ", userPrincipal=" + userPrincipal
				+ ", idStudent=" + idStudent + "]";
	}

}
