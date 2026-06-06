/**
 * 
 */
package co.vistafoundation.vlearning.marketer.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "MARKETER")
public class Marketer extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMARKETER", nullable = false)
	private Long idMarketer;
	
	@Column(name = "idVL_USER",nullable = false)
	private Long idVlUser;
	
	@Column(name ="NAME",nullable = false)
	private String name;
	
	
	@Column(name="JOIN_DATE",nullable = false)
	private LocalDate startDate;
	
	
	@Column(name="END_DATE")
	private LocalDate endDate;


	/**
	 * @return the idMarketer
	 */
	public Long getIdMarketer() {
		return idMarketer;
	}


	/**
	 * @param idMarketer the idMarketer to set
	 */
	public void setIdMarketer(Long idMarketer) {
		this.idMarketer = idMarketer;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	/**
	 * @param idVlUser
	 * @param name
	 * @param startDate
	 * @param endDate
	 */
	public Marketer(Long idVlUser, String name, LocalDate startDate, LocalDate endDate) {
		super();
		this.idVlUser = idVlUser;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	/**
	 * 
	 */
	public Marketer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
