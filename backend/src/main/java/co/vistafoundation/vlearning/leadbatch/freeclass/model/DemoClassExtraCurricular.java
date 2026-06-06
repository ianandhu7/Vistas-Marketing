/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.time.Instant;
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
@Table(name = "DEMO_CLASS_EXTRA_CURRICULAR")
public class DemoClassExtraCurricular {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_DEMO_CLASS_EXTRA_CURRICULAR")
	private Long idDemoClassExtraCurricular;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;

	@Column(name = "CLASS_CONDUCTED_FLAG")
	private Boolean classConductedFlag;

	@Column(name = "CLASS_SCHEDULE_DATE")
	private LocalDate classScheduleDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FROM_TIME")
	private LocalTime fromTime;

	@Column(name = "TO_TIME")
	private LocalTime toTime;

	@Column(name = "ID_TEACHER")
	private Long idTeacher;

	@Column(name = "MAX_STUDENTS")
	private int maxStudents;

	@Column(name = "TOTAL_STUDENTS_ENROLLED")
	private int totalStudentsEnrolled;

	@Column(name = "ID_LEVEL_EXTRA_CURRICULAR")
	private Long idLevelExtraCurricular;

	@Column(name = "ID_SUBJECT_EXTRA_CURRICULAR")
	private Long idSubjectExtraCurricular;

	@Column(name = "ACTUAL_START_TIMESTAMP")
	private Instant actualStartTimeStamp;

	@Column(name = "ACTUAL_END_TIMESTAMP")
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
	 * @param idTeacher
	 * @param maxStudents
	 * @param totalStudentsEnrolled
	 * @param idLevelExtraCurricular
	 * @param idSubjectExtraCurricular
	 * @param actualStartTimeStamp
	 * @param actualEndTimeStamp
	 */
	public DemoClassExtraCurricular(Long idDemoClassExtraCurricular, Boolean activeFlag, Boolean classConductedFlag,
			LocalDate classScheduleDate, String description, LocalTime fromTime, LocalTime toTime, Long idTeacher,
			int maxStudents, int totalStudentsEnrolled, Long idLevelExtraCurricular, Long idSubjectExtraCurricular,
			Instant actualStartTimeStamp, Instant actualEndTimeStamp) {
		super();
		this.idDemoClassExtraCurricular = idDemoClassExtraCurricular;
		this.activeFlag = activeFlag;
		this.classConductedFlag = classConductedFlag;
		this.classScheduleDate = classScheduleDate;
		this.description = description;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.idTeacher = idTeacher;
		this.maxStudents = maxStudents;
		this.totalStudentsEnrolled = totalStudentsEnrolled;
		this.idLevelExtraCurricular = idLevelExtraCurricular;
		this.idSubjectExtraCurricular = idSubjectExtraCurricular;
		this.actualStartTimeStamp = actualStartTimeStamp;
		this.actualEndTimeStamp = actualEndTimeStamp;
	}

	/**
	 * 
	 */
	public DemoClassExtraCurricular() {
		super();
		// TODO Auto-generated constructor stub
	}

}
