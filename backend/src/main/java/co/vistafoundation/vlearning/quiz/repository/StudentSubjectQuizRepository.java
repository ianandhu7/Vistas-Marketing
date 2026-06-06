package co.vistafoundation.vlearning.quiz.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectRankingDTO;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuiz;

public interface StudentSubjectQuizRepository extends JpaRepository<StudentSubjectQuiz, Long> {

	StudentSubjectQuiz findByIdStudentSubjectQuiz(Long idStudentSubjectQuiz);

	List<StudentSubjectQuiz> findByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(Long idStudentSubscription,
			Long idSubject);

	List<StudentSubjectQuiz> findByIdStudentSubscrAndIdSubjectOrderByIdStudentSubjectQuizAsc(Long idStudentSubscription,
			Long idSubject);

	StudentSubjectQuiz findFirstByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(Long idStudentSubscr, Long idSubject);

	StudentSubjectQuiz findFirstByIdStudentSubscrAndIdSubjectAndQuizDateBetweenOrderByQuizDateDesc(Long idStudentSubscr,
			Long idSubject, Instant fromDate, Instant toDate);

	public Boolean existsByIdStudentSubscrInAndQuizDateBetweenOrderByQuizDateDesc(List<Long> idStundetSubscriptions,
			Instant fromDate, Instant toDate);

	@Query(value = "select new co.vistafoundation.vlearning.quiz.dto.SubjectRankingDTO"
			+ "(ssq.idStudentSubscr,ssq.quizScore,ssq.idStudentSubjectQuiz,cs.idClassStandard,"
			+ "sy.idSyllabus,st.idState,s.idSubject,u.userSurId,p.idProduct,ssq.quizDate)"
			+ " from StudentSubjectQuiz ssq inner join Quiz q on q.idQuiz= ssq.idQuiz "
			+ " inner join Product p on p.idProduct = q.idProduct and p.idProductLine=13"
			+ " inner join Subject s on s.idSubject= q.idSubject and (:idSubject is null or s.idSubject=:idSubject)"
			+ " inner join State st on p.idState= st.idState and (:idState is null or st.idState=:idState)"
			+ " inner join ClassStandard cs on p.idClassStandard=cs.idClassStandard and (:idClassStandard is null or cs.idClassStandard=:idClassStandard)"
			+ " inner join Syllabus sy on p.idSyllabus=sy.idSyllabus and (:idSyllabus is null or sy.idSyllabus=:idSyllabus)"
			+ " inner join StudentSubscription ss on ss.idStudentSubscription = ssq.idStudentSubscr "
			+ " inner join User u on ss.userSurId= u.userSurId"
			+ " where (ssq.quizDate BETWEEN '2023-02-04 00:00:00' AND '2023-02-13 18:00:00')"
			+ " order by ssq.quizScore desc")
	public List<SubjectRankingDTO> getSubjectListRanking(Long idClassStandard, Long idState, Long idSyllabus,
			Long idSubject);
	
	@Query(value = "select new co.vistafoundation.vlearning.quiz.dto.SubjectRankingDTO"
			+ "(ssq.idStudentSubscr,ssq.quizScore,ssq.idStudentSubjectQuiz,cs.idClassStandard,"
			+ "sy.idSyllabus,st.idState,s.idSubject,u.userSurId,p.idProduct,ssq.quizDate)"
			+ " from StudentSubjectQuiz ssq inner join Quiz q on q.idQuiz= ssq.idQuiz "
			+ " inner join Product p on p.idProduct = q.idProduct and p.idProductLine=13"
			+ " inner join Subject s on s.idSubject= q.idSubject and (:idSubject is null or s.idSubject=:idSubject)"
			+ " inner join State st on p.idState= st.idState and (:idState is null or st.idState=:idState)"
			+ " inner join ClassStandard cs on p.idClassStandard=cs.idClassStandard and (:idClassStandard is null or cs.idClassStandard=:idClassStandard)"
			+ " inner join Syllabus sy on p.idSyllabus=sy.idSyllabus and (:idSyllabus is null or sy.idSyllabus=:idSyllabus)"
			+ " inner join StudentSubscription ss on ss.idStudentSubscription = ssq.idStudentSubscr "
			+ " inner join User u on ss.userSurId= u.userSurId"
			+ " where (ssq.quizDate BETWEEN '2023-02-04 00:00:00' AND '2023-02-13 18:00:00')"
			+ " order by ssq.quizScore desc")
	public List<SubjectRankingDTO> getSubjectListRankingWithMarkConstrain(Long idClassStandard, Long idState, Long idSyllabus,
			Long idSubject);
	

