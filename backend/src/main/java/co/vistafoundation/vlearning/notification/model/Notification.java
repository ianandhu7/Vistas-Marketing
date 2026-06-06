package co.vistafoundation.vlearning.notification.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/** Author Meghana**/

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends UserDateAudit implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idNOTIFICATION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotification;
	
	@Column(name = "MESSAGE", length= 200)
	private String message;
	
	@Column(name = "ATTACHMENT", length= 1000)
	private String attachment;
	
	@Column(name = "HEADER", length= 1000)
	private String header;	

	@Column(name = "NOTIFICATION_DATE", length= 1000)
	private Instant notificationDate;
	
	@Column(name = "FROM_USER_ID")
	private Long fromUserId;
	
	@Column(name = "TO_USER_ID")
	private Long toUserId;
	
	@Column(name = "idBatch")
	private Long idBatch;
	
	@Column(name = "READ_STATUS")
	private Boolean readStatus;
	
	

	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	

	public Boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Instant getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Instant notificationDate) {
		this.notificationDate = notificationDate;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	
	
	

	public Notification(String message, String attachment, String header, Instant notificationDate, Long fromUserId,
			Long toUserId, Long idBatch, Boolean readStatus) {
		super();
		this.message = message;
		this.attachment = attachment;
		this.header = header;
		this.notificationDate = notificationDate;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.idBatch = idBatch;
		this.readStatus = readStatus;
	}

	public Notification() {
		
	}


}
