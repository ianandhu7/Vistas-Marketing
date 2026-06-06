/**
 * 
 */
package co.vistafoundation.vlearning.share.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 *
 */
@Entity
@Table(name = "SHARE_VIDEO")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ShareVideo extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idShareVideo")
	private Long idShareVideo;

	@Column(name = "VIDEO_TYPE", nullable = false)
	private String videoType;

	@Column(name = "idSTATE")
	private Long idState;

	@Column(name = "idVIDEO")
	private Long idVideo;

	@Column(name = "S3_BUCKET_URL")
	private String s3BucketURL;

	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "idSYLLABUS")
	private Long idSyllabus;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	@Column(name = "idREQUESTED_USER")
	private Long idRequestedUser;

	@Column(name = "VIEW_COUNT")
	private int viewCount;

	@Column(name = "VCP_VIDEO_ID", length = 500)
	private String vcpVideoId;

	@Column(name = "VCP_VIDEO_OTP")
	private String vcpVideoOtp;

	@Column(name = "REQUESTED_DATE")
	private LocalDate requestedDate;

	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;

	@Column(name = "VIDEO_TITLE")
	private String videoTitle;

	@Column(name = "VIDEO_SUB_TITLE")
	private String videoSubTitle;

	@Column(name = "VIDEO_DESCRIPTION")
	private String videoDescription;

	@Column(name = "THUMBNAIL_URL")
	private String thumbnailURL;

	/**
	 * @return the idShareVideo
	 */
	public Long getIdShareVideo() {
		return idShareVideo;
	}

	/**
	 * @param idShareVideo the idShareVideo to set
	 */
	public void setIdShareVideo(Long idShareVideo) {
		this.idShareVideo = idShareVideo;
	}

	/**
	 * @return the videoType
	 */
	public String getVideoType() {
		return videoType;
	}

	/**
	 * @param videoType the videoType to set
	 */
	public void setVideoType(String videoType) {
		this.videoType = videoType;
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
	 * @return the idVideo
	 */
	public Long getIdVideo() {
		return idVideo;
	}

	/**
	 * @param idVideo the idVideo to set
	 */
	public void setIdVideo(Long idVideo) {
		this.idVideo = idVideo;
	}

	/**
	 * @return the s3BucketURL
	 */
	public String getS3BucketURL() {
		return s3BucketURL;
	}

	/**
	 * @param s3BucketURL the s3BucketURL to set
	 */
	public void setS3BucketURL(String s3BucketURL) {
		this.s3BucketURL = s3BucketURL;
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
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the idRequestedUser
	 */
	public Long getIdRequestedUser() {
		return idRequestedUser;
	}

	/**
	 * @param idRequestedUser the idRequestedUser to set
	 */
	public void setIdRequestedUser(Long idRequestedUser) {
		this.idRequestedUser = idRequestedUser;
	}

	/**
	 * @return the viewCount
	 */
	public int getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @return the vcpVideoId
	 */
	public String getVcpVideoId() {
		return vcpVideoId;
	}

	/**
	 * @param vcpVideoId the vcpVideoId to set
	 */
	public void setVcpVideoId(String vcpVideoId) {
		this.vcpVideoId = vcpVideoId;
	}

	/**
	 * @return the vcpVideoOtp
	 */
	public String getVcpVideoOtp() {
		return vcpVideoOtp;
	}

	/**
	 * @param vcpVideoOtp the vcpVideoOtp to set
	 */
	public void setVcpVideoOtp(String vcpVideoOtp) {
		this.vcpVideoOtp = vcpVideoOtp;
	}

	/**
	 * @return the requestedDate
	 */
	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	/**
	 * @param requestedDate the requestedDate to set
	 */
	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
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
	 * @return the videoTitle
	 */
	public String getVideoTitle() {
		return videoTitle;
	}

	/**
	 * @param videoTitle the videoTitle to set
	 */
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	/**
	 * @return the videoSubTitle
	 */
	public String getVideoSubTitle() {
		return videoSubTitle;
	}

	/**
	 * @param videoSubTitle the videoSubTitle to set
	 */
	public void setVideoSubTitle(String videoSubTitle) {
		this.videoSubTitle = videoSubTitle;
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

	public ShareVideo(String videoType, Long idState, Long idVideo, String s3BucketURL, Long idClassStandard,
			Long idSyllabus, String contentType, Long idRequestedUser, int viewCount, String vcpVideoId,
			String vcpVideoOtp, LocalDate requestedDate, Boolean activeFlag, String videoTitle, String videoSubTitle,
			String videoDescription, String thumbnailURL) {
		super();
		this.videoType = videoType;
		this.idState = idState;
		this.idVideo = idVideo;
		this.s3BucketURL = s3BucketURL;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.contentType = contentType;
		this.idRequestedUser = idRequestedUser;
		this.viewCount = viewCount;
		this.vcpVideoId = vcpVideoId;
		this.vcpVideoOtp = vcpVideoOtp;
		this.requestedDate = requestedDate;
		this.activeFlag = activeFlag;
		this.videoTitle = videoTitle;
		this.videoSubTitle = videoSubTitle;
		this.videoDescription = videoDescription;
		this.thumbnailURL = thumbnailURL;
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
	 * 
	 */
	public ShareVideo() {
		super();
	}

}
