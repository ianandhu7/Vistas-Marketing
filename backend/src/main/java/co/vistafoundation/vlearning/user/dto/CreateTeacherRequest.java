package co.vistafoundation.vlearning.user.dto;

import java.util.List;

import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherSubject;

public class CreateTeacherRequest {
	
	private String teacherFirstName;
	private String teacherLastName;
	private String mobileNumber;
	private String password;
	private Teacher teacher;
	private Long userId ;
	private List<TeacherSubject> teacherSubject;
	private List<TeacherSubjectDTO> teacherSubjectDTO;
	
	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public List<TeacherSubjectDTO> getTeacherSubjectDTO() {
		return teacherSubjectDTO;
	}


	public void setTeacherSubjectDTO(List<TeacherSubjectDTO> teacherSubjectDTO) {
		this.teacherSubjectDTO = teacherSubjectDTO;
	}


	public CreateTeacherRequest(String teacherFirstName, String teacherLastName, String mobileNumber, String password,
			Teacher teacher) {
		super();
		this.teacherFirstName = teacherFirstName;
		this.teacherLastName = teacherLastName;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.teacher = teacher;
	}


	public CreateTeacherRequest() {
		
	}
	
	


	public List<TeacherSubject> getTeacherSubject() {
		return teacherSubject;
	}


	public void setTeacherSubject(List<TeacherSubject> teacherSubject) {
		this.teacherSubject = teacherSubject;
	}


	public String getTeacherFirstName() {
		return teacherFirstName;
	}
	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}
	public String getTeacherLastName() {
		return teacherLastName;
	}
	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
