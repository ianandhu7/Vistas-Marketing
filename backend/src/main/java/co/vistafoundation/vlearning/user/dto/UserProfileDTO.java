package co.vistafoundation.vlearning.user.dto;

import javax.validation.constraints.NotNull;

public class UserProfileDTO {

	@NotNull
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String mobileNumber;

	@NotNull
	private Long  userSurId;

	@NotNull
	private Long classStandard;

	private String classStandadName;

	@NotNull
	private Long idSyllabus;

	private String syllabusName;

	@NotNull
	private Long idState;
	
	private String state;

	@NotNull
	private Long idStudentMedium;

	private String medium;

	@NotNull
	private Long idSecondLanguage;

	private String secondLanguageName;
	
	private Boolean isProfileEdited;
	
	/**
	 * 
	 */
	public UserProfileDTO() {
		super();
	}

	/**
	 * @param firstName
	 * @param classStandard
	 * @param idSyllabus
	 * @param idState
	 * @param idStudentMedium
	 * @param idSecondLanguage
	 */
	public UserProfileDTO(@NotNull String firstName, @NotNull Long classStandard, @NotNull Long idSyllabus,
			@NotNull Long idState, @NotNull Long idStudentMedium, @NotNull Long idSecondLanguage) {
		super();
		this.firstName = firstName;
		this.classStandard = classStandard;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.idStudentMedium = idStudentMedium;
		this.idSecondLanguage = idSecondLanguage;
	}
	
	/**
	 * @param firstName
	 * @param email
	 * @param classStandard
	 * @param idSyllabus
	 * @param idState
	 * @param idStudentMedium
	 * @param idSecondLanguage
	 */
	public UserProfileDTO(@NotNull String firstName,String email, @NotNull Long classStandard, @NotNull Long idSyllabus,
			@NotNull Long idState, @NotNull Long idStudentMedium, @NotNull Long idSecondLanguage) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.classStandard = classStandard;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.idStudentMedium = idStudentMedium;
		this.idSecondLanguage = idSecondLanguage;
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
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the classStandard
	 */
	public Long getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(Long classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @return the classStandadName
	 */
	public String getClassStandadName() {
		return classStandadName;
	}

	/**
	 * @param classStandadName the classStandadName to set
	 */
	public void setClassStandadName(String classStandadName) {
		this.classStandadName = classStandadName;
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
	 * @return the syllabusName
	 */
	public String getSyllabusName() {
		return syllabusName;
	}

	/**
	 * @param syllabusName the syllabusName to set
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the idStudentMedium
	 */
	public Long getIdStudentMedium() {
		return idStudentMedium;
	}

	/**
	 * @param idStudentMedium the idStudentMedium to set
	 */
	public void setIdStudentMedium(Long idStudentMedium) {
		this.idStudentMedium = idStudentMedium;
	}

	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param medium the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @return the idSecondLanguage
	 */
	public Long getIdSecondLanguage() {
		return idSecondLanguage;
	}

	/**
	 * @param idSecondLanguage the idSecondLanguage to set
	 */
	public void setIdSecondLanguage(Long idSecondLanguage) {
		this.idSecondLanguage = idSecondLanguage;
	}

	/**
	 * @return the secondLanguageName
	 */
	public String getSecondLanguageName() {
		return secondLanguageName;
	}

	/**
	 * @param secondLanguageName the secondLanguageName to set
	 */
	public void setSecondLanguageName(String secondLanguageName) {
		this.secondLanguageName = secondLanguageName;
	}

	/**
	 * @return the isProfileEdited
	 */
	public Boolean getIsProfileEdited() {
		return isProfileEdited;
	}

	/**
	 * @param isProfileEdited the isProfileEdited to set
	 */
	public void setIsProfileEdited(Boolean isProfileEdited) {
		this.isProfileEdited = isProfileEdited;
	}

	@Override
	public String toString() {
		return "UserProfileDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", userSurId=" + userSurId + ", classStandard=" + classStandard
				+ ", classStandadName=" + classStandadName + ", idSyllabus=" + idSyllabus + ", syllabusName="
				+ syllabusName + ", idState=" + idState + ", state=" + state + ", idStudentMedium=" + idStudentMedium
				+ ", medium=" + medium + ", idSecondLanguage=" + idSecondLanguage + ", secondLanguageName="
				+ secondLanguageName + ", isProfileEdited=" + isProfileEdited + "]";
	}
	
	
	
}
