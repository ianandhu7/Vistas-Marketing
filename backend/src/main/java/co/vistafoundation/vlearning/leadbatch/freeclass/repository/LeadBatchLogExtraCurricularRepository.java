/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLogExtraCurricular;

/**
 * @author Ahmed
 *
 */
public interface LeadBatchLogExtraCurricularRepository extends JpaRepository<LeadBatchLogExtraCurricular, Long> {

	LeadBatchLogExtraCurricular findByIdSubjectExtraCurricularAndIdVlUser(Long idSubjectExtraCurricular, Long idVlUser);

	LeadBatchLogExtraCurricular findByIdVlUser(Long userSurId);

	List<LeadBatchLogExtraCurricular> findByIdDemoClassExtraCurricular(Long idDemoClassExtraCurricular);

}
