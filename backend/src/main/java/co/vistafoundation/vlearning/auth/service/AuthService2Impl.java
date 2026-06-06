package co.vistafoundation.vlearning.auth.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.dto.IdObjectDTO;
import co.vistafoundation.vlearning.auth.dto.LoginRequest;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.ForgotPasswordRepository;
import co.vistafoundation.vlearning.auth.repository.MobileOtpRepository;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.CustomUserDetailsService;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailServiceImpl;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.exception.ForbiddenException;
import co.vistafoundation.vlearning.exception.ResourceNotFoundException;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.AvailableSchedule;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetails;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.AvailableScheduleRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchDetailsRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.marketer.model.ReferralCode;
import co.vistafoundation.vlearning.marketer.model.VendorStudentPayment;
import co.vistafoundation.vlearning.marketer.repository.ReferralCodeRepository;
import co.vistafoundation.vlearning.marketer.repository.VendorStudentPaymentRepository;
import co.vistafoundation.vlearning.notification.service.FirebaseMessagingService;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentMediumRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.TeacherSubjectRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.utils.GoogleIDTokenVerification;
import co.vistafoundation.vlearning.utils.SMSHorizonService;

@Service
public class AuthService2Impl implements AuthService2 {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	ParentRepository parentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	AuthService authService;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepo;

	@Autowired
	EmailServiceImpl emailService;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	StudentMediumRepository studentMediumRepository;

	@Autowired
	LevelExtraCurricularRepository levelExtraCurricularRepository;

	@Autowired
	UserDeviceRepository userDeviceRepository;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	SMSHorizonService smsHorizonService;

	@Autowired
	GoogleIDTokenVerification googleIDTokenVerification;

	@Value("${sms.flag}")
	private Boolean smsFlag;

	@Value("${app.lockDuration}")
	private Integer lockDuration;

//	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	@Autowired
	MobileOtpRepository mobileOtpRepository;

	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	FirebaseMessagingService firebaseService;
	@Autowired
	ReferralCodeRepository referralCodeRepositroy;
	@Autowired
	VendorStudentPaymentRepository vendorStudentPaymentRepositroy;

	@Autowired
	private ClassRepository classStandardRepository;
	@Autowired
	private LeadBatchDetailsRepository leadBatchDetailsRepository;

	@Autowired
	private AvailableScheduleRepository availableScheduleRepository;

	@Autowired
	private SubjectChapterRepository subjectChapterRepository;

	@Autowired
	CaptureGeoLocation location;

	/**
	 * 
	 * To get Ids of different roles eg role is student get StudentId
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getIdsOfdifferentRole(String role, Long userId) {

		Document result = new Document();
		try {

			IdObjectDTO idObjectDTO = new IdObjectDTO();
			Parent parent = new Parent();
			Student student = new Student();
			Teacher teacher = new Teacher();

			if (role.equals("Parent"))
				parent = parentRepository.getParentByUser_UserSurId(userId);

			else if (role.equals("Student"))
				student = studentRepository.getStudentByUser_UserSurId(userId);

			else if (role.equals("Teacher"))
				teacher = teacherRepository.getTeacherByUser_UserSurId(userId);

			if (parent != null)
				idObjectDTO.setIdParent(parent.getIdParent());

			if (student != null)
				idObjectDTO.setIdStudent(student.getIdStudent());

			if (teacher != null)
				idObjectDTO.setIdTeacher(teacher.getIdTeacher());

			result.setData(idObjectDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings("null")
	@Override
	public Document<?> userRegistrationNewSignUp(NewSignupRequestDTO signUpRequest, Device device, String ip,
			String userAgent) {

		Document<Map<String, Object>> doc = new Document<>();

		try {

			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

			if (signUpRequest.getReferralCode() != null) {
				ReferralCode referralCode = referralCodeRepositroy.findByReferralCode(signUpRequest.getReferralCode());

				if (referralCode == null)
					throw new AppException("Invalid ReferralCode.");

			}

			if (signUpRequest.getEmail() != null) {
				User checkUser = userRepository.getByEmail(signUpRequest.getEmail());

				if (checkUser != null)

					throw new AppException("This email id is already existed in our record.");

			}

			if (language == null)
				throw new AppException("Invalid Language.");

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getIdClassStandard(),
					signUpRequest.getMobileNumber(), signUpRequest.getRole(), language.getLanguage());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			if (signUpRequest.getRole().equals("Student")) {

				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
				if (userRole == null)
					throw new AppException("User Role not set.");
				user.setRoles(Collections.singleton(userRole));

				result = userRepository.save(user);

				// creation of student after user
				Student student = new Student();
				student.setUser(result);
				student.setIdClassStandard(signUpRequest.getIdClassStandard());
				student.setIdLangauage(language.getIdLanguage());
				student.setIdState(signUpRequest.getIdState());
				student.setIdStudentMedium(signUpRequest.getIdMedium());
				student.setIdSyllabus(signUpRequest.getIdSyllabus());
				student.setIsProfileEdited(Boolean.FALSE);

				if (signUpRequest.getReferralCode() != null) {
					ReferralCode referralCode = referralCodeRepositroy
							.findByReferralCode(signUpRequest.getReferralCode());
					if (referralCode != null) {
						student.setIdReferralCode(referralCode.getIdReferralCode());
						Student savedStudent = studentRepository.save(student);

						VendorStudentPayment savevendorStudentpayment = new VendorStudentPayment();
						savevendorStudentpayment.setIdVendor(referralCode.getIdVendor());
						savevendorStudentpayment.setIdStudent(savedStudent.getIdStudent());
						savevendorStudentpayment.setCreatedBy(savedStudent.getUser().getUserSurId());
						savevendorStudentpayment.setUpdatedBy(savedStudent.getUser().getUserSurId());
						savevendorStudentpayment.setOnBoardedDate(LocalDate.now());
						savevendorStudentpayment.setVendorPaymentStatus("NOT_PAID");
						savevendorStudentpayment.setPaymentAmount(0.0F);
						vendorStudentPaymentRepositroy.save(savevendorStudentpayment);
					}

				} else
					student = studentRepository.save(student);

				/**
				 * Updated by @author Naveen Kumar A Create student subscription when user
				 * provides idsubject.
				 */
				if (signUpRequest.getIdSubject() != null) {

					Product prod = productRepository
							.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(5L,
									signUpRequest.getIdClassStandard(), signUpRequest.getIdSubject(),
									signUpRequest.getIdSyllabus(), signUpRequest.getIdState(), Boolean.TRUE);

					if (prod != null) {
						StudentSubscription studentSubscription = new StudentSubscription();
						studentSubscription.setActiveFlag(true);
						studentSubscription.setIdProduct(prod.getIdProduct());
						studentSubscription.setIdProductGroup(prod.getIdProductGroup());
						studentSubscription.setIdStudent(student.getIdStudent());
						studentSubscription.setIdproductLine(5L);
						studentSubscription.setFreeFlag(true);
						studentSubscription.setUserSurId(result.getUserSurId());
						studentSubscription = studentSubscriptionRepository.save(studentSubscription);
					}
				}

