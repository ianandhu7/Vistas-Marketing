package co.vistafoundation.vlearning.batch.service;

import java.util.List;
import java.util.Map;

import co.vistafoundation.vlearning.batch.dto.BatchListDTO;
import co.vistafoundation.vlearning.batch.dto.BatchResponseDTO;
import co.vistafoundation.vlearning.batch.dto.BatchScheduleDTO;
import co.vistafoundation.vlearning.batch.dto.CreateBatchRequestDTO;
import co.vistafoundation.vlearning.batch.dto.ECAPersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingBatchListResponseDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.dto.TeacherBatchDetailsDTO;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchRunDetail;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.subject.model.Subject;

/**
 * @author NaveenKumar
 * 
 **/
public interface BatchService {

	@SuppressWarnings("rawtypes")
	public Document getBatchMetaData();

	@SuppressWarnings("rawtypes")
	public Document getBatchInfo(Long classId, Long subjectId, Long productLineId, Long teacherId, Long daySlots,
			String fromTime, String toTime);

	public Document<Batch> createBatch(CreateBatchRequestDTO request);

	public Document<List<DayOfWeekCode>> getDayofWeekCode();

	@SuppressWarnings("rawtypes")
	public Document getBatchProdcutLine();

	public List<Batch> getBatchLists(Long idTeacher);

	public String generateWebExToken();

	@SuppressWarnings("rawtypes")

	public Document getBatchshortInfo(Long idBatch);

	public List<Batch> fetchAllBatchesLists();

	@SuppressWarnings("rawtypes")
	public Document getAttendanceInfo(Long idBatch, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getBatchrecordingDetails(Long idStudent);

	@SuppressWarnings("rawtypes")
	Document getBatchrecordingDetailsOndate(Long idBatch, String batchRundate);

	public Document<?> listAllWebexRecordingXml(String webExUserId, String webExPassword, Long idBatch, String dateOfRecording,BatchRunDetail batchRunDetail);

	@SuppressWarnings("rawtypes")
	public Document saveBatchRunDetails(BatchRunDetail batchRunDetail, Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document getBatchInformationByIdBatch(Long idBatch);

	@SuppressWarnings("rawtypes")
	public Document fetchWebExHostCredentialsByIdWebexPool(Long idWebExPool);

	@SuppressWarnings("rawtypes")
	public Document takeStudentAttendanceWhileJoiningABatch(Long idStudent, Long idBatch);

	@SuppressWarnings("rawtypes")
	public Document getBatchrecordingDetailsForTeacher(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document fetchListOfStudentsByIdBatch(Long idBatch);

	@SuppressWarnings("rawtypes")
	public Document fetchBatchRunDetailsByIdbatch(Long idBatch);

	@SuppressWarnings("rawtypes")
	public Document getBatchQuizReview(Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getNotificationForUpcomingBatchTest(Long idBatch, Long idStudentSubscription);

	public Document<List<Object>> fetchAllBatchLists();

	public Document<List<Subject>> getAllBatchSubject(Long idClassStandard);

	public Document<List<ProductLine>> getAllProductLineByBatchSubject(Long idClassStandard, Long idSyllabus);



	@SuppressWarnings("rawtypes")
	public Document batchSubscriptionCheckexpiry(Long idSubscription);

	public Document<List<BatchResponseDTO>> getTeacherScheduledBatch(Long idTeacher, Long idClassStandard);

	public Document<?> getProductLineForBatchOfOneStudents();

	public Document<List<PersonalCoachingBatchListResponseDTO>> getBatchInformationForPersonalCoaching(
			PersonalCoachingFilterDTO personalCoachingFilterDTO);

	public Document<List<ProductLine>> getAllProductLineECABatch(Long idClassStandard, Long idSyllabus);

	public Document<List<Subject>> getAllBatchECASubject(Long idClassStandard, Long idProductLine, Long idSyllabus);

	public Document<?> getProductLineForBatchOfOneStudentsExtraCurricular();

	/**
	 * @author Naveen Kumar
	 * @param personalCoachingFilterDTO
	 * @return this method returs list batch for extra curricular courses.
	 */
	public Document<List<PersonalCoachingBatchListResponseDTO>> getBatchInformationForECAPersonalCoaching(
			ECAPersonalCoachingFilterDTO personalCoachingFilterDTO);

	public Document<?> studentJoinPersonalCoaching(Long idBatch, Long idStudent);

	public List<BatchRunDetail> fetchBatchRunDetailsForTodaysDate();

	public Document<?> getBatchRunDetailWebExInfoForTodaysDate(Long idBatch);

	public Document<List<BatchListDTO>> getAllBatchesByIdTeacher(Long idTeacher);

	public Document<List<TeacherBatchDetailsDTO>> getAllBatchDetailsList(Long idTeacher);

	public Document<List<Map<String, Object>>> getAllBatchConductedDetails(Long idBatch);

	public Document<?> getAllBatchRecordingsForParticularBatchSelected(Long idBatch);

	public Document<Boolean> checkTeacherAvailablity(BatchScheduleDTO request, Long idTeacher, Long idBatchGroup);

	public Document<List<BatchResponseDTO>> getBatchDataByFilter(PersonalCoachingFilterDTO request);

	public Document<List<Object>> getBatchDetailsForTeacher(Long idTeacher);

	public Document<Boolean> deleteBatchCalender(Long idBatch, Long idBatchCalender);

	public Document<Map<String, Object>> getBatchDetailByIdBatch(Long idBatch);

	public Document<Batch> updateBatchDetail(CreateBatchRequestDTO request);
	
	
	public Document<Boolean> deactivateBatchByIdBatch(Long idBatch);
	
	/**
	 * @author vk
	 * @param Batch List
	 * @return this method returs Document Boolean with custom message.
	 */
	public Document<Boolean> checkConflictsOnBatch(List<Long> idBatches);
	
	
	public List<BatchRunDetail> fetchBatchRunDetailForBatchRecording();

	
}
