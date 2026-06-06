package co.vistafoundation.vlearning.user.dto;

import co.vistafoundation.vlearning.user.model.Student;

public class CreateStudentDTO {
	
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String password;
	private String username;
	private String gmail;
	private Long classStandard;
	private Long userId;
	private String secondaryLanguage;
	private Student student;
	
	
	
	
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	public Long getClassStandard() {
		return classStandard;
	}
	public void setClassStandard(Long classStandard) {
		this.classStandard = classStandard;
	}

	
	
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
}
