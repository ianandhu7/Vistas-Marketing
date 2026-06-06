/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author vk
 *
 */
@Service
public class FileUploadService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket}")
	private String bucket;
	
	private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

	public File convertStringDataToFile(final String data, String filename) {
		File file = new File(filename);
        try {
            //write string to file
            FileUtils.writeStringToFile(file, data, StandardCharsets.UTF_8);
            System.out.print("Data written to file successfully.");
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
		return file;
	}
	
	/**
	 * @author NAVEEN KUMAR A
	 * @param dirLocation (without bucket name , provide directory where file wants to get stored"
	 * @param uniqueFileName (Unique File name for the s3 object)
	 * @param file 
	 * @return String (s3 Location url);
	 */
	public String uploadFileToS3Bucket(String dirLocation,String uniqueFileName,  File file) {
		
		String bucketURI= bucket+dirLocation;
		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketURI, uniqueFileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketURI, uniqueFileName).toString();
		return s3Url;
	}
	
	public File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		}
		return file;
	}
	
}
