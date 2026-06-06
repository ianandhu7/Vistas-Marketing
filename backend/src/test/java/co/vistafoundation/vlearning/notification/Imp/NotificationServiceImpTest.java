package co.vistafoundation.vlearning.notification.Imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.notification.dto.NoificationDTO;
import co.vistafoundation.vlearning.notification.dto.StudentsDto;
import co.vistafoundation.vlearning.notification.model.Notification;
import co.vistafoundation.vlearning.notification.repository.NotificationRepository;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.ParentRepository;

/**
 * Meghana
 * **/

@SpringBootTest
class NotificationServiceImpTest {
	
	@MockBean
	NotificationRepository notificationRepository;
	
	@MockBean
	ParentRepository parentRepository;
	
	@MockBean
	UserRepository userRepository;
	
	@Autowired
	NotificationService notificationService;
	
	
	@BeforeEach
	public void setUp() throws ParseException {		
		
		Notification notificationsave = new Notification();
		notificationsave.setFromUserId(4L);
		notificationsave.setToUserId(2L);
		notificationsave.setHeader("Tittle");
		notificationsave.setIdBatch(1L);
		notificationsave.setMessage("Message");
		notificationsave.setNotificationDate(Instant.now());
		notificationsave.setReadStatus(false);
		notificationsave.setIdNotification(1L);
		
		Mockito.when(notificationRepository.save(any(Notification.class))).thenReturn(notificationsave);
		
		Mockito.when(notificationRepository.findByIdNotification(2L)).thenReturn(notificationsave);	
		
		
		List<Notification> NotiList = new ArrayList<Notification>();
		
		Notification notification = new Notification();
		notification.setFromUserId(4L);
		notification.setToUserId(2L);
		notification.setHeader("Tittle");
		notification.setIdBatch(1L);
		notification.setMessage("Message");
		notification.setNotificationDate(Instant.now());
		notification.setReadStatus(false);
		notification.setIdNotification(1L);
		
		NotiList.add(notification);
		
		Notification notification1 = new Notification();
		notification1.setFromUserId(4L);
		notification1.setToUserId(2L);
		notification1.setHeader("Tittle1");
		notification1.setIdBatch(1L);
		notification1.setMessage("Message1");
		notification1.setNotificationDate(Instant.now());
		notification1.setReadStatus(false);
		notification1.setIdNotification(2L);
		
		NotiList.add(notification1);
		
		Mockito.when(notificationRepository.findByToUserId(2L)).thenReturn(NotiList);
		Mockito.when(notificationRepository.findByToUserIdAndReadStatusOrderByNotificationDateDesc(2L,false)).thenReturn(NotiList);
		
		User user = new User();
		user.setUserSurId(4L);
		user.setFirstName("TeacherFirstName");
		user.setLastName("LastName");
		
		Mockito.when(userRepository.findByUserSurId(4L)).thenReturn(user);
		
			
	
		
		
		
		List<Student> list = new ArrayList<Student>();
		
		Student student = new Student();
		
		student.setIdStudent(1L);
		student.setGender("Male");
		student.setIdClassStandard(1L);
		student.setIdLangauage(1L);
		
		Parent parent = new Parent();
		parent.setIdParent(1L);
		
		User userParent = new User();
		userParent.setEmail("parent_email");	
		userParent.setFirstName("Thanuja");
		userParent.setLastName("M");
		userParent.setMobileNumber("12345678");
		userParent.setRegisteredAs("Parent");
		userParent.setUserSurId(1L);
		
		parent.setUser(userParent);
		
		student.setParent(parent);
		
		User userStudent = new User();
		userStudent.setEmail("student_email");	
		userStudent.setFirstName("Meghana");
		userStudent.setLastName("M");
		userStudent.setMobileNumber("12345678");
		userStudent.setRegisteredAs("Student");
		userStudent.setUserSurId(2L);
		
		student.setUser(userStudent);
		
		list.add(student);
		
		Student student2 = new Student();
		
		student2.setIdStudent(2L);
		student2.setGender("Female");
		student2.setIdClassStandard(1L);
		student2.setIdLangauage(1L);
		
		User userStudent2 = new User();
		userStudent2.setEmail("student2_email");	
		userStudent2.setFirstName("Theja");
		userStudent2.setLastName("M");
		userStudent2.setMobileNumber("12345678");
		userStudent2.setRegisteredAs("Student");
		userStudent2.setUserSurId(3L);
		
		student2.setUser(userStudent2);
		
		list.add(student2);
		
		List<StudentSubscription> stuSubList = new ArrayList<StudentSubscription>();
		
		StudentSubscription stusub = new StudentSubscription();
		stusub.setIdStudentSubscription(1L);
		stusub.setIdBatch(1L);
		stusub.setIdProduct(1L);
		stusub.setIdProductGroup(1L);
		stusub.setIdproductLine(1L);
		stusub.setIdStudent(1L);
		
		stuSubList.add(stusub);
		
		StudentSubscription stusub1 = new StudentSubscription();
		stusub1.setIdStudentSubscription(2L);
		stusub1.setIdBatch(1L);
		stusub1.setIdProduct(1L);
		stusub1.setIdProductGroup(1L);
		stusub1.setIdproductLine(1L);
		stusub1.setIdStudent(2L);
		
		stuSubList.add(stusub);
		
		Mockito.when(notificationRepository.getStudentsBasedonBatches(1L)).thenReturn(list);
	}

