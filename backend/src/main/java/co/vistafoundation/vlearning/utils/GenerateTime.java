package co.vistafoundation.vlearning.utils;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.exception.AppException;

@Component
public class GenerateTime {

	public String generateTimeFromSeconds(int totalSeconds) {
		if (totalSeconds < 0) {
			throw new AppException("Seconds cannot be negative.");
		}

		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		//int seconds = totalSeconds % 60;

		return hours + "h " + minutes + "min ";
	}	

    public static Instant truncateToMinutes(Instant instant) {
        // Convert Instant to ZonedDateTime to manipulate the time components
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.systemDefault());
        
        // Truncate to hour, setting minutes, seconds, and nanoseconds to zero
        ZonedDateTime truncatedDateTime = zonedDateTime.withSecond(0).withNano(0);
        
        // Return the Instant representation
        return truncatedDateTime.toInstant();
    }
}


