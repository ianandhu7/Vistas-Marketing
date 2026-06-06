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

/***
 * 
 * @author sarfaraz
 *
 */

@Entity
@Table(name = "STUDENT_OFFLINE_QUIZ")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentOfflineQuiz extends UserDateAudit implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idSTUDENT_OFFLINE_QUIZ")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentOfflineQuiz;

	@Column(name = "QUIZ_DATE" )
	private Instant quizDate;
	
	@Column(name = "QUIZ_SCORE")
	private Float quizScore;
	
	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;
	
	@Column(name = "idSUBJECT_CHAPTER", nullable = false)
	private Long idSubjectChapter;
	
	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscr;
	
	@Column(name="idOFFLINE_VIDEO_COURSE")
	private Long idOfflineVideoCourse;

	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY)
	@JoinColumn(name = "idSTUDENT_OFFLINE_QUIZ", referencedColumnName = "idSTUDENT_OFFLINE_QUIZ")
	@JsonIgnore
	private List<StudentOfflineQuizQuestion> studentOfflineQuizQuestion;

	public Long getIdStudentOfflineQuiz() {
		return idStudentOfflineQuiz;
	}

	public void setIdStudentOfflineQuiz(Long idStudentOfflineQuiz) {
		this.idStudentOfflineQuiz = idStudentOfflineQuiz;
	}

	public Instant getQuizDate() {
		return quizDate;
	}

	public void setQuizDate(Instant quizDate) {
		this.quizDate = quizDate;
	}

	public Float getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(Float quizScore) {
		this.quizScore = quizScore;
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

	public List<StudentOfflineQuizQuestion> getStudentOfflineQuizQuestion() {
		return studentOfflineQuizQuestion;
	}

	public void setStudentOfflineQuizQuestion(List<StudentOfflineQuizQuestion> studentOfflineQuizQuestion) {
		this.studentOfflineQuizQuestion = studentOfflineQuizQuestion;
	}

	public StudentOfflineQuiz(Instant quizDate, Float quizScore, Long idSubject, Long idSubjectChapter,
			Long idStudentSubscr, Long idOfflineVideoCourse,
			List<StudentOfflineQuizQuestion> studentOfflineQuizQuestion) {
		super();
		this.quizDate = quizDate;
		this.quizScore = quizScore;
		this.idSubject = idSubject;
		this.idSubjectChapter = idSubjectChapter;
		this.idStudentSubscr = idStudentSubscr;
		this.idOfflineVideoCourse = idOfflineVideoCourse;
		this.studentOfflineQuizQuestion = studentOfflineQuizQuestion;
	}

	public StudentOfflineQuiz() {
		super();
	}
}
