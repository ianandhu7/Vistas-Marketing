package co.vistafoundation.vlearning.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.analytics.service.AnalyticsService;
import co.vistafoundation.vlearning.liveclass.service.LiveClassService;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.user_activity.config.LoggedUserHandler;
import co.vistafoundation.vlearning.video.service.SocialVideoService;

/**
 * @author Sarfaraz, Naveen
 *
 */
@RestController
@RequestMapping("/api/v1/scheduler")
public class SchedulerController {

	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@Autowired
	LiveClassService liveClassService;

	@Autowired
	SocialVideoService socialVideoService;
	
	@Autowired
	LoggedUserHandler loggedUserHandler;
	
	@Autowired
	UserService userService;

	@Autowired
	AnalyticsService analyticsService;
	
	
	/**
	 * @author Naveen Kumar A This scheduler will run each night at 12:30 and chech
	 *         for for exiperd student subscripiton records in any data found it
	 *         deactivate those records.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/check-subscription-expiry")
	public ResponseEntity<String> checkSubscriptionExpiry() {

		studentSubscriptionService.checkSubscriptionExpiry();

		return ResponseEntity.status(200).body("Student Subscritption Expiry Check Started Scucessfully");
	}

	/**
	 * @author Vinay Kumar This scheduler will run each night at 12:40 and check for
	 *         for exiperd student subscripiton records in any data found it
	 *         deactivate those records.
	 * 
	 *         deprecate by @author NAVEEN since the adfree user subscription is
	 *         removed completly for 1 month trial.
	 */
	@Deprecated
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	// @GetMapping(value = "/check-usersubscription-expiry")
	public void checkUserSubscriptionExpiry() {
		studentSubscriptionService.checkUserSubscriptionExpiry();
	}

	/**
	 * @author Sarfaraz Ahmed
	 * @return This will run the Scheduler at every Morning 10:30 and send
	 *         notification to student by checking current date is 3 day , 2 day,
	 *         one day earlier with respect to next payment date
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/payment-reminder")
	public ResponseEntity<String> paymentReminderScheduler() {

		studentSubscriptionService.paymentReminder();
		return ResponseEntity.status(200).body("Student Payment remainder notification scheduler started.");

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/check-deleted-live-class")
	public ResponseEntity<String> deactivateDeletedVideos() {

		liveClassService.deactivateDeletedVideos();

		return ResponseEntity.status(200).body("Check LiveClass Youtube Deleted videos Schduler started. ");
	}

	/**
	 * @author Naveen Kumar A
	 * 
	 *         This method will called every hour and send notifications to the user
	 *         who made request to notify for a particular live class.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/live-class-hourly-reminder")
	public ResponseEntity<String> sendNotification() {
		liveClassService.sendLiveClassReminderNotificationEmail();
		return ResponseEntity.status(200).body("Live Class Hourly Reminder Scheduler Strated.");
	}

	/**
	 * @author NAVEEN
	 * 
	 *         This method will get the inactive social video records and delete
	 *         them permanently from the system.
	 * @return Document<List<String>
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/delete-inactive-video")
	public ResponseEntity<String> deactivateInActiveDeletedVideos() {

		 socialVideoService.deleteSocialVideoByScheduler();
		return ResponseEntity.status(200).body("Social Video Clean Up Scheduler Strated.");

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/pending-payment-status-update")
	public ResponseEntity<String> updatePendingPaymentstatus() {
       studentSubscriptionService.paymentPendingStatusUpdate();
		return ResponseEntity.status(200).body("Pending Status update Scheduler Strated.");

	}
	
	
	/*
	 * @author NAVEEN KUMAR A
	 * Asynchronously deletes expired user sessions from DynamoDB to maintain a valid count of user information in the admin panel.
	 * The expired user sessions are identified based on certain criteria, such as session last activity  timestamp.
	 * Deleting expired sessions ensures that the user information displayed in the admin panel remains up to date and accurate.
	 * 
	 * modified by @author Abdul Elahi
	 * 
	 * added the hourly logging mechanism to this controller
	 * 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/expired-session-validator")
	public ResponseEntity<String> removeUserInactiveUserActivity() {
		loggedUserHandler.expiredSessionValidator();
		return ResponseEntity.status(200).body("User Activity Expried Session Validator Scheduler Strated.");

	}
	
    @GetMapping("/save-daily-logs")
    public ResponseEntity<String> generateAndSaveDailyLogs() {
        try {
            analyticsService.generateAndSaveDailyLogs();
            return ResponseEntity.status(HttpStatus.OK).body("Daily logs generation successful scheduler started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating daily logs: " + e.getMessage());
        }
    }
    
    @GetMapping("/save-weekly-logs")
    public ResponseEntity<String> generateAndSaveWeeklyLogs() {
        try {
            analyticsService.generateAndSaveWeeklyLogs();
            return ResponseEntity.status(HttpStatus.OK).body("Weekly logs generation successful scheduler started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating daily logs: " + e.getMessage());
        }
    }    
    
    @GetMapping("/save-monthly-logs")
    public ResponseEntity<String> generateAndSaveMonthlylyLogs() {
        try {
            analyticsService.generateAndSaveMonthlyLogs();
            return ResponseEntity.status(HttpStatus.OK).body("Monthly logs generation successful scheduler started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating daily logs: " + e.getMessage());
        }
    }

}

