package co.vistafoundation.vlearning.auth.dto;

public class PaymentOrderBody {

	PaymentResultInfo resultInfo;

	String txnId;

	String bankTxnId;

	String orderId;

	String txnAmount;
	
	String txnType;
	
	String gatewayName;
	
	String bankName;
	
	String mid;
	
	String paymentMode;
	
	String refundAmt;
	
	String txnDate;
	
	String authRefId;
	
	String cardScheme;

	/**
	 * @return the resultInfo
	 */
	public PaymentResultInfo getResultInfo() {
		return resultInfo;
	}

	/**
	 * @param resultInfo the resultInfo to set
	 */
	public void setResultInfo(PaymentResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * @return the txnId
	 */
	public String getTxnId() {
		return txnId;
	}

	/**
	 * @param txnId the txnId to set
	 */
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	/**
	 * @return the bankTxnId
	 */
	public String getBankTxnId() {
		return bankTxnId;
	}

	/**
	 * @param bankTxnId the bankTxnId to set
	 */
	public void setBankTxnId(String bankTxnId) {
		this.bankTxnId = bankTxnId;
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
	 * @return the txnAmount
	 */
	public String getTxnAmount() {
		return txnAmount;
	}

	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}

	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the gatewayName
	 */
	public String getGatewayName() {
		return gatewayName;
	}

	/**
	 * @param gatewayName the gatewayName to set
	 */
	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the refundAmt
	 */
	public String getRefundAmt() {
		return refundAmt;
	}

	/**
	 * @param refundAmt the refundAmt to set
	 */
	public void setRefundAmt(String refundAmt) {
		this.refundAmt = refundAmt;
	}

	/**
	 * @return the txnDate
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @return the authRefId
	 */
	public String getAuthRefId() {
		return authRefId;
	}

	/**
	 * @param authRefId the authRefId to set
	 */
	public void setAuthRefId(String authRefId) {
		this.authRefId = authRefId;
	}

	/**
	 * @return the cardScheme
	 */
	public String getCardScheme() {
		return cardScheme;
	}

	/**
	 * @param cardScheme the cardScheme to set
	 */
	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}
	
	

}
