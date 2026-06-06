package co.vistafoundation.vlearning.subscription.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.dto.SACVideoDurationSyncDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.StudentDownloadVideoInfoDTO;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.subscription.dto.Card;
import co.vistafoundation.vlearning.subscription.dto.StudentSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.SubjectVideoWatchDTO;
import co.vistafoundation.vlearning.subscription.model.VideoStreamingLog;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

/**
 * 
 * @author NaveenKumar
 *
 */
public interface StudentAssignedCourseService {

	/**
	 * This method will update the duration of streaming and for next time
	 * rendering.
	 * 
	 * @param saci     studentAssignedCourseId
	 * @param duration (video duration)
	 * @return
	 */
	public Document<StudentAssignedCourse> updateStudentAssignedCourseById(Long saci, String duration);

	/**
	 * This method will provide information for downloading the video rendering.
	 * 
	 * @param videoId
	 * @return VideoCipherOTP
	 */
	public Document<VideoCipherOTP> getDownloadVideoInfo(String videoId);
	
	
	public Document<StudentAssignedCourse> createStudentAssigendCourse(Long idOfflineCourse,Long idStudentSubcription);
	
	public Document<VideoCipherOTP> createCourseVideoOtp(String videoId);
	
	public Document<StudentDownloadVideoInfoDTO> getStudentVideoDataInfo(Long idStudentAssignedCourse);
	
	
	public Document<List<StudentDownloadVideoInfoDTO>> updateSACVideoDataInfo( List<SACVideoDurationSyncDTO> request);
	
	public Document<List<VideoStreamingLog>> getAcademicVideoStreamingLogs();
	
	public Document<List<VideoStreamingLog>> getExtraCurricularVideoStreamingLogs();

	public Document<List<Card>> getStudentSummery(Long idVlUser);

	public Document<List<StudentSummaryDTO>> getAllStudentSummery();

}
