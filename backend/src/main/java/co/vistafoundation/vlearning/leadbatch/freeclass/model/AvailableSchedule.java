package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AVAILABLE_SCHEDULE")
public class AvailableSchedule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idAVAILABLE_SCHEDULE")
	private Long idAvailableSchedule;
	
	@Column(name="DAY_OF_WEEK", length=45)
	private String dayOfWeek;
	
	@Column(name="FROM_TIME")
	private LocalTime fromTime;
	
	@Column(name="TO_TIME")
	private LocalTime toTime;
	
	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}

	public Long getIdAvailableSchedule() {
		return idAvailableSchedule;
	}

	public void setIdAvailableSchedule(Long idAvailableSchedule) {
		this.idAvailableSchedule = idAvailableSchedule;
	}	

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}


	public AvailableSchedule(String dayOfWeek, LocalTime fromTime, LocalTime toTime) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public AvailableSchedule() {
		
	}
}
