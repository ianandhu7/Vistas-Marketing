/**
 * Test
 */
package co.vistafoundation.vlearning.offlinecourse.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoCourseRatingDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoDetailsDTO;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourseRating;

/**
 * @author Abdul Elahi
 */
public interface OfflineVideoCourseRatingRepository extends JpaRepository<OfflineVideoCourseRating, Long> {

	/**
	 * @param idOfflineVideoCourse
	 * @param userSurId
	 * @return
	 */
	OfflineVideoCourseRating findByidOfflineVideoCourseAndUserSurId(Long idOfflineVideoCourse, Long userSurId);

	@Query("SELECT AVG(r.rating) FROM OfflineVideoCourseRating r WHERE r.idOfflineVideoCourse = :idOfflineVideoCourse")
	Double calculateAverageRating(@Param("idOfflineVideoCourse") Long idOfflineVideoCourse);
	
    @Query("SELECT COUNT(r) FROM OfflineVideoCourseRating r WHERE r.idOfflineVideoCourse = :idOfflineVideoCourse")
	Long totalRatingForVideo(@Param("idOfflineVideoCourse") Long idOfflineVideoCourse);

	@Query("select new co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterDTO(u.userSurId, "
			+ "c.classStandadName , u.firstName, ovcr.rating, ovcr.createdAt,"
			+ "ovcr.review, sub.subjectName, ovc.topic) " + "from OfflineVideoCourseRating ovcr "
			+ "left join User u on ovcr.userSurId = u.userSurId "
			+ "left join Student s on s.user.userSurId = u.userSurId "
			+ "left join ClassStandard c on c.idClassStandard = s.idClassStandard "
			+ "left join OfflineVideoCourse ovc on ovc.idOfflineVideoCourse = ovcr.idOfflineVideoCourse "
			+ "left join Subject sub on ovc.idSubject = sub.idSubject "
			+ "where (:userSurId is null or u.userSurId = :userSurId) and "
			+ "(:idClassStandard is null or c.idClassStandard = :idClassStandard) and "
			+ "(:dateFrom is null or DATE(ovcr.createdAt) >= :dateFrom) and "
			+ "(:dateTo is null or DATE(ovcr.createdAt) <= :dateTo) and "
			+ "(:rating is null or ovcr.rating = :rating) and " + "(:idSubject is null or sub.idSubject = :idSubject) "
			+ "ORDER BY u.createdAt DESC ")
	Page<RatingFilterDTO> findRatingsDetails(@Param(value = "userSurId") Long userSurId,
			@Param(value = "idClassStandard") Long idClassStandard, @Param(value = "dateFrom") Date dateFrom,
			@Param(value = "dateTo") Date dateTo, @Param(value = "rating") Integer ratings,
			@Param(value = "idSubject") Long idSubject, Pageable page);

	@Query("SELECT new co.vistafoundation.vlearning.offlinecourse.dto.VideoDetailsDTO(ovc.idOfflineVideoCourse, "
			+ "cl.classStandadName, AVG(ovcr.rating), " + "sub.subjectName, ovc.topic, ovc.videoPoster1Location) "
			+ "FROM OfflineVideoCourse ovc "
			+ "INNER JOIN SubjectChapter c ON ovc.idSubjectChapter = c.idSubjectChapter "
			+ "INNER JOIN Subject sub ON c.idSubject = sub.idSubject "
			+ "INNER JOIN ClassStandard cl ON c.idClassStandard = cl.idClassStandard "
			+ "INNER JOIN OfflineVideoCourseRating ovcr ON ovc.idOfflineVideoCourse = ovcr.idOfflineVideoCourse "
			+ "WHERE (:idClassStandard is null or c.idClassStandard = :idClassStandard) and "
			+ "(:idSubject is null or sub.idSubject = :idSubject) and "
			+ "(:idState is null or c.idState = :idState) and "
			+ "(:idSyllabus is null or c.idSyllabus = :idSyllabus) and "
			+ "(:rating is null or (ovcr.rating BETWEEN :rating AND :rating + 1)) "
			+ "GROUP BY ovc.idOfflineVideoCourse, cl.classStandadName, sub.subjectName, ovc.topic, ovc.videoPoster1Location ")
	Page<VideoDetailsDTO> getVideoDetails(@Param("idClassStandard") Long idClassStandard,
			@Param("idSubject") Long idSubject, @Param("rating") Integer rating, @Param("idState") Long idState,
			@Param("idSyllabus") Long idSyllabus, Pageable pageable);

	@Query("SELECT new co.vistafoundation.vlearning.offlinecourse.dto.VideoCourseRatingDTO("
	        + "ovc.topic, ovcr.userSurId, u.username, s.schoolName, ovc.idOfflineVideoCourse, ovcr.rating, "
	        + "ovcr.review, ovcr.visibleFlag ) "
	        + "FROM OfflineVideoCourseRating ovcr "
	        + "JOIN OfflineVideoCourse ovc ON ovcr.idOfflineVideoCourse = ovc.idOfflineVideoCourse "
	        + "JOIN User u ON ovcr.userSurId = u.userSurId "
	        + "JOIN Student s ON u.userSurId = s.user.userSurId "
	        + "WHERE ovcr.idOfflineVideoCourse = :idOfflineVideoCourse "
	        + "AND (:userSurId IS NULL OR u.userSurId = :userSurId) "
	        + "AND (:userName IS NULL OR u.username LIKE CONCAT('%',:userName, '%')) "
	        + "AND (:schoolName IS NULL OR s.schoolName LIKE CONCAT('%',:schoolName, '%')) "
	        + "AND ovcr.rating BETWEEN :from AND :to")
	Page<VideoCourseRatingDTO> getRatingsForVideos(@Param("idOfflineVideoCourse") Long idOfflineVideoCourse,
	        @Param("userSurId") Long userSurId, 
	        @Param("userName") String userName,
	        @Param("schoolName") String schoolName, 
	        @Param("from") int from, @Param("to") int to, Pageable pageable);


	@Query(value = "SELECT avg(ovcr.rating) FROM offline_video_course ovc inner join offline_video_course_rating ovcr on "
			+ "ovc.idoffline_video_course = ovcr.id_offline_video_course "
			+ "where idproduct= :idProduct ", nativeQuery = true)
	Double avgSubjectRating(@Param("idProduct") Long idProduct);

	@Query(value = "SELECT avg(ovcr.rating) FROM offline_video_course ovc "
			+ "left join offline_video_course_rating ovcr on ovc.idoffline_video_course = ovcr.id_offline_video_course "
			+ "where idsubject_chapter= :idSubjectChapter", nativeQuery = true)
	Double avgChapterRating(@Param("idSubjectChapter") Long idSubjectChapter);

	List<OfflineVideoCourseRating> findByidOfflineVideoCourse(Long idOfflineVideoCourse);
}

