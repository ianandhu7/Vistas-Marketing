
package co.vistafoundation.vlearning.auth.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import co.vistafoundation.vlearning.auth.config.CustomDevice;
import co.vistafoundation.vlearning.auth.dto.BulkSignupDTO;
import co.vistafoundation.vlearning.auth.dto.GoogleLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.GoogleOauthSignupV3;
import co.vistafoundation.vlearning.auth.dto.ManualSignupDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupClassInfoRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV3DTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV4DTO;
import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.MobileOtp;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.SignupPlatform;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.ForgotPasswordRepository;
import co.vistafoundation.vlearning.auth.repository.MobileOtpRepository;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.SignupPlatformRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.auth.utils.CreateUserAndStudent;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailServiceImpl;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequestV3;
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
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.model.Redemption;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.specialoffer.repository.RedemptionRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionServiceImplV2;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.StudentMedium;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentMediumRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.TeacherSubjectRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.GoogleIDTokenVerification;
import co.vistafoundation.vlearning.utils.RandomStringGenerator;
import co.vistafoundation.vlearning.utils.SMSHorizonService;

/**
 * @author NAVEEN
 *
 *         This service created to enhance security in order to avoid take over
 *         Bypassing Mobile Verification during the time of sign-up, Please
 *         refer https://v-learning.atlassian.net/browse/VL-1216 for further
 *         information.
 * 
 */

@Service
public class AuthService3Impl implements AuthService3 {

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
	ClassRepository classRepository;

	@Autowired
	ProductRepository productRepository;

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
	SMSHorizonService smsHorizonService;

	@Autowired
	GoogleIDTokenVerification googleIDTokenVerification;

	@Value("${sms.flag}")
	Boolean smsFlag;

	@Value("${app.lockDuration}")
	Integer lockDuration;

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
	ClassRepository classStandardRepository;

	@Autowired
	LeadBatchDetailsRepository leadBatchDetailsRepository;

	@Autowired
	AvailableScheduleRepository availableScheduleRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Autowired
	StudentSubscriptionServiceImplV2 studentSubscriptionServiceImplV2;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	SignupPlatformRepository signupPlatformRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	StudentOrderRepository studentOrderRepository;

	@Autowired
	RandomStringGenerator randomStringGenerator;

	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	private RedemptionRepository redemptionRepository;
	
	@Autowired
	CaptureGeoLocation geoLocation;	
	
	@Autowired
	UserService userService;

	@Autowired
	private CreateUserAndStudent createUserAndStudent;

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");

	private static final Logger logger = LoggerFactory.getLogger(AuthService3Impl.class);

	@Override
	public Document<?> userRegistrationNewSignUp(NewSignupRequestV3DTO signUpRequest, Device device,String ip, String userAgent) {

		Document<Map<String, Object>> doc = new Document<>();

		try {

			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

			if (language == null)
				throw new AppException("Invalid Language.");

			if (signUpRequest.getReferralCode() != null) {

				if (!signUpRequest.getReferralCode().isEmpty()) {

					ReferralCode referralCode = referralCodeRepositroy
							.findByReferralCode(signUpRequest.getReferralCode());

					if (referralCode == null)
						throw new AppException("Invalid ReferralCode.");
				}

			}

			Boolean phoneFlag = userRepository.existsByMobileNumber(signUpRequest.getMobileNumber());

			if (phoneFlag)
				throw new AppException("This Mobile Number is already existed in our record.");

			if (signUpRequest.getEmail() != null) {

				Boolean emailFlag = userRepository.existsByEmail(signUpRequest.getEmail());

				if (emailFlag)

					throw new AppException("This email id is already existed in our record.");

			}

			if (signUpRequest.getOtp() == null || signUpRequest.getOtp().isEmpty())

				throw new AppException("Please provide the OTP, sent to the user mobile number.");

			// verify user mobile OTP process

			MobileOtp mobileOtp = authService.verifyMobileOTP(signUpRequest.getMobileNumber(), signUpRequest.getOtp());

			if (mobileOtp == null)
				throw new AppException("Please request the otp first.");

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getIdClassStandard(),
					signUpRequest.getMobileNumber(), signUpRequest.getRole(), language.getLanguage(),
					signUpRequest.getSignUpSource());

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

			// Delete Record After Otp Verification
			mobileOtpRepository.delete(mobileOtp);

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
				/*
				 * @author Abdul Elahi added valid flag after creation of new auth signup module
				 * WEF V 4.0.0
				 */

				login.put("validFlag", Boolean.TRUE);
				
				JSONObject loc = geoLocation.generateGeoLocation(ip); 
				login.put("location", loc);

				authService.checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
						findByUsername.getUsername(), findByUsername.getClassStandard(), device,loc,userAgent);

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
					doc.setMessage("Entered UserName or Email or Phone Number is Already Registered");
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

