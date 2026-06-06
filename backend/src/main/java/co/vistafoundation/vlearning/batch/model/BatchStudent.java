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

@Entity
@Table(name = "BATCH_STUDENT")
public class BatchStudent extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idBATCH_STUDENT", nullable = false)
	private Long idBatchStudent;

	@Column(name = "idBATCH", nullable = false)
	private Long idBatch;

	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscription;

	public Long getIdBatchStudent() {
		return idBatchStudent;
	}

	public void setIdBatchStudent(Long idBatchStudent) {
		this.idBatchStudent = idBatchStudent;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	public BatchStudent(Long idBatch, Long idStudentSubscription) {
		super();
		this.idBatch = idBatch;
		this.idStudentSubscription = idStudentSubscription;
	}

	public BatchStudent() {
	}

}
