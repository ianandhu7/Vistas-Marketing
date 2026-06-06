/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@Entity
@Table(name = "SYLLABUS")
public class Syllabus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_SYLLABUS")
	private Long idSyllabus;
	@Column(name="SYLLABUS_NAME")
	private String syllabusName;

	public Syllabus(Long idSyllabus, String syllabusName) {
		super();
		this.idSyllabus = idSyllabus;
		this.syllabusName = syllabusName;
	}

	public Syllabus() {

	}

	public Long getIdSyllabus() {
		return idSyllabus;
	}

	public void setIdSyllabus(Long idSyllabus) {
		this.idSyllabus = idSyllabus;
	}

	public String getSyllabusName() {
		return syllabusName;
	}

	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}

}
