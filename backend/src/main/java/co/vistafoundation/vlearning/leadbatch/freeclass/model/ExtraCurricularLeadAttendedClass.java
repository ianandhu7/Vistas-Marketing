/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Ahmed
 *
 */
@Entity
@Table(name = "EXTRA_CURRICULAR_LEAD_ATTENDED_CLASS")
public class ExtraCurricularLeadAttendedClass extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEXTRA_CURRICULAR_LEAD_ATTENDED_CLASS")
	private Long idExtraCurricularLeadAttendedClass;

	@Column(name = "idLEAD_BATCH_LOG_EXTRA_CURRICULAR")
	private Long idLeadBatchLogExtraCurricular;

	@Column(name = "idVlUser")
	private Long idVlUser;

	/**
	 * @return the idExtraCurricularLeadAttendedClass
	 */
	public Long getIdExtraCurricularLeadAttendedClass() {
		return idExtraCurricularLeadAttendedClass;
	}

	/**
	 * @param idExtraCurricularLeadAttendedClass the
	 *                                           idExtraCurricularLeadAttendedClass
	 *                                           to set
	 */
	public void setIdExtraCurricularLeadAttendedClass(Long idExtraCurricularLeadAttendedClass) {
		this.idExtraCurricularLeadAttendedClass = idExtraCurricularLeadAttendedClass;
	}

	/**
	 * @return the idLeadBatchLogExtraCurricular
	 */
	public Long getIdLeadBatchLogExtraCurricular() {
		return idLeadBatchLogExtraCurricular;
	}

	/**
	 * @param idLeadBatchLogExtraCurricular the idLeadBatchLogExtraCurricular to set
	 */
	public void setIdLeadBatchLogExtraCurricular(Long idLeadBatchLogExtraCurricular) {
		this.idLeadBatchLogExtraCurricular = idLeadBatchLogExtraCurricular;
	}

	/**
	 * @return the idVlUser
	 */
	public Long getIdVlUser() {
		return idVlUser;
	}

	/**
	 * @param idVlUser the idVlUser to set
	 */
	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	/**
	 * @param idExtraCurricularLeadAttendedClass
	 * @param idLeadBatchLogExtraCurricular
	 * @param idVlUser
	 */
	public ExtraCurricularLeadAttendedClass(Long idExtraCurricularLeadAttendedClass, Long idLeadBatchLogExtraCurricular,
			Long idVlUser) {
		super();
		this.idExtraCurricularLeadAttendedClass = idExtraCurricularLeadAttendedClass;
		this.idLeadBatchLogExtraCurricular = idLeadBatchLogExtraCurricular;
		this.idVlUser = idVlUser;
	}

	/**
	 * 
	 */
	public ExtraCurricularLeadAttendedClass() {
		super();
		// TODO Auto-generated constructor stub
	}

}
