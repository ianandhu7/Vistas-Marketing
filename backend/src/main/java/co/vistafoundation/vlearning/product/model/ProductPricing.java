/**
 * 
 */
package co.vistafoundation.vlearning.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 *
 */
@Entity
@Table(name = "PRODUCT_PRICING", uniqueConstraints = @UniqueConstraint(columnNames = { "idPRODUCT",
		"idPRODUCT_DURATION", "idPRODUCT_AMOUNT" }))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ProductPricing extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT_PRICING", nullable = false)
	private Long idProductPricing;

	@Column(name = "PROMO_TEXT", nullable = false)
	private String promoText;
	
	@Column(name = "PLAN_DESC", nullable = false)
	private String planDescription;
	
	@Column(name = "idPRODUCT", nullable = false)
	private Long idProduct;

	@Column(name = "idPRODUCT_DURATION", nullable = false)
	private Long idProductDuration;

	@Column(name = "idPRODUCT_AMOUNT", nullable = false)
	private Long idProductAmount;
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	@Column(name = "is_visible")
	private Boolean isVisible;

	public ProductPricing() {
	}

	/**
	 * @param idProductPricing
	 * @param promoText
	 * @param planDescription
	 * @param idProduct
	 * @param idProductDuration
	 * @param idProductAmount
	 * @param activeFlag
	 */
	public ProductPricing(Long idProductPricing, String promoText, String planDescription, Long idProduct,
			Long idProductDuration, Long idProductAmount, Boolean activeFlag) {
		super();
		this.idProductPricing = idProductPricing;
		this.promoText = promoText;
		this.planDescription = planDescription;
		this.idProduct = idProduct;
		this.idProductDuration = idProductDuration;
		this.idProductAmount = idProductAmount;
		this.activeFlag = activeFlag;
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
	 * @return the activeFlag
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
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
	
}
