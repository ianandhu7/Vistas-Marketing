/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.UserVideoViewHistory;

/**
 * @author Naveen Kumar
 *
 */
public interface UserVideoViewHistoryReposirtory extends JpaRepository<UserVideoViewHistory, Long> {

	UserVideoViewHistory findBySocialVideo_idSocialVideoAndIdVlUser(Long idSocialVideo, Long userSurId);

	void deleteAllBySocialVideo(SocialVideo socialVideo);

}
