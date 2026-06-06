package co.vistafoundation.vlearning.batch.model;

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
 * @author KamachiDevi
 * 
 **/
@Entity
@Table(name = "BATCH_STUDENT_QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchStudentQuizQuestion extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idBATCH_STUDENT_QUIZ_QUESTION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBatchStudentQuizQuestion;

	@Column(name = "idBATCH_STUDENT_QUIZ")
	private Long idBatchStudentQuiz;

	@Column(name = "idBATCH_QUIZ_QUESTION")
	private Long idBatchQuizQuestion;

	@Column(name = "CORRECT_FLAG")
	private boolean correctFlag;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBATCH_STUDENT_QUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })

	private List<BatchStudentQuizAnswer> batchStudentQuizAnswer;
	
	public List<BatchStudentQuizAnswer> getBatchStudentQuizAnswer() {
		return batchStudentQuizAnswer;
	}

	public void setBatchStudentQuizAnswer(List<BatchStudentQuizAnswer> batchStudentQuizAnswer) {
		this.batchStudentQuizAnswer = batchStudentQuizAnswer;
	}
	
	public Long getIdBatchStudentQuizQuestion() {
		return idBatchStudentQuizQuestion;
	}

	public void setIdBatchStudentQuizQuestion(Long idBatchStudentQuizQuestion) {
		this.idBatchStudentQuizQuestion = idBatchStudentQuizQuestion;
	}

	public Long getIdBatchStudentQuiz() {
		return idBatchStudentQuiz;
	}

	public void setIdBatchStudentQuiz(Long idBatchStudentQuiz) {
		this.idBatchStudentQuiz = idBatchStudentQuiz;
	}

	public Long getIdBatchQuizQuestion() {
		return idBatchQuizQuestion;
	}

	public void setIdBatchQuizQuestion(Long idBatchQuizQuestion) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
	}

	public boolean getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(boolean correctFlag) {
		this.correctFlag = correctFlag;
	}

	public BatchStudentQuizQuestion(Long idBatchStudentQuizQuestion, Long idBatchStudentQuiz, Long idBatchQuizQuestion,
			boolean correctFlag) {
		super();
		this.idBatchStudentQuizQuestion = idBatchStudentQuizQuestion;
		this.idBatchStudentQuiz = idBatchStudentQuiz;
		this.idBatchQuizQuestion = idBatchQuizQuestion;
		this.correctFlag = correctFlag;
	}

	public BatchStudentQuizQuestion() {
	}

}
