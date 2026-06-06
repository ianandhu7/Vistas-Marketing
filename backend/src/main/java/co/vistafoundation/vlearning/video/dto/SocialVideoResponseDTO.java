package co.vistafoundation.vlearning.video.dto;


import java.util.List;

import co.vistafoundation.vlearning.video.model.SocialVideoResolution;
import co.vistafoundation.vlearning.video.model.VideoComment;



public class SocialVideoResponseDTO {

	
	private Long idSocialVideo;

	private String videoTitle;

	private String videoDescription;

	private Integer videoDuration;
	
	private Long totalLikes;
	
	private Long totalDisLikes;
	
	private Boolean completionFlag;
	
	private String thumbnailLink;
	
	private Boolean likeFlag;
	
	private Boolean disLikeFlag;
	
	private List<VideoComment> comments;
	
	private List<SocialVideoResolution> videoLinks;
	


	
	

	/**
	 * 
	 */
	public SocialVideoResponseDTO() {
		
	}
	
	


	/**
	 * @param idSocialVideo
	 * @param videoTitle
	 * @param videoDescription
	 * @param videoDuration
	 * @param completionFlag
	 * @param thumbnailLink
	 */
	public SocialVideoResponseDTO(Long idSocialVideo, String videoTitle, String videoDescription, Integer videoDuration,
			Boolean completionFlag, String thumbnailLink) {
		super();
		this.idSocialVideo = idSocialVideo;
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoDuration = videoDuration;
		this.completionFlag = completionFlag;
		this.thumbnailLink = thumbnailLink;
	}


	




	/**
	 * @param idSocialVideo
	 * @param videoTitle
	 * @param videoDescription
	 * @param videoDuration
	 * @param totalLikes
	 * @param totalDisLikes
	 * @param completionFlag
	 * @param thumbnailLink
	 * @param likeFlag
	 * @param disLikeFlag
	 */
	public SocialVideoResponseDTO(Long idSocialVideo, String videoTitle, String videoDescription, Integer videoDuration,
			Long totalLikes, Long totalDisLikes, Boolean completionFlag, String thumbnailLink, Boolean likeFlag,
			Boolean disLikeFlag) {
		super();
		this.idSocialVideo = idSocialVideo;
		this.videoTitle = videoTitle;
		this.videoDescription = videoDescription;
		this.videoDuration = videoDuration;
		this.totalLikes = totalLikes;
		this.totalDisLikes = totalDisLikes;
		this.completionFlag = completionFlag;
		this.thumbnailLink = thumbnailLink;
		this.likeFlag = likeFlag;
		this.disLikeFlag = disLikeFlag;
	}




	/**
	 * @return the idSocialVideo
	 */
	public Long getIdSocialVideo() {
		return idSocialVideo;
	}

	/**
	 * @param idSocialVideo the idSocialVideo to set
	 */
	public void setIdSocialVideo(Long idSocialVideo) {
		this.idSocialVideo = idSocialVideo;
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
	 * @return the videoDuration
	 */
	public Integer getVideoDuration() {
		return videoDuration;
	}







	/**
	 * @param videoDuration the videoDuration to set
	 */
	public void setVideoDuration(Integer videoDuration) {
		this.videoDuration = videoDuration;
	}





	/**
	 * @return the totalLikes
	 */
	public Long getTotalLikes() {
		return totalLikes;
	}




	/**
	 * @param totalLikes the totalLikes to set
	 */
	public void setTotalLikes(Long totalLikes) {
		this.totalLikes = totalLikes;
	}




	/**
	 * @return the totalDisLikes
	 */
	public Long getTotalDisLikes() {
		return totalDisLikes;
	}




	/**
	 * @param totalDisLikes the totalDisLikes to set
	 */
	public void setTotalDisLikes(Long totalDisLikes) {
		this.totalDisLikes = totalDisLikes;
	}




	/**
	 * @return the completionFlag
	 */
	public Boolean isCompletionFlag() {
		return completionFlag;
	}

	/**
	 * @param completionFlag the completionFlag to set
	 */
	public void setCompletionFlag(Boolean completionFlag) {
		this.completionFlag = completionFlag;
	}

	/**
	 * @return the videoLinks
	 */
	public List<SocialVideoResolution> getVideoLinks() {
		return videoLinks;
	}

	/**
	 * @param videoLinks the videoLinks to set
	 */
	public void setVideoLinks(List<SocialVideoResolution> videoLinks) {
		this.videoLinks = videoLinks;
	}







	/**
	 * @return the thumbnailLink
	 */
	public String getThumbnailLink() {
		return thumbnailLink;
	}







	/**
	 * @param thumbnailLink the thumbnailLink to set
	 */
	public void setThumbnailLink(String thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}




	/**
	 * @return the likeFlag
	 */
	public Boolean getLikeFlag() {
		return likeFlag;
	}




	/**
	 * @param likeFlag the likeFlag to set
	 */
	public void setLikeFlag(Boolean likeFlag) {
		this.likeFlag = likeFlag;
	}




	/**
	 * @return the disLikeFlag
	 */
	public Boolean getDisLikeFlag() {
		return disLikeFlag;
	}




	/**
	 * @param disLikeFlag the disLikeFlag to set
	 */
	public void setDisLikeFlag(Boolean disLikeFlag) {
		this.disLikeFlag = disLikeFlag;
	}




	/**
	 * @return the comments
	 */
	public List<VideoComment> getComments() {
		return comments;
	}




	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<VideoComment> comments) {
		this.comments = comments;
	}







		
	
}
