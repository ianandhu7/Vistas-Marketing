/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoView;
/**
 * @author Naveen Kumar
 *
 */
public interface VideoViewReposirtory extends JpaRepository<VideoView, Long> {

	public VideoView findBySocialVideo_idSocialVideoAndIdVlUser(Long idSocialVideo, Long userSurId);

	public void deleteAllBySocialVideo(SocialVideo socialVideo);
	
	public List<VideoView> findByIdVlUserAndCompleteFlag(Long userSurId, Boolean completeFlag);
	
	@Query("SELECT sv FROM SocialVideo sv " +
		    "WHERE sv.idSocialVideo NOT IN " +
		    "(SELECT vw.socialVideo FROM VideoView vw " +
		    "WHERE vw.idVlUser = :idVlUser AND vw.completeFlag = :completeFlag)")
	public Page<SocialVideo> getAllVideos(Long idVlUser, Boolean completeFlag, Pageable paging);


}
