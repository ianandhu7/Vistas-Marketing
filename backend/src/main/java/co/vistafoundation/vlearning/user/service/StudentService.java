package co.vistafoundation.vlearning.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CreateStudentDTO;
import co.vistafoundation.vlearning.user.dto.UserReportDTO;
import co.vistafoundation.vlearning.user.model.StudentMedium;

public interface StudentService {

	@SuppressWarnings("rawtypes")
	public Document saveStudent(CreateStudentDTO createStudentDTO);

	@SuppressWarnings("rawtypes")
	public Document getListofstudent(Long idParent);
	
	@SuppressWarnings("rawtypes")
	public Document getListofstudents(Long idParent);

	@SuppressWarnings("rawtypes")
	public Document changeMobile(Long userSurId, String email, String newMobile);

	@SuppressWarnings("rawtypes")
	public Document verifyChangeMobileNumber(Long userSurId, String verificationCode, String mobileNumber);

	@SuppressWarnings("rawtypes")
	public Document fetchUserObjectByUserSurId(Long userSurId);

	@SuppressWarnings("rawtypes")
	public Document studentFactData(Long idStudentSubcription, Long idSubject);
	
	@SuppressWarnings("rawtypes")
	public Document uploadUserProfilePicture(MultipartFile userProfilePicture, Long userSurId);
	
	@SuppressWarnings("rawtypes")
	public Document getUserPofilePicture(Long userSurId);
	
	@SuppressWarnings("rawtypes")
	public Document getClassStandard(Long idStudent);
	
	public Document<List<StudentMedium>> getStudentMedium();
	
	public Document<List<UserReportDTO>> getUserAnalyticReport();
	
}
