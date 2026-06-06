package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

@Entity
@Table(name = "invoice_csv_logs")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class InvoiceCsvLogs extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInvoiceCsvLogs;
	
	private String mobile;
	
	private String email;
	
	private String orderId;
	
	private String statusList;
	
	private Date fromDate;
	
	private Instant toDate;
	
	private String invoiceName;

	public InvoiceCsvLogs() {
		super();
	}

	
	
	/**
	 * @return the idInvoiceCsvLogs
	 */
	public Long getIdInvoiceCsvLogs() {
		return idInvoiceCsvLogs;
	}



	/**
	 * @param idInvoiceCsvLogs the idInvoiceCsvLogs to set
	 */
	public void setIdInvoiceCsvLogs(Long idInvoiceCsvLogs) {
		this.idInvoiceCsvLogs = idInvoiceCsvLogs;
	}



	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}



	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}



	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	/**
	 * @return the statusList
	 */
	public String getStatusList() {
		return statusList;
	}



	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}



	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}



	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}



	/**
	 * @return the toDate
	 */
	public Instant getToDate() {
		return toDate;
	}



	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Instant toDate) {
		this.toDate = toDate;
	}



	/**
	 * @return the invoiceName
	 */
	public String getInvoiceName() {
		return invoiceName;
	}



	/**
	 * @param invoiceName the invoiceName to set
	 */
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}



	public InvoiceCsvLogs(String mobile, String email, String orderId, String statusList, Date fromDate, Instant toDate,
			String invoiceName) {
		super();
		this.mobile = mobile;
		this.email = email;
		this.orderId = orderId;
		this.statusList = statusList;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.invoiceName = invoiceName;
	}
	
	

}
