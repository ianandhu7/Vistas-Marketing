/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

/**
 * @author NAVEEN
 *
 */
public class BatchPromoCodeDTO {
	
	private Long idBatch;
	
	private String productLineCode;
	
	private Long idSpecialOffer;
	
	private String couponCode;
	
	private Long idProduct;
	
	private Float monthlySubcrAmt;
	
	private Float annualSubscrAmt;
	
	private Float qtrSubscrAmt;




	/**
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}



	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}



	/**
	 * @return the productLineCode
	 */
	public String getProductLineCode() {
		return productLineCode;
	}



	/**
	 * @param productLineCode the productLineCode to set
	 */
	public void setProductLineCode(String productLineCode) {
		this.productLineCode = productLineCode;
	}



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
	 * @return the monthlySubcrAmt
	 */
	public Float getMonthlySubcrAmt() {
		return monthlySubcrAmt;
	}



	/**
	 * @param monthlySubcrAmt the monthlySubcrAmt to set
	 */
	public void setMonthlySubcrAmt(Float monthlySubcrAmt) {
		this.monthlySubcrAmt = monthlySubcrAmt;
	}



	/**
	 * @return the annualSubscrAmt
	 */
	public Float getAnnualSubscrAmt() {
		return annualSubscrAmt;
	}



	/**
	 * @param annualSubscrAmt the annualSubscrAmt to set
	 */
	public void setAnnualSubscrAmt(Float annualSubscrAmt) {
		this.annualSubscrAmt = annualSubscrAmt;
	}



	/**
	 * @return the qtrSubscrAmt
	 */
	public Float getQtrSubscrAmt() {
		return qtrSubscrAmt;
	}



	/**
	 * @param qtrSubscrAmt the qtrSubscrAmt to set
	 */
	public void setQtrSubscrAmt(Float qtrSubscrAmt) {
		this.qtrSubscrAmt = qtrSubscrAmt;
	}



	/**
	 * @param idBatch
	 * @param productLineCode
	 * @param idSpecialOffer
	 * @param couponCode
	 * @param idProduct
	 */
	public BatchPromoCodeDTO(Long idBatch, String productLineCode, Long idSpecialOffer, String couponCode,
			Long idProduct) {
		super();
		this.idBatch = idBatch;
		this.productLineCode = productLineCode;
		this.idSpecialOffer = idSpecialOffer;
		this.couponCode = couponCode;
		this.idProduct = idProduct;
	}



	/**
	 * @param idBatch
	 * @param productLineCode
	 * @param idSpecialOffer
	 * @param couponCode
	 * @param idProduct
	 * @param monthlySubcrAmt
	 * @param annualSubscrAmt
	 * @param qtrSubscrAmt
	 */
	public BatchPromoCodeDTO(Long idBatch, String productLineCode, Long idSpecialOffer, String couponCode,
			Long idProduct, Float monthlySubcrAmt, Float annualSubscrAmt, Float qtrSubscrAmt) {
		super();
		this.idBatch = idBatch;
		this.productLineCode = productLineCode;
		this.idSpecialOffer = idSpecialOffer;
		this.couponCode = couponCode;
		this.idProduct = idProduct;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
	}



	/**
	 * 
	 */
	public BatchPromoCodeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
