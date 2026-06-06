/**
 * 
 */
package co.vistafoundation.vlearning.websocket.dto;

import java.time.Instant;

/**
 * @author vk
 *
 */
public class ChatMessage {
	private Instant createdAt;

    private Instant updatedAt;
	
    private MessageType type;
	
    private String question;
    
    private String answer;
    
    private String sender;
    
    private Long senderIdVlUser;
    
    private String receiver;
    
    private Long receiverIdVlUser;
    
    private Long idLiveClass;

    
    public ChatMessage() {
    	
	}

	/**
	 * @param type
	 * @param question
	 * @param answer
	 * @param sender
	 * @param senderIdVlUser
	 * @param receiver
	 * @param receiverIdVlUser
	 * @param idLiveClass
	 */
	public ChatMessage(Instant createdAt, Instant updatedAt, MessageType type, String question, String answer,
			String sender, Long senderIdVlUser, String receiver, Long receiverIdVlUser, Long idLiveClass) {
		super();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.sender = sender;
		this.senderIdVlUser = senderIdVlUser;
		this.receiver = receiver;
		this.receiverIdVlUser = receiverIdVlUser;
		this.idLiveClass = idLiveClass;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the senderIdVlUser
	 */
	public Long getSenderIdVlUser() {
		return senderIdVlUser;
	}

	/**
	 * @param senderIdVlUser the senderIdVlUser to set
	 */
	public void setSenderIdVlUser(Long senderIdVlUser) {
		this.senderIdVlUser = senderIdVlUser;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the receiverIdVlUser
	 */
	public Long getReceiverIdVlUser() {
		return receiverIdVlUser;
	}

	/**
	 * @param receiverIdVlUser the receiverIdVlUser to set
	 */
	public void setReceiverIdVlUser(Long receiverIdVlUser) {
		this.receiverIdVlUser = receiverIdVlUser;
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

	@Override
	public String toString() {
		return "ChatMessage [type=" + type + ", question=" + question + ", answer=" + answer + ", sender=" + sender
				+ ", senderIdVlUser=" + senderIdVlUser + ", receiver=" + receiver + ", receiverIdVlUser="
				+ receiverIdVlUser + ", idLiveClass=" + idLiveClass + "]";
	}

}

