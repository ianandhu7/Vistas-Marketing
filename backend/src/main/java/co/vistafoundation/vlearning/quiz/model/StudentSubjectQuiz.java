package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author meghana, Updated Sajini
 *
 */

@Entity
@Table(name = "STUDENT_SUBJECT_QUIZ")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentSubjectQuiz extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idSTUDENT_SUBJECT_QUIZ")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentSubjectQuiz;

	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscr;

	@Column(name = "QUIZ_DATE" )
	private Instant quizDate;
	
	@Column(name = "QUIZ_SCORE")
	private Float quizScore;
	
	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;
	
	@Column(name="idQUIZ")
	private Long idQuiz;
	
	@Column(name="TOTAL_MARKS_SCORED")
	private String totalMarksScored;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idSTUDENT_SUBJECT_QUIZ", referencedColumnName = "idSTUDENT_SUBJECT_QUIZ")
	@JsonIgnore
	private List<StudentSubjectQuizQuestion> studentSubjectQuizQuestion;


	public List<StudentSubjectQuizQuestion> getStudentSubjectQuizQuestion() {
		return studentSubjectQuizQuestion;
	}

	public void setStudentSubjectQuizQuestion(List<StudentSubjectQuizQuestion> studentSubjectQuizQuestion) {
		this.studentSubjectQuizQuestion = studentSubjectQuizQuestion;
	}

	public Long getIdQuiz() {
		return idQuiz;
	}

	public void setIdQuiz(Long idQuiz) {
		this.idQuiz = idQuiz;
	}

	public Long getIdStudentSubjectQuiz() {
		return idStudentSubjectQuiz;
	}

	public void setIdStudentSubjectQuiz(Long idStudentSubjectQuiz) {
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
	}

	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
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
	

	
	public String getTotalMarksScored() {
		return totalMarksScored;
	}

	public void setTotalMarksScored(String totalMarksScored) {
		this.totalMarksScored = totalMarksScored;
	}
	
	

	public StudentSubjectQuiz(Long idStudentSubscr, Instant quizDate, Float quizScore, Long idSubject, Long idQuiz,
			String totalMarksScored, List<StudentSubjectQuizQuestion> studentSubjectQuizQuestion) {
		super();
		this.idStudentSubscr = idStudentSubscr;
		this.quizDate = quizDate;
		this.quizScore = quizScore;
		this.idSubject = idSubject;
		this.idQuiz = idQuiz;
		this.totalMarksScored = totalMarksScored;
		this.studentSubjectQuizQuestion = studentSubjectQuizQuestion;
	}

	public StudentSubjectQuiz(Long idStudentSubscr, Instant quizDate, Float quizScore, Long idSubject, Long idQuiz,
			List<StudentSubjectQuizQuestion> studentSubjectQuizQuestion) {
		super();
		this.idStudentSubscr = idStudentSubscr;
		this.quizDate = quizDate;
		this.quizScore = quizScore;
		this.idSubject = idSubject;
		this.idQuiz = idQuiz;
		this.studentSubjectQuizQuestion = studentSubjectQuizQuestion;
	}

	public StudentSubjectQuiz() {
		
	}
	

}
