package co.vistafoundation.vlearning.subject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subject.dto.CreateSubjectChapterDTO;
import co.vistafoundation.vlearning.subject.dto.ExamPreparationSubjectDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectCrudDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectResponseDTO;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.service.SubjectService;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.vct.dto.VCTSubjectDTO;

/**
 * @author NaveenKumar
 * 
 **/

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

	@Autowired
	SubjectService subjectService;

	@GetMapping(value = "/list")
	public ResponseEntity<?> getbatchMetaData() {
		Document<?> document = subjectService.getAllSubject();
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/{idSubject}/chapters")
	public ResponseEntity<?> getChaptersBySubject(@PathVariable Long idSubject, @RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<List<SubjectChapter>> document = subjectService.getAllChapterBySubject(idSubject, idClassStandard,
				idSyllabus, idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/academic")
	public ResponseEntity<?> getAcademicSubjects(@RequestParam Long idClassStandard, @RequestParam Long idSyllabus) {
		Document<?> document = subjectService.getAcademicSubjectByClassStandard(idClassStandard, idSyllabus);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/{categoryCode}/getSubjects")
	public ResponseEntity<?> getSubjectBasedOnCategory(@PathVariable String categoryCode) {
		Document<?> document = subjectService.getSubjectbasedOnCategory(categoryCode);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/eca")
	public ResponseEntity<?> getExtraCurricularSubjects() {
		Document<List<SubjectResponseDTO>> document = subjectService.getExtraCurricularSubjects();
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/batch")
	public ResponseEntity<?> getBatchSubjects(@RequestParam Long idClassStandard, @RequestParam Long idSyllabus,
			@RequestParam Long idProductLine) {
		Document<List<SubjectResponseDTO>> document = subjectService.getBacthSubjectByClassStandard(idClassStandard,
				idSyllabus, idProductLine);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/extracur-sub")
	public ResponseEntity<?> getECASubjects(@RequestParam Long idProductLine,
			@RequestParam Long idLevelExtraCurricular) {
		Document<List<Subject>> document = subjectService.getECASubjects(idProductLine, idLevelExtraCurricular);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/chapter")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> createSubjectChapter(@Valid @RequestBody CreateSubjectChapterDTO createSubjectChapterDTO) {
		Document<SubjectChapter> document = subjectService.createSubjectChapter(createSubjectChapterDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/chapter")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> updateSubjectChapter(@RequestBody SubjectChapter subjectChapter) {
		Document<SubjectChapter> document = subjectService.updateSubjectChapter(subjectChapter);
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

	@GetMapping(value = "/academic/list")
	public ResponseEntity<?> getAcademicSubjectsByProduct(@RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<?> document = subjectService.getAcademicSubjectByProduct(idClassStandard, idSyllabus, idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping(value = "/browse-course")
	public ResponseEntity<?> browseCourseBeforeLogin() {
		Document<ArrayList<Object>> document = subjectService.browseCourseBeforeLogin();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/exam-preparation")
	public ResponseEntity<?> getExamPreparationsSUbjects() {
		Document<List<ExamPreparationSubjectDTO>> document = subjectService.getExamPreparationSubject();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/vct")
	public ResponseEntity<?> getVCTSubjects() {
		Document<List<VCTSubjectDTO>> document = subjectService.getVCTSubject();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	/*
	 * crud for subject
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/")
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache","teacherCache"}, allEntries = true)
	 public ResponseEntity<Document<Subject>> saveSubject(@Valid @RequestBody SubjectCrudDTO subjectDTO) {
		Document<Subject> result = subjectService.saveSubject(subjectDTO);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<Document<List<Subject>>> getAllSubjects() {
		Document<List<Subject>> result = subjectService.getAllSubjects();
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/{idSubject}")
	public ResponseEntity<Document<Subject>> getSubjectById(@PathVariable Long idSubject) {
		Document<Subject> result = subjectService.getSubjectById(idSubject);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/{idSubject}")
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache","teacherCache"}, allEntries = true)
	  public ResponseEntity<Document<Subject>> updateSubject(@PathVariable Long idSubject, @Valid @RequestBody SubjectCrudDTO subjectDTO) {
		Document<Subject> result = subjectService.updateSubject(idSubject, subjectDTO);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/")
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache","teacherCache"}, allEntries = true)
	public ResponseEntity<Document<String>> deleteSubject(@RequestParam Long idSubject){
		Document<String> result = subjectService.deleteSubject(idSubject);
		return ResponseEntity.status(result.getStatusCode()).body(result);

	}


	 @GetMapping("/chapter-filter")
	 public ResponseEntity<Document<NewStudentSubscriptionSubjectDTO>> chapterFilter(@RequestParam Long idProductLine,
				@RequestParam Long idSubject, @RequestParam Long idClassStandard, @RequestParam Long idSyllabus,
				@RequestParam Long idState) {
		Document<NewStudentSubscriptionSubjectDTO> result = subjectService.chapterFilter(idProductLine,
				idSubject, idClassStandard, idSyllabus, idState);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	 
	
	@GetMapping("/static-extracurr-subject")
	public ResponseEntity<Document<List<StudentSubjectResDTO>>> getSubscribedExtraCurSubStatus() {
		Document<List<StudentSubjectResDTO>> document = subjectService.getSubscribedExtraCurSubStatus();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PutMapping("/update-chapter-status")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> updateChapterStatus(Long idSubjectChapter,Boolean status) {
		Document<?> document = subjectService.updateChapterStatus(idSubjectChapter,status);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

}
