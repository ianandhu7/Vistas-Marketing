/**
 * 
 */
package co.vistafoundation.vlearning.batch.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

/**
 * @author Naveen Kumar
 *
 */
public class ECAPersonalCoachingFilterDTO {
    
	@NotNull
	private Long idSubject;
	@NotNull
	private Long idProductLine;
	@NotNull
	private Long idLevelExtraCurricular;
	private Long idTeacher;
	private Long idDayofWeekCode;
	private LocalTime fromTime;
	private LocalTime toTime;

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
	 * @return the idLevelExtraCurricular
	 */
	public Long getIdLevelExtraCurricular() {
		return idLevelExtraCurricular;
	}

	/**
	 * @param idLevelExtraCurricular the idLevelExtraCurricular to set
	 */
	public void setIdLevelExtraCurricular(Long idLevelExtraCurricular) {
		this.idLevelExtraCurricular = idLevelExtraCurricular;
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

}
