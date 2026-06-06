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

import co.vistafoundation.vlearning.audit.model.DateAudit;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@Entity
@Table(name = "VL_LEAD")
public class Lead extends DateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_LEAD")
	private Long idLead;

	@Column(name = "LEAD_NAME")
	private String leadName;

	@Column(name = "LEAD_EMAIL")
	private String leadEmail;

	@Column(name = "LEAD_PHONE")
	private String leadPhone;

	@Column(name = "LEAD_FOOTFALL_DATE")
	private String leadFootfallDate;

	@Column(name = "PRODUCT_CATEGORY")
	private String productCategory;

	public Lead() {

	}

	/**
	 * @param idLead
	 * @param leadName
	 * @param leadEmail
	 * @param leadPhone
	 * @param leadFootfallDate
	 * @param productCategory
	 */
	public Lead(Long idLead, String leadName, String leadEmail, String leadPhone, String leadFootfallDate,
			String productCategory) {
		super();
		this.idLead = idLead;
		this.leadName = leadName;
		this.leadEmail = leadEmail;
		this.leadPhone = leadPhone;
		this.leadFootfallDate = leadFootfallDate;
		this.productCategory = productCategory;
	}

	/**
	 * @return the idLead
	 */
	public Long getIdLead() {
		return idLead;
	}

	/**
	 * @param idLead the idLead to set
	 */
	public void setIdLead(Long idLead) {
		this.idLead = idLead;
	}

	/**
	 * @return the leadName
	 */
	public String getLeadName() {
		return leadName;
	}

	/**
	 * @param leadName the leadName to set
	 */
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	/**
	 * @return the leadEmail
	 */
	public String getLeadEmail() {
		return leadEmail;
	}

	/**
	 * @param leadEmail the leadEmail to set
	 */
	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}

	/**
	 * @return the leadPhone
	 */
	public String getLeadPhone() {
		return leadPhone;
	}

	/**
	 * @param leadPhone the leadPhone to set
	 */
	public void setLeadPhone(String leadPhone) {
		this.leadPhone = leadPhone;
	}

	/**
	 * @return the leadFootfallDate
	 */
	public String getLeadFootfallDate() {
		return leadFootfallDate;
	}

	/**
	 * @param leadFootfallDate the leadFootfallDate to set
	 */
	public void setLeadFootfallDate(String leadFootfallDate) {
		this.leadFootfallDate = leadFootfallDate;
	}

	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

}
