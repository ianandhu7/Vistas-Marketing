package co.vistafoundation.vlearning.batch.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.client.RestTemplate;

import co.vistafoundation.vlearning.batch.dto.BatchResponseDTO;
import co.vistafoundation.vlearning.batch.dto.BatchScheduleDTO;
import co.vistafoundation.vlearning.batch.dto.CreateBatchRequestDTO;
import co.vistafoundation.vlearning.batch.dto.ECAPersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingBatchListResponseDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchRunDetail;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.batch.service.BatchService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;

/**
 * @author NaveenKumar
 **/

@RestController
@RequestMapping("/api/v1/batch")
public class BatchController {

	@Autowired
	BatchService batchService;

	@Autowired
	private WebExPoolRepository webExPoolRepository;

	private static final Logger logger = LoggerFactory.getLogger(BatchController.class);
	
	/**
	 * @author Naveen Kumar A
	 * 
	 * @return This method will return batch meta data, for phase 1 ui.
	 */
	@GetMapping(value = "")
	public ResponseEntity<?> getbatchMetaData() {
		Document<?> reponses = batchService.getBatchMetaData();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * 
	 * @return This method will return batch info, for phase 1 ui.
	 */
	@GetMapping(value = "/class/{classId}/info")
	public ResponseEntity<?> getbatchInfo(@PathVariable("classId") Long classId,
			@RequestParam("subjectId") Long subjectId, @RequestParam("productLineId") Long productLineId,
			@RequestParam(name = "teacherId", required = false, defaultValue = "-1") Long teacherId,
			@RequestParam(name = "dayCodeId", required = false, defaultValue = "-1") Long dayCodeId,
			@RequestParam(name = "fromTime", required = false, defaultValue = "-1") String fromTime,
			@RequestParam(name = "toTime", required = false, defaultValue = "-1") String toTime) {

		Document<?> reponses = batchService.getBatchInfo(classId, subjectId, productLineId, teacherId, dayCodeId,
				fromTime, toTime);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Ahmed Reza
	 * @param userSurId
	 * @return list of Batches for the Teacher User id Provided
	 */
	@GetMapping(value = "/fetch-all-batch-lists-by-teacher-id")
	public ResponseEntity<?> getBatchListsByTeacherId(@RequestParam("idTeacher") Long idTeacher) {

		Map<String, Object> response = new HashMap<>();
		Document<Object> doc = new Document<>();

		if (idTeacher != 0 || idTeacher != null) {
			List<Batch> batchLists = batchService.getBatchLists(idTeacher);
			if (batchLists.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Batch Lists is Empty for this idTeacher");
				doc.setStatusCode(200);
				response.put("batchLists", doc);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			doc.setData(batchLists);
			doc.setMessage("List Of Batches for idTeacher " + idTeacher);
			doc.setStatusCode(200);
			response.put("batchLists", doc);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			doc.setData(null);
			doc.setMessage("Teacher Id cannot  be null or empty");
			doc.setStatusCode(500);
			response.put("batchLists", doc);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@GetMapping(value = "/listAllWebExRecordingsJSON")
	public ResponseEntity<?> fetchAllRecordingsFromWebExByHostJSON() {
		String clientId = "C64540a9dee082a991812d9e19046d3fafbbd8d1a7630a5338d490730c7e5211e";
		String clientSecret = "92b909f1d8483fcc1ad34e4fde332fb6ac9e7d79f0f00b3ec0826501cb6328a2";
		String integrationId = "Y2lzY29zcGFyazovL3VzL0FQUExJQ0FUSU9OL0M2NDU0MGE5ZGVlMDgyYTk5MTgxMmQ5ZTE5MDQ2ZDNmYWZiYmQ4ZDFhNzYzMGE1MzM4ZDQ5MDczMGM3ZTUyMTFl";

		String token = batchService.generateWebExToken();
		String url = "https://webexapis.com/v1/recordings";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(token);
		HttpEntity request = new HttpEntity(headers);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		Document doc = new Document<>();
		if (result != null) {
			doc.setData(result.getBody());
			doc.setMessage("Recordings Fetched Successfully");
			doc.setStatusCode(200);
			return new ResponseEntity<>(doc, HttpStatus.OK);
		} else {
			doc.setData(null);
			doc.setMessage("Recordings Cannot be fetched");
			doc.setStatusCode(500);
			return new ResponseEntity<>(doc, HttpStatus.OK);
		}

	}

	/**
	 * @author Ahmed Reza
	 * @return This will run the Scheduler at Night 11:30 and fetches the list of
	 *         Webex recordings for the Batch Run Details that happens on the same
	 *         day and inserts the record in Batch run Recording Table
	 * 
	 *         updated by @author NAVEEN
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/batch-run-scheduler")
	// @Scheduled(cron = "0 30 23 * * *")
	public ResponseEntity<?> webexRecordingsScheduler() {

		List<String> result = new ArrayList<>();

		Instant startTime, endTime;

		startTime = Instant.now();

		int errCount = 0;

		System.out.println("Batch Run Recording scheduler starting");

		result.add("Batch Run Recording scheduler starting");

		result.add("Started time: " + startTime);

		List<BatchRunDetail> listOfBatchRunDetails = batchService.fetchBatchRunDetailForBatchRecording();

		if (listOfBatchRunDetails.isEmpty())
			System.out.println("No Batch Run Detail Data found!");

		for (BatchRunDetail batchRunDetail : listOfBatchRunDetails) {
			SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
			try {
				String dateOfRecording = myFormat.format(dbFormat.parse(batchRunDetail.getBatchRundate()));

				WebExPool webexHostDetails = webExPoolRepository.findByIdWebExPool(batchRunDetail.getIdWebExPool());

				ResponseEntity<?> re = fetchAllRecordingsFromWebExByHostXML(webexHostDetails.getWebExUserId(),
						webexHostDetails.getWebExPassword(), batchRunDetail.getIdBatch(), dateOfRecording,
						batchRunDetail);
				result.add(((Document<?>) re.getBody()).getData().toString());

			} catch (ParseException e) {

				logger.error(e.getLocalizedMessage());
				result.add("Following Error Occured on  while running idBatchRunDetail: "
						+ batchRunDetail.getIdBatchRunDetail());
				result.add(e.getCause().getLocalizedMessage());

				errCount++;
			}

		}

		endTime = Instant.now();
		result.add("Total No.of Batch Run-Details founded: " + listOfBatchRunDetails.size() + " .");
//		result.add("Total No.of subscriptions deactivated: " +tnoda+" .");
//		result.add("Total No.of batch counts updated: " +dabc+" .");
		result.add("Total No.of Errors Occured: " + errCount + " .");
		result.add("End Time:" + endTime);
		result.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		result.add("Batch Run Recording scheduler Completed!!!");

		return ResponseEntity.status(200).body(result);
	}

	/**
	 * ˙
	 * 
	 * @author Ahmed Reza
	 * @param webExUserId
	 * @param webExPassword
	 * @param idBatch
	 * @return fetches the list of Webex recordings for the Batch Run Details that
	 *         happens on the same day and inserts the record in Batch run Recording
	 *         Table
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/listAllWebExRecordingsXML")
	public ResponseEntity<?> fetchAllRecordingsFromWebExByHostXML(
			@RequestParam(name = "webExUserId") String webExUserId,
			@RequestParam(name = "webExPassword") String webExPassword, Long idBatch, String dateOfRecording,
			BatchRunDetail batchRunDetail) {

		Document<?> response = batchService.listAllWebexRecordingXml(webExUserId, webExPassword, idBatch,
				dateOfRecording, batchRunDetail);
		return ResponseEntity.status(response.getStatusCode()).body(response);

	}

	/**
	 * @author Ahmed Reza
	 * @param BatchRunDetail
	 * @return Creates the Webex meeting and stores the info in Batch Run Details
	 *         Table and returns it
	 */
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/save-batch-run-details")
	public ResponseEntity<?> saveBatchRunDetails(@RequestBody BatchRunDetail batchRunDetail,
			@RequestParam("idTeacher") Long idTeacher) {

		Document reponses = batchService.saveBatchRunDetails(batchRunDetail, idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar
	 * @param CreateBatchRequestDTO
	 * @return This method will return batch course by create batch course dto.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-live-course")
	public ResponseEntity<?> createBatch(@RequestBody @Valid CreateBatchRequestDTO request) {

		Document<Batch> reponses = batchService.createBatch(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @return This method will return list day of week code.
	 * 
	 */
	@GetMapping(value = "/weekcode")
	public ResponseEntity<?> getbatchDayofWeekCode() {
		Document<List<DayOfWeekCode>> reponses = batchService.getDayofWeekCode();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @return This method will return list day of product line for batch.
	 * 
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/products")
	public ResponseEntity<?> getbatchProductLineData() {
		Document<?> reponses = batchService.getBatchProdcutLine();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "/getBatchInfo")
	public ResponseEntity<?> getBatchInfo(@RequestParam("idBatch") Long idBatch) {
		Document<?> reponses = batchService.getBatchshortInfo(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}


	@GetMapping(value = "/getAttendanceInfo")
	public ResponseEntity<?> getAttendanceInfo(@RequestParam("idBatch") Long idBatch,
			@RequestParam("idStudentSubscription") Long idStudentSubscription) {
		Document<?> reponses = batchService.getAttendanceInfo(idBatch, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "/getBatchrecordingDetails")
	public ResponseEntity<?> getBatchrecordingDetails(@RequestParam("idStudent") Long idStudent) {
		Document<?> reponses = batchService.getBatchrecordingDetails(idStudent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}


	@GetMapping(value = "/getBatchrecordingDetailsOnDate")
	public ResponseEntity<?> getBatchrecordingDetailsOnDate(@RequestParam("idBatch") Long idBatch,
			@RequestParam("batchRundate") String batchRundate) {

		Document<?> reponses = batchService.getBatchrecordingDetailsOndate(idBatch, batchRundate);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Ahmed Reza
	 * @param idBatch
	 * @return the selected Batch Information for the idBatch Provided
	 */

	@GetMapping(value = "/getSelectedBatchInformationByIdBatch")
	public ResponseEntity<?> getBatchInformationByIdBatch(@RequestParam(name = "idBatch") Long idBatch) {
		Document<?> reponses = batchService.getBatchInformationByIdBatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param idWebExPool
	 * @return Webex pool Object based on Id WebEx pool Provided
	 */


	@Deprecated
	@GetMapping(value = "/fetchWebExHostCredentialsByIdWebexPool")
	public ResponseEntity<?> fetchWebExHostCredentialsByIdWebexPool(
			@RequestParam(name = "idWebExPool") Long idWebExPool) {
		// Document<?> reponses =
		// batchService.fetchWebExHostCredentialsByIdWebexPool(idWebExPool);
		return ResponseEntity.status(410).body("This Deprecated API.");

	}

	/**
	 * @author Ahmed Reza
	 * @param idStudent
	 * @param idBatch
	 * @return Take Student Attendance and returns the Updated Batch Student
	 *         Attendance Object
	 */

	@GetMapping("/take-student-attendance")
	public ResponseEntity<?> takeStudentAttendanceWhileJoiningABatch(@RequestParam("idStudent") Long idStudent,
			@RequestParam("idBatch") Long idBatch) {
		Document<?> reponses = batchService.takeStudentAttendanceWhileJoiningABatch(idStudent, idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/show-student-lists")
	public ResponseEntity<?> showListOfStudentsInTeacherHomePage(@RequestParam("idBatch") Long idBatch) {
		Document<?> reponses = batchService.fetchListOfStudentsByIdBatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param idBatch
	 * @return Batch Run Details info for the Selected Batch And todays Date
	 */

	@GetMapping("/fetch-batch-run-details-by-idbatch")
	public ResponseEntity<?> fetchBatchRunDetailsByIdbatch(@RequestParam("idBatch") Long idBatch) {

		Document<?> reponses = batchService.fetchBatchRunDetailsByIdbatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/getBatchQuizReview")
	public ResponseEntity<Document> getBatchQuizReview(@RequestParam("idBatchStudentQuiz") Long idBatchStudentQuiz) {
		Document reponses = batchService.getBatchQuizReview(idBatchStudentQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/getBatchInfoOfTeacher")
	public ResponseEntity<Document> getBatchInfoOfTeacher(@RequestParam("idTeacher") Long idTeacher) {
		Document reponses = batchService.getBatchrecordingDetailsForTeacher(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/getNotificationForUpcomingBatchTest")
	public ResponseEntity<?> getNotificationForUpcomingBatchTest(@RequestParam(name = "idBatch") Long idBatch,
			@RequestParam(name = "idStudentSubscription") Long idStudentSubscription) {
		Document<?> reponses = batchService.getNotificationForUpcomingBatchTest(idBatch, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza update by @author Naveen Kumar
	 * @return list of All Batches
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/fetch-batch-lists")
	public ResponseEntity<?> fetchAllBatchLists() {
		Document<?> reponses = batchService.fetchAllBatchLists();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idClassStandard
	 * @return This method will return list subjects for idClassStandard for batchs.
	 * 
	 */
	@GetMapping(value = "/subjects")
	public ResponseEntity<?> getAllBatchSubjectData(@RequestParam Long idClassStandard) {
		Document<List<Subject>> reponses = batchService.getAllBatchSubject(idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idClassStandard
	 * @param idSyllabus
	 * @return This method will return list of product line table by passing idClass
	 *         standard and idSyllabus as param.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/product-line")
	public ResponseEntity<?> getProductLineByBatchSubject(@RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus) {

		Document<List<ProductLine>> reponses = batchService.getAllProductLineByBatchSubject(idClassStandard,
				idSyllabus);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @param idBatch
	 * @return deactiveat the Batch Information for the idBatch Provided. Not
	 *         Recommended to delete if batch has already been purchased by Students
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> deleteABatch(@RequestParam Long idBatch) {
		Document<Boolean> reponses = batchService.deactivateBatchByIdBatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/batchExpiryMessage/{idSubscription}")
	public ResponseEntity<?> batchExpiryMessage(@PathVariable Long idSubscription) {
		Document<?> reponses = batchService.batchSubscriptionCheckexpiry(idSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/getTeacherScheduledBatch/{idTeacher}/{idClassStandard}")
	public ResponseEntity<?> getTeacherScheduledBatch(@PathVariable Long idTeacher,
			@PathVariable Long idClassStandard) {
		Document<List<BatchResponseDTO>> reponses = batchService.getTeacherScheduledBatch(idTeacher, idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @return Product Line Object for Batch of 1 Student Academic
	 */
	@GetMapping("/getProductLineForBatchOfOneStudents")
	public ResponseEntity<?> getProductLineForBatchOfOneStudents() {

		Document<?> reponses = batchService.getProductLineForBatchOfOneStudents();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param PersonalCoachingFilterDTO
	 * @return list of Batches based on Filters Provided
	 */

	@PostMapping("/getBatchInformationForPersonalCoaching")
	public ResponseEntity<?> getBatchInformationForPersonalCoaching(
			@RequestBody PersonalCoachingFilterDTO personalCoachingFilterDTO) {

		Document<List<PersonalCoachingBatchListResponseDTO>> reponses = batchService
				.getBatchInformationForPersonalCoaching(personalCoachingFilterDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idClassStandard
	 * @param idSyllabus
	 * @return list of idproductlines for the given params
	 */
	@GetMapping(value = "/eca-product-line")
	public ResponseEntity<?> getProductLineByECABatch(@RequestParam Long idClassStandard,
			@RequestParam Long idSyllabus) {

		Document<List<ProductLine>> reponses = batchService.getAllProductLineECABatch(idClassStandard, idSyllabus);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @param idClassStandard
	 * @param idProductLine
	 * @param idSyllabus
	 * @return list of subject for the based on idclassstandard , id productline and
	 *         idsyllabus.
	 */

	@GetMapping(value = "/eca-subjects")
	public ResponseEntity<?> getAllBatchSubjectData(@RequestParam Long idClassStandard,
			@RequestParam Long idProductLine, @RequestParam Long idSyllabus) {
		Document<List<Subject>> reponses = batchService.getAllBatchECASubject(idClassStandard, idProductLine,
				idSyllabus);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getProductLineForBatchOfOneStudentsExtraCurricular")
	public ResponseEntity<Document<?>> getProductLineForBatchOfOneStudentsExtraCurricular() {

		Document<?> reponses = batchService.getProductLineForBatchOfOneStudentsExtraCurricular();

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param personalCoachingFilterDTO
	 * @return this method returns list of PersonalCoachingBatchListResponseDTO
	 *         based on the params sent.
	 */

	@PostMapping("/eca-personal-coaching-info")
	public ResponseEntity<?> getBatchInformationForECAPersonalCoaching(
			@RequestBody ECAPersonalCoachingFilterDTO personalCoachingFilterDTO) {

		Document<List<PersonalCoachingBatchListResponseDTO>> reponses = batchService
				.getBatchInformationForECAPersonalCoaching(personalCoachingFilterDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param idBatch
	 * @param idStudent
	 * @return Webex Attendee Join Meeting Url And Take Attendance if subscription
	 *         found
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/student-join-personal-coaching")
	public ResponseEntity<?> studentJoinPersonalCoaching(@RequestParam("idBatch") Long idBatch,
			@RequestParam("idStudent") Long idStudent) {

		Document<?> reponses = batchService.studentJoinPersonalCoaching(idBatch, idStudent);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param idBatch
	 * @return Batch Run Detail Info for Selected Batch And Todays Date
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/getBatchRunDetailWebExInfoForTodaysDate")
	public ResponseEntity<?> getBatchRunDetailWebExInfoForTodaysDate(@RequestParam("idBatch") Long idBatch) {

		Document<?> reponses = batchService.getBatchRunDetailWebExInfoForTodaysDate(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/batches-by-teachers")
	public ResponseEntity<?> getAllBatchesByIdTeacher(@RequestParam Long idTeacher) {
		Document reponses = batchService.getAllBatchesByIdTeacher(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/batch-details-by-teacher")
	public ResponseEntity<?> getAllBatchDetailsList(@RequestParam Long idTeacher) {
		Document reponses = batchService.getAllBatchDetailsList(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/batch-run")
	public ResponseEntity<?> getAllBatchConductedDetails(@RequestParam Long idBatch) {
		Document<List<Map<String, Object>>> reponses = batchService.getAllBatchConductedDetails(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param idBatch
	 * @return get All Batch Recordings For Particular Batch Selected
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/getAllBatchRecordingsForParticularBatchSelected")
	public ResponseEntity<?> getAllBatchRecordingsForParticularBatchSelected(@RequestParam("idBatch") Long idBatch) {

		Document<?> reponses = batchService.getAllBatchRecordingsForParticularBatchSelected(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idTeacher
	 * @param BatchScheduleDTO
	 * 
	 *                         This method will return boolean true , if teacher
	 *                         have availablity for a particular day of week , from
	 *                         and to timings. return false when particular teacher
	 *                         dosen't have have availablity.
	 * 
	 *                         throws AppException when teacher try to have already
	 *                         assigned to particular availablity schedule.
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/check-teacher-availablity")
	public ResponseEntity<?> checkTeacherAvailablitySchedule(@RequestParam Long idTeacher,
			@RequestBody @Valid BatchScheduleDTO request,
			@RequestParam(required = false, defaultValue = "-1") Long idBatchGroup) {

		Document<Boolean> reponses = batchService.checkTeacherAvailablity(request, idTeacher, idBatchGroup);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param personalCoachingFilterDTO
	 * @return this method returns list of PersonalCoachingBatchListResponseDTO
	 *         based on the params sent.
	 */

	@PostMapping("/browse-course")
	public ResponseEntity<?> getBatchInformationForFilters(
			@RequestBody PersonalCoachingFilterDTO personalCoachingFilterDTO) {

		Document<List<BatchResponseDTO>> reponses = batchService.getBatchDataByFilter(personalCoachingFilterDTO);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idTeacher
	 * @return this method returns list of batch assigned to the teacher.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/teacher-assigned")
	public ResponseEntity<?> getBatchInformationForTeacher(@RequestParam Long idTeacher) {

		Document<List<Object>> reponses = batchService.getBatchDetailsForTeacher(idTeacher);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idBatchCalender,idBatch
	 * @return this method wil delete batchcalender record.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/batch-calendar")
	public ResponseEntity<?> deleteBatchCalender(@RequestParam Long idBatch, @RequestParam Long idBatchCalender) {

		Document<Boolean> reponses = batchService.deleteBatchCalender(idBatch, idBatchCalender);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idBatchCalender,idBatch
	 * @return This is method will return batch detail and batch calender details
	 *         for idbatch provided.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<?> getBatchById(@RequestParam Long idBatch) {

		Document<Map<String, Object>> reponses = batchService.getBatchDetailByIdBatch(idBatch);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param CreateBatchRequestDTO
	 * @return This is method will return batch detail and update batch details
	 *         based on the request.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/")
	public ResponseEntity<?> editBatch(@RequestBody @Valid CreateBatchRequestDTO request) {

		Document<Batch> reponses = batchService.updateBatchDetail(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

}
