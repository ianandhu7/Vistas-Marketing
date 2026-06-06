package co.vistafoundation.vlearning.video.dto;

import java.io.Serializable;
import java.util.Date;

public class CommentFilterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSocialVideo;
	
	private Boolean isVisible;
	
	private Long idVlUser;
	
	private Date from;
	
	private Date to;

	
	/**
	 * 
	 */
	public CommentFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param idSocialVideo
	 * @param isVisible
	 * @param idVlUser
	 * @param from
	 * @param to
	 */
	public CommentFilterDTO(Long idSocialVideo, Boolean isVisible, Long idVlUser, Date from, Date to) {
		super();
		this.idSocialVideo = idSocialVideo;
		this.isVisible = isVisible;
		this.idVlUser = idVlUser;
		this.from = from;
		this.to = to;
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
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

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
	
	
}
