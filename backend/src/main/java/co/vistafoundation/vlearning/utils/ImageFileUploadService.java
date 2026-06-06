/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


/**
 * @author vk
 *
 */
@Service
public class ImageFileUploadService {
	
	@Autowired
	private  AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket}")
	private  String bucketName;
	
	private static final Logger log = LoggerFactory.getLogger(ImageFileUploadService.class);

	public  File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		}
		return file;
	}
	
	public  String uploadFileToS3Bucket(final File file, String folder, String uniqueFile) {
		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName+"/"+folder, uniqueFile, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName+"/"+folder, uniqueFile).toString();
		return s3Url;
	}
	
	public void deleteFileFromS3(String filePath) {
		 for (S3ObjectSummary file : amazonS3.listObjects(bucketName, filePath).getObjectSummaries()){
			 amazonS3.deleteObject(bucketName, file.getKey());
		    }
	}
	
	//fix me later
	String videoBucket = "vlearning-prod";
	public void deleteSocialVideoFromS3(String filePath) {
		 for (S3ObjectSummary file : amazonS3.listObjects(videoBucket, filePath).getObjectSummaries()){
			 amazonS3.deleteObject(videoBucket, file.getKey());
		    }
	}
	
}
