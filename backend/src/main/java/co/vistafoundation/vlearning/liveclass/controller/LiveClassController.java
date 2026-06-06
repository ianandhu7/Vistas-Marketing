
package co.vistafoundation.vlearning.liveclass.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassQuestionAnswerRequestDTO;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;
import co.vistafoundation.vlearning.liveclass.model.LiveClassWatchedHistory;
import co.vistafoundation.vlearning.liveclass.model.UserLiveClassAttended;
import co.vistafoundation.vlearning.liveclass.model.YoutubeMaster;
import co.vistafoundation.vlearning.liveclass.service.LiveClassService;

/**
 * 
 * @author Sajini
 *
 */

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("api/v1/liveClass")
public class LiveClassController {

	@Autowired
	LiveClassService liveClassService;

	@GetMapping(value = "/")
	public ResponseEntity<?> getAllBroadcastedVideos(@RequestParam Long idLiveClassCategory,
			@RequestParam Long idClassStandard) {
		Document<?> reponses = liveClassService.getAllBroadcastedClassesAcademic(idLiveClassCategory, idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping(value = "/extra-broadcast")
	public ResponseEntity<?> getAllBroadcastedVideos(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getAllBroadcastedClassesExtra(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping(value = "/getLiveClassDetails")
	public ResponseEntity<?> getLiveClassDetails(@RequestParam Long idLiveClass) {
		Document<?> reponses = liveClassService.getLiveClassDetailsByIdLiveClass(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/future-class/")
	public ResponseEntity<?> getFutureLiveClassDate() {
		Document<LiveClassDto> document = liveClassService.getFutureLiveClassDate();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "/checkYoutubeUserIdExistenceStatus")
	public ResponseEntity<?> checkForUserIdDuplicationinYoutubeMasterTable(
			@RequestParam("youtubeUserId") String youtubeUserId) {

		Document<?> reponses = liveClassService.checkForUserIdDuplicationinYoutubeMasterTable(youtubeUserId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/assignYoutubeCredentialsToTeacher")
	public ResponseEntity<?> assignYoutubeCredentialsToTeacher(@RequestBody YoutubeMaster youtubeMaster) {

		Document<?> reponses = liveClassService.assignYoutubeCredentialsToTeacher(youtubeMaster);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/getAllYoutubeMasterData")
	public ResponseEntity<?> getAllYoutubeMasterData() {

		Document<?> reponses = liveClassService.getAllYoutubeMasterData();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@DeleteMapping("/deleteYoutubeMaster")
	public ResponseEntity<?> deleteYoutubeMaster(@RequestParam("idYoutubeMaster") Long idYoutubeMaster) {

		Document<?> reponses = liveClassService.deleteYoutubeMaster(idYoutubeMaster);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/reAssignTeacherToYoutubeMaster")
	public ResponseEntity<?> reAssignTeacherToYoutubeMaster(@RequestParam("idSelectedTeacher") Long idSelectedTeacher,
			@RequestParam("idYoutubeMaster") Long idYoutubeMaster) {

		Document<?> reponses = liveClassService.reAssignTeacherToYoutubeMaster(idSelectedTeacher, idYoutubeMaster);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/checkForIsTeacherAlreadyAssigned")
	public ResponseEntity<?> checkForIsTeacherAlreadyAssignedYoutubeMaster(@RequestParam("idTeacher") Long idTeacher) {

		Document<?> reponses = liveClassService.checkForIsTeacherAlreadyAssignedYoutubeMaster(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getAllLiveClassCategories")
	public ResponseEntity<?> fetchAllLiveClassCategories() {
		Document<?> reponses = liveClassService.fetchAllLiveClassCategories();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/getYoutubeMatserDataByIdTeacher")
	public ResponseEntity<?> getYoutubeMatserDataByIdTeacher(@RequestParam("idTeacher") Long idTeacher) {
		Document<?> reponses = liveClassService.getYoutubeMatserDataByIdTeacher(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/save-live-class")
	public ResponseEntity<?> createLiveClass(@RequestBody LiveClass liveClass) {
		Document<?> reponses = liveClassService.createLiveClass(liveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@PostMapping(value = "/saveQuestion")
	public ResponseEntity<?> saveLiveClassQuestion(@RequestBody LiveClassQuestion liveClassQuestion) {
		Document<?> reponses = liveClassService.saveLiveClassQuestion(liveClassQuestion);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/getAllQuestionAndAnswer")
	public ResponseEntity<?> getAllQuestionAndAnswer(@RequestParam Long idLiveClass) {
		Document<?> reponses = liveClassService.getLiveClassAllQuestionAndAnswer(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

	
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/getLiveClassListByIdTeacher")
	public ResponseEntity<?> getLiveClassListByIdTeacher(@RequestParam Long idTeacher) {
		Document<?> document = liveClassService.getLiveClassListByIdTeacher(idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/getAllQuestions")
	public ResponseEntity<?> getAllQuestions(@RequestParam Long idLiveClass) {
		Document<?> reponses = liveClassService.getAllQuestions(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@PostMapping(value = "/saveQuestionAndAnswer")
	public ResponseEntity<?> saveLiveClassQuestionAndAnswer(
			@RequestBody LiveClassQuestionAnswerRequestDTO request) {
		Document<?> reponses = liveClassService.saveLiveClassQuestionAndAnswer(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/fetchAllLiveClassByTeacherAndDate")
	public ResponseEntity<?> fetchAllLiveClassByTeacherAndDate(@RequestParam("idTeacher") Long idTeacher,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		Document<?> reponses = liveClassService.fetchAllLiveClassByTeacherAndDate(idTeacher, date);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@DeleteMapping("/deleteLiveClass")
	public ResponseEntity<?> deleteLiveClassByIdLiveClass(@RequestParam("idLiveClass") Long idLiveClass) {

		Document<?> reponses = liveClassService.deleteLiveClassByIdLiveClass(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
 
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/reAssignTeacherToLiveClass")
	public ResponseEntity<?> reAssignTeacherToLiveClass(@RequestParam("idSelectedTeacher") Long idSelectedTeacher,
			@RequestParam("idLiveClass") Long idLiveClass) {
		Document<?> reponses = liveClassService.reAssignTeacherToLiveClass(idSelectedTeacher, idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/fetchAllLiveClassesAssignedToTeacher")
	public ResponseEntity<?> fetchAllLiveClassesAssignedToTeacher(@RequestParam("idTeacher") Long idTeacher) {

		Document<?> reponses = liveClassService.fetchAllLiveClassesAssignedToTeacher(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/filterLiveClassByDateTeacher")
	public ResponseEntity<?> filterLiveClassByDateTeacher(@RequestParam("idTeacher") Long idTeacher,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		Document<?> reponses = liveClassService.filterLiveClassByDateTeacher(idTeacher, date);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/startLiveWebinarByTeacher")
	public ResponseEntity<?> startLiveWebinarForTeacher(@RequestParam("idLiveClass") Long idLiveClass) {

		Document<?> reponses = liveClassService.startLiveWebinarForTeacher(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/endLiveClassByTeacher")
	public ResponseEntity<?> endLiveWebinarForTeacher(@RequestParam("idLiveClass") Long idLiveClass) {

		Document<?> reponses = liveClassService.endLiveWebinarForTeacher(idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/scheduleLiveClassByTeacher")
	public ResponseEntity<?> scheduleLiveClassByTeacher(@RequestBody LiveClass liveClass) {

		Document<?> reponses = liveClassService.scheduleLiveClassByTeacher(liveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/editLiveClassByTeacher")
	public ResponseEntity<?> editLiveClassByTeacher(@RequestBody LiveClass liveClass,
			@RequestParam("idLiveClass") Long idLiveClass) {

		Document<?> reponses = liveClassService.editLiveClassByTeacher(liveClass, idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping("/getLiveClass")
	public ResponseEntity<?> getLiveClass(@RequestParam Long idClassStandard) {
		Document<?> reponses = liveClassService.getLiveClass(idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getLiveAcademicClasses")
	public ResponseEntity<?> getLiveAcademicClasses(@RequestParam Long idClassStandard,
			@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getLiveAcademicClasses(idClassStandard, idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getUpcomingAcademicClasses")
	public ResponseEntity<?> getUpcomingAcademicClasses(@RequestParam Long idClassStandard,
			@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getUpcomingAcademicClasses(idClassStandard, idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getLiveExtraCurricular")
	public ResponseEntity<?> getLiveExtraCurricularClasses(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getLiveExtraCurricularClasses(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/getUpcomingExtraCurricular")
	public ResponseEntity<?> getUpcomingExtraCurricular(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getUpcomingExtraCurricular(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@GetMapping(value = "/getLiveClassDetailsByIdClassStandard")
	public ResponseEntity<Document<?>> getLiveClassDetailsByIdClassStandard(@RequestParam Long idClassStandard) {
		Document<?> reponses = liveClassService.getLiveClassDetailsByIdClassStandard(idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@PostMapping(value = "/save-attended")
	public ResponseEntity<?> saveLiveClassAttended(@RequestBody UserLiveClassAttended request) {
		Document<?> reponses = liveClassService.saveLiveClassAttended(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
	@PostMapping(value = "uploadImage")
	public ResponseEntity<?> uploadThumbnailImage(@RequestParam("liveClassImage") MultipartFile file) {
		Document<?> reponses = liveClassService.uploadThumbnailImageToS3(file);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@GetMapping(value = "/allcategory-live-classes")
	public ResponseEntity<?> getAllLiveClassesByCategory(@RequestParam Long idClassStandard,
			@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getAllLiveClassesByCategory(idClassStandard, idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping(value = "/all-broadcast")
	public ResponseEntity<?> getAllBroadCastVideo(@RequestParam List<Long> IdClassStandards,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam Long idSyllabus,
			@RequestParam Long idState) {
		Document<?> reponses = liveClassService.getAllBroadCastVideo(IdClassStandards, idLanguage, idSubject, pageNumber,
				idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@PreAuthorize("permitAll()")
	@GetMapping(value = "/fetch-allclass")
	public ResponseEntity<?> getAllLiveClass() {
		Document<?> reponses = liveClassService.getAllLiveClass();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/fetch-allclass-ByIdClassStandard")
	public ResponseEntity<?> getAllLiveClassByIdClassStandard(@RequestParam List<Long> IdClassStandards,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<?> reponses = liveClassService.getAllLiveClassByIdClassStandard(IdClassStandards, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
	@PostMapping(value = "/uploadPdf")
	public ResponseEntity<?> uploadPdfFile(@RequestParam("liveClassPdf") MultipartFile file) {
		Document<?> reponses = liveClassService.uploadPdfFileoS3(file);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("permitAll()")
	@GetMapping("/fetch-liveclass-homepage")
	public ResponseEntity<?> getLiveClassForHomePage(
			@RequestParam(defaultValue = "1", required = false) Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getLiveClassForHomePage(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/extra-home")
	public ResponseEntity<?> getMainVideoExtraVideo(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getMainVideoExtraVideo(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@PreAuthorize("permitAll()")
	@GetMapping("/academic-home")
	public ResponseEntity<?> getMainVideoAcademicVideo(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getMainAcademicVideo(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * list of 5 or less, on going live classes based on existing main live class
	 * 
	 * @param idLiveClass
	 * @return on going live classes
	 */

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping("/ongoingliveclasses")
	public ResponseEntity<?> onGoingLiveClasses(@RequestParam Long idLiveClass) {
		Document<?> document = liveClassService.getOngoingLiveClasses(idLiveClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping("/notify-for-upcoming-live-class")
	public ResponseEntity<?> notifyLiveClass(@RequestParam("idVlUser") Long idVlUser,
			@RequestParam("idLiveClass") Long idLiveClass) {
		Document<?> reponses = liveClassService.notifyLiveClass(idVlUser, idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * edit live class based on existing live class Id
	 * 
	 * @param idLiveClass and edited live class object
	 * @return on edited live classes
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
	@PostMapping("/edit-live-class-admin")
	public ResponseEntity<?> editLiveClassByAdmin(@RequestBody LiveClass liveClass,
			@RequestParam("idLiveClass") Long idLiveClass) {
		Document<?> reponses = liveClassService.editLiveClassByAdmin(liveClass, idLiveClass);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/notify-all-for-upcoming-live-class")
	public ResponseEntity<?> sendLiveClassNotificationToAll(@RequestParam("idLiveClass") Long idLiveClass,
			@RequestParam("idClassStandard") Long idClassStandard) {
		Document<?> reponses = liveClassService.sendLiveClassNotificationToAll(idLiveClass, idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("permitAll()")
	@GetMapping("/all-academic-live-class")
	public ResponseEntity<?> getAllAcademicClasses(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getAllAcademicClasses(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT')")
	@GetMapping("/academic-live-class")
	public ResponseEntity<?> getAcademicClasses(@RequestParam Long idLiveClassCategory) {
		Document<?> reponses = liveClassService.getAcademicClasses(idLiveClassCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@PreAuthorize("permitAll()")
	@GetMapping("/all-broadcasted-class")
	public ResponseEntity<?> getAllBroadCastedVideo(
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam Long idLiveClassCategory, @RequestParam(defaultValue = "-1", required = false) Long idState) {
		Document<?> reponses = liveClassService.getAllBroadCastedVideo(idLanguage, idSubject, pageNumber, idClassStandard,
				idSyllabus, idLiveClassCategory, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping("/graph-result")
	public ResponseEntity<?> getGraphData(@RequestParam Long idClassStandard, @RequestParam Long userSurId) {
		Document<?> reponses = liveClassService.getGraphData(idClassStandard, userSurId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * @author Sajini
	 * @param idLiveClassCategory
	 * @return
	 */

	@PreAuthorize("permitAll()")
	@GetMapping("/all-academic-live")
	public ResponseEntity<?> getAllAcademicLive(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllAcademicLive(idLiveClassCategory, idLanguage,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/after-login-otherclass-live")
	public ResponseEntity<?> otherclassAfterLoginLive(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherclassAfterLoginLive(idLiveClassCategory,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/all-academic-upcoming")
	public ResponseEntity<?> getAllAcademicUpcoming(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllAcademicUpcoming(idLiveClassCategory, idLanguage,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/after-login-otherclass-upcoming")
	public ResponseEntity<?> otherclassAfterLoginUpcoming(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherclassAfterLoginUpcoming(idLiveClassCategory,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/before-login-academic-live")
	public ResponseEntity<?> BeforeLoginAcademicLiveClasses(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(value = "idState", defaultValue = "-1", required = false) Long idState) {
		Document<List<LiveClassDto>> reponses = liveClassService.beforeLoginAcademicLiveClasses(idLiveClassCategory,
				idLanguage, idClassStandard, idSubject, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/otherclass_live_before_login")
	public ResponseEntity<?> otherclassLiveBeforeLogin(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(defaultValue = "-1", required = false) Long idState) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherclassLiveBeforeLogin(idLiveClassCategory,
				idClassStandard, idSubject, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/before-login-academic-upcoming")
	public ResponseEntity<?> BeforeLoginAcademicUpcomingClasses(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(defaultValue = "-1", required = false) Long idState) {
		Document<List<LiveClassDto>> reponses = liveClassService.beforeLoginAcademicUpcomingClasses(idLiveClassCategory,
				idLanguage, idClassStandard, idSubject, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/otherclass_upcoming_before_login")
	public ResponseEntity<?> otherclassUpcomingBeforeLogin(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(defaultValue = "-1", required = false) Long idState) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherclassUpcomingBeforeLogin(idLiveClassCategory,
				idClassStandard, idSubject, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/after-login-academic-live")
	public ResponseEntity<?> afterLoginAcademicLive(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage) {
		Document<List<LiveClassDto>> reponses = liveClassService.afterLoginAcademicLive(idLiveClassCategory,
				idLanguage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/after-login-academic-upcoming")
	public ResponseEntity<?> afterLoginAcademicUpcoming(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage) {
		Document<List<LiveClassDto>> reponses = liveClassService.afterLoginAcademicUpcoming(idLiveClassCategory,
				idLanguage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/all-live-class-by-class")
	public ResponseEntity<?> getAllLiveClassByClass(@RequestParam Long idClassStandard,
			@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllLiveClassByClass(idClassStandard,
				idLiveClassCategory, idLanguage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/all-upcoming-class-by-class")
	public ResponseEntity<?> getAllUpcomingClassByClass(@RequestParam Long idClassStandard,
			@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllUpcomingClassByClass(idClassStandard,
				idLiveClassCategory, idLanguage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping("/live-main-video-by-class")
	public ResponseEntity<?> getLiveClassMainVideoByClass(@RequestParam Long idClassStandard,
			@RequestParam(defaultValue = "1", required = false) Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<LiveClassDto> reponses = liveClassService.getLiveClassMainVideoByClass(idClassStandard,
				idLiveClassCategory, idLanguage, idSubject, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/live-class-by-lan")
	public ResponseEntity<?> getAllAcademicLiveByLanguage(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllAcademicLiveByLanguage(idLiveClassCategory,
				idLanguage);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/live-class-by-sub-and-lan")
	public ResponseEntity<?> getAllLiveAndUpcomingBySubjectAndClassAndLan(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "language", defaultValue = "English", required = false) String language,
			@RequestParam Long idSubject, Long idClassStandard, @RequestParam Long idSyllabus,
			@RequestParam Long idState) {
		Document<List<LiveClassDto>> reponses = liveClassService.getAllLiveAndUpcomingBySubjectAndClassAndLan(
				idLiveClassCategory, language, idSubject, idClassStandard, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

//	/**
//	 * @author Shruthi
//	 * 
//	 * This method will called every night at 12:20
//	 * in order to deactive deleted live classes.
//	 * 
//	 */
//	@Scheduled(cron = "0 20 0 * * *")
//	public void deactivateDeletedVideos(){
//		liveClassService.deactivateDeletedVideos();
//		
//	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping("broadcasted-video-user-access")
	public ResponseEntity<Document<Boolean>> getUserAccessToBroadCasted(@RequestParam Long idLiveClass) {
		Document<Boolean> document = new Document<>();
		Boolean accessFlag = liveClassService.userAllowedToAccessBroadCastedVideo(idLiveClass);
		if (accessFlag) {
			document.setData(true);
			document.setStatusCode(HttpStatus.OK.value());
			document.setMessage("Allowed to access this broadcasted video");
		} else {
			document.setData(false);
			document.setStatusCode(HttpStatus.OK.value());
			document.setMessage("Not allowed to access this broadcasted video");
		}
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}



	@GetMapping(value = "/all-extra-cur-webcasted")
	public ResponseEntity<?> getAllExtraCurWebCastedVideos(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idLanguage", defaultValue = "7", required = false) Long idLanguage,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(defaultValue = "0") int pageNumber) {
		Document<?> reponses = liveClassService.getAllExtraCurWebCastedVideos(idLiveClassCategory, idLanguage, idSubject,
				pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/extracur-otherclass-live")
	public ResponseEntity<?> otherExtraCurrclassLive(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherExtraCurrclassLive(idLiveClassCategory,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/extracur-otherclass-upcoming")
	public ResponseEntity<?> otherExtraCurrclassUpcoming(@RequestParam Long idLiveClassCategory,
			@RequestParam(value = "idSubject", defaultValue = "-1", required = false) Long idSubject) {
		Document<List<LiveClassDto>> reponses = liveClassService.otherExtraCurrclassUpcoming(idLiveClassCategory,
				idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
		
	}
	
	@PostMapping("/sync-live-class-watched-history")
	public ResponseEntity<Document<?>> saveWatchedHistory (@RequestParam Long idLiveClass){
		Document<LiveClassWatchedHistory> response = liveClassService.saveLiveClassWatchedHistory(idLiveClass);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-all-live-class-watched-history")
	public ResponseEntity<?> getAllWatchedHistory(@RequestParam(defaultValue = "0") int pageNumber , 
			@RequestParam(defaultValue="-1", required = false) Long idSubject,
			@RequestParam(defaultValue="-1",required = false)Long  idLanguage,
			@RequestParam(defaultValue="premium",required = false)String  type
			)
	{
		Document<Page<Map<String,List<LiveClassWatchedHistory>>>> response = liveClassService.getAllWatchedHistoy(pageNumber,idSubject,idLanguage,type);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PutMapping("/update-watched-history")
	public ResponseEntity<?> updateWatchHistory (@RequestParam Long idLiveClass , @RequestParam Long watchedDuration){
		Document<LiveClassWatchedHistory> response = liveClassService.updateLiveClassWatchedHistory(idLiveClass,watchedDuration);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
