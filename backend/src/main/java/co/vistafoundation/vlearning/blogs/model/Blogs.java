/**
 * 
 */
package co.vistafoundation.vlearning.blogs.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "BLOG")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" , "tags"})
public class Blogs extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBLOG", nullable = false)
	private Long idBlog;
	
	@Column(name = "idBLOG_CATEGORY", nullable = false)
	private Long idBlogCategory;
	
	@Column(name = "idVL_USER")
	private Long userSurId;
	
	@Column(name = "BLOG_TITLE", nullable = false)
	private String blogTitle;
	
	@Column(name = "SUBJECT_IMAGE_LINK")
	private String subjectImageLink;
	
	@Column(name = "THUMBNAIL_IMAGE_LINK", nullable = false)
	private String thumnailImageLink;
	
	@Column(name = "PUBLISH_DATE")
	private LocalDate publishDate;
	
	@Column(name = "BLOG_S3_LINK", nullable = false)
	private String blogS3Link;
	
	@Column(name = "HIDE_FLAG")
	private Boolean hideFlag;
	
	@Column(name = "NUM_CLICKS")
	private Integer numClick;
	
	@Column(name = "PUBLISHER_NAME")
	private String publisherName;
	
	@Column(name = "TAGS")
	private String tags;
	
	@Column(name = "META_TITLE")
	private String metaTitle;
	
	@Column(name = "META_DESCRIPTION")
	private String metaDescritption;
	
	@Column(name = "SLUG_URL", nullable = false,unique=true)
	private String slugURL;
	

	/**
	 * 
	 */
	public Blogs() {
		super();
	}

	/**
	 * @param idBlog
	 * @param idBlogCategory
	 * @param userSurId
	 * @param blogTitle
	 * @param subjectImageLink
	 * @param thumnailImageLink
	 * @param publishDate
	 * @param blogS3Link
	 * @param hideFlag
	 * @param numClick
	 * @param publisherName
	 * @param tags
	 */
	public Blogs(Long idBlog, Long idBlogCategory, Long userSurId, String blogTitle, String subjectImageLink,
			String thumnailImageLink, LocalDate publishDate, String blogS3Link, Boolean hideFlag, Integer numClick,
			String publisherName,String tags) {
		super();
		this.idBlog = idBlog;
		this.idBlogCategory = idBlogCategory;
		this.userSurId = userSurId;
		this.blogTitle = blogTitle;
		this.subjectImageLink = subjectImageLink;
		this.thumnailImageLink = thumnailImageLink;
		this.publishDate = publishDate;
		this.blogS3Link = blogS3Link;
		this.hideFlag = hideFlag;
		this.numClick = numClick;
		this.publisherName = publisherName;
		this.tags=tags;
	}
	
	

	

	public Blogs(Long idBlogCategory, Long userSurId, String blogTitle, String subjectImageLink,
			String thumnailImageLink, LocalDate publishDate, String blogS3Link, Boolean hideFlag, Integer numClick,
			String publisherName, String tags, String metaTitle, String metaDescritption, String slugURL) {
		super();
		this.idBlogCategory = idBlogCategory;
		this.userSurId = userSurId;
		this.blogTitle = blogTitle;
		this.subjectImageLink = subjectImageLink;
		this.thumnailImageLink = thumnailImageLink;
		this.publishDate = publishDate;
		this.blogS3Link = blogS3Link;
		this.hideFlag = hideFlag;
		this.numClick = numClick;
		this.publisherName = publisherName;
		this.tags = tags;
		this.metaTitle = metaTitle;
		this.metaDescritption = metaDescritption;
		this.slugURL = slugURL;
	}

	/**
	 * @return the idBlog
	 */
	public Long getIdBlog() {
		return idBlog;
	}

	/**
	 * @param idBlog the idBlog to set
	 */
	public void setIdBlog(Long idBlog) {
		this.idBlog = idBlog;
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
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the blogTitle
	 */
	public String getBlogTitle() {
		return blogTitle;
	}

	/**
	 * @param blogTitle the blogTitle to set
	 */
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	/**
	 * @return the subjectImageLink
	 */
	public String getSubjectImageLink() {
		return subjectImageLink;
	}

	/**
	 * @param subjectImageLink the subjectImageLink to set
	 */
	public void setSubjectImageLink(String subjectImageLink) {
		this.subjectImageLink = subjectImageLink;
	}

	/**
	 * @return the thumnailImageLink
	 */
	public String getThumnailImageLink() {
		return thumnailImageLink;
	}

	/**
	 * @param thumnailImageLink the thumnailImageLink to set
	 */
	public void setThumnailImageLink(String thumnailImageLink) {
		this.thumnailImageLink = thumnailImageLink;
	}

	/**
	 * @return the publishDate
	 */
	public LocalDate getPublishDate() {
		return publishDate;
	}

	/**
	 * @param publishDate the publishDate to set
	 */
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * @return the blogS3Link
	 */
	public String getBlogS3Link() {
		return blogS3Link;
	}

	/**
	 * @param blogS3Link the blogS3Link to set
	 */
	public void setBlogS3Link(String blogS3Link) {
		this.blogS3Link = blogS3Link;
	}

	/**
	 * @return the hideFlag
	 */
	public Boolean getHideFlag() {
		return hideFlag;
	}

	/**
	 * @param hideFlag the hideFlag to set
	 */
	public void setHideFlag(Boolean hideFlag) {
		this.hideFlag = hideFlag;
	}

	/**
	 * @return the numClick
	 */
	public Integer getNumClick() {
		return numClick;
	}

	/**
	 * @param numClick the numClick to set
	 */
	public void setNumClick(Integer numClick) {
		this.numClick = numClick;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	
	

	public String getMetaTitle() {
		return metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getMetaDescritption() {
		return metaDescritption;
	}

	public void setMetaDescritption(String metaDescritption) {
		this.metaDescritption = metaDescritption;
	}

	public String getSlugURL() {
		return slugURL;
	}

	public void setSlugURL(String slugURL) {
		this.slugURL = slugURL;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
}
