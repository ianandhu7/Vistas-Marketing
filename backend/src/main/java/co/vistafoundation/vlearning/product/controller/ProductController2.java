package co.vistafoundation.vlearning.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.service.ProductService;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionPlanDTO;

@RestController
@RequestMapping("api/v2/Product")
public class ProductController2 {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/user-subscription-product")
	public ResponseEntity<Document<List<NewSubscriptionPlanDTO>>> getUserSubscriptionProduct(@RequestParam String productCd){
		Document<List<NewSubscriptionPlanDTO>> responses = productService.getUserSubscriptionProduct(productCd);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	@GetMapping("/new-auth-subscription-plans")
	public ResponseEntity<Document<List<SubscriptionPlanDTO>>> getNewUserSubscriptionProduct(@RequestParam String productCd){
		Document<List<SubscriptionPlanDTO>> responses = productService.getNewUserSubscriptionProduct(productCd);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
}
