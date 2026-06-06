package co.vistafoundation.vlearning.quiz.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuizAnswer;

public class StudentOfflineQuizAnswerRequestDTO {

	private Long idQuizQuestion;
	private List<StudentOfflineQuizAnswer> answer;
	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}
	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}
	public List<StudentOfflineQuizAnswer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<StudentOfflineQuizAnswer> answer) {
		this.answer = answer;
	}
	public StudentOfflineQuizAnswerRequestDTO(Long idQuizQuestion, List<StudentOfflineQuizAnswer> answer) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.answer = answer;
	}
}
