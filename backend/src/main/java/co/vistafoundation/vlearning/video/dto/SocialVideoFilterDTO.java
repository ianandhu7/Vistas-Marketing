package co.vistafoundation.vlearning.video.dto;

import java.util.Date;

import co.vistafoundation.vlearning.video.model.VideoCategory;

public class SocialVideoFilterDTO {

	private Long idVlUser;
	private VideoCategory videoCategory;;
	private Date from;
	private Date to;
	private Boolean activeFlag;
	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}
	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
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
	 * @return the from
	 */
	public Date getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(Date from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public Date getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(Date to) {
		this.to = to;
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
	
	
	
}
