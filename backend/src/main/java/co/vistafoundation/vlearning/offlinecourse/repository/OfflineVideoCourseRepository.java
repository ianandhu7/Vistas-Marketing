package co.vistafoundation.vlearning.offlinecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.subscription.dto.StudentVideoStreamingDTO;

/**
 * @author NaveenKumar
 */
public interface OfflineVideoCourseRepository extends JpaRepository<OfflineVideoCourse, Long> {

	public List<OfflineVideoCourse> findTop10ByOrderByIdOfflineVideoCourseDesc();

	public List<OfflineVideoCourse> findByIdProduct(Long idProduct);

	public OfflineVideoCourse findByIdOfflineVideoCourse(Long idOfflineVideoCourse);

	public List<OfflineVideoCourse> findByIdProductAndIdSubjectChapterOrderByVideoSeqNumberAsc(Long idProduct,
			Long idSubjectChapter);

	public List<OfflineVideoCourse> findByIdSubjectAndIdSubjectChapter(Long idSubject, Long idSubjectChapter);

	public List<OfflineVideoCourse> findByIdSubjectAndIdSubjectChapterOrderByVideoSeqNumberAsc(Long idSubject,
			Long idSubjectChapter);
	
	public List<OfflineVideoCourse> findTop10ByIdSubjectOrderByIdOfflineVideoCourseDesc(Long idSubject);

	@Query(value ="select o.* from offline_video_course o inner join product p on o.idPRODUCT = p.idPRODUCT inner join product_line pl on p.idPRODUCT_LINE = pl.idPRODUCT_LINE"
			+ " where pl.product_line_cd = :product_line_cd"
			+ " order by o.created_at desc", nativeQuery = true)
	public List<OfflineVideoCourse> getProductLineCd(String product_line_cd);
	
	public List<OfflineVideoCourse> findByIdSubjectChapterAndIdProduct(Long idSubjectChapter, Long idProduct);

	public List<OfflineVideoCourse> findByIdSubjectAndIdSubjectChapterAndIdProductOrderByVideoSeqNumberAsc(
			Long idSubject, Long idSubjectChapter, Long idProduct);

	public List<OfflineVideoCourse> findByIdProductAndVideoEnLinkNotIn(Long idProduct, List <String> videoLinks);

	public List<OfflineVideoCourse> findByIdProductAndVideoEnLinkNotInAndIdSubjectChapter(Long idProduct,
			List<String> marketingIds, Long idSubjectChapter);

	public OfflineVideoCourse getByIdOfflineVideoCourse(Long idVideo);
	
	public List<OfflineVideoCourse>  findByIdSubjectChapterOrderByVideoSeqNumberAsc(Long idSubjectChapter);
	
	@Query(value = "select new "
			+ " co.vistafoundation.vlearning.subscription.dto.StudentVideoStreamingDTO(ovc.idOfflineVideoCourse, ovc.videoPoster1Location, ovc.videoEnLink, ovc.videoOtp, ovc.pdfURL,ovc.teacherNoteURL, ovc.question, ovc.answer, ovc.videoSeqNumber,"
			+ " sac.idStudentAssignedCourse, sac.completeFlag, sac.videoCoverageDuration, ovc.videoDuration, sac.pctComplete) "
			+ " FROM OfflineVideoCourse ovc LEFT JOIN StudentAssignedCourse sac"
			+ " ON ovc.idOfflineVideoCourse = sac.idOfflineVideoCourse AND sac.idStudentSubscription = :idStudentSubscription"
			+ " WHERE ovc.idSubjectChapter = :idSubjectChapter ")
	public List<StudentVideoStreamingDTO> getStudentVideoStreamingLog(@Param("idStudentSubscription") Long idStudentSubscription,
			@Param("idSubjectChapter") Long idSubjectChapter);

	public OfflineVideoCourse findByVideoEnLink(String  videoEnLink);
	
	public OfflineVideoCourse findFirstByVideoEnLink(String  videoEnLink);
	
}
