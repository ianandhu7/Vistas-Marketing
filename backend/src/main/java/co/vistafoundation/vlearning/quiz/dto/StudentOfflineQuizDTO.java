package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

public class StudentOfflineQuizDTO {

	@NotNull
	private Long idSubject;
	
	@NotNull
	private Long idSubjectChapter;

	@NotNull
	private Long idStudentSubscr;
	
	@NotNull
	private Long idOfflineVideoCourse;

	@NotNull
	private List<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();

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

	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}

	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	public List<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}

	public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}
}
