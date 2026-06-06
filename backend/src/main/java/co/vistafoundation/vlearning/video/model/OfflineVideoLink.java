package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

//deprecated 27-NOV-2020
@Table(name = "OFFLINE_VIDEO_LINK")
public class OfflineVideoLink extends UserDateAudit implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idOFFLINE_VIDEO_LINK")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idOfflineVideoLink;
	
	@Column(name = "idOFFLINE_VIDEO_COURSE",nullable=false)
	private Long idOfflineVideoCourse;
   
	
	@Column(name = "VIDEO_LINK", length = 200)
	private String videoLink;
    
	@Column(name = "LANGUAGE", length = 45)
	private String language;
	
	@Column(name = "idLANGUAGE",nullable=false)
	private Long idLANGUAGE;

	public Long getIdOfflineVideoLink() {
		return idOfflineVideoLink;
	}

	public void setIdOfflineVideoLink(Long idOfflineVideoLink) {
		this.idOfflineVideoLink = idOfflineVideoLink;
	}

	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
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

	public Long getIdLANGUAGE() {
		return idLANGUAGE;
	}

	public void setIdLANGUAGE(Long idLANGUAGE) {
		this.idLANGUAGE = idLANGUAGE;
	}

	public OfflineVideoLink(Long idOfflineVideoCourse, String videoLink, String language, Long idLANGUAGE) {
		super();
		this.idOfflineVideoCourse = idOfflineVideoCourse;
		this.videoLink = videoLink;
		this.language = language;
		this.idLANGUAGE = idLANGUAGE;
	}
	
	public OfflineVideoLink() {}
	
	
}
