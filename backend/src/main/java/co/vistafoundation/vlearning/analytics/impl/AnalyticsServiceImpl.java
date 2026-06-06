package co.vistafoundation.vlearning.analytics.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import co.vistafoundation.vlearning.analytics.dto.UserActivityTimelyLogDTO;
import co.vistafoundation.vlearning.analytics.dto.UserSignUpAndSubscriptionDTO;
import co.vistafoundation.vlearning.analytics.model.UserActivityDailyLog;
import co.vistafoundation.vlearning.analytics.model.UserActivityMonthlyLog;
import co.vistafoundation.vlearning.analytics.model.UserActivityTimelyLog;
import co.vistafoundation.vlearning.analytics.model.UserActivityWeeklyLog;
import co.vistafoundation.vlearning.analytics.repository.UserActivityDailyLogRepository;
import co.vistafoundation.vlearning.analytics.repository.UserActivityLiveLogsRepository;
import co.vistafoundation.vlearning.analytics.repository.UserActivityMonthlyLogRepository;
import co.vistafoundation.vlearning.analytics.repository.UserActivityTimelyLogRepository;
import co.vistafoundation.vlearning.analytics.repository.UserActivityWeeklyLogRepository;
import co.vistafoundation.vlearning.analytics.repository.UserRoleCountRepo;
import co.vistafoundation.vlearning.analytics.repository.UserSignUpAnalyticsRepo;
import co.vistafoundation.vlearning.analytics.repository.UserSubscriptionAnalyticsRepo;
import co.vistafoundation.vlearning.analytics.service.AnalyticsService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user_activity.UserActivity;
import co.vistafoundation.vlearning.user_activity.UserActivityTimelyLogs;
import co.vistafoundation.vlearning.user_activity.config.DynamoDbConfig;
import co.vistafoundation.vlearning.user_activity.repository.UserActivityRepository;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.GenerateTime;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	UserSignUpAnalyticsRepo userSignUpAnalyticsRepo;

	@Autowired
	UserSubscriptionAnalyticsRepo userSubscriptionAnalyticsRepo;

	@Autowired
	UserRoleCountRepo userRoleCountRepo;

	@Autowired
	UserActivityTimelyLogRepository userActivityTimelyLogRepository;

	@Autowired
	UserActivityDailyLogRepository userActivityDailyLogRepository;

	@Autowired
	UserActivityWeeklyLogRepository userActivityWeeklyLogRepository;

	@Autowired
	DynamoDBMapper dynamoDBMapper;

	@Autowired
	UserActivityRepository userActivityRepository;

	@Autowired
	DynamoDbConfig dynamoDbConfig;

	@Autowired
	UserActivityMonthlyLogRepository userActivityMonthlyLogRepository;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	private UserActivityLiveLogsRepository userActivityLiveRepository;

	private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

	private Instant convertToInstant(Date date) {
		Date utilDate = new Date(date.getTime());
		return utilDate.toInstant();
	}

	@Override
	public Document<UserSignUpAndSubscriptionDTO> getUserSignUpAndSubscription(String dateType, String startDate,
			String endDate) {


		Document<UserSignUpAndSubscriptionDTO> response = new Document<>();

		UserSignUpAndSubscriptionDTO userSignUpAndSubscriptionDTO = new UserSignUpAndSubscriptionDTO();

		Integer signUpUserCount = null;
		Integer subscriptionUserCount = null;

		try {

			if (startDate.equals("-1") && endDate.equals("-1")) {
				List<Integer> signUpcountList = new ArrayList<>();
				List<Integer> subscriptioncountList = new ArrayList<>();

				List<String> dateList = new ArrayList<>();

				Map<String, String> intervals = new LinkedHashMap<>();

				String fromDate = null;
				String toDate = null;
				switch (dateType.toLowerCase()) {

				case "daily":
					fromDate = LocalDate.now().minusDays(10).toString();
					toDate = LocalDate.now().toString();
					intervals = getDailyIntervels(fromDate, toDate);
					break;
				case "weekly":
					fromDate = LocalDate.now().minusMonths(1).withDayOfMonth(1).toString();
					toDate = LocalDate.now().toString() ;
					intervals = getWeeklyIntervels(fromDate, toDate);
					break;
				case "monthly":
					fromDate = LocalDate.now().withDayOfYear(1).toString();
					toDate = LocalDate.now().toString() ;
					intervals = getMonthlyIntervels(fromDate, toDate);
					break;
				case "yearly":
					fromDate = LocalDate.now().minusYears(5).withDayOfYear(1).toString();
					toDate = LocalDate.now().toString();
					intervals = getYearlyIntervels(fromDate, toDate);
					break;

				default:
					throw new IllegalArgumentException("Invalid dateType: " + dateType);

				}

				for (Map.Entry<String, String> entry : intervals.entrySet()) {

					
					signUpUserCount = userSignUpAnalyticsRepo.getUserSignUpCount(entry.getKey(), entry.getValue());
					subscriptionUserCount = userSubscriptionAnalyticsRepo.getUserSubscriptionCount(entry.getKey(),
							entry.getValue());
					signUpcountList.add(signUpUserCount);
					subscriptioncountList.add(subscriptionUserCount);
					dateList.add(entry.getValue().substring(0, 10));

				}

				userSignUpAndSubscriptionDTO.setUserSignUpData(signUpcountList);
				userSignUpAndSubscriptionDTO.setUserSubscriptionData(subscriptioncountList);
				userSignUpAndSubscriptionDTO.setLabels(dateList);

				response.setData(userSignUpAndSubscriptionDTO);
				response.setMessage("Request Successfull");
				response.setStatusCode(HttpStatus.OK.value());

			} else {
				List<Integer> signUpcountList = new ArrayList<>();
				List<Integer> subscriptioncountList = new ArrayList<>();

				List<String> dateList = new ArrayList<>();

				Map<String, String> intervals = new LinkedHashMap<>();

				switch (dateType.toLowerCase()) {

				case "daily":
					intervals = getDailyIntervels(startDate, endDate);
					break;
				case "weekly":
					intervals = getWeeklyIntervels(startDate, endDate);
					break;
				case "monthly":
					intervals = getMonthlyIntervels(startDate, endDate);
					break;
				case "yearly":
					intervals = getYearlyIntervels(startDate, endDate);
					break;

				default:
					throw new IllegalArgumentException("Invalid dateType: " + dateType);

				}

				for (Map.Entry<String, String> entry : intervals.entrySet()) {

					signUpUserCount = userSignUpAnalyticsRepo.getUserSignUpCount(entry.getKey(), entry.getValue());
					subscriptionUserCount = userSubscriptionAnalyticsRepo.getUserSubscriptionCount(entry.getKey(),
							entry.getValue());
					signUpcountList.add(signUpUserCount);
					subscriptioncountList.add(subscriptionUserCount);
					dateList.add(entry.getValue().substring(0, 10));

				}

				userSignUpAndSubscriptionDTO.setUserSignUpData(signUpcountList);
				userSignUpAndSubscriptionDTO.setUserSubscriptionData(subscriptioncountList);
				userSignUpAndSubscriptionDTO.setLabels(dateList);

				response.setData(userSignUpAndSubscriptionDTO);
				response.setMessage("Request Successfull");
				response.setStatusCode(HttpStatus.OK.value());

			}
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(e.getLocalizedMessage());
			return response;
		}

		return response;
	}


	@Override
	public Document<List<Object>> getUserRoleCount() {

		Document<List<Object>> response = new Document<>();
		try {
			List<Object[]> users = userRoleCountRepo.getUserRoleCount();

			List<Object> result = new ArrayList<>();

			for (Object obj[] : users) {

				HashMap<String, Object> roleMap = new HashMap<>();
				roleMap.put("role", obj[0]);
				roleMap.put("active", obj[1]);
				roleMap.put("inactive", obj[2]);
				roleMap.put("total", obj[3]);
				result.add(roleMap);

			}
			response.setData(result);
			response.setMessage("Request Successfull");
			response.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			response.setData(null);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(e.getLocalizedMessage());
			return response;
		}
		return response;
	}

	public Document<Map<String, Object>> getUserLiveCountFromActiveStore(List<UserActivity> userActvity) {

		Document<Map<String, Object>> result = new Document<>();

		try {

			Map<String, Object> response = new HashMap<>();

			if (userActvity.isEmpty()) {
				response.put("mobileActiveSession", 0);
				response.put("mobileInActiveSession", 0);
				response.put("webActiveSession", 0);
				response.put("webInActiveSession", 0);
				response.put("totalSession", 0);

			} else {

				int mobileActiveSession, mobileInActiveSession, webActiveSession, webInActiveSession;

				mobileActiveSession = 0;
				mobileInActiveSession = 0;
				webActiveSession = 0;
				webInActiveSession = 0;

				for (UserActivity ua : userActvity) {

					Instant thresholdTime = Instant.now().minus(10, ChronoUnit.MINUTES);

					if (Instant.parse(ua.getLastActivityTime()).compareTo(thresholdTime) > 0) {

						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileActiveSession++;
						else
							webActiveSession++;

					} else {

						if (ua.getDeviceType().equalsIgnoreCase("mobile"))
							mobileInActiveSession++;
						else
							webInActiveSession++;
					}

				}

				response.put("mobileActiveSession", mobileActiveSession);
				response.put("mobileInActiveSession", mobileInActiveSession);
				response.put("webActiveSession", webActiveSession);
				response.put("webInActiveSession", webInActiveSession);
				response.put("totalSession", userActvity.size());

			}

			result.setData(response);
			result.setMessage("user online  active status fetched sucessfully!");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(500);

		}

		return result;
	}

	/**
	 * @author Abdul Elahi.
	 * 
	 *         Calculates and stores user live count based on time for the given
	 *         list of user activities.
	 *
	 *         This method calculates user live count data based on the provided
	 *         list of user activities and stores it in a UserActivityTimelyLog. The
	 *         user live count data is partitioned based on time and includes
	 *         information about mobile and web clients, active and idle sessions,
	 *         and total sessions.
	 *
	 * @param userActivity A list of UserActivity objects representing user activity
	 *                     data.
	 */
	@Override
	public void getUserLiveCountBasedOnTime(List<UserActivity> userActvity) {

		try {
			Document<Map<String, Object>> userLiveCountData = getUserLiveCountFromActiveStore(userActvity);

			UserActivityTimelyLog userActivityLog = new UserActivityTimelyLog();
			int avg = userLiveCountData.getData().size();
			Instant instant = Instant.now();
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			ZonedDateTime zonedDateTime = instant.atZone(zoneId);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

			LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
			localDateTime = localDateTime.withMinute(0).withSecond(0).withNano(0);
			instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
			Date date = Date.from(instant);

			String formattedTimestamp = formatter.format(zonedDateTime);

			userActivityLog.setPartitionKey(formattedTimestamp);
			userActivityLog.setMobileClient((Integer) userLiveCountData.getData().get("mobileActiveSession") / avg);
			userActivityLog.setWebClient((Integer) userLiveCountData.getData().get("webActiveSession") / avg);
			userActivityLog
					.setMobileClientIdle((Integer) userLiveCountData.getData().get("mobileInActiveSession") / avg);
			userActivityLog.setWebClientIdle((Integer) userLiveCountData.getData().get("webInActiveSession") / avg);
			userActivityLog.setTotalMetric((Integer) userLiveCountData.getData().get("totalSession") / avg);
			userActivityLog.setCreatedAt(date);

			userActivityTimelyLogRepository.save(userActivityLog);

			// Note: DynamoDB saving was changed to RDS due to performance issues
			// dynamoDBMapper.save(userActivityLog);

			// removed deletion feature
			// Here i added mechanism to delete the records which are older than 15 days
//			Instant cutoffTime = instant.minus(360, ChronoUnit.HOURS);
//			Date cutoffDateAsDate = Date.from(cutoffTime);
//
//			List<UserActivityTimelyLog> recordsToDelete = userActivityTimelyLogRepository
//					.findByCreatedAtBefore(cutoffDateAsDate);
//			userActivityTimelyLogRepository.deleteAll(recordsToDelete);

		} catch (Exception e) {
			System.err.println("Following error occured in userLivecount information creation event.");

			logger.error(e.getMessage());

		}
	}

	@Override
	public void generateAndSaveDailyLogs() {
		try {
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");

			Instant now = Instant.now();
			LocalDateTime localDateTimeNow = now.atZone(zoneId).toLocalDateTime();
			localDateTimeNow = localDateTimeNow.withMinute(0).withSecond(0);
			now = localDateTimeNow.atZone(zoneId).toInstant();

			Instant twentyFourHoursAgo = now.minus(24, ChronoUnit.HOURS);
			LocalDateTime localDateTimeTwentyFourHoursAgo = twentyFourHoursAgo.atZone(zoneId).toLocalDateTime();
			localDateTimeTwentyFourHoursAgo = localDateTimeTwentyFourHoursAgo.withMinute(0).withSecond(0);
			twentyFourHoursAgo = localDateTimeTwentyFourHoursAgo.atZone(zoneId).toInstant();

			Date start = Date.from(twentyFourHoursAgo);
			Date end = Date.from(now);

			// for partition key
			ZonedDateTime zonedDateTime = now.atZone(zoneId);
			DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

			List<UserActivityTimelyLog> timelyLogs = userActivityTimelyLogRepository.findByCreatedAtBetween(start, end);

			Map<String, UserActivityDailyLog> dailyLogMap = new HashMap<>();

			for (UserActivityTimelyLog timelyLog : timelyLogs) {
				String partitionKey = formatterr.format(zonedDateTime);

				UserActivityDailyLog dailyLog = dailyLogMap.get(partitionKey);

				if (dailyLog == null) {
					dailyLog = new UserActivityDailyLog();
					dailyLog.setPartitionKey(partitionKey);
					dailyLog.setCreatedAt(end);
					dailyLogMap.put(partitionKey, dailyLog);
				}

				Integer mobileClientValue = dailyLog.getMobileClient();
				Integer mobileClientIdleValue = dailyLog.getMobileClientIdle();
				Integer totalMetricValue = dailyLog.getTotalMetric();
				Integer webClientValue = dailyLog.getWebClient();
				Integer webClientIdleValue = dailyLog.getWebClientIdle();

				dailyLog.setMobileClient(
						(mobileClientValue != null ? mobileClientValue : 0) + timelyLog.getMobileClient());
				dailyLog.setMobileClientIdle(
						(mobileClientIdleValue != null ? mobileClientIdleValue : 0) + timelyLog.getMobileClientIdle());
				dailyLog.setTotalMetric((totalMetricValue != null ? totalMetricValue : 0) + timelyLog.getTotalMetric());
				dailyLog.setWebClient((webClientValue != null ? webClientValue : 0) + timelyLog.getWebClient());
				dailyLog.setWebClientIdle(
						(webClientIdleValue != null ? webClientIdleValue : 0) + timelyLog.getWebClientIdle());
			}

			userActivityDailyLogRepository.saveAll(dailyLogMap.values());

			// removed the deletion feature

			// Here i added mechanism to delete the records which are older than four months
//			Instant cutoffDate = now.minus(120, ChronoUnit.DAYS);
//			LocalDateTime localCutoffDate = cutoffDate.atZone(zoneId).toLocalDateTime();
//			localCutoffDate = localCutoffDate.withMinute(0).withSecond(0);
//			cutoffDate = localCutoffDate.atZone(zoneId).toInstant();
//			Date cutoffDateAsDate = Date.from(cutoffDate);
//
//			List<UserActivityDailyLog> recordsToDelete = userActivityDailyLogRepository
//					.findByCreatedAtBefore(cutoffDateAsDate);
//			userActivityDailyLogRepository.deleteAll(recordsToDelete);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void generateAndSaveWeeklyLogs() {
		try {
			Instant now = Instant.now();
			Instant sevenDaysAgo = now.minus(7, ChronoUnit.DAYS);
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");

			LocalDateTime localDateTimeNow = now.atZone(zoneId).toLocalDateTime();
			localDateTimeNow = localDateTimeNow.withHour(0).withMinute(0).withSecond(0);
			now = localDateTimeNow.atZone(zoneId).toInstant();

			Date start = Date.from(sevenDaysAgo);
			Date end = Date.from(now);

			ZonedDateTime zonedDateTime = now.atZone(zoneId);
			DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

			List<UserActivityDailyLog> dailyLogs = userActivityDailyLogRepository.findByCreatedAtBetween(start, end);

			Map<String, UserActivityWeeklyLog> weeklyLogMap = new HashMap<>();

			for (UserActivityDailyLog dailyLog : dailyLogs) {
				String partitionKey = formatterr.format(zonedDateTime);
				UserActivityWeeklyLog weeklyLog = weeklyLogMap.get(partitionKey);
				if (weeklyLog == null) {
					weeklyLog = new UserActivityWeeklyLog();
					weeklyLog.setPartitionKey(partitionKey);
					weeklyLog.setCreatedAt(end);
					weeklyLogMap.put(partitionKey, weeklyLog);
				}

				Integer mobileClientValue = weeklyLog.getMobileClient();
				Integer mobileClientIdleValue = weeklyLog.getMobileClientIdle();
				Integer totalMetricValue = weeklyLog.getTotalMetric();
				Integer webClientValue = weeklyLog.getWebClient();
				Integer webClientIdleValue = weeklyLog.getWebClientIdle();

				weeklyLog.setMobileClient(
						(mobileClientValue != null ? mobileClientValue : 0) + dailyLog.getMobileClient());
				weeklyLog.setMobileClientIdle(
						(mobileClientIdleValue != null ? mobileClientIdleValue : 0) + dailyLog.getMobileClientIdle());
				weeklyLog.setTotalMetric((totalMetricValue != null ? totalMetricValue : 0) + dailyLog.getTotalMetric());
				weeklyLog.setWebClient((webClientValue != null ? webClientValue : 0) + dailyLog.getWebClient());
				weeklyLog.setWebClientIdle(
						(webClientIdleValue != null ? webClientIdleValue : 0) + dailyLog.getWebClientIdle());
			}

			userActivityWeeklyLogRepository.saveAll(weeklyLogMap.values());

			// removed the deletion feature

			// Here i added mechanism to delete the records which are older than four months
//			Instant cutoffDate = now.minus(209, ChronoUnit.DAYS);
//			Date cutoffDateAsDate = Date.from(cutoffDate);

//			List<UserActivityWeeklyLog> recordsToDelete = userActivityWeeklyLogRepository
//					.findByCreatedAtBefore(cutoffDateAsDate);
//			userActivityWeeklyLogRepository.deleteAll(recordsToDelete);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void generateAndSaveMonthlyLogs() {
		try {
			Instant now = Instant.now();
			Instant thirtyDaysAgo = now.minus(30, ChronoUnit.DAYS);
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");

			LocalDateTime localDateTimeNow = now.atZone(zoneId).toLocalDateTime();
			localDateTimeNow = localDateTimeNow.withHour(0).withMinute(0).withSecond(0).withNano(0);
			now = localDateTimeNow.atZone(zoneId).toInstant();

			Date start = Date.from(thirtyDaysAgo);
			Date end = Date.from(now);

			ZonedDateTime zonedDateTime = now.atZone(zoneId);
			DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

			List<UserActivityDailyLog> dailyLogs = userActivityDailyLogRepository.findByCreatedAtBetween(start, end);

			Map<String, UserActivityMonthlyLog> monthlyLogMap = new HashMap<>();

			for (UserActivityDailyLog dailyLog : dailyLogs) {
				String partitionKey = formatterr.format(zonedDateTime);

				UserActivityMonthlyLog monthlyLog = monthlyLogMap.get(partitionKey);
				if (monthlyLog == null) {
					monthlyLog = new UserActivityMonthlyLog();
					monthlyLog.setPartitionKey(partitionKey);
					monthlyLog.setCreatedAt(end); // Use the end date of the month
					monthlyLogMap.put(partitionKey, monthlyLog);
				}

				Integer mobileClientValue = monthlyLog.getMobileClient();
				Integer mobileClientIdleValue = monthlyLog.getMobileClientIdle();
				Integer totalMetricValue = monthlyLog.getTotalMetric();
				Integer webClientValue = monthlyLog.getWebClient();
				Integer webClientIdleValue = monthlyLog.getWebClientIdle();

				// Calculate the sum only if values are not null
				monthlyLog.setMobileClient(
						(mobileClientValue != null ? mobileClientValue : 0) + dailyLog.getMobileClient());
				monthlyLog.setMobileClientIdle(
						(mobileClientIdleValue != null ? mobileClientIdleValue : 0) + dailyLog.getMobileClientIdle());
				monthlyLog
						.setTotalMetric((totalMetricValue != null ? totalMetricValue : 0) + dailyLog.getTotalMetric());
				monthlyLog.setWebClient((webClientValue != null ? webClientValue : 0) + dailyLog.getWebClient());
				monthlyLog.setWebClientIdle(
						(webClientIdleValue != null ? webClientIdleValue : 0) + dailyLog.getWebClientIdle());
			}

			userActivityMonthlyLogRepository.saveAll(monthlyLogMap.values());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @author Abdul Elahi.
	 * 
	 *         Retrieves user live count data based on the specified date
	 *         type(hourly, daily, weekly, or monthly).
	 *
	 * @param dateType The type of time basis for which user live count data is
	 *                 requested (e.g., "hourly", "daily", "weekly", "monthly").
	 * @return A Document containing a list of UserActivityTimelyLogDTO objects with
	 *         user activity data for the specified time basis.
	 * @throws IllegalArgumentException if the provided dateType is not one of the
	 *                                  valid options.
	 */
	@Override
	public Document<?> getUserLiveCountOnTimeBases(String dateType, Date from, Date to) {
		Document<List<UserActivityTimelyLogDTO>> result = new Document<>();
		List<UserActivityTimelyLogDTO> dtos = new ArrayList<>();
		try {
			switch (dateType.toLowerCase()) {

			case "hourly":
				dtos = generateTimestampsHourly(from,to);
				break;

			case "daily":
				dtos = generateTimestampsDaily(from, to);
				break;

			case "weekly":
				dtos = generateTimestampsWeekly(from, to);
				break;

			case "monthly":
				dtos = generateTimestampsMonthly(from, to);
				break;

			default:
				throw new IllegalArgumentException("Invalid dateType: " + dateType);

			}

			result.setData(dtos);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	public List<UserActivityTimelyLogDTO> generateTimestampsHourly(Date from, Date to) {

		List<UserActivityTimelyLogDTO> userActivityDailyLogs = new ArrayList<>();
		List<UserActivityTimelyLog> userActivityTimelyLogs = null;
		if (from != null)
			userActivityTimelyLogs = userActivityTimelyLogRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(from,
					to);
		else
			userActivityTimelyLogs = userActivityTimelyLogRepository.findTop24ByOrderByCreatedAtDesc();

		int totalMatrix = 0;
		int mobileClient = 0;
		int webClient = 0;
		int mobileClientIdle = 0;
		int webClientIdle = 0;

		Date date = null;

		int average = 0;

		if (userActivityTimelyLogs != null) {
			for (UserActivityTimelyLog userActivityLog : userActivityTimelyLogs) {
				if (userActivityLog != null) {

					totalMatrix = userActivityLog.getTotalMetric();
					mobileClient = userActivityLog.getMobileClient();
					webClient = userActivityLog.getWebClient();
					mobileClientIdle = userActivityLog.getMobileClientIdle();
					webClientIdle = userActivityLog.getWebClientIdle();
					date = userActivityLog.getCreatedAt();
				}
				if (!userActivityTimelyLogs.isEmpty()) {
					average = totalMatrix / userActivityTimelyLogs.size();
				}

				UserActivityTimelyLogDTO dataDto = new UserActivityTimelyLogDTO();
				dataDto.setMobileClient(mobileClient);
				dataDto.setMobileClientIdle(mobileClientIdle);
				dataDto.setWebClient(webClient);
				dataDto.setWebClientIdle(webClientIdle);
				dataDto.setTotalMetrics(totalMatrix);
				dataDto.setAverage(average);
				dataDto.setCreatedAt(date != null ? date.toString() : null);
				userActivityDailyLogs.add(dataDto);

			}
		}

		return userActivityDailyLogs;
	}

	public List<UserActivityTimelyLogDTO> generateTimestampsDaily(Date from, Date to) {

		List<UserActivityTimelyLogDTO> userActivityDailyLogs = new ArrayList<>();
		List<UserActivityDailyLog> userActivityTimelyLogs = null;

		if (from != null)
			userActivityTimelyLogs = userActivityDailyLogRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(from,
					to);
		else
			userActivityTimelyLogs = userActivityDailyLogRepository.findTop30ByOrderByCreatedAtDesc();

		int totalMatrix = 0;
		int mobileClient = 0;
		int webClient = 0;
		int mobileClientIdle = 0;
		int webClientIdle = 0;

		Date date = null;

		int average = 0;

		if (userActivityTimelyLogs != null) {
			for (UserActivityDailyLog userActivityLog : userActivityTimelyLogs) {
				if (userActivityLog != null) {

					totalMatrix = userActivityLog.getTotalMetric();
					mobileClient = userActivityLog.getMobileClient();
					webClient = userActivityLog.getWebClient();
					mobileClientIdle = userActivityLog.getMobileClientIdle();
					webClientIdle = userActivityLog.getWebClientIdle();
					date = userActivityLog.getCreatedAt();
				}
				if (!userActivityTimelyLogs.isEmpty()) {
					average = totalMatrix / userActivityTimelyLogs.size();
				}

				UserActivityTimelyLogDTO dataDto = new UserActivityTimelyLogDTO();
				dataDto.setMobileClient(mobileClient);
				dataDto.setMobileClientIdle(mobileClientIdle);
				dataDto.setWebClient(webClient);
				dataDto.setWebClientIdle(webClientIdle);
				dataDto.setTotalMetrics(totalMatrix);
				dataDto.setAverage(average);
				dataDto.setCreatedAt(date != null ? date.toString() : null);
				userActivityDailyLogs.add(dataDto);

			}
		}

		return userActivityDailyLogs;
	}

	public List<UserActivityTimelyLogDTO> generateTimestampsWeekly(Date from, Date to) {

		List<UserActivityTimelyLogDTO> userActivityWeeklyLogs = new ArrayList<>();

		List<UserActivityWeeklyLog> userActivityTimelyLogs = null;
		if (from != null)
			userActivityTimelyLogs = userActivityWeeklyLogRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(from,
					to);
		else
			userActivityTimelyLogs = userActivityWeeklyLogRepository.findTop30ByOrderByCreatedAtDesc();

		int totalMatrix = 0;
		int mobileClient = 0;
		int webClient = 0;
		int mobileClientIdle = 0;
		int webClientIdle = 0;

		Date date = null;

		int average = 0;

		if (userActivityTimelyLogs != null) {
			for (UserActivityWeeklyLog userActivityLog : userActivityTimelyLogs) {
				if (userActivityLog != null) {
					totalMatrix = userActivityLog.getTotalMetric();
					mobileClient = userActivityLog.getMobileClient();
					webClient = userActivityLog.getWebClient();
					mobileClientIdle = userActivityLog.getMobileClientIdle();
					webClientIdle = userActivityLog.getWebClientIdle();
					date = userActivityLog.getCreatedAt();
				}
				if (!userActivityTimelyLogs.isEmpty()) {
					average = totalMatrix / userActivityTimelyLogs.size();
				}

				UserActivityTimelyLogDTO dataDto = new UserActivityTimelyLogDTO();
				dataDto.setMobileClient(mobileClient);
				dataDto.setMobileClientIdle(mobileClientIdle);
				dataDto.setWebClient(webClient);
				dataDto.setCreatedAt(String.valueOf(date));
				dataDto.setWebClientIdle(webClientIdle);
				dataDto.setTotalMetrics(totalMatrix);
				dataDto.setCreatedAt(date != null ? date.toString() : null);
				dataDto.setAverage(average);
				userActivityWeeklyLogs.add(dataDto);
			}
		}

		return userActivityWeeklyLogs;
	}

	public List<UserActivityTimelyLogDTO> generateTimestampsMonthly(Date from, Date to) {

		List<UserActivityTimelyLogDTO> userActivityWeeklyLogs = new ArrayList<>();
		List<UserActivityMonthlyLog> userActivityTimelyLogs = null;
		if (from != null)
			userActivityTimelyLogs = userActivityMonthlyLogRepository
					.findAllByCreatedAtBetweenOrderByCreatedAtDesc(from, to);
		else
			userActivityTimelyLogs = userActivityMonthlyLogRepository.findAllByOrderByCreatedAtDesc();

		int totalMatrix = 0;
		int mobileClient = 0;
		int webClient = 0;
		int mobileClientIdle = 0;
		int webClientIdle = 0;

		Date date = null;

		int average = 0;

		if (userActivityTimelyLogs != null) {
			for (UserActivityMonthlyLog userActivityLog : userActivityTimelyLogs) {
				if (userActivityLog != null) {
					totalMatrix = userActivityLog.getTotalMetric();
					mobileClient = userActivityLog.getMobileClient();
					webClient = userActivityLog.getWebClient();
					mobileClientIdle = userActivityLog.getMobileClientIdle();
					webClientIdle = userActivityLog.getWebClientIdle();
					date = userActivityLog.getCreatedAt();
				}
				if (!userActivityTimelyLogs.isEmpty()) {
					average = totalMatrix / userActivityTimelyLogs.size();
				}

				UserActivityTimelyLogDTO dataDto = new UserActivityTimelyLogDTO();
				dataDto.setMobileClient(mobileClient);
				dataDto.setMobileClientIdle(mobileClientIdle);
				dataDto.setWebClient(webClient);
				dataDto.setCreatedAt(String.valueOf(date));
				dataDto.setWebClientIdle(webClientIdle);
				dataDto.setTotalMetrics(totalMatrix);
				dataDto.setCreatedAt(date != null ? date.toString() : null);
				dataDto.setAverage(average);
				userActivityWeeklyLogs.add(dataDto);
			}
		}

		return userActivityWeeklyLogs;
	}

	/**
	 * @author Abdul Elahi
	 * 
	 * 
	 *         This method fetches data from a DynamoDB table, processes and
	 *         transforms the information, and saves it to another repository. The
	 *         primary goal is to scan the DynamoDB table for
	 *         UserActivityTimelyLogs, convert the createdAt timestamp to a
	 *         specified time zone, and persist the processed data to a different
	 *         repository.
	 *
	 * @throws Exception If an error occurs during the data retrieval, processing,
	 *                   or persistence.
	 */
	@Async
	@Override
	public void fetchDataFromDynamoDB() {
		int size = 0;
		try {
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
			List<UserActivityTimelyLogs> userActivityLogs = dynamoDBMapper.scan(UserActivityTimelyLogs.class,
					scanExpression);
			if (userActivityLogs != null && !userActivityLogs.isEmpty()) {
				for (UserActivityTimelyLogs a : userActivityLogs) {

					ZonedDateTime zonedDateTime = ZonedDateTime.parse(a.getCreatedAt());

					ZoneId zoneId = ZoneId.of("Asia/Kolkata");
					LocalDateTime localDateTimeNow = zonedDateTime.withZoneSameInstant(zoneId).toLocalDateTime();
					localDateTimeNow = localDateTimeNow.withMinute(0).withSecond(0).withNano(0);
					zonedDateTime = localDateTimeNow.atZone(zoneId);

					Date createdAt = Date.from(zonedDateTime.toInstant());

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
					String partitionKey = dateFormat.format(createdAt);

					UserActivityTimelyLog userActivityTimelyLog = new UserActivityTimelyLog();
					userActivityTimelyLog.setPartitionKey(partitionKey);
					userActivityTimelyLog.setCreatedAt(createdAt);
					userActivityTimelyLog.setMobileClient(a.getMobileClient());
					userActivityTimelyLog.setMobileClientIdle(a.getMobileClientIdle());
					userActivityTimelyLog.setWebClient(a.getWebClient());
					userActivityTimelyLog.setWebClientIdle(a.getWebClientIdle());
					userActivityTimelyLog.setTotalMetric(a.getTotalMetrice());

					userActivityTimelyLogRepository.save(userActivityTimelyLog);
				}
				size = userActivityLogs.size();
			} else {
				logger.error("No data found!");
			}
			propagateDailyData();
			propagateWeeklyData();
			propagateMonthlyData();
		} catch (Exception e) {
			System.err.println("Error occurred in userLivecount information retrieval.");
			e.printStackTrace();
		} finally {
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss");
			String datetime = formatter.format(date);

			String fileName = "migrationOfUserAnalyticsfromDynamoDbtoRDS" + datetime + ".txt";

			try (FileWriter writer = new FileWriter(fileName)) {

				writer.write("Propagation successful. " + System.lineSeparator()
						+ "The total number of timely logs fetched and migrated from DynamoDB to RDS: " + size + ".");

				File logFile = new File(fileName);

				try {
					fileUploadService.uploadFileToS3Bucket("/logs/creation/useranaltyics/", fileName, logFile);
				} catch (Exception e) {
					logger.error("Error uploading file to S3: ", e);
				}

				writer.close();

			} catch (IOException e) {
				logger.error("Error writing to the log files: ", e);
			}
			try {
				File logFile = new File(fileName);
				boolean isDeletedFile1 = logFile.delete();
				logger.info("Log file deleted from the system: " + isDeletedFile1);
			} catch (Exception e) {
				logger.error("Error deleting local files: ", e);
			}
		}
	}

	public void propagateDailyData() {
		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			int day = 1;
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			ZonedDateTime endDateTime = ZonedDateTime.now(zoneId).withHour(0).withMinute(0).withSecond(0);

			List<UserActivityTimelyLog> logs = userActivityTimelyLogRepository.findAll();

			Map<Instant, List<UserActivityTimelyLog>> map = logs.stream()
					.collect(Collectors.groupingBy(e -> e.getCreatedAt().toInstant().truncatedTo(ChronoUnit.DAYS)));

			ZonedDateTime startDateTime = endDateTime.minusDays(day).withMinute(0).withSecond(0);

			for (int i = 0; i < map.size(); i++) {
				String endDateTimeFormatted = endDateTime.format(formatter);

				Date startDate = Date.from(startDateTime.toInstant());
				Date endDate = Date.from(endDateTime.toInstant());

				List<UserActivityTimelyLog> timelyLogs = userActivityTimelyLogRepository
						.findByCreatedAtBetween(startDate, endDate);

				if (!timelyLogs.isEmpty()) {
					UserActivityDailyLog dailyLog = new UserActivityDailyLog();
					dailyLog.setPartitionKey(endDateTimeFormatted);
					dailyLog.setCreatedAt(Date.from(endDateTime.toInstant()));

					for (UserActivityTimelyLog log : timelyLogs) {
						if (log != null) {
							dailyLog.setMobileClient(dailyLog.getMobileClient() == null ? 0
									: dailyLog.getMobileClient() + log.getMobileClient());
							dailyLog.setMobileClientIdle(dailyLog.getMobileClientIdle() == null ? 0
									: dailyLog.getMobileClientIdle() + log.getMobileClientIdle());
							dailyLog.setTotalMetric(dailyLog.getTotalMetric() == null ? 0
									: dailyLog.getTotalMetric() + log.getTotalMetric());
							dailyLog.setWebClient(
									dailyLog.getWebClient() == null ? 0 : dailyLog.getWebClient() + log.getWebClient());
							dailyLog.setWebClientIdle(dailyLog.getWebClientIdle() == null ? 0
									: dailyLog.getWebClientIdle() + log.getWebClientIdle());
						}
					}

					dailyLog.setMobileClient(dailyLog.getMobileClient() / timelyLogs.size());
					dailyLog.setMobileClientIdle(dailyLog.getMobileClientIdle() / timelyLogs.size());
					dailyLog.setTotalMetric(dailyLog.getTotalMetric() / timelyLogs.size());
					dailyLog.setWebClient(dailyLog.getWebClient() / timelyLogs.size());
					dailyLog.setWebClientIdle(dailyLog.getWebClientIdle() / timelyLogs.size());

					userActivityDailyLogRepository.save(dailyLog);
					startDateTime = startDateTime.minusDays(1);
					endDateTime = endDateTime.minusDays(1);
				} else {
					System.out.println("completed");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void propagateWeeklyData() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		int day = 6;
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime endDateTime = ZonedDateTime.now(zoneId).withHour(0).withMinute(0).withSecond(0).withNano(0);

		List<UserActivityDailyLog> logs = userActivityDailyLogRepository.findAll();

		Map<Object, List<UserActivityDailyLog>> map = logs.stream()
				.collect(Collectors.groupingBy(e -> convertToInstant(e.getCreatedAt()).atZone(ZoneId.systemDefault())
						.toLocalDate().with(DayOfWeek.MONDAY)));

		ZonedDateTime startDateTime = endDateTime.minusDays(day).withNano(0);

		for (int i = 1; i <= map.size(); i++) {
			String endDateTimeFormatted = endDateTime.format(formatter);

			Date startDate = Date.from(startDateTime.toInstant());
			Date endDate = Date.from(endDateTime.toInstant());

			List<UserActivityDailyLog> dailyLogs = userActivityDailyLogRepository.findByCreatedAtBetween(startDate,
					endDate);

			if (!dailyLogs.isEmpty()) {
				UserActivityWeeklyLog weeklyLog = new UserActivityWeeklyLog();
				weeklyLog.setPartitionKey(endDateTimeFormatted);
				weeklyLog.setCreatedAt(Date.from(endDateTime.toInstant()));

				for (UserActivityDailyLog log : dailyLogs) {
					if (log != null) {
						weeklyLog.setMobileClient(weeklyLog.getMobileClient() == null ? 0
								: weeklyLog.getMobileClient() + log.getMobileClient());
						weeklyLog.setMobileClientIdle(weeklyLog.getMobileClientIdle() == null ? 0
								: weeklyLog.getMobileClientIdle() + log.getMobileClientIdle());
						weeklyLog.setTotalMetric(weeklyLog.getTotalMetric() == null ? 0
								: weeklyLog.getTotalMetric() + log.getTotalMetric());
						weeklyLog.setWebClient(
								weeklyLog.getWebClient() == null ? 0 : weeklyLog.getWebClient() + log.getWebClient());
						weeklyLog.setWebClientIdle(weeklyLog.getWebClientIdle() == null ? 0
								: weeklyLog.getWebClientIdle() + log.getWebClientIdle());
					}
				}

				userActivityWeeklyLogRepository.save(weeklyLog);
			}

			startDateTime = startDateTime.minusDays(day);
			endDateTime = endDateTime.minusDays(day);
		}

	}

	public void propagateMonthlyData() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		int daysInMonth = YearMonth.now().lengthOfMonth();
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		ZonedDateTime endDateTime = ZonedDateTime.now(zoneId).withHour(0).withMinute(0).withSecond(0).withNano(0);

		List<UserActivityDailyLog> logs = userActivityDailyLogRepository.findAll();

		Map<Object, List<UserActivityDailyLog>> map = logs.stream()
				.collect(Collectors.groupingBy(e -> convertToInstant(e.getCreatedAt()).atZone(ZoneId.systemDefault())
						.toLocalDate().withDayOfMonth(1)));

		ZonedDateTime startDateTime = endDateTime.minusDays(daysInMonth).withNano(0);

		for (int i = 1; i <= map.size(); i++) {
			String endDateTimeFormatted = endDateTime.format(formatter);

			Date startDate = Date.from(startDateTime.toInstant());
			Date endDate = Date.from(endDateTime.toInstant());

			List<UserActivityDailyLog> dailyLogs = userActivityDailyLogRepository.findByCreatedAtBetween(startDate,
					endDate);

			if (!dailyLogs.isEmpty()) {
				UserActivityMonthlyLog monthlyLog = new UserActivityMonthlyLog();
				monthlyLog.setPartitionKey(endDateTimeFormatted);
				monthlyLog.setCreatedAt(Date.from(endDateTime.toInstant()));

				for (UserActivityDailyLog log : dailyLogs) {
					if (log != null) {
						monthlyLog.setMobileClient(monthlyLog.getMobileClient() == null ? 0
								: monthlyLog.getMobileClient() + log.getMobileClient());
						monthlyLog.setMobileClientIdle(monthlyLog.getMobileClientIdle() == null ? 0
								: monthlyLog.getMobileClientIdle() + log.getMobileClientIdle());
						monthlyLog.setTotalMetric(monthlyLog.getTotalMetric() == null ? 0
								: monthlyLog.getTotalMetric() + log.getTotalMetric());
						monthlyLog.setWebClient(
								monthlyLog.getWebClient() == null ? 0 : monthlyLog.getWebClient() + log.getWebClient());
						monthlyLog.setWebClientIdle(monthlyLog.getWebClientIdle() == null ? 0
								: monthlyLog.getWebClientIdle() + log.getWebClientIdle());
					}
				}

				userActivityMonthlyLogRepository.save(monthlyLog);
			}

			startDateTime = startDateTime.minusDays(daysInMonth);
			endDateTime = endDateTime.minusDays(daysInMonth);
		}
	}

	/**
	 * @author Abdul Elahi
	 * 
	 */
	public Document<?> getLastTenActivities() {
		Document<List<Integer>> result = new Document<>();
		try {

			Instant now = GenerateTime.truncateToMinutes(Instant.now());
			Instant startTime = now.minus((30 - 1) * 1, ChronoUnit.MINUTES);

			List<Integer> activityCounts = new ArrayList<>();
			for (int i = 0; i < 30; i++) {
				Instant end = startTime.plus(1, ChronoUnit.MINUTES);
				long count = userActivityLiveRepository.countByTimestampBetween(startTime, end);
				activityCounts.add((int) count);
				startTime = end;
			}

			result.setData(activityCounts);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Fetched Successfully");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}

		return result;
	}
	

	public Map<String, String> getDailyIntervels(String startDateStr,String endDateStr){
		      
		   
        // Define the date and time format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Parse the input dates and add time (00:00:00 for start of day)
        LocalDateTime startDate = LocalDateTime.parse(startDateStr + " 00:00:00", dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr + " 00:00:00", dateTimeFormatter);
        
        // Create a map to store the intervals
        Map<String, String> intervals = new LinkedHashMap<>();
        
        // Iterate through the days between the start and end dates
        LocalDateTime current = startDate;
        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            // Calculate the end of the current day
            LocalDateTime dayEnd = current.withHour(23).withMinute(59).withSecond(59);
            
            // Adjust the day end if it goes beyond the end date
            if (dayEnd.isAfter(endDate)) {
                dayEnd = endDate.withHour(23).withMinute(59).withSecond(59);
            }
            
            // Add the interval to the map
            intervals.put(current.format(dateTimeFormatter), dayEnd.format(dateTimeFormatter));
            
            // Move to the next day
            current = current.plusDays(1).withHour(0).withMinute(0).withSecond(0);
        }
        
        return intervals;

	}
	
	
	public Map<String, String> getWeeklyIntervels(String startDateStr,String endDateStr){
		      
        // Define the date and time format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Parse the input dates and add time (00:00:00 for start of day)
        LocalDateTime startDate = LocalDateTime.parse(startDateStr + " 00:00:00", dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr + " 00:00:00", dateTimeFormatter);
        
        // Create a map to store the intervals
        Map<String, String> intervals = new LinkedHashMap<>();
        
        // Iterate through the weeks between the start and end dates
        LocalDateTime current = startDate;
        while (current.isBefore(endDate)) {
            // Calculate the end of the current week
            LocalDateTime weekEnd = current.with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59);
            
            // Adjust the week end if it goes beyond the end date
            if (weekEnd.isAfter(endDate.minusDays(1))) {
                weekEnd = endDate.minusDays(1).withHour(23).withMinute(59).withSecond(59);
            }
            
            // Add the interval to the map
            intervals.put(current.format(dateTimeFormatter), weekEnd.format(dateTimeFormatter));
            
            // Move to the next week
            current = current.plusWeeks(1).with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0);
        }
    
        
        return intervals;

	}
	
	public Map<String, String> getMonthlyIntervels(String startDateStr,String endDateStr){
		

        // Define the date and time format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Parse the input dates and add time (00:00:00 for start of day)
        LocalDateTime startDate = LocalDateTime.parse(startDateStr + " 00:00:00", dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr + " 00:00:00", dateTimeFormatter);
        
        // Create a map to store the intervals
        Map<String, String> intervals = new LinkedHashMap<>();
        
        // Iterate through the months between the start and end dates
        LocalDateTime current = startDate;
        while (current.isBefore(endDate)) {
            // Calculate the end of the current month
            LocalDateTime monthEnd = current.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
            
            // Adjust the month end if it goes beyond the end date
            if (monthEnd.isAfter(endDate.minusDays(1))) {
                monthEnd = endDate.minusDays(1).withHour(23).withMinute(59).withSecond(59);
            }
            
            // Add the interval to the map
            intervals.put(current.format(dateTimeFormatter), monthEnd.format(dateTimeFormatter));
            
            // Move to the next month
            current = current.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
        }
        
        return intervals;

	}

	
	public Map<String, String> getYearlyIntervels(String startDateStr,String endDateStr){
		

        // Define the date and time format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               
        // Parse the input dates and add time (00:00:00 for start of day)
        LocalDateTime startDate = LocalDateTime.parse(startDateStr + " 00:00:00", dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr + " 00:00:00", dateTimeFormatter);
        
        // Create a map to store the intervals
        Map<String, String> intervals = new LinkedHashMap<>();
        
        // Iterate through the years between the start and end dates
        LocalDateTime current = startDate;
        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            // Calculate the end of the current year
            LocalDateTime yearEnd = current.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59);
            
            // Adjust the year end if it goes beyond the end date
            if (yearEnd.isAfter(endDate)) {
                yearEnd = endDate.withHour(23).withMinute(59).withSecond(59);
            }
            
            // Add the interval to the map
            intervals.put(current.format(dateTimeFormatter), yearEnd.format(dateTimeFormatter));
            
            // Move to the next year
            current = current.plusYears(1).with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0);
        }
       
        
        return intervals;

	}

}
