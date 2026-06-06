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
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "SOCIAL_VIDEO_RATING", uniqueConstraints = @UniqueConstraint(columnNames = { "idSOCIAL_VIDEO",
		"idVL_USER" }))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class SocialVideoRating extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSOCIAL_VIDEO_RATING", nullable = false)
	private Long idSocialVideoRating;

	@Min(value = 0)
	@Max(value = 5)
	@Column(name = "RATING", nullable = false)
	private byte rating;

	@ManyToOne
	@JoinColumn(name = "idSOCIAL_VIDEO", referencedColumnName = "idSOCIAL_VIDEO", nullable = false)
	private SocialVideo socialVideo;

	@Column(name = "idVL_USER", nullable = false)
	private Long idVlUser;

	/**
	 * @return the idSocialVideoRating
	 */
	public Long getIdSocialVideoRating() {
		return idSocialVideoRating;
	}

	/**
	 * @param idSocialVideoRating the idSocialVideoRating to set
	 */
	public void setIdSocialVideoRating(Long idSocialVideoRating) {
		this.idSocialVideoRating = idSocialVideoRating;
	}

	/**
	 * @return the rating
	 */
	public byte getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(byte rating) {
		this.rating = rating;
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
	 * @param rating
	 * @param socialVideo
	 * @param idVlUser
	 */
	public SocialVideoRating(@Min(0) @Max(5) byte rating, SocialVideo socialVideo, Long idVlUser) {
		super();
		this.rating = rating;
		this.socialVideo = socialVideo;
		this.idVlUser = idVlUser;
	}

	/**
	 * 
	 */
	public SocialVideoRating() {
		super();
		// TODO Auto-generated constructor stub
	}

}
