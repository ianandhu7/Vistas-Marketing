/**
 * 
 */
package co.vistafoundation.vlearning.marketer.model;

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
 * @author NAVEEN
 *
 */
@Entity
@Table(name = "VENDOR")
public class Vendor  extends UserDateAudit implements Serializable {
	
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVENDOR", nullable = false)
	private Long idVendor;
	
	
	@Column(name ="VENDOR_NAME",nullable = false)
	private String vendorName;
	
	
	@Column(name ="EMAIL",nullable = false)
	private String email;
	
	
	@Column(name = "idVL_USER",nullable = false)
	private Long idVlUser;
	
	
	@Column(name = "ONBOARDING_IDMARKETER")
	private Long onBoardingIdMarketer;
	
	@Column(name = "REALTIONSHIP_IDMARKETER")
	private Long relationshipIdMarketer;
	
	
	@Column(name ="REMARKS")
	private String remarks;
	
	
	@Column(name="ONBOARDED_DATE")
	private LocalDate onBoardedDate;
	
	
	@Column(name="RUNNER_PAID_STATUS")
	private String runnerPaidStatus;
	
	@Column(name="PAYMENT_AMOUNT")
	private Float paymentAmount;
	
	
	
	/**
	 * @param idVendor
	 * @param vendorName
	 * @param email
	 * @param idVlUser
	 * @param onBoardingIdMarketer
	 * @param relationshipIdMarketer
	 * @param remarks
	 * @param onBoardedDate
	 * @param runnerPaidStatus
	 * @param paymentAmount
	 */
	public Vendor(String vendorName, String email, Long idVlUser, Long onBoardingIdMarketer,
			Long relationshipIdMarketer, String remarks, LocalDate onBoardedDate, String runnerPaidStatus,
			Float paymentAmount) {
		super();
		this.vendorName = vendorName;
		this.email = email;
		this.idVlUser = idVlUser;
		this.onBoardingIdMarketer = onBoardingIdMarketer;
		this.relationshipIdMarketer = relationshipIdMarketer;
		this.remarks = remarks;
		this.onBoardedDate = onBoardedDate;
		this.runnerPaidStatus = runnerPaidStatus;
		this.paymentAmount = paymentAmount;
	}

	

	/**
	 * @return the idVENDOR
	 */


	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @return the idVendor
	 */
	public Long getIdVendor() {
		return idVendor;
	}

	/**
	 * @param idVendor the idVendor to set
	 */
	public void setIdVendor(Long idVendor) {
		this.idVendor = idVendor;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}


	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the onBoardedDate
	 */
	public LocalDate getOnBoardedDate() {
		return onBoardedDate;
	}

	/**
	 * @param onBoardedDate the onBoardedDate to set
	 */
	public void setOnBoardedDate(LocalDate onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the onBoardingIdMarketer
	 */
	public Long getOnBoardingIdMarketer() {
		return onBoardingIdMarketer;
	}

	/**
	 * @param onBoardingIdMarketer the onBoardingIdMarketer to set
	 */
	public void setOnBoardingIdMarketer(Long onBoardingIdMarketer) {
		this.onBoardingIdMarketer = onBoardingIdMarketer;
	}

	/**
	 * @return the relationshipIdMarketer
	 */
	public Long getRelationshipIdMarketer() {
		return relationshipIdMarketer;
	}

	/**
	 * @param relationshipIdMarketer the relationshipIdMarketer to set
	 */
	public void setRelationshipIdMarketer(Long relationshipIdMarketer) {
		this.relationshipIdMarketer = relationshipIdMarketer;
	}
	
	public String getRunnerPaidStatus() {
		return runnerPaidStatus;
	}

	public void setRunnerPaidStatus(String runnerPaidStatus) {
		this.runnerPaidStatus = runnerPaidStatus;
	}

	/**
	 * @param vendorName
	 * @param email
	 * @param idVlUser
	 * @param onBoardingIdMarketer
	 * @param relationshipIdMarketer
	 * @param remarks
	 * @param onBoardedDate
	 * @param runnerPaidStatus
	 */
	public Vendor(String vendorName, String email, Long idVlUser, Long onBoardingIdMarketer,
			Long relationshipIdMarketer, String remarks, LocalDate onBoardedDate, String runnerPaidStatus) {
		super();
		this.vendorName = vendorName;
		this.email = email;
		this.idVlUser = idVlUser;
		this.onBoardingIdMarketer = onBoardingIdMarketer;
		this.relationshipIdMarketer = relationshipIdMarketer;
		this.remarks = remarks;
		this.onBoardedDate = onBoardedDate;
		this.runnerPaidStatus = runnerPaidStatus;
	}

	/**
	 * @return the paymentAmount
	 */
	public float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}


}
