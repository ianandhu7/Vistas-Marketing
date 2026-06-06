/**
 * 
 */
package co.vistafoundation.vlearning.share.dto;

/**
 * @author NaveenKumar
 *
 */
public class ShareVideoDTO {

	private String idShareVideo;

	private String videoType;

	private String s3BucketURL;

	private String vcpVideoId;

	private String vcpVideoOtp;

	private String liveClassURL;

	private Boolean premiumFlag;

	private String title;

	private String subTitle;

	private String description;

	private String thumbnailURL;

	/**
	 * @return the premiumFlag
	 */
	public Boolean getPremiumFlag() {
		return premiumFlag;
	}

	/**
	 * @param premiumFlag the premiumFlag to set
	 */
	public void setPremiumFlag(Boolean premiumFlag) {
		this.premiumFlag = premiumFlag;
	}

	public String getIdShareVideo() {
		return idShareVideo;
	}

	public void setIdShareVideo(String idShareVideo) {
		this.idShareVideo = idShareVideo;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getS3BucketURL() {
		return s3BucketURL;
	}

	public void setS3BucketURL(String s3BucketURL) {
		this.s3BucketURL = s3BucketURL;
	}

	public String getVcpVideoId() {
		return vcpVideoId;
	}

	public void setVcpVideoId(String vcpVideoId) {
		this.vcpVideoId = vcpVideoId;
	}

	public String getVcpVideoOtp() {
		return vcpVideoOtp;
	}

	public void setVcpVideoOtp(String vcpVideoOtp) {
		this.vcpVideoOtp = vcpVideoOtp;
	}

	public String getLiveClassURL() {
		return liveClassURL;
	}

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * @param subTitle the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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

	public ShareVideoDTO(String idShareVideo, String videoType, String s3BucketURL, String vcpVideoId,
			String vcpVideoOtp, String liveClassURL, Boolean premiumFlag, String title, String subTitle,
			String description, String thumbnailURL) {
		super();
		this.idShareVideo = idShareVideo;
		this.videoType = videoType;
		this.s3BucketURL = s3BucketURL;
		this.vcpVideoId = vcpVideoId;
		this.vcpVideoOtp = vcpVideoOtp;
		this.liveClassURL = liveClassURL;
		this.premiumFlag = premiumFlag;
		this.title = title;
		this.subTitle = subTitle;
		this.description = description;
		this.thumbnailURL = thumbnailURL;
	}

	public ShareVideoDTO() {
		super();
	}

}
