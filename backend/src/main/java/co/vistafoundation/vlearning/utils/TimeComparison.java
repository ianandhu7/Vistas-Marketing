/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/**
 * @author vk
 *
 */
@Component
public class TimeComparison {
	
	private static final String TIME_FORMATTER= "HH:mm:ss";

	/**
	 * @author vk
	 * @since 29 March 2021
	 * finds average time between two LocalTime times
	 */
	public static LocalTime averageTime(LocalTime startTime, LocalTime endTime) {
		Duration duration = Duration.between(startTime, endTime);
		if (startTime.isAfter(endTime)) {
			duration = duration.plusHours(24);
		}
		return startTime.plus(duration.dividedBy(2L));
	}

	/**
	 * @author vk
	 * @since 29 March 2021
	 * checks given start and end times lying under existing two times
	 */
	public static boolean checkTimeInBetween(String startTime, String endTime, String checkFromTime, String checkToTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
		LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
		LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
		LocalTime checkLocalFromTime = LocalTime.parse(checkFromTime, formatter);
		LocalTime checkLocalToTime = LocalTime.parse(checkToTime, formatter);

		boolean isInBetween = startLocalTime.compareTo(checkLocalToTime) <= 0 && endLocalTime.compareTo(checkLocalFromTime) >= 0;
			return isInBetween;
	}

	public static String getCurrentTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
        return LocalDateTime.now().format(formatter);
    }
	
	/**
	 * @author vk
	 * @since 03 January 2022
	 * compares duration in minutes between two instant date types
	 */
	public static Long minutesDifferenceTwoInstant(Instant instant1, Instant instant2) {
		Duration duration = Duration.between(instant1, instant2);
		System.out.println("minutesDifferenceTwoInstant "+duration.toMinutes());
		return duration.toMinutes();
	}
	
	/**
	 * @author vk
	 * @since 03 January 2022
	 * compares duration in hours between two instant date types
	 */
	public static Long hoursDifferenceTwoInstant(Instant instant1, Instant instant2) {
		Duration duration = Duration.between(instant1, instant2);
		return duration.toHours();
	}
	
	/**
	 * @author vk
	 * @since 03 January 2022
	 * compares duration in days between two instant date types
	 */
	public static Long daysDifferenceTwoInstant(Instant instant1, Instant instant2) {
		Duration duration = Duration.between(instant1, instant2);
		return duration.toDays();
	}
}
