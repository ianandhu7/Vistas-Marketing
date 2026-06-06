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
 * @author sarfaraz
 *
 */

@Entity
@Table(name = "STUDENT_OFFLINE_QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentOfflineQuizQuestion extends UserDateAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idSTUDENT_OFFLINE_QUIZ_QUESTION")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentOfflineQuizQuestion;

	@Column(name="idQUIZ_QUESTION")
	private Long idQuizQuestion;
	
	@Column(name = "CORRECT_FLAG")
	private Boolean correctFlag;
	
	@Column(name="idSTUDENT_OFFLINE_QUIZ")
	private Long idStudentOfflineQuiz;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idSTUDENT_OFFLINE_QUIZ_QUESTION", referencedColumnName = "idSTUDENT_OFFLINE_QUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private List<StudentOfflineQuizAnswer> studentOfflineQuizAnswer;

	public Long getIdStudentOfflineQuizQuestion() {
		return idStudentOfflineQuizQuestion;
	}

	public void setIdStudentOfflineQuizQuestion(Long idStudentOfflineQuizQuestion) {
		this.idStudentOfflineQuizQuestion = idStudentOfflineQuizQuestion;
	}

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public Boolean getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(Boolean correctFlag) {
		this.correctFlag = correctFlag;
	}

	public Long getIdStudentOfflineQuiz() {
		return idStudentOfflineQuiz;
	}

	public void setIdStudentOfflineQuiz(Long idStudentOfflineQuiz) {
		this.idStudentOfflineQuiz = idStudentOfflineQuiz;
	}

	public List<StudentOfflineQuizAnswer> getStudentOfflineQuizAnswer() {
		return studentOfflineQuizAnswer;
	}

	public void setStudentOfflineQuizAnswer(List<StudentOfflineQuizAnswer> studentOfflineQuizAnswer) {
		this.studentOfflineQuizAnswer = studentOfflineQuizAnswer;
	}

	public StudentOfflineQuizQuestion(Long idQuizQuestion, Boolean correctFlag, 
			Long idStudentOfflineQuiz,List<StudentOfflineQuizAnswer> studentOfflineQuizAnswer) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.correctFlag = correctFlag;
		this.idStudentOfflineQuiz = idStudentOfflineQuiz;
		this.studentOfflineQuizAnswer = studentOfflineQuizAnswer;
	}
	
	public StudentOfflineQuizQuestion() {
		super();
	}
}
