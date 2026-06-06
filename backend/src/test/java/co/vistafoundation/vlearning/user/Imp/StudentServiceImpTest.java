package co.vistafoundation.vlearning.user.Imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.CreateStudentDTO;
import co.vistafoundation.vlearning.user.dto.StudentDTO;
import co.vistafoundation.vlearning.user.model.ChangeMobileRepository;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentCompletionFactRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.service.StudentService;


@SpringBootTest
class StudentServiceImpTest {
	

	@Autowired
	StudentService studentService;
	
	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	PasswordEncoder passwordEncoder;

	@MockBean
	ParentRepository parentRepository;

	@MockBean
	UserRepository userRepository;

	@MockBean
	ChangeMobileRepository changeMobileNumberRepository;

	@MockBean
	StudentCompletionFactRepository studentCompletionFactRepository;
	
	@MockBean
	private StudentSubscriptionRepository studentSubscriptionRepository;
	
	@MockBean
	BatchRepository batchRepository;
	
	@MockBean
	ProductRepository productRepository;
	
	@MockBean
	SubjectRepository subjectRepository;
	
	@MockBean
	TeacherRepository teacherRepository;
	
	
	@BeforeEach
	public void setUp() throws ParseException {
		//CreateStudentDTO dto = new CreateStudentDTO();
		//dto.setClassStandard(1L);
		//dto.setFirstName("Meghana");
		//dto.setGmail("meghanagmail.com");
		//dto.setLastName("L");
		//dto.setMobileNumber("99999999999");
		//dto.setUserId(1L);
	
		Student stude = new Student();
		stude.setGender("Male");
		stude.setIdClassStandard(1L);
		stude.setIdLangauage(1L);
		stude.setIdStudent(1L);
		
		User user = new User();
		user.setUserSurId(1L);
		user.setUsername("any");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setMobileNumber("9876543210");
		user.setPassword("Password");
		
		
		Mockito.when(userRepository.findByUserSurId(1L)).thenReturn(user);
		
		Parent parent = new Parent();
		parent.setIdParent(1L);
		Mockito.when(parentRepository.findByUser(Mockito.any(User.class))).thenReturn(parent);
		Mockito.when(parentRepository.findByIdParent(1L)).thenReturn(parent);		
		
		stude.setParent(parent);
		//dto.setStudent(stude);
		stude.setUser(user);
		
		Mockito.when(studentRepository.save(any(Student.class))).thenReturn(stude);
		
		List<Student> list = new ArrayList<Student>();
		Student student = new Student();
		student.setIdStudent(1L);
		student.setIdClassStandard(1L);
		User user1 = new User();
		user1.setUserSurId(1L);
		user1.setFirstName("FirstName");
		user1.setLastName("Last");
		student.setUser(user1);
		
		list.add(student);
		
		Mockito.when(studentRepository.findByParent(Mockito.any(Parent.class))).thenReturn(list);
		
		
	}

	 @Test
	 public void saveStudenttest() {
		
		
		CreateStudentDTO dto = new CreateStudentDTO();
		dto.setClassStandard(1L);
		dto.setFirstName("Meghana");
		dto.setGmail("meghanajan10@gmail.com");
		dto.setLastName("L");
		dto.setMobileNumber("99999999999");
		dto.setUserId(1L);
	
		Student stude = new Student();
		stude.setGender("Male");
		stude.setIdClassStandard(1L);
		stude.setIdLangauage(1L);
		stude.setIdStudent(1L);
		
		User user = new User();
		user.setUserSurId(1L);
		user.setUsername("any");		
		
		Parent parent = new Parent();			
		stude.setParent(parent);		
		dto.setStudent(stude);
				
		Document<?> doc = studentService.saveStudent(dto);		
		assertEquals(200, doc.getStatusCode());
	
		Student student = (Student) doc.getData();
		assertEquals("Male", student.getGender());
		assertEquals(1, student.getIdClassStandard());
		assertEquals(1, student.getIdLangauage());
		assertEquals(1, student.getIdStudent());
		
		 
		
	}
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	 public void getListofstudenttest() {
		 
		 Document doc = studentService.getListofstudent(1L);
		 assertEquals(200, doc.getStatusCode());
		 
		 List<StudentDTO> list = (List<StudentDTO>) doc.getData();
		 assertEquals(1, list.get(0).getIdStudent());
		 assertEquals("FirstName", list.get(0).getFirstName());
		 assertEquals("Last", list.get(0).getLastName());
	 }
	

}
