
package co.vistafoundation.vlearning.video.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.dto.CommentFilterDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoFilterDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoResponseDTO;
import co.vistafoundation.vlearning.video.model.SocialVideo;

import co.vistafoundation.vlearning.video.model.VideoCategory;
import co.vistafoundation.vlearning.video.model.VideoComment;
import co.vistafoundation.vlearning.video.model.VideoLikeDislike;
import co.vistafoundation.vlearning.video.model.VideoView;
import co.vistafoundation.vlearning.video.service.SocialVideoService;
import co.vistafoundation.vlearning.video.service.VideoCommentService;
import co.vistafoundation.vlearning.video.service.VideoLikeDislikeService;
import co.vistafoundation.vlearning.video.service.VideoViewService;

@RestController
@RequestMapping("/api/v1/social-video")
public class SocialVideoController {

	@Autowired
	private SocialVideoService socialVideoService;

	@Autowired
	private VideoLikeDislikeService videoLikeDislikeService;

	@Autowired
	private VideoViewService videoViewService;

	@Autowired
	private VideoCommentService videoCommentService;

	@SuppressWarnings("rawtypes")
	@Deprecated
	@PostMapping(value = "/upload-social-video-to-youtube")
	public ResponseEntity<Document> uploadSocialVideoToYoutube(@RequestParam("socialVideoFile") MultipartFile file,
			@RequestParam("videoDescription") String videoDescription) {
		Document reponses = socialVideoService.uploadSocialVideoToYoutube(file, videoDescription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @return This method will return all category for social-video.
	 */
	@GetMapping(value = "/category")
	public ResponseEntity<?> getAllVideoCategory() {
		Document<List<VideoCategory>> reponses = socialVideoService.getAllVideoCategory();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param socialVideoUploadDTO
	 * @return This method will create social video record.
	 */
	@PreAuthorize("hasAnyRole('ROLE_BLOGGER , ROLE_MODERATOR')")
	@PostMapping(value = "/save")
	public ResponseEntity<?> saveSocialVideo(@RequestParam("videoTitle") String videoTitle,
			@RequestParam("videoDescription") String videoDescription,
			@RequestParam("idVideoCategory") Long idVideoCategory, @RequestParam("duration") Integer duration,
			@RequestParam("video") MultipartFile video, @RequestParam("thumbnail") MultipartFile thumbnail) {
		Document<SocialVideo> reponses = socialVideoService.createSocialVideo(videoTitle, videoDescription,
				idVideoCategory, duration, video, thumbnail);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idCategory
	 * @return This method will return list of Social Video based on category as
	 *         param.
	 */
	@GetMapping(value = "/category/{idCategory}/videos")
	public ResponseEntity<?> getVideosByIdCategory(@PathVariable Long idCategory,@RequestParam(defaultValue="0") int pageNumber) {
		Document<Page<SocialVideoDTO>> reponses = socialVideoService.getSocialVideoByCategory(idCategory,pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param pageNumber
	 * @return This is method will return list Social video based on latest upload.
	 */
	@GetMapping(value = "/")
	public ResponseEntity<?> getAllVideos(@RequestParam(defaultValue = "0") int pageNumber) {
		Document<Page<SocialVideoDTO>> reponses = socialVideoService.getALLSocialVideo(pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @return This method will return socialvideo based on param idSocialVideo.
	 */
	@GetMapping(value = "/id/{idSocialVideo}")
	public ResponseEntity<?> getVideoById(@PathVariable Long idSocialVideo) {
		Document<SocialVideoDTO> reponses = socialVideoService.getVideoById(idSocialVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param likeFlag
	 * @param disLikeFlag
	 * @return This method will update like or dislike flag for a social video.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PostMapping(value = "/{idSocialVideo}/like-dislike")
	public ResponseEntity<?> setSocialVideoLike(@PathVariable long idSocialVideo, @RequestParam boolean likeFlag,
			@RequestParam boolean disLikeFlag) {
		Document<VideoLikeDislike> reponses = videoLikeDislikeService.createUserLikeVideo(idSocialVideo, likeFlag,
				disLikeFlag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @return This method will give VideoLikeDisLike based param idSocialVideo.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "/{idSocialVideo}/like-dislike")
	public ResponseEntity<?> getSocialVideoLike(@PathVariable long idSocialVideo) {
		Document<VideoLikeDislike> reponses = videoLikeDislikeService.getUserLikeVideo(idSocialVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param duration
	 * @return This method will update video view count for the watched video by
	 *         user.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PostMapping(value = "/{idSocialVideo}/view-count")
	public ResponseEntity<?> setSocialVideoView(@PathVariable long idSocialVideo, @RequestParam String duration) {
		Document<VideoView> reponses = videoViewService.createVideoView(idSocialVideo, duration);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param videoComment
	 * @return This method will create comment for the video
	 * @param by the user.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PostMapping("/{idSocialVideo}/comment")
	public ResponseEntity<?> createSocialVideoComment(@PathVariable Long idSocialVideo,
			@RequestBody VideoComment videoComment) {
		Document<VideoComment> reponses = videoCommentService.saveComment(idSocialVideo, videoComment);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param videoComment
	 * @return This method will update comment for the @param.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PutMapping("/{idSocialVideo}/comment")
	public ResponseEntity<?> updateSocialVideoComment(@PathVariable Long idSocialVideo,
			@RequestBody VideoComment videoComment) {
		Document<VideoComment> reponses = videoCommentService.updateComment(idSocialVideo, videoComment);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param idVideoComment
	 * @return This method will delete user comment for the @param.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@DeleteMapping("/{idSocialVideo}/comment")
	public ResponseEntity<?> deleteSocialVideoComment(@PathVariable Long idSocialVideo,
			@RequestParam Long idVideoComment) {
		Document<String> reponses = videoCommentService.deleteComment(idSocialVideo, idVideoComment);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 * @param pageNumber
	 * @return This method will list social video uploaded by the user with
	 *         pagination by default page number will be 0.
	 */
	@PreAuthorize("hasAnyRole('ROLE_BLOGGER , ROLE_MODERATOR')")
	@GetMapping(value = "/my-videos")
	public ResponseEntity<?> getAllUserVideos() {
		Document<List<SocialVideoDTO>> reponses = socialVideoService.getAllMySocialVideo();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idUser
	 * @return This method will return latest social-video uploaded by user.
	 */
	@GetMapping(value = "/latest")
	public ResponseEntity<?> getLatestVideo(@RequestParam(defaultValue = "-1") Long idUser) {
		Document<SocialVideoDTO> reponses = socialVideoService.getLatestVideo(idUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @param pageNumber
	 * @return This method will return comments inculding pagination for @param
	 *         provided.
	 */
	@GetMapping(value = "/fetch-all-comments")
	public ResponseEntity<?> getAllCommentsByVideoId(@RequestParam Long idSocialVideo,@RequestParam boolean isvisble,
			@RequestParam(defaultValue = "0") int pageNumber) {
		Document<Page<VideoComment>> reponses = videoCommentService.getAllCommentsForVideo(idSocialVideo,isvisble,pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param city
	 * @param pageNumber
	 * @return This method will return list social video based on location provided
	 *         in
	 * @param with pagintaion.
	 */
	@GetMapping(value = "/popular")
	public ResponseEntity<?> getAllCommentsByVideoId(@RequestParam String city,
			@RequestParam(defaultValue = "0") int pageNumber) {
		Document<Page<SocialVideoDTO>> reponses = socialVideoService.getALLPopularSocialVideo(city, pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * 
	 * @param idSocialVideo This method will delete social video and all of their
	 *                      child table data.
	 */
	@PreAuthorize("hasAnyRole('ROLE_BLOGGER , ROLE_ADMIN')")
	@DeleteMapping("/id/{idSocialVideo}")
	public ResponseEntity<?> deleteSocialVideo(@PathVariable Long idSocialVideo) {
		Document<String> reponses = socialVideoService.deleteSocialVideoByUser(idSocialVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	/**
	 * @author Shruthi K
	 * 
	 * @param idSocialVideo This method will update the social video
	 */
	@PreAuthorize("hasAnyRole('ROLE_BLOGGER , ROLE_MODERATOR ,ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> updateSocialVideo(@RequestParam("idSocialVideo") Long idSocialVideo,
			@RequestParam("videoTitle") String videoTitle, @RequestParam("videoDescription") String videoDescription,
			@RequestParam("idVideoCategory") Long idVideoCategory, @RequestParam("duration") Integer duration,
			@RequestParam(required = false) MultipartFile video, @RequestParam(required = false) MultipartFile thumbnail) {
		Document<SocialVideo> reponses = socialVideoService.updateSocialVideo(idSocialVideo, videoTitle,
				videoDescription, idVideoCategory, duration, video, thumbnail);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Naveen Kumar A
	 *  This scheduler will run on daily basis get the inactive social
	 *         video records and delete them permanently from the system.
	 * @return
	 */
//	@Scheduled(cron = "0 0 1 * * *")
//	public void deleteInActiveSocialVideoByScheduler() {
//		Document<List<String>> response = socialVideoService.deleteSocialVideoByScheduler();
//
//		if (response.getData() != null) {
//			List<String> infoList = (List<String>) response.getData();
//
//			for (String out : infoList) {
//				logger.info(out);
//			}
//		}
//	}
	
	/**
	 * @author Naveen Kumar A
	 * @param pageNumber
	 * @return This method will list active social video uploaded by the user with
	 *         pagination by default page number will be 0.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@GetMapping(value = "/landing-my-videos")
	public ResponseEntity<?> getAllUserVideosForLanding(@RequestParam(defaultValue = "0") int pageNumber) {
		Document<Page<SocialVideoDTO>> reponses = socialVideoService.getAllMySocialVideoForLanding(pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 * @author Shruthi
	 * @param media file
	 * @return This method will upload the media file to s3 bucket and return the s3 URL.
	 */
	@PostMapping(value = "/upload-social-video")
	public ResponseEntity<?> UploadSocialVideo(@RequestParam MultipartFile socialVideoFile) {
		Document<String> responses = socialVideoService.uploadSocialVideo(socialVideoFile);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@PostMapping(value = "/{idSocialVideo}/social-video-log")
	public ResponseEntity<?> saveSocialVideoLog(@PathVariable Long idSocialVideo,
			@RequestParam boolean completeFlag) {

		Document<VideoView> reponses = socialVideoService
				.saveSocialVideoLog(idSocialVideo, completeFlag);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/get-social-videos")
	public ResponseEntity<Document<Page<SocialVideoResponseDTO>>> getSocialVideos(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "5") int pageSize) {
		Document<Page<SocialVideoResponseDTO>> reponses = socialVideoService.getSocialVideos(pageNumber, pageSize);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_BLOGGER , ROLE_MODERATOR,ROLE_ADMIN')")
	@PostMapping("/update-social-video-status")
	public ResponseEntity<Document<String>> updateSocialVideoStatus(@RequestParam("idSocialVideo") Long idSocialVideo,@RequestParam("status") Boolean status) {
		Document<String> reponses = socialVideoService.updateSocialVideoStatus(idSocialVideo, status);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/get-comments")
	public ResponseEntity<Document<Page<VideoComment>>> getComments(@RequestBody CommentFilterDTO commentFilterDTO,@RequestParam(defaultValue = "desc") String sort,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "25") int size) {
		
		Document<Page<VideoComment>> reponses = videoCommentService.getComments(commentFilterDTO,sort,
				pageNumber, size);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/update-comment-status")
	public ResponseEntity<Document<VideoComment>> updateCommentStatus(@RequestParam Long idVideoComment,@RequestParam Boolean isVisible) {
		
		Document<VideoComment> reponses = videoCommentService.updateCommentStatus(idVideoComment,isVisible );
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/social-video-filter")
	public ResponseEntity<Document<Page<SocialVideoDTO>>> socialVideoFilter(@RequestBody SocialVideoFilterDTO socialVideoFilterDTO,@RequestParam(defaultValue = "desc") String sort,
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "25") int size) {
		
		Document<Page<SocialVideoDTO>> reponses = socialVideoService.socialVideoFilter(socialVideoFilterDTO,sort,
				pageNumber, size);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	 
}
