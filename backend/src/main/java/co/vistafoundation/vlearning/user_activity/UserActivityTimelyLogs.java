package co.vistafoundation.vlearning.user_activity;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/*
 * @author Abdul Elahi
 * 
 * this entity is created for hourly log the user activity to dynamo db
 */

@DynamoDBTable(tableName ="user_activity_timely_logs")
public class UserActivityTimelyLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DynamoDBHashKey
	private String idUserActivityLog;

	@DynamoDBAttribute
	private Integer mobileClient;
	
	@DynamoDBAttribute
	private Integer webClient;
	
	@DynamoDBAttribute
	private Integer mobileClientIdle;
	
	@DynamoDBAttribute
	private Integer webClientIdle;
	
	@DynamoDBAttribute
	private Integer totalMetrice;
	
	@DynamoDBAttribute
	private String createdAt;

	public String getIdUserActivityLog() {
		return idUserActivityLog;
	}

	public void setIdUserActivityLog(String idUserActivityLog) {
		this.idUserActivityLog = idUserActivityLog;
	}

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

	public Integer getTotalMetrice() {
		return totalMetrice;
	}

	public void setTotalMetrice(Integer totalMetrice) {
		this.totalMetrice = totalMetrice;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public UserActivityTimelyLogs() {
		super();
	}

	public UserActivityTimelyLogs(Integer mobileClient, Integer webClient, Integer mobileClientIdle, Integer webClientIdle,
			Integer totalMetrice, String createdAt) {
		super();
		this.mobileClient = mobileClient;
		this.webClient = webClient;
		this.mobileClientIdle = mobileClientIdle;
		this.webClientIdle = webClientIdle;
		this.totalMetrice = totalMetrice;
		this.createdAt = createdAt;
	}

	public UserActivityTimelyLogs(String idUserActivityLog, Integer mobileClient, Integer webClient, Integer mobileClientIdle,
			Integer webClientIdle, Integer totalMetrice, String createdAt) {
		super();
		this.idUserActivityLog = idUserActivityLog;
		this.mobileClient = mobileClient;
		this.webClient = webClient;
		this.mobileClientIdle = mobileClientIdle;
		this.webClientIdle = webClientIdle;
		this.totalMetrice = totalMetrice;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "idUserActivityLog=" + idUserActivityLog + ",\n mobileClient=" + mobileClient
				+ ",\n webClient=" + webClient + ",\n mobileClientIdle=" + mobileClientIdle + ",\n webClientIdle="
				+ webClientIdle + ",\n totalMetrice=" + totalMetrice + ",\n createdAt=" + createdAt + ".";
	}

	
}
