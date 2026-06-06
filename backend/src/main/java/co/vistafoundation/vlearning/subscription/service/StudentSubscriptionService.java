/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.InfoFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.InvoiceCsvLogsDTO;
import co.vistafoundation.vlearning.subscription.dto.InvoiceCsvLogsFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.OrderDTO;
import co.vistafoundation.vlearning.subscription.dto.OrderFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.PaytmParamsRequestDTO;
import co.vistafoundation.vlearning.subscription.dto.ProductDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentAcademicGraphDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentStreamingInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubdcribedSubDto;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectProgressDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.user.dto.UserCartRequestDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;

/**
 * @author vk
 *
 */
public interface StudentSubscriptionService {

	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception;

	@SuppressWarnings("rawtypes")
	public Document saveNewStudentSubscription(UserCartRequestDTO userCartRequestDTO);

	@SuppressWarnings("rawtypes")
	public Document saveRenewalStudentSubscription(Long idStudentSubscription, Long userSurId);

	@SuppressWarnings("rawtypes")
	public Document makePayment(CartSummaryDTO cartSummaryDTO);

	@SuppressWarnings("rawtypes")
	public Document makeRenewalPayment(CartSummaryDTO cartSummaryDTO);

	public Model paymentResponse(HttpServletRequest request, Model model, String callBackURLPayment) throws Exception;

	@SuppressWarnings("rawtypes")
	public Document getStudentSubscriptionDetails(Long idStudent, String[] subscriptionType);

	@SuppressWarnings("rawtypes")
	public Document getStudentSubMetaInfo(Long idStudent);

	/**
	 * This method will provide the basic streaming informations, such as Subject
	 * subscribed , subject chapters and course completion.
	 * 
	 * @param idStudent
	 * @param idProductLine
	 * 
	 * @return Document ( with list of array based on idProductLine")
	 * @return null in Document data when student subscription got expired
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Document getStudentSubOfflineData(Long idStudent, Long idProductLine);

	/**
	 * Method will provide streaming data from studentAssignedCourse based subject
	 * chapter.
	 * 
	 * @param idStudent
	 * @param idProductLine
	 * @param idSubject
	 * @param idSubjectChapter
	 * @return Document(Student Assigned Courses)
	 */
	@SuppressWarnings("rawtypes")
	public Document getStudentSubStreamingByChapter(Long idStudent, Long idProductLine, Long idSubject,
			Long idSubjectChapter, Long idSubscription);

	/**
	 * This method will check Expire of subscription and update batch table.
	 */
	public void checkSubscriptionExpiry();

	/**
	 * This method will check Expire of subscription and update subscription table.
	 */
	public void checkUserSubscriptionExpiry();

	@SuppressWarnings("rawtypes")
	public Document getOrderDetailsByUserSurId(Long userSurId);

	public Document<StudentSubscription> checkStudentSubscriptionByIdSubject(Long idSubject);

	public Document<StudentSubscription> checkStudentSubscriptionByIdSubjectForExtraCurr(Long idSubject);

	public Document<StudentSubscriptionSubjectDTO> studentSubjectBySubscription(Long idStudentSubscription);

	public Document<StudentStreamingInfoDTO> studentFreeSubscriptionStreamingData(Long idSubject, Long idSubjectChapter,
			Long idState, Long idClassStandard, Long idSyllabus, String language, Long idStudentSubscription);

	public Document<StudentAssignedCourse> getStudentAssignedCourse(Long idOfflineVideoCourse,
			Long idStudentSubscription);

