/**
 * 
 */
package co.vistafoundation.vlearning.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import co.vistafoundation.vlearning.product.model.ProductAmount;

/**
 * @author NaveenKumar
 *
 */
public interface ProductAmountRepository extends JpaRepository<ProductAmount, Long> {

	public ProductAmount findByIdProductAmount(Long idProductAmount);
    List<ProductAmount> findAllByAmountCode(String amountCode);
	public ProductAmount getByIdProductAmount(Long id);
	
	public ProductAmount findByAmount(Float amount);

}
