/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

/**
 * @author naveenkumar
 *
 */
public class VCTQuizDTO {

	String questionType;

	String mark;

	String noOfQuestion;

	String questionTitle;

	List<QuizQuestion> questions;

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getNoOfQuestion() {
		return noOfQuestion;
	}

	public void setNoOfQuestion(String noOfQuestion) {
		this.noOfQuestion = noOfQuestion;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public List<QuizQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuizQuestion> questions) {
		this.questions = questions;
	}

	public VCTQuizDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
