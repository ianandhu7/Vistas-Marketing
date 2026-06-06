/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadAttendedClass;

/**
 * @author vk
 *
 */
public interface LeadAttendedClassRepository extends JpaRepository<LeadAttendedClass, Long>{

	LeadAttendedClass findByIdVlUserAndIdLeadBatchLog(Long idVlUser, Long idLeadBatchLog);

	LeadAttendedClass findByIdVlUser(Long idVlUser);

}
