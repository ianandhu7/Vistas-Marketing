/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * @author NAVEEN
 *
 *         This DTO created to enhance security in order to avoid take over
 *         Bypassing Mobile Verification during the time of sign-up, Please refer
 *         https://v-learning.atlassian.net/browse/VL-1216 for further
 *         information.
 * 
 */
public class NewSignupRequestV3DTO {

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String password;

	private String mobileNumber;

	private String role;

	private Long idSyllabus;

	private Long idMedium;

	private Long idClassStandard;

	private Long idSecondaryLanguage;

	private Long idState;

	private Long idSubject;

	private String deviceId;

	private String referralCode;

	private String otp;
	
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idMedium
	 */
	public Long getIdMedium() {
		return idMedium;
	}

	/**
	 * @param idMedium the idMedium to set
	 */
	public void setIdMedium(Long idMedium) {
		this.idMedium = idMedium;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the idSecondaryLanguage
	 */
	public Long getIdSecondaryLanguage() {
		return idSecondaryLanguage;
	}

	/**
	 * @param idSecondaryLanguage the idSecondaryLanguage to set
	 */
	public void setIdSecondaryLanguage(Long idSecondaryLanguage) {
		this.idSecondaryLanguage = idSecondaryLanguage;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
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
	 * @param username
	 * @param email
	 * @param password
	 * @param mobileNumber
	 * @param role
	 * @param idSyllabus
	 * @param idMedium
	 * @param idClassStandard
	 * @param idSecondaryLanguage
	 * @param idState
	 * @param idSubject
	 * @param deviceId
	 * @param referralCode
	 * @param otp
	 */
	public NewSignupRequestV3DTO(String firstName, String lastName, String username, String email, String password,
			String mobileNumber, String role, Long idSyllabus, Long idMedium, Long idClassStandard,
			Long idSecondaryLanguage, Long idState, Long idSubject, String deviceId, String referralCode, String otp) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.idSyllabus = idSyllabus;
		this.idMedium = idMedium;
		this.idClassStandard = idClassStandard;
		this.idSecondaryLanguage = idSecondaryLanguage;
		this.idState = idState;
		this.idSubject = idSubject;
		this.deviceId = deviceId;
		this.referralCode = referralCode;
		this.otp = otp;
	}

	/**
	 * 
	 */
	public NewSignupRequestV3DTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
