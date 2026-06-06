/**
 *
 */
package co.vistafoundation.vlearning.auth.service;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Naveen Kumar
 *
 */
@SpringBootTest
class AuthServiceImplTest {

//	@Autowired
//	AuthService authService;
//	
//	@MockBean
//	UserRepository userRepository;
//
//	@MockBean
//	RoleRepository roleRepository;
//
//	@MockBean
//	PasswordEncoder passwordEncoder;
//
//	@MockBean
//	ParentRepository parentRepository;
//	
//	@MockBean
//	ClassRepository classRepository;
//
//
//	@MockBean
//	StudentRepository studentRepository;
//
//	@MockBean
//	TeacherRepository teacherRepository;
//
//	@MockBean
//	ForgotPasswordRepository forgotPasswordRepo;
//
//	@MockBean
//	LanguageRepository languageRepository;
//
//	@MockBean
//	JwtTokenProvider jwtTokenProvider;
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeEach
//	void setUp() throws Exception {
//
//		Set<Role> userRole = new HashSet<Role>();
//
//		Role role = new Role();
//		role.setRoleName(RoleName.ROLE_STUDENT);
//		role.setIdRole(1L);
//
//		userRole.add(role);
//
//		User user = new User();
//		user.setClassStandard(1L);
//		user.setFirstName("Chinnu");
//		user.setLastName("S");
//		user.setEmail("chinnu@gmail.com");
//		user.setMobileNumber("8138094345");
//		user.setPassword("Sajini@123");
//		user.setRegisteredAs("Student");
//		user.setSecondaryLanguage("Hindi");
//		user.setRoles(userRole);
//
//		user.setUsername("chinnu@gmail.com");
//
//		User userOutput = new User();
//		userOutput.setClassStandard(1L);
//		userOutput.setFirstName("Chinnu");
//		userOutput.setLastName("S");
//		userOutput.setEmail("chinnu@gmail.com");
//		userOutput.setMobileNumber("8138094345");
//		userOutput.setPassword("Sajini@123");
//		userOutput.setRegisteredAs("Student");
//		userOutput.setSecondaryLanguage("Hindi");
//		userOutput.setUsername("chinnu@gmail.com");
//		userOutput.setUserSurId(1L);
//		userOutput.setRoles(userRole);
//
//		User mockUser = new User();
//		mockUser.setClassStandard(1L);
//		mockUser.setFirstName("Naveen");
//		mockUser.setLastName("Kumar");
//		mockUser.setEmail("test@gmail.com");
//		mockUser.setMobileNumber("8904651018");
//		mockUser.setPassword("$2a$10$zcmgjz3gpcTgD/pj6.LYwejxO/XZ.7F676eVauLMugQUnT7RXdHG2");
//		mockUser.setRegisteredAs("Student");
//		mockUser.setSecondaryLanguage("Tamil");
//		mockUser.setUsername("mock_user");
//		mockUser.setUserSurId(3L);
//		mockUser.setRoles(userRole);
//
//		Parent parent = new Parent();
//		parent.setUser(user);
//
//		Parent parentOutput = new Parent();
//		parentOutput.setIdParent(1L);
//		parentOutput.setUser(user);
//
//		Student student = new Student();
//		student.setIdClassStandard(1L);
//		student.setIdLangauage(1L);
//		student.setUser(user);
//		student.setParent(parent);
//
//		Student studentOutput = new Student();
//		studentOutput.setIdClassStandard(1L);
//		studentOutput.setIdLangauage(1L);
//		studentOutput.setUser(user);
//		studentOutput.setParent(parent);
//		studentOutput.setIdStudent(1L);
//		
//		ClassStandard classStandard = new ClassStandard();
//		classStandard.setClassStandadName("10");
//		classStandard.setIdClassStandard(1L);
//		
//		
//
//		Mockito.when(studentRepository.save(student)).thenReturn(studentOutput);
//		
//		Mockito.when(studentRepository.findByUser(Mockito.any(User.class))).thenReturn(studentOutput);
//
//		Mockito.when(userRepository.findByUsername("mock_user")).thenReturn(mockUser);
//
//		Mockito.when(jwtTokenProvider.generateToken(Mockito.any(Authentication.class), null)).thenReturn(
//				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjEwOTc2NjkyLCJuYmYiOjE2MTA5NzY2OTIsImV4cCI6MTYxMTA2MzA5Mn0.arFJ92JSqQny0ciXyH0mHT-9UoCPcJymnfzjDYckFMha1odBrejphZ0PYO6jb489spl4slI1usnzYpv3BF2nlw");
//
//		Mockito.when(parentRepository.save(parent)).thenReturn(parentOutput);
//
//		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userOutput);
//
//		Mockito.when(roleRepository.findByRoleName(Mockito.any(RoleName.class))).thenReturn(role);
//		
//		Mockito.when(classRepository.findByIdClassStandard(1L)).thenReturn(classStandard);
//
//		List<Language> listLang = new ArrayList<Language>();
//		Language lang = new Language();
//		lang.setIdLanguage(1L);
//		lang.setLanguage("Tamil");
//
//		Language lang1 = new Language();
//		lang1.setIdLanguage(2L);
//		lang1.setLanguage("Kannada");
//
//		Language lang2 = new Language();
//		lang2.setIdLanguage(3L);
//		lang2.setLanguage("Telugu");
//
//		Language lang3 = new Language();
//		lang3.setIdLanguage(4L);
//		lang3.setLanguage("Hindi");
//
//		listLang.add(lang);
//		listLang.add(lang1);
//		listLang.add(lang2);
//		listLang.add(lang3);
//
//		Mockito.when(languageRepository.findAll()).thenReturn(listLang);
//
//	}
//
//	@Test
//	@SuppressWarnings("unchecked")
//	void testAuthenticateUser() {
//
//		// valid credentials login
//		LoginRequest request = new LoginRequest();
//		request.setUsernameOrEmail("mock_user");
//		request.setPassword("secret@123");
//
//		Document<Map<String, Object>> doc = authService.authenticateUser(request, null);
//		assertEquals(doc.getStatusCode(), 200);
//		Map<String, Object> obj = (Map<String, Object>) doc.getData();
//		assertEquals(13, obj.size());
//		assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjEwOTc2NjkyLCJuY"
//				+ "mYiOjE2MTA5NzY2OTIsImV4cCI6MTYxMTA2Mz" + "A5Mn0.arFJ92JSqQny0ciXyH0mHT-9UoCPcJy"
//				+ "mnfzjDYckFMha1odBrejphZ0PYO6j" + "b489spl4slI1usnzYpv3BF2nlw", obj.get("accessToken"));
//
//		assertEquals("Bearer", obj.get("tokenType"));
//		assertEquals("mock_user", obj.get("username"));
//		assertEquals("Naveen", obj.get("firstName"));
//		assertEquals("Kumar", obj.get("lastName"));
//		assertEquals("test@gmail.com", obj.get("email"));
//		assertEquals("Tamil", obj.get("secondary_language"));
//		assertEquals("8904651018", obj.get("mobileNumber"));
//		assertEquals("Student", obj.get("registeredAs"));
//		assertEquals(3L, obj.get("userSurId"));
//
//		doc = null;
//
//		// invalid credentails login
//
//		LoginRequest request2 = new LoginRequest();
//		request2.setUsernameOrEmail("Something");
//		request2.setPassword("password");
//
//		doc = authService.authenticateUser(request2, null);
//
//		assertEquals(401, doc.getStatusCode());
//		assertEquals(null, doc.getData());
//		assertEquals("Bad credentials", doc.getMessage());
//
//	}
//
//	@Test
//	void testRegisterUser() {
//
//		SignUpRequest srStudent = new SignUpRequest();
//		srStudent.setEmail("chinnu@gmail.com");
//		srStudent.setFirstName("Chinnu");
//		srStudent.setLastName("S");
//		srStudent.setPassword("Sajini@123");
//		srStudent.setUsername("chinnu@gmail.com");
//		srStudent.setMobileNumber("8138094345");
//		srStudent.setRole("Student");
//		srStudent.setSecondaryLanguage("Hindi");
//		srStudent.setClassStandard(1L);
//
//		User u = authService.registerUser(srStudent);
//
//		assertEquals(srStudent.getClassStandard(), u.getClassStandard());
//		assertEquals(srStudent.getFirstName(), u.getFirstName());
//		assertEquals(srStudent.getLastName(), u.getLastName());
//		assertEquals(srStudent.getEmail(), u.getEmail());
//		assertEquals(srStudent.getMobileNumber(), u.getMobileNumber());
//		assertEquals(srStudent.getUsername(), u.getUsername());
//		assertEquals(srStudent.getPassword(), u.getPassword());
//		assertEquals(srStudent.getSecondaryLanguage(), u.getSecondaryLanguage());
//
//	}
//
////        @Test
//	void testGetIdsOfdifferentRole() {
//		fail("Not yet implemented"); // TODO
//	}
//
////        @Test
//	void testResetPassword() {
//		fail("Not yet implemented"); // TODO
//	}
//
////        @Test
//	void testForgotPassword() {
//		fail("Not yet implemented"); // TODO
//	}
//
////        @Test
//	void testVerifyForgotPassword() {
//		fail("Not yet implemented"); // TODO
//	}
//
////        @Test
//	void testFetchAllUserLists() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	void testGetAllLanguage() {
//
//		Document<List<Language>> doc = authService.getAllLanguage();
//
//		assertEquals(doc.getStatusCode(), 200);
//		List<Language> obj = doc.getData();
//
//		assertEquals(4, obj.size());
//		assertEquals(1L, obj.get(0).getIdLanguage());
//		assertEquals("Tamil", obj.get(0).getLanguage());
//		assertEquals(2L, obj.get(1).getIdLanguage());
//		assertEquals("Kannada", obj.get(1).getLanguage());
//		assertEquals(3L, obj.get(2).getIdLanguage());
//		assertEquals("Telugu", obj.get(2).getLanguage());
//		assertEquals(4L, obj.get(3).getIdLanguage());
//		assertEquals("Hindi", obj.get(3).getLanguage());
//
//	}

}