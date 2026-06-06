package co.vistafoundation.vlearning.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuiz;

public interface StudentOfflineQuizRepository extends JpaRepository<StudentOfflineQuiz, Long>{

	public StudentOfflineQuiz findByIdOfflineVideoCourse(Long idOfflineVideoCourse);

	public List<StudentOfflineQuiz> findByIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourseAndIdStudentSubscrOrderByQuizDateDesc(Long idSubject, Long idChapter,
			Long idOfflineVideoCourse,Long idStudentSub);

	public StudentOfflineQuiz findByIdStudentOfflineQuiz(Long idStudentOfflineQuiz);

	public List<StudentOfflineQuiz> findByIdSubjectChapterAndIdStudentSubscrOrderByQuizDateDesc(Long idSubjectChapter,
			Long idStudentSubscription);
}
