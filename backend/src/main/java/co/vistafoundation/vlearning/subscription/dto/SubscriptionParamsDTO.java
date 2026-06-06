/**
 * Test
 */
package co.vistafoundation.vlearning.subscription.dto;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;

/**
 * Test
 */
public class SubscriptionParamsDTO {
	
	String phoneNumber;
	
	User user;
	
	Long idStudent;
	
	UserPrincipal userPrincipal;

	/**
	 * 
	 */
	public SubscriptionParamsDTO() {
		super();
	}

	/**
	 * @param phoneNumber
	 * @param user
	 * @param idStudent
	 * @param userPrincipal
	 */
	public SubscriptionParamsDTO(String phoneNumber, User user, Long idStudent, UserPrincipal userPrincipal) {
		super();
		this.phoneNumber = phoneNumber;
		this.user = user;
		this.idStudent = idStudent;
		this.userPrincipal = userPrincipal;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @return the userPrincipal
	 */
	public UserPrincipal getUserPrincipal() {
		return userPrincipal;
	}

	/**
	 * @param userPrincipal the userPrincipal to set
	 */
	public void setUserPrincipal(UserPrincipal userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	@Override
	public String toString() {
		return "SubscriptionParamsDTO [phoneNumber=" + phoneNumber + ", user=" + user + ", idStudent=" + idStudent
				+ ", userPrincipal=" + userPrincipal + "]";
	}
	
	
}
