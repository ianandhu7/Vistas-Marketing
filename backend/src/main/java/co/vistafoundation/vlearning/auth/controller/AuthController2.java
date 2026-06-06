/**
 * 
 */
package co.vistafoundation.vlearning.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.auth.dto.NewSignupRequestDTO;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.JwtTokenProvider;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.auth.service.AuthService2;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;

/**
 * @author Noushad
 *
 */
@RestController
@RequestMapping("/api/v2/auth")

public class AuthController2 {

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
	AuthService2 authService2;

	@Autowired
	CaptureGeoLocation location;

	@PostMapping("/new-signup")
	public ResponseEntity<?> userRegistrationNewSignUp(@Valid @RequestBody NewSignupRequestDTO signUpRequest,
			Device device, HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> reponses = authService2.userRegistrationNewSignUp(signUpRequest, device, ip,userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/google-oauth-signup")
	public ResponseEntity<?> googleOAuthSignup(@RequestBody NewSignupRequestDTO signupRequestDTO, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> reponses = authService2.googleOAuthSignup(signupRequestDTO, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/verify-referral-code")
	public ResponseEntity<?> verifyReferralCode(@RequestParam String referralCode) {
		Document<Boolean> reponses = authService2.verifyReferralCode(referralCode);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/saveleadbatchDetailsDataAfterLoggedIn")
	public ResponseEntity<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			@RequestBody LeadBatchDetailsRequest leadBatchDetailsRequest) {

		Document<?> doc = authService2.saveLeadBatchDetailsDataAfterLogginInBookFreeClass(leadBatchDetailsRequest);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
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

}
