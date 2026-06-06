/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

/**
 * @author Ahmed
 *
 */
public class TelecallerExtracurricularDTO {

	private String studentName;
	private String studentEmail;
	private String mobileNumber;
	private Long idVlUser;

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the studentEmail
	 */
	public String getStudentEmail() {
		return studentEmail;
	}

	/**
	 * @param studentEmail the studentEmail to set
	 */
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @param studentName
	 * @param studentEmail
	 * @param mobileNumber
	 * @param idVlUser
	 */
	public TelecallerExtracurricularDTO(String studentName, String studentEmail, String mobileNumber, Long idVlUser) {
		super();
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.mobileNumber = mobileNumber;
		this.idVlUser = idVlUser;
	}

	/**
	 * 
	 */
	public TelecallerExtracurricularDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