	@Override
	public Document<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			LeadBatchDetailsRequestV3 leadBatchDetailsRequest) {

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

			// referral code verification
			if (leadBatchDetailsRequest.getReferralCode() != null) {

				if (!leadBatchDetailsRequest.getReferralCode().isEmpty()) {

					ReferralCode referralCode = referralCodeRepositroy
							.findByReferralCode(leadBatchDetailsRequest.getReferralCode());

					if (referralCode == null)
						throw new AppException("Invalid ReferralCode.");
				}

			}

			if (leadBatchDetailsRequest.getOtp() == null || leadBatchDetailsRequest.getOtp().isEmpty())

				throw new AppException("Please provide the OTP, sent to the user mobile number.");

			// verify user mobile OTP process

			MobileOtp mobileOtp = authService.verifyMobileOTP(leadBatchDetailsRequest.getMobileNumber(),
					leadBatchDetailsRequest.getOtp());

			if (mobileOtp == null)
				throw new AppException("Please request the otp first.");

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

			// Delete Record After OTP Verification
			mobileOtpRepository.delete(mobileOtp);

			LeadBatchDetails saved = leadBatchDetailsRepository.save(leadBatchDetails);

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsRequest.getIdAvailableSchedule());

			User userInfo = userRepository.findByUserSurId(leadBatchDetailsRequest.getIdVlUser());

			if (userInfo == null)
				throw new AppException("Invalid User Info found.");

			if (leadBatchDetailsRequest.getIdVlUser() != null) {

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
			}

			doc.setData(saved);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Lead Batch Details Saved Successfully...");

		}

		catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Entred UserName or Email or PhoneNumber is Already Registred");

				}

				else {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					doc.setMessage(exp.getLocalizedMessage());

				}
			} else {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				doc.setMessage(exp.getLocalizedMessage());
			}
		}

		return doc;

	}

	@Override
	public Document<?> googleOAuthSignup(GoogleOauthSignupV3 signUpRequest, Device device, String ip, String userAgent) {

		Document<Map<String, Object>> doc = new Document<>();

		try {

			if (signUpRequest == null) {
				throw new NullPointerException("All the Fields are Mandatory. Please Fill All the details");
			}

			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

			if (language == null)
				throw new AppException("Invalid IdLanguage.");

			if (signUpRequest.getReferralCode() != null) {

				if (!signUpRequest.getReferralCode().isEmpty()) {

					ReferralCode referralCode = referralCodeRepositroy
							.findByReferralCode(signUpRequest.getReferralCode());

					if (referralCode == null)
						throw new AppException("Invalid ReferralCode.");
				}

			}

			Boolean phoneFlag = userRepository.existsByMobileNumber(signUpRequest.getMobileNumber());

			if (phoneFlag)
				throw new AppException("This Mobile Number is already existed in our record.");

			if (signUpRequest.getEmail() != null) {

				Boolean emailFlag = userRepository.existsByEmail(signUpRequest.getEmail());

				if (emailFlag)

					throw new AppException("This email id is already existed in our record.");

			}

			if (signUpRequest.getOtp() == null || signUpRequest.getOtp().isEmpty())
				throw new AppException("Please provide the OTP, sent to the user mobile number.");

			// verify user mobile OTP process

			// checking if user already exists with this email or mobile number
			User userExists = userRepository.findByEmailAndMobileNumber(signUpRequest.getEmail(),
					signUpRequest.getMobileNumber());

			if (userExists != null) {
				throw new Exception("Email or ");
			}

			MobileOtp mobileOtp = authService.verifyMobileOTP(signUpRequest.getMobileNumber(), signUpRequest.getOtp());

			if (mobileOtp == null)
				throw new AppException("Please request the otp first.");

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getIdClassStandard(),
					signUpRequest.getMobileNumber(), signUpRequest.getRole(), language.getLanguage(),"Google OAuth");

			GoogleLoginRequestDTO googleSignUpvalidation = new GoogleLoginRequestDTO(signUpRequest.getEmail(),
					signUpRequest.getIdToken(), "");

			boolean isValid = googleIDTokenVerification.getGoogleIDTokenVerification(googleSignUpvalidation);

			if (isValid) {

				System.out.print("valid sign up");

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

					emailService.sendWelcomeEmailOnSuccessfulSignUp(studentEmail, fullName, userName, mobileNumber,
							role);
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

					emailService.sendWelcomeEmailOnSuccessfulSignUp(parentEmail, fullName, userName, mobileNumber,
							role);
				}

				// Delete Record After Otp Verification
				mobileOtpRepository.delete(mobileOtp);

				// Make the user Login to the Application After Signing up

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(),
								signUpRequest.getPassword()));

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

					// update for new jwt generation

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
				/*
				 * @author Abdul Elahi added valid flag after creation of new auth signup module
				 * WEF V 4.0.0
				 */
				login.put("validFlag", Boolean.TRUE);
				
				JSONObject loc = geoLocation.generateGeoLocation(ip);
				login.put("location", loc);
				
				authService.checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
						findByUsername.getUsername(), findByUsername.getClassStandard(), device,loc,userAgent);

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

				doc.setData(login);
				doc.setMessage("User SignUp Successful. Logged In to the Application");
				doc.setStatusCode(200);
			} else {
				doc.setData(null);
				doc.setMessage("Google authentication failed");
				doc.setStatusCode(500);
			}

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
			} else {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				doc.setMessage(e.getLocalizedMessage());
			}

		}

		return doc;

	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	public BulkSignupDTO verifyBulkSignupConstraints(String name, String email, String mobileNumber, String password,
			String syllabus, String classStandard, String state, String medium, String secondaryLanguage,
			String schoolName, String remarks) {
		BulkSignupDTO result = new BulkSignupDTO();

		try {

			// Validate syllabus
			if (syllabus == null || syllabus.trim().isEmpty()) {
				throw new AppException("Syllabus is required.");
			} else {
				try {
					Long idSyllabus = Long.parseLong(syllabus); // Attempt to parse syllabus as an integer
					Syllabus syl = syllabusRepository.findByIdSyllabus(idSyllabus);
					if (syl == null || syl.getIdSyllabus().equals(4L)) {
						throw new AppException("Invalid syllabus.");
					}
					syllabus = syl.getSyllabusName();
				} catch (NumberFormatException e) {
					throw new AppException("Syllabus must be an integer."); // Throw exception if parsing fails
				}
			}

			// Validate class standard
			if (classStandard == null || classStandard.trim().isEmpty()) {
				throw new AppException("Class standard is required.");
			} else {
				try {
					Long idClassStandard = Long.parseLong(classStandard); // Attempt to parse syllabus as an integer
					ClassStandard classStd = classStandardRepository.findByIdClassStandard(idClassStandard);
					if (classStd == null || classStd.getIdClassStandard().equals(4L)) {
						throw new AppException("Invalid Class Standard.");
					}
					classStandard = classStd.getClassStandadName();
				} catch (NumberFormatException e) {
					throw new AppException("ClassStandard must be an integer."); // Throw exception if parsing fails
				}
			}

			// Validate state
			if (state == null || state.trim().isEmpty()) {
				throw new AppException("State is required.");
			} else {
				try {
					Long idState = Long.parseLong(state);
					State stateVal = stateRepository.findByIdState(idState);
					if (stateVal == null || stateVal.getIdState().equals(6L)) {
						throw new AppException("Invalid State ");
					}
					state = stateVal.getState();
				} catch (NumberFormatException e) {
					throw new AppException("State must be an integer."); // Throw exception if parsing fails
				}
			}

			// Validate medium
			if (medium == null || medium.trim().isEmpty()) {
				throw new AppException("Medium is required.");
			} else {
				try {
					Long idMedium = Long.parseLong(medium);
					StudentMedium studentMedium = studentMediumRepository.findByIdStudentMedium(idMedium);
					if (studentMedium == null) {
						throw new AppException("Invalid Medium.");
					}
					medium = studentMedium.getMedium();
				} catch (NumberFormatException e) {
					throw new AppException("Medium must be an integer");
				}
			}

			if (secondaryLanguage == null || secondaryLanguage.trim().isEmpty()) {
				throw new AppException("Secondary language required");
			} else {
				try {
					Long idSeconaryLanguage = Long.parseLong(secondaryLanguage);
					Language language = languageRepository.findByIdLanguage(idSeconaryLanguage);
					if (language == null) {
						throw new AppException("invalid secondary lanuage");
					}
					secondaryLanguage = language.getLanguage();
				} catch (NumberFormatException e) {
					throw new AppException("Secondary Language must be an integer");
				}

			}
			// Validate name
			if (name == null || name.trim().isEmpty()) {
				throw new AppException("Name is required.");
			}

			// Validate email
			if (email != null && !email.trim().isEmpty()) {
				String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
				if (!email.matches(emailRegex)) {
					throw new AppException("Invalid email format.");
				}
				if (userRepository.existsByEmail(email)) {
					throw new AppException("Email id " + email + " already exists.");
				}
			}

			// Validate mobile number
			if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
				throw new AppException("Mobile number is required.");
			}

			// Check if password is null or empty
			if (password == null || password.trim().isEmpty()) {
				// Generate a default password based on the last four digits of the mobilenumber
				String defaultPassword = "vista@" + mobileNumber.substring(mobileNumber.length() - 4);
				password = defaultPassword;
			}

			if (!mobileNumber.matches("\\d+")) {
				throw new AppException("Mobile number must contain only digits");
			}
			if (mobileNumber.length() != 10) {
				throw new AppException("Mobile number must be 10 digit number");
			}
			if (!mobileNumber.matches("[98762].*")) {
				throw new AppException("Invalid mobile number: " + mobileNumber);
			}

			if (userRepository.existsByMobileNumber(mobileNumber)) {
				throw new AppException("Mobile number " + mobileNumber + " already exists.");
			}

			result.setName(name);
			result.setEmail(email);
			result.setMobileNumber(mobileNumber);
			result.setPassword(password);
			result.setSyllabus(syllabus);
			result.setClassStandard(classStandard);
			result.setState(state);
			result.setMedium(medium);
			result.setSecondaryLanguage(secondaryLanguage);
			result.setSchoolName(schoolName);
			result.setRemarks(remarks);
			result.setIsValid(true);
			result.setMessage("user will be signed up under username: " + mobileNumber + " .");

		} catch (AppException ex) {
			result.setName(name);
			result.setEmail(email);
			result.setMobileNumber(mobileNumber);
			result.setPassword(password);
			result.setSyllabus(syllabus);
			result.setClassStandard(classStandard);
			result.setState(state);
			result.setMedium(medium);
			result.setSecondaryLanguage(secondaryLanguage);
			result.setSchoolName(schoolName);
			result.setRemarks(remarks);
			result.setIsValid(false);
			result.setMessage(ex.getLocalizedMessage());
//			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> validateUploadBatchMannualSignUp(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<>();

		List<String> errorLogList = new ArrayList<>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {

			errorLogList.add("Bulk Signup verification Batch Process started at: " + Instant.now());
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			}
			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();

			CsvMapper csvMapper = new CsvMapper();

			// read CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			readAll.forEach(o -> {

				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("name")) {
					errorLogList
							.add("Missing column header 'name'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'name'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("email")) {
					errorLogList
							.add("Missing column header 'email'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'email'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("mobile_number")) {
					errorLogList.add(
							"Missing column header 'mobile_number'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header  'mobile_number'. Please make sure you are using the correct template.\"");
				}

				if (!header.containsKey("password")) {
					errorLogList.add(
							"Missing column header 'password'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'password'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("syllabus")) {
					errorLogList.add(
							"Missing column header 'syllabus'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'syllabus'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("class_standard")) {
					errorLogList.add(
							"Missing column header 'class_standard'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'class_standard'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("state")) {
					errorLogList
							.add("Missing column header 'state'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'state'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("medium")) {
					errorLogList.add(
							"Missing column header 'medium'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'medium'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("secondary_language")) {
					errorLogList.add(
							"Missing column header 'secondary_language'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'secondary_language'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("school_name")) {
					errorLogList.add(
							"Missing column header 'school_name'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'school_name'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("remarks")) {
					errorLogList.add(
							"Missing column header 'remarks'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'remarks'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("name") || !header.containsKey("email") || !header.containsKey("mobile_number")
						|| !header.containsKey("password") || !header.containsKey("syllabus")
						|| !header.containsKey("class_standard") || !header.containsKey("state")
						|| !header.containsKey("medium") || !header.containsKey("secondary_language")
						|| !header.containsKey("school_name") || !header.containsKey("remarks")) {
					throw new AppException("Invalid file or missing header in the upload csv.");
				}
			});

			List<BulkSignupDTO> signUpList = new ArrayList<>();

			int totalRecords = 0;

			Iterator<Object> readAllIterator = readAll.listIterator();

			while (readAllIterator.hasNext()) {
				BulkSignupDTO bulkSignup = new BulkSignupDTO();
				Map<String, String> row = (Map<String, String>) readAllIterator.next();
				totalRecords++;

				bulkSignup = verifyBulkSignupConstraints(row.get("name"), row.get("email"), row.get("mobile_number"),
						row.get("password"), row.get("syllabus"), row.get("class_standard"), row.get("state"),
						row.get("medium"), row.get("secondary_language"), row.get("school_name"), row.get("remarks"));
				signUpList.add(bulkSignup);

			}

			int sucessfullCount = (int) signUpList.stream().filter(s -> s.getIsValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

			errorLogList.add("Total no of records : " + totalRecords + ".");
			errorLogList.add("Total no of records valid: " + sucessfullCount + ".");
			errorLogList.add("Total no of records invalid: " + rejectedRecords + ".");
			errorLogList.add("Bulk Signup verification Batch Process ended at: " + Instant.now());

			dataMap.put("updatedRecord", signUpList);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", sucessfullCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			errorLogList.add("Error: " + e.getMessage());
			errorLogList.add("Bulk Signup verification Batch Process Terminated  at: " + Instant.now());
			dataMap.put("log", errorLogList);

			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.error(e.getLocalizedMessage());
		} finally {
			boolean deleTeFlag = file.delete();
			logger.info("Removed bulk file from memory: " + deleTeFlag);
		}
		return result;
	}
@Deprecated
	public BulkSignupDTO bulkSignup(BulkSignupDTO signUpRequest) {
//		Document<BulkSignupDTO> doc = new Document<>();
		try {
			Long idSyllabus;
			Long idClassStandard;
			Long idState;
			Long idMedium;
			Long idSecondaryLanguage;
			if (signUpRequest.getMobileNumber() == null || signUpRequest.getMobileNumber().trim().isEmpty()) {
				throw new AppException("Mobile number is required.");
			}
			// Check if password is null or empty
			if (signUpRequest.getPassword() == null || signUpRequest.getPassword().trim().isEmpty()) {
				String defaultPassword = "vista@"
						+ signUpRequest.getMobileNumber().substring(signUpRequest.getMobileNumber().length() - 4);
				signUpRequest.setPassword(defaultPassword);
			}

			// Validate syllabus
			if (signUpRequest.getSyllabus() == null || signUpRequest.getSyllabus().trim().isEmpty()) {
				throw new AppException("Syllabus is required.");
			} else {
				try {
					idSyllabus = Long.parseLong(signUpRequest.getSyllabus());
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(idSyllabus);
					if (syllabus == null || syllabus.getIdSyllabus().equals(4L)) {
						throw new AppException("Invalid syllabus.");
					}
					signUpRequest.setSyllabus(syllabus.getSyllabusName());
				} catch (NumberFormatException e) {
					throw new AppException("Syllabus must be an integer.");
				}
			}

			// Validate class standard
			if (signUpRequest.getClassStandard() == null || signUpRequest.getClassStandard().trim().isEmpty()) {
				throw new AppException("Class standard is required.");
			} else {
				try {
					idClassStandard = Long.parseLong(signUpRequest.getClassStandard());

					ClassStandard classStandard = classStandardRepository.findByIdClassStandard(idClassStandard);
					if (classStandard == null || classStandard.getIdClassStandard().equals(4L)) {
						throw new AppException("Invalid class standard.");
					}
					idClassStandard = classStandard.getIdClassStandard();
					signUpRequest.setClassStandard(classStandard.getClassStandadName());
				} catch (NumberFormatException e) {
					throw new AppException("Class standard must be an integer.");
				}
			}

			// Validate state
			if (signUpRequest.getState() == null || signUpRequest.getState().trim().isEmpty()) {
				throw new AppException("State is required.");
			} else {
				try {
					idState = Long.parseLong(signUpRequest.getState());
					State state = stateRepository.findByIdState(idState);
					if (state == null || state.getIdState().equals(6L)) {
						throw new AppException("Invalid state.");
					}
					signUpRequest.setState(state.getState());
				} catch (NumberFormatException e) {
					throw new AppException("State must be an integer.");
				}
			}

			// Validate medium
			if (signUpRequest.getMedium() == null || signUpRequest.getMedium().trim().isEmpty()) {
				throw new AppException("Medium is required.");
			} else {
				try {
					idMedium = Long.parseLong(signUpRequest.getMedium());
					StudentMedium studentMedium = studentMediumRepository.findByIdStudentMedium(idMedium);
					if (studentMedium == null) {
						throw new AppException("Invalid medium.");
					}
					signUpRequest.setMedium(studentMedium.getMedium());
				} catch (NumberFormatException e) {
					throw new AppException("Medium must be an integer.");
				}
			}

			// Validate secondary language
			if (signUpRequest.getSecondaryLanguage() == null || signUpRequest.getSecondaryLanguage().trim().isEmpty()) {
				throw new AppException("Secondary language is required.");
			} else {
				try {
					idSecondaryLanguage = Long.parseLong(signUpRequest.getSecondaryLanguage());
					Language secondaryLanguage = languageRepository.findByIdLanguage(idSecondaryLanguage);
					if (secondaryLanguage == null) {
						throw new AppException("Invalid secondary language.");
					}
					signUpRequest.setSecondaryLanguage(secondaryLanguage.getLanguage());
				} catch (NumberFormatException e) {
					throw new AppException("Secondary language must be an integer.");
				}
			}

			// Validate name
			if (signUpRequest.getName() == null || signUpRequest.getName().trim().isEmpty()) {
				throw new AppException("Name is required.");
			}

			// Validate email
			if (signUpRequest.getEmail() != null && !signUpRequest.getEmail().isEmpty()) {
				String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
				if (!signUpRequest.getEmail().matches(emailRegex)) {
					throw new AppException("Invalid email format.");
				}
				if (userRepository.existsByEmail(signUpRequest.getEmail())) {
					throw new AppException("Email id " + signUpRequest.getEmail() + " already exists.");
				}
			}

			// Validate mobile number
			if (!signUpRequest.getMobileNumber().matches("[9876]\\d{9}")) {
				throw new AppException("Invalid mobile number: " + signUpRequest.getMobileNumber());
			}

			if (userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
				throw new AppException("Mobile number " + signUpRequest.getMobileNumber() + " already exists.");
			}

			User user = new User(signUpRequest.getName(), ".", signUpRequest.getMobileNumber(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), idClassStandard,
					signUpRequest.getMobileNumber(), "Student", signUpRequest.getSecondaryLanguage());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null) {
				throw new AppException("User Role not set.");
			}
			user.setRoles(Collections.singleton(userRole));

			User savedUser = userRepository.save(user);

			Student student = new Student();
			student.setUser(savedUser);
			student.setIdClassStandard(idClassStandard);
			student.setIdLangauage(idSecondaryLanguage);
			student.setIdState(idState);
			student.setIdStudentMedium(idMedium);
			student.setIdSyllabus(idSyllabus);
			student.setIsProfileEdited(Boolean.FALSE);
			student.setSchoolName(signUpRequest.getSchoolName());
			student.setRemarks(signUpRequest.getRemarks());
			student = studentRepository.save(student);

			emailService.sendWelcomeEmailOnSuccessfulSignUpWithCredentials(signUpRequest.getEmail(),
					signUpRequest.getName(), signUpRequest.getMobileNumber(), signUpRequest.getMobileNumber(),
					signUpRequest.getPassword(), signUpRequest.getState(), signUpRequest.getClassStandard(),
					signUpRequest.getSyllabus());

			String message = "Dear " + signUpRequest.getName()
					+ ", \\n Thank you for Registering on V-Learning.\\n Happy Learning, Vista Foundation.";
			String templateId = "1207161960052810550";

			smsHorizonService.smsService(signUpRequest.getMobileNumber(), message, templateId);

			return signUpRequest;
		} catch (AppException e) {
			signUpRequest.setIsValid(false);
			signUpRequest.setMessage(e.getLocalizedMessage());
			return signUpRequest;
		} catch (Exception e) {
			signUpRequest.setIsValid(false);
			signUpRequest.setMessage("An error occurred during signup.");

			// Log the exception for further investigation
			logger.error(e.getLocalizedMessage());
			return signUpRequest;
		}

	}
	
	@Deprecated
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Override
	public Document<?> uploadBulkSignupData(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();
		Map<String, Object> dataMap = new HashMap<>();

		List<String> errorLogList = new ArrayList<>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			List<ClassStandard> classStandards = classStandardRepository.findAll();
			List<State> states = stateRepository.findAll();
			List<StudentMedium> studentMediums = studentMediumRepository.findAll();
			List<Language> languages = languageRepository.findAll();

			errorLogList.add("Bulk Signup Process started at: " + Instant.now());
			errorLogList.add("Bulk Signup Uploaded started by the user : " + userPrincipal.getUserSurId() + ".");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			} // creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();

			CsvMapper csvMapper = new CsvMapper();

			// read CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			readAll.forEach(o -> {

				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("name") || !header.containsKey("email") || !header.containsKey("mobile_number")
						|| !header.containsKey("password") || !header.containsKey("syllabus")
						|| !header.containsKey("class_standard") || !header.containsKey("state")
						|| !header.containsKey("medium") || !header.containsKey("secondary_language")
						|| !header.containsKey("school_name") || !header.containsKey("remarks")) {
					throw new AppException("Invalid file or missing header in the upload csv.");
				}
			});

			List<BulkSignupDTO> signUpList = new ArrayList<>();

			int totalRecords = 0;

			Iterator<Object> readAllIterator = readAll.listIterator();

			List<BulkSignupDTO> ll = new ArrayList<>();

			while (readAllIterator.hasNext()) {
				BulkSignupDTO subscr = new BulkSignupDTO();
				Map<String, String> row = (Map<String, String>) readAllIterator.next();
				totalRecords++;

				BulkSignupDTO bulkSignupDTO = new BulkSignupDTO();

				if (!classStandards.contains(row.get("class_standard"))) {
					bulkSignupDTO.setRemarks("invalid class_standard");
					bulkSignupDTO.setIsValid(false);
				}
				if (!states.contains(row.get("state"))) {
					bulkSignupDTO.setRemarks("invalid state");
					bulkSignupDTO.setIsValid(false);
				}
				if (!studentMediums.contains(row.get("medium"))) {
					bulkSignupDTO.setRemarks("invalid student Medium");
					bulkSignupDTO.setIsValid(false);
				}
				if (!languages.contains(row.get("secondary_language"))) {
					bulkSignupDTO.setRemarks("invalid language");
					bulkSignupDTO.setIsValid(false);
				}

				bulkSignupDTO.setName(row.get("name"));
				bulkSignupDTO.setEmail(row.get("email"));
				bulkSignupDTO.setMobileNumber(row.get("mobile_number"));
				bulkSignupDTO.setPassword(row.get("password"));
				bulkSignupDTO.setSyllabus(row.get("syllabus"));
				bulkSignupDTO.setClassStandard(row.get("class_standard"));
				bulkSignupDTO.setState(row.get("state"));
				bulkSignupDTO.setMedium(row.get("medium"));
				bulkSignupDTO.setSecondaryLanguage(row.get("secondary_language"));
				bulkSignupDTO.setSchoolName(row.get("school_name"));
				bulkSignupDTO.setRemarks("Bulk Signup Created: User id:" + userPrincipal.getUserSurId() + " User Name:"
						+ userPrincipal.getUsername() + " initiated signup  at " + Instant.now()
						+ ". remarks From csv :" + row.get("remarks"));
				bulkSignupDTO.setMessage("user signed up successfully");

				ll.add(bulkSignupDTO);

				subscr = bulkSignup(bulkSignupDTO);
				signUpList.add(subscr);

				if (subscr.getIsValid()) {
					errorLogList.add("user has been signedup under username : " + bulkSignupDTO.getMobileNumber()
							+ " and password : " + bulkSignupDTO.getPassword());
				} else {
					errorLogList.add("error occured at row : " + totalRecords + " due to " + subscr.getMessage());
				}
			}
			int sucessfullCount = (int) signUpList.stream().filter(s -> s.getIsValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

			errorLogList.add("Total no of records : " + totalRecords + ".");
			errorLogList.add("Total no of records valid: " + sucessfullCount + ".");
			errorLogList.add("Total no of records invalid: " + rejectedRecords + ".");
			errorLogList.add("Bulk Signup Process ended at: " + Instant.now());

			dataMap.put("updatedRecord", signUpList);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", sucessfullCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			errorLogList.add("Error: " + e.getMessage());
			errorLogList.add("Bulk Signup Process Terminated  at: " + Instant.now());
			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			String fileName = "BulkSignupLog" + Instant.now() + ".txt";
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/signup/", fileName, logFile);

				boolean isDeletedLogFile = logFile.delete();
				boolean isDeletedFile = file.delete();
				logger.info("Logs file and batch file deleted from the system : " + isDeletedLogFile + " - "
						+ isDeletedFile);

			} catch (IOException e) {

				logger.error(e.getLocalizedMessage());

			}
		}

		return result;

	}


	@Override
	public Document<?> uploadBulkSignupDataV2(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();
		Map<String, Object> dataMap = new HashMap<>();
		List<String> errorLogList = new ArrayList<>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			List<ClassStandard> classStandards = classStandardRepository.findAll();
			List<State> states = stateRepository.findAll();
			List<StudentMedium> studentMediums = studentMediumRepository.findAll();
			List<Language> languages = languageRepository.findAll();
			
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null) {
				throw new AppException("User Role not set.");
			}

			errorLogList.add("Bulk Signup Process started at: " + Instant.now());
			errorLogList.add("Bulk Signup Uploaded started by the user : " + userPrincipal.getUserSurId() + ".");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			}

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// Read CSV data
			MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
					.readValues(file);
			
			// read CSV data
			List<Map<String, String>> readAll = mappingIterator.readAll();
			
			readAll.forEach(o -> {

				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("name") || !header.containsKey("email") || !header.containsKey("mobile_number")
						|| !header.containsKey("password") || !header.containsKey("syllabus")
						|| !header.containsKey("class_standard") || !header.containsKey("state")
						|| !header.containsKey("medium") || !header.containsKey("secondary_language")
						|| !header.containsKey("school_name")) {
					throw new AppException("Invalid file or missing header in the upload csv.");
				}
			});
			List<BulkSignupDTO> signUpList = new ArrayList<>();
			int totalRecords = 0;

			for (Map<String, String> row : readAll) {
				totalRecords++;
				
				BulkSignupDTO bulkSignupDTO = new BulkSignupDTO();
				bulkSignupDTO.setName(row.get("name"));
				bulkSignupDTO.setEmail(row.get("email"));
				bulkSignupDTO.setMobileNumber(row.get("mobile_number"));
				bulkSignupDTO.setPassword(row.get("password"));
				bulkSignupDTO.setSyllabus(row.get("syllabus"));
				bulkSignupDTO.setClassStandard(row.get("class_standard"));
				bulkSignupDTO.setState(row.get("state"));
				bulkSignupDTO.setMedium(row.get("medium"));
				bulkSignupDTO.setSecondaryLanguage(row.get("secondary_language"));
				bulkSignupDTO.setSchoolName(row.get("school_name"));
				bulkSignupDTO.setRemarks("Bulk Signup Created: User id:" + userPrincipal.getUserSurId() + " User Name:"
						+ userPrincipal.getUsername() + " initiated signup  at " + Instant.now()
						+ ". remarks From csv :" + row.get("remarks"));
				bulkSignupDTO.setMessage("user will be signed up.");
				
				
				// Validate mobile number
				if (!bulkSignupDTO.getMobileNumber().matches("[98762]\\d{9}")) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid mobile number: " + bulkSignupDTO.getMobileNumber());
				}

				// Validate mobile number existence in the database
				boolean isExist = userRepository.existsByMobileNumber(row.get("mobile_number"));
				if (isExist) {
				    bulkSignupDTO.setIsValid(false);
				    bulkSignupDTO.setMessage("User Already Exists.");
				}

				// Validate email
				if (bulkSignupDTO.getEmail() != null && !bulkSignupDTO.getEmail().isEmpty()) {
					String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
					if (!bulkSignupDTO.getEmail().matches(emailRegex)) {
						bulkSignupDTO.setIsValid(false);
						bulkSignupDTO.setMessage("Invalid email format.");
					}
					if (userRepository.existsByEmail(bulkSignupDTO.getEmail())) {
						bulkSignupDTO.setIsValid(false);
						bulkSignupDTO.setMessage("Email id " + bulkSignupDTO.getEmail() + " already exists.");
					}
				}

				// Validate ClassStandard
				Long classStandardId = Long.parseLong(row.get("class_standard"));
				boolean isValidClassStandard = classStandards.stream()
						.anyMatch(cs -> cs.getIdClassStandard().equals(classStandardId));
				if (!isValidClassStandard) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid class standard ID in CSV: " + classStandardId);
				}

				// Validate State
				Long stateId = Long.parseLong(row.get("state"));
				boolean isValidState = states.stream().anyMatch(s -> s.getIdState().equals(stateId));
				if (!isValidState) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid state ID in CSV: " + stateId);
				}

				// Validate StudentMedium
				Long studentMediumId = Long.parseLong(row.get("medium"));
				boolean isValidStudentMedium = studentMediums.stream()
						.anyMatch(sm -> sm.getIdStudentMedium().equals(studentMediumId));
				if (!isValidStudentMedium) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid student medium ID in CSV: " + studentMediumId);
				}

				// Validate Language
				Long languageId = Long.parseLong(row.get("secondary_language"));
				boolean isValidLanguage = languages.stream().anyMatch(l -> l.getIdLanguage().equals(languageId));
				if (!isValidLanguage) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid language ID in CSV: " + languageId);
				}
				
				
				boolean isMobileAvailable = signUpList.stream()
						.anyMatch(m -> m.getMobileNumber().equals(bulkSignupDTO.getMobileNumber()));
				if (isMobileAvailable) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Mobile number exists in CSV.");
				}


				// Check if password is null or empty
				if (bulkSignupDTO.getPassword() == null || bulkSignupDTO.getPassword().trim().isEmpty()) {
					String defaultPassword = "vista@"
							+ bulkSignupDTO.getMobileNumber().substring(bulkSignupDTO.getMobileNumber().length() - 4);
					bulkSignupDTO.setPassword(defaultPassword);
				}
				
				if (bulkSignupDTO.getClassStandard().equals("4") || bulkSignupDTO.getState().equals("6")
						|| bulkSignupDTO.getSyllabus().equals("4")) {
					bulkSignupDTO.setIsValid(false);
					bulkSignupDTO.setMessage("Invalid meta data .");
				}
		
				signUpList.add(bulkSignupDTO);
			}

			// Additional operations...
			long successfulCount = signUpList.stream().filter(BulkSignupDTO::getIsValid).count();
			long rejectedRecords = totalRecords - successfulCount;

	        if(rejectedRecords<=0) {
	        	Document<?> queryResponse = createUserAndStudent.createUsersAndStudents(signUpList, userPrincipal.getUserSurId().toString());
	        	System.out.println(queryResponse.getData());
	        	errorLogList.add("Uploaded Successfully.");
	        }
			if(rejectedRecords>0)
				errorLogList.add("Please Correct all the details in the csv and upload again.");

			errorLogList.add("Total no of records: " + totalRecords + ".");
			errorLogList.add("Total no of valid records: " + successfulCount + ".");
			errorLogList.add("Total no of invalid records: " + rejectedRecords + ".");
			errorLogList.add("Bulk Signup Process ended at: " + Instant.now());

			// Prepare data for response
			dataMap.put("updatedRecord", signUpList);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", successfulCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			// Upload log file to S3 bucket
			String fileName = "BulkSignupLog" + Instant.now() + ".txt";
			try (FileWriter writer = new FileWriter(fileName)) {
				for (String log : errorLogList) {
					writer.write(log + System.lineSeparator());
				}
				File logFile = new File(fileName);
				fileUploadService.uploadFileToS3Bucket("/logs/creation/signup/", fileName, logFile);
				boolean isDeletedLogFile = logFile.delete();
				boolean isDeletedFile = file.delete();
				logger.info("Logs file and batch file deleted from the system: " + isDeletedLogFile + " - "
						+ isDeletedFile);
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}

			// Log and return the result
			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	/**
	 * Handles the manual signup process for a new user.
	 *
	 * This method processes a manual signup request by accepting a
	 * `ManualSignupDTO` object containing user information. It validates the
	 * provided information, including mobile number, password, syllabus, class
	 * standard, state, medium, secondary language, name, and email. If the
	 * information is valid, a new user is created and saved in the database. The
	 * method also sends a welcome email to the user and a confirmation SMS.
	 *
	 * @param signUpRequest The `ManualSignupDTO` containing user information for
	 *                      signup.
	 * @return A `Document` containing the result of the signup operation, including
	 *         the status code, a message, and the provided user information. If the
	 *         signup is successful, the status code is 200; otherwise, an error
	 *         message and an appropriate status code are provided.
	 */
	@Override
	public Document<ManualSignupDTO> manualSignup(ManualSignupDTO signUpRequest) {
		Document<ManualSignupDTO> doc = new Document<>();
		try {
			Long idSyllabus;
			Long idClassStandard;
			Long idState;
			Long idMedium;
			Long idSecondaryLanguage;
			if (signUpRequest.getMobileNumber() == null || signUpRequest.getMobileNumber().trim().isEmpty()) {
				throw new AppException("Mobile number is required.");
			}
			// Check if password is null or empty
			if (signUpRequest.getPassword() == null || signUpRequest.getPassword().trim().isEmpty()) {
				String defaultPassword = "vista@"
						+ signUpRequest.getMobileNumber().substring(signUpRequest.getMobileNumber().length() - 4);
				signUpRequest.setPassword(defaultPassword);
			}

			// Validate syllabus
			if (signUpRequest.getSyllabus() == null || signUpRequest.getSyllabus().trim().isEmpty()) {
				throw new AppException("Syllabus is required.");
			} else {
				try {
					idSyllabus = Long.parseLong(signUpRequest.getSyllabus());
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(idSyllabus);
					if (syllabus == null || syllabus.getIdSyllabus().equals(4L)) {
						throw new AppException("Invalid syllabus.");
					}
					signUpRequest.setSyllabus(syllabus.getSyllabusName());
				} catch (NumberFormatException e) {
					throw new AppException("Syllabus must be an integer.");
				}
			}

			// Validate class standard
			if (signUpRequest.getClassStandard() == null || signUpRequest.getClassStandard().trim().isEmpty()) {
				throw new AppException("Class standard is required.");
			} else {
				try {
					idClassStandard = Long.parseLong(signUpRequest.getClassStandard());

					ClassStandard classStandard = classStandardRepository.findByIdClassStandard(idClassStandard);
					if (classStandard == null || classStandard.getIdClassStandard().equals(4L)) {
						throw new AppException("Invalid class standard.");
					}
					idClassStandard = classStandard.getIdClassStandard();
					signUpRequest.setClassStandard(classStandard.getClassStandadName());
				} catch (NumberFormatException e) {
					throw new AppException("Class standard must be an integer.");
				}
			}

			// Validate state
			if (signUpRequest.getState() == null || signUpRequest.getState().trim().isEmpty()) {
				throw new AppException("State is required.");
			} else {
				try {
					idState = Long.parseLong(signUpRequest.getState());
					State state = stateRepository.findByIdState(idState);
					if (state == null || state.getIdState().equals(6L)) {
						throw new AppException("Invalid state.");
					}
					signUpRequest.setState(state.getState());
				} catch (NumberFormatException e) {
					throw new AppException("State must be an integer.");
				}
			}

			// Validate medium
			if (signUpRequest.getMedium() == null || signUpRequest.getMedium().trim().isEmpty()) {
				throw new AppException("Medium is required.");
			} else {
				try {
					idMedium = Long.parseLong(signUpRequest.getMedium());
					StudentMedium studentMedium = studentMediumRepository.findByIdStudentMedium(idMedium);
					if (studentMedium == null) {
						throw new AppException("Invalid medium.");
					}
					signUpRequest.setMedium(studentMedium.getMedium());
				} catch (NumberFormatException e) {
					throw new AppException("Medium must be an integer.");
				}
			}

			// Validate secondary language
			if (signUpRequest.getSecondaryLanguage() == null || signUpRequest.getSecondaryLanguage().trim().isEmpty()) {
				throw new AppException("Secondary language is required.");
			} else {
				try {
					idSecondaryLanguage = Long.parseLong(signUpRequest.getSecondaryLanguage());
					Language secondaryLanguage = languageRepository.findByIdLanguage(idSecondaryLanguage);
					if (secondaryLanguage == null) {
						throw new AppException("Invalid secondary language.");
					}
					signUpRequest.setSecondaryLanguage(secondaryLanguage.getLanguage());
				} catch (NumberFormatException e) {
					throw new AppException("Secondary language must be an integer.");
				}
			}

			// Validate name
			if (signUpRequest.getName() == null || signUpRequest.getName().trim().isEmpty()) {
				throw new AppException("Name is required.");
			}

			// Validate email
			if (signUpRequest.getEmail() != null && !signUpRequest.getEmail().isEmpty()) {
				String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
				if (!signUpRequest.getEmail().matches(emailRegex)) {
					throw new AppException("Invalid email format.");
				}
				if (userRepository.existsByEmail(signUpRequest.getEmail())) {
					throw new AppException("Email id " + signUpRequest.getEmail() + " already exists.");
				}
			}

			// Validate mobile number

			if (!signUpRequest.getMobileNumber().matches("\\d+")) {
				throw new AppException("Mobile number must contain only digits.");
			}
			if (signUpRequest.getMobileNumber().length() != 10) {
				throw new AppException("Mobile number must be a 10-digit number.");
			}
			if (!signUpRequest.getMobileNumber().matches("[98762].*")) {
				throw new AppException("Invalid mobile number: " + signUpRequest.getMobileNumber());
			}

			if (userRepository.existsByMobileNumber(signUpRequest.getMobileNumber())) {
				throw new AppException("Mobile number " + signUpRequest.getMobileNumber() + " already exists.");
			}

			User user = new User(signUpRequest.getName(), ".", signUpRequest.getMobileNumber(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), idClassStandard,
					signUpRequest.getMobileNumber(), "Student", signUpRequest.getSecondaryLanguage(),"Mannual Signup");

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null) {
				throw new AppException("User Role not set.");
			}
			user.setRoles(Collections.singleton(userRole));

			User savedUser = userRepository.save(user);

			Student student = new Student();
			student.setUser(savedUser);
			student.setIdClassStandard(idClassStandard);
			student.setIdLangauage(idSecondaryLanguage);
			student.setIdState(idState);
			student.setIdStudentMedium(idMedium);
			student.setIdSyllabus(idSyllabus);
			student.setIsProfileEdited(Boolean.FALSE);
			student.setSchoolName(signUpRequest.getSchoolName());
			student.setRemarks(signUpRequest.getRemarks());
			student = studentRepository.save(student);

			emailService.sendWelcomeEmailOnSuccessfulSignUpWithCredentials(signUpRequest.getEmail(),
					signUpRequest.getName(), signUpRequest.getMobileNumber(), signUpRequest.getMobileNumber(),
					signUpRequest.getPassword(), signUpRequest.getState(), signUpRequest.getClassStandard(),
					signUpRequest.getSyllabus());

			String message = "Dear " + signUpRequest.getName()
					+ ", \\n Thank you for Registering on V-Learning.\\n Happy Learning, Vista Foundation.";
			String templateId = "1207161960052810550";

			smsHorizonService.smsService(signUpRequest.getMobileNumber(), message, templateId);

			doc.setData(signUpRequest);
			doc.setMessage("User signup successful.");
			doc.setStatusCode(200);
		} catch (AppException e) {

			doc.setData(signUpRequest);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
		} catch (Exception e) {

			doc.setData(signUpRequest);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage("Internal server error.");

			logger.error(e.getLocalizedMessage());

		}

		return doc;
	}

	@Override
	public Boolean isValidDevice(String jwt, Device device, User user) {
		boolean result = true;

		try {
			String checkDevice = "";
			if (device.isMobile()) {
				checkDevice = "MOBILE";
				
			} else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {
				checkDevice = "TV";
				
			} else {
				checkDevice = "WEB";
			}

			UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(user.getUserSurId(),
					checkDevice);
			if (userDevice != null) {

				if (!jwt.trim().equalsIgnoreCase(userDevice.getJwtToken())) {

					result = false;
					return result;
				}
			} else {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Handles the process of new user signup for the "Student" role with mobile
	 * nmbr and name.
	 *
	 * @param signUpRequest The request body containing user signup information.
	 * @param device        The device information from the request.
	 * @return A Document containing information about the result of the signup. The
	 *         data in the Document includes user details, subscription information,
	 *         and an optional plan summary. The HTTP status code in the Document
	 *         corresponds to the result status. - Successful signup and login: HTTP
	 *         200 OK - Error during signup: Appropriate HTTP error status code
	 */
	@Override
	public Document<?> newSignup(NewSignupRequestV4DTO signUpRequest, Device device,String ip, String userAgent) {
		Document<Object> doc = new Document<>();
		try {
			User result = new User();

			Boolean phoneFlag = userRepository.existsByMobileNumber(signUpRequest.getMobileNumber());

			if (phoneFlag)
				throw new AppException("This Mobile Number is already existed in our record.");

			if (!signUpRequest.getMobileNumber().matches("\\d+")) {
				throw new AppException("Mobile number must contain only digits.");
			}
			if (signUpRequest.getMobileNumber().length() != 10) {
				throw new AppException("Mobile number must be a 10-digit number.");
			}
			if (!signUpRequest.getMobileNumber().matches("[98762].*")) {
				throw new AppException("Invalid mobile number: " + signUpRequest.getMobileNumber());
			}

			// Check if password is null or empty
			String password = "vista@2024";

			if (signUpRequest.getOtp() == null || signUpRequest.getOtp().isEmpty())
				throw new AppException("Please provide the OTP, sent to the user mobile number.");

			MobileOtp mobileOtp = authService.verifyMobileOTP(signUpRequest.getMobileNumber(), signUpRequest.getOtp());
			if (mobileOtp == null)
				throw new AppException("Please request the otp first.");

			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(),
					signUpRequest.getMobileNumber(), "", password, -1L, signUpRequest.getMobileNumber(), "Student",
					"NA",signUpRequest.getSignUpSource());
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));

			result = userRepository.save(user);

			// creation of student after user
			Student student = new Student();
			student.setUser(result);
			student.setIdClassStandard(-1L);
			student.setIdLangauage(-1L);
			student.setIdState(-1L);
			student.setIdStudentMedium(-1L);
			student.setIdSyllabus(-1L);
			student.setIsProfileEdited(Boolean.FALSE);

			student = studentRepository.save(student);
			if (signUpRequest.getPlatform() != null)
				signupPlatformRepository.save(new SignupPlatform(signUpRequest.getPlatform(), result));

			CartSummaryDTO studentOrderDetails = null;

			Map<String, Object> login = new HashMap<>();

			if (signUpRequest.getPlan() == null && signUpRequest.getCouponCode() != null
					&& !signUpRequest.getCouponCode().isEmpty()) {

				LocalDateTime currentDateTime = LocalDateTime.now();

				Coupon coupon = couponRepository.findByCouponCode(signUpRequest.getCouponCode());

				if (coupon == null)
					throw new AppException("Invalid coupon code");

				if (coupon.getCouponType().equalsIgnoreCase("TRIAL")) {

					Redemption redemption = redemptionRepository.findByCouponCodeAndIdVlUser(coupon.getCouponCode(),
							user.getUserSurId());

					if (Boolean.FALSE.equals(coupon.getIsActive()))
						throw new AppException("Sorry, the coupon code entered is not currently active");

					if (currentDateTime.isAfter(coupon.getEndDate()))
						throw new AppException("Sorry, This coupon has expired.");

					if (redemption != null) {
						if (redemption.getIdVlUser().equals(user.getUserSurId())) {
							throw new AppException("Sorry, This coupon has already been claimed.");
						}
					}
					if (coupon.getTotalCount() < coupon.getUsedCount())
						throw new AppException("Sorry, This coupon has already been claimed.");

					LocalDateTime dateMonth = Instant.now().atZone(zoneIndia).toLocalDateTime()
							.plusDays(coupon.getExtensionDuration());

					StudentOrder studentOrder = new StudentOrder();
					studentOrder.setUserSurId(user.getUserSurId());
					studentOrder.setOrderDate(Instant.now());
					studentOrder.setOrderStatus("Success");
					studentOrder.setNetAmount(0.0F);
					studentOrder.setGstAmount(0.0F);
					studentOrder.setTotalAmount(0.0F);
					studentOrder.setCouponCode(signUpRequest.getCouponCode());
					studentOrder.setActualAmount(0.0F);

					studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
					studentOrder = studentOrderRepository.save(studentOrder);

					if (studentOrder != null) {
						// save all staging student subscription data
						List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
						StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
						// stagingStudentSubscription.setIdBatch(newSubscriptionPlanDTO.getIdBatch());
						stagingStudentSubscription.setIdProduct(93L);
						stagingStudentSubscription.setIdProductGroup(51L);
						stagingStudentSubscription.setIdproductLine(11L);
						stagingStudentSubscription.setIdStudent(student.getIdStudent());
						stagingStudentSubscription.setPurchaseAmount("0.0");
						stagingStudentSubscription.setPurchaseLevel("AD_FREE_TRIAL");// productCd
						stagingStudentSubscription.setPurchaseType("NEW");
						stagingStudentSubscription.setSubscriptionType("7 Days");
						stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
						stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
						stagingStudentSubscriptions.add(stagingStudentSubscription);
						stagingStudentSubscription.setPaymentStatus("Success");
						stagingStudentSubscription.setUserSurId(user.getUserSurId());
						stagingStudentSubscriptionRepository.saveAll(stagingStudentSubscriptions);

					}

					StudentSubscription studentSubscription = new StudentSubscription();
					studentSubscription.setIdProduct(93L);
					studentSubscription.setIdBatch(null);
					studentSubscription.setIdProductGroup(51L);
					studentSubscription.setIdproductLine(11L);
					studentSubscription.setIdStudent(student.getIdStudent());
					studentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
					studentSubscription.setLastPaymentDate(Instant.now());
					studentSubscription.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
					studentSubscription.setPurchaseAmount("0.0");
					studentSubscription.setPurchaseDate(Instant.now());
					studentSubscription.setPurchaseLevel("AD_FREE_TRIAL");
					studentSubscription.setSubscriptionEndDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
					studentSubscription.setSubscriptionType("7 Days");
					studentSubscription.setPurchaseType("NEW");
					studentSubscription.setActiveFlag(Boolean.TRUE);
					studentSubscription.setFreeFlag(Boolean.TRUE);
					studentSubscription.setUserSurId(user.getUserSurId());
					studentSubscription = studentSubscriptionRepository.save(studentSubscription);

					Redemption redemptionSave = new Redemption();
					redemptionSave.setCouponCode(coupon.getCouponCode());
					redemptionSave.setIdVlUser(user.getUserSurId());
					redemptionSave.setIdProductPricing(0L);
					redemptionSave.setRedemptionDate(LocalDateTime.now());

					redemptionRepository.save(redemptionSave);
					Long updatedCount = coupon.getUsedCount() + 1;
					coupon.setUsedCount(updatedCount);
					couponRepository.save(coupon);

				}

			}

			if (signUpRequest.getPlan() != null) {

				studentOrderDetails = studentSubscriptionServiceImplV2
						.saveNewUserSubscriptionNewSignup(signUpRequest.getPlan(), user);
			} else {

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(signUpRequest.getMobileNumber(), password));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				ClassStandard standard = null;
				Syllabus syllabus = null;
				State state = null;

				User findByUsername = userRepository.findByUsername(authentication.getName());

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
						Student stu = studentRepository.findByUser(findByUsername);
						if (student != null) {
							standard = classRepository.findByIdClassStandard(stu.getIdClassStandard());

							login.put("stateObject", state);

							login.put("classStandardObject", standard);
							login.put("syllabusObject", syllabus);
						} else
							throw new NullPointerException("Role Not Found");
					}

					StudentPostLoginDTO spl = studentSubscriptionService
							.checkExistingSubscriptionLogin(user.getUserSurId());
					Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

					UserMetaClaim umc = new UserMetaClaim(-1L, -1L, -1L, isSubsribed);
					String jwt = tokenProvider.generateToken(authentication, device, umc);
					String jwtRefresh = tokenProvider.refreshToken(authentication, device);
					boolean validFlag = false;

					login.put("accessToken", jwt);
					login.put("refreshToken", jwtRefresh);
					login.put("validFlag", validFlag);

					JSONObject loc = geoLocation.generateGeoLocation(ip);
					authService.checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt,
							findByUsername.getUserSurId(), findByUsername.getUsername(),
							findByUsername.getClassStandard(), device,loc,userAgent);

				}
			}
			System.out.println(user.toString());
			doc.setData(studentOrderDetails == null ? login : studentOrderDetails);
			doc.setMessage("User SignUp Successful. Logged In to the Application");
			doc.setStatusCode(200);

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Entered UserName or Email or Phone Number is Already Registered");
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

	/**
	 * 
	 * @author Abdul Elahi Handles the process of filling additional details after a
	 *         user signs up as a student.
	 *
	 * @param dto The request body containing additional information for the user
	 *            signup.
	 * @return A Document containing information about the result of filling details
	 *         after signup. The data in the Document includes details about the
	 *         user and subscription information. The HTTP status code in the
	 *         Document corresponds to the result status. - Successful fill: HTTP
	 *         200 OK - Error during fill: Appropriate HTTP error status code
	 */
	@Override
	public Document<?> fillDetailsAfterSignup(NewSignupClassInfoRequestDTO dto, Device device,String ip, String userAgent) {
		Document<Map<String, Object>> document = new Document<>();
		try {
			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}
			User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			if (user == null)
				throw new AppException("invalid User");

			if (user.getRegisteredAs().equalsIgnoreCase("Student")) {
				Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
				if (student == null)
					throw new AppException("No Student Record found ");
				Map<String, Object> login = new HashMap<>();

				if (student.getIdClassStandard() == -1L && student.getIdState() == -1L
						&& student.getIdSyllabus() == -1L) {

					ClassStandard standard = classRepository.findByIdClassStandard(dto.getIdClassStandard());
					if (standard == null)
						throw new AppException("Invalid IdClassStandard.");
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(dto.getIdSyllabus());
					if (syllabus == null)
						throw new AppException("Invalid IdSyllabus.");
					State state = stateRepository.findByIdState(dto.getIdState());
					if (state == null)
						throw new AppException("Invalid IdState.");
					Language language = languageRepository.findByIdLanguage(dto.getIdSecondaryLanguage());
					if (language == null)
						throw new AppException("Invalid IdSecondaryLanguage.");
					StudentMedium studentMedium = studentMediumRepository.findByIdStudentMedium(dto.getIdMedium());
					if (studentMedium == null)
						throw new AppException("Invalid IdStudentMedium.");

					if (dto.getDeviceId() != null && !dto.getDeviceId().equals("")) {

					}

					user.setClassStandard(standard.getIdClassStandard());
					user.setSecondaryLanguage(language.getLanguage());
					userRepository.save(user);

					student.setIdClassStandard(standard.getIdClassStandard());
					student.setIdLangauage(language.getIdLanguage());
					student.setIdState(state.getIdState());
					student.setIdStudentMedium(studentMedium.getIdStudentMedium());
					student.setIdSyllabus(syllabus.getIdSyllabus());
					Student stu = studentRepository.save(student);

					if (stu != null) {

						login.put("tokenType", "Bearer");
						login.put("username", user.getUsername());
						login.put("firstName", user.getFirstName());
						login.put("lastName", user.getLastName());
						login.put("email", user.getEmail());
						login.put("secondary_language", user.getSecondaryLanguage());
						login.put("mobileNumber", user.getMobileNumber());
						login.put("registeredAs", user.getRegisteredAs());
						login.put("userSurId", user.getUserSurId());
						login.put("userProfilePic", user.getUserProfilePic());
						login.put("subscriptionFlag",
								studentSubscriptionService.checkExistingSubscriptionLogin(user.getUserSurId()));
						login.put("stateObject", state);
						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);

						StudentPostLoginDTO spl = studentSubscriptionService
								.checkExistingSubscriptionLogin(user.getUserSurId());
						Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

						UserMetaClaim umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
								state.getIdState(), isSubsribed);
						String jwt = tokenProvider.generateToken(authentication, device, umc);
						String jwtRefresh = tokenProvider.refreshToken(authentication, device);
						boolean validFlag = true;

						login.put("accessToken", jwt);
						login.put("refreshToken", jwtRefresh);
						login.put("validFlag", validFlag);
						
						JSONObject loc = geoLocation.generateGeoLocation(ip);

						authService.checkAndUpdateUserDeviceId(dto.getDeviceId(), jwt, user.getUserSurId(),
								user.getUsername(), user.getClassStandard(), device,loc,userAgent);

					}

				}
				document.setData(login);
				document.setStatusCode(HttpStatus.OK.value());
				document.setMessage("Successfully Filled The Class Standard Information");

			}

		} catch (Exception e) {
			e.printStackTrace();
			document.setData(null);
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			document.setMessage(e.getLocalizedMessage());

		}
		return document;
	}

	/**
	 * @author Abdul Elahi
	 *
	 *         Checks if the current user's password matches the default signup
	 *         password.
	 *
	 * @return A {@code Document<Boolean>} indicating whether the password matches.
	 *         The result is wrapped in a Document object containing status
	 *         information.
	 *
	 * @throws AppException If the user is invalid.
	 */
	@Override
	public Document<Boolean> defaultSignupPasswordValidation() {
		Document<Boolean> result = new Document<>();
		try {
			User user = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			Optional<User> optionalUser = Optional.ofNullable(user);
			if (optionalUser.isPresent()) {
				User currentUser = optionalUser.get();

				String password = "vista@2024";
				String existingPassword = currentUser.getPassword();

				if (passwordEncoder.matches(password, existingPassword)) {
					result.setData(true);
					result.setMessage("User password matched our default password");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					result.setData(false);
					result.setMessage("User password did not match our default password");
					result.setStatusCode(HttpStatus.OK.value());
				}

			} else {
				throw new AppException("Invalid User");
			}

			return result;

		} catch (Exception e) {
			result.setData(null);
			result.setMessage("An error occurred during password validation.");
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.error("Error in defaultSignupPasswordValidation method", e);
			return result;
		}
	}

}
