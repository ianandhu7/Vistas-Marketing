package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuizQuestion;

public interface StudentOfflineQuizQuestionRepository extends JpaRepository<StudentOfflineQuizQuestion, Long>{

	List<StudentOfflineQuizQuestion> findByIdStudentOfflineQuizQuestion(Long idStudentSubjectQuiz);

	List<StudentOfflineQuizQuestion> findByIdStudentOfflineQuiz(Long idStudentOfflineQuiz);

	//List<StudentOfflineQuizQuestion> findByQuiz(StudentOfflineQuiz quiz);
}
