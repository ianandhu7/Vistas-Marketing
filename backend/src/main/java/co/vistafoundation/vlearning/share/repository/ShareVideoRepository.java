/**
 * 
 */
package co.vistafoundation.vlearning.share.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.share.model.ShareVideo;

/**
 * @author NaveenKumar
 *
 */
public interface ShareVideoRepository extends JpaRepository<ShareVideo, Long> {

	public ShareVideo findByIdShareVideoAndActiveFlag(Long shareId, Boolean activeFlag);

	public List<ShareVideo> getViewCountByIdRequestedUserAndRequestedDate(Long userSurId, LocalDate localDate);

	public ShareVideo findByIdRequestedUserAndRequestedDateAndVideoTypeAndIdVideo(Long userSurId, LocalDate date,
			String videoType, Long idVideo);

	public ShareVideo findByIdShareVideo(String videoId);

	public ShareVideo findByIdShareVideoAndActiveFlag(String decodedString, Boolean true1);

}
