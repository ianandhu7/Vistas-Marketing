/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

/**
 * @author vk
 *
 */
@Service
public class SNSService {

	// AWS Credentials
	@Value("${aws.accesskey.id}")
	private String accessKey;

	@Value("${aws.secretaccess.key}")
	private String secretAccessKey;

	@Value("${sms.flag}")
	private String sendFlag;

	private static final Logger log = LoggerFactory.getLogger(SNSService.class);
	/**
	 * @param String mobile
	 * @param String message
	 * @return void
	 * 
	 * method will allow user to send sms for the client using AWS-SNS
	 * Service
	 */
	public boolean snsSMSService(String mobile, String message) {
		System.out.println(accessKey+" : "+secretAccessKey+" : "+sendFlag);
		// check send flag is 
		if (Boolean.parseBoolean(sendFlag)) {
			Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
			smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("mySenderID") // The
					// sender ID
					// shown on
					// the
					// device.
					.withDataType("String"));
			smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue().withStringValue("1.00") // Sets the max
					// price to 0.50
					// USD
					// (default).
					.withDataType("Number"));
			smsAttributes.put("AWS.SNS.SMS.SMSType",
					new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

			// Used for authenticating to AWS
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);

			// Create SNS Client
			AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Regions.US_WEST_2) // region for SNS service
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

			try {
				PublishResult result = snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(mobile)
						.withMessageAttributes(smsAttributes));
				if (result.getMessageId()!=null) {
					return true;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				snsClient.shutdown();
			}

		}
		return false;
	}
}