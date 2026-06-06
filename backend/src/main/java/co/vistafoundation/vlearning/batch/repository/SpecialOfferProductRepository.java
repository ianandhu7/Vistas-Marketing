/**
 * 
 */
package co.vistafoundation.vlearning.batch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.SpecialOfferProduct;

/**
 * @author NAVEEN
 *
 */
public interface SpecialOfferProductRepository extends JpaRepository<SpecialOfferProduct, Long> {

	List<SpecialOfferProduct> findByIdBatch(Long idBatch);
	
	Optional<SpecialOfferProduct> findByIdBatchAndIdSpecialOffer(Long idBatch, Long idSpecialOffer);
}
