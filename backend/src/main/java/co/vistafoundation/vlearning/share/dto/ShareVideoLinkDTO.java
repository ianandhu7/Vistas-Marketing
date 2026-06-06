package co.vistafoundation.vlearning.share.dto;

import javax.validation.constraints.NotNull;

public class ShareVideoLinkDTO {

	@NotNull
	Long idVideo;
	
	@NotNull
	String videoType;
	
	@NotNull
	String contentType;

	public ShareVideoLinkDTO(@NotNull Long idVideo, @NotNull String videoType, @NotNull String contentType) {
		super();
		this.idVideo = idVideo;
		this.videoType = videoType;
		this.contentType = contentType;
	}

	public ShareVideoLinkDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(Long idVideo) {
		this.idVideo = idVideo;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
