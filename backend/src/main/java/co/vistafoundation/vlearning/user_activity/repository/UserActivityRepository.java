/**
 * 
 */
package co.vistafoundation.vlearning.user_activity.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import co.vistafoundation.vlearning.user_activity.UserActivity;

/**
 * @author NaveenKumar
 *
 */

@Repository
public class UserActivityRepository {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;


	/**
	 * Saves a UserActivity object to DynamoDB.
	 *
	 * @param userActivity The UserActivity object to be saved.
	 * @return The saved UserActivity object.
	 */
	public UserActivity save(UserActivity userActivity) {
		dynamoDBMapper.save(userActivity);
		return userActivity;
	}

	/**
	 * Retrieves a UserActivity object from DynamoDB based on the provided ID.
	 *
	 * @param idUserActivity The ID of the UserActivity object to retrieve.
	 * @return The retrieved UserActivity object, or null if not found.
	 */
	public UserActivity getUserActivity(String idUserActivity) {
		return dynamoDBMapper.load(UserActivity.class, idUserActivity);
	}

	/**
	 * Retrieves all UserActivity objects from DynamoDB.
	 *
	 * @return A list of all UserActivity objects.
	 */
	public List<UserActivity> getAllUserActivity() {
		return dynamoDBMapper.scan(UserActivity.class, new DynamoDBScanExpression());
	}

	/**
	 * Deletes a UserActivity object from DynamoDB based on the provided ID.
	 *
	 * @param idUserActivity The ID of the UserActivity object to delete.
	 * @return A string indicating the result of the deletion operation.
	 */
	public String delete(String idUserActivity) {
		UserActivity ua = dynamoDBMapper.load(UserActivity.class, idUserActivity);
		dynamoDBMapper.delete(ua);
		return "UserActivty Deleted!";
	}
	/**
	 * Updates a UserActivity object in DynamoDB.
	 *
	 * @param userActivity The UserActivity object to update.
	 * @return The updated UserActivity object.
	 */

	public UserActivity update(UserActivity userActivity) {

		dynamoDBMapper.save(userActivity, new DynamoDBSaveExpression().withExpectedEntry("idUserActivity",
				new ExpectedAttributeValue(new AttributeValue().withS(userActivity.getIdUserActivity()))));
		return userActivity;
	}
	
	
	 public List<UserActivity> getMatchingUserActivities(UserActivity userActivity) {
		 Map<String, AttributeValue> attributeValues = new HashMap<>();
		    attributeValues.put(":deviceTypeVal", new AttributeValue().withS(userActivity.getDeviceType()));
		    attributeValues.put(":userSurIdVal", new AttributeValue().withN(String.valueOf(userActivity.getUserSurId())));

		    ScanRequest scanRequest = new ScanRequest()
		            .withTableName("user_activity") // Replace with your table name
		            .withFilterExpression("deviceType = :deviceTypeVal and userSurId = :userSurIdVal")
		            .withExpressionAttributeValues(attributeValues);

		    ScanResult scanResult = amazonDynamoDB.scan(scanRequest);
		    
		    List<UserActivity> userActivities = scanResult.getItems().stream()
		            .map(item -> dynamoDBMapper.marshallIntoObject(UserActivity.class, item))
		            .collect(Collectors.toList());
		    
		    return userActivities;

	    }
	
}
