package co.vistafoundation.vlearning.quiz.dto;

import java.util.List;

import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

public class ListOfQuestionRequestDTO {
	
	private List<QuizQuestion> questions;

	public List<QuizQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuizQuestion> questions) {
		this.questions = questions;
	}
	

}
