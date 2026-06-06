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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
/**
 * 
 * @author Sajini
 *
 */

@Entity
@Table(name = "QUIZ_QUESTION")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class QuizQuestion extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idQUIZ_QUESTION")
	private Long idQuizQuestion;

	@Column(name = "QUESTION_TEXT")
	@Lob
	@NotNull
	private String questionText;
	
	@Column(name = "QUESTION_ACTIVE_FLAG")
	private boolean questionActiveFlag;

	@Column(name = "ANSWER_TEXT")
	@Lob
	private String answerText;

	@Column(name = "QUESTION_TYPE")
	@NotNull
	private String questionType;
	
	@Column(name = "NUMBER_OPTIONS", length = 1000)
	private Long numberOptions;	
	
	@Column(name="DIAGRAM_LOC")
	private String diagramLoc;

	@ManyToOne()
	@JoinColumn(name = "idQUIZ" , referencedColumnName = "idQUIZ", nullable =false)
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private Quiz quiz;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idQUIZ_QUESTION", referencedColumnName = "idQUIZ_QUESTION")
	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
	private List<Answer> answers;
	
	
	@Column(name="QUESTION_TITLE")
	private String questionTitle;
	
	@Column(name="MARKS")
	private Short marks;
	

	public Long getNumberOptions() {
		return numberOptions;
	}

	public void setNumberOptions(Long numberOptions) {
		this.numberOptions = numberOptions;
	}

	public String getQuestionText() {
		return questionText;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
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

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	

	public String getDiagramLoc() {
		return diagramLoc;
	}

	public void setDiagramLoc(String diagramLoc) {
		this.diagramLoc = diagramLoc;
	}	

	public boolean getQuestionActiveFlag() {
		return questionActiveFlag;
	}

	public void setQuestionActiveFlag(boolean questionActiveFlag) {
		this.questionActiveFlag = questionActiveFlag;
	}
	

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public Short getMarks() {
		return marks;
	}

	public void setMarks(Short marks) {
		this.marks = marks;
	}

	public QuizQuestion(@NotNull String questionText, String answerText, @NotNull String questionType,
			Long numberOptions, String diagramLoc, Quiz quiz, List<Answer> answers, boolean questionActiveFlag) {
		super();
		this.questionText = questionText;
		this.answerText = answerText;
		this.questionType = questionType;
		this.numberOptions = numberOptions;
		this.diagramLoc = diagramLoc;
		this.quiz = quiz;
		this.answers = answers;
		this.questionActiveFlag=questionActiveFlag;
	}
	
	

	public QuizQuestion(@NotNull String questionText, boolean questionActiveFlag, String answerText,
			@NotNull String questionType, Long numberOptions, String diagramLoc, Quiz quiz, List<Answer> answers,
			String questionTitle, Short marks) {
		super();
		this.questionText = questionText;
		this.questionActiveFlag = questionActiveFlag;
		this.answerText = answerText;
		this.questionType = questionType;
		this.numberOptions = numberOptions;
		this.diagramLoc = diagramLoc;
		this.quiz = quiz;
		this.answers = answers;
		this.questionTitle = questionTitle;
		this.marks = marks;
	}

	public QuizQuestion() {
		super();

	}

}
