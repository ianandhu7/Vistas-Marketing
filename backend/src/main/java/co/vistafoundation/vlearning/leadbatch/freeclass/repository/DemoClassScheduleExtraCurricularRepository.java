/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricularSchedule;

/**
 * @author AHmed
 *
 */

public interface DemoClassScheduleExtraCurricularRepository
		extends JpaRepository<DemoClassExtraCurricularSchedule, Long> {

	DemoClassExtraCurricularSchedule findByIdDemoClassExtraCurricular(Long idDemoClassExtraCurricular);

}
