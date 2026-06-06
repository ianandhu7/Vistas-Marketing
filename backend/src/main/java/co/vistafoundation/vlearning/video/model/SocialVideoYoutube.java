package co.vistafoundation.vlearning.video.model;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "social_video_youtube")
public class SocialVideoYoutube implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="video_description")
	private String videoDescription;
	@Column(name="social_video_file")
	private File socialVideoFile;

	public SocialVideoYoutube(Long id, String videoDescription, File socialVideoFile) {
		super();
		this.id = id;
		this.videoDescription = videoDescription;
		this.socialVideoFile = socialVideoFile;
	}

	public SocialVideoYoutube() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public File getSocialVideoFile() {
		return socialVideoFile;
	}

	public void setSocialVideoFile(File socialVideoFile) {
		this.socialVideoFile = socialVideoFile;
	}

}
