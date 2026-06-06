/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author vk
 *
 */
public class TokenRefreshRequest {

	@NotBlank
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
