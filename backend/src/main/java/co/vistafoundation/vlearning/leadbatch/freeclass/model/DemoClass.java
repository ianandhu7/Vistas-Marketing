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
import javax.persistence.UniqueConstraint;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "DEMO_CLASS", uniqueConstraints = {@UniqueConstraint(columnNames = {"idTEACHER", "CLASS_SCHEDULE_DATE",
		"FROM_TIME", "TO_TIME"})})
public class DemoClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDEMO_CLASS")
	private Long idDemoClass;

	@Column(name = "idTEACHER")
	private Long idTeacher;

	@Column(name = "idSUBJECT")
	private Long idSubject;

	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "ID_SYLLABUS")
	private Long idSyllabus;

	@Column(name = "CLASS_SCHEDULE_DATE")
	private LocalDate classScheduleDate;

	@Column(name = "CLASS_CONDUCTED_FLAG")
	private Boolean classConductedFlag;

	@Column(name = "FROM_TIME")
	private LocalTime fromTime;

	@Column(name = "TO_TIME")
	private LocalTime toTime;

	@Column(name = "MAX_STUDENTS")
	private Integer maxStudents;

	@Column(name = "TOTAL_STUDENT_ENROLLED")
	private Integer totalStudentEnrolled;

	@Column(name = "idSUBJECT_CHAPTER")
	private Long idSubjectChapter;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "idSTATE")
	private Long idState;

	/**
	 * 
	 */
	public DemoClass() {
		super();
	}

	/**
	 * @param idTeacher
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param classScheduleDate
	 * @param classConductedFlag
	 * @param fromTime
	 * @param toTime
	 * @param maxStudents
	 * @param totalStudentEnrolled
	 * @param idSubjectChapter
	 * @param activeFlag
	 * @param description
	 */
	public DemoClass(Long idTeacher, Long idSubject, Long idClassStandard, Long idSyllabus,
			LocalDate classScheduleDate, Boolean classConductedFlag, LocalTime fromTime, LocalTime toTime,
			Integer maxStudents, Integer totalStudentEnrolled, Long idSubjectChapter, Boolean activeFlag,
			String description) {
		super();
		this.idTeacher = idTeacher;
		this.idSubject = idSubject;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.classScheduleDate = classScheduleDate;
		this.classConductedFlag = classConductedFlag;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.maxStudents = maxStudents;
		this.totalStudentEnrolled = totalStudentEnrolled;
		this.idSubjectChapter = idSubjectChapter;
		this.activeFlag = activeFlag;
		this.description = description;
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
