/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;

/**
 * @author Naveen Kumar
 *
 */
public class StudentOfflineQuizResponseDTO {
	
	private Long idStudentOfflineQuiz;

	private Instant quizDate;
	
	private Float quizScore;

	private Long idSubject;
	
	private Long idSubjectChapter;
	
	private Long idStudentSubscr;

	private Long idOfflineVideoCourse;
	
	private String Heading;
	
	
	/**
	 * @return the idStudentOfflineQuiz
	 */
	public Long getIdStudentOfflineQuiz() {
		return idStudentOfflineQuiz;
	}

	/**
	 * @param idStudentOfflineQuiz the idStudentOfflineQuiz to set
	 */
	public void setIdStudentOfflineQuiz(Long idStudentOfflineQuiz) {
		this.idStudentOfflineQuiz = idStudentOfflineQuiz;
	}

	/**
	 * @return the quizDate
	 */
	public Instant getQuizDate() {
		return quizDate;
	}

	/**
	 * @param quizDate the quizDate to set
	 */
	public void setQuizDate(Instant quizDate) {
		this.quizDate = quizDate;
	}

	/**
	 * @return the quizScore
	 */
	public Float getQuizScore() {
		return quizScore;
	}

	/**
	 * @param quizScore the quizScore to set
	 */
	public void setQuizScore(Float quizScore) {
		this.quizScore = quizScore;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	/**
	 * @return the idSubjectChapter
	 */
	public Long getIdSubjectChapter() {
		return idSubjectChapter;
	}

	/**
	 * @param idSubjectChapter the idSubjectChapter to set
	 */
	public void setIdSubjectChapter(Long idSubjectChapter) {
		this.idSubjectChapter = idSubjectChapter;
	}

	/**
	 * @return the idStudentSubscr
	 */
	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	/**
	 * @param idStudentSubscr the idStudentSubscr to set
	 */
	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}

	/**
	 * @return the idOfflineVideoCourse
	 */
	public Long getIdOfflineVideoCourse() {
		return idOfflineVideoCourse;
	}

	/**
	 * @param idOfflineVideoCourse the idOfflineVideoCourse to set
	 */
	public void setIdOfflineVideoCourse(Long idOfflineVideoCourse) {
		this.idOfflineVideoCourse = idOfflineVideoCourse;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() {
		return Heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		Heading = heading;
	}

	
	
}
