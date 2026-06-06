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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 *
 */
@Entity
@Table(name = "PRODUCT_AMOUNT")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class ProductAmount extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPRODUCT_AMOUNT", nullable = false)
	private Long idProductAmount;

	@Column(name = "AMOUNT", nullable = false,unique = true)
	private Float amount;
	
	@Column(name = "OLD_AMOUNT", nullable = false)
	private Float oldAmount;

	@Column(name = "AMOUNT_NAME", length = 50, nullable = false)
	private String amountName;

	@Column(name = "AMOUNT_CODE", length = 50, nullable = false)
	private String amountCode;
    
    @Column(name = "ios_enabled")
    private Boolean iosEnabled;
	
	public ProductAmount() {
	}

	/**
	 * @param idProductAmount
	 * @param amount
	 * @param oldAmount
	 * @param amountName
	 * @param amountCode
	 */
	public ProductAmount(Long idProductAmount, Float amount, Float oldAmount, String amountName, String amountCode) {
		super();
		this.idProductAmount = idProductAmount;
		this.amount = amount;
		this.oldAmount = oldAmount;
		this.amountName = amountName;
		this.amountCode = amountCode;
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
	 * @param idProductAmount
	 * @param amount
	 * @param oldAmount
	 * @param amountName
	 * @param amountCode
	 * @param iosEnabled
	 */
	public ProductAmount(Long idProductAmount, Float amount, Float oldAmount, String amountName, String amountCode,
			Boolean iosEnabled) {
		super();
		this.idProductAmount = idProductAmount;
		this.amount = amount;
		this.oldAmount = oldAmount;
		this.amountName = amountName;
		this.amountCode = amountCode;
		this.iosEnabled = iosEnabled;
	}

}
