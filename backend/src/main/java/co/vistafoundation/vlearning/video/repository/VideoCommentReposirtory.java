/**
 * 
 */
package co.vistafoundation.vlearning.video.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoComment;

/**
 * @author Naveen Kumar
 *
 */
public interface VideoCommentReposirtory extends JpaRepository<VideoComment, Long> {

	VideoComment findByIdVideoCommentAndUser_userSurId(Long idVideoComment, Long userSurId);

	List<VideoComment> findBySocialVideoOrderByCreatedAtDesc(SocialVideo sv);

	Page<VideoComment> findBySocialVideoAndIsVisibleOrderByCreatedAtDesc(SocialVideo sv,boolean isVisible,Pageable paging);

	void deleteAllBySocialVideo(SocialVideo socialVideo);
	
    @Query("SELECT vc FROM VideoComment vc WHERE vc.socialVideo.idSocialVideo = :idSocialVideo AND vc.isVisible =:isVisible")
    List<VideoComment> findCommentsBySocialVideoId(@Param("idSocialVideo") Long idSocialVideo,@Param("isVisible") Boolean isVisible);
    
    @Query("SELECT vc FROM VideoComment vc WHERE vc.socialVideo.idSocialVideo = :idSocialVideo AND vc.user.userSurId =:idVlUser")
    Page<VideoComment> getCommentsByIdSocialVideoAndUser(@Param("idSocialVideo") Long idSocialVideo,Long idVlUser,Pageable paging);

	@Query("SELECT vc FROM VideoComment vc WHERE vc.socialVideo.idSocialVideo = :idSocialVideo AND (:isVisble IS NULL OR vc.isVisible=:isVisble) AND (:idVlUser IS NULL OR vc.user.userSurId = :idVlUser) AND (:from is null or DATE(vc.createdAt) >= :from) AND (:to is null or DATE(vc.createdAt) <= :to)")
	Page<VideoComment> getComments(Long idSocialVideo, Boolean isVisble, Long idVlUser, Date from, Date to,
			Pageable paging);

	VideoComment findByIdVideoComment(Long idVideoComment);
}
