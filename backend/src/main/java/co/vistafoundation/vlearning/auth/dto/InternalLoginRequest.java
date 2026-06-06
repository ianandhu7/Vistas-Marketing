package co.vistafoundation.vlearning.auth.dto;
/**
 * 
 */

import javax.validation.constraints.NotBlank;

/**
 * @author vk
 *
 */
public class InternalLoginRequest {

	@NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
    
    @NotBlank
    private String role;

	/**
	 * @return the usernameOrEmail
	 */
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param usernameOrEmail the usernameOrEmail to set
	 */
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
    
}
