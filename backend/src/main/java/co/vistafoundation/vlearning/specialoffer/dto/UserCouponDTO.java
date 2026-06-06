package co.vistafoundation.vlearning.specialoffer.dto;

import java.time.LocalDateTime;



public class UserCouponDTO {

	private Long idCoupon;

	private String couponCode;
	
	private String description;

	private String couponType;
		
	private int extensionDuration;
	
	private float discount;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private Boolean isActive;
	
	private Boolean isVisible;
	
	private Long usedCount;
	
	private Long totalCount;
	
	private String emailOrPhone;

	public UserCouponDTO() {
		
	}

	public UserCouponDTO(Long idCoupon, String couponCode, String description, String couponType, int extensionDuration,
			float discount, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive, Boolean isVisible,
			Long usedCount, Long totalCount, String emailOrPhone) {
		super();
		this.idCoupon = idCoupon;
		this.couponCode = couponCode;
		this.description = description;
		this.couponType = couponType;
		this.extensionDuration = extensionDuration;
		this.discount = discount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isActive = isActive;
		this.isVisible = isVisible;
		this.usedCount = usedCount;
		this.totalCount = totalCount;
		this.emailOrPhone = emailOrPhone;
	}

	public Long getIdCoupon() {
		return idCoupon;
	}

	public void setIdCoupon(Long idCoupon) {
		this.idCoupon = idCoupon;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public int getExtensionDuration() {
		return extensionDuration;
	}

	public void setExtensionDuration(int extensionDuration) {
		this.extensionDuration = extensionDuration;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
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

	public Long getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Long usedCount) {
		this.usedCount = usedCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String getEmailOrPhone() {
		return emailOrPhone;
	}

	public void setEmailOrPhone(String emailOrPhone) {
		this.emailOrPhone = emailOrPhone;
	}
	

}