				// Extracting Necessary Fields for Sending Email
				// Send Welcome Email

				String studentEmail = signUpRequest.getEmail();
				String fullName = signUpRequest.getFirstName();
				String userName = signUpRequest.getUsername();
				String mobileNumber = signUpRequest.getMobileNumber();
				String role = signUpRequest.getRole();

				emailService.sendWelcomeEmailOnSuccessfulSignUp(studentEmail, fullName, userName, mobileNumber, role);
			}

			else if (signUpRequest.getRole().equals("Parent")) {

				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
				if (userRole == null)
					throw new AppException("User Role not set.");

				user.setRoles(Collections.singleton(userRole));
				result = userRepository.save(user);

				// creation of parent from user
				Parent parent = new Parent();
				parent.setUser(result);
				parent.setIdClassStandard(signUpRequest.getIdClassStandard());
				parent.setIdLangauage(language.getIdLanguage());
				parent.setIdState(signUpRequest.getIdState());
				parent.setIdStudentMedium(signUpRequest.getIdMedium());
				parent.setIdSyllabus(signUpRequest.getIdSyllabus());
				parent = parentRepository.save(parent);

				// Extracting Necessary Fields for Sending Email
				// Send Welcome Email

				String parentEmail = signUpRequest.getEmail();
				String fullName = signUpRequest.getFirstName();
				String userName = signUpRequest.getUsername();
				String mobileNumber = signUpRequest.getMobileNumber();
				String role = signUpRequest.getRole();

				emailService.sendWelcomeEmailOnSuccessfulSignUp(parentEmail, fullName, userName, mobileNumber, role);
			}

			// Make the user Login to the Application After Signing up

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			ClassStandard standard = null;
			Syllabus syllabus = null;
			State state = null;

			User findByUsername = userRepository.findByUsername(authentication.getName());

			Map<String, Object> login = new HashMap<>();

			if (findByUsername != null) {

				login.put("tokenType", "Bearer");
				login.put("username", findByUsername.getUsername());
				login.put("firstName", findByUsername.getFirstName());
				login.put("lastName", findByUsername.getLastName());
				login.put("email", findByUsername.getEmail());
				login.put("secondary_language", findByUsername.getSecondaryLanguage());
				login.put("mobileNumber", findByUsername.getMobileNumber());
				login.put("registeredAs", findByUsername.getRegisteredAs());
				login.put("userSurId", findByUsername.getUserSurId());
				login.put("userProfilePic", findByUsername.getUserProfilePic());
				login.put("subscriptionFlag",
						studentSubscriptionService.checkExistingSubscriptionLogin(findByUsername.getUserSurId()));

				if (findByUsername.getRegisteredAs().equals("Student")) {
					Student student = studentRepository.findByUser(findByUsername);
					if (student != null) {
						standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
						if (standard == null)
							throw new AppException("Invalid IdClassStandard.");
						syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
						if (syllabus == null)
							throw new AppException("Invalid IdSyllabus.");
						state = stateRepository.findByIdState(student.getIdState());

						if (state == null)
							throw new AppException("Invalid IdState.");

						login.put("stateObject", state);

						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);
					} else
						throw new NullPointerException("Role Not Found");
				} else if (findByUsername.getRegisteredAs().equals("Parent")) {
					Parent parent = parentRepository.findByUser(findByUsername);
					if (parent != null) {
						standard = classRepository.findByIdClassStandard(parent.getIdClassStandard());
						if (standard == null)
							throw new AppException("Invalid IdClassStandard.");
						syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
						if (syllabus == null)
							throw new AppException("Invalid IdSyllabus.");

						state = stateRepository.findByIdState(parent.getIdState());

						if (state == null)
							throw new AppException("Invalid IdState.");

						login.put("stateObject", state);

						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);

					} else
						throw new NullPointerException("Role Not Found");
				}

				StudentPostLoginDTO spl = studentSubscriptionService
						.checkExistingSubscriptionLogin(user.getUserSurId());
				Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

				UserMetaClaim umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
						state.getIdState(), isSubsribed);
				String jwt = tokenProvider.generateToken(authentication, device, umc);
				String jwtRefresh = tokenProvider.refreshToken(authentication, device);

				login.put("accessToken", jwt);
				login.put("refreshToken", jwtRefresh);

				JSONObject loc = location.generateGeoLocation(ip);

				authService.checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
						findByUsername.getUsername(), findByUsername.getClassStandard(), device, loc, userAgent);

				/**
				 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
				 *         issues raised vl-843 , un-comment this to intialize the sso for all
				 *         the user
				 * 
				 * 
				 * 
				 * 
				 *         if (!checkFlag) throw new AppException( "A device is currently logged
				 *         in through this account. Please logout from that device in order to
				 *         log in.");
				 * 
				 */

			}

			doc.setData(login);
			doc.setMessage("User SignUp Successful. Logged In to the Application");
			doc.setStatusCode(200);

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Entred UserName or Email or PhoneNumber is Already Registred");
					return doc;
				}

				else {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					doc.setMessage(exp.getLocalizedMessage());
					return doc;
				}

			}

			else {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				doc.setMessage(exp.getLocalizedMessage());
				return doc;
			}

		}

		return doc;
	}

	@SuppressWarnings("unused")
	private Boolean checkMobileDevice(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device) throws AppException {
		Boolean checkfalg = false;

		UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userId, "MOBILE");

		if (userDevice != null) {
			// previous user device record found

			// check the previous token validity
			Boolean validFlag = firebaseService.verifyToken(userDevice.getDeviceId());

			if (validFlag)
				throw new AppException(
						"A device is currently logged in through this account. Please logout from that device in order to log in.");

			userDevice.setDeviceId(deviceId);
			userDevice.setJwtToken(generatedToken);
			userDevice.setDeviceType("MOBILE");
			userDevice.setIdClassStandard(idClassStandard);
			userDevice = userDeviceRepository.save(userDevice);

			checkfalg = true;
		} else {
			// if there is no user device data found , new entry will be created here.
			UserDevice u_device = new UserDevice();
			u_device.setDeviceId(deviceId);
			u_device.setUserSurId(userId);
			u_device.setDeviceType("MOBILE");
			u_device.setIdClassStandard(idClassStandard);
			u_device.setJwtToken(generatedToken);
			u_device = userDeviceRepository.save(u_device);

			checkfalg = true;
		}

		return checkfalg;
	}

	@SuppressWarnings("unused")
	private Boolean checkWebDevice(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device) throws AppException {
		Boolean checkfalg = false;

		// web implementations starts here

		UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userId, "WEB");

		if (userDevice != null) {

			if (userDevice.getJwtToken() != null && !userDevice.getJwtToken().isEmpty()) {

				UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(username);
				Boolean validFlag = tokenProvider.validateToken(userDevice.getJwtToken(), userPrincipal);

				if (validFlag)
					throw new AppException(
							"A device is currently logged in through this account. Please logout from that device in order to log in.");

				userDevice.setJwtToken(generatedToken);

			} else {
				userDevice.setJwtToken(generatedToken);
			}

			userDevice.setDeviceId(deviceId);
			userDevice.setDeviceType("WEB");
			userDevice.setIdClassStandard(idClassStandard);
			userDevice = userDeviceRepository.save(userDevice);

			checkfalg = true;

		} else {
			// if there is no user device data found , new entry will be created here.
			UserDevice u_device = new UserDevice();
			u_device.setDeviceId(deviceId);
			u_device.setUserSurId(userId);
			u_device.setDeviceType("WEB");
			u_device.setIdClassStandard(idClassStandard);
			u_device.setJwtToken(generatedToken);
			u_device = userDeviceRepository.save(u_device);

			checkfalg = true;
		}

		return checkfalg;
	}

	@Override
	public Document<Boolean> verifyReferralCode(String referralCode) {

		Document<Boolean> doc = new Document<>();

		try {
			ReferralCode refcode = referralCodeRepositroy.findByReferralCode(referralCode);

			if (refcode != null) {
				doc.setData(true);
				doc.setMessage("Valid Referral Code.");
				doc.setStatusCode(200);

			} else {

				doc.setData(false);
				doc.setMessage("invalid Referral Code");
				doc.setStatusCode(200);

			}
		} catch (Exception e) {
			doc.setData(false);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}

		return doc;

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	@Override
	public Document googleOAuthSignup(NewSignupRequestDTO signUpRequest, Device device, String ip, String userAgent) {

		Document doc = new Document<>();

		try {

			if (signUpRequest == null) {
				throw new NullPointerException("All the Fields are Mandatory. Please Fill All the details");
			}

			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

			if (language == null)
				throw new AppException("Invalid IdLanguage.");

			if (signUpRequest.getReferralCode() != null) {
				ReferralCode referralCode = referralCodeRepositroy.findByReferralCode(signUpRequest.getReferralCode());

				if (referralCode == null)
					throw new AppException("Invalid ReferralCode.");

			}

			// String randomPassword = signUpRequest.getPassword() + "_" +
			// RandomStringUtils.random(10);

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getIdClassStandard(),
					signUpRequest.getMobileNumber(), signUpRequest.getRole(), language.getLanguage());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			if (signUpRequest.getRole().equals("Student")) {
				ProductGroup productGroup = null;
				Product prod = null;
				if (signUpRequest.getIdSubject() != null) {
					// 5 is the productLine Id which is fixed for academic
					productGroup = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(
							signUpRequest.getIdClassStandard(), 5L, signUpRequest.getIdSyllabus());
					if (productGroup == null) {
						throw new NullPointerException("Cannot find the product group ");
					}
					prod = productRepository
							.findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(
									productGroup.getIdProductGroup(), signUpRequest.getIdClassStandard(),
									signUpRequest.getIdSubject(), signUpRequest.getIdSyllabus(), Boolean.TRUE);
					if (prod == null) {
						throw new NullPointerException("Cannot find the product");
					}
				}

				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
				if (userRole == null)
					throw new AppException("User Role not set.");
				user.setRoles(Collections.singleton(userRole));

				result = userRepository.save(user);

				// creation of student after user
				Student student = new Student();
				student.setUser(result);
				student.setIdClassStandard(signUpRequest.getIdClassStandard());
				student.setIdLangauage(language.getIdLanguage());
				student.setIdState(signUpRequest.getIdState());
				student.setIdStudentMedium(signUpRequest.getIdMedium());
				student.setIdSyllabus(signUpRequest.getIdSyllabus());
				student.setIsProfileEdited(Boolean.FALSE);

				if (signUpRequest.getReferralCode() != null) {
					ReferralCode referralCode = referralCodeRepositroy
							.findByReferralCode(signUpRequest.getReferralCode());
					if (referralCode != null) {
						student.setIdReferralCode(referralCode.getIdReferralCode());
						Student savedStudent = studentRepository.save(student);

						VendorStudentPayment savevendorStudentpayment = new VendorStudentPayment();
						savevendorStudentpayment.setIdVendor(referralCode.getIdVendor());
						savevendorStudentpayment.setIdStudent(savedStudent.getIdStudent());
						savevendorStudentpayment.setCreatedBy(savedStudent.getUser().getUserSurId());
						savevendorStudentpayment.setUpdatedBy(savedStudent.getUser().getUserSurId());
						savevendorStudentpayment.setOnBoardedDate(LocalDate.now());
						savevendorStudentpayment.setVendorPaymentStatus("NOT_PAID");
						savevendorStudentpayment.setPaymentAmount(0.0F);
						vendorStudentPaymentRepositroy.save(savevendorStudentpayment);
					}

				} else
					student = studentRepository.save(student);

				if (signUpRequest.getIdSubject() != null) {
					StudentSubscription studentSubscription = new StudentSubscription();
					studentSubscription.setActiveFlag(true);
					studentSubscription.setIdProduct(prod.getIdProduct());
					studentSubscription.setIdProductGroup(productGroup.getIdProductGroup());
					studentSubscription.setIdStudent(student.getIdStudent());
					studentSubscription.setIdproductLine(5L);
					studentSubscription.setFreeFlag(true);
					studentSubscription.setUserSurId(result.getUserSurId());
					studentSubscription = studentSubscriptionRepository.save(studentSubscription);

				}

				// Extracting Necessary Fields for Sending Email
				// Send Welcome Email

				String studentEmail = signUpRequest.getEmail();
				String fullName = signUpRequest.getFirstName();
				String userName = signUpRequest.getUsername();
				String mobileNumber = signUpRequest.getMobileNumber();
				String role = signUpRequest.getRole();

				emailService.sendWelcomeEmailOnSuccessfulSignUp(studentEmail, fullName, userName, mobileNumber, role);
			}

			else if (signUpRequest.getRole().equals("Parent")) {
				Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
				if (userRole == null)
					throw new AppException("User Role not set.");

				user.setRoles(Collections.singleton(userRole));
				result = userRepository.save(user);

				// creation of parent from user
				Parent parent = new Parent();
				parent.setUser(result);
				parent.setIdClassStandard(signUpRequest.getIdClassStandard());
				parent.setIdLangauage(language.getIdLanguage());
				parent.setIdState(signUpRequest.getIdState());
				parent.setIdStudentMedium(signUpRequest.getIdMedium());
				parent.setIdSyllabus(signUpRequest.getIdSyllabus());
				parent = parentRepository.save(parent);

				// Extracting Necessary Fields for Sending Email
				// Send Welcome Email

				String parentEmail = signUpRequest.getEmail();
				String fullName = signUpRequest.getFirstName();
				String userName = signUpRequest.getUsername();
				String mobileNumber = signUpRequest.getMobileNumber();
				String role = signUpRequest.getRole();

				emailService.sendWelcomeEmailOnSuccessfulSignUp(parentEmail, fullName, userName, mobileNumber, role);
			}

			// Make the user Login to the Application After Signing up

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			User findByUsername = userRepository.findByUsername(authentication.getName());
			ClassStandard standard = null;
			Syllabus syllabus = null;
			State state = null;

			Map<String, Object> login = new HashMap<>();

			if (findByUsername != null) {

				login.put("tokenType", "Bearer");
				login.put("username", findByUsername.getUsername());
				login.put("firstName", findByUsername.getFirstName());
				login.put("lastName", findByUsername.getLastName());
				login.put("email", findByUsername.getEmail());
				login.put("secondary_language", findByUsername.getSecondaryLanguage());
				login.put("mobileNumber", findByUsername.getMobileNumber());
				login.put("registeredAs", findByUsername.getRegisteredAs());
				login.put("userSurId", findByUsername.getUserSurId());
				login.put("userProfilePic", findByUsername.getUserProfilePic());
				login.put("subscriptionFlag",
						studentSubscriptionService.checkExistingSubscriptionLogin(findByUsername.getUserSurId()));
				if (findByUsername.getRegisteredAs().equals("Student")) {
					Student student = studentRepository.findByUser(findByUsername);
					if (student != null) {
						standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
						if (standard == null)
							throw new AppException("invalid idClassStandard .");

						syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());

						if (syllabus == null)
							throw new AppException("invalid idSyllabus .");

						state = stateRepository.findByIdState(student.getIdState());

						if (state == null)
							throw new AppException("Invalid IdState.");

						login.put("stateObject", state);

						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);
					} else
						throw new NullPointerException("Role Not Found");
				} else if (findByUsername.getRegisteredAs().equals("Parent")) {
					Parent parent = parentRepository.findByUser(findByUsername);
					if (parent != null) {
						standard = classRepository.findByIdClassStandard(parent.getIdClassStandard());
						if (standard == null)
							throw new AppException("invalid idClassStandard .");

						syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
						if (syllabus == null)
							throw new AppException("invalid idSyllabus .");

						state = stateRepository.findByIdState(parent.getIdState());

						if (state == null)
							throw new AppException("Invalid IdState.");

						login.put("stateObject", state);
						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);
					} else
						throw new NullPointerException("Role Not Found");
				}
			}

			// update for new jwt generation
			StudentPostLoginDTO spl = studentSubscriptionService.checkExistingSubscriptionLogin(user.getUserSurId());
			Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

			UserMetaClaim umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
					state.getIdState(), isSubsribed);
			String jwt = tokenProvider.generateToken(authentication, device, umc);
			String jwtRefresh = tokenProvider.refreshToken(authentication, device);

			login.put("accessToken", jwt);
			login.put("refreshToken", jwtRefresh);

			JSONObject loc = location.generateGeoLocation(ip);

			authService.checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
					findByUsername.getUsername(), findByUsername.getClassStandard(), device, loc, userAgent);

			/**
			 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
			 *         issues raised vl-843 , un-comment this to intialize the sso for all
			 *         the user
			 * 
			 * 
			 * 
			 *         if (!checkFlag) throw new AppException( "A device is currently logged
			 *         in through this account. Please logout from that device in order to
			 *         log in.");
			 * 
			 */

			doc.setData(login);
			doc.setMessage("User SignUp Successful. Logged In to the Application");
			doc.setStatusCode(200);

		} catch (Exception e) {
			if (e.getCause() != null) {

				if (e.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Entred UserName or Email or PhoneNumber is Already Registred");
					return doc;
				}

				else {
					doc.setData(null);
					doc.setMessage(e.getLocalizedMessage());
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return doc;
				}
			}

		}
		return doc;
	}

	@SuppressWarnings("null")
	public Document<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			LeadBatchDetailsRequest leadBatchDetailsRequest) {
		Document<LeadBatchDetails> doc = new Document<>();
		try {

			if (leadBatchDetailsRequest == null) {
				throw new NullPointerException("Lead Batch Details Cannot be Null");
			}

			User result = null;

			ClassStandard classStandard = classStandardRepository
					.findByIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());

			if (classStandard == null) {
				throw new NullPointerException("classStandard Details Not Found...");
			}

			Subject subject = subjectRepository.findByIdSubject(leadBatchDetailsRequest.getIdSubject());

			if (subject == null) {
				throw new NullPointerException("subject Details Not Found...");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());

			if (syllabus == null) {
				throw new NullPointerException("syllabus Details Not Found...");
			}

			Language language = languageRepository.findByIdLanguage(leadBatchDetailsRequest.getIdLanguage());

			if (language == null) {
				throw new NullPointerException("language Details Not Found...");
			}

			SubjectChapter subjectChapter = subjectChapterRepository
					.findByIdSubjectChapter(leadBatchDetailsRequest.getIdSujectChapter());

			if (subjectChapter == null) {
				throw new NullPointerException("SubjectChapter Details Not Found...");
			}

			if (leadBatchDetailsRequest.getReferralCode() != null) {
				ReferralCode referralCode = referralCodeRepositroy
						.findByReferralCode(leadBatchDetailsRequest.getReferralCode());

				if (referralCode == null)
					throw new AppException("Invalid ReferralCode.");

			}

			if (leadBatchDetailsRequest.getIdVlUser() == null) {
				User user = new User();
				user.setFirstName(leadBatchDetailsRequest.getFirstName());
				user.setLastName(leadBatchDetailsRequest.getLastName());
				user.setEmail(leadBatchDetailsRequest.getEmail());
				user.setMobileNumber(leadBatchDetailsRequest.getMobileNumber());
				user.setRegisteredAs(leadBatchDetailsRequest.getRole());
				user.setUsername(leadBatchDetailsRequest.getUsername());
				user.setClassStandard(leadBatchDetailsRequest.getIdClassStandard());
				user.setSecondaryLanguage(language.getLanguage());

				final String randomPassword = RandomStringUtils.randomAlphabetic(6);

				user.setPassword(passwordEncoder.encode(randomPassword));

				if (leadBatchDetailsRequest.getRole().equals("Student")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
					if (userRole == null)
						throw new AppException("User Role not set.");
					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of student after user
					Student student = new Student();
					student.setUser(result);
					student.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
					student.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
					student.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());
					student.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());

					if (leadBatchDetailsRequest.getIdState() != null)
						student.setIdState(leadBatchDetailsRequest.getIdState());
					if (leadBatchDetailsRequest.getReferralCode() != null) {
						ReferralCode referralCode = referralCodeRepositroy
								.findByReferralCode(leadBatchDetailsRequest.getReferralCode());

						if (referralCode != null) {
							student.setIdReferralCode(referralCode.getIdReferralCode());
							Student savedStudent = studentRepository.save(student);

							VendorStudentPayment savevendorStudentpayment = new VendorStudentPayment();
							savevendorStudentpayment.setIdVendor(referralCode.getIdVendor());
							savevendorStudentpayment.setIdStudent(savedStudent.getIdStudent());
							savevendorStudentpayment.setCreatedBy(savedStudent.getUser().getUserSurId());
							savevendorStudentpayment.setUpdatedBy(savedStudent.getUser().getUserSurId());
							savevendorStudentpayment.setOnBoardedDate(LocalDate.now());
							savevendorStudentpayment.setVendorPaymentStatus("NOT_PAID");
							savevendorStudentpayment.setPaymentAmount(0.0F);
							vendorStudentPaymentRepositroy.save(savevendorStudentpayment);
						}

					} else

						studentRepository.save(student);

					String studentEmail = leadBatchDetailsRequest.getEmail();
					String fullName = leadBatchDetailsRequest.getFirstName();
					String userName = leadBatchDetailsRequest.getUsername();
					String mobileNumber = leadBatchDetailsRequest.getMobileNumber();
					String role = leadBatchDetailsRequest.getRole();
					String password = randomPassword;
					if (studentEmail != null) {
						emailService.sendEmailToStudentWithCredentialsAfterBookingFreeClass(studentEmail, fullName,
								userName, mobileNumber, role, password);
					}
				} else if (leadBatchDetailsRequest.getRole().equals("Parent")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
					if (userRole == null)
						throw new AppException("User Role not set.");

					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of parent from user
					Parent parent = new Parent();
					parent.setUser(result);
					parent.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
					parent.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
					parent.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());
					parent.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
					if (leadBatchDetailsRequest.getIdState() != null)
						parent.setIdState(leadBatchDetailsRequest.getIdState());
					parentRepository.save(parent);

					String parentEmail = parent.getUser().getEmail();
					String parentFullName = parent.getUser().getFirstName();
					String parentUserName = parent.getUser().getUsername();
					String mobileNumber = parent.getUser().getMobileNumber();
					String role = leadBatchDetailsRequest.getRole();
					String password = randomPassword;
					if (parentEmail != null) {
						emailService.sendEmailToParentWithCredentialsAfterBookingFreeClass(parentEmail, parentFullName,
								parentUserName, mobileNumber, role, password);
					}
				}
			}

			LeadBatchDetails leadBatchDetails = new LeadBatchDetails();
			leadBatchDetails.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
			if (leadBatchDetailsRequest.getIdVlUser() != null) {
				leadBatchDetails.setIdVlUser(leadBatchDetailsRequest.getIdVlUser());
			} else
				leadBatchDetails.setIdVlUser(result.getUserSurId());

			leadBatchDetails.setIdSubject(leadBatchDetailsRequest.getIdSubject());
			leadBatchDetails.setIdSujectChapter(leadBatchDetailsRequest.getIdSujectChapter());
			leadBatchDetails.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
			leadBatchDetails.setIdAvailableSchedule(leadBatchDetailsRequest.getIdAvailableSchedule());
			leadBatchDetails.setIdLanguage(leadBatchDetailsRequest.getIdLanguage());
			leadBatchDetails.setIdState(leadBatchDetailsRequest.getIdState());
			if (result != null) {
				leadBatchDetails.setCreatedBy(result.getUserSurId());
				leadBatchDetails.setUpdatedBy(result.getUserSurId());
			} else {
				leadBatchDetails.setCreatedBy(leadBatchDetailsRequest.getIdVlUser());
				leadBatchDetails.setUpdatedBy(leadBatchDetailsRequest.getIdVlUser());
			}

			LeadBatchDetails saved = leadBatchDetailsRepository.save(leadBatchDetails);

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsRequest.getIdAvailableSchedule());

			if (leadBatchDetailsRequest.getIdVlUser() != null) {
				User userInfo = userRepository.findByUserSurId(leadBatchDetailsRequest.getIdVlUser());
				// Send Thanks Mail For Notifying
				if (userInfo.getEmail() != null) {
					if (availableSchedule.isPresent()) {
						emailService.sendBookDemoClassWelcomeEmail(userInfo.getFirstName(), userInfo.getEmail(),
								userInfo.getMobileNumber(), classStandard.getClassStandadName(),
								subject.getSubjectName(), syllabus.getSyllabusName(),
								availableSchedule.get().getDayOfWeek(), availableSchedule.get().getFromTime(),
								availableSchedule.get().getToTime(), language.getLanguage(),
								subjectChapter.getChapterName());
					} else {
						throw new AppException("Schedule not available");
					}
				}
			} else {
				// Send Thanks Mail For Notifying
				if (result.getEmail() != null) {
					if (availableSchedule.isPresent()) {
						emailService.sendBookDemoClassWelcomeEmail(result.getFirstName(), result.getEmail(),
								result.getMobileNumber(), classStandard.getClassStandadName(), subject.getSubjectName(),
								syllabus.getSyllabusName(), availableSchedule.get().getDayOfWeek(),
								availableSchedule.get().getFromTime(), availableSchedule.get().getToTime(),
								language.getLanguage(), subjectChapter.getChapterName());
					} else {
						throw new AppException("Schedule not available");
					}
				}
			}

			doc.setData(saved);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Lead Batch Details Saved Successfully...");
			return doc;

		}

		catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Entred UserName or Email or PhoneNumber is Already Registred");
					return doc;
				}

				else {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					doc.setMessage(exp.getLocalizedMessage());
					return doc;
				}
			}
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

	}

	public Boolean checkAndUpdateUserDeviceId(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device) throws Exception {
		Boolean checkfalg = false;

		// Mobile implementations starts here
		if (device.isTablet() || device.isMobile()) {
			
			//Removed device id check form login api need revert once to added in status api
			
//			if (deviceId != null && !deviceId.isEmpty()) {
//				checkfalg = this.checkMobileDevice(deviceId, generatedToken, userId, username, idClassStandard, device);
//			} else {
//				checkfalg = this.checkWebDevice(deviceId, generatedToken, userId, username, idClassStandard, device);
//			}
			checkfalg = this.checkMobileDevice(deviceId, generatedToken, userId, username, idClassStandard, device);

		} else {

			checkfalg = this.checkWebDevice(deviceId, generatedToken, userId, username, idClassStandard, device);

		}

		return checkfalg;
	}

	public Document<Object> authenticateUser(LoginRequest loginRequest, Device device) {
		Document<Object> doc = new Document<>();

		try {
			User userAuth = userRepository.findByEmailOrMobileNumber(loginRequest.getUsernameOrEmail());

			if (userAuth == null)
				throw new ResourceNotFoundException("Your credentials not found in our records, please register");

			if (!userAuth.getActiveFlag())
				throw new ForbiddenException("Your account is deactivated, please contact us");

			if (!userAuth.getRegisteredAs().equals("Student"))
				throw new ForbiddenException("Please login as internal user!");

			Instant userLockTime = userAuth.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
			Long userLockTimeEpoch = userLockTime.getEpochSecond();
			Long currentEpoch = Instant.now().getEpochSecond();

			Authentication authentication = null;
			try {
				// cross verify whether any updation happened to user record for the past
				// lockDuration min
				if (userLockTimeEpoch > currentEpoch) {
					// see whether user already attempted to maximum allocated value
					if (userAuth.getMaxAttempts() >= 3)

						throw new AppException(
								"For security reasons Your account is temporarily Locked , Please login after  "
										+ LocalDateTime.ofInstant(userLockTime, ZoneId.of("Asia/Kolkata"))
												.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")));
				}

				// check user credentials
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userAuth.getUsername(), loginRequest.getPassword()));

			} catch (Exception e) {
				e.printStackTrace();
				// if the exception occurred due to bad credentials
				if (e.getClass().equals(BadCredentialsException.class)) {

					// check whether system time is greater then previous updated time , since lock
					// duration
					if (userLockTimeEpoch > currentEpoch) {
						if (userAuth.getMaxAttempts() < 3) {
							userAuth.setMaxAttempts(userAuth.getMaxAttempts() + 1);
							userRepository.save(userAuth);

						} else {
							throw new AppException(
									"For security reasons Your account is temporarily Locked , Please login after  "
											+ LocalDateTime.ofInstant(userLockTime, ZoneId.of("Asia/Kolkata"))
													.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
						}
					} else {
						// this condition will be executed when user trying login with wrong credentials
						// after lock time
						if (userAuth.getMaxAttempts() >= 3) {
							userAuth.setMaxAttempts(1);
							userRepository.save(userAuth);
						} else if (userAuth.getMaxAttempts() <= 3) {
							userAuth.setMaxAttempts(userAuth.getMaxAttempts() + 1);
							userRepository.save(userAuth);

						} else {
							throw new AppException(
									"For security reasons Your account is temporarily Locked , Please login after  "
											+ LocalDateTime.ofInstant(userLockTime, ZoneId.of("Asia/Kolkata"))
													.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
						}

					}

				} else {
					throw new AppException(e.getLocalizedMessage());
				}
			}

			if (authentication == null)
				throw new AppException("Bad credentials");
			// if its successful attempt means update the value to zero as default.
			userAuth.setMaxAttempts(0);
			userRepository.save(userAuth);
			// user locking changes ends here

			SecurityContextHolder.getContext().setAuthentication(authentication);

			User findByUsername = userRepository.findByUsername(authentication.getName());

			/*
			 * @author Abdul Elahi Created the user constrain mechanism for limit the users
			 * accessing multiple devices with same account
			 */
			boolean constrainFlag = false;

			List<UserDevice> userDevice = userDeviceRepository.findByUserSurId(findByUsername.getUserSurId());

			if (userDevice.size() > 0 || !userDevice.isEmpty()) {
				constrainFlag = true;
			}

			ClassStandard standard = null;
			Syllabus syllabus = null;
			State state = null;

			StudentPostLoginDTO spl = studentSubscriptionService
					.checkExistingSubscriptionLogin(findByUsername.getUserSurId());
			Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;
			Map<String, Object> login = new HashMap<>();

			if (findByUsername != null) {

				login.put("tokenType", "Bearer");
				login.put("username", findByUsername.getUsername());
				login.put("firstName", findByUsername.getFirstName());
				login.put("lastName", findByUsername.getLastName());
				login.put("email", findByUsername.getEmail());
				login.put("secondary_language", findByUsername.getSecondaryLanguage());
				login.put("mobileNumber", findByUsername.getMobileNumber());
				login.put("registeredAs", findByUsername.getRegisteredAs());
				login.put("userSurId", findByUsername.getUserSurId());
				login.put("userProfilePic", findByUsername.getUserProfilePic());
				login.put("subscriptionFlag", spl);

				if (findByUsername.getRegisteredAs().equals("Student")) {
					Student student = studentRepository.findByUser(findByUsername);
					if (student != null) {
						standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
						syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
						state = stateRepository.findByIdState(student.getIdState());
						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);
						login.put("stateObject", state);
					} else
						throw new NullPointerException("Role Not Found");
				}

				UserMetaClaim umc = null;
				/*
				 * @author Abdul Elahi added valid flag after creation of new auth signup module
				 * WEF V 4.0.0
				 */
				boolean validFlag = true;
				if (standard != null && syllabus != null && state != null) {
					umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(), state.getIdState(),
							isSubsribed);
				} else {
					umc = new UserMetaClaim(-1L, -1L, -1L, isSubsribed);
					validFlag = false;
				}

				String jwt = tokenProvider.generateToken(authentication, device, umc);
				String jwtRefresh = tokenProvider.refreshToken(authentication, device);

				login.put("accessToken", jwt);
				login.put("refreshToken", jwtRefresh);
				login.put("validFlag", validFlag);

			}

			doc.setData(login);
			doc.setMessage(!constrainFlag ? "Login Successful" : "Your account has been logged out in another device.");
			doc.setStatusCode(200);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}
	
	

}
