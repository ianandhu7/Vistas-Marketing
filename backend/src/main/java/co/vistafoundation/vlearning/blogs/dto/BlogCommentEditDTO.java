/**
 * 
 */
package co.vistafoundation.vlearning.blogs.dto;

import javax.validation.constraints.NotNull;

/**
 * @author vk
 *
 */
public class BlogCommentEditDTO {
	
	@NotNull
	private Long idBlogComment;

	@NotNull
	private Long idBlog;

	@NotNull
	private Long userSurId;

	@NotNull
	private String userName;

	private String message;

	/**
	 * @return the idBlogComment
	 */
	public Long getIdBlogComment() {
		return idBlogComment;
	}

	/**
	 * @return the idBlog
	 */
	public Long getIdBlog() {
		return idBlog;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param idBlogComment the idBlogComment to set
	 */
	public void setIdBlogComment(Long idBlogComment) {
		this.idBlogComment = idBlogComment;
	}

	/**
	 * @param idBlog the idBlog to set
	 */
	public void setIdBlog(Long idBlog) {
		this.idBlog = idBlog;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
