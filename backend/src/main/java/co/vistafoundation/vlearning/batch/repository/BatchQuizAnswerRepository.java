package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchQuizAnwser;

public interface BatchQuizAnswerRepository extends JpaRepository<BatchQuizAnwser, Long> {

	BatchQuizAnwser findByIdBatchQuizQuestionAndFieldIdAndTextFieldValue(Long idBatchQuizQuestion, String fieldId,
			String textFieldValue);

	BatchQuizAnwser findByIdBatchQuizQuestionAndFieldIdAndCorrectValueFlag(Long idBatchQuizQuestion, String fieldId,
			Boolean true1);

	List<BatchQuizAnwser> findByIdBatchQuizQuestionAndCorrectValueFlag(Long idBatchQuizQuestion, boolean correctValueFlag);
	
	List<BatchQuizAnwser>findByIdBatchQuizQuestion(Long idBatchQuizQuestion);
	
	

	public int countByIdBatchQuizQuestionAndCorrectValueFlag(Long idBatchQuizQuestion, boolean correctValueFlag);
}
