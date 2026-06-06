/**
 * Test
 */
package co.vistafoundation.vlearning.auth.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptureGeoLocation {

	public JSONObject generateGeoLocation() {
		String ipServiceURL = "https://api.ipify.org"; // Service to get public IP
		String geoServiceURL = "http://ip-api.com/json/"; // Service to get geolocation details

		try {
			// Step 1: Get the public IP address
			URL url = new URL(ipServiceURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String publicIPAddress = in.readLine();
			in.close();

			// Step 2: Get the location details for the public IP address
			URL geoURL = new URL(geoServiceURL + publicIPAddress);
			HttpURLConnection geoConnection = (HttpURLConnection) geoURL.openConnection();
			geoConnection.setRequestMethod("GET");

			BufferedReader geoIn = new BufferedReader(new InputStreamReader(geoConnection.getInputStream()));
			StringBuilder geoResponse = new StringBuilder();
			String line;
			while ((line = geoIn.readLine()) != null) {
				geoResponse.append(line);
			}
			geoIn.close();

			// Parse the JSON response
			JSONObject jsonObject = new JSONObject(geoResponse.toString());

			String country = jsonObject.getString("country");
			String region = jsonObject.getString("regionName");
			String city = jsonObject.getString("city");
			double lat = jsonObject.getDouble("lat");
			double lon = jsonObject.getDouble("lon");
			String isp = jsonObject.getString("isp");
			String zip = jsonObject.getString("zip");

			// Construct a new JSONObject with the location details
			JSONObject locationDetails = new JSONObject();

			locationDetails.put("IP Address", publicIPAddress);
			locationDetails.put("country", country);
			locationDetails.put("region", region);
			locationDetails.put("city", city);
			locationDetails.put("latitude", lat);
			locationDetails.put("longitude", lon);
			locationDetails.put("isp", isp);
			locationDetails.put("zip", zip);

			return locationDetails;

		} catch (Exception e) {
			System.err.println("Unable to retrieve location details.");
			e.printStackTrace();
			return null; // Return null in case of an error
		}
	}

	 public JSONObject generateGeoLocation(String clientIp) {
	        String geoServiceURL = "http://ip-api.com/json/";

	        try {
	            // Step 1: Get the location details for the client's IP address
	            URL geoURL = new URL(geoServiceURL + clientIp);
	            HttpURLConnection geoConnection = (HttpURLConnection) geoURL.openConnection();
	            geoConnection.setRequestMethod("GET");

	            BufferedReader geoIn = new BufferedReader(new InputStreamReader(geoConnection.getInputStream()));
	            StringBuilder geoResponse = new StringBuilder();
	            String line;
	            while ((line = geoIn.readLine()) != null) {
	                geoResponse.append(line);
	            }
	            geoIn.close();

	            // Parse the JSON response
	            JSONObject jsonObject = new JSONObject(geoResponse.toString());
	            
	            String country = jsonObject.optString("country", "N/A");
	            String region = jsonObject.optString("regionName", "N/A");
	            String city = jsonObject.optString("city", "N/A");
	            double lat = jsonObject.optDouble("lat", 0.0);
	            double lon = jsonObject.optDouble("lon", 0.0);
	            String isp = jsonObject.optString("isp", "N/A");
	            String zip = jsonObject.optString("zip", "N/A");

	            // Construct a new JSONObject with the location details
	            JSONObject locationDetails = new JSONObject();

	            locationDetails.put("IP Address", clientIp);
	            locationDetails.put("country", country);
	            locationDetails.put("region", region);
	            locationDetails.put("city", city);
	            locationDetails.put("latitude", lat);
	            locationDetails.put("longitude", lon);
	            locationDetails.put("isp", isp);
	            locationDetails.put("zip", zip);

	            return locationDetails;

	        } catch (Exception e) {
	            System.err.println("Unable to retrieve location details.");
	            e.printStackTrace();
	            return null; // Return null in case of an error
	        }
	    }
	
	
}
