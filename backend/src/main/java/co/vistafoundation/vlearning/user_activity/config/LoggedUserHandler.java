/**
 * 
 */
package co.vistafoundation.vlearning.user_activity.config;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.analytics.model.UserActivityLiveLogs;
import co.vistafoundation.vlearning.analytics.repository.UserActivityLiveLogsRepository;
import co.vistafoundation.vlearning.analytics.service.AnalyticsService;
import co.vistafoundation.vlearning.user_activity.UserActivity;
import co.vistafoundation.vlearning.user_activity.repository.UserActivityRepository;
import co.vistafoundation.vlearning.utils.GenerateTime;

/**
 * @author NaveenKumar
 *
 */
@Component
public class LoggedUserHandler {

	@Autowired
	AnalyticsService analyticsService;

	@Autowired
	UserActivityRepository userActivityRepository;

	@Autowired
	UserActivityLiveLogsRepository userActivityLiveLogsRepository;

	private static final Logger logger = LoggerFactory.getLogger(LoggedUserHandler.class);

	@Async
	public void updateUserCount(UserActivity sessionUserActivity) {
		UserActivity userActivity = null;

		String device = sessionUserActivity.getDeviceType() != null
				? ((sessionUserActivity.getDeviceType().equalsIgnoreCase("mobile")
						|| sessionUserActivity.getDeviceType().equalsIgnoreCase("tablet")) ? "mobile" : "web")
				: "web";

		sessionUserActivity.setDeviceType(device);
		String uniqueKey = sessionUserActivity.getUserSurId() + "#" + device.toUpperCase();
		sessionUserActivity.setIdUserActivity(uniqueKey);

		userActivity = userActivityRepository.getUserActivity(uniqueKey);

		if (userActivity != null) { // updating the session entry
			userActivity.setLastActivityTime(Instant.now().toString());
			userActivityRepository.update(userActivity);
		} else {
			// creating new entry of logging record
			userActivityRepository.save(sessionUserActivity);
		}

	}

	@Async
	public void deleteUserCount(Long userSurId, String device) {

		UserActivity userActivity = null;

		String uniqueKey = userSurId + "#" + device.toUpperCase();

		userActivity = userActivityRepository.getUserActivity(uniqueKey);

		if (userActivity != null)
			userActivityRepository.delete(userActivity.getIdUserActivity());

	}

//	@Async  removed because it is not logging in production
	public void expiredSessionValidator() {
		logger.info("Expired session validator is started.");
		int removedSessionCount = 0;
		Instant now = Instant.now();
		List<UserActivity> activeUserStore = userActivityRepository.getAllUserActivity();

		List<UserActivity> finalList = new ArrayList<>();

		Instant previousTime = now.minus(Duration.ofHours(24));

		for (UserActivity ua : activeUserStore) {
			if (Instant.parse(ua.getLastActivityTime()).compareTo(previousTime) < 0) {
				userActivityRepository.delete(ua.getIdUserActivity());
				removedSessionCount++;
			} else {
				finalList.add(ua);
			}
		}

		logger.info("Total user activity session found: " + activeUserStore.size());
		logger.info("Total user delete session: " + removedSessionCount);

		analyticsService.getUserLiveCountBasedOnTime(finalList);

	}

	 public void addUserActivity(UserActivityLiveLogs activity) {
	        Instant before = Instant.now().minus(59, ChronoUnit.SECONDS);
	        Long userSurId = activity.getUserSurId();
	        
	        activity.setTimestamp(GenerateTime.truncateToMinutes(Instant.now()));

	        boolean isExist = userActivityLiveLogsRepository.existsByUserSurIdAndTimestampBetween(userSurId, before , Instant.now());

	        if (!isExist) {
	            userActivityLiveLogsRepository.save(activity);
	        }

	        deleteOldRecords();
	    }

	private void deleteOldRecords() {
		long currentCount = userActivityLiveLogsRepository.count();
		if (currentCount > 1000) {
			List<UserActivityLiveLogs> oldestRecord = userActivityLiveLogsRepository.findOldestRecord();
			if (!oldestRecord.isEmpty()) {
				userActivityLiveLogsRepository.delete(oldestRecord.get(0));
			}
		}
	}
}
