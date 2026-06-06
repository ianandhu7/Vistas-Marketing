/**
 * 
 */
package co.vistafoundation.vlearning.marketer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author NAVEEN
 *
 */

@Entity
@Table(name = "SALES_ADMIN")
public class SalesAdmin extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSALES_ADMIN", nullable = false)
	private Long idSalesAdmin;
	
	
	@Column(name = "idVL_USER",nullable = false)
	private Long idVlUser;


	/**
	 * @return the idSalesAdmin
	 */
	public Long getIdSalesAdmin() {
		return idSalesAdmin;
	}


	/**
	 * @param idSalesAdmin the idSalesAdmin to set
	 */
	public void setIdSalesAdmin(Long idSalesAdmin) {
		this.idSalesAdmin = idSalesAdmin;
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
	 * @param idVlUser
	 */
	public SalesAdmin(Long idVlUser) {
		super();
		this.idVlUser = idVlUser;
	}


	/**
	 * 
	 */
	public SalesAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
