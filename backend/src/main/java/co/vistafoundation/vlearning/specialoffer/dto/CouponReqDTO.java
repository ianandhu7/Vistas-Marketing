package co.vistafoundation.vlearning.specialoffer.dto;

import java.util.List;
/**
 * @author Mohan Kumar
 *
 */
public class CouponReqDTO {

	private List<Long> idCoupon;
	
	private String batchName;

    private Boolean isActive;
    
    private Boolean isVisible;

	public CouponReqDTO() {
		
	}

	public CouponReqDTO(List<Long> idCoupon, String batchName, Boolean isActive, Boolean isVisible) {
		super();
		this.idCoupon = idCoupon;
		this.batchName = batchName;
		this.isActive = isActive;
		this.isVisible = isVisible;
	}

	public List<Long> getIdCoupon() {
		return idCoupon;
	}

	public void setIdCoupon(List<Long> idCoupon) {
		this.idCoupon = idCoupon;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
    
	
    
	
	
}
