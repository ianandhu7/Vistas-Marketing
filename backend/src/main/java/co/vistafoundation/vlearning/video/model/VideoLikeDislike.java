/**
 * 
 */
package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "VIDEO_LIKE_DISLIKE" ,uniqueConstraints= @UniqueConstraint(columnNames={"idSOCIAL_VIDEO", "idVL_USER"}))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class VideoLikeDislike extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVIDEO_LIKE_DISLIKE", nullable = false)
	private Long idVideoLikeDisLike;
    
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idSOCIAL_VIDEO", referencedColumnName = "idSOCIAL_VIDEO", nullable = false)
	private SocialVideo socialVideo;

	@Column(name = "LIKE_FLAG", nullable = false)
	private boolean likeFlag;
	
	@Column(name = "DISLIKE_FLAG", nullable = false)
	private boolean disLikeFlag;

	@Column(name = "idVL_USER",nullable = false)
	private Long idVlUser;

	
	
	/**
	 * @return the idVideoLikeDisLike
	 */
	public Long getIdVideoLikeDisLike() {
		return idVideoLikeDisLike;
	}

	/**
	 * @param idVideoLikeDisLike the idVideoLikeDisLike to set
	 */
	public void setIdVideoLikeDisLike(Long idVideoLikeDisLike) {
		this.idVideoLikeDisLike = idVideoLikeDisLike;
	}

	/**
	 * @return the socialVideo
	 */
	public SocialVideo getSocialVideo() {
		return socialVideo;
	}

	/**
	 * @param socialVideo the socialVideo to set
	 */
	public void setSocialVideo(SocialVideo socialVideo) {
		this.socialVideo = socialVideo;
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
	 * @param socialVideo
	 * @param likeFlag
	 * @param idVlUser
	 */
	

	
	public VideoLikeDislike() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param socialVideo
	 * @param likeFlag
	 * @param disLikeFlag
	 * @param idVlUser
	 */
	public VideoLikeDislike(SocialVideo socialVideo, boolean likeFlag, boolean disLikeFlag, Long idVlUser) {
		super();
		this.socialVideo = socialVideo;
		this.likeFlag = likeFlag;
		this.disLikeFlag = disLikeFlag;
		this.idVlUser = idVlUser;
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
	
	

}
