package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohan Kumar K M
 *
 */
public class StudentChapterListDTO  implements Serializable{

	private static final long serialVersionUID = 1L;
	List<NewStreamingSubjectChapterDTO>  subjectChapters;

	
	 
	
	/**
	 * 
	 */
	public StudentChapterListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the subjectChapters
	 */
	public List<NewStreamingSubjectChapterDTO> getSubjectChapters() {
		return subjectChapters;
	}

	/**
	 * @param subjectChapters the subjectChapters to set
	 */
	public void setSubjectChapters(List<NewStreamingSubjectChapterDTO> subjectChapters) {
		this.subjectChapters = subjectChapters;
	}
}
