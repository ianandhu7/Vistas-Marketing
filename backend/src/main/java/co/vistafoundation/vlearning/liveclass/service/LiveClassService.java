package co.vistafoundation.vlearning.liveclass.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassQuestionAnswerRequestDTO;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;
import co.vistafoundation.vlearning.liveclass.model.LiveClassWatchedHistory;
import co.vistafoundation.vlearning.liveclass.model.UserLiveClassAttended;
import co.vistafoundation.vlearning.liveclass.model.YoutubeMaster;

/**
 * 
 * @author Sajini
 *
 */

public interface LiveClassService {

	public Document<List<LiveClass>> getAllBroadcastedClassesAcademic(Long idClassStandard, Long idLiveClassCategory);

	public Document<List<LiveClass>> getAllBroadcastedClassesExtra(Long idLiveClassCategory);

	/**
	 * @author Sarfaraz
	 * 
	 * @return Live Class Details
	 */
	@SuppressWarnings("rawtypes")
	public Document getLiveClassDetailsByIdLiveClass(Long idLiveClass);

	/**
	 * @author Sarfaraz
	 * 
	 * @return save question for Live class
	 */
	@SuppressWarnings("rawtypes")
	public Document saveLiveClassQuestion(LiveClassQuestion liveClassQuestion);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Question and Answer for Live Class
	 */
	@SuppressWarnings("rawtypes")
	public Document getLiveClassAllQuestionAndAnswer(Long idLiveClass);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Live Classes for Teacher
	 */
	@SuppressWarnings("rawtypes")
	public Document getLiveClassListByIdTeacher(Long idTeacher);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Questions for Live Class
	 */
	@SuppressWarnings("rawtypes")
	public Document getAllQuestions(Long idLiveClass);

	/**
	 * @author Sarfaraz
	 * 
	 * @return save Question and Answer for Live Class
	 */
	@SuppressWarnings("rawtypes")
	public Document saveLiveClassQuestionAndAnswer(LiveClassQuestionAnswerRequestDTO request);

	public Document<LiveClassDto> getFutureLiveClassDate();

	@SuppressWarnings("rawtypes")
	public Document checkForUserIdDuplicationinYoutubeMasterTable(String youtubeUserId);

	@SuppressWarnings("rawtypes")
	public Document assignYoutubeCredentialsToTeacher(YoutubeMaster youtubeMaster);

	@SuppressWarnings("rawtypes")
	public Document getAllYoutubeMasterData();

	@SuppressWarnings("rawtypes")
	public Document deleteYoutubeMaster(Long idYoutubeMaster);

	@SuppressWarnings("rawtypes")
	public Document reAssignTeacherToYoutubeMaster(Long idSelectedTeacher, Long idYoutubeMaster);

