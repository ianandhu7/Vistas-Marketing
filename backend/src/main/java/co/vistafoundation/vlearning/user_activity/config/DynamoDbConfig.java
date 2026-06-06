package co.vistafoundation.vlearning.user_activity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDbConfig {

	@Value("${aws.accesskey.id}")
	private String awsAccessKey;

	@Value("${aws.secretaccess.key}")
	private String awsSecretKey;

	private static final Logger logger = LoggerFactory.getLogger(DynamoDbConfig.class);

	@Value("${aws.region}")
	private String awsRegion;

	/**
	 * Creates and returns the AWS credentials using the provided access key and
	 * secret key.
	 *
	 * @return The AWS credentials.
	 */
	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
	}

	/**
	 * Creates and returns the AWS credentials provider using the configured AWS
	 * credentials.
	 *
	 * @return The AWS credentials provider.
	 */

	public AWSCredentialsProvider amazonAWSCredentialsProvider() {
		return new AWSStaticCredentialsProvider(amazonAWSCredentials());
	}
	// Returns the amazonDB instance using the endpoint as well as credentials

	/**
	 * Creates and returns the Amazon DynamoDB client instance using the configured
	 * endpoint and credentials.
	 *
	 * @return The Amazon DynamoDB client instance.
	 */
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {

		logger.info("Initializing User session monitor on dynamoDB.");
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
						"dynamodb." + awsRegion + ".amazonaws.com", awsRegion))
				.withCredentials(amazonAWSCredentialsProvider()).build();
	}

	/**
	 * Creates and returns the DynamoDBMapper instance using the configured Amazon
	 * DynamoDB client.
	 *
	 * @return The DynamoDBMapper instance.
	 */
	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(amazonDynamoDB());
	}
}
