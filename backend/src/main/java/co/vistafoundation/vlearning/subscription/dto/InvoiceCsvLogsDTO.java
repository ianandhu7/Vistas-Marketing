package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class InvoiceCsvLogsDTO {
	   
	    private Long id;
	    private String mobile;
	    private String email;
	    private String orderId;
	    private List<String> statusList; 
	    private Date fromDate;
	    private Instant toDate;
	    private String invoiceName;
	    private Instant createdAt;
	
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		
		public List<String> getStatusList() {
			return statusList;
		}
		public void setStatusList(List<String> statusList) {
			this.statusList = statusList;
		}
		public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public Instant getToDate() {
			return toDate;
		}
		public void setToDate(Instant toDate) {
			this.toDate = toDate;
		}
		public String getInvoiceName() {
			return invoiceName;
		}
		public void setInvoiceName(String invoiceName) {
			this.invoiceName = invoiceName;
		}
		public Instant getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Instant createdAt) {
			this.createdAt = createdAt;
		}
	    
	    
}
