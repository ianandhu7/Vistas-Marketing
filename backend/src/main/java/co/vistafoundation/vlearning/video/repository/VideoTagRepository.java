package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.VideoTag;

/**
 * 
 * @author NaveenKumar
 *
 */
public interface VideoTagRepository extends JpaRepository<VideoTag, Long> {

	void deleteAllByIdSocialVideo(Long idSocialVideo);

}
