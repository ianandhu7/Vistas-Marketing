package co.vistafoundation.vlearning.specialoffer.dto;

import javax.validation.constraints.NotNull;

public class SpecialOfferDTO {
	
	
	private String couponCode;
	
	
	private String specialOfferDetails;
	
	

	/**
	 * @param couponCode
	 * @param specialOfferDetails
	 */
	public SpecialOfferDTO(@NotNull String couponCode, @NotNull String specialOfferDetails) {
		super();
		this.couponCode = couponCode;
		this.specialOfferDetails = specialOfferDetails;
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
	
}
