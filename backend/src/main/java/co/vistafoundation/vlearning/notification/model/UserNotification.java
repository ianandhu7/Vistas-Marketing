/**
 * 
 */
package co.vistafoundation.vlearning.notification.model;

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
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "USER_NOTIFICATION")
@JsonIgnoreProperties({ "updatedBy", "createdBy","updatedAt" })
public class UserNotification  extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idUSER_NOTIFICATION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserNotification;
	
	@Column(name = "HEADER", length= 50,nullable = false)
	private String header;	
	
	@Column(name = "CONTENT", length= 1000)
	private String content;
	
	@Column(name = "IS_NOT_VIEWED")
	private Boolean isNotViewed;
	
	@Column(name = "idVL_USER", nullable = false)
	private Long idVlUser;

	/**
	 * @return the idUserNotification
	 */
	public Long getIdUserNotification() {
		return idUserNotification;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the isNotViewed
	 */
	public Boolean getisNotViewed() {
		return isNotViewed;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idUserNotification the idUserNotification to set
	 */
	public void setIdUserNotification(Long idUserNotification) {
		this.idUserNotification = idUserNotification;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param isNotViewed the isNotViewed to set
	 */
	public void setisNotViewed(Boolean isNotViewed) {
		this.isNotViewed = isNotViewed;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @param header
	 * @param content
	 * @param isNotViewed
	 * @param idVlUser
	 */
	public UserNotification(String header, String content, Boolean isNotViewed, Long idVlUser) {
		super();
		this.header = header;
		this.content = content;
		this.isNotViewed = isNotViewed;
		this.idVlUser = idVlUser;
	}
	
	public UserNotification() 	
	{}
	
}
