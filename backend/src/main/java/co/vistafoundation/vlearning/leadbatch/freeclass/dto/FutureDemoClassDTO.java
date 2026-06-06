/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Ahmed
 *
 */
public class FutureDemoClassDTO {

	private String teacherName;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LocalDate classScheduledDate;
	private String description;
	private Boolean classConductedFlag;

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
	 * @return the classScheduledDate
	 */
	public LocalDate getClassScheduledDate() {
		return classScheduledDate;
	}

	/**
	 * @param classScheduledDate the classScheduledDate to set
	 */
	public void setClassScheduledDate(LocalDate classScheduledDate) {
		this.classScheduledDate = classScheduledDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the classConductedFlag
	 */
	public Boolean getClassConductedFlag() {
		return classConductedFlag;
	}

	/**
	 * @param classConductedFlag the classConductedFlag to set
	 */
	public void setClassConductedFlag(Boolean classConductedFlag) {
		this.classConductedFlag = classConductedFlag;
	}

	/**
	 * @param teacherName
	 * @param fromTime
	 * @param toTime
	 * @param classScheduledDate
	 * @param description
	 * @param classConductedFlag
	 */
	public FutureDemoClassDTO(String teacherName, LocalTime fromTime, LocalTime toTime, LocalDate classScheduledDate,
			String description, Boolean classConductedFlag) {
		super();
		this.teacherName = teacherName;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.classScheduledDate = classScheduledDate;
		this.description = description;
		this.classConductedFlag = classConductedFlag;
	}

	/**
	 * 
	 */
	public FutureDemoClassDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
