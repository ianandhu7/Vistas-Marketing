/**
 * 
 */
package co.vistafoundation.vlearning.video.service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.model.VideoLikeDislike;

/**
 * @author Naveen Kumar
 *
 */

public interface VideoLikeDislikeService {

	/**
	 * This method will update the user like and dislike
	 * 
	 * @param idSocialVideo
	 * @param likeFlag
	 * @param disLikeFlag
	 * @return
	 */

	public Document<VideoLikeDislike> createUserLikeVideo(Long idSocialVideo, boolean likeFlag, boolean disLikeFlag);
	
	
	public Document<VideoLikeDislike> getUserLikeVideo(Long idSocialVideo);

}
