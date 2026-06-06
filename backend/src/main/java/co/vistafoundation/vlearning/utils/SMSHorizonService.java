/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CheckSMSCredits;

/**
 * @author Naveen Kumar
 *
 */
@Service
public class SMSHorizonService {

	@Value("${sms.user.id}")
	String user;

	@Value("${sms.apikey.id}")
	String apikey;

	@Value("${sms.sender.id}")
	String senderid;

	// For Plain Text, use "txt" ; for Unicode symbols or regional Languages like
	// hindi/tamil/kannada use "uni"
	String type = "txt";

	// Send SMS API
	// String mainUrl ="https://smshorizon.co.in/api/sendsms.php?";

	private static final Logger log = LoggerFactory.getLogger(SMSHorizonService.class);

	/**
	 * @param String mobile
	 * @param String message
	 * @return void
	 * 
	 *         method will allow user to send sms for the client using SMS-Horizon
	 *         Service.
	 * 
	 */
	@SuppressWarnings("deprecation")
	public Boolean smsService(String mobile, String message) {

		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		String tempUrl = "https://smshorizon.co.in/api/sendsms.php?";

		String encoded_message = URLEncoder.encode(message);

		StringBuilder sbPostData = new StringBuilder(tempUrl);
		sbPostData.append("user=" + user);
		sbPostData.append("&apikey=" + apikey);
		sbPostData.append("&message=" + encoded_message);
		sbPostData.append("&mobile=" + mobile);
		sbPostData.append("&senderid=" + senderid);
		sbPostData.append("&type=" + type);

		String mainUrl = sbPostData.toString();

		try {
			// prepare connection
			myURL = new URL(tempUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(
					new InputStreamReader(((HttpURLConnection) (new URL(mainUrl)).openConnection()).getInputStream(),
							Charset.forName("UTF-8")));
			// reading response
			String response;
			while ((response = reader.readLine()) != null) {
				System.out.println("message-id: " + response);
				return true;
			}
			// print response

			// finally close connection
			reader.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return false;

	}

	/**
	 * @author Naveen Kumar A
	 * @param mobile
	 * @param message
	 * @param templateId
	 * 
	 *                   Use this method to send sms with standard templates created
	 *                   in sms Horizon
	 * 
	 *                   return Boolean , display message-transaction-id in console
	 */
	@SuppressWarnings("deprecation")
	public Boolean smsService(String mobile, String message, String templateId) {

		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		String tempUrl = "https://smshorizon.co.in/api/sendsms.php?";

		String encoded_message = URLEncoder.encode(message);

		StringBuilder sbPostData = new StringBuilder(tempUrl);
		sbPostData.append("user=" + user);
		sbPostData.append("&apikey=" + apikey);
		sbPostData.append("&message=" + encoded_message);
		sbPostData.append("&mobile=" + mobile);
		sbPostData.append("&senderid=" + senderid);
		sbPostData.append("&type=" + type);
		sbPostData.append("&tid=" + templateId);

		String mainUrl = sbPostData.toString();

		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			// reader= new BufferedReader(new
			// InputStreamReader(myURLConnection.getInputStream()));

			reader = new BufferedReader(
					new InputStreamReader(((HttpURLConnection) (new URL(mainUrl)).openConnection()).getInputStream(),
							Charset.forName("UTF-8")));
			// reading response
			String response;
			while ((response = reader.readLine()) != null) {
				System.out.println("message-id: " + response);
				return true;

			}
			// print response

			// finally close connection
			reader.close();

		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return false;

	}

	public Document<CheckSMSCredits> checkSMSCredits() {
		Document<CheckSMSCredits> result = new Document<CheckSMSCredits>();
		boolean isLowBalance = false;
		CheckSMSCredits sms = new CheckSMSCredits();

		try {
			String apiKey = apikey;
			System.out.println(apiKey);
			String username = user;
			System.out.println(username);
			String urlString = "http://smshorizon.co.in/api/balance.php?user="+username+"&apikey="+apiKey;
			System.out.println(urlString);
			// Create a URL object
			URL url = new URL(urlString);

			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			// Get the response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// Read the response if successful
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				String balance = response.toString().replaceAll("[^0-9]", "");
				Long balanceLong = Long.parseLong(balance);

				if (balanceLong <= 500)
					isLowBalance = true;

				sms.setBalance(balanceLong.toString());
				sms.setIsLowBalance(isLowBalance);
				System.out.println(responseCode);
System.out.println(sms);
				result.setData(sms);
				result.setStatusCode(responseCode);
				result.setMessage("Balance: " + balance);

			} else {
				result.setData(null);
				result.setStatusCode(responseCode);
				result.setMessage("Error Occurred While fetching balance " );
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getMessage());
		}
		return result;
	}

}
