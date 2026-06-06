/**
 * 
 */
package co.vistafoundation.vlearning.email.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;

import co.vistafoundation.vlearning.email.dto.SendMailDTO;
import co.vistafoundation.vlearning.email.dto.SendMailDTOWithAttachment;

/**
 * @author vk
 *
 */
@Service
public class EmailServiceUtil {

	/*
	 * @Autowired
	 * 
	 * SendGridAPI sg;
	 */

	private static final Logger log = LoggerFactory.getLogger(EmailServiceUtil.class);
	
	private final AmazonSimpleEmailService amazonSimpleEmailService;
	
	//@Autowired
    public EmailServiceUtil(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

	/**
	 * @author vk
	 * modified 13-January-2022
	 * Deprecated the SendGrid email service
	 */
	@Deprecated
	public void sendEmailSendGrid(SendMailDTO sendMailDTO) {
		/*
		 * if (sendMailDTO == null) { throw new
		 * NullPointerException("Data Cannot be Null"); } try { Email fromAddress = new
		 * Email("support@dreammithra.in", "VLearning Support"); String subject =
		 * sendMailDTO.getSubject(); Email toAddress = new
		 * Email(sendMailDTO.getToAddress()); Content content = new Content("text/html",
		 * sendMailDTO.getMessageBody()); Mail mail = new Mail(fromAddress, subject,
		 * toAddress, content); Request request = new Request();
		 * request.addHeader("Priority", "Urgent"); request.addHeader("X-Priority",
		 * "1"); request.addHeader("Importance", "high"); request.addHeader("format",
		 * "flowed"); request.setMethod(Method.POST); request.setEndpoint("mail/send");
		 * request.setBody(mail.build()); sg.api(request); } catch (IOException e) {
		 * throw new AppException(e.getMessage()); }
		 */
	}

	/**
	 * @author vk
	 * added 13-January-2022
	 * AWS SES Email Integration
	 */
	@Async
	public void sendEmail(SendMailDTO sendMailDTO) throws MessagingException {
		SendEmailRequest sendEmailRequest =
				new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(sendMailDTO.getToAddress()))
				.withMessage(
						new Message()
						.withBody(
								new Body()
								.withHtml(new Content().withCharset("UTF-8").withData(sendMailDTO.getMessageBody())))
						.withSubject(new Content().withCharset("UTF-8").withData(sendMailDTO.getSubject())))
				.withSource("Vistas Learning Support <support@v-learning.in>");
		SendEmailResult result = amazonSimpleEmailService.sendEmail(sendEmailRequest);
		System.out.println(result.getMessageId());
	}
	
	@Async
	public void sendEmailWithAttachment(SendMailDTOWithAttachment sendMailDTO) throws MessagingException {

		Session session = Session.getDefaultInstance(new Properties());

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress("Vistas Learning Support <support@v-learning.in>"));
		message.setSubject(sendMailDTO.getSubject(), "UTF-8");
		message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(sendMailDTO.getToAddress()));

		MimeMultipart msg_body = new MimeMultipart("alternative");

		MimeBodyPart wrap = new MimeBodyPart();

		// Define the HTML part.
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(sendMailDTO.getMessageBody(), "text/html; charset=UTF-8");

		// Add the text and HTML parts to the child container.
		msg_body.addBodyPart(htmlPart);

		// Add the child container to the wrapper object.
		wrap.setContent(msg_body);

		// Create a multipart/mixed parent container.
		MimeMultipart msg = new MimeMultipart("mixed");

		// Add the parent container to the message.
		message.setContent(msg);

		// Add the multipart/alternative part to the message.
		msg.addBodyPart(wrap);

		// Try to send the email.
		try {
			System.out.println("Attempting to send an email...");

			// Instantiate your email client here
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.AP_SOUTH_1).build();

			// Define the attachment
			MimeBodyPart att = new MimeBodyPart();
			InputStream inputStream = sendMailDTO.getByteArrayInputStream();
			att.setDataHandler(new DataHandler(new ByteArrayDataSource(inputStream, "application/pdf")));
			att.setFileName("attachment.pdf");

			// Add the attachment to the message.
			msg.addBodyPart(att);

			// Send the email.
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));

			SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
			client.sendRawEmail(rawEmailRequest);
			System.out.println("Email sent!" + rawEmailRequest.getRawMessage());

			// Close the InputStream
			inputStream.close();

		} catch (Exception ex) {
			System.out.println("Email Failed");
			System.err.println("Error message: " + ex.getMessage());
			log.error(ex.getMessage());
		}
	} 

}
