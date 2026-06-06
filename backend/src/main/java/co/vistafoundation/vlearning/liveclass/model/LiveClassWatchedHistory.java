package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

@Entity
@Table(name = "LIVE_CLASS_WATCHED_HISTORY")
public class LiveClassWatchedHistory extends UserDateAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idLIVE_CLASS_WATCHED_HISTORY")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLiveClassWatchedHistory;

	@Column(name = "idLiveClass")
	private Long idLiveClass;

	@Column(name = "thumbnailURL")
	private String thumbnailURL;

	@Column(name = "LAST_ACCESSED_TIMESTMAP")
	private LocalDateTime lastAccessedTimestamp;

	@Column(name = "VIDEO_COVERAGE_DURATION")
	private Long videoCoverageDuration;

	@Column(name = "idLiveClassCategory")
	private Long idLiveClassCategory;

	
	@Column (name="liveClassHeading")
	private String liveClassHeading;
	
	@Column (name="liveClassDescription",length = 500)
	private String liveClassDescription;

	
  
	@Column (name="idVL_USER")
	private Long idVlUser;
	
	@Column (name="id_STUDENT")

	private Long idStudent;

	public LiveClassWatchedHistory() {
		super();
	}
	public LiveClassWatchedHistory( Long idLiveClass, String thumbnailURL,
			LocalDateTime lastAccessedTimestamp, Long videoCoverageDuration, Long idLiveClassCategory,
			String liveClassHeading, String liveClassDescription, Long idVlUser, Long idStudent,Long idSubject, String subjectName) {


		super();
		this.idLiveClass = idLiveClass;
		this.thumbnailURL = thumbnailURL;
		this.lastAccessedTimestamp = lastAccessedTimestamp;
		this.videoCoverageDuration = videoCoverageDuration;
		this.idLiveClassCategory = idLiveClassCategory;
		this.liveClassHeading = liveClassHeading;
		this.liveClassDescription = liveClassDescription;
		this.idVlUser = idVlUser;
		this.idStudent = idStudent;
		this.idSubject=idSubject;
		this.subjectName=subjectName;
		}


	@Column (name="id_Subject")
	private Long idSubject;
	
	@Column (name="subject_Name")
	private String subjectName;
	
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



	
	public String getLiveClassHeading() {
		return liveClassHeading;
	}

	public void setLiveClassHeading(String liveClassHeading) {
		this.liveClassHeading = liveClassHeading;
	}

	public String getLiveClassDescription() {
		return liveClassDescription;
	}

	public void setLiveClassDescription(String liveClassDescription) {
		this.liveClassDescription = liveClassDescription;
	}




	
	
	

	public Long getIdLiveClassCategory() {
		return idLiveClassCategory;
	}

	public void setIdLiveClassCategory(Long idLiveClassCategory) {
		this.idLiveClassCategory = idLiveClassCategory;
	}

	public Long getIdLiveClassWatchedHistory() {
		return idLiveClassWatchedHistory;
	}

	public void setIdLiveClassWatchedHistory(Long idLiveClassWatchedHistory) {
		this.idLiveClassWatchedHistory = idLiveClassWatchedHistory;
	}

	public Long getIdLiveClass() {
		return idLiveClass;
	}

	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public LocalDateTime getLastAccessedTimestamp() {
		return lastAccessedTimestamp;
	}

	public void setLastAccessedTimestamp(LocalDateTime lastAccessedTimestamp) {
		this.lastAccessedTimestamp = lastAccessedTimestamp;
	}

	public Long getVideoCoverageDuration() {
		return videoCoverageDuration;
	}

	public void setVideoCoverageDuration(Long videoCoverageDuration) {
		this.videoCoverageDuration = videoCoverageDuration;
	}

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
