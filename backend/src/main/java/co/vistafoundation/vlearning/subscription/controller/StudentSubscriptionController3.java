/**
 * 
 */
package co.vistafoundation.vlearning.subscription.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV3;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionServiceV3;

/**
 * @author NaveenKumar
 *
 */
@RestController
@RequestMapping("/api/v3/subscription")
public class StudentSubscriptionController3 {

	@Autowired
	StudentSubscriptionServiceV3 studentSubscriptionServiceImpl;

	
	/**
	 * @author NaveenKumar A
	 * @param idStudentSubscription
	 * @param idProductLine
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @return This method will return student subscription subject info for
	 *         selected param with quiz availability for each chapter.
	 */

	@GetMapping(value = "/subject-info")
	public ResponseEntity<Document<StudentSubscriptionSubjectDTOV3>> getStudentSubscriptionData(
			@RequestParam Long idStudentSubscription, @RequestParam Long idProductLine, @RequestParam Long idSubject,
			@RequestParam Long idClassStandard, @RequestParam Long idSyllabus, @RequestParam Long idState) {

		Document<StudentSubscriptionSubjectDTOV3> reponses = studentSubscriptionServiceImpl
				.studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(idProductLine, idSubject,
						idClassStandard, idSyllabus, idState, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author NaveenKumar A
	 * @param idStudentSubscription
	 * @param idProductLine
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @return This method will return list of chapter for respective subject for
	 *         selected param with .
	 */

	@GetMapping(value = "/subject-chapter-list")
	public ResponseEntity<Document<StudentSubscriptionSubjectDTO>> getStudentChapterList(
			@RequestParam Long idStudentSubscription, @RequestParam Long idProductLine, @RequestParam Long idSubject,
			@RequestParam Long idClassStandard, @RequestParam Long idSyllabus, @RequestParam Long idState) {

		Document<StudentSubscriptionSubjectDTO> reponses = studentSubscriptionServiceImpl.getStudentChapterList(
				idProductLine, idSubject, idClassStandard, idSyllabus, idState, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/get-chapter-quiz-availability")
	public ResponseEntity<Document<Map<String, Object>>> getStudentChapterQuizAvailblity(
			@RequestParam Long idSubjectChapter,@RequestParam Long idStudentSubscription) {

		Document<Map<String, Object>> reponses = studentSubscriptionServiceImpl
				.getQuizAvailblityStatus(idSubjectChapter,idStudentSubscription);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

//	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
//	@PostMapping("/user-newsubscription")
//	public ResponseEntity<Document<CartSummaryDTO>> saveNewUserSubscription(@RequestBody NewSubscriptionPlanDTO newSubscriptionPlanDTO) {
//		Document<CartSummaryDTO> document = studentSubscriptionServiceImpl.saveNewUserSubscription(newSubscriptionPlanDTO);
//		return ResponseEntity.status(document.getStatusCode()).body(document);
//	}
	
	
}
