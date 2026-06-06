/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author vk
 *
 */
public class DemoClassRequestDTO {

	private Long idDemoClass;

	private Long idTeacher;

	private Long idSubject;

	private Long idClassStandard;

	private Long idSyllabus;

	private LocalDate fromDate;
	
	private LocalDate toDate;

	private LocalTime fromTime;

	private LocalTime toTime;

	private Integer maxStudents;

	private Long idSubjectChapter;
	
	private List<String> daysOfWeek;
	
	private String description;
	
	private Long idLevelExtraCurricular;
	
	private Long idState;

	/**
	 * @param idDemoClass
	 * @param idTeacher
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param fromDate
	 * @param toDate
	 * @param fromTime
	 * @param toTime
	 * @param maxStudents
	 * @param idSubjectChapter
	 */
	public DemoClassRequestDTO(Long idDemoClass, Long idTeacher, Long idSubject, Long idClassStandard, Long idSyllabus,
			LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime, Integer maxStudents,
			Long idSubjectChapter, List<String> daysOfWeek, String description) {
		super();
		this.idDemoClass = idDemoClass;
		this.idTeacher = idTeacher;
		this.idSubject = idSubject;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.maxStudents = maxStudents;
		this.idSubjectChapter = idSubjectChapter;
		this.daysOfWeek = daysOfWeek;
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
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
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
	 * @return the daysOfWeek
	 */
	public List<String> getDaysOfWeek() {
		return daysOfWeek;
	}

	/**
	 * @param daysOfWeek the daysOfWeek to set
	 */
	public void setDaysOfWeek(List<String> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
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
