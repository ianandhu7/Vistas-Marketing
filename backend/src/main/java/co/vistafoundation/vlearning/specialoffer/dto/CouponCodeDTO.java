package co.vistafoundation.vlearning.specialoffer.dto;

import java.math.BigInteger;

public class CouponCodeDTO {
	
	
	private BigInteger idSpecialOffer;
	
	private String couponCode;
	
	private String specialOfferDetails;


	public BigInteger getIdSpecialOffer() {
		return idSpecialOffer;
	}

	public void setIdSpecialOffer(BigInteger idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getSpecialOfferDetails() {
		return specialOfferDetails;
	}

	public void setSpecialOfferDetails(String specialOfferDetails) {
		this.specialOfferDetails = specialOfferDetails;
	}

	public CouponCodeDTO(BigInteger idSpecialOffer, String couponCode, String specialOfferDetails) {
		super();
		this.idSpecialOffer = idSpecialOffer;
		this.couponCode = couponCode;
		this.specialOfferDetails = specialOfferDetails;
	}

	public CouponCodeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
