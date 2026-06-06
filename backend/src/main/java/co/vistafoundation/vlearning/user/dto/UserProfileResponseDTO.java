/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

/**
 * @author vk
 *
 */
public class UserProfileResponseDTO {

	private Long userSurId;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	private String registeredAs;

	private String mobileNumber;

	private String secondaryLanguage;

	private Long classStandard;

	private String userProfilePic;
	
	/**
	 * 
	 */
	public UserProfileResponseDTO() {
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
	 * @param classStandard
	 * @param userProfilePic
	 */
	public UserProfileResponseDTO(Long userSurId, String firstName, String lastName, String username, String email,
			String registeredAs, String mobileNumber, String secondaryLanguage, Long classStandard,
			String userProfilePic) {
		super();
		this.userSurId = userSurId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.registeredAs = registeredAs;
		this.mobileNumber = mobileNumber;
		this.secondaryLanguage = secondaryLanguage;
		this.classStandard = classStandard;
		this.userProfilePic = userProfilePic;
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
	
}
