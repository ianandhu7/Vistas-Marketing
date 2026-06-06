/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.product.dto.ProductDTO;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subscription.dto.PriceDTO;

/**
 * @author vk
 *
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	ProductGroupRepository productGroupRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public PriceDTO getPricing(PriceDTO priceDTO) {
		if (!Objects.isNull(priceDTO.getIdProductGroup())) {
			ProductGroup productGroup = productGroupRepository.findByIdProductGroup(priceDTO.getIdProductGroup());
			if (productGroup == null) throw new AppException("Invalid idProduct found.");
			if (priceDTO.getSubscriptionType().equals("monthly")) {
				priceDTO.setMonthlySubcrAmt(productGroup.getMonthlySubscrAmt());
				priceDTO.setQtrSubscrAmt(0F);
				priceDTO.setAnnualSubscrAmt(0F);
			}else if (priceDTO.getSubscriptionType().equals("quarterly")) {
				priceDTO.setMonthlySubcrAmt(0F);
				priceDTO.setQtrSubscrAmt(productGroup.getQtrSubscrAmt());
				priceDTO.setAnnualSubscrAmt(0F);
			}else if (priceDTO.getSubscriptionType().equals("annually")){
				priceDTO.setMonthlySubcrAmt(0F);
				priceDTO.setQtrSubscrAmt(0F);
				priceDTO.setAnnualSubscrAmt(productGroup.getAnnualSubscrAmt());
			}
		}else {
			List<ProductDTO> listProducts = new ArrayList<>();
			for (ProductDTO product : priceDTO.getProducts()) {
				Product productReturn = productRepository.findByIdProductAndActiveFlag(product.getIdProduct(),Boolean.TRUE);
				if (productReturn == null) throw new AppException("Invalid idProduct found.");
				product.setIdProduct(productReturn.getIdProduct());
				product.setProductName(productReturn.getProductName());
				product.setIdSubject(productReturn.getIdSubject());
				if (priceDTO.getSubscriptionType().equals("monthly")) {
					product.setMonthlySubcrAmt(productReturn.getMonthlySubcrAmt());
					product.setQtrSubscrAmt(0F);
					product.setAnnualSubscrAmt(0F);
				}else if (priceDTO.getSubscriptionType().equals("quarterly")) {
					product.setMonthlySubcrAmt(0F);
					product.setQtrSubscrAmt(productReturn.getQtrSubscrAmt());
					product.setAnnualSubscrAmt(0F);
				}else if (priceDTO.getSubscriptionType().equals("annually")){
					product.setMonthlySubcrAmt(0F);
					product.setQtrSubscrAmt(0F);
					product.setAnnualSubscrAmt(productReturn.getAnnualSubscrAmt());
				}
				listProducts.add(product);
			}
			priceDTO.setProducts(listProducts);
		}
		return priceDTO;
	}

}
