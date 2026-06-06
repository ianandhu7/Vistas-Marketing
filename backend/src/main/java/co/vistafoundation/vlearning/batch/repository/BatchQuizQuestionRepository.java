package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;

public interface BatchQuizQuestionRepository extends JpaRepository<BatchQuizQuestion, Long>{

	List<BatchQuizQuestion> findByBatchQuizMeta_IdBatchQuizMeta(Long idBatchQuizMeta);
	BatchQuizQuestion findByIdBatchQuizQuestion(Long idBatchQuizQuestion);
}
