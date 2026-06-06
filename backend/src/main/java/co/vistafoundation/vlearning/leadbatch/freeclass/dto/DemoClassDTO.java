/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author vk
 *
 */
public class DemoClassDTO {

	private Long idDemoClass;
	
	private Long idTeacher;
	
	private String teacherName;
	
	private Long idSubject;
	
	private String subjectName;
	
	private Long idClassStandard;
	
	private String classStandardName;
	
	private Long idSyllabus;
	
	private String syllabusName;
	
	private LocalDate classScheduleDate;
	
	private Boolean classConductedFlag;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;
	
	private Integer maxStudents;
	
	private Integer totalStudentEnrolled;
	
	private Long idSubjectChapter;
	
	private String chapterName;
	
	private Boolean activeFlag;
	
	private String description;
	
	private Long idState;
	
	private String stateName;
	
	/**
	 * @param idDemoClass
	 * @param idTeacher
	 * @param teacherName
	 * @param idSubject
	 * @param subjectName
	 * @param idClassStandard
	 * @param classStandardName
	 * @param idSyllabus
	 * @param syllabusName
	 * @param classScheduleDate
	 * @param classConductedFlag
	 */
	public DemoClassDTO(Long idDemoClass, Long idTeacher, String teacherName, Long idSubject, String subjectName,
			Long idClassStandard, String classStandardName, Long idSyllabus, String syllabusName,
			LocalDate classScheduleDate, Boolean classConductedFlag, LocalTime fromTime, LocalTime toTime,
			Integer maxStudents, Integer totalStudentEnrolled, Long idSubjectChapter, String chapterName, Boolean activeFlag, String description) {
		super();
		this.idDemoClass = idDemoClass;
		this.idTeacher = idTeacher;
		this.teacherName = teacherName;
		this.idSubject = idSubject;
		this.subjectName = subjectName;
		this.idClassStandard = idClassStandard;
		this.classStandardName = classStandardName;
		this.idSyllabus = idSyllabus;
		this.syllabusName = syllabusName;
		this.classScheduleDate = classScheduleDate;
		this.classConductedFlag = classConductedFlag;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.maxStudents = maxStudents;
		this.totalStudentEnrolled = totalStudentEnrolled;
		this.idSubjectChapter = idSubjectChapter;
		this.chapterName = chapterName;
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
	 * @return the classStandardName
	 */
	public String getClassStandardName() {
		return classStandardName;
	}

	/**
	 * @param classStandardName the classStandardName to set
	 */
	public void setClassStandardName(String classStandardName) {
		this.classStandardName = classStandardName;
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
	 * @return the syllabusName
	 */
	public String getSyllabusName() {
		return syllabusName;
	}

	/**
	 * @param syllabusName the syllabusName to set
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
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
	 * @return the chapterName
	 */
	public String getChapterName() {
		return chapterName;
	}

	/**
	 * @param chapterName the chapterName to set
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
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
	 * 
	 */
	public DemoClassDTO() {
		super();
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/** updated by 
	 * @author Naveen Kumar A
	 * 
	 * @param idDemoClass
	 * @param idTeacher
	 * @param teacherName
	 * @param idSubject
	 * @param subjectName
	 * @param idClassStandard
	 * @param classStandardName
	 * @param idSyllabus
	 * @param syllabusName
	 * @param classScheduleDate
	 * @param classConductedFlag
	 * @param fromTime
	 * @param toTime
	 * @param maxStudents
	 * @param totalStudentEnrolled
	 * @param idSubjectChapter
	 * @param chapterName
	 * @param activeFlag
	 * @param description
	 * @param idState
	 * @param stateName
	 */
	public DemoClassDTO(Long idDemoClass, Long idTeacher, String teacherName, Long idSubject, String subjectName,
			Long idClassStandard, String classStandardName, Long idSyllabus, String syllabusName,
			LocalDate classScheduleDate, Boolean classConductedFlag, LocalTime fromTime, LocalTime toTime,
			Integer maxStudents, Integer totalStudentEnrolled, Long idSubjectChapter, String chapterName,
			Boolean activeFlag, String description, Long idState, String stateName) {
		super();
		this.idDemoClass = idDemoClass;
		this.idTeacher = idTeacher;
		this.teacherName = teacherName;
		this.idSubject = idSubject;
		this.subjectName = subjectName;
		this.idClassStandard = idClassStandard;
		this.classStandardName = classStandardName;
		this.idSyllabus = idSyllabus;
		this.syllabusName = syllabusName;
		this.classScheduleDate = classScheduleDate;
		this.classConductedFlag = classConductedFlag;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.maxStudents = maxStudents;
		this.totalStudentEnrolled = totalStudentEnrolled;
		this.idSubjectChapter = idSubjectChapter;
		this.chapterName = chapterName;
		this.activeFlag = activeFlag;
		this.description = description;
		this.idState = idState;
		this.stateName = stateName;
	}
	
	
	
}
