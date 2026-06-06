/**
 * 
 */
package co.vistafoundation.vlearning.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.dto.DeleteSocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.ReviewSocialVideoDTO;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.service.SocialVideoService;

/**
 * @author NAVEEN
 *
 */

@RestController
@RequestMapping("/api/v1/moderator")
public class SocialVideoReviewController {
	
	@Autowired
	private SocialVideoService socialVideoService;
	
	
	/**
	 * @author Naveen Kumar A
	 * @param pageNumber, active Flag
	 * @return This is method will 
	 * return list Social video based on 
	 * latest upload.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_MODERATOR')")
	@GetMapping(value = "/videos")
	public ResponseEntity<?> getAllVideos(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam Boolean activeFlag) 
	{
		Document<Page<SocialVideo>> reponses = socialVideoService.getALLSocialVideo(pageNumber,activeFlag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo, active Flag
	 * @return This is method will update
	 * social video info based user @param activeFlag
	 * 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_MODERATOR')")
	@PutMapping(value = "/review-video")
	public ResponseEntity<?> reviewSocialVideoByID(@RequestBody ReviewSocialVideoDTO  reviewSocialVideoDTO) 
	{
		Document<SocialVideo> reponses = socialVideoService.setVideoByActiveFlagId(reviewSocialVideoDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo, playFlag
	 * @return This is method will update
	 * social video info based user @param activeFlag
	 *  
	 *  if play flag true , means some  moderator is watching video.
	 *  flag false no watching video.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_MODERATOR,ROLE_BLOGGER')")
	@PutMapping(value = "/play-status")
	public ResponseEntity<?> setSocialVideoPlayFlag(@RequestParam Long idSocialVideo,@RequestParam Boolean playFlag) 
	{
		Document<SocialVideo> reponses = socialVideoService.setVideoPlayFlagById(idSocialVideo, playFlag);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	/**
	 * @author Naveen Kumar A
	 * @param idSocialVideo
	 * @return This is method will 
	 * return  Social video based on 
	 * id provided.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_MODERATOR,ROLE_BLOGGER')")
	@GetMapping(value = "/video-info")
	public ResponseEntity<?> getVideoByIdSocialVideo(@RequestParam Long idSocialVideo) 
	{
		Document<SocialVideo> reponses = socialVideoService.getVideoForModeratorById(idSocialVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	
	/**
	 * @author Naveen Kumar A
	 * @param DeleteSocialVideoDTO
	 * @return This is method will 
	 * delete  Social video based on 
	 * id provided.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_BLOGGER')")
	@PostMapping(value = "/delete-social-video")
	public ResponseEntity<?> deleteVideoByIdSocialVideo(@RequestBody DeleteSocialVideoDTO deleteSocialVideoDTO) 
	{
		Document<String> reponses = socialVideoService.deleteSocialVideoByAdmin(deleteSocialVideoDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

}