	@Query("SELECT COUNT(ssq) FROM StudentSubjectQuiz ssq "
			+ "INNER JOIN User u on ssq.createdBy = u.userSurId "
			+ "INNER JOIN Student s on u.userSurId = s.user.userSurId "
			+ "INNER JOIN ClassStandard c on s.idClassStandard = c.idClassStandard "
			+ "INNER JOIN Syllabus sy on s.idSyllabus = sy.idSyllabus "
			+ "INNER JOIN Subject su on ssq.idSubject = su.idSubject "
			+ "INNER JOIN State st on s.idState = st.idState " 
			+ "WHERE ssq.idSubject = :idSubject "
			+ "AND ssq.createdAt BETWEEN :startDate AND :endDate "
	        + "AND (:idState IS NULL OR st.idState = :idState) "
	        + "AND (:idSyllabus IS NULL OR sy.idSyllabus = :idSyllabus) "
	        + "AND (:idClassStandard IS NULL OR c.idClassStandard = :idClassStandard)")
	Long findCountByCreatedAtBetweenAndIdSubject(@Param("startDate") Instant startDate,
			@Param("endDate") Instant endDate, @Param("idSubject") Long idSubject, 
			Long idClassStandard, Long idState, Long idSyllabus);

	/**
	 * @param userSurID
	 * @param name
	 * @param classStandard
	 * @param state
	 * @param syllabus
	 * @param schoolName
	 * @param subject
	 * @param quizTaken
	 * @param quizScore
	 */
	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO(u.userSurId, u.firstName, "
			+ "c.classStandadName, st.state, "
			+ "sy.syllabusName, s.schoolName, su.subjectName, ssq.createdAt, ssq.quizScore, s.idClassStandard, "
			+ "sy.idSyllabus, st.idState ) "
			+ "FROM StudentSubjectQuiz ssq " 
			+ "INNER JOIN User u on ssq.createdBy = u.userSurId "
			+ "INNER JOIN Student s on u.userSurId = s.user.userSurId "
			+ "INNER JOIN ClassStandard c on s.idClassStandard = c.idClassStandard "
			+ "INNER JOIN Syllabus sy on s.idSyllabus = sy.idSyllabus "
			+ "INNER JOIN Subject su on ssq.idSubject = su.idSubject "
			+ "INNER JOIN State st on s.idState = st.idState " 
			+ "WHERE ssq.idSubject = :idSubject "
			+ "AND ssq.createdAt BETWEEN :startDate AND :endDate "
	        + "AND (:idState IS NULL OR st.idState = :idState) "
	        + "AND (:idSyllabus IS NULL OR sy.idSyllabus = :idSyllabus) "
	        + "AND (:idClassStandard IS NULL OR c.idClassStandard = :idClassStandard) "
	        + "ORDER BY ssq.createdAt DESC")
	Page<QuizScoreResponseDTO> findQuizScoreBetweenCreatedAt(Instant startDate, Instant endDate, Long idSubject,Long idClassStandard, Long idState, 
	        Long idSyllabus,Pageable pageable);
	
	@Query("SELECT new co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO(u.userSurId, u.firstName, "
	        + "c.classStandadName, st.state, "
	        + "sy.syllabusName, s.schoolName, su.subjectName, ssq.createdAt, ssq.quizScore, s.idClassStandard, "
	        + "sy.idSyllabus, st.idState ) "
	        + "FROM StudentSubjectQuiz ssq " 
	        + "INNER JOIN User u on ssq.createdBy = u.userSurId "
	        + "INNER JOIN Student s on u.userSurId = s.user.userSurId "
	        + "INNER JOIN ClassStandard c on s.idClassStandard = c.idClassStandard "
	        + "INNER JOIN Syllabus sy on s.idSyllabus = sy.idSyllabus "
	        + "INNER JOIN Subject su on ssq.idSubject = su.idSubject "
	        + "INNER JOIN State st on s.idState = st.idState " 
	        + "WHERE ssq.idSubject = :idSubject "
	        + "AND (:idState IS NULL OR st.idState = :idState) "
	        + "AND (:idSyllabus IS NULL OR sy.idSyllabus = :idSyllabus) "
	        + "AND (:idClassStandard IS NULL OR c.idClassStandard = :idClassStandard) "
	        + "ORDER BY ssq.createdAt DESC")
	Page<QuizScoreResponseDTO> findQuizScoreBetweenCreatedAt(Long idSubject, Long idClassStandard, Long idState, 
	        Long idSyllabus, Pageable pageable);

}
