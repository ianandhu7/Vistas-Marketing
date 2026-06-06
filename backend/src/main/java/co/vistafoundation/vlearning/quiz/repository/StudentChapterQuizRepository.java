package co.vistafoundation.vlearning.quiz.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.QuizStatisticsDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectQuizStatisticsDTO;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;

public interface StudentChapterQuizRepository extends JpaRepository<StudentChapterQuiz, Long> {

	List<StudentChapterQuiz> findByIdStudentSubscrAndIdSubjectChapterOrderByQuizDateDesc(Long idStudentSubscription,
			Long idChapter);

	StudentChapterQuiz findByIdStudentChapterQuiz(Long idStudentChapterQuiz);
	
	List<StudentChapterQuiz> findByIdSubjectChapterAndIdStudentSubscrOrderByQuizDateDesc(Long idSubjectChapter, Long idStudentSubscription);
	
	
	StudentChapterQuiz findDistinctByIdStudentSubscrAndIdSubjectChapter(Long idStudentSubscription,
			Long idChapter);
	

	@Query("SELECT COUNT(ssq) FROM StudentChapterQuiz ssq WHERE ssq.createdAt BETWEEN :startDate AND :endDate "
			+ "AND ssq.idSubjectChapter = :idSubjectChapter")
    Long findCountByCreatedAtBetweenAndIdSubject(Instant startDate, Instant endDate, Long idSubjectChapter);

	/**
	 * @param startInstant
	 * @param endInstant
	 * @param idSubjectChapter
	 * @param pageable
	 * @return
	 */
	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO(u.userSurId, u.firstName, "
			+ "c.classStandadName, st.state, "
			+ "sy.syllabusName, s.schoolName, su.subjectName,sc.chapterName, scq.createdAt, scq.quizScore) "
			+ "FROM StudentChapterQuiz scq " 
			+ "INNER JOIN User u on scq.createdBy = u.userSurId "
			+ "INNER JOIN Student s on u.userSurId = s.user.userSurId "
			+ "INNER JOIN ClassStandard c on s.idClassStandard = c.idClassStandard "
			+ "INNER JOIN Syllabus sy on s.idSyllabus = sy.idSyllabus "
			+ "INNER JOIN SubjectChapter sc on scq.idSubjectChapter = sc.idSubjectChapter " 
			+ "INNER JOIN Subject su on sc.idSubject = su.idSubject "
			+ "INNER JOIN State st on s.idState = st.idState " + "WHERE scq.idSubjectChapter = :idSubjectChapter "
			+ "AND scq.createdAt BETWEEN :startDate AND :endDate"
			+ " ORDER BY scq.createdAt DESC")
	Page<QuizScoreResponseDTO> findQuizScoreBetweenCreatedAt(Instant startDate, Instant endDate,
			Long idSubjectChapter, Pageable pageable);

	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO(u.userSurId, u.firstName, "
			+ "c.classStandadName, st.state, "
			+ "sy.syllabusName, s.schoolName, su.subjectName,sc.chapterName, scq.createdAt, scq.quizScore) "
			+ "FROM StudentChapterQuiz scq " 
			+ "INNER JOIN User u on scq.createdBy = u.userSurId "
			+ "INNER JOIN Student s on u.userSurId = s.user.userSurId "
			+ "INNER JOIN ClassStandard c on s.idClassStandard = c.idClassStandard "
			+ "INNER JOIN Syllabus sy on s.idSyllabus = sy.idSyllabus "
			+ "INNER JOIN SubjectChapter sc on scq.idSubjectChapter = sc.idSubjectChapter " 
			+ "INNER JOIN Subject su on sc.idSubject = su.idSubject "
			+ "INNER JOIN State st on s.idState = st.idState " 
			+ "WHERE scq.idSubjectChapter = :idSubjectChapter "
			+ " ORDER BY scq.createdAt DESC")
	Page<QuizScoreResponseDTO> findQuizScore(Long idSubjectChapter, Pageable pageable);
	
	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.QuizStatisticsDTO(" +
		       "COUNT(scq.idStudentChapterQuiz), " +     
		       "COALESCE(AVG(CASE WHEN scq.quizScore > 40 THEN scq.quizScore ELSE NULL END), 0)) " +
		       "FROM StudentChapterQuiz scq " +
		       "JOIN StudentSubscription ss ON ss.idStudentSubscription = scq.idStudentSubscr " +
		       "WHERE ss.userSurId = :userSurId")
		QuizStatisticsDTO getQuizStatistics(@Param("userSurId") Long userSurId);


	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.SubjectQuizStatisticsDTO(" +
		       "s.subjectName, " +
		       "COUNT(scq.idStudentChapterQuiz), " +
		       "MAX(scq.quizScore))" + 
		       "FROM StudentChapterQuiz scq " +
		       "JOIN StudentSubscription ss ON ss.idStudentSubscription = scq.idStudentSubscr " +
		       "JOIN Product p ON p.idProduct = ss.idProduct " +
		       "JOIN Subject s ON s.idSubject = p.idSubject " +
		       "WHERE ss.userSurId = :userSurId " +
		       "GROUP BY s.subjectName " +
		       "ORDER BY COUNT(scq.idStudentChapterQuiz) DESC")
		List<SubjectQuizStatisticsDTO> getSubjectQuizStatistics(@Param("userSurId") Long userSurId);




}
