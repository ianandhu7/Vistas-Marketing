/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author SHAIKH AHMED REZA
 *
 */
@Entity
@Table(name = "SPECIAL_OFFER")
public class SpecialOffer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_SPECIAL_OFFER")
	private Long idSpecialOffer;

	@Column(name = "SPECIAL_OFFER_DETAILS")
	private String specialOfferDetails;

	@Column(name = "SPECIAL_OFFER_TYPE",nullable=false)
	private String specialOfferType;

	
	@Column(name = "COUPON_CODE", unique= true)
	private String couponCode;
	
	@Column(name = "SPECIAL_OFFER_START_DATE")
	private LocalDate specialOfferStartDate;
	
	@Column(name = "SPECIAL_OFFER_END_DATE")
	private LocalDate specialOfferEndDate;
	
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	
	/**
	 * @return the idSpecialOffer
	 */
	public Long getIdSpecialOffer() {
		return idSpecialOffer;
	}

	/**
	 * @param idSpecialOffer the idSpecialOffer to set
	 */
	public void setIdSpecialOffer(Long idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
	}

	/**
	 * @return the specialOfferDetails
	 */
	public String getSpecialOfferDetails() {
		return specialOfferDetails;
	}

	/**
	 * @param specialOfferDetails the specialOfferDetails to set
	 */
	public void setSpecialOfferDetails(String specialOfferDetails) {
		this.specialOfferDetails = specialOfferDetails;
	}

	/**
	 * @return the specialOfferType
	 */
	public String getSpecialOfferType() {
		return specialOfferType;
	}

	/**
	 * @param specialOfferType the specialOfferType to set
	 */
	public void setSpecialOfferType(String specialOfferType) {
		this.specialOfferType = specialOfferType;
	}

	/**
	 * @param idSpecialOffer
	 * @param specialOfferDetails
	 * @param specialOfferType
	 */
	public SpecialOffer(Long idSpecialOffer, String specialOfferDetails, String specialOfferType) {
		super();
		this.idSpecialOffer = idSpecialOffer;
		this.specialOfferDetails = specialOfferDetails;
		this.specialOfferType = specialOfferType;
	}

	/**
	 * 
	 */
	public SpecialOffer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param couponCode the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the specialOfferStartDate
	 */
	public LocalDate getSpecialOfferStartDate() {
		return specialOfferStartDate;
	}

	/**
	 * @param specialOfferStartDate the specialOfferStartDate to set
	 */
	public void setSpecialOfferStartDate(LocalDate specialOfferStartDate) {
		this.specialOfferStartDate = specialOfferStartDate;
	}

	/**
	 * @return the specialOfferEndDate
	 */
	public LocalDate getSpecialOfferEndDate() {
		return specialOfferEndDate;
	}

	/**
	 * @param specialOfferEndDate the specialOfferEndDate to set
	 */
	public void setSpecialOfferEndDate(LocalDate specialOfferEndDate) {
		this.specialOfferEndDate = specialOfferEndDate;
	}

	/**
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	
}
