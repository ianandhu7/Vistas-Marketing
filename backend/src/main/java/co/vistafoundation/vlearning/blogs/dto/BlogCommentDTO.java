package co.vistafoundation.vlearning.blogs.dto;

import javax.validation.constraints.NotNull;

public class BlogCommentDTO {
	
	@NotNull
	Long idBlog;

	@NotNull
	Long userSurId;

	@NotNull
	String userName;

	String message;
	
	/**
	 * @param idBlog
	 * @param userSurId
	 * @param userName
	 * @param message
	 */
	public BlogCommentDTO(Long idBlog, Long userSurId, String userName, String message) {
		super();
		this.idBlog = idBlog;
		this.userSurId = userSurId;
		this.userName = userName;
		this.message = message;
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
