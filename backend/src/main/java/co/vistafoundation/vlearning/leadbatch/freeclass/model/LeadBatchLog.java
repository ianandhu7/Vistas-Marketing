/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "LEAD_BATCH_LOG")
public class LeadBatchLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLEAD_BATCH_LOG")
	private Long idLeadBatchLog;

	@Column(name = "idDEMO_CLASS")
	private Long idDemoClass;

	@Column(name = "idSYLLABUS")
	private Long idSyllabus;

	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "idSUBJECT")
	private Long idSubject;

	@Column(name = "idTEACHER")
	private Long idTeacher;

	@Column(name = "idVL_USER")
	private Long idVlUser;

	@Column(name = "idSUBJECT_CHAPTER")
	private Long idSubjectChapter;

	@Column(name = "CLASS_SCHEDULE_DATE")
	private LocalDate classScheduleDate;

	@Column(name = "FROM_TIME")
	private LocalTime fromTime;

	@Column(name = "TO_TIME")
	private LocalTime toTime;

	@Column(name = "ID_LANGUAGE")
	private Long idLanguage;

	@Column(name = "TELECALLER_IDVL_USER")
	private Long telecallerIdVlUser;
	
	@Column(name = "idSTATE")
	private Long idState;

	public LeadBatchLog() {
		super();
	}

	/**
	 * @param idLead
	 * @param idDemoClass
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param idSubject
	 * @param idTeacher
	 * @param notificationSentDate
	 * @param notificationSentFlag
	 */

	/**
	 * @return the idLeadBatchLog
	 */
	public Long getIdLeadBatchLog() {
		return idLeadBatchLog;
	}

	/**
	 * @param idLeadBatchLog the idLeadBatchLog to set
	 */
	public void setIdLeadBatchLog(Long idLeadBatchLog) {
		this.idLeadBatchLog = idLeadBatchLog;
	}

	/**
	 * @return the idLead
	 */

	/**
	 * @return the idDemoClass
	 */
	public Long getIdDemoClass() {
		return idDemoClass;
	}

	/**
	 * @param idDemoClass the idDemoClass to set
	 */
	public void setIdDemoClass(Long idDemoClass) {
		this.idDemoClass = idDemoClass;
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

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	public LocalDate getClassScheduleDate() {
		return classScheduleDate;
	}

	public void setClassScheduleDate(LocalDate classScheduleDate) {
		this.classScheduleDate = classScheduleDate;
	}

	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
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
	 * @param idLeadBatchLog
	 * @param idDemoClass
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param idSubject
	 * @param idTeacher
	 * @param idVlUser
	 * @param idSubjectChapter
	 * @param classScheduleDate
	 * @param fromTime
	 * @param toTime
	 * @param idLanguage
	 * @param telecallerIdVlUser
	 */
	public LeadBatchLog(Long idLeadBatchLog, Long idDemoClass, Long idSyllabus, Long idClassStandard, Long idSubject,
			Long idTeacher, Long idVlUser, Long idSubjectChapter, LocalDate classScheduleDate, LocalTime fromTime,
			LocalTime toTime, Long idLanguage, Long telecallerIdVlUser) {
		super();
		this.idLeadBatchLog = idLeadBatchLog;
		this.idDemoClass = idDemoClass;
		this.idSyllabus = idSyllabus;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idTeacher = idTeacher;
		this.idVlUser = idVlUser;
		this.idSubjectChapter = idSubjectChapter;
		this.classScheduleDate = classScheduleDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.idLanguage = idLanguage;
		this.telecallerIdVlUser = telecallerIdVlUser;
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
