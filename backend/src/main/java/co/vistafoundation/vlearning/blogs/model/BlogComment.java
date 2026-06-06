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

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "BLOG_COMMENT")
public class BlogComment extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBLOG_COMMENT", nullable = false)
	private Long idBlogComment;
	
	@Column(name = "idBLOG", nullable = false)
	private Long idBlog;
	
	@Column(name = "idVL_USER", nullable = false)
	private Long userSurId;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "MESSAGE")
	private String message;

	/**
	 * 
	 */
	public BlogComment() {
		super();
	}

	/**
	 * @param idBlogComment
	 * @param idBlog
	 * @param userSurId
	 * @param userName
	 * @param message
	 */
	public BlogComment(Long idBlogComment, Long idBlog, Long userSurId, String userName, String message) {
		super();
		this.idBlogComment = idBlogComment;
		this.idBlog = idBlog;
		this.userSurId = userSurId;
		this.userName = userName;
		this.message = message;
	}

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
