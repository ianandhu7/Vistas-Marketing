/**
 * 
 */
package co.vistafoundation.vlearning.subscription.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */

@Entity
@Table(name = "SUBSCRIPTION_PAYMENT_HISTORY")
public class SubscriptionPaymentHistory extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSUBSCRIPTION_PAYMENT_HISTORY", nullable = false)
	private Long idSubscriptionPaymentHistory;

	@Column(name = "idSTUDENT_SUBSCR", nullable = false)
	private Long idStudentSubscription;

	@Column(name = "idSTUDENT_ORDER", nullable = false)
	private Long idStudentOrder;
	
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;
	
	@Column(name = "PAYMENT_AMOUNT")
	private Float paymentAmount;

	/**
	 * 
	 */
	public SubscriptionPaymentHistory() {
		super();
	}

	/**
	 * @param idStudentSubscription
	 * @param idStudentOrder
	 * @param paymentDate
	 * @param paymentAmount
	 */
	public SubscriptionPaymentHistory(Long idStudentSubscription, Long idStudentOrder, Date paymentDate,
			Float paymentAmount) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idStudentOrder = idStudentOrder;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the idSubscriptionPaymentHistory
	 */
	public Long getIdSubscriptionPaymentHistory() {
		return idSubscriptionPaymentHistory;
	}

	/**
	 * @param idSubscriptionPaymentHistory the idSubscriptionPaymentHistory to set
	 */
	public void setIdSubscriptionPaymentHistory(Long idSubscriptionPaymentHistory) {
		this.idSubscriptionPaymentHistory = idSubscriptionPaymentHistory;
	}

	/**
	 * @return the idStudentSubscription
	 */
	public Long getIdStudentSubscription() {
		return idStudentSubscription;
	}

	/**
	 * @param idStudentSubscription the idStudentSubscription to set
	 */
	public void setIdStudentSubscription(Long idStudentSubscription) {
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @return the idStudentOrder
	 */
	public Long getIdStudentOrder() {
		return idStudentOrder;
	}

	/**
	 * @param idStudentOrder the idStudentOrder to set
	 */
	public void setIdStudentOrder(Long idStudentOrder) {
		this.idStudentOrder = idStudentOrder;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the paymentAmount
	 */
	public Float getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount the paymentAmount to set
	 */
	public void setPaymentAmount(Float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
}
