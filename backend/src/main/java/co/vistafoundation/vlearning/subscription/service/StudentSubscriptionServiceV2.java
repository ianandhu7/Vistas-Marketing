/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;


import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubdcribedSubDto;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV2;

/**
 * @author NAVEEN
 *
 */
public interface StudentSubscriptionServiceV2 {
	
	public Document<StudentSubscriptionSubjectDTOV2> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long classStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription);
	
	public Document<CartSummaryDTO> saveNewUserSubscription(NewSubscriptionPlanDTO newSubscriptionPlanDTO);

	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception;
	
	public Document<Object> getSubscribedProductForUser();

	/**
	 * @param newSubscriptionPlanDTO
	 * @param user
	 * @return
	 */
	public CartSummaryDTO saveNewUserSubscriptionNewSignup(NewSubscriptionPlanDTO newSubscriptionPlanDTO, User user);


	public Document<List<StudentSubjectResDTO>> getSubscribedSubStatus(Long idClassStandard, Long idSyllabus, Long idState);

	public Document<List<StudentSubjectResDTO>> getSubscribedExtraCurSubStatus();


	/**
	 * @param batchFile
	 * @return
	 */
	Document<?> uploadBatchMannualSubscription(MultipartFile batchFile);
	
	public Document<Map<String, Object>> batchSubscriptionRenewal(MultipartFile batchFile);

}
