/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class LeadBatchDetailsRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String username;

	@Email
	private String email;
	
	@NotBlank
	@Size(min = 10, max = 12)
	private String mobileNumber;

	@NotBlank
	@Size(min = 2, max = 20)
	private String role;
	private Long idVlUser;
	private Long idSyllabus;
	private Long idClassStandard;
	private Long idSubject;
	private Long idAvailableSchedule;
	private Long idSujectChapter;
	private Long idLanguage;
	private Long idMedium;
	private Boolean googleOAuthFlag;
	private Long idState;
	private String referralCode;
	



	/**
	 * 
	 */
	public LeadBatchDetailsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param mobileNumber
	 * @param role
	 * @param idVlUser
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param idSubject
	 * @param idAvailableSchedule
	 * @param idSujectChapter
	 * @param idLanguage
	 * @param idMedium
	 * @param googleOAuthFlag
	 */
	public LeadBatchDetailsRequest(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String username,
			@NotBlank @Email String email, @NotBlank @Size(min = 10, max = 12) String mobileNumber,
			@NotBlank @Size(min = 2, max = 20) String role, Long idVlUser, Long idSyllabus, Long idClassStandard,
			Long idSubject, Long idAvailableSchedule, Long idSujectChapter, Long idLanguage, Long idMedium,
			Boolean googleOAuthFlag, String referralCode) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.idVlUser = idVlUser;
		this.idSyllabus = idSyllabus;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idAvailableSchedule = idAvailableSchedule;
		this.idSujectChapter = idSujectChapter;
		this.idLanguage = idLanguage;
		this.idMedium = idMedium;
		this.googleOAuthFlag = googleOAuthFlag;
		this.referralCode=referralCode;
	}

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
	 * @return the idAvailableSchedule
	 */
	public Long getIdAvailableSchedule() {
		return idAvailableSchedule;
	}

	/**
	 * @param idAvailableSchedule the idAvailableSchedule to set
	 */
	public void setIdAvailableSchedule(Long idAvailableSchedule) {
		this.idAvailableSchedule = idAvailableSchedule;
	}

	/**
	 * @return the idSujectChapter
	 */
	public Long getIdSujectChapter() {
		return idSujectChapter;
	}

	/**
	 * @param idSujectChapter the idSujectChapter to set
	 */
	public void setIdSujectChapter(Long idSujectChapter) {
		this.idSujectChapter = idSujectChapter;
	}

	/**
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
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
	 * @return the googleOAuthFlag
	 */
	public Boolean getGoogleOAuthFlag() {
		return googleOAuthFlag;
	}

	/**
	 * @param googleOAuthFlag the googleOAuthFlag to set
	 */
	public void setGoogleOAuthFlag(Boolean googleOAuthFlag) {
		this.googleOAuthFlag = googleOAuthFlag;
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
	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	

}
