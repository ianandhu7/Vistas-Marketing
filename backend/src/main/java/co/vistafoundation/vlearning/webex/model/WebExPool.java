package co.vistafoundation.vlearning.webex.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NaveenKumar
 * 
 **/
@Entity
@Table(name = "WEBEX_POOL", uniqueConstraints = { @UniqueConstraint(columnNames = { "WEBEX_USER_ID" }) })
public class WebExPool extends UserDateAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idWEBEX_POOL", nullable = false)
	private Long idWebExPool;

	@Column(name = "WEBEX_USER_ID", length = 100, unique = true)
	@NotNull
	private String webExUserId;

	@Column(name = "WEBEX_PASSWORD", length = 100)
	@NotNull
	private String webExPassword;

	@Column(name = "AVAILABLE_FLAG")
	private Boolean availableFlag;

	public Long getIdWebExPool() {
		return idWebExPool;
	}

	public void setIdWebExPool(Long idWebExPool) {
		this.idWebExPool = idWebExPool;
	}

	public String getWebExUserId() {
		return webExUserId;
	}

	public void setWebExUserId(String webExUserId) {
		this.webExUserId = webExUserId;
	}

	public String getWebExPassword() {
		return webExPassword;
	}

	public void setWebExPassword(String webExPassword) {
		this.webExPassword = webExPassword;
	}

	public Boolean getAvailableFlag() {
		return availableFlag;
	}

	public void setAvailableFlag(Boolean availableFlag) {
		this.availableFlag = availableFlag;
	}

	public WebExPool(String webExUserId, String webExPassword, Boolean availableFlag) {
		super();
		this.webExUserId = webExUserId;
		this.webExPassword = webExPassword;
		this.availableFlag = availableFlag;
	}

	public WebExPool() {
	}

}
