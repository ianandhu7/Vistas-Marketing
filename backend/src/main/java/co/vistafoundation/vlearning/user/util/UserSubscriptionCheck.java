/**
 * 
 */
package co.vistafoundation.vlearning.user.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;

/**
 * 
 * @author NAVEEN
 *
 */

@Configuration
public class UserSubscriptionCheck {

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	@Autowired
	UserRepository userRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	private SubscribedUserDTO checkUserSubscribed(Long userSurId) {

		SubscribedUserDTO subscribedUserDTO = new SubscribedUserDTO();

		User user = userRepository.findByUserSurId(userSurId);

		if (user == null)
			throw new AppException("Invalid User");

		StudentSubscription studentSubscription = studentSubscriptionRepository
				.findByUserSurIdAndIdproductLineAndActiveFlag(user.getUserSurId(), 11L, Boolean.TRUE);

		if (studentSubscription == null)
			return subscribedUserDTO = null;

		LocalDate dateNextPaymentDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateEndDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();

		if ((dateNextPaymentDate.isAfter(dateToday) || dateNextPaymentDate.equals(dateToday))
				&& dateEndDate.isAfter(dateToday) || dateEndDate.equals(dateToday)) {
			subscribedUserDTO.setIdProduct(studentSubscription.getIdProduct());
			subscribedUserDTO.setIdStudent(studentSubscription.getIdStudent());
			subscribedUserDTO.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
			subscribedUserDTO.setUserSurId(studentSubscription.getUserSurId());
		} else {
			subscribedUserDTO = null;
		}

		return subscribedUserDTO;
	}

	public StudentPostLoginDTO checkExistingSubscriptionLogin(Long userSurId) {

		StudentPostLoginDTO result = new StudentPostLoginDTO();

		User user = null;

		user = userRepository.findByUserSurId(userSurId);

		if (user == null)
			throw new AppException("Invalid User");

		UserCreatedDTO ucDTO = this.getUserCreatedWithinTwoDays(user.getUserSurId());

		boolean trialFlag = ucDTO == null ? false : true;

		SubscribedUserDTO su = this.checkUserSubscribed(userSurId);

		boolean subscribedFlag = su == null ? false : true;

		result.setSubscribedFlag(subscribedFlag);
		result.setTrialFlag(trialFlag);

		return result;

	}

	private UserCreatedDTO getUserCreatedWithinTwoDays(Long userSurId) {

		User loggedInUser = userRepository.findByUserSurId(userSurId);

		if (loggedInUser == null)
			throw new AppException("Invalid User");
		UserCreatedDTO userCreatedDTO = new UserCreatedDTO();
		User user = userRepository.findByUserSurId(loggedInUser.getUserSurId());
		if (user == null)
			return userCreatedDTO = null;
		LocalDate userCreatedDate = user.getCreatedAt().atZone(zoneIndia).toLocalDate();
		LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
		Long intervalBetweenDays = ChronoUnit.DAYS.between(userCreatedDate, dateToday);
		if (intervalBetweenDays <= 2) {
			userCreatedDTO.setCreatedAt(user.getCreatedAt());
			userCreatedDTO.setUserSurId(user.getUserSurId());
		} else {
			userCreatedDTO = null;
		}
		return userCreatedDTO;
	}

}
