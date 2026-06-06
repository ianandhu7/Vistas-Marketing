//package co.vistafoundation.vlearning.offlinecourse.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.mockito.ArgumentMatchers.any;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import co.vistafoundation.vlearning.common.response.Document;
//import co.vistafoundation.vlearning.offlinecourse.dto.CreateVideoRequestDTO;
//import co.vistafoundation.vlearning.offlinecourse.dto.VideoLinkDTO;
//import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
//import co.vistafoundation.vlearning.offlinecourse.repository.OfflineCourseRepository;
//import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
//import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
//import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;
//import co.vistafoundation.vlearning.product.model.Product;
//import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
//import co.vistafoundation.vlearning.product.repository.ProductRepository;
//import co.vistafoundation.vlearning.subject.model.Subject;
//import co.vistafoundation.vlearning.subject.model.SubjectChapter;
//import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
//import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
//import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
//import co.vistafoundation.vlearning.videocipher.dto.VideoCipherMeta;
//import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;
//import co.vistafoundation.vlearning.videocipher.dto.VideoPoster;
//
///**
// * 
// * @author Naveen Kumar
// *
// */
//@SpringBootTest
//class OfflineCourseServiceImplTest {
//
//	@Autowired
//	OfflineCourseService offlineCourseService;
//
//	@MockBean
//	public OfflineCourseRepository offlineCourseRepository;
//
//	@MockBean
//	ProductRepository productRepository;
//
//	@MockBean
//	ProductGroupRepository productGroupRepository;
//
//	@MockBean
//	OfflineVideoCourseRepository offlineVideoCourseRepository;
//
//	@MockBean
//	SubjectRepository subjectRepository;
//
//	@MockBean
//	SubjectChapterRepository subjectChapterRepository;
//
//	@MockBean
//	StudentAssignedCourseRepository studentAssignedCourseRepository;
//
//	@MockBean
//	VideoCipherConfiguration videoCipherConfiguration;
//	
//
//
//	@BeforeEach
//	public void setUp() throws Exception
//
//	{
//		// videoCipher
//
//		VideoCipherOTP vco = new VideoCipherOTP();
//		vco.setOtp("demo_OTP_here");
//		vco.setPlaybackInfo("playbackInfo");
//
//		List<VideoPoster> posters = new ArrayList<VideoPoster>();
//		VideoPoster vp = new VideoPoster();
//		vp.setHeight(300);
//		vp.setUrl("some video poster url for height 300");
//
//		VideoPoster vp1 = new VideoPoster();
//		vp1.setHeight(600);
//		vp1.setUrl("some video poster url for height 600");
//
//		VideoPoster vp2 = new VideoPoster();
//		vp2.setHeight(1200);
//		vp2.setUrl("some video poster url for height 1200");
//
//		posters.add(vp);
//		posters.add(vp1);
//		posters.add(vp2);
//
//		VideoCipherMeta vcm = new VideoCipherMeta();
//		vcm.setDescription("Some Description");
//		vcm.setDuration(300);
//		vcm.setPosters(posters);
//
//		Mockito.when(videoCipherConfiguration.getOTP(any(String.class))).thenReturn(vco);
//		Mockito.when(videoCipherConfiguration.getOTP(any(String.class), any(String.class))).thenReturn(vco);
//		//Mockito.when(videoCipherConfiguration.getVideoMetaData(any(String.class))).thenReturn(vcm);
//
//		// Initialize subject data
//		List<Subject> subList = new ArrayList<Subject>();
//		Subject sub1 = new Subject();
//		sub1.setSubjectName("Maths");
//		sub1.setIdSubject(1L);
//		Subject sub2 = new Subject();
//		sub2.setSubjectName("Social");
//		sub2.setIdSubject(2L);
//		Subject sub3 = new Subject();
//		sub3.setSubjectName("Science");
//		sub3.setIdSubject(3L);
//		Subject sub4 = new Subject();
//		sub3.setSubjectName("NA");
//		sub3.setIdSubject(4L);
//
//		subList.add(sub1);
//		subList.add(sub2);
//		subList.add(sub3);
//		subList.add(sub4);
//
//		Mockito.when(subjectRepository.findAll()).thenReturn(subList);
//		Mockito.when(subjectRepository.findByIdSubject(1L)).thenReturn(sub1);
//		Mockito.when(subjectRepository.findBySubjectName("NA")).thenReturn(sub4);
//
//		List<SubjectChapter> chpaList = new ArrayList<SubjectChapter>();
//		List<SubjectChapter> chpaList2 = new ArrayList<SubjectChapter>();
//
//		SubjectChapter sc1 = new SubjectChapter();
//		sc1.setIdSubject(1L);
//		sc1.setIdSubjectChapter(1L);
//		sc1.setPlaylistLink("playlist");
//		sc1.setChapterName("REAL NUMBERS");
//
//		SubjectChapter sc2 = new SubjectChapter();
//		sc2.setIdSubject(1L);
//		sc2.setIdSubjectChapter(1L);
//		sc2.setPlaylistLink("playlist");
//		sc2.setChapterName("Polynomials");
//
//		SubjectChapter sc3 = new SubjectChapter();
//		sc3.setIdSubject(1L);
//		sc3.setIdSubjectChapter(1L);
//		sc3.setPlaylistLink("playlist");
//		sc3.setChapterName("NA");
//
//		chpaList.add(sc1);
//		chpaList.add(sc2);
//		chpaList.add(sc3);
//		chpaList2.add(sc3);
//
//		Mockito.when(subjectChapterRepository.findByIdSubject(4L)).thenReturn(chpaList2);
//
//		// product
//
//		Product p1 = new Product();
//		p1.setAgeGroup("14-16");
//		p1.setAnnualSubscrAmt(30000f);
//		p1.setIdClassStandard(1L);
//		p1.setIdProduct(1L);
//		p1.setIdProductGroup(1L);
//		p1.setIdSubject(1L);
//		p1.setMonthlySubcrAmt(3000f);
//		p1.setProductCd("BATCH_1_MATHS_10");
//		p1.setProductName("Batch of 1 Student, Maths- Class 10");
//		p1.setQtrSubscrAmt(10000f);
//		p1.setBatchSize(1);
//		p1.setTotalVideoCount(0);
//
//		Mockito.when(productRepository.findByIdProductAndActiveFlag(any(Long.class),Boolean.TRUE)).thenReturn(p1);
//
//		List<OfflineVideoCourse> videoList = new ArrayList<>();
//
//		OfflineVideoCourse ovc = new OfflineVideoCourse();
//		ovc.setIdOfflineVideoCourse(1L);
//		ovc.setIdProduct(2L);
//		ovc.setIdSubject(1L);
//		ovc.setIdSubjectChapter(1L);
//		ovc.setTopic("Adding Two Numbers");
//		ovc.setVideo1Link("Video_Link_1");
//		ovc.setVideo2Link("Video_Link_2");
//		ovc.setVideo3Link("Video_Link_3");
//		ovc.setVideo4Link("Video_Link_4");
//		ovc.setVideo5Link("Video_Link_5");
//		ovc.setVideoDescription("Some Description");
//		ovc.setVideoDuration(300);
//		ovc.setVideoEnLink("Video_Link_EN");
//		ovc.setVideoHnLink("Video_Link_HN");
//		ovc.setVideoKnLink("Video_Link_KN");
//		ovc.setVideoMhLink("Video_Link_MH");
//		ovc.setVideoMlLink("Video_Link_ML");
//		ovc.setVideoOtp("demo_OTP_here");
//		ovc.setVideoPoster1Location("some video poster url for height 300");
//		ovc.setVideoPoster2Location("some video poster url for height 600");
//		ovc.setVideoPoster3Location("some video poster url for height 1200");
//		ovc.setVideoSeqNumber(2);
//		ovc.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
//		ovc.setVideoTlLink("Video_Link_TL");
//		ovc.setVideoTmLink("Video_Link_TM");
//
//		OfflineVideoCourse ovc1 = new OfflineVideoCourse();
//		ovc1.setIdOfflineVideoCourse(2L);
//		ovc1.setIdProduct(2L);
//		ovc1.setIdSubject(1L);
//		ovc1.setIdSubjectChapter(1L);
//		ovc1.setTopic("Subtracting two Numbers");
//		ovc1.setVideo1Link("Video_Link_1");
//		ovc1.setVideo2Link("Video_Link_2");
//		ovc1.setVideo3Link("Video_Link_3");
//		ovc1.setVideo4Link("Video_Link_4");
//		ovc1.setVideo5Link("Video_Link_5");
//		ovc1.setVideoDescription("Some Description");
//		ovc1.setVideoDuration(300);
//		ovc1.setVideoEnLink("Video_Link_EN");
//		ovc1.setVideoHnLink("Video_Link_HN");
//		ovc1.setVideoKnLink("Video_Link_KN");
//		ovc1.setVideoMhLink("Video_Link_MH");
//		ovc1.setVideoMlLink("Video_Link_ML");
//		ovc1.setVideoOtp("demo_OTP_here");
//		ovc1.setVideoPoster1Location("some video poster url for height 300");
//		ovc1.setVideoPoster2Location("some video poster url for height 600");
//		ovc1.setVideoPoster3Location("some video poster url for height 1200");
//		ovc1.setVideoSeqNumber(3);
//		ovc1.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
//		ovc1.setVideoTlLink("Video_Link_TL");
//		ovc1.setVideoTmLink("Video_Link_TM");
//
//		OfflineVideoCourse ovc2 = new OfflineVideoCourse();
//
//		ovc2.setIdOfflineVideoCourse(3L);
//		ovc2.setIdProduct(2L);
//		ovc2.setIdSubject(1L);
//		ovc2.setIdSubjectChapter(1L);
//		ovc2.setTopic("Divide Two Numbers");
//		ovc2.setVideo1Link("Video_Link_1");
//		ovc2.setVideo2Link("Video_Link_2");
//		ovc2.setVideo3Link("Video_Link_3");
//		ovc2.setVideo4Link("Video_Link_4");
//		ovc2.setVideo5Link("Video_Link_5");
//		ovc2.setVideoDescription("Some Description");
//		ovc2.setVideoDuration(300);
//		ovc2.setVideoEnLink("Video_Link_EN");
//		ovc2.setVideoHnLink("Video_Link_HN");
//		ovc2.setVideoKnLink("Video_Link_KN");
//		ovc2.setVideoMhLink("Video_Link_MH");
//		ovc2.setVideoMlLink("Video_Link_ML");
//		ovc2.setVideoOtp("demo_OTP_here");
//		ovc2.setVideoPoster1Location("some video poster url for height 300");
//		ovc2.setVideoPoster2Location("some video poster url for height 600");
//		ovc2.setVideoPoster3Location("some video poster url for height 1200");
//		ovc2.setVideoSeqNumber(3);
//		ovc2.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
//		ovc2.setVideoTlLink("Video_Link_TL");
//		ovc2.setVideoTmLink("Video_Link_TM");
//
//		videoList.add(ovc);
//		videoList.add(ovc1);
//		videoList.add(ovc2);
//
//		Mockito.when(offlineVideoCourseRepository.save(any(OfflineVideoCourse.class))).thenReturn(ovc);
//
//		Mockito.when(offlineVideoCourseRepository.findTop10ByOrderByIdOfflineVideoCourseDesc()).thenReturn(videoList);
//
//		Mockito.when(offlineVideoCourseRepository.findTop10ByOrderByIdOfflineVideoCourseDesc()).thenReturn(videoList);
//		
//		Mockito.when(productRepository.save(any(Product.class))).thenReturn(p1);
//		
//	}
//
//	// @Test
//	void testGetAllSampleVideos() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@SuppressWarnings("rawtypes")
////	@Test
//	void testCreateOfflineVideoRecord() {
//
//		CreateVideoRequestDTO request = new CreateVideoRequestDTO();
//
//		request.setDescription("Some Description");
//		request.setIdClassStandard(1L);
//		request.setIdProduct(2L);
//		request.setIdSubject(1L);
//		request.setIdSubjectChapter(1L);
//		request.setTopic("Adding Two Numbers");
//		request.setVideoEnLink("Some_OTP");
//		Set<VideoLinkDTO> videoIds = new HashSet<VideoLinkDTO>();
//
//		VideoLinkDTO vld = new VideoLinkDTO();
//		vld.setLanguage("kannada");
//		vld.setLink("Video_Link_KN");
//
//		VideoLinkDTO vld1 = new VideoLinkDTO();
//		vld1.setLanguage("tamil");
//		vld1.setLink("Video_Link_TM");
//
//		VideoLinkDTO vld2 = new VideoLinkDTO();
//		vld2.setLanguage("telugu");
//		vld2.setLink("Video_Link_TL");
//
//		VideoLinkDTO vld3 = new VideoLinkDTO();
//		vld3.setLanguage("hindi");
//		vld3.setLink("Video_Link_HN");
//
//		VideoLinkDTO vld4 = new VideoLinkDTO();
//		vld4.setLanguage("malayalam");
//		vld4.setLink("Video_Link_ML");
//
//		VideoLinkDTO vld5 = new VideoLinkDTO();
//		vld5.setLanguage("marathi");
//		vld5.setLink("Video_Link_MH");
//
//		videoIds.add(vld);
//		videoIds.add(vld1);
//		videoIds.add(vld2);
//		videoIds.add(vld3);
//		videoIds.add(vld4);
//		videoIds.add(vld5);
//
//		request.setVideoLinks(videoIds);
//
//		Document doc = offlineCourseService.createOfflineVideoRecord(request);
//
//		assertEquals(200, doc.getStatusCode());
//		OfflineVideoCourse ovc = (OfflineVideoCourse) doc.getData();
//		assertEquals(1L, ovc.getIdOfflineVideoCourse());
//		assertEquals(2L, ovc.getIdProduct());
//		assertEquals(1L, ovc.getIdSubject());
//		assertEquals(1L, ovc.getIdSubjectChapter());
//		assertEquals("Adding Two Numbers", ovc.getTopic());
//		assertEquals("Video_Link_1", ovc.getVideo1Link());
//		assertEquals("Video_Link_2", ovc.getVideo2Link());
//		assertEquals("Video_Link_3", ovc.getVideo3Link());
//		assertEquals("Video_Link_4", ovc.getVideo4Link());
//		assertEquals("Video_Link_5", ovc.getVideo5Link());
//		assertEquals("Some Description", ovc.getVideoDescription());
//		assertEquals(300, ovc.getVideoDuration());
//		assertEquals("Video_Link_EN", ovc.getVideoEnLink());
//		assertEquals("Video_Link_HN", ovc.getVideoHnLink());
//		assertEquals("Video_Link_KN", ovc.getVideoKnLink());
//		assertEquals("Video_Link_MH", ovc.getVideoMhLink());
//		assertEquals("Video_Link_ML", ovc.getVideoMlLink());
//		assertEquals("demo_OTP_here", ovc.getVideoOtp());
//		assertEquals("some video poster url for height 300", ovc.getVideoPoster1Location());
//		assertEquals("some video poster url for height 600", ovc.getVideoPoster2Location());
//		assertEquals("some video poster url for height 1200", ovc.getVideoPoster3Location());
//		assertEquals(2, ovc.getVideoSeqNumber());
//		assertEquals("9ae8bbe8dd964ddc9bdb932cca1cb59a", ovc.getVideoTheme());
//		assertEquals("Video_Link_TL", ovc.getVideoTlLink());
//		assertEquals("Video_Link_TM", ovc.getVideoTmLink());
//
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetTop10VideoRecords() {
//
//		Document doc = offlineCourseService.getTop10VideoRecords();
//
//		assertEquals(200, doc.getStatusCode());
//		List<OfflineVideoCourse> ovcList = (List<OfflineVideoCourse>) doc.getData();
//		assertEquals(3, ovcList.size());
//		assertEquals(1L, ovcList.get(0).getIdOfflineVideoCourse());
//		assertEquals(2L, ovcList.get(0).getIdProduct());
//		assertEquals(1L, ovcList.get(0).getIdSubject());
//		assertEquals(1L, ovcList.get(0).getIdSubjectChapter());
//		assertEquals("Adding Two Numbers", ovcList.get(0).getTopic());
//		assertEquals("Video_Link_1", ovcList.get(0).getVideo1Link());
//		assertEquals("Video_Link_2", ovcList.get(0).getVideo2Link());
//		assertEquals("Video_Link_3", ovcList.get(0).getVideo3Link());
//		assertEquals("Video_Link_4", ovcList.get(0).getVideo4Link());
//		assertEquals("Video_Link_5", ovcList.get(0).getVideo5Link());
//		assertEquals("Some Description", ovcList.get(0).getVideoDescription());
//		assertEquals(300, ovcList.get(0).getVideoDuration());
//		assertEquals("Video_Link_EN", ovcList.get(0).getVideoEnLink());
//		assertEquals("Video_Link_HN", ovcList.get(0).getVideoHnLink());
//		assertEquals("Video_Link_KN", ovcList.get(0).getVideoKnLink());
//		assertEquals("Video_Link_MH", ovcList.get(0).getVideoMhLink());
//		assertEquals("Video_Link_ML", ovcList.get(0).getVideoMlLink());
//		assertEquals("demo_OTP_here", ovcList.get(0).getVideoOtp());
//		assertEquals("some video poster url for height 300", ovcList.get(0).getVideoPoster1Location());
//		assertEquals("some video poster url for height 600", ovcList.get(0).getVideoPoster2Location());
//		assertEquals("some video poster url for height 1200", ovcList.get(0).getVideoPoster3Location());
//		assertEquals(2, ovcList.get(0).getVideoSeqNumber());
//		assertEquals("9ae8bbe8dd964ddc9bdb932cca1cb59a", ovcList.get(0).getVideoTheme());
//		assertEquals("Video_Link_TL", ovcList.get(0).getVideoTlLink());
//		assertEquals("Video_Link_TM", ovcList.get(0).getVideoTmLink());
//
//		assertEquals(3L, ovcList.get(2).getIdOfflineVideoCourse());
//		assertEquals(2L, ovcList.get(2).getIdProduct());
//		assertEquals(1L, ovcList.get(2).getIdSubject());
//		assertEquals(1L, ovcList.get(2).getIdSubjectChapter());
//		assertEquals("Divide Two Numbers", ovcList.get(2).getTopic());
//		assertEquals("Video_Link_EN", ovcList.get(2).getVideoEnLink());
//		assertEquals("Video_Link_HN", ovcList.get(2).getVideoHnLink());
//		assertEquals("Video_Link_KN", ovcList.get(2).getVideoKnLink());
//		assertEquals("Video_Link_MH", ovcList.get(2).getVideoMhLink());
//		assertEquals("Video_Link_ML", ovcList.get(2).getVideoMlLink());
//		assertEquals("demo_OTP_here", ovcList.get(2).getVideoOtp());
//		assertEquals("Video_Link_1", ovcList.get(2).getVideo1Link());
//		assertEquals("Video_Link_2", ovcList.get(2).getVideo2Link());
//		assertEquals("Video_Link_3", ovcList.get(2).getVideo3Link());
//		assertEquals("Video_Link_4", ovcList.get(2).getVideo4Link());
//		assertEquals("Video_Link_5", ovcList.get(2).getVideo5Link());
//		assertEquals("Video_Link_TL", ovcList.get(2).getVideoTlLink());
//		assertEquals("Video_Link_TM", ovcList.get(2).getVideoTmLink());
//		assertEquals("Some Description", ovcList.get(2).getVideoDescription());
//		assertEquals(300, ovcList.get(2).getVideoDuration());
//		assertEquals("some video poster url for height 300", ovcList.get(2).getVideoPoster1Location());
//		assertEquals("some video poster url for height 600", ovcList.get(2).getVideoPoster2Location());
//		assertEquals("some video poster url for height 1200", ovcList.get(2).getVideoPoster3Location());
//		assertEquals(3, ovcList.get(2).getVideoSeqNumber());
//		assertEquals("9ae8bbe8dd964ddc9bdb932cca1cb59a", ovcList.get(2).getVideoTheme());
//
//	}
//
//	// @Test
//	void testGetVideoRecordByIdProduct() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Test
//	void testGetVideoDataByVideoId() {
//
//		Document doc = offlineCourseService.getVideoDataByVideoId("Some String");
//
//		VideoCipherOTP vco = (VideoCipherOTP) doc.getData();
//
//		assertEquals(200, doc.getStatusCode());
//		assertEquals("demo_OTP_here", vco.getOtp());
//		assertEquals("playbackInfo", vco.getPlaybackInfo());
//
//	}
//
//}
