package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuizQuestion;

public interface StudentSubjectQuizQuestionRepository extends JpaRepository<StudentSubjectQuizQuestion, Long> {

	List<StudentSubjectQuizQuestion> findByIdStudentSubjectQuiz(Long idStudentSubjectQuiz);
	
	
	@Query(value = "SELECT ssqq FROM StudentSubjectQuizQuestion ssqq "
			+ " inner join StudentSubjectQuiz ssq on ssq.idStudentSubjectQuiz = ssqq.idStudentSubjectQuiz "
			+ " inner join QuizQuestion qq on qq.idQuizQuestion = ssqq.idQuizQuestion "
			+ " where ssq.idStudentSubjectQuiz= :idStudentSubjectQuiz and "
			+ " qq.questionType= :questionType and ssqq.correctFlag=:correctFlag and qq.marks= :mark and qq.questionTitle=:questionTitle")
	List<StudentSubjectQuizQuestion> findByLatestQuizQuestionByCorrectAnswer(Long idStudentSubjectQuiz,
			String questionType,Boolean correctFlag,Short mark,String questionTitle);

}
