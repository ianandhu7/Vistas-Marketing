package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.quiz.model.BatchStudentQuiz;

public interface BatchStudentQuizRepository extends JpaRepository<BatchStudentQuiz, Long> {

	BatchStudentQuiz findByIdStudentSubscription(Long idStudentScription);

	List<BatchStudentQuiz> findByIdStudentSubscriptionAndIdBatchQuizAssignment(Long idStudentSubscription,
			Long idBatchQuizAssignment);

	BatchStudentQuiz findByIdBatchStudentQuiz(Long idBatchStudentQuiz);

	List<BatchStudentQuiz> findByIdBatchQuizAssignment(Long idBatchQuizAssignment);
	
	@Query(value = "SELECT COUNT(*) FROM vlearning_dev.batch_student_quiz WHERE quiz_complete_flag=1 && idbatch_quiz_assignment=?", nativeQuery = true)
	int getStudentQuizAttemptedCount(Long IdBatchQuizAssignment);

}
