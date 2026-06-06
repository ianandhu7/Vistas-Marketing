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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;


/**
 * @author NaveenKumar
 * 
 **/
@Entity
@Table(name = "BATCH_QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchQuizQuestion extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idBATCH_QUIZ_QUESTION",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBatchQuizQuestion;	
	
	@Column(name = "QUESTION_TYPE", length = 45)
	private String questionType;
	
	@Column(name = "QUESTION_TEXT", length = 45)
	private String questionText;
	
	@Column(name = "ANSWER_TEXT", length = 45)
	private String answerText;
	
	@Column(name = "DIAGRAM_LOC", length = 1000)
	private String diagramLoc;
	
	@Column(name = "NUMBER_OPTIONS", length = 1000)
	private Long numberOptions;	
	
	@ManyToOne()
	@JoinColumn(name = "idBATCH_QUIZ_META" , referencedColumnName = "idBATCH_QUIZ_META", nullable =false)
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private BatchQuizMeta batchQuizMeta;	
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBATCH_QUIZ_QUESTION", referencedColumnName = "idBATCH_QUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private List<BatchQuizAnwser> batchanswers;	
	
	

	public Long getIdBatchQuizQuestion() {
		return idBatchQuizQuestion;
	}

	public void setIdBatchQuizQuestion(Long idBatchQuizQuestion) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
	}



	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public String getDiagramLoc() {
		return diagramLoc;
	}

	public void setDiagramLoc(String diagramLoc) {
		this.diagramLoc = diagramLoc;
	}

	public Long getNumberOptions() {
		return numberOptions;
	}

	public void setNumberOptions(Long numberOptions) {
		this.numberOptions = numberOptions;
	}

	public BatchQuizMeta getBatchQuizMeta() {
		return batchQuizMeta;
	}
	
	public void setBatchQuizMeta(BatchQuizMeta batchQuizMeta) {
		this.batchQuizMeta = batchQuizMeta;
	}

	public List<BatchQuizAnwser> getBatchanswers() {
		return batchanswers;
	}

	public void setBatchanswers(List<BatchQuizAnwser> batchanswers) {
		this.batchanswers = batchanswers;
	}
	
	
	

	public BatchQuizQuestion(String questionType, String questionText, String answerText, String diagramLoc,
			Long numberOptions, BatchQuizMeta batchQuizMeta, List<BatchQuizAnwser> batchanswers) {
		super();
		this.questionType = questionType;
		this.questionText = questionText;
		this.answerText = answerText;
		this.diagramLoc = diagramLoc;
		this.numberOptions = numberOptions;
		this.batchQuizMeta = batchQuizMeta;
		this.batchanswers = batchanswers;
	}

	public BatchQuizQuestion() {
	}

}
