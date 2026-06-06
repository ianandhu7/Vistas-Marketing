/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.util.Map;
import java.util.TreeMap;

import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV3;

/**
 * @author NaveenKumar
 *
 */
public interface StudentSubscriptionServiceV3 {

	public Document<StudentSubscriptionSubjectDTOV3> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long classStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription);

	public Document<StudentSubscriptionSubjectDTO> getStudentChapterList(Long idProductLine, Long idSubject,
			Long classStandard, Long idSyllabus, Long idState, Long idStudentSubscription);

	public Document<Map<String, Object>> getQuizAvailblityStatus(Long idSubjectChapter,Long idStudentSubscription);

//	public Document<CartSummaryDTO> saveNewUserSubscription(NewSubscriptionPlanDTO newSubscriptionPlanDTO);

	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception;
	
}
