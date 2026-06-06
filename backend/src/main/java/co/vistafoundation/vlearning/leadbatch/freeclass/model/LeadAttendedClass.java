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

/**
 * @author vk
 *
 */
@Entity
@Table(name = "LEAD_ATTENDED_CLASS")
public class LeadAttendedClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLEAD_ATTENDED_CLASS")
	private Long idLeadAttendedClass;

	@Column(name = "idLEAD_BATCH_LOG")
	private Long idLeadBatchLog;

	@Column(name = "idVlUser")
	private Long idVlUser;

	/**
	 * 
	 */
	public LeadAttendedClass() {
		super();
	}

	/**
	 * @param idLeadAttendedClass
	 * @param idLeadBatchLog
	 * @param idVlUser
	 */
	public LeadAttendedClass(Long idLeadAttendedClass, Long idLeadBatchLog, Long idVlUser) {
		super();
		this.idLeadAttendedClass = idLeadAttendedClass;
		this.idLeadBatchLog = idLeadBatchLog;
		this.idVlUser = idVlUser;
	}

	/**
	 * @return the idLeadAttendedClass
	 */
	public Long getIdLeadAttendedClass() {
		return idLeadAttendedClass;
	}

	/**
	 * @param idLeadAttendedClass the idLeadAttendedClass to set
	 */
	public void setIdLeadAttendedClass(Long idLeadAttendedClass) {
		this.idLeadAttendedClass = idLeadAttendedClass;
	}

	/**
	 * @return the idLeadBatchLog
	 */
	public Long getIdLeadBatchLog() {
		return idLeadBatchLog;
	}

	/**
	 * @param idLeadBatchLog the idLeadBatchLog to set
	 */
	public void setIdLeadBatchLog(Long idLeadBatchLog) {
		this.idLeadBatchLog = idLeadBatchLog;
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

}
