package co.vistafoundation.vlearning.email.service;

import java.time.LocalTime;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;

public interface EmailService {

	@SuppressWarnings("rawtypes")
	Document sendForgotPasswordEmail(String toAddress, String username, String randomString, String fullName,
			Long userSurId);

	@SuppressWarnings("rawtypes")
	Document sendForgotPasswordEmailInternal(String toAddress, String username, String randomString, String fullName,
			Long userSurId);

	@SuppressWarnings("rawtypes")
	Document sendVerificationMailForChangeMobileNumber(String email, String newMobile, Long userSurId,
			String randomString, String fullNAme);

	@SuppressWarnings("rawtypes")
	Document sendWelcomeEmailToStudentOnSuccessfulSignUp(String studentEmail, String fullName, String userName,
			String mobileNumber, String role, String classStandard);

	@SuppressWarnings("rawtypes")
	Document sendWelcomeEmailToParentOnSuccessfulSignUp(String parentEmail, String parentFullName,
			String parentUserName, String mobileNumber, String role, String classStandard, String kidUserName,
			String kidPassword);

	@SuppressWarnings("rawtypes")
	Document sendTeacherCredentialsEmail(String emailId, String password, String fullName, String mobileNumber);

	@SuppressWarnings("rawtypes")
	Document sendWelcomeEmailToStudentOnSuccessfulcreationByParent(String studentEmail, String fullName,
			String userName, String mobileNumber, String password, Long classStandard);

	void sendLeadBatchFreeCourseInterestEmail(String leadName, String leadEmail, String leadPhone,
			String classStandadName, String subjectName, String syllabusName, String teacherName, String prefferedTime,
			String prefferedDate);

	void sendEmailToTelecallerWithCredentials(String email, String fullName, String userName, String mobileNumber,
			String role, String password);

	void sendEmailToModeratorWithCredentials(String email, String fullName, String userName, String mobileNumber,
			String role, String password);

	void sendEmailToStudentWithCredentialsAfterBookingFreeClass(String studentEmail, String fullName, String userName,
			String mobileNumber, String role, String password);

	void sendEmailToParentWithCredentialsAfterBookingFreeClass(String parentEmail, String parentFullName,
			String parentUserName, String mobileNumber, String role, String password);

	@SuppressWarnings("rawtypes")
	Document sendNotificationEmailwhenDemoClassAssigned(String email, String fullName, String scheduleDate,
			String timings, String subjectName);

	void sendEmailToAllStudentsForJoiningDemoClass(String firstName, String email, String attendeeJoinMeetingUrlActual,
			Long idDemoClass, Long idClassStandard, Long idSubject, Long idSubjectChapter, Long idSyllabus,
			LocalTime fromTime, LocalTime toTime);

	void sendWelcomeEmailOnSuccessfulSignUp(String email, String fullName, String userName, String mobileNumber,
			String role);

	void sendBookDemoClassWelcomeEmail(String firstName, String email, String mobileNumber, String classStandadName,
			String subjectName, String syllabusName, String dayOfWeek, LocalTime fromTime, LocalTime toTime,
			String language, String chapterName);

	void sendOTPForSignUp(String email, String name, String OTP);

	void sendEmailToAllStudentsForJoiningDemoClassExtraCurricular(String firstName, String email,
			String attendeeJoinMeetingUrlActual, Long idDemoClassExtraCurricular, Long idSubjectExtraCurricular,
			LocalTime fromTime, LocalTime toTime);

	public void sendLiveClassReminderNotification(String firstName, String header, String fromTime, String email);

	void sendNotificationEmailwhenLiveClassStarts(String firstName, String email, String liveClassHeading,
			Long idLiveClass);

	void sendNotificationEmailWhenBatchStarts(String firstName, String email, String batchName, Long idBatch);

	void sendTicketResponseEmailNotification(String email, String fullName, Long ticketNo, String adminResponse,
			OrderTicketStatus status);

	public void sendGlobalEmail(String email, String fullName, String heading, String message) throws Exception;

	public Document<?> sendInvoceThroughEmail(String orderId);

	void sendWelcomeEmailOnSuccessfulSignUpWithCredentials(String email, String fullName, String userName,
			String mobileNumber, String password, String state, String classStandard, String syllabus);

}
