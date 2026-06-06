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
@Table(name = "DONATOR")
public class Donator extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDONATOR", nullable = false)
	private Long idDONATOR;
	
	@Column(name ="DONATOR_NAME",nullable = false)
	private String donatorName;
	
	
	@Column(name ="TOTAL_FREE_SUBSCRIPTION",nullable = false)
	private String totalFreeSubscripiton;


	/**
	 * @return the idDONATOR
	 */
	public Long getIdDONATOR() {
		return idDONATOR;
	}


	/**
	 * @param idDONATOR the idDONATOR to set
	 */
	public void setIdDONATOR(Long idDONATOR) {
		this.idDONATOR = idDONATOR;
	}


	/**
	 * @return the donatorName
	 */
	public String getDonatorName() {
		return donatorName;
	}


	/**
	 * @param donatorName the donatorName to set
	 */
	public void setDonatorName(String donatorName) {
		this.donatorName = donatorName;
	}


	/**
	 * @return the totalFreeSubscripiton
	 */
	public String getTotalFreeSubscripiton() {
		return totalFreeSubscripiton;
	}


	/**
	 * @param totalFreeSubscripiton the totalFreeSubscripiton to set
	 */
	public void setTotalFreeSubscripiton(String totalFreeSubscripiton) {
		this.totalFreeSubscripiton = totalFreeSubscripiton;
	}


	/**
	 * 
	 */
	public Donator() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param donatorName
	 * @param totalFreeSubscripiton
	 */
	public Donator(String donatorName, String totalFreeSubscripiton) {
		super();
		this.donatorName = donatorName;
		this.totalFreeSubscripiton = totalFreeSubscripiton;
	}
	
	

}
