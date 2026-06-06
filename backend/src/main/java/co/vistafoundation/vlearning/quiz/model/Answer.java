package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

@Entity
@Table(name = "ANSWER",uniqueConstraints= @UniqueConstraint(columnNames={"FIELD_ID", "idQUIZ_QUESTION"}))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class Answer extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name ="idANSWER")
	private Long idAnswer;
	
	@Column(name ="TEXT_FIELD_VALUE")
	private String textFieldValue;
	
	@Column(name= "CORRECT_VALUE_FLAG")
	private boolean correctValueFlag;
	
	@Column(name="FIELD_ID")
	private String fieldId;
	
	@Column(name ="idQUIZ_QUESTION")
	private Long idQuizQuestion;


//	@ManyToOne ()
//	@JoinColumn(name = "idQUIZ_QUESTION" , referencedColumnName = "idQUIZ_QUESTION")
//	private QuizQuestion quizQuestion;

	public String getFieldId() {
		return fieldId;
	}

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Long getIdAnswer() {
		return idAnswer;
	}

	public void setIdAnswer(Long idAnswer) {
		this.idAnswer = idAnswer;
	}


	public String getTextFieldValue() {
		return textFieldValue;
	}

	public void setTextFieldValue(String textFieldValue) {
		this.textFieldValue = textFieldValue;
	}

	public boolean getCorrectValueFlag() {
		return correctValueFlag;
	}

	public void setCorrectValueFlag(boolean correctValueFlag) {
		this.correctValueFlag = correctValueFlag;
	}

	public Answer(String textFieldValue, boolean correctValueFlag, String fieldId) {
		super();
		this.textFieldValue = textFieldValue;
		this.correctValueFlag = correctValueFlag;
		this.fieldId = fieldId;
	}

	public Answer() {
		super();
		
	}
	

}
