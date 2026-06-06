
package co.vistafoundation.vlearning.auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.config.CustomDevice;
import co.vistafoundation.vlearning.auth.dto.AfterSignUpMetadataDTO;
import co.vistafoundation.vlearning.auth.dto.DeviceInfoDTO;
import co.vistafoundation.vlearning.auth.dto.GoogleLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.IdObjectDTO;
import co.vistafoundation.vlearning.auth.dto.InternalLoginRequest;
import co.vistafoundation.vlearning.auth.dto.LoginRequest;
import co.vistafoundation.vlearning.auth.dto.MobileNumberLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.dto.SubscriptionClickDTO;
import co.vistafoundation.vlearning.auth.dto.TeacherInfoDTO;
import co.vistafoundation.vlearning.auth.dto.TeacherSubjectDTO;
import co.vistafoundation.vlearning.auth.dto.UserListDTO;
import co.vistafoundation.vlearning.auth.dto.UserMetaClaim;
import co.vistafoundation.vlearning.auth.model.ForgotPassword;
import co.vistafoundation.vlearning.auth.model.MobileOtp;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.SubscriptionClick;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.ForgotPasswordRepository;
import co.vistafoundation.vlearning.auth.repository.MobileOtpRepository;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.SubscriptionClickRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.CustomUserDetailsService;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailServiceImpl;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.marketer.model.Marketer;
import co.vistafoundation.vlearning.marketer.model.Vendor;
import co.vistafoundation.vlearning.marketer.repository.MarketerRepository;
import co.vistafoundation.vlearning.marketer.repository.VendorRepository;
import co.vistafoundation.vlearning.notification.dto.FcmDTO;
import co.vistafoundation.vlearning.notification.service.FirebaseMessagingService;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.FreeSignUp;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.VideoStreamingLogRepository;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.dto.DeviceHistoryDTO;
import co.vistafoundation.vlearning.user.dto.UserFetchDTO;
import co.vistafoundation.vlearning.user.dto.UserProfileDTO;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.StudentMedium;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherSubject;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.model.UserDeviceHistory;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentMediumRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.TeacherSubjectRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceHistoryRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user_activity.config.DynamoDbHelper;
import co.vistafoundation.vlearning.user_activity.config.LoggedUserHandler;
import co.vistafoundation.vlearning.utils.GoogleIDTokenVerification;
import co.vistafoundation.vlearning.utils.ObjectDataCompare;
import co.vistafoundation.vlearning.utils.SMSHorizonService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;


