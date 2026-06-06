package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.VideoCategory;

/**
 * 
 * 
 * @author NaveenKumar
 *
 */
public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {

	public VideoCategory findByIdVideoCategory(Long idVideoCategory);
}
