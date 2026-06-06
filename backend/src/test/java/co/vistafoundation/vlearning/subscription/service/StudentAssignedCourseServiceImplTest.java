/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

/**
 * @author Naveen Kumar
 *
 */

@SpringBootTest
class StudentAssignedCourseServiceImplTest {

	@Autowired
	StudentAssignedCourseService studentAssignedCourseService;

	@MockBean
	StudentAssignedCourseRepository studentAssignedCourseRepository;

	@MockBean
	VideoCipherConfiguration videoCipherConfiguration;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		StudentAssignedCourse sac = new StudentAssignedCourse();
		sac.setChapterName("chapterName");
		sac.setCompleteFlag(false);
		sac.setIdOfflineVideoCourse(1L);
		sac.setIdStudentAssignedCourse(1L);
		sac.setIdStudentSubscription(1L);
		sac.setIdSubject(1L);
		sac.setIdSubjectChapter(1L);
		sac.setLastAccessedDate(formatter.parse(formatter.format(new Date())));
		sac.setPctComplete("0");
		sac.setSubjectName("subjectName");
		sac.setTopic("someTopic");
		sac.setVideo1Link("Video_Link_1");
		sac.setVideo2Link("Video_Link_2");
		sac.setVideo3Link("Video_Link_3");
		sac.setVideo4Link("Video_Link_4");
		sac.setVideo5Link("Video_Link_5");
		sac.setVideoCoverageDuration(0);
		sac.setVideoDescription("Some Description");
		sac.setVideoDuration(300);
		sac.setVideoEnLink("Video_Link_EN");
		sac.setVideoHnLink("Video_Link_HN");
		sac.setVideoKnLink("Video_Link_KN");
		sac.setVideoMhLink("Video_Link_MH");
		sac.setVideoMlLink("Video_Link_ML");
		sac.setVideoOtp("demo_OTP_here");
		sac.setVideoPoster1Location("some video poster url for height 300");
		sac.setVideoPoster2Location("some video poster url for height 600");
		sac.setVideoPoster3Location("some video poster url for height 1200");
		sac.setVideoSeqNumber(2);
		sac.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
		sac.setVideoTlLink("Video_Link_TL");
		sac.setVideoTmLink("Video_Link_TM");

		StudentAssignedCourse sac2 = new StudentAssignedCourse();
		sac2.setChapterName("chapterName");
		sac2.setCompleteFlag(false);
		sac2.setIdOfflineVideoCourse(1L);
		sac2.setIdStudentAssignedCourse(1L);
		sac2.setIdStudentSubscription(1L);
		sac2.setIdSubject(1L);
		sac2.setIdSubjectChapter(1L);
		sac2.setLastAccessedDate(formatter.parse(formatter.format(new Date())));
		sac2.setPctComplete("33.33%");
		sac2.setSubjectName("subjectName");
		sac2.setTopic("someTopic");
		sac2.setVideo1Link("Video_Link_1");
		sac2.setVideo2Link("Video_Link_2");
		sac2.setVideo3Link("Video_Link_3");
		sac2.setVideo4Link("Video_Link_4");
		sac2.setVideo5Link("Video_Link_5");
		sac2.setVideoCoverageDuration(100);
		sac2.setVideoDescription("Some Description");
		sac2.setVideoDuration(300);
		sac2.setVideoEnLink("Video_Link_EN");
		sac2.setVideoHnLink("Video_Link_HN");
		sac2.setVideoKnLink("Video_Link_KN");
		sac2.setVideoMhLink("Video_Link_MH");
		sac2.setVideoMlLink("Video_Link_ML");
		sac2.setVideoOtp("demo_OTP_here");
		sac2.setVideoPoster1Location("some video poster url for height 300");
		sac2.setVideoPoster2Location("some video poster url for height 600");
		sac2.setVideoPoster3Location("some video poster url for height 1200");
		sac2.setVideoSeqNumber(2);
		sac2.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
		sac2.setVideoTlLink("Video_Link_TL");
		sac2.setVideoTmLink("Video_Link_TM");

		VideoCipherOTP vco = new VideoCipherOTP();
		vco.setOtp("demo_OTP_here");
		vco.setPlaybackInfo("playbackInfo");

		Mockito.when(studentAssignedCourseRepository.findByIdStudentAssignedCourse(1L)).thenReturn(sac);

		Mockito.when(studentAssignedCourseRepository.save(Mockito.any(StudentAssignedCourse.class))).thenReturn(sac2);

