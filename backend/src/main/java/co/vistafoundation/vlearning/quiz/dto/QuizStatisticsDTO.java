package co.vistafoundation.vlearning.quiz.dto;

public class QuizStatisticsDTO {

	private Long totalQuizCount;
	private Double averageQuizScore; // Changed from Integer to Double

	/**
	 * @param totalQuizCount
	 * @param averageQuizScore
	 */
	public QuizStatisticsDTO(Long totalQuizCount, Double averageQuizScore) {
		super();
		this.totalQuizCount = totalQuizCount;
		this.averageQuizScore = averageQuizScore;
	}

	/**
	 * @return the totalQuizCount
	 */
	public Long getTotalQuizCount() {
		return totalQuizCount;
	}

	/**
	 * @param totalQuizCount the totalQuizCount to set
	 */
	public void setTotalQuizCount(Long totalQuizCount) {
		this.totalQuizCount = totalQuizCount;
	}

	/**
	 * @return the averageQuizScore
	 */
	public String getAverageQuizScore() {
         return String.format("%.2f", averageQuizScore);
	}

	/**
	 * @param averageQuizScore the averageQuizScore to set
	 */
	public void setAverageQuizScore(Double averageQuizScore) {
		this.averageQuizScore = averageQuizScore;
	}

}
