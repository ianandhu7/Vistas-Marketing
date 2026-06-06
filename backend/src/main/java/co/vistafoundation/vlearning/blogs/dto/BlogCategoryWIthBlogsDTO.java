package co.vistafoundation.vlearning.blogs.dto;

import java.util.List;

public class BlogCategoryWIthBlogsDTO {
	
	private String categoryImageLink;
	
	private Boolean activeFlag;
	
	private Long idBlogCategory;
	
	private List<BlogsNewDto> blogList;
	
	private String categoryName;

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
	 * @return the blogList
	 */
	public List<BlogsNewDto> getBlogList() {
		return blogList;
	}

	/**
	 * @param blogList the blogList to set
	 */
	public void setBlogList(List<BlogsNewDto> blogList) {
		this.blogList = blogList;
	}

}
