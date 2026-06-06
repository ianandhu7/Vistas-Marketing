/**
 * 
 */
package co.vistafoundation.vlearning.notification.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.notification.dto.FcmDTO;

/**
 * @author vk
 *
 */

@Service
public class FirebaseMessagingService {

	@Value("${google-server-id}")
	private String serverAPIKey;

	@Autowired
	private FirebaseMessaging firebaseMessaging;
	
	
	private static final Logger logger = LoggerFactory.getLogger(FirebaseMessagingService.class);
	

	public String sendNotification(FcmDTO fcmDTO, String token) throws FirebaseMessagingException {

		try {

			Notification notification = Notification.builder().setTitle(fcmDTO.getSubject())
					.setBody(fcmDTO.getContent()).setImage(fcmDTO.getImage()).build();

			Message message = Message.builder().setToken(token).setNotification(notification)
					.putAllData(fcmDTO.getData()).build();
			firebaseMessaging.send(message);
		} catch (Exception exp) {
			logger.error(exp.getLocalizedMessage());
			return "Failure";
		}

		return "Success";

	}

	public Boolean verifyToken(String idToken) {
		Boolean validFlag = false;

		URL url;
		try {

			url = new URL("https://iid.googleapis.com/iid/info/" + idToken);

			// Open a connection(?) on the URL(??) and cast the response(???)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Now it's "open", we can set the request method, headers etc.
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "key=" + serverAPIKey);
			connection.setRequestProperty("Content-Type", "application/json");

			// This line makes the request

			if (connection.getResponseCode() != 200)
				throw new AppException("Failed : HTTP error code : " + connection.getResponseCode());

			InputStream responseStream = connection.getInputStream();

			// Manually converting the response body InputStream to APOD using Jackson

			Map<String,Object> respons = new ObjectMapper().readValue(responseStream,
					new TypeReference<Map<String,Object>>() {
					});

			// Finally we have the response
			System.out.println(respons.get("appSigner"));

			validFlag = true;

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		return validFlag;

	}

}
