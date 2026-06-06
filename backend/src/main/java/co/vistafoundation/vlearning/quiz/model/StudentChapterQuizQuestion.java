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

/**
 * 
 * @author Sajini
 *
 */

@Entity
@Table(name = "STUDENT_CHAPTER_QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentChapterQuizQuestion extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_CHAPTER_QUIZ_QUESTION")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentChapterQuizQuestion;

	@Column(name = "idQUIZ_QUESTION")
	private Long idQuizQuestion;

	@Column(name = "idSTUDENT_CHAPTER_QUIZ")
	private Long idStudentChapterQuiz;

	@Column(name = "CORRECT_FLAG")
	private boolean correctFlag;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idSTUDENT_CHAPTER_QUIZ_QUESTION", referencedColumnName = "idSTUDENT_CHAPTER_QUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private List<StudentChapterQuizAnswer> studentChapterQuizAnswer;

	public Long getIdStudentChapterQuizQuestion() {
		return idStudentChapterQuizQuestion;
	}

	public List<StudentChapterQuizAnswer> getStudentChapterQuizAnswer() {
		return studentChapterQuizAnswer;
	}

	public void setStudentChapterQuizAnswer(List<StudentChapterQuizAnswer> studentChapterQuizAnswer) {
		this.studentChapterQuizAnswer = studentChapterQuizAnswer;
	}

	public void setIdStudentChapterQuizQuestion(Long idStudentChapterQuizQuestion) {
		this.idStudentChapterQuizQuestion = idStudentChapterQuizQuestion;
	}

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public boolean isCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(boolean correctFlag) {
		this.correctFlag = correctFlag;
	}

	public Long getIdStudentChapterQuiz() {
		return idStudentChapterQuiz;
	}

	public void setIdStudentChapterQuiz(Long idStudentChapterQuiz) {
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}

	public StudentChapterQuizQuestion(Long idQuizQuestion, boolean correctFlag,
			List<StudentChapterQuizAnswer> studentChapterQuizAnswer, Long idStudentChapterQuiz) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.correctFlag = correctFlag;
		this.studentChapterQuizAnswer = studentChapterQuizAnswer;
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}

	public StudentChapterQuizQuestion() {
		super();

	}

}
