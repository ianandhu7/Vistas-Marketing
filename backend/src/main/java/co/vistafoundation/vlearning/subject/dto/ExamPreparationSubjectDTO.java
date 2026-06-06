/**
 * 
 */
package co.vistafoundation.vlearning.subject.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.dto.QuizDifficulty;

/**
 * @author NAVEEN
 *
 */
public class ExamPreparationSubjectDTO {

	private String subjectName;

	private Long idSubject;

	private String quizDescription;

	private String color;
	
	private List<QuizDifficulty> quizLevels;
	
	private String imageURL;

	/**
	 * @return the quizDescription
	 */
	public String getQuizDescription() {
		return quizDescription;
	}

	/**
	 * @param quizDescription the quizDescription to set
	 */
	public void setQuizDescription(String quizDescription) {
		this.quizDescription = quizDescription;
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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * 
	 */
	public ExamPreparationSubjectDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the quizLevels
	 */
	public List<QuizDifficulty> getQuizLevels() {
		return quizLevels;
	}

	/**
	 * @param quizLevels the quizLevels to set
	 */
	public void setQuizLevels(List<QuizDifficulty> quizLevels) {
		this.quizLevels = quizLevels;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	

	
}
