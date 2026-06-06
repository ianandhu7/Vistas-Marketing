package co.vistafoundation.vlearning.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.auth.dto.TeacherInfoDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.CreateTeacherRequest;
import co.vistafoundation.vlearning.user.dto.TeacherResponseDTO;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherAvailability;

public interface TeacherService {

	@SuppressWarnings("rawtypes")
	public Document getTeacherById(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document updateTeacher(CreateTeacherRequest createTeacherRequest);

	public Document<String> uploadTeacherIntroVideo(MultipartFile teacherIntroVideo, String teacherName);

	@SuppressWarnings("rawtypes")
	public Document getTeacherByCategory(String Category);

	public Document<Page<TeacherInfoDTO>> searchTeacherByCategory(String Category, String searchTerm, int pageNumber);

	public Document<List<TeacherResponseDTO>> getLandingTeacherByCategory(String Category);

	public Document<Teacher> createTeacher(CreateTeacherRequest createTeacherRequest);
	
	/*@SuppressWarnings("rawtypes")
	public Document saveTeacherAvailability(TeacherAvailability teacherAvailability);*/
	
	
	public Document<List<TeacherAvailability>> getTeacherAvailability(Long idTeacher);
	
}
