/**
 * Test
 */
package co.vistafoundation.vlearning.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.dto.NewSubscriptionPlanV2DTO;
import co.vistafoundation.vlearning.product.model.ProductPricing;

@Service
public interface ProductService2 {

	Document<List<NewSubscriptionPlanV2DTO>> getUserSubscriptionProduct();

	/**
	 * @return
	 */
	Document<ProductPricing> editPlanVisibality(Long idProductPricingId, Boolean isVisible);
	
}
