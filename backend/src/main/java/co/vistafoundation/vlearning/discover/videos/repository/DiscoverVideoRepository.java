package co.vistafoundation.vlearning.discover.videos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscoverVideoRepository extends JpaRepository<DiscoverVideo, Long>{
	
	public List<DiscoverVideo> findByFeaturedFlag(Boolean featuredFlag);
	
	 @Query(value="select * from discover_video d order by d.uploaded_date desc limit 10" , nativeQuery=true)
	 public List<DiscoverVideo>  getLatestDiscoverVideos();	 
	 
	 
	 List<DiscoverVideo> findTOP10ByOrderByUploadedDateDesc();
	 	 
	 List<DiscoverVideo> findTOP10ByLanguageOrderByUploadedDateDesc(String language);
	 
	 
	 List<DiscoverVideo>findByIdDiscoverVideoCategory(Long idDiscoverVideoCategory);
	 
	 
	 
	 List<DiscoverVideo>findByIdDiscoverVideoCategoryAndLanguage(Long idDiscoverVideoCategory, String Language);	 
	 
	 @Query(value="select DISTINCT d.language from DiscoverVideo d")
	 public List<String>  getLanguages();	
	 
	Page<DiscoverVideo> findByTopicContainingIgnoreCase(String topic,Pageable pageable);
	List<DiscoverVideo> findByTopicContainingIgnoreCase(String topic);
	
		
//	 for Discover video category latest to 10 videos-Language based 
	List<DiscoverVideo> findTOP10ByIdDiscoverVideoCategoryAndLanguageOrderByUploadedDateDesc(Long idDiscoverVideoCategory, String language);

//	with default language Language 
	List<DiscoverVideo> findTOP10ByIdDiscoverVideoCategoryOrderByUploadedDateDesc(Long idDiscoverVideoCategory);
	
//	See All Latest Videos
	public List<DiscoverVideo> findAllByIdDiscoverVideoCategoryOrderByUploadedDateDesc(Long idDiscoverVideoCategory);
	
	
	//All category Videos-10nos
	public List<DiscoverVideo> findTop10ByIdDiscoverVideoCategory(Long idDiscoverVideoCategory);
	
	//Similar videos
	public Page<DiscoverVideo> findAllByIdDiscoverVideoCategory(Long idDiscoverVideoCategory,Pageable pageable);

	public Page<DiscoverVideo> findAllByIdDiscoverVideoCategoryAndLanguage(Long idDiscoverVideoCategory,
			String language, Pageable paging);

	public DiscoverVideo findByIdDiscoverVideo(Long idVideo);


}
