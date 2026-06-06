package co.vistafoundation.vlearning.video.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.video.model.SocialVideoResolution;
import co.vistafoundation.vlearning.video.model.VideoCategory;

public class SocialVideoDTO {

	private Long idSocialVideo;

	private String videoTitle;

	private String videoLink;
	
	private String thumbnailLink;

	private String videoDescription;
	
	private Instant createdAt;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
		"classStandard","username","email","mobileNumber","secondaryLanguage","activeFlag","maxAttempts"})
	private User user;

	private VideoCategory videoCategory;

	private int videoRating;

	private Long totalLikes;

	private Long totalDisLikes;

	private Long totalComments;

	private Long totalViews;

	private int videoDuration;

	private boolean ageRestrictionFlag;

	private Long idLocation;

	private boolean activeFlag;

	private boolean playingFlag;

	private Long approvedModeratorUserId;
	
	private boolean likeFlag =false;
	
	private boolean disLikeFlag = false;
	
	private List<SocialVideoResolution> idSocialVideoResolution;
	
	 
	 
	 

	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the idSocialVideoResolution
	 */
	public List<SocialVideoResolution> getIdSocialVideoResolution() {
		return idSocialVideoResolution;
	}

	/**
	 * @param idSocialVideoResolution the idSocialVideoResolution to set
	 */
	public void setIdSocialVideoResolution(List<SocialVideoResolution> idSocialVideoResolution) {
		this.idSocialVideoResolution = idSocialVideoResolution;
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
	 * @return the videoLink
	 */
	public String getVideoLink() {
		return videoLink;
	}

	/**
	 * @param videoLink the videoLink to set
	 */
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the videoCategory
	 */
	public VideoCategory getVideoCategory() {
		return videoCategory;
	}

	/**
	 * @param videoCategory the videoCategory to set
	 */
	public void setVideoCategory(VideoCategory videoCategory) {
		this.videoCategory = videoCategory;
	}

	/**
	 * @return the videoRating
	 */
	public int getVideoRating() {
		return videoRating;
	}

	/**
	 * @param videoRating the videoRating to set
	 */
	public void setVideoRating(int videoRating) {
		this.videoRating = videoRating;
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
	 * @return the totalComments
	 */
	public Long getTotalComments() {
		return totalComments;
	}

	/**
	 * @param totalComments the totalComments to set
	 */
	public void setTotalComments(Long totalComments) {
		this.totalComments = totalComments;
	}

	/**
	 * @return the totalViews
	 */
	public Long getTotalViews() {
		return totalViews;
	}

	/**
	 * @param totalViews the totalViews to set
	 */
	public void setTotalViews(Long totalViews) {
		this.totalViews = totalViews;
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
	 * @return the ageRestrictionFlag
	 */
	public boolean isAgeRestrictionFlag() {
		return ageRestrictionFlag;
	}

	/**
	 * @param ageRestrictionFlag the ageRestrictionFlag to set
	 */
	public void setAgeRestrictionFlag(boolean ageRestrictionFlag) {
		this.ageRestrictionFlag = ageRestrictionFlag;
	}

	/**
	 * @return the idLocation
	 */
	public Long getIdLocation() {
		return idLocation;
	}

	/**
	 * @param idLocation the idLocation to set
	 */
	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	/**
	 * @return the activeFlag
	 */
	public boolean isActiveFlag() {		
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the playingFlag
	 */
	public boolean isPlayingFlag() {
		return playingFlag;
	}

	/**
	 * @param playingFlag the playingFlag to set
	 */
	public void setPlayingFlag(boolean playingFlag) {
		this.playingFlag = playingFlag;
	}

	/**
	 * @return the approvedModeratorUserId
	 */
	public Long getApprovedModeratorUserId() {
		return approvedModeratorUserId;
	}

	/**
	 * @param approvedModeratorUserId the approvedModeratorUserId to set
	 */
	public void setApprovedModeratorUserId(Long approvedModeratorUserId) {
		this.approvedModeratorUserId = approvedModeratorUserId;
	}

	/**
	 * @return the likeFlag
	 */
	public boolean isLikeFlag() {
		return likeFlag;
	}

	/**
	 * @param likeFlag the likeFlag to set
	 */
	public void setLikeFlag(boolean likeFlag) {
		this.likeFlag = likeFlag;
	}

	/**
	 * @return the disLikeFlag
	 */
	public boolean isDisLikeFlag() {
		return disLikeFlag;
	}

	/**
	 * @param disLikeFlag the disLikeFlag to set
	 */
	public void setDisLikeFlag(boolean disLikeFlag) {
		this.disLikeFlag = disLikeFlag;
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
	
	
	

}
