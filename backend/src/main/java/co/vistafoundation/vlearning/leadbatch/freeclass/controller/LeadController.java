/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.vistafoundation.vlearning.auth.dto.ApiResponse;
import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsExtraCurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchExtraCurDetailDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetailsExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.LeadService;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@RestController
@RequestMapping("/api/v1/lead")
public class LeadController {

	@Autowired
	private LeadService leadService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthService authService;

	/**
	 * @author Ahmed Reza
	 * @param SignUpRequest
	 * @return Registering User while Booking for Demo CLass And then taking Lead
	 *         BAtch Details Info. Sending email with the generated Password
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/createUserForBookFreeClass")
	public ResponseEntity<?> createUserForBookFreeClass(@Valid @RequestBody SignUpRequest signUpRequest) {
		User result = new User();
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username or Email Id is already taken!", null),
					HttpStatus.BAD_REQUEST);
		}

		result = leadService.registerUser(signUpRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully", result));
	}

	/**
	 * @author Ahmed Reza
	 * @param LeadBatchDetailsRequest
	 * @return Capturing Lead Batch Details Information and Sending Email
	 *         Confirmation
	 */

	@PreAuthorize("hasAnyRole('ROLE_STUDENT,ROLE_PARENT')")
	@PostMapping("/saveleadbatchDetailsData")
	public ResponseEntity<?> saveLeadBatchDetailsData(
			@RequestBody LeadBatchDetailsRequest leadBatchDetailsRequest) {

		Document<?> doc = leadService.saveLeadBatchDetailsData(leadBatchDetailsRequest);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @param LeadBatchDetailsRequest
	 * @return Signing up for a User Account as well as Capturing Lead Batch Details
	 *         Information and Sending Email Confirmation
	 */
	@PostMapping("/saveleadbatchDetailsDataAfterLoggedIn")
	public ResponseEntity<?> saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			@RequestBody LeadBatchDetailsRequest leadBatchDetailsRequest) {

		Document<?> doc = leadService.saveLeadBatchDetailsDataAfterLogginInBookFreeClass(leadBatchDetailsRequest);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @return List of All Syllabus Available in DB
	 */
	
	@GetMapping("/syllabusLists")
	public ResponseEntity<?> fetchAllSyallabus() {
		Document<?> doc = leadService.fetchAllSyllabus();
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	
	@PostMapping("/sendotp")
	public ResponseEntity<?> sendOtp(@RequestParam String mobile, @RequestParam(required=false) String email,
			@RequestParam String name) {
		Document<?> doc = leadService.sendOtp(mobile, email, name);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}
	

	@PostMapping("/verifyotp")
	public ResponseEntity<?> verifyOtp(@RequestParam String mobile, @RequestParam String otp) {
		Document<?> doc = leadService.verifyOtp(mobile, otp);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}
	
	
	@PostMapping("/forgot-password-sendotp")
	public ResponseEntity<?> sendOtpForResetPassword(@RequestParam String mobile) {
		Document<?> doc = leadService.sendOtpForResetPassword(mobile);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}
	
	
	@PostMapping("/forgot-password-verifyotp")
	public ResponseEntity<?> verifyOtpForgotPassword(@RequestParam String mobile, @RequestParam String otp) {
		Document<?> doc = leadService.verifyOtpForResetPassword(mobile, otp);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @param mobile
	 * @return Checking for Duplicate Mobile Number in vl_user Table. One Person One
	 *         Mobile Number
	 */
	@GetMapping("/checkMobileNumberExistence")
	public ResponseEntity<?> checkMobileNumberExistence(@RequestParam(name = "mobile") String mobile) {
		Document<?> doc = leadService.checkMobileNumberExistence(mobile);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @return Lists of All Available SLots Available in DB
	 */
	
	@GetMapping("/fetchAllAvailableSlots")
	public ResponseEntity<?> fetchAllAvailableSlots() {
		Document<?> doc = leadService.fetchAllAvailableSlots();
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @param LeadBatchDetailsExtraCurricular
	 * @return Capturing Lead Batch Details ExtraCurricular Information and Sending
	 *         Email Confirmation
	 */
	@PostMapping("/saveLeadBatchDetailsExtraCurricularData")
	public ResponseEntity<?> saveLeadBatchDetailsExtraCurricularData(
			@RequestBody LeadBatchDetailsExtraCurricularDTO leadBatchDetailsExtraCurricular) {

//		Document<?> doc = leadService.saveLeadBatchDetailsExtraCurricularData(leadBatchDetailsExtraCurricular);
		return ResponseEntity.status(410).body(null);
	}

	/**
	 * @author Ahmed Reza
	 * @param LeadBatchDetailsExtraCurricular
	 * @return Capturing Lead Batch Details ExtraCurricular Information After Loggin
	 *         In and Sending Email Confirmation
	 */
	@PostMapping("/saveLeadBatchDetailsExtraCurricularDataAfterLoggedIn")
	public ResponseEntity<?> saveLeadBatchExtraCurricularDetailsDataAfterLoggedIn(
			@RequestBody LeadBatchDetailsExtraCurricular leadBatchDetailsExtraCurricular) {

//		Document<?> doc = leadService
//				.saveLeadBatchExtraCurricularDetailsDataAfterLoggedIn(leadBatchDetailsExtraCurricular);
		return ResponseEntity.status(410).body(null);
	}

	/**
	 * @author Ahmed Reza
	 * @return Lists of All Extra Curricular Levels Available in DB
	 */
	@GetMapping("/fetchAllLevelsExtraCurricular")
	public ResponseEntity<?> fetchAllLevelsExtraCurricular() {

		Document<?> doc = leadService.fetchAllLevelsExtraCurricular();
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @param userSurId
	 * @param category
	 * @return One User One Lead. Only One Demo Class is Allowed from Both Academic
	 *         And Extra Curricular Side. Return False if no demo class History
	 *         Found. else will throw exception
	 */
	@GetMapping("/checkForDemoClassBookedHistoryForUser")
	public ResponseEntity<?> checkForDemoClassBookedHistoryForUser(@RequestParam("userSurId") Long userSurId,
			@RequestParam("category") String category) {

		Document<?> doc = leadService.checkForDemoClassBookedHistoryForUser(userSurId, category);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	/**
	 * @author Ahmed Reza
	 * @param idSubject
	 * @param idLevel
	 * @return Lists of All Students registered for Extra Curricular Demo class for
	 *         this Subject and Level. Telecaller
	 */
	@GetMapping("/filterExtraCurricularLeadBatchDetails")
	public ResponseEntity<?> filterExtraCurricularLeadBatchDetails(@RequestParam("idSubject") Long idSubject,
			@RequestParam("idLevel") Long idLevel) {

		Document<?> doc = leadService.filterExtraCurricularLeadBatchDetails(idSubject, idLevel);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> AllLeadBatchDetailsCount() {
		Document<List<LeadBatchDetailsDTO>> reponses = leadService.AllLeadBatchDetailsCount();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping(value = "/extracur-leads-count")
	public ResponseEntity<?> getAllExtraCurLeadsCount() {
		Document<List<LeadBatchExtraCurDetailDTO>> reponses = leadService.getAllExtraCurLeadsCount();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}


	@GetMapping("/getHomepageBookFreeTrialAndPersonalCoachingData")
	public ResponseEntity<?> getHomepageBookFreeTrialAndPersonalCoachingData(@RequestParam("category") String category,
			@RequestParam("idVlUser") Long idVlUser, @RequestParam("idStudent") Long idStudent) {
		Document<?> reponses = leadService.getHomepageBookFreeTrialAndPersonalCoachingData(category, idVlUser, idStudent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping("/checkForLeadBatchLogsAndLeadAttendedEntry")
	public ResponseEntity<?> checkForLeadBatchLogsAndLeadAttendedEntry(@RequestParam("category") String category,
			@RequestParam("userSurId") Long idVlUser) {

		Document<?> reponses = leadService.checkForLeadBatchLogsAndLeadAttendedEntry(category, idVlUser);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping("/checkForDemoClassBookedInLeadBatchDetails")
	public ResponseEntity<?> checkForDemoClassBookedInLeadBatchDetails(@RequestParam("userSurId") Long userSurId,
			@RequestParam("category") String category) {

		Document<?> reponses = leadService.checkForDemoClassBookedInLeadBatchDetails(userSurId, category);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	@GetMapping("/checkForDemoClassBookedInLeadBatchLog")
	public ResponseEntity<?> checkForDemoClassBookedInLeadBatchLog(@RequestParam("userSurId") Long userSurId,
			@RequestParam("category") String category) {

		Document<?> reponses = leadService.checkForDemoClassBookedInLeadBatchLog(userSurId, category);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
}
