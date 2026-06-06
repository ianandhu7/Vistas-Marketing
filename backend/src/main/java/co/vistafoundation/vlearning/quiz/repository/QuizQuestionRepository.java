package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

	List<QuizQuestion> findByQuiz_idQuizAndQuestionActiveFlag(Long idQuiz, Boolean questionActiveFlag);

	public QuizQuestion findByIdQuizQuestion(Long idQuizQuestion);

	List<QuizQuestion> findByQuiz(Quiz quiz);

	QuizQuestion findByIdQuizQuestionAndQuestionActiveFlag(Long idQuizQuestion, Boolean questionActiveFlag);

	List<QuizQuestion> findByQuizAndQuestionActiveFlag(Quiz quiz, Boolean questionActiveFlag);

	@Query(value = "select qq from QuizQuestion qq inner join Quiz q on q.idQuiz=qq.quiz.idQuiz where q.idSubjectChapter= :idSubjectChapter and qq.questionActiveFlag= :questionActiveFlag")
	public List<QuizQuestion> getByIdSubjectChapter(Long idSubjectChapter, Boolean questionActiveFlag);

	@Query(value = "select qq from QuizQuestion qq inner join Quiz q on q.idQuiz=qq.quiz.idQuiz where q.idSubjectChapter= :idSubjectChapter and q.idSubject = :idSubject and q.idProduct =:idProduct  and qq.questionActiveFlag= :questionActiveFlag")
	public List<QuizQuestion> findByProductAndSubjectAndSubjectChapter(Long idProduct, Long idSubject,
			Long idSubjectChapter, Boolean questionActiveFlag);
	
	@Query(value = "select qq from QuizQuestion qq inner join Quiz q on q.idQuiz=qq.quiz.idQuiz"
			+ " where q.idSubjectChapter= :idSubjectChapter and q.idSubject = :idSubject "
			+ "and q.idProduct =:idProduct  and qq.questionActiveFlag= :questionActiveFlag  and qq.questionTitle like %:title%  and qq.questionType = :type and qq.marks = :mark")
	public List<QuizQuestion> findByProductAndQuestionType(Long idProduct, Long idSubject,
			Long idSubjectChapter, Boolean questionActiveFlag,String type,Short mark,String title);
	
	@Query("SELECT sum(qq.marks) from QuizQuestion qq where qq.quiz.idQuiz=:idQuiz")
	public Integer sumOfMarks(Long idQuiz);
	
	@Query("SELECT sum(qq.marks) from QuizQuestion qq where qq.quiz.idProduct in (:idProducts) and qq.questionActiveFlag=:active")
	public Integer sumOfMarksAllIdProducts(List<Long> idProducts,Boolean active);
	

	public Boolean existsByQuiz_idSubjectChapterAndQuestionActiveFlag(Long idSubjectChapter, Boolean questionActiveFlag);
	
	@Query(value = "select qq from QuizQuestion qq inner join Quiz q on q.idQuiz=qq.quiz.idQuiz where q.idSubjectChapter= :idSubjectChapter and q.idSubject=:idSubject  and qq.questionActiveFlag= :questionActiveFlag")
	public List<QuizQuestion> getByIdSubjectChapterAndIdSubject(Long idSubjectChapter, Long idSubject, Boolean questionActiveFlag);
}