	@Test
	@SuppressWarnings({ "rawtypes"})
	void saveNotificationTest() {
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//String yyyyMMdd = sdf.format(Instant.now());		
	
		Notification notification = new Notification();
		notification.setFromUserId(4L);
		notification.setToUserId(2L);
		notification.setHeader("Tittle");
		notification.setIdBatch(1L);
		notification.setMessage("Message");		
		notification.setReadStatus(true);	
		
		Document doc =	notificationService.saveNotification(notification);
		
		assertEquals(200, doc.getStatusCode());
		Notification not = (Notification) doc.getData();
		assertEquals(1, not.getIdNotification());
		assertEquals(4, not.getFromUserId());
		assertEquals(2, not.getToUserId());
		assertEquals("Tittle", not.getHeader());
		assertEquals(1, not.getIdBatch());
		assertEquals("Message", not.getMessage());
		assertEquals(false, not.getReadStatus());
		//assertEquals(yyyyMMdd, sdf.format(not.getNotificationDate()));
		
	}
	
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked"})
	void getListofStudentsTest() {
		Document doc = notificationService.getListofStudents(1L);
		
		assertEquals(200, doc.getStatusCode());
		List<StudentsDto> studeList = (List<StudentsDto>) doc.getData();
		assertEquals(2, studeList.size());
		assertEquals(1, studeList.get(0).getIdStudent());
		assertEquals("Meghana M",studeList.get(0).getStudentName()) ;
		assertEquals(2,studeList.get(0).getIdUser()) ;
		assertEquals(1,studeList.get(0).getIdParent()) ;
		assertEquals("Thanuja M",studeList.get(0).getParentName()) ;
		assertEquals(1, studeList.get(0).getIdParentUser());
		
		assertEquals(2, studeList.get(1).getIdStudent());
		assertEquals("Theja M",studeList.get(1).getStudentName()) ;
		assertEquals(3,studeList.get(1).getIdUser()) ;
		
		
	}
	
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked"})
	void getAllNotifications() {
		Document doc = notificationService.getAllNotifications(2L);
		
		assertEquals(200, doc.getStatusCode());
		
		List<NoificationDTO> dtoList = (List<NoificationDTO>) doc.getData();
		assertEquals(2, dtoList.size());
		assertEquals(1, dtoList.get(0).getIdNotification());
		assertEquals("Message", dtoList.get(0).getMessage());
		assertEquals("Tittle", dtoList.get(0).getHeader());
		assertEquals(false, dtoList.get(0).getReadStatus());
		assertEquals("TeacherFirstName LastName", dtoList.get(0).getFrom());
		
		assertEquals(2, dtoList.get(1).getIdNotification());
		assertEquals("Message1", dtoList.get(1).getMessage());
		assertEquals("Tittle1", dtoList.get(1).getHeader());
		assertEquals(false, dtoList.get(1).getReadStatus());
		assertEquals("TeacherFirstName LastName", dtoList.get(1).getFrom());
	}
	
	
	@Test
	@SuppressWarnings({ "rawtypes"})
	void updateNotificationReadStatusTest() {		
		
		Notification notification = new Notification();
		notification.setFromUserId(4L);
		notification.setToUserId(2L);
		notification.setHeader("Tittle");
		notification.setIdBatch(1L);
		notification.setMessage("Message");		
		notification.setReadStatus(true);	
		
		Document doc = notificationService.updateNotificationReadStatus(2L);
		
		assertEquals(200, doc.getStatusCode());
		Notification not = (Notification) doc.getData();		
		assertEquals(true, not.getReadStatus());
		
	}

	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked"})
	void getUnreadNotificationsTest() {	
		
		Document doc = notificationService.getUnreadNotifications(2L);
		
		assertEquals(200, doc.getStatusCode());
		List<NoificationDTO> dtoList = (List<NoificationDTO>) doc.getData();
		assertEquals(2, dtoList.size());
		
		
		assertEquals(1, dtoList.get(0).getIdNotification());
		assertEquals("Message", dtoList.get(0).getMessage());
		assertEquals("Tittle", dtoList.get(0).getHeader());
		assertEquals(false, dtoList.get(0).getReadStatus());
		assertEquals("TeacherFirstName LastName", dtoList.get(0).getFrom());
		
		assertEquals(2, dtoList.get(1).getIdNotification());
		assertEquals("Message1", dtoList.get(1).getMessage());
		assertEquals("Tittle1", dtoList.get(1).getHeader());
		assertEquals(false, dtoList.get(1).getReadStatus());
		assertEquals("TeacherFirstName LastName", dtoList.get(1).getFrom());
		
	}
}
