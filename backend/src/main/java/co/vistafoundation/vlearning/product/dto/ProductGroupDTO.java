package co.vistafoundation.vlearning.product.dto;

import java.util.List;

public class ProductGroupDTO {
	
	private Long idProductGroup;
	
	private String productGroupName;
	
	private Float monthlySubscrAmt;
	
	private Float qtrSubscrAmt;
	
	private Float annualSubscrAmt;
	
	private String extraCurrCategory; 
	
	private List<ProductDTO> productDTO;

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
	 * @return the productGroupName
	 */
	public String getProductGroupName() {
		return productGroupName;
	}

	/**
	 * @param productGroupName the productGroupName to set
	 */
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	/**
	 * @return the monthlySubscrAmt
	 */
	public Float getMonthlySubscrAmt() {
		return monthlySubscrAmt;
	}

	/**
	 * @param monthlySubscrAmt the monthlySubscrAmt to set
	 */
	public void setMonthlySubscrAmt(Float monthlySubscrAmt) {
		this.monthlySubscrAmt = monthlySubscrAmt;
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
	 * @return the productDTO
	 */
	public List<ProductDTO> getProductDTO() {
		return productDTO;
	}

	/**
	 * @param productDTO the productDTO to set
	 */
	public void setProductDTO(List<ProductDTO> productDTO) {
		this.productDTO = productDTO;
	}

}
