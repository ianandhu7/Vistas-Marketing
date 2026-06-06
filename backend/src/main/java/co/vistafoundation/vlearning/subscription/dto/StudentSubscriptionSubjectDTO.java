/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;
import java.io.Serializable;
import java.util.List;

import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTO;

/**
 * @author Naveen Kumar
 *
 */
public class StudentSubscriptionSubjectDTO implements Serializable{
	
	private static final long serialVersionUID = -3502443239531312389L;

	private  String classStandardName;
	
	private  String syllabusName;
	
	private String  subjectName;
	
	private List<StreamingSubjectChapterDTO> streamingSubjectChapters;
	
	private String percentageCompletion;
	
	private Long idProduct;

	/**
	 * @return the classStandardName
	 */
	public String getClassStandardName() {
		return classStandardName;
	}

	/**
	 * @param classStandardName the classStandardName to set
	 */
	public void setClassStandardName(String classStandardName) {
		this.classStandardName = classStandardName;
	}

	/**
	 * @return the syllabusName
	 */
	public String getSyllabusName() {
		return syllabusName;
	}

	/**
	 * @param syllabusName the syllabusName to set
	 */
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the percentageCompletion
	 */
	public String getPercentageCompletion() {
		return percentageCompletion;
	}

	/**
	 * @param percentageCompletion the percentageCompletion to set
	 */
	public void setPercentageCompletion(String percentageCompletion) {
		this.percentageCompletion = percentageCompletion;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the streamingSubjectChapters
	 */
	public List<StreamingSubjectChapterDTO> getStreamingSubjectChapters() {
		return streamingSubjectChapters;
	}

	/**
	 * @param streamingSubjectChapters the streamingSubjectChapters to set
	 */
	public void setStreamingSubjectChapters(List<StreamingSubjectChapterDTO> streamingSubjectChapters) {
		this.streamingSubjectChapters = streamingSubjectChapters;
	}
	
	
	
	
	
	

}