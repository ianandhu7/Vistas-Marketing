/**
 * 
 */
package co.vistafoundation.vlearning.product.dto;

/**
 * @author NaveenKumar
 *
 */
public class ProductPricingDTO {
	

	public ProductPricingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long idProductDuration;
	
	private Long idProductPricing;
	
	private Integer duration;
	
	private String durationName;
	
	private Long idProductAmount;
	
	private String amountName;
	
	private Float amount;
	
	private Long idProduct;
	
	private Long idProductGroup;
	
	private String productName;
	
	private Long idProductLine;
	
	private Boolean activeFlag;

	private String planDescription;
	
	private String promoText;
	
	private Boolean isVisible;

	/**
	 * @return the idProductDuration
	 */
	public Long getIdProductDuration() {
		return idProductDuration;
	}

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
	 * @param idProductDuration the idProductDuration to set
	 */
	public void setIdProductDuration(Long idProductDuration) {
		this.idProductDuration = idProductDuration;
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
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public ProductPricingDTO(Long idProductDuration, Long idProductPricing, Integer duration, String durationName,
			Long idProductAmount, String amountName, Float amount, Long idProduct, Long idProductGroup,
			String productName, Long idProductLine, Boolean activeFlag,String planDescription, String promoText) {
		super();
		this.idProductDuration = idProductDuration;
		this.idProductPricing = idProductPricing;
		this.duration = duration;
		this.durationName = durationName;
		this.idProductAmount = idProductAmount;
		this.amountName = amountName;
		this.amount = amount;
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.productName = productName;
		this.idProductLine = idProductLine;
		this.activeFlag = activeFlag;
		this.planDescription = planDescription;
		this.promoText = promoText;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public ProductPricingDTO(Long idProductDuration, Long idProductPricing, Integer duration, String durationName,
			Long idProductAmount, String amountName, Float amount, Long idProduct, Long idProductGroup,
			String productName, Long idProductLine, Boolean activeFlag,String planDescription, String promoText, Boolean isVisible) {
		super();
		this.idProductDuration = idProductDuration;
		this.idProductPricing = idProductPricing;
		this.duration = duration;
		this.durationName = durationName;
		this.idProductAmount = idProductAmount;
		this.amountName = amountName;
		this.amount = amount;
		this.idProduct = idProduct;
		this.idProductGroup = idProductGroup;
		this.productName = productName;
		this.idProductLine = idProductLine;
		this.activeFlag = activeFlag;
		this.planDescription = planDescription;
		this.promoText = promoText;
		this.isVisible = isVisible;
	}
	
}
