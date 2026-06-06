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
@Table(name = "MARKETER_VENDOR_PAYMENT")
public class MarketerVendorPayment extends UserDateAudit implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idMARKETER_VENDOR_PAYMENT", nullable = false)
	private Long idMarketerVendorPayment;
	
	@Column(name = "idMARKETER",nullable = false)
	private Long idMarketer;
	
	@Column(name = "idVENDOR",nullable = false)
	private Long idVendor;
	
	
	@Column(name ="PAYMENT_STATUS")
	private String paymentStatus;
	
	@Column(name="PAYMENT_DATE")
	private LocalDate paymentDate;
	
	@Column(name = "idSALES_ADMIN")
	private Long idSalesAdmin;

	/**
	 * @return the idMarketerVendorPayment
	 */
	public Long getIdMarketerVendorPayment() {
		return idMarketerVendorPayment;
	}

	/**
	 * @param idMarketerVendorPayment the idMarketerVendorPayment to set
	 */
	public void setIdMarketerVendorPayment(Long idMarketerVendorPayment) {
		this.idMarketerVendorPayment = idMarketerVendorPayment;
	}

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
	 * @return the idVendor
	 */
	public Long getIdVendor() {
		return idVendor;
	}

	/**
	 * @param idVendor the idVendor to set
	 */
	public void setIdVendor(Long idVendor) {
		this.idVendor = idVendor;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @return the paymentDate
	 */
	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

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
	 * @param idMarketer
	 * @param idVendor
	 * @param paymentStatus
	 * @param paymentDate
	 * @param idSalesAdmin
	 */
	public MarketerVendorPayment(Long idMarketer, Long idVendor, String paymentStatus, LocalDate paymentDate,
			Long idSalesAdmin) {
		super();
		this.idMarketer = idMarketer;
		this.idVendor = idVendor;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
		this.idSalesAdmin = idSalesAdmin;
	}

	/**
	 * 
	 */
	public MarketerVendorPayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
