package co.vistafoundation.vlearning.offlinecourse.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.dto.CreateVideoRequestDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.DetailsFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideosReportDto;
import co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoRatingInputDTO;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourseRating;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;
import co.vistafoundation.vlearning.subscription.dto.ChapterBasedVideoDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;

public interface OfflineCourseService {

	public List<ProductSampleVideo> getAllSampleVideos();

	@SuppressWarnings("rawtypes")
	/**
	 * 
	 * @param CreateVideoRequestDTO
	 * @return Document
	 */
	public Document createOfflineVideoRecord(CreateVideoRequestDTO request);

	@SuppressWarnings("rawtypes")
	/**
	 * Fetch all the records from offline video recordings.
	 *
	 * @return Document
	 */
	public Document getTop10VideoRecords();


	/**
	 * Fetch all the records from offline video recordings based on product id.
	 */
	public void getVideoRecordByIdProduct(StudentSubscription studentSubscription);

	/**
	 * Method return video streaming data for a particular video id which will be
	 * valid for only 5 mins
	 * 
	 * @param VideoId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Document getVideoDataByVideoId(String videoId);

	@SuppressWarnings("rawtypes")
	public Document getOfflinevideoCoursesByProdIdSubId(Long idProduct, Long idSubjectChapter);

	public Document<OfflineVideoCourse> updateVideoCourse(CreateVideoRequestDTO offlineVideoCourse,
			Long idOfflineVideoCourse);

	public Document<CreateVideoRequestDTO> getOfflineVideoCourseData(Long idOfflineVideoCourse);

	public Document<List<OfflineVideoCourse>> getTop10LatestPosts(String productLineCd);

	public Document<List<OfflineVideoCourse>> getTop10VideosByIdSubject(Long idSubject);

	public Document<?> getOfflineCourseonProduct(Long idSubject, Long idSubjectChapter, Long idSyllabus,
			Long idClassStandard, Long idState);

	public Document<List<Object>> getListofHeadingForChapter(Long idProduct, Long idSubjectChapter);
	
	public void deleteOfflineVideoCourse(Long idOfflineVideoCourse,Long userId);
	
	public Document<List<OfflineVideosReportDto>> getTotalUploadedVideoCount(Long idClass, Long idSyllabus,Long idState);
	
	public void updateOfflineVideoSequencingAutomatically(Long userSurId,Long idSubject, Long idClass, Long idSyllabus,Long idState,Long idSubjectChapter);

	Document<ChapterBasedVideoDTO> chapterBasedVideo(Long idStudentSubscription, Long idSubjectChapter);

	/**
	 * @param idOfflineVideoCourse
	 * @return
	 */
	Document<Boolean> isRatingGiven(Long idOfflineVideoCourse);

	/**
	 * @param updatedRating
	 * @return
	 */
	Document<OfflineVideoCourseRating> updateVideoRating(OfflineVideoCourseRating updatedRating);

	/**
	 * @param idOfflineVideoCourse
	 * @return
	 */
	Document<Double> getAverageRating(Long idOfflineVideoCourse);

	/**
	 * @param rating
	 * @return
	 */
	Document<OfflineVideoCourseRating> saveVideoRating(VideoRatingInputDTO rating);

	/**
	 * @param idOfflineVideoCourse
	 * @return
	 */
	Document<OfflineVideoCourseRating> getRatingForUser(Long idOfflineVideoCourse);

	/**
	 * @param inputDTO
	 * @return
	 */
	Document<?> filterVideoRatings(RatingFilterInputDTO inputDTO);

	/**
	 * @param idClassStandard
	 * @param idSubject
	 * @param getPage
	 * @param getSize
	 * @return
	 */
	Document<?> getAllVideos(DetailsFilterInputDTO inputDTO);

	/**
	 * @param idProduct
	 * @return
	 */
	public Document<?> getAverageSubjectRating(Long idProduct);

	/**
	 * @param idSubjectChapter
	 * @return
	 */
	Document<?> getAverageChapterRating(Long idSubjectChapter);

	/**
	 * @param idOfflineViseoCourse
	 * @param userSurId
	 * @param userName
	 * @param schoolName
	 * @param from
	 * @param to
	 * @return
	 */
	Document<?> getRatingsForVideos(Long idOfflineViseoCourse, Long userSurId, String userName, String schoolName,
			int from, int to, int page, int size);

	/**
	 * @param idOfflineVideoCourse
	 * @param userSurId
	 * @param isVisible
	 * @return
	 */
	Document<OfflineVideoCourseRating> isRatingVisible(Long idOfflineVideoCourse, Long userSurId, Boolean isVisible);

	public Document<?> getVdocipherFolders(String folderId);

	public Document<String> vdocipherWebhook(String payload);

	public Document<String> uploadVideo(MultipartFile video);

	public Document<?> deleteVideo(String key);

	public Document<String> uploadTeacherNote(MultipartFile teacherNote);
}
