package co.vistafoundation.vlearning.notification.dto;

import java.time.Instant;

public class NoificationDTO {
	
	private Long idNotification;
	private String message;
	private String from;
	private String batch;
	private Instant notificationDate;
	private Boolean readStatus;
	private Long NotiCount;
	private String header;
	private String tomesage;
	
	
	
	
	
	
	public String getTomesage() {
		return tomesage;
	}
	public void setTomesage(String tomesage) {
		this.tomesage = tomesage;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}	
	public Long getNotiCount() {
		return NotiCount;
	}
	public void setNotiCount(Long notiCount) {
		NotiCount = notiCount;
	}
	public Boolean getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}
	public Instant getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(Instant notificationDate) {
		this.notificationDate = notificationDate;
	}
	public Long getIdNotification() {
		return idNotification;
	}
	public void setIdNotification(Long idNotification) {
		this.idNotification = idNotification;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	
}
