/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.leadbatch.freeclass.dto.TelecallerStudentList;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetails;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public interface LeadBatchDetailsRepository extends JpaRepository<LeadBatchDetails, Long> {

	LeadBatchDetails findByIdLeadBatchDetails(Long idLeadBatchDetails);

	@Query(value = "select new co.vistafoundation.vlearning.leadbatch.freeclass.dto.TelecallerStudentList(lb.idLeadBatchDetails,u.firstName,u.mobileNumber) from LeadBatchDetails lb inner join User u on lb.idVlUser = u.userSurId "
			+ "where lb.idSyllabus =:idSyllabus and lb.idClassStandard =:idClassStandard and lb.idSubject =:idSubject and lb.idSujectChapter =:idSujectChapter and lb.idLanguage= :idLanguage")
	List<TelecallerStudentList> findAllStudents(Long idClassStandard, Long idSyllabus, Long idSubject,
			Long idSujectChapter, Long idLanguage);

	// getting all lead batch based on parameters
	List<LeadBatchDetails> findAllByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguage(
			Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter, Long idLanguage);

	// getting all lead batch based on parameters including idAvailableSchedule
	List<LeadBatchDetails> findAllByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguageAndIdAvailableScheduleAndIdState(
			Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter, Long idLanguage,
			Long idAvailableSchedule,Long idState);

	LeadBatchDetails findByIdClassStandardAndIdSubjectAndIdVlUser(Long idClassStandard, Long idSubject, Long idVlUser);

	LeadBatchDetails findByIdVlUser(Long userSurId);
    
	
	@Query("select distinct  a.idClassStandard,a.idSyllabus, a.idSubject,  a.idSujectChapter, a.idLanguage,  a.idAvailableSchedule ,a.idState from LeadBatchDetails a")
	List<Object[]> findAllDistinctData();
//	

	List<LeadBatchDetails> findDistinctByIdClassStandardNotNullAndIdSubjectNotNullAndIdAvailableScheduleNotNullAndIdSujectChapterNotNullAndIdLanguageNotNullAndIdSyllabusNotNull();

	int countByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguageAndIdAvailableSchedule(
			Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter, Long idLanguage,
			Long idAvailableSchedule);

//	@Query("SELECT DISTINCT new co.vistafoundation.vlearning.leadbatch.freeclass.dto LeadBatchDetailsDTO(a.idClassStandard ,a.idSyllabus,a.idSubject,a.idSujectChapter,a.idLanguage,a.idAvailableSchedule,c.classStandadName,sy.syllabusName,s.subjectName,ch.chapterName,l.language,avb.dayOfWeek,avb.fromTime,avb.toTime) from LeadBatchDetails a inner join ClassStandard c on a.idClassStandard = c.idClassStandard inner join Syllabus sy on a.idSyllabus = sy.idSyllabus inner join Subject s on a.idSubject = s.idSubject inner join SubjectChapter ch on a.idSubjectChapter = ch.idSubjectChapter inner join Language l on a.idLanguage = l.idLanguage inner join AvailableSchedule avb on a.idAvailableSchedule = avb.idAvailableSchedule")

	//<LeadBatchDetailsDTO> findAllDistinctData();

}
