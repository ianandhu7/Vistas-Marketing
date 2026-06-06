package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * 
 * @author NaveenKumar
 *
 */
@Entity
@Table(name = "SOCIAL_VIDEO")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "updatedAt" })
public class SocialVideo extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSOCIAL_VIDEO", nullable = false)
	private Long idSocialVideo;

	@Column(name = "VIDEO_TITLE", length = 100)
	private String videoTitle;

	@Column(name = "VIDEO_LINK", length = 500)
	private String videoLink;
	

	@Column(name = "THUMBNAIL_LINK", length = 150)
	private String thumbnailLink;

	@Column(name = "VIDEO_DESCRIPTION", length = 500)
	private String videoDescription;
    
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
	"classStandard","username","email","mobileNumber","secondaryLanguage"})
	@ManyToOne
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId", nullable = false)
	private User user;
    
	
	@ManyToOne
	@JoinColumn(name = "idVIDEO_CATEGORY", referencedColumnName = "idVIDEO_CATEGORY", nullable = false)
	private VideoCategory videoCategory;

	@Min(value = 0)
	@Max(value = 5)
	@Column(name = "VIDEO_RATING", nullable = false)
	private int videoRating;

	@Min(value = 0)
	@Column(name = "TOTAL_LIKES", nullable = false)
	private Long totalLikes;

	@Min(value = 0)
	@Column(name = "TOTAL_DISLIKES", nullable = false)
	private Long totalDisLikes;

	@Min(value = 0)
	@Column(name = "TOTAL_COMMENTS", nullable = false)
	private Long totalComments;

	@Min(value = 0)
	@Column(name = "TOTAL_VIEWS", nullable = false)
	private Long totalViews;

	@Max(value = 500)
	@Column(name = "VIDEO_DURATION", nullable = false)
	private int videoDuration;

	@Column(name = "AGE_RESTRICTION_FLAG", nullable = false)
	private boolean ageRestrictionFlag;

	@Column(name = "idLOCATION")
	private Long idLocation;
	
	@Column(name = "ACTIVE_FLAG", nullable = false,columnDefinition = "boolean default true")
	private boolean activeFlag;

	@Column(name = "PLAYING_FLAG", nullable = false,columnDefinition = "boolean default false")
	private boolean playingFlag;
	
	
	@Column(name = "APPROVED_MODERATOR_USER_ID")
	private Long approvedModeratorUserId;
	
	@JsonManagedReference
	@OneToMany(fetch=FetchType.LAZY, mappedBy="socialVideo",cascade=CascadeType.PERSIST)
	private List<SocialVideoResolution> idSocialVideoResolution;

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
	public long getIdLocation() {
		return idLocation;
	}

	/**
	 * @param idLocation the idLocation to set
	 */
	public void setIdLocation(long idLocation) {
		this.idLocation = idLocation;
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
	 * @param videoTitle
	 * @param videoLink
	 * @param videoDescription
	 * @param user
	 * @param videoCategory
	 * @param videoRating
	 * @param totalLikes
	 * @param totalDisLikes
	 * @param totalComments
	 * @param totalViews
	 * @param videoDuration
	 * @param ageRestrictionFlag
	 * @param idLocation
	 */
	public SocialVideo(String videoTitle, String videoLink,String thumbnailLink, String videoDescription, User user,
			VideoCategory videoCategory, @Min(0) @Max(5) int videoRating, @Min(0) Long totalLikes,
			@Min(0) Long totalDisLikes, @Min(0) Long totalComments, @Min(0) Long totalViews,
			@Max(500) int videoDuration, boolean ageRestrictionFlag, long idLocation) {
		super();
		this.videoTitle = videoTitle;
		this.videoLink = videoLink;
		this.thumbnailLink=thumbnailLink;
		this.videoDescription = videoDescription;
		this.user = user;
		this.videoCategory = videoCategory;
		this.videoRating = videoRating;
		this.totalLikes = totalLikes;
		this.totalDisLikes = totalDisLikes;
		this.totalComments = totalComments;
		this.totalViews = totalViews;
		this.videoDuration = videoDuration;
		this.ageRestrictionFlag = ageRestrictionFlag;
		this.idLocation = idLocation;
	}

	/**
	 * 
	 */
	public SocialVideo() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @param idLocation the idLocation to set
	 */
	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	/**
	 * @return the approvedModeratorUserId
	 */
	public Long getApprovedModeratorUserId() {
		return approvedModeratorUserId;
	}

	/**
	 * @param approvedModeratorUserId the approvedModeratorUserId to setR
	 */
	public void setApprovedModeratorUserId(Long approvedModeratorUserId) {
		this.approvedModeratorUserId = approvedModeratorUserId;
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
	
	


}
