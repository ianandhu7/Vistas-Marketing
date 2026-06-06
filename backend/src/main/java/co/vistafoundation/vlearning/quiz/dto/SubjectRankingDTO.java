/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;

/**
 * @author NaveenKumar
 *
 */
public class SubjectRankingDTO {

	private Long idStudentSubscr;

	private Float quizScore;

	private Long idStudentSubjectQuiz;

	private Long idClassStandard;

	private Long idSyllabus;

	private Long idState;

	private Long idSubject;

	private Long idVlUser;

	private Long idProduct;

	private Instant attemptedDate;

	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}

	public Float getQuizScore() {
		return quizScore;
	}

	public void setQuizScore(Float quizScore) {
		this.quizScore = quizScore;
	}

	public Long getIdStudentSubjectQuiz() {
		return idStudentSubjectQuiz;
	}

	public void setIdStudentSubjectQuiz(Long idStudentSubjectQuiz) {
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public Long getIdState() {
		return idState;
	}

	public void setIdState(Long idState) {
		this.idState = idState;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	public SubjectRankingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Instant getAttemptedDate() {
		return attemptedDate;
	}

	public void setAttemptedDate(Instant attemptedDate) {
		this.attemptedDate = attemptedDate;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public SubjectRankingDTO(Long idStudentSubscr, Float quizScore, Long idStudentSubjectQuiz, Long idClassStandard,
			Long idSyllabus, Long idState, Long idSubject, Long idVlUser, Long idProduct, Instant attemptedDate) {
		super();
		this.idStudentSubscr = idStudentSubscr;
		this.quizScore = quizScore;
		this.idStudentSubjectQuiz = idStudentSubjectQuiz;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idState = idState;
		this.idSubject = idSubject;
		this.idVlUser = idVlUser;
		this.idProduct = idProduct;
		this.attemptedDate = attemptedDate;
	}

	@Override
	public String toString() {
		return "SubjectRankingDTO [idStudentSubscr=" + idStudentSubscr + ", quizScore=" + quizScore
				+ ", idStudentSubjectQuiz=" + idStudentSubjectQuiz + "]";
	}

}
