package co.vistafoundation.vlearning.blogs.dto;

import java.time.LocalDate;

public class BlogsNewDto {	
	
	private Long idBlog;
	
	
	private Long idBlogCategory;
	
	
	private Long userSurId;
	
	
	private String blogTitle;
	
	
	private String subjectImageLink;
	
	
	private String thumnailImageLink;
	
	private LocalDate publishDate;
	
	
	private String blogS3Link;	
	
	private Boolean hideFlag;
	
	
	private Integer numClick;
	
	
	private String publisherName;	
	
	private String tags;
	
	
	private Integer NoOfComments;
	
	
	private String metaTitle;

	private String metaDescritption;


	private String slugURL;


	public Long getIdBlog() {
		return idBlog;
	}


	public void setIdBlog(Long idBlog) {
		this.idBlog = idBlog;
	}


	public Long getIdBlogCategory() {
		return idBlogCategory;
	}


	public void setIdBlogCategory(Long idBlogCategory) {
		this.idBlogCategory = idBlogCategory;
	}


	public Long getUserSurId() {
		return userSurId;
	}


	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}


	public String getBlogTitle() {
		return blogTitle;
	}


	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}


	public String getSubjectImageLink() {
		return subjectImageLink;
	}


	public void setSubjectImageLink(String subjectImageLink) {
		this.subjectImageLink = subjectImageLink;
	}


	public String getThumnailImageLink() {
		return thumnailImageLink;
	}


	public void setThumnailImageLink(String thumnailImageLink) {
		this.thumnailImageLink = thumnailImageLink;
	}


	public LocalDate getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}


	public String getBlogS3Link() {
		return blogS3Link;
	}


	public void setBlogS3Link(String blogS3Link) {
		this.blogS3Link = blogS3Link;
	}


	public Boolean getHideFlag() {
		return hideFlag;
	}


	public void setHideFlag(Boolean hideFlag) {
		this.hideFlag = hideFlag;
	}


	public Integer getNumClick() {
		return numClick;
	}


	public void setNumClick(Integer numClick) {
		this.numClick = numClick;
	}


	public String getPublisherName() {
		return publisherName;
	}


	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public Integer getNoOfComments() {
		return NoOfComments;
	}


	public void setNoOfComments(Integer noOfComments) {
		NoOfComments = noOfComments;
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
	
	
	

}

