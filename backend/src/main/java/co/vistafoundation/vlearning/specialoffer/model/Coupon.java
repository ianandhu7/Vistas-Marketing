package co.vistafoundation.vlearning.specialoffer.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Mohan Kumar 
 * 
 **/

@Entity
@Table(name = "COUPON")
//@JsonIgnoreProperties({ "idCoupon","isActive", "isVisible", "usedCount" })
public class Coupon implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4125002708749640526L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_COUPON")
	private Long idCoupon;

	@Column(name = "COUPON_CODE", unique= true,nullable = false)
	private String couponCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "COUPON_TYPE")
	private String couponType;
	
	@Column(name = "EXTENSION_DURATION")
	private int extensionDuration;
	
	@Column(name = "DISCOUNT")
	private float discount;
	
	@Column(name = "START_DATE")
	private LocalDateTime startDate;
	
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name = "IS_VISIBLE")
	private Boolean isVisible;
	
	@Column(name = "USED_COUNT")
	private Long usedCount;
	
	@Column(name = "TOTAL_COUNT")
	private Long totalCount;
	
	@Column(name = "BATCH_NAME")
	private String batchName;

	public Coupon() {
		super();
		
	}

	public Coupon(Long idCoupon, String couponCode, String description, String couponType, int extensionDuration,
			float discount, LocalDateTime startDate, LocalDateTime endDate, Boolean isActive, Boolean isVisible,
			Long usedCount, Long totalCount, String batchName) {
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
		this.batchName = batchName;
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

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	
	
	
}
