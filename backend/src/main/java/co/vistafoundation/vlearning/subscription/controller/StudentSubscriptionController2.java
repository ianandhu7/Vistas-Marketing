/**
 * 
 */
package co.vistafoundation.vlearning.subscription.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;




import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubdcribedSubDto;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV2;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionServiceImplV2;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionServiceV2;

/**
 * @author Naveen Kumar
 *
 */
@RestController
@RequestMapping("/api/v2/subscription")

public class StudentSubscriptionController2 {

	@Autowired
	StudentSubscriptionServiceV2 studentSubscriptionServiceV2;
	
	@Autowired
	StudentSubscriptionServiceImplV2 studentSubscriptionServiceImpl;
	
	 @Autowired
	    private CacheManager cacheManager;


	/**
	 * @author Naveen Kumar A
	 * @param idStudentSubscription
	 * @param idProductLine
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @return This method will return student subscription info for selected param.
	 */
	
	@GetMapping(value = "/subject-info")
	public ResponseEntity<?> getStudentSubscriptionData(
			@RequestParam Long idStudentSubscription,
			@RequestParam Long idProductLine, @RequestParam Long idSubject, @RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		
		Document<StudentSubscriptionSubjectDTOV2> reponses = studentSubscriptionServiceImpl
				.studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(idProductLine, idSubject,
						idClassStandard, idSyllabus, idState, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Controller method to handle the creation of a new user subscription.
	 * @author Mohan Kumar 
	 * @param newSubscriptionPlanDTO The request body containing details of the new subscription plan.
	 * @return This method will return student subscription info and paytm payment details.
	 *        
	 */
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/user-newsubscription")
	public ResponseEntity<Document<CartSummaryDTO>> saveNewUserSubscription(@RequestBody NewSubscriptionPlanDTO newSubscriptionPlanDTO) {
		Document<CartSummaryDTO> document = studentSubscriptionServiceV2.saveNewUserSubscription(newSubscriptionPlanDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-subscribed-product")
	public ResponseEntity<Document<Object>> getSubscribedProductForUser() {
		Document<Object> response = studentSubscriptionServiceV2.getSubscribedProductForUser();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-sub-status")
	public ResponseEntity<Document<List<StudentSubjectResDTO>>> getSubscribedSubStatus(@RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<List<StudentSubjectResDTO>> document = studentSubscriptionServiceV2.getSubscribedSubStatus(idClassStandard,idSyllabus,idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-extracurr-sub-status")
	public ResponseEntity<Document<List<StudentSubjectResDTO>>> getSubscribedExtraCurSubStatus() {
		Document<List<StudentSubjectResDTO>> document = studentSubscriptionServiceV2
				.getSubscribedExtraCurSubStatus();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	
	/**
	 * Controller method to upload batch manual subscription document.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 *  @author Abdul Elahi
	 * 
	 * @param batchFile The batch file to be uploaded for manual subscription.
	 * @return A ResponseEntity containing a Document object with the upload response.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-subscription")
	public ResponseEntity<?> uploadBatchManualSubscriptionDocument(@RequestParam("batchFile") MultipartFile batchFile) {
		Document<?> response = studentSubscriptionServiceV2.uploadBatchMannualSubscription(batchFile);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-subscription-renewal")
	public ResponseEntity<Document<Map<String, Object>>> batchSubscriptionRenewal(@RequestParam MultipartFile batchFile) {
		Document<Map<String, Object>> response = studentSubscriptionServiceV2.batchSubscriptionRenewal(batchFile);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
}
