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
 * 
 * @author NaveenKumar
 *
 */

@Entity
@Table(name = "TAG_LIST")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class TagList extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTAG_LIST", nullable = false)
	private Long idTagList;

	@Column(name = "TAG_NAME", length = 100, unique = true)
	private String tagName;

	/**
	 * @return the idTagList
	 */
	public Long getIdTagList() {
		return idTagList;
	}

	/**
	 * @param idTagList the idTagList to set
	 */
	public void setIdTagList(Long idTagList) {
		this.idTagList = idTagList;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @param tagName
	 */
	public TagList(String tagName) {
		super();
		this.tagName = tagName;
	}

	/**
	 * 
	 */
	public TagList() {
		super();
		// TODO Auto-generated constructor stub
	}

}
