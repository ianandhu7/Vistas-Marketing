package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;

public class StudentSubscriptionBatchDTO {

	private Long idStudentSubscription;
	
	private Long idBatch;
	
	private String batchName;
	
	private Instant nextPaymentDate;
	
	private Long idSpecialOffer;
	
	private Long idStudentOrder;

	/**
	 * @param idStudentSubscription
	 * @param idBatch
	 * @param batchName
	 * @param nextPaymentDate
	 * @param idSpecialOffer
	 */
	public StudentSubscriptionBatchDTO(Long idStudentSubscription, Long idBatch, String batchName,
			Instant nextPaymentDate, Long idSpecialOffer, Long idStudentOrder) {
		super();
		this.idStudentSubscription = idStudentSubscription;
		this.idBatch = idBatch;
		this.batchName = batchName;
		this.nextPaymentDate = nextPaymentDate;
		this.idSpecialOffer = idSpecialOffer;
		this.idStudentOrder = idStudentOrder;
	}

	/**
	 * 
	 */
	public StudentSubscriptionBatchDTO() {
		super();
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
	 * @return the idBatch
	 */
	public Long getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * @return the nextPaymentDate
	 */
	public Instant getNextPaymentDate() {
		return nextPaymentDate;
	}

	/**
	 * @param nextPaymentDate the nextPaymentDate to set
	 */
	public void setNextPaymentDate(Instant nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	/**
	 * @return the idSpecialOffer
	 */
	public Long getIdSpecialOffer() {
		return idSpecialOffer;
	}

	/**
	 * @param idSpecialOffer the idSpecialOffer to set
	 */
	public void setIdSpecialOffer(Long idSpecialOffer) {
		this.idSpecialOffer = idSpecialOffer;
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
	
}
