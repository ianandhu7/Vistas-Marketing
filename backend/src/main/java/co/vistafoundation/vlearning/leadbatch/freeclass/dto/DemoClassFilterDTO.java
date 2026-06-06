/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author vk
 *
 */
public class DemoClassFilterDTO {

	private Long idTeacher;

	private Long idSubject;

	private Long idClassStandard;

	private Long idSyllabus;

	private Long idSubjectChapter;
	
	private Long idLevelExtraCurricular;
	
	private Long idState;
	

	/**
	 * @param idTeacher
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idSubjectChapter
	 */
	public DemoClassFilterDTO(Long idTeacher, Long idSubject, Long idClassStandard, Long idSyllabus,
			Long idSubjectChapter) {
		super();
		this.idTeacher = idTeacher;
		this.idSubject = idSubject;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idSubjectChapter = idSubjectChapter;
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

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
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
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idSubjectChapter
	 */
	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	/**
	 * @param idSubjectChapter the idSubjectChapter to set
	 */
	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	/**
	 * @return the idLevelExtraCurricular
	 */
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}

	/**
	 * @param idLevelExtraCurricular the idLevelExtraCurricular to set
	 */
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}
	
	
	
	
}
