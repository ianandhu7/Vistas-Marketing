package co.vistafoundation.vlearning.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
/**
 * 
 * @author NaveenKumar
 *
 */
@Entity
@Table(name = "VIDEO_CATEGORY")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class VideoCategory extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVIDEO_CATEGORY")
	private Long idVideoCategory;
	

	@Column(name = "CATEGORY", length = 100, unique = true)
	private String category;

	/**
	 * 
	 */
	public VideoCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idVideoCategory
	 */
	public Long getIdVideoCategory() {
		return idVideoCategory;
	}

	/**
	 * @param idVideoCategory the idVideoCategory to set
	 */
	public void setIdVideoCategory(Long idVideoCategory) {
		this.idVideoCategory = idVideoCategory;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param idVideoCategory
	 * @param category
	 */
	public VideoCategory(String category) {
		super();
		this.category = category;
	}

}
