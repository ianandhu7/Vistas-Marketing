package co.vistafoundation.vlearning.quiz.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.model.Answer;

public class AnswerRequestDTO {

	private Long idQuizQuestion;
	private List<Answer> answer;


	public Long getIdQuizQuestion() {
		return idQuizQuestion;
	}
	
	public void setIdQuizQuestion(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}
	public List<Answer> getAnswer() {
		return answer;
	}
	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}
	public AnswerRequestDTO(Long idQuizQuestion, List<Answer> answer) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.answer = answer;
	}

	public AnswerRequestDTO() {
		super();
		
	}

}
