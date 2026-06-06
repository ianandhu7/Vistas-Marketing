/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;
import co.vistafoundation.vlearning.auth.model.User;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "PARENT")
public class Parent extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idPARENT", nullable = false)
	private Long idParent;

	@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt", "password", "roles", "registeredAs",
			"classStandard" })
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idVL_USER", referencedColumnName = "userSurId")
	private User user;

	@Column(name = "idCLASS_STANDARD")
	private Long idClassStandard;

	@Column(name = "idLANGUAGE")
	private Long idLangauage;

	@Column(name = "GENDER", length = 10)
	private String gender;

	@Column(name = "idSYLLABUS", length = 10)
	private Long idSyllabus;

	@Column(name = "idSTATE")
	private Long idState;
	
	@Column(name = "idSTUDENT_MEDIUM")
	private Long idStudentMedium;

	public Parent(User user) {
		super();
		this.user = user;
	}

	public Parent() {

	}

	/**
	 * @param user
	 * @param idState
	 */
	public Parent(User user, Long idState) {
		super();
		this.user = user;
		this.idState = idState;
	}

	/**
	 * @return the idParent
	 */
	public Long getIdParent() {
		return idParent;
	}

	/**
	 * @param idParent the idParent to set
	 */
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
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
	 * @return the idLangauage
	 */
	public Long getIdLangauage() {
		return idLangauage;
	}

	/**
	 * @param idLangauage the idLangauage to set
	 */
	public void setIdLangauage(Long idLangauage) {
		this.idLangauage = idLangauage;
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
	
}