		Mockito.when(studentAssignedCourseRepository.save(Mockito.any(StudentAssignedCourse.class))).thenReturn(sac2);

		String requestBody ="{\"licenseRules\":{\"canPersist\":true,\"rentalDuration\":432000}}";
		Mockito.when(videoCipherConfiguration.getOTP("valid_video_id", requestBody)).thenReturn(vco);
	}

	/**
	 * Test method for
	 * {@link co.vistafoundation.vlearning.subscription.service.StudentAssignedCourseServiceImpl#updateStudentAssignedCourseById(java.lang.Long, java.lang.String)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	void testUpdateStudentAssignedCourseById() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Document<StudentAssignedCourse> doc = studentAssignedCourseService.updateStudentAssignedCourseById(1L, "100");
		assertEquals(200, doc.getStatusCode());
		StudentAssignedCourse obj = doc.getData();
		assertEquals(1L, obj.getIdStudentAssignedCourse());
		assertEquals("chapterName", obj.getChapterName());
		assertEquals(false, obj.getCompleteFlag());
		assertEquals(1L, obj.getIdOfflineVideoCourse());
		assertEquals(1L, obj.getIdStudentSubscription());
		assertEquals(1L, obj.getIdSubject());
		assertEquals(1L, obj.getIdSubjectChapter());
		assertEquals(formatter.parse(formatter.format(new Date())), obj.getLastAccessedDate());
		assertEquals("33.33%", obj.getPctComplete());
		assertEquals("subjectName", obj.getSubjectName());
		assertEquals("someTopic", obj.getTopic());
		assertEquals("Video_Link_1", obj.getVideo1Link());
		assertEquals("Video_Link_2", obj.getVideo2Link());
		assertEquals("Video_Link_3", obj.getVideo3Link());
		assertEquals("Video_Link_4", obj.getVideo4Link());
		assertEquals("Video_Link_5", obj.getVideo5Link());
		assertEquals(100, obj.getVideoCoverageDuration());
		assertEquals("Some Description", obj.getVideoDescription());
		assertEquals(300, obj.getVideoDuration());
		assertEquals("Video_Link_EN", obj.getVideoEnLink());
		assertEquals("Video_Link_HN", obj.getVideoHnLink());
		assertEquals("Video_Link_KN", obj.getVideoKnLink());
		assertEquals("Video_Link_MH", obj.getVideoMhLink());
		assertEquals("Video_Link_ML", obj.getVideoMlLink());
		assertEquals("Video_Link_TL", obj.getVideoTlLink());
		assertEquals("Video_Link_TM", obj.getVideoTmLink());
		assertEquals("some video poster url for height 300", obj.getVideoPoster1Location());
		assertEquals("some video poster url for height 600", obj.getVideoPoster2Location());
		assertEquals("some video poster url for height 1200", obj.getVideoPoster3Location());
		assertEquals(2, obj.getVideoSeqNumber());

		doc = null;
		obj = null;
		// passing invalid id
		doc = studentAssignedCourseService.updateStudentAssignedCourseById(2L, "100");
		assertEquals(500, doc.getStatusCode());
		assertEquals(null, doc.getData());
		assertEquals("Invalid Student Assigened Courses Id.", doc.getMessage());

		doc = null;
		obj = null;
		// passing invalid timedutration
		doc = studentAssignedCourseService.updateStudentAssignedCourseById(1L, "400");
		assertEquals(500, doc.getStatusCode());
		assertEquals(null, doc.getData());
		assertEquals("Video Duration Exceed the Limit!", doc.getMessage());

	}

	/**
	 * Test method for
	 * {@link co.vistafoundation.vlearning.subscription.service.StudentAssignedCourseServiceImpl#getDownloadVideoInfo(java.lang.String)}.
	 */
	void testGetDownloadVideoInfo() {

		Document<VideoCipherOTP> doc = studentAssignedCourseService.getDownloadVideoInfo("valid_video_id");
		assertEquals(201, doc.getStatusCode());
		assertEquals("demo_OTP_here", doc.getData().getOtp());
		assertEquals("playbackInfo", doc.getData().getPlaybackInfo());

		// passing invalid Video id
		doc = studentAssignedCourseService.getDownloadVideoInfo("Invalid_video_id");
		assertEquals(500, doc.getStatusCode());
		assertEquals(null, doc.getData());
		assertEquals("Invalid Video Id.", doc.getMessage());

	}

}
