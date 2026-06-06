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
@Table(name = "VENDOR_STUDENT_PAYMENT")
public class VendorStudentPayment extends UserDateAudit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVENDOR_STUDENT_PAYMENT", nullable = false)
	private Long idVendorStudentPayment;
	
	
	@Column(name = "idSTUDENT",nullable = false)
	private Long idStudent;

	
	@Column(name = "idVENDOR",nullable = false)
	private Long idVendor;

	
	@Column(name="ON_BOARDED_DATE")
	private LocalDate onBoardedDate;
	
	
	@Column(name ="VENDOR_PAYMENT_STATUS")
	private String vendorPaymentStatus;
	
	@Column(name="PAYMENT_DATE")
	private LocalDate paymentDate;
	
	@Column(name="PAYMENT_AMOUNT")
	private float paymentAmount;
	

	/**
	 * @return the idVendorStudentPayment
	 */
	public Long getIdVendorStudentPayment() {
		return idVendorStudentPayment;
	}

	/**
	 * @param idVendorStudentPayment the idVendorStudentPayment to set
	 */
	public void setIdVendorStudentPayment(Long idVendorStudentPayment) {
		this.idVendorStudentPayment = idVendorStudentPayment;
	}

	/**
	 * @return the idStudent
	 */
	public Long getIdStudent() {
		return idStudent;
	}

	/**
	 * @param idStudent the idStudent to set
	 */
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
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
	 * @return the onBoardedDate
	 */
	public LocalDate getOnBoardedDate() {
		return onBoardedDate;
	}

	/**
	 * @param onBoardedDate the onBoardedDate to set
	 */
	public void setOnBoardedDate(LocalDate onBoardedDate) {
		this.onBoardedDate = onBoardedDate;
	}

	/**
	 * @return the vendorPaymentStatus
	 */
	public String getVendorPaymentStatus() {
		return vendorPaymentStatus;
	}

	/**
	 * @param vendorPaymentStatus the vendorPaymentStatus to set
	 */
	public void setVendorPaymentStatus(String vendorPaymentStatus) {
		this.vendorPaymentStatus = vendorPaymentStatus;
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
	 * 
	 */
	public VendorStudentPayment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the paymentAmount
	 */
	public float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	
	
	/**
	 * @param idStudent
	 * @param idVendor
	 * @param onBoardedDate
	 * @param vendorPaymentStatus
	 * @param paymentDate
	 * @param paymentAmount
	 */
	public VendorStudentPayment(Long idStudent, Long idVendor, LocalDate onBoardedDate, String vendorPaymentStatus,
			LocalDate paymentDate, float paymentAmount) {
		super();
		this.idStudent = idStudent;
		this.idVendor = idVendor;
		this.onBoardedDate = onBoardedDate;
		this.vendorPaymentStatus = vendorPaymentStatus;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;
	}
	
}
