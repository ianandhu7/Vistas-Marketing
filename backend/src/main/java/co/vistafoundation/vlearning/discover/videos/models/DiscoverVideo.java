package co.vistafoundation.vlearning.discover.videos.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Meghana, vk
 *
 */

@Entity
@Table(name = "DISCOVER_VIDEO")
public class DiscoverVideo extends UserDateAudit implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idDISCOVER_VIDEO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDiscoverVideo;
	
	@Column(name = "VIDEO_LINK", length= 500)
	private String videoLink;
	
	@Column(name = "LANGUAGE", length= 50)
	private String language;	

	@Column(name = "FEATURED_FLAG")
	private Boolean featuredFlag;
	
	@Column(name = "idDISCOVER_VIDEO_CATEGORY")
	private Long idDiscoverVideoCategory;
	
	@Column(name = "UPLOADED_DATE")
	private Date uploadedDate;
	
	@Column(name = "TOPIC", length = 150)
	private String topic;
	
	@Column(name = "VIDEO_OTP", length = 150)
	private String videoOtp;
	
	@Column(name = "VIDEO_DURATION")
	private int videoDuration;
	
	@Column(name = "POSTAR_LOC", length = 500)
	private String postarLoc;
	
	@Column(name = "VIDEO_THEME", length = 100)
	private String videoTheme;
	
	@Column(name = "VIDEO_DESCRIPTION", length = 1000)
	private String videoDescription;
	
	@Column(name ="TOTAL_VIEWS")
	private Long totalViews;

	/**
	 * 
	 */
	public DiscoverVideo() {
		
	}

	/**
	 * @param videoLink
	 * @param language
	 * @param featuredFlag
	 * @param idDiscoverVideoCategory
	 * @param uploadedDate
	 * @param topic
	 * @param videoOtp
	 * @param videoDuration
	 * @param postarLoc
	 * @param videoTheme
	 * @param videoDescription
	 */
	public DiscoverVideo(String videoLink, String language, Boolean featuredFlag, Long idDiscoverVideoCategory,
			Date uploadedDate, String topic, String videoOtp, int videoDuration, String postarLoc, String videoTheme, Long totalViews,
			String videoDescription) {
		super();
		this.videoLink = videoLink;
		this.language = language;
		this.featuredFlag = featuredFlag;
		this.idDiscoverVideoCategory = idDiscoverVideoCategory;
		this.uploadedDate = uploadedDate;
		this.topic = topic;
		this.videoOtp = videoOtp;
		this.videoDuration = videoDuration;
		this.postarLoc = postarLoc;
		this.videoTheme = videoTheme;
		this.videoDescription = videoDescription;
		this.totalViews = totalViews;
	}

	public Long getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(Long totalViews) {
		this.totalViews = totalViews;
	}

	/**
	 * @return the idDiscoverVideo
	 */
	public Long getIdDiscoverVideo() {
		return idDiscoverVideo;
	}

	/**
	 * @param idDiscoverVideo the idDiscoverVideo to set
	 */
	public void setIdDiscoverVideo(Long idDiscoverVideo) {
		this.idDiscoverVideo = idDiscoverVideo;
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
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the featuredFlag
	 */
	public Boolean getFeaturedFlag() {
		return featuredFlag;
	}

	/**
	 * @param featuredFlag the featuredFlag to set
	 */
	public void setFeaturedFlag(Boolean featuredFlag) {
		this.featuredFlag = featuredFlag;
	}

	/**
	 * @return the idDiscoverVideoCategory
	 */
	public Long getIdDiscoverVideoCategory() {
		return idDiscoverVideoCategory;
	}

	/**
	 * @param idDiscoverVideoCategory the idDiscoverVideoCategory to set
	 */
	public void setIdDiscoverVideoCategory(Long idDiscoverVideoCategory) {
		this.idDiscoverVideoCategory = idDiscoverVideoCategory;
	}

	/**
	 * @return the uploadedDate
	 */
	public Date getUploadedDate() {
		return uploadedDate;
	}

	/**
	 * @param uploadedDate the uploadedDate to set
	 */
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the videoOtp
	 */
	public String getVideoOtp() {
		return videoOtp;
	}

	/**
	 * @param videoOtp the videoOtp to set
	 */
	public void setVideoOtp(String videoOtp) {
		this.videoOtp = videoOtp;
	}

	/**
	 * @return the videoDuration
	 */
	public int getVideoDuration() {
		return videoDuration;
	}

	/**
	 * @param videoDuration the videoDuration to set
	 */
	public void setVideoDuration(int videoDuration) {
		this.videoDuration = videoDuration;
	}

	/**
	 * @return the postarLoc
	 */
	public String getPostarLoc() {
		return postarLoc;
	}

	/**
	 * @param postarLoc the postarLoc to set
	 */
	public void setPostarLoc(String postarLoc) {
		this.postarLoc = postarLoc;
	}

	/**
	 * @return the videoTheme
	 */
	public String getVideoTheme() {
		return videoTheme;
	}

	/**
	 * @param videoTheme the videoTheme to set
	 */
	public void setVideoTheme(String videoTheme) {
		this.videoTheme = videoTheme;
	}

	/**
	 * @return the videoDescription
	 */
	public String getVideoDescription() {
		return videoDescription;
	}

	/**
	 * @param videoDescription the videoDescription to set
	 */
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	
}
