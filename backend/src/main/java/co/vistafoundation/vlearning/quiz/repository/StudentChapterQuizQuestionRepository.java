package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizQuestion;

public interface StudentChapterQuizQuestionRepository extends JpaRepository<StudentChapterQuizQuestion, Long> {

	List<StudentChapterQuizQuestion> findByIdStudentChapterQuiz(Long idStudentChapterQuiz);

}
