package co.vistafoundation.vlearning.user.dto;

import java.io.Serializable;
import java.util.List;

public class TeacherResponseDTO implements Serializable {
	

	private static final long serialVersionUID = 3388410346984408183L;

	private Long idTeacher;
	
	private String teacherName;
	
	private String teacherDesc;
	
	private String userImage;
	
	private String expLevel;
	
	private String primarySubejct;
	
	private Boolean displayInHomepageFlag;
	
	private String teacherHeader;
	
	private String Email;
	
	private String introVideo;
	
	private int joinedStudent;	
	
	
	public int getJoinedStudent() {
		return joinedStudent;
	}

	public void setJoinedStudent(int joinedStudent) {
		this.joinedStudent = joinedStudent;
	}

	public String getIntroVideo() {
		return introVideo;
	}

	public void setIntroVideo(String introVideo) {
		this.introVideo = introVideo;
	}

	private List <TeacherSubjectDTO> teacherSubjects;
	
	
	

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the primarySubejct
	 */
	public String getPrimarySubejct() {
		return primarySubejct;
	}

	/**
	 * @param primarySubejct the primarySubejct to set
	 */
	public void setPrimarySubejct(String primarySubejct) {
		this.primarySubejct = primarySubejct;
	}

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return the teacherDesc
	 */
	public String getTeacherDesc() {
		return teacherDesc;
	}

	/**
	 * @param teacherDesc the teacherDesc to set
	 */
	public void setTeacherDesc(String teacherDesc) {
		this.teacherDesc = teacherDesc;
	}

	/**
	 * @return the userImage
	 */
	public String getUserImage() {
		return userImage;
	}

	/**
	 * @param userImage the userImage to set
	 */
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	/**
	 * @return the expLevel
	 */
	public String getExpLevel() {
		return expLevel;
	}

	/**
	 * @param expLevel the expLevel to set
	 */
	public void setExpLevel(String expLevel) {
		this.expLevel = expLevel;
	}
	
	

	/**
	 * @return the displayInHomepageFlag
	 */
	public Boolean getDisplayInHomepageFlag() {
		return displayInHomepageFlag;
	}

	/**
	 * @param displayInHomepageFlag the displayInHomepageFlag to set
	 */
	public void setDisplayInHomepageFlag(Boolean displayInHomepageFlag) {
		this.displayInHomepageFlag = displayInHomepageFlag;
	}

	/**
	 * @return the teacherSubjects
	 */
	public List<TeacherSubjectDTO> getTeacherSubjects() {
		return teacherSubjects;
	}

	/**
	 * @param teacherSubjects the teacherSubjects to set
	 */
	public void setTeacherSubjects(List<TeacherSubjectDTO> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	/**
	 * @return the teacherHeader
	 */
	public String getTeacherHeader() {
		return teacherHeader;
	}

	/**
	 * @param teacherHeader the teacherHeader to set
	 */
	public void setTeacherHeader(String teacherHeader) {
		this.teacherHeader = teacherHeader;
	}

	
}
