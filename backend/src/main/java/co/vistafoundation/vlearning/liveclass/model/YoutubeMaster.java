/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "YOUTUBE_MASTER", uniqueConstraints = {@UniqueConstraint(columnNames = {"YOUTUBE_USER_ID", "idTEACHER"})})
public class YoutubeMaster extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idYOUTUBE_MASTER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idYoutubeMaster;
	
	@Column(name = "YOUTUBE_USER_ID")
	private String youtubeUserId;
	
	@Column(name = "YOUTUBE_PASSWORD")
	private String youtubePassword;
	
	@Column(name="idTEACHER")
	private Long idTeacher;

	/**
	 * 
	 */
	public YoutubeMaster() {
		super();
	}

	/**
	 * @param youtubeUserId
	 * @param youtubePassword
	 * @param idTeacher
	 */
	public YoutubeMaster(String youtubeUserId, String youtubePassword, Long idTeacher) {
		super();
		this.youtubeUserId = youtubeUserId;
		this.youtubePassword = youtubePassword;
		this.idTeacher = idTeacher;
	}

	/**
	 * @return the idYoutubeMaster
	 */
	public Long getIdYoutubeMaster() {
		return idYoutubeMaster;
	}

	/**
	 * @param idYoutubeMaster the idYoutubeMaster to set
	 */
	public void setIdYoutubeMaster(Long idYoutubeMaster) {
		this.idYoutubeMaster = idYoutubeMaster;
	}

	/**
	 * @return the youtubeUserId
	 */
	public String getYoutubeUserId() {
		return youtubeUserId;
	}

	/**
	 * @param youtubeUserId the youtubeUserId to set
	 */
	public void setYoutubeUserId(String youtubeUserId) {
		this.youtubeUserId = youtubeUserId;
	}

	/**
	 * @return the youtubePassword
	 */
	@JsonIgnore
	public String getYoutubePassword() {
		return youtubePassword;
	}

	/**
	 * @param youtubePassword the youtubePassword to set
	 */
	public void setYoutubePassword(String youtubePassword) {
		this.youtubePassword = youtubePassword;
	}

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}
	
}
