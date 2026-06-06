/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import javax.validation.constraints.NotNull;

/**
 * @author NAVEEN KUAMR A
 *
 */
public class PaytmParamsRequestDTO {

	@NotNull
	private String requestType;

	@NotNull
	private String mid;

	@NotNull
	private String websiteName;

	@NotNull
	private String orderId;

	@NotNull
	private String callbackUrl;

	@NotNull
	private String value;

	@NotNull
	private String currency;

	@NotNull
	private String custId;

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the websiteName
	 */
	public String getWebsiteName() {
		return websiteName;
	}

	/**
	 * @param websiteName the websiteName to set
	 */
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
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
	 * @return the callbackUrl
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * @param callbackUrl the callbackUrl to set
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

}
