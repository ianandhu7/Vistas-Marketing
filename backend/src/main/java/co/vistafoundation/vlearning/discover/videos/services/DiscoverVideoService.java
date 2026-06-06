package co.vistafoundation.vlearning.discover.videos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;

/**
 * author  Meghana
 * 
 * **/

public interface DiscoverVideoService {
	public Document<?> getAllfeaturedVideos();
	
	public Document<List<DiscoverVideo>> getTop10LatestVideos();
	
	/**
	 * 
	 * updated by Sajini
	 * 
	 */
	
		
	public Document<?> getDiscoverVideosByCategory(Long idDiscoverVideoCategory);
		

	public Document<?> getAllDiscoverCategories();
	

	public Document<?> saveDiscoverVideos(DiscoverVideo discoverVideo);
	

	public Document<?> uploadVideoCategoryImageToS3(MultipartFile file,String categoryName);
	 
	public Document<?> getAllLanguages();
	

	public Document<?> getAllLatestVideosOnLanguage(String language);
	

	public Document<?> getDiscoverVideosByCategoryandLanguage(Long idDiscoverVideoCategory, String langauge);
	
	/**
	 *  updated by sajini
	 *  added pagination
	 * @param topic
	 * @param pageNumber
	 * @return
	 */
	public Document<Page<DiscoverVideo>> searchDiscoverVideo(String topic,int pageNumber);
	
//	Method for getting Top 10 latest videos of discover video category-Language Based
	
	
	public Document<List<DiscoverVideo>> getTop10CategoryVideosByLanguage(Long idDiscoverVideoCategory, String language);
	
//	with default language Language 
	
	public Document<List<DiscoverVideo>> getTop10LatestCategoryVideos(Long idDiscoverVideoCategory);
	
//	See All latest Videos
	
	public Document<List<DiscoverVideo>> getAllLatestCategoryVideos(Long idDiscoverVideoCategory);
	
	//All category Videos-10nos
	public Document<List<DiscoverVideo>> getTop10CategoryVideos(Long idDiscoverVideoCategory);
	
	//See All Videos
	public Document<Page<DiscoverVideo>> getAllCategoryVideos(Long idDiscoverVideoCategory,int pageNumber,String language);
	
	//Similar Videos	
	public Document<Page<DiscoverVideo>> getSimilarVideo(Long idDiscoverVideoCategory,int pageNumber);
	
	// getting discover video category object by passing id category
	public Document<DiscoverVideoCategory> getDiscoverCategoryById(Long idDiscoverVideoCategory);

	public Document<DiscoverVideo> getDiscoverVideoById(Long idDiscoverVideo);
	
	
}
