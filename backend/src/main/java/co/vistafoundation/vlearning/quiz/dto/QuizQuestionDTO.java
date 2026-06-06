package co.vistafoundation.vlearning.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizAnswer;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuizAnswer;

public class QuizQuestionDTO {
	private Long idQuizQuestion;
	private String questionText;
	private String questionType;
	private String answerText;
	private boolean correctValueFlag;
	private List<StudentSubjectQuizAnswer> studentAnswer = new ArrayList<StudentSubjectQuizAnswer>();
	private List<Answer> correctAnswer = new ArrayList<Answer>();
	
	private List<StudentChapterQuizAnswer> studentChapterAnswer = new ArrayList<StudentChapterQuizAnswer>();

	public List<StudentChapterQuizAnswer> getStudentChapterAnswer() {
		return studentChapterAnswer;
	}

	public void setStudentChapterAnswer(List<StudentChapterQuizAnswer> studentChapterAnswer) {
		this.studentChapterAnswer = studentChapterAnswer;
	}

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

	public List<StudentSubjectQuizAnswer> getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(List<StudentSubjectQuizAnswer> studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public List<Answer> getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(List<Answer> correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

}
