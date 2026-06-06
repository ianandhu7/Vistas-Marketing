package co.vistafoundation.vlearning.product.dto;

public class NewSubscriptionPlanV2DTO {

	private Long idProduct;

	private Long idProductGroup;

	private Long idProductLine;

	private String ageGroup;

	private String productName;

	private String productCd;

	private String extraCurrCategory;

	private Integer batchSize;

	private Long idProductPricing;

	private String promoText;

	private String planDescription;

	private Long idProductAmount;

	private Float amount;

	private Float oldAmount;

	private String amountCode;

	private String amountName;

	private Long idProductDuration;

	private Integer duration;

	private String durationCode;

	private String durationName;
	
	private String couponCode;

	private Boolean iosEnabled;
	
	private Boolean isVisible;

	private Boolean activeFlag;
	
	public NewSubscriptionPlanV2DTO() {
		super();
	}
	
	/**
	 * @param idProduct
	 * @param idProductGroup
	 * @param idProductLine
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param extraCurrCategory
	 * @param batchSize
	 * @param idProductPricing
	 * @param promoText
	 * @param planDescription
	 * @param idProductAmount
	 * @param amount
	 * @param oldAmount
	 * @param amountCode
	 * @param amountName
	 * @param idProductDuration
	 * @param duration
	 * @param durationCode
	 * @param durationName
	 * @param iosEnabled
	 */
	public NewSubscriptionPlanV2DTO(Long idProduct, Long idProductGroup, Long idProductLine, String ageGroup,
			String productName, String productCd, String extraCurrCategory, Integer batchSize,  Long idProductPricing,
			String promoText, String planDescription, Long idProductAmount, Float amount, Float oldAmount,
			String amountCode, String amountName, Boolean iosEnabled, Long idProductDuration, Integer duration, String durationCode,
			String durationName, Boolean isVisible, Boolean activeFlag) {
		super();
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.idProductLine = idProductLine;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.extraCurrCategory = extraCurrCategory;
		this.batchSize = batchSize;
		this.idProductPricing = idProductPricing;
		this.promoText = promoText;
		this.planDescription = planDescription;
		this.idProductAmount = idProductAmount;
		this.amount = amount;
		this.oldAmount = oldAmount;
		this.amountCode = amountCode;
		this.amountName = amountName;
		this.idProductDuration = idProductDuration;
		this.duration = duration;
		this.durationCode = durationCode;
		this.durationName = durationName;
		this.iosEnabled = iosEnabled;
		this.isVisible = isVisible;
		this.activeFlag = activeFlag;
	}
	/**
	 * @param idProduct
	 * @param idProductGroup
	 * @param idProductLine
	 * @param ageGroup
	 * @param productName
	 * @param productCd
	 * @param extraCurrCategory
	 * @param batchSize
	 * @param idProductPricing
	 * @param promoText
	 * @param planDescription
	 * @param idProductAmount
	 * @param amount
	 * @param oldAmount
	 * @param amountCode
	 * @param amountName
	 * @param idProductDuration
	 * @param duration
	 * @param durationCode
	 * @param durationName
	 * @param couponCode
	 */
	public NewSubscriptionPlanV2DTO(Long idProduct, Long idProductGroup, Long idProductLine, String ageGroup,
			String productName, String productCd, String extraCurrCategory, Integer batchSize, Long idProductPricing,
			String promoText, String planDescription, Long idProductAmount, Float amount, Float oldAmount,
			String amountCode, String amountName, Long idProductDuration, Integer duration, String durationCode,
			String durationName, Boolean isVisible, Boolean activeFlag) {
		super();
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.idProductLine = idProductLine;
		this.ageGroup = ageGroup;
		this.productName = productName;
		this.productCd = productCd;
		this.extraCurrCategory = extraCurrCategory;
		this.batchSize = batchSize;
		this.idProductPricing = idProductPricing;
		this.promoText = promoText;
		this.planDescription = planDescription;
		this.idProductAmount = idProductAmount;
		this.amount = amount;
		this.oldAmount = oldAmount;
		this.amountCode = amountCode;
		this.amountName = amountName;
		this.idProductDuration = idProductDuration;
		this.duration = duration;
		this.durationCode = durationCode;
		this.durationName = durationName;
		this.isVisible = isVisible;
		this.activeFlag = activeFlag;
		
	}

