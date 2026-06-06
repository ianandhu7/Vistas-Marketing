/**
 * 
 */
package co.vistafoundation.vlearning.blogs.model;

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
 * @author vk
 *
 */
@Entity
@Table(name = "BLOG_CATEGORY")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BlogsCategory extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBLOG_CATEGORY", nullable = false)
	private Long idBlogCategory;
	
	@Column(name = "CATEGORY_NAME", length = 100)
	private String categoryName;
	
	@Column(name = "CATEGORY_IMAGE_LINK" , nullable = false)
	private String categoryImageLink;
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag = true;
	

	/**
	 * 
	 */
	public BlogsCategory() {
		super();
	}

	/**
	 * @param idBlogCategory
	 * @param categoryName
	 * @param categoryImageLink
	 * @param activeFlag
	 */
	public BlogsCategory(Long idBlogCategory, String categoryName , String categoryImageLink , Boolean activeFlag) {
		super();
		this.idBlogCategory = idBlogCategory;
		this.categoryName = categoryName;
		this.categoryImageLink=categoryImageLink;
		this.activeFlag=activeFlag;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the categoryImageLink
	 */
	public String getCategoryImageLink() {
		return categoryImageLink;
	}

	/**
	 * @param categoryImageLink the categoryImageLink to set
	 */
	public void setCategoryImageLink(String categoryImageLink) {
		this.categoryImageLink = categoryImageLink;
	}

	/**
	 * @return the idBlogCategory
	 */
	public Long getIdBlogCategory() {
		return idBlogCategory;
	}

	/**
	 * @param idBlogCategory the idBlogCategory to set
	 */
	public void setIdBlogCategory(Long idBlogCategory) {
		this.idBlogCategory = idBlogCategory;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
