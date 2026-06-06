/**
 * 
 */
package co.vistafoundation.vlearning.user.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Sarfaraz
 *
 */
@Entity
@Table(name = "TEACHER_AVAILABILITY")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class TeacherAvailability extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTEACHER_AVAILABILITY", nullable = false)
	private Long idTeacherAvailability;

	@Column(name = "DAY_OF_WEEK")
	private String dayOfWeek;

	@Column(name = "FROM_TIME")
	private LocalTime fromTime;

	@Column(name = "TO_TIME")
	private LocalTime toTime;

	@Column(name = "idTEACHER")
	private Long idTeacher;

	public TeacherAvailability() {
		super();
	}

	/**
	 * @param dayOfWeek
	 * @param fromTime
	 * @param toTime
	 * @param teacher
	 * @param idTeacher
	 */
	public TeacherAvailability(String dayOfWeek, LocalTime fromTime, LocalTime toTime, Long idTeacher) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.idTeacher = idTeacher;
	}

	/**
	 * @return the idTeacherAvailability
	 */
	public Long getIdTeacherAvailability() {
		return idTeacherAvailability;
	}

	/**
	 * @param idTeacherAvailability the idTeacherAvailability to set
	 */
	public void setIdTeacherAvailability(Long idTeacherAvailability) {
		this.idTeacherAvailability = idTeacherAvailability;
	}

	/**
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the fromTime
	 */
	public LocalTime getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public LocalTime getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the idTeacher
	 */
	public Long getIdTeacher() {
		return idTeacher;
	}

	/**
	 * @param idTeacher the idTeacher to set
	 */
	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

}