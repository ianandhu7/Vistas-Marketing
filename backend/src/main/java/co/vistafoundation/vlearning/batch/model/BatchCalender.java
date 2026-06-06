package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/
@Entity
@Table(name = "BATCH_CALENDAR")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchCalender extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idBATCH_CALENDAR", nullable = false)
	private Long idBatchCalendar;

	@Column(name = "DAY_OF_WEEK", nullable = false)
	private String dayOfWeek;

	@Column(name = "FROM_TIME", nullable = false)
	private String fromTime;

	@Column(name = "TO_TIME", nullable = false)
	private String toTime;
   
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "idBATCH", referencedColumnName = "idBATCH")
	private Batch batch;

	public Long getIdBatchCalendar() {
		return idBatchCalendar;
	}

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

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public BatchCalender(Long idBatchCalendar, String dayOfWeek, String fromTime, String toTime, Batch batch) {
		this.idBatchCalendar = idBatchCalendar;
		this.dayOfWeek = dayOfWeek;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.batch = batch;
	}
	

	public BatchCalender() {

	}

}
