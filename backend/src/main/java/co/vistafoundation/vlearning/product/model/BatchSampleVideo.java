package co.vistafoundation.vlearning.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Meghana
 * updated  by @author Naveen Kumar 
 **/
@Entity
@Table(name = "BATCH_SAMPLE_VIDEO")
public class BatchSampleVideo extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBATCH_SAMPLE_VIDEO", nullable = false)
	private Long idBatchSampleVideo;

	@Column(name = "idBATCH", nullable = false)
	private Long idBatch;

	@Column(name = "VIDEO_LINK", length = 500, nullable = false)
	private String videoLink;

	@Column(name = "idSUBJECT")
	private Long idSubject;

	@Column(name = "video_description", length = 1000)
	private String videoDescription;

	public Long getIdBatchSampleVideo() {
		return idBatchSampleVideo;
	}

	public void setIdBatchSampleVideo(Long idBatchSampleVideo) {
		this.idBatchSampleVideo = idBatchSampleVideo;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public BatchSampleVideo(Long idBatch, String videoLink, Long idSubject, String videoDescription) {
		super();
		this.idBatch = idBatch;
		this.videoLink = videoLink;
		this.idSubject = idSubject;
		this.videoDescription = videoDescription;
	}

	/**
	 * 
	 */
	public BatchSampleVideo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
