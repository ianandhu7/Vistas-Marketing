package co.vistafoundation.vlearning.offlinecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideosReportDto;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;




public interface OfflineCourseRepository extends JpaRepository<ProductSampleVideo, Long>{
	
	@Query(value = "SELECT * FROM  vlearning_dev.product_sample_video where idproduct = (select idproduct from vlearning_dev.product where idclass_standard =1 and idproduct_line =1)", nativeQuery = true)		  		
	public List<ProductSampleVideo> findAllSampleVideos();
	
	
//	@Query(value="select count(o.idOfflineVideoCourse) from OfflineVideoCourse o join SubjectChapter s "
//			+ "on o.idSubjectChapter=s.idSubjectChapter where (:idClassStandard is null or s.idClassStandard = :idClassStandard) "
//			+ "and (:idState is null or s.idState =:idState ) and (:idSyllabus is null or s.idSyllabus =:idSyllabus)")
	@Query(value="select new co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideosReportDto( s1.subjectName, count(o.idOfflineVideoCourse)) from OfflineVideoCourse o "
			+ "join SubjectChapter as s on o.idSubjectChapter=s.idSubjectChapter join Subject as s1 on o.idSubject=s1.idSubject where (:idClassStandard is null or s.idClassStandard = :idClassStandard) "
			+ "and (:idState is null or s.idState =:idState ) and (:idSyllabus is null or s.idSyllabus =:idSyllabus) and s1.subjectName != 'NA' group by o.idSubject ")
	public List<OfflineVideosReportDto> getTotalVideoCount (Long idClassStandard, Long idSyllabus,Long idState);
	

}