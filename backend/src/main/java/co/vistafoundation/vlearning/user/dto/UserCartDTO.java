package co.vistafoundation.vlearning.user.dto;

import java.util.List;

import co.vistafoundation.vlearning.user.model.UserCart;

public class UserCartDTO {

	private List<UserCartResponseDTO> cartList;
	
	private List<UserCart> duplicateCartList;
	
	private boolean allowToCheckout;
	
	/**
	 * @return the cartList
	 */
	public List<UserCartResponseDTO> getCartList() {
		return cartList;
	}
	/**
	 * @param cartList the cartList to set
	 */
	public void setCartList(List<UserCartResponseDTO> cartList) {
		this.cartList = cartList;
	}
	/**
	 * @return the duplicateCartList
	 */
	public List<UserCart> getDuplicateCartList() {
		return duplicateCartList;
	}
	/**
	 * @param duplicateCartList the duplicateCartList to set
	 */
	public void setDuplicateCartList(List<UserCart> duplicateCartList) {
		this.duplicateCartList = duplicateCartList;
	}
	/**
	 * @return the allowToCheckout
	 */
	public boolean isAllowToCheckout() {
		return allowToCheckout;
	}
	/**
	 * @param allowToCheckout the allowToCheckout to set
	 */
	public void setAllowToCheckout(boolean allowToCheckout) {
		this.allowToCheckout = allowToCheckout;
	}
	
}
