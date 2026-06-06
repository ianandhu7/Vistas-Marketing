package co.vistafoundation.vlearning.auth.dto;

import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;

/**
 * @author Abdul Elahi
 */
public class NewSignupRequestV4DTO {

	private String firstName;

	private String lastName;

	private NewSubscriptionPlanDTO plan;

	private String mobileNumber;

	private String deviceId;

	private String couponCode;

	private String otp;

	private String platform;

	private String signUpSource;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the plan
	 */
	public NewSubscriptionPlanDTO getPlan() {
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(NewSubscriptionPlanDTO plan) {
		this.plan = plan;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the signUpSource
	 */
	public String getSignUpSource() {
		return signUpSource;
	}

	/**
	 * @param signUpSource the signUpSource to set
	 */
	public void setSignUpSource(String signUpSource) {
		this.signUpSource = signUpSource;
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param plan
	 * @param mobileNumber
	 * @param deviceId
	 * @param couponCode
	 * @param otp
	 */
	public NewSignupRequestV4DTO(String firstName, String lastName, NewSubscriptionPlanDTO plan, String mobileNumber,
			String deviceId, String couponCode, String otp) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.plan = plan;
		this.mobileNumber = mobileNumber;
		this.deviceId = deviceId;
		this.couponCode = couponCode;
		this.otp = otp;
	}

	/**
	 * 
	 */
	public NewSignupRequestV4DTO() {
		super();
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param plan
	 * @param mobileNumber
	 * @param deviceId
	 * @param couponCode
	 * @param otp
	 * @param platform
	 */
	public NewSignupRequestV4DTO(String firstName, String lastName, NewSubscriptionPlanDTO plan, String mobileNumber,
			String deviceId, String couponCode, String otp, String platform) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.plan = plan;
		this.mobileNumber = mobileNumber;
		this.deviceId = deviceId;
		this.couponCode = couponCode;
		this.otp = otp;
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "NewSignupRequestV4DTO [firstName=" + firstName + ", lastName=" + lastName + ", plan=" + plan
				+ ", mobileNumber=" + mobileNumber + ", deviceId=" + deviceId + ", couponCode=" + couponCode + ", otp="
				+ otp + "]";
	}

}
