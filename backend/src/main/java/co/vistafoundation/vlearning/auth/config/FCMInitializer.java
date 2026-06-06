/**
 * 
 */
package co.vistafoundation.vlearning.auth.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * @author vk
 *
@Configuration
 *
 */
@Configuration
@Service
public class FCMInitializer {

	@Value("${fcm.credentials.path}")
	private Resource fcmCredentials;

	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {	
			
		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(fcmCredentials.getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions
				.builder()
				.setCredentials(googleCredentials)
				.build();
		FirebaseApp app;
		if (FirebaseApp.getApps().isEmpty()) {
			app = FirebaseApp.initializeApp(firebaseOptions);
		} else {
		    app = FirebaseApp.getApps().get(0);
		}
		return FirebaseMessaging.getInstance(app);
	}
    
}
