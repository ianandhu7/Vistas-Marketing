/**
 * 
 */
package co.vistafoundation.vlearning.marketer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.marketer.model.Marketer;

/**
 * @author NAVEEN
 *
 */
public interface MarketerRepository extends JpaRepository<Marketer, Long> {
	
	Marketer findByIdVlUser (Long idVlUser);

	Marketer findByIdMarketer(Long onBoardingIdMarketer);

}
