package co.vistafoundation.vlearning.discover.videos.Imp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;
import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoCategoryRepository;
import co.vistafoundation.vlearning.discover.videos.repository.DiscoverVideoRepository;
import co.vistafoundation.vlearning.discover.videos.services.DiscoverVideoService;


@SpringBootTest
public class DiscoverVideosImplTest {

	@Autowired
	private DiscoverVideoService discoverVideoService;
	
	@MockBean
	private DiscoverVideoRepository discoverVideoRepository;
	
	@MockBean
	private DiscoverVideoCategoryRepository discoverVideoCategoryRepository;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws ParseException {
		
		DiscoverVideo discoverVideo = new DiscoverVideo();
		discoverVideo.setIdDiscoverVideo(1L);
		discoverVideo.setVideoLink("b60b303af8914151875fbb8f593c81d2");
		discoverVideo.setLanguage("English");
		discoverVideo.setFeaturedFlag(Boolean.TRUE);
		discoverVideo.setIdDiscoverVideoCategory(1L);
		discoverVideo.setUploadedDate((new Date(2021, 4, 1)));
		discoverVideo.setTopic("Topic1");
		discoverVideo.setVideoOtp("123456");
		discoverVideo.setVideoDuration(30);
		discoverVideo.setPostarLoc("PosterLoc");
		discoverVideo.setVideoTheme("VideoTheme");
		
		List<DiscoverVideo> videolist=new ArrayList<DiscoverVideo>();
		videolist.add(discoverVideo);
		
		DiscoverVideoCategory discoverVideoCategory =new DiscoverVideoCategory();
		discoverVideoCategory.setIdDiscoverVideoCategory(1L);
		discoverVideoCategory.setCategory("Drawing");
		discoverVideoCategory.setImageLink("https://vistasocialmedia.s3.ap-south-1.amazonaws.com/Discover_Videos/assest/Placeholder_hkx.png");
		
		List<DiscoverVideoCategory> videoCategoryList=new ArrayList<DiscoverVideoCategory>();
		videoCategoryList.add(discoverVideoCategory);
		
		Mockito.when(discoverVideoRepository.findByFeaturedFlag(Boolean.TRUE)).thenReturn(videolist);
		
		Mockito.when(discoverVideoRepository.findTOP10ByOrderByUploadedDateDesc()).thenReturn(videolist);
		
		Mockito.when(discoverVideoRepository.findByIdDiscoverVideoCategory(1L)).thenReturn(videolist);
		
		Mockito.when(discoverVideoRepository.findByIdDiscoverVideoCategoryAndLanguage(1L,"English")).thenReturn(videolist);
		
		Mockito.when(discoverVideoCategoryRepository.findAll()).thenReturn(videoCategoryList);
		
		
		Mockito.when(discoverVideoRepository.findTOP10ByLanguageOrderByUploadedDateDesc("English")).thenReturn(videolist);
		
		

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
//	@Test
	void testGetFeaturedVideos() {
		Document doc = discoverVideoService.getAllfeaturedVideos();
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideo> discoverVideolist = (List<DiscoverVideo>) doc.getData();
		assertEquals(1, discoverVideolist.size());
		assertEquals(1, discoverVideolist.get(0).getIdDiscoverVideo());
		assertEquals("b60b303af8914151875fbb8f593c81d2", discoverVideolist.get(0).getVideoLink());
		assertEquals("English", discoverVideolist.get(0).getLanguage());
		assertEquals(Boolean.TRUE, discoverVideolist.get(0).getFeaturedFlag());
		assertEquals(1, discoverVideolist.get(0).getIdDiscoverVideoCategory());
		assertEquals(new Date(2021, 4, 1), discoverVideolist.get(0).getUploadedDate());
		assertEquals("Topic1", discoverVideolist.get(0).getTopic());
		assertEquals("123456", discoverVideolist.get(0).getVideoOtp());
		assertEquals(30, discoverVideolist.get(0).getVideoDuration());
		assertEquals("PosterLoc", discoverVideolist.get(0).getPostarLoc());
		assertEquals("VideoTheme", discoverVideolist.get(0).getVideoTheme());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Test
	void testGetAllLatestVideos() {
		Document doc = discoverVideoService.getTop10LatestVideos();
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideo> latestdiscoverVideolist = (List<DiscoverVideo>) doc.getData();
		assertEquals(1, latestdiscoverVideolist.size());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideo());
		assertEquals("b60b303af8914151875fbb8f593c81d2", latestdiscoverVideolist.get(0).getVideoLink());
		assertEquals("English", latestdiscoverVideolist.get(0).getLanguage());
		assertEquals(Boolean.TRUE, latestdiscoverVideolist.get(0).getFeaturedFlag());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideoCategory());
		assertEquals(new Date(2021, 4, 1), latestdiscoverVideolist.get(0).getUploadedDate());
		assertEquals("Topic1", latestdiscoverVideolist.get(0).getTopic());
		assertEquals("123456", latestdiscoverVideolist.get(0).getVideoOtp());
		assertEquals(30, latestdiscoverVideolist.get(0).getVideoDuration());
		assertEquals("PosterLoc", latestdiscoverVideolist.get(0).getPostarLoc());
		assertEquals("VideoTheme", latestdiscoverVideolist.get(0).getVideoTheme());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Test
	void testGetDiscoverVideosByCategory() {
		Document doc = discoverVideoService.getDiscoverVideosByCategory(1L);
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideo> latestdiscoverVideolist = (List<DiscoverVideo>) doc.getData();
		assertEquals(1, latestdiscoverVideolist.size());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideo());
		assertEquals("b60b303af8914151875fbb8f593c81d2", latestdiscoverVideolist.get(0).getVideoLink());
		assertEquals("English", latestdiscoverVideolist.get(0).getLanguage());
		assertEquals(Boolean.TRUE, latestdiscoverVideolist.get(0).getFeaturedFlag());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideoCategory());
		assertEquals(new Date(2021, 4, 1), latestdiscoverVideolist.get(0).getUploadedDate());
		assertEquals("Topic1", latestdiscoverVideolist.get(0).getTopic());
		assertEquals("123456", latestdiscoverVideolist.get(0).getVideoOtp());
		assertEquals(30, latestdiscoverVideolist.get(0).getVideoDuration());
		assertEquals("PosterLoc", latestdiscoverVideolist.get(0).getPostarLoc());
		assertEquals("VideoTheme", latestdiscoverVideolist.get(0).getVideoTheme());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Test
	void testGetDiscoverVideosByCategoryandLanguage() {
		Document doc = discoverVideoService.getDiscoverVideosByCategoryandLanguage(1L,"English");
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideo> latestdiscoverVideolist = (List<DiscoverVideo>) doc.getData();
		assertEquals(1, latestdiscoverVideolist.size());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideo());
		assertEquals("b60b303af8914151875fbb8f593c81d2", latestdiscoverVideolist.get(0).getVideoLink());
		assertEquals("English", latestdiscoverVideolist.get(0).getLanguage());
		assertEquals(Boolean.TRUE, latestdiscoverVideolist.get(0).getFeaturedFlag());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideoCategory());
		assertEquals(new Date(2021, 4, 1), latestdiscoverVideolist.get(0).getUploadedDate());
		assertEquals("Topic1", latestdiscoverVideolist.get(0).getTopic());
		assertEquals("123456", latestdiscoverVideolist.get(0).getVideoOtp());
		assertEquals(30, latestdiscoverVideolist.get(0).getVideoDuration());
		assertEquals("PosterLoc", latestdiscoverVideolist.get(0).getPostarLoc());
		assertEquals("VideoTheme", latestdiscoverVideolist.get(0).getVideoTheme());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void testGetAllDiscoverCategories() {
		Document doc = discoverVideoService.getAllDiscoverCategories();
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideoCategory> videoCategoryList = (List<DiscoverVideoCategory>) doc.getData();
		assertEquals(1, videoCategoryList.size());
		assertEquals(1, videoCategoryList.get(0).getIdDiscoverVideoCategory());
		assertEquals("Drawing", videoCategoryList.get(0).getCategory());
		assertEquals("https://vistasocialmedia.s3.ap-south-1.amazonaws.com/Discover_Videos/assest/Placeholder_hkx.png", videoCategoryList.get(0).getImageLink());
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Test
	void testGetAllLatestVideosOnLanguage() {
		Document doc = discoverVideoService.getAllLatestVideosOnLanguage("English");
		assertEquals(200, doc.getStatusCode());
		
		List<DiscoverVideo> latestdiscoverVideolist = (List<DiscoverVideo>) doc.getData();
		assertEquals(1, latestdiscoverVideolist.size());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideo());
		assertEquals("b60b303af8914151875fbb8f593c81d2", latestdiscoverVideolist.get(0).getVideoLink());
		assertEquals("English", latestdiscoverVideolist.get(0).getLanguage());
		assertEquals(Boolean.TRUE, latestdiscoverVideolist.get(0).getFeaturedFlag());
		assertEquals(1, latestdiscoverVideolist.get(0).getIdDiscoverVideoCategory());
		assertEquals(new Date(2021, 4, 1), latestdiscoverVideolist.get(0).getUploadedDate());
		assertEquals("Topic1", latestdiscoverVideolist.get(0).getTopic());
		assertEquals("123456", latestdiscoverVideolist.get(0).getVideoOtp());
		assertEquals(30, latestdiscoverVideolist.get(0).getVideoDuration());
		assertEquals("PosterLoc", latestdiscoverVideolist.get(0).getPostarLoc());
		assertEquals("VideoTheme", latestdiscoverVideolist.get(0).getVideoTheme());
		
	}
}
