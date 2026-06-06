/**
 * 
 */
package co.vistafoundation.vlearning.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchGroup;

/**
 * @author NAVEEN
 *
 */
public interface BatchGroupRepository extends JpaRepository<BatchGroup, Long> {
	
	public BatchGroup findByIdBatchGroup(Long idBatchGroup);

}
