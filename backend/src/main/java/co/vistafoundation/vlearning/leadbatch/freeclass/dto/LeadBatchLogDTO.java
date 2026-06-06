/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author SHAIKH AHMED REZA
 *
 */
public class LeadBatchLogDTO {

	private Long idLeadBatchLog;
	private Long idDemoClass;
	private Long idSyllabus;
	private Long idClassStandard;
	private Long idSubject;
	private Long idTeacher;
	private Long idVlUser;
	private Long idSubjectChapter;
	private LocalDate classScheduleDate;
	private LocalTime fromTime;
	private LocalTime toTime;
	private Long idLanguage;
	private Long telecallerIdVlUser;

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
	 * @return the idSubjectChapter
	 */
	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	/**
	 * @param idSubjectChapter the idSubjectChapter to set
	 */
	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
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
	public LeadBatchLogDTO(Long idLeadBatchLog, Long idDemoClass, Long idSyllabus, Long idClassStandard, Long idSubject,
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
	 * 
	 */
	public LeadBatchLogDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
