package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	
	public Answer findByIdQuizQuestionAndFieldIdAndTextFieldValue(Long idQuizQuestion, String fieldId, String textFieldValue);
	
//	@Query(value = "SELECT * FROM Answer  WHERE idQUIZ_QUESTION = ?1 and FIELD_ID = ?2 and TEXT_FIELD_VALUE= ?3", nativeQuery = true)
//	Answer findAnswerByIdQuizQuestionAndFieldIdAndTextFieldValue(Long idQuizQuestion, String fieldId, String textFieldValue);
	
	public Answer findByIdQuizQuestionAndFieldIdAndCorrectValueFlag(Long idQuizQuestion, String fieldId, boolean correctValueFlag);
//	@Query(value = "SELECT * FROM Answer  WHERE idQUIZ_QUESTION = ?1 and FIELD_ID = ?2 and CORRECT_VALUE_FLAG= ?3", nativeQuery = true)
//	Answer findAnswerByIdQuizQuestionAndFieldIdAndCorrectValueFlag(Long idQuizQuestion, String fieldId, boolean correctValueFlag);
	
	public List<Answer> findByIdQuizQuestionAndCorrectValueFlag(Long idQuizQuestion, boolean correctValueFlag);

	public int countByIdQuizQuestionAndCorrectValueFlag(Long idQuizQuestion, boolean correctValueFlag);

	public List<Answer> findByIdQuizQuestion(Long idQuizQuestion);

}
