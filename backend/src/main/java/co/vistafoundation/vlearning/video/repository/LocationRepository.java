/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.Location;

/**
 * @author Naveen Kumar
 *
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

	Location findByCityName(String city);

}
