//package co.vistafoundation.vlearning.liveclass.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import co.vistafoundation.vlearning.auth.model.User;
//import co.vistafoundation.vlearning.batch.dto.BatchAttendanceDTO;
//import co.vistafoundation.vlearning.batch.dto.BatchResonseDTO;
//import co.vistafoundation.vlearning.batch.model.Batch;
//import co.vistafoundation.vlearning.common.response.Document;
//import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;
//import co.vistafoundation.vlearning.liveclass.dto.LiveClassQuestionAnswerDTO;
//import co.vistafoundation.vlearning.liveclass.dto.LiveClassQuestionAnswerRequestDTO;
//import co.vistafoundation.vlearning.liveclass.model.LiveClass;
//import co.vistafoundation.vlearning.liveclass.model.LiveClassQndA;
//import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;
//import co.vistafoundation.vlearning.liveclass.model.UserLiveClassAttended;
//import co.vistafoundation.vlearning.liveclass.repository.LiveClassQndARepository;
//import co.vistafoundation.vlearning.liveclass.repository.LiveClassQuestionRepository;
//import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;
//import co.vistafoundation.vlearning.liveclass.repository.UserLiveClassAttendedRepository;
//import co.vistafoundation.vlearning.liveclass.service.LiveClassService;
//import co.vistafoundation.vlearning.user.model.Teacher;
//import co.vistafoundation.vlearning.user.repository.TeacherRepository;
//import co.vistafoundation.vlearning.websocket.dto.ChatMessage;
//
//@SpringBootTest
//public class LiveClassServiceImplTest {
//
//	@Autowired
//	private LiveClassService liveClassService;
//	
//	@MockBean
//	private LiveClassRepository liveClassRepository;
//	
//	@MockBean
//	private TeacherRepository teacherRepository;
//	
//	@MockBean
//	private LiveClassQuestionRepository liveClassQuestionRepository;
//	
//	@MockBean
//	private LiveClassQndARepository liveClassQndARepository;
//	
//	@MockBean
//	private UserLiveClassAttendedRepository userLiveClassAttendedRepository;
//	
//	@SuppressWarnings("deprecation")
//	@BeforeEach
//	public void setUp() throws ParseException {
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date1 = sdf.format(new Date());
//		Date date = new Date();
//		
//		// Initialize LiveClass data
//		LiveClass liveClass = new LiveClass();
//		liveClass.setIdLiveClass(1L);
//		liveClass.setIdTeacher(1L);
//		liveClass.setLiveClassHeading("Demo1");
//		liveClass.setLiveClassDesc("DemoSession1");
//		liveClass.setLiveClassURL("https://www.youtube.com/embed/YczRa9FwboY");
//		liveClass.setIdLiveClassCategory(1L);
//		liveClass.setClassDate(LocalDate.now());
//		liveClass.setCurrentRunningFlag(Boolean.FALSE);
//		liveClass.setLiveCompletionFlag(Boolean.FALSE);
//		liveClass.setIdYoutubeMaster(1L);
//		liveClass.setFromTime(LocalTime.parse("01:00:00"));
//		liveClass.setToTime(LocalTime.parse("02:00:00"));
//		liveClass.setActualClassStartDate(new Date(2021, 4, 1, 0, 0, 0).toInstant());
//		liveClass.setActualClassEndDate(new Date(2021, 4, 1, 0, 0, 0).toInstant());
//		liveClass.setIdClassStandard(1L);
//		
//		List<LiveClass> liveClassList = new ArrayList<LiveClass>();
//		liveClassList.add(liveClass);
//		
//		//Initialize User Live Class Attended
//		UserLiveClassAttended classAttended =new UserLiveClassAttended();
//		classAttended.setIdUserLiveClassAttended(1L);
//		classAttended.setIdLiveClass(1L);
//		classAttended.setUserSurId(1L);
//		
//		Mockito.when(liveClassRepository.findByIdLiveClass(1L)).thenReturn(liveClass);
//		
//		Mockito.when(liveClassRepository.findByIdTeacher(1L)).thenReturn(liveClassList);
//		
////		Mockito.when(liveClassRepository.findByLiveCompletionFlagAndIdLiveClassCategory(Boolean.TRUE, 1L)).thenReturn(liveClassList);
//		
//		Mockito.when(liveClassRepository.findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndClassDate(1L,Boolean.FALSE,Boolean.TRUE,LocalDate.now())).thenReturn(liveClass);
//		
//		Mockito.when(liveClassRepository.findByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDate(1L,1L,Boolean.FALSE,Boolean.TRUE,LocalDate.now())).thenReturn(liveClassList);
//		
//		Mockito.when(liveClassRepository.findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDate(1L,1L,Boolean.FALSE, Boolean.TRUE, LocalDate.now()));
//		
//		Mockito.when(liveClassRepository.findByIdClassStandardAndIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlag(1L,1L,LocalDate.now(), LocalTime.parse("00:00"), Boolean.FALSE)).thenReturn(liveClassList);
//		
//		Mockito.when(liveClassRepository.findByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDate(1L,Boolean.FALSE,Boolean.TRUE,LocalDate.now())).thenReturn(liveClassList);
//		
//		Mockito.when(liveClassRepository.findByIdLiveClassCategoryAndLiveCompletionFlagAndClassDateAndFromTimeAfter(1L,Boolean.FALSE, LocalDate.now(), LocalTime.now())).thenReturn(liveClassList);
//		
//		Mockito.when(liveClassRepository.findByIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlag(1L,LocalDate.now(), LocalTime.parse("00:00"), Boolean.FALSE)).thenReturn(liveClassList);
//		
//		Mockito.when(userLiveClassAttendedRepository.findByIdLiveClassAndUserSurId(1L,1L)).thenReturn(classAttended);
//		
//		Mockito.when(userLiveClassAttendedRepository.save(any(UserLiveClassAttended.class))).thenReturn(classAttended);
//		
//		
//		User user = new User();
//		user.setUserSurId(1L);
//		user.setFirstName("Naveen");
//		user.setLastName("Kumar");
//		
//		// Initialize Teacher data
//		Teacher teacher = new Teacher();
//		teacher.setIdTeacher(1L);
//		teacher.setUser(user);
//		teacher.setActiveFlag(Boolean.TRUE);
//		teacher.setIdWebexPool(1L);
//		teacher.setTeacherDesc("Description");
//		teacher.setExpLevel("Expert");
//		teacher.setEmailId("naveen9036@gmail.com");
//		teacher.setTeacherName("Naveen Kumar");
//		teacher.setPrimarySubject("MATHS");
//		teacher.setTeacherImage("");
//		teacher.setRating(5);
//		teacher.setTeacherAddress("Munishwara Temple Street");
//		teacher.setJoinedDate(new Date());
//		teacher.setDisplayInHomepageFlag(Boolean.TRUE);
//		teacher.setTeacherHeader("Professional in Maths");
//		teacher.setIntroVideo("");
//		teacher.setCategory("ACADEMIC");
//				
//		Mockito.when(teacherRepository.findByIdTeacher(1L)).thenReturn(teacher);
//		
//		
//		List<LiveClassQuestion> questionsList = new ArrayList<LiveClassQuestion>();
//		LiveClassQuestion question = new LiveClassQuestion();
//		question.setIdLiveClassQuestion(1L);
//		question.setIdLiveClass(1L);
//		question.setUserSurId(1L);
//		question.setQuestionText("Name The Capital Of India");
//		
//		questionsList.add(question);
//		
//		//save Live Class Question
//		Mockito.when(liveClassQuestionRepository.save(any(LiveClassQuestion.class))).thenReturn(question);
//		
//		Mockito.when(liveClassQuestionRepository.findByIdLiveClass(1L)).thenReturn(questionsList);
//		
//		
//		
//		List<LiveClassQndA> list = new ArrayList<LiveClassQndA>();
//		// Initialize LiveClassQndA data
//		LiveClassQndA QndA = new LiveClassQndA();
//		QndA.setIdLiveClassQAndA(1L);
//		QndA.setIdLiveClass(1L);
//		QndA.setUserSurId(1L);
//		QndA.setQuestionText("Name The Capital Of India");
//		QndA.setAnswerText("Delhi");
//		
//		list.add(QndA);
//		
//		//save Live Class Question And Answer
//		Mockito.when(liveClassQndARepository.save(any(LiveClassQndA.class))).thenReturn(QndA);
//		
//		Mockito.when(liveClassQndARepository.findByIdLiveClass(1L)).thenReturn(list);
//
//		
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveClassDetailsByIdLiveClass() {
//		Document doc = liveClassService.getLiveClassDetailsByIdLiveClass(1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		LiveClassDto liveClassDto = (LiveClassDto) doc.getData();
//		assertEquals(1,liveClassDto.getIdLiveClass());
//		assertEquals("Demo1",liveClassDto.getLiveClassHeading());
//		assertEquals("DemoSession1",liveClassDto.getLiveClassDescription());
//		assertEquals(LocalDate.now(),liveClassDto.getClassDate());
//		assertEquals(LocalTime.parse("01:00:00"),liveClassDto.getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"),liveClassDto.getToTime());
//		assertEquals(1,liveClassDto.getIdYoutubeMaster());
//		assertEquals("Naveen Kumar",liveClassDto.getTeacherName());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveClassListByIdTeacher() {
//		Document doc = liveClassService.getLiveClassListByIdTeacher(1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		List<LiveClassDto> classList = (List<LiveClassDto>) doc.getData();
//		assertEquals(1, classList.size());
//		assertEquals(1,classList.get(0).getIdLiveClass());
//		assertEquals("Naveen Kumar",classList.get(0).getTeacherName());
//		assertEquals("Demo1",classList.get(0).getLiveClassHeading());
//		assertEquals("DemoSession1",classList.get(0).getLiveClassDescription());
//		assertEquals(LocalDate.now(),classList.get(0).getClassDate());
//		assertEquals(1,classList.get(0).getIdYoutubeMaster());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testSaveLiveClassQuestion() {
//		//Set Live Class Question Details
//		LiveClassQuestion question = new LiveClassQuestion();
//		question.setIdLiveClassQuestion(1L);
//		question.setIdLiveClass(1L);
//		question.setUserSurId(1L);
//		question.setQuestionText("Name The Capital Of India");
//		
//		Document doc = liveClassService.saveLiveClassQuestion(question);
//		assertEquals(200, doc.getStatusCode());
//		
//		LiveClassQuestion liveClassQuestion = (LiveClassQuestion) doc.getData();
//		assertEquals(1, liveClassQuestion.getIdLiveClassQuestion());
//		assertEquals(1, liveClassQuestion.getIdLiveClass());
//		assertEquals(1, liveClassQuestion.getUserSurId());
//		assertEquals("Name The Capital Of India", liveClassQuestion.getQuestionText());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetAllQuestions() {
//		Document doc = liveClassService.getAllQuestions(1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		List<ChatMessage> questionsList = (List<ChatMessage>) doc.getData();
//		assertEquals(1, questionsList.size());
//		assertEquals(1, questionsList.get(0).getIdLiveClass());
//		assertEquals(1, questionsList.get(0).getSenderIdVlUser());
//		assertEquals("Name The Capital Of India", questionsList.get(0).getQuestion());	
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testSaveLiveClassQuestionAndAnswer() {
//		
//		//Set Live Class Question And Answer
//		LiveClassQuestionAnswerRequestDTO dto =new LiveClassQuestionAnswerRequestDTO();
//		dto.setIdLiveClass(1L);
//		dto.setUserSurId(1L);
//		dto.setQuestion("Name The Capital Of India");
//		dto.setAnswer("Delhi");
//		
//		Document doc = liveClassService.saveLiveClassQuestionAndAnswer(dto);
//		assertEquals(200, doc.getStatusCode());
//		
//		LiveClassQndA liveClassQndA = (LiveClassQndA) doc.getData();
//		assertEquals(1, dto.getIdLiveClass());
//		assertEquals(1, dto.getUserSurId());
//		assertEquals("Name The Capital Of India", dto.getQuestion());
//		assertEquals("Delhi", dto.getAnswer());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveClassAllQuestionAndAnswer() {
//		Document doc = liveClassService.getLiveClassAllQuestionAndAnswer(1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		List<ChatMessage> QndAList = (List<ChatMessage>) doc.getData();
//		assertEquals(1, QndAList.size());
//		assertEquals("Name The Capital Of India", QndAList.get(0).getQuestion());
//		assertEquals("Delhi", QndAList.get(0).getAnswer());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveClass() {
//		Document doc = liveClassService.getLiveClass(1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		LiveClassDto liveClassDto =(LiveClassDto)  doc.getData();
//		assertEquals(1, liveClassDto.getIdLiveClass());
//		assertEquals(LocalDate.now(), liveClassDto.getClassDate());
//		assertEquals(1, liveClassDto.getIdTeacher());
//		assertEquals(LocalTime.parse("01:00:00"), liveClassDto.getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"), liveClassDto.getToTime());
//		assertEquals("Demo1", liveClassDto.getLiveClassHeading());
//		assertEquals("DemoSession1", liveClassDto.getLiveClassDescription());
//		assertEquals("https://www.youtube.com/embed/YczRa9FwboY", liveClassDto.getLiveClassURL());
//		assertEquals(1, liveClassDto.getIdYoutubeMaster());
//		
//		
//	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveAcademicClasses() {
//		Document doc = liveClassService.getLiveAcademicClasses(1L,1L);
//		assertEquals(200, doc.getStatusCode());
//		
//		List<LiveClassDto> academicClassList= (List<LiveClassDto>) doc.getData();
//		assertEquals(1, academicClassList.get(0).getIdLiveClass());
//		assertEquals(LocalDate.now(), academicClassList.get(0).getClassDate());
//		assertEquals(1, academicClassList.get(0).getIdTeacher());
//		assertEquals(LocalTime.parse("01:00:00"), academicClassList.get(0).getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"), academicClassList.get(0).getToTime());
//		assertEquals("Demo1", academicClassList.get(0).getLiveClassHeading());
//		assertEquals("DemoSession1", academicClassList.get(0).getLiveClassDescription());
//		assertEquals("https://www.youtube.com/embed/YczRa9FwboY", academicClassList.get(0).getLiveClassURL());
//		assertEquals(1, academicClassList.get(0).getIdYoutubeMaster());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetUpcomingAcademicClasses() {
//		Document doc = liveClassService.getUpcomingAcademicClasses(1L,1L);
//		//assertEquals(200, doc.getStatusCode());
//		
//		List<LiveClassDto> upcomingClassList= (List<LiveClassDto>) doc.getData();
//		assertEquals(1, upcomingClassList.get(0).getIdLiveClass());
//		assertEquals(LocalDate.now(), upcomingClassList.get(0).getClassDate());
//		assertEquals(1, upcomingClassList.get(0).getIdTeacher());
//		assertEquals(LocalTime.parse("01:00:00"), upcomingClassList.get(0).getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"), upcomingClassList.get(0).getToTime());
//		assertEquals("Demo1", upcomingClassList.get(0).getLiveClassHeading());
//		assertEquals("DemoSession1", upcomingClassList.get(0).getLiveClassDescription());
//		assertEquals("https://www.youtube.com/embed/YczRa9FwboY", upcomingClassList.get(0).getLiveClassURL());
//		assertEquals(1, upcomingClassList.get(0).getIdYoutubeMaster());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetLiveExtraCurricularClasses() {
//		Document doc = liveClassService.getLiveExtraCurricularClasses(1L);
//		//assertEquals(200, doc.getStatusCode());
//		
//		List<LiveClassDto> academicClassList= (List<LiveClassDto>) doc.getData();
//		assertEquals(1, academicClassList.get(0).getIdLiveClass());
//		assertEquals(LocalDate.now(), academicClassList.get(0).getClassDate());
//		assertEquals(1, academicClassList.get(0).getIdTeacher());
//		assertEquals(LocalTime.parse("01:00:00"), academicClassList.get(0).getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"), academicClassList.get(0).getToTime());
//		assertEquals("Demo1", academicClassList.get(0).getLiveClassHeading());
//		assertEquals("DemoSession1", academicClassList.get(0).getLiveClassDescription());
//		assertEquals("https://www.youtube.com/embed/YczRa9FwboY", academicClassList.get(0).getLiveClassURL());
//		assertEquals(1, academicClassList.get(0).getIdYoutubeMaster());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testGetUpcomingExtraCurricular() {
//		Document doc = liveClassService.getUpcomingExtraCurricular(1L);
//		//assertEquals(200, doc.getStatusCode());
//		
//		List<LiveClassDto> upcomingExtraCurrList= (List<LiveClassDto>) doc.getData();
//		assertEquals(1, upcomingExtraCurrList.get(0).getIdLiveClass());
//		assertEquals(LocalDate.now(), upcomingExtraCurrList.get(0).getClassDate());
//		assertEquals(1, upcomingExtraCurrList.get(0).getIdTeacher());
//		assertEquals(LocalTime.parse("01:00:00"), upcomingExtraCurrList.get(0).getFromTime());
//		assertEquals(LocalTime.parse("02:00:00"), upcomingExtraCurrList.get(0).getToTime());
//		assertEquals("Demo1", upcomingExtraCurrList.get(0).getLiveClassHeading());
//		assertEquals("DemoSession1", upcomingExtraCurrList.get(0).getLiveClassDescription());
//		assertEquals("https://www.youtube.com/embed/YczRa9FwboY", upcomingExtraCurrList.get(0).getLiveClassURL());
//		assertEquals(1, upcomingExtraCurrList.get(0).getIdYoutubeMaster());
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
//	void testSaveLiveClassAttended() {
//		
//		//Set Live Class Attended 
//		UserLiveClassAttended attended =new UserLiveClassAttended();
//		attended.setIdLiveClass(1L);
//		attended.setUserSurId(1L);
//		
//		
//		Document doc = liveClassService.saveLiveClassAttended(attended);
//		assertEquals(200, doc.getStatusCode());
//		
//		UserLiveClassAttended liveClassAttended = (UserLiveClassAttended) doc.getData();
//		assertEquals(1, liveClassAttended.getIdLiveClass());
//		assertEquals(1, liveClassAttended.getUserSurId());
//	}
//	
//	
//	
//}
//
