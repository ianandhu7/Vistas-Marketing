package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.quiz.model.Quiz;



public interface QuizRepository extends JpaRepository<Quiz, Long>{
	
	public Quiz findByIdQuiz(Long idQuiz);
	
	public List<Quiz> findByIdProductAndIdSubject(Long idProduct, Long idSubject);
	
	public Quiz findByIdProductAndIdSubjectAndIdSubjectChapter(Long idProduct, Long idSubject, Long idSubjectChapter);
	
	public Quiz findByIdSubjectAndIdSubjectChapter(Long idSubject, Long idSubjectChapter);
	
	public Quiz findByIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourse(Long idSubject, Long idSubjectChapter,Long idOfflineVideoCourse);
	
	public Quiz findByIdOfflineVideoCourse(Long idOfflineVideoCourse);
	
	public Quiz findByIdProductAndIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourse(Long idProduct, Long idSubject, Long idSubjectChapter, Long idOfflineVideoCourse);

	public Quiz findFirstByIdProductAndIdSubjectAndIdSubjectChapter(Long idProduct,
			Long idSubject, Long idSubjectChapter);
	
	
	
	
	
	@Query(value=" SELECT  count(qq.idquiz_question) as count , qq.question_type, qq.marks,qq.question_title "
			+ " FROM quiz_question qq where qq.idquiz in  (select q.idquiz from quiz q "
			+ " where q.idproduct = :idProduct ) and qq.question_active_flag=:activeFlag"
			+ " GROUP BY qq.question_type,qq.marks,qq.question_title order by qq.marks asc",nativeQuery = true)
	public List<Object[]> getVCTQuizQuestionTypesByMarks(Long idProduct,Boolean activeFlag);
 
}
