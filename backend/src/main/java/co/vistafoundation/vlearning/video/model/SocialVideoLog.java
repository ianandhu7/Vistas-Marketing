package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

@Entity
@Table(name = "SOCIAL_VIDEO_LOG")
public class SocialVideoLog extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = -1236990305780720083L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDSOCIAL_VIDEO_LOG")
	private Long idSocialVideoLog;

	@Column(name = "IDSOCIAL_VIDEO", nullable = false)
	private Long idSocialVideo;

	@Column(name = "IDVL_USER", nullable = false)
	private Long idVLUser;

	@Column(name = "COMPLETE_FLAG")
	private Boolean completeFlag;

	/**
	 * 
	 */
	public SocialVideoLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idSocialVideoLog
	 * @param createdAt
	 * @param updatedAt
	 * @param createdBy
	 * @param updatedBy
	 * @param idSocialVideo
	 * @param videoDescription
	 * @param videoLink
	 * @param videoTitle
	 * @param idVLUser
	 * @param idVideoCategory
	 * @param videoCoverageDuration
	 * @param videoDuration
	 * @param completeFlag
	 */
	public SocialVideoLog(Long idSocialVideoLog, Long idSocialVideo, Long idVLUser, Boolean completeFlag) {
		super();
		this.idSocialVideoLog = idSocialVideoLog;
		this.idSocialVideo = idSocialVideo;
		this.idVLUser = idVLUser;
		this.completeFlag = completeFlag;
	}

	/**
	 * @return the idSocialVideoLog
	 */
	public Long getIdSocialVideoLog() {
		return idSocialVideoLog;
	}

	/**
	 * @param idSocialVideoLog the idSocialVideoLog to set
	 */
	public void setIdSocialVideoLog(Long idSocialVideoLog) {
		this.idSocialVideoLog = idSocialVideoLog;
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
	 * @return the idVLUser
	 */
	public Long getIdVLUser() {
		return idVLUser;
	}

	/**
	 * @param idVLUser the idVLUser to set
	 */
	public void setIdVLUser(Long idVLUser) {
		this.idVLUser = idVLUser;
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

}
