/**
 * Test
 */
package co.vistafoundation.vlearning.user.dto;

/**
 * @author Abdul Elahi
 */
public class LiveUsersDataResponseDTO {
	
	private Long userSurId;
	
	private String name;
	
	private String classStandard;
	
	private String userName;

	private String syllabus;

	private String timing;
	
	private String userType;
	
	private String status;
	
	private String school;

	private String deviceLocation;
	
	private String deviceType;
	
//	private int mobileActiveSession, mobileInActiveSession, webActiveSession, webInActiveSession;

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the classStandard
	 */
	public String getClassStandard() {
		return classStandard;
	}

	/**
	 * @param classStandard the classStandard to set
	 */
	public void setClassStandard(String classStandard) {
		this.classStandard = classStandard;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return userName;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.userName = phone;
	}

	/**
	 * @return the syllabus
	 */
	public String getSyllabus() {
		return syllabus;
	}

	/**
	 * @param syllabus the syllabus to set
	 */
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	/**
	 * @return the timing
	 */
	public String getTiming() {
		return timing;
	}

	/**
	 * @param timing the timing to set
	 */
	public void setTiming(String timing) {
		this.timing = timing;
	}

	/**
	 * @return the user type
	 */
	public String getuserType() {
		return userType;
	}

	/**
	 * @param user type the user type to set
	 */
	public void setuserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the deviceLocation
	 */
	public String getdeviceLocation() {
		return deviceLocation;
	}

	/**
	 * @param deviceLocation the deviceLocation to set
	 */
	public void setdeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	
	

	/**
	 * @return the deviceType
	 */
	public String getdeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setdeviceType(String deviceType) {
		this.deviceType = deviceType;
	}



	/**
	 * @param userSurId
	 * @param name
	 * @param classStandard
	 * @param phone
	 * @param syllabus
	 * @param timing
	 * @param userType
	 * @param status
	 * @param school
	 * @param deviceLocation
	 * @param deviceType
	 */
	public LiveUsersDataResponseDTO(Long userSurId, String name, String classStandard, String phone, String syllabus,
			String timing, String userType, String status, String school, String deviceLocation, String deviceType) {
		super();
		this.userSurId = userSurId;
		this.name = name;
		this.classStandard = classStandard;
		this.userName = phone;
		this.syllabus = syllabus;
		this.timing = timing;
		this.userType = userType;
		this.status = status;
		this.school = school;
		this.deviceLocation = deviceLocation;
		this.deviceType = deviceType;
	}

	/**
	 * 
	 */
	public LiveUsersDataResponseDTO() {
		super();
	}

	@Override
	public String toString() {
		return "LiveUsersDataResponseDTO [userSurId=" + userSurId + ", name=" + name + ", classStandard="
				+ classStandard + ", phone=" + userName + ", syllabus=" + syllabus + ", timing=" + timing + ", userType="
				+ deviceType + "]";
	}
	
	
}
