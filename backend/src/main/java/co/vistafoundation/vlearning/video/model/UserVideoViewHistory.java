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
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "USER_VIDEO_VIEW_HISTORY", uniqueConstraints = @UniqueConstraint(columnNames = { "idSOCIAL_VIDEO",
		"idVL_USER" }))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class UserVideoViewHistory extends UserDateAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUSER_VIDEO_VIEW_HISTORY", nullable = false)
	private Long idUseVideoViewHistory;

	@Max(value = 500)
	@Column(name = "WATCHED_DURATION", nullable = false)
	private int watchedDuration;

	@Column(name = "WATCHED_PERCENTAGE", length = 5, nullable = false)
	private String watchedPercentage;

	@ManyToOne
	@JoinColumn(name = "idSOCIAL_VIDEO", referencedColumnName = "idSOCIAL_VIDEO", nullable = false)
	private SocialVideo socialVideo;

	@Column(name = "idVL_USER", nullable = false)
	private Long idVlUser;

	/**
	 * @return the idUseVideoViewHistory
	 */
	public Long getIdUseVideoViewHistory() {
		return idUseVideoViewHistory;
	}

	/**
	 * @param idUseVideoViewHistory the idUseVideoViewHistory to set
	 */
	public void setIdUseVideoViewHistory(Long idUseVideoViewHistory) {
		this.idUseVideoViewHistory = idUseVideoViewHistory;
	}

	/**
	 * @return the watchedDuration
	 */
	public int getWatchedDuration() {
		return watchedDuration;
	}

	/**
	 * @param watchedDuration the watchedDuration to set
	 */
	public void setWatchedDuration(int watchedDuration) {
		this.watchedDuration = watchedDuration;
	}

	/**
	 * @return the watchedPercentage
	 */
	public String getWatchedPercentage() {
		return watchedPercentage;
	}

	/**
	 * @param watchedPercentage the watchedPercentage to set
	 */
	public void setWatchedPercentage(String watchedPercentage) {
		this.watchedPercentage = watchedPercentage;
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
	 * 
	 */
	public UserVideoViewHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param watchedDuration
	 * @param watchedPercentage
	 * @param socialVideo
	 * @param idVlUser
	 */
	public UserVideoViewHistory(@Max(500) int watchedDuration, String watchedPercentage, SocialVideo socialVideo,
			Long idVlUser) {
		super();
		this.watchedDuration = watchedDuration;
		this.watchedPercentage = watchedPercentage;
		this.socialVideo = socialVideo;
		this.idVlUser = idVlUser;
	}

}
