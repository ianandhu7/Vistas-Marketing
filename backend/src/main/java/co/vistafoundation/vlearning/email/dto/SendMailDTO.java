/**
 * 
 */
package co.vistafoundation.vlearning.email.dto;

/**
 * @author vk
 *
 */
public class SendMailDTO {

	private String subject;
	private String messageBody;
	private String toAddress;
	
	/**
	 * 
	 */
	public SendMailDTO() {
		super();
	}
	
	/**
	 * @param subject
	 * @param messageBody
	 * @param toAddress
	 */
	public SendMailDTO(String subject, String messageBody, String toAddress) {
		super();
		this.subject = subject;
		this.messageBody = messageBody;
		this.toAddress = toAddress;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the messageBody
	 */
	public String getMessageBody() {
		return messageBody;
	}

	/**
	 * @param messageBody the messageBody to set
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	/**
	 * @return the toAddress
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @param toAddress the toAddress to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
}
