package co.vistafoundation.vlearning.offlinecourse.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.dto.CreateVideoRequestDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.DetailsFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideosReportDto;
import co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoRatingInputDTO;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourseRating;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRatingRepository;
import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;
import co.vistafoundation.vlearning.subscription.dto.ChapterBasedVideoDTO;

@RestController
@RequestMapping("api/v1/offlineCourse/")

public class OfflineCoursesController {

	@Autowired
	private OfflineCourseService offlineCourseService;
	
	@Autowired
	private OfflineVideoCourseRatingRepository offlineVideoCourseRatingRepository;
	

	@GetMapping(value = "")
	public ResponseEntity<?> getOfflineSampleCourses() {

		return new ResponseEntity<>(offlineCourseService.getAllSampleVideos(), HttpStatus.OK);
	}

	/**
	 * @author Naveen Kumar A
	 * @param CreateVideoRequestDTO
	 * @return This method will create offline video course by accepting
	 *         CreateVideoRequestDTO as param.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "upload")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> uploadOfflineCourse(@Valid @RequestBody CreateVideoRequestDTO request) {
		Document<?> document = offlineCourseService.createOfflineVideoRecord(request);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param CreateVideoRequestDTO
	 * @return This method will give top 10 offline video course uploaded.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "top10/history")
	public ResponseEntity<?> getRecentVideoRecords() {

		Document<?> document = offlineCourseService.getTop10VideoRecords();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param videoId
	 * @return This method will return offline course data.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "video/info")
	public ResponseEntity<?> getVideoinfo(@RequestParam String videoId) {

		Document<?> document = offlineCourseService.getVideoDataByVideoId(videoId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "getCourseVideos/{idProduct}/{idSubjectChapter}")
	public ResponseEntity<?> getVideoinfo(@PathVariable Long idProduct, @PathVariable Long idSubjectChapter) {

		Document<?> document = offlineCourseService.getOfflinevideoCoursesByProdIdSubId(idProduct, idSubjectChapter);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param CreateVideoRequestDTO
	 * @return This method will update offline course data.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "updateVideoCourses")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> getVideoinfo(@Valid @RequestBody CreateVideoRequestDTO offlineVideoCourse,
			@RequestParam Long idOfflineVideoCourse) {

		Document<OfflineVideoCourse> document = offlineCourseService.updateVideoCourse(offlineVideoCourse,
				idOfflineVideoCourse);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idOfflineVideoCourse
	 * @return This method will return offline Video course data for the param
	 *         provided.
	 */

