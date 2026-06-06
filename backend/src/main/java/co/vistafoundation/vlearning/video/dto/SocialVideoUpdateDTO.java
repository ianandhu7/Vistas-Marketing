package co.vistafoundation.vlearning.video.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.auth.model.User;

public class SocialVideoUpdateDTO {
	
	@NotNull
	private Long idSocialVideo;
	
	public Long getIdSocialVideo() {
		return idSocialVideo;
	}

	public void setIdSocialVideo(Long idSocialVideo) {
		this.idSocialVideo = idSocialVideo;
	}

	@NotNull
	private String videoTitle;

	@Size(max = 1000)
	private String videoDescription;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
		"classStandard","username","email","mobileNumber","secondaryLanguage","activeFlag","maxAttempts"})
	private User user;

	private Long idVideoCategory;

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getIdVideoCategory() {
		return idVideoCategory;
	}

	public void setVideoCategory(Long idVideoCategory) {
		this.idVideoCategory = idVideoCategory;
	}
	
	
}
