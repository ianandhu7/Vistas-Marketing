package co.vistafoundation.vlearning.offlinecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.subscription.dto.SubjectVideoWatchDTO;
import co.vistafoundation.vlearning.subscription.dto.TotalVideoWatchDTO;

/**
 * 
 * @author Naveen Kumar
 *
 */
public interface StudentAssignedCourseRepository extends JpaRepository<StudentAssignedCourse, Long> {

	List<StudentAssignedCourse> findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotIn(Long idStudentSubscription,
			Long idSubject, List<String> videoIds);

	List<StudentAssignedCourse> findByIdStudentSubscriptionAndIdSubjectAndIdSubjectChapterOrderByVideoSeqNumberAsc(
			Long idStudentSubscription, Long idSubject, Long idSubjectChapter);

	List<StudentAssignedCourse> findByIdStudentSubscription(Long idStudentSubscription);

	@Query(value = "select * from student_assigned_course s group by s.idsubject, s.idstudent_subscr", nativeQuery = true)
	List<StudentAssignedCourse> getAllAssignedCourse();

	public StudentAssignedCourse findByIdStudentAssignedCourse(Long idStudentAssignedCourse);

	public StudentAssignedCourse findByIdOfflineVideoCourseAndIdStudentSubscription(Long idOfflineCourse,
			Long idStudentSubcription);

	List<StudentAssignedCourse> findByIdStudentSubscriptionAndIdProductAndCompleteFlag(Long idStudentSubscription,
			Long idProduct, Boolean completeFlag);

	List<StudentAssignedCourse> findByIdOfflineVideoCourse(Long idOfflineVideoCourse);

	@Query(value = "select * from student_assigned_course left join student_subscription"
			+ "on student_assigned_course.idproduct = student_subscription.idproduct and student_subscription.idstudent=:idStudent and student_subscription.idproduct_line=:idProductLine;", nativeQuery = true)
	List<StudentAssignedCourse> findSubscribedSubProgressData(Long idStudent, Long idProductLine);

	List<StudentAssignedCourse> findByIdSubjectIn(List<Long> subIdList);

	List<StudentAssignedCourse> findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndIdSubjectChapter(
			Long idStudentSubscription, Long idSubject, List<String> marketingIds, Long idSubjectChapter);

	List<StudentAssignedCourse> findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndCompleteFlag(
			Long parseLong, Long idSubject, List<String> marketingIds, Boolean CompleteFlag);

	StudentAssignedCourse findFirstByIdOfflineVideoCourseAndIdStudentSubscription(Long idOfflineVideoCourse,
			Long idStudentSubscription);
	
	@Query("SELECT new co.vistafoundation.vlearning.subscription.dto.TotalVideoWatchDTO(" +
		       "COUNT(sac.idStudentAssignedCourse), " +
		       "SUM(sac.videoCoverageDuration)) " +
		       "FROM StudentAssignedCourse sac " +
		       "JOIN StudentSubscription ss ON sac.idStudentSubscription = ss.idStudentSubscription " +
		       "JOIN User u ON u.userSurId = ss.userSurId " +
		       "WHERE u.userSurId = :userSurId " +
		       "GROUP BY u.userSurId " + // Grouping by userSurId to use HAVING
		       "HAVING SUM(sac.videoCoverageDuration) > 300")
	TotalVideoWatchDTO	 getTotalVideoProgress(@Param("userSurId") Long userSurId);


	@Query("SELECT new co.vistafoundation.vlearning.subscription.dto.SubjectVideoWatchDTO(\n"
			+ "s.subjectName, \n"
			+ "COUNT(sac.idStudentAssignedCourse), \n"
			+ "SUM(sac.videoCoverageDuration)) \n"
			+ "FROM StudentAssignedCourse sac \n"
			+ "JOIN StudentSubscription ss ON ss.idStudentSubscription = sac.idStudentSubscription \n"
			+ "JOIN Subject s ON s.idSubject = sac.idSubject \n"
			+ "WHERE ss.userSurId = :vlUserId \n"
			+ "GROUP BY sac.idSubject, s.subjectName \n"
			+ "HAVING SUM(sac.videoCoverageDuration) > 300 \n"
			+ "ORDER BY SUM(sac.videoCoverageDuration) DESC")
		List<SubjectVideoWatchDTO> getSubjectWatchDetails(@Param("vlUserId") Long vlUserId);

}