	@GetMapping(value = "/{idOfflineVideoCourse}")
	public ResponseEntity<?> getOfflineVideoCourse(@PathVariable Long idOfflineVideoCourse) {
		Document<?> document = offlineCourseService.getOfflineVideoCourseData(idOfflineVideoCourse);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping(value = "getOfflineVideoCourseBasedOnproduct/{idSubject}/{idSubjectChapter}/{idSyllabus}/{idClassStandard}/{idState}")
	public ResponseEntity<?> getOfflineVideoCourseBasedOnproduct(@PathVariable Long idSubject,
			@PathVariable Long idSubjectChapter, @PathVariable Long idSyllabus, @PathVariable Long idClassStandard,
			@PathVariable Long idState) {
		Document<?> document = offlineCourseService.getOfflineCourseonProduct(idSubject, idSubjectChapter, idSyllabus,
				idClassStandard, idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "headings")
	public ResponseEntity<?> getHeadingFromChapter(@RequestParam Long idProduct, @RequestParam Long idSubjectChapter) {
		Document<List<Object>> document = offlineCourseService.getListofHeadingForChapter(idProduct, idSubjectChapter);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/total-video-count")
	public ResponseEntity<?> getTotalVideosUploaded(
			@RequestParam(value = "idClassStandard", defaultValue = "-1", required = true) Long idClassStandard,
			@RequestParam(value = "idSyllabus", defaultValue = "-1", required = true) Long idSyllabus,
			@RequestParam(value = "idState", defaultValue = "-1", required = true) Long idState) {
		Document<List<OfflineVideosReportDto>> document = offlineCourseService
				.getTotalUploadedVideoCount(idClassStandard, idSyllabus, idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "remove-ovc")
	@CacheEvict(value = {"chapterCache"}, allEntries = true)
	public ResponseEntity<?> getHeadingFromChapter(@RequestParam Long idOfflineVideoCourse) {

		Document<String> doc = new Document<>();

		UserPrincipal userPrincipal = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}

		if (userPrincipal == null) {
			throw new AppException("invalid User principal");
		} else {
			try {
				offlineCourseService.deleteOfflineVideoCourse(idOfflineVideoCourse, userPrincipal.getUserSurId());
				doc.setData(
						"Deleting OfflineVideoCourse completed, Please wait for while to reflects those changes in the UI.");
				doc.setStatusCode(200);
				doc.setMessage(
						"Deleting OfflineVideoCourse completed, Please wait for while to reflects those changes in the UI.");

			} catch (Exception e) {
				e.printStackTrace();
				doc.setMessage(e.getLocalizedMessage());
				doc.setStatusCode(500);
				doc.setData(null);
			}

		}

		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/auto-updated-video-sequences")
	public ResponseEntity<Document<String>> autoUpdatevideoSequence(
			@RequestParam(defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(defaultValue = "-1", required = false) Long idSubjectChapter,
			@RequestParam(defaultValue = "-1", required = false) Long idClassStandard,
			@RequestParam(defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(defaultValue = "-1", required = false) Long idState) {

		Document<String> doc = new Document<>();

		UserPrincipal userPrincipal = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}

		if (userPrincipal == null) {
			doc.setData("Invalid User Principal.");
			doc.setStatusCode(500);
			doc.setMessage("Invalid User Principal.");
		} else {
			offlineCourseService.updateOfflineVideoSequencingAutomatically(userPrincipal.getUserSurId(), idSubject,
					idClassStandard, idSyllabus, idState, idSubjectChapter);
			doc.setData("Auto updation of offline video course video-sequencing  functionality, successfully invoked.");
			doc.setStatusCode(200);
			doc.setMessage(
					"Auto updation of offline video course video-sequencing  functionality, successfully invoked.");
		}

		return ResponseEntity.status(200).body(doc);

	}

	@GetMapping(value = "/chapter-based-video")
	public ResponseEntity<Document<ChapterBasedVideoDTO>> chapterBasedVideo(@RequestParam Long idStudentSubscription,
			@RequestParam Long idSubjectChapter) {
		Document<ChapterBasedVideoDTO> document = offlineCourseService.chapterBasedVideo(idStudentSubscription,
				idSubjectChapter);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Saves video ratings submitted by users. This method is accessible to
	 *         users with the role 'STUDENT'.
	 * 
	 * @param rating The VideoRatingInputDTO representing the video rating.
	 * @return ResponseEntity containing the saved OfflineVideoCourseRating.
	 * 
	 */
	@PostMapping(value = "/save-video-ratings")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> saveVideoRatings(@RequestBody VideoRatingInputDTO rating) {
		Document<OfflineVideoCourseRating> document = offlineCourseService.saveVideoRating(rating);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Updates existing video ratings. This method is accessible to users
	 *         with the role 'STUDENT'.
	 * 
	 * @param updatedRating The updated OfflineVideoCourseRating object.
	 * @return ResponseEntity containing the updated OfflineVideoCourseRating.
	 * 
	 */
	@PutMapping("/update-video-rating")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> updateVideoRating(@RequestBody OfflineVideoCourseRating updatedRating) {
		Document<OfflineVideoCourseRating> document = offlineCourseService.updateVideoRating(updatedRating);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Retrieves the rating given by the current user for a specific video.
	 *         This method is accessible to users with the role 'STUDENT'.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return ResponseEntity containing the rating given by the user.
	 * 
	 */
	@GetMapping("/get-rating-for-user/{idOfflineVideoCourse}")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> getRatingForUser(@PathVariable Long idOfflineVideoCourse) {
		Document<?> document = offlineCourseService.getRatingForUser(idOfflineVideoCourse);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Retrieves the average rating for a specific video. This method is
	 *         accessible to users with the role 'STUDENT'.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return ResponseEntity containing the average rating for the video.
	 * 
	 */
	@GetMapping("/get-average-rating/{idOfflineVideoCourse}")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> getAverageRating(@PathVariable Long idOfflineVideoCourse) {
		Document<Double> document = offlineCourseService.getAverageRating(idOfflineVideoCourse);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/get-average-subject-rating")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getAverageSubjectRating(@RequestParam Long idProduct) {
		Document<?> document = offlineCourseService.getAverageSubjectRating(idProduct);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/get-average-chapter-rating")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getAverageSubjecChaptertRating(@RequestParam Long idSubjectChapter) {
		Document<?> document = offlineCourseService.getAverageChapterRating(idSubjectChapter);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/get-videocourse-rating")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getRatingsForVideos(@RequestParam Long idOfflineVideoCourse,
			@RequestParam(defaultValue = "-1") Long userSurId, @RequestParam(defaultValue = "-1") String userName,
			@RequestParam(defaultValue = "-1") String schoolName, @RequestParam(defaultValue = "0") int from,
			@RequestParam(defaultValue = "5") int to, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "25") int size) {

		System.out.println("Video id:" + idOfflineVideoCourse);
		Document<?> document = offlineCourseService.getRatingsForVideos(idOfflineVideoCourse, userSurId, userName,
				schoolName, from, to, page, size);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Checks if the current user has already given a rating for a specific
	 *         video. This method is accessible to users with the role 'STUDENT'.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return ResponseEntity containing a boolean value indicating whether the user
	 *         has given a rating or not.
	 * 
	 */
	@GetMapping("/is-rating-available")
	@PreAuthorize("hasAnyRole('STUDENT')")
	public ResponseEntity<?> isRatingAvailable(@RequestParam Long idOfflineVideoCourse) {
		Document<Boolean> document = offlineCourseService.isRatingGiven(idOfflineVideoCourse);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PutMapping("/is-rating-visible")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> isRatingVisible(@RequestParam Long idOfflineVideoCourse, @RequestParam Long userSurId,
			@RequestParam boolean visibleFlag) {
		Document<?> document = offlineCourseService.isRatingVisible(idOfflineVideoCourse, userSurId, visibleFlag);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Filters video ratings based on various criteria. This method is
	 *         accessible to users with the role 'ROLE_ADMIN'.
	 * 
	 * @param inputDTO The RatingFilterInputDTO containing filtering criteria.
	 * @return ResponseEntity containing a Document with filtered video ratings.
	 * 
	 */
	@PostMapping("/filterVideoRatings")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Document<?>> filterVideoRatings(@RequestBody RatingFilterInputDTO inputDTO) {
		Document<?> result = offlineCourseService.filterVideoRatings(inputDTO);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @Author Abdul Elahi
	 * 
	 *         Filters video details based on various criteria. This method is
	 *         accessible to users with the role 'ROLE_ADMIN'.
	 * 
	 * @param detailsFilterInputDTO The DetailsFilterInputDTO containing filtering
	 *                              criteria.
	 * @return ResponseEntity containing a Document with filtered video details.
	 *
	 */
	@PostMapping("/filterVideoDetails")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<Document<?>> filterVideoDetails(@RequestBody DetailsFilterInputDTO detailsFilterInputDTO) {
		Document<?> result = offlineCourseService.getAllVideos(detailsFilterInputDTO);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	

	@PostMapping("/vdocipher-web-hook")
	public  ResponseEntity<Document<String>> vdocipherWebhook(@RequestHeader(value = "Authorization", required = false) String authorization,
			@RequestHeader(value = "Content-Type") String contentType, @RequestBody String payload) throws IOException {

		Document<String> result=offlineCourseService.vdocipherWebhook(payload);
		return ResponseEntity.status(result.getStatusCode()).body(result);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-vdocipher-folders")
	public ResponseEntity<Document<?>> getVdocipherFolders(@RequestParam String folderId) {
		Document<?> result = offlineCourseService.getVdocipherFolders(folderId);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/upload-video")
	public ResponseEntity<Document<String>> uploadVideo(@RequestParam("video") MultipartFile video) {
		Document<String> result = offlineCourseService.uploadVideo(video);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/delete-video")
	public ResponseEntity<Document<?>> deleteVideo(@RequestParam String key) {
		Document<?> result = offlineCourseService.deleteVideo(key);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/upload-teacher-note")
	public ResponseEntity<Document<String>> uploadTeacherNote(@RequestParam("teacherNote") MultipartFile teacherNote) {
		Document<String> result = offlineCourseService.uploadTeacherNote(teacherNote);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
}