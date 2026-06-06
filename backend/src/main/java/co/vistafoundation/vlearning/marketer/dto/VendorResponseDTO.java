/**
 * 
 */
package co.vistafoundation.vlearning.marketer.dto;

import java.time.LocalDate;

/**
 * @author NAVEEN
 *
 */
public class VendorResponseDTO {

	private Long idVendor;

	private String vendorName;

	private String email;

	private Long idVlUser;

	private Long onBoardingIdMarketer;

	private String onBoardingMarketer;

	private Long relationshipIdMarketer;

	private String relationshipMarketer;

	private String remarks;

	private LocalDate onBoardedDate;

	private String runnerPaidStatus;

	private String mobileNumber;

	private String referralCode;

	private Long idReferralCode;

	private Long totalStudents = 0l;
	
	private Float paymentAmount; 

	/**
	 * @return the idVendor
	 */
	public Long getIdVendor() {
		return idVendor;
	}

	/**
	 * @param idVendor the idVendor to set
	 */
	public void setIdVendor(Long idVendor) {
		this.idVendor = idVendor;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @return the onBoardingIdMarketer
	 */
	public Long getOnBoardingIdMarketer() {
		return onBoardingIdMarketer;
	}

	/**
	 * @param onBoardingIdMarketer the onBoardingIdMarketer to set
	 */
	public void setOnBoardingIdMarketer(Long onBoardingIdMarketer) {
		this.onBoardingIdMarketer = onBoardingIdMarketer;
	}

	/**
	 * @return the onBoardingMarketer
	 */
	public String getOnBoardingMarketer() {
		return onBoardingMarketer;
	}

	/**
	 * @param onBoardingMarketer the onBoardingMarketer to set
	 */
	public void setOnBoardingMarketer(String onBoardingMarketer) {
		this.onBoardingMarketer = onBoardingMarketer;
	}

	/**
	 * @return the relationshipIdMarketer
	 */
	public Long getRelationshipIdMarketer() {
		return relationshipIdMarketer;
	}

	/**
	 * @param relationshipIdMarketer the relationshipIdMarketer to set
	 */
	public void setRelationshipIdMarketer(Long relationshipIdMarketer) {
		this.relationshipIdMarketer = relationshipIdMarketer;
	}

	/**
	 * @return the relationshipMarketer
	 */
	public String getRelationshipMarketer() {
		return relationshipMarketer;
	}

	/**
	 * @param relationshipMarketer the relationshipMarketer to set
	 */
	public void setRelationshipMarketer(String relationshipMarketer) {
		this.relationshipMarketer = relationshipMarketer;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the onBoardedDate
	 */
	public LocalDate getOnBoardedDate() {
		return onBoardedDate;
	}

	/**
	 * @param onBoardedDate the onBoardedDate to set
	 */
	public void setOnBoardedDate(LocalDate onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	/**
	 * @return the runnerPaidStatus
	 */
	public String getRunnerPaidStatus() {
		return runnerPaidStatus;
	}

	/**
	 * @param runnerPaidStatus the runnerPaidStatus to set
	 */
	public void setRunnerPaidStatus(String runnerPaidStatus) {
		this.runnerPaidStatus = runnerPaidStatus;
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
	 * @return the referralCode
	 */
	public String getReferralCode() {
		return referralCode;
	}

	/**
	 * @param referralCode the referralCode to set
	 */
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	/**
	 * @return the totalStudents
	 */
	public Long getTotalStudents() {
		return totalStudents;
	}

	/**
	 * @param totalStudents the totalStudents to set
	 */
	public void setTotalStudents(Long totalStudents) {
		this.totalStudents = totalStudents;
	}

	/**
	 * @return the idReferralCode
	 */
	public Long getIdReferralCode() {
		return idReferralCode;
	}

	/**
	 * @param idReferralCode the idReferralCode to set
	 */
	public void setIdReferralCode(Long idReferralCode) {
		this.idReferralCode = idReferralCode;
	}

	/**
	 * @param idVendor
	 * @param vendorName
	 * @param email
	 * @param idVlUser
	 * @param onBoardingIdMarketer
	 * @param onBoardingMarketer
	 * @param relationshipIdMarketer
	 * @param relationshipMarketer
	 * @param remarks
	 * @param onBoardedDate
	 * @param runnerPaidStatus
	 * @param mobileNumber
	 * @param referralCode
	 * @param totalStudents
	 */
	public VendorResponseDTO(Long idVendor, String vendorName, String email, Long idVlUser, Long onBoardingIdMarketer,
			String onBoardingMarketer, Long relationshipIdMarketer, String relationshipMarketer, String remarks,
			LocalDate onBoardedDate, String runnerPaidStatus, String mobileNumber, String referralCode,
			Long idReferralCode,Float paymentAmount) {
		super();
		this.idVendor = idVendor;
		this.vendorName = vendorName;
		this.email = email;
		this.idVlUser = idVlUser;
		this.onBoardingIdMarketer = onBoardingIdMarketer;
		this.onBoardingMarketer = onBoardingMarketer;
		this.relationshipIdMarketer = relationshipIdMarketer;
		this.relationshipMarketer = relationshipMarketer;
		this.remarks = remarks;
		this.onBoardedDate = onBoardedDate;
		this.runnerPaidStatus = runnerPaidStatus;
		this.mobileNumber = mobileNumber;
		this.referralCode = referralCode;
		this.idReferralCode = idReferralCode;
		this.paymentAmount = paymentAmount;
	}

	/**
	 * 
	 */
	public VendorResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the paymentAmount
	 */
	public Float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	

}
