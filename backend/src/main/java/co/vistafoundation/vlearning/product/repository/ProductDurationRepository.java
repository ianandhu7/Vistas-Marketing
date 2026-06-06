/**
 * 
 */
package co.vistafoundation.vlearning.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.product.model.ProductDuration;

/**
 * @author NaveenKumar
 *
 */
public interface ProductDurationRepository extends JpaRepository<ProductDuration, Long> {

   public ProductDuration findByIdProductDuration(Long idProductDuration);
   
   public ProductDuration findByDurationCode(String durationCode);

   public ProductDuration getByIdProductDuration(Long id);
   
   public ProductDuration findByDuration(Integer idDuration);

}
