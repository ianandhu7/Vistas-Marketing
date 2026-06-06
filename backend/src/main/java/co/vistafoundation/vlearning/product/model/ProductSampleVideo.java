package co.vistafoundation.vlearning.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Meghana
 * 
 **/

@Entity
@Table(name = "PRODUCT_SAMPLE_VIDEO")
public class ProductSampleVideo extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT_SAMPLE_VIDEO", nullable = false)
	private Long idProductSampleVideo;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPRODUCT", nullable = false, referencedColumnName = "idPRODUCT")
	private Product product;

	@Column(name = "VIDEO_LINK")
	private String videoLink;

	@Column(name = "idSUBJECT")
	private Long idSubject;

	@Column(name = "CHAPTER", length = 45)
	private Long chapter;

	@Column(name = "video_description", length = 1000)
	private String videoDescription;

	public Long getIdProductSampleVideo() {
		return idProductSampleVideo;
	}

	public void setIdProductSampleVideo(Long idProductSampleVideo) {
		this.idProductSampleVideo = idProductSampleVideo;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getChapter() {
		return chapter;
	}

	public void setChapter(Long chapter) {
		this.chapter = chapter;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public ProductSampleVideo() {

	}

}
