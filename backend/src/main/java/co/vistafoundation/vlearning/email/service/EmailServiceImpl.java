package co.vistafoundation.vlearning.email.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.dto.SendMailDTO;
import co.vistafoundation.vlearning.email.dto.SendMailDTOWithAttachment;
import co.vistafoundation.vlearning.email.utils.EmailServiceUtil;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.pdfgeneration.service.PdfInvoiceService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	Environment environment;

	@Autowired
	ClassRepository classStandardRepo;

	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	EmailServiceUtil emailServiceUtil;
	
	@Autowired
	@org.springframework.context.annotation.Lazy
	StudentSubscriptionService studentSubscriptionService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentOrderRepository studentOrderRepository;
	
	@Autowired
	PdfInvoiceService pdfInvoiceService;

	@Value("${app.angular.url}")
	private String angularUrl;
	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendForgotPasswordEmail(String toAddress, String username, String randomString, String fullName,
			Long userSurId) {
		Document doc = new Document();
		String resetPwd =angularUrl+"/authentication";
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:20px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:0px solid lightgrey;\r\n"
				+ "border-radius:8px;\r\n" + "background-color:#0c0755;\r\n" + "padding:10px;\r\n" + "color:white !important;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n" 
				+ "<center><img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img></center>\r\n"
				+ "<p>Dear " + fullName + "<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please click on the below Link to reset your password.\r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<center style=\"margin-top:10%;\">\r\n" + "<a href=\""+ resetPwd
				+ "/forgot-password?userSurId=" + userSurId + "&randomString=" + randomString
				+ "&forgotPasswordUsername=" + username + "\" class=\"btn\" target=\"_blank\">Reset Password</a>\r\n"
				+ "</center>\r\n" + "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n"
				+ "Vistas Learning\r\n" + "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Reset Password Email ";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(toAddress);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Email Sent Success");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendForgotPasswordEmailInternal(String toAddress, String username, String randomString, String fullName,
			Long userSurId) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:20px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:5%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:0px solid lightgrey;\r\n"
				+ "border-radius:8px;\r\n" + "background-color:#0c0755;\r\n" + "padding:10px;\r\n" + "color:white !important;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n" + "<p>Dear <b>"+fullName+"</b>,<br><br>\r\n"
				+ "You can use the following new credentials to access the application as inter user login.\r\n"
				+ "<br><br>\r\n <p> Your username is : "+username+"</p>\r\n"
				+ "<p> Your password is : "+randomString+"</p>"
				+ "<br><p>Please note that this email is unique to you. Be careful not to forward this mail.</p>\r\n"
				+ "<p class=\"footer-text\">" + "Thanks,<br>\r\n"
				+ "Vistas Learning\r\n" + "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Request to Reset Password ";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(toAddress);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Email Sent Success");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendVerificationMailForChangeMobileNumber(String email, String newMobile, Long userSurId,
			String randomString, String fullName) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family:sans-serif;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4><u>Vistas Learning</u></h4>\r\n" + "</center>\r\n"
				+ "\r\n" + "<p style=\"margin-top:20px\">\r\n" + "Dear " + fullName + ",<br><br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please Enter the following Verification Code to change your Mobile Number.\r\n"
				+ "</p>\r\n" + "\r\n" + "<center style=\"margin-top:100px\">\r\n"
				+ "<div style=\"border:1px solid #111;border-radius:10px;background:#eee;width:200px;padding:10px\">\r\n"
				+ "<b>" + randomString + "</b>\r\n" + "</div>\r\n" + "</center>\r\n" + "\r\n"
				+ "<footer style=\"margin-top:30px\">\r\n" + "Thanks & Regards,<br>\r\n" + "Vistas Learning Team\r\n"
				+ "</footer>\r\n" + "\r\n" + "</body>";
		String subject = "Vistas Learning :: Change Mobile Number";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Email Sent Success");
			doc.setStatusCode(200);
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document sendWelcomeEmailToStudentOnSuccessfulSignUp(String studentEmail, String fullName, String userName,
			String mobileNumber, String role, String classStandard) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:1px solid lightgrey;\r\n"
				+ "border-radius:20px;\r\n" + "background-color:#111;\r\n" + "padding:15px;\r\n" + "color:white;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n"
				+ "\r\n" + "th, td {\r\n" + "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to Vistas Learning E-Learning Platform. You have been Registered as Student. Your Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<center style=\"margin-top:6%;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Email</th>\r\n" + "<th>UserName</th>\r\n" + "<th>Mobile Number</th>\r\n"
				+ "<th>Class Standard</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + studentEmail
				+ "</td>\r\n" + "<td>" + userName + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "<td>Class-"
				+ classStandard + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n"
				+ "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(studentEmail);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Welcome Email Sent to Student...");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document sendWelcomeEmailToParentOnSuccessfulSignUp(String parentEmail, String parentFullName,
			String parentUserName, String mobileNumber, String role, String classStandard, String kidUserName,
			String kidPassword) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:1px solid lightgrey;\r\n"
				+ "border-radius:20px;\r\n" + "background-color:#111;\r\n" + "padding:15px;\r\n" + "color:white;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n"
				+ "\r\n" + "th, td {\r\n" + "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<p>Dear " + parentFullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to Vistas Learning E-Learning Platform. You have been Registered as Parent. Your Details and Your Kid Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Parent Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Mobile Number</th>\r\n" + "<th>Class Standard</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n"
				+ "<tr>\r\n" + "<td>" + parentEmail + "</td>\r\n" + "<td>" + parentUserName + "</td>\r\n" + "<td>"
				+ mobileNumber + "</td>\r\n" + "<td>Class-" + classStandard + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>\r\n" + "</center>\r\n" + "\r\n" + "<h5>Kid Details</h5>\r\n"
				+ "<center style=\"margin-top:3%;\">\r\n" + "<table>\r\n" + "<thead>\r\n" + "<th>Kid Email</th>\r\n"
				+ "<th>Kid UserName</th>\r\n" + "<th>Kid Password</th>\r\n" + "<th>Mobile Number</th>\r\n"
				+ "<th>Kid Class Standard</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + parentEmail
				+ "</td>\r\n" + "<td>" + kidUserName + "</td>\r\n" + "<td>" + kidPassword + "</td>\r\n" + "<td>"
				+ mobileNumber + "</td>\r\n" + "<td>Class-" + classStandard + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>\r\n" + "</center>\r\n" + "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n"
				+ "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(parentEmail);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Welcome Email Sent to Parent...");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendTeacherCredentialsEmail(String emailId, String password, String fullName, String mobileNumber) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:1px solid lightgrey;\r\n"
				+ "border-radius:20px;\r\n" + "background-color:#111;\r\n" + "padding:15px;\r\n" + "color:white;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n"
				+ "\r\n" + "th, td {\r\n" + "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to Vistas Learning E-Learning Platform. You have been Registered as Teacher. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<center style=\"margin-top:4%;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Email</th>\r\n" + "<th>UserName</th>\r\n" + "<th>Password</th>\r\n"
				+ "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + emailId
				+ "</td>\r\n" + "<td>" + emailId + "</td>\r\n" + "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber
				+ "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "\r\n"
				+ "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(emailId);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Teacher Credentials Email Sent...");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document sendWelcomeEmailToStudentOnSuccessfulcreationByParent(String studentEmail, String fullName,
			String userName, String mobileNumber, String password, Long classStandard) {
		Document doc = new Document();
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n" + "* a{\r\n"
				+ "text-decoration:none;\r\n" + "}\r\n" + ".btn{\r\n" + "border:1px solid lightgrey;\r\n"
				+ "border-radius:20px;\r\n" + "background-color:#111;\r\n" + "padding:15px;\r\n" + "color:white;\r\n"
				+ "width:200px;\r\n" + "}\r\n" + "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n"
				+ "\r\n" + "th, td {\r\n" + "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to Vistas Learning E-Learning Platform. You have been Registered as Student. Your Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<center style=\"margin-top:6%;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Email</th>\r\n" + "<th>UserName</th>\r\n" + "<th>Password</th>\r\n"
				+ "<th>Mobile Number</th>\r\n" + "<th>Full Name</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + studentEmail + "</td>\r\n" + "<td>" + userName + "</td>\r\n" + "<td>" + password
				+ "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "<td>" + fullName + "</td>\r\n" + "</tr>\r\n"
				+ "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(studentEmail);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Welcome Email Sent to Student...");
			doc.setStatusCode(200);
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@Override
	public void sendLeadBatchFreeCourseInterestEmail(String leadName, String leadEmail, String leadPhone,
			String classStandadName, String subjectName, String syllabusName, String teacherName, String prefferedTime,
			String prefferedDate) {
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n"
				+ "    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\r\n"
				+ "    <link href=\"https://fonts.googleapis.com/css2?family=Lato&display=swap\" rel=\"stylesheet\">\r\n"
				+ "    <style>\r\n" + "        .footer-text {\r\n" + "            text-align: justify;\r\n"
				+ "            margin-top: 5%;\r\n" + "            margin-left: 2%;\r\n" + "        }\r\n" + "\r\n"
				+ "        table,\r\n" + "        th,\r\n" + "        td {\r\n"
				+ "            border: 1px solid black;\r\n" + "        }\r\n" + "\r\n" + "        th,\r\n"
				+ "        td {\r\n" + "            padding: 10px;\r\n" + "        }\r\n" + "\r\n"
				+ "        body {\r\n" + "            margin: 0px;\r\n" + "        }\r\n" + "\r\n" + "        * {\r\n"
				+ "            font-family: 'Lato', sans-serif;\r\n" + "            font-size: 16px;\r\n"
				+ "            text-align: justify;\r\n" + "        }\r\n" + "\r\n" + "        .logo {\r\n"
				+ "            border: 1px solid #eee;\r\n" + "            box-shadow: 2px 3px #eee;\r\n"
				+ "            width: fit-content;\r\n" + "            background: black;\r\n"
				+ "            margin-left: 2%;\r\n" + "            margin-top: 2%;\r\n" + "        }\r\n"
				+ "    </style>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "    <div>\r\n"
				+ "        <div class=\"logo\">\r\n"
				+ "            <img src=\"https://i.ibb.co/VWdwg01/v-learning-logo-new.png\" alt=\"v-learning-logo-new\"></img>\r\n"
				+ "        </div>\r\n" + "        <pre>\r\n" + "        Hey " + leadName + ", \r\n"
				+ "                 Thanks for Showing Interest. We will notify you in the below details when the next live class will start.\r\n"
				+ "        </pre>\r\n" + "\r\n" + "        <center style=\"margin-top: 4%;\">\r\n"
				+ "            <table>\r\n" + "                <thead>\r\n" + "                    <tr>\r\n"
				+ "                        <th>Name</th>\r\n" + "                        <th>Email</th>\r\n"
				+ "                        <th>Phone</th>\r\n"
				+ "                        <th>Choosen Class Standard</th>\r\n"
				+ "                        <th>Subject</th>\r\n" + "                        <th>Syllabus</th>\r\n"
				+ "                        <th>Selected Teacher</th>\r\n"
				+ "                        <th>Preferred Time</th>\r\n"
				+ "                        <th>Preffered Date</th>\r\n" + "                    </tr>\r\n"
				+ "                </thead>\r\n" + "                <tbody>\r\n" + "                    <tr>\r\n"
				+ "                        <td>" + leadName + "</td>\r\n" + "                        <td>" + leadEmail
				+ "</td>\r\n" + "                        <td>" + leadPhone + "</td>\r\n"
				+ "                        <td>" + classStandadName + "</td>\r\n" + "                        <td>"
				+ subjectName + "</td>\r\n" + "                        <td>" + syllabusName + "</td>\r\n"
				+ "                        <td>" + teacherName + "</td>\r\n" + "                        <td>"
				+ prefferedTime + "</td>\r\n" + "                        <td>" + prefferedDate + "</td>\r\n"
				+ "                    </tr>\r\n" + "                </tbody>\r\n" + "            </table>\r\n"
				+ "        </center>\r\n" + "        <p class=\"footer-text\">\r\n"
				+ "            Thanks & Regards,<br>\r\n" + "           Vistas Learning Team,<br>\r\n"
				+ "            <a style=\"color:black;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\"\r\n"
				+ "                target=\"_blank\">Link to the Official Website</a>\r\n" + "        </p>\r\n"
				+ "    </div>\r\n" + "</body>\r\n" + "\r\n" + "</html>";
		String subject = "Vistas Learning :: Thanks For Showing Interest in Demo Class";

		// Get properties object
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(leadEmail);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public void sendEmailToTelecallerWithCredentials(String email, String fullName, String userName,
			String mobileNumber, String role, String password) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<center><img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img></center>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>Telecaller</b>. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Password</th>\r\n" + "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + fullName + "</td>\r\n" + "<td>" + email + "</td>\r\n" + "<td>" + userName + "</td>\r\n"
				+ "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to VistasLearning";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public void sendEmailToModeratorWithCredentials(String email, String fullName, String userName, String mobileNumber,
			String role, String password) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>Moderator</b>. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Password</th>\r\n" + "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + fullName + "</td>\r\n" + "<td>" + email + "</td>\r\n" + "<td>" + userName + "</td>\r\n"
				+ "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to VistasLearning";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public void sendEmailToBloggerWithCredentials(String email, String fullName, String userName, String mobileNumber,
			String role, String password) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>Blogger</b>. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Password</th>\r\n" + "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + fullName + "</td>\r\n" + "<td>" + email + "</td>\r\n" + "<td>" + userName + "</td>\r\n"
				+ "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n"
				+ "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to VistasLearning";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendEmailToStudentWithCredentialsAfterBookingFreeClass(String studentEmail, String fullName,
			String userName, String mobileNumber, String role, String password) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>Student</b>. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Password</th>\r\n" + "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + fullName + "</td>\r\n" + "<td>" + studentEmail + "</td>\r\n" + "<td>" + userName
				+ "</td>\r\n" + "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n"
				+ "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to VistasLearning";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(studentEmail);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendEmailToParentWithCredentialsAfterBookingFreeClass(String parentEmail, String parentFullName,
			String parentUserName, String mobileNumber, String role, String password) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + parentFullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>Parent</b>. Your Login Details are mentioned below. \r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n"
				+ "<table>\r\n" + "<thead>\r\n" + "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n"
				+ "<th>Password</th>\r\n" + "<th>Mobile Number</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n"
				+ "<td>" + parentFullName + "</td>\r\n" + "<td>" + parentEmail + "</td>\r\n" + "<td>" + parentUserName
				+ "</td>\r\n" + "<td>" + password + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n"
				+ "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to VistasLearning";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(parentEmail);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}

	@Override
	public void sendEmailToAllStudentsForJoiningDemoClass(String firstName, String email,
			String attendeeJoinMeetingUrlActual, Long idDemoClass, Long idClassStandard, Long idSubject,
			Long idSubjectChapter, Long idSyllabus, LocalTime fromTime, LocalTime toTime) {

		ClassStandard classStandard = classStandardRepo.findByIdClassStandard(idClassStandard);
		Subject classSubject = subjectRepo.findByIdSubject(idSubject);
		SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapter(idSubjectChapter);

		Syllabus syllabus = syllabusRepository.findByIdSyllabus(idSyllabus);

		String joinClassHref = angularUrl + "/redirection/join-demo-class-redirect";

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "button{\r\n" + "background:#555555;\r\n" + "color:white;\r\n"
				+ "cursor:pointer;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + firstName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Welcome to vistaslearning.com. You have been registered for the below Demo Class. The Demo Class has been started. Please Click on the Join button to join the class.\r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Demo Class Details</h5>\r\n"
				+ "<center style=\"margin-top:4%;overflow-x:auto;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Class Standard</th>\r\n" + "<th>Subject</th>\r\n" + "<th>Topic</th>\r\n"
				+ "<th>Syllabus</th>\r\n" + "<th>From Time</th>\r\n" + "<th>To Time</th>\r\n" + "<th>Action</th>\r\n"
				+ "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + classStandard.getClassStandadName()
				+ "</td>\r\n" + "<td>" + classSubject.getSubjectName() + "</td>\r\n" + "<td>"
				+ subjectChapter.getChapterName() + "</td>\r\n" + "<td>" + syllabus.getSyllabusName() + "</td>\r\n"
				+ "<td>" + fromTime + "</td>\r\n" + "<td>" + toTime + "</td>\r\n" + "<td><a href=" + joinClassHref
				+ " target=\"_blank\">Join Now</a></td>\r\n" + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n"
				+ "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n"
				+ "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Demo CLass Has been Started. Please join";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document sendNotificationEmailwhenDemoClassAssigned(String email, String fullName, String scheduleDate,
			String timings, String subjectName) {
		Document doc = new Document();

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:18px;\r\n" + "font-family:sans-serif;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "<body>\r\n" + "<center>\r\n" + "<h4><u>Vistas Learning</u></h4>\r\n" + "</center>\r\n"
				+ "\r\n" + "<p style=\"margin-top:20px\">\r\n" + "Dear " + fullName + ",<br><br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your Demo class has been scheduled at &nbsp;" + scheduleDate
				+ " Following is the scheduled detail " + "\r\n" + "</p>\r\n" + "\r\n"
				+ "<center style=\"margin-top:100px\">\r\n"
				+ "<div style=\"border:1px solid #111;border-radius:10px;background:#eee;width:500px;padding:10px\">\r\n"
				+ "<b>" + subjectName + " " + scheduleDate + " " + timings + "</b>\r\n" + "</div>\r\n" + "</center>\r\n"
				+ "\r\n" + "<footer style=\"margin-top:30px\">\r\n" + "Thanks & Regards,<br>\r\n"
				+ "Vistas Learning Team\r\n" + "</footer>\r\n" + "\r\n" + "</body>";
		String subject = "Vistas Learning :: Confirmation Notification";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("Success");
			doc.setMessage("Email Sent Success");
			doc.setStatusCode(200);
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}
	
	public void sendNotificationEmailwhenLiveClassStarts(String email, String fullName,String liveClassHeading,Long idLiveClass) {
		//String joinClassHref = angularUrl + "/live-course";
		String joinClassHref =angularUrl+"/redirection/join-live-class-redirect/"+idLiveClass;
		
		String msgBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "p {"
				+ "margin: 0; font-size: 16px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin-top: 0; margin-bottom: 0;"
				+ "font-family: Roboto,\"Helvetica Neue\", sans-serif;" + "}" + "</style>" + "</head>" + "<body>" + ""
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>"
				+ "</br>" + "<h3>Dear " + fullName + ",</h3>" + "<p >This is a reminder to inform you that '<strong>"
				+ liveClassHeading + "</strong>' live class has been started " +".</p>" + "<br>"
				+ "<a href=\""+joinClassHref+"\" style=\"display: inline-block; background: #e85034; color: #ffffff; font-family: Ubuntu, Helvetica, Arial, sans-serif, Helvetica, Arial, sans-serif; font-size: 13px; font-weight: normal; line-height: 100%; margin: 0; text-decoration: none; text-transform: none; padding: 9px 26px 9px 26px; mso-padding-alt: 0px; border-radius: 24px;\" target=\"_blank\">"
				+"Join Now"+"</a>"
				+"<br>"
				+ "<p>Thanks & Regards,<br/>Vistas Learning Team</p>"  	;

		String subject = "Vistas Learning :: " + liveClassHeading + " Live Class ";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

			

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());

		}
	}
	
	public void sendTicketResponseEmailNotification(String email, String fullName,Long ticketNo,String adminResponse ,OrderTicketStatus status) {
		//String joinClassHref = angularUrl + "/live-course";
		
		String msgBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "p {"
				+ "margin: 0; font-size: 16px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin-top: 0; margin-bottom: 0;"
				+ "font-family: Roboto,\"Helvetica Neue\", sans-serif;" + "}" + "</style>" + "</head>" + "<body>" + ""
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://i.ibb.co/cbWyKbP/v-learning-logo-new.png\" alt=\"vlearning_logo\"></img>"
				+ "</br>" + "<h3>Dear " + fullName + ",</h3>" + "<p > The update of the issue with respect to the ticket no : <strong>"+ ticketNo +"</strong> is <br>"
				+ "<strong>"+adminResponse + "</strong>'" +".</p>" + "<br>"
				+ "<strong> TICKET STATUS :"+ status +"</strong>"
				+ "<p>"
				+ "If you have any further query on this order, please reply to this mail or call us on toll free no 1800 419 3629" 
				+ "</p>"
			
				+"<br>"
				+ "<p>Thanks & Regards,<br/>Vistas Learning Team</p>"  	;

		String subject = "Vistas Learning :: Ticket No: " + ticketNo ;

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

			

		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public void sendWelcomeEmailOnSuccessfulSignUp(String email, String fullName, String userName, String mobileNumber,
			String role) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + fullName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to vistaslearning.com. You have been Registered as <b>"
				+ role + "</b>. Your Login Details are mentioned below. \r\n" + "<br>\r\n" + "<br>\r\n"
				+ "<h5>Login Details</h5>\r\n" + "<center style=\"margin-top:4%;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Name</th>\r\n" + "<th>Email</th>\r\n" + "<th>UserName</th>\r\n" + "<th>Mobile Number</th>\r\n"
				+ "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + fullName + "</td>\r\n" + "<td>" + email
				+ "</td>\r\n" + "<td>" + userName + "</td>\r\n" + "<td>" + mobileNumber + "</td>\r\n" + "</tr>\r\n"
				+ "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendBookDemoClassWelcomeEmail(String firstName, String email, String mobileNumber,
			String classStandadName, String subjectName, String syllabusName, String dayOfWeek, LocalTime fromTime,
			LocalTime toTime, String language, String chapterName) {

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + firstName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Thanks for Showing interest in booking demo class. We will let you know the actual date and time for the same.\r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Booked Demo Class Details</h5>\r\n"
				+ "<center style=\"margin-top:4%;overflow-x:auto;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Email</th>\r\n" + "<th>Mobile Number</th>\r\n" + "<th>Class Standard</th>\r\n"
				+ "<th>Subject</th>\r\n" + "<th>Topic</th>\r\n" + "<th>Syllabus</th>\r\n"
				+ "<th>Preferred Language</th>\r\n" + "<th>Preferred Day</th>\r\n" + "<th>From Time</th>\r\n"
				+ "<th>To Time</th>\r\n" + "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + email + "</td>\r\n"
				+ "<td>" + mobileNumber + "</td>\r\n" + "<td>" + classStandadName + "</td>\r\n" + "<td>" + subjectName
				+ "</td>\r\n" + "<td>" + chapterName + "</td>\r\n" + "<td>" + syllabusName + "</td>\r\n" + "<td>"
				+ language + "</td>\r\n" + "<td>" + dayOfWeek + "</td>\r\n" + "<td>" + fromTime + "</td>\r\n" + "<td>"
				+ toTime + "</td>\r\n" + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n" + "</center>\r\n" + "\r\n"
				+ "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: Thanks for Showing Interest";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendOTPForSignUp(String email, String name, String OTP) {
		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:5%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<center><img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img></center>\r\n"
				+ "<p>Dear " + name + ",<br><br>\r\n" + "Welcome to vistaslearning.com. Use <b>" + OTP
				+ "</b> as One Time Password (OTP) to successful sign-up. \r\n" + "<p class=\"footer-text\">\r\n"
				+ "Thanks & Regards,<br>\r\n" + "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "</body>\r\n" + "</html>";
		String subject = "Vistas Learning :: One Time Password (OTP)";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendEmailToAllStudentsForJoiningDemoClassExtraCurricular(String firstName, String email,
			String attendeeJoinMeetingUrlActual, Long idDemoClassExtraCurricular, Long idSubjectExtraCurricular,
			LocalTime fromTime, LocalTime toTime) {

		Subject classSubject = subjectRepo.findByIdSubject(idSubjectExtraCurricular);

		String joinClassHref = angularUrl + "/redirection/join-demo-class-extra-curricular-redirect";

		String msgBody = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<style>\r\n" + "*{\r\n"
				+ "font-size:16px;\r\n" + "font-family: Roboto,\"Helvetica Neue\", sans-serif;\r\n" + "}\r\n"
				+ ".footer-text{\r\n" + "text-align:justify;\r\n" + "margin-top:10%;\r\n" + "}\r\n"
				+ "table, th, td {\r\n" + "  border: 1px solid black;\r\n" + "}\r\n" + "\r\n" + "th, td {\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "button{\r\n" + "background:#555555;\r\n" + "color:white;\r\n"
				+ "cursor:pointer;\r\n" + "}\r\n" + "</style>\r\n" + "</head>\r\n" + "<body>\r\n" + "<center>\r\n"
				+ "<h4>Vistas Learning</h4>\r\n" + "</center>\r\n"
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>\r\n"
				+ "<p>Dear " + firstName + ",<br>\r\n"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Welcome to vistaslearning.com. You have been registered for the below Extra Curricular Demo Class. The Demo Class has been started. Please Click on the Join link to join the class.\r\n"
				+ "<br>\r\n" + "<br>\r\n" + "<h5>Demo Class Details</h5>\r\n"
				+ "<center style=\"margin-top:4%;overflow-x:auto;\">\r\n" + "<table>\r\n" + "<thead>\r\n"
				+ "<th>Subject</th>\r\n" + "<th>From Time</th>\r\n" + "<th>To Time</th>\r\n" + "<th>Action</th>\r\n"
				+ "</thead>\r\n" + "<tbody>\r\n" + "<tr>\r\n" + "<td>" + classSubject.getSubjectName() + "</td>\r\n"
				+ "<td>" + fromTime + "</td>\r\n" + "<td>" + toTime + "</td>\r\n" + "<td><a href=" + joinClassHref
				+ " target=\"_blank\">Join Now</a></td>\r\n" + "</tr>\r\n" + "</tbody>\r\n" + "</table>\r\n"
				+ "</center>\r\n" + "\r\n" + "<p class=\"footer-text\">\r\n" + "Thanks & Regards,<br>\r\n"
				+ "Vistas Learning Team,<br>\r\n"
				+ "<a style=\"color:orange;cursor:pointer;font-size:14px;\" href=\"https://vistaslearning.com\" target=\"_blank\">Link to the Official Website</a>\r\n"
				+ "</p>\r\n" + "</p>\r\n" + "\r\n" + "</body>\r\n" + "</html>";

		String subject = "Vistas Learning :: " + classSubject.getSubjectName() + " Demo CLass Has been Started. Please join";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendLiveClassReminderNotification(String firstName, String header, String fromTime, String email) {

		String msgBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "p {"
				+ "margin: 0; font-size: 16px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin-top: 0; margin-bottom: 0;"
				+ "font-family: Roboto,\"Helvetica Neue\", sans-serif;" + "}" + "</style>" + "</head>" + "<body>" + ""
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>"
				+ "</br>" + "<h3>Dear " + firstName + ",</h3>" + "<p >This is a reminder to inform you that '<strong>"
				+ header + "</strong>' will be live today at " + fromTime + ".</p>" + "<br> <br>"
				+ "<p>Thanks & Regards,<br/>Vistas Learning Team</p>" + "" + "</body>" + "</html>";

		String subject = "Vistas Learning :: " + header + " Live Class going start soon.";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	@Override
	public void sendNotificationEmailWhenBatchStarts(String firstName, String email, String batchName, Long idBatch) {
		
		String joinClassHref =angularUrl+"/redirection/redirect-join-personalcoaching/"+idBatch;
	
		String msgBody ="<!DOCTYPE html>"+"<html>"+"<head>"+"<style>"+"p {"+"margin: 0; "+"font-size: 16px; "+"line-height: 1.2; "+"word-break: break-word; "+
				"mso-line-height-alt: 14px; "+"margin-top: 0; "+
				"margin-bottom: 0;"+"font-family: Roboto,\"Helvetica Neue\", sans-serif;"+"}"+"</style>"+"</head>"+"<body>"+
				"<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/v_learning_logo_new.png\" alt=\"vlearning_logo\"></img>"+
				"</br>"+"<h3>Dear "+firstName +", </h3>"+"<p >This is a reminder to inform you that your class <strong> "+ batchName +"</strong> has been started. Please join for the session.</p>"+
				"<br> <br>"
				+ "<a href=\""+joinClassHref+"\" style=\"display: inline-block; background: #e85034; color: #ffffff; font-family: Ubuntu, Helvetica, Arial, sans-serif, Helvetica, Arial, sans-serif; font-size: 13px; font-weight: normal; line-height: 100%; margin: 0; text-decoration: none; text-transform: none; padding: 9px 26px 9px 26px; mso-padding-alt: 0px; border-radius: 24px;\" target=\"_blank\">"
				+"Join Now"+"</a>"
				+"<br> <br> <br>"
				+"<p>Thanks & Regards,<br/>Vistas Learning Team</p>"+"</body>"+"</html>";
					
		
		String subject = "Vistas Learning :: " + batchName + " Class Started.";
		
		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}

	}
	
	@Override
	public void sendGlobalEmail(String email, String fullName,String heading, String message) throws Exception {
		//String joinClassHref = angularUrl + "/live-course";
		
		String msgBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "p {"
				+ "margin: 0; font-size: 16px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin-top: 0; margin-bottom: 0;"
				+ "font-family: Roboto,\"Helvetica Neue\", sans-serif;" + "}" + "</style>" + "</head>" + "<body>" + ""
				+ "<img style=\"background: rgba(8,7,88,.9);padding: 8px;height: 7vh;border-radius: 8px;\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/main_logo.png\" alt=\"vlearning_logo\"></img>"
				+ "</br>" + "<h3>Dear " + fullName + ",</h3>" 
				+ "<strong>"+heading + "</strong>" +".</p>" + "<br>"
				+ "<p> "
				+message+
				 " </p>"
				+"<br>"
				+ "<p>Thanks & Regards,<br/>Vistas Learning Team</p>"  	;


			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(heading);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

	}

	

	@Override
	public Document<?> sendInvoceThroughEmail(String orderId) {
		

		 Document<String> doc = new Document<>();
			
		    StagingStudentSubscription sss = stagingStudentSubscriptionRepository.getByOrderId(orderId);

		    if (sss == null) {
		        doc.setData(null);
		        doc.setStatusCode(500);
		        doc.setMessage("Student Subscription not found.");
		        return doc;
		    }
		    String orderStatus= sss.getPaymentStatus();

		    String amount = sss.getPurchaseAmount();
		    
		    User user = userRepository.findByUserSurId(sss.getUserSurId());
		    
		    if (user == null)
		    	throw new AppException("Invalid User data found.");
		    
	    
	    Instant purchaseDateTime = sss.getPurchaseDate()==null ?sss.getCreatedAt():sss.getPurchaseDate();
	    LocalDateTime purchaseDate = LocalDateTime.ofInstant(purchaseDateTime, ZoneId.systemDefault());
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String formattedPurchaseDate = formatter.format(purchaseDate);


	    String subscriptionType= sss.getSubscriptionType();
	    String transactionId = sss.getBankTransactionId()==null ?"--":sss.getBankTransactionId() ;
	    
	    String particulars = ("Vistas Learning Premium "+subscriptionType +" Subscription").toUpperCase();
	    
	    String bankName = sss.getBankName()==null ?"--":sss.getBankName();
	    String paymentMode =sss.getPaymentMode()==null ?"--":sss.getPaymentMode();
	    Instant nextPaymentDateTime=sss.getNextPaymentDate()==null ? null :sss.getNextPaymentDate();
	    LocalDateTime nextPayementDate = null;
	    if (nextPaymentDateTime != null) 
	    {
	    	nextPayementDate = LocalDateTime.ofInstant(nextPaymentDateTime, ZoneId.systemDefault());
	    }
	    String nextSunscriptionDate = nextPayementDate == null ? "--":formatter.format(nextPayementDate);
	   
	    

	    if (amount == null || formattedPurchaseDate == null || particulars == null || user == null) {
	        doc.setData(null);
	        doc.setStatusCode(500);
	        doc.setMessage("Incomplete subscription details");
	        return doc;
	    }

	    String custName = user.getFirstName() != null ? user.getFirstName() : "";
	    String custPhone = user.getMobileNumber() != null ? user.getMobileNumber() : "";
	    String email=user.getEmail() !=null ? user.getEmail(): " ";
	    float amountFloat = Float.parseFloat(amount);
	    float cgst = (amountFloat * 9) / 100;
	    float sgst = (amountFloat * 9) / 100;
	    float priceWithoutGST = (amountFloat * 82) / 100;
	    float taxableValue = (amountFloat * 18) / 100;
	    float rate = priceWithoutGST;
	   
	    String paymentSuccessMessage="<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "\n"
				+ "<head>\n"
				+ "	<meta charset=\"UTF-8\">\n"
				+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "	<style>\n"
				+ "		.container {\n"
				+ "			background-color: #ededed\n"
				+ "		}\n"
				+ "\n"
				+ "		.main-div {\n"
				+ "			padding-top: 3%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.header-heading {\n"
				+ "			text-align: center;\n"
				+ "			width: 100%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.address-col-right {\n"
				+ "			text-align: end;\n"
				+ "		}\n"
				+ "\n"
				+ "		thead {\n"
				+ "			background-color: #8ea9db;\n"
				+ "		}\n"
				+ "\n"
				+ "		.last-col {\n"
				+ "			background-color: #f7caac;\n"
				+ "			text-align: center;\n"
				+ "		}\n"
				+ "\n"
				+ "		.table td {\n"
				+ "			padding: 2px 5px;\n"
				+ "			border: 2px solid #000 !important;\n"
				+ "			border-collapse: collapse;\n"
				+ "		}\n"
				+ "\n"
				+ "		.table {\n"
				+ "			border: 2px solid #000 !important;\n"
				+ "			border-collapse: collapse;\n"
				+ "		}\n"
				+ "\n"
				+ "		.my-table td {\n"
				+ "			padding: 2px 5px;\n"
				+ "			border: 2px solid #000 !important;\n"
				+ "			border-collapse: collapse;\n"
				+ "\n"
				+ "		}\n"
				+ "\n"
				+ "		.my-table {\n"
				+ "			border: 2px solid #000 !important;\n"
				+ "			border-collapse: collapse;\n"
				+ "\n"
				+ "		}\n"
				+ "\n"
				+ "		.footer {\n"
				+ "			text-align: center;\n"
				+ "		}\n"
				+ "\n"
				+ "		.mt-5 {\n"
				+ "			margin-top: 5%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.mt-10 {\n"
				+ "			margin-top: 10%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.mt-20 {\n"
				+ "			margin-top: 20%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.col-2 {\n"
				+ "			display: inline-block;\n"
				+ "			width: 48%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.col-6 {\n"
				+ "			display: inline-block;\n"
				+ "			width: 49%;\n"
				+ "		}\n"
				+ "\n"
				+ "		.container {\n"
				+ "			padding: 2% 10%;\n"
				+ "		}\n"
				+ "\n"
				+ "		h4 {\n"
				+ "			margin: 10px 0px;\n"
				+ "		}\n"
				+ "\n"
				+ "		.tbl1 td {\n"
				+ "			padding: 5px 10px;\n"
				+ "		}\n"
				+ "\n"
				+ "		.txt-center {\n"
				+ "			text-align: center;\n"
				+ "		}\n"
				+ "\n"
				+ "		.tbl1 th {\n"
				+ "			padding: 5px 10px;\n"
				+ "			border: 2px solid #000 !important;\n"
				+ "			border-collapse: collapse;\n"
				+ "		}\n"
				+ "\n"
				+ "		@media only screen and (max-width: 600px) {\n"
				+ "			.top-col {\n"
				+ "				width: 78%\n"
				+ "			}\n"
				+ "		}\n"
				+ "\n"
				+ "		.top-col {\n"
				+ "			text-align:center;\n"
				+ "		}\n"
				+ "p{"
				+ "font-size:12pt;\n"
				+ "}"
				+ ".address-col-right{margin-left:-10px;}"
				+ ".dm-h2{font-size:16pt;}"
				+ "        @media (max-width: 490px) {\n"
				+ "            .header-heading {\n"
				+ "                margin-right: 0;\n"
				+ "                margin-left: 50px;\n"
				+ "            }\n"
				+ "\n"
				+ "            .container {\n"
				+ "                padding: 2% 2%;\n"
				+ "                width: fit-content;\n"
				+ "            }\n"
				+ "\n"
				+ "            img{\n"
				+ "                margin-left: 48px;\n"
				+ "            }\n"
				+ "\n"
				+ "            .address-col-left{\n"
				+ "                font-size: 1pt;\n"
				+ "            }\n"
				+ "            \n"
				+ "            .bill-details{\n"
				+ "                font-size: xx-small;\n"
				+ "            }\n"
				+ "        }"
				+ "	</style>\n"
				+ "</head>\n"
				+ "\n"
				+ "<body>\n"
				+ "	<div class=\"container main-div\"><br>\n"
				+ "\n"
				+ "		<div class=\"top-col\"><br>\n"
				+ "			<img width=\"300px\" src='https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/vlearning_blue.png'>\n"
				+ "		</div>\n"
				+ "		<br>\n"
				+ "		<div class=\" top-col\">\n"
				+ "			<div class=\"header-heading\" style=\" color:black important;\">\n"
				+ "				<h2 class=\"dm-h2\">DREAMMITRA PRIVATE LIMITED</h2>\n"
				+ "				<p><b>LIVE YOUR DREAM, TRUST THE PROCESS</b></p>\n"
				+ "				<p><b>CIN: U72900KA2018PTC118831</b></p>\n"
				+ "			</div>\n"
				+ "\n"
				+ "		</div>\n"
				+ "\n"
				+ "		<div class=\"address-details mt-5\">\n"
				+ "			<div class=\"row\">\n"
				+ "				<div class=\"col-6 address-col-left\">\n"
				+ "					<p>Invoice To,</p>\n"
				+ "					<div class=\"client-name\">\n"
				+ "						<p><b>Customer Name: </b>"+custName+"</p>\n"
				+ "					</div>\n"
				+ "					<div class=\"client-address\">\n"
				+ "						<p><b>Mobile No. </b>"+custPhone+"</p>\n"
				+ "					</div>\n"
				+ "				</div>\n"
				+ "				<div class=\"col-6 address-col-right\">\n"
				+ "					<div class=\"bill-details\">\n"
				+ "						<p><b>Bill No: </b>"+orderId+"</p>\n"
				+ "						<p><b>Invoice Type: </b>Tax Invoice</p>\n"
				+ "						<p><b>Date: </b> "+formattedPurchaseDate+"</p>\n"
				+ "					</div>\n"
				+ "				</div>\n"
				+ "			</div>\n"
				+ "		</div>\n"
				+ "		<div class=\"billing-details mt-10\">\n"
				+ "			<div class=\"bill-details-table\">\n"
				+ "				<div>\n"
				+ "					<table class=\"table tbl1 table-bordered\">\n"
				+ "						<thead>\n"
				+ "							<tr>\n"
				+ "								<th width=\"3%\" valign=\"top\">Sl No.</th>\n"
				+ "								<th width=\"40%\">Particulars</th>\n"
				+ "								<th width=\"5%\">Qty</th>\n"
				+ "								<th width=\"10%\">Rate</th>\n"
				+ "								<th width=\"10%\">Amount</th>\n"
				+ "							</tr>\n"
				+ "						</thead>\n"
				+ "						<tr>\n"
				+ "							<td>1</td>\n"
				+ "							<td>"+particulars+"</td>\n"
				+ "							<td>1</td>\n"
				+ "							<td class='txt-center'>"+rate+"</td>\n"
				+ "							<td class='txt-center'>"+priceWithoutGST+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td></td>\n"
				+ "							<td>Taxable Value</td>\n"
				+ "							<td></td>\n"
				+ "							<td></td>\n"
				+ "							<td class='txt-center'>"+taxableValue+"</td>\n"
				+ "						</tr>\n"
				+ "						<td></td>\n"
				+ "						<td>Add: CGST @ 9%</td>\n"
				+ "						<td></td>\n"
				+ "						<td></td>\n"
				+ "						<td class='txt-center'>"+cgst+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td></td>\n"
				+ "							<td>Add: SGST @ 9%</td>\n"
				+ "							<td></td>\n"
				+ "							<td></td>\n"
				+ "							<td class='txt-center'>"+sgst+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td></td>\n"
				+ "							<td>Round Off</td>\n"
				+ "							<td></td>\n"
				+ "							<td></td>\n"
				+ "							<td class='txt-center'>"+amount+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr class=\"last-col\">\n"
				+ "							<td colspan=\"4\">Total </td>\n"
				+ "							<td class='txt-center'>"+amount+"</td>\n"
				+ "						</tr>\n"
				+ "					</table>\n"
				+ "				</div>\n"
				+ "			</div>\n"
				+ "		</div>\n"
				+ "		<div class=\"bank-details mt-5\">\n"
				+ "			<div align='right'>\n"
				+ "				<div class=\"detail-table\">\n"
				+ "					<table class=\"my-table table-bordered \">\n"
				+ "						<tr>\n"
				+ "							<td><strong>Subscription Type:</strong></td>\n"
				+ "							<td>"+subscriptionType+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td><strong>Transaction ID:</strong></td>\n"
				+ "							<td>"+transactionId+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td><strong>Payment Mode:</strong></td>\n"
				+ "							<td>"+paymentMode+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td><strong>Bank:</strong></td>\n"
				+ "							<td>"+bankName+"</td>\n"
				+ "						</tr>\n"
				+ "						<tr>\n"
				+ "							<td><strong>Next Payment Date:</strong></td>\n"
				+ "							<td>"+nextSunscriptionDate+"</td>\n"
				+ "						</tr>\n"
				+ "					</table>\n"
				+ "				</div>\n"
				+ "			</div>\n"
				+ "		</div><br>\n"
				+ "		<div class=\"bottom-msg mt-10\">\n"
				+ "			<p><strong>For any order related queries please reach out to us at </strong> <a href='mailto:support@v-learning.in'>\n"
				+ "					support@v-learning.in</a> .<br><br>\n"
				+ "				<strong>Toll Free : </strong><a href='tel:+919945899989'>99458-99989 / </a> <a href='tel:+919945899937'>99458-99937</a>\n"
				+ "				<br></p>\n"
				+ "		</div>\n"
				+ "		<footer class=\"footer mt-20\">\n"
				+ "			<p> <a href='https://vistaslearning.com/'>www.vistaslearning.com</a> <br> 35/7, 2 nd Floor, NGR Layout, Roopena\n"
				+ "				Agrahara, Bommanahalli, Bengaluru - 560068</p>\n"
				+ "		</footer>\n"
				+ "	</div>\n"
				+ "            </div>\n"
				+ "</body>\n"
				+ "\n"
				+ "</html>";

		String subject = "Vistas Learning :: Invoice ";
		
	  

		String paymentFailureMessage="<!DOCTYPE html>\n"
				+ "    <html lang=\"en\">\n"
				+ " <head>\n"
				+ "   <meta charset=\"UTF-8\">\n"
				+ "   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "   <title>Document</title>\n"
				+ "   <!-- <link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css'> -->\n"
				+ " <style>\n"
				+ " .container{background-color:#f7f8f2}\n"
				+ "   .main-div{\n"
				+ "       padding-top:3%;\n"
				+ "       margin: 5%\n"
				+ "   }\n"
				+ "   .header-heading{\n"
				+ "       text-align: center;\n"
				+ "       width: 100%;\n"
				+ "   }\n"
				+ "   .address-col-right{\n"
				+ "       text-align: end;\n"
				+ "   }\n"
				+ "   .last-col{\n"
				+ "       background-color: #f7caac;\n"
				+ "       text-align: center;\n"
				+ "   }\n"
				+ "\n"
				+ "   .footer{\n"
				+ "       text-align: center;\n"
				+ "   }\n"
				+ "   \n"
				+ "   .mt-10{\n"
				+ "       margin-top: 10%;\n"
				+ "   }\n"
				+ "   .mt-20{\n"
				+ "       margin-top: 20%;\n"
				+ "   }\n"
				+ "   .col-2{\n"
				+ "       display: inline-block;\n"
				+ "       width: 60%;\n"
				
				+ "   }\n"
				+ "   .col-6{\n"
				+ "       margin-left: 0px;\n"
				+ "       display: inline-block;\n"
				+ "       width: 90%;\n"
				+ "   }\n"
				+ "   .container{\n"
				+ "       padding: 2% 10%;\n"
				+ "   }\n"
				+ "   h4{\n"
				+ "       margin: 10px 0px;\n"
				+ "   }\n"
				+ "   .top-header-msg{\n"
				+ "        width: 95%;\n"
				+ "        background-color: red;\n"
				+ "        font-size: 28px;\n"
				+ "        color: #fff;\n"
				+ "        padding: 5% 2%;\n"
				+ "        text-align: center;\n"
				+ "        margin-top: 4%;\n"
				+ "margin-left:2px"
				+ "   }\n"
				+ " \n"
				+ " .txt-center{text-align:center;}\n"
				+ "   .tbl1 th{\n"
				+ "       padding: 5px 10px;\n"
				+ "       border:2px solid #000 !important;\n"
				+ "       border-collapse: collapse;\n"
				+ "   }@media only screen and (max-width: 600px) {\n"
				+ "   .top-col{width:78%}\n"
				+ " }\n"
				+ ".top-col{"
				+ "text-align:center;"
				+ "}\n"
				+ "p{"
				+ "font-size:12pt;\n"
				+ "}"
				+ ".dm-h2{font-size:16pt}"
				+ "        @media (max-width: 490px) {\n"
				+ "            .header-heading {\n"
				+ "                margin-right: 0;\n"
				+ "                margin-left: 50px;\n"
				+ "            }\n"
				+ "\n"
				+ "            .container {\n"
				+ "                padding: 2% 2%;\n"
				+ "                width: fit-content;"
				+ "margin-left: 2px;\n"
				+ "            }\n"
				+ "\n"
				+ "            img{\n"
				+ "                margin-left: 48px;\n"
				+ "            }\n"
				+ "\n"
				+ "            .address-col-left{\n"
				+ "                font-size: 1pt;\n"
				+ "            }\n"
				+ "            \n"
				+ "            .bill-details{\n"
				+ "                font-size: xx-small;\n"
				+ "            }\n"
				+ "        }"
				+ " </style>\n"
				+ "</head>\n"
				+ "\n"
				+ "<body>\n"
				+ "   <div class=\"container main-div\"><br>\n"
		
				+ "           <div class=\"top-col\">\n"
				+ "               <img width=\"300px\" src='https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/vlearning_blue.png'>\n"
				+ "           </div>\n"
				+ "<br>"
				+ "           <div class=\" top-col\">\n"
				+ "               <div class=\"header-heading\">\n"
				+ "                   <h2 class=\"dm-h2\">DREAMMITRA PRIVATE LIMITED</h2>\n"
				+ "                   <p>LIVE YOUR DREAM, TRUST THE PROCESS<br>CIN: U72900KA2018PTC118831</p>\n"
				+ "               </div>\n"
				+ "\n"
				+ "           </div>\n"

				+ "\n"
				+ "       <div class=\"top-header-msg\">\n"
				+ "            <span>Payment Failed </span>\n"
				+ "       </div>\n"
				+ "     <br>  \n"
				+ "\n"
				+ "				<p> <strong>Don't Worry your money is safe! </strong> If money was debited from your account, \n"
				+ "                    it will be refunded automatically in 5-7 working days!</p><br>\n"
				+ "       <p><strong>Order Id : </strong> "+orderId+" </p>\n"
				+ "       <p><strong>Date : </strong>"+formattedPurchaseDate+" </p><br> \n"
				+ "\n"
				+ "\n"

				+ "        <div class=\"bottom-msg mt-10\">\n"
				+ "            <p><strong>For any order related queries please reach out to us at</strong> <a href='mailto:support@v-learning.in'> support@v-learning.in</a> .<br><br>\n"
				+ "            <strong>Toll Free : </strong><a href='tel:+919945899989'>99458-99989  /  </a> <a href='tel:+919945899937'>99458-99937</a> <br></p>\n"
				+ "        </div>\n"
				+ "        <footer class=\"footer mt-20\">\n"
				+ "            <p> <a href='https://vistaslearning.com/'>www.vistaslearning.com</a> <br> 35/7, 2 nd Floor, NGR Layout, Roopena Agrahara, Bommanahalli, Bengaluru - 560068</p>\n"
				+ "        </footer>\n"
				+ "           </div>\n"
				+ "       </body>\n"
				+ "       \n"
				+ "       </html>";
		
		try {
			if(orderStatus.equalsIgnoreCase("Success")){
				SendMailDTOWithAttachment sendMailDTO = new SendMailDTOWithAttachment();
				sendMailDTO.setMessageBody(paymentSuccessMessage);
				sendMailDTO.setSubject(subject);
				sendMailDTO.setToAddress(email);
				sendMailDTO.setByteArrayInputStream(pdfInvoiceService.generatePdf(orderId));
				emailServiceUtil.sendEmailWithAttachment(sendMailDTO);

				doc.setData("Success");
				doc.setMessage("Email Sent Success");
				doc.setStatusCode(HttpStatus.OK.value());
			}
			else 
			{
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(paymentFailureMessage);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);

			doc.setData("fail");
			doc.setMessage("Email Sent Success with failure message");
			doc.setStatusCode(HttpStatus.OK.value());
			
			} 
			return doc;	
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}

	}

	@Override
	public void sendWelcomeEmailOnSuccessfulSignUpWithCredentials(String email, String fullName, String userName, String mobileNumber,
			String password, String state, String classStandard , String syllabus ) {
		String msgBody="<!doctype html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "    <title>\r\n"
				+ "    </title>\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "        #outlook a {\r\n"
				+ "            padding: 0;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        .ReadMsgBody {\r\n"
				+ "            width: 100%;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        .ExternalClass {\r\n"
				+ "            width: 100%;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        .ExternalClass * {\r\n"
				+ "            line-height: 100%;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        body {\r\n"
				+ "            margin: 0;\r\n"
				+ "            padding: 0;\r\n"
				+ "            -webkit-text-size-adjust: 100%;\r\n"
				+ "            -ms-text-size-adjust: 100%;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        table,\r\n"
				+ "        td {\r\n"
				+ "            border-collapse: collapse;\r\n"
				+ "            mso-table-lspace: 0pt;\r\n"
				+ "            mso-table-rspace: 0pt;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        img {\r\n"
				+ "            border: 0;\r\n"
				+ "            height: auto;\r\n"
				+ "            line-height: 100%;\r\n"
				+ "            outline: none;\r\n"
				+ "            text-decoration: none;\r\n"
				+ "            -ms-interpolation-mode: bicubic;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        p {\r\n"
				+ "            display: block;\r\n"
				+ "            margin: 13px 0;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "    <!--[if !mso]><!-->\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "        @media only screen and (max-width:480px) {\r\n"
				+ "            @-ms-viewport {\r\n"
				+ "                width: 320px;\r\n"
				+ "            }\r\n"
				+ "            @viewport {\r\n"
				+ "                width: 320px;\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "    <!--<![endif]-->\r\n"
				+ "    <!--[if mso]>\r\n"
				+ "        <xml>\r\n"
				+ "        <o:OfficeDocumentSettings>\r\n"
				+ "          <o:AllowPNG/>\r\n"
				+ "          <o:PixelsPerInch>96</o:PixelsPerInch>\r\n"
				+ "        </o:OfficeDocumentSettings>\r\n"
				+ "        </xml>\r\n"
				+ "        <![endif]-->\r\n"
				+ "    <!--[if lte mso 11]>\r\n"
				+ "        <style type=\"text/css\">\r\n"
				+ "          .outlook-group-fix { width:100% !important; }\r\n"
				+ "        </style>\r\n"
				+ "        <![endif]-->\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "        @media only screen and (min-width:480px) {\r\n"
				+ "            .mj-column-per-100 {\r\n"
				+ "                width: 100% !important;\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "    </style>\r\n"
				+ "\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body style=\"background-color:#f9f9f9;\">\r\n"
				+ "    <div style=\"background-color:#f9f9f9;\">\r\n"
				+ "        <div style=\"background:#f9f9f9;background-color:#f9f9f9;Margin:0px auto;max-width:600px;\">\r\n"
				+ "\r\n"
				+ "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#f9f9f9;background-color:#f9f9f9;width:100%;\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td style=\"border-bottom:#0b59a5 solid 5px;direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "\r\n"
				+ "        </div>\r\n"
				+ "        <div style=\"background:#fff;background-color:#fff;Margin:0px auto;max-width:600px;\">\r\n"
				+ "\r\n"
				+ "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#fff;background-color:#fff;width:100%;\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td style=\"border:#dddddd solid 1px;border-top:0px;direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\r\n"
				+ "                            <div class=\"mj-column-per-100 outlook-group-fix\" style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:bottom;width:100%;\">\r\n"
				+ "\r\n"
				+ "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:bottom;\" width=\"100%\">\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\r\n"
				+ "                                            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px;\">\r\n"
				+ "                                                <tbody>\r\n"
				+ "                                                    <tr>\r\n"
				+ "                                                        <td style=\"width:64px;\">\r\n"
				+ "                                                            <img height=\"auto\" src=\"https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/logos/logo-square-dark.png\" style=\"border:0;display:block;outline:none;text-decoration:none;width:100%;\" width=\"64\" />\r\n"
				+ "\r\n"
				+ "                                                        </td>\r\n"
				+ "                                                    </tr>\r\n"
				+ "                                                </tbody>\r\n"
				+ "                                            </table>\r\n"
				+ "                                        </td>\r\n"
				+ "                                    </tr>\r\n"
				+ "\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-bottom:40px;word-break:break-word;\">\r\n"
				+ "\r\n"
				+ "                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:28px;font-weight:bold;line-height:1;text-align:center;color:#555;\">\r\n"
				+ "                                                Welcome to Vista's Learning\r\n"
				+ "                                            </div>\r\n"
				+ "\r\n"
				+ "                                        </td>\r\n"
				+ "                                    </tr>\r\n"
				+ "\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <td align=\"left\" style=\"font-size:0px;padding:10px 25px;word-break:break-word;\">\r\n"
				+ "                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:16px;line-height:22px;text-align:left;color:#555;\">\r\n"
				+ "                                                Dear Candidate.<br><br>\r\n"
				+ "                                                Welcome to Vistas Learning App! We are thrilled to have you join our online learning as a "+state+" State, "+classStandard+"th Class, "+syllabus+" Syllabus student. We believe that education should be accessible, engaging, and empowering, and we are committed to providing you with an exceptional learning experience <br>        \r\n"
				+ "                                                <br>To help you get started, here are your login credentials: <br>\r\n"
				+ "														Username: "+userName+"\n <br>"
				+ "														Password: "+password+"\r\n <br>"
				+ "                                                    <br>\r\n"
				+ "                                                    Please keep these credentials safe and secure. We recommend changing your password after logging in for the first time to ensure the utmost security of your account.\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </td>\r\n"
				+ "                                    </tr>\r\n"
				+ "                                    <tr>\r\n"
				+ "                                        <td align=\"center\" style=\"font-size:0px;padding:10px 25px;padding-top:30px;padding-bottom:50px;word-break:break-word;\">\r\n"
				+ "\r\n"
				+ "                                            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;line-height:100%;\">\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <a href=\"https://student.vistaslearning.com/login\" style=\"text-decoration: none;\">\r\n"
				+ "                                                        <div align=\"center\" bgcolor=\"#2F67F6\" role=\"presentation\" style=\"border:none;border-radius:3px;color:#ffffff;cursor:auto;padding:15px 25px; background:#0b59a5; width:77%;\" valign=\"middle\">\r\n"
				+ "                                                            <p style=\"background:#0b59a5;;color:#ffffff;font-family:'Helvetica Neue',Arial,sans-serif;font-size:15px;font-weight:normal;line-height:120%;Margin:0;text-decoration:none;text-transform:none;\">\r\n"
				+ "                                                                Login to Your Account\r\n"
				+ "                                                            </p>\r\n"
				+ "                                                        </div>\r\n"
				+ "                                                    </a>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </table>\r\n"
				+ "                                        </td>\r\n"
				+ "                                    </tr>\r\n"
				+ "                                </table>\r\n"
				+ "                            </div>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "\r\n"
				+ "        </div>\r\n"
				+ "        <div style=\"Margin:0px auto;max-width:600px;\">\r\n"
				+ "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\r\n"
				+ "                <tbody>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td style=\"direction:ltr;font-size:0px;padding:20px 0;text-align:center;vertical-align:top;\">\r\n"
				+ "                            <div class=\"mj-column-per-100 outlook-group-fix\" style=\"font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:bottom;width:100%;\">\r\n"
				+ "\r\n"
				+ "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\r\n"
				+ "                                    <tbody>\r\n"
				+ "                                        <tr>\r\n"
				+ "                                            <td style=\"vertical-align:bottom;padding:0;\">\r\n"
				+ "\r\n"
				+ "                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\r\n"
				+ "\r\n"
				+ "                                                    <tr>\r\n"
				+ "                                                        <td align=\"center\" style=\"font-size:0px;padding:0;word-break:break-word;\">\r\n"
				+ "\r\n"
				+ "                                                            <div style=\"font-family:'Helvetica Neue',Arial,sans-serif;font-size:12px;font-weight:300;line-height:1;text-align:center;color:#575757;\">\r\n"
				+ "                                                                Dreammithra Pvt Ltd, Bengaluru, India\r\n"
				+ "                                                            </div>\r\n"
				+ "                                                        </td>\r\n"
				+ "                                                    </tr>\r\n"
				+ "                                                    <tr>\r\n"
				+ "                                                        <td align=\"center\" style=\"font-size:0px;padding:10px;word-break:break-word;\">\r\n"
				+ "                                                        </td>\r\n"
				+ "                                                    </tr>\r\n"
				+ "                                                </table>\r\n"
				+ "                                            </td>\r\n"
				+ "                                        </tr>\r\n"
				+ "                                    </tbody>\r\n"
				+ "                                </table>\r\n"
				+ "                            </div>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </tbody>\r\n"
				+ "            </table>\r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "    </div>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>";
		
		String subject = "Vistas Learning :: Welcome to Vistas Learning E-Learning Platform";

		try {
			SendMailDTO sendMailDTO = new SendMailDTO();
			sendMailDTO.setMessageBody(msgBody);
			sendMailDTO.setSubject(subject);
			sendMailDTO.setToAddress(email);
			emailServiceUtil.sendEmail(sendMailDTO);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		
	}
	
}
	
	



