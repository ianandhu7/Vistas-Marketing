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
@Table (name ="LIVE_CLASS_Q_AND_A")
public class LiveClassQndA extends UserDateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLIVE_CLASS_Q_AND_A")
	private Long idLiveClassQAndA;
	
	@Column(name="idLIVE_CLASS")
	private Long idLiveClass;
	
	@Column(name="idVL_USER")
	private Long userSurId;
	
	@Column(name = "QUESTION_TEXT", length = 200)
	private String questionText;
	
	@Column(name = "ANSWER_TEXT", length = 500)
	private String answerText;

	/**
	 * 
	 */
	public LiveClassQndA() {
		super();
	}

	/**
	 * @param idLiveClass
	 * @param userSurId
	 * @param questionText
	 * @param answerText
	 */
	public LiveClassQndA(Long idLiveClass, Long userSurId, String questionText, String answerText) {
		super();
		this.idLiveClass = idLiveClass;
		this.userSurId = userSurId;
		this.questionText = questionText;
		this.answerText = answerText;
	}

	/**
	 * @return the idLiveClassQAndA
	 */
	public Long getIdLiveClassQAndA() {
		return idLiveClassQAndA;
	}

	/**
	 * @param idLiveClassQAndA the idLiveClassQAndA to set
	 */
	public void setIdLiveClassQAndA(Long idLiveClassQAndA) {
		this.idLiveClassQAndA = idLiveClassQAndA;
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

	/**
	 * @return the answerText
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * @param answerText the answerText to set
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
}
