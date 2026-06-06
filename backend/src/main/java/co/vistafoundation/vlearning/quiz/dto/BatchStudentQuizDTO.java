package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;

public class BatchStudentQuizDTO {
	@NotNull
	private Long idBatchStudentQuiz;
	
	@NotNull
	private Long idBatchQuizAssignment;
	
	@NotNull
	private Long idStudentSubscr;
	@NotNull
	private List<BatchQuizQuestion> batchQuizQuestions = new ArrayList<BatchQuizQuestion>();
	
	public Long getIdBatchQuizAssignment() {
		return idBatchQuizAssignment;
	}
	public void setIdBatchQuizAssignment(Long idBatchQuizAssignment) {
		this.idBatchQuizAssignment = idBatchQuizAssignment;
	}
	public Long getIdBatchStudentQuiz() {
		return idBatchStudentQuiz;
	}
	public void setIdBatchStudentQuiz(Long idBatchStudentQuiz) {
		this.idBatchStudentQuiz = idBatchStudentQuiz;
	}
	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}
	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}
	public List<BatchQuizQuestion> getBatchQuizQuestions() {
		return batchQuizQuestions;
	}
	public void setBatchQuizQuestions(List<BatchQuizQuestion> batchQuizQuestions) {
		this.batchQuizQuestions = batchQuizQuestions;
	}

}
