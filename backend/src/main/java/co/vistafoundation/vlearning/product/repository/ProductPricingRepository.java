/**
 * 
 */
package co.vistafoundation.vlearning.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import co.vistafoundation.vlearning.product.dto.ProductPricingDTO;
import co.vistafoundation.vlearning.product.model.ProductPricing;

/**
 * @author NaveenKumar
 *
 */
public interface ProductPricingRepository extends JpaRepository<ProductPricing, Long> {

	public ProductPricing findByIdProductPricing(Long idProductPricing);

	@Query("SELECT new co.vistafoundation.vlearning.product.dto.ProductPricingDTO( pd.idProductDuration, pp.idProductPricing, pd.duration, pd.durationName, pa.idProductAmount, pa.amountName, pa.amount, p.idProduct, p.idProductGroup, p.productName, p.idProductLine, pp.activeFlag, pp.planDescription, pp.promoText) " +
		       " FROM ProductPricing pp " +
		       " INNER JOIN Product p ON pp.idProduct = p.idProduct" +
		       " INNER JOIN ProductDuration pd ON pp.idProductDuration = pd.idProductDuration " +
		       " INNER JOIN ProductAmount pa ON pp.idProductAmount = pa.idProductAmount " +
		       " WHERE p.idProduct = :idProduct")
		public List<ProductPricingDTO> findProductPricingListById(@Param("idProduct") Long idProduct);

		@Query("SELECT new co.vistafoundation.vlearning.product.dto.ProductPricingDTO( pd.idProductDuration, pp.idProductPricing, pd.duration, pd.durationName, pa.idProductAmount, pa.amountName, pa.amount, p.idProduct, p.idProductGroup, p.productName, p.idProductLine, pp.activeFlag, pp.planDescription, pp.promoText, pp.isVisible) " +
		       " FROM ProductPricing pp " +
		       " INNER JOIN Product p ON pp.idProduct = p.idProduct" +
		       " INNER JOIN ProductDuration pd ON pp.idProductDuration = pd.idProductDuration " +
		       " INNER JOIN ProductAmount pa ON pp.idProductAmount = pa.idProductAmount")
		public List<ProductPricingDTO> findAllProductPricing();

	public  List<ProductPricing> findByIdProduct(Long idProduct);

	public ProductPricing getByIdProductPricing(Long id);

	@Query("SELECT new co.vistafoundation.vlearning.product.dto.ProductPricingDTO( pd.idProductDuration, pp.idProductPricing, pd.duration, pd.durationName, pa.idProductAmount, pa.amountName, pa.amount, p.idProduct, p.idProductGroup, p.productName, p.idProductLine, pp.activeFlag, pp.planDescription, pp.promoText) " +
		       " FROM ProductPricing pp " +
		       " INNER JOIN Product p ON pp.idProduct = p.idProduct" +
		       " INNER JOIN ProductDuration pd ON pp.idProductDuration = pd.idProductDuration " +
		       " INNER JOIN ProductAmount pa ON pp.idProductAmount = pa.idProductAmount " +
		       " WHERE pp.idProduct = :idProduct and pp.idProductDuration = :idProductDuration and pp.idProductAmount=:idProductAmount")
	public ProductPricingDTO getProductPricing(Long idProduct,Long idProductAmount,Long idProductDuration);

	public ProductPricing findByIdProductAmount(Long id);
	
	public ProductPricing findByIdProductDuration(Long id);
	
	public ProductPricing findByIdProductAmountAndIdProductDurationAndIdProduct(Long idProductAmount,
			Long idProductDuration, Long idProduct);

}
