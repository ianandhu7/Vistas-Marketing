package co.vistafoundation.vlearning.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CreateStudentDTO;
import co.vistafoundation.vlearning.user.dto.UserReportDTO;
import co.vistafoundation.vlearning.user.service.StudentService;

/**
 * @author Meghana
 **/

@Controller
@RequestMapping("/api/v1/student")
public class StudentController {

	@Autowired
	private StudentService studentService;


	@PostMapping(value = "/create-student")
	@Transactional
	@Deprecated
	public ResponseEntity<?> createStudnt(@RequestBody CreateStudentDTO createStudentDTO) {

		Document<?> reponses = studentService.saveStudent(createStudentDTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "/getStudents")
	@Transactional
	public ResponseEntity<?> getStudents(@RequestParam(name = "idParent") Long idParent) {

		Document<?> reponses = studentService.getListofstudent(idParent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "/studentdetails")
	@Transactional
	public ResponseEntity<?> getStudentsWithSubscriptionDetails(@RequestParam(name = "idParent") Long idParent) throws Exception{
		Document<?> reponses = studentService.getListofstudents(idParent);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

	
	@GetMapping(value = "/getStudentFact")
	@Transactional
	public ResponseEntity<?> getStudentsFact(@RequestParam(name = "idStudentSubcription") Long idStudentSubcription,@RequestParam(name = "idSubject") Long idSubject) {

		Document<?> reponses = studentService.studentFactData(idStudentSubcription,idSubject);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	
	@GetMapping(value = "/change-mobile")
	public ResponseEntity<?> changeMobileNumber(@RequestParam(name = "userSurId") Long userSurId,
			@RequestParam(name = "email") String email, @RequestParam(name = "newMobile") String newMobile) {

		Document<?> reponses = studentService.changeMobile(userSurId, email, newMobile);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping(value = "/verify-mobile-verification-code")
	public ResponseEntity<?> verifyChangeMobileNumberVerificationCode(
			@RequestParam(name = "userSurId") Long userSurId,
			@RequestParam(name = "verificationCode") String verificationCode,
			@RequestParam(name = "newMobile") String mobileNumber) {
		Document<?> reponses = studentService.verifyChangeMobileNumber(userSurId, verificationCode, mobileNumber);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@GetMapping(value = "/fetchUserDataByUserSurId")
	public ResponseEntity<?> fetchUserObjectByUserSurId(@RequestParam(name = "userSurId") Long userSurId) {

		Document<?> reponses = studentService.fetchUserObjectByUserSurId(userSurId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	 
	  @PostMapping(value ="/uploadProfilePicture")
	  public ResponseEntity<?> uploadUserProfilePicture(@RequestParam(name = "userProfilePicture") MultipartFile userProfilePicture,
			  @RequestParam(name="userSurId") Long userSurId){
		  
		  Document<?> reponses = studentService.uploadUserProfilePicture(userProfilePicture , userSurId);
		  return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	  }
	 
	 
	  @PostMapping(value ="/getProfilePicture")
	  public ResponseEntity<?> getUserPofilePicture(@RequestParam(name="userSurId") Long userSurId){
		  
		  Document<?> reponses = studentService.getUserPofilePicture(userSurId);
		  return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	  }

	  @GetMapping(value="/class-standard/{idStudent}")
	  public ResponseEntity<?> getClassStandard(@PathVariable Long idStudent){
		  
		  Document<?> reponses = studentService.getClassStandard(idStudent);
		  return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	  }
	  

	  @GetMapping(value="medium")
	  public ResponseEntity<?> getStudentMedium(){
		  
		  Document<?> reponses = studentService.getStudentMedium();
		  return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	  }
	   
	  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@GetMapping("/user-syllabus-report")
		public ResponseEntity<?> getUserSyllabusWiseReport() {
		  Document<List<UserReportDTO>> document = studentService.getUserAnalyticReport();
			return ResponseEntity.status(document.getStatusCode()).body(document);
		}
}
