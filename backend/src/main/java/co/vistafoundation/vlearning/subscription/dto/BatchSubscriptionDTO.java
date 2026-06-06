/**
 * 
 */
package co.vistafoundation.vlearning.subscription.dto;

import java.time.Instant;
import java.time.LocalTime;

import co.vistafoundation.vlearning.batch.dto.BatchInfoDTO;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;

/**
 * @author vk
 *
 */
public class BatchSubscriptionDTO extends BatchInfoDTO {

	private Instant lastPaymentDate;

	private Instant subscriptionEndDate;

	private String purchaseAmount;
	
	private Long idStudentSubscription;

	/**
	 * 
	 */
	public BatchSubscriptionDTO() {
		super();
	}

	/**
	 * @param batchName
	 * @param idBatch
	 * @param subjectName
	 * @param idTeacher
	 * @param batchSize
	 * @param teacherName
	 * @param dayOfWeekCode
	 * @param batchFromTime
	 * @param batchToTime
	 * @param attendeeMeetingUrl
	 * @param nextPaymentDate
	 * @param meetingPassword
	 */
	public BatchSubscriptionDTO(String batchName, Long idBatch, String subjectName, Long idTeacher, int batchSize,
			String teacherName, DayOfWeekCode dayOfWeekCode, LocalTime batchFromTime, LocalTime batchToTime,
			String attendeeMeetingUrl, Instant nextPaymentDate, String meetingPassword) {
		super(batchName, idBatch, subjectName, idTeacher, batchSize, teacherName, dayOfWeekCode, batchFromTime, batchToTime,
				attendeeMeetingUrl, nextPaymentDate, meetingPassword);
	}

	/**
	 * @param lastPaymentDate
	 * @param subscriptionEndDate
	 * @param purchaseAmount
	 * @param idStudentSubscription
	 */
	public BatchSubscriptionDTO(Instant lastPaymentDate, Instant subscriptionEndDate, String purchaseAmount,
			Long idStudentSubscription) {
		super();
		this.lastPaymentDate = lastPaymentDate;
		this.subscriptionEndDate = subscriptionEndDate;
		this.purchaseAmount = purchaseAmount;
		this.idStudentSubscription = idStudentSubscription;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Instant getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Instant lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the subscriptionEndDate
	 */
	public Instant getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	/**
	 * @param subscriptionEndDate the subscriptionEndDate to set
	 */
	public void setSubscriptionEndDate(Instant subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	/**
	 * @return the purchaseAmount
	 */
	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	/**
	 * @param purchaseAmount the purchaseAmount to set
	 */
	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
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

}
