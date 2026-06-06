package co.vistafoundation.vlearning.analytics.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import co.vistafoundation.vlearning.analytics.dto.UserSignUpAndSubscriptionDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user_activity.UserActivity;

public interface AnalyticsService {

	public Document<List<Object>> getUserRoleCount();

	void getUserLiveCountBasedOnTime(List<UserActivity> userActvity);

	Document<?> getUserLiveCountOnTimeBases(String dateType, Date from, Date to);

	void generateAndSaveDailyLogs();

	void generateAndSaveWeeklyLogs();

	void generateAndSaveMonthlyLogs();

	void fetchDataFromDynamoDB();

	public Document<?> getLastTenActivities();

	/**
	 * @param dateType
	 * @param from
	 * @param to
	 * @return
	 */
	public Document<UserSignUpAndSubscriptionDTO> getUserSignUpAndSubscription(String dateType, String startDate,String endDate);

}
