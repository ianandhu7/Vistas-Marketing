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

@Entity
@Table(name = "FREE_VIDEO")
public class FreeVideo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "video_description", length = 500)
	private String videoDescription;
	@Column(name = "video_link", length = 2000)
	private String videoLink;

	@Column(name = "video_destination_page")
	private String destinationPage;

	@Column(name = "video_category")
	private String videoCategory;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPRODUCT", nullable = false, referencedColumnName = "idPRODUCT")
	private Product product;

	public FreeVideo() {

	}

	public FreeVideo(Long id, String videoDescription, String videoLink, String destinationPage, String videoCategory,
			Product product) {
		this.id = id;
		this.videoDescription = videoDescription;
		this.videoLink = videoLink;
		this.destinationPage = destinationPage;
		this.videoCategory = videoCategory;
		this.product = product;
	}

	public String getDestinationPage() {
		return destinationPage;
	}

	public void setDestinationPage(String destinationPage) {
		this.destinationPage = destinationPage;
	}

	public String getVideoCategory() {
		return videoCategory;
	}

	public void setVideoCategory(String videoCategory) {
		this.videoCategory = videoCategory;
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

	@Override
	public String toString() {
		return "FreeVideo [id=" + id + ", videoDescription=" + videoDescription + ", videoLink=" + videoLink
				+ ", product=" + product + "]";
	}

}
