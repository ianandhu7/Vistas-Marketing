/**
 * 
 */
package co.vistafoundation.vlearning.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.subscription.dto.PriceDTO;
import co.vistafoundation.vlearning.subscription.service.PriceService;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/price")
public class PriceController {
	
	@Autowired
	PriceService priceService;
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/pricing")
	public ResponseEntity<?> getPricingDetails(@RequestBody PriceDTO priceDTO){
		PriceDTO priceDTOResponse = priceService.getPricing(priceDTO);
		return ResponseEntity.ok(priceDTOResponse);
	}

}
