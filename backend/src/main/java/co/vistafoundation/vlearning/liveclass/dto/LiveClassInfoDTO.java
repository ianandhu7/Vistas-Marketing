/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import co.vistafoundation.vlearning.classes.model.ClassStandard;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public class LiveClassInfoDTO {

	private Long idLiveClass;

	private Long idTeacher;

	private String liveClassHeading;

	private String liveClassDesc;

	private String liveClassURL;

	private Long idLiveClassCategory;

	private LocalDate classDate;

	private boolean currentRunningFlag;

	private boolean liveCompletionFlag;

	private Long idYoutubeMaster;

	private LocalTime fromTime;

	private LocalTime toTime;

	private Instant actualClassStartDate;

	private Instant actualClassEndDate;

	private ClassStandard classStandard;
	
	private String thumbnailURL;
	
	private String pdfURL;
	
	private String introVideoURL;
	
	private Long idLanguage;
	
	private Long idSubject;
	
	private Long idSyllabus;
	
	private Long idState;
	
	private String classType;

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
	 * @return the liveClassDesc
	 */
	public String getLiveClassDesc() {
		return liveClassDesc;
	}

	/**
	 * @param liveClassDesc the liveClassDesc to set
	 */
	public void setLiveClassDesc(String liveClassDesc) {
		this.liveClassDesc = liveClassDesc;
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
	 * @return the actualClassStartDate
	 */
	public Instant getActualClassStartDate() {
		return actualClassStartDate;
	}

	/**
	 * @param actualClassStartDate the actualClassStartDate to set
	 */
	public void setActualClassStartDate(Instant actualClassStartDate) {
		this.actualClassStartDate = actualClassStartDate;
	}

	/**
	 * @return the actualClassEndDate
	 */
	public Instant getActualClassEndDate() {
		return actualClassEndDate;
	}

	/**
	 * @param actualClassEndDate the actualClassEndDate to set
	 */
	public void setActualClassEndDate(Instant actualClassEndDate) {
		this.actualClassEndDate = actualClassEndDate;
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
	 * @return the idLanguage
	 */
	public Long getIdLanguage() {
		return idLanguage;
	}

	/**
	 * @param idLanguage the idLanguage to set
	 */
	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @param idLiveClass
	 * @param idTeacher
	 * @param liveClassHeading
	 * @param liveClassDesc
	 * @param liveClassURL
	 * @param idLiveClassCategory
	 * @param classDate
	 * @param currentRunningFlag
	 * @param liveCompletionFlag
	 * @param idYoutubeMaster
	 * @param fromTime
	 * @param toTime
	 * @param actualClassStartDate
	 * @param actualClassEndDate
	 * @param classStandard
	 * @param thumbnailURL
	 * @param pdfURL
	 * @param introVideoURL
	 * @param idLanguage;
	 */
	public LiveClassInfoDTO(Long idLiveClass, Long idTeacher, String liveClassHeading, String liveClassDesc,
			String liveClassURL, Long idLiveClassCategory, LocalDate classDate, boolean currentRunningFlag,
			boolean liveCompletionFlag, Long idYoutubeMaster, LocalTime fromTime, LocalTime toTime,
			Instant actualClassStartDate, Instant actualClassEndDate, ClassStandard classStandard, String thumbnailURL, String pdfURL, String introVideoURL, Long idLanguage) {
		super();
		this.idLiveClass = idLiveClass;
		this.idTeacher = idTeacher;
		this.liveClassHeading = liveClassHeading;
		this.liveClassDesc = liveClassDesc;
		this.liveClassURL = liveClassURL;
		this.idLiveClassCategory = idLiveClassCategory;
		this.classDate = classDate;
		this.currentRunningFlag = currentRunningFlag;
		this.liveCompletionFlag = liveCompletionFlag;
		this.idYoutubeMaster = idYoutubeMaster;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.actualClassStartDate = actualClassStartDate;
		this.actualClassEndDate = actualClassEndDate;
		this.classStandard = classStandard;
		this.thumbnailURL=thumbnailURL;
		this.pdfURL=pdfURL;
		this.introVideoURL=introVideoURL;
		this.idLanguage=idLanguage;
	}

	/**
	 * 
	 */
	public LiveClassInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the classType
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * @param classType the classType to set
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}
	
	
	

}
