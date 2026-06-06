package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizAnswer;

public interface StudentChapterQuizAnswerRepository extends JpaRepository<StudentChapterQuizAnswer, Long> {

	List<StudentChapterQuizAnswer> findByIdStudentChapterQuizQuestion(Long idStudentChapterQuizQuestion);

}
