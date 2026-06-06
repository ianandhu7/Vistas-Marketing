/**
 * 
 */
package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NAVEEN
 *
 */
@Entity
@Table(name = "SPECIAL_OFFER_PRODUCT", uniqueConstraints = {@UniqueConstraint(columnNames = {"idBATCH", "ID_SPECIAL_OFFER_PRODUCT"})})
public class SpecialOfferProduct extends UserDateAudit implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_SPECIAL_OFFER_PRODUCT")
	private Long idSpecialOfferProduct;

	@Column(name = "ID_SPECIAL_OFFER", nullable = false)
	private Long idSpecialOffer;

	@Column(name = "idBATCH")
	private Long idBatch;
	
	@Column(name = "idPRODUCT", nullable = false)
	private Long idProduct;

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
	 * @return the idSpecialOfferProduct
	 */
	public Long getIdSpecialOfferProduct() {
		return idSpecialOfferProduct;
	}

	/**
	 * @param idSpecialOfferProduct the idSpecialOfferProduct to set
	 */
	public void setIdSpecialOfferProduct(Long idSpecialOfferProduct) {
		this.idSpecialOfferProduct = idSpecialOfferProduct;
	}

}
