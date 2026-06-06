package co.vistafoundation.vlearning.classes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar, vk
 * 
 **/
@Entity
@Table(name = "CLASS_STANDARD")
@JsonIgnoreProperties({"updatedBy", "createdBy","createdAt","updatedAt"})
public class ClassStandard extends UserDateAudit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idclass_standard", nullable = false)
	private Long idClassStandard;
	
	@Column(name = "CLASS_STANDARD_NAME", length = 45)
	private String classStandadName;

	/**
	 * @param idClassStandard
	 * @param classStandadName
	 */
	public ClassStandard(Long idClassStandard, String classStandadName) {
		super();
		this.classStandadName = classStandadName;
	}

	/**
	 * 
	 */
	public ClassStandard() {
		super();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idClassStandard
	 */
	public Long getIdClassStandard() {
		return idClassStandard;
	}

	/**
	 * @return the classStandadName
	 */
	public String getClassStandadName() {
		return classStandadName;
	}

	/**
	 * @param idClassStandard the idClassStandard to set
	 */
	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	/**
	 * @param classStandadName the classStandadName to set
	 */
	public void setClassStandadName(String classStandadName) {
		this.classStandadName = classStandadName;
	}
	
}
