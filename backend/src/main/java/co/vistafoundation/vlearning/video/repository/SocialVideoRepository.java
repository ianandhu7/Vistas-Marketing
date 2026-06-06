package co.vistafoundation.vlearning.video.repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.video.dto.SocialVideoResponseDTO;
import co.vistafoundation.vlearning.video.model.SocialVideo;
import co.vistafoundation.vlearning.video.model.VideoCategory;
/**
 * 
 * @author NaveenKumar
 *
 */
public interface SocialVideoRepository extends JpaRepository<SocialVideo, Long> {

	public Page<SocialVideo> findByVideoCategory_idVideoCategoryAndActiveFlagOrderByTotalViewsDescCreatedAtDesc(Long idVideoCategory,Boolean active,Pageable paging);
	
	public Page<SocialVideo> findAllByActiveFlagOrderByCreatedAtDesc(Boolean active,Pageable pageable);
	
	public SocialVideo findByidSocialVideoAndActiveFlag(Long idSocialVideo,Boolean active);

	public SocialVideo findByIdSocialVideoAndActiveFlag(Long idSocialVideo,Boolean active);

	public List<SocialVideo> findByUser_userSurIdAndActiveFlag(Long userSurId,Boolean active);
	
	public SocialVideo findFirstByActiveFlagOrderByCreatedAtDesc(Boolean active);
	
	public SocialVideo findFirstByUser_userSurIdAndActiveFlagOrderByCreatedAtDesc(Long idVlUser,Boolean active);

	public Page<SocialVideo> findByIdLocationAndActiveFlagOrderByTotalViewsDescCreatedAtDesc(Long idLocation, Boolean active ,Pageable paging);

	public Page<SocialVideo> findAllByActiveFlagOrderByTotalViewsDescCreatedAtDesc(Boolean active,Pageable paging);

	public Page<SocialVideo> findByIdLocationAndActiveFlag(Long idLocation, Boolean active,Pageable paging);

	public Page<SocialVideo> findByUser_userSurIdAndActiveFlag(Long userSurId,Boolean active ,Pageable paging);
	
	public SocialVideo findByIdSocialVideo(Long idSocialVideo);

	public SocialVideo findByIdSocialVideoAndUser_userSurId(Long idSocialVideo, Long userSurId);

	public Page<SocialVideo> findByUser_userSurId(Long userSurId, Pageable paging);

	public List<SocialVideo> findAllByActiveFlagAndUpdatedAtLessThan(Boolean b, Instant date);

	public Page<SocialVideo> findByVideoCategory_idVideoCategoryNotAndActiveFlagOrderByCreatedAtDescVideoCategory_idVideoCategoryAsc(
			Long idCategory, boolean b, Pageable paging);
	
	public List<SocialVideo> findAllByIdSocialVideoIn(List<Long> ids);

	@Query("select new co.vistafoundation.vlearning.video.dto.SocialVideoResponseDTO"
			+ "(sv.idSocialVideo,sv.videoTitle,sv.videoDescription,sv.videoDuration,sv.totalLikes,sv.totalDisLikes,vw.completeFlag,sv.thumbnailLink,vld.likeFlag,vld.disLikeFlag)"
			+ " FROM SocialVideo sv LEFT JOIN VideoLikeDislike vld on vld.socialVideo.idSocialVideo=sv.idSocialVideo AND vld.idVlUser = :userId"
			+ " LEFT JOIN VideoView vw ON vw.socialVideo.idSocialVideo=sv.idSocialVideo AND vw.idVlUser=:userId  where sv.activeFlag=:status  order by vw.completeFlag Asc")
	public List<SocialVideoResponseDTO> getSocialVideos(@Param("userId") Long userId,Pageable paging,Boolean status);

	public List<SocialVideo> findByUser_userSurId(Long userSurId);
	
	@Query("SELECT sv FROM SocialVideo sv WHERE  (:videoCategory IS NULL OR sv.videoCategory=:videoCategory) AND (:activeFlag IS NULL OR sv.activeFlag=:activeFlag) AND (:idVlUser IS NULL OR sv.user.userSurId = :idVlUser) AND (:from is null or DATE(sv.createdAt) >= :from) AND (:to is null or DATE(sv.createdAt) <= :to)")
	public Page<SocialVideo> socialVideoFilter(Long idVlUser,VideoCategory videoCategory, Date from, Date to,
			Boolean activeFlag,Pageable paging);

}
