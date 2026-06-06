package co.vistafoundation.vlearning.blogs.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class BlogsDto {

	@NotNull
	Long idBlogCategory;

	Long userSurId;

	@NotNull
	String blogTitle;

	String subjectImageLink;

	@NotNull
	String thumnailImageLink;

	LocalDate publishDate;

	@NotNull
	String blogS3Link;

	Boolean hideFlag;

	Integer numClick;

	String publisherName;

	Long idBlog;

	String tags;

	String metaTitle;

	String metaDescritption;

	@NotNull
	String slugURL;

	/**
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
//	public BlogsDto(@NotNull Long idBlogCategory, Long userSurId, @NotNull String blogTitle, String subjectImageLink,
//			@NotNull String thumnailImageLink, LocalDate publishDate, @NotNull String blogS3Link, Boolean hideFlag,
//			Integer numClick, String publisherName, String tags) {
//		super();
//		this.idBlogCategory = idBlogCategory;
//		this.userSurId = userSurId;
//		this.blogTitle = blogTitle;
//		this.subjectImageLink = subjectImageLink;
//		this.thumnailImageLink = thumnailImageLink;
//		this.publishDate = publishDate;
//		this.blogS3Link = blogS3Link;
//		this.hideFlag = hideFlag;
//		this.numClick = numClick;
//		this.publisherName = publisherName;
//		this.tags = tags;
//	}
//	
//	




	/**
	 * @return the idBlogCategory
	 */
	public Long getIdBlogCategory() {
		return idBlogCategory;
	}

	public BlogsDto(@NotNull Long idBlogCategory, Long userSurId, @NotNull String blogTitle, String subjectImageLink,
			@NotNull String thumnailImageLink, LocalDate publishDate, @NotNull String blogS3Link, Boolean hideFlag,
			Integer numClick, String publisherName, Long idBlog, String tags, String metaTitle, String metaDescritption,
			@NotNull String slugURL) {
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
		this.idBlog = idBlog;
		this.tags = tags;
		this.metaTitle = metaTitle;
		this.metaDescritption = metaDescritption;
		this.slugURL = slugURL;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @return the blogTitle
	 */
	public String getBlogTitle() {
		return blogTitle;
	}

	/**
	 * @return the subjectImageLink
	 */
	public String getSubjectImageLink() {
		return subjectImageLink;
	}

	/**
	 * @return the thumnailImageLink
	 */
	public String getThumnailImageLink() {
		return thumnailImageLink;
	}

	/**
	 * @return the publishDate
	 */
	public LocalDate getPublishDate() {
		return publishDate;
	}

	/**
	 * @return the blogS3Link
	 */
	public String getBlogS3Link() {
		return blogS3Link;
	}

	/**
	 * @return the hideFlag
	 */
	public Boolean getHideFlag() {
		return hideFlag;
	}

	/**
	 * @return the numClick
	 */
	public Integer getNumClick() {
		return numClick;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @param idBlogCategory the idBlogCategory to set
	 */
	public void setIdBlogCategory(Long idBlogCategory) {
		this.idBlogCategory = idBlogCategory;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @param blogTitle the blogTitle to set
	 */
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	/**
	 * @param subjectImageLink the subjectImageLink to set
	 */
	public void setSubjectImageLink(String subjectImageLink) {
		this.subjectImageLink = subjectImageLink;
	}

	/**
	 * @param thumnailImageLink the thumnailImageLink to set
	 */
	public void setThumnailImageLink(String thumnailImageLink) {
		this.thumnailImageLink = thumnailImageLink;
	}

	/**
	 * @param publishDate the publishDate to set
	 */
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * @param blogS3Link the blogS3Link to set
	 */
	public void setBlogS3Link(String blogS3Link) {
		this.blogS3Link = blogS3Link;
	}

	/**
	 * @param hideFlag the hideFlag to set
	 */
	public void setHideFlag(Boolean hideFlag) {
		this.hideFlag = hideFlag;
	}

	/**
	 * @param numClick the numClick to set
	 */
	public void setNumClick(Integer numClick) {
		this.numClick = numClick;
	}

	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
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
