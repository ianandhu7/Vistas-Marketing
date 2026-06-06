package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchStudentQuizAnswer;

/**
 * @author KamachiDevi
 *
 */
public interface BatchStudentQuizAnswerRepository extends JpaRepository<BatchStudentQuizAnswer, Long> {

	List<BatchStudentQuizAnswer> findByIdBatchStudentQuizQuestion(Long idBatchQuizQuestion);

}
