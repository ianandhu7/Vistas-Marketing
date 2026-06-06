/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.util.List;

import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTOV2;

/**
 * @author NAVEEN
 *
 */
public class StudentSubscriptionSubjectDTOV2 {
	
	private String classStandardName;

	private String syllabusName;

	private String subjectName;

	private List<StreamingSubjectChapterDTOV2> streamingSubjectChapters;

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
	 * @return the streamingSubjectChapters
	 */
	public List<StreamingSubjectChapterDTOV2> getStreamingSubjectChapters() {
		return streamingSubjectChapters;
	}

	/**
	 * @param streamingSubjectChapters the streamingSubjectChapters to set
	 */
	public void setStreamingSubjectChapters(List<StreamingSubjectChapterDTOV2> streamingSubjectChapters) {
		this.streamingSubjectChapters = streamingSubjectChapters;
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

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @param classStandardName
	 * @param syllabusName
	 * @param subjectName
	 * @param streamingSubjectChapters
	 * @param percentageCompletion
	 * @param idProduct
	 */
	public StudentSubscriptionSubjectDTOV2(String classStandardName, String syllabusName, String subjectName,
			List<StreamingSubjectChapterDTOV2> streamingSubjectChapters, String percentageCompletion, Long idProduct) {
		super();
		this.classStandardName = classStandardName;
		this.syllabusName = syllabusName;
		this.subjectName = subjectName;
		this.streamingSubjectChapters = streamingSubjectChapters;
		this.percentageCompletion = percentageCompletion;
		this.idProduct = idProduct;
	}

	/**
	 * 
	 */
	public StudentSubscriptionSubjectDTOV2() {
		super();
		// TODO Auto-generated constructor stub
	}

}
