package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar, vk
 * 
 **/

@Entity
@Table(name = "TEACHER_FEEDBACK")
public class TeacherFeedback extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTEACHER_FEEDBACK", nullable = false)
	private Long idTeacherFeedback;

	@Column(name = "idTEACHER", nullable = false)
	private Long idTeacher;
    
	@Min(0)
	@Max(5)
	@Column(name = "RATING")
	private int rating;

	/**
	 * @param idTeacher
	 * @param rating
	 */
	public TeacherFeedback(Long idTeacherFeedback, Long idTeacher, int rating) {
		super();
		this.idTeacher = idTeacher;
		this.rating = rating;
	}

	/**
	 * 
	 */
	public TeacherFeedback() {
		super();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idTeacherFeedback
	 */
	public Long getIdTeacherFeedback() {
		return idTeacherFeedback;
	}

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param idTeacherFeedback the idTeacherFeedback to set
	 */
	public void setIdTeacherFeedback(Long idTeacherFeedback) {
		this.idTeacherFeedback = idTeacherFeedback;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

}
