package co.vistafoundation.vlearning.liveclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * @author Sajini
 *
 */

public class LiveClassDto {
	
	private Long idLiveClass;
	
	private Long idTeacher;
	
//	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDate classDate;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;

	private String liveClassHeading;
	
	private String liveClassDescription;
	
	private Long idYoutubeMaster;
	
	private String liveClassURL;
	
	private String thumbnailURL;
	
	private boolean currentRunningFlag;
	
	private String pdfURL;
	
	private boolean liveCompletionFlag;
	
	private Long idLiveClassCategory;
	
	private String classCategory;
	
	private String teacherName;
	
	private String teacherHeader;
	
	private String teacherDescription;
	
	private String introVideoURL;
	
	private String classStandard;
	
	private String language;
	
	private Long idClassStandard;
	
	private Long idSubject;
	
	private String subjectName;
	
	private Long idSyllabus;
	
	private String syllabusName;
	
     private Long idState;
	
	private String stateName;
	
	
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	private String textBelowLiveClass;
	/**
	 * @return the idLiveClass
	 */
	public Long getIdLiveClass() {
		return idLiveClass;
	}

	/**
	 * @param idLiveClass the idLiveClass to set
	 */
	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
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
	 * @return the classDate
	 */
	public LocalDate getClassDate() {
		return classDate;
	}

	/**
	 * @param classDate the classDate to set
	 */
	public void setClassDate(LocalDate classDate) {
		this.classDate = classDate;
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
	 * @return the liveClassHeading
	 */
	public String getLiveClassHeading() {
		return liveClassHeading;
	}

	/**
	 * @param liveClassHeading the liveClassHeading to set
	 */
	public void setLiveClassHeading(String liveClassHeading) {
		this.liveClassHeading = liveClassHeading;
	}

	/**
	 * @return the liveClassDescription
	 */
	public String getLiveClassDescription() {
		return liveClassDescription;
	}

	/**
	 * @param liveClassDescription the liveClassDescription to set
	 */
	public void setLiveClassDescription(String liveClassDescription) {
		this.liveClassDescription = liveClassDescription;
	}

	/**
	 * @return the idYoutubeMaster
	 */
	public Long getIdYoutubeMaster() {
		return idYoutubeMaster;
	}

	/**
	 * @param idYoutubeMaster the idYoutubeMaster to set
	 */
	public void setIdYoutubeMaster(Long idYoutubeMaster) {
		this.idYoutubeMaster = idYoutubeMaster;
	}

	/**
	 * @return the liveClassURL
	 */
	public String getLiveClassURL() {
		return liveClassURL;
	}

	/**
	 * @param liveClassURL the liveClassURL to set
	 */
	public void setLiveClassURL(String liveClassURL) {
		this.liveClassURL = liveClassURL;
	}

	/**
	 * @return the thumbnailURL
	 */
	public String getThumbnailURL() {
		return thumbnailURL;
	}

	/**
	 * @param thumbnailURL the thumbnailURL to set
	 */
	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	/**
	 * @return the currentRunningFlag
	 */
	public boolean isCurrentRunningFlag() {
		return currentRunningFlag;
	}

	/**
	 * @param currentRunningFlag the currentRunningFlag to set
	 */
	public void setCurrentRunningFlag(boolean currentRunningFlag) {
		this.currentRunningFlag = currentRunningFlag;
	}

	/**
	 * @return the pdfURL
	 */
	public String getPdfURL() {
		return pdfURL;
	}

	/**
	 * @param pdfURL the pdfURL to set
	 */
	public void setPdfURL(String pdfURL) {
		this.pdfURL = pdfURL;
	}

	/**
	 * @return the liveCompletionFlag
	 */
	public boolean isLiveCompletionFlag() {
		return liveCompletionFlag;
	}

	/**
	 * @param liveCompletionFlag the liveCompletionFlag to set
	 */
	public void setLiveCompletionFlag(boolean liveCompletionFlag) {
		this.liveCompletionFlag = liveCompletionFlag;
	}

	/**
	 * @return the idLiveClassCategory
	 */
	public Long getIdLiveClassCategory() {
		return idLiveClassCategory;
	}

	/**
	 * @param idLiveClassCategory the idLiveClassCategory to set
	 */
	public void setIdLiveClassCategory(Long idLiveClassCategory) {
		this.idLiveClassCategory = idLiveClassCategory;
	}

	/**
	 * @return the classCategory
	 */
	public String getClassCategory() {
		return classCategory;
	}

	/**
	 * @param classCategory the classCategory to set
	 */
	public void setClassCategory(String classCategory) {
		this.classCategory = classCategory;
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
	 * @return the teacherHeader
	 */
	public String getTeacherHeader() {
		return teacherHeader;
	}

	/**
	 * @param teacherHeader the teacherHeader to set
	 */
	public void setTeacherHeader(String teacherHeader) {
		this.teacherHeader = teacherHeader;
	}

	/**
	 * @return the teacherDescription
	 */
	public String getTeacherDescription() {
		return teacherDescription;
	}

	/**
	 * @param teacherDescription the teacherDescription to set
	 */
	public void setTeacherDescription(String teacherDescription) {
		this.teacherDescription = teacherDescription;
	}

	/**
	 * @return the introVideoURL
	 */
	public String getIntroVideoURL() {
		return introVideoURL;
	}

	/**
	 * @param introVideoURL the introVideoURL to set
	 */
	public void setIntroVideoURL(String introVideoURL) {
		this.introVideoURL = introVideoURL;
	}

	/**
	 * @return the classStandard
	 */
	public String getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTextBelowLiveClass() {
		return textBelowLiveClass;
	}

	public void setTextBelowLiveClass(String textBelowLiveClass) {
		this.textBelowLiveClass = textBelowLiveClass;
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

	
	

	
}
