/**
 * 
 */
package co.vistafoundation.vlearning.subject.dto;

import java.io.Serializable;

import co.vistafoundation.vlearning.subject.model.SubjectChapter;

/**
 * @author NAVEEN
 *
 */
public class StreamingSubjectChapterDTO implements Serializable{
	
	

	private static final long serialVersionUID = 883839739176485384L;

	private SubjectChapter chapter;
	
	private Boolean accessAllowed;
	
	

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
	
	

}
