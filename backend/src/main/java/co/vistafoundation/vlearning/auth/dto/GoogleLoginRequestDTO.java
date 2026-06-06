/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author vk
 *
 */
public class GoogleLoginRequestDTO {

	@NotBlank
	private String email;
	
	@NotBlank
	private String idToken;
	
	private String deviceId;
	
	/**
	 * @param email
	 * @param idToken
	 * @param deviceId
	 */
	public GoogleLoginRequestDTO(@NotBlank String email, @NotBlank String idToken, String deviceId) {
		super();
		this.email = email;
		this.idToken = idToken;
		this.deviceId = deviceId;
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
	 * @return the idToken
	 */
	public String getIdToken() {
		return idToken;
	}

	/**
	 * @param idToken the idToken to set
	 */
	public void setIdToken(String idToken) {
		this.idToken = idToken;
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
	
}