	@SuppressWarnings("rawtypes")
	public Document checkForIsTeacherAlreadyAssignedYoutubeMaster(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document fetchAllLiveClassCategories();

	@SuppressWarnings("rawtypes")
	public Document getYoutubeMatserDataByIdTeacher(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document createLiveClass(LiveClass liveClass);

	@SuppressWarnings("rawtypes")
	public Document fetchAllLiveClassByTeacherAndDate(Long idTeacher, LocalDate date);

	@SuppressWarnings("rawtypes")
	public Document deleteLiveClassByIdLiveClass(Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document reAssignTeacherToLiveClass(Long idSelectedTeacher, Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document fetchAllLiveClassesAssignedToTeacher(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document filterLiveClassByDateTeacher(Long idTeacher, LocalDate date);

	@SuppressWarnings("rawtypes")
	public Document startLiveWebinarForTeacher(Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document endLiveWebinarForTeacher(Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document scheduleLiveClassByTeacher(LiveClass liveClass);

	@SuppressWarnings("rawtypes")
	public Document editLiveClassByTeacher(LiveClass liveClass, Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document getLiveClass(Long idClassStandard);

	@SuppressWarnings("rawtypes")
	public Document getLiveAcademicClasses(Long idClassStandard, Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getUpcomingAcademicClasses(Long idClassStandard, Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getLiveExtraCurricularClasses(Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getUpcomingExtraCurricular(Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getLiveClassDetailsByIdClassStandard(Long idClassStandard);

	@SuppressWarnings("rawtypes")
	public Document saveLiveClassAttended(UserLiveClassAttended userLiveClassAttended);

	@SuppressWarnings("rawtypes")
	public Document uploadThumbnailImageToS3(MultipartFile file);

	@SuppressWarnings("rawtypes")
	public Document getAllLiveClassesByCategory(Long idClassStandard, Long idLiveClassCategory);

	
	public Document<Page<LiveClassDto>> getAllBroadCastVideo(List<Long> IdClassStandards, Long idLanguage,
			Long idSubject, int pageNumber, Long idSyllabus, Long idState);

	@SuppressWarnings("rawtypes")
	public Document getAllLiveClass();

	public Document<List<LiveClassDto>> getAllLiveClassByIdClassStandard(List<Long> IdClassStandards, Long idSyllabus,
			Long idState);

	@SuppressWarnings("rawtypes")
	public Document uploadPdfFileoS3(MultipartFile file);

	@SuppressWarnings("rawtypes")
	public Document getLiveClassForHomePage(Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getMainVideoExtraVideo(Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getMainAcademicVideo(Long idLiveClassCategory);

	@SuppressWarnings("rawtypes")
	public Document getOngoingLiveClasses(Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document notifyLiveClass(Long idVlUser, Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document editLiveClassByAdmin(LiveClass liveClass, Long idLiveClass);

	@SuppressWarnings("rawtypes")
	public Document sendLiveClassNotificationToAll(Long idLiveClass, Long idClassStandard);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Academic Live Classes before User Login
	 */
	@SuppressWarnings("rawtypes")
	public Document getAllAcademicClasses(Long idLiveClassCategory);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Academic Live Classes after User Login
	 */
	@SuppressWarnings("rawtypes")
	public Document getAcademicClasses(Long idLiveClassCategory);

	/**
	 * @author Sarfaraz
	 * 
	 * @return List of Broadcasted Video before User Login
	 */

	public Document<Page<LiveClassDto>> getAllBroadCastedVideo(Long idLanguage, Long idSubject, int pageNumber,
			Long idClassStandard, Long idSyllabus, Long idLiveClassCategory, Long idState);

	@SuppressWarnings("rawtypes")
	public Document getGraphData(Long idClassStandard, Long userSurId);

	public void sendLiveClassReminderNotificationEmail();

	/**
	 * 
	 * @author Sajini
	 * 
	 * @param idLiveClassCategory
	 * @return
	 */

	public Document<List<LiveClassDto>> getAllAcademicLive(Long idLiveClassCategory, Long idLanguage, Long idSubject);

	public Document<List<LiveClassDto>> getAllAcademicUpcoming(Long idLiveClassCategory, Long idLanguage,
			Long idSubject);

	public Document<List<LiveClassDto>> beforeLoginAcademicLiveClasses(Long idLiveClassCategory, Long idLanguage,
			Long idClassStandard, Long idSubject, Long idSyllabus, Long idState);

	public Document<List<LiveClassDto>> beforeLoginAcademicUpcomingClasses(Long idLiveClassCategory, Long idLanguage,
			Long idClassStandard, Long idSubject, Long idSyllabus, Long idState);

	public Document<List<LiveClassDto>> afterLoginAcademicLive(Long idLiveClassCategory, Long idLanguage);

	public Document<List<LiveClassDto>> afterLoginAcademicUpcoming(Long idLiveClassCategory, Long idLanguage);

	public Document<List<LiveClassDto>> getAllLiveClassByClass(Long idClassStandard, Long idLiveClassCategory,
			Long idLanguage);

	public Document<List<LiveClassDto>> getAllUpcomingClassByClass(Long idClassStandard, Long idLiveClassCategory,
			Long idLanguage);

	public Document<LiveClassDto> getLiveClassMainVideoByClass(Long idClassStandard, Long idLiveClassCategory,
			Long idLanguage, Long idSubject, Long idSyllabus, Long idState);

	public Document<List<LiveClassDto>> getAllAcademicLiveByLanguage(Long idLiveClassCategory, Long idLanguage);

//	public Document<List<Subject>> getAllSubjectsByCategory(Long idLiveClassCategory);

	public Document<List<LiveClassDto>> getAllLiveAndUpcomingBySubjectAndClassAndLan(Long idLiveClassCategory,
			String language, Long idSubject, Long idClassStandard, Long idSyllabus, Long idState);

	public void deactivateDeletedVideos();

	public Boolean userAllowedToAccessBroadCastedVideo(Long idLiveClass);

	public Document<List<LiveClassDto>> otherclassLiveBeforeLogin(Long idLiveClassCategory, Long idClassStandard,
			Long idSubject, Long idSyllabus, Long idState);

	public Document<List<LiveClassDto>> otherclassUpcomingBeforeLogin(Long idLiveClassCategory, Long idClassStandard,
			Long idSubject, Long idSyllabus, Long idState);

	public Document<List<LiveClassDto>> otherclassAfterLoginLive(Long idLiveClassCategory, Long idSubject);

	public Document<List<LiveClassDto>> otherclassAfterLoginUpcoming(Long idLiveClassCategory, Long idSubject);

	
	public Document<Page<LiveClassDto>> getAllExtraCurWebCastedVideos(Long idLiveClassCategory, Long idLanguage,
			Long idSubject, int pageNumber);

	public Document<List<LiveClassDto>> otherExtraCurrclassLive(Long idLiveClassCategory, Long idSubject);

	public Document<List<LiveClassDto>> otherExtraCurrclassUpcoming(Long idLiveClassCategory, Long idSubject);
	
	public Document<LiveClassWatchedHistory> saveLiveClassWatchedHistory(Long idLiveClass);
	
	public Document<Page<Map<String,List<LiveClassWatchedHistory>>>> getAllWatchedHistoy(int pageNumber,Long idSubject, Long idLanguage, String type);
	
	public Document<LiveClassWatchedHistory> updateLiveClassWatchedHistory(Long idLiveClass, Long watchedDuration);
	

}
