package co.vistafoundation.vlearning.batch.dto;

import java.util.ArrayList;
import java.util.List;

import co.vistafoundation.vlearning.batch.model.BatchQuizAnwser;
import co.vistafoundation.vlearning.batch.model.BatchStudentQuizAnswer;

public class BatchQuizQuestionDTO {
	private Long idBatchQuizQuestion;
	private String questionText;
	private String questionType;
	private String answerText;
	private boolean correctValueFlag;
	private List<BatchStudentQuizAnswer> batchStudentQuizAnswer = new ArrayList<BatchStudentQuizAnswer>();
	private List<BatchQuizAnwser> correctAnswer = new ArrayList<BatchQuizAnwser>();

	public Long getIdBatchQuizQuestion() {
		return idBatchQuizQuestion;
	}

	public void setIdBatchQuizQuestion(Long idBatchQuizQuestion) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
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

	public boolean isCorrectValueFlag() {
		return correctValueFlag;
	}

	public void setCorrectValueFlag(boolean correctValueFlag) {
		this.correctValueFlag = correctValueFlag;
	}

	public List<BatchStudentQuizAnswer> getBatchStudentQuizAnswer() {
		return batchStudentQuizAnswer;
	}

	public void setBatchStudentQuizAnswer(List<BatchStudentQuizAnswer> batchStudentQuizAnswer) {
		this.batchStudentQuizAnswer = batchStudentQuizAnswer;
	}

	public List<BatchQuizAnwser> getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(List<BatchQuizAnwser> correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

}
