package co.vistafoundation.vlearning.user.dto;

import java.time.LocalTime;

public class TeacherAvailabilityDTO {
	private String dayOfWeek;
	private LocalTime fromTime;
	private LocalTime toTime;
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
	
	
}
