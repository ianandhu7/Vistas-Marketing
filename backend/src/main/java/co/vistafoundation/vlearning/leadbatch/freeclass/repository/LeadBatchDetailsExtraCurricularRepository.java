/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetailsExtraCurricular;

/**
 * @author Ahmed
 *
 */
public interface LeadBatchDetailsExtraCurricularRepository
		extends JpaRepository<LeadBatchDetailsExtraCurricular, Long> {

	LeadBatchDetailsExtraCurricular findByIdSubjectExtraCurricularAndIdVlUser(Long idSubjectExtraCurricular,
			Long idVlUser);

	LeadBatchDetailsExtraCurricular findByIdVlUser(Long userSurId);

	List<LeadBatchDetailsExtraCurricular> findByIdSubjectExtraCurricularAndIdLevelExtraCurricular(Long idSubject, Long idLevel);
/**
 * 
 *@author Sajini
 * 
 */

	/*  Query for distinct Extra curricular demo class details*/
	@Query("select distinct  a.idSubjectExtraCurricular,  a.idLevelExtraCurricular, a.idLanguage,  a.idAvailableSlot from LeadBatchDetailsExtraCurricular a")
	List<Object[]> findAllDistinctExctraCurData();
	
	
	int countByIdSubjectExtraCurricularAndIdLevelExtraCurricularAndIdLanguageAndIdAvailableSlot(
			 Long idSubjectExtraCurricular, Long idLevelExtraCurricular, Long idLanguage,
			Long idAvailableSlot);

}
