/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.dto.SignUpRequest;
import co.vistafoundation.vlearning.auth.model.ForgotPassword;
import co.vistafoundation.vlearning.auth.model.MobileOtp;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.RoleName;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.ForgotPasswordRepository;
import co.vistafoundation.vlearning.auth.repository.MobileOtpRepository;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.service.AuthService;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsExtraCurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchDetailsRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchExtraCurDetailDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchLogDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadRequest;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.TelecallerExtracurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.AvailableSchedule;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Lead;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadAttendedClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetails;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetailsExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLog;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLogExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.AvailableScheduleRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadAttendedClassRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchDetailsExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchDetailsRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchLogExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchLogRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.LeadService;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.utils.SMSHorizonService;

/**
 * @author Shaikh Ahmed Reza
 *
 */
@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private LeadBatchDetailsRepository leadBatchDetailsRepository;

	@SuppressWarnings("unused")
	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Autowired
	private ClassRepository classStandardRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	@SuppressWarnings("unused")
	private TeacherRepository teacherRepository;

	@Autowired
	private SyllabusRepository syllabusRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	AvailableScheduleRepository availableScheduleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MobileOtpRepository mobileOtpRepository;

	@Autowired
	@SuppressWarnings("unused")
	private ClassRepository classRepository;

	@Autowired
	ParentRepository parentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LeadBatchLogRepository leadBatchLogRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	LeadBatchDetailsExtraCurricularRepository leadBatchDetailsExtraCurricularRepository;

	@Autowired
	LeadBatchLogExtraCurricularRepository leadBatchLogExtraCurricularRepository;

	@Autowired
	LevelExtraCurricularRepository levelExtraCurricularRepository;

	@Autowired
	SMSHorizonService smsHorizonService;

	@Autowired
	LeadAttendedClassRepository leadAttendedClassRepository;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	AuthService authService;

	@Value("${app.angular.url}")
	private String angularUrl;

	@Value("${sms.flag}")
	private Boolean smsFlag;

	@Value("${test.otp}")
	private String testOtp;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document saveLeadData(LeadRequest leadBatchRequest) {

		Document result = new Document();
		try {
			if (leadBatchRequest == null) {
				throw new NullPointerException("Lead Data Cannot be Null");
			}

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String yymmdd = formatter.format(new Date());

			Lead lead = new Lead();
			lead.setLeadEmail(leadBatchRequest.getLeadEmail());
			lead.setLeadName(leadBatchRequest.getLeadName());
			lead.setLeadPhone(leadBatchRequest.getLeadPhone());
			lead.setLeadFootfallDate(yymmdd);
			lead.setProductCategory("BATCH");

			Lead saved = leadRepository.save(lead);

			result.setData(saved);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Lead Data Saved Successfully...");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document saveLeadBatchDetailsData(LeadBatchDetailsRequest leadBatchDetailsRequest) {

		Document result = new Document();
		try {
			if (leadBatchDetailsRequest == null) {
				throw new NullPointerException("Lead Batch Details Cannot be Null");
			}

			LeadBatchDetails leadBatchDetails = new LeadBatchDetails();
			leadBatchDetails.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
			leadBatchDetails.setIdVlUser(leadBatchDetailsRequest.getIdVlUser());
			leadBatchDetails.setIdSubject(leadBatchDetailsRequest.getIdSubject());
			leadBatchDetails.setIdSujectChapter(leadBatchDetailsRequest.getIdSujectChapter());
			leadBatchDetails.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
			leadBatchDetails.setIdAvailableSchedule(leadBatchDetailsRequest.getIdAvailableSchedule());
			leadBatchDetails.setIdLanguage(leadBatchDetailsRequest.getIdLanguage());

			LeadBatchDetails saved = leadBatchDetailsRepository.save(leadBatchDetails);

			// Fetch User Details By UserSurId
			User user = userRepository.findByUserSurId(leadBatchDetailsRequest.getIdVlUser());

			if (user == null) {
				throw new NullPointerException("User Details Not Found...");
			}

			if (user.getRegisteredAs().equals("Student")) {
				Student existingStudent = studentRepository.findByUser(user);

				if (existingStudent == null) {
					throw new NullPointerException("Student Details Not Found...");
				}

				existingStudent.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
				existingStudent.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
				existingStudent.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
				existingStudent.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());

//				studentRepository.save(existingStudent);
			} else if (user.getRegisteredAs().equals("Parent")) {
				Parent existingParent = parentRepository.findByUser(user);

				if (existingParent == null) {
					throw new NullPointerException("Parent Details Not Found...");
				}

				existingParent.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
				existingParent.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
				existingParent.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
				existingParent.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());

//				parentRepository.save(existingParent);
			}

			ClassStandard classStandard = classStandardRepository
					.findByIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());

			if (classStandard == null) {
				throw new NullPointerException("ClassStandard Details Not Found...");
			}

			Subject subject = subjectRepository.findByIdSubject(leadBatchDetailsRequest.getIdSubject());

			if (subject == null) {
				throw new NullPointerException("Subject Details Not Found...");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());

			if (syllabus == null) {
				throw new NullPointerException("Syllabus Details Not Found...");
			}

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsRequest.getIdAvailableSchedule());

			Language language = languageRepository.findByIdLanguage(leadBatchDetailsRequest.getIdLanguage());

			if (language == null) {
				throw new NullPointerException("Language Details Not Found...");
			}

			SubjectChapter subjectChapter = subjectChapterRepository
					.findByIdSubjectChapter(leadBatchDetailsRequest.getIdSujectChapter());

			if (subjectChapter == null) {
				throw new NullPointerException("SubjectChapter Details Not Found...");
			}

			if (availableSchedule.isPresent()) {
			    emailService.sendBookDemoClassWelcomeEmail(
			        user.getFirstName(),
			        user.getEmail(),
			        user.getMobileNumber(),
			        classStandard.getClassStandadName(),
			        subject.getSubjectName(),
			        syllabus.getSyllabusName(),
			        availableSchedule.get().getDayOfWeek(),
			        availableSchedule.get().getFromTime(),
			        availableSchedule.get().getToTime(),
			        language.getLanguage(),
			        subjectChapter.getChapterName()
			    );
			} else {
				throw new AppException("Schedule not available");
			}
			result.setData(saved);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Lead Batch Details Saved Successfully...");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	
	@Override
	public Document<?> fetchAllSyllabus() {

		Document<List<Syllabus>> result = new Document<>();

		try {
			List<Syllabus> listsOfAllSyllabus = syllabusRepository.findAll();
			if (listsOfAllSyllabus.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("SyllabusList is Empty");
				return result;
			} else {
				result.setData(listsOfAllSyllabus);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Syllabus Lists");
				return result;
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendOtp(String mobile, String email, String name) {
		Document document = new Document<>();
		
		Boolean smsFlag = this.smsFlag != null ? this.smsFlag : false;
		
	    try 
	    {
	    	MobileOtp motp = authService.getMobileOtp(mobile);
	    	
	    	String otp = motp.getOtp();
			
			if (email != null) {
				
				if (!email.isEmpty())
				emailService.sendOTPForSignUp(email, name, otp);
			}

			if (!smsFlag) {
				String finalOtp = testOtp != null ? testOtp : "123456";
				motp.setOtp(finalOtp);
				motp.setExpiryTime(Instant.now().plus(30, ChronoUnit.MINUTES));
				mobileOtpRepository.save(motp);
				document.setData(true);
				document.setMessage("Internal Testing: SMS notifications are disabled. Please use the default OTP for authentication.");
				document.setStatusCode(HttpStatus.OK.value());
			} else {
				String message = "Your OTP for mobile verification is " + otp + ".\n Happy Learning, V-Learning.";
				// OTP Template id hardcoded for temp
				String templeateId = "1207162701552642477";
				
				boolean returnValue = smsHorizonService.smsService(mobile, message, templeateId);
				
				if (returnValue) {
					    mobileOtpRepository.save(motp);
						document.setData(true);
						document.setMessage("Message sent successfully");
						document.setStatusCode(HttpStatus.OK.value());
					
				} else {
					document.setData(null);
					document.setMessage("Message not sent");
					document.setStatusCode(HttpStatus.NOT_FOUND.value());
				}
			}
	    }
	    catch(Exception e)
	    {
	    	document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(500);
	    	
	    }
	
		return document;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document verifyOtp(String mobile, String otp) {
		Document document = new Document<>();
		
		try {
			MobileOtp existigRecord = authService.verifyMobileOTP(mobile, otp);

			if (existigRecord != null) {
				document.setData(true);
				document.setMessage("Correct otp");
				document.setStatusCode(HttpStatus.OK.value());

				// Delete Record After Otp Verification
				//mobileOtpRepository.delete(existigRecord);
			}
			else {
				document.setData(false);
				document.setMessage("Incorrect otp");
				document.setStatusCode(500);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(500);
		}
		
		
		return document;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document sendOtpForResetPassword(String mobile) {
		
		Document document = new Document<>();
		User user = userRepository.findByMobileNumber(mobile);
		if (user == null) {
			document.setData(null);
			document.setMessage("Mobile number is not found in our records");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}

		if (!user.getActiveFlag()) {
			document.setData(null);
			document.setStatusCode(HttpStatus.FORBIDDEN.value());
			document.setMessage("Your account is deactivated, please contact us");
			return document;
		}
		try {
			MobileOtp existData = authService.getMobileOtp(mobile);
			String otp = existData.getOtp();
			
			Boolean smsFlag = this.smsFlag != null ? this.smsFlag : false;
			
			if (!smsFlag) {
				String finalOtp = testOtp != null ? testOtp : "123456";
				existData.setOtp(finalOtp);
				existData.setExpiryTime(Instant.now().plus(30, ChronoUnit.MINUTES));
				existData = mobileOtpRepository.save(existData);
				document.setData(true);
				document.setMessage("Internal Testing: SMS notifications are disabled. Please use the default OTP for authentication.");
				document.setStatusCode(HttpStatus.OK.value());
			} else {
				String message = "Your OTP for forgot password is " + otp
						+ ". Please don't share it with anyone.\n Happy Learning, V-Learning.";
				// OTP Template id hardcoded for temp
				String templeateId = "1207162755923845053";
				boolean returnValue = smsHorizonService.smsService(mobile, message, templeateId);
				if (returnValue) {

					existData = mobileOtpRepository.save(existData);
					document.setData(true);
					document.setMessage("Message sent successfully");
					document.setStatusCode(HttpStatus.OK.value());

				} else {
					document.setData(null);
					document.setMessage("Message not sent, please try again");
					document.setStatusCode(HttpStatus.NOT_FOUND.value());
				}
			}
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(500);

		}
		return document;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document verifyOtpForResetPassword(String mobile, String otp) {

		Document document = new Document<>();
	
		try {
			
			MobileOtp existigRecord = authService.verifyMobileOTP(mobile, otp);

			if (existigRecord == null) {
				document.setData(false);
				document.setMessage("Incorrect otp");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}
			
			User existingUser = userRepository.findByMobileNumber(mobile);
			if (existingUser == null) {
				document.setData(false);
				document.setMessage("User details not found in our records");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}
			if (!existingUser.getActiveFlag()) {
				document.setData(null);
				document.setStatusCode(HttpStatus.FORBIDDEN.value());
				document.setMessage("Your account is deactivated, please contact us");
				return document;
			}

			// Generate random string for resetting password
			String randomString = RandomStringUtils.random(10, true, true);

			ForgotPassword existingForgotPasswordObj = forgotPasswordRepository
					.findByUserSurId(existingUser.getUserSurId());

			if (existingForgotPasswordObj == null) {
				ForgotPassword forgotPassword = new ForgotPassword();
				forgotPassword.setForgotPasswordUsername(existingUser.getUsername());
				forgotPassword.setRandomString(randomString);
				forgotPassword.setUserSurId(existingUser.getUserSurId());
				forgotPassword = forgotPasswordRepository.save(forgotPassword);
			} else {
				existingForgotPasswordObj.setForgotPasswordUsername(existingUser.getUsername());
				existingForgotPasswordObj.setRandomString(randomString);
				existingForgotPasswordObj.setUserSurId(existingUser.getUserSurId());
				existingForgotPasswordObj = forgotPasswordRepository.save(existingForgotPasswordObj);
			}

			if (existingUser.getEmail() != null) {
				emailService.sendForgotPasswordEmail(existingUser.getEmail(), existingUser.getUsername(), randomString,
						existingUser.getFirstName(), existingUser.getUserSurId());
			}
			Map<String, Object> forgotPasswordObject = new HashMap<>();
			forgotPasswordObject.put("userSurId", existingUser.getUserSurId());
			forgotPasswordObject.put("forgotPasswordUsername", existingUser.getUsername());
			forgotPasswordObject.put("resetLink",
					angularUrl + "/forgot-password?userSurId=" + existingUser.getUserSurId() + "&randomString="
							+ randomString + "&forgotPasswordUsername=" + existingUser.getUsername());
			forgotPasswordObject.put("randomString", randomString);
			document.setData(forgotPasswordObject);
			document.setMessage("OTP verified successfully, you can reset your new password");
			document.setStatusCode(HttpStatus.OK.value());

			// Delete Record After Otp Verification
			mobileOtpRepository.delete(existigRecord);
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(500);
			return document;
		}
		return document;
	}

	@Override
	public User registerUser(@Valid SignUpRequest signUpRequest) {
		User result = new User();

		// Creating user's account
		// User user = new User(signUpRequest.getFirstName(),
		// signUpRequest.getLastName(), signUpRequest.getUsername(),
		// signUpRequest.getEmail(), RandomStringUtils.randomAlphabetic(6),
		// signUpRequest.getClassStandard(),
		// signUpRequest.getMobileNumber(), signUpRequest.getRole(),
		// signUpRequest.getSecondaryLanguage());

		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setEmail(signUpRequest.getEmail());
		user.setMobileNumber(signUpRequest.getMobileNumber());
		user.setRegisteredAs(signUpRequest.getRole());
		user.setUsername(signUpRequest.getUsername());

		final String randomPassword = RandomStringUtils.randomAlphabetic(6);

		user.setPassword(passwordEncoder.encode(randomPassword));

		if (signUpRequest.getRole().equals("Student")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
			if (userRole == null)
				throw new AppException("User Role not set.");
			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// creation of student after user
			Student student = new Student();
			student.setUser(result);
			student = studentRepository.save(student);

			// Set Password to Null For Security Purposes
			result.setPassword(null);

			// Extracting Necessary Fields for Sending Email
			// Send Welcome Email

			String studentEmail = signUpRequest.getEmail();
			String fullName = signUpRequest.getFirstName();
			String userName = signUpRequest.getUsername();
			String mobileNumber = signUpRequest.getMobileNumber();
			String role = signUpRequest.getRole();
			String password = randomPassword;

			emailService.sendEmailToStudentWithCredentialsAfterBookingFreeClass(studentEmail, fullName, userName,
					mobileNumber, role, password);

		} else if (signUpRequest.getRole().equals("Parent")) {
			Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
			if (userRole == null)
				throw new AppException("User Role not set.");

			user.setRoles(Collections.singleton(userRole));
			result = userRepository.save(user);

			// creation of parent from user
			Parent parent = new Parent();
			parent.setUser(result);

			parent = parentRepository.save(parent);

			// Set Password to Null For Security Purposes
			result.setPassword(null);

			String parentEmail = parent.getUser().getEmail();
			String parentFullName = parent.getUser().getFirstName();
			String parentUserName = parent.getUser().getUsername();
			String mobileNumber = parent.getUser().getMobileNumber();
			String role = signUpRequest.getRole();
			String password = randomPassword;

			emailService.sendEmailToParentWithCredentialsAfterBookingFreeClass(parentEmail, parentFullName,
					parentUserName, mobileNumber, role, password);
		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkMobileNumberExistence(String mobile) {

		Document doc = new Document<>();

		try {

			if (mobile == null || mobile.equals("0")) {
				throw new NullPointerException("Mobile Number is invalid");
			}

			Boolean exists = userRepository.existsByMobileNumber(mobile);
			if (exists) {
				doc.setData(true);
				doc.setMessage("Mobile Number Has Already Taken");
				doc.setStatusCode(200);
				return doc;
			} else {
				doc.setData(false);
				doc.setMessage("Mobile Number has not been taken");
				doc.setStatusCode(200);
				return doc;
			}
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document fetchAllAvailableSlots() {

		Document doc = new Document<>();

		try {
			List<AvailableSchedule> allAvailableSlots = availableScheduleRepository.findAll();

			if (allAvailableSlots.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Available Slot List is Empty");
				doc.setStatusCode(200);
			}
			doc.setData(allAvailableSlots);
			doc.setMessage("Available Slot Lists");
			doc.setStatusCode(200);
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}

		return doc;
	}

	@Override
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLeadBatchDetailsDataAfterLogginInBookFreeClass(
			LeadBatchDetailsRequest leadBatchDetailsRequest) {
		Document doc = new Document();
		try {

			if (leadBatchDetailsRequest == null) {
				throw new NullPointerException("Lead Batch Details Cannot be Null");
			}

			User result = null;

			ClassStandard classStandard = classStandardRepository
					.findByIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());

			if (classStandard == null) {
				throw new NullPointerException("classStandard Details Not Found...");
			}

			Subject subject = subjectRepository.findByIdSubject(leadBatchDetailsRequest.getIdSubject());

			if (subject == null) {
				throw new NullPointerException("subject Details Not Found...");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());

			if (syllabus == null) {
				throw new NullPointerException("syllabus Details Not Found...");
			}

			Language language = languageRepository.findByIdLanguage(leadBatchDetailsRequest.getIdLanguage());

			if (language == null) {
				throw new NullPointerException("language Details Not Found...");
			}

			SubjectChapter subjectChapter = subjectChapterRepository
					.findByIdSubjectChapter(leadBatchDetailsRequest.getIdSujectChapter());

			if (subjectChapter == null) {
				throw new NullPointerException("SubjectChapter Details Not Found...");
			}

			if (leadBatchDetailsRequest.getIdVlUser() == null) {
				User user = new User();
				user.setFirstName(leadBatchDetailsRequest.getFirstName());
				user.setLastName(leadBatchDetailsRequest.getLastName());
				user.setEmail(leadBatchDetailsRequest.getEmail());
				user.setMobileNumber(leadBatchDetailsRequest.getMobileNumber());
				user.setRegisteredAs(leadBatchDetailsRequest.getRole());
				user.setUsername(leadBatchDetailsRequest.getUsername());
				user.setClassStandard(leadBatchDetailsRequest.getIdClassStandard());
				user.setSecondaryLanguage(language.getLanguage());

				final String randomPassword = RandomStringUtils.randomAlphabetic(6);

				user.setPassword(passwordEncoder.encode(randomPassword));

				if (leadBatchDetailsRequest.getRole().equals("Student")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
					if (userRole == null)
						throw new AppException("User Role not set.");
					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of student after user
					Student student = new Student();
					student.setUser(result);
					student.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
					student.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
					student.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());
					student.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());

					if (leadBatchDetailsRequest.getIdState() != null)
						student.setIdState(leadBatchDetailsRequest.getIdState());

					studentRepository.save(student);

					String studentEmail = leadBatchDetailsRequest.getEmail();
					String fullName = leadBatchDetailsRequest.getFirstName();
					String userName = leadBatchDetailsRequest.getUsername();
					String mobileNumber = leadBatchDetailsRequest.getMobileNumber();
					String role = leadBatchDetailsRequest.getRole();
					String password = randomPassword;
					if (studentEmail != null) {
						emailService.sendEmailToStudentWithCredentialsAfterBookingFreeClass(studentEmail, fullName,
								userName, mobileNumber, role, password);
					}
				} else if (leadBatchDetailsRequest.getRole().equals("Parent")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
					if (userRole == null)
						throw new AppException("User Role not set.");

					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of parent from user
					Parent parent = new Parent();
					parent.setUser(result);
					parent.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
					parent.setIdLangauage(leadBatchDetailsRequest.getIdLanguage());
					parent.setIdStudentMedium(leadBatchDetailsRequest.getIdMedium());
					parent.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
					if (leadBatchDetailsRequest.getIdState() != null)
						parent.setIdState(leadBatchDetailsRequest.getIdState());
					parentRepository.save(parent);

					String parentEmail = parent.getUser().getEmail();
					String parentFullName = parent.getUser().getFirstName();
					String parentUserName = parent.getUser().getUsername();
					String mobileNumber = parent.getUser().getMobileNumber();
					String role = leadBatchDetailsRequest.getRole();
					String password = randomPassword;
					if (parentEmail != null) {
						emailService.sendEmailToParentWithCredentialsAfterBookingFreeClass(parentEmail, parentFullName,
								parentUserName, mobileNumber, role, password);
					}
				}
			}

			LeadBatchDetails leadBatchDetails = new LeadBatchDetails();
			leadBatchDetails.setIdClassStandard(leadBatchDetailsRequest.getIdClassStandard());
			if (leadBatchDetailsRequest.getIdVlUser() != null) {
				leadBatchDetails.setIdVlUser(leadBatchDetailsRequest.getIdVlUser());
			} else
				leadBatchDetails.setIdVlUser(result.getUserSurId());

			leadBatchDetails.setIdSubject(leadBatchDetailsRequest.getIdSubject());
			leadBatchDetails.setIdSujectChapter(leadBatchDetailsRequest.getIdSujectChapter());
			leadBatchDetails.setIdSyllabus(leadBatchDetailsRequest.getIdSyllabus());
			leadBatchDetails.setIdAvailableSchedule(leadBatchDetailsRequest.getIdAvailableSchedule());
			leadBatchDetails.setIdLanguage(leadBatchDetailsRequest.getIdLanguage());
			leadBatchDetails.setIdState(leadBatchDetailsRequest.getIdState());
			if (result != null) {
				leadBatchDetails.setCreatedBy(result.getUserSurId());
				leadBatchDetails.setUpdatedBy(result.getUserSurId());
			} else {
				leadBatchDetails.setCreatedBy(leadBatchDetailsRequest.getIdVlUser());
				leadBatchDetails.setUpdatedBy(leadBatchDetailsRequest.getIdVlUser());
			}

			LeadBatchDetails saved = leadBatchDetailsRepository.save(leadBatchDetails);

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsRequest.getIdAvailableSchedule());

			
			if (result == null)
				 throw new NullPointerException("User Accounts info not available.");
			
			if (leadBatchDetailsRequest.getIdVlUser() != null) {
				User userInfo = userRepository.findByUserSurId(leadBatchDetailsRequest.getIdVlUser());
				// Send Thanks Mail For Notifying
				if (userInfo.getEmail() != null) {
					if (availableSchedule.isPresent()) {
					    emailService.sendBookDemoClassWelcomeEmail(
					        userInfo.getFirstName(),
					        userInfo.getEmail(),
					        userInfo.getMobileNumber(),
					        classStandard.getClassStandadName(),
					        subject.getSubjectName(),
					        syllabus.getSyllabusName(),
					        availableSchedule.get().getDayOfWeek(),
					        availableSchedule.get().getFromTime(),
					        availableSchedule.get().getToTime(),
					        language.getLanguage(),
					        subjectChapter.getChapterName()
					    );
					} else {
						throw new AppException("Schedule not available");
					}

				}
			} else {
				// Send Thanks Mail For Notifying
				if (result.getEmail() != null) {
					if (availableSchedule.isPresent()) {
					    emailService.sendBookDemoClassWelcomeEmail(
					        result.getFirstName(),
					        result.getEmail(),
					        result.getMobileNumber(),
					        classStandard.getClassStandadName(),
					        subject.getSubjectName(),
					        syllabus.getSyllabusName(),
					        availableSchedule.get().getDayOfWeek(),
					        availableSchedule.get().getFromTime(),
					        availableSchedule.get().getToTime(),
					        language.getLanguage(),
					        subjectChapter.getChapterName()
					    );
					} else {
						throw new AppException("Schedule not available");
					}

				}else {
					throw new AppException("Email not available");
				}
			}

			doc.setData(saved);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Lead Batch Details Saved Successfully...");
			return doc;

		}

		catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLeadBatchDetailsExtraCurricularData(
			LeadBatchDetailsExtraCurricularDTO leadBatchDetailsExtraCurricular) {

		Document doc = new Document();
		try {
			if (leadBatchDetailsExtraCurricular == null) {
				throw new NullPointerException("Batch Details Cannot be Null");
			}

			User result = null;

			if (leadBatchDetailsExtraCurricular.getIdVlUser() == null) {

				User user = new User();
				user.setFirstName(leadBatchDetailsExtraCurricular.getFirstName());
				user.setLastName(leadBatchDetailsExtraCurricular.getLastName());
				user.setEmail(leadBatchDetailsExtraCurricular.getEmail());
				user.setMobileNumber(leadBatchDetailsExtraCurricular.getMobileNumber());
				user.setRegisteredAs(leadBatchDetailsExtraCurricular.getRole());
				user.setUsername(leadBatchDetailsExtraCurricular.getUsername());

				final String randomPassword = RandomStringUtils.randomAlphabetic(6);

				user.setPassword(passwordEncoder.encode(randomPassword));

				if (leadBatchDetailsExtraCurricular.getRole().equals("Student")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_STUDENT);
					if (userRole == null)
						throw new AppException("User Role not set.");
					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of student after user
					Student student = new Student();
					student.setUser(result);
					student.setIdClassStandard(4L);
					student.setIdLangauage(leadBatchDetailsExtraCurricular.getIdLanguage());
					student.setIdSyllabus(4L);
					studentRepository.save(student);

					// Set Password to Null For Security Purposes
					// result.setPassword(null);

					// Extracting Necessary Fields for Sending Email
					// Send Welcome Email

					String studentEmail = leadBatchDetailsExtraCurricular.getEmail();
					String fullName = leadBatchDetailsExtraCurricular.getFirstName();
					String userName = leadBatchDetailsExtraCurricular.getUsername();
					String mobileNumber = leadBatchDetailsExtraCurricular.getMobileNumber();
					String role = leadBatchDetailsExtraCurricular.getRole();
					String password = randomPassword;

					emailService.sendEmailToStudentWithCredentialsAfterBookingFreeClass(studentEmail, fullName,
							userName, mobileNumber, role, password);

				} else if (leadBatchDetailsExtraCurricular.getRole().equals("Parent")) {
					Role userRole = roleRepository.findByRoleName(RoleName.ROLE_PARENT);
					if (userRole == null)
						throw new AppException("User Role not set.");

					user.setRoles(Collections.singleton(userRole));
					result = userRepository.save(user);

					// creation of parent from user
					Parent parent = new Parent();
					parent.setUser(result);
					parent.setIdClassStandard(4L);
					parent.setIdLangauage(leadBatchDetailsExtraCurricular.getIdLanguage());
					parent.setIdSyllabus(4L);
					parentRepository.save(parent);

					// Set Password to Null For Security Purposes
					// result.setPassword(null);

					String parentEmail = parent.getUser().getEmail();
					String parentFullName = parent.getUser().getFirstName();
					String parentUserName = parent.getUser().getUsername();
					String mobileNumber = parent.getUser().getMobileNumber();
					String role = leadBatchDetailsExtraCurricular.getRole();
					String password = randomPassword;

					emailService.sendEmailToParentWithCredentialsAfterBookingFreeClass(parentEmail, parentFullName,
							parentUserName, mobileNumber, role, password);
				}

			}

			LeadBatchDetailsExtraCurricular batchDetailsExtraCurricular = new LeadBatchDetailsExtraCurricular();
			batchDetailsExtraCurricular.setIdAvailableSlot(leadBatchDetailsExtraCurricular.getIdAvailableSlot());
			batchDetailsExtraCurricular.setIdLanguage(leadBatchDetailsExtraCurricular.getIdLanguage());
			batchDetailsExtraCurricular
					.setIdLevelExtraCurricular(leadBatchDetailsExtraCurricular.getIdLevelExtraCurricular());
			batchDetailsExtraCurricular
					.setIdSubjectExtraCurricular(leadBatchDetailsExtraCurricular.getIdSubjectExtraCurricular());

			if (leadBatchDetailsExtraCurricular.getIdVlUser() != null) {
				batchDetailsExtraCurricular.setIdVlUser(leadBatchDetailsExtraCurricular.getIdVlUser());
			} else
				batchDetailsExtraCurricular.setIdVlUser(result.getUserSurId());

			LeadBatchDetailsExtraCurricular saved = leadBatchDetailsExtraCurricularRepository
					.save(batchDetailsExtraCurricular);

			ClassStandard classStandard = classStandardRepository.findByIdClassStandard(4L);

			if (classStandard == null) {
				throw new NullPointerException("ClassStandard Details Not Found...");
			}

			Subject subject = subjectRepository
					.findByIdSubject(leadBatchDetailsExtraCurricular.getIdSubjectExtraCurricular());

			if (subject == null) {
				throw new NullPointerException("Subject Details Not Found...");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(4L);

			if (syllabus == null) {
				throw new NullPointerException("Syllabus Details Not Found...");
			}

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsExtraCurricular.getIdAvailableSlot());

			Language language = languageRepository.findByIdLanguage(leadBatchDetailsExtraCurricular.getIdLanguage());

			if (language == null) {
				throw new NullPointerException("Language Details Not Found...");
			}

			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapter(21L);

			if (subjectChapter == null) {
				throw new NullPointerException("SubjectChapter Details Not Found...");
			}

			User userInfo = null;

			if (leadBatchDetailsExtraCurricular.getIdVlUser() != null) {
				userInfo = userRepository.findByUserSurId(leadBatchDetailsExtraCurricular.getIdVlUser());
				// Send Thanks Mail For Notifying
				if (availableSchedule.isPresent()) {
				    emailService.sendBookDemoClassWelcomeEmail(
				        userInfo.getFirstName(),
				        userInfo.getEmail(),
				        userInfo.getMobileNumber(),
				        classStandard.getClassStandadName(),
				        subject.getSubjectName(),
				        syllabus.getSyllabusName(),
				        availableSchedule.get().getDayOfWeek(),
				        availableSchedule.get().getFromTime(),
				        availableSchedule.get().getToTime(),
				        language.getLanguage(),
				        subjectChapter.getChapterName()
				    );
				} else {
					throw new AppException("Schedule not available");
				}

			} else {
				// Send Thanks Mail For Notifying
				if (availableSchedule.isPresent()) {
				    emailService.sendBookDemoClassWelcomeEmail(
				        result.getFirstName(),
				        result.getEmail(),
				        result.getMobileNumber(),
				        classStandard.getClassStandadName(),
				        subject.getSubjectName(),
				        syllabus.getSyllabusName(),
				        availableSchedule.get().getDayOfWeek(),
				        availableSchedule.get().getFromTime(),
				        availableSchedule.get().getToTime(),
				        language.getLanguage(),
				        subjectChapter.getChapterName()
				    );
				} else {
					throw new AppException("Schedule not available");
				}

			}

			doc.setData(saved);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Lead Batch Details Extra Curricular Saved Successfully...");
			return doc;

		}

		catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLeadBatchExtraCurricularDetailsDataAfterLoggedIn(
			LeadBatchDetailsExtraCurricular leadBatchDetailsExtraCurricular) {

		Document result = new Document();
		try {

			if (leadBatchDetailsExtraCurricular == null) {
				throw new NullPointerException("Batch Details Cannot be Null");
			}

			// Fetch User Details By UserSurId
			User user = userRepository.findByUserSurId(leadBatchDetailsExtraCurricular.getIdVlUser());

			if (user == null) {
				throw new NullPointerException("User Details Not Found...");
			}

			LeadBatchDetailsExtraCurricular existingLeadBatchDetailsExtraCurricularForSubject = leadBatchDetailsExtraCurricularRepository
					.findByIdSubjectExtraCurricularAndIdVlUser(
							leadBatchDetailsExtraCurricular.getIdSubjectExtraCurricular(),
							leadBatchDetailsExtraCurricular.getIdVlUser());

			if (existingLeadBatchDetailsExtraCurricularForSubject != null) {
				throw new NullPointerException(
						"You have already enrolled for this Subject. Please Choose any Different Subject");
			}

			LeadBatchLogExtraCurricular existingLeadBatchLogExtraCurricularForSubject = leadBatchLogExtraCurricularRepository
					.findByIdSubjectExtraCurricularAndIdVlUser(
							leadBatchDetailsExtraCurricular.getIdSubjectExtraCurricular(),
							leadBatchDetailsExtraCurricular.getIdVlUser());

			if (existingLeadBatchLogExtraCurricularForSubject != null) {
				throw new NullPointerException(
						"Your Demo Class Has Already been Scheduled for this subject or You have already attended this demo class for this subject");
			}

			LeadBatchDetailsExtraCurricular saved = leadBatchDetailsExtraCurricularRepository
					.save(leadBatchDetailsExtraCurricular);

			ClassStandard classStandard = classStandardRepository.findByIdClassStandard(4L);

			if (classStandard == null) {
				throw new NullPointerException("ClassStandard Details Not Found...");
			}

			Subject subject = subjectRepository
					.findByIdSubject(leadBatchDetailsExtraCurricular.getIdSubjectExtraCurricular());

			if (subject == null) {
				throw new NullPointerException("Subject Details Not Found...");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(4L);

			if (syllabus == null) {
				throw new NullPointerException("Syllabus Details Not Found...");
			}

			Optional<AvailableSchedule> availableSchedule = availableScheduleRepository
					.findById(leadBatchDetailsExtraCurricular.getIdAvailableSlot());

			Language language = languageRepository.findByIdLanguage(leadBatchDetailsExtraCurricular.getIdLanguage());

			if (language == null) {
				throw new NullPointerException("Language Details Not Found...");
			}

			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapter(21L);

			if (subjectChapter == null) {
				throw new NullPointerException("SubjectChapter Details Not Found...");
			}

			// Send Thanks Mail For Notifying
			if (availableSchedule.isPresent()) {
			    emailService.sendBookDemoClassWelcomeEmail(
			        user.getFirstName(),
			        user.getEmail(),
			        user.getMobileNumber(),
			        classStandard.getClassStandadName(),
			        subject.getSubjectName(),
			        syllabus.getSyllabusName(),
			        availableSchedule.get().getDayOfWeek(),
			        availableSchedule.get().getFromTime(),
			        availableSchedule.get().getToTime(),
			        language.getLanguage(),
			        subjectChapter.getChapterName()
			    );
			} else {
				throw new AppException("Schedule not available");
			}

			result.setData(saved);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Lead Batch Details Saved Successfully...");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document fetchAllLevelsExtraCurricular() {

		Document doc = new Document<>();

		try {

			List<LevelExtraCurricular> listOfAllLevelsExtraCurricular = levelExtraCurricularRepository.findAll();

			if (listOfAllLevelsExtraCurricular.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Level Extra Curricular Lists is Empty");
				doc.setStatusCode(200);
			} else {
				doc.setData(listOfAllLevelsExtraCurricular);
				doc.setMessage("List of All Levels Extra Curricular");
				doc.setStatusCode(200);
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkForDemoClassBookedHistoryForUser(Long userSurId, String category) {

		Document doc = new Document<>();

		try {

			if (userSurId == null || userSurId.equals(0L)) {
				throw new NullPointerException("User Id Cannot be Null");
			}

			if (category == null) {
				throw new NullPointerException("Category cannot be Null");
			}

			switch (category) {
			case "ACADEMIC":
				LeadBatchDetails leadBatchDetails = leadBatchDetailsRepository.findByIdVlUser(userSurId);

				if (leadBatchDetails != null) {
					throw new Exception("Demo Class Record Already Exists. Cannot book another demo class");
				}

				else {
					doc.setData(Boolean.FALSE);
					doc.setMessage("No Demo class History found. Can book one Demo class");
					doc.setStatusCode(200);
				}
				List<LeadBatchLog> listOfLeadBatchLog = leadBatchLogRepository.findByIdVlUser(userSurId);

				if (listOfLeadBatchLog.isEmpty()) {
					doc.setData(Boolean.FALSE);
					doc.setMessage("No Demo class History found. Can book one Demo class");
					doc.setStatusCode(200);
				} else {
					throw new Exception(
							"Demo Class has been scheduled by TeleCaller. Record Already Exists. Cannot book another demo class");
				}
				break;

			case "EXTRA_CURRICULAR":
				LeadBatchDetailsExtraCurricular leadBatchDetailsExtraCurricular = leadBatchDetailsExtraCurricularRepository
						.findByIdVlUser(userSurId);

				if (leadBatchDetailsExtraCurricular != null) {
					throw new Exception("Demo Class Record Already Exists. Cannot book another demo class");
				}

				else {
					doc.setData(Boolean.FALSE);
					doc.setMessage("No Demo class History found. Can book one Demo class");
					doc.setStatusCode(200);
				}

				LeadBatchLogExtraCurricular leadBatchLogExtraCurricular = leadBatchLogExtraCurricularRepository
						.findByIdVlUser(userSurId);
				if (leadBatchLogExtraCurricular == null) {
					doc.setData(Boolean.FALSE);
					doc.setMessage("No Demo class History found. Can book one Demo class");
					doc.setStatusCode(200);
				} else {
					throw new Exception(
							"Demo Class has been scheduled by TeleCaller. Record Already Exists. Cannot book another demo class");
				}

			default:
				break;
			}

		} catch (Exception exp) {
			if (userSurId == null || category == null) {
				doc.setData(null);
				doc.setMessage(exp.getLocalizedMessage());
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} else {
				doc.setData(Boolean.TRUE);
				doc.setMessage(exp.getLocalizedMessage());
				doc.setStatusCode(HttpStatus.OK.value());
			}

		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document filterExtraCurricularLeadBatchDetails(Long idSubject, Long idLevel) {

		Document doc = new Document<>();

		try {

			if (idSubject.equals(0L) || idSubject == null) {
				throw new NullPointerException("Subject id Cannot be Null. Please Select Subject");
			}

			if (idLevel.equals(0L) || idLevel == null) {
				throw new NullPointerException("Level Id Cannot be Null. Please Select Level");
			}

			Subject subject = subjectRepository.findByIdSubject(idSubject);

			LevelExtraCurricular levelExtraCurricular = levelExtraCurricularRepository
					.findByIdLevelExtraCurricular(idLevel);

			if (subject == null) {
				throw new NullPointerException("Selected Subject Not Found");
			}

			if (levelExtraCurricular == null) {
				throw new NullPointerException("Selected Level Not Found");
			}

			List<LeadBatchDetailsExtraCurricular> listOfStudentsExtraCurricular = leadBatchDetailsExtraCurricularRepository
					.findByIdSubjectExtraCurricularAndIdLevelExtraCurricular(idSubject, idLevel);

			if (listOfStudentsExtraCurricular.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("No Students Found for this filter");
				doc.setStatusCode(500);
			} else {

				List<TelecallerExtracurricularDTO> telecallerExtracurricularDTOs = new ArrayList<>();

				for (LeadBatchDetailsExtraCurricular leadBatchDetailsExtraCurricular : listOfStudentsExtraCurricular) {

					User user = userRepository.findByUserSurId(leadBatchDetailsExtraCurricular.getIdVlUser());

					if (user == null) {
						// throw new NullPointerException("User Details Not Found...");
						continue;
					}

					TelecallerExtracurricularDTO telecallerExtracurricularDTO = new TelecallerExtracurricularDTO();

					telecallerExtracurricularDTO.setStudentName(user.getFirstName());
					telecallerExtracurricularDTO.setStudentEmail(user.getEmail());
					telecallerExtracurricularDTO.setMobileNumber(user.getMobileNumber());
					telecallerExtracurricularDTO.setIdVlUser(leadBatchDetailsExtraCurricular.getIdVlUser());

					telecallerExtracurricularDTOs.add(telecallerExtracurricularDTO);
				}

				doc.setData(telecallerExtracurricularDTOs);
				doc.setMessage("List Of All Students Registered For Extra Curricular Demo Class");
				doc.setStatusCode(200);
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<List<LeadBatchDetailsDTO>> AllLeadBatchDetailsCount() {

		Document<List<LeadBatchDetailsDTO>> result = new Document<>();

		try {

			// List<LeadBatchDetails> lbdList =
			// leadBatchDetailsRepository.findDistinctByIdClassStandardNotNullAndIdSubjectNotNullAndIdAvailableScheduleNotNullAndIdSujectChapterNotNullAndIdLanguageNotNullAndIdSyllabusNotNull();

			List<Object[]> finalList = leadBatchDetailsRepository.findAllDistinctData();

			if (finalList.isEmpty())
				throw new NullPointerException("No Lead Batch Details found");

			List<LeadBatchDetailsDTO> leadBatchDetailsDTOList = new ArrayList<LeadBatchDetailsDTO>();

			for (Object obj[] : finalList) {

				LeadBatchDetailsDTO lbdListDTO = new LeadBatchDetailsDTO();

				ClassStandard classStandard = classStandardRepository
						.findByIdClassStandard(Long.parseLong(obj[0].toString()));
				if (classStandard == null)
					throw new NullPointerException("No Class found");
				AvailableSchedule avs = availableScheduleRepository
						.findByIdAvailableSchedule(Long.parseLong(obj[5].toString()));
				if (avs == null)
					throw new NullPointerException("No schedules found");
				Language language = languageRepository.findByIdLanguage(Long.parseLong(obj[4].toString()));
				if (language == null)
					throw new NullPointerException("No Languge found");
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(Long.parseLong(obj[1].toString()));
				if (syllabus == null)
					throw new NullPointerException("No Syllabus Found");
				Subject sub = subjectRepository.findByIdSubject(Long.parseLong(obj[2].toString()));
				if (sub == null)
					throw new NullPointerException("No Subjects Found");
				SubjectChapter subChapter = subjectChapterRepository
						.findByIdSubjectChapter(Long.parseLong(obj[3].toString()));
				if (subChapter == null)
					throw new NullPointerException("No Chpter found");

				State state = stateRepository.findByIdState(Long.parseLong(obj[6].toString()));

				if (state == null)
					throw new NullPointerException("No State found");

				lbdListDTO.setIdAvailableSchedule(avs.getIdAvailableSchedule());
				lbdListDTO.setIdClassStandard(classStandard.getIdClassStandard());
				lbdListDTO.setIdLanguage(language.getIdLanguage());
				lbdListDTO.setIdSubject(sub.getIdSubject());
				lbdListDTO.setIdSujectChapter(subChapter.getIdSubjectChapter());
				lbdListDTO.setIdSyllabus(syllabus.getIdSyllabus());
				lbdListDTO.setClassStandadName(classStandard.getClassStandadName());
				lbdListDTO.setDayOfWeek(avs.getDayOfWeek());
				lbdListDTO.setLanguage(language.getLanguage());
				lbdListDTO.setSyllabusName(syllabus.getSyllabusName());
				lbdListDTO.setSubjectName(sub.getSubjectName());
				lbdListDTO.setChapterName(subChapter.getChapterName());
				lbdListDTO.setFromTime(avs.getFromTime());
				lbdListDTO.setToTime(avs.getToTime());
				lbdListDTO.setIdState(state.getIdState());
				lbdListDTO.setStateName(state.getState());

				int totalCount = leadBatchDetailsRepository
						.countByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguageAndIdAvailableSchedule(
								classStandard.getIdClassStandard(), syllabus.getIdSyllabus(), sub.getIdSubject(),
								subChapter.getIdSubjectChapter(), language.getIdLanguage(),
								avs.getIdAvailableSchedule());

				lbdListDTO.setCount(totalCount);
				leadBatchDetailsDTOList.add(lbdListDTO);
			}

			result.setData(leadBatchDetailsDTOList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<List<LeadBatchExtraCurDetailDTO>> getAllExtraCurLeadsCount() {

		Document<List<LeadBatchExtraCurDetailDTO>> result = new Document<>();
		try {

			List<Object[]> outcomeList = leadBatchDetailsExtraCurricularRepository.findAllDistinctExctraCurData();
			if (outcomeList.isEmpty())
				throw new NullPointerException("No Leads found for ExtraCurricular");

			List<LeadBatchExtraCurDetailDTO> leadBatchExtraCurDetailDTO = new ArrayList<LeadBatchExtraCurDetailDTO>();
			for (Object object[] : outcomeList) {
				LeadBatchExtraCurDetailDTO leadBatchECDto = new LeadBatchExtraCurDetailDTO();

				AvailableSchedule avs = availableScheduleRepository
						.findByIdAvailableSchedule(Long.parseLong(object[3].toString()));
				if (avs == null)
					throw new NullPointerException("No schedules found");
				Language language = languageRepository.findByIdLanguage(Long.parseLong(object[2].toString()));
				if (language == null)
					throw new NullPointerException("No Languge found");
				Subject sub = subjectRepository.findByIdSubject(Long.parseLong(object[0].toString()));
				if (sub == null)
					throw new NullPointerException("No Subjects Found");
				LevelExtraCurricular level = levelExtraCurricularRepository
						.findByIdLevelExtraCurricular(Long.parseLong(object[1].toString()));
				if (level == null)
					throw new NullPointerException("No Levels Found");

				leadBatchECDto.setIdAvailableSlot(avs.getIdAvailableSchedule());
				leadBatchECDto.setIdSubjectExtraCurricular(sub.getIdSubject());
				leadBatchECDto.setIdLevelExtraCurricular(level.getIdLevelExtraCurricular());
				leadBatchECDto.setIdLanguage(language.getIdLanguage());
				leadBatchECDto.setDayOfWeek(avs.getDayOfWeek());
				leadBatchECDto.setFromTime(avs.getFromTime());
				leadBatchECDto.setToTime(avs.getToTime());
				leadBatchECDto.setSubjectName(sub.getSubjectName());
				leadBatchECDto.setLevel(level.getLevel());
				leadBatchECDto.setLanguage(language.getLanguage());

				int leadCount = leadBatchDetailsExtraCurricularRepository
						.countByIdSubjectExtraCurricularAndIdLevelExtraCurricularAndIdLanguageAndIdAvailableSlot(
								sub.getIdSubject(), level.getIdLevelExtraCurricular(), language.getIdLanguage(),
								avs.getIdAvailableSchedule());

				leadBatchECDto.setCount(leadCount);
				leadBatchExtraCurDetailDTO.add(leadBatchECDto);
			}

			result.setData(leadBatchExtraCurDetailDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getHomepageBookFreeTrialAndPersonalCoachingData(String category, Long idVlUser, Long idStudent) {

		Document doc = new Document<>();

		try {

			if (category == null) {
				throw new NullPointerException("Category should not be Empty");
			}

			if (idVlUser == null) {
				throw new NullPointerException("User Id Cannot be Null. Make sure that You are Logged in");
			}

			if (idStudent == null) {
				throw new NullPointerException("Student Id Not found. Make sure that You are Logged in");
			}

			LeadBatchDetails leadBatchDetails = leadBatchDetailsRepository.findByIdVlUser(idVlUser);

			if (leadBatchDetails != null) {
				doc.setData("LeadBatchDetails " + Boolean.TRUE);
				doc.setMessage("Demo Class Record Already Exists. Cannot book another demo class");
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				// throw new Exception("Demo Class Record Already Exists. Cannot book another
				// demo class");
			}

			else if (leadBatchDetails == null) {
				doc.setData("LeadBatchDetails " + Boolean.FALSE);
				doc.setMessage("No Demo class History found. Can book one Demo class");
				doc.setStatusCode(200);
			}

			List<LeadBatchLog> listOfLeadBatchLog = leadBatchLogRepository.findByIdVlUser(idVlUser);

			if (listOfLeadBatchLog.isEmpty()) {
				doc.setData("LeadBatchLog " + Boolean.FALSE);
				doc.setMessage("No Demo class History found. Can book one Demo class");
				doc.setStatusCode(200);
				return doc;
			}
			if (!listOfLeadBatchLog.isEmpty()) {
				doc.setData("LeadBatchLog " + Boolean.TRUE);
				doc.setMessage(
						"Demo Class has been scheduled by TeleCaller. Record Already Exists. Cannot book another demo class");
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}

		} catch (Exception exp) {
			if (category == null || idVlUser == null || idStudent == null) {
				doc.setData(null);
				doc.setMessage(exp.getLocalizedMessage());
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} else {
				doc.setData(Boolean.TRUE);
				doc.setMessage(exp.getLocalizedMessage());
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}

		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkForLeadBatchLogsAndLeadAttendedEntry(String category, Long idVlUser) {

		Document doc = new Document<>();
		try {

			if (category == null) {
				throw new NullPointerException("Category Should not be Empty");
			}

			if (idVlUser == null) {
				throw new NullPointerException("User Id Cannot be Null. Make sure you are logged in");
			}

			List<LeadBatchLog> leadBatchLogs = leadBatchLogRepository.findByIdVlUser(idVlUser);

			if (leadBatchLogs.isEmpty()) {
				doc.setData(Boolean.FALSE);
				doc.setMessage("No Demo Class Has been Assigned By Telecaller.So, No Lead Attended Data");
				doc.setStatusCode(HttpStatus.OK.value());
			} else {
				LeadAttendedClass attendedClass = leadAttendedClassRepository.findByIdVlUser(idVlUser);

				if (attendedClass == null) {

					LeadBatchLogDTO batchLogDTO = new LeadBatchLogDTO();
					batchLogDTO.setClassScheduleDate(leadBatchLogs.get(0).getClassScheduleDate());
					batchLogDTO.setFromTime(leadBatchLogs.get(0).getFromTime());
					batchLogDTO.setIdClassStandard(leadBatchLogs.get(0).getIdClassStandard());
					batchLogDTO.setIdDemoClass(leadBatchLogs.get(0).getIdDemoClass());
					batchLogDTO.setIdLanguage(leadBatchLogs.get(0).getIdLanguage());
					batchLogDTO.setIdLeadBatchLog(leadBatchLogs.get(0).getIdLeadBatchLog());
					batchLogDTO.setIdSubject(leadBatchLogs.get(0).getIdSubject());
					batchLogDTO.setIdSubjectChapter(leadBatchLogs.get(0).getIdSubjectChapter());
					batchLogDTO.setIdSyllabus(leadBatchLogs.get(0).getIdSyllabus());
					batchLogDTO.setIdTeacher(leadBatchLogs.get(0).getIdTeacher());
					batchLogDTO.setIdVlUser(leadBatchLogs.get(0).getIdVlUser());
					batchLogDTO.setTelecallerIdVlUser(leadBatchLogs.get(0).getTelecallerIdVlUser());
					batchLogDTO.setToTime(leadBatchLogs.get(0).getToTime());
					doc.setData(batchLogDTO);
					doc.setMessage("Demo Class Has been Scheduled But Not Attended");
					doc.setStatusCode(HttpStatus.OK.value());
				} else {
					doc.setData(attendedClass);
					doc.setMessage("Demo Class Already Attended. Check For Batch Purchase Logic");
					doc.setStatusCode(HttpStatus.OK.value());
				}
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkForDemoClassBookedInLeadBatchDetails(Long userSurId, String category) {

		Document doc = new Document<>();

		try {
			if (category == null) {
				throw new NullPointerException("Category Should not be Empty");
			}

			if (userSurId == null) {
				throw new NullPointerException("User Id Cannot be Null. Make sure you are logged in");
			}

			LeadBatchDetails leadBatchDetails = leadBatchDetailsRepository.findByIdVlUser(userSurId);

			if (leadBatchDetails == null) {
				doc.setData(Boolean.FALSE);
				doc.setMessage("No Demo Class Booking Happened Till Now. Can Book One Demo Class");
				doc.setStatusCode(HttpStatus.OK.value());
				return doc;
			} else {
				doc.setData(Boolean.TRUE);
				doc.setMessage("Demo Class Has Already Booked. Can Not Book One Demo Class");
				doc.setStatusCode(HttpStatus.OK.value());
				return doc;
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkForDemoClassBookedInLeadBatchLog(Long userSurId, String category) {

		Document doc = new Document<>();

		try {
			if (category == null) {
				throw new NullPointerException("Category Should not be Empty");
			}

			if (userSurId == null) {
				throw new NullPointerException("User Id Cannot be Null. Make sure you are logged in");
			}

			List<LeadBatchLog> leadBatchLogs = leadBatchLogRepository.findByIdVlUser(userSurId);

			if (leadBatchLogs.isEmpty()) {
				doc.setData(Boolean.FALSE);
				doc.setMessage("Demo Class Has not yet Scheduled.");
				doc.setStatusCode(HttpStatus.OK.value());
				return doc;
			} else {
				doc.setData(Boolean.TRUE);
				doc.setMessage("Demo Class Has Already Been Scheduled or Attended. Can Not Book One Demo Class");
				doc.setStatusCode(HttpStatus.OK.value());
				return doc;
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
	}

}
