package co.vistafoundation.vlearning.analytics.model;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/*
 * @author Abdul Elahi
 * 
 * this entity is created for hourly log the user activity to db
 */

@Entity
@Table(name = "user_activity_timely_logs")
public class UserActivityTimelyLog implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_activity_timely_logs")
    private Long idUserActivityTimelyLogs;

    @Column(name = "partition_key")
    private String partitionKey;  //yyyyMMddHHmm

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "mobile_client")
    private Integer mobileClient;

    @Column(name = "mobile_client_idle")
    private Integer mobileClientIdle;

    @Column(name = "total_metrics")
    private Integer totalMetric;

    @Column(name = "web_client")
    private Integer webClient;

    @Column(name = "web_client_idle")
    private Integer webClientIdle;

    public UserActivityTimelyLog() {

    }

	public UserActivityTimelyLog(Long idUserActivityTimelyLogs, String partitionKey, Date createdAt,
			Integer mobileClient, Integer mobileClientIdle, Integer totalMetric, Integer webClient,
			Integer webClientIdle) {
		super();
		this.idUserActivityTimelyLogs = idUserActivityTimelyLogs;
		this.partitionKey = partitionKey;
		this.createdAt = createdAt;
		this.mobileClient = mobileClient;
		this.mobileClientIdle = mobileClientIdle;
		this.totalMetric = totalMetric;
		this.webClient = webClient;
		this.webClientIdle = webClientIdle;
	}

	public UserActivityTimelyLog(String partitionKey, Date createdAt, Integer mobileClient, Integer mobileClientIdle,
			Integer totalMetric, Integer webClient, Integer webClientIdle) {
		super();
		this.partitionKey = partitionKey;
		this.createdAt = createdAt;
		this.mobileClient = mobileClient;
		this.mobileClientIdle = mobileClientIdle;
		this.totalMetric = totalMetric;
		this.webClient = webClient;
		this.webClientIdle = webClientIdle;
	}

	public Long getIdUserActivityTimelyLogs() {
		return idUserActivityTimelyLogs;
	}

	public void setIdUserActivityTimelyLogs(Long idUserActivityTimelyLogs) {
		this.idUserActivityTimelyLogs = idUserActivityTimelyLogs;
	}

	public String getPartitionKey() {
		return partitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getMobileClient() {
		return mobileClient;
	}

	public void setMobileClient(Integer mobileClient) {
		this.mobileClient = mobileClient;
	}

	public Integer getMobileClientIdle() {
		return mobileClientIdle;
	}

	public void setMobileClientIdle(Integer mobileClientIdle) {
		this.mobileClientIdle = mobileClientIdle;
	}

	public Integer getTotalMetric() {
		return totalMetric;
	}

	public void setTotalMetric(Integer totalMetric) {
		this.totalMetric = totalMetric;
	}

	public Integer getWebClient() {
		return webClient;
	}

	public void setWebClient(Integer webClient) {
		this.webClient = webClient;
	}

	public Integer getWebClientIdle() {
		return webClientIdle;
	}

	public void setWebClientIdle(Integer webClientIdle) {
		this.webClientIdle = webClientIdle;
	}

	@Override
	public String toString() {
		return "UserActivityTimelyLog [idUserActivityTimelyLogs=" + idUserActivityTimelyLogs + ", partitionKey="
				+ partitionKey + ", createdAt=" + createdAt + ", mobileClient=" + mobileClient + ", mobileClientIdle="
				+ mobileClientIdle + ", totalMetric=" + totalMetric + ", webClient=" + webClient + ", webClientIdle="
				+ webClientIdle + "]";
	}

}
