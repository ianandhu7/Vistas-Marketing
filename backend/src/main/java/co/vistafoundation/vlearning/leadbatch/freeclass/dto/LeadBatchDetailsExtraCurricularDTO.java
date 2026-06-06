/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author Ahmed
 *
 */
public class LeadBatchDetailsExtraCurricularDTO {

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String mobileNumber;
	private String role;
	private Long idVlUser;
	private Long idSubjectExtraCurricular;
	private Long idLevelExtraCurricular;
	private Long idLanguage;
	private Long idAvailableSlot;
	private Boolean googleOAuthFlag;

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
	 * @return the idSubjectExtraCurricular
	 */
	public Long getIdSubjectExtraCurricular() {
		return idSubjectExtraCurricular;
	}

	/**
	 * @param idSubjectExtraCurricular the idSubjectExtraCurricular to set
	 */
	public void setIdSubjectExtraCurricular(Long idSubjectExtraCurricular) {
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
	}

	/**
	 * @return the idLevelExtraCurricular
	 */
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}

	/**
	 * @param idLevelExtraCurricular the idLevelExtraCurricular to set
	 */
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
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
	 * @return the idAvailableSlot
	 */
	public Long getIdAvailableSlot() {
		return idAvailableSlot;
	}

	/**
	 * @param idAvailableSlot the idAvailableSlot to set
	 */
	public void setIdAvailableSlot(Long idAvailableSlot) {
		this.idAvailableSlot = idAvailableSlot;
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
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param mobileNumber
	 * @param role
	 * @param idVlUser
	 * @param idSubjectExtraCurricular
	 * @param idLevelExtraCurricular
	 * @param idLanguage
	 * @param idAvailableSlot
	 * @param googleOAuthFlag
	 */
	public LeadBatchDetailsExtraCurricularDTO(String firstName, String lastName, String username, String email,
			String mobileNumber, String role, Long idVlUser, Long idSubjectExtraCurricular, Long idLevelExtraCurricular,
			Long idLanguage, Long idAvailableSlot, Boolean googleOAuthFlag) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.idVlUser = idVlUser;
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idLanguage = idLanguage;
		this.idAvailableSlot = idAvailableSlot;
		this.googleOAuthFlag = googleOAuthFlag;
	}

	/**
	 * 
	 */
	public LeadBatchDetailsExtraCurricularDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
