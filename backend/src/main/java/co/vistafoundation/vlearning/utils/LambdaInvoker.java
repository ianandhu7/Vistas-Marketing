package co.vistafoundation.vlearning.utils;

import java.nio.ByteBuffer;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

@Component
public class LambdaInvoker {

	@Value("${aws.accesskey.id}")
	private String accessId;

	@Value("${aws.secretaccess.key}")
	private String secretKey;

	@Async
	public void userActivityLamda(Long userSurId, String device, Instant time, String ipAddress) {

		try {

			AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessId, secretKey)))
					.withRegion(Regions.AP_SOUTH_1).build();
System.out.println("testing.........................");
			String payload = String.format(
					"{ \"UserSurId\": \"%s\", \"Timestamp\": \"%s\", \"Device\": \"%s\", \"IpAddress\": \"%s\" }",
					userSurId, time.toString(), device, ipAddress);

			InvokeRequest invokeRequest = new InvokeRequest()
					.withFunctionName("arn:aws:lambda:ap-south-1:753548365490:function:userActivityTimeSeries")
					.withPayload(ByteBuffer.wrap(payload.getBytes()));
			String functionOutput = null;

			InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
			System.out.println("Invoked.........................");
			functionOutput = new String(invokeResult.getPayload().array());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
