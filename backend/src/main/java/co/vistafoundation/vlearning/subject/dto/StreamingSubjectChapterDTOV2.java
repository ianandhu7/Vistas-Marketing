package co.vistafoundation.vlearning.subject.dto;

import co.vistafoundation.vlearning.subject.model.SubjectChapter;

public class StreamingSubjectChapterDTOV2 {

	private SubjectChapter chapter;

	private Boolean accessAllowed;

	private String completionPercentage;

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

	/**
	 * @param chapter
	 * @param accessAllowed
	 * @param completionPercentage
	 */
	public StreamingSubjectChapterDTOV2(SubjectChapter chapter, Boolean accessAllowed, String completionPercentage) {
		super();
		this.chapter = chapter;
		this.accessAllowed = accessAllowed;
		this.completionPercentage = completionPercentage;
	}

	/**
	 * 
	 */
	public StreamingSubjectChapterDTOV2() {
		super();
		// TODO Auto-generated constructor stub
	}

}
