package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchStudentQuizQuestion;

/**
 * @author KamachiDevi
 *
 */
public interface BatchStudentQuizQuestionRepository extends JpaRepository<BatchStudentQuizQuestion, Long> {

	List<BatchStudentQuizQuestion> findByIdBatchStudentQuiz(Long idBatchStudentQuiz);

}
