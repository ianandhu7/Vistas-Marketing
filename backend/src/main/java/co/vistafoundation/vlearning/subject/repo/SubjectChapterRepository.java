package co.vistafoundation.vlearning.subject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subject.model.SubjectChapter;
/**
 * @author NaveenKumar
 * 
 **/
public interface SubjectChapterRepository extends JpaRepository<SubjectChapter,Long> {

	List <SubjectChapter> findByIdSubject(Long idSubject);


	SubjectChapter findByIdSubjectChapter(Long idSubjectChapter);


	List<SubjectChapter> findByIdSubjectChapterInAndActiveFlagOrderBySortOrder(List<Long> finalChList,boolean status);


	List<SubjectChapter> findByIdSubjectAndIdClassStandard(Long idSubject, Long idClassStandard);


	List<SubjectChapter> findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusOrderBySortOrder(Long idSubject, Long idClassStandard,
			Long idState, Long idSyllabus);
	

	List<SubjectChapter> findTop2ByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusOrderBySortOrderAsc( Long idClassStandard,Long idSubject,
			Long idState, Long idSyllabus);

    	SubjectChapter findTopByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndSortOrderLessThanOrderBySortOrderDesc(
			Long idClassStandard, Long idSubject, Long idState, Long idSyllabus, int sortOrder);
	
	
	@Query(value = "select distinct sc.idSubject from SubjectChapter sc join StudentAssignedCourse sac on sac.idSubjectChapter=sc.idSubjectChapter")
	List<Long> getAllSubjectsFromSubChapterAndSAC ();

	
	List<SubjectChapter> findTop4ByIdSubjectAndIdClassStandardAndIdSyllabusAndIdStateOrderBySortOrderAsc(Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState);

	
	SubjectChapter findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusAndSortOrder(Long idSubject,
			Long idClassStandard, Long idState, Long idSyllabus,int sortOrder);
	
	
	SubjectChapter findByIdSubjectChapterAndIdClassStandardAndIdStateAndIdSyllabus(Long idSubjectChapter, Long idClassStandard,
			Long idState, Long idSyllabus);
	
	
	SubjectChapter findByIdSubjectChapterAndActiveFlag(Long idSubjectChapter,Boolean status);
	
	List<SubjectChapter> findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrder(Long idSubject, Long idClassStandard,
			Long idState, Long idSyllabus,Boolean status);

	List<SubjectChapter> findTop2ByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrderAsc(
			Long idClassStandard, Long idSubject, Long idState, Long idSyllabus, Boolean Status);

	SubjectChapter findByIdSubjectChapterAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(Long idSubjectChapter,
			Long idClassStandard, Long idState, Long idSyllabus, Boolean status);

	List<SubjectChapter> findTop4ByIdSubjectAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlagOrderBySortOrderAsc(
			Long idSubject, Long idClassStandard, Long idSyllabus, Long idState, Boolean status);

	SubjectChapter findTopByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndActiveFlagAndSortOrderLessThanOrderBySortOrderDesc(
			Long idClassStandard, Long idSubject, Long idState, Long idSyllabus, Boolean status, int sortOrder);
	
}
