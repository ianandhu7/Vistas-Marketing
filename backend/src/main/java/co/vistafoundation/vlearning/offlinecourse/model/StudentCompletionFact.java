/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.UserDateAudit;

/**
 * @author vk
 *
 */
@Entity
@Table(name = "STUDENT_COMPLETION_FACT")
@JsonIgnoreProperties({ "updatedBy", "createdBy", "createdAt", "updatedAt" })
public class StudentCompletionFact extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idSTUDENT_COMPLETION_FACT", nullable = false)
	private Long idStudentCompletionFact;

	@Column(name = "idSUBJECT", nullable = false)
	private Long idSubject;

	@Column(name = "AS_ON_DATE")
	private Instant asOnDate;

	@Column(name = "COMPLETION_PCT")
	private Long completionPCT;

	@Column(name = "idSTUDENT_SUBSCR")
	private Long idStudentSubscription;

	@Column(name = "subjectNAME")
	private String subjectName;

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	 * @param idStudentCompletionFact
	 * @param idSubject
	 * @param asOnDate
	 * @param completionPCT
	 * @param idStudentSubscription
	 */
	public StudentCompletionFact(Long idStudentCompletionFact, Long idSubject, Instant asOnDate, Long completionPCT,
			Long idStudentSubscription, String subjectName) {
		super();
		this.idStudentCompletionFact = idStudentCompletionFact;
		this.idSubject = idSubject;
		this.asOnDate = asOnDate;
		this.completionPCT = completionPCT;
		this.idStudentSubscription = idStudentSubscription;
		this.subjectName = subjectName;
	}

	/**
	 * 
	 */
	public StudentCompletionFact() {
		super();
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idStudentCompletionFact
	 */
	public Long getIdStudentCompletionFact() {
		return idStudentCompletionFact;
	}

	/**
	 * @return the idSubject
	 */
	public Long getIdSubject() {
		return idSubject;
	}

	/**
	 * @return the asOnDate
	 */
	public Instant getAsOnDate() {
		return asOnDate;
	}

	/**
	 * @return the completionPCT
	 */
	public Long getCompletionPCT() {
		return completionPCT;
	}

	/**
	 * @param idStudentCompletionFact the idStudentCompletionFact to set
	 */
	public void setIdStudentCompletionFact(Long idStudentCompletionFact) {
		this.idStudentCompletionFact = idStudentCompletionFact;
	}

	/**
	 * @param idSubject the idSubject to set
	 */
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	/**
	 * @param asOnDate the asOnDate to set
	 */
	public void setAsOnDate(Instant asOnDate) {
		this.asOnDate = asOnDate;
	}

	/**
	 * @param completionPCT the completionPCT to set
	 */
	public void setCompletionPCT(Long completionPCT) {
		this.completionPCT = completionPCT;
	}

}
