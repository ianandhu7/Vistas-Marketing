/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * @author Ahmed
 *
 */
public class AfterSignUpMetadataDTO {

	private Long idVlUser;
	private Long idSyllabus;
	private Long idMedium;
	private Long idClassStandard;
	private Long idSecondaryLanguage;
	private Long idState;
	private Long idSubject;

	/**
	 * @param idVlUser
	 * @param idSyllabus
	 * @param idMedium
	 * @param idClassStandard
	 * @param idSecondaryLanguage
	 * @param idState
	 * @param idSubject
	 */
	public AfterSignUpMetadataDTO(Long idVlUser, Long idSyllabus, Long idMedium, Long idClassStandard,
			Long idSecondaryLanguage, Long idState, Long idSubject) {
		super();
		this.idVlUser = idVlUser;
		this.idSyllabus = idSyllabus;
		this.idMedium = idMedium;
		this.idClassStandard = idClassStandard;
		this.idSecondaryLanguage = idSecondaryLanguage;
		this.idState = idState;
		this.idSubject = idSubject;
	}

	/**
	 * 
	 */
	public AfterSignUpMetadataDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
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

}
