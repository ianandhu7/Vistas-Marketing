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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Naveen Kumar
 *
 */
@Entity
@Table(name = "TEACHER_SUBJECT",uniqueConstraints= @UniqueConstraint(columnNames={"idTEACHER", "idSUBJECT","idSYLLABUS"}))
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class TeacherSubject extends UserDateAudit implements Serializable

{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTEACHER_SUBJECT", nullable = false)
	private Long idTeacherSubject;

	@Column(name = "PROFICIENCY", nullable = false)
	private String proficiency;

	@ManyToOne
	@JoinColumn(name = "idTEACHER", referencedColumnName = "idTEACHER", nullable = false)
	private Teacher teacher;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "idSYLLABUS", nullable = false)
	private Long idSyllabus;
	
	@Column(name = "SUB_INTRO_VIDEO" , length=500)
	private String subIntroVideo;
	


	/**
	 * @return the idTeacherSubject
	 */
	public Long getIdTeacherSubject() {
		return idTeacherSubject;
	}

	public String getSubIntroVideo() {
		return subIntroVideo;
	}

	public void setSubIntroVideo(String subIntroVideo) {
		this.subIntroVideo = subIntroVideo;
	}

	/**
	 * @param idTeacherSubject the idTeacherSubject to set
	 */
	public void setIdTeacherSubject(Long idTeacherSubject) {
		this.idTeacherSubject = idTeacherSubject;
	}

	/**
	 * @return the proficiency
	 */
	public String getProficiency() {
		return proficiency;
	}

	/**
	 * @param proficiency the proficiency to set
	 */
	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}

	/**
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
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
	 * @param proficiency
	 * @param teacher
	 * @param idSubject
	 * @param idSyllabus
	 */
		
	
	/**
	 * 
	 */
	public TeacherSubject() {
		super();
	}

	public TeacherSubject(Long idTeacherSubject, String proficiency, Teacher teacher, Long idSubject, Long idSyllabus,
			String subIntroVideo) {
		super();
		this.idTeacherSubject = idTeacherSubject;
		this.proficiency = proficiency;
		this.teacher = teacher;
		this.idSubject = idSubject;
		this.idSyllabus = idSyllabus;
		this.subIntroVideo = subIntroVideo;
	}

	
	

}
