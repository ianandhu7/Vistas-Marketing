package co.vistafoundation.vlearning.subscription.dto;

public class TotalVideoWatchDTO {

	private Long totalVideoCount;
	private Long totalWatchHours;

	// Constructor
	public TotalVideoWatchDTO(Long totalVideoCount, Long totalWatchHours) {
		this.totalVideoCount = totalVideoCount;
		this.totalWatchHours = totalWatchHours;
	}

	// Getters and Setters
	public Long getTotalVideoCount() {
		return totalVideoCount;
	}

	public void setTotalVideoCount(Long totalVideoCount) {
		this.totalVideoCount = totalVideoCount;
	}

	public String getTotalWatchHours() {
		long hours = totalWatchHours / 3600;
        long minutes = (totalWatchHours % 3600) / 60;
     
        return hours + "h " + minutes + "m ";
	}
        
	public void setTotalWatchHours(Long totalWatchHours) {
		this.totalWatchHours = totalWatchHours;
	}

	@Override
	public String toString() {
		return "VideoWatchStatisticsDTO{" + "totalVideoCount=" + totalVideoCount + ", totalWatchHours="
				+ totalWatchHours + '}';
	}

}
