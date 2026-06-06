package co.vistafoundation.vlearning.video.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.dto.DeleteSocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.ReviewSocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoFilterDTO;
import co.vistafoundation.vlearning.video.dto.SocialVideoResponseDTO;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoCategory;
import co.vistafoundation.vlearning.video.model.VideoView;

public interface SocialVideoService {

	Document<?> uploadSocialVideoToYoutube(MultipartFile file, String videoDescription);

	/**
	 * @author NaveenKumar
	 * 
	 * @return List of Video Category for Social Video
	 */
	public Document<List<VideoCategory>> getAllVideoCategory();

	/**
	 * @author NaveenKumar
	 * 
	 * @param SocialVideoUploadDTO
	 * 
	 *                             Method will create new Social video.
	 * @return SocialVideo
	 */
	public Document<SocialVideo> createSocialVideo( String videoTitle, String videoDescription,  Long idVideoCategory ,int duration, MultipartFile socialViddeo,MultipartFile thumbnail);

	/**
	 * @author NaveenKumar
	 * 
	 * @param idCategory
	 * 
	 * Method will get all video by id category.
	 * @return SocialVideo
	 */
	public Document<Page<SocialVideoDTO>> getSocialVideoByCategory(Long idCategory,int pageNumber);

	public Document<Page<SocialVideoDTO>> getALLSocialVideo(int pageNumber);

	public Document<List<SocialVideoDTO>> getAllMySocialVideo();

	public Document<SocialVideoDTO> getLatestVideo(Long idUser);

	public Document<SocialVideoDTO> getVideoById(Long idSocialVideo);

	public Document<Page<SocialVideoDTO>> getALLPopularSocialVideo(String city, int pageNumber);

	public Document<Page<SocialVideo>> getALLSocialVideo(int pageNumber, Boolean activeFlag);

	public Document<SocialVideo> setVideoByActiveFlagId(ReviewSocialVideoDTO request);

	public Document<SocialVideo> setVideoPlayFlagById(Long idSocialVideo, Boolean activeFlag);

	public Document<SocialVideo> getVideoForModeratorById(Long idSocialVideo);

	/**
	 * This is method will allow the user to delete video uploaded.
	 * 
	 * @param idSocialVideo
	 * @return
	 */
	public Document<String> deleteSocialVideoByUser(Long idSocialVideo);

	/**
	 * This is method will allow the admin to delete video uploaded.
	 * 
	 * @param DeleteSocialVideoDTO
	 * @return
	 */
	public Document<String> deleteSocialVideoByAdmin(DeleteSocialVideoDTO request);

	public void deleteSocialVideoByScheduler();
	
	public Document<Page<SocialVideoDTO>> getAllMySocialVideoForLanding(int pageNumber);

//	public Document <SocialVideo> updateSocialVideo(Long idSocialVideo,String videoTitle, String videoDescription,  Long idVideoCategory, Integer duration,MultipartFile video, MultipartFile thumbnail);

	public Document<String> uploadSocialVideo(MultipartFile socialVideoFile);

	public Document<VideoView> saveSocialVideoLog(Long idSocialVideo, boolean completeFlag);

	public Document<Page<SocialVideoResponseDTO>> getSocialVideos(int pageNumber, int pageSize);

	public Document<String> updateSocialVideoStatus(Long idSocialVideo, Boolean status);

	/**
	 * @param idSocialVideo
	 * @param videoTitle
	 * @param videoDescription
	 * @param idVideoCategory
	 * @param duration
	 * @return
	 */
	public Document<SocialVideo> updateSocialVideo(Long idSocialVideo, String videoTitle, String videoDescription,
			Long idVideoCategory, Integer duration, MultipartFile video, MultipartFile thumbnail);

	Document<Page<SocialVideoDTO>> socialVideoFilter(SocialVideoFilterDTO socialVideoFilterDTO, String sort,
			int pageNumber, int size);

	
}
