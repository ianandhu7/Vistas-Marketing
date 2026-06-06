package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/***
 * 
 * @author meghana
 *
 */

@Entity
@Table(name = "BatchQuizAssignment")
public class BatchQuizAssignment extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idBATCH_QUIZ_ASSIGNMENT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBatchQuizAssignment;

	@Column(name = "BATCH_QUIZ_NAME", length = 45)
	private String batchQuizName;

	@Column(name = "QUIZ_DATE", nullable = false)
	private Instant quizDate;

	@Column(name = "idBATCH_QUIZ_META")
	private Long idBatchQuizMeta;

	@Column(name = "idBATCH", nullable = false)
	private Long idBatch;

	public Long getIdBatchQuizAssignment() {
		return idBatchQuizAssignment;
	}

	public void setIdBatchQuizAssignment(Long idBatchQuizAssignment) {
		this.idBatchQuizAssignment = idBatchQuizAssignment;
	}

	public String getBatchQuizName() {
		return batchQuizName;
	}

	public void setBatchQuizName(String batchQuizName) {
		this.batchQuizName = batchQuizName;
	}

	public Instant getQuizDate() {
		return quizDate;
	}

	public void setQuizDate(Instant quizDate) {
		this.quizDate = quizDate;
	}

	public Long getIdBatchQuizMeta() {
		return idBatchQuizMeta;
	}

	public void setIdBatchQuizMeta(Long idBatchQuizMeta) {
		this.idBatchQuizMeta = idBatchQuizMeta;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}
	
	

	public BatchQuizAssignment(String batchQuizName, Instant quizDate, Long idBatchQuizMeta, Long idBatch) {
		super();
		this.batchQuizName = batchQuizName;
		this.quizDate = quizDate;
		this.idBatchQuizMeta = idBatchQuizMeta;
		this.idBatch = idBatch;
	}

	public BatchQuizAssignment() {

	}

}
