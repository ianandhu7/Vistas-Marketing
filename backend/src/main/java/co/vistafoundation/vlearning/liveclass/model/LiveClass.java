package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * 
 * @author Sajini
 *
 */

@Entity
@Table (name ="LIVE_CLASS")
@Where(clause = "CLASS_TYPE='premium'")
public class LiveClass extends UserDateAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idLIVE_CLASS")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLiveClass;
	
	@Column(name="idTEACHER")
	private Long idTeacher;
	
	@Column(name ="LIVE_CLASS_HEADING")
	private String liveClassHeading;
	
	@Column(name="LIVE_CLASS_DESC",length = 500)
	private String liveClassDesc;
	
	@Column(name="LIVE_CLASS_URL")
	private String liveClassURL;
	
	@Column(name= "idLIVE_CATEGORY")
	private Long idLiveClassCategory;
	
	@Column(name="CLASS_DATE")
	private LocalDate classDate;
	
	@Column(name="CURRENT_RUNNING_FLAG")
	private boolean currentRunningFlag;
	
	@Column(name="LIVE_COMPLETION_FLAG")
	private boolean liveCompletionFlag;
	
	@Column(name= "idYOUTUBE_MASTER")
	private Long idYoutubeMaster;
	
	@Column(name="FROM_TIME")
	private LocalTime fromTime;
	
	@Column(name="TO_TIME")
	private LocalTime toTime;
	
	@Column(name="ACTUAL_CLASS_START_DATE")
	private Instant actualClassStartDate;
	
	@Column(name="ACTUAL_CLASS_END_DATE")
	private Instant actualClassEndDate;
	
	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;
	
	@Column(name="THUMBNAIL_URL")
	private String thumbnailURL;
	
	@Column(name="PDF_URL")
	private String pdfURL;
	
	@Column(name="INTRO_VIDEO_URL")
	private String introVideoURL;
	
	@Nullable
	@Column(name="idLANGUAGE")
	private Long idLanguage;
	
	@NotNull
	@Column(name = "idSUBJECT")
	private Long idSubject;
	
	@Column(name ="ACTIVE_FLAG")
	private Boolean activeFlag;  
	
	@Column(name = "idSYLLABUS" ,nullable = false)
	private Long idSyllabus;
	
	 @Column(name = "idSTATE" ,nullable = false)
	 private Long idState;
	 
	 @Column(name="CLASS_TYPE",length = 50)
     private String classType;
	

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
		public Boolean getActiveFlag() {
			return activeFlag;
		}
		public void setActiveFlag(Boolean activeFlag) {
			this.activeFlag = activeFlag;
		}

	/**
	 * 
	 */
		
	
		
	public LiveClass() {
		super();
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
	 * @param idClassStandard
	 * @param thumbnailURL
	 * @param pdfURL
	 * @param introVideoURL
	 * @param idLanguage
	 * @param idSubject
	 * @param activeFlag
	 * @param idSyllabus
	 */
	public LiveClass(Long idTeacher, String liveClassHeading, String liveClassDesc, String liveClassURL,
			Long idLiveClassCategory, LocalDate classDate, boolean currentRunningFlag, boolean liveCompletionFlag,
			Long idYoutubeMaster, LocalTime fromTime, LocalTime toTime, Instant actualClassStartDate,
			Instant actualClassEndDate, Long idClassStandard, String thumbnailURL, String pdfURL, String introVideoURL,
			Long idLanguage, @NotNull Long idSubject, Boolean activeFlag, Long idSyllabus) {
		super();
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
		this.idClassStandard = idClassStandard;
		this.thumbnailURL = thumbnailURL;
		this.pdfURL = pdfURL;
		this.introVideoURL = introVideoURL;
		this.idLanguage = idLanguage;
		this.idSubject = idSubject;
		this.activeFlag = activeFlag;
		this.idSyllabus = idSyllabus;
	}
	
	/**
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
	 * @param idClassStandard
	 * @param thumbnailURL
	 * @param pdfURL
	 * @param introVideoURL
	 * @param idLanguage
	 * @param idSubject
	 * @param activeFlag
	 * @param idSyllabus
	 * @param idState
	 * @param classType
	 */
	public LiveClass(Long idTeacher, String liveClassHeading, String liveClassDesc, String liveClassURL,
			Long idLiveClassCategory, LocalDate classDate, boolean currentRunningFlag, boolean liveCompletionFlag,
			Long idYoutubeMaster, LocalTime fromTime, LocalTime toTime, Instant actualClassStartDate,
			Instant actualClassEndDate, Long idClassStandard, String thumbnailURL, String pdfURL, String introVideoURL,
			Long idLanguage, @NotNull Long idSubject, Boolean activeFlag, Long idSyllabus, Long idState,
			String classType) {
		super();
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
		this.idClassStandard = idClassStandard;
		this.thumbnailURL = thumbnailURL;
		this.pdfURL = pdfURL;
		this.introVideoURL = introVideoURL;
		this.idLanguage = idLanguage;
		this.idSubject = idSubject;
		this.activeFlag = activeFlag;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.classType = classType;
	}
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

	public Instant getActualClassStartDate() {
		return actualClassStartDate;
	}

	public void setActualClassStartDate(Instant actualClassStartDate) {
		this.actualClassStartDate = actualClassStartDate;
	}

	public Instant getActualClassEndDate() {
		return actualClassEndDate;
	}

	public void setActualClassEndDate(Instant actualClassEndDate) {
		this.actualClassEndDate = actualClassEndDate;
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

	public String getThumbnailURL() {
		return thumbnailURL;
	}

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
	 * 
	 * @return the language
	 */

	public Long getIdLanguage() {
		return idLanguage;
	}
	
	/**
	 * 
	 * @param language
	 */

	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
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