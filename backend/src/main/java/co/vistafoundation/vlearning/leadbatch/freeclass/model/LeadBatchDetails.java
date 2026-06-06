/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@Entity
@Table(name = "LEAD_BATCH_DETAILS")
public class LeadBatchDetails extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LEAD_BATCH_DETAILS")
	private Long idLeadBatchDetails;

	@Column(name = "ID_VL_USER", unique = true)
	private Long idVlUser;

	@Column(name = "ID_SYLLABUS")
	private Long idSyllabus;

	@Column(name = "ID_CLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "ID_SUBJECT")
	private Long idSubject;

	@Column(name = "ID_SUBJECT_CHAPTER")
	private Long idSujectChapter;

	@Column(name = "ID_AVAILABLE_SCHEDULE")
	private Long idAvailableSchedule;

	@Column(name = "ID_LANGUAGE")
	private Long idLanguage;
	
	@Column(name = "idSTATE")
	private Long idState;

	/**
	 * @return the idLeadBatchDetails
	 */
	public Long getIdLeadBatchDetails() {
		return idLeadBatchDetails;
	}

	/**
	 * @param idLeadBatchDetails the idLeadBatchDetails to set
	 */
	public void setIdLeadBatchDetails(Long idLeadBatchDetails) {
		this.idLeadBatchDetails = idLeadBatchDetails;
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
	 * @return the idSujectChapter
	 */
	public Long getIdSujectChapter() {
		return idSujectChapter;
	}

	/**
	 * @param idSujectChapter the idSujectChapter to set
	 */
	public void setIdSujectChapter(Long idSujectChapter) {
		this.idSujectChapter = idSujectChapter;
	}

	/**
	 * @return the idAvailableSchedule
	 */
	public Long getIdAvailableSchedule() {
		return idAvailableSchedule;
	}

	/**
	 * @param idAvailableSchedule the idAvailableSchedule to set
	 */
	public void setIdAvailableSchedule(Long idAvailableSchedule) {
		this.idAvailableSchedule = idAvailableSchedule;
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

	public LeadBatchDetails(Long idSyllabus, Long idClassStandard, Long idSubject, Long idSujectChapter,
			Long idAvailableSchedule, Long idLanguage) {
		super();
		this.idSyllabus = idSyllabus;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idSujectChapter = idSujectChapter;
		this.idAvailableSchedule = idAvailableSchedule;
		this.idLanguage = idLanguage;
	}

	/**
	 * @param idLeadBatchDetails
	 * @param idVlUser
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param idSubject
	 * @param idSujectChapter
	 * @param idAvailableSchedule
	 * @param idLanguage
	 */
	public LeadBatchDetails(Long idLeadBatchDetails, Long idVlUser, Long idSyllabus, Long idClassStandard,
			Long idSubject, Long idSujectChapter, Long idAvailableSchedule, Long idLanguage) {
		super();
		this.idLeadBatchDetails = idLeadBatchDetails;
		this.idVlUser = idVlUser;
		this.idSyllabus = idSyllabus;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idSujectChapter = idSujectChapter;
		this.idAvailableSchedule = idAvailableSchedule;
		this.idLanguage = idLanguage;
	}

	/**
	 * 
	 */
	public LeadBatchDetails() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
}
