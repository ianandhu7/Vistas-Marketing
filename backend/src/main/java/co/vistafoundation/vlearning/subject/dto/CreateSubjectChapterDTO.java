/**
 * 
 */
package co.vistafoundation.vlearning.subject.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Naveen Kumar
 *
 */
public class CreateSubjectChapterDTO {

	@NotNull
	private Long idSubject;

	@NotNull
	private Long idClassStandard;

	@NotNull
	private Long idSyllabus;

	@NotNull
	private Long idState;

	@NotNull
	private int sortOrder;
	
	@NotNull
	private String chapterName;

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
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
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @return the chapterName
	 */
	public String getChapterName() {
		return chapterName;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
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
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @param chapterName the chapterName to set
	 */
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}


}
