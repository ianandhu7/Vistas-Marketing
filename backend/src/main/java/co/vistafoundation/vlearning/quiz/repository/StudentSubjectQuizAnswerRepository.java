package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuizAnswer;

public interface StudentSubjectQuizAnswerRepository extends JpaRepository<StudentSubjectQuizAnswer, Long> {

	List<StudentSubjectQuizAnswer> findByIdStudentSubjectQuizQuestion(Long idStudentSubjectQuizQuestion);

}
