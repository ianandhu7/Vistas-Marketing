package co.vistafoundation.vlearning.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.DateAudit;

@Entity
@Table(name = "subscription_click")
public class SubscriptionClick extends DateAudit implements Serializable {

	private static final long serialVersionUID = -6728169469847127909L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDSUBSCRIPTION_CLICK")
	private Long id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "MOBILE_NUMBER", length = 50, nullable = false)
	private String mobileNumber;

	@Column(name = "ID_VL_USER", nullable = false)
	private Long vlUserId;

	@Column(name = "SOURCE", length = 100)
	private String source;

	@Column(name = "STATUS", length = 20)
	private String status;

	// Default constructor (required by JPA)
	public SubscriptionClick() {
	}

	public SubscriptionClick(Long id, String name, String mobileNumber, Long vlUserId, String source, String status) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.vlUserId = vlUserId;
		this.source = source;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getVlUserId() {
		return vlUserId;
	}

	public void setVlUserId(Long vlUserId) {
		this.vlUserId = vlUserId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
