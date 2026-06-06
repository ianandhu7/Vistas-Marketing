package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/***
 * 
 * @author meghana, Updated Sajini
 *
 */

@Entity
@Table(name = "STUDENT_SUBJECT_QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentSubjectQuizQuestion extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_SUBJECT_QUIZ_QUESTION")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentSubjectQuizQuestion;

//	@Column(name = "QUIZ_SUR_ID", nullable = false)
//	private Long quizSurId;

	@Column(name = "CORRECT_FLAG")
	private Boolean correctFlag;

	@Column(name = "idQUIZ_QUESTION")
	private Long idQuizQuestion;

	@Column(name = "idSTUDENT_SUBJECT_QUIZ")
	private Long idStudentSubjectQuiz;

	@Column(name = "SCORED_MARKS")
	private String scoredMarks;

	public Long getIdStudentSubjectQuiz() {
		return idStudentSubjectQuiz;
	}

	public void setIdStudentSubjectQuiz(Long idStudentSubjectQuiz) {
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
	}

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public List<StudentSubjectQuizAnswer> getStudentSubjectQuizanswers() {
		return studentSubjectQuizAnswers;
	}

	public void setStudentSubjectQuizAnswers(List<StudentSubjectQuizAnswer> studentSubjectQuizAnswers) {
		this.studentSubjectQuizAnswers = studentSubjectQuizAnswers;
	}

	/*
	 * @ManyToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name="idSTUDENT_SUBJECT_QUIZ") private StudentSubjectQuiz
	 * studentSubjectQuiz;
	 * 
	 * public StudentSubjectQuiz getStudentSubjectQuiz() { return
	 * studentSubjectQuiz; }
	 * 
	 * public void setStudentSubjectQuiz(StudentSubjectQuiz studentSubjectQuiz) {
	 * this.studentSubjectQuiz = studentSubjectQuiz; }
	 */

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idSTUDENT_SUBJECT_QUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })

	private List<StudentSubjectQuizAnswer> studentSubjectQuizAnswers;

	public List<StudentSubjectQuizAnswer> getStudentSubjectQuizAnswers() {
		return studentSubjectQuizAnswers;
	}

	public Long getIdStudentSubjectQuizQuestion() {
		return idStudentSubjectQuizQuestion;
	}

	public void setIdStudentSubjectQuizQuestion(Long idStudentSubjectQuizQuestion) {
		this.idStudentSubjectQuizQuestion = idStudentSubjectQuizQuestion;
	}

//	public Long getQuizSurId() {
//		return quizSurId;
//	}
//
//	public void setQuizSurId(Long quizSurId) {
//		this.quizSurId = quizSurId;
//	}

	public Boolean getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(Boolean correctFlag) {
		this.correctFlag = correctFlag;
	}

	public String getScoredMarks() {
		return scoredMarks;
	}

	public void setScoredMarks(String scoredMarks) {
		this.scoredMarks = scoredMarks;
	}

	public StudentSubjectQuizQuestion(Boolean correctFlag, Long idQuizQuestion, Long idStudentSubjectQuiz,
			String scoredMarks, List<StudentSubjectQuizAnswer> studentSubjectQuizAnswers) {
		super();
		this.correctFlag = correctFlag;
		this.idQuizQuestion = idQuizQuestion;
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
		this.scoredMarks = scoredMarks;
		this.studentSubjectQuizAnswers = studentSubjectQuizAnswers;
	}

	public StudentSubjectQuizQuestion(Boolean correctFlag, Long idQuizQuestion,
			List<StudentSubjectQuizAnswer> studentSubjectQuizAnswers) {
		super();
		this.correctFlag = correctFlag;
		this.idQuizQuestion = idQuizQuestion;
		this.studentSubjectQuizAnswers = studentSubjectQuizAnswers;
	}

	public StudentSubjectQuizQuestion() {

	}

}
