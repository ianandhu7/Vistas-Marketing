package co.vistafoundation.vlearning.analytics.dto;

public class UserActivityTimelyLogDTO {

	private Integer mobileClient;

	private Integer webClient;

	private Integer mobileClientIdle;

	private Integer webClientIdle;

	private Integer totalMetrics;

	private String createdAt;

	private Integer average;

	public Integer getMobileClient() {
		return mobileClient;
	}

	public void setMobileClient(Integer mobileClient) {
		this.mobileClient = mobileClient;
	}

	public Integer getWebClient() {
		return webClient;
	}

	public void setWebClient(Integer webClient) {
		this.webClient = webClient;
	}

	public Integer getMobileClientIdle() {
		return mobileClientIdle;
	}

	public void setMobileClientIdle(Integer mobileClientIdle) {
		this.mobileClientIdle = mobileClientIdle;
	}

	public Integer getWebClientIdle() {
		return webClientIdle;
	}

	public void setWebClientIdle(Integer webClientIdle) {
		this.webClientIdle = webClientIdle;
	}

	public Integer getTotalMetrics() {
		return totalMetrics;
	}

	public void setTotalMetrics(Integer totalMetrice) {
		this.totalMetrics = totalMetrice;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer averageInteger) {
		this.average = averageInteger;
	}

	@Override
	public String toString() {
		return "UserActivityTimelyLogDTO [ mobileClient=" + mobileClient
				+ ", webClient=" + webClient + ", mobileClientIdle=" + mobileClientIdle + ", webClientIdle="
				+ webClientIdle + ", totalMetrics=" + totalMetrics + ", createdAt=" + createdAt + ", averageInteger="
				+ average + "]";
	}

	public UserActivityTimelyLogDTO() {
		super();
	}

	public UserActivityTimelyLogDTO(Integer mobileClient, Integer webClient,
			Integer mobileClientIdle, Integer webClientIdle, Integer totalMetrics, String createdAt,
			Integer averageInteger) {
		super();
		
		this.mobileClient = mobileClient;
		this.webClient = webClient;
		this.mobileClientIdle = mobileClientIdle;
		this.webClientIdle = webClientIdle;
		this.totalMetrics = totalMetrics;
		this.createdAt = createdAt;
		this.average = averageInteger;
	}

}
