package co.vistafoundation.vlearning.subscription.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.dto.SACVideoDurationSyncDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.StudentDownloadVideoInfoDTO;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.subscription.dto.Card;
import co.vistafoundation.vlearning.subscription.dto.SubjectVideoWatchDTO;
import co.vistafoundation.vlearning.subscription.model.VideoStreamingLog;
import co.vistafoundation.vlearning.subscription.service.StudentAssignedCourseService;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

/**
 * 
 * @author NaveenKumar A
 *
 */

@RestController
@RequestMapping("/api/v1/assigned-course")
public class StudentAssignedCouserController {

	@Autowired
	StudentAssignedCourseService studentAssignedCourseService;
    	
	/**
	 * @author NaveenKumar A
	 * @param idAssignedCourse
	 * @param duration
	 * @return This method will return 
	 * student Assigend course  for provided param.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@PutMapping(value = "/video/{idAssignedCourse}")
	public ResponseEntity<?> getStudentSubStreamingData(@PathVariable Long idAssignedCourse,
			@RequestParam String duration) {

		Document<StudentAssignedCourse> reponses = studentAssignedCourseService
				.updateStudentAssignedCourseById(idAssignedCourse, duration);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
    /**
     * @author Naveen Kumar A
     * @param videoId
     * @return This method will download video info 
     * for mobile devices.
     */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/download/video")
	public ResponseEntity<?> getDownloadSubStreamingData(@RequestParam String videoId) {

		Document<VideoCipherOTP> reponses = studentAssignedCourseService.getDownloadVideoInfo(videoId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 * @author Naveen Kumar A
	 * @param offlineVideoCourseId
	 * @param idStudentSubscription
	 * @return methos will return 
	 * student assigned course for 
	 * provide params.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/")
	public ResponseEntity<?> getStudentAssignedCourse(@RequestParam Long offlineVideoCourseId ,@RequestParam Long idStudentSubscription) {

		Document<StudentAssignedCourse> reponses = studentAssignedCourseService.
				createStudentAssigendCourse(offlineVideoCourseId, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 * @author Naveen Kumar A
	 * @param videoId
	 * @return This methos will provide 
	 * otp for video id provided.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/otp")
	public ResponseEntity<?> getStudentAssignedCourse(@RequestParam String videoId) {

		Document<VideoCipherOTP> reponses = studentAssignedCourseService.createCourseVideoOtp(videoId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	/**
	 *  @author Naveen Kumar A
	 * @param idStudentAssignedCourse
	 * @return this method will provide information about video meta
	 * for mobile video download.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/offline-meta-info")
	public ResponseEntity<?> getDownloadVideo(@RequestParam Long idStudentAssignedCourse) {

		Document<StudentDownloadVideoInfoDTO> reponses = studentAssignedCourseService.getStudentVideoDataInfo(idStudentAssignedCourse);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 *  @author Naveen Kumar A
	 * @param List<SACVideoDurationSyncDTO>
	 * @return this method will update the download video info 
	 * to student assigned course.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@PutMapping(value = "/update-dowload-info")
	public ResponseEntity<?> updatetDownloadVideoInfo(@RequestBody List<SACVideoDurationSyncDTO> request) {

		Document<List<StudentDownloadVideoInfoDTO>> reponses = studentAssignedCourseService.updateSACVideoDataInfo(request);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	/**
	 * Retrieves academic continue to watch videos of user.
	 * @author Mohan Kumar K M
	 * 
	 * @return this method will return the user video streaming logs
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/continue-to-watch-videos")
	public ResponseEntity<Document<List<VideoStreamingLog>>> getAcademicVideoStreamingLogs() {
		Document<List<VideoStreamingLog>> reponses = studentAssignedCourseService.getAcademicVideoStreamingLogs();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 * Retrieves extra curricular continue to watch videos of user.
	 * @author Mohan Kumar K M
	 * 
	 * @return this method will return the user video streaming logs
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/eca-continue-to-watch-videos")
	public ResponseEntity<Document<List<VideoStreamingLog>>> getExtraCurricularVideoStreamingLogs() {
		Document<List<VideoStreamingLog>> reponses = studentAssignedCourseService.getExtraCurricularVideoStreamingLogs();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/get-student-summary")
    public ResponseEntity<Document<List<Card>>> getStudentSummery(Long idVlUser) {
		Document<List<Card>>reponses = studentAssignedCourseService.getStudentSummery(idVlUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/get-all-student-summary")
	public ResponseEntity<Document<?>> getAllStudentSummery() {
		Document<?>reponses = studentAssignedCourseService.getAllStudentSummery();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}         
