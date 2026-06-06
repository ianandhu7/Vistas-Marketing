/**
 * 
 */
package co.vistafoundation.vlearning.notification.Imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.notification.dto.GlobalNotifcationDTO;
import co.vistafoundation.vlearning.notification.model.UserNotification;
import co.vistafoundation.vlearning.notification.repository.UserNotificationRepository;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.notification.service.UserNotificationService;

/**
 * 
 * @author NaveenKumar
 *
 */
@Service
public class UserNotificationServiceImpl implements UserNotificationService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserNotificationRepository userNotificationRepository;

	@Autowired
	EmailService emailSerivce;

	@Autowired
	NotificationService notificationService;
	
	
	@Value("${global.notification.topic}")
	private String notificationTopic;
	
	

	@Override
	public Document<List<UserNotification>> getUserAllNotification() {

		Document<List<UserNotification>> result = new Document<>();

		try {
			UserPrincipal loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				loggedInUser = (UserPrincipal) authentication.getPrincipal();
			} else {
				throw new AppException("Invalid user data.");
			}

			List<UserNotification> response = userNotificationRepository
					.findByIdVlUserOrderByCreatedAtDesc(loggedInUser.getUserSurId());

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Boolean createNotifcation(String Heading, String Message, Long idVlUser) {

		Boolean result = false;

		try {

			UserNotification userNotification = new UserNotification();
			userNotification.setContent(Message);
			userNotification.setHeader(Heading);
			userNotification.setisNotViewed(Boolean.TRUE);
			userNotification.setIdVlUser(idVlUser);
			userNotificationRepository.save(userNotification);

			result = true;

		} catch (Exception e) {

			result = false;
		}

		return result;
	}

	@Async
	@Transactional
	@Override
	public void updateNotifcation(Long idUserNotification, Long idVlUser) {

		try {

			// passing the idVluser inorder too cross verfiy , whether right user is trying
			// to access the object.
			UserNotification userNotification = userNotificationRepository
					.findByidUserNotificationAndIdVlUser(idUserNotification, idVlUser);

			if (userNotification == null)
				throw new NullPointerException("Invalid User Notification,No such Data found.");

			userNotification.setisNotViewed(Boolean.FALSE);
			userNotificationRepository.save(userNotification);
			System.out.println("UserNotification updated, For idUserNotifcation: " + idUserNotification);

		} catch (Exception e) {

			System.err.println(e.getLocalizedMessage());
		}

	}

	@Async
	@Transactional
	@Override
	public void deleteNotifcation(Long idUserNotification, Long idVlUser) {
		try {

			// passing the idVluser in-order too cross verfiy , whether right user is trying
			// to access the object.
			UserNotification userNotification = userNotificationRepository
					.findByidUserNotificationAndIdVlUser(idUserNotification, idVlUser);

			if (userNotification == null)
				throw new NullPointerException("Invalid User Notification,No such Data found.");

			userNotificationRepository.delete(userNotification);
			System.out.println("UserNotification deleted, For idUserNotifcation: " + idUserNotification);

		} catch (Exception e) {

			System.err.println(e.getLocalizedMessage());
		}

	}

	@Override
	public Document<Map<String, Object>> sendGlobalNotification(GlobalNotifcationDTO request) {

		Document<Map<String, Object>> result = new Document<>();
		try {

			// identify whether the notification for particular user-id's

			List<String> logsList = new ArrayList<>();
			List<String> fireBaseIds = new ArrayList<>();
			List<UserNotification> allUserNotifications = new ArrayList<>();
			int emailCounter = 0;

			logsList.add(
					request.getEmailFlag() == true ? "Requested for email notification" : "No Email Notification.");
			logsList.add(request.getFirebaseFlag() == true ? "Requested for Firebase notification"
					: "No Firebase Notification.");
			logsList.add(
					request.getInAppFlag() == true ? "Requested for InAPP notification" : "No InAPP Notification.");

			if (!request.getUserSurIds().isEmpty() && request.getUser().equalsIgnoreCase("userids")) {

				List<Object[]> userList = userRepository.getUserNotificationData(request.getUserSurIds());

				for (Object[] obj : userList) {

					if (request.getEmailFlag()) {
						if (obj[1] != null) {
							if (!obj[1].toString().isEmpty() && (!obj[1].toString().equals(""))) {
								try {
									emailSerivce.sendGlobalEmail(obj[1].toString(), obj[3].toString(),
											request.getHeading(), request.getMessage());
									emailCounter++;
								} catch (Exception e) {

								}
							}
						}
					}

					if (request.getInAppFlag()) {
						UserNotification userNotification = new UserNotification();
						userNotification.setContent(request.getMessage());
						userNotification.setHeader(request.getHeading());
						userNotification.setisNotViewed(Boolean.TRUE);
						userNotification.setIdVlUser(((BigInteger) obj[0]).longValue());
						allUserNotifications.add(userNotification);

					}

					if (obj[2] != null) {
						fireBaseIds.add(obj[2].toString());
					}

				}

			} else {

				// when user id not available , treat the request for global
				List<Object[]> userList = userRepository.findAllUserByResgisteredAs(request.getUser());

				System.out.println(userList.size());
				for (Object[] obj : userList) {

					if (request.getEmailFlag()) {
						if (obj[1] != null) {
							if (!obj[1].toString().isEmpty() && (!obj[1].toString().equals(""))) {
								try {

									emailSerivce.sendGlobalEmail(obj[1].toString(), obj[3].toString(),
											request.getHeading(), request.getMessage());
									emailCounter++;
								} catch (Exception e) {
									continue;
								}
							}
						}
					}

					if (request.getInAppFlag()) {
						UserNotification userNotification = new UserNotification();
						userNotification.setContent(request.getMessage());
						userNotification.setHeader(request.getHeading());
						userNotification.setisNotViewed(Boolean.TRUE);
						userNotification.setIdVlUser(((BigInteger) obj[0]).longValue());

						allUserNotifications.add(userNotification);

					}

					if (obj[2] != null) {
						fireBaseIds.add(obj[2].toString());
					}

				}

			}

			if (request.getFirebaseFlag())

			{
				if (!request.getUserSurIds().isEmpty() && !fireBaseIds.isEmpty()
						&& request.getUser().equalsIgnoreCase("userids")) {
					
					notificationService.sendNotification(fireBaseIds, request.getMessage(), request.getHeading());

			
				}

				else if (!fireBaseIds.isEmpty() && !request.getUser().equalsIgnoreCase("student")) {

					notificationService.sendNotification(fireBaseIds, request.getMessage(), request.getHeading());

				} else if (fireBaseIds.isEmpty() && (!request.getUser().equalsIgnoreCase("student")
						|| request.getUser().equalsIgnoreCase("userids"))) {
					 logsList.add("No Firebases token found for the selected user category.");
				}

				else {
					Boolean sentFlag = false;

					for (int i = 0; i < 5; i++) {
						@SuppressWarnings("unchecked")
						Document<Boolean> flagData = (Document<Boolean>) notificationService.sendNotificationByTopic(
								notificationTopic, request.getMessage(), request.getHeading(), request.getImageURL());

						sentFlag = flagData.getData();

						if (sentFlag) {
							logsList.add("Total Apporximate firebase notification sent:" + fireBaseIds.size() + " .");
							break;
						} else if (i >= 3) {
							logsList.add("Mulitple attempts tried firebase is throwing following error.");
							logsList.add(flagData.getMessage());
							break;

						} else {
							Thread.sleep(5000);
						}

					}
				}

			}

			if (request.getInAppFlag()) {
				if (!allUserNotifications.isEmpty()) {
					userNotificationRepository.saveAll(allUserNotifications);
					logsList.add("Total no of Inapp notification sent :" + allUserNotifications.size() + " .");
				}

			}

			logsList.add("Total no of email notification sent :" + emailCounter + " .");
			Map<String, Object> data = new HashMap<>();
			data.put("log", logsList);

			result.setData(data);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

}