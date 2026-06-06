/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.ExtraCurricularLeadAttendedClass;

/**
 * @author Ahmed
 *
 */

public interface ExtraCurricularLeadAttendedClassRepository
		extends JpaRepository<ExtraCurricularLeadAttendedClass, Long> {

	ExtraCurricularLeadAttendedClass findByIdVlUserAndIdLeadBatchLogExtraCurricular(Long idVlUser,
			Long idLeadBatchLogExtraCurricular);

}
