package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/
@Entity
@Table(name = "BATCH_STUDENT_QUIZ_ANSWER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchStudentQuizAnswer extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idBATCH_STUDENT_QUIZ_ANSWER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBatchStudentQuizAnswer;

	@Column(name = "IdBATCH_STUDENT_QUIZ_QUESTION")
	private Long idBatchStudentQuizQuestion;

	@Column(name = "FIELD_ID")
	private String fieldId;

	@Column(name = "TEXT_FIELD_VALUE")
	private String textFieldValue;

	public Long getIdBatchStudentQuizAnswer() {
		return idBatchStudentQuizAnswer;
	}

	public void setIdBatchStudentQuizAnswer(Long idBatchStudentQuizAnswer) {
		this.idBatchStudentQuizAnswer = idBatchStudentQuizAnswer;
	}

	public Long getIdBatchStudentQuizQuestion() {
		return idBatchStudentQuizQuestion;
	}

	public void setIdBatchStudentQuizQuestion(Long idBatchStudentQuizQuestion) {
		this.idBatchStudentQuizQuestion = idBatchStudentQuizQuestion;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}


	public BatchStudentQuizAnswer(Long idBatchStudentQuizAnswer, Long idBatchStudentQuizQuestion, String fieldId,
			String textFieldValue) {
		super();
		this.idBatchStudentQuizAnswer = idBatchStudentQuizAnswer;
		this.idBatchStudentQuizQuestion = idBatchStudentQuizQuestion;
		this.fieldId = fieldId;
		this.textFieldValue = textFieldValue;
	}
	public BatchStudentQuizAnswer() {
	}

}
