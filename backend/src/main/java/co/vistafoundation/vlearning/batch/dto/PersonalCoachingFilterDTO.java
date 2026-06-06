/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalTime;

/**
 * @author Ahmed
 *
 */
public class PersonalCoachingFilterDTO {

	private Long idState;
	private Long idSyllabus;
	private Long idClassStandard;
	private Long idSubject;
	private Long idProductLine;
	private Long idTeacher;
	private Long idDayofWeekCode;
	private LocalTime fromTime;
	private LocalTime toTime;

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
	 * @return the idProductLine
	 */
	public Long getIdProductLine() {
		return idProductLine;
	}

	/**
	 * @param idProductLine the idProductLine to set
	 */
	public void setIdProductLine(Long idProductLine) {
		this.idProductLine = idProductLine;
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

	/**
	 * @return the idDayofWeekCode
	 */
	public Long getIdDayofWeekCode() {
		return idDayofWeekCode;
	}

	/**
	 * @param idDayofWeekCode the idDayofWeekCode to set
	 */
	public void setIdDayofWeekCode(Long idDayofWeekCode) {
		this.idDayofWeekCode = idDayofWeekCode;
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
	 * @param idState
	 * @param idSyllabus
	 * @param idClassStandard
	 * @param idSubject
	 * @param idProductLine
	 * @param idTeacher
	 * @param idDayofWeekCode
	 * @param fromTime
	 * @param toTime
	 */
	public PersonalCoachingFilterDTO(Long idState, Long idSyllabus, Long idClassStandard, Long idSubject,
			Long idProductLine, Long idTeacher, Long idDayofWeekCode, LocalTime fromTime, LocalTime toTime) {
		super();
		this.idState = idState;
		this.idSyllabus = idSyllabus;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.idProductLine = idProductLine;
		this.idTeacher = idTeacher;
		this.idDayofWeekCode = idDayofWeekCode;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	/**
	 * 
	 */
	public PersonalCoachingFilterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
