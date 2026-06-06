/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class TeacherDemoClassExtraCurricularDTO {

	private Long idDemoClassExtraCurricular;

	private Boolean activeFlag;

	private Boolean classConductedFlag;

	private LocalDate classScheduleDate;

	private String description;

	private LocalTime fromTime;

	private LocalTime toTime;

	private Teacher teacher;

	private int maxStudents;

	private int totalStudentsEnrolled;

	private LevelExtraCurricular levelExtraCurricular;

	private Subject subject;

	private Instant actualStartTimeStamp;

	private Instant actualEndTimeStamp;

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
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the classConductedFlag
	 */
	public Boolean getClassConductedFlag() {
		return classConductedFlag;
	}

	/**
	 * @param classConductedFlag the classConductedFlag to set
	 */
	public void setClassConductedFlag(Boolean classConductedFlag) {
		this.classConductedFlag = classConductedFlag;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the maxStudents
	 */
	public int getMaxStudents() {
		return maxStudents;
	}

	/**
	 * @param maxStudents the maxStudents to set
	 */
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	/**
	 * @return the totalStudentsEnrolled
	 */
	public int getTotalStudentsEnrolled() {
		return totalStudentsEnrolled;
	}

	/**
	 * @param totalStudentsEnrolled the totalStudentsEnrolled to set
	 */
	public void setTotalStudentsEnrolled(int totalStudentsEnrolled) {
		this.totalStudentsEnrolled = totalStudentsEnrolled;
	}

	/**
	 * @return the levelExtraCurricular
	 */
	public LevelExtraCurricular getLevelExtraCurricular() {
		return levelExtraCurricular;
	}

	/**
	 * @param levelExtraCurricular the levelExtraCurricular to set
	 */
	public void setLevelExtraCurricular(LevelExtraCurricular levelExtraCurricular) {
		this.levelExtraCurricular = levelExtraCurricular;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the actualStartTimeStamp
	 */
	public Instant getActualStartTimeStamp() {
		return actualStartTimeStamp;
	}

	/**
	 * @param actualStartTimeStamp the actualStartTimeStamp to set
	 */
	public void setActualStartTimeStamp(Instant actualStartTimeStamp) {
		this.actualStartTimeStamp = actualStartTimeStamp;
	}

	/**
	 * @return the actualEndTimeStamp
	 */
	public Instant getActualEndTimeStamp() {
		return actualEndTimeStamp;
	}

	/**
	 * @param actualEndTimeStamp the actualEndTimeStamp to set
	 */
	public void setActualEndTimeStamp(Instant actualEndTimeStamp) {
		this.actualEndTimeStamp = actualEndTimeStamp;
	}

	/**
	 * @param idDemoClassExtraCurricular
	 * @param activeFlag
	 * @param classConductedFlag
	 * @param classScheduleDate
	 * @param description
	 * @param fromTime
	 * @param toTime
	 * @param teacher
	 * @param maxStudents
	 * @param totalStudentsEnrolled
	 * @param levelExtraCurricular
	 * @param subject
	 * @param actualStartTimeStamp
	 * @param actualEndTimeStamp
	 */
	public TeacherDemoClassExtraCurricularDTO(Long idDemoClassExtraCurricular, Boolean activeFlag,
			Boolean classConductedFlag, LocalDate classScheduleDate, String description, LocalTime fromTime,
			LocalTime toTime, Teacher teacher, int maxStudents, int totalStudentsEnrolled,
			LevelExtraCurricular levelExtraCurricular, Subject subject, Instant actualStartTimeStamp,
			Instant actualEndTimeStamp) {
		super();
		this.idDemoClassExtraCurricular = idDemoClassExtraCurricular;
		this.activeFlag = activeFlag;
		this.classConductedFlag = classConductedFlag;
		this.classScheduleDate = classScheduleDate;
		this.description = description;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.teacher = teacher;
		this.maxStudents = maxStudents;
		this.totalStudentsEnrolled = totalStudentsEnrolled;
		this.levelExtraCurricular = levelExtraCurricular;
		this.subject = subject;
		this.actualStartTimeStamp = actualStartTimeStamp;
		this.actualEndTimeStamp = actualEndTimeStamp;
	}

	/**
	 * 
	 */
	public TeacherDemoClassExtraCurricularDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
