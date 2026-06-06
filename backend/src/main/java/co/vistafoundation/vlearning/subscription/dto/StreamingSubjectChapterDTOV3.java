/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import co.vistafoundation.vlearning.subject.model.SubjectChapter;

/**
 * @author NaveenKumar
 *
 */
public class StreamingSubjectChapterDTOV3 {

	private SubjectChapter chapter;

	private Boolean accessAllowed;

	private String completionPercentage;

	private Boolean isQuizAvailable;

	/**
	 * @return the chapter
	 */
	public SubjectChapter getChapter() {
		return chapter;
	}

	/**
	 * @param chapter the chapter to set
	 */
	public void setChapter(SubjectChapter chapter) {
		this.chapter = chapter;
	}

	/**
	 * @return the accessAllowed
	 */
	public Boolean getAccessAllowed() {
		return accessAllowed;
	}

	/**
	 * @param accessAllowed the accessAllowed to set
	 */
	public void setAccessAllowed(Boolean accessAllowed) {
		this.accessAllowed = accessAllowed;
	}

	/**
	 * @return the completionPercentage
	 */
	public String getCompletionPercentage() {
		return completionPercentage;
	}

	/**
	 * @param completionPercentage the completionPercentage to set
	 */
	public void setCompletionPercentage(String completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public StreamingSubjectChapterDTOV3(SubjectChapter chapter, Boolean accessAllowed, String completionPercentage,
			Boolean isQuizAvailable) {
		super();
		this.chapter = chapter;
		this.accessAllowed = accessAllowed;
		this.completionPercentage = completionPercentage;
		this.isQuizAvailable = isQuizAvailable;
	}

	/**
	 * @return the isQuizAvailable
	 */
	public Boolean getIsQuizAvailable() {
		return isQuizAvailable;
	}

	/**
	 * @param isQuizAvailable the isQuizAvailable to set
	 */
	public void setIsQuizAvailable(Boolean isQuizAvailable) {
		this.isQuizAvailable = isQuizAvailable;
	}

	/**
	 * 
	 */
	public StreamingSubjectChapterDTOV3() {
		super();
		// TODO Auto-generated constructor stub
	}

}
