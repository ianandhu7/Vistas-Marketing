/**
 * Test
 */
package co.vistafoundation.vlearning.user.dto;

public class DeactivatedIdsDTO {
	
	private String phoneNumber;
	private Long userSurId;
	private Boolean isActive;
	private String remarks;
    private String message;
    private Boolean isValid;
	
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
	 * @param boolean1 
	 * @return the isActive
	 */
	public Boolean getIsActive(Boolean boolean1) {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the isValid
	 */
	public Boolean getIsValid() {
		return isValid;
	}
	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	/**
	 * @param phoneNumber
	 * @param userSurId
	 * @param isActive
	 * @param remarks
	 * @param message
	 * @param isValid
	 */
	public DeactivatedIdsDTO(String phoneNumber, Long userSurId, Boolean isActive, String remarks, String message,
			Boolean isValid) {
		super();
		this.phoneNumber = phoneNumber;
		this.userSurId = userSurId;
		this.isActive = isActive;
		this.remarks = remarks;
		this.message = message;
		this.isValid = isValid;
	}
	/**
	 * 
	 */
	public DeactivatedIdsDTO() {
		super();
	}
	@Override
	public String toString() {
		return "DeactivatedIdsDTO [phoneNumber=" + phoneNumber + ", userSurId=" + userSurId + ", isActive=" + isActive
				+ ", remarks=" + remarks + ", message=" + message + ", isValid=" + isValid + "]";
	}
	
}
