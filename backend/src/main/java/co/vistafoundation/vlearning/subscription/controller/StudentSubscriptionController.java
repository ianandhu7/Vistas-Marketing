/**
 * 
 */
package co.vistafoundation.vlearning.subscription.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.InvoiceCsvLogsFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
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
import co.vistafoundation.vlearning.subscription.dto.InfoFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.dto.UserCartRequestDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/subscription")
public class StudentSubscriptionController {

	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Value("${payment.url.website}")
	private String paymentUrl;

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/newsubscription")
	public ResponseEntity<?> saveNewStudentSubscription(@RequestBody UserCartRequestDTO userCartRequestDTO) {
		Document<?> document = studentSubscriptionService.saveNewStudentSubscription(userCartRequestDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/renewsubscription")
	public ResponseEntity<?> saveRenewalStudentSubscription(@RequestParam Long idStudentSubscription,
			@RequestParam Long userSurId) {
		Document<?> document = studentSubscriptionService.saveRenewalStudentSubscription(idStudentSubscription,
				userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/makepayment")
	public ResponseEntity<?> saveMakePayment(@RequestBody CartSummaryDTO cartSummaryDTO) {
		Document<?> document = studentSubscriptionService.makePayment(cartSummaryDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/make-renewal-payment")
	public ResponseEntity<?> saveRenewalMakePayment(@RequestBody CartSummaryDTO cartSummaryDTO) {
		Document<?> document = studentSubscriptionService.makeRenewalPayment(cartSummaryDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	// this API is not directly in use, since it's not calling directly from web UI
	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @RequestMapping(value = "/intiatePayment", method = RequestMethod.POST)
	 * public ResponseEntity<Document> intiatePayment(@RequestBody PaytmDetailsDTO
	 * paytmDetailsDTO) throws Exception { Document document = new Document();
	 * TreeMap<String, String> result =
	 * studentSubscriptionService.intiatePayment(paytmDetailsDTO); if (result !=
	 * null) { document.setData(result);
	 * document.setStatusCode(HttpStatus.OK.value());
	 * document.setMessage(HttpStatus.OK.name()); return
	 * ResponseEntity.status(HttpStatus.OK).body(document); } else {
	 * document.setData(null); document.setStatusCode(HttpStatus.OK.value());
	 * document.setMessage(HttpStatus.OK.name()); return
	 * ResponseEntity.status(HttpStatus.OK).body(document); } }
	 */

	@PostMapping(value = "/paymentresponse")
	public ModelAndView getResponseRedirect(HttpServletRequest request, Model model, Authentication authentication)
			throws Exception {
		final String callBackURLPayment = paymentUrl;
		studentSubscriptionService.paymentResponse(request, model, callBackURLPayment);
		return new ModelAndView("report");
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/getStudentSubcsriptionBatch")
	public ResponseEntity<?> getStudentSubcsription(@RequestParam(required = true) Long idStudent,
			@RequestParam(required = true) String[] subscriptionType) {

		Document<?> reponses = studentSubscriptionService.getStudentSubscriptionDetails(idStudent, subscriptionType);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudent
	 * @return This method will return student subscripiton meta info by providing
	 *         idStudent.
	 */
	@GetMapping(value = "/info")
	public ResponseEntity<?> getStudentSubCourseInfo(@RequestParam Long idStudent) {

		Document<?> reponses = studentSubscriptionService.getStudentSubMetaInfo(idStudent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudent
	 * @param idProductLine
	 * @return This method will return student subscripiton info by providing
	 *         idStudent.
	 */
	@GetMapping(value = "/get/{idProductLine}/course")
	public ResponseEntity<?> getStudentSubCourseInfo(@RequestParam Long idStudent, @PathVariable Long idProductLine) {

		Document<?> reponses = studentSubscriptionService.getStudentSubOfflineData(idStudent, idProductLine);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * @param idStudent
	 * @param idSubscription
	 * @param idProductLine
	 * @param idSubject
	 * @param idSubjectChapter
	 * @return This method will return student subject streaming data.
	 */

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/subject/{idSubject}/chapter/{idSubjectChapter}/streaming")
	public ResponseEntity<?> getStudentSubStreamingData(@RequestParam Long idStudent, @RequestParam Long idSubscription,
			@RequestParam Long idProductLine, @PathVariable Long idSubject, @PathVariable Long idSubjectChapter) {

		Document<?> reponses = studentSubscriptionService.getStudentSubStreamingByChapter(idStudent, idProductLine,
				idSubject, idSubjectChapter, idSubscription);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/getOrderDetails")
	public ResponseEntity<?> getOrderDetailsByUserSurId(@RequestParam(name = "userSurId") Long userSurId) {
		Document<?> responses = studentSubscriptionService.getOrderDetailsByUserSurId(userSurId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Sarfaraz Ahmed
	 * @return This method will return all order details based on search filter .
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/getAllOrderDetails")
	public ResponseEntity<?> getOrderDetails(@RequestBody OrderFilterDTO orderFilterDTO,
			@RequestParam(defaultValue = "latest", required = false) String sort,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "50") int pageSize) {
		Document<?> responses = studentSubscriptionService.getOrderDetails(orderFilterDTO, sort, pageNumber, pageSize);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSubject
	 * @return This method will return student subscription .
	 */
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/")
	public ResponseEntity<?> getSubjectSubscription(@RequestParam Long idSubject) {
		Document<StudentSubscription> reponses = studentSubscriptionService
				.checkStudentSubscriptionByIdSubject(idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Shruthi
	 * @param idSubject
	 * @return This method will return student subscription .
	 */
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/extra-curr")
	public ResponseEntity<?> getExtraCurricularSubjectSubscription(@RequestParam Long idSubject) {
		Document<StudentSubscription> reponses = studentSubscriptionService
				.checkStudentSubscriptionByIdSubjectForExtraCurr(idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudentSubscription
	 * @return This method will return Student Subscription for provided params.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "/subject-data")
	public ResponseEntity<?> getStudentSubscriptionData(@RequestParam Long idStudentSubscription) {
		Document<StudentSubscriptionSubjectDTO> reponses = studentSubscriptionService
				.studentSubjectBySubscription(idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/*
	 * deprecated by @author Naveen Kumar
	 * 
	 * @GetMapping(value = "/free-streaming-data") public ResponseEntity<?>
	 * getStudentFreeStreamingData(@RequestParam Long idSubject,
	 * 
	 * @RequestParam Long idSubjectChapter,
	 * 
	 * @RequestParam(value = "language", required = false, defaultValue = "English")
	 * String language) { Document<List<Object>> reponses =
	 * studentSubscriptionService .studentFreeSubscriptionStreamingData(idSubject,
	 * idSubjectChapter, language); return
	 * ResponseEntity.status(reponses.getStatusCode()).body(reponses); }
	 * 
	 */
	/**
	 * @author Naveen Kumar A
	 * @param idOfflineVideoCourse
	 * @param idStudentSubscription
	 * @return This method will return Student Subscription for provided params.
	 */
	@GetMapping(value = "/free-streaming-data/student-assigned")
	public ResponseEntity<?> getStudentAssignedCourse(@RequestParam Long idOfflineVideoCourse,
			@RequestParam Long idStudentSubscription) {
		Document<StudentAssignedCourse> reponses = studentSubscriptionService
				.getStudentAssignedCourse(idOfflineVideoCourse, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idOfflineVideoCourse
	 * @return This method will return Student Subscription for provided params.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "/student-assigned-course")
	public ResponseEntity<?> getStudentAssignedCourseByIdOfflineCourse(@RequestParam Long idOfflineVideoCourse) {
		Document<StudentAssignedCourse> reponses = studentSubscriptionService
				.getStudentAssignedCourseByOfflineCourse(idOfflineVideoCourse);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/checkExistingSubOrCreate/{idUser}/{idProduct}")
	public ResponseEntity<?> checkExistingSubOrCreate(@PathVariable Long idUser, @PathVariable Long idProduct) {
		Document<?> responses = studentSubscriptionService.checkExistingSubOrCreate(idUser, idProduct);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudentSubscription
	 * @param idProductLine
	 * @param idSubject
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @return This method will return students subscrption dto for the provided
	 *         params.
	 */
	@GetMapping(value = "/subject-info")
	public ResponseEntity<?> getStudentSubscriptionData(
			@RequestParam(value = "idStudentSubscription", required = false) Long idStudentSubscription,
			@RequestParam Long idProductLine, @RequestParam Long idSubject, @RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<StudentSubscriptionSubjectDTO> reponses = studentSubscriptionService
				.studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(idProductLine, idSubject,
						idClassStandard, idSyllabus, idState, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSubject
	 * @param idSubjectChapter
	 * @param idState
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param language
	 * @return will return streaming info for the provided params.
	 */
	@GetMapping(value = "/streaming-info")
	public ResponseEntity<?> getStudentFreeStreamingData(@RequestParam Long idSubject,
			@RequestParam Long idSubjectChapter, @RequestParam Long idState, @RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus,
			@RequestParam(value = "language", required = false, defaultValue = "English") String language,
			@RequestParam(value = "idStudentSubscription", required = false, defaultValue = "-1") Long idStudentSubscription) {

		Document<StudentStreamingInfoDTO> reponses = studentSubscriptionService.studentFreeSubscriptionStreamingData(
				idSubject, idSubjectChapter, idState, idClassStandard, idSyllabus, language, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "/streaming-info-admin")
	public ResponseEntity<?> getAdminTeacherStreamingData(@RequestParam Long idSubject,
			@RequestParam Long idSubjectChapter, @RequestParam Long idState, @RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus,
			@RequestParam(value = "language", required = false, defaultValue = "English") String language) {

		Document<List<Object>> reponses = studentSubscriptionService.adminStreamingData(idSubject, idSubjectChapter,
				idState, idClassStandard, idSyllabus, language);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudentSubscription
	 * @return will return product details for the provided params.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/product-details")
	public ResponseEntity<?> getProductDetails(@RequestParam Long idStudentSubscription) {
		Document<ProductDTO> responses = studentSubscriptionService.getProductDetails(idStudentSubscription);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idStudentSubscription and idSubject
	 * @return will return subject progress for the provided params.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "/progress-status")
	public ResponseEntity<?> getSubjectProgressData(@RequestParam Long idStudentSubscription,
			@RequestParam Long idSubject) {
		Document<StudentAcademicGraphDTO> responses = studentSubscriptionService
				.getAcademicProgressSubjectGraphinfo(idStudentSubscription, idSubject);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/user-newsubscription")
	public ResponseEntity<?> saveNewUserSubscription(@RequestBody UserCartResponseDTO userCart) {
		Document<?> document = studentSubscriptionService.saveNewUserSubscription(userCart);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/user-renewsubscription")
	public ResponseEntity<?> saveRenewalUserSubscription(@RequestParam Long idStudentSubscription,
			@RequestParam String interval, @RequestParam Long userSurId) {
		Document<?> document = studentSubscriptionService.saveRenewalUserSubscription(idStudentSubscription, interval,
				userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping("/check-user-subscription")
	public ResponseEntity<?> checkFreeAndExistingSubscription(@RequestParam Long userSurId) {
		Document<?> document = studentSubscriptionService.checkFreeAndExistingSubscription(userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/user-subscription-postlogin")
	public ResponseEntity<Document> checkExistingSubscriptionLogin(@RequestParam Long userSurId) {
		Document document = new Document();
		StudentPostLoginDTO result = studentSubscriptionService.checkExistingSubscriptionLogin(userSurId);
		document.setData(result);
		document.setMessage("Fetching user subscription details");
		document.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

//	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/payment-intial-token")
	public ResponseEntity<?> getPaymentIntialTransactionToken(
			@RequestBody PaytmParamsRequestDTO paytmParamsRequestDTO) {
		Document<HashMap<String, Object>> document = studentSubscriptionService.getTokenInfo(paytmParamsRequestDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/check-subscription-details")
	public ResponseEntity<Document> getUserSubscription(@RequestParam Long userSurId) {
		Document document = new Document();
		SubscribedUserDTO checkUserSubscription = studentSubscriptionService.checkUserSubscribed(userSurId);
		if (checkUserSubscription != null) {
			document.setData(checkUserSubscription);
			document.setMessage("Fetching user subscription details successfuly");
			document.setStatusCode(HttpStatus.OK.value());
		} else {
			document.setData(null);
			document.setMessage("User subscription not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/renew-specialoffer-batch")
	public ResponseEntity<?> saveRenewalSpecialOfferBatchSubscription(@RequestParam Long idStudentOrder,
			@RequestParam Long idSpecialOffer, @RequestParam Long userSurId) {
		Document<?> document = studentSubscriptionService.saveRenewalSpecialOfferBatchSubscription(idStudentOrder,
				idSpecialOffer, userSurId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/make-renewal-payment-specialoffer")
	public ResponseEntity<?> makeRenewalSpecialOfferBatchPayment(@RequestBody CartSummaryDTO cartSummaryDTO) {
		Document<?> document = studentSubscriptionService.makeRenewalSpecialOfferBatchPayment(cartSummaryDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/get-all-subscription")
	public ResponseEntity<?> getAllSubscriptionForOrder(@RequestParam String orderId) {
		Document<List<StudentSubscriptionInfoDTO>> document = studentSubscriptionService
				.getStudentSubscriptionsByOrder(orderId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/create-manually")
	public ResponseEntity<?> createManualSubscription(@RequestParam Long idStudentOrder) {
		Document<StudentSubscription> document = studentSubscriptionService
				.createStudentSubscriptionManually(idStudentOrder);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/view-order-status")
	public ResponseEntity<?> getOrderStatusbyOrderId(@RequestParam String orderId) {
		Document<Map<String, Map<String, Object>>> document = studentSubscriptionService.getOrderStatusForOrderId(orderId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-sub-status")
	public ResponseEntity<Document<List<StudentSubdcribedSubDto>>> getSubscribedSubStatus(@RequestParam(required = false,defaultValue = "-1") Long studentId) {
		Document<List<StudentSubdcribedSubDto>> document = studentSubscriptionService.getSubscribedSubStatus(studentId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-extracurr-sub-status")
	public ResponseEntity<Document<List<StudentSubdcribedSubDto>>> getSubscribedExtraCurSubStatus(@RequestParam(required = false,defaultValue = "-1") Long studentId) {
		Document<List<StudentSubdcribedSubDto>> document = studentSubscriptionService
				.getSubscribedExtraCurSubStatus(studentId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * Controller method to send an email with an invoice.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 * @param orderId The ID of the order for which the invoice email is to be sent.
	 * @return A ResponseEntity containing a Document object with the email sending response.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/send-email-invoice")
	public ResponseEntity<?> sendEmailThroughEmail(@RequestParam String orderId) {
		Document<?> document = studentSubscriptionService.sendEmailInvoce(orderId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * Generates a CSV file containing subscription data based on the provided
	 * filter criteria and returns it as a downloadable file with the specified file
	 * name.
	 *
	 * @author Abdul Elahi
	 *
	 * @param orderFilterDto An instance of OrderFilterDTO that represents the
	 *                       filter criteria for the orders that are to be included
	 *                       in the CSV file
	 * 
	 * @param sort           An optional String parameter that specifies the sorting
	 *                       order of the orders in the CSV file. Defaults to
	 *                       "latest".
	 * 
	 * @param csvFileName    An optional String parameter that specifies the file
	 *                       name of the CSV file that will be generated. Defaults
	 *                       to "INV".
	 * 
	 * @return A ResponseEntity object that encapsulates the CSV data, along with
	 *         the necessary HTTP headers and content type information, and a status
	 *         code that corresponds to the status code of the document returned by
	 *         generateCSVSubscriptionData().
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/invoice-download-csv")
	public ResponseEntity<?> downloadCSV(@RequestBody OrderFilterDTO orderFilterDto,
			@RequestParam(defaultValue = "latest", required = false) String sort,
			@RequestParam(defaultValue = "INV", required = false) String csvFileName,
			@RequestParam(defaultValue = "new", required = false) String recordType
			) {
		Document<?> document = studentSubscriptionService.generateCSVSubscriptionData(orderFilterDto, sort,csvFileName,recordType);
		if(document.getData()!=null)
		{
			byte[] data = (byte[]) document.getData();
			ByteArrayResource resource = new ByteArrayResource(data);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= " + csvFileName + ".csv");

			return ResponseEntity.status(document.getStatusCode()).headers(headers).contentLength(data.length)
					.contentType(MediaType.parseMediaType("text/csv")).body(resource);	
			
		}else {
			return ResponseEntity.status(document.getStatusCode()).body(document);
		}	
		
	}

	/**
	 * Validates the meta information of a CSV file containing subscription data
	 * based on the provided filter criteria and returns the document containing the
	 * validation results.
	 * 
	 * @author Abdul Elahi
	 *
	 * @param orderFilterDto An instance of OrderFilterDTO that represents the
	 *                       filter criteria for the orders that are to be included
	 *                       in the CSV file.
	 * @return A ResponseEntity object that encapsulates the document containing the
	 *         validation results, along with the corresponding HTTP status code.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/meta-info-invoice-download")
	public ResponseEntity<?> validateCSV(@RequestBody OrderFilterDTO orderFilterDto) {
		Document<?> document = studentSubscriptionService.verifyCSVSubscriptionData(orderFilterDto);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	/**
	 * Controller method to get invoice CSV logs.
	 * 
	 * @author Abdul Elahi
	 * 
	 * @param invoiceCsvLogsFilterDTO The data transfer object containing filters for invoice CSV logs.
	 * @param sort The sorting criteria for the invoice CSV logs (default is 'latest').
	 * @param pageNumber The page number for paginated results (default is 0).
	 * @param pageSize The number of items per page for paginated results (default is 50).
	 * @return A ResponseEntity containing a Document object with the invoice CSV log information.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/invoice-csv-logs")
	public ResponseEntity<?> getInvoiceCsvLogs(@RequestBody InvoiceCsvLogsFilterDTO invoiceCsvLogsFilterDTO,
			@RequestParam(defaultValue = "latest", required = false) String sort,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "50") int pageSize) {
		Document<?> responses = studentSubscriptionService.getInvoiceCsvLogs(invoiceCsvLogsFilterDTO, sort, pageNumber,
				pageSize);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	/**
	 * Controller method to download CSV by Invoice CSV log ID.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 *  @author Abdul Elahi
	 * 
	 * @param idInvoiceCsvLogs The ID of the Invoice CSV log to download.
	 * @param recordType The type of record to download (default is 'old').
	 * @param sort The sorting criteria for the downloaded records (default is 'latest').
	 * @return A ResponseEntity containing the CSV file to download or a Document with an error message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/{idInvoiceCsvLogs}/invoice-download-csv")
	public ResponseEntity<?> downloadCSVByIdInvoiceCsvLogs(@PathVariable("idInvoiceCsvLogs") Long idInvoiceCsvLogs,
			@RequestParam(defaultValue = "old", required = false) String recordType,
			@RequestParam(defaultValue = "latest", required = false) String sort) {

		Document<?> document = new Document<>();

		document = studentSubscriptionService.downloadCSVByIdInvoiceCsvLogs(idInvoiceCsvLogs, recordType, sort);
		if(document.getData()!=null)
		{
			byte[] data = (byte[]) document.getData();
			ByteArrayResource resource = new ByteArrayResource(data);

			HttpHeaders headers = new HttpHeaders();

			return ResponseEntity.status(document.getStatusCode()).headers(headers).contentLength(data.length)
					.contentType(MediaType.parseMediaType("text/csv")).body(resource);
		}else
		{
			return ResponseEntity.status(document.getStatusCode()).body(document);
		}
		
	}
	
	/**
	 * Controller method to validate batch upload for manual subscription.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 *  @author Abdul Elahi
	 * 
	 * @param batchFile The batch file to be uploaded for manual subscription validation.
	 * @return A ResponseEntity containing a Document object with the validation response.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-upload-subscription-validation")
	public ResponseEntity<?> validatingUploadBatchManualSubscription(
			@RequestParam("batchFile") MultipartFile batchFile) {
		Document<?> response = studentSubscriptionService.validateUploadBatchMannualSubscription(batchFile);
		return ResponseEntity.status(response.getStatusCode()).body(response);
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
	@PostMapping("/batch-upload-subscription")
	public ResponseEntity<?> uploadBatchManualSubscriptionDocument(@RequestParam("batchFile") MultipartFile batchFile) {
		Document<?> response = studentSubscriptionService.uploadBatchMannualSubscription(batchFile);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	/**
	 * Controller method to get student subscription information.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 *  @author Abdul Elahi
	 * 
	 * @param infoFilterDTO The data transfer object containing filters for subscription information.
	 * @param sort The sorting criteria for the subscription information (default is 'latest').
	 * @param pageNumber The page number for paginated results (default is 0).
	 * @param pageSize The number of items per page for paginated results (default is 500).
	 * @return A ResponseEntity containing a Document object with the student subscription information.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/getSubscriptionInfo")
	public ResponseEntity<Document<?>> getStudentSubscriptionInfo(@RequestBody InfoFilterDTO infoFilterDTO,
			@RequestParam(defaultValue = "latest", required = false) String sort,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "50") int pageSize) {

	    Document<?> result = studentSubscriptionService.getStudentSubcriptionInfo(infoFilterDTO, sort, pageNumber, pageSize);
	   
	    return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	/**
	 * Controller method to get all basic users who are unsubscribed.
	 * Requires the user to have the 'ROLE_ADMIN' role.
	 * 
	 *  @author Abdul Elahi
	 * 
	 * @param infoFilterDTO The data transfer object containing filters for user information.
	 * @param sort The sorting criteria for the user information (default is 'latest').
	 * @param pageNumber The page number for paginated results (default is 0).
	 * @param pageSize The number of items per page for paginated results (default is 500).
	 * @return A ResponseEntity containing a Document object with the information of all unsubscribed basic users.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/get-all-basic-users")
    public ResponseEntity<Document<?>> getAllUnsubscribedUsers(@RequestBody InfoFilterDTO infoFilterDTO,
			@RequestParam(defaultValue = "latest", required = false) String sort,
			@RequestParam(defaultValue = "0")int pageNumber, @RequestParam(defaultValue = "50") int pageSize) {
        Document<?> result = studentSubscriptionService.getAllBasicUsers(infoFilterDTO, sort, pageNumber, pageSize);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-subscribed-product")
	public ResponseEntity<Document<Object>> getSubscribedProductForUser() {
		Document<Object> response = studentSubscriptionService.getSubscribedProductForUser();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	/**
	 * @author Abdul Elahi
	 * Upgrade the purchase level of a student's subscription.
	 *
	 * This endpoint allows administrators to upgrade the purchase level of a student's subscription.
	 *
	 * @param userSurId   The ID of the user whose subscription is being upgraded.
	 * @param planType    The type of plan to upgrade to.
	 * @return A ResponseEntity containing a Document with the upgraded StudentSubscription and its status code.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/upgrade-purchase-level")
	public ResponseEntity<Document<StudentSubscription>> upgradePurchaseLevel(@RequestParam Long idStudentOrder,@RequestParam String planType) {
		Document<StudentSubscription> response = studentSubscriptionService.upgradePlan(idStudentOrder,planType);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/ios-order-creation")
	public ResponseEntity<Document<CartSummaryDTO>>  iosOrderCreation(@RequestBody NewSubscriptionPlanDTO newSubscriptionPlanDTO) {
		Document<CartSummaryDTO> document = studentSubscriptionService.iosOrderCreation(newSubscriptionPlanDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@PostMapping("/ios-subscription-creatrion")
	public ResponseEntity<Document<String>> iosSubscriptionCreation(String orderId,String transactionId) {
		Document<String> document = studentSubscriptionService.iosSubscriptionCreation(orderId,transactionId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_PARENT', 'ROLE_STUDENT')")
	@GetMapping(value = "/student-subject-progress")
	public ResponseEntity<Document<NewStudentSubscriptionSubjectDTO>> getStudentSubjectProgress(
			@RequestParam Long idStudentSubscription, @RequestParam Long idProductLine, @RequestParam Long idSubject,
			@RequestParam Long idClassStandard, @RequestParam Long idSyllabus, @RequestParam Long idState) {

		Document<NewStudentSubscriptionSubjectDTO> reponses = studentSubscriptionService.getStudentSubjectProgress(
				idProductLine, idSubject, idClassStandard, idSyllabus, idState, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
}
