/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import javax.validation.constraints.NotNull;

/**
 * @author vk
 *
 */
public class StudentOrderTicketRequestDTO {
	
	@NotNull
	private Long idStudentOrder;
	
	@NotNull
	private Long userSurId;
	
	private String userEmail;
	
	@NotNull
	private String userMobile;
	
	@NotNull
	private String studentRemarks;

	/**
	 * @param idStudentOrder
	 * @param userSurId
	 * @param userEmail
	 * @param userMobile
	 * @param remarks
	 */
	public StudentOrderTicketRequestDTO(Long idStudentOrder, Long userSurId, String userEmail, String userMobile,
			String studentRemarks) {
		super();
		this.idStudentOrder = idStudentOrder;
		this.userSurId = userSurId;
		this.userEmail = userEmail;
		this.userMobile = userMobile;
		this.studentRemarks = studentRemarks;
	}

	/**
	 * @return the idStudentOrder
	 */
	public Long getIdStudentOrder() {
		return idStudentOrder;
	}

	/**
	 * @param idStudentOrder the idStudentOrder to set
	 */
	public void setIdStudentOrder(Long idStudentOrder) {
		this.idStudentOrder = idStudentOrder;
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
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the studentRemarks
	 */
	public String getStudentRemarks() {
		return studentRemarks;
	}

	/**
	 * @param studentRemarks the studentRemarks to set
	 */
	public void setStudentRemarks(String studentRemarks) {
		this.studentRemarks = studentRemarks;
	}

}
