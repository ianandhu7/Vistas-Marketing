/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STUDENT_MEDIUM")
public class StudentMedium implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_MEDIUM")
	private Long idStudentMedium;
	
	@Column(name = "MEDIUM")
	private String medium;

	/**
	 * 
	 */
	public StudentMedium() {
		super();
	}

	/**
	 * @param medium
	 */
	public StudentMedium(String medium) {
		super();
		this.medium = medium;
	}

	/**
	 * @return the idStudentMedium
	 */
	public Long getIdStudentMedium() {
		return idStudentMedium;
	}

	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param idStudentMedium the idStudentMedium to set
	 */
	public void setIdStudentMedium(Long idStudentMedium) {
		this.idStudentMedium = idStudentMedium;
	}

	/**
	 * @param medium the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}
	
}
