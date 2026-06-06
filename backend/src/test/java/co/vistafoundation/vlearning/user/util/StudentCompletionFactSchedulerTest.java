package co.vistafoundation.vlearning.user.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.model.StudentCompletionFact;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.user.repository.StudentCompletionFactRepository;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(webDriverEnabled = false)

class StudentCompletionFactSchedulerTest {
	@SuppressWarnings("unused")
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	StudentAssignedCourseRepository studentAssignedCourseRepository;
	
	@MockBean
	StudentCompletionFactRepository studentCompletionFactRepository;
	 
	
	ObjectMapper om = new ObjectMapper();

	
	//@Test
	public void updateFactTest() throws JsonProcessingException, ParseException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<StudentAssignedCourse> studentAssignedCourse = new ArrayList<StudentAssignedCourse>();
		StudentAssignedCourse assignedCourse1 = new StudentAssignedCourse();
//		StudentAssignedCourse assignedCourse2 = new StudentAssignedCourse();
//		StudentAssignedCourse assignedCourse3 = new StudentAssignedCourse();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		assignedCourse1.setChapterName("SenseOrgans");
		assignedCourse1.setCompleteFlag(true);
		assignedCourse1.setIdOfflineVideoCourse(1L);
		assignedCourse1.setIdStudentAssignedCourse(1L);
		assignedCourse1.setIdStudentSubscription(1L);
		assignedCourse1.setIdSubject(1L);
		assignedCourse1.setIdSubjectChapter(1L);
		assignedCourse1.setLastAccessedDate(dateFormat.parse(dateFormat.format(new Date())));
		assignedCourse1.setPctComplete("50");
		assignedCourse1.setSubjectName("Biology");
		assignedCourse1.setTopic("Eye");
		assignedCourse1.setCreatedBy(1L);
		assignedCourse1.setUpdatedBy(1L);
		assignedCourse1.setVideoCoverageDuration(5);
		assignedCourse1.setVideoDuration(9);
		assignedCourse1.setVideoEnLink("sfgsfgsfsgsfsgsfsgfsfsgfsf");
		assignedCourse1.setVideoOtp("abcd");
		assignedCourse1.setVideoSeqNumber(1);
		assignedCourse1.setVideoTheme("scienceTheme");
		
		
		StudentCompletionFact studentCompletionFact = new StudentCompletionFact();
		
		studentCompletionFact.setAsOnDate(Instant.now());
		studentCompletionFact.setCompletionPCT(25L);
		studentCompletionFact.setCreatedBy(1L);
		studentCompletionFact.setIdStudentSubscription(1L);
		studentCompletionFact.setIdSubject(1L);
		studentCompletionFact.setUpdatedBy(1L);
		
		StudentCompletionFact studentCompletionFact2 = new StudentCompletionFact();
		
		studentCompletionFact2.setAsOnDate(Instant.now());
		studentCompletionFact2.setCompletionPCT(25L);
		studentCompletionFact2.setCreatedBy(1L);
		studentCompletionFact2.setIdStudentSubscription(1L);
		studentCompletionFact2.setIdSubject(1L);
		studentCompletionFact2.setUpdatedBy(1L);
		studentCompletionFact2.setIdStudentCompletionFact(1L);
//		String jsonrequest = om.writeValueAsString(studentCompletionFact);
		Mockito.when(studentAssignedCourseRepository
				.findByIdStudentSubscription(1L)).thenReturn(studentAssignedCourse);
		
		assertEquals("SenseOrgans", assignedCourse1.getChapterName());
		assertEquals(true, assignedCourse1.getCompleteFlag());
		assertEquals(1L,assignedCourse1.getIdOfflineVideoCourse());
		assertEquals(1L,assignedCourse1.getIdStudentAssignedCourse());
		assertEquals(1L,assignedCourse1.getIdStudentSubscription());
		assertEquals(1L,assignedCourse1.getIdSubject());
		assertEquals(1L, assignedCourse1.getIdSubjectChapter());
		assertEquals(dateFormat.parse(dateFormat.format(new Date())), assignedCourse1.getLastAccessedDate());
		assertEquals("50", assignedCourse1.getPctComplete());
		assertEquals("Biology",assignedCourse1.getSubjectName());
		assertEquals("Eye",assignedCourse1.getTopic());
		assertEquals(1L, assignedCourse1.getUpdatedBy());     
		assertEquals(1L,assignedCourse1.getCreatedBy());
		assertEquals(5,assignedCourse1.getVideoCoverageDuration());
		assertEquals(9, assignedCourse1.getVideoDuration());
		assertEquals("sfgsfgsfsgsfsgsfsgfsfsgfsf", assignedCourse1.getVideoEnLink());
		assertEquals("abcd", assignedCourse1.getVideoOtp());
		assertEquals("scienceTheme", assignedCourse1.getVideoTheme());
		assertEquals(1, assignedCourse1.getVideoSeqNumber());
		
		
		Mockito.when(studentCompletionFactRepository.save(studentCompletionFact)).thenReturn(studentCompletionFact2);
		
		assertEquals(dateFormat.parse(dateFormat.format(new Date())),studentCompletionFact.getAsOnDate());
		assertEquals(25L,studentCompletionFact2.getCompletionPCT());
		assertEquals(1L,studentCompletionFact2.getCreatedBy());
		assertEquals(1L,studentCompletionFact2.getIdStudentCompletionFact());
		assertEquals(1L,studentCompletionFact2.getIdSubject());
		assertEquals(1L,studentCompletionFact2.getUpdatedBy());
		assertEquals(1L,studentCompletionFact2.getIdStudentSubscription());
		
	}
	

}
