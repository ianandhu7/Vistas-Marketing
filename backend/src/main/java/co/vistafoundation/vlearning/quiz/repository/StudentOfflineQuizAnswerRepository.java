package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuizAnswer;

public interface StudentOfflineQuizAnswerRepository extends JpaRepository<StudentOfflineQuizAnswer, Long>{

	List<StudentOfflineQuizAnswer> findByIdStudentOfflineQuizQuestion(Long idQuizQuestion);

}
