/**
 * 
 */
package co.vistafoundation.vlearning.auth.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.DateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "MOBILE_OTP")
public class MobileOtp extends DateAudit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idMobileOtp")
	private Long idMobileOTP;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "EXPIRY_TIME")
	private Instant expiryTime;
	
	@Column(name = "MAX_REQUEST")
	private Integer maxRequest;

	@Column(name = "MAX_ATTEMPT")
	private Integer maxAttempt;
	
	/**
	 * 
	 */
	public MobileOtp() {
		super();
	}


	/**
	 * @param mobileNumber
	 * @param otp
	 * @param expiryTime
	 * @param maxRequest
	 * @param maxAttempt
	 */
	public MobileOtp(String mobileNumber, String otp, Instant expiryTime, Integer maxRequest, Integer maxAttempt) {
		super();
		this.mobileNumber = mobileNumber;
		this.otp = otp;
		this.expiryTime = expiryTime;
		this.maxRequest = maxRequest;
		this.maxAttempt = maxAttempt;
	}


	/**
	 * @return the idMobileOTP
	 */
	public Long getIdMobileOTP() {
		return idMobileOTP;
	}

	/**
	 * @param idMobileOTP the idMobileOTP to set
	 */
	public void setIdMobileOTP(Long idMobileOTP) {
		this.idMobileOTP = idMobileOTP;
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
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the expiryTime
	 */
	public Instant getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime the expiryTime to set
	 */
	public void setExpiryTime(Instant expiryTime) {
		this.expiryTime = expiryTime;
	}

	/**
	 * @return the maxRequest
	 */
	public Integer getMaxRequest() {
		return maxRequest;
	}

	/**
	 * @param maxRequest the maxRequest to set
	 */
	public void setMaxRequest(Integer maxRequest) {
		this.maxRequest = maxRequest;
	}


	/**
	 * @return the maxAttempt
	 */
	public Integer getMaxAttempt() {
		return maxAttempt;
	}


	/**
	 * @param maxAttempt the maxAttempt to set
	 */
	public void setMaxAttempt(Integer maxAttempt) {
		this.maxAttempt = maxAttempt;
	}
	
}
