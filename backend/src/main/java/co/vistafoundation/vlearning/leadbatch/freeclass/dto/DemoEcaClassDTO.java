/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Naveen Kumar
 *
 */
public class DemoEcaClassDTO {
	
	private Long idDemoClassExtraCurricular;
	
	private Long idTeacher;
	
	private String teacherName;
	
	private Long idSubject;
	
	private String subjectName;
	
	private LocalDate classScheduleDate;
	
	private Boolean classConductedFlag;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;
	
	private Integer maxStudents;
	
	private Integer totalStudentEnrolled;
	
	private Boolean activeFlag;
	
	private String description;

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
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	 * @return the maxStudents
	 */
	public Integer getMaxStudents() {
		return maxStudents;
	}

	/**
	 * @param maxStudents the maxStudents to set
	 */
	public void setMaxStudents(Integer maxStudents) {
		this.maxStudents = maxStudents;
	}

	/**
	 * @return the totalStudentEnrolled
	 */
	public Integer getTotalStudentEnrolled() {
		return totalStudentEnrolled;
	}

	/**
	 * @param totalStudentEnrolled the totalStudentEnrolled to set
	 */
	public void setTotalStudentEnrolled(Integer totalStudentEnrolled) {
		this.totalStudentEnrolled = totalStudentEnrolled;
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
	
	
}
