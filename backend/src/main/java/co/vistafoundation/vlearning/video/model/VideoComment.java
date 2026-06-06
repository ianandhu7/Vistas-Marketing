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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "VIDEO_COMMENT")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "updatedAt" })
public class VideoComment extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVIDEO_COMMENT", nullable = false)
	private Long idVideoComment;

	@NotNull
	@Column(name = "COMMENT", length = 500)
	private String comment;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idSOCIAL_VIDEO", referencedColumnName = "idSOCIAL_VIDEO", nullable = false)
	private SocialVideo socialVideo;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
			"classStandard", "username", "email", "mobileNumber", "secondaryLanguage" })
	@ManyToOne
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId", nullable = false)
	private User user;
	
	@Column(name = "IS_VISIBLE", nullable = false,columnDefinition = "boolean default false")
	private boolean isVisible;

	/**
	 * @return the idVideoComment
	 */
	public Long getIdVideoComment() {
		return idVideoComment;
	}

	
	
	/**
	 * @param idVideoComment
	 * @param comment
	 * @param socialVideo
	 * @param user
	 * @param isVisible
	 */
	public VideoComment(Long idVideoComment, @NotNull String comment, SocialVideo socialVideo, User user,
			boolean isVisible) {
		super();
		this.idVideoComment = idVideoComment;
		this.comment = comment;
		this.socialVideo = socialVideo;
		this.user = user;
		this.isVisible = isVisible;
	}



	/**
	 * @param idVideoComment the idVideoComment to set
	 */
	public void setIdVideoComment(Long idVideoComment) {
		this.idVideoComment = idVideoComment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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

	/**
	 * 
	 */
	public VideoComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param comment
	 * @param socialVideo
	 * @param user
	 */
	public VideoComment(@NotNull String comment, SocialVideo socialVideo, User user) {
		super();
		this.comment = comment;
		this.socialVideo = socialVideo;
		this.user = user;
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
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	
}
