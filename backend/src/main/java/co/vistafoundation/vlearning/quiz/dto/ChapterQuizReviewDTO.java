package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizAnswer;

public class ChapterQuizReviewDTO {
	private Long idQuizQuestion;
	private String questionText;
	private String questionType;
	private String answerText;
	private boolean correctValueFlag;
	private List<StudentChapterQuizAnswer> studentAnswer = new ArrayList<StudentChapterQuizAnswer>();
	private List<Answer> correctAnswer = new ArrayList<Answer>();

	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}

	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public boolean isCorrectValueFlag() {
		return correctValueFlag;
	}

	public void setCorrectValueFlag(boolean correctValueFlag) {
		this.correctValueFlag = correctValueFlag;
	}

	public List<StudentChapterQuizAnswer> getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(List<StudentChapterQuizAnswer> studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public List<Answer> getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(List<Answer> correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ChapterQuizReviewDTO(Long idQuizQuestion, String questionText, String questionType, String answerText,
			boolean correctValueFlag, List<StudentChapterQuizAnswer> studentAnswer, List<Answer> correctAnswer) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.questionText = questionText;
		this.questionType = questionType;
		this.answerText = answerText;
		this.correctValueFlag = correctValueFlag;
		this.studentAnswer = studentAnswer;
		this.correctAnswer = correctAnswer;
	}

	public ChapterQuizReviewDTO() {
	}

}
