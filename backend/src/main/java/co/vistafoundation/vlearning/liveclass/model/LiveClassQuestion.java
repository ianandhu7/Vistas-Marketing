/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.model;

import java.io.Serializable;

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
@Table(name = "LIVE_CLASS_QUESTION")
public class LiveClassQuestion extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLIVE_CLASS_QUESTION")
	private Long idLiveClassQuestion;
	
	@Column(name="idLIVE_CLASS")
	private Long idLiveClass;
	
	@Column(name="idVL_USER")
	private Long userSurId;
	
	@Column(name = "QUESTION_TEXT", length = 200)
	private String questionText;

	/**
	 * 
	 */
	public LiveClassQuestion() {
		super();
	}

	/**
	 * @param idLiveClass
	 * @param userSurId
	 * @param questionText
	 */
	public LiveClassQuestion(Long idLiveClass, Long userSurId, String questionText) {
		super();
		this.idLiveClass = idLiveClass;
		this.userSurId = userSurId;
		this.questionText = questionText;
	}

	/**
	 * @return the idLiveClassQuestion
	 */
	public Long getIdLiveClassQuestion() {
		return idLiveClassQuestion;
	}

	/**
	 * @param idLiveClassQuestion the idLiveClassQuestion to set
	 */
	public void setIdLiveClassQuestion(Long idLiveClassQuestion) {
		this.idLiveClassQuestion = idLiveClassQuestion;
	}

	/**
	 * @return the idLiveClass
	 */
	public Long getIdLiveClass() {
		return idLiveClass;
	}

	/**
	 * @param idLiveClass the idLiveClass to set
	 */
	public void setIdLiveClass(Long idLiveClass) {
		this.idLiveClass = idLiveClass;
	}

	/**
	 * @return the userSurId
	 */
	public Long getUserSurId() {
		return userSurId;
	}

	/**
	 * @param userSurId the userSurId to set
	 */
	public void setUserSurId(Long userSurId) {
		this.userSurId = userSurId;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
}
