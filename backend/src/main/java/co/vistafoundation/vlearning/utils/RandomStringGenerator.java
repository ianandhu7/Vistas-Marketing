package co.vistafoundation.vlearning.utils;
/**
 * 
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;

/**
 * @author vk
 *
 */
@Component
public class RandomStringGenerator {
	
	@Autowired
	StudentOrderRepository studentOrderRepository;

	public String generateRandomOrderId(Long userSurId) {
		
		DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("ddMMyyyy");
		DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmmss");
		LocalDateTime now = LocalDateTime.now();
		String date = dtfDate.format(now), time = dtfTime.format(now);
		
	
		
		//combination of random number, date-time, user id
		String randomOrderIdString = RandomStringUtils.randomNumeric(5)+date+time+userSurId;
		Boolean orderIdExists = studentOrderRepository.existsByOrderId(randomOrderIdString);
		if (orderIdExists) {
			randomOrderIdString = RandomStringUtils.randomNumeric(5)+date+time+userSurId;
			return randomOrderIdString;
		}else {
			return randomOrderIdString;
		}
	}
	
	/**
	 * @author Naveen Kumar A
	 *  
	 * @return String System 
	 * Time in milliseconds
	 */
	public String generateTimeStamp() 
	{
	return  Long.toString(System.currentTimeMillis());
	}
	
}
