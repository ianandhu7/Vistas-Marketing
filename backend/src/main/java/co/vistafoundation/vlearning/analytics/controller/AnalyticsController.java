package co.vistafoundation.vlearning.analytics.controller;

import java.time.Instant;
import java.util.Date;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.analytics.repository.UserSignUpAnalyticsRepo;
import co.vistafoundation.vlearning.analytics.service.AnalyticsService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CheckSMSCredits;
import co.vistafoundation.vlearning.utils.SMSHorizonService;

/**
 * @author Mohan Kuamr
 * 
 *
 */
@RestController
@RequestMapping("api/v1/analytics")
public class AnalyticsController {

	@Autowired
	AnalyticsService analyticsService;

	
	@Autowired
	UserSignUpAnalyticsRepo userSignUpAnalyticsRepo;
	
	@Autowired
	SMSHorizonService smsHorizonService;


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/user-signup-subscription-analytics")
	public ResponseEntity<?> getUserSignUpAndSubscription(@RequestParam String dateType,
			@RequestParam(defaultValue = "-1", required = false) String startDate,
			@RequestParam(defaultValue = "-1", required = false) String endDate) {
		Document<?> response = analyticsService.getUserSignUpAndSubscription(dateType, startDate, endDate);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping(value = "/user-role-count")
	public ResponseEntity<?> getUserRoleCount() {
		Document<?> response = analyticsService.getUserRoleCount();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping(value = "/getUserLiveCountTimelyBasis")
	public ResponseEntity<?> getUserActivityTimelyBasis(@RequestParam("type") String type,
	        @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date from,
	        @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date to) {
		Document<?> response = analyticsService.getUserLiveCountOnTimeBases(type, from, to);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("/fetchDataFromDynamoDB")
	public ResponseEntity<?> fetchDataFromDynamoDB() {
		analyticsService.fetchDataFromDynamoDB();
		return ResponseEntity.status(200).body("Successfully trigered");
	}

	/**
	 * @author Abdul Elahi this api fetches the last 30 number of live users
	 */
	@GetMapping("/live-analytics")
	public ResponseEntity<?> fetchlivedata() {
		Document<?> response = analyticsService.getLastTenActivities();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/check-sms-credits")
	public ResponseEntity<?> checkSMSCredits() {
		Document<CheckSMSCredits> doc = smsHorizonService.checkSMSCredits();
		System.out.println(doc.getData()+"\n"+doc.getStatusCode()+"\n"+doc.getMessage());
		return ResponseEntity.status(doc.getStatusCode()).body(doc);
	}

}
