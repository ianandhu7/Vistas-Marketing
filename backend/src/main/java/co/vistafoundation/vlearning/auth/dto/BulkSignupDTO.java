package co.vistafoundation.vlearning.auth.dto;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import co.vistafoundation.vlearning.auth.model.Role;

public class BulkSignupDTO {

	private String name;
	private String email;
	private String mobileNumber;
	private String password;
	private String syllabus;
	private String classStandard;
	private String state;
	private String medium;
	private String secondaryLanguage;
	private String schoolName;
	private String remarks;
	private Boolean isValid = true;
	private String message;

	public BulkSignupDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String Syllabus) {
		this.syllabus = Syllabus;
	}

	public String getClassStandard() {
		return classStandard;
	}

	public void setClassStandard(String ClassStandard) {
		this.classStandard = ClassStandard;
	}

	public String getState() {
		return state;
	}

	public void setState(String State) {
		this.state = State;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String Medium) {
		this.medium = Medium;
	}

	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}

	public void setSecondaryLanguage(String SecondaryLanguage) {
		this.secondaryLanguage = SecondaryLanguage;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BulkSignupDTO(String name, String email, String mobileNumber, String password, String Syllabus,
			String ClassStandard, String State, String Medium, String SecondaryLanguage, String schoolName,
			String remarks) {
		super();
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.syllabus = Syllabus;
		this.classStandard = ClassStandard;
		this.state = State;
		this.medium = Medium;
		this.secondaryLanguage = SecondaryLanguage;
		this.schoolName = schoolName;
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "BulkSignupDTO [name=" + name + ", email=" + email + ", mobileNumber=" + mobileNumber + ", password="
				+ password + ", Syllabus=" +syllabus + ", ClassStandard=" + classStandard + ", State="
				+ state + ", Medium=" + medium + ", SecondaryLanguage=" + secondaryLanguage + ", schoolName="
				+ schoolName + ", remarks=" + remarks + ", isValid=" + isValid + ", message=" + message + "]";
	}
	
	


	

}
