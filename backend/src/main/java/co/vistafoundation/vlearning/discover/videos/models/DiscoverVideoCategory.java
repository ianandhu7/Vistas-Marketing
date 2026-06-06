package co.vistafoundation.vlearning.discover.videos.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/** Author Meghana**/

@Entity
@Table(name = "DISCOVER_VIDEO_CATEGORY")

public class DiscoverVideoCategory extends UserDateAudit implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idDISCOVER_VIDEO_CATEGORY")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDiscoverVideoCategory;
	
	@Column(name = "CATEGORY", length= 500)
	private String category;
	
	@Column(name = "IMAGE_LINK", length= 500)
	private String imageLink;
	
	
	

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public Long getIdDiscoverVideoCategory() {
		return idDiscoverVideoCategory;
	}

	public void setIdDiscoverVideoCategory(Long idDiscoverVideoCategory) {
		this.idDiscoverVideoCategory = idDiscoverVideoCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	
	public DiscoverVideoCategory(String category, String imageLink) {
		super();
		this.category = category;
		this.imageLink = imageLink;
	}

	public DiscoverVideoCategory() {
		
	}
	
}
