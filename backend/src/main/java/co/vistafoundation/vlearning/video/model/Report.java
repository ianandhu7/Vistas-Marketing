/**
 * 
 */
package co.vistafoundation.vlearning.video.model;

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
@Table(name = "REPORT")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class Report extends UserDateAudit implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idREPORT", nullable = false)
	private Long idReport;

	@Column(name = "SUMMERY", length = 500)
	private String videoDescription;

	@Column(name = "idVL_USER")
	private Long idVlUser;

	/**
	 * @return the idReport
	 */
	public Long getIdReport() {
		return idReport;
	}

	/**
	 * @param idReport the idReport to set
	 */
	public void setIdReport(Long idReport) {
		this.idReport = idReport;
	}

	/**
	 * @return the videoDescription
	 */
	public String getVideoDescription() {
		return videoDescription;
	}

	/**
	 * @param videoDescription the videoDescription to set
	 */
	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @param videoDescription
	 * @param idVlUser
	 */
	public Report(String videoDescription, Long idVlUser) {
		super();
		this.videoDescription = videoDescription;
		this.idVlUser = idVlUser;
	}

	/**
	 * 
	 */
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

}
