package co.vistafoundation.vlearning.video.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.SocialVideoResolution;

public interface SocialVideoResolutionRepository extends JpaRepository<SocialVideoResolution, Long>{

	@Query(value="Select * from social_video_resolution where idsocial_video=:idSocialVideo order by resolution asc",nativeQuery=true)
	public List<SocialVideoResolution> findByIdSocialVideo(@Param("idSocialVideo")  Long idSocialVideo);
	
	
	public void deleteAllBySocialVideo(SocialVideo socialVideo);
}