	/**
	 * This method will provide subject data.
	 * 
	 * @param idProductLine
	 * @param idSubject
	 * @param classStandard
	 * @param idSyllabus
	 * @param idState
	 * @param idStudentSubscription
	 * @return Document<StudentSubscriptionSubjectDTO>
	 */
	public Document<StudentSubscriptionSubjectDTO> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long classStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription);

	public Document<StudentAssignedCourse> getStudentAssignedCourseByOfflineCourse(Long idOfflineVideoCourse);

	@SuppressWarnings("rawtypes")
	public Document checkExistingSubOrCreate(Long idUser, Long idProduct);
	
	public Document<Page<OrderDTO>> getOrderDetails(OrderFilterDTO orderFilterDTO,String sort, int pageNumber,int pageSize);

	public void paymentReminder();

	public Document<ProductDTO> getProductDetails(Long idStudentSubscription);

	public Document<Map<String, String>> getSubjectCompletionPercentage(Long idStudentSubscription, Long idSubject);

	@SuppressWarnings("rawtypes")
	public Document saveNewUserSubscription(UserCartResponseDTO userCart);
	
	@SuppressWarnings("rawtypes")
	public Document saveRenewalUserSubscription(Long idStudentSubscription, String interval, Long userSurId);

	@SuppressWarnings("rawtypes")
	public Document checkFreeAndExistingSubscription(Long userSurId);

	public StudentPostLoginDTO checkExistingSubscriptionLogin(Long userSurId);

	public Document<HashMap<String, Object>> getTokenInfo(PaytmParamsRequestDTO request);

	public Document<List<Object>> adminStreamingData(Long idSubject, Long idSubjectChapter, Long idState,
			Long idClassStandard, Long idSyllabus, String language);

	public SubscribedUserDTO checkUserSubscribed(Long userSurId);

	public Document<StudentAcademicGraphDTO> getAcademicProgressSubjectGraphinfo(Long idStudentSubscription,
			Long idSubject);

	@SuppressWarnings("rawtypes")
	public Document saveRenewalSpecialOfferBatchSubscription(Long idStudentOrder, Long idSpecialOffer, Long userSurId);

	@SuppressWarnings("rawtypes")
	public Document makeRenewalSpecialOfferBatchPayment(CartSummaryDTO cartSummaryDTO);

	public Document<List<StudentSubscriptionInfoDTO>> getStudentSubscriptionsByOrder(String orderId);

	public Document<StudentSubscription> createStudentSubscriptionManually(Long idStudentOrder);

	public Document<Map<String, Map<String, Object>>> getOrderStatusForOrderId(String orderId);

	public Document<List<StudentSubdcribedSubDto>> getSubscribedSubStatus(Long studentId);

	public Document<List<StudentSubdcribedSubDto>> getSubscribedExtraCurSubStatus(Long studentId);

	public Document<?> sendEmailInvoce(String orderId);

	public Document<?> verifyCSVSubscriptionData(OrderFilterDTO orderFilterDTO);

	public Document<Page<InvoiceCsvLogsDTO>> getInvoiceCsvLogs(InvoiceCsvLogsFilterDTO invoiceCsvLogsFilterDTO,String sort, int pageNumber,int pageSize);

	/**
	 * @author Abdul Elahi
	 * 
	 *         to generate the csv file based on the filters
	 * 
	 */
	Document<byte[]> generateCSVSubscriptionData(OrderFilterDTO orderFilterDTO, String sort, String fileName,String recordType);
	
	Document<?>  downloadCSVByIdInvoiceCsvLogs(Long idInvoiceCsvLogs,String recordType,String sort);

	public Document<?> uploadBatchMannualSubscription(MultipartFile batchFile);

	public Document<?> validateUploadBatchMannualSubscription(MultipartFile batchFile);

	public void paymentPendingStatusUpdate();
	
	public Document<Object> getSubscribedProductForUser();

	Document<Page<SubscriptionInfoDTO>> getStudentSubcriptionInfo(InfoFilterDTO infoFilterDTO, String sort,
			int pageNumber, int pageSize);

	Document<?> getAllBasicUsers(InfoFilterDTO infoFilterDTO, String sort, int pageNumber, int pageSize);

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
//	Document<Page<SubscriptionInfoDTO>> getMannualStudentSubscriptionInfo(InfoFilterDTO infoFilterDTO, String sort,
//			int pageNumber, int pageSize);
	public Document<StudentSubscription> upgradePlan(Long userSurId, String planType);

	public Document<StudentOrder> paymentRefresh(String orderId);

	public Document<CartSummaryDTO> iosOrderCreation(NewSubscriptionPlanDTO newSubscriptionPlanDTO);

	public Document<String> iosSubscriptionCreation(String orderId, String transactionId);
	
	public Document<NewStudentSubscriptionSubjectDTO> getStudentSubjectProgress(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState, Long idStudentSubscription);
	
}
