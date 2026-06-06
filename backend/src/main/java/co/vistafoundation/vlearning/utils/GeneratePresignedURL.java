
package co.vistafoundation.vlearning.utils;

/**
 * @author Naveen Kumar A
 */

import java.net.URL;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

@Configuration
public class GeneratePresignedURL {

	// Access key id will be read from the application.properties
	@Value("${aws.accesskey.id}")
	private String accessKeyId;

	// Secret access key will be read from the application.properties
	@Value("${aws.secretaccess.key}")
	private String secretAccessKey;
	
    //defined in application.properties 
	@Value("${aws.s3.bucket}")
	private String bucketName;

	// Region will be read from the application.properties
	@Value("${aws.s3.region}")
	private String region;

	public String generate(String s3URL) {

		String objectKey;

		try {

			objectKey = new GeneratePresignedURL().getObjectKey(s3URL);

			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
					.withCredentials(
							new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
					.build();

			// Set the presigned URL to expire after one hour.
			java.util.Date expiration = new java.util.Date();
			long expTimeMillis = Instant.now().toEpochMilli();
			expTimeMillis += 1000 * 60 * 60;
			expiration.setTime(expTimeMillis);

			// Generate the presigned URL.
			System.out.println("Generating pre-signed URL.");
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
					objectKey).withMethod(HttpMethod.GET).withExpiration(expiration);
			URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

			return url.toString();

		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
			return null;
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
			return null;
		}
	}

	private String getObjectKey(String url) {
		String[] arrOfStr = url.split("/");
		return arrOfStr[arrOfStr.length - 2] + "/" + arrOfStr[arrOfStr.length - 1];

	}
}
