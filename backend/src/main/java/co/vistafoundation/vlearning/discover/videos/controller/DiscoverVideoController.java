package co.vistafoundation.vlearning.discover.videos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;
import co.vistafoundation.vlearning.discover.videos.services.DiscoverVideoService;

/**
 * author Meghana
 * 
 **/

@RestController
@RequestMapping("api/v1/discover/")
public class DiscoverVideoController {

	@Autowired
	DiscoverVideoService discoverVideoService;

	
	@GetMapping(value = "getFeaturedVideos")
	public ResponseEntity<?> getFeaturedVideos() {
		Document<?> reponses = discoverVideoService.getAllfeaturedVideos();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "getTop10Latestvideos")
	public ResponseEntity<?> getTop30Latestvideos() {
		Document<List<DiscoverVideo>> reponses = discoverVideoService.getTop10LatestVideos();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "getDiscoverVideosByCategory/{idDiscoverVideoCategory}")
	public ResponseEntity<?> updateNotificationReadStatus(@PathVariable Long idDiscoverVideoCategory) {
		Document<?> reponses = discoverVideoService.getDiscoverVideosByCategory(idDiscoverVideoCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "getAllCategories")
	public ResponseEntity<?> getAllCategories() {
		Document<?> reponses = discoverVideoService.getAllDiscoverCategories();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("save-discover-video")
	public ResponseEntity<?> saveDiscoverVideos(@RequestBody DiscoverVideo discoverVideo) {
		System.out.println("discv " + discoverVideo);
		Document<?> reponses = discoverVideoService.saveDiscoverVideos(discoverVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "save-discover-video-category")
	public ResponseEntity<?> saveDiscoverVideoCategory(@RequestParam("categoryImageFile") MultipartFile file,
			@RequestParam("videoCategory") String categoryName) {
		Document<?> reponses = discoverVideoService.uploadVideoCategoryImageToS3(file, categoryName);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "getLanguages")
	public ResponseEntity<?> getAllLanguages() {
		Document<?> reponses = discoverVideoService.getAllLanguages();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "getAllLatestVideosOnLanguage/{language}")
	public ResponseEntity<?> getAllLatestVideosOnLanguage(@PathVariable String language) {
		Document<?> reponses = discoverVideoService.getAllLatestVideosOnLanguage(language);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "getDiscoverVideosByCategory/{idDiscoverVideoCategory}/{language}")
	public ResponseEntity<?> updateNotificationReadStatus(@PathVariable Long idDiscoverVideoCategory,
			@PathVariable String language) {
		Document<?> reponses = discoverVideoService.getDiscoverVideosByCategoryandLanguage(idDiscoverVideoCategory,
				language);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "searchDiscoverVideo")
	public ResponseEntity<?> searchDiscoverVideo(@RequestParam String topic, @RequestParam int pageNumber) {
		Document<?> reponses = discoverVideoService.searchDiscoverVideo(topic, pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	// Discover categoryvideos -Language Based


	@GetMapping(value = "latest-videos/{idDiscoverVideoCategory}/{language}")
	public ResponseEntity<?> getTop10DiscoverCategoryVideosByLanguage(@PathVariable Long idDiscoverVideoCategory,
			@PathVariable String language) {
		Document<?> reponses = discoverVideoService.getTop10CategoryVideosByLanguage(idDiscoverVideoCategory, language);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	// Discover categoryvideos - with default language Language
	
	@GetMapping(value = "latest-videos/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getTop10LatestDiscoverCategoryVideos(@PathVariable Long idDiscoverVideoCategory) {
		Document<?> reponses = discoverVideoService.getTop10LatestCategoryVideos(idDiscoverVideoCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	// See All Latest Videos
	@GetMapping(value = "all-latest-videos/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getAllLatestCategoryVideos(@PathVariable Long idDiscoverVideoCategory) {
		Document<?> reponses = discoverVideoService.getAllLatestCategoryVideos(idDiscoverVideoCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	// All category Videos-10nos

	
	@GetMapping(value = "videos/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getTop10CategoryVideos(@PathVariable Long idDiscoverVideoCategory) {
		Document<?> reponses = discoverVideoService.getTop10CategoryVideos(idDiscoverVideoCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	// All Videos
	//pagination Updated by @author Naveen Kumar A 
	@GetMapping(value = "all-videos/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getAllCategoryVideos(@PathVariable Long idDiscoverVideoCategory,@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue ="all", required= false) String language) {
		Document<Page<DiscoverVideo>> reponses = discoverVideoService.getAllCategoryVideos(idDiscoverVideoCategory,pageNumber,language);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	// Similar Video
	//pagination Updated by @author Naveen Kumar A 
	@GetMapping(value = "similar/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getSimilarVideos(@PathVariable Long idDiscoverVideoCategory,@RequestParam(defaultValue = "0") int pageNumber) {
		Document<Page<DiscoverVideo>> reponses = discoverVideoService.getSimilarVideo(idDiscoverVideoCategory,pageNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	// category by id
	
	
	@GetMapping(value = "category/{idDiscoverVideoCategory}")
	public ResponseEntity<?> getDiscoverCategoryById(@PathVariable Long idDiscoverVideoCategory) {
		Document<DiscoverVideoCategory> reponses = discoverVideoService.getDiscoverCategoryById(idDiscoverVideoCategory);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@GetMapping(value = "discover-video/{idDiscoverVideo}")
	public ResponseEntity<Document<DiscoverVideo>> getDiscoverVideoById(@PathVariable Long idDiscoverVideo) {
		Document<DiscoverVideo> reponses = discoverVideoService.getDiscoverVideoById(idDiscoverVideo);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

}
