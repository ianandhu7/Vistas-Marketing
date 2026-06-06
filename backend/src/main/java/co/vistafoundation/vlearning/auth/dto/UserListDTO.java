/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

import java.time.Instant;

/**
 * @author Ahmed
 * @author vk
 * @date updated 07-02-2022
 */
public class UserListDTO {

	private Long userSurId;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String registeredAs;

	private String mobileNumber;

	private String secondaryLanguage;

	private String state;

	private String userProfilePic;

	private Long idClassStandard;

	private String classStandadName;

	private Boolean activeFlag;

	private Instant createdAtTime;

	private Boolean premiumUser;
	
	private Boolean studentEditFalg;
	
    private Long idSyllabus;
	
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Boolean getStudentEditFalg() {
		return studentEditFalg;
	}
	
	/**
	 * 
	 */
	public UserListDTO() {
		super();
	}

	/**
	 * @param userSurId
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param registeredAs
	 * @param mobileNumber
	 * @param secondaryLanguage
	 * @param userProfilePic
	 * @param idClassStandard
	 * @param classStandadName
	 * @param activeFlag
	 * @param createdAtTime
	 * @param premiumUser
	 */
	public UserListDTO(Long userSurId, String firstName, String lastName, String username, String email,
			String registeredAs, String mobileNumber, String secondaryLanguage, String userProfilePic,
			Long idClassStandard, String classStandadName, Boolean activeFlag, Instant createdAtTime,
			Boolean premiumUser) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.userProfilePic = userProfilePic;
		this.idClassStandard = idClassStandard;
		this.classStandadName = classStandadName;
		this.activeFlag = activeFlag;
		this.createdAtTime = createdAtTime;
		this.premiumUser = premiumUser;
	}

	/**
	 * @param userSurId
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param email
	 * @param registeredAs
	 * @param mobileNumber
	 * @param secondaryLanguage
	 * @param userProfilePic
	 * @param idClassStandard
	 * @param classStandadName
	 * @param activeFlag
	 * @param createdAtTime
	 * @param premiumUser
	 * @param studentEditFalg
	 */
	public UserListDTO(Long userSurId, String firstName, String lastName, String username, String email,
			String registeredAs, String mobileNumber, String secondaryLanguage, String userProfilePic,
			Long idClassStandard, String classStandadName, Boolean activeFlag, Instant createdAtTime,
			Boolean premiumUser, Boolean studentEditFalg) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.userProfilePic = userProfilePic;
		this.idClassStandard = idClassStandard;
		this.classStandadName = classStandadName;
		this.activeFlag = activeFlag;
		this.createdAtTime = createdAtTime;
		this.premiumUser = premiumUser;
		this.studentEditFalg = studentEditFalg;
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
	 * @return the registeredAs
	 */
	public String getRegisteredAs() {
		return registeredAs;
	}

	/**
	 * @param registeredAs the registeredAs to set
	 */
	public void setRegisteredAs(String registeredAs) {
		this.registeredAs = registeredAs;
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
	 * @return the secondaryLanguage
	 */
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}

	/**
	 * @param secondaryLanguage the secondaryLanguage to set
	 */
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}

	/**
	 * @return the userProfilePic
	 */
	public String getUserProfilePic() {
		return userProfilePic;
	}

	/**
	 * @param userProfilePic the userProfilePic to set
	 */
	public void setUserProfilePic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
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
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the createdAtTime
	 */
	public Instant getCreatedAtTime() {
		return createdAtTime;
	}

	/**
	 * @param createdAtTime the createdAtTime to set
	 */
	public void setCreatedAtTime(Instant createdAtTime) {
		this.createdAtTime = createdAtTime;
	}

	/**
	 * @return the premiumUser
	 */
	public Boolean getPremiumUser() {
		return premiumUser;
	}

	/**
	 * @param premiumUser the premiumUser to set
	 */
	public void setPremiumUser(Boolean premiumUser) {
		this.premiumUser = premiumUser;
	}

	/**
	 * @return the studentEditFalg
	 */
	public Boolean isStudentEditFalg() {
		return studentEditFalg;
	}

	/**
	 * @param studentEditFalg the studentEditFalg to set
	 */
	public void setStudentEditFalg(Boolean studentEditFalg) {
		this.studentEditFalg = studentEditFalg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	public UserListDTO(Long userSurId, String firstName, String lastName, String username, String email,
			String registeredAs, String mobileNumber, String secondaryLanguage, String state, String userProfilePic,
			Long idClassStandard, String classStandadName, Boolean activeFlag, Instant createdAtTime,
			Boolean premiumUser, Boolean studentEditFalg , Long idSyllabus) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.state = state;
		this.userProfilePic = userProfilePic;
		this.idClassStandard = idClassStandard;
		this.classStandadName = classStandadName;
		this.activeFlag = activeFlag;
		this.createdAtTime = createdAtTime;
		this.premiumUser = premiumUser;
		this.studentEditFalg = studentEditFalg;
		this.idSyllabus = idSyllabus;


	}

	public UserListDTO(Long userSurId, String firstName, String lastName, String username, String email,
			String registeredAs, String mobileNumber, String secondaryLanguage, String userProfilePic,
			Long idClassStandard, String classStandadName, Boolean activeFlag, Instant createdAtTime,
			Boolean premiumUser, Boolean studentEditFalg, Long idSyllabus) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.userProfilePic = userProfilePic;
		this.idClassStandard = idClassStandard;
		this.classStandadName = classStandadName;
		this.activeFlag = activeFlag;
		this.createdAtTime = createdAtTime;
		this.premiumUser = premiumUser;
		this.studentEditFalg = studentEditFalg;
		this.idSyllabus = idSyllabus;
	}
	
	
	
	

}
