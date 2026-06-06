package co.vistafoundation.vlearning.subscription.dto;

public class SubscriptionPlanDTO {
	
	public Long idProduct;

	public Long idProductGroup;


	public Long idProductLine;

//	public String ageGroup;

	public String productName;

	public SubscriptionPlanDTO(Long idProduct, Long idProductGroup, Long idProductLine, String productName,
			String productCd, Long idProductPricing,  Long idProductAmount,
			Float amount, Float oldAmount, String amountName, Long idProductDuration, Integer duration,
			String durationName) {
		super();
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.idProductLine = idProductLine;
		this.productName = productName;
		this.productCd = productCd;
		this.idProductPricing = idProductPricing;
		this.idProductAmount = idProductAmount;
		this.amount = amount;
		this.oldAmount = oldAmount;
		this.amountName = amountName;
		this.idProductDuration = idProductDuration;
		this.duration = duration;
		this.durationName = durationName;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Long getIdProductGroup() {
		return idProductGroup;
	}

	public void setIdProductGroup(Long idProductGroup) {
		this.idProductGroup = idProductGroup;
	}

	public Long getIdProductLine() {
		return idProductLine;
	}

	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public Long getIdProductPricing() {
		return idProductPricing;
	}

	public void setIdProductPricing(Long idProductPricing) {
		this.idProductPricing = idProductPricing;
	}


	public Long getIdProductAmount() {
		return idProductAmount;
	}

	public void setIdProductAmount(Long idProductAmount) {
		this.idProductAmount = idProductAmount;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getOldAmount() {
		return oldAmount;
	}

	public void setOldAmount(Float oldAmount) {
		this.oldAmount = oldAmount;
	}

	public String getAmountName() {
		return amountName;
	}

	public void setAmountName(String amountName) {
		this.amountName = amountName;
	}

	public Long getIdProductDuration() {
		return idProductDuration;
	}

	public void setIdProductDuration(Long idProductDuration) {
		this.idProductDuration = idProductDuration;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDurationName() {
		return durationName;
	}

	public void setDurationName(String durationName) {
		this.durationName = durationName;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	
	public SubscriptionPlanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String productCd;

//	public String extraCurrCategory;

//	public Integer batchSize;

	public Long idProductPricing;


	public Long idProductAmount;

	public Float amount;

	public Float oldAmount;

//	public String amountCode;

	public String amountName;

	public Long idProductDuration;

	public Integer duration;

//	public String durationCode;

	public String durationName;
	
	public String couponCode;

}
