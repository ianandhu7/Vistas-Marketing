package co.vistafoundation.vlearning.quiz.model;

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
 * 
 * @author sarfaraz
 *  
 */

@Entity
@Table(name = "STUDENT_OFFLINE_QUIZ_ANSWER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentOfflineQuizAnswer extends UserDateAudit implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_OFFLINE_QUIZ_ANSWER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentOfflineQuizAnswer;

	@Column(name = "FIELD_ID")
	private Long fieldId;

	@Column(name = "TEXT_FIELD_VALUE")
	private String textFieldValue;

	@Column(name = "idSTUDENT_OFFLINE_QUIZ_QUESTION")
	private Long idStudentOfflineQuizQuestion;

	public Long getIdStudentOfflineQuizAnswer() {
		return idStudentOfflineQuizAnswer;
	}

	public void setIdStudentOfflineQuizAnswer(Long idStudentOfflineQuizAnswer) {
		this.idStudentOfflineQuizAnswer = idStudentOfflineQuizAnswer;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}

	public Long getIdStudentOfflineQuizQuestion() {
		return idStudentOfflineQuizQuestion;
	}

	public void setIdStudentOfflineQuizQuestion(Long idStudentOfflineQuizQuestion) {
		this.idStudentOfflineQuizQuestion = idStudentOfflineQuizQuestion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StudentOfflineQuizAnswer(Long fieldId, String textFieldValue,Long idStudentOfflineQuizQuestion) {
		super();
		this.fieldId = fieldId;
		this.textFieldValue = textFieldValue;
		this.idStudentOfflineQuizQuestion = idStudentOfflineQuizQuestion;
	}
	
	public StudentOfflineQuizAnswer() {
		super();
	}
	
}
