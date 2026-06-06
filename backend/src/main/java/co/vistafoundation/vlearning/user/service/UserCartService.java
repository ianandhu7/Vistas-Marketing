/**
 * 
 */
package co.vistafoundation.vlearning.user.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.PromoCodeDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;
import co.vistafoundation.vlearning.user.model.UserCart;

/**
 * @author vk
 *
 */
public interface UserCartService {
	
	@SuppressWarnings("rawtypes")
	public Document createCart(UserCart userCart);
	
	@SuppressWarnings("rawtypes")
	public Document createCartItems(List<UserCart> userCart);
	
	@SuppressWarnings("rawtypes")
	public Document cartItems(List<UserCart> userCart);
	
	@SuppressWarnings("rawtypes")
	public Document updateCart(UserCart userCart);
	
	@SuppressWarnings("rawtypes")
	public Document findAllByUser(Long userSurId);
	
	@SuppressWarnings("rawtypes")
	public Document findAllByUser2(Long userSurId);
	
	@SuppressWarnings("rawtypes")
	public Document deleteCartItem(Long idUserCart);
	
	@SuppressWarnings("rawtypes")
	public Document deleteAllCartItems(List<Long> idUserCarts);
	
	@SuppressWarnings("rawtypes")
	public Document updateBatchIfCheckoutCancel(List<UserCartResponseDTO> userCarts);
	
	public Document<PromoCodeDTO> applyPromoCode(String promoCode);

}
