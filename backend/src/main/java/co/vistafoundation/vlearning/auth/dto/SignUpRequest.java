/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author vk
 *
 */
public class SignUpRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String username;

	@Email
	private String email;

	private String password;

	@NotBlank
	@Size(min = 10, max = 12)
	private String mobileNumber;

	@NotBlank
	@Size(min = 2, max = 20)
	private String role;

	private Long classStandard;

	@Size(min = 2, max = 20)
	private String secondaryLanguage;

	private Long idSyllabus;

	public SignUpRequest(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String username,
			@Email String email, String password, @NotBlank @Size(min = 10, max = 12) String mobileNumber,
			@NotBlank @Size(min = 2, max = 20) String role, Long classStandard,
			@Size(min = 2, max = 20) String secondaryLanguage, Long idSyllabus) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.classStandard = classStandard;
		this.secondaryLanguage = secondaryLanguage;
		this.idSyllabus = idSyllabus;

	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(Long classStandard) {
		this.classStandard = classStandard;
	}

	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}

	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}

}
