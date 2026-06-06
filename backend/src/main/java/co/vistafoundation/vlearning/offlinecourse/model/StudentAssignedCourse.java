/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk, @author Naveen Kumar
 *
 */
@Entity
@Table(name = "STUDENT_ASSIGNED_COURSE")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentAssignedCourse extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_ASSIGNED_COURSE", nullable = false)
	private Long idStudentAssignedCourse;

	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscription;

	@Column(name = "idOFFLINE_VIDEO_COURSE", nullable = false)
	private Long idOfflineVideoCourse;

	@Column(name = "LAST_ACCESSED_DATE")
	private Date lastAccessedDate;

	@Column(name = "COMPLETE_FLAG")
	private Boolean completeFlag;

	@Column(name = "VIDEO_COVERAGE_DURATION" , nullable = false)
	private int videoCoverageDuration;

	@Column(name = "VIDEO_DURATION", nullable = false)
	private int videoDuration;

	@Column(name = "PCT_COMPLETE")
	private String pctComplete;

	@Column(name = "idSUBJECT_CHAPTER", nullable = false)
	private Long idSubjectChapter;

	@Column(name = "SUBJECT_NAME", length = 45)
	private String subjectName;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "CHAPTER_NAME", length = 100)
	private String chapterName;

	@Column(name = "TOPIC", length = 45)
	private String topic;

	@Column(name = "VIDEO_SEQ_NUMBER")
	private int videoSeqNumber;

	@Column(name = "VIDEO_OTP", length = 200, nullable = false)
	private String videoOtp;

	@Column(name = "VIDEO_THEME", length = 100, nullable = false)
	private String videoTheme;

	@Column(name = "VIDEO_EN_Link", length = 500)
	private String videoEnLink;

	@Column(name = "VIDEO_TM_Link", length = 500)
	private String videoTmLink;

	@Column(name = "VIDEO_TL_Link", length = 500)
	private String videoTlLink;

	@Column(name = "VIDEO_HN_LINK", length = 500)
	private String videoHnLink;

	@Column(name = "VIDEO_KN_LINK", length = 500)
	private String videoKnLink;

	@Column(name = "VIDEO_ML_LINK", length = 500)
	private String videoMlLink;

	@Column(name = "VIDEO_MH_LINK", length = 500)
	private String videoMhLink;

	@Column(name = "VIDEO_1_LINK", length = 500)
	private String video1Link;

	@Column(name = "VIDEO_2_LINK", length = 500)
	private String video2Link;

	@Column(name = "VIDEO_3_LINK", length = 500)
	private String video3Link;

	@Column(name = "VIDEO_4_LINK", length = 500)
	private String video4Link;

	@Column(name = "VIDEO_5_LINK", length = 500)
	private String video5Link;

	@Column(name = "VIDEO_POSTER_1_LOC", length = 500)
	private String videoPoster1Location;

	@Column(name = "VIDEO_POSTER_2_LOC", length = 500)
	private String videoPoster2Location;

	@Column(name = "VIDEO_POSTER_3_LOC", length = 500)
	private String videoPoster3Location;
	
	@Column(name = "VIDEO_DESCRIPTION", length = 500)
	private String videoDescription;
	
	@Column(name = "idPRODUCT", nullable = false)
	private Long idProduct;
	
	
	/* @author Naveen Kumar 
	 * 05/04/2021
	 * add new properties 
	 */
	
	@Column(name = "VIDEO_OTP_TM", length = 200)
	private String videoOtpTM;
	
	@Column(name = "VIDEO_OTP_TL", length = 200)
	private String videoOtpTL;
	
	@Column(name = "VIDEO_OTP_HN", length = 200)
	private String videoOtpHN;
	
	@Column(name = "VIDEO_OTP_KN", length = 200)
	private String videoOtpKN;
	
	@Column(name = "VIDEO_OTP_ML", length = 200)
	private String videoOtpML;
	
	@Column(name = "VIDEO_OTP_MH", length = 200)
	private String videoOtpMH;
	
	@Column(name = "VIDEO_OTP_1_LINK", length = 200)
	private String videoOtp1Link;
	
	@Column(name = "VIDEO_OTP_2_LINK", length = 200)
	private String videoOtp2Link;
	
	@Column(name = "VIDEO_OTP_3_LINK", length = 200)
	private String videoOtp3Link;
	
	@Column(name = "VIDEO_OTP_4_LINK", length = 200)
	private String videoOtp4Link;
	
	@Column(name = "VIDEO_OTP_5_LINK", length = 200)
	private String videoOtp5Link;
	

	/**
	 * @return the idStudentAssignedCourse
	 */
	public Long getIdStudentAssignedCourse() {
		return idStudentAssignedCourse;
	}

	/**
	 * @param idStudentAssignedCourse the idStudentAssignedCourse to set
	 */
	public void setIdStudentAssignedCourse(Long idStudentAssignedCourse) {
		this.idStudentAssignedCourse = idStudentAssignedCourse;
	}

	/**
	 * @return the idStudentSubscription
	 */
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @return the idOfflineVideoCourse
	 */
	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	/**
	 * @param idOfflineVideoCourse the idOfflineVideoCourse to set
	 */
	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	/**
	 * @return the lastAccessedDate
	 */
	public Date getLastAccessedDate() {
		return lastAccessedDate;
	}

	/**
	 * @param lastAccessedDate the lastAccessedDate to set
	 */
	public void setLastAccessedDate(Date lastAccessedDate) {
		this.lastAccessedDate = lastAccessedDate;
	}

	/**
	 * @return the completeFlag
	 */
	public Boolean getCompleteFlag() {
		return completeFlag;
	}

	/**
	 * @param completeFlag the completeFlag to set
	 */
	public void setCompleteFlag(Boolean completeFlag) {
		this.completeFlag = completeFlag;
	}

	/**
	 * @return the videoCoverageDuration
	 */
	public int getVideoCoverageDuration() {
		return videoCoverageDuration;
	}

	/**
	 * @param videoCoverageDuration the videoCoverageDuration to set
	 */
	public void setVideoCoverageDuration(int videoCoverageDuration) {
		this.videoCoverageDuration = videoCoverageDuration;
	}

	/**
	 * @return the videoDuration
	 */
	public int getVideoDuration() {
		return videoDuration;
	}

	/**
	 * @param videoDuration the videoDuration to set
	 */
	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}

	/**
	 * @return the pctComplete
	 */
	public String getPctComplete() {
		return pctComplete;
	}

	/**
	 * @param pctComplete the pctComplete to set
	 */
	public void setPctComplete(String pctComplete) {
		this.pctComplete = pctComplete;
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
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	

	/**
	 * @return the videoSeqNumber
	 */
	public int getVideoSeqNumber() {
		return videoSeqNumber;
	}

	/**
	 * @param videoSeqNumber the videoSeqNumber to set
	 */
	public void setVideoSeqNumber(int videoSeqNumber) {
		this.videoSeqNumber = videoSeqNumber;
	}

	/**
	 * @return the videoOtp
	 */
	public String getVideoOtp() {
		return videoOtp;
	}

	/**
	 * @param videoOtp the videoOtp to set
	 */
	public void setVideoOtp(String videoOtp) {
		this.videoOtp = videoOtp;
	}

	/**
	 * @return the videoTheme
	 */
	public String getVideoTheme() {
		return videoTheme;
	}

	/**
	 * @param videoTheme the videoTheme to set
	 */
	public void setVideoTheme(String videoTheme) {
		this.videoTheme = videoTheme;
	}

	/**
	 * @return the videoEnLink
	 */
	public String getVideoEnLink() {
		return videoEnLink;
	}

	/**
	 * @param videoEnLink the videoEnLink to set
	 */
	public void setVideoEnLink(String videoEnLink) {
		this.videoEnLink = videoEnLink;
	}

	/**
	 * @return the videoTmLink
	 */
	public String getVideoTmLink() {
		return videoTmLink;
	}

	/**
	 * @param videoTmLink the videoTmLink to set
	 */
	public void setVideoTmLink(String videoTmLink) {
		this.videoTmLink = videoTmLink;
	}

	/**
	 * @return the videoTlLink
	 */
	public String getVideoTlLink() {
		return videoTlLink;
	}

	/**
	 * @param videoTlLink the videoTlLink to set
	 */
	public void setVideoTlLink(String videoTlLink) {
		this.videoTlLink = videoTlLink;
	}

	/**
	 * @return the videoHnLink
	 */
	public String getVideoHnLink() {
		return videoHnLink;
	}

	/**
	 * @param videoHnLink the videoHnLink to set
	 */
	public void setVideoHnLink(String videoHnLink) {
		this.videoHnLink = videoHnLink;
	}

	/**
	 * @return the videoKnLink
	 */
	public String getVideoKnLink() {
		return videoKnLink;
	}

	/**
	 * @param videoKnLink the videoKnLink to set
	 */
	public void setVideoKnLink(String videoKnLink) {
		this.videoKnLink = videoKnLink;
	}

	/**
	 * @return the videoMlLink
	 */
	public String getVideoMlLink() {
		return videoMlLink;
	}

	/**
	 * @param videoMlLink the videoMlLink to set
	 */
	public void setVideoMlLink(String videoMlLink) {
		this.videoMlLink = videoMlLink;
	}

	/**
	 * @return the videoMhLink
	 */
	public String getVideoMhLink() {
		return videoMhLink;
	}

	/**
	 * @param videoMhLink the videoMhLink to set
	 */
	public void setVideoMhLink(String videoMhLink) {
		this.videoMhLink = videoMhLink;
	}

	/**
	 * @return the video1Link
	 */
	public String getVideo1Link() {
		return video1Link;
	}

	/**
	 * @param video1Link the video1Link to set
	 */
	public void setVideo1Link(String video1Link) {
		this.video1Link = video1Link;
	}

	/**
	 * @return the video2Link
	 */
	public String getVideo2Link() {
		return video2Link;
	}

	/**
	 * @param video2Link the video2Link to set
	 */
	public void setVideo2Link(String video2Link) {
		this.video2Link = video2Link;
	}

	/**
	 * @return the video3Link
	 */
	public String getVideo3Link() {
		return video3Link;
	}

	/**
	 * @param video3Link the video3Link to set
	 */
	public void setVideo3Link(String video3Link) {
		this.video3Link = video3Link;
	}

	/**
	 * @return the video4Link
	 */
	public String getVideo4Link() {
		return video4Link;
	}

	/**
	 * @param video4Link the video4Link to set
	 */
	public void setVideo4Link(String video4Link) {
		this.video4Link = video4Link;
	}

	/**
	 * @return the video5Link
	 */
	public String getVideo5Link() {
		return video5Link;
	}

	/**
	 * @param video5Link the video5Link to set
	 */
	public void setVideo5Link(String video5Link) {
		this.video5Link = video5Link;
	}

	/**
	 * @return the videoPoster1Location
	 */
	public String getVideoPoster1Location() {
		return videoPoster1Location;
	}

	/**
	 * @param videoPoster1Location the videoPoster1Location to set
	 */
	public void setVideoPoster1Location(String videoPoster1Location) {
		this.videoPoster1Location = videoPoster1Location;
	}

	/**
	 * @return the videoPoster2Location
	 */
	public String getVideoPoster2Location() {
		return videoPoster2Location;
	}

	/**
	 * @param videoPoster2Location the videoPoster2Location to set
	 */
	public void setVideoPoster2Location(String videoPoster2Location) {
		this.videoPoster2Location = videoPoster2Location;
	}

	/**
	 * @return the videoPoster3Location
	 */
	public String getVideoPoster3Location() {
		return videoPoster3Location;
	}

	/**
	 * @param videoPoster3Location the videoPoster3Location to set
	 */
	public void setVideoPoster3Location(String videoPoster3Location) {
		this.videoPoster3Location = videoPoster3Location;
	}
	
	

	/**
	 * @return the videoDescription
	 */
	public String getVideoDescription() {
		return videoDescription;
	}

	/**
	 * @param videoDescription the videoDescription to set
	 */
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	
	

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @param idStudentSubscription
	 * @param idOfflineVideoCourse
	 * @param lastAccessedDate
	 * @param completeFlag
	 * @param videoCoverageDuration
	 * @param videoDuration
	 * @param pctComplete
	 * @param idSubjectChapter
	 * @param subjectName
	 * @param idSubject
	 * @param chapterName
	 * @param topic
	 * @param videoSeqNumber
	 * @param videoOtp
	 * @param videoTheme
	 * @param videoEnLink
	 * @param videoTmLink
	 * @param videoTlLink
	 * @param videoHnLink
	 * @param videoKnLink
	 * @param videoMlLink
	 * @param videoMhLink
	 * @param video1Link
	 * @param video2Link
	 * @param video3Link
	 * @param video4Link
	 * @param video5Link
	 * @param videoPoster1Location
	 * @param videoPoster2Location
	 * @param videoPoster3Location
	 * @param videoDescription
	 */
	public StudentAssignedCourse(Long idStudentSubscription, Long idOfflineVideoCourse, Date lastAccessedDate,
			Boolean completeFlag, int videoCoverageDuration, int videoDuration, String pctComplete,
			Long idSubjectChapter, String subjectName, Long idSubject, String chapterName, String topic,
			int videoSeqNumber, String videoOtp, String videoTheme, String videoEnLink, String videoTmLink,
			String videoTlLink, String videoHnLink, String videoKnLink, String videoMlLink, String videoMhLink,
			String video1Link, String video2Link, String video3Link, String video4Link, String video5Link,
			String videoPoster1Location, String videoPoster2Location, String videoPoster3Location,
			String videoDescription) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idOfflineVideoCourse = idOfflineVideoCourse;
		this.lastAccessedDate = lastAccessedDate;
		this.completeFlag = completeFlag;
		this.videoCoverageDuration = videoCoverageDuration;
		this.videoDuration = videoDuration;
		this.pctComplete = pctComplete;
		this.idSubjectChapter = idSubjectChapter;
		this.subjectName = subjectName;
		this.idSubject = idSubject;
		this.chapterName = chapterName;
		this.topic = topic;
		this.videoSeqNumber = videoSeqNumber;
		this.videoOtp = videoOtp;
		this.videoTheme = videoTheme;
		this.videoEnLink = videoEnLink;
		this.videoTmLink = videoTmLink;
		this.videoTlLink = videoTlLink;
		this.videoHnLink = videoHnLink;
		this.videoKnLink = videoKnLink;
		this.videoMlLink = videoMlLink;
		this.videoMhLink = videoMhLink;
		this.video1Link = video1Link;
		this.video2Link = video2Link;
		this.video3Link = video3Link;
		this.video4Link = video4Link;
		this.video5Link = video5Link;
		this.videoPoster1Location = videoPoster1Location;
		this.videoPoster2Location = videoPoster2Location;
		this.videoPoster3Location = videoPoster3Location;
		this.videoDescription = videoDescription;
	}

	/**
	 * 
	 */
	public StudentAssignedCourse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the videoOtpTM
	 */
	public String getVideoOtpTM() {
		return videoOtpTM;
	}

	/**
	 * @param videoOtpTM the videoOtpTM to set
	 */
	public void setVideoOtpTM(String videoOtpTM) {
		this.videoOtpTM = videoOtpTM;
	}

	/**
	 * @return the videoOtpTL
	 */
	public String getVideoOtpTL() {
		return videoOtpTL;
	}

	/**
	 * @param videoOtpTL the videoOtpTL to set
	 */
	public void setVideoOtpTL(String videoOtpTL) {
		this.videoOtpTL = videoOtpTL;
	}

	/**
	 * @return the videoOtpHN
	 */
	public String getVideoOtpHN() {
		return videoOtpHN;
	}

	/**
	 * @param videoOtpHN the videoOtpHN to set
	 */
	public void setVideoOtpHN(String videoOtpHN) {
		this.videoOtpHN = videoOtpHN;
	}

	/**
	 * @return the videoOtpKN
	 */
	public String getVideoOtpKN() {
		return videoOtpKN;
	}

	/**
	 * @param videoOtpKN the videoOtpKN to set
	 */
	public void setVideoOtpKN(String videoOtpKN) {
		this.videoOtpKN = videoOtpKN;
	}

	/**
	 * @return the videoOtpML
	 */
	public String getVideoOtpML() {
		return videoOtpML;
	}

	/**
	 * @param videoOtpML the videoOtpML to set
	 */
	public void setVideoOtpML(String videoOtpML) {
		this.videoOtpML = videoOtpML;
	}

	/**
	 * @return the videoOtpMH
	 */
	public String getVideoOtpMH() {
		return videoOtpMH;
	}

	/**
	 * @param videoOtpMH the videoOtpMH to set
	 */
	public void setVideoOtpMH(String videoOtpMH) {
		this.videoOtpMH = videoOtpMH;
	}

	/**
	 * @return the videoOtp1Link
	 */
	public String getVideoOtp1Link() {
		return videoOtp1Link;
	}

	/**
	 * @param videoOtp1Link the videoOtp1Link to set
	 */
	public void setVideoOtp1Link(String videoOtp1Link) {
		this.videoOtp1Link = videoOtp1Link;
	}

	/**
	 * @return the videoOtp2Link
	 */
	public String getVideoOtp2Link() {
		return videoOtp2Link;
	}

	/**
	 * @param videoOtp2Link the videoOtp2Link to set
	 */
	public void setVideoOtp2Link(String videoOtp2Link) {
		this.videoOtp2Link = videoOtp2Link;
	}

	/**
	 * @return the videoOtp3Link
	 */
	public String getVideoOtp3Link() {
		return videoOtp3Link;
	}

	/**
	 * @param videoOtp3Link the videoOtp3Link to set
	 */
	public void setVideoOtp3Link(String videoOtp3Link) {
		this.videoOtp3Link = videoOtp3Link;
	}

	/**
	 * @return the videoOtp4Link
	 */
	public String getVideoOtp4Link() {
		return videoOtp4Link;
	}

	/**
	 * @param videoOtp4Link the videoOtp4Link to set
	 */
	public void setVideoOtp4Link(String videoOtp4Link) {
		this.videoOtp4Link = videoOtp4Link;
	}

	/**
	 * @return the videoOtp5Link
	 */
	public String getVideoOtp5Link() {
		return videoOtp5Link;
	}

	/**
	 * @param videoOtp5Link the videoOtp5Link to set
	 */
	public void setVideoOtp5Link(String videoOtp5Link) {
		this.videoOtp5Link = videoOtp5Link;
	}
	
	

}
