package co.vistafoundation.vlearning.email.dto;

import java.io.ByteArrayInputStream;

public class SendMailDTOWithAttachment {
    private String toAddress;
    private String subject;
    private String messageBody;
    private ByteArrayInputStream byteArrayInputStream;

    public SendMailDTOWithAttachment(String toAddress, String subject, String messageBody, ByteArrayInputStream byteArrayInputStream) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.messageBody = messageBody;
        this.byteArrayInputStream = byteArrayInputStream;
    }

    public SendMailDTOWithAttachment() {
		// TODO Auto-generated constructor stub
	}

	public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public ByteArrayInputStream getByteArrayInputStream() {
        return byteArrayInputStream;
    }

    public void setByteArrayInputStream(ByteArrayInputStream byteArrayInputStream) {
        this.byteArrayInputStream = byteArrayInputStream;
    }
}

