/**
 * 
 */
package co.vistafoundation.vlearning.auth.dto;

/**
 * @author vk
 *
 */
public class PaytmDetailsDTO {
    
    private String merchantEmail;
    
    private String merchantPhone;
    
    private String merchantId;
    
    private String merchantKey;
    
    private String customerId;
    
    private String transactionAmount;
    
    private String orderId;

    /**
     * @return the merchantEmail
     */
    public String getMerchantEmail() {
        return merchantEmail;
    }

    /**
     * @param merchantEmail the merchantEmail to set
     */
    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    /**
     * @return the merchantPhone
     */
    public String getMerchantPhone() {
        return merchantPhone;
    }

    /**
     * @param merchantPhone the merchantPhone to set
     */
    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    /**
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId the merchantId to set
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the merchantKey
     */
    public String getMerchantKey() {
        return merchantKey;
    }

    /**
     * @param merchantKey the merchantKey to set
     */
    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the transactionAmount
     */
    public String getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * @param transactionAmount the transactionAmount to set
     */
    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
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
    
}

