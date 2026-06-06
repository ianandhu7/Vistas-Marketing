package co.vistafoundation.vlearning.batch.dto;

import java.time.Instant;

public class BatchTestNotificationDTO {
	
	private String batchQuizName;
	private Instant quizDate;
	private Long idBatchQuizAssignment;
	private Long idBatchStudentQuiz;
	private Boolean takeTest;
		
	
	
	
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
	public String getBatchQuizName() {
		return batchQuizName;
	}
	public void setBatchQuizName(String batchQuizName) {
		this.batchQuizName = batchQuizName;
	}
	public Instant getQuizDate() {
		return quizDate;
	}
	public void setQuizDate(Instant quizDate) {
		this.quizDate = quizDate;
	}
	public Boolean getTakeTest() {
		return takeTest;
	}
	public void setTakeTest(Boolean takeTest) {
		this.takeTest = takeTest;
	}
	
	

}
