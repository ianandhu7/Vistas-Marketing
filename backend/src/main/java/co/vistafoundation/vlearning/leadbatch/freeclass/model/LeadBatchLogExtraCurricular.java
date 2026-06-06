/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
@Table(name = "LEAD_BATCH_LOG_EXTRA_CURRICULAR")
public class LeadBatchLogExtraCurricular {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_LEAD_BATCH_LOG_EXTRA_CURRICULAR")
	private Long idLeadBatchLogExtraCurricular;

	@Column(name = "ID_SUBJECT_EXTRA_CURRICULAR")
	private Long idSubjectExtraCurricular;

	@Column(name = "ID_LEVEL_EXTRA_CURRICULAR")
	private Long idLevelExtraCurricular;

	@Column(name = "ID_LANGUAGE")
	private Long idLanguage;

	@Column(name = "ID_AVAILABLE_SLOT")
	private Long idAvailableSlot;

	@Column(name = "ID_VLUSER")
	private Long idVlUser;

	@Column(name = "ID_TEACHER")
	private Long idTeacher;

	@Column(name = "CLASS_SCHEDULE_DATE")
	private LocalDate classScheduleDate;

	@Column(name = "ID_DEMO_CLASS_EXTRA_CURRICULAR")
	private Long idDemoClassExtraCurricular;

	@Column(name = "FROM_TIME")
	private LocalTime fromTime;

	@Column(name = "TO_TIME")
	private LocalTime toTime;

	@Column(name = "TELECALLER_IDVL_USER")
	private Long telecallerIdVlUser;

	/**
	 * @return the idLeadBatchLogExtraCurricular
	 */
	public Long getIdLeadBatchLogExtraCurricular() {
		return idLeadBatchLogExtraCurricular;
	}

	/**
	 * @param idLeadBatchLogExtraCurricular the idLeadBatchLogExtraCurricular to set
	 */
	public void setIdLeadBatchLogExtraCurricular(Long idLeadBatchLogExtraCurricular) {
		this.idLeadBatchLogExtraCurricular = idLeadBatchLogExtraCurricular;
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

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	/**
	 * @return the classScheduleDate
	 */
	public LocalDate getClassScheduleDate() {
		return classScheduleDate;
	}

	/**
	 * @param classScheduleDate the classScheduleDate to set
	 */
	public void setClassScheduleDate(LocalDate classScheduleDate) {
		this.classScheduleDate = classScheduleDate;
	}

	/**
	 * @return the idDemoClassExtraCurricular
	 */
	public Long getIdDemoClassExtraCurricular() {
		return idDemoClassExtraCurricular;
	}

	/**
	 * @param idDemoClassExtraCurricular the idDemoClassExtraCurricular to set
	 */
	public void setIdDemoClassExtraCurricular(Long idDemoClassExtraCurricular) {
		this.idDemoClassExtraCurricular = idDemoClassExtraCurricular;
	}

	/**
	 * @return the fromTime
	 */
	public LocalTime getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public LocalTime getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the telecallerIdVlUser
	 */
	public Long getTelecallerIdVlUser() {
		return telecallerIdVlUser;
	}

	/**
	 * @param telecallerIdVlUser the telecallerIdVlUser to set
	 */
	public void setTelecallerIdVlUser(Long telecallerIdVlUser) {
		this.telecallerIdVlUser = telecallerIdVlUser;
	}

	/**
	 * @param idLeadBatchLogExtraCurricular
	 * @param idSubjectExtraCurricular
	 * @param idLevelExtraCurricular
	 * @param idLanguage
	 * @param idAvailableSlot
	 * @param idVlUser
	 * @param idTeacher
	 * @param classScheduleDate
	 * @param idDemoClassExtraCurricular
	 * @param fromTime
	 * @param toTime
	 * @param telecallerIdVlUser
	 */
	public LeadBatchLogExtraCurricular(Long idLeadBatchLogExtraCurricular, Long idSubjectExtraCurricular,
			Long idLevelExtraCurricular, Long idLanguage, Long idAvailableSlot, Long idVlUser, Long idTeacher,
			LocalDate classScheduleDate, Long idDemoClassExtraCurricular, LocalTime fromTime, LocalTime toTime,
			Long telecallerIdVlUser) {
		super();
		this.idLeadBatchLogExtraCurricular = idLeadBatchLogExtraCurricular;
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idLanguage = idLanguage;
		this.idAvailableSlot = idAvailableSlot;
		this.idVlUser = idVlUser;
		this.idTeacher = idTeacher;
		this.classScheduleDate = classScheduleDate;
		this.idDemoClassExtraCurricular = idDemoClassExtraCurricular;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.telecallerIdVlUser = telecallerIdVlUser;
	}

	/**
	 * 
	 */
	public LeadBatchLogExtraCurricular() {
		super();
		// TODO Auto-generated constructor stub
	}

}
