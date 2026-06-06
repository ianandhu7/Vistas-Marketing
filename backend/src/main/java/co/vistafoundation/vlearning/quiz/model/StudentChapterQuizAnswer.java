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
 * updated by @author NaveenKumar 
 */

@Entity
@Table(name = "STUDENT_CHAPTER_QUIZ_ANSWER")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentChapterQuizAnswer extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idSTUDENT_CHAPTER_QUIZ_ANSWER")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentChapterQuizAnswer;

	@Column(name = "FIELD_ID")
	private String fieldId;

	@Column(name = "TEXT_FIELD_VALUE")
	private String textFieldValue;

	@Column(name = "idSTUDENT_CHAPTER_QUIZ_QUESTION")
	private Long idStudentChapterQuizQuestion;

	public Long getIdStudentChapterQuizAnswer() {
		return idStudentChapterQuizAnswer;
	}

	public void setIdStudentChapterQuizAnswer(Long idStudentChapterQuizAnswer) {
		this.idStudentChapterQuizAnswer = idStudentChapterQuizAnswer;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String string) {
		this.fieldId = string;
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}

	public Long getIdStudentChapterQuizQuestion() {
		return idStudentChapterQuizQuestion;
	}

	public void setIdStudentChapterQuizQuestion(Long idStudentChapterQuizQuestion) {
		this.idStudentChapterQuizQuestion = idStudentChapterQuizQuestion;
	}

	public StudentChapterQuizAnswer(String fieldId, String textFieldValue, Long idStudentChapterQuizQuestion) {
		super();
		this.fieldId = fieldId;
		this.textFieldValue = textFieldValue;
		this.idStudentChapterQuizQuestion = idStudentChapterQuizQuestion;
	}

	public StudentChapterQuizAnswer() {
		super();

	}

}
