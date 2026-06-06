package co.vistafoundation.vlearning.notification.Imp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;


import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.notification.dto.NoificationDTO;
import co.vistafoundation.vlearning.notification.dto.StudentsDto;
import co.vistafoundation.vlearning.notification.model.Notification;
import co.vistafoundation.vlearning.notification.repository.NotificationRepository;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.ParentRepository;

@Service
public class NotificationServiceImp implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	ParentRepository parentRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationUtility notificationUtility;

	@Value("${fcm.credentials.path}")
	private Resource fcmCredentials;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveNotification(Notification notification) {

		Document result = new Document();
		try {
			notification.setNotificationDate(Instant.now());
			notification.setReadStatus(false);
			Notification res = notificationRepository.save(notification);
			result.setData(res);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getListofStudents(Long idBatch) {

		Document result = new Document();
		try {

			List<Student> stu = notificationRepository.getStudentsBasedonBatches(idBatch);

			if (stu.isEmpty())
				throw new AppException("There are no students");

			List<StudentsDto> listSt = new ArrayList<StudentsDto>();

			for (Student list : stu) {

				StudentsDto stdo = new StudentsDto();
				stdo.setIdStudent(list.getIdStudent());
				stdo.setStudentName(list.getUser().getFirstName() + ' ' + list.getUser().getLastName());
				stdo.setIdUser(list.getUser().getUserSurId());

				if (list.getParent() != null) {
					stdo.setIdParent(list.getParent().getIdParent());
					stdo.setParentName(
							list.getParent().getUser().getFirstName() + ' ' + list.getParent().getUser().getLastName());
					stdo.setIdParentUser(list.getParent().getUser().getUserSurId());
				}

				else {
					stdo.setParentName("No parent for this student");
				}

				listSt.add(stdo);
			}

			result.setData(listSt);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllNotifications(Long userId) {

		Document result = new Document();
		try {

			List<Notification> notlist = notificationRepository.findByToUserId(userId);

			if (notlist.isEmpty()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			} else {
				List<NoificationDTO> dtoList = new ArrayList<NoificationDTO>();

				for (Notification list : notlist) {
					NoificationDTO dto = new NoificationDTO();

					dto.setIdNotification(list.getIdNotification());
					dto.setNotificationDate(list.getNotificationDate());
					dto.setMessage(list.getMessage());
					dto.setReadStatus(list.getReadStatus());
					dto.setHeader(list.getHeader());

					User user = userRepository.findByUserSurId(list.getFromUserId());

					if (user == null)
						throw new AppException("Could not find any User");

					dto.setFrom(user.getFirstName() + ' ' + user.getLastName());

					dtoList.add(dto);

				}

				result.setData(dtoList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			}

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateNotificationReadStatus(Long idNotification) {

		Document result = new Document();
		try {
			Notification notification = notificationRepository.findByIdNotification(idNotification);
			if (notification == null)
				throw new AppException("Could not find any Notification");
			notification.setReadStatus(true);
			Notification res = notificationRepository.save(notification);
			result.setData(res);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getUnreadNotifications(Long userId) {

		Document result = new Document();
		try {

			List<Notification> notlist = notificationRepository
					.findByToUserIdAndReadStatusOrderByNotificationDateDesc(userId, false);

			if (notlist.isEmpty()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			} else {
				List<NoificationDTO> dtoList = new ArrayList<NoificationDTO>();

				for (Notification list : notlist) {
					NoificationDTO dto = new NoificationDTO();

					dto.setIdNotification(list.getIdNotification());
					;
					dto.setNotificationDate(list.getNotificationDate());
					dto.setMessage(list.getMessage());
					dto.setReadStatus(list.getReadStatus());
					dto.setHeader(list.getHeader());
					// setting the count of unread notification
					dto.setNotiCount(notificationRepository.getNotificationCount(userId));

					User user = userRepository.findByUserSurId(list.getFromUserId());

					if (user == null)
						throw new AppException("Could not find any User");

					dto.setFrom(user.getFirstName() + ' ' + user.getLastName());

					dtoList.add(dto);

				}

				result.setData(dtoList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;

			}

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getsentNotificationOfTeacher(Long userId) {

		Document result = new Document();
		try {

			List<Notification> notlist = notificationRepository.findByFromUserId(userId);

			if (notlist.isEmpty()) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			} else {
				List<NoificationDTO> dtoList = new ArrayList<NoificationDTO>();

				for (Notification list : notlist) {
					NoificationDTO dto = new NoificationDTO();

					dto.setIdNotification(list.getIdNotification());
					dto.setNotificationDate(list.getNotificationDate());
					dto.setMessage(list.getMessage());
					dto.setReadStatus(list.getReadStatus());
					dto.setHeader(list.getHeader());

					dto.setNotiCount(notificationRepository.getNotificationCount(userId));

					User user = userRepository.findByUserSurId(list.getFromUserId());

					if (user == null)
						throw new AppException("Could not find any User");

					dto.setFrom(user.getFirstName() + ' ' + user.getLastName());

					User user2 = userRepository.findByUserSurId(list.getToUserId());

					if (user2 == null)
						throw new AppException("Could not find any Receiver");

					dto.setTomesage(user2.getFirstName() + ' ' + user2.getLastName());

					dtoList.add(dto);

				}

				result.setData(dtoList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;

			}

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendNotification(List<String> deviceRegIdList, String message, String flag) {
		Document result = new Document();
		try {

			ArrayList<String> regIdArray1 = new ArrayList();
			for (int i = 0; i < deviceRegIdList.size(); i++) {
				regIdArray1.add(deviceRegIdList.get(i));
			}

			AndroidConfig androidConfig = AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build();

			MulticastMessage message1 = MulticastMessage.builder().addAllTokens(regIdArray1)
					.setNotification(notificationUtility.buildNotification("V-Learning", message))
					.setAndroidConfig(androidConfig).build();

			try {
				FirebaseMessaging.getInstance().sendEachForMulticast(message1);
				System.out.println("Notification sent successfully.");
			} catch (Exception e) {
				System.out.println("Error sending notification: " + e.getMessage());
			}

			result.setData("Notification Send Sucessfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Notification Send Sucessfully");
			return result;
		} catch (Exception exp) {
			System.err.println("Error occured while sending notification");
			System.out.println(exp.getLocalizedMessage());
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<Boolean> sendNotificationByTopic(String topic, String message1, String title, String imageURL) {

		Document<Boolean> result = new Document<>();
		try {

			String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";

			GoogleCredentials googleCredentials = GoogleCredentials
					.fromStream(fcmCredentials.getInputStream())
					.createScoped(Collections.singletonList(MESSAGING_SCOPE));
			googleCredentials.refreshIfExpired();
			String accessToken = googleCredentials.getAccessToken().getTokenValue();

			JSONObject message = new JSONObject();

			// Add the topic
			message.put("topic", topic);

			// Add the notification
			JSONObject notification = new JSONObject();
			notification.put("title", "V-Learng");
			message.put("notification", notification);

			// Add the Android notification
			JSONObject android = new JSONObject();
			JSONObject androidNotification = new JSONObject();
			android.put("notification", androidNotification);
			message.put("android", android);

			// Add the APNs payload
			JSONObject apns = new JSONObject();
			JSONObject payload = new JSONObject();
			JSONObject aps = new JSONObject();
			aps.put("mutable-content", 1);
			payload.put("aps", aps);
			apns.put("payload", payload);

			JSONObject fcmOptions = new JSONObject();
			apns.put("fcm_options", fcmOptions);
			message.put("apns", apns);

			// Add the Webpush headers
			JSONObject webpush = new JSONObject();
			JSONObject headers = new JSONObject();
			webpush.put("headers", headers);
			message.put("webpush", webpush);

			if (imageURL != null) {
				androidNotification.put("image", imageURL);
				fcmOptions.put("image", imageURL);
				headers.put("image", imageURL);
			}

			JSONObject mainjsonob = new JSONObject();
			mainjsonob.put("message", message);

			System.out.println("mainjsonob " + mainjsonob);

			// mainJsonObject.put("message", message);
			String fcmUrl = "https://fcm.googleapis.com/v1/projects/vlearning-e7dae/messages:send";
			String authKey = "71efd0aad3d8f55cd4f2bda3a23a3b59dfc8a3fb";
			System.out.println("mainjsonob " + mainjsonob);

			URL url = new URL(fcmUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(mainjsonob.toString());
			wr.flush();
			wr.close();

//			int responseCode = conn.getResponseCode();
			// System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response = response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			result.setData(true);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Notification Send Sucessfully");
			return result;
		} catch (Exception exp) {
			System.err.println("Error occured while sending notification");
			System.out.println(exp.getLocalizedMessage());
			result.setData(false);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}
}
