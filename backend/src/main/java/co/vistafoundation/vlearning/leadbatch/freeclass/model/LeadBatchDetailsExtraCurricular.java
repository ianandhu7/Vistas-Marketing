/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ahmed
 *
 */

@Entity
@Table(name = "LEAD_BATCH_DETAILS_EXTRA_CURRICULAR")
public class LeadBatchDetailsExtraCurricular {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_LEAD_BATCH_DETAILS_EXTRA_CURRICULAR")
	private Long idLeadBatchDetailsExtraCurricular;
	
	@Column(name="ID_SUBJECT_EXTRA_CURRICULAR")
	private Long idSubjectExtraCurricular;
	
	@Column(name="ID_LEVEL_EXTRA_CURRICULAR")
	private Long idLevelExtraCurricular;
	
	@Column(name="ID_LANGUAGE")
	private Long idLanguage;
	
	@Column(name="ID_AVAILABLE_SLOT")
	private Long idAvailableSlot;
	
	@Column(name="ID_VLUSER")
	private Long idVlUser;

	/**
	 * @param idLeadBatchDetailsExtraCurricular
	 * @param idSubjectExtraCurricular
	 * @param idLevelExtraCurricular
	 * @param idLanguage
	 * @param idAvailableSlot
	 * @param idVlUser
	 */
	public LeadBatchDetailsExtraCurricular(Long idLeadBatchDetailsExtraCurricular, Long idSubjectExtraCurricular,
			Long idLevelExtraCurricular, Long idLanguage, Long idAvailableSlot, Long idVlUser) {
		super();
		this.idLeadBatchDetailsExtraCurricular = idLeadBatchDetailsExtraCurricular;
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idLanguage = idLanguage;
		this.idAvailableSlot = idAvailableSlot;
		this.idVlUser = idVlUser;
	}

	/**
	 * 
	 */
	public LeadBatchDetailsExtraCurricular() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idLeadBatchDetailsExtraCurricular
	 */
	public Long getIdLeadBatchDetailsExtraCurricular() {
		return idLeadBatchDetailsExtraCurricular;
	}

	/**
	 * @param idLeadBatchDetailsExtraCurricular the
	 *                                          idLeadBatchDetailsExtraCurricular to
	 *                                          set
	 */
	public void setIdLeadBatchDetailsExtraCurricular(Long idLeadBatchDetailsExtraCurricular) {
		this.idLeadBatchDetailsExtraCurricular = idLeadBatchDetailsExtraCurricular;
	}

	/**
	 * @return the idSubjectExtraCurricular
	 */
	public Long getIdSubjectExtraCurricular() {
		return idSubjectExtraCurricular;
	}

	/**
	 * @param idSubjectExtraCurricular the idSubjectExtraCurricular to set
	 */
	public void setIdSubjectExtraCurricular(Long idSubjectExtraCurricular) {
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
	}

	/**
	 * @return the idLevelExtraCurricular
	 */
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}

	/**
	 * @param idLevelExtraCurricular the idLevelExtraCurricular to set
	 */
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
	}

	/**
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @return the idAvailableSlot
	 */
	public Long getIdAvailableSlot() {
		return idAvailableSlot;
	}

	/**
	 * @param idAvailableSlot the idAvailableSlot to set
	 */
	public void setIdAvailableSlot(Long idAvailableSlot) {
		this.idAvailableSlot = idAvailableSlot;
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

}
