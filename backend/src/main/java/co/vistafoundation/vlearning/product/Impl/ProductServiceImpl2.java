/**
 * Test
 */
package co.vistafoundation.vlearning.product.Impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.product.dto.NewSubscriptionPlanV2DTO;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.repository.ProductPricingRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.product.service.ProductService2;

@Service
public class ProductServiceImpl2 implements ProductService2 {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductPricingRepository productPricingRepository;

	@Override
	public Document<List<NewSubscriptionPlanV2DTO>> getUserSubscriptionProduct() {
		Document<List<NewSubscriptionPlanV2DTO>> result = new Document<>();
		try {

			List<Product> productList = productRepository.findByIdProductLineAndActiveFlag(11L, Boolean.TRUE);
			if (productList == null)
				throw new AppException("No product found.");
			List<NewSubscriptionPlanV2DTO> subscriptionPlansList = new ArrayList<>();

			for (Product p : productList) {
				List<NewSubscriptionPlanV2DTO> subscriptionPlans = productRepository
						.getSubscriptionPlanV2(p.getIdProduct());
				subscriptionPlansList.addAll(subscriptionPlans);

			}
			subscriptionPlansList = subscriptionPlansList.stream()
					.sorted(Comparator.comparing(NewSubscriptionPlanV2DTO::getAmount)).collect(Collectors.toList());
			result.setData(subscriptionPlansList);
			result.setMessage("Subscription plan data fetched successfully!");
			result.setStatusCode(200);
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

	}

	@Override
	public Document<ProductPricing> editPlanVisibality(Long idProductPricing, Boolean isVisible) {
		Document<ProductPricing> result = new Document<>();
		try {

			ProductPricing productPricing = productPricingRepository.findByIdProductPricing(idProductPricing);
			if (productPricing == null) {
				throw new AppException("Invalid product id: " + idProductPricing);
			}
			
			productPricing.setIsVisible(isVisible);
			
			productPricing = productPricingRepository.save(productPricing);
			
			result.setData(productPricing);
			result.setMessage("Updated visiblity changed succesfully");
			result.setStatusCode(200);

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}
		return result;
	}

}
