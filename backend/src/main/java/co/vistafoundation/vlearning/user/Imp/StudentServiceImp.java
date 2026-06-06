package co.vistafoundation.vlearning.user.Imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.AcademicSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.BatchSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.ExtraCurricularSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.KidsSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.CreateStudentDTO;
import co.vistafoundation.vlearning.user.dto.KidsDTO;
import co.vistafoundation.vlearning.user.dto.StudentDTO;
import co.vistafoundation.vlearning.user.dto.StudentDetailsDTO;
import co.vistafoundation.vlearning.user.dto.UserProfileResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserReportDTO;
import co.vistafoundation.vlearning.user.model.ChangeMobileNumber;
import co.vistafoundation.vlearning.user.model.ChangeMobileRepository;
import co.vistafoundation.vlearning.user.model.Parent;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.StudentMedium;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.ParentRepository;
import co.vistafoundation.vlearning.user.repository.StudentCompletionFactRepository;
import co.vistafoundation.vlearning.user.repository.StudentMediumRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.service.StudentService;

/**
 * Author Meghana
 **/

@Service

public class StudentServiceImp implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ParentRepository parentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ChangeMobileRepository changeMobileNumberRepository;

	@Autowired
	StudentCompletionFactRepository studentCompletionFactRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	private StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	StudentMediumRepository studentMediumRepository;
	
	@Value("${aws.s3.bucket}")
	private String bucketName;

	private static final Logger log = LoggerFactory.getLogger(StudentServiceImp.class);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveStudent(CreateStudentDTO createStudentDTO) {

		
		Document result = new Document();
		try {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 9998 + 1);
			createStudentDTO.setUsername(createStudentDTO.getUsername() + "_" + randomNum);

			String password = RandomStringUtils.randomAlphanumeric(6);

			System.out.println(createStudentDTO.getPassword());
			User user = new User(createStudentDTO.getFirstName(), createStudentDTO.getLastName(),
					createStudentDTO.getUsername(), createStudentDTO.getGmail(), passwordEncoder.encode(password),
					createStudentDTO.getStudent().getIdClassStandard(), createStudentDTO.getMobileNumber(), "Student",
					createStudentDTO.getSecondaryLanguage());
			if (createStudentDTO.getStudent() == null)
				throw new NullPointerException("Student data cannot be null");
			Student student2 = createStudentDTO.getStudent();
			student2.setUser(user);

			User userPare = new User();
			userPare = userRepository.findByUserSurId(createStudentDTO.getUserId());

			if (userPare == null)
				throw new NullPointerException("parent data not found");

			student2.setParent(parentRepository.findByUser(userPare));
			Student save = studentRepository.save(student2);

			emailService.sendWelcomeEmailToStudentOnSuccessfulcreationByParent(createStudentDTO.getGmail(),
					createStudentDTO.getFirstName() + " " + createStudentDTO.getLastName(),
					save.getUser().getUsername(), createStudentDTO.getMobileNumber(), password,
					createStudentDTO.getClassStandard());

			result.setData(save);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getListofstudent(Long idParent) {

		Document result = new Document();
		try {
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User loggedInUser = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			

			Parent parent = new Parent();
			parent = parentRepository.findByUser(loggedInUser);

			if (parent == null) {
				throw new AppException("Parent not found");
			}

			List<StudentDTO> list = new ArrayList<StudentDTO>();
			List<Student> listStudent = new ArrayList<Student>();

			listStudent = studentRepository.findByParent(parent);
			if (listStudent.isEmpty())
				throw new AppException("Student data not found");

			for (Student student : listStudent) {
				StudentDTO studentDTO = new StudentDTO();
				studentDTO.setIdStudent(student.getIdStudent());
				studentDTO.setFirstName(student.getUser().getFirstName());
				studentDTO.setLastName(student.getUser().getLastName());

				list.add(studentDTO);

			}

			result.setData(list);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document changeMobile(Long userSurId, String email, String newMobile) {

		Document doc = new Document<>();

		try {
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User user = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (user == null)
				throw new AppException("Invalid User");
			

			
			if (user.getMobileNumber() == newMobile) {
				throw new NullPointerException("Mobile Number Unchanged..Enter New Mobile Number");
			}

			String randomString = RandomStringUtils.random(10, true, true);

			ChangeMobileNumber obj = changeMobileNumberRepository.findByUserSurId(userSurId);

			if (obj == null) {
				ChangeMobileNumber changeMobileNumber = new ChangeMobileNumber();
				changeMobileNumber.setEmail(email);
				changeMobileNumber.setUserSurId(userSurId);
				changeMobileNumber.setVerificationCode(randomString);

				ChangeMobileNumber save = changeMobileNumberRepository.save(changeMobileNumber);

				// send mail
				Document sent = emailService.sendVerificationMailForChangeMobileNumber(email, newMobile, userSurId,
						randomString, user.getFirstName() + " " + user.getLastName());

				doc.setData("Check your Mail for Verification Code");
				doc.setMessage("Email Sent along with Verification Code");
				doc.setStatusCode(200);
				return doc;
			}

			else if (obj.getUserSurId() == userSurId) {
				obj.setEmail(email);
				obj.setUserSurId(userSurId);
				obj.setVerificationCode(randomString);
				ChangeMobileNumber save = changeMobileNumberRepository.save(obj);

				// Send Verification Mail
				Document sent = emailService.sendVerificationMailForChangeMobileNumber(email, newMobile, userSurId,
						randomString, user.getFirstName() + " " + user.getLastName());

				doc.setData("Check your Mail for Verification Code");
				doc.setMessage("Email Sent along with Verification Code");
				doc.setStatusCode(200);
				return doc;
			} else {
				throw new NullPointerException("Some Unexpected Error Occured...");
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setStatusCode(500);
			doc.setMessage("Error While Changing Mobile Number");
			return doc;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public Document verifyChangeMobileNumber(Long userSurId, String verificationCode, String mobileNumber) {

		Document doc = new Document<>();

		try {
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User user = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (user == null)
				throw new AppException("Invalid User");
			

			ChangeMobileNumber findByUserSurId = changeMobileNumberRepository.findByUserSurId(user.getUserSurId());

			if (findByUserSurId == null)
				throw new NullPointerException("ChangeMobileNumber data not found");

			if (findByUserSurId.getVerificationCode().equals(verificationCode)) {
				user.setMobileNumber(mobileNumber);
				User saved = userRepository.save(user);

				// Delete the Record from Change Mobile Table
				changeMobileNumberRepository.delete(findByUserSurId);

				doc.setData("Mobile Number Changed Successfully");
				doc.setMessage("Success");
				doc.setStatusCode(200);
				return doc;
			} else {
				throw new NullPointerException("Verification Code Mismatch");
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setStatusCode(500);
			doc.setMessage("Verification Code Mismatch or Some Unexpected Error Occured");
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document fetchUserObjectByUserSurId(Long userSurId) {

		Document doc = new Document<>();
		try {
			

			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User user = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (user == null)
				throw new AppException("Invalid User");			
			
			UserProfileResponseDTO uprDTO = new UserProfileResponseDTO();
			BeanUtils.copyProperties(user, uprDTO);

			doc.setData(uprDTO);
			doc.setMessage("User Object Fetch Successful");
			doc.setStatusCode(200);
			return doc;

		} catch (Exception e) {
			doc.setMessage(e.getLocalizedMessage());
			doc.setMessage("Failed to Get User Object. User Id not found in the db");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document studentFactData(Long idStudentSubcription, Long idSubject) {
		Document result = new Document();
		try {
			result.setData(studentCompletionFactRepository
					.findTop10ByIdStudentSubscriptionAndIdSubjectOrderByAsOnDateAsc(idStudentSubcription, idSubject));
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Student CompletionFact Details");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document getListofstudents(Long idParent) {
		Document result = new Document();
		try {
			List<KidsDTO> kidsList = new ArrayList<KidsDTO>();
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User loggedInUser = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			

			Parent parent = new Parent();
			parent = parentRepository.findByUser(loggedInUser);

			if (parent == null) {
				throw new AppException("Parent not found");
			}
			
			
			List<Student> listStudent = studentRepository.findByParent_IdParent(parent.getIdParent());
			if (listStudent.isEmpty())
				throw new AppException("Student data not found");
			for (Student student : listStudent) {
				KidsDTO kidsDto = new KidsDTO();
				ClassStandard classStandard = classRepository.findByIdClassStandard(student.getIdClassStandard());
				if (classStandard == null) {
					throw new NullPointerException("Cannot find the class standard data");
				}
				// set student details
				StudentDetailsDTO studentDetailsDTO = new StudentDetailsDTO();
				studentDetailsDTO.setIdStudent(student.getIdStudent());
				studentDetailsDTO.setUser(student.getUser());
				studentDetailsDTO.setClassStandadName(classStandard.getClassStandadName());
				studentDetailsDTO.setIdClassStandard(classStandard.getIdClassStandard());
//				studentDetailsDTO.setGender(student.getGender());
				// set batch details
				List<BatchSubscriptionDTO> batchList = new ArrayList<>();
				List<ExtraCurricularSubscriptionDTO> extracurricularList = new ArrayList<>();
				List<AcademicSubscriptionDTO> academicList = new ArrayList<>();
				List<KidsSubscriptionDTO> studentSubscriptionlistBtach = studentSubscriptionRepository
						.getAllBatchesDetails(student.getIdStudent(), "Batch", Boolean.TRUE);
				if (studentSubscriptionlistBtach.size() == 0) {
					kidsDto.setBatchSubscriptionDTOs(null);
				} else {
					for (KidsSubscriptionDTO kidSubscriptionBatchDTO : studentSubscriptionlistBtach) {
						BatchSubscriptionDTO batchInfoDTO = new BatchSubscriptionDTO();
						Batch batch = batchRepository.findByIdBatch(kidSubscriptionBatchDTO.getIdBatch());
						if (batch == null)
							throw new NullPointerException("Cannot find the batch data");
						batchInfoDTO.setIdBatch(batch.getIdBatch());
						batchInfoDTO.setBatchName(batch.getBatchName());
						batchInfoDTO.setNextPaymentDate(kidSubscriptionBatchDTO.getNextPaymentDate());
						batchInfoDTO.setLastPaymentDate(kidSubscriptionBatchDTO.getLastPaymentDate());
						batchInfoDTO.setSubscriptionEndDate(kidSubscriptionBatchDTO.getSubscriptionEndDate());
						batchInfoDTO.setPurchaseAmount(kidSubscriptionBatchDTO.getPurchaseAmount());
						batchInfoDTO.setIdStudentSubscription(kidSubscriptionBatchDTO.getIdStudentSubscription());
						Product prod = new Product();
						prod = productRepository.findByIdProductAndActiveFlag(
								batch.getIdProduct(),Boolean.TRUE);
						if (prod == null)
							throw new NullPointerException("Cannot find the product data");
						batchInfoDTO.setBatchSize(prod.getBatchSize());
						Subject subject = new Subject();
						subject = subjectRepository.findByIdSubject(prod.getIdSubject());
						batchInfoDTO.setSubjectName(subject.getSubjectName());
						Teacher teacher = new Teacher();
						teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());
						batchInfoDTO.setTeacherName(
								teacher.getUser().getFirstName() + ' ' + teacher.getUser().getLastName());
						batchInfoDTO.setDayOfWeekCode(batch.getDayOfWeekCode());
						batchInfoDTO.setBatchFromTime(batch.getBatchFromTime());
						batchInfoDTO.setBatchToTime(batch.getBatchToTime());
						batchList.add(batchInfoDTO);
					}
				}
				// set product group details
				List<StudentSubscription> studentSubscriptionlistGroup = studentSubscriptionRepository
						.findByproductDetails(student.getIdStudent(), 6L, Boolean.TRUE);
				if (studentSubscriptionlistGroup.size() == 0) {
					kidsDto.setExtraCurricularSubscriptionDTOs(null);
				} else {
					for (StudentSubscription studentSubscriptionExtraCurrDTO : studentSubscriptionlistGroup) {
						ExtraCurricularSubscriptionDTO extraCurrDTO = new ExtraCurricularSubscriptionDTO();
						if (studentSubscriptionExtraCurrDTO.getIdProductGroup() != null) {
							ProductGroup productGroup = productGroupRepository
									.findByIdProductGroup(studentSubscriptionExtraCurrDTO.getIdProductGroup());
							if (productGroup != null) {
								extraCurrDTO.setIdProductGroup(productGroup.getIdProductGroup());
								extraCurrDTO.setIdStudentSubscription(
										studentSubscriptionExtraCurrDTO.getIdStudentSubscription());
								extraCurrDTO.setProductName(productGroup.getProductGroupName());
								extraCurrDTO.setLastPaymentDate(studentSubscriptionExtraCurrDTO.getLastPaymentDate());
								extraCurrDTO.setNextPaymentDate(studentSubscriptionExtraCurrDTO.getNextPaymentDate());
								extraCurrDTO.setSubscriptionEndDate(
										studentSubscriptionExtraCurrDTO.getSubscriptionEndDate());
								extraCurrDTO.setPurchaseAmount(studentSubscriptionExtraCurrDTO.getPurchaseAmount());
								extracurricularList.add(extraCurrDTO);
							}
						} else if (studentSubscriptionExtraCurrDTO.getIdProduct() != null) {
							Product product = productRepository
									.findByIdProductAndActiveFlag(
											studentSubscriptionExtraCurrDTO.getIdProduct(),Boolean.TRUE);
							if (product != null) {
								extraCurrDTO.setIdProduct(product.getIdProduct());
								extraCurrDTO.setIdStudentSubscription(
										studentSubscriptionExtraCurrDTO.getIdStudentSubscription());
								extraCurrDTO.setProductName(product.getProductName());
								extraCurrDTO.setLastPaymentDate(studentSubscriptionExtraCurrDTO.getLastPaymentDate());
								extraCurrDTO.setNextPaymentDate(studentSubscriptionExtraCurrDTO.getNextPaymentDate());
								extraCurrDTO.setSubscriptionEndDate(
										studentSubscriptionExtraCurrDTO.getSubscriptionEndDate());
								extraCurrDTO.setPurchaseAmount(studentSubscriptionExtraCurrDTO.getPurchaseAmount());
								extracurricularList.add(extraCurrDTO);
							}
						}
					}
				}
				// set product details
				List<StudentSubscription> studentSubscriptionlistProduct = studentSubscriptionRepository
						.findByIdStudentAndIdproductLineAndActiveFlag(student.getIdStudent(), 5L, Boolean.TRUE);
				if (studentSubscriptionlistProduct.size() == 0) {
					kidsDto.setAcademicSubscriptionDTOs(null);
				} else {
					for (StudentSubscription studentSubscriptionAcademicDTO : studentSubscriptionlistProduct) {
						AcademicSubscriptionDTO academicDTO = new AcademicSubscriptionDTO();
						if (studentSubscriptionAcademicDTO.getIdProduct() != null) {
							Product product = productRepository
									.findByIdProductAndActiveFlag(studentSubscriptionAcademicDTO.getIdProduct(),Boolean.TRUE);
							if (product != null) {
								academicDTO.setIdProduct(product.getIdProduct());
								academicDTO.setIdStudentSubscription(
										studentSubscriptionAcademicDTO.getIdStudentSubscription());
								academicDTO.setProductName(product.getProductName());
								academicDTO.setLastPaymentDate(studentSubscriptionAcademicDTO.getLastPaymentDate());
								academicDTO.setNextPaymentDate(studentSubscriptionAcademicDTO.getNextPaymentDate());
								academicDTO.setSubscriptionEndDate(
										studentSubscriptionAcademicDTO.getSubscriptionEndDate());
								academicDTO.setPurchaseAmount(studentSubscriptionAcademicDTO.getPurchaseAmount());
								academicList.add(academicDTO);
							}
						} else if (studentSubscriptionAcademicDTO.getIdProductGroup() != null) {
							ProductGroup productGroup = productGroupRepository
									.findByIdProductGroup(studentSubscriptionAcademicDTO.getIdProductGroup());
							if (productGroup != null) {
								academicDTO.setIdProductGroup(productGroup.getIdProductGroup());
								academicDTO.setIdStudentSubscription(
										studentSubscriptionAcademicDTO.getIdStudentSubscription());
								academicDTO.setProductName(productGroup.getProductGroupName());
								academicDTO.setLastPaymentDate(studentSubscriptionAcademicDTO.getLastPaymentDate());
								academicDTO.setNextPaymentDate(studentSubscriptionAcademicDTO.getNextPaymentDate());
								academicDTO.setSubscriptionEndDate(
										studentSubscriptionAcademicDTO.getSubscriptionEndDate());
								academicDTO.setPurchaseAmount(studentSubscriptionAcademicDTO.getPurchaseAmount());
								academicList.add(academicDTO);
							}
						}
					}
				}
				// adding details into KidsDTO
				kidsDto.setStudent(studentDetailsDTO);
				kidsDto.setBatchSubscriptionDTOs(batchList);
				kidsDto.setExtraCurricularSubscriptionDTOs(extracurricularList);
				kidsDto.setAcademicSubscriptionDTOs(academicList);
				kidsList.add(kidsDto);
			}
			result.setData(kidsList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Kids Course Details");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document uploadUserProfilePicture(MultipartFile userProfilePicture, Long userSurId) {
		Document result = new Document();
		try {
			
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User user = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (user == null)
				throw new AppException("Invalid User");

            
		    /**
		     * @author Naveen Kumar A
		     * 
		     * its generate unique file name.
		     */
			String uniqueFile = userSurId+"_"+Long.toString(System.currentTimeMillis());

			File file = convertMultiPartFileToFile(userProfilePicture);
			
			String extension = "";
			
			int i = file.getName().lastIndexOf('.');
			if (i > 0) {
			    extension = file.getName().substring(i+1);
			}
			
			String uploadedPictureUrl = uploadFileToS3Bucket(file, uniqueFile+"."+extension);

			if (uploadedPictureUrl != null) {
				user.setUserProfilePic(uploadedPictureUrl);
				userRepository.save(user);
			}
			user.setUserProfilePic(uploadedPictureUrl);
			userRepository.save(user);

			// Delete the file After Upload Success from Classpath
			// Added By Ahmed
			boolean isDeletedFile = file.delete();
			log.info("file deleted from the system : " +isDeletedFile );
			result.setData(uploadedPictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Profile Picture Uploaded Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getUserPofilePicture(Long userSurId) {
		Document result = new Document();
		try {
			
			
			/*
			 * Updated by @author Naveen Kumar
			 * Fetching user data based on 
			 * session information instead
			 * of parameter which sent through 
			 * request. 
			 * 
			 */
			User user = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (user == null)
				throw new AppException("Invalid User");

			
			String profilePictureUrl = user.getUserProfilePic();
			result.setData(profilePictureUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Profile Picture Retrived Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			log.error(ex.getMessage());
		}
		return file;
	}

	private String uploadFileToS3Bucket(final File file, String uniqueFile) {
		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName+"/vlearning-user-pics", uniqueFile, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName+"/vlearning-user-pics", uniqueFile).toString();
		return s3Url;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getClassStandard(Long idStudent) {

		Document result = new Document();
		try {

			HashMap<String, Object> map = new HashMap<String, Object>();

			Student student = studentRepository.findByIdStudent(idStudent);
			if (student == null)
				throw new NullPointerException("No student found");

			ClassStandard classStandard = classRepository.findByIdClassStandard(student.getIdClassStandard());
			if (classStandard == null)
				throw new NullPointerException("No Class Details found");
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
			if (syllabus == null)
				throw new NullPointerException("No Syllabus Details found");
			map.put("classStandard", classStandard);
			map.put("syllabus", syllabus);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage(" Successfull Request");
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
	}

	@Override
	public Document<List<StudentMedium>> getStudentMedium() {

		Document<List<StudentMedium>> result = new Document<>();
		try {
			List<StudentMedium> studentMedium = studentMediumRepository.findAll();
			if (studentMedium.isEmpty())
				throw new NullPointerException("No Student Medium !");

			result.setData(studentMedium);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage(" Successfull Request");
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

	}

	@Override
	public Document<List<UserReportDTO>> getUserAnalyticReport() {
		// TODO Auto-generated method stub
		Document<List<UserReportDTO>> result=new Document<>();
//		Page<List<UserReportDTO>> page = null;
//		Pageable paging = PageRequest.of(pageNumber, 12);

		//List<UserReportDTO> reportList=new ArrayList<>();
		try {
			List<UserReportDTO>  list=studentRepository.getUsersReport();
			if(list.isEmpty())
				throw new AppException("No users");
			
				if (list.isEmpty() ) {
					result.setData(null);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("No More data Available Right Now!");
				} 
				else {
//					List<UserReportDTO> reports = list.getContent();
//					List<UserReportDTO> sortedReportList = reports.stream()
//							.sorted((s1, s2) -> s2.getUserCount().compareTo(s1.getUserCount()))
//							.collect(Collectors.toList());
//					final int start = (int)paging.getOffset();
//					final int end = Math.min((start + paging.getPageSize()), sortedReportList.size());
					//final Page<UserReportDTO> sortedPage = new PageImpl<>(sortedReportList, paging, list.getTotalElements());
					result.setData(list);
					result.setMessage("Success");
					result.setStatusCode(200);
				}
			
			
			
		}catch(Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		
		
		return result;
	}

}
