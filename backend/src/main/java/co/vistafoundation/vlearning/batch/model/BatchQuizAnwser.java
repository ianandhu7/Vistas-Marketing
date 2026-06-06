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
@Table(name = "BATCH_QUIZ_ANSWER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchQuizAnwser extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idBATCH_QUIZ_ANSWER", nullable = false)
	private Long idBatchQuizAnswer;

	@Column(name = "TEXT_FIELD_VALUE", length = 45)
	private String textFieldValue;

	@Column(name = "CORRECT_VALUE_FLAG")
	private Boolean correctValueFlag;

	@Column(name = "idBATCH_QUIZ_QUESTION")
	private Long idBatchQuizQuestion;

	@Column(name = "FIELD_ID")
	private String fieldId;

	public Long getIdBatchQuizAnswer() {
		return idBatchQuizAnswer;
	}

	public void setIdBatchQuizAnswer(Long idBatchQuizAnswer) {
		this.idBatchQuizAnswer = idBatchQuizAnswer;
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}

	public Boolean getCorrectValueFlag() {
		return correctValueFlag;
	}

	public void setCorrectValueFlag(Boolean correctValueFlag) {
		this.correctValueFlag = correctValueFlag;
	}

	public Long getIdBatchQuizQuestion() {
		return idBatchQuizQuestion;
	}

	public void setIdBatchQuizQuestion(Long idBatchQuizQuestion) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
	}


	public String getFieldId() {
		return fieldId;
	}


	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}


	/**
	 * @param textFieldValue
	 * @param correctValueFlag
	 * @param idBatchQuizQuestion
	 * @param fieldId
	 */

	public BatchQuizAnwser(String textFieldValue, Boolean correctValueFlag, Long idBatchQuizQuestion, String fieldId) {
		super();
		this.textFieldValue = textFieldValue;
		this.correctValueFlag = correctValueFlag;
		this.idBatchQuizQuestion = idBatchQuizQuestion;
		this.fieldId = fieldId;
	}

	public BatchQuizAnwser() {
	}

}
