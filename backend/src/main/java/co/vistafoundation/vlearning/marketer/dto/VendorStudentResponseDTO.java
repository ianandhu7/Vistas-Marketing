/**
 * 
 */
package co.vistafoundation.vlearning.marketer.dto;

import java.time.LocalDate;

/**
 * @author NAVEEN
 *
 */
public class VendorStudentResponseDTO {

	private Long idStudent;

	private Long idClassStandard;

	private Long idSyllabus;

	private Long idStudentMedium;

	private Long idState;

	private String firstName;

	private String email;

	private String mobileNumber;

	private LocalDate onBoardedDate;

	private String vendorPaymentStatus;

	private LocalDate paymentDate;

	private Float paymentAmount;

	private String classStandardName;

	private String syllabus;

	private String state;

	private String medium;

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
	 * @return the idSyllabus
	 */
	public Long getIdSyllabus() {
		return idSyllabus;
	}

	/**
	 * @param idSyllabus the idSyllabus to set
	 */
	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	/**
	 * @return the idStudentMedium
	 */
	public Long getIdStudentMedium() {
		return idStudentMedium;
	}

	/**
	 * @param idStudentMedium the idStudentMedium to set
	 */
	public void setIdStudentMedium(Long idStudentMedium) {
		this.idStudentMedium = idStudentMedium;
	}

	/**
	 * @return the idState
	 */
	public Long getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(Long idState) {
		this.idState = idState;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the onBoardedDate
	 */
	public LocalDate getOnBoardedDate() {
		return onBoardedDate;
	}

	/**
	 * @param onBoardedDate the onBoardedDate to set
	 */
	public void setOnBoardedDate(LocalDate onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	/**
	 * @return the vendorPaymentStatus
	 */
	public String getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	/**
	 * @param vendorPaymentStatus the vendorPaymentStatus to set
	 */
	public void setVendorPaymentStatus(String vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
	}

	/**
	 * @return the paymentDate
	 */
	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the paymentAmount
	 */
	public float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the classStandardName
	 */
	public String getClassStandardName() {
		return classStandardName;
	}

	/**
	 * @param classStandardName the classStandardName to set
	 */
	public void setClassStandardName(String classStandardName) {
		this.classStandardName = classStandardName;
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param medium the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @param idStudent
	 * @param idClassStandard
	 * @param gender
	 * @param idSyllabus
	 * @param idStudentMedium
	 * @param idState
	 * @param firstName
	 * @param email
	 * @param mobileNumber
	 * @param onBoardedDate
	 * @param vendorPaymentStatus
	 * @param paymentDate
	 * @param paymentAmount
	 * @param classStandardName
	 * @param syllabus
	 * @param state
	 * @param medium
	 */
	public VendorStudentResponseDTO(Long idStudent, Long idClassStandard, Long idSyllabus, Long idStudentMedium,
			Long idState, String firstName, String email, String mobileNumber, LocalDate onBoardedDate,
			String vendorPaymentStatus, LocalDate paymentDate, Float paymentAmount, String classStandardName,
			String syllabus, String state, String medium) {
		super();
		this.idStudent = idStudent;
		this.idClassStandard = idClassStandard;
		this.idSyllabus = idSyllabus;
		this.idStudentMedium = idStudentMedium;
		this.idState = idState;
		this.firstName = firstName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.onBoardedDate = onBoardedDate;
		this.vendorPaymentStatus = vendorPaymentStatus;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;
		this.classStandardName = classStandardName;
		this.syllabus = syllabus;
		this.state = state;
		this.medium = medium;
	}

	/**
	 * 
	 */
	public VendorStudentResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
