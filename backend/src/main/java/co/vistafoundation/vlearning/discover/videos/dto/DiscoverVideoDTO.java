package co.vistafoundation.vlearning.discover.videos.dto;

public class DiscoverVideoDTO {

	private Long idDiscoverVideo;
	private String videoLink;
	private String language;		
	private String topic;
	private String description;
	private String videoOtp;
	private int videoDuration;
	private String postarLoc;
	private String videoTheme;
	
	public Long getIdDiscoverVideo() {
		return idDiscoverVideo;
	}
	public void setIdDiscoverVideo(Long idDiscoverVideo) {
		this.idDiscoverVideo = idDiscoverVideo;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideoOtp() {
		return videoOtp;
	}
	public void setVideoOtp(String videoOtp) {
		this.videoOtp = videoOtp;
	}
	public int getVideoDuration() {
		return videoDuration;
	}
	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}
	public String getPostarLoc() {
		return postarLoc;
	}
	public void setPostarLoc(String postarLoc) {
		this.postarLoc = postarLoc;
	}
	public String getVideoTheme() {
		return videoTheme;
	}
	public void setVideoTheme(String videoTheme) {
		this.videoTheme = videoTheme;
	}
}
