/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.SocialVideoRating;
/**
 * @author Naveen Kumar
 *
 */
public interface SocialVideoRatingReposirtory extends JpaRepository<SocialVideoRating, Long> 
{

	void deleteAllBySocialVideo(SocialVideo socialVideo);

}
