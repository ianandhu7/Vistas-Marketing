package co.vistafoundation.vlearning.batch.dto;

import java.util.List;

import co.vistafoundation.vlearning.batch.model.BatchQuizAnwser;

public class BatchQuizAnswerRequestDTO {
	private Long idBatchQuizQuestion;
	private List<BatchQuizAnwser> batchAnswer;

	public BatchQuizAnswerRequestDTO(Long idBatchQuizQuestion, List<BatchQuizAnwser> batchanswers) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
		this.batchAnswer = batchanswers;
	}

	public Long getIdBatchQuizQuestion() {
		return idBatchQuizQuestion;
	}

	public void setIdBatchQuizQuestion(Long idBatchQuizQuestion) {
		this.idBatchQuizQuestion = idBatchQuizQuestion;
	}

	public List<BatchQuizAnwser> getBatchAnswer() {
		return batchAnswer;
	}

	public void setBatchAnswer(List<BatchQuizAnwser> batchAnswer) {
		this.batchAnswer = batchAnswer;
	}

}
