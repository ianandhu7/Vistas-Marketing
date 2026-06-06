/**
 * 
 */
package co.vistafoundation.vlearning.auth.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.pg.merchant.PaytmChecksum;

import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.auth.dto.PaytmOrderStatusResponse;
import co.vistafoundation.vlearning.auth.model.Merchant;
import co.vistafoundation.vlearning.auth.model.PaytmDetails;
import co.vistafoundation.vlearning.auth.repository.MerchantRepository;

/**
 * @author vk
 *
 */
@Configuration
public class PaytmPaymentConfig {

	@Autowired
	private PaytmDetails paytmDetails;

	@Autowired
	MerchantRepository merchantRepository;
	
	@Value("${paytm.order.status.url}")
	private String paymentOrderStatusUrl;
	
	@Value("${paytm.payment.vsms.merchant.id}")
	private String merchantId;
	
	private static final Logger logger = LoggerFactory.getLogger(PaytmPaymentConfig.class);

	/*
	 * redirects to payment gateway page 
	 */
	public TreeMap<String, String> getRedirect(PaytmDetailsDTO paytmDetailsDTO) throws Exception {
		//	ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", paytmDetailsDTO.getMerchantPhone());
		parameters.put("EMAIL", paytmDetailsDTO.getMerchantEmail());
		parameters.put("MID", paytmDetailsDTO.getMerchantId());
		parameters.put("ORDER_ID", paytmDetailsDTO.getOrderId());
		parameters.put("TXN_AMOUNT", paytmDetailsDTO.getTransactionAmount());
		parameters.put("CUST_ID", paytmDetailsDTO.getCustomerId());
		String checkSum = getCheckSum(parameters, paytmDetailsDTO.getMerchantKey());
		parameters.put("CHECKSUMHASH", checkSum);
		return parameters;
	}

	/*
	 * validate checksum
	 */
	public boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
		Merchant merchant = merchantRepository.findByMerchantId(merchantId);
		if (merchant != null) {
			return PaytmChecksum.verifySignature(parameters, merchant.getMerchantKey(), paytmChecksum);
		}else {
			return false;
		}
	}

	/*
	 * get checksum string
	 */
	private String getCheckSum(TreeMap<String, String> parameters, String key) throws Exception {
		return PaytmChecksum.generateSignature(parameters, key);
	}
	
	public PaytmOrderStatusResponse checkTranscationStatus(String orderId)
	{   
		
		Merchant merchant = merchantRepository.findByMerchantId(merchantId);
		
		if (merchant == null) throw new  NullPointerException("Invalid Merchant Data");
		
		JSONObject paytmParams = new JSONObject();

		/* body parameters */
		JSONObject body = new JSONObject();


		
		try {
			
			body.put("mid", merchant.getMerchantId());

			/* Enter your order id which needs to be check status for */
			body.put("orderId", orderId);

		/**
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
		*/
	  
	    
	    logger.info("body: "+body.toString());
		String checksum = PaytmChecksum.generateSignature(body.toString(),merchant.getMerchantKey());
		
		 boolean verifySignature = PaytmChecksum.verifySignature(body.toString(), merchant.getMerchantKey(), checksum);
		/* head parameters */
		JSONObject head = new JSONObject();

		/* put generated checksum value here */
		head.put("signature", checksum);
		
	     logger.info("Generated CheckSome: "+checksum );
        
	     logger.info("Verify CheckSome: "+verifySignature );
		/* prepare JSON string for request */
		paytmParams.put("body", body);
		paytmParams.put("head", head);
		logger.info("head: "+head.toString());
		String post_data = paytmParams.toString();
		

		/* for Staging */
		URL url = new URL(paymentOrderStatusUrl);

		/* for Production */
	
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);
		    connection.setConnectTimeout(5000); // 5 seconds connection timeout
            connection.setReadTimeout(5000); // 5 seconds read timeout
		    

		    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		    requestWriter.writeBytes(post_data);
		    requestWriter.close();
		    String responseData = "";
		    PaytmOrderStatusResponse posr = null;
		    InputStream is = connection.getInputStream();
		    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		    if ((responseData = responseReader.readLine()) != null) {
		        System.out.append("Response: " + responseData);
		        posr = new ObjectMapper().readValue(responseData, PaytmOrderStatusResponse.class);
		    }
		    responseReader.close();
		    
		    return posr;
		    
		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage());
		}
		
		return null;
	}

	public Map<String, Map<String, Object>> paymentPendingStatusCheck(String orderId)

	{

		Merchant merchant = merchantRepository.findByMerchantId(merchantId);

		if (merchant == null)
			throw new NullPointerException("Invalid Merchant Data");

		JSONObject paytmParams = new JSONObject();

		/* body parameters */
		JSONObject body = new JSONObject();

		try {

			body.put("mid", merchant.getMerchantId());

			/* Enter your order id which needs to be check status for */
			body.put("orderId", orderId);

			/**
			 * Generate checksum by parameters we have in body You can get Checksum JAR from
			 * https://developer.paytm.com/docs/checksum/ Find your Merchant Key in your
			 * Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
			 */

			System.out.println("body: " + body.toString());
			String checksum = PaytmChecksum.generateSignature(body.toString(), merchant.getMerchantKey());

			boolean verifySignature = PaytmChecksum.verifySignature(body.toString(), merchant.getMerchantKey(),
					checksum);
			/* head parameters */
			JSONObject head = new JSONObject();

			/* put generated checksum value here */
			head.put("signature", checksum);

			System.out.println("Generated CheckSome: " + checksum);

			System.out.println("Verify CheckSome: " + verifySignature);
			/* prepare JSON string for request */
			paytmParams.put("body", body);
			paytmParams.put("head", head);
			System.out.println("head: " + head.toString());
			String post_data = paytmParams.toString();

			System.out.println(post_data);

			/* for Staging */
			URL url = new URL(paymentOrderStatusUrl);

			/* for Production */

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			requestWriter.writeBytes(post_data);
			requestWriter.close();
			String responseData = "";
			Map<String, Map<String, Object>> posr = null;
			InputStream is = connection.getInputStream();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			if ((responseData = responseReader.readLine()) != null) {
				System.out.append("Response: " + responseData);
				posr = new ObjectMapper().readValue(responseData,
						new TypeReference<Map<String, Map<String, Object>>>() {
						});
			}
			responseReader.close();

			return posr;

		} catch (Exception exception) {
			logger.error(exception.getLocalizedMessage());
		}

		return null;
	}

}
