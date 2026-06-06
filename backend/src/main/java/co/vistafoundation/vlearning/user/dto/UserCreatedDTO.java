/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

import java.time.Instant;

/**
 * @author vk
 *
 */
public class UserCreatedDTO {
	
	private Instant createdAt;
	
	private Long userSurId;

	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}
	
}
