/**
 * 
 */
package co.vistafoundation.vlearning.user.dto;

import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author vk
 *
 */
public class StudentDetailsDTO {
	
	private Long idStudent;
	
	private User user;
	
	private Long idClassStandard;
	
	private String classStandadName;
	
	private String gender;
	
	

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @return the classStandadName
	 */
	public String getClassStandadName() {
		return classStandadName;
	}

	/**
	 * @param classStandadName the classStandadName to set
	 */
	public void setClassStandadName(String classStandadName) {
		this.classStandadName = classStandadName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
