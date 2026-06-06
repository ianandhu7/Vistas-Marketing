/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLog;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author Ahmed
 *
 */
public class LeadBatchLogListDTO {

	private ClassStandard classStandard;
	private Subject subject;
	private SubjectChapter subjectChapter;
	private Syllabus syllabus;
	private Teacher teacher;
	private DemoClass demoClass;
	private LocalDate classScheduleDate;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LeadBatchLog leadBatchLog;
	private String meetingPassword;

	/**
	 * @param classStandard
	 * @param subject
	 * @param subjectChapter
	 * @param syllabus
	 * @param teacher
	 * @param demoClass
	 * @param classScheduleDate
	 * @param fromTime
	 * @param toTime
	 * @param leadBatchLog
	 * @param meetingPassword
	 */
	public LeadBatchLogListDTO(ClassStandard classStandard, Subject subject, SubjectChapter subjectChapter,
			Syllabus syllabus, Teacher teacher, DemoClass demoClass, LocalDate classScheduleDate, LocalTime fromTime,
			LocalTime toTime, LeadBatchLog leadBatchLog, String meetingPassword) {
		super();
		this.classStandard = classStandard;
		this.subject = subject;
		this.subjectChapter = subjectChapter;
		this.syllabus = syllabus;
		this.teacher = teacher;
		this.demoClass = demoClass;
		this.classScheduleDate = classScheduleDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.leadBatchLog = leadBatchLog;
		this.meetingPassword = meetingPassword;
	}

	/**
	 * 
	 */
	public LeadBatchLogListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the classStandard
	 */
	public ClassStandard getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(ClassStandard classStandard) {
		this.classStandard = classStandard;
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
	 * @return the subjectChapter
	 */
	public SubjectChapter getSubjectChapter() {
		return subjectChapter;
	}

	/**
	 * @param subjectChapter the subjectChapter to set
	 */
	public void setSubjectChapter(SubjectChapter subjectChapter) {
		this.subjectChapter = subjectChapter;
	}

	/**
	 * @return the syllabus
	 */
	public Syllabus getSyllabus() {
		return syllabus;
	}

	/**
	 * @param syllabus the syllabus to set
	 */
	public void setSyllabus(Syllabus syllabus) {
		this.syllabus = syllabus;
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
	 * @return the demoClass
	 */
	public DemoClass getDemoClass() {
		return demoClass;
	}

	/**
	 * @param demoClass the demoClass to set
	 */
	public void setDemoClass(DemoClass demoClass) {
		this.demoClass = demoClass;
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
	 * @return the leadBatchLog
	 */
	public LeadBatchLog getLeadBatchLog() {
		return leadBatchLog;
	}

	/**
	 * @param leadBatchLog the leadBatchLog to set
	 */
	public void setLeadBatchLog(LeadBatchLog leadBatchLog) {
		this.leadBatchLog = leadBatchLog;
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

}
