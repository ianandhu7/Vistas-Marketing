package co.vistafoundation.vlearning.subscription.dto;

public class StudentSummaryDTO {

	public String firstNmae;
	public Long totalVideoCount;
	public Long WatchedVideoCount;
	public Long totalQuizCount;
	public Long totalAttemptCount;
	/**
	 * @return the firstNmae
	 */
	public String getFirstNmae() {
		return firstNmae;
	}
	/**
	 * @param firstNmae the firstNmae to set
	 */
	public void setFirstNmae(String firstNmae) {
		this.firstNmae = firstNmae;
	}
	/**
	 * @return the totalVideoCount
	 */
	public Long getTotalVideoCount() {
		return totalVideoCount;
	}
	/**
	 * @param totalVideoCount the totalVideoCount to set
	 */
	public void setTotalVideoCount(Long totalVideoCount) {
		this.totalVideoCount = totalVideoCount;
	}
	/**
	 * @return the watchedVideoCount
	 */
	public Long getWatchedVideoCount() {
		return WatchedVideoCount;
	}
	/**
	 * @param watchedVideoCount the watchedVideoCount to set
	 */
	public void setWatchedVideoCount(Long watchedVideoCount) {
		WatchedVideoCount = watchedVideoCount;
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
	 * @return the totalAttemptCount
	 */
	public Long getTotalAttemptCount() {
		return totalAttemptCount;
	}
	/**
	 * @param totalAttemptCount the totalAttemptCount to set
	 */
	public void setTotalAttemptCount(Long totalAttemptCount) {
		this.totalAttemptCount = totalAttemptCount;
	}
	
	
	
}
