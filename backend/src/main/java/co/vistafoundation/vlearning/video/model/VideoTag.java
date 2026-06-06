package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * 
 * @author NaveenKumar
 *
 */

@Entity
@Table(name = "VIDEO_TAG", uniqueConstraints = @UniqueConstraint(columnNames = { "idSOCIAL_VIDEO", "idTAG_LIST" }))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class VideoTag extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idVIDEO_TAG", nullable = false)
	private Long idVideoTag;

	@Column(name = "idSOCIAL_VIDEO", nullable = false)
	private Long idSocialVideo;

	@Column(name = "idTAG_LIST", nullable = false)
	private Long idTagList;

	/**
	 * @return the idVideoTag
	 */
	public Long getIdVideoTag() {
		return idVideoTag;
	}

	/**
	 * @param idVideoTag the idVideoTag to set
	 */
	public void setIdVideoTag(Long idVideoTag) {
		this.idVideoTag = idVideoTag;
	}

	/**
	 * @return the idSocialVideo
	 */
	public Long getIdSocialVideo() {
		return idSocialVideo;
	}

	/**
	 * @param idSocialVideo the idSocialVideo to set
	 */
	public void setIdSocialVideo(Long idSocialVideo) {
		this.idSocialVideo = idSocialVideo;
	}

	/**
	 * @return the idTagList
	 */
	public Long getIdTagList() {
		return idTagList;
	}

	/**
	 * @param idTagList the idTagList to set
	 */
	public void setIdTagList(Long idTagList) {
		this.idTagList = idTagList;
	}

	/**
	 * @param idSocialVideo
	 * @param idTagList
	 */
	public VideoTag(Long idSocialVideo, Long idTagList) {
		super();
		this.idSocialVideo = idSocialVideo;
		this.idTagList = idTagList;
	}

	/**
	 * 
	 */
	public VideoTag() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
