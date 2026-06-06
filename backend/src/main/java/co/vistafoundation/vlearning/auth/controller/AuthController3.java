/**
 * 
 */
package co.vistafoundation.vlearning.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.auth.config.PaytmPaymentConfig;
import co.vistafoundation.vlearning.auth.dto.GoogleOauthSignupV3;
import co.vistafoundation.vlearning.auth.dto.ManualSignupDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupClassInfoRequestDTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV3DTO;
import co.vistafoundation.vlearning.auth.dto.NewSignupRequestV4DTO;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.auth.service.AuthService3;
import co.vistafoundation.vlearning.auth.utils.CaptureGeoLocation;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequestV3;

/**
 * @author NAVEEN
 *
 *         This V3 Controllers created to enhance security in order to avoid
 *         take over Bypassing Mobile Verification during the time of sign-up,
 *         Please refer https://v-learning.atlassian.net/browse/VL-1216 for
 *         further information.
 * 
 */

@RestController
@RequestMapping("/api/v3/auth")
class AuthController3 {

	@Autowired
	AuthService3 authService3;

	@Autowired
	AuthService authService;

	@Autowired
	PaytmPaymentConfig paytmPaymentConfig;

	@Autowired
	CaptureGeoLocation geoLocation;

	@PostMapping("/new-signup")
	public ResponseEntity<?> userRegistrationNewSignUp(@Valid @RequestBody NewSignupRequestV3DTO signUpRequest,
			Device device, HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> reponses = authService3.userRegistrationNewSignUp(signUpRequest, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PostMapping("/saveleadbatchDetailsDataAfterLoggedIn")
	public ResponseEntity<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			@RequestBody LeadBatchDetailsRequestV3 leadBatchDetailsRequest) {

		Document<?> doc = authService3.saveLeadBatchDetailsDataAfterLogginInBookFreeClass(leadBatchDetailsRequest);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@PostMapping("/google-oauth-signup")
	public ResponseEntity<?> googleOAuthSignup(@RequestBody GoogleOauthSignupV3 signupRequestDTO, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> reponses = authService3.googleOAuthSignup(signupRequestDTO, device, ip, userAgent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * modified
	 * 
	 * @author Abdul-Elahi added boolean newUserFlag after new auth signup
	 * 
	 */

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam(name = "idVLUser") Long idVLUser,
			@RequestParam(name = "oldPassword") String oldPassword,
			@RequestParam(name = "newPassword") String newPassword) {

		Document<?> reponses = authService.resetPassword(idVLUser, oldPassword, newPassword);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Abdul Elahi Verifies bulk signups by validating an uploaded batch
	 *         file.
	 *
	 * @param batchFile The batch file containing signup data.
	 * @return A ResponseEntity containing a Document object representing the
	 *         validation result.
	 */
	@Deprecated
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-signup/verify")
	public ResponseEntity<Document<?>> verifyBulkSignup(@RequestParam("file") MultipartFile batchFile) {
		Document<?> result = authService3.validateUploadBatchMannualSignUp(batchFile);

		HttpStatus status = HttpStatus.valueOf(result.getStatusCode());
		return ResponseEntity.status(status).body(result);
	}

	/**
	 * @author Abdul Elahi Uploads bulk signup data by processing an uploaded batch
	 *         file.
	 *
	 * @param batchFile The batch file containing signup data.
	 * @return A ResponseEntity containing a Document object representing the upload
	 *         result.
	 * @updated by @author Abdul Elahi nerged verify snd upload api to one api
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-signup/upload")
	public ResponseEntity<Document<?>> uploadBulkSignup(@RequestParam("file") MultipartFile batchFile) {
		Document<?> result = authService3.uploadBulkSignupDataV2(batchFile);

		HttpStatus status = HttpStatus.valueOf(result.getStatusCode());
		return ResponseEntity.status(status).body(result);
	}

	/**
	 * Handles a manual signup request, allowing users with the 'ROLE_ADMIN' role to
	 * sign up
	 * 
	 * It processes the manual signup request by accepting a `ManualSignupDTO`
	 * object in the request body and delegates the signup operation to the
	 * `authService3.manualSignup` method.
	 *
	 * @param manualSignupDTO The data necessary for the manual signup process.
	 * @return A ResponseEntity containing a Document with the result of the manual
	 *         signup operation. The HTTP status of the response is determined by
	 *         the result's status code.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/manual-signup")
	public ResponseEntity<Document<?>> manualSignup(@RequestBody ManualSignupDTO manualSignupDTO) {
		Document<?> result = authService3.manualSignup(manualSignupDTO);

		HttpStatus status = HttpStatus.valueOf(result.getStatusCode());
		return ResponseEntity.status(status).body(result);
	}

	/**
	 * @author Abdul Elahi
	 * 
	 *         Handles HTTP POST requests for new user signup with mobile number and
	 *         name without additional parameters.
	 *
	 * @param signUpRequest The request body containing information for the new
	 *                      signup.
	 * @param device        The device information from the request.
	 * @return A ResponseEntity containing a Document with information about the
	 *         signup result. The HTTP status code in the ResponseEntity corresponds
	 *         to the result status. - Successful signup: HTTP 200 OK - Error during
	 *         signup: Appropriate HTTP error status code
	 */
	@PostMapping("/new-auth-signup")
	public ResponseEntity<Document<?>> newSignup(@RequestBody NewSignupRequestV4DTO signUpRequest, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> result = authService3.newSignup(signUpRequest, device, ip, userAgent);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @author Abdul Elahi
	 * 
	 *         Handles HTTP POST requests to save additional class related
	 *         information after user signup.
	 *
	 * @param dto    The request body containing additional information for the user
	 *               signup.
	 * @param device The device information from the request.
	 * @return A ResponseEntity containing a Document with information about the
	 *         result of saving additional details after signup. The HTTP status
	 *         code in the ResponseEntity corresponds to the result status. -
	 *         Successful save: HTTP 200 OK - Error during save: Appropriate HTTP
	 *         error status code
	 */
	@PostMapping("/save-info-post-signup")
	public ResponseEntity<Document<?>> fillDataAfterSignup(@RequestBody NewSignupClassInfoRequestDTO dto, Device device,
			HttpServletRequest request,
			@RequestHeader(value = "X-Forwarded-For", required = false) String xForwardedFor,
			@RequestHeader(value = "User-Agent", required = false) String userAgent) {
		String ip = extractClientIp(xForwardedFor, request);
		Document<?> result = authService3.fillDetailsAfterSignup(dto, device, ip, userAgent);
		return ResponseEntity.status(result.getStatusCode()).body(result);
	}

	/**
	 * @author Abdul Elahi
	 * 
	 *         Endpoint for validating the default signup password.
	 *
	 *         This endpoint checks if the current user's password matches the
	 *         default signup password.
	 *
	 * @return A ResponseEntity containing a Document with the validation result.
	 *         The Document will include a status code, data (result of the
	 *         validation), and a message.
	 */
	@GetMapping("/new-signup-reset-password")
	public ResponseEntity<Document<?>> defaultSignupPasswordValidation() {
		Document<?> result = authService3.defaultSignupPasswordValidation();
		return ResponseEntity.status(result.getStatusCode()).body(result);
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
