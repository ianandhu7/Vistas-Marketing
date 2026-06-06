package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author NaveenKumar
 * 
 **/

@Entity
@Table(name = "STUDENT")
public class Student extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSTUDENT", nullable = false)
	private Long idStudent;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPARENT", referencedColumnName = "idPARENT")
	private Parent parent;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "roles", "registeredAs",
			"classStandard", "password" })
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId")
	private User user;

	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "idLANGUAGE")
	private Long idLangauage;

	@Column(name = "GENDER", length = 10)
	private String gender;

	@Column(name = "idSYLLABUS")
	private Long idSyllabus;
	
	@Column(name = "idSTUDENT_MEDIUM")
	private Long idStudentMedium;

	@Column(name = "idSTATE")
	private Long idState;
	
	@Column(name = "school_name")
	private String schoolName;
	
	@Column(name = "remarks")
	private String remarks;
	
	
	/**
	 * @author added by vk
	 */
	@Column(name = "isProfile_Edited", columnDefinition="BOOLEAN DEFAULT false")
	private Boolean isProfileEdited = false;

	
	@Column(name ="ID_REFERRALCODE")
	private Long idReferralCode;
	
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	* @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @return the idLangauage
	 */
	public Long getIdLangauage() {
		return idLangauage;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * @param idParent the idParent to set
	 */

	/**
	 * @param idVlUser the idVlUser to set
	 */

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param idLangauage the idLangauage to set
	 */
	public void setIdLangauage(Long idLangauage) {
		this.idLangauage = idLangauage;
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
	 * @return the isProfileEdited
	 */
	public Boolean getIsProfileEdited() {
		return isProfileEdited;
	}

	/**
	 * @param isProfileEdited the isProfileEdited to set
	 */
	public void setIsProfileEdited(Boolean isProfileEdited) {
		this.isProfileEdited = isProfileEdited;
	}

	/**
	 * @param parent
	 * @param user
	 * @param idClassStandard
	 * @param idLangauage
	 * @param gender
	 * @param idSyllabus
	 * @param idStudentMedium
	 * @param idState
	 */
	public Student(Parent parent, User user, Long idClassStandard, Long idLangauage, String gender, Long idSyllabus,
			Long idStudentMedium, Long idState, Boolean isProfileEdited) {
		super();
		this.parent = parent;
		this.user = user;
		this.idClassStandard = idClassStandard;
		this.idLangauage = idLangauage;
		this.gender = gender;
		this.idSyllabus = idSyllabus;
		this.idStudentMedium = idStudentMedium;
		this.idState = idState;
		this.isProfileEdited = isProfileEdited;
	}

	public Student() {
		super();
	}

	/**
	 * @return the idReferralCode
	 */
	public Long getIdReferralCode() {
		return idReferralCode;
	}

	/**
	 * @param idReferralCode the idReferralCode to set
	 */
	public void setIdReferralCode(Long idReferralCode) {
		this.idReferralCode = idReferralCode;
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

	/**
	 * @param parent
	 * @param user
	 * @param idClassStandard
	 * @param idLangauage
	 * @param gender
	 * @param idSyllabus
	 * @param idStudentMedium
	 * @param idState
	 * @param isProfileEdited
	 * @param idReferralCode
	 */
	public Student(Parent parent, User user, Long idClassStandard, Long idLangauage, String gender, Long idSyllabus,
			Long idStudentMedium, Long idState, Boolean isProfileEdited, Long idReferralCode) {
		super();
		this.parent = parent;
		this.user = user;
		this.idClassStandard = idClassStandard;
		this.idLangauage = idLangauage;
		this.gender = gender;
		this.idSyllabus = idSyllabus;
		this.idStudentMedium = idStudentMedium;
		this.idState = idState;
		this.isProfileEdited = isProfileEdited;
		this.idReferralCode = idReferralCode;
	}

	/**
	 * @return the referralCode
	 */
	
	
}
