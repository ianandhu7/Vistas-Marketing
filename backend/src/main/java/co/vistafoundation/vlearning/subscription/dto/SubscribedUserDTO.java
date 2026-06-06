/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

/**
 * @author vk
 *
 */
public class SubscribedUserDTO {
	
	private Long idStudentSubscription;
	
	private Long idStudent;
	
	private Long userSurId;
	
	private Long idProduct;

	/**
	 * @return the idStudentSubscription
	 */
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	
}
