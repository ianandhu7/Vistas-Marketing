/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoLikeDislike;

/**
 * @author Naveen Kumar
 *
 */
public interface VideoLikeDislikeReposirtory extends JpaRepository<VideoLikeDislike, Long> {

	public VideoLikeDislike findBySocialVideo_idSocialVideoAndIdVlUser(Long idSocialVideo, Long userSurId);

	public void deleteAllBySocialVideo(SocialVideo socialVideo);

}
