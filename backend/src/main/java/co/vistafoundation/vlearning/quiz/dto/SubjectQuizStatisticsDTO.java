package co.vistafoundation.vlearning.quiz.dto;

public class SubjectQuizStatisticsDTO {

	private String subjectName;
	private Long totalQuizCount;
	private Float score;

	/**
	 * @param subjectName
	 * @param totalQuizCount
	 * @param score
	 */
	public SubjectQuizStatisticsDTO(String subjectName, Long totalQuizCount, Float score) {
		super();
		this.subjectName = subjectName;
		this.totalQuizCount = totalQuizCount;
		this.score = score;
	}

	// Getters and Setters
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getTotalQuizCount() {
		return totalQuizCount;
	}

	public void setTotalQuizCount(Long totalQuizCount) {
		this.totalQuizCount = totalQuizCount;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return String.format("%.0f", score);
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SubjectQuizStatisticsDTO{" + "subjectName='" + subjectName + '\'' + ", totalQuizCount=" + totalQuizCount
				+ '}';
	}
}
