package co.vistafoundation.vlearning.user.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CreateTeacherRequest;
import co.vistafoundation.vlearning.user.dto.TeacherResponseDTO;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherAvailability;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private TeacherService teacherService;
	
	@Value("${aws.s3.bucket}")
	private String bucketName;
	
	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

	/**
	 * updated by
	 * 
	 * @author Naveen Kumar
	 * @param createTeacherRequest This mehod create teacher profile and deliver
	 *                             email to the respective user.
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-teacher")
	@CacheEvict(value = {"teacherCache"}, allEntries = true)
	public ResponseEntity<?> createTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
		Document<Teacher> responses = teacherService.createTeacher(createTeacherRequest);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/fetch-all-teachers")
	public ResponseEntity<?> getAllTeachers() {

		Map<String, Object> response = new HashMap<String, Object>();

		List<Teacher> listOfTeachers = teacherRepository.findAll();
		if (listOfTeachers.isEmpty()) {
			response.put("teachersList", new ArrayList<>());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("teachersList", listOfTeachers);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/get-teacher-info")
	public ResponseEntity<?> getTeacherDetailsByUserId(@RequestParam(name = "userSurId") Long userSurId) {

		Document<Object> doc = new Document<>();
		if (userSurId != 0 || userSurId != null) {

			Teacher newTeacher = teacherRepository.getTeacherByUser_UserSurId(userSurId);

			if (newTeacher != null) {
				doc.setData(newTeacher);
				doc.setMessage("Teacher Details Fetched Successfully");
				doc.setStatusCode(200);
				return new ResponseEntity<>(doc, HttpStatus.OK);
			}
			doc.setData(null);
			doc.setMessage("Teacher Not found with this userSurId");
			doc.setStatusCode(200);
			return new ResponseEntity<>(doc, HttpStatus.OK);
		} else {
			doc.setData(null);
			doc.setMessage("userSurId cannot be null or empty");
			doc.setStatusCode(500);
			return new ResponseEntity<>(doc, HttpStatus.OK);
		}
	}
	
	/**
	 * @author Naveen Kumar A
	 * @param teacherProfileImage
	 * @param teacherName
	 * @return This method upload user picture to s3 and returns URL.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping(value = "/uploadTeacherProfilePicture")
	@CacheEvict(value = {"teacherCache"}, allEntries = true)
	public ResponseEntity<?> uploadUserProfilePicture(
			@RequestParam(name = "teacherProfileImage") MultipartFile teacherProfileImage,
			@RequestParam(name = "teacherName") String teacherName) {

		Document<String> result = new Document<>();
		try {

			String uniqueFile = Long.toString(System.currentTimeMillis());

			File file = convertMultiPartFileToFile(teacherProfileImage);
			
		
			// Delete the file After Upload Success from Classpath
			// Added By Ahmed
			String extension = "";
			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
			    extension = file.getName().substring(i+1);
			}
			
			String uploadedPictureUrl = uploadFileToS3Bucket(file, uniqueFile+"."+extension);
			
			boolean isDeletedFile = file.delete();
			log.info("file deleted from the system : " +isDeletedFile );

			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Teacher Profile Picture Uploaded Successfully");
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return new ResponseEntity<Document<?>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		}
		return file;
	}

	private String uploadFileToS3Bucket(final File file, String uniqueFile) {

		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName+"/staff/pictures", uniqueFile, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName+"/staff/pictures", uniqueFile).toString();
		return s3Url;
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/getTeacherById/{idTeacher}")
	public ResponseEntity<?> getAllQuestionsForSubject(@PathVariable Long idTeacher) {
		Document<?> responses = teacherService.getTeacherById(idTeacher);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/UpdateTeacher")
	@CacheEvict(value = {"teacherCache"}, allEntries = true)
	public ResponseEntity<?> UpdateTeacher(@RequestBody CreateTeacherRequest createTeacherRequest) {
		Document<?> responses = teacherService.updateTeacher(createTeacherRequest);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param teacherIntroVideo
	 * @param teacherName
	 * @return This method will upload teacher intro to s3 and return url.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping(value = "/upload-teacher-intro")
	@CacheEvict(value = {"teacherCache"}, allEntries = true)
	public ResponseEntity<?> UploadTeacherIntroVideo(@RequestParam MultipartFile teacherIntroVideo,
			@RequestParam String teacherName) {
		Document<String> responses = teacherService.uploadTeacherIntroVideo(teacherIntroVideo, teacherName);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "/getteacherByCategory")
	public ResponseEntity<?> getteacherByCategory(@RequestParam String category) {
		Document<?> responses = teacherService.getTeacherByCategory(category);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param category
	 * @param query
	 * @param pageNumber
	 * @return This method will return list of teacher based on params.
	 */
	
	@GetMapping(value = "/search/{category}")
	public ResponseEntity<?> getAllQuestionsForSubject(@PathVariable String category, @RequestParam String query,
			@RequestParam(defaultValue = "0") int pageNumber) {
		Document<?> responses = teacherService.searchTeacherByCategory(category, query, pageNumber);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar A
	 * @param category
	 * @return list of teacher based on batch assigned and category.
	 */
	@GetMapping(value = "/teacher-by-category")
	public ResponseEntity<?> getLandingTeachersByCategory(@RequestParam String category) {
		Document<List<TeacherResponseDTO>> responses = teacherService.getLandingTeacherByCategory(category);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	/**
	 * @author Naveen Kumar A
	 * @param idTeacher
	 * @return List of teacher availablity. 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "/availablity")
	public ResponseEntity<?> getTeacherAvailablity(@RequestParam Long idTeacher) {
		Document<List<TeacherAvailability>> responses = teacherService.getTeacherAvailability(idTeacher);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
}
