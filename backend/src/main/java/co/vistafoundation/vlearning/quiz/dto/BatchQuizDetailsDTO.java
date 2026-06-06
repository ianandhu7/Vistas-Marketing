package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;
import java.util.List;

public class BatchQuizDetailsDTO {
	private Instant quizDate;
	private String batchQuizName;
	private String subject;
	private String classStandard;
	private int totalNoOfStudent;
	private int studentAttempted;
	private List<QuizScoreDTO> quizScoreList;
	
	public Instant getQuizDate() {
		return quizDate;
	}
	public void setQuizDate(Instant quizDate) {
		this.quizDate = quizDate;
	}
	public String getBatchQuizName() {
		return batchQuizName;
	}
	public void setBatchQuizName(String batchQuizName) {
		this.batchQuizName = batchQuizName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getClassStandard() {
		return classStandard;
	}
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}
	
	public int getTotalNoOfStudent() {
		return totalNoOfStudent;
	}
	public void setTotalNoOfStudent(int totalNoOfStudent) {
		this.totalNoOfStudent = totalNoOfStudent;
	}
	public int getStudentAttempted() {
		return studentAttempted;
	}
	public void setStudentAttempted(int studentAttempted) {
		this.studentAttempted = studentAttempted;
	}
	
	public List<QuizScoreDTO> getQuizScoreList() {
		return quizScoreList;
	}
	public void setQuizScoreList(List<QuizScoreDTO> quizScoreList) {
		this.quizScoreList = quizScoreList;
	}
	
}
