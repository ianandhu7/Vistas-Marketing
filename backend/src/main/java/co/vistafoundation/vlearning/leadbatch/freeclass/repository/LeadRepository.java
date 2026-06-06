/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.Lead;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public interface LeadRepository extends JpaRepository<Lead, Long> {
	
	Lead findByIdLead(Long idLead);

}
