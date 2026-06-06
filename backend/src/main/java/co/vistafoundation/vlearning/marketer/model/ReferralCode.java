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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NAVEEN
 *
 */
@Entity
@Table(name = "REFERRAL_CODE")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ReferralCode extends UserDateAudit implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idREFERRAL_CODE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReferralCode;
	
	
	@Column(name ="OFFER_TYPE",nullable = false)
	private String offerType;
	
	
	@Column(name ="REFERRAL_CODE",nullable = false,unique=true)
	private String referralCode;
	
	
	@Column(name="START_DATE")
	private LocalDate startDate;
	
	
	@Column(name="END_DATE")
	private LocalDate endDate;
	
	
	@Column(name = "idSTUDENT")
	private Long idStudent;

	
	@Column(name = "idVENDOR")
	private Long idVendor;
	
	
	@Column(name = "idDONATOR")
	private Long idDonator;


	/**
	 * @return the idReferralCode
	 */
	public Long getIdReferralCode() {
		return idReferralCode;
	}


	/**
	 * @param idReferralCode the idReferralCode to set
	 */
	public void setIdReferralCode(Long idReferralCode) {
		this.idReferralCode = idReferralCode;
	}


	/**
	 * @return the offerType
	 */
	public String getOfferType() {
		return offerType;
	}


	/**
	 * @param offerType the offerType to set
	 */
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}


	/**
	 * @return the referralCode
	 */
	public String getReferralCode() {
		return referralCode;
	}


	/**
	 * @param referralCode the referralCode to set
	 */
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}


	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() { 
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}


	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
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
	 * @return the idDonator
	 */
	public Long getIdDonator() {
		return idDonator;
	}


	/**
	 * @param idDonator the idDonator to set
	 */
	public void setIdDonator(Long idDonator) {
		this.idDonator = idDonator;
	}


	/**
	 * @param offerType
	 * @param referralCode
	 * @param startDate
	 * @param endDate
	 * @param idStudent
	 * @param idVendor
	 * @param idDonator
	 */
	public ReferralCode(String offerType, String referralCode, LocalDate startDate, LocalDate endDate, Long idStudent,
			Long idVendor, Long idDonator) {
		super();
		this.offerType = offerType;
		this.referralCode = referralCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.idStudent = idStudent;
		this.idVendor = idVendor;
		this.idDonator = idDonator;
	}


	/**
	 * 
	 */
	public ReferralCode() {
		super();
		// TODO Auto-generated constructor stub
	}


	

}
