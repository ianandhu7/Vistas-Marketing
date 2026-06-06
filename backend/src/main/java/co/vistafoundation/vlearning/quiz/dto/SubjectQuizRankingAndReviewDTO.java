/**
 * 
 */
package co.vistafoundation.vlearning.quiz.dto;

import java.time.Instant;
import java.util.List;

/**
 * 
 * @author NaveenKumar
 *
 */
public class SubjectQuizRankingAndReviewDTO {

	List<VCTQuizOverAllReviewDTO> overAllReview;

	Integer ranking;

	Long idSubject;

	String subjectName;

	String color;

	Float overAllTotalMarksScored;

	Float overAllTotalMarks;

	Instant attemptedOn;

	String imageUrl;

	public List<VCTQuizOverAllReviewDTO> getOverAllReview() {
		return overAllReview;
	}

	public void setOverAllReview(List<VCTQuizOverAllReviewDTO> overAllReview) {
		this.overAllReview = overAllReview;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Float getOverAllTotalMarksScored() {
		return overAllTotalMarksScored;
	}

	public void setOverAllTotalMarksScored(Float overAllTotalMarksScored) {
		this.overAllTotalMarksScored = overAllTotalMarksScored;
	}

	public Float getOverAllTotalMarks() {
		return overAllTotalMarks;
	}

	public void setOverAllTotalMarks(Float overAllTotalMarks) {
		this.overAllTotalMarks = overAllTotalMarks;
	}

	public Instant getAttemptedOn() {
		return attemptedOn;
	}

	public void setAttemptedOn(Instant attemptedOn) {
		this.attemptedOn = attemptedOn;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public SubjectQuizRankingAndReviewDTO(List<VCTQuizOverAllReviewDTO> overAllReview, Integer ranking, Long idSubject,
			String subjectName, String color, Float overAllTotalMarksScored, Float overAllTotalMarks,
			Instant attemptedOn, String imageUrl) {
		super();
		this.overAllReview = overAllReview;
		this.ranking = ranking;
		this.idSubject = idSubject;
		this.subjectName = subjectName;
		this.color = color;
		this.overAllTotalMarksScored = overAllTotalMarksScored;
		this.overAllTotalMarks = overAllTotalMarks;
		this.attemptedOn = attemptedOn;
		this.imageUrl = imageUrl;
	}

	public SubjectQuizRankingAndReviewDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
