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
@Table(name = "REDEMPTION")
public class Redemption implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7224549245277350134L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_REDEMPTION")
	private Long idRedemption;
	
	@Column(name = "COUPON_CODE")
	private String couponCode;

	@Column(name = "ID_VL_USER")
	private Long idVlUser;

	@Column(name = "idPRODUCT_PRICING")
	private Long idProductPricing;

	@Column(name = "REDEMPTION_DATE")
	private LocalDateTime redemptionDate;
	
	@Column(name = "EXTENSION_DURATION")
	private int extensionDuration;
	
	@Column(name = "DISCOUNT")
	private float discount;

	public Redemption() {
		
	}

	/**
	 * @param idRedemption
	 * @param couponCode
	 * @param idVlUser
	 * @param idProductPricing
	 * @param redemptionDate
	 * @param extensionDuration
	 * @param discount
	 */
	public Redemption(Long idRedemption, String couponCode, Long idVlUser, Long idProductPricing,
			LocalDateTime redemptionDate, int extensionDuration, float discount) {
		super();
		this.idRedemption = idRedemption;
		this.couponCode = couponCode;
		this.idVlUser = idVlUser;
		this.idProductPricing = idProductPricing;
		this.redemptionDate = redemptionDate;
		this.extensionDuration = extensionDuration;
		this.discount = discount;
	}

	/**
	 * @return the idRedemption
	 */
	public Long getIdRedemption() {
		return idRedemption;
	}

	/**
	 * @param idRedemption the idRedemption to set
	 */
	public void setIdRedemption(Long idRedemption) {
		this.idRedemption = idRedemption;
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
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
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
	 * @return the redemptionDate
	 */
	public LocalDateTime getRedemptionDate() {
		return redemptionDate;
	}

	/**
	 * @param redemptionDate the redemptionDate to set
	 */
	public void setRedemptionDate(LocalDateTime redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	/**
	 * @return the extensionDuration
	 */
	public int getExtensionDuration() {
		return extensionDuration;
	}

	/**
	 * @param extensionDuration the extensionDuration to set
	 */
	public void setExtensionDuration(int extensionDuration) {
		this.extensionDuration = extensionDuration;
	}

	/**
	 * @return the discount
	 */
	public float getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	

		
}
