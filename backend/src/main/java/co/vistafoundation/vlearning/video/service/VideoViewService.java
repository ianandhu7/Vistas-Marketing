/**
 * 
 */
package co.vistafoundation.vlearning.video.service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.video.model.VideoView;

/**
 * @author Naveen Kumar
 *
 */
public interface VideoViewService {
	
	/**
	 * This method will created the video view.
	 * @param idSocialVideo
	 * @return
	 */
	public Document<VideoView> createVideoView(Long idSocialVideo, String duration);

}
