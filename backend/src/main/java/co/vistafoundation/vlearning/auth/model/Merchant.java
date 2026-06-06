/**
 * 
 */
package co.vistafoundation.vlearning.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "MERCHANT")
public class Merchant extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idMERCHANT")
	private Long idMerchant;

	@Column(name = "MERCHANT_NAME", length = 100)
	private String merchantName;

	@Column(name = "MERCHANT_EMAIL", nullable = false, length = 100, unique = true)
	private String merchantEmail;

	@Column(name = "MERCHANT_PHONE", nullable = false, length = 100, unique = true)
	private String merchantPhone;

	@Column(name = "MERCHANT_ID", nullable = false, length = 100, unique = true)
	private String merchantId;

	@Column(name = "MERCHANT_KEY", nullable = false, length = 100, unique = true)
	private String merchantKey;

	@Column(name = "MERCHANT_INDUSTRY_TYPE_ID", length = 255)
	private String industryTypeId;

	@Column(name = "DEFAULT_MERCHANT", nullable = false)
	private Boolean defaultMerchant;

	/**
	 * @param merchantName
	 * @param merchantEmail
	 * @param merchantPhone
	 * @param merchantId
	 * @param merchantKey
	 * @param industryTypeId
	 * @param defaultMerchant
	 */
	public Merchant(String merchantName, String merchantEmail, String merchantPhone, String merchantId,
			String merchantKey, String industryTypeId, Boolean defaultMerchant) {
		super();
		this.merchantName = merchantName;
		this.merchantEmail = merchantEmail;
		this.merchantPhone = merchantPhone;
		this.merchantId = merchantId;
		this.merchantKey = merchantKey;
		this.industryTypeId = industryTypeId;
		this.defaultMerchant = defaultMerchant;
	}

	/**
	 * 
	 */
	public Merchant() {
		super();
	}

	/**
	 * @return the idMerchant
	 */
	public Long getIdMerchant() {
		return idMerchant;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @return the merchantEmail
	 */
	public String getMerchantEmail() {
		return merchantEmail;
	}

	/**
	 * @return the merchantPhone
	 */
	public String getMerchantPhone() {
		return merchantPhone;
	}

	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @return the merchantKey
	 */
	public String getMerchantKey() {
		return merchantKey;
	}

	/**
	 * @return the industryTypeId
	 */
	public String getIndustryTypeId() {
		return industryTypeId;
	}

	/**
	 * @return the defaultMerchant
	 */
	public Boolean getDefaultMerchant() {
		return defaultMerchant;
	}

	/**
	 * @param idMerchant the idMerchant to set
	 */
	public void setIdMerchant(Long idMerchant) {
		this.idMerchant = idMerchant;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @param merchantEmail the merchantEmail to set
	 */
	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	/**
	 * @param merchantPhone the merchantPhone to set
	 */
	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @param merchantKey the merchantKey to set
	 */
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	/**
	 * @param industryTypeId the industryTypeId to set
	 */
	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}

	/**
	 * @param defaultMerchant the defaultMerchant to set
	 */
	public void setDefaultMerchant(Boolean defaultMerchant) {
		this.defaultMerchant = defaultMerchant;
	}

}
