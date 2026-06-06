package co.vistafoundation.vlearning.specialoffer.dto;


/**
 * @author Mohan Kumar K M
 * 
 **/
public class CouponDTO {

	private String description;

	private String couponType;


	/**
	 * 
	 */
	public CouponDTO() {
	
	}
	
	

	/**
	 * @param description
	 * @param couponType
	 */
	public CouponDTO(String description, String couponType) {
		super();
		this.description = description;
		this.couponType = couponType;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the couponType
	 */
	public String getCouponType() {
		return couponType;
	}

	/**
	 * @param couponType the couponType to set
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	
}
