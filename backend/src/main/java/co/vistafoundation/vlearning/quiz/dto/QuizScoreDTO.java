package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;

public class QuizScoreDTO {

	private String studentName;
	private Float score;
	private Boolean quizCompleteFlag;
	private Instant attemptDate;
	private Long IdBatchStudentQuiz;
	private Long idStudentSubscription;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Boolean getQuizCompleteFlag() {
		return quizCompleteFlag;
	}
	public void setQuizCompleteFlag(Boolean quizCompleteFlag) {
		this.quizCompleteFlag = quizCompleteFlag;
	}
	public Instant getAttemptDate() {
		return attemptDate;
	}
	public void setAttemptDate(Instant attemptDate) {
		this.attemptDate = attemptDate;
	}
	public Long getIdBatchStudentQuiz() {
		return IdBatchStudentQuiz;
	}
	public void setIdBatchStudentQuiz(Long idBatchStudentQuiz) {
		IdBatchStudentQuiz = idBatchStudentQuiz;
	}
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}
}
