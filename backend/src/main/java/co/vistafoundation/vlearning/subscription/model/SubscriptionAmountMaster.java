/**
 * 
 */
package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "SUBSCR_AMT_MASTER")
public class SubscriptionAmountMaster extends UserDateAudit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSUBSCR_AMT_MASTER", nullable = false)
	private Long idSubscriptionAmountMaster;
	
	@Column(name = "SUBSCR_INTERVAL", nullable = false)
	private String subscriptionInterval;
	
	@Column(name = "SUBSCR_AMT", nullable = false)
	private String subscriptionAmount;
	
	@Column(name = "VALID_FROM_DT", nullable = false)
	private LocalDate validFromDate;
	
	@Column(name = "VALID_TO_DT", nullable = false)
	private LocalDate validToDate;
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	@Column(name = "SUBSCR_DESC", length = 100)
	private String subscriptionDescription;

	/**
	 * 
	 */
	public SubscriptionAmountMaster() {
		super();
	}

	/**
	 * @param subscriptionInterval
	 * @param subscriptionAmount
	 * @param validFromDate
	 * @param validToDate
	 * @param activeFlag
	 * @param subscriptionDescription
	 */
	public SubscriptionAmountMaster(String subscriptionInterval, String subscriptionAmount, LocalDate validFromDate,
			LocalDate validToDate, Boolean activeFlag, String subscriptionDescription) {
		super();
		this.subscriptionInterval = subscriptionInterval;
		this.subscriptionAmount = subscriptionAmount;
		this.validFromDate = validFromDate;
		this.validToDate = validToDate;
		this.activeFlag = activeFlag;
		this.subscriptionDescription = subscriptionDescription;
	}

	/**
	 * @return the idSubscriptionAmountMaster
	 */
	public Long getIdSubscriptionAmountMaster() {
		return idSubscriptionAmountMaster;
	}

	/**
	 * @return the subscriptionInterval
	 */
	public String getSubscriptionInterval() {
		return subscriptionInterval;
	}

	/**
	 * @return the subscriptionAmount
	 */
	public String getSubscriptionAmount() {
		return subscriptionAmount;
	}

	/**
	 * @return the validFromDate
	 */
	public LocalDate getValidFromDate() {
		return validFromDate;
	}

	/**
	 * @return the validToDate
	 */
	public LocalDate getValidToDate() {
		return validToDate;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @return the subscriptionDescription
	 */
	public String getSubscriptionDescription() {
		return subscriptionDescription;
	}

	/**
	 * @param idSubscriptionAmountMaster the idSubscriptionAmountMaster to set
	 */
	public void setIdSubscriptionAmountMaster(Long idSubscriptionAmountMaster) {
		this.idSubscriptionAmountMaster = idSubscriptionAmountMaster;
	}

	/**
	 * @param subscriptionInterval the subscriptionInterval to set
	 */
	public void setSubscriptionInterval(String subscriptionInterval) {
		this.subscriptionInterval = subscriptionInterval;
	}

	/**
	 * @param subscriptionAmount the subscriptionAmount to set
	 */
	public void setSubscriptionAmount(String subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	/**
	 * @param validFromDate the validFromDate to set
	 */
	public void setValidFromDate(LocalDate validFromDate) {
		this.validFromDate = validFromDate;
	}

	/**
	 * @param validToDate the validToDate to set
	 */
	public void setValidToDate(LocalDate validToDate) {
		this.validToDate = validToDate;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @param subscriptionDescription the subscriptionDescription to set
	 */
	public void setSubscriptionDescription(String subscriptionDescription) {
		this.subscriptionDescription = subscriptionDescription;
	}
	
}
