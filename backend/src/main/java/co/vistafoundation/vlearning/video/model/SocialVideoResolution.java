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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SOCIAL_VIDEO_RESOLUTION")
public class SocialVideoResolution implements Serializable{

	
	private static final long serialVersionUID = 5210529453366547804L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDSOCIAL_VIDEO_RESOLUTION")
	private Long idSocialVideoResolution;

	@Column(name = "VIDEO_LINK", length = 500)
	private String videoLink;
	
	@Column(name = "RESOLUTION")
	private int resolution;
//	
//	@Column(name = "IDSOCIAL_VIDEO", nullable = false)
//	private Long idSocialVideo;
//	
	 @JsonBackReference
	 @ManyToOne
	 @JoinColumn(name="IDSOCIAL_VIDEO")
	 private SocialVideo socialVideo;

	
	public SocialVideoResolution() {
		super();
		
	}


	/**
	 * @param idSocialVideoResolution
	 * @param videoLink
	 * @param resolution
	 * @param idSocialVideo
	 */
//	public SocialVideoResolution(Long idSocialVideoResolution, String videoLink, int seqNumber, Long idSocialVideo) {
//		super();
//		this.idSocialVideoResolution = idSocialVideoResolution;
//		this.videoLink = videoLink;
//		this.seqNumber = seqNumber;
//		this.idSocialVideo = idSocialVideo;
//	}
	



	


	/**
	 * @return the idSocialVideoResolution
	 */
	public Long getIdSocialVideoResolution() {
		return idSocialVideoResolution;
	}



	/**
	 * @param videoLink
	 * @param resolution
	 * @param socialVideo
	 */
	public SocialVideoResolution(String videoLink, int resolution, SocialVideo socialVideo) {
		super();
		this.videoLink = videoLink;
		this.resolution = resolution;
		this.socialVideo = socialVideo;
	}


	/**
	 * @param idSocialVideoResolution the idSocialVideoResolution to set
	 */
	public void setIdSocialVideoResolution(Long idSocialVideoResolution) {
		this.idSocialVideoResolution = idSocialVideoResolution;
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
	 * @return the resolution
	 */
	public int getResolution() {
		return resolution;
	}


	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
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

	@Override
	public String toString() {
		return "SocialVideoResolution [idSocialVideoResolution=" + idSocialVideoResolution + ", videoLink=" + videoLink
				+ ", resolution=" + resolution + ", socialVideo=" + socialVideo + "]";
	}
	
}
