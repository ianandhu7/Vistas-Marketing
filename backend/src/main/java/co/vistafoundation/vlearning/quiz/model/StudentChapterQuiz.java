package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

@Entity
@Table(name = "STUDENT_CHAPTER_QUIZ")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentChapterQuiz extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_CHAPTER_QUIZ")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentChapterQuiz;

	@Column(name = "QUIZ_DATE")
	private Instant quizDate;

	@Column(name = "QUIZ_SCORE")
	private Float quizScore;

	@Column(name = "idSUBJECT_CHAPTER", nullable = false)
	private Long idSubjectChapter;

	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscr;

	public Long getIdStudentChapterQuiz() {
		return idStudentChapterQuiz;
	}

	public void setIdStudentChapterQuiz(Long idStudentChapterQuiz) {
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}

	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY)
	@JoinColumn(name = "idSTUDENT_CHAPTER_QUIZ", referencedColumnName = "idSTUDENT_CHAPTER_QUIZ")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	@JsonIgnore
	private List<StudentChapterQuizQuestion> studentChapterQuizQuestion;

	public List<StudentChapterQuizQuestion> getStudentChapterQuizQuestion() {
		return studentChapterQuizQuestion;
	}

	public void setStudentChapterQuizQuestion(List<StudentChapterQuizQuestion> studentChapterQuizQuestion) {
		this.studentChapterQuizQuestion = studentChapterQuizQuestion;
	}

	public Long getIdSubjectChapterQuiz() {
		return idStudentChapterQuiz;
	}

	public void setIdSubjectChapterQuiz(Long idStudentChapterQuiz) {
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}

	public Instant getQuizDate() {
		return quizDate;
	}

	public void setQuizDate(Instant localDate) {
		this.quizDate = localDate;
	}

	public Float getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(Float quizScore) {
		this.quizScore = quizScore;
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

	public StudentChapterQuiz(Instant quizDate, Float quizScore, Long idSubjectChapter, Long idStudentSubscr,
			List<StudentChapterQuizQuestion> studentChapterQuizQuestion) {
		super();
		this.quizDate = quizDate;
		this.quizScore = quizScore;
		this.idSubjectChapter = idSubjectChapter;
		this.idStudentSubscr = idStudentSubscr;
		this.studentChapterQuizQuestion = studentChapterQuizQuestion;
	}

	public StudentChapterQuiz() {

	}

}
