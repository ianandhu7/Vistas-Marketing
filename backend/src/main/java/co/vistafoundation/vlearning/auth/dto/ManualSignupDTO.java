package co.vistafoundation.vlearning.auth.dto;

/*
 * @author Abdul Elahi
 * created to accept the fileds to create manual signup
 */
public class ManualSignupDTO {
	
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
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}
	public String getClassStandard() {
		return classStandard;
	}
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getSecondaryLanguage() {
		return secondaryLanguage;
	}
	public void setSecondaryLanguage(String secondaryLanguage) {
		this.secondaryLanguage = secondaryLanguage;
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
	public ManualSignupDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ManualSignupDTO(String name, String email, String mobileNumber, String password, String syllabus,
			String classStandard, String state, String medium, String secondaryLanguage, String schoolName,
			String remarks) {
		super();
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.syllabus = syllabus;
		this.classStandard = classStandard;
		this.state = state;
		this.medium = medium;
		this.secondaryLanguage = secondaryLanguage;
		this.schoolName = schoolName;
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "ManualSignupDTO [name=" + name + ", email=" + email + ", mobileNumber=" + mobileNumber + ", password="
				+ password + ", syllabus=" + syllabus + ", classStandard=" + classStandard + ", state=" + state
				+ ", medium=" + medium + ", secondaryLanguage=" + secondaryLanguage + ", schoolName=" + schoolName
				+ ", remarks=" + remarks + "]";
	}
	
	
	

}
