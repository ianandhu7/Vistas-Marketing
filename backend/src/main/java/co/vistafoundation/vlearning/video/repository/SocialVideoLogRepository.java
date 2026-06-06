package co.vistafoundation.vlearning.video.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.SocialVideoLog;

public interface SocialVideoLogRepository extends JpaRepository<SocialVideoLog, Long>{

	
	public SocialVideoLog findByIdVLUserAndIdSocialVideo(Long idVLUser,Long idSocialVideo);

	public List<SocialVideoLog> findByIdVLUserAndCompleteFlag(Long userSurId, Boolean completeFlag);

//	public Page<SocialVideoLog> findByIdVLUserOrderByVideoCoverageDurationAsc(Long userSurId, Pageable paging);
	
	@Query("SELECT sv FROM SocialVideo sv " +
		    "WHERE sv.idSocialVideo NOT IN " +
		    "(SELECT svl.idSocialVideo FROM SocialVideoLog svl " +
		    "WHERE svl.idVLUser = :idVlUser AND svl.completeFlag = :completeFlag)")
	public Page<SocialVideo> getAllVideos(Long idVlUser, Boolean completeFlag, Pageable paging);
}
