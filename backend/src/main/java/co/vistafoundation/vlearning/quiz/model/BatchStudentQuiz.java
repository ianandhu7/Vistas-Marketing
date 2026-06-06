package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/***
 * 
 * @author meghana
 *
 */




@Entity
@Table(name = "BATCH_STUDENT_QUIZ")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchStudentQuiz extends UserDateAudit implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idBATCH_STUDENT_QUIZ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBatchStudentQuiz;

	@Column(name = "QUIZ_COMPLETE_FLAG", nullable = false)
	private Boolean quizCompleteFlag;

	@Column(name = "ATTEMPT_DATE" )
	private Instant attemptDate;

	@Column(name = "idSTUDENT_SUBSCR")
	private Long idStudentSubscription;
	
	@Column(name = "idBATCH_STUDENT")
	private Long idBatchStudent;
	
	@Column(name = "SCORE")
	private Float score;
	
	@Column(name = "idBATCH_QUIZ_ASSIGNMENT", nullable = false)
	private Long idBatchQuizAssignment;

	public Long getIdBatchStudentQuiz() {
		return idBatchStudentQuiz;
	}

	public void setIdBatchStudentQuiz(Long idBatchStudentQuiz) {
		this.idBatchStudentQuiz = idBatchStudentQuiz;
	}

	public Boolean getQuizCompleteFlag() {
		return quizCompleteFlag;
	}

	public void setQuizCompleteFlag(Boolean quizCompleteFlag) {
		this.quizCompleteFlag = quizCompleteFlag;
	}

	public Instant getAttemptDate() {
		return attemptDate;
	}

	public void setAttemptDate(Instant attemptDate) {
		this.attemptDate = attemptDate;
	}

	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}
	public Long getIdBatchStudent() {
		return idBatchStudent;
	}

	public void setIdBatchStudent(Long idBatchStudent) {
		this.idBatchStudent = idBatchStudent;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getIdBatchQuizAssignment() {
		return idBatchQuizAssignment;
	}

	public void setIdBatchQuizAssignment(Long idBatchQuizAssignment) {
		this.idBatchQuizAssignment = idBatchQuizAssignment;
	}

	public BatchStudentQuiz(Boolean quizCompleteFlag, Instant attemptDate, Long idStudentSubscription, Float score,
			Long idBatchQuizAssignment,Long idBatchStudent) {
		super();
		this.quizCompleteFlag = quizCompleteFlag;
		this.attemptDate = attemptDate;
		this.idStudentSubscription = idStudentSubscription;
		this.score = score;
		this.idBatchQuizAssignment = idBatchQuizAssignment;
		this.idBatchStudent = idBatchStudent;
	}

	public BatchStudentQuiz() {
	}
}
