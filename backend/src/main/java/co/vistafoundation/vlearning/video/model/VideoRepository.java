package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "VIDEO_REPOSITORY")
public class VideoRepository  extends UserDateAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VIDEO_REPO_ID", nullable = false)
	private Long videoRepoId;
	
	@Column(name = "VIDEO_NAME", length = 45)
	private String videoName;
	
	@Column(name = "VIDEO_TAG", length = 45)
	private String videoTag;
	
	@Column(name = "VIDEO_TOPIC", length = 45)
	private String videoTopic;
	
	@Column(name = "DURATION", length = 45)
	private String duration;
	
	@Column(name = "VIDEO_LOCATION", length = 45)
	private String videoLocation;

	public Long getVideoRepoId() {
		return videoRepoId;
	}

	public void setVideoRepoId(Long videoRepoId) {
		this.videoRepoId = videoRepoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoTag() {
		return videoTag;
	}

	public void setVideoTag(String videoTag) {
		this.videoTag = videoTag;
	}

	public String getVideoTopic() {
		return videoTopic;
	}

	public void setVideoTopic(String videoTopic) {
		this.videoTopic = videoTopic;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

	public VideoRepository(String videoName, String videoTag, String videoTopic, String duration,
			String videoLocation) {
		super();
		this.videoName = videoName;
		this.videoTag = videoTag;
		this.videoTopic = videoTopic;
		this.duration = duration;
		this.videoLocation = videoLocation;
	}
	
	public VideoRepository() {}
	
	

}
