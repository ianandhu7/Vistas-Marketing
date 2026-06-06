/**
 * 
 */
package co.vistafoundation.vlearning.auth.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.vistafoundation.vlearning.auth.dto.AfterSignUpMetadataDTO;
import co.vistafoundation.vlearning.auth.dto.ApiResponse;
import co.vistafoundation.vlearning.auth.dto.GoogleLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.InternalLoginRequest;
import co.vistafoundation.vlearning.auth.dto.LoginRequest;
import co.vistafoundation.vlearning.auth.dto.MobileNumberLoginRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.dto.SubscriptionClickDTO;
import co.vistafoundation.vlearning.auth.dto.TeacherInfoDTO;
import co.vistafoundation.vlearning.auth.model.SubscriptionClick;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.subscription.dto.FreeSignUp;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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
	ThreadPoolTaskExecutor asyncExecutor;

	@Autowired
	CaptureGeoLocation captureGeoLocation;

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/login")
	public ResponseEntity<Document> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> doc = authService.authenticateUser(loginRequest, device, ip, userAgent);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@PostMapping("/internal-login")
	public ResponseEntity<?> authenticateInternalUser(@Valid @RequestBody InternalLoginRequest loginRequest,
			Device device) {
		Document<?> doc = authService.authenticateInternalUser(loginRequest, device);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

		User result = new User();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username or Email Id is already taken!", null),
					HttpStatus.BAD_REQUEST);
		}

		// @Deprecated
		/*
		 * This statement deprecated due to email id no longer used for unique id in the
		 * column if (userRepository.existsByEmail(signUpRequest.getEmail())) { return
		 * new ResponseEntity(new ApiResponse(false, "Email Address already in use!",
		 * null), HttpStatus.BAD_REQUEST); }
		 */
		result = authService.registerUser(signUpRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully", null));
	}

	/**
	 * @author NaveenKumarA
	 * @param userName
	 * @return This method accepts @param username returns boolean as response for
	 *         username availablity.
	 * 
	 *         Upgraded by @author Abdul Elahi validates the username using regular
	 *         expression if it matches email pattern it will give message as email
	 *         available and not available
	 */
	@GetMapping("/check/username")
	public ResponseEntity<Document<Boolean>> checkUserNameAvailability(@RequestParam String userName) {
		Document<Boolean> doc = authService.getUserNameAvailablity(userName);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/getCorrespondingIds")
	@Transactional
	public ResponseEntity<Document> getIdsOfdifferentRole(@RequestParam String role, @RequestParam Long userId) {

		Document reponses = authService.getIdsOfdifferentRole(role, userId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * @author Ahmed Reza
	 * @param idVLUser
	 * @param oldPassword
	 * @param newPassword
	 * @return Reset the Password to new Password provided after validating old
	 *         password
	 * 
	 * @author Abdul Elahi added @param newUserFlag after new signup
	 */

	@GetMapping(value = "/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam(name = "idVLUser") Long idVLUser,
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "newPassword") String newPassword,
			@RequestParam(name = "newUserFlag", defaultValue = "false") boolean newUserFlag) {

		Document<?> reponses = authService.resetPassword(idVLUser, oldPassword, newPassword);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param forgotPasswordUsername
	 * @return Sends the reset password email after verifying the correct username
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/forgot-password")
	public ResponseEntity<Document> forgotPassword(
			@RequestParam(name = "forgotPasswordUsername") String forgotPasswordUsername) {
		Document reponses = authService.forgotPassword(forgotPasswordUsername);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param userSurId
	 * @param randomString
	 * @param forgotPasswordUsername
	 * @param newPassword
	 * @return Sets the new Password after matching the request parameters
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/verify-forgot-password")
	public ResponseEntity<Document> verifyForgotPassword(@RequestParam(name = "userSurId") Long userSurId,
			@RequestParam(name = "randomString") String randomString,
			@RequestParam(name = "forgotPasswordUsername") String forgotPasswordUsername,
			@RequestParam(name = "newPassword") String newPassword) {
		Document reponses = authService.verifyForgotPassword(userSurId, randomString, forgotPasswordUsername,
				newPassword);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author NaveenKumar A This method will return list of language.
	 * @return
	 */
	@GetMapping("/languages")
	public ResponseEntity<?> getAllLanguages() {

		Document<List<Language>> reponses = authService.getAllLanguage();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/freeSubscription")
	public ResponseEntity<Document> freeSubscription(@Valid @RequestBody FreeSignUp freeSignUp, Device device) {

		Document reponses = authService.registerUserforFree(freeSignUp, device);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param idSubject
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param category
	 * @param pageNumber
	 * 
	 *                        This method will return list teacherinfo DTO with
	 *                        paging feature , by defualt paging value will be 0 and
	 *                        category will be 'Academic'
	 * 
	 * @return
	 */
	@GetMapping("/fetch-teachers")
	public ResponseEntity<?> fetchAllTeachers(@RequestParam Long idSubject, @RequestParam Long idSyllabus,
			@RequestParam Long idClassStandard, @RequestParam(defaultValue = "ACADEMIC") String category,
			@RequestParam(defaultValue = "0") int pageNumber) {

		Document<Page<TeacherInfoDTO>> reponses = authService
				.getAllTeachersByIdSubjectAndSyllabusAndClassStandrdAndCategory(idSubject, idClassStandard, idSyllabus,
						category, pageNumber);

		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/teacherProfile")
	public ResponseEntity<?> getTeacherProfile(@RequestParam Long idTeacher) {
		Document reponses = authService.getTeacherProfile(idTeacher);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/statesList")
	public ResponseEntity<?> getAllState() {
		Document reponses = authService.getAllState();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param NewSignupRequestDTO
	 * @return Registering a Student or a Parent. Also Creating a Free Subscription
	 *         if idSubject is found in the request
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/new-signup")
	public ResponseEntity<Document> userRegistrationNewSignUp(@Valid @RequestBody NewSignupRequestDTO signUpRequest,
			Device device, HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document reponses = authService.userRegistrationNewSignUp(signUpRequest, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param AfterSignUpMetadataDTO
	 * @return Collecting Meta Data after successfully Signing up. Not in Use
	 * @deprecated
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/collect-metadata-after-signup")
	public ResponseEntity<Document> takeMetadataAfterSignup(
			@Valid @RequestBody AfterSignUpMetadataDTO afterSignUpMetadataDTO) {
		Document reponses = authService.takeMetadataAfterSignup(afterSignUpMetadataDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @return list of All Teachers available in Teacher Table
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@SuppressWarnings("rawtypes")
	@GetMapping("/teacherLists")
	public ResponseEntity<?> getAllTeacher() {
		Document reponses = authService.getAllTeacher();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param NewSignupRequestDTO
	 * @return Registering a Student or a Parent through Google oAuth. Also Creating
	 *         a Free Subscription if idSubject is found in the request
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/google-oauth-signup")
	public ResponseEntity<?> googleOAuthSignup(@RequestBody NewSignupRequestDTO signupRequestDTO, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document reponses = authService.googleOAuthSignup(signupRequestDTO, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Ahmed Reza
	 * @param username
	 * @param password
	 * @return Logging into the Application If User Has Previously registered
	 *         himself through Gooogle oAuth
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/google-oauth-login")
	public ResponseEntity<?> googleOAuthSignin(@RequestBody GoogleLoginRequestDTO googleLoginRequestDTO, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document reponses = authService.googleOAuthSignin(googleLoginRequestDTO, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar A
	 * @return This method will return list of ECA Levels.
	 */

	@GetMapping("/eca-levels")
	public ResponseEntity<?> getAllEcaLevel() {
		Document<List<LevelExtraCurricular>> reponses = authService.getAllExtraLevels();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/login/send-otp")
	public ResponseEntity<?> sendOTPToUser(@RequestParam String mobileNumber) {
		Document<Boolean> reponses = authService.sendOTPForUserLogin(mobileNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/login/verify")
	public ResponseEntity<?> verifyUser(@RequestBody MobileNumberLoginRequestDTO loginRequest, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<Map<String, Object>> reponses = authService.verifyOTPForUserLogin(loginRequest, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/forgot-password-internal")
	public ResponseEntity<Document> forgotPasswordInternalUser(
			@RequestParam(name = "forgotUsername") String forgotUsername) {
		Document reponses = authService.forgotPasswordInternaluser(forgotUsername);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/detect-device")
	public ResponseEntity<?> getDevice(Device device) {
		Document<String> reponses = new Document<String>();
		String msg = null;
		if (device.isMobile()) {
			msg = "mobile";
		} else if (device.isTablet()) {
			msg = "tablet";
		} else {
			msg = "desktop";
		}
		reponses.setData(msg + " device on platform " + device.getDevicePlatform().name());
		reponses.setStatusCode(200);
		reponses.setMessage("Request successfull.");
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/get-internal-corresponding-id")
	@Transactional
	public ResponseEntity<Document> getInternalIdsOfdifferentRole(@RequestParam String role,
			@RequestParam Long userId) {

		Document reponses = authService.getInternalIdOfdifferentRole(role, userId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping("/thread-pool-info")
	public ResponseEntity<?> getThreadPoolInfo() {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Thread Name", asyncExecutor.getThreadNamePrefix());
		result.put("Pool Size", asyncExecutor.getPoolSize());
		result.put("Max Pool Size", asyncExecutor.getMaxPoolSize());
		result.put("Core Pool Size", asyncExecutor.getCorePoolSize());
		result.put("Active pool ", asyncExecutor.getActiveCount());
		result.put("Inactive pool ", asyncExecutor.getPoolSize() - asyncExecutor.getActiveCount());
		return ResponseEntity.status(HttpStatus.OK.value()).body(result);
	}

	/**
	 * @author MOHAMMED HAARIS
	 * @return This method will the memory info and system properties.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/system-info/jvm-status")
	public Map<String, Object> getSystemInfo() {
		Map<String, Object> systemInfo = new HashMap<>();

		Map<String, Long> memoryInfo = getMemoryInfo();
		systemInfo.put("MemoryInfo", memoryInfo);
		Properties systemProperties = System.getProperties();
		systemProperties.remove("sun.boot.library.path");
		systemProperties.remove("package.access");
		systemProperties.remove("package.definition");
		systemProperties.remove("common.loader");
		systemProperties.remove("tomcat.util.scan");
		systemProperties.remove("StandardJarScanFilter.jarsToScan");
		systemProperties.remove("tomcat.util.scan.StandardJarScanFilter.jarsToSkip");

		systemInfo.put("SystemProperties", systemProperties);

		return systemInfo;
	}

	private Map<String, Long> getMemoryInfo() {
		Map<String, Long> memoryInfo = new HashMap<>();

		Runtime env = Runtime.getRuntime();
		memoryInfo.put("MaxHeapSizeMB", env.maxMemory() / (1024 * 1024));
		memoryInfo.put("CurrentHeapSizeMB", env.totalMemory() / (1024 * 1024));
		memoryInfo.put("FreeHeapSizeMB", env.freeMemory() / (1024 * 1024));
		memoryInfo.put("UsedHeapSizeMB", (env.totalMemory() - env.freeMemory()) / (1024 * 1024));
		memoryInfo.put("UnassignedHeapSizeMB", (env.maxMemory() - env.totalMemory()) / (1024 * 1024));
		memoryInfo.put("TotallyAvailableHeapSizeMB",
				((env.maxMemory() - env.totalMemory()) + env.freeMemory()) / (1024 * 1024));

		return memoryInfo;
	}

	@GetMapping("/getGeoLocation")
	public String getClientIp(HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor) {
		return extractClientIp(xForwardedFor, request);
	}

	private String extractClientIp(String xForwardedFor, HttpServletRequest request) {
		System.out.println(xForwardedFor);
		if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
			// The X-Forwarded-For header can contain a comma-separated list of IPs, return
			// the first one
			return xForwardedFor.split(",")[0].trim();
		}
		// Fallback to remote address if X-Forwarded-For header is not present
		String remoteAddr = request.getRemoteAddr();
		// Handle local testing addresses
		if (remoteAddr.equals("0:0:0:0:0:0:0:1") || remoteAddr.equals("::1")) {
			return "TestingIP";
		}
		return remoteAddr;
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/loggedin-devices")
	public ResponseEntity<?> getLoggedInDevices() {
		Document<?> reponses = authService.getUserLoggedinDevices();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/devices-history")
	public ResponseEntity<?> getDevicesHistory() {
		Document<?> reponses = authService.getDevicesHistory();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") 
	@PutMapping("/reset-max-attempts")
	public ResponseEntity<Document<String>> resetMaxAttempts(@RequestParam Long idVlUser) {
		Document<String> reponses = authService.resetMaxAttempts(idVlUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
		
	@PostMapping("/save-subscription-click-details")
	public ResponseEntity<Document<String>> saveSubscriptionClickDetails(@RequestBody SubscriptionClickDTO subscriptionClickDTO) {
		Document<String> reponses = authService.saveSubscriptionClickDetails(subscriptionClickDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-all-subscription-tickets")
	public ResponseEntity<Document<List<SubscriptionClick>>> getAllSubsriptionTickets() {
		Document<List<SubscriptionClick>> reponses = authService.getAllSubsriptionTickets();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping("/get-subscription-click-status")
	public ResponseEntity<Document<Boolean>> getSubscriptionClickStatus() {
		Document<Boolean> reponses = authService.getSubscriptionClickStatus();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}
