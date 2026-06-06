package co.vistafoundation.vlearning.batch.dto;

import javax.validation.constraints.NotNull;

public class BatchScheduleDTO {
	
	private Long idBatchCalendar;
    
	@NotNull
	private String dayOfWeek;
	
	@NotNull
	private String fromTime;
	
	@NotNull
	private String toTime;
	
	/**
	 * @return the idBatchCalendar
	 */
	public Long getIdBatchCalendar() {
		return idBatchCalendar;
	}
	/**
	 * @param idBatchCalendar the idBatchCalendar to set
	 */
	public void setIdBatchCalendar(Long idBatchCalendar) {
		this.idBatchCalendar = idBatchCalendar;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	
	
}
