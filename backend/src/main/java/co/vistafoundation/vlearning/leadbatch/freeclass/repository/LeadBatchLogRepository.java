/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLog;

/**
 * @author vk
 *
 */
public interface LeadBatchLogRepository extends JpaRepository<LeadBatchLog, Long>{

	List<LeadBatchLog> findByIdDemoClass(Long idDemoClass);

	List<LeadBatchLog> findByIdVlUser(Long idVlUser);

	LeadBatchLog findByIdClassStandardAndIdSubjectAndIdVlUser(Long idClassStandard, Long idSubject,Long idVlUser);

}