/**
 * @author vk
 *
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
	GoogleIDTokenVerification googleIDTokenVerification;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	SMSHorizonService smsHorizonService;
	
	@Autowired
	SubscriptionClickRepository  subscriptionClickRepository; 
	 
	 
	@Value("${sms.flag}")
	private Boolean smsFlag;

	@Value("${app.lockDuration}")
	private Integer lockDuration;

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	@Autowired
	MobileOtpRepository mobileOtpRepository;

	@Autowired
	@Lazy
	StudentSubscriptionService studentSubscriptionService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	FirebaseMessagingService firebaseService;

	@Autowired
	MarketerRepository marketerRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	LoggedUserHandler loggedUserHandler;

	@Autowired
	VideoStreamingLogRepository videoStreamingLogRepository;

	@Autowired
	DynamoDbHelper dynamoDbHelper;

	@Autowired
	CaptureGeoLocation geoLocation;
	
	@Autowired
	UserDeviceHistoryRepository  userDeviceHistoryRepository;

	@Value("${test.otp}")
	private String testOtp;
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Document<?> authenticateUser(LoginRequest loginRequest, Device device, String ip, String userAgent) {
		
		
		System.out.println(".................*******.................");

		System.out.println("User Agent  "+userAgent);
		System.out.println("Mobile  "+device.isMobile());
		System.out.println("Normal  "+device.isNormal());
		System.out.println("Tablet  "+device.isTablet());
		
		
		System.out.println(".................*******.................");
		
	
		
		
		
		Document doc = new Document<>();
		try {
			User userAuth = userRepository.findByEmailOrMobileNumber(loginRequest.getUsernameOrEmail());
			if (userAuth != null) {
				if (!userAuth.getActiveFlag()) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.FORBIDDEN.value());
					doc.setMessage("Your account is deactivated, please contact us");
					return doc;
				}
				if (!userAuth.getRegisteredAs().equals("Student")) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.FORBIDDEN.value());
					doc.setMessage("Please login as internal user!");
					return doc;
				}
			} else if (userAuth == null) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.NOT_FOUND.value());
				doc.setMessage("Your credentials not found in our records, please register");
				return doc;
			}

			/**
			 * @author NAVEEN KUMAR A
			 * 
			 *         Below implementation will check for brute force attack multiple login
			 *         attempts will be controlled to 3 for per user. Lock Duration will be
			 *         available on application properties.
			 * 
			 */

			// user locking changes starts here

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
										+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
												.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")));
				}

				// check user credentials
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userAuth.getUsername(), loginRequest.getPassword()));

			} catch (Exception e) {
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
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
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
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
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
			JSONObject loc = geoLocation.generateGeoLocation(ip);

			System.out.println("==========================================");
			System.out.println(loc.toString());
			System.out.println("==========================================");
			
			String userAgent1 = "";
			if (device.isMobile()) {
				userAgent1 = userAgent;
				System.out.println("mob in method user agent  "+userAgent);
			} else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {
				userAgent1 = userAgent;
				System.out.println("tv in method user agent  "+userAgent);
			} else {
				userAgent1 = getUserAgent(userAgent);
				System.out.println("web in method user agent  "+userAgent);
			}

			saveDevice(device,loc.toString(), userAgent1,findByUsername.getUserSurId(),loginRequest.getDeviceId());
			
			if (findByUsername != null) {

				login.put("location", loc);
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
				} else if (findByUsername.getRegisteredAs().equals("Parent")) {
					Parent parent = parentRepository.findByUser(findByUsername);
					if (parent != null) {
						standard = classRepository.findByIdClassStandard(parent.getIdClassStandard());
						syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
						state = stateRepository.findByIdState(parent.getIdState());
						login.put("classStandardObject", standard);
						login.put("syllabusObject", syllabus);
						login.put("stateObject", state);
					} else
						throw new NullPointerException("Role Not Found");
				} else {
					throw new NullPointerException("Role Not Found : " + findByUsername.getRegisteredAs());
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

				checkAndUpdateUserDeviceId(loginRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
						findByUsername.getUsername(), findByUsername.getClassStandard(), device, loc, userAgent);
				/**
				 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
				 *         issues raised vl-843 , un-comment this to initialize the sso for all
				 *         the user
				 * 
				 *
				 *         if (!checkFlag) throw new AppException( "A device is currently logged
				 *         " + "in through this account. Please logout from that device in order
				 *         " + "to log in.");
				 */
			}

			doc.setData(login);
			doc.setMessage(!constrainFlag ? "Login Successful" : "Your account has been logged out in another device.");
			doc.setStatusCode(200);

			return doc;
		} catch (Exception exp) {
			exp.printStackTrace();
			if (exp.getLocalizedMessage().equalsIgnoreCase("Bad credentials")) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				doc.setMessage(exp.getLocalizedMessage());
				return doc;
			}
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document authenticateInternalUser(InternalLoginRequest loginRequest, Device device) {
		Document doc = new Document<>();
		try {
			User userAuth = userRepository.findByEmailOrMobileNumber(loginRequest.getUsernameOrEmail());

			if (userAuth != null) {
				if (!userAuth.getActiveFlag()) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.FORBIDDEN.value());
					doc.setMessage("Your account is deactivated, please contact us");
					return doc;
				}
				if (userAuth.getRegisteredAs().equals("Student")) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.FORBIDDEN.value());
					doc.setMessage("You are not authorized to login here!");
					return doc;
				}

				if (!userAuth.getRegisteredAs().equalsIgnoreCase(loginRequest.getRole())) {

					doc.setData(null);
					doc.setStatusCode(HttpStatus.UNAUTHORIZED.value());
					doc.setMessage("Please select valid role!");
					return doc;
				}
			} else if (userAuth == null) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.NOT_FOUND.value());
				doc.setMessage("Your credentials not found in our records, please register");
				return doc;
			}

			/**
			 * @author NAVEEN KUMAR A
			 * 
			 *         Below implementation will check for brute force attack multiple login
			 *         attempts will be controlled to 3 for per user. Lock Duration will be
			 *         available on application properties.
			 * 
			 */

			// user locking changes starts here

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
										+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
												.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
				}

				// check user credentials
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userAuth.getUsername(), loginRequest.getPassword()));

			} catch (Exception e) {
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
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
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
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
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
			UserMetaClaim umc = new UserMetaClaim(null, null, null, null);
			String jwt = tokenProvider.generateToken(authentication, device, umc);
			// String jwtRefresh = tokenProvider.refreshToken(authentication, device);

			User findByUsername = userRepository.findByUsername(authentication.getName());

			Map<String, Object> login = new HashMap<>();

			if (findByUsername != null) {
				login.put("accessToken", jwt);
				login.put("refreshToken", null);
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
				login.put("subscriptionFlag", false);
				login.put("classStandardObject", null);
				login.put("syllabusObject", null);
				login.put("stateObject", null);
			}
			doc.setData(login);
			doc.setMessage("Login Successful");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception exp) {
			if (exp.getLocalizedMessage().equalsIgnoreCase("Bad credentials")) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				doc.setMessage(exp.getLocalizedMessage());
				return doc;
			}
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	@Transactional
	public User registerUser(SignUpRequest signUpRequest) {

		User result = new User();

		// Creating user's account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
				signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getClassStandard(),
				signUpRequest.getMobileNumber(), signUpRequest.getRole(), signUpRequest.getSecondaryLanguage());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (signUpRequest.getRole().equals("Student")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// creation of student after user
			Student student = new Student();
			student.setIdClassStandard(signUpRequest.getClassStandard());
			student.setIdSyllabus(signUpRequest.getIdSyllabus());
			student.setUser(result);
			student.setIsProfileEdited(Boolean.FALSE);
			student = studentRepository.save(student);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email
			ClassStandard classStd = classRepository.findByIdClassStandard(signUpRequest.getClassStandard());

			if (classStd == null)
				throw new AppException("Invalid IdClassStandard.");

			String studentEmail = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName() + " " + signUpRequest.getLastName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String classStandard = classStd.getClassStandadName();

			emailService.sendWelcomeEmailToStudentOnSuccessfulSignUp(studentEmail, fullName, userName, mobileNumber,
					role, classStandard);

		} else if (signUpRequest.getRole().equals("Parent")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
			if (userRole == null)
				throw new AppException("User Role not set.");
			Role userRoleStudent = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRoleStudent == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// creation of parent from user
			Parent parent = new Parent();
			parent.setUser(result);
			parent = parentRepository.save(parent);

			// creation of student-user, student after parent and user
			User userStudent = new User();
			userStudent.setFirstName(signUpRequest.getFirstName());
			userStudent.setLastName(signUpRequest.getLastName());
			userStudent.setClassStandard(signUpRequest.getClassStandard());
			userStudent.setEmail(signUpRequest.getEmail());
			userStudent.setMobileNumber(signUpRequest.getMobileNumber());
			userStudent.setRoles(Collections.singleton(userRoleStudent));
			// storing the username for user reference
			String tempUserName = signUpRequest.getEmail().split("@")[0] + RandomStringUtils.randomNumeric(4);
			userStudent.setUsername(tempUserName);
			// storing the password for user reference before encryptions
			String tempPassword = RandomStringUtils.randomAlphanumeric(6);
			userStudent.setPassword(passwordEncoder.encode(tempPassword));

			System.out.println("Student username: " + tempUserName);
			System.out.println("Student password: " + tempPassword);

			// by default student profile will be created
			userStudent.setRegisteredAs("Student");
			// get user second language from signup request
			userStudent.setSecondaryLanguage(signUpRequest.getSecondaryLanguage());
			userStudent = userRepository.save(userStudent);

			Student student = new Student();
			student.setIdClassStandard(signUpRequest.getClassStandard());
			student.setParent(parent);
			student.setUser(userStudent);
			student.setIdSyllabus(signUpRequest.getIdSyllabus());
			student.setIsProfileEdited(Boolean.FALSE);
			student = studentRepository.save(student);

			// Extracting Necessary Fields for Sending Email
			ClassStandard classStd = classRepository.findByIdClassStandard(signUpRequest.getClassStandard());

			if (classStd == null)
				throw new AppException("Invalid IdClassStandard.");

			String parentEmail = parent.getUser().getEmail();
			String parentFullName = parent.getUser().getFirstName() + " " + parent.getUser().getLastName();
			String parentUserName = parent.getUser().getUsername();
			String mobileNumber = parent.getUser().getMobileNumber();
			String role = signUpRequest.getRole();
			String classStandard = classStd.getClassStandadName();

			String kidUserName = tempUserName;
			String kidPassword = tempPassword;

			emailService.sendWelcomeEmailToParentOnSuccessfulSignUp(parentEmail, parentFullName, parentUserName,
					mobileNumber, role, classStandard, kidUserName, kidPassword);
		} else if (signUpRequest.getRole().equals("Telecaller")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_TELECALLER);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email

			String email = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String password = signUpRequest.getPassword();

			emailService.sendEmailToTelecallerWithCredentials(email, fullName, userName, mobileNumber, role, password);

		} else if (signUpRequest.getRole().equals("Moderator")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_MODERATOR);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email

			String email = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String password = signUpRequest.getPassword();

			emailService.sendEmailToModeratorWithCredentials(email, fullName, userName, mobileNumber, role, password);

		} else if (signUpRequest.getRole().equals("Blogger")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_BLOGGER);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email

			String email = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String password = signUpRequest.getPassword();

			emailService.sendEmailToBloggerWithCredentials(email, fullName, userName, mobileNumber, role, password);

		}
		/*
		 * else if (signUpRequest.getRole().equals("Admin")) { Role userRole =
		 * roleRepository.findByRoleName(RoleName.ROLE_ADMIN) .orElseThrow(() -> new
		 * AppException("User Role not set."));
		 * user.setRoles(Collections.singleton(userRole)); result =
		 * userRepository.save(user);
		 * 
		 * } else if (signUpRequest.getRole().equals("Teacher")) { Role userRole =
		 * roleRepository.findByRoleName(RoleName.ROLE_TEACHER) .orElseThrow(() -> new
		 * AppException("User Role not set."));
		 * user.setRoles(Collections.singleton(userRole)); result =
		 * userRepository.save(user);
		 * 
		 * //creating of teacher after user Teacher teacher = new Teacher();
		 * teacher.setActiveFlag(Boolean.TRUE);
		 * teacher.setEmailId(signUpRequest.getEmail()); teacher.setUser(result);
		 * teacher = teacherRepository.save(teacher); }
		 */
		return result;
	}

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

	@Override
	public Document<?> resetPassword(Long idVLUser, String oldPassword, String newPassword) {
		Document<User> doc = new Document<>();
		try {
			if (idVLUser == 0 || idVLUser == null) {
				throw new NullPointerException("User Id Cannot be null");
			}

			User findByUserSurId = userRepository.findByUserSurId(idVLUser);

			if (findByUserSurId == null)
				throw new AppException("Invalid user.");

			String existingPassword = findByUserSurId.getPassword();

			if (passwordEncoder.matches(oldPassword, existingPassword)) {
				findByUserSurId.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(findByUserSurId);
				doc.setData(new User());
				doc.setMessage("Password Reset Successful");
				doc.setStatusCode(200);
				return doc;
			} else {
				throw new AppException("Entered Old Password is Incorrect");
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document forgotPassword(String forgotPasswordUsername) {

		Document doc = new Document<>();
		try {
			if (forgotPasswordUsername == null) {
				throw new NullPointerException("Username Cannot be null");
			}

			User existingUser = userRepository.findByUsername(forgotPasswordUsername);

			if (existingUser == null) {
				throw new NullPointerException("Entered Username Not Found");
			}

			if (!existingUser.getActiveFlag()) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.FORBIDDEN.value());
				doc.setMessage("Your account is deactivated, please contact us");
				return doc;
			}

			String randomString = RandomStringUtils.random(10, true, true);

			ForgotPassword existingForgotPasswordObj = forgotPasswordRepo.findByUserSurId(existingUser.getUserSurId());

			if (existingForgotPasswordObj == null) {
				ForgotPassword forgotPassword = new ForgotPassword();
				forgotPassword.setForgotPasswordUsername(existingUser.getUsername());
				forgotPassword.setRandomString(randomString);
				forgotPassword.setUserSurId(existingUser.getUserSurId());

				ForgotPassword saved = forgotPasswordRepo.save(forgotPassword);
			} else {
				existingForgotPasswordObj.setForgotPasswordUsername(existingUser.getUsername());
				existingForgotPasswordObj.setRandomString(randomString);
				existingForgotPasswordObj.setUserSurId(existingUser.getUserSurId());

				ForgotPassword saved = forgotPasswordRepo.save(existingForgotPasswordObj);
			}

			// backup
			// Check if role is Student then Send Mail to parent saying that Your Kid has
			// requested for resetting password

			if (!existingUser.getEmail().isEmpty() || existingUser.getEmail() != null) {
				Document response = emailService.sendForgotPasswordEmail(existingUser.getEmail(),
						existingUser.getUsername(), randomString, existingUser.getFirstName(),
						existingUser.getUserSurId());
				if (response.getStatusCode() == 200) {
					doc.setData("Success");
					doc.setStatusCode(200);
					doc.setMessage("Check E-Mail for Resetting Password.");
					return doc;
				} else {
					throw new Exception(
							"Failed to send Password Reset Mail.Check your Internet Connectivity or Conatct Vistas Learning Admin.");
				}
			} else {
				doc.setData("Success");
				doc.setStatusCode(200);
				doc.setMessage("Check E-Mail for Resetting Password.");
				return doc;
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Error Occured.");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document verifyForgotPassword(Long userSurId, String randomString, String forgotPasswordUsername,
			String newPassword) {

		Document doc = new Document<>();
		try {
			if (userSurId == 0 || userSurId == null) {
				throw new NullPointerException("User Id Cannot be zero or Null");
			} else if (randomString == null) {
				throw new NullPointerException("Random String Cannot be Null");
			} else if (forgotPasswordUsername == null) {
				throw new NullPointerException(" Username Cannot be Null");
			}

			ForgotPassword findByUserSurId = forgotPasswordRepo.findByUserSurId(userSurId);

			if (findByUserSurId == null)
				throw new AppException("Reset password link is expired or invalid user.");

			User user = userRepository.findByUserSurId(userSurId);

			if (user == null)
				throw new AppException("Invalid User data");

			if (!user.getActiveFlag()) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.FORBIDDEN.value());
				doc.setMessage("Your account is deactivated, please contact us");
				return doc;
			}

			if (findByUserSurId.getForgotPasswordUsername().equals(forgotPasswordUsername)
					&& findByUserSurId.getRandomString().equals(randomString)) {

				user.setPassword(passwordEncoder.encode(newPassword));
				User saved = userRepository.save(user);

				// delete the record from forgot password
				forgotPasswordRepo.delete(findByUserSurId);

				doc.setData("Success");
				doc.setMessage("New Password Set Successful");
				doc.setStatusCode(200);
				return doc;
			} else {
				throw new NullPointerException("Email Or Random String Mismatch. Contact Admin for more info");
			}
		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Error Occured.");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@Override
	public Document<Page<UserListDTO>> fetchAllUserLists(UserFetchDTO userFetchDTO) {

		Document<Page<UserListDTO>> doc = new Document<>();
		try {
			Pageable paging = PageRequest.of(userFetchDTO.getPage(), userFetchDTO.getSize());
			Page<UserListDTO> usersLists;

			if (userFetchDTO.getDateFrom() != null && userFetchDTO.getDateTo() != null
					&& userFetchDTO.getRoleName().equals("Student")) {
				usersLists = userRepository.findAllSpecificRoleWithDate(userFetchDTO.getRoleName(),
						userFetchDTO.getDateFrom(), userFetchDTO.getDateTo(), paging);
			} else if (userFetchDTO.getDateFrom() != null && userFetchDTO.getDateTo() != null
					&& !userFetchDTO.getRoleName().equals("Student")) {
				usersLists = userRepository.findAllRoleOtherWithDate(userFetchDTO.getRoleName(),
						userFetchDTO.getDateFrom(), userFetchDTO.getDateTo(), paging);
			} else if (!userFetchDTO.getRoleName().equals("Student")) {
				usersLists = userRepository.findAllRoleOther(userFetchDTO.getRoleName(), userFetchDTO.getFirstName(),
						userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(), paging);
			} else {
				usersLists = userRepository.findAllSpecificRole(userFetchDTO.getRoleName(), userFetchDTO.getFirstName(),
						userFetchDTO.getEmail(), userFetchDTO.getMobileNumber(), paging);
			}

			if (usersLists.isEmpty()) {
				doc.setData(usersLists);
				doc.setMessage("No Users Available");
				doc.setStatusCode(200);
				return doc;
			} else {
				doc.setData(usersLists);
				doc.setMessage("All VLUser Lists...");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@Override
	public Document<List<Language>> getAllLanguage() {
		Document<List<Language>> doc = new Document<>();
		try {
			List<Language> languageList = languageRepository.findAll();

			if (languageList.isEmpty())
				throw new NullPointerException("Language list is Empty");

			doc.setData(languageList);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}

		return doc;
	}

	/*
	 * Free sign up and watch videos
	 * 
	 * 
	 */

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Document registerUserforFree(FreeSignUp signUpRequest, Device device) {

		Document doc = new Document<>();

		try {

			User result = new User();

			// Creating user's account
			User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(),
					signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getClassStandard(),
					signUpRequest.getMobileNumber(), signUpRequest.getRole(), signUpRequest.getSecondaryLanguage());

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// creation of student after user
			Student student = new Student();
			student.setIdClassStandard(signUpRequest.getClassStandard());
			student.setIdSyllabus(signUpRequest.getIdSyllabus());
			student.setUser(result);
			student.setIsProfileEdited(Boolean.FALSE);
			student = studentRepository.save(student);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email
			ClassStandard classStd = classRepository.findByIdClassStandard(signUpRequest.getClassStandard());

			if (classStd == null)
				throw new AppException("invalid  Classstandard.");

			String studentEmail = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName() + " " + signUpRequest.getLastName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String classStandard = classStd.getClassStandadName();

			emailService.sendWelcomeEmailToStudentOnSuccessfulSignUp(studentEmail, fullName, userName, mobileNumber,
					role, classStandard);

			// 5 is the productLine Id which is fixed for academic

			ProductGroup productGroup = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(
					signUpRequest.getClassStandard(), 5L, signUpRequest.getIdSyllabus());

			if (productGroup == null) {
				throw new NullPointerException("Cannot find the product group ");
			}

			Product prod = productRepository
					.findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(
							productGroup.getIdProductGroup(), signUpRequest.getClassStandard(),
							signUpRequest.getIdSubject(), signUpRequest.getIdSyllabus(), Boolean.TRUE);

			if (prod == null) {
				throw new NullPointerException("Cannot find the product");
			}

			StudentSubscription studentSubscription = new StudentSubscription();

			studentSubscription.setActiveFlag(true);
			studentSubscription.setIdProduct(prod.getIdProduct());
			studentSubscription.setIdProductGroup(productGroup.getIdProductGroup());
			studentSubscription.setIdStudent(student.getIdStudent());
			studentSubscription.setIdproductLine(5L);
			studentSubscription.setFreeFlag(true);
			studentSubscription.setUserSurId(result.getUserSurId());

			studentSubscription = studentSubscriptionRepository.save(studentSubscription);

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			// passing value null since its depricated api
			String jwt = tokenProvider.generateToken(authentication, device, new UserMetaClaim(null, null, null, null));
			String jwtRefresh = tokenProvider.refreshToken(authentication, device);

			User findByUsername = userRepository.findByUsername(authentication.getName());

			if (findByUsername == null)
				throw new AppException("invalid  Username.");

			Map<String, Object> login = new HashMap<>();

			if (findByUsername != null) {
				login.put("accessToken", jwt);
				login.put("refreshToken", jwtRefresh);
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
			}

			doc.setData(login);
			doc.setMessage("Request Successfull");
			doc.setStatusCode(200);
			return doc;

		}

		catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}

	}

	@Override
	public Document<Page<TeacherInfoDTO>> getAllTeachersByIdSubjectAndSyllabusAndClassStandrdAndCategory(Long idSubject,
			Long idClassStandard, Long idSyllabus, String category, int pageNumber) {

		Document<Page<TeacherInfoDTO>> result = new Document<>();

		Page<TeacherInfoDTO> page = null;

		try {

			List<TeacherInfoDTO> temp = new ArrayList<>();
			Pageable pageable = PageRequest.of(pageNumber, 12);

			// get Extracurricular teachers.
			if (category.equalsIgnoreCase("EXTRA_CUR")) {
				/*
				 * List<Batch> batchList = batchRepository.findAll(); if (batchList.isEmpty())
				 * throw new NullPointerException(“Batch List Empty.“);
				 */
				if (idSubject != -1) {
					// List<Batch> finalbatchList = new ArrayList<>();
//					List<Product> productList = productRepository.findByIdClassStandardAndIdSubjectAndIdSyllabus(4L,
//							idSubject, 4L);
//					if (productList.isEmpty())
//						throw new NullPointerException("No Products Available for Particular Subject");
					/*
					 * batchList.stream().forEach(b -> { productList.stream().forEach(p -> { if
					 * (b.getIdProduct().equals(p.getIdProduct())) { finalbatchList.add(b); } });
					 * });
					 */
					/*
					 * if (finalbatchList.isEmpty()) throw new NullPointerException(“No Teachers
					 * found for the selected Subject.“); HashSet<Long> teacherIds = new
					 * HashSet<>(); finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));
					 * 
					 */
					/*
					 * List<Long> finalTeacherIdList = new ArrayList<>(teacherIds); List<Teacher>
					 * teachers = teacherRepository.findByIdTeacherIn(finalTeacherIdList);
					 */
					List<Teacher> teachers = teacherRepository.findTeacherSubjectsidsubjectAndidSyllabus(idSubject, 4L);
					if (teachers.isEmpty() && pageNumber > 0) {
						throw new AppException("No More Teachers Available");
					} else if (teachers.isEmpty())
						throw new NullPointerException("No Teachers Available");
					teachers.stream().forEach(t -> {
						TeacherInfoDTO tid = new TeacherInfoDTO();
						tid.setExpLevel(t.getExpLevel());
						tid.setIdTeacher(t.getIdTeacher());
						tid.setTeacherDesc(t.getTeacherDesc());
						tid.setTeacherName(t.getTeacherName());
						tid.setUserImage(t.getTeacherImage());
						tid.setPrimarySubejct(t.getPrimarySubject());
						tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
						tid.setTeacherHeader(t.getTeacherHeader());
						List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);
						List<TeacherSubjectDTO> tsd = new ArrayList<>();
						if (!ts.isEmpty()) {
							ts.stream().forEach(tempSub -> {
								TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();
								tsTemp.setProficiency(tempSub.getProficiency());
								tsTemp.setSubject(
										subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
								tsTemp.setSyllabus(
										syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus()).getSyllabusName());
								tsd.add(tsTemp);
							});
						}
						tid.setTeacherSubjects(tsd);
						temp.add(tid);
					});
				} else {
//					List<Batch> finalbatchList = new ArrayList<>();
					System.out.println("Fetching all teachers for extracurricular");
					// hardcoded the value of idclassStandard and idSyllabus to NA applicable value
//					List<Product> productList = productRepository.findByIdClassStandardAndIdSyllabus(4L, 4L);
//					if (productList.isEmpty())
//						throw new NullPointerException("No Academic Products Available for Particular Class Standard.");
					/*
					 * batchList.stream().forEach(b -> { productList.stream().forEach(p -> { if
					 * (b.getIdProduct().equals(p.getIdProduct())) { finalbatchList.add(b); } });
					 * }); if (finalbatchList.isEmpty()) throw new NullPointerException(“No Teachers
					 * found for the selected class Standard”); HashSet<Long> teacherIds = new
					 * HashSet<>(); finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));
					 * List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);
					 */

					List<Teacher> teachers = teacherRepository.findByCategory("EXTRA_CUR");
					if (teachers.isEmpty() && pageNumber > 0) {
						throw new AppException("No More Teachers Available.");
					} else if (teachers.isEmpty())
						throw new NullPointerException("No Teachers Available.");
					teachers.stream().forEach(t -> {
						TeacherInfoDTO tid = new TeacherInfoDTO();
						tid.setExpLevel(t.getExpLevel());
						tid.setIdTeacher(t.getIdTeacher());
						tid.setTeacherDesc(t.getTeacherDesc());
						tid.setTeacherName(t.getTeacherName());
						tid.setUserImage(t.getTeacherImage());
						tid.setPrimarySubejct(t.getPrimarySubject());
						tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
						tid.setTeacherHeader(t.getTeacherHeader());
						List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);
						List<TeacherSubjectDTO> tsd = new ArrayList<>();
						if (!ts.isEmpty()) {
							ts.stream().forEach(tempSub -> {
								TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();
								tsTemp.setProficiency(tempSub.getProficiency());
								tsTemp.setSubject(
										subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
								tsTemp.setSyllabus(
										syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus()).getSyllabusName());
								tsd.add(tsTemp);
							});
						}
						tid.setTeacherSubjects(tsd);
						temp.add(tid);
					});
				}
			}

			else {
				// get Academic teachers.

				// when class standard filter not applied.
				if (idClassStandard == -1) {

					if (idSubject == -1 && idSyllabus == -1) {
						System.out.println("get all teachers");
						List<Teacher> teachers = teacherRepository.findAllByCategory("ACADEMIC");

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						if (teachers.isEmpty())
							throw new AppException("Teachers list is Empty.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});
							}
							tid.setTeacherSubjects(tsd);
							temp.add(tid);
						});

					} else if (idSubject == -1 && idSyllabus != -1) {

						System.out.println("get all teachers for syllabus");

						List<Teacher> teachers = teacherRepository.findAllByCategory("ACADEMIC");

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacherAndIdSyllabus(t,
									idSyllabus);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});

								tid.setTeacherSubjects(tsd);
								temp.add(tid);
							}

						});

					} else if (idSubject != -1 && idSyllabus == -1)

					{
						System.out.println("get all teachers for subjects");

						Subject sub = subjectRepository.findByIdSubject(idSubject);

						if (sub == null)
							throw new NullPointerException("Subject not found.");

						List<Teacher> teachers = teacherRepository.findAllByPrimarySubject(sub.getSubjectName());

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});

							}

							tid.setTeacherSubjects(tsd);
							temp.add(tid);

						});

					}

					else {

						System.out.println("get all teachers for subject and syllabus");

						Subject sub = subjectRepository.findByIdSubject(idSubject);

						if (sub == null)
							throw new NullPointerException("Subject not found.");

						List<Teacher> teachers = teacherRepository.findByPrimarySubject(sub.getSubjectName());

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacherAndIdSyllabusAndIdSubject(t,
									idSyllabus, idSubject);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});

								tid.setTeacherSubjects(tsd);
								temp.add(tid);
							}

						});

					}
				}

				else {
					// when class standard filter applied.

					List<Batch> batchList = batchRepository.findAll();
					if (batchList.isEmpty())
						throw new NullPointerException("Batch List Empty.");

					if (idSubject != -1 && idSyllabus != -1)

					{
						List<Batch> finalbatchList = new ArrayList<>();

						List<Product> productList = productRepository
								.findByIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(idClassStandard, idSubject,
										idSyllabus, Boolean.TRUE);
						if (productList.isEmpty())
							throw new NullPointerException(
									"No Academic Products Available for Particular Class Standard , Syllabus & Subject.");

						batchList.stream().forEach(b -> {

							productList.stream().forEach(p -> {

								if (b.getIdProduct().equals(p.getIdProduct())) {
									finalbatchList.add(b);
								}

							});
						});

						if (finalbatchList.isEmpty())
							throw new NullPointerException(
									"No Teachers found for the selected Class Standard , Syllabus & Subject.");

						HashSet<Long> teacherIds = new HashSet<>();

						finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));

						List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);

						List<Teacher> teachers = teacherRepository.findByIdTeacherIn(finalTeacherIdList);

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});
							}
							tid.setTeacherSubjects(tsd);
							temp.add(tid);
						});

					}

					else if (idSubject == -1 && idSyllabus != -1) {

						List<Batch> finalbatchList = new ArrayList<>();

						List<Product> productList = productRepository.findByIdClassStandardAndIdSyllabusAndActiveFlag(
								idClassStandard, idSyllabus, Boolean.TRUE);

						if (productList.isEmpty())
							throw new NullPointerException(
									"No Academic Products Available for Particular Class Standard , Syllabus & Subject.");

						for (Batch b : batchList) {

							for (Product p : productList) {

								System.out.println(b.getIdProduct() + "," + p.getIdProduct());

								if (b.getIdProduct().equals(p.getIdProduct())) {
									finalbatchList.add(b);
								}

							}
						}

						if (finalbatchList.isEmpty())
							throw new NullPointerException(
									"No Teachers found for the selected Class Standard , Syllabus & Subject.");

						HashSet<Long> teacherIds = new HashSet<>();

						finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));

						List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);

						List<Teacher> teachers = teacherRepository.findByIdTeacherIn(finalTeacherIdList);

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});
							}
							tid.setTeacherSubjects(tsd);
							temp.add(tid);
						});

					} else if (idSubject != -1 && idSyllabus == -1) {

						List<Batch> finalbatchList = new ArrayList<>();

						List<Product> productList = productRepository.findByIdClassStandardAndIdSubjectAndActiveFlag(
								idClassStandard, idSubject, Boolean.TRUE);

						if (productList.isEmpty())
							throw new NullPointerException(
									"No Academic Products Available for Particular Class Standard , Syllabus & Subject.");

						batchList.stream().forEach(b -> {

							productList.stream().forEach(p -> {

								if (b.getIdProduct().equals(p.getIdProduct())) {
									finalbatchList.add(b);
								}

							});
						});

						if (finalbatchList.isEmpty())
							throw new NullPointerException(
									"No Teachers found for the selected Class Standard , Syllabus & Subject.");

						HashSet<Long> teacherIds = new HashSet<>();

						finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));

						List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);

						List<Teacher> teachers = teacherRepository.findByIdTeacherIn(finalTeacherIdList);

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});
							}
							tid.setTeacherSubjects(tsd);
							temp.add(tid);
						});

					} else {

						List<Batch> finalbatchList = new ArrayList<>();
						System.out.println("Fetching all teachers for a particular classes.");

						List<Product> productList = productRepository
								.findByIdClassStandardAndActiveFlag(idClassStandard, Boolean.TRUE);

						if (productList.isEmpty())
							throw new NullPointerException(
									"No Academic Products Available for Particular Class Standard.");

						batchList.stream().forEach(b -> {

							productList.stream().forEach(p -> {

								if (b.getIdProduct().equals(p.getIdProduct())) {
									finalbatchList.add(b);
								}

							});
						});

						if (finalbatchList.isEmpty())
							throw new NullPointerException("No Teachers found for the selected class Standard");

						HashSet<Long> teacherIds = new HashSet<>();

						finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));

						List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);

						List<Teacher> teachers = teacherRepository.findByIdTeacherIn(finalTeacherIdList);

						if (teachers.isEmpty() && pageNumber > 0) {
							throw new AppException("No More Teachers Available.");
						} else if (teachers.isEmpty())
							throw new NullPointerException("No Teachers Available.");

						teachers.stream().forEach(t -> {

							TeacherInfoDTO tid = new TeacherInfoDTO();
							tid.setExpLevel(t.getExpLevel());
							tid.setIdTeacher(t.getIdTeacher());
							tid.setTeacherDesc(t.getTeacherDesc());
							tid.setTeacherName(t.getTeacherName());
							tid.setUserImage(t.getTeacherImage());
							tid.setPrimarySubejct(t.getPrimarySubject());
							tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
							tid.setTeacherHeader(t.getTeacherHeader());

							List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

							List<TeacherSubjectDTO> tsd = new ArrayList<>();

							if (!ts.isEmpty()) {
								ts.stream().forEach(tempSub -> {

									TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

									tsTemp.setProficiency(tempSub.getProficiency());
									tsTemp.setSubject(
											subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
									tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus())
											.getSyllabusName());
									tsd.add(tsTemp);
								});
							}
							tid.setTeacherSubjects(tsd);
							temp.add(tid);
						});

					}

				}

			}

			if (temp.isEmpty())
				throw new AppException("No Teacher's available");

			if ((pageable.getPageSize() * pageable.getPageNumber() >= temp.size())) {
				result.setData(null);
				result.setMessage("No More Teachers Available");
				result.setStatusCode(HttpStatus.OK.value());
			} else {

				int start = (int) pageable.getOffset();
				int end = (start + pageable.getPageSize()) > temp.size() ? temp.size()
						: (start + pageable.getPageSize());
				page = new PageImpl<>(temp.subList(start, end), pageable, temp.size());

				result.setData(page);
				result.setMessage("Request Successfull");
				result.setStatusCode(200);

			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;
	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getTeacherProfile(Long idTeacher) {

		Document doc = new Document<>();
		try {

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			TeacherInfoDTO teacherInfoDTO = new TeacherInfoDTO();

			if (teacher == null) {
				throw new NullPointerException("Cannot find the teacher");
			}

			// List<TeacherSubject> findByTeacher_IdTeacher(Long idTeacher);

			List<TeacherSubject> teacherSubList = teacherSubjectRepository
					.findByTeacher_IdTeacher(teacher.getIdTeacher());

			if (teacherSubList.isEmpty()) {
				throw new NullPointerException("Cannot find the teacherSubject");
			}

			List<TeacherSubjectDTO> tsd = new ArrayList<>();

			if (!teacherSubList.isEmpty()) {
				teacherSubList.stream().forEach(tempSub -> {

					TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

					tsTemp.setProficiency(tempSub.getProficiency());
					tsTemp.setSubject(subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
					tsTemp.setSyllabus(syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus()).getSyllabusName());
					tsd.add(tsTemp);
				});
			}

			teacherInfoDTO.setTeacherSubjects(tsd);
			teacherInfoDTO.setIdTeacher(teacher.getIdTeacher());
			teacherInfoDTO.setTeacherDesc(teacher.getTeacherDesc());
			teacherInfoDTO.setTeacherHeader(teacher.getTeacherHeader());
			teacherInfoDTO.setCategory(teacher.getCategory());
			teacherInfoDTO.setTeacherName(teacher.getTeacherName());
			teacherInfoDTO.setUserImage(teacher.getTeacherImage());
			teacherInfoDTO.setEmail(teacher.getEmailId());
			teacherInfoDTO.setIntroVideo(teacher.getIntroVideo());
			teacherInfoDTO
					.setJoinedStudent(studentSubscriptionRepository.getJoinedStudentCount(teacher.getIdTeacher()));

			doc.setData(teacherInfoDTO);
			doc.setMessage("Successful");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Error Occured.");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAllState() {

		Document result = new Document();
		try {
			List<State> response = stateRepository.findAllByOrderByDisplayOrderAsc();
			if (response.isEmpty()) {
				throw new NullPointerException("State not found");
			} else {
				result.setData(response);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());

			}

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document userRegistrationNewSignUp(NewSignupRequestDTO signUpRequest, Device device, String ip,
			String userAgent) {

		Document doc = new Document<>();
		try {
			JSONObject loc = geoLocation.generateGeoLocation(ip);
			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

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
				} else {
					throw new NullPointerException("Role Not Found : " + findByUsername.getRegisteredAs());
				}

				StudentPostLoginDTO spl = studentSubscriptionService
						.checkExistingSubscriptionLogin(findByUsername.getUserSurId());
				Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

				UserMetaClaim umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
						state.getIdState(), isSubsribed);
				String jwt = tokenProvider.generateToken(authentication, device, umc);
				String jwtRefresh = tokenProvider.refreshToken(authentication, device);

				login.put("accessToken", jwt);
				login.put("refreshToken", jwtRefresh);
				login.put("location", loc);

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

			}

			doc.setData(login);
			doc.setMessage("User SignUp Successful. Logged In to the Application");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document takeMetadataAfterSignup(AfterSignUpMetadataDTO afterSignUpMetadataDTO) {

		Document result = new Document<>();

		try {
			if (afterSignUpMetadataDTO == null) {
				throw new NullPointerException("All the Fields are Mandatory");
			}

			User userObject = userRepository.findByUserSurId(afterSignUpMetadataDTO.getIdVlUser());

			if (userObject.getRegisteredAs().equals("Student")) {

				Student student = studentRepository.findByUser(userObject);
				student.setIdClassStandard(afterSignUpMetadataDTO.getIdClassStandard());
				student.setIdLangauage(afterSignUpMetadataDTO.getIdSecondaryLanguage());
				student.setIdState(afterSignUpMetadataDTO.getIdState());
				student.setIdStudentMedium(afterSignUpMetadataDTO.getIdMedium());
				student.setIdSyllabus(afterSignUpMetadataDTO.getIdSyllabus());
				student.setIsProfileEdited(Boolean.FALSE);
				Student updated = studentRepository.save(student);

				if (afterSignUpMetadataDTO.getIdSubject() != null) {
					// 5 is the productLine Id which is fixed for academic

					ProductGroup productGroup = productGroupRepository
							.findByIdClassStandardAndIdProductLineAndIdSyllabus(
									afterSignUpMetadataDTO.getIdClassStandard(), 5L,
									afterSignUpMetadataDTO.getIdSyllabus());

					if (productGroup == null) {
						throw new NullPointerException("Cannot find the product group ");
					}

					Product prod = productRepository
							.findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(
									productGroup.getIdProductGroup(), afterSignUpMetadataDTO.getIdClassStandard(),
									afterSignUpMetadataDTO.getIdSubject(), afterSignUpMetadataDTO.getIdSyllabus(),
									Boolean.TRUE);

					if (prod == null) {
						throw new NullPointerException("Cannot find the product");
					}

					StudentSubscription studentSubscription = new StudentSubscription();

					studentSubscription.setActiveFlag(true);
					studentSubscription.setIdProduct(prod.getIdProduct());
					studentSubscription.setIdProductGroup(productGroup.getIdProductGroup());
					studentSubscription.setIdStudent(student.getIdStudent());
					studentSubscription.setIdproductLine(5L);
					studentSubscription.setFreeFlag(true);
					studentSubscription.setUserSurId(userObject.getUserSurId());

					studentSubscription = studentSubscriptionRepository.save(studentSubscription);
				}

				if (updated != null) {
					result.setData(updated);
					result.setMessage("Metadata Captured Success");
					result.setStatusCode(201);
				}
			} else if (userObject.getRegisteredAs().equals("Parent")) {
				Parent parent = parentRepository.findByUser(userObject);

				if (parent == null)
					throw new AppException("Invalid IdParent.");
				parent.setIdClassStandard(afterSignUpMetadataDTO.getIdClassStandard());
				parent.setIdLangauage(afterSignUpMetadataDTO.getIdSecondaryLanguage());
				parent.setIdState(afterSignUpMetadataDTO.getIdState());
				parent.setIdStudentMedium(afterSignUpMetadataDTO.getIdMedium());
				parent.setIdSyllabus(afterSignUpMetadataDTO.getIdSyllabus());

				Parent updated = parentRepository.save(parent);

				if (updated != null) {
					result.setData(updated);
					result.setMessage("Metadata Captured Success");
					result.setStatusCode(201);
				}
			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAllTeacher() {

		Document doc = new Document<>();
		try {

			List<Teacher> allTeacher = teacherRepository.findAll();
			if (allTeacher.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Teacher List is Empty");
				doc.setStatusCode(200);
			} else {
				doc.setData(allTeacher);
				doc.setMessage("All Teacher Lists");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document googleOAuthSignup(NewSignupRequestDTO signUpRequest, Device device, String ip, String userAgent) {

		Document doc = new Document<>();

		try {
			JSONObject loc = geoLocation.generateGeoLocation(ip);

			if (signUpRequest == null) {
				throw new NullPointerException("All the Fields are Mandatory. Please Fill All the details");
			}

			User result = new User();

			Language language = languageRepository.findByIdLanguage(signUpRequest.getIdSecondaryLanguage());

			if (language == null)
				throw new AppException("Invalid IdLanguage.");

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
				student = studentRepository.save(student);

				if (signUpRequest.getIdSubject() != null) {
					StudentSubscription studentSubscription = new StudentSubscription();
					studentSubscription.setActiveFlag(true);
					studentSubscription.setIdProduct(prod != null ? prod.getIdProduct() : null);
					studentSubscription
							.setIdProductGroup(productGroup != null ? productGroup.getIdProductGroup() : null);
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
							throw new NullPointerException("invalid idClassStandard .");

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
							throw new NullPointerException("invalid idClassStandard .");

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
			} else {
				throw new NullPointerException("User Not Found ");
			}

			// update for new jwt generation
			StudentPostLoginDTO spl = studentSubscriptionService
					.checkExistingSubscriptionLogin(findByUsername.getUserSurId());
			Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

			UserMetaClaim umc = new UserMetaClaim(standard != null ? standard.getIdClassStandard() : null,
					(syllabus != null ? syllabus.getIdSyllabus() : null), (state != null ? state.getIdState() : null),
					isSubsribed);
			String jwt = tokenProvider.generateToken(authentication, device, umc);
			String jwtRefresh = tokenProvider.refreshToken(authentication, device);

			login.put("accessToken", jwt);
			login.put("refreshToken", jwtRefresh);
			login.put("validFlag", Boolean.TRUE);
			login.put("location", loc);

			authService.checkAndUpdateUserDeviceId("", jwt, findByUsername.getUserSurId(), findByUsername.getUsername(),
					findByUsername.getClassStandard(), device, loc, userAgent);

			checkAndUpdateUserDeviceId(signUpRequest.getDeviceId(), jwt, findByUsername.getUserSurId(),
					findByUsername.getUsername(), findByUsername.getClassStandard(), device, loc, userAgent);

			/**
			 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
			 *         issues raised vl-843 , un-comment this to intialize the sso for all
			 *         the user
			 * 
			 * 
			 *         if (!checkFlag) throw new AppException( "A device is currently logged
			 *         in through this account. Please logout from that device in order to
			 *         log in.");
			 */

			doc.setData(login);
			doc.setMessage("User SignUp Successful. Logged In to the Application");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document googleOAuthSignin(GoogleLoginRequestDTO googleLoginRequestDTO, Device device, String ip,
			String userAgent) {

		Document doc = new Document<>();

		try {
			
			System.out.println("=======================================");
			System.out.println("Device id : "+googleLoginRequestDTO.getDeviceId());
			System.out.println("Is Mobile : "+device.isMobile());
			System.out.println("Is Web : "+device.isNormal());
			System.out.println("Is Tablet : "+device.isTablet());
			System.out.println("=======================================");

			if (googleLoginRequestDTO.getEmail() == null) {
				doc.setData(null);
				doc.setMessage("Username/email Cannot be empty");
				doc.setStatusCode(500);
				return doc;
			}

			if (googleLoginRequestDTO.getIdToken() == null || googleLoginRequestDTO.getIdToken().isEmpty()) {
				doc.setData(null);
				doc.setMessage("idToken cannot be empty");
				doc.setStatusCode(500);
				return doc;
			}

			boolean googleVerify = googleIDTokenVerification.getGoogleIDTokenVerification(googleLoginRequestDTO);

			if (!googleVerify) {
				doc.setData(null);
				doc.setMessage("Google login authentcation failed");
				doc.setStatusCode(500);
				return doc;
			}

			User user = userRepository.findByEmailOrMobileNumber(googleLoginRequestDTO.getEmail());

			if (user == null) {

				doc.setData(null);
				doc.setMessage("Username/email not found in our records");
				doc.setStatusCode(500);
				return doc;
			}

			if (!user.getActiveFlag()) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.FORBIDDEN.value());
				doc.setMessage("Your account is deactivated, please contact us");
				return doc;
			}

			if (!user.getRegisteredAs().equals("Student")) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				doc.setMessage("Please login as internal user!");
				return doc;
			}

			UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

			Instant userLockTime = user.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
			Long userLockTimeEpoch = userLockTime.getEpochSecond();
			Long currentEpoch = Instant.now().getEpochSecond();
			// cross verify whether any updation happened to user record for the past
			// lockDuration min
			if (userLockTimeEpoch > currentEpoch) {
				// see whether user already attempted to maximum allocated value
				if (user.getMaxAttempts() >= 3)

					throw new AppException(
							"For security reasons Your account is temporarily Locked , Please login after  "
									+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
											.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			User findByUsername = userRepository.findByUsername(authentication.getName());

			findByUsername.setMaxAttempts(0);
			userRepository.save(findByUsername);

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
						login.put("validFlag", true);
					} else
						throw new NullPointerException("Role Not Found");
				} else {
					throw new NullPointerException("Role Not Found : " + findByUsername.getRegisteredAs());
				}
			}

			StudentPostLoginDTO spl = studentSubscriptionService
					.checkExistingSubscriptionLogin(findByUsername.getUserSurId());
			Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

			UserMetaClaim umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
					state.getIdState(), isSubsribed);
			String jwt = tokenProvider.generateToken(authentication, device, umc);
			String jwtRefresh = tokenProvider.refreshToken(authentication, device);

			login.put("accessToken", jwt);
			login.put("refreshToken", jwtRefresh);
			login.put("validFlag", true);

			JSONObject loc = geoLocation.generateGeoLocation(ip);

//			authService.checkAndUpdateUserDeviceId("", jwt, findByUsername.getUserSurId(), findByUsername.getUsername(),
//					findByUsername.getClassStandard(), device, loc, userAgent);
			
			System.out.println("--------------------------------");
			System.out.println("Device Id : "+googleLoginRequestDTO.getDeviceId());

			checkAndUpdateUserDeviceId(googleLoginRequestDTO.getDeviceId(), jwt, findByUsername.getUserSurId(),
					findByUsername.getUsername(), findByUsername.getClassStandard(), device, loc, userAgent);

			/**
			 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
			 *         issues raised vl-843 , un-comment this to initialize the sso for all
			 *         the user
			 * 
			 * 
			 *
			 *         if (!checkFlag) throw new AppException( "A device is currently logged
			 *         " + "in through this account. Please logout from that device in order
			 *         " + "to log in.");
			 */

			doc.setData(login);
			doc.setMessage(!constrainFlag ? "Login Successful" : "Your account has been logged out in another device.");
			doc.setStatusCode(200);

		} catch (Exception e) {
			
			e.printStackTrace();

			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	public <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
		if (pageSize <= 0 || page <= 0) {
			throw new IllegalArgumentException("invalid page size: " + pageSize);
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() <= fromIndex) {
			return Collections.emptyList();
		}

		// toIndex exclusive
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}

	@Override
	public Document<UserProfileDTO> getUserProfile() {

		Document<UserProfileDTO> result = new Document<>();
		try {
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			if (user == null)
				throw new NullPointerException("No User Found");

			UserProfileDTO userProfileDTO = new UserProfileDTO();

			switch (user.getRegisteredAs().toUpperCase()) {

			case "STUDENT": {
				Student student = studentRepository.getStudentByUser_UserSurId(userPrincipal.getUserSurId());
				if (student == null)
					throw new NullPointerException("No Student Found");
				userProfileDTO.setClassStandard(student.getIdClassStandard());
				userProfileDTO.setEmail(user.getEmail());
				userProfileDTO.setIdState(student.getIdState());
				userProfileDTO.setIdStudentMedium(student.getIdStudentMedium());
				userProfileDTO.setUserSurId(user.getUserSurId());
				userProfileDTO.setMobileNumber(user.getMobileNumber());
				userProfileDTO.setIdSyllabus(student.getIdSyllabus());
				userProfileDTO.setFirstName(user.getFirstName());
				userProfileDTO.setLastName(user.getLastName());
				userProfileDTO.setIdSecondLanguage(student.getIdLangauage());
				userProfileDTO.setIsProfileEdited(student.getIsProfileEdited());

				ClassStandard classStd = classRepository.findByIdClassStandard(student.getIdClassStandard());
				userProfileDTO.setClassStandadName(classStd != null ? classStd.getClassStandadName() : null);

				Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
				userProfileDTO.setSyllabusName(syllabus != null ? syllabus.getSyllabusName() : null);

				State state = stateRepository.findByIdState(student.getIdState());
				userProfileDTO.setState(state != null ? state.getState() : null);

				StudentMedium medium = studentMediumRepository.findByIdStudentMedium(student.getIdStudentMedium());
				userProfileDTO.setMedium(medium != null ? medium.getMedium() : null);

				Language language = languageRepository.findByIdLanguage(student.getIdLangauage());
				userProfileDTO.setSecondLanguageName(language != null ? language.getLanguage() : null);

				result.setData(userProfileDTO);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			}

			case "PARENT": {
				Parent parent = parentRepository.getParentByUser_UserSurId(userPrincipal.getUserSurId());
				if (parent == null)
					throw new NullPointerException("No Parent Found");

				userProfileDTO.setClassStandard(parent.getIdClassStandard());
				userProfileDTO.setEmail(user.getEmail());
				userProfileDTO.setIdState(parent.getIdState());
				userProfileDTO.setIdStudentMedium(parent.getIdStudentMedium());
				userProfileDTO.setIdSyllabus(parent.getIdSyllabus());
				userProfileDTO.setMobileNumber(user.getMobileNumber());
				userProfileDTO.setUserSurId(user.getUserSurId());
				userProfileDTO.setFirstName(user.getFirstName());
				userProfileDTO.setLastName(user.getLastName());

				ClassStandard classStd = classRepository.findByIdClassStandard(parent.getIdClassStandard());
				userProfileDTO.setClassStandadName(classStd.getClassStandadName());

				Syllabus syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
				userProfileDTO.setSyllabusName(syllabus.getSyllabusName());

				State state = stateRepository.findByIdState(parent.getIdState());
				userProfileDTO.setState(state.getState());

				StudentMedium medium = studentMediumRepository.findByIdStudentMedium(parent.getIdStudentMedium());
				userProfileDTO.setMedium(medium.getMedium());

				Language language = languageRepository.findByIdLanguage(parent.getIdLangauage());
				userProfileDTO.setSecondLanguageName(language != null ? language.getLanguage() : null);

				result.setData(userProfileDTO);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());

				return result;

			}

			}

		} catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}
		return result;
	}

	@Override
	@Transactional
	public Document<UserProfileDTO> editUserProfile(UserProfileDTO userProfileDTO) {
		Document<UserProfileDTO> result = new Document<>();
		try {
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			if (user == null)
				throw new NullPointerException("No User Found");

			boolean isMarketer = (user.getRoles()).stream()
					.anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_MARKETER));

			UserProfileDTO upDB = userRepository.findCustomStudentProfile(userProfileDTO.getUserSurId());
			UserProfileDTO upRequest = new UserProfileDTO(userProfileDTO.getFirstName(),userProfileDTO.getEmail(),
					userProfileDTO.getClassStandard(), userProfileDTO.getIdSyllabus(), userProfileDTO.getIdState(),
					userProfileDTO.getIdStudentMedium(), userProfileDTO.getIdSecondLanguage());
			upDB.setEmail(upDB.getEmail()== null?"":upDB.getEmail());

			List<String> list = ObjectDataCompare.getDifference(upDB, upRequest);
			if (list.isEmpty()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				result.setMessage("No changes detected in edit profile details!");
				return result;
			}
			boolean editValidation = (list.stream().anyMatch("firstName"::equalsIgnoreCase)
					|| list.stream().anyMatch("idStudentMedium"::equalsIgnoreCase)
							&& list.stream().anyMatch("idSecondLanguage"::equalsIgnoreCase))
					&& (!list.stream().anyMatch("classStandard"::equalsIgnoreCase)
							&& !list.stream().anyMatch("idSyllabus"::equalsIgnoreCase)
							&& !list.stream().anyMatch("idState"::equalsIgnoreCase)
							 && !list.stream().anyMatch("email"::equalsIgnoreCase));

			if (!editValidation) {

				/**
				 * @author NAVEEN KUMAR A
				 */
				// if user is changing only language in is profile, the academic data will not
				// be flushed
				boolean langFLag = (list.size() == 1 && list.stream().anyMatch("idSecondLanguage"::equalsIgnoreCase))
						? false
						: true;

				if (langFLag) {
					this.updateStudentAcademicProfile(userProfileDTO.getUserSurId());
				}

				UserDevice ud = userDeviceRepository.findByUserSurIdAndDeviceType(user.getUserSurId(), "MOBILE");
				if (ud != null) {
					if (ud.getDeviceId() != null) {
						Map<String, String> mapData = new HashMap<>();
						mapData.put("profileEdited", "true");
						FcmDTO fcmDTO = new FcmDTO();
						fcmDTO.setSubject("Your profile has been edited");
						fcmDTO.setData(mapData);
						fcmDTO.setContent("Profile edited successfuly");
						String fcmResponse = firebaseService.sendNotification(fcmDTO, ud.getDeviceId());
						System.out.println("fcmResponse::: " + fcmResponse);
					}
				}
			}

			Student studentCheck = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());

			if (studentCheck.getIsProfileEdited()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				result.setMessage("Only one time user is allowed to edit profile details!");
				return result;
			}

			user.setFirstName(
					userProfileDTO.getFirstName() != null ? userProfileDTO.getFirstName() : user.getFirstName());

			userProfileDTO.setFirstName(user.getFirstName());
			userProfileDTO.setUserSurId(user.getUserSurId());

			ClassStandard classStandard = classRepository.findByIdClassStandard(userProfileDTO.getClassStandard());

			if (classStandard == null)
				throw new NullPointerException("Invalid ClassStandard Found");

			userProfileDTO.setClassStandadName(classStandard.getClassStandadName());
			user.setClassStandard(userProfileDTO.getClassStandard());

			Language lang = languageRepository.findByIdLanguage(userProfileDTO.getIdSecondLanguage());

			if (lang == null)
				throw new NullPointerException("No Language Found");

			user.setSecondaryLanguage(lang.getLanguage());
			userProfileDTO.setSecondLanguageName(lang.getLanguage());

			State state = stateRepository.findByIdState(userProfileDTO.getIdState());

			if (state == null)
				throw new NullPointerException("Invalid state Found");

			userProfileDTO.setState(state.getState());

			StudentMedium studentMedium = studentMediumRepository
					.findByIdStudentMedium(userProfileDTO.getIdStudentMedium());

			if (studentMedium == null)
				throw new NullPointerException("Invalid StudentMedium Found");

			userProfileDTO.setMedium(studentMedium.getMedium());

			Syllabus syb = syllabusRepository.findByIdSyllabus(userProfileDTO.getIdSyllabus());

			if (syb == null)
				throw new NullPointerException("Invalid Syllabus Found");

			userProfileDTO.setSyllabusName(syb.getSyllabusName());

//			if (userProfileDTO.getEmail() != null  || !userProfileDTO.getEmail().isEmpty())
//				user.setEmail(userProfileDTO.getEmail());

			user = userRepository.save(user);

			if (user == null) {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage("Error while updating user information!");
				return result;
			}
			switch (user.getRegisteredAs().toUpperCase()) {

			case "STUDENT": {
				Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
				if (student == null)
					throw new NullPointerException("No Student found");

				student.setIdClassStandard(userProfileDTO.getClassStandard());
				student.setIdState(userProfileDTO.getIdState());
				student.setIdStudentMedium(userProfileDTO.getIdStudentMedium());
				student.setIdSyllabus(userProfileDTO.getIdSyllabus());
				student.setIdLangauage(userProfileDTO.getIdSecondLanguage());
				student.setUser(user);
				student.setIsProfileEdited(isMarketer ? !isMarketer : (editValidation ? Boolean.FALSE : Boolean.TRUE));
				studentRepository.save(student);
				userProfileDTO.setEmail(user.getEmail());
				userProfileDTO.setMobileNumber(user.getMobileNumber());
				result.setData(userProfileDTO);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			}
			case "PARENT": {
				Parent parent = parentRepository.getParentByUser_UserSurId(user.getUserSurId());
				if (parent == null)
					throw new NullPointerException("No Parent Found");
				parent.setIdClassStandard(userProfileDTO.getClassStandard());
				parent.setIdState(userProfileDTO.getIdState());
				parent.setIdStudentMedium(userProfileDTO.getIdStudentMedium());
				parent.setIdSyllabus(userProfileDTO.getIdSyllabus());
				parent.setIdLangauage(userProfileDTO.getIdSecondLanguage());
				parent.setUser(user);
				parentRepository.save(parent);
				userProfileDTO.setEmail(user.getEmail());
				userProfileDTO.setMobileNumber(user.getMobileNumber());
				result.setData(userProfileDTO);
				result.setMessage("Request successfull");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			}

			}

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@Override
	public Document<List<LevelExtraCurricular>> getAllExtraLevels() {
		Document<List<LevelExtraCurricular>> result = new Document<>();
		try {

			List<LevelExtraCurricular> temp = levelExtraCurricularRepository.findAll();
			if (temp.isEmpty())
				throw new AppException("No ECA Level data found ");

			result.setData(temp);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		}

		catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<Boolean> sendOTPForUserLogin(String mobileNumber) {

		Document<Boolean> result = new Document<>();

		System.out.println(this.smsFlag);

		Boolean smsFlag = this.smsFlag != null ? this.smsFlag : false;

		try {
			User user = userRepository.findByMobileNumber(mobileNumber);

			if (user == null)
				throw new AppException(" Provided Mobile Number Not Registered with our app.");

			if (!user.getActiveFlag()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("Your account is deactivated, please contact us");
				return result;
			}

			/**
			 * @author NAVEEN KUMAR A
			 * 
			 *         Below implementation will check for brute force attack multiple login
			 *         attempts will be controlled to 3 for per user.
			 * 
			 */

			Instant userLockTime = user.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
			Long userLockTimeEpoch = userLockTime.getEpochSecond();
			Long currentEpoch = Instant.now().getEpochSecond();
			// cross verify whether any updation happened to user record for the past
			// lockDuration min
			if (userLockTimeEpoch > currentEpoch) {
				// see whether user already attempted to maximum allocated value
				if (user.getMaxAttempts() >= 3)

					throw new AppException(
							"For security reasons Your account is temporarily Locked , Please login after  "
									+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
											.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
			}

			if (!user.getRegisteredAs().equals("Student")) {
				result.setData(null);
				result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				result.setMessage("Please login as internal user!");
				return result;
			}

			// sms Flag is false the random number will be hardcoded, no sms will be sent to
			// user
			if (!smsFlag) {
			//	String otp = "987654";
				
				String otp =testOtp;

				MobileOtp existData = mobileOtpRepository.findByMobileNumber(mobileNumber);

				if (existData != null) {
					existData.setOtp(otp);
					existData.setExpiryTime(Instant.now().plus(30, ChronoUnit.MINUTES));
					existData = mobileOtpRepository.save(existData);

					result.setData(true);
					result.setMessage("                                                     Internal Testing:\n" +
			                 "SMS notifications are disabled. Please use the default OTP for authentication.");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					MobileOtp mobileOtp = new MobileOtp(mobileNumber, otp, Instant.now().plus(30, ChronoUnit.MINUTES),
							0, 0);
					mobileOtp = mobileOtpRepository.save(mobileOtp);
					if (mobileOtp != null) {
						result.setData(true);
						result.setMessage("                                                     Internal Testing:\n" +
				                 "SMS notifications are disabled. Please use the default OTP for authentication.");
						result.setStatusCode(HttpStatus.OK.value());
					}
				}
			} else {

				MobileOtp existData = this.getMobileOtp(mobileNumber);

				String otp = existData.getOtp();

				String message = "Your OTP for mobile verification is " + otp + ".\n Happy Learning, V-Learning.";

				// OTP Template id hardcoded for temp
				String templeateId = "1207162701552642477";

				boolean returnValue = smsHorizonService.smsService(mobileNumber, message, templeateId);

				if (returnValue) {
					existData = mobileOtpRepository.save(existData);
					result.setData(true);
					result.setMessage("Message sent successfully");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					result.setData(null);
					result.setMessage("Message not sent");
					result.setStatusCode(HttpStatus.NOT_FOUND.value());
				}

			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<Map<String, Object>> verifyOTPForUserLogin(MobileNumberLoginRequestDTO request, Device device,
			String ip, String userAgent) {

		Document<Map<String, Object>> result = new Document<>();

		try {

			User user = userRepository.findByMobileNumber(request.getMobileNumber());

			if (user == null)
				throw new AppException(" Provided Mobile Number Not Registered with our app.");

			if (!user.getActiveFlag()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("Your account is deactivated, please contact us");
				return result;
			}

			/**
			 * @author NAVEEN KUMAR A
			 * 
			 *         Below implementation will check for brute force attack multiple login
			 *         attempts will be controlled to 3 for per user.
			 * 
			 */

			Instant userLockTime = user.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
			Long userLockTimeEpoch = userLockTime.getEpochSecond();
			Long currentEpoch = Instant.now().getEpochSecond();
			// cross verify whether any updation happened to user record for the past
			// lockDuration min
			if (userLockTimeEpoch > currentEpoch) {
				// see whether user already attempted to maximum allocated value
				if (user.getMaxAttempts() >= 3)

					throw new AppException(
							"For security reasons Your account is temporarily Locked , Please login after  "
									+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
											.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
			}

			if (request.getOtp() != null) {

				MobileOtp existigRecord = mobileOtpRepository.findByMobileNumberAndOtp(request.getMobileNumber(),
						request.getOtp());

				if (existigRecord == null) {

					if (userLockTimeEpoch > currentEpoch) {
						if (user.getMaxAttempts() < 3) {
							user.setMaxAttempts(user.getMaxAttempts() + 1);
							userRepository.save(user);

						} else {
							throw new AppException(
									"For security reasons Your account is temporarily Locked , Please login after  "
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
													.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
						}
					} else {
						// this condition will be executed when user trying login with wrong credentials
						// after lock time
						if (user.getMaxAttempts() >= 3) {
							user.setMaxAttempts(1);
							userRepository.save(user);
						} else if (user.getMaxAttempts() <= 3) {
							user.setMaxAttempts(user.getMaxAttempts() + 1);
							userRepository.save(user);

						} else {
							throw new AppException(
									"For security reasons Your account is temporarily Locked , Please login after  "
											+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
													.format(DateTimeFormatter.ofPattern("dd-MM-yyyy |  hh:mm a")));
						}

					}

				}

				if (existigRecord == null)
					throw new AppException("Incorrect otp provided");

				// getting userDetail for the user

				UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

				// create user authentication by userDetails and authorites
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(authentication);

				// Removing otp record from the table
				mobileOtpRepository.delete(existigRecord);

				SecurityContextHolder.getContext().setAuthentication(authentication);

				/*
				 * @author Abdul Elahi Created the user constrain mechanism for limit the users
				 * accessing multiple devices with same account
				 */
				boolean constrainFlag = false;

				List<UserDevice> userDevice = userDeviceRepository.findByUserSurId(user.getUserSurId());
				if (userDevice.size() > 0 || !userDevice.isEmpty()) {
					constrainFlag = true;
				}

				ClassStandard standard = null;
				Syllabus syllabus = null;
				State state = null;

				user.setMaxAttempts(0);
				userRepository.save(user);

				Map<String, Object> login = new HashMap<>();

				if (user != null) {

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

					if (user.getRegisteredAs().equals("Student")) {
						Student student = studentRepository.findByUser(user);
						if (student != null) {
							standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
							syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
							state = stateRepository.findByIdState(student.getIdState());
							login.put("classStandardObject", standard != null ? standard : -1);
							login.put("syllabusObject", syllabus != null ? syllabus : -1);
							login.put("stateObject", state != null ? state : -1);
						} else
							throw new NullPointerException("Role Not Found");
					} else if (user.getRegisteredAs().equals("Parent")) {
						Parent parent = parentRepository.findByUser(user);
						if (parent != null) {
							standard = classRepository.findByIdClassStandard(parent.getIdClassStandard());
							syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
							state = stateRepository.findByIdState(parent.getIdState());
							login.put("classStandardObject", standard);
							login.put("syllabusObject", syllabus);
							login.put("stateObject", state);
						} else
							throw new NullPointerException("Role Not Found");
					} else {
						throw new NullPointerException("Role Not Found : " + user.getRegisteredAs());
					}

					// update for new jwt generation
					StudentPostLoginDTO spl = studentSubscriptionService
							.checkExistingSubscriptionLogin(user.getUserSurId());
					Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

					UserMetaClaim umc = null;
					boolean validFlag = true;
					if (standard != null && syllabus != null && state != null) {
						umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
								state.getIdState(), isSubsribed);
					} else {
						umc = new UserMetaClaim(-1L, -1L, -1L, isSubsribed);
						validFlag = false;

					}

					String jwt = tokenProvider.generateToken(authentication, device, umc);
					String jwtRefresh = tokenProvider.refreshToken(authentication, device);

					login.put("accessToken", jwt);
					login.put("refreshToken", jwtRefresh);
					login.put("validFlag", validFlag);

					JSONObject loc = geoLocation.generateGeoLocation(ip);
					login.put("location", loc);

					authService.checkAndUpdateUserDeviceId(request.getDeviceId(), jwt, user.getUserSurId(),
							userDetails.getUsername(), user.getClassStandard(), device, loc, userAgent);
					
					String userAgent1 = "";
					if (device.isMobile()) {
						userAgent1 = userAgent;
					} else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {
						userAgent1 = userAgent;
					} else {
						userAgent1 = getUserAgent(userAgent);
					}
					
					saveDevice(device,loc.toString(), userAgent1,user.getUserSurId(),request.getDeviceId());

					/**
					 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
					 *         issues raised vl-843 , un-comment this to intialize the sso for all
					 *         the user
					 * 
					 * 
					 * 
					 *
					 *         if (!checkFlag) throw new AppException( "A device is currently logged
					 *         " + "in through this account. Please logout from that device in order
					 *         " + "to log in.");
					 */

				}

				result.setData(login);
				result.setMessage(
						!constrainFlag ? "Login Successful" : "Your account has been logged out in another device.");
				result.setStatusCode(200);

			} else if (request.getPassword() != null) {
				// getting userDetail for the user

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				/*
				 * @author Abdul Elahi Created the user constrain mechanism for limit the users
				 * accessing multiple devices with same account
				 */
				boolean constrainFlag = false;

				String devicePlatform = device.isNormal() ? "WEB" : "MOBILE";
				String secretString = devicePlatform.equalsIgnoreCase("WEB") ? "V0VCQVBQ" : "TU9CSUxFQVBQ";

				UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(user.getUserSurId(),
						devicePlatform);

				if (userDevice != null) {
					logoutUser(secretString, device);
					constrainFlag = true;
				}

				ClassStandard standard = null;
				Syllabus syllabus = null;
				State state = null;

				Map<String, Object> login = new HashMap<>();

				if (user != null) {

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

					if (user.getRegisteredAs().equals("Student")) {
						Student student = studentRepository.findByUser(user);
						if (student != null) {
							standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
							syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
							state = stateRepository.findByIdState(student.getIdState());
							login.put("classStandardObject", standard != null ? standard : -1);
							login.put("syllabusObject", syllabus != null ? syllabus : -1);
							login.put("stateObject", state != null ? state : -1);
						} else
							throw new NullPointerException("Role Not Found");
					} else if (user.getRegisteredAs().equals("Parent")) {
						Parent parent = parentRepository.findByUser(user);
						if (parent != null) {
							standard = classRepository.findByIdClassStandard(parent.getIdClassStandard());
							syllabus = syllabusRepository.findByIdSyllabus(parent.getIdSyllabus());
							state = stateRepository.findByIdState(parent.getIdState());
							login.put("classStandardObject", standard);
							login.put("syllabusObject", syllabus);
							login.put("stateObject", state);
						} else
							throw new NullPointerException("Role Not Found");
					} else {
						throw new NullPointerException("Role Not Found : " + user.getRegisteredAs());
					}

					// update for new jwt generation
					StudentPostLoginDTO spl = studentSubscriptionService
							.checkExistingSubscriptionLogin(user.getUserSurId());
					Boolean isSubsribed = (spl.getSubscribedFlag() || spl.getTrialFlag()) ? true : false;

					UserMetaClaim umc = null;
					boolean validFlag = true;
					if (standard != null && syllabus != null && state != null) {
						umc = new UserMetaClaim(standard.getIdClassStandard(), syllabus.getIdSyllabus(),
								state.getIdState(), isSubsribed);
					} else {
						umc = new UserMetaClaim(-1L, -1L, -1L, isSubsribed);
						validFlag = false;

					}

					String jwt = tokenProvider.generateToken(authentication, device, umc);
					String jwtRefresh = tokenProvider.refreshToken(authentication, device);

					login.put("accessToken", jwt);
					login.put("refreshToken", jwtRefresh);
					login.put("validFlag", validFlag);

					JSONObject loc = geoLocation.generateGeoLocation(ip);
					login.put("location", loc);

					authService.checkAndUpdateUserDeviceId(request.getDeviceId(), jwt, user.getUserSurId(),
							user.getUsername(), user.getClassStandard(), device, loc, userAgent);

					/**
					 * @author NAVEEN KUMAR A Below SSO login is feature is commented as per the
					 *         issues raised vl-843 , un-comment this to intialize the sso for all
					 *         the user
					 * 
					 * 
					 *         if (!checkFlag) throw new AppException( "A device is currently logged
					 *         in through this account. Please logout from that device in order to
					 *         log in.");
					 * 
					 */

				}

				result.setData(login);
				result.setMessage(
						!constrainFlag ? "Login Successful" : "Your account has been logged out in another device.");
				result.setStatusCode(200);

			} else {

				throw new AppException("Please provided your password or otp.");

			}

		}

		catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document forgotPasswordInternaluser(String forgotUsername) {
		Document doc = new Document<>();
		try {
			User userAuth = userRepository.findByEmailOrMobileNumber(forgotUsername);
			if (forgotUsername == null || userAuth == null) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.NOT_FOUND.value());
				doc.setMessage("Entered username not found");
				return doc;
			}
			if (userAuth != null) {
				if (!userAuth.getActiveFlag()) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.FORBIDDEN.value());
					doc.setMessage("Your account is deactivated, please contact us");
					return doc;
				}
				if (userAuth.getRegisteredAs().equals("Student")) {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.UNAUTHORIZED.value());
					doc.setMessage("You are not authorized to login here!");
					return doc;
				}
			}

			String randomString = RandomStringUtils.random(10, true, true);

			if (!userAuth.getEmail().isEmpty() || userAuth.getEmail() != null) {
				Document response = emailService.sendForgotPasswordEmailInternal(userAuth.getEmail(),
						userAuth.getUsername(), randomString, userAuth.getFirstName(), userAuth.getUserSurId());
				if (response.getStatusCode() == 200) {
					userAuth.setPassword(passwordEncoder.encode(randomString));
					userAuth = userRepository.save(userAuth);
					doc.setData("Success");
					doc.setStatusCode(200);
					doc.setMessage("Check E-Mail for new password.");
					return doc;
				} else {
					doc.setData(null);
					doc.setMessage(
							"Failed to send password reset mail. Check your internet connectivity or conatct Vistas Learning Admin.");
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return doc;
				}
			}
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
		return doc;
	}

	@Modifying
	@Transactional
	public void updateStudentAcademicProfile(Long userSurId) {
		try {

			// idProduct line 5l hard coded , since we need to remove only academic records

			List<String> compositeKeyStrings = dynamoDbHelper.getCompositeKeyForVSL(userSurId, 5L);

			// invoke video streaming log deletion
			videoStreamingLogRepository.deleteAllVideoStreamingLogs(compositeKeyStrings);

			entityManager.joinTransaction();
			// do your changes here
			Query query = entityManager.createNativeQuery(
					"delete from student_assigned_course where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5) as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_chapter_quiz_detail where idstudent_chapter_quiz \r\n"
							+ "	IN (select idstudent_chapter_quiz from student_chapter_quiz scq where scq.idstudent_subscr IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5) AS idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_chapter_quiz_answer   where  idstudent_chapter_quiz_question \r\n"
							+ "	IN (select idstudent_chapter_quiz_question from student_chapter_quiz_question scqq \r\n"
							+ "		JOIN student_chapter_quiz scq  ON scq.idstudent_chapter_quiz = scqq.idstudent_chapter_quiz\r\n"
							+ "		where scq.idstudent_subscr IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5) as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_chapter_quiz_question where  idstudent_chapter_quiz \r\n"
							+ "	IN(select idstudent_chapter_quiz from student_chapter_quiz scq where scq.idstudent_subscr IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_chapter_quiz where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_completion_fact  where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_offline_quiz_answer   where  idstudent_offline_quiz_question \r\n"
							+ "	IN (select idstudent_offline_quiz_question from student_offline_quiz_question soqq \r\n"
							+ "		JOIN student_offline_quiz soq ON soqq.idstudent_offline_quiz = soq.idstudent_offline_quiz\r\n"
							+ "		where soq.idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_offline_quiz_question where  idstudent_offline_quiz\r\n"
							+ "	IN (select idstudent_offline_quiz from student_offline_quiz soq \r\n"
							+ "		where soq.idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_offline_quiz where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_subject_quiz_answer   where  idstudent_subject_quiz_question \r\n"
							+ "IN (select idstudent_subject_quiz_question from  student_subject_quiz_question ssqq \r\n"
							+ "	JOIN student_subject_quiz ssq ON ssqq.idstudent_subject_quiz = ssq.idstudent_subject_quiz\r\n"
							+ "	WHERE ssq.idstudent_subscr IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5) as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_subject_quiz_question where  idstudent_subject_quiz \r\n"
							+ "	IN (select idstudent_subject_quiz from student_subject_quiz ssq  where ssq.idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr))");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_subject_quiz where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_subscription where  idstudent_subscr  IN (SELECT idstudent_subscr from (SELECT idstudent_subscr FROM student_subscription ss JOIN student s on ss.idstudent = s.idstudent WHERE s.idvl_user =:idvl_user and ss.idproduct_line=5)as idstudent_subscr)");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from live_class_watched_history where idvl_user=:idvl_user ");
			query.setParameter("idvl_user", userSurId);
			query.executeUpdate();

		} catch (RuntimeException re) {
			System.err.println(re);
		}
	}

	@Override
	public Document<Map<String, Long>> fetchAllUserListsSumData() {

		Document<Map<String, Long>> result = new Document<Map<String, Long>>();
		Map<String, Long> mapData = new HashMap<>();
		Long admin = 0L, student = 0L, teacher = 0L, telecaller = 0L, parent = 0L, moderator = 0L, blogger = 0L;
		admin = userRepository.countByRegisteredAs("Admin");
		student = userRepository.countByRegisteredAs("Student");
		teacher = userRepository.countByRegisteredAs("Teacher");
		telecaller = userRepository.countByRegisteredAs("Telecaller");
		parent = userRepository.countByRegisteredAs("Parent");
		moderator = userRepository.countByRegisteredAs("Moderator");
		blogger = userRepository.countByRegisteredAs("Blogger");
		mapData.put("adminCount", admin);
		mapData.put("studentCount", student);
		mapData.put("parentCount", parent);
		mapData.put("teacherCount", teacher);
		mapData.put("telecallerCount", telecaller);
		mapData.put("moderatorCount", moderator);
		mapData.put("bloggerCount", blogger);
		result.setData(mapData);
		result.setMessage("Fetching data successfuly");
		result.setStatusCode(200);
		return result;
	}

	@Override
	public Boolean checkAndUpdateUserDeviceId(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device, JSONObject deviceLocation, String userAgent) throws Exception {
		Boolean checkfalg = false;
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Mobile Device "+device.isMobile());
		System.out.println("Tablet Device "+device.isTablet());
		System.out.println("WEB Device "+device.isNormal());
		System.out.println("--------------------------------------------------------");

		// Mobile implementations starts here
		if (device.isTablet() || device.isMobile()) {
			
			//Removed device id check form login api need revert once to added in status api
			
//			if (deviceId != null && !deviceId.isEmpty()) {
//				
//				checkfalg = this.checkMobileDevice(deviceId, generatedToken, userId, username, idClassStandard, device,
//						deviceLocation, userAgent);
//			} else {
//				checkfalg = this.checkWebDevice(deviceId, generatedToken, userId, username, idClassStandard, device,
//						deviceLocation, userAgent);
//			}
			checkfalg = this.checkMobileDevice(deviceId, generatedToken, userId, username, idClassStandard, device,
					deviceLocation, userAgent);

		} else if(device instanceof CustomDevice && ((CustomDevice) device).isTv()){
			
			checkfalg = this.checkTvDevice(deviceId, generatedToken, userId, username, idClassStandard, device,
					deviceLocation, userAgent);
		}
		else {

			checkfalg = this.checkWebDevice(deviceId, generatedToken, userId, username, idClassStandard, device,
					deviceLocation, userAgent);

		}

		return checkfalg;
	}

	private Boolean checkMobileDevice(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device, JSONObject deviceLocation, String userAgent) throws AppException {
		Boolean checkfalg = false;

		UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userId, "MOBILE");

		if (userDevice != null) {
			// previous user device record found

			// check the previous token validity
//			Boolean validFlag = firebaseService.verifyToken(userDevice.getDeviceId());
//
//			if (validFlag)
//				throw new AppException("A device is currently logged in through this account. "
//						+ "Please logout from that device in order to log in.");
			
			userDevice.setDeviceId(deviceId);
			userDevice.setJwtToken(generatedToken);
			userDevice.setDeviceType("MOBILE");
			userDevice.setIdClassStandard(idClassStandard);
			userDevice.setCreatedAt(Instant.now());
			userDevice.setUpdatedAt(Instant.now());
			userDevice.setDeviceLocation(deviceLocation.toString());
			userDevice.setUserAgent(userAgent);

			userDevice = userDeviceRepository.save(userDevice);

			checkfalg = true;
		} else {
			System.out.println("creating new entry for user device");
			// if there is no user device data found , new entry will be created here.
			UserDevice u_device = new UserDevice();
			u_device.setDeviceId(deviceId);
			u_device.setUserSurId(userId);
			u_device.setDeviceType("MOBILE");
			u_device.setIdClassStandard(idClassStandard);
			u_device.setJwtToken(generatedToken);
			u_device.setCreatedAt(Instant.now());
			u_device.setUpdatedAt(Instant.now());
			u_device.setDeviceLocation(deviceLocation.toString());
			u_device.setUserAgent(userAgent);

			u_device = userDeviceRepository.save(u_device);

			checkfalg = true;
		}

		return checkfalg;
	}

	private Boolean checkWebDevice(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device, JSONObject deviceLocation, String userAgent) throws AppException {
		Boolean checkfalg = false;

		// web implementations starts here

		UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userId, "WEB");
//		UserDevice userDevice = new UserDevice();

		if (userDevice != null) {

			// check the previous token validity
//			Boolean validFlag = firebaseService.verifyToken(userDevice.getDeviceId());
//
//			if (validFlag)
//				throw new AppException("A device is currently logged in through this account. "
//						+ "Please logout from that device in order to log in.");

			userDevice.setJwtToken(generatedToken);
			userDevice.setDeviceId(deviceId);
			userDevice.setDeviceType("WEB");
			userDevice.setIdClassStandard(idClassStandard);
			userDevice.setCreatedAt(Instant.now());
			userDevice.setUpdatedAt(Instant.now());
			userDevice.setDeviceLocation(deviceLocation.toString());
			userDevice.setUserAgent(userAgent);

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
			u_device.setCreatedAt(Instant.now());
			u_device.setUpdatedAt(Instant.now());
			u_device.setDeviceLocation(deviceLocation.toString());
			u_device.setUserAgent(userAgent);

			u_device = userDeviceRepository.save(u_device);

			checkfalg = true;
		}

		return checkfalg;

	}

	@Override
	public Document<String> logoutUser(String actualDevice, Device device) {

		Document<String> result = new Document<String>();
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			Base64.Decoder decoder = Base64.getDecoder();

			String decodedString = new String(decoder.decode(actualDevice));

			if (userPrincipal == null)
				throw new AppException("Invalid User");
			// removing session log
			String deviceType = (device.isMobile() || device.isTablet()) ? "mobile" : "web";

			loggedUserHandler.deleteUserCount(userPrincipal.getUserSurId(), deviceType);

			if (device.isTablet() || device.isMobile()) {

				if (decodedString.equalsIgnoreCase("mobileapp")) {
					UserDevice userDevice = userDeviceRepository
							.findByUserSurIdAndDeviceType(userPrincipal.getUserSurId(), "MOBILE");

					if (userDevice == null) {
						result.setData("No Mobile User device information found.");
						result.setMessage("Logout successfull");
						result.setStatusCode(HttpStatus.OK.value());
					} else {
						userDeviceRepository.delete(userDevice);
						result.setData("Mobile User device information deleted sucessfully");
						result.setMessage("Logout successfull");
						result.setStatusCode(HttpStatus.OK.value());
					}

				} else {
					UserDevice userDevice = userDeviceRepository
							.findByUserSurIdAndDeviceType(userPrincipal.getUserSurId(), "WEB");

					if (userDevice == null) {
						result.setData("No Web User device information found.");
						result.setMessage("Logout successfull");
						result.setStatusCode(HttpStatus.OK.value());
					} else {
						userDeviceRepository.delete(userDevice);
						result.setData("Web User device information deleted sucessfully");
						result.setMessage("Logout successfull");
						result.setStatusCode(HttpStatus.OK.value());
					}
				}

			} else {

				UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userPrincipal.getUserSurId(),
						"WEB");

				if (userDevice == null) {
					result.setData("No Web User device information found.");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					userDeviceRepository.delete(userDevice);
					result.setData("Web User device information deleted sucessfully");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				}

			}

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<Map<String, Object>> getInternalIdOfdifferentRole(String role, Long userId) {

		Document<Map<String, Object>> result = new Document<>();
		try {

			Teacher teacher = null;
			Marketer marketer = null;
			Vendor vendor = null;

			User user = userRepository.findByUserSurId(userId);

			if (user == null)

				throw new NullPointerException("Invalid user id");

			Map<String, Object> res = new HashMap<String, Object>();
			res.put("role", user.getRegisteredAs());

			if (role.equalsIgnoreCase("teacher")) {
				teacher = teacherRepository.getTeacherByUser_UserSurId(userId);

				if (teacher == null)

					throw new NullPointerException("Invalid teacher for provide user id");

				res.put("id", teacher.getIdTeacher());
			}

			else if (role.equalsIgnoreCase("marketer")) {
				marketer = marketerRepository.findByIdVlUser(userId);

				if (marketer == null)

					throw new NullPointerException("Invalid marketer for provide user id");

				res.put("id", marketer.getIdMarketer());

			}

			else if (role.equalsIgnoreCase("vendor")) {
				vendor = vendorRepository.findByIdVlUser(userId);

				if (vendor == null)

					throw new NullPointerException("Invalid vendor for provide user id");

				res.put("id", vendor.getIdVendor());

			}

			else
				throw new AppException("Invalid role for the user");

			result.setData(res);
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

	@Override
	public Long getActiveStudentCount() {
		Long activeStudents = userRepository.getActiveStudentCount();
		return activeStudents;
	}

	@Override
	public MobileOtp getMobileOtp(String mobile) throws Exception {
		MobileOtp motp = null;

		MobileOtp existingRecord = mobileOtpRepository.findByMobileNumber(mobile);

		// if request the otp for the first time
		if (existingRecord == null) {
			String otp = RandomStringUtils.randomNumeric(6);
			motp = new MobileOtp(mobile, otp, Instant.now(), 0, 0);
		} else {
			// if user request for otp equal or greater then 5 times
			if (existingRecord.getMaxRequest() >= 5) {
				Instant userLockTime = existingRecord.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
				Long userLockTimeEpoch = userLockTime.getEpochSecond();
				Long currentEpoch = Instant.now().getEpochSecond();

				if (userLockTimeEpoch > currentEpoch) {
					// see whether user already attempted to maximum allocated value
					throw new AppException(
							"For security reasons your OTP request has been denied. , Please request after  "
									+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
											.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")));
				} else {
					// resetting otp count to normal for fresh start
					String otp = RandomStringUtils.randomNumeric(6);
					existingRecord.setOtp(otp);
					existingRecord.setExpiryTime(Instant.now());
					existingRecord.setMaxRequest(0);
					existingRecord.setMaxAttempt(0);
					motp = existingRecord;
				}

			} else { // give new otp to the user and increment the attempts counts
				String otp = RandomStringUtils.randomNumeric(6);
				existingRecord.setOtp(otp);
				existingRecord.setExpiryTime(Instant.now());
				existingRecord.setMaxRequest(existingRecord.getMaxRequest() + 1);
				existingRecord.setMaxAttempt(existingRecord.getMaxAttempt());
				motp = existingRecord;
			}
		}

		return motp;
	}

	@Override
	public MobileOtp verifyMobileOTP(String mobile, String otp) throws Exception {

		MobileOtp motp = null;

		MobileOtp existingRecord = mobileOtpRepository.findByMobileNumber(mobile);

		// if block will be executed when no record found in mobile repository
		if (existingRecord == null)
			throw new AppException("Please request the otp first.");
		else {
			// this block execute when record found
			if (existingRecord.getOtp().equalsIgnoreCase(otp)) {
				motp = existingRecord;
			}

			else {
				if (existingRecord.getMaxAttempt() >= 5) {
					Instant userLockTime = existingRecord.getUpdatedAt().plus(lockDuration, ChronoUnit.MINUTES);
					Long userLockTimeEpoch = userLockTime.getEpochSecond();
					Long currentEpoch = Instant.now().getEpochSecond();

					if (userLockTimeEpoch > currentEpoch) {
						// see whether user already attempted to maximum allocated value
						throw new AppException(
								"For security reasons your OTP verification has been denied. , Please verify after  "
										+ LocalDateTime.ofInstant(userLockTime, zoneIndia)
												.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")));
					} else {
						// resetting otp count to normal for fresh start
						existingRecord.setExpiryTime(Instant.now());
						existingRecord.setMaxRequest(0);
						existingRecord.setMaxAttempt(0);
						mobileOtpRepository.save(existingRecord);

						throw new AppException("Please enter the correct OTP.");

					}

				} else {
					existingRecord.setMaxRequest(existingRecord.getMaxRequest());
					existingRecord.setMaxAttempt(existingRecord.getMaxAttempt() + 1);

					mobileOtpRepository.save(existingRecord);

					throw new AppException("Please enter the correct OTP.");
				}
			}

		}

		return motp;

	}

	@Override
	public Document<Boolean> getUserNameAvailablity(String userName) {

		Document<Boolean> doc = new Document<>();

		try {

			if (userName == null || userName.isEmpty() || userName.length() < 1)
				throw new AppException("username cannot be empty or null.");

			Boolean existByUserName = userRepository.existsByUsername(userName);
			Boolean existByEmail = userRepository.existsByEmail(userName);
			Boolean existByMobileNumber = userRepository.existsByMobileNumber(userName);

			// Validate email using regular expression
			Boolean isValidEmail = userName
					.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

			Boolean isValidMobileNumber = userName.matches("[98762]\\d{9}");

			if (Boolean.TRUE.equals(isValidEmail)) {
				if (Boolean.TRUE.equals(existByEmail)) {
					doc.setData(Boolean.TRUE);
					doc.setMessage("Oops! It looks like the email is already registered.");
				} else {
					doc.setData(Boolean.FALSE);
					doc.setMessage("Email is available!");
				}
			}

			else if (Boolean.TRUE.equals(isValidMobileNumber)) {
				if (existByMobileNumber || existByUserName) {
					doc.setData(Boolean.TRUE);
					doc.setMessage("Oops! It looks like the Mobile Number is already registered.");
				} else {
					doc.setData(Boolean.FALSE);
					doc.setMessage("Mobile Number is available!");
				}
			} else {
				if (Boolean.TRUE.equals(existByUserName)) {
					doc.setData(Boolean.TRUE);
					doc.setMessage("Oops! It looks like the Username is already registered.");
				} else {
					doc.setData(Boolean.FALSE);
					doc.setMessage("Username is available!");
				}
			}

			doc.setStatusCode(HttpStatus.OK.value());
		}

		catch (Exception exp) {
			logger.error(exp.getLocalizedMessage());
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;

	}

	@Override
	public Document<List<DeviceInfoDTO>> getUserLoggedinDevices() {
		Document<List<DeviceInfoDTO>> result = new Document<>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			List<UserDevice> userDevices = userDeviceRepository.findByUserSurId(loggedInUser.getUserSurId());
			List<DeviceInfoDTO> devices = new ArrayList<>();

			if (userDevices != null) {

				for (UserDevice userDevice : userDevices) {

					DeviceInfoDTO deviceInfo = new DeviceInfoDTO();

					deviceInfo.setIdUserActivity(userDevice.getIdUserDevice());
					deviceInfo.setLastLogin(userDevice.getUpdatedAt());
					deviceInfo.setDeviceName(userDevice.getUserAgent() == null ? "N/A" : userDevice.getUserAgent());
					deviceInfo.setDeviceType(userDevice.getDeviceType());
					deviceInfo.setLocation(
							userDevice.getDeviceLocation() == null ? "N/A" : userDevice.getDeviceLocation());

					devices.add(deviceInfo);
				}
			}

			result.setData(devices);
			result.setMessage("Logged in devices");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {

			e.printStackTrace();

			result.setData(null);
			result.setMessage("An error occurred while retrieving logged in devices: " + e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<String> logoutFromDevices(Long idUserDevice, boolean allDevices) {

		Document<String> result = new Document<String>();
		try {
			if (!allDevices) {
				UserDevice userDevice = userDeviceRepository.findByIdUserDevice(idUserDevice);

				if (userDevice == null) {
					result.setData("No User device information found.");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					loggedUserHandler.deleteUserCount(userDevice.getUserSurId(), userDevice.getDeviceType());

					userDeviceRepository.delete(userDevice);
					result.setData(" User device information deleted sucessfully");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				}

			} else {
				User loggedInUser = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
					loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				}

				if (loggedInUser == null)
					throw new AppException("Invalid User");

				List<UserDevice> userDevices = userDeviceRepository.findByUserSurId(loggedInUser.getUserSurId());

				if (userDevices == null) {
					result.setData("No User device information found.");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					loggedUserHandler.deleteUserCount(loggedInUser.getUserSurId(), "Mobile");
					loggedUserHandler.deleteUserCount(loggedInUser.getUserSurId(), "Web");

					userDeviceRepository.deleteAll(userDevices);
					result.setData(" User device information deleted sucessfully");
					result.setMessage("Logout successfull");
					result.setStatusCode(HttpStatus.OK.value());
				}
			}
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}
	

	private Boolean checkTvDevice(String deviceId, String generatedToken, Long userId, String username,
			Long idClassStandard, Device device, JSONObject deviceLocation, String userAgent) throws AppException {
		Boolean checkfalg = false;

		// web implementations starts here

		UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userId, "TV");
//		UserDevice userDevice = new UserDevice();

		if (userDevice != null) {

			// check the previous token validity
//			Boolean validFlag = firebaseService.verifyToken(userDevice.getDeviceId());
//
//			if (validFlag)
//				throw new AppException("A device is currently logged in through this account. "
//						+ "Please logout from that device in order to log in.");

			userDevice.setJwtToken(generatedToken);
			userDevice.setDeviceId(deviceId);
			userDevice.setDeviceType("TV");
			userDevice.setIdClassStandard(idClassStandard);
			userDevice.setCreatedAt(Instant.now());
			userDevice.setUpdatedAt(Instant.now());
			userDevice.setDeviceLocation(deviceLocation.toString());
			userDevice.setUserAgent(userAgent);

			userDevice = userDeviceRepository.save(userDevice);

			checkfalg = true;

		} else {
			// if there is no user device data found , new entry will be created here.
			UserDevice u_device = new UserDevice();
			u_device.setDeviceId(deviceId);
			u_device.setUserSurId(userId);
			u_device.setDeviceType("TV");
			u_device.setIdClassStandard(idClassStandard);
			u_device.setJwtToken(generatedToken);
			u_device.setCreatedAt(Instant.now());
			u_device.setUpdatedAt(Instant.now());
			u_device.setDeviceLocation(deviceLocation.toString());
			u_device.setUserAgent(userAgent);

			u_device = userDeviceRepository.save(u_device);

			checkfalg = true;
		}

		return checkfalg;

	}


	
	
	public String checkDeviceType(String deviceId, Device device) throws Exception {
		Boolean checkfalg = false;

		String deviceType = "";

		// Mobile implementations starts here
		if (device.isTablet() || device.isMobile()) {
			
			//Removed device id check form login api need revert once to added in status api
			
			
//			if (deviceId != null && !deviceId.isEmpty()) {
//
//				deviceType = "MOBILE";
//			} else {
//				deviceType = "WEB";
//			}
			deviceType = "MOBILE";

		} else if (device instanceof CustomDevice && ((CustomDevice) device).isTv()) {

			deviceType = "TV";
		} else {

			deviceType = "WEB";

		}

		return deviceType;
	}
	
	public String getUserAgent(String userAgent) {

		UserAgent userAgent1 = UserAgent.parseUserAgentString(userAgent);

		// Extract browser details
		Browser browser = userAgent1.getBrowser();
		String browserName = browser.getName();
		String browserVersion = userAgent1.getBrowserVersion().getVersion();

		// Extract operating system details
		OperatingSystem os = userAgent1.getOperatingSystem();
		String osName = os.getName();
		DeviceType deviceType = os.getDeviceType();

		String deviceName = getDeviceName(osName);

		String userAgentString = deviceName +" "+browserName;

		// Print results
		System.out.println("Browser: " + browserName + " " + browserVersion);
		System.out.println("Operating System: " + osName);
		System.out.println("Device Type: " + deviceType);

		return userAgentString;
	}

	public static String getDeviceName(String osName) {
		if (osName.contains("Mac")) {
			return "Mac";
		} else if (osName.contains("Windows")) {
			return "Windows";
		} else if (osName.contains("Linux")) {
			return "Linux";
		} else {
			return "Other";
		}
	}
	
	public void saveDevice(Device device, String loc, String userAgent, Long idVlUser, String deviceId)
			throws Exception {

	    // Determine the device type dynamically
        String deviceType = checkDeviceType(deviceId, device);

        // Check if the record already exists
        UserDeviceHistory existingRecord =
            userDeviceHistoryRepository.findByIdVLUserAndDeviceTypeAndUserAgent(idVlUser, deviceType, userAgent);

        if (existingRecord != null) {
            // Record found, no need to save it again
            System.out.println("Record already exists for deviceType: " + deviceType + ", userAgent: " + userAgent);
            return; // Exit the method early
        }

        // Record not found, create a new one
        UserDeviceHistory newRecord = new UserDeviceHistory();
        newRecord.setDeviceId(deviceId);
        newRecord.setDeviceLocation(loc);
        newRecord.setDeviceType(deviceType);
        newRecord.setUserAgent(userAgent);
        newRecord.setIdVLUser(idVlUser);

        // Save the new record
        userDeviceHistoryRepository.save(newRecord);
	}

	@Override
	public Document<List<DeviceHistoryDTO>> getDevicesHistory() {
		Document<List<DeviceHistoryDTO>> result = new Document<>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
		    List<UserDeviceHistory>	userDevicelist= userDeviceHistoryRepository.findTop10ByIdVLUserOrderByCreatedAtDesc(loggedInUser.getUserSurId());
		    
		    if(userDevicelist==null)
		    	throw new AppException("No Devices History found");
		    
		    List<DeviceHistoryDTO> deviceHistoryDTOList = new ArrayList<>();
		    
		    for(UserDeviceHistory userDevice :userDevicelist) {
		    	
		    	DeviceHistoryDTO deviceHistoryDTO =new DeviceHistoryDTO();
		    	deviceHistoryDTO.setDeviceLocation(userDevice.getDeviceLocation());
		    	deviceHistoryDTO.setDeviceType(userDevice.getDeviceType());
		    	deviceHistoryDTO.setIdVLUser(userDevice.getIdVLUser());
		    	deviceHistoryDTO.setUserAgent(userDevice.getUserAgent());
		    	deviceHistoryDTO.setLastLoginTime(userDevice.getCreatedAt());
		    	deviceHistoryDTOList.add(deviceHistoryDTO);
		    	
		    }
		    
			result.setData(deviceHistoryDTOList);
			result.setMessage("History of devices");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage("An error occurred while retrieving logged in devices: " + e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<String> resetMaxAttempts(Long idVlUser) {
 		Document<String> result = new Document<String>();
 		try {
 			
 		User user=	userRepository.findByUserSurId(idVlUser);
 		
 		if(user==null) 
 			throw new AppException("Invalid User");
 		
 		user.setMaxAttempts(0);
 		userRepository.save(user);
 		
		result.setData("Request Sucessfull");
		result.setMessage("Request Sucessfull");
		result.setStatusCode(HttpStatus.OK.value());
 			
 		} catch (Exception e) {
 			result.setData(null);
 			result.setMessage(e.getLocalizedMessage());
 			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
 		}
 		return result;
 	}		

	public Document<String> saveSubscriptionClickDetails(SubscriptionClickDTO subscriptionClickDTO) {

		Document<String> result = new Document<String>();

		try {
			
	

			SubscriptionClick subscriptionClick = new SubscriptionClick();

			if (subscriptionClickDTO.getMobileNumber() == null)
				throw new AppException("Mobile number is empty");
			
			
			
		    Boolean isValidMobileNumber=userRepository.existsByMobileNumber(subscriptionClickDTO.getMobileNumber());

		    if (!isValidMobileNumber)
				throw new AppException("mobile number not exist");
		    
			if (subscriptionClickDTO.getName() == null)
				throw new AppException("Name is empty");

			boolean isPresent = subscriptionClickRepository
					.existsByMobileNumberAndStatus(subscriptionClickDTO.getMobileNumber(), "Pending");

			if (isPresent) {
				result.setData("Subscription Details saved");
				result.setMessage("Subscription Details saved");
				result.setStatusCode(HttpStatus.OK.value());
				return result;
			}

			subscriptionClick.setMobileNumber(subscriptionClickDTO.getMobileNumber());
			subscriptionClick.setName(subscriptionClickDTO.getName());
			subscriptionClick.setVlUserId(subscriptionClickDTO.getVlUserId());
			subscriptionClick.setSource(subscriptionClickDTO.getSource());
			subscriptionClick.setStatus("Pending");
			

			subscriptionClickRepository.save(subscriptionClick);

			result.setData("Subscription Details saved");
			result.setMessage("Subscription Details saved");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}

	@Override
	public Document<List<SubscriptionClick>> getAllSubsriptionTickets() {
		Document<List<SubscriptionClick>> result = new Document<>();

		try {

			List<SubscriptionClick> subscriptionClickList = subscriptionClickRepository.findAll();

			result.setData(subscriptionClickList);
			result.setMessage("Request Sucessfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}


	@Override
	public Document<Boolean> getSubscriptionClickStatus() {
		Document<Boolean> result = new Document<Boolean>();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
            List<String> statusList=Arrays.asList("Pending");
            

			Boolean isPresent = subscriptionClickRepository.existsByVlUserIdAndStatusIn(loggedInUser.getUserSurId(), statusList);
		
            result.setData(isPresent);	
			result.setMessage("Request Sucessfull");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}
		
}
