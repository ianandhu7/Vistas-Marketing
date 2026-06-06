package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/

/*
 * @updated By Shaikh Ahmed Reza
 */

@Entity
@Table(name = "BATCH_STUDENT_ATTENDANCE")
public class BatchStudentAttendance extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idBATCH_STUDENT_ATTENDANCE", nullable = false)
	private Long idBatchStudentAttendance;

	@Column(name = "idBatch")
	private Long idBatch;

	@Column(name = "batch_run_date")
	private String batchRunDate;

	@Column(name = "ABSENT_PRESENT_FLAG")
	private Boolean absentPresentFlag;

	@Column(name = "idStudentSubscr")
	private Long idStudentSubscr;

	public Long getIdBatchStudentAttendance() {
		return idBatchStudentAttendance;
	}

	public void setIdBatchStudentAttendance(Long idBatchStudentAttendance) {
		this.idBatchStudentAttendance = idBatchStudentAttendance;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public String getBatchRunDate() {
		return batchRunDate;
	}

	public void setBatchRunDate(String batchRunDate) {
		this.batchRunDate = batchRunDate;
	}

	public Boolean getAbsentPresentFlag() {
		return absentPresentFlag;
	}

	public void setAbsentPresentFlag(Boolean absentPresentFlag) {
		this.absentPresentFlag = absentPresentFlag;
	}

	public Long getIdStudentSubscr() {
		return idStudentSubscr;
	}

	public void setIdStudentSubscr(Long idStudentSubscr) {
		this.idStudentSubscr = idStudentSubscr;
	}

	public BatchStudentAttendance(Long idBatchStudentAttendance, Long idBatch, String batchRunDate,
			Boolean absentPresentFlag, Long idStudentSubscr) {
		super();
		this.idBatchStudentAttendance = idBatchStudentAttendance;
		this.idBatch = idBatch;
		this.batchRunDate = batchRunDate;
		this.absentPresentFlag = absentPresentFlag;
		this.idStudentSubscr = idStudentSubscr;
	}

	public BatchStudentAttendance() {

	}

}
