package co.vistafoundation.vlearning.video.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import co.vistafoundation.vlearning.video.model.Location;

/**
 * 
 * @author NaveenKumar
 *
 */
public class SocialVideoUploadDTO {

	@NotNull
	private String videoTitle;

	@Size(max = 1000)
	private String videoDescription;

	@NotNull
	@Size(max = 500)
	private String videoLink;

	private Long idVideoCategory;

	private List<String> videoTags;
	
	private boolean ageRestrictionFlag;
	
	@NotNull
	private Location location ;


	

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@NotNull
    private int duration;
	

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
	 * @return the idVideoCategory
	 */
	public Long getIdVideoCategory() {
		return idVideoCategory;
	}

	/**
	 * @param idVideoCategory the idVideoCategory to set
	 */
	public void setIdVideoCategory(Long idVideoCategory) {
		this.idVideoCategory = idVideoCategory;
	}

	/**
	 * @return the videoTags
	 */
	public List<String> getVideoTags() {
		return videoTags;
	}

	/**
	 * @param videoTags the videoTags to set
	 */
	public void setVideoTags(List<String> videoTags) {
		this.videoTags = videoTags;
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
	 * @return the ageRestrictionFlag
	 */
	public boolean getAgeRestrictionFlag() {
		return ageRestrictionFlag;
	}

	/**
	 * @param ageRestrictionFlag the ageRestrictionFlag to set
	 */
	public void setAgeRestrictionFlag(boolean ageRestrictionFlag) {
		this.ageRestrictionFlag = ageRestrictionFlag;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	

}
