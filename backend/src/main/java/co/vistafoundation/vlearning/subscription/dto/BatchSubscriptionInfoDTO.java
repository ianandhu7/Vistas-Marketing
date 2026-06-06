/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author NAVEEN
 *
 */
public class BatchSubscriptionInfoDTO {
	
	
	private String batchName;
	
	private BigInteger idStudentSubscription;
	
	private BigInteger idVluser;
	
	private Timestamp nextPaymentDate;
	
	private Timestamp subscriptionEndDate;

	private String email;
	
	private String mobileNumber;
	
	private String firstName;
	
	private String deviceId;

	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * @return the idStudentSubscription
	 */
	public BigInteger getIdStudentSubscription() {
		return idStudentSubscription;
	}

	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(BigInteger idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @return the idVluser
	 */
	public BigInteger getIdVluser() {
		return idVluser;
	}

	/**
	 * @param idVluser the idVluser to set
	 */
	public void setIdVluser(BigInteger idVluser) {
		this.idVluser = idVluser;
	}

	/**
	 * @return the nextPaymentDate
	 */
	public Timestamp getNextPaymentDate() {
		return nextPaymentDate;
	}

	/**
	 * @param nextPaymentDate the nextPaymentDate to set
	 */
	public void setNextPaymentDate(Timestamp nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public Timestamp getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Timestamp subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
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
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param batchName
	 * @param idStudentSubscription
	 * @param idVluser
	 * @param nextPaymentDate
	 * @param subscriptionEndDate
	 * @param email
	 * @param mobileNumber
	 * @param firstName
	 * @param deviceId
	 */
	public BatchSubscriptionInfoDTO(String batchName, BigInteger idStudentSubscription, BigInteger idVluser, Timestamp nextPaymentDate,
			Timestamp subscriptionEndDate, String email, String mobileNumber, String firstName, String deviceId) {
		super();
		this.batchName = batchName;
		this.idStudentSubscription = idStudentSubscription;
		this.idVluser = idVluser;
		this.nextPaymentDate = nextPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.firstName = firstName;
		this.deviceId = deviceId;
	}


	public BatchSubscriptionInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
