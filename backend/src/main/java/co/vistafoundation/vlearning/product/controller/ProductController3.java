package co.vistafoundation.vlearning.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.dto.NewSubscriptionPlanV2DTO;
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.service.ProductService2;

@RestController
@RequestMapping("api/v3/Product")
public class ProductController3 {

	@Autowired
	ProductService2 productService;

	@GetMapping("/user-subscription-product")
	public ResponseEntity<Document<List<NewSubscriptionPlanV2DTO>>> getUserSubscriptionProduct() {
		Document<List<NewSubscriptionPlanV2DTO>> responses = productService.getUserSubscriptionProduct();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{idProductPricingId}/product-pricing")
	public ResponseEntity<?> updateProductVisibality(@PathVariable Long idProductPricingId, @RequestParam Boolean isVisible) {
		Document<ProductPricing> response = productService.editPlanVisibality(idProductPricingId, isVisible);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
