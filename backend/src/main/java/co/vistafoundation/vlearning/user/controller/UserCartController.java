/**
 * 
 */
package co.vistafoundation.vlearning.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.PromoCodeDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;
import co.vistafoundation.vlearning.user.model.UserCart;
import co.vistafoundation.vlearning.user.service.UserCartService;

/**
 * @author vk
 *
 */

@Controller
@RequestMapping("/api/v1/usercart")
public class UserCartController {

	@Autowired
	UserCartService userCartService;

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value="/additem")
	public ResponseEntity<?> create(@RequestBody(required = true) UserCart userCart){
		Document<?> document = userCartService.createCart(userCart);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value="/additems")
	public ResponseEntity<?> createItems(@RequestBody(required = true) List<UserCart> userCarts){
		Document<?> document = userCartService.createCartItems(userCarts);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value="/cartitems")
	public ResponseEntity<?> cartItems(@RequestBody(required = true) List<UserCart> userCarts){
		Document<?> document = userCartService.cartItems(userCarts);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value="/updateitem")
	public ResponseEntity<?> update(@RequestBody(required = true) UserCart userCart){
		Document<?> document = userCartService.updateCart(userCart);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/list")
	public ResponseEntity<?> getCartItemByUser(@RequestParam(required = true) Long userSurId){
		Document<?> document = userCartService.findAllByUser(userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/list2")
	public ResponseEntity<?> getCartItemByUser2(@RequestParam(required = true) Long userSurId){
		Document<?> reponses = userCartService.findAllByUser2(userSurId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value = "/deleteitem")
	public ResponseEntity<?> deleteCartItem(@RequestParam(required = true) Long idUserCart) {
		Document<?> document = userCartService.deleteCartItem(idUserCart);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value = "/deleteall")
	public ResponseEntity<?> deleteCartItems(@RequestBody(required = true) List<Long> idUserCarts) {
		Document<?> document = userCartService.deleteAllCartItems(idUserCarts);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping(value = "/update-batch-cancel-checkout")
	public ResponseEntity<?> updateBatchIfCheckoutCancel(@RequestBody(required = true) List<UserCartResponseDTO> userCarts) {
		Document<?> document = userCartService.updateBatchIfCheckoutCancel(userCarts);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/apply-promo")
	public ResponseEntity<?> applyPromoCode(@RequestParam String promoCode){
		Document<PromoCodeDTO> document = userCartService.applyPromoCode(promoCode);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	
}
