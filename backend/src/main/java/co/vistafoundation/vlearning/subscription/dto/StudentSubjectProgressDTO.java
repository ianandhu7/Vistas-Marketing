package co.vistafoundation.vlearning.subscription.dto;

import java.io.Serializable;
import java.util.List;

import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTO;

public class StudentSubjectProgressDTO  implements Serializable {

	

	private static final long serialVersionUID = -2399874610614395518L;
	
	private  String classStandardName;
	
	private  String syllabusName;
	
	private String  subjectName;
	
	private String percentageCompletion;
	
	private Long idProduct;

	private String totalSubjectVideos;

	private String totalSubjectWatchHours;
	
	private String subjectThumbnailURL;
	
	
	

	/**
	 * 
	 */
	public StudentSubjectProgressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

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
	 * @return the totalSubjectVideos
	 */
	public String getTotalSubjectVideos() {
		return totalSubjectVideos;
	}

	/**
	 * @param totalSubjectVideos the totalSubjectVideos to set
	 */
	public void setTotalSubjectVideos(String totalSubjectVideos) {
		this.totalSubjectVideos = totalSubjectVideos;
	}

	/**
	 * @return the totalSubjectWatchHours
	 */
	public String getTotalSubjectWatchHours() {
		return totalSubjectWatchHours;
	}

	/**
	 * @param totalSubjectWatchHours the totalSubjectWatchHours to set
	 */
	public void setTotalSubjectWatchHours(String totalSubjectWatchHours) {
		this.totalSubjectWatchHours = totalSubjectWatchHours;
	}

	/**
	 * @return the subjectThumbnailURL
	 */
	public String getSubjectThumbnailURL() {
		return subjectThumbnailURL;
	}

	/**
	 * @param subjectThumbnailURL the subjectThumbnailURL to set
	 */
	public void setSubjectThumbnailURL(String subjectThumbnailURL) {
		this.subjectThumbnailURL = subjectThumbnailURL;
	}

	

}
