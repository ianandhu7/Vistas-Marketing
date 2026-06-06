package co.vistafoundation.vlearning.subject.model;

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
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "SUBJECT")
@JsonIgnoreProperties({"updatedBy", "createdBy","createdAt","updatedAt"})
public class Subject extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "SUBJECT_NAME", length = 45)
	private String subjectName;
	
	@Column(name = "VIDEO_URL", length=500)
	private String videoURL;

	@Column(name = "HEADER", length=100)
	private String header;
	
	@Column(name = "DESCRIPTION", length=1000)
	private String description;
	
	@Column(name = "COLOR", length=100)
	private String color;
	
	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) { 
		this.idSubject = idSubject;
	}

	public String getSubjectName() {
		return subjectName;
	}
	

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the videoURL
	 */
	public String getVideoURL() {
		return videoURL;
	}

	/**
	 * @param videoURL the videoURL to set
	 */
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	
	

	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public Subject() {

	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @param subjectName
	 * @param videoURL
	 * @param header
	 * @param description
	 * @param color
	 */
	public Subject(String subjectName, String videoURL, String header, String description, String color) {
		super();
		this.subjectName = subjectName;
		this.videoURL = videoURL;
		this.header = header;
		this.description = description;
		this.color = color;
	}
	
	

}
