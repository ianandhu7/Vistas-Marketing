package co.vistafoundation.vlearning.quiz.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.vistafoundation.vlearning.audit.model.DateAudit;



@Entity
@Table(name = "chatbot_qa")
@JsonIgnoreProperties(value = {"createdBy", "updatedBy"}, allowGetters = true)
public class ChatbotQA extends DateAudit implements Serializable{

    private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CHATBOT_QA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChatbotQa;

	@Lob
    @Column(name ="QUESTION",nullable = false,columnDefinition = "TEXT")
    private String question;

    @Lob
    @Column(name ="ANSWER",nullable = false,columnDefinition = "TEXT")
    private String answer;
    
    @Column(name ="ID_VL_USER")
    private Long idVlUser;
    
    @Column(name ="STATUS")
    private Boolean status;

	public Long getIdChatbotQa() {
		return idChatbotQa;
	}

	public void setIdChatbotQa(Long idChatbotQa) {
		this.idChatbotQa = idChatbotQa;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getIdVlUser() {
		return idVlUser;
	}

	public void setIdVlUser(Long idVlUser) {
		this.idVlUser = idVlUser;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
    
}
