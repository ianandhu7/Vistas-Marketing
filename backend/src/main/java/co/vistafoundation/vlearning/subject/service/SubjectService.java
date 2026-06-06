package co.vistafoundation.vlearning.subject.service;

import java.util.ArrayList;

/**
 * @author NaveenKumar
 * 
 **/

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subject.dto.CreateSubjectChapterDTO;
import co.vistafoundation.vlearning.subject.dto.ExamPreparationSubjectDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectCrudDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectResponseDTO;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.vct.dto.VCTSubjectDTO;

public interface SubjectService {

	@SuppressWarnings("rawtypes")
	public Document getAllSubject();

	/**
	 * @return List of chapters for the subject id provided
	 * @return null when no data found in db
	 */
	public Document<List<SubjectChapter>> getAllChapterBySubject(Long idSubject, Long idClassStandard, Long idSyllabus,
			Long idState);

	public Document<List<SubjectResponseDTO>> getAcademicSubjectByClassStandard(Long idClassStandard, Long idSyllabus);

	Document<?> getSubjectbasedOnCategory(String categoryCode);

	public Document<List<SubjectResponseDTO>> getExtraCurricularSubjects();

	public Document<List<SubjectResponseDTO>> getBacthSubjectByClassStandard(Long idClassStandard, Long idSyllabus,
			Long idProductLine);

	public Document<SubjectChapter> createSubjectChapter(CreateSubjectChapterDTO request);

	public Document<SubjectChapter> updateSubjectChapter(SubjectChapter request);

	public Document<List<SubjectResponseDTO>> getAcademicSubjectByProduct(Long idClassStandard, Long idSyllabus,
			Long idState);

	public Document<ArrayList<Object>> browseCourseBeforeLogin();

	public Document<List<Subject>> getECASubjects(Long idProductLine, Long idLevelExtraCurricular);

	public Document<List<ExamPreparationSubjectDTO>> getExamPreparationSubject();

	public Document<List<VCTSubjectDTO>> getVCTSubject();
	
	

	public Document<List<Subject>> getAllSubjects();

	public Document<Subject> getSubjectById(Long id);

	public Document<Subject> saveSubject(SubjectCrudDTO subjectCrudDto);

	public Document<Subject> updateSubject(Long idSubject, SubjectCrudDTO subjectDTO);

	public Document<String> deleteSubject(Long idSubject);

	public Document<NewStudentSubscriptionSubjectDTO> chapterFilter(Long idProductLine, Long idSubject, Long idClassStandard, Long idSyllabus,
			Long idState);

	public Document<List<StudentSubjectResDTO>> getSubscribedExtraCurSubStatus();

	public Document<?> updateChapterStatus(Long idSubjectChapter,Boolean status);

	

}
