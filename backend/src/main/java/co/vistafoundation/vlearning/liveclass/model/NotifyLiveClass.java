/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author Ahmed 
 * updated by @author Naveen Kumar
 *
 */
@Entity
@Table(name = "NOTIFY_LIVE_CLASS",uniqueConstraints= @UniqueConstraint(columnNames={"idLIVE_CLASS", "idVL_USER"}))
public class NotifyLiveClass extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idNOTIFY_LIVE_CLASS")
	private Long idNotifyLiveClass;

	@Column(name = "idLIVE_CLASS")
	private Long idLiveClass;

	@Column(name = "idVL_USER")
	private Long idVlUser;

	@Column(name = "NOTIFIED_FLAG")
	private boolean notifiedFlag;

	/**
	 * @return the idNotifyLiveClass
	 */
	public Long getIdNotifyLiveClass() {
		return idNotifyLiveClass;
	}

	/**
	 * @param idNotifyLiveClass the idNotifyLiveClass to set
	 */
	public void setIdNotifyLiveClass(Long idNotifyLiveClass) {
		this.idNotifyLiveClass = idNotifyLiveClass;
	}

	/**
	 * @return the idLiveClass
	 */
	public Long getIdLiveClass() {
		return idLiveClass;
	}

	/**
	 * @param idLiveClass the idLiveClass to set
	 */
	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
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
	 * @param idLiveClass
	 * @param idVlUser
	 * @param notifiedFlag
	 */
	public NotifyLiveClass(Long idLiveClass, Long idVlUser, boolean notifiedFlag) {
		super();
		this.idLiveClass = idLiveClass;
		this.idVlUser = idVlUser;
		this.notifiedFlag = notifiedFlag;
	}

	/**
	 * @return the notifiedFlag
	 */
	public boolean isNotifiedFlag() {
		return notifiedFlag;
	}

	/**
	 * @param notifiedFlag the notifiedFlag to set
	 */
	public void setNotifiedFlag(boolean notifiedFlag) {
		this.notifiedFlag = notifiedFlag;
	}

	/**
	 * 
	 */
	public NotifyLiveClass() {
		super();
		// TODO Auto-generated constructor stub
	}

}
