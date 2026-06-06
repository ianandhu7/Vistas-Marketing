/**
 * Test
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * Test
 */
public class NewSignupClassInfoRequestDTO {
	
	private Long userSurId;
	
	private Long idSyllabus;

	private Long idMedium;

	private Long idClassStandard;

	private Long idSecondaryLanguage;

	private Long idState;

	private Long idSubject;
	
	private String deviceId;

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
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idMedium
	 */
	public Long getIdMedium() {
		return idMedium;
	}

	/**
	 * @param idMedium the idMedium to set
	 */
	public void setIdMedium(Long idMedium) {
		this.idMedium = idMedium;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the idSecondaryLanguage
	 */
	public Long getIdSecondaryLanguage() {
		return idSecondaryLanguage;
	}

	/**
	 * @param idSecondaryLanguage the idSecondaryLanguage to set
	 */
	public void setIdSecondaryLanguage(Long idSecondaryLanguage) {
		this.idSecondaryLanguage = idSecondaryLanguage;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
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

	/**
	 * 
	 */
	public NewSignupClassInfoRequestDTO() {
		super();
		
	}

	/**
	 * @param userSurId
	 * @param idSyllabus
	 * @param idMedium
	 * @param idClassStandard
	 * @param idSecondaryLanguage
	 * @param idState
	 * @param idSubject
	 */
	public NewSignupClassInfoRequestDTO(Long userSurId, Long idSyllabus, Long idMedium, Long idClassStandard,
			Long idSecondaryLanguage, Long idState, Long idSubject) {
		super();
		this.userSurId = userSurId;
		this.idSyllabus = idSyllabus;
		this.idMedium = idMedium;
		this.idClassStandard = idClassStandard;
		this.idSecondaryLanguage = idSecondaryLanguage;
		this.idState = idState;
		this.idSubject = idSubject;
	}

	@Override
	public String toString() {
		return "NewSignupClassInfoRequestDTO [userSurId=" + userSurId + ", idSyllabus=" + idSyllabus + ", idMedium="
				+ idMedium + ", idClassStandard=" + idClassStandard + ", idSecondaryLanguage=" + idSecondaryLanguage
				+ ", idState=" + idState + ", idSubject=" + idSubject + "]";
	}
	
	

}
