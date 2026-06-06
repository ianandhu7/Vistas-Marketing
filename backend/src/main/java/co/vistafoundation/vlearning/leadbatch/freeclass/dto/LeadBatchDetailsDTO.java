package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalTime;

/**
 * 
 * @author Sajini
 *
 */

public class LeadBatchDetailsDTO {
	
	private Long idClassStandard;
	private Long idSyllabus;
	private Long idSubject;
	private Long idSujectChapter;
	private Long idLanguage;
	private Long idAvailableSchedule;
	private int count;
	private String classStandadName;
	private String syllabusName;
	private String subjectName;
	private String chapterName;
	private String language;
	private String dayOfWeek;
	private LocalTime fromTime;
	private LocalTime toTime;
	private Long idState;
	private String stateName;
	
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getClassStandadName() {
		return classStandadName;
	}
	public void setClassStandadName(String classStandadName) {
		this.classStandadName = classStandadName;
	}
	public String getSyllabusName() {
		return syllabusName;
	}
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Long getIdClassStandard() {
		return idClassStandard;
	}
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}
	public Long getIdSyllabus() {
		return idSyllabus;
	}
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public Long getIdSujectChapter() {
		return idSujectChapter;
	}
	public void setIdSujectChapter(Long idSujectChapter) {
		this.idSujectChapter = idSujectChapter;
	}
	public Long getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}
	public Long getIdAvailableSchedule() {
		return idAvailableSchedule;
	}
	public void setIdAvailableSchedule(Long idAvailableSchedule) {
		this.idAvailableSchedule = idAvailableSchedule;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idSubject
	 * @param idSujectChapter
	 * @param idLanguage
	 * @param idAvailableSchedule
	 * @param classStandadName
	 * @param syllabusName
	 * @param subjectName
	 * @param chapterName
	 * @param language
	 * @param dayOfWeek
	 * @param fromTime
	 * @param toTime
	 */
	public LeadBatchDetailsDTO(Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter,
			Long idLanguage, Long idAvailableSchedule, String classStandadName, String syllabusName, String subjectName,
			String chapterName, String language, String dayOfWeek, LocalTime fromTime, LocalTime toTime) {
		super();
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idSubject = idSubject;
		this.idSujectChapter = idSujectChapter;
		this.idLanguage = idLanguage;
		this.idAvailableSchedule = idAvailableSchedule;
		this.classStandadName = classStandadName;
		this.syllabusName = syllabusName;
		this.subjectName = subjectName;
		this.chapterName = chapterName;
		this.language = language;
		this.dayOfWeek = dayOfWeek;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}
	/**
	 * 
	 */
	public LeadBatchDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idSubject
	 * @param idSujectChapter
	 * @param idLanguage
	 * @param idAvailableSchedule
	 */
	public LeadBatchDetailsDTO(Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter,
			Long idLanguage, Long idAvailableSchedule) {
		super();
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idSubject = idSubject;
		this.idSujectChapter = idSujectChapter;
		this.idLanguage = idLanguage;
		this.idAvailableSchedule = idAvailableSchedule;
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
