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
 * @author Sajini
 * UPDATE @author NaveenKumar 
 *
 */

@Entity
@Table(name = "STUDENT_SUBJECT_QUIZ_ANSWER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentSubjectQuizAnswer extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_SUBJECT_QUIZ_ANSWER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentSubjectQuizAnswer;

	@Column(name = "FIELD_ID")
	private String fieldId;

	@Column(name = "TEXT_FIELD_VALUE")
	private String textFieldValue;

	@Column(name = "idSTUDENT_SUBJECT_QUIZ_QUESTION")
	private Long idStudentSubjectQuizQuestion;

	public Long getIdStudentSubjectQuizAnswer() {
		return idStudentSubjectQuizAnswer;
	}

	public void setIdStudentSubjectQuizAnswer(Long idStudentSubjectQuizAnswer) {
		this.idStudentSubjectQuizAnswer = idStudentSubjectQuizAnswer;
	}
	
	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}

	public Long getIdStudentSubjectQuizQuestion() {
		return idStudentSubjectQuizQuestion;
	}

	public void setIdStudentSubjectQuizQuestion(Long idStudentSubjectQuizQuestion) {
		this.idStudentSubjectQuizQuestion = idStudentSubjectQuizQuestion;
	}

	public StudentSubjectQuizAnswer(String fieldID, String textFieldValue, Long idStudentSubjectQuizQuestion) {
		super();
		this.fieldId = fieldID;
		this.textFieldValue = textFieldValue;
		this.idStudentSubjectQuizQuestion = idStudentSubjectQuizQuestion;
	}

	public StudentSubjectQuizAnswer() {
		super();

	}

}
