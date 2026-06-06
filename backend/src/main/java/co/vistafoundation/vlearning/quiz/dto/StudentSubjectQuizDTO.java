package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

public class StudentSubjectQuizDTO {
	
	
	@NotNull
	private Long idQuiz;
	@NotNull
	private Long idSubject;
	@NotNull
	private Long idStudentSubscr;
	
	@NotNull
	private List<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();
	
	
	
	public Long getIdQuiz() {
		return idQuiz;
	}
	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}
	public Long getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}
	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}
	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}
	public List<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}
	public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

}