	/**
	 * @return the idProduct
	 */
	public Long getIdProduct() {
		return idProduct;
	}

	/**
	 * @param idProduct the idProduct to set
	 */
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * @return the idProductGroup
	 */
	public Long getIdProductGroup() {
		return idProductGroup;
	}

	/**
	 * @param idProductGroup the idProductGroup to set
	 */
	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}

	/**
	 * @return the idProductLine
	 */
	public Long getIdProductLine() {
		return idProductLine;
	}

	/**
	 * @param idProductLine the idProductLine to set
	 */
	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	/**
	 * @return the ageGroup
	 */
	public String getAgeGroup() {
		return ageGroup;
	}

	/**
	 * @param ageGroup the ageGroup to set
	 */
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}

	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	/**
	 * @return the extraCurrCategory
	 */
	public String getExtraCurrCategory() {
		return extraCurrCategory;
	}

	/**
	 * @param extraCurrCategory the extraCurrCategory to set
	 */
	public void setExtraCurrCategory(String extraCurrCategory) {
		this.extraCurrCategory = extraCurrCategory;
	}

	/**
	 * @return the batchSize
	 */
	public Integer getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize the batchSize to set
	 */
	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * @return the idProductPricing
	 */
	public Long getIdProductPricing() {
		return idProductPricing;
	}

	/**
	 * @param idProductPricing the idProductPricing to set
	 */
	public void setIdProductPricing(Long idProductPricing) {
		this.idProductPricing = idProductPricing;
	}

	/**
	 * @return the promoText
	 */
	public String getPromoText() {
		return promoText;
	}

	/**
	 * @param promoText the promoText to set
	 */
	public void setPromoText(String promoText) {
		this.promoText = promoText;
	}

	/**
	 * @return the planDescription
	 */
	public String getPlanDescription() {
		return planDescription;
	}

	/**
	 * @param planDescription the planDescription to set
	 */
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	/**
	 * @return the idProductAmount
	 */
	public Long getIdProductAmount() {
		return idProductAmount;
	}

	/**
	 * @param idProductAmount the idProductAmount to set
	 */
	public void setIdProductAmount(Long idProductAmount) {
		this.idProductAmount = idProductAmount;
	}

	/**
	 * @return the amount
	 */
	public Float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}

	/**
	 * @return the oldAmount
	 */
	public Float getOldAmount() {
		return oldAmount;
	}

	/**
	 * @param oldAmount the oldAmount to set
	 */
	public void setOldAmount(Float oldAmount) {
		this.oldAmount = oldAmount;
	}

	/**
	 * @return the amountCode
	 */
	public String getAmountCode() {
		return amountCode;
	}

	/**
	 * @param amountCode the amountCode to set
	 */
	public void setAmountCode(String amountCode) {
		this.amountCode = amountCode;
	}

	/**
	 * @return the amountName
	 */
	public String getAmountName() {
		return amountName;
	}

	/**
	 * @param amountName the amountName to set
	 */
	public void setAmountName(String amountName) {
		this.amountName = amountName;
	}

	/**
	 * @return the idProductDuration
	 */
	public Long getIdProductDuration() {
		return idProductDuration;
	}

	/**
	 * @param idProductDuration the idProductDuration to set
	 */
	public void setIdProductDuration(Long idProductDuration) {
		this.idProductDuration = idProductDuration;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the durationCode
	 */
	public String getDurationCode() {
		return durationCode;
	}

	/**
	 * @param durationCode the durationCode to set
	 */
	public void setDurationCode(String durationCode) {
		this.durationCode = durationCode;
	}

	/**
	 * @return the durationName
	 */
	public String getDurationName() {
		return durationName;
	}

	/**
	 * @param durationName the durationName to set
	 */
	public void setDurationName(String durationName) {
		this.durationName = durationName;
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
	 * @return the iosEnabled
	 */
	public Boolean getIosEnabled() {
		return iosEnabled;
	}

	/**
	 * @param iosEnabled the iosEnabled to set
	 */
	public void setIosEnabled(Boolean iosEnabled) {
		this.iosEnabled = iosEnabled;
	}

	/**
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
