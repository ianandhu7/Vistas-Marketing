package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/***
 * 
 * @author meghana
 *
 */

@Entity
@Table(name = "STUDENT_CHAPTER_QUIZ_DETAIL")

public class StudentChapterQuizDetail extends UserDateAudit implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idSTUDENT_CHAPTER_QUIZ_DETAIL")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idStudentChapterQuizDetail;

	@Column(name = "idQUIZ_QUESTION", nullable = false)
	private Long idQuizQuestion;

	@Column(name = "ANSWER" ,  length =  45)
	private String answer;
	
	@Column(name = "CORRECT_FLAG")
	private Boolean correctFlag;
	
	@Column(name = "idSTUDENT_CHAPTER_QUIZ", nullable = false)
	private Long idStudentChapterQuiz;

	public Long getIdStudentChapterQuizDetail() {
		return idStudentChapterQuizDetail;
	}

	public void setIdStudentChapterQuizDetail(Long idStudentChapterQuizDetail) {
		this.idStudentChapterQuizDetail = idStudentChapterQuizDetail;
	}

	public Long getQuizSurId() {
		return idQuizQuestion;
	}

	public void setQuizSurId(Long idQuizQuestion) {
		this.idQuizQuestion = idQuizQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(Boolean correctFlag) {
		this.correctFlag = correctFlag;
	}

	public Long getIdStudentChapterQuiz() {
		return idStudentChapterQuiz;
	}

	public void setIdStudentChapterQuiz(Long idStudentChapterQuiz) {
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}

	public StudentChapterQuizDetail(Long idQuizQuestion, String answer, Boolean correctFlag, Long idStudentChapterQuiz) {
		super();
		this.idQuizQuestion = idQuizQuestion;
		this.answer = answer;
		this.correctFlag = correctFlag;
		this.idStudentChapterQuiz = idStudentChapterQuiz;
	}
	
	public StudentChapterQuizDetail() {
		
	}

}
