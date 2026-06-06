package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;
import java.util.List;


/**
 * @author sarfaraz
 *
 */

public class OrderDTO {
	
	private Long idStudentOrder;
	
	private String orderId;
	
	private Float amount;
	
	private Instant orderDate;
	
	private String orderStatus;
	
	private boolean expanded;
	
	private String userName;
	
	private Boolean disputeFlag;
	
	private String secondaryStatus;
	
	private Long subscriptionId;
	
	private List<ProductDTO> productList;
	
	

	/**
	 * @return the idStudentOrder
	 */
	public Long getIdStudentOrder() {
		return idStudentOrder;
	}
	/**
	 * @param idStudentOrder the idStudentOrder to set
	 */
	public void setIdStudentOrder(Long idStudentOrder) {
		this.idStudentOrder = idStudentOrder;
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
	 * @return the amount
	 */
	public Float getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	/**
	 * @return the orderDate
	 */
	public Instant getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Instant orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the disputeFlag
	 */
	public Boolean getDisputeFlag() {
		return disputeFlag;
	}
	/**
	 * @param disputeFlag the disputeFlag to set
	 */
	public void setDisputeFlag(Boolean disputeFlag) {
		this.disputeFlag = disputeFlag;
	}
	/**
	 * @return the secondaryStatus
	 */
	public String getSecondaryStatus() {
		return secondaryStatus;
	}
	/**
	 * @param secondaryStatus the secondaryStatus to set
	 */
	public void setSecondaryStatus(String secondaryStatus) {
		this.secondaryStatus = secondaryStatus;
	}
	/**
	 * @return the subscriptionId
	 */
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	/**
	 * @param subscriptionId the subscriptionId to set
	 */
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	/**
	 * @return the productList
	 */
	public List<ProductDTO> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}
	
}
