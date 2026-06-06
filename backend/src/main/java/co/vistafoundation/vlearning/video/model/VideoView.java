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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "VIDEO_VIEW", uniqueConstraints = @UniqueConstraint(columnNames = { "idSOCIAL_VIDEO", "idVL_USER" }))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class VideoView extends UserDateAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVIDEO_VIEW", nullable = false)
	private Long idVideoView;

	@ManyToOne
	@JoinColumn(name = "idSOCIAL_VIDEO", referencedColumnName = "idSOCIAL_VIDEO", nullable = false)
	private SocialVideo socialVideo;

	@Column(name = "idVL_USER", nullable = false)
	private Long idVlUser;
	
	@Column(name = "COMPLETE_FLAG")
	private Boolean completeFlag;

	
	/**
	 * 
	 */
	public VideoView() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * @return the idVideoView
	 */
	public Long getIdVideoView() {
		return idVideoView;
	}

	/**
	 * @param idVideoView the idVideoView to set
	 */
	public void setIdVideoView(Long idVideoView) {
		this.idVideoView = idVideoView;
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
	 * @param idVlUser
	 */
	public VideoView(SocialVideo socialVideo, Long idVlUser) {
		super();
		this.socialVideo = socialVideo;
		this.idVlUser = idVlUser;
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
