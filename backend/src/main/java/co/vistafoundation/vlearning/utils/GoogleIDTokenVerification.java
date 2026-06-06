/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import co.vistafoundation.vlearning.auth.dto.GoogleLoginRequestDTO;

/**
 * @author vk
 *
 */
@Service
public class GoogleIDTokenVerification {

	@Value("${google-client-id}")
	String CLIENT_ID;

	@Value("#{${listOfClientIds}}")
	private List<String> clientIds;
	
	private static final Logger log = LoggerFactory.getLogger(GoogleIDTokenVerification.class);

	private static final HttpTransport transport = new NetHttpTransport();

	private static final JsonFactory jsonFactory = new JacksonFactory();

	public boolean getGoogleIDTokenVerification(GoogleLoginRequestDTO googleLoginRequestDTO) {

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(clientIds).build();

		try {

			GoogleIdToken idToken = verifier.verify(googleLoginRequestDTO.getIdToken());

			if (idToken != null) {
				Payload payload = idToken.getPayload();
				String email = payload.getEmail();

				if (email.equals(googleLoginRequestDTO.getEmail())) {
					return true;

				} else
					return false;

			} else {
				System.out.println("Invalid GoogleID token.");
				return false;
			}
		} catch (GeneralSecurityException e) {
			log.error(e.getMessage());
			System.out.println("Invalid GoogleID token.");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}

}
