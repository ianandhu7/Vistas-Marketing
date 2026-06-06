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
@Table(name = "SUBJECT_CHAPTER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class SubjectChapter extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSUBJECT_CHAPTER", nullable = false)
	private Long idSubjectChapter;

	@Column(name = "CHAPTER_NAME", length = 100)
	private String chapterName;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "PLAYLIST_LINK", length = 45)
	private String playlistLink;
	
	
	@Column(name = "idCLASS_STANDARD", nullable = false)
	private Long idClassStandard;
	
	@Column(name = "idSYLLABUS")
	private Long idSyllabus;
	
	@Column(name = "idSTATE")
    private Long idState;
	
	@Column(name = "SORT_ORDER")
    private int sortOrder;
	
	@Column(name = "ACTIVE_FLAG")
    private Boolean  activeFlag;
	

	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}


	public String getPlaylistLink() {
		return playlistLink;
	}

	public void setPlaylistLink(String playlistLink) {
		this.playlistLink = playlistLink;
	}

	
	public SubjectChapter(String chapterName, Long idSubject, String playlistLink) {
		super();
		this.chapterName = chapterName;
		this.idSubject = idSubject;
		this.playlistLink = playlistLink;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public SubjectChapter() {
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	

}
