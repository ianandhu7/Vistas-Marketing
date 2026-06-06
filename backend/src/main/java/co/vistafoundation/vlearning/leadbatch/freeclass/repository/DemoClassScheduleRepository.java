/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassSchedule;

/**
 * @author vk
 *
 */
public interface DemoClassScheduleRepository extends JpaRepository<DemoClassSchedule, Long>{

	DemoClassSchedule findByIdDemoClass(Long idDemoClass);

}
