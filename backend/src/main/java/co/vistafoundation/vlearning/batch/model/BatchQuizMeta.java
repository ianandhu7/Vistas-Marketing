package co.vistafoundation.vlearning.batch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;


/**
 * @author NaveenKumar
 * 
 **/
@Entity
@Table(name = "BATCH_QUIZ_META")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class BatchQuizMeta extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idBATCH_QUIZ_META")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBatchQuizMeta;

	@Column(name = "idTEACHER", nullable = false)
	private Long idTeacher;

	@Column(name = "idCLASS_STANDARD", nullable = false)
	private Long idClassStandard;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "BATCH_QUIZ_NAME", length = 45)
	private String batchQuizName;

	public Long getIdBatchQuizMeta() {
		return idBatchQuizMeta;
	}

	public void setIdBatchQuizMeta(Long idBatchQuizMeta) {
		this.idBatchQuizMeta = idBatchQuizMeta;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public Long getIdClassStandard() {
		return idClassStandard;
	}

	public void setIdClassStandard(Long idClassStandard) {
		this.idClassStandard = idClassStandard;
	}

	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	public String getBatchQuizName() {
		return batchQuizName;
	}

	public void setBatchQuizName(String batchQuizName) {
		this.batchQuizName = batchQuizName;
	}

	public BatchQuizMeta(Long idTeacher, Long idClassStandard, Long idSubject, String batchQuizName) {
		super();
		this.idTeacher = idTeacher;
		this.idClassStandard = idClassStandard;
		this.idSubject = idSubject;
		this.batchQuizName = batchQuizName;
	}

	public BatchQuizMeta() {
	}

}
