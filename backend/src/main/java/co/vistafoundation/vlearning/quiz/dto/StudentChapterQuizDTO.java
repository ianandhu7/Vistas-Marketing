package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;


/**
 * @author Sajini
 * updated by @author NaveenKumar 
 */
public class StudentChapterQuizDTO {

	@NotNull
	private Long idSubjectChapter;

	@NotNull
	private Long idStudentSubscr;

	@NotNull
	private List<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();

	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
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
