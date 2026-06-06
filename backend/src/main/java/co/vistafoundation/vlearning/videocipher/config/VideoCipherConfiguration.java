package co.vistafoundation.vlearning.videocipher.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.dto.FolderListResponseDTO;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;


/**
 * 
 * @author Naveen Kumar
 *
 */
@Service
public class VideoCipherConfiguration {

	@Value("${videocipher.secret}")
	private String secretKey;

	/**
	 * This method generate new OTP Key for the provided unique videoId from video
	 * Cipher.
	 * 
	 * 
	 * @param String videoId
	 * @return VideoCipherOTP
	 */
	public VideoCipherOTP getOTP(String videoId) {

		String AuthKey = "Apisecret " + secretKey;

		try {
			// preparing the API URL
			String requestURL = "https://dev.vdocipher.com/api/videos/" + videoId + "/otp";

			URL url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// requested headers from videocipher.
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", AuthKey);

			// if responses is not success
			if (conn.getResponseCode() != 200)
				throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// creating responses instance
			VideoCipherOTP vcr = null;
			String output;
			while ((output = br.readLine()) != null) {
				// Assigning the object to video cipher;
				vcr = new ObjectMapper().readValue(output, VideoCipherOTP.class);

			}

			// Disconnecting the connection
			conn.disconnect();

			return vcr;

		} catch (Exception e) {

			System.err.println(e.getLocalizedMessage());

		}

		return null;
	}

	/**
	 * This method generate new OTP Key for the provided unique videoId from video
	 * Cipher and request body.
	 * 
	 * Below are standard json format as to be followed while sending the request
	 * body send it as string.
	 * 
	 * For creating the life time of video , below example will keep video alive for
	 * 5 min . Then licenses will expire. "{\"ttl\":300}"
	 * 
	 * For cerating watermark ,for more details refer
	 * https://www.vdocipher.com/blog/2014/12/add-text-to-videos-with-watermark/
	 * 
	 * "{\"annotate\":\"[{'type':'rtext', 'text':' {name}', 'alpha':'0.60',
	 * 'color':'0xFF0000','size':'15','interval':'5000'}]\"}"
	 * 
	 * For whitelisting domain,will allow to play video only on the mentioned
	 * domain. please use only domain name eg: google.com.
	 * 
	 * "{\"whitelisthref\":\"localhost:4200\"}"
	 * 
	 * For Ipset and geoRules, for more details refer
	 * https://dev.vdocipher.com/api/docs/book/playbackauth/ipgeo.html
	 * 
	 * "{\"ipGeoRules\":[{\"action\":\"false\",\"ipSet\":[],\"countrySet\":[]},{\"action\":\"true\",\"ipSet\":[],\"countrySet\":[\"IN\",\"GB\"]}]}"
	 * 
	 * 
	 * For Forced Bitrate "{\"forcedBitrate\":900}"
	 * 
	 * For rental video or offline download availblity period
	 * 
	 * "{\"licenseRules\":{\"canPersist\":true,\"rentalDuration\":1296000}}"
	 * 
	 * @param String videoId
	 * @return VideoCipherOTP
	 */
	public VideoCipherOTP getOTP(String videoId, String requestBody) {

		String AuthKey = "Apisecret " + secretKey;

		try {
			// preparing the API URL
			String requestURL = "https://dev.vdocipher.com/api/videos/" + videoId + "/otp";

			URL url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// requested headers from videocipher.
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", AuthKey);

			System.out.println("Request body:" + requestBody);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(requestBody);	
			wr.close();

			// if resposes is not sucess
			if (conn.getResponseCode() != 200)
				throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// creating responses instance
			VideoCipherOTP vcr = null;
			String output;
			while ((output = br.readLine()) != null) {
				// assiging the object to video cipher;
				vcr = new ObjectMapper().readValue(output, VideoCipherOTP.class);

			}

			// disconneting the connection
			conn.disconnect();

			return vcr;

		} catch (Exception e) {

			System.err.println(e.getLocalizedMessage());
		
		}

		return null;
	}

	/**
	 * This method will get all videoCipher metadata for the @param videoId
	 * provided.
	 * 
	 * @param videoId
	 * @return VideoCipherMeta
	 * @return null when videoId is invalid
	 */
	@SuppressWarnings("unchecked")
	public HashMap <String,Object>  getVideoMetaData(String videoId) throws Exception {

		
			// preparing the API URL
			String requestURL = "https://dev.vdocipher.com/api/meta/" + videoId;

			URL url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			// requested headers from video cipher.
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");

			// if responses is not success
			if (conn.getResponseCode() != 200)
				throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// creating responses instance
//			VideoCipherMeta vcm = null;
			
			HashMap <String,Object> vcm = null;
			
			String output;
			while ((output = br.readLine()) != null) {
				// assiging the object to video cipher meta;

				vcm = new ObjectMapper().readValue(output, HashMap.class);

			}

			// disconneting the connection
			conn.disconnect();

			return vcm;

		

	}
	
	
	public FolderListResponseDTO getVdocipherFolders(String folderId) {

		String url = "https://dev.vdocipher.com/api/videos/folders/" + folderId;

		// Create HttpClient instance
		HttpClient client = HttpClient.newHttpClient();

		// Build the GET request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", "application/json")
				.header("Authorization", "Apisecret " + secretKey) // Replace with actual API secret
				.GET().build();

		// Send the request and get the response
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Print the response code (for debugging)
		System.out.println("Response Code : " + response.statusCode());

		if (response.statusCode() == 404)
			throw new AppException("Folder does not exist");

		String jsonResponse = response.body();

		// Parse the JSON response into FolderListResponseDTO
		ObjectMapper objectMapper = new ObjectMapper();
		FolderListResponseDTO folderListResponse = null;
		try {
			folderListResponse = objectMapper.readValue(jsonResponse, FolderListResponseDTO.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return folderListResponse;

	}

}
