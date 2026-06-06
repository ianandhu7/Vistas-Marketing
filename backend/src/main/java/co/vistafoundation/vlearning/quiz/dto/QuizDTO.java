package co.vistafoundation.vlearning.quiz.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

public class QuizDTO {

	private Long idQuiz;
	private Long idProduct;
	private Long idSubject;
	private Long idSubjectChapter;
	private String quizName;
	List<QuizQuestion> quizQuestionList;
	
	public Long getIdQuiz() {
		return idQuiz;
	}
	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}
	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public List<QuizQuestion> getQuizQuestionList() {
		return quizQuestionList;
	}
	public void setQuizQuestionList(List<QuizQuestion> quizQuestionList) {
		this.quizQuestionList = quizQuestionList;
	}
	
}
