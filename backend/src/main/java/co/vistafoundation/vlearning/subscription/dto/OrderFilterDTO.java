package co.vistafoundation.vlearning.subscription.dto;

import java.util.Date;
import java.util.List;

/**
 * @author Sarfaraz Ahmed
 *
 */
public class OrderFilterDTO {

	private String orderId;

	private String email;

	private String mobile;

	private List<String> statusList;

	private Date fromDate;

	private Date toDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<String> statusList) {
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
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public OrderFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderFilterDTO(String orderId, String email, String mobile, List<String> statusList, Date fromDate,
			Date toDate) {
		super();
		this.orderId = orderId;
		this.email = email;
		this.mobile = mobile;
		this.statusList = statusList;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

}
