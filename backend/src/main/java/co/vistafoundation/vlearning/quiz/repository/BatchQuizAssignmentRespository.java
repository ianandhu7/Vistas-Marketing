package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.BatchQuizAssignment;

public interface BatchQuizAssignmentRespository extends JpaRepository<BatchQuizAssignment, Long> {

	List<BatchQuizAssignment> findByIdBatch(Long idBatch);

	List<BatchQuizAssignment> findByIdBatchAndIdBatchQuizMeta(Long idBatch, Long idBatchQuizMeta);

	BatchQuizAssignment findByIdBatchQuizAssignment(Long idBatchQuizAssignment);

}
