package co.vistafoundation.vlearning.subscription.repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Item;

import co.vistafoundation.vlearning.subscription.model.VideoStreamingLog;
import co.vistafoundation.vlearning.user_activity.config.DynamoDbConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class VideoStreamingLogRepository {

	private static final Logger logger = LoggerFactory.getLogger(VideoStreamingLogRepository.class);

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	@Autowired
	private DynamoDbConfig dynamoDbConfig; 
	
	
	public VideoStreamingLog save(VideoStreamingLog videoStreamingLog) {
		dynamoDBMapper.save(videoStreamingLog);
		return videoStreamingLog;
	}
	
	public VideoStreamingLog getVideoStreamingLog(Long idVlUser, Long idSubject) {
       return dynamoDBMapper.load(VideoStreamingLog.class,idVlUser+"#"+idSubject);
	}
	
	public String deleteVideoStreamingLog(VideoStreamingLog videoStreamingLog)
	{
		dynamoDBMapper.delete(videoStreamingLog);
		return "Video Stream Log Deleted";
	}
	
	public List<VideoStreamingLog> getVslByIdVlUser(Long idVlUser,String category)  {

		ObjectMapper mapper = new ObjectMapper();

		AmazonDynamoDB client = dynamoDbConfig.amazonDynamoDB();

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("video_streaming_log");

		Map<String, Object> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":val1", idVlUser);
		expressionAttributeValues.put(":val2", category);
		

		ItemCollection<ScanOutcome> items = table.scan("idVlUser = :val1 and category = :val2", // FilterExpression
				null, // ProjectionExpression
				null, // ExpressionAttributeNames - not used in this example
				expressionAttributeValues);
		VideoStreamingLog vsl = null;
		Iterator<Item> iterator = items.iterator();
		List<VideoStreamingLog> vslList= new ArrayList<>();
		while (iterator.hasNext()) {

			String jsonStr = iterator.next().toJSONPretty();

			try {
				vsl = mapper.readValue(jsonStr, VideoStreamingLog.class);
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
			} 
			
			vslList.add(vsl);
		}
		return vslList;

	}
	

	@Async
	public void deleteAllVideoStreamingLogs(List<String> partitionKeys)
	{    
		logger.info("Invoked Video Streaming log Deletion functionality for the partition keys:" + partitionKeys);
	
		for( String partitionKey : partitionKeys) 
		{
			VideoStreamingLog vsl = new VideoStreamingLog();
			vsl.setIdVideoStreamingLog(partitionKey);
			dynamoDBMapper.delete(vsl);
		}
		
		logger.info("Video Streaming log Deletion functionality for the partition keys completed , Total no.of logs deleted : "+ partitionKeys.size()+" ." );
	}
	
	public List<VideoStreamingLog> getVideoStreamingListByPartitionKeyIn(List<String> keyList){
		
		List<VideoStreamingLog> vslList =new ArrayList<>();
		for(String hashKey:keyList) {
			
			VideoStreamingLog vsl = dynamoDBMapper.load(VideoStreamingLog.class,hashKey);
			if(vsl!=null)
			      vslList.add(vsl);
			
		}
		return vslList;
	}
	
}
