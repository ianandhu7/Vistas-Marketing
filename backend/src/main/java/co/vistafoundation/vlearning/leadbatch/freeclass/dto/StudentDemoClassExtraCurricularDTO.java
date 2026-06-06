/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLogExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class StudentDemoClassExtraCurricularDTO {

	private Subject subject;
	private Teacher teacher;
	private DemoClassExtraCurricular demoClassExtraCurricular;
	private LevelExtraCurricular levelExtraCurricular;
	private LocalDate classScheduleDate;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LeadBatchLogExtraCurricular leadBatchLogExtraCurricular;
	private String meetingPassword;

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
	 * @return the demoClassExtraCurricular
	 */
	public DemoClassExtraCurricular getDemoClassExtraCurricular() {
		return demoClassExtraCurricular;
	}

	/**
	 * @param demoClassExtraCurricular the demoClassExtraCurricular to set
	 */
	public void setDemoClassExtraCurricular(DemoClassExtraCurricular demoClassExtraCurricular) {
		this.demoClassExtraCurricular = demoClassExtraCurricular;
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
	 * @return the leadBatchLogExtraCurricular
	 */
	public LeadBatchLogExtraCurricular getLeadBatchLogExtraCurricular() {
		return leadBatchLogExtraCurricular;
	}

	/**
	 * @param leadBatchLogExtraCurricular the leadBatchLogExtraCurricular to set
	 */
	public void setLeadBatchLogExtraCurricular(LeadBatchLogExtraCurricular leadBatchLogExtraCurricular) {
		this.leadBatchLogExtraCurricular = leadBatchLogExtraCurricular;
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
	 * @return the meetingPassword
	 */
	public String getMeetingPassword() {
		return meetingPassword;
	}

	/**
	 * @param meetingPassword the meetingPassword to set
	 */
	public void setMeetingPassword(String meetingPassword) {
		this.meetingPassword = meetingPassword;
	}

	/**
	 * @param subject
	 * @param teacher
	 * @param demoClassExtraCurricular
	 * @param levelExtraCurricular
	 * @param classScheduleDate
	 * @param fromTime
	 * @param toTime
	 * @param leadBatchLogExtraCurricular
	 * @param meetingPassword
	 */
	public StudentDemoClassExtraCurricularDTO(Subject subject, Teacher teacher,
			DemoClassExtraCurricular demoClassExtraCurricular, LevelExtraCurricular levelExtraCurricular,
			LocalDate classScheduleDate, LocalTime fromTime, LocalTime toTime,
			LeadBatchLogExtraCurricular leadBatchLogExtraCurricular, String meetingPassword) {
		super();
		this.subject = subject;
		this.teacher = teacher;
		this.demoClassExtraCurricular = demoClassExtraCurricular;
		this.levelExtraCurricular = levelExtraCurricular;
		this.classScheduleDate = classScheduleDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.leadBatchLogExtraCurricular = leadBatchLogExtraCurricular;
		this.meetingPassword = meetingPassword;
	}

	/**
	 * 
	 */
	public StudentDemoClassExtraCurricularDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
