package co.vistafoundation.vlearning.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchStudent;

/**
 * @author Kamachi Devi
 *
 */
public interface BatchStudentRepository extends JpaRepository<BatchStudent, Long> {

	BatchStudent findByIdStudentSubscription(Long idStudentSubscr);
	


}
