/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.util.List;

import co.vistafoundation.vlearning.product.dto.ProductDTO;

/**
 * @author vk
 *
 */
public class PriceDTO {
	
	private Long idProductGroup;
	
	private List<ProductDTO> products;
	
	private String subscriptionType;
	
	private Long idClassStandard;
	
	private Float monthlySubcrAmt;
	
	private Float qtrSubscrAmt;
	
	private Float annualSubscrAmt;

	/**
	 * @param idProductGroup
	 * @param products
	 * @param subscriptionType
	 * @param idClassStandard
	 * @param monthlySubcrAmt
	 * @param qtrSubscrAmt
	 * @param annualSubscrAmt
	 */
	public PriceDTO(Long idProductGroup, List<ProductDTO> products, String subscriptionType, Long idClassStandard,
			Float monthlySubcrAmt, Float qtrSubscrAmt, Float annualSubscrAmt) {
		super();
		this.idProductGroup = idProductGroup;
		this.products = products;
		this.subscriptionType = subscriptionType;
		this.idClassStandard = idClassStandard;
		this.monthlySubcrAmt = monthlySubcrAmt;
		this.qtrSubscrAmt = qtrSubscrAmt;
		this.annualSubscrAmt = annualSubscrAmt;
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
	 * @return the products
	 */
	public List<ProductDTO> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	/**
	 * @return the subscriptionType
	 */
	public String getSubscriptionType() {
		return subscriptionType;
	}

	/**
	 * @param subscriptionType the subscriptionType to set
	 */
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
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

}
