/**
 * 
 */
package co.vistafoundation.vlearning.faq.dto;

import java.util.List;

/**
 * @author NAVEEN
 *
 */
public class FaqDTO {

	String question;

	List<FaqObjectAnswer> answers;

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answers
	 */
	public List<FaqObjectAnswer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<FaqObjectAnswer> answers) {
		this.answers = answers;
	}

	/**
	 * @param question
	 * @param answers
	 */
	public FaqDTO(String question, List<FaqObjectAnswer> answers) {
		super();
		this.question = question;
		this.answers = answers;
	}

	/**
	 * 
	 */
	public FaqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
