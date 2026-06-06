/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.util.List;

import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;

/**
 * @author vk
 *
 */
public class PromoCodeDTO {

	private String appliedPromoCode;
	
	private List<UserCartResponseDTO> promoAppliedList;
	
	private List<UserCartResponseDTO> nonPromoList;
	
	private Long totalAmount;
	
	private Long actualAmount;
	
	private Long promoCodeValue;
	
	private Long nonPromoItemsAmount;

	/**
	 * @return the appliedPromoCode
	 */
	public String getAppliedPromoCode() {
		return appliedPromoCode;
	}

	/**
	 * @return the promoAppliedList
	 */
	public List<UserCartResponseDTO> getPromoAppliedList() {
		return promoAppliedList;
	}

	/**
	 * @return the nonPromoList
	 */
	public List<UserCartResponseDTO> getNonPromoList() {
		return nonPromoList;
	}

	/**
	 * @return the totalAmount
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @return the actualAmount
	 */
	public Long getActualAmount() {
		return actualAmount;
	}

	/**
	 * @return the promoCodeValue
	 */
	public Long getPromoCodeValue() {
		return promoCodeValue;
	}

	/**
	 * @return the nonPromoItemsAmount
	 */
	public Long getNonPromoItemsAmount() {
		return nonPromoItemsAmount;
	}

	/**
	 * @param appliedPromoCode the appliedPromoCode to set
	 */
	public void setAppliedPromoCode(String appliedPromoCode) {
		this.appliedPromoCode = appliedPromoCode;
	}

	/**
	 * @param promoAppliedList the promoAppliedList to set
	 */
	public void setPromoAppliedList(List<UserCartResponseDTO> promoAppliedList) {
		this.promoAppliedList = promoAppliedList;
	}

	/**
	 * @param nonPromoList the nonPromoList to set
	 */
	public void setNonPromoList(List<UserCartResponseDTO> nonPromoList) {
		this.nonPromoList = nonPromoList;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @param actualAmount the actualAmount to set
	 */
	public void setActualAmount(Long actualAmount) {
		this.actualAmount = actualAmount;
	}

	/**
	 * @param promoCodeValue the promoCodeValue to set
	 */
	public void setPromoCodeValue(Long promoCodeValue) {
		this.promoCodeValue = promoCodeValue;
	}

	/**
	 * @param nonPromoItemsAmount the nonPromoItemsAmount to set
	 */
	public void setNonPromoItemsAmount(Long nonPromoItemsAmount) {
		this.nonPromoItemsAmount = nonPromoItemsAmount;
	}
	
}
