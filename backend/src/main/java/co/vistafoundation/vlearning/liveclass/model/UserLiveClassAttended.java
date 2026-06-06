package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * 
 * @author Sarfaraz
 *
 */

@Entity
@Table (name ="USER_LIVE_CLASS_ATTENDED")
public class UserLiveClassAttended extends UserDateAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idUSER_LIVE_CLASS_ATTENDED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUserLiveClassAttended;
	
	@Column(name="idLIVE_CLASS")
	private Long idLiveClass;
	
	@Column(name="idVL_USER")
	private Long userSurId;
	
	public UserLiveClassAttended() {
		super();
	}
	
	public UserLiveClassAttended(Long idLiveClass, Long userSurId) {
		super();
		this.idLiveClass = idLiveClass;
		this.userSurId = userSurId;
	}



	public Long getIdUserLiveClassAttended() {
		return idUserLiveClassAttended;
	}

	public void setIdUserLiveClassAttended(Long idUserLiveClassAttended) {
		this.idUserLiveClassAttended = idUserLiveClassAttended;
	}

	public Long getIdLiveClass() {
		return idLiveClass;
	}

	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
	}

	public Long getUserSurId() {
		return userSurId;
	}

	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}
}
