package co.vistafoundation.vlearning.user.Imp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import co.vistafoundation.vlearning.auth.dto.TeacherInfoDTO;
import co.vistafoundation.vlearning.auth.model.Role;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.RoleRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.user.dto.CreateTeacherRequest;
import co.vistafoundation.vlearning.user.dto.TeacherResponseDTO;
import co.vistafoundation.vlearning.user.dto.TeacherSubjectDTO;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherAvailability;
import co.vistafoundation.vlearning.user.model.TeacherSubject;
import co.vistafoundation.vlearning.user.repository.TeacherAvailabilityRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.TeacherSubjectRepository;
import co.vistafoundation.vlearning.user.service.TeacherService;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;

/**
 * author Meghana
 * 
 **/

@Service
public class TeacherServiceImp implements TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private SyllabusRepository syllabusRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebExPoolRepository webExPoolRepo;

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private TeacherAvailabilityRepository teacherAvailabilityRepository;
	
	@Autowired
	private FileUploadService fileUploadService;

	private static final Logger log = LoggerFactory.getLogger(TeacherServiceImp.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getTeacherById(Long idTeacher) {
		Document result = new Document();
		try {

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null)
				throw new NullPointerException("No Teacher Found");

			List<TeacherSubject> subList = teacherSubjectRepository.findByTeacher_IdTeacher(idTeacher);

			List<TeacherSubjectDTO> teacherdtoList = new ArrayList<>();

			if (subList.isEmpty())
				throw new AppException("No subject list found for the teacher.");
			CreateTeacherRequest teacherRequest = new CreateTeacherRequest();

			for (TeacherSubject sub : subList) {

				TeacherSubjectDTO dto = new TeacherSubjectDTO();

				dto.setIdSubject(sub.getIdSubject());
				dto.setIdSyllabus(sub.getIdSyllabus());
				dto.setProficiency(sub.getProficiency());
				Subject subject = subjectRepository.findByIdSubject(sub.getIdSubject());
				if (subject == null)
					throw new NullPointerException("subject data not found");
				dto.setSubjectName(subject.getSubjectName());
				Syllabus syl = syllabusRepository.findByIdSyllabus(sub.getIdSyllabus());
				if (syl == null)
					throw new NullPointerException("Syllabus data not found");
				dto.setSyllabusName(syl.getSyllabusName());
				dto.setSubIntroVideo(sub.getSubIntroVideo());

				teacherdtoList.add(dto);

			}

			teacherRequest.setTeacher(teacher);
			teacherRequest.setTeacherSubjectDTO(teacherdtoList);

			result.setData(teacherRequest);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document updateTeacher(CreateTeacherRequest createTeacherRequest) {
		Document result = new Document();
		try {

			User user = userRepository.findByUserSurId(createTeacherRequest.getUserId());
			if (user == null)
				throw new NullPointerException("user data not found");
			user.setFirstName(createTeacherRequest.getTeacherFirstName());
			user.setLastName(createTeacherRequest.getTeacherLastName());
			user.setEmail(createTeacherRequest.getTeacher().getEmailId());
			user.setMobileNumber(createTeacherRequest.getMobileNumber());
			user.setUserProfilePic(createTeacherRequest.getTeacher().getTeacherImage());
			user.setUsername(createTeacherRequest.getTeacher().getEmailId());
			
			Teacher PrevData = teacherRepository.findByIdTeacher(createTeacherRequest.getTeacher().getIdTeacher());

			if (PrevData == null)
				throw new NullPointerException("Teacher data not found");

			Teacher teacher = createTeacherRequest.getTeacher();

			if (createTeacherRequest.getTeacher().getIdWebexPool() != null) {
				System.out.println("/n User sending webex id "+ createTeacherRequest.getTeacher().getIdWebexPool());				
				if (PrevData.getIdWebexPool() != null) 
				{
					
					if (!createTeacherRequest.getTeacher().getIdWebexPool().equals(PrevData.getIdWebexPool()) ) {
						
						System.out.println("/n deiffernt  web ex id found " + createTeacherRequest.getTeacher().getIdWebexPool() +" another id "+PrevData.getIdWebexPool() );

						WebExPool webxPool = webExPoolRepo
								.findByIdWebExPool(createTeacherRequest.getTeacher().getIdWebexPool());

						if (webxPool == null)
							throw new NullPointerException("webxPool data not found");

						if (webxPool.getAvailableFlag() == true)
							throw new AppException("You cant assign this webx pool beacause it is already assigned ");

						/**
						 * The previous web pool making it available
						 **/

						WebExPool pool = webExPoolRepo.findByIdWebExPool(PrevData.getIdWebexPool());

						if (pool == null)
							throw new NullPointerException("webxPool data not found");

						pool.setAvailableFlag(false);
						webExPoolRepo.save(pool);

					}
					
				}
				
				System.out.println("/n assigning web ex id ");	

				WebExPool pool = webExPoolRepo.findByIdWebExPool(createTeacherRequest.getTeacher().getIdWebexPool());

				if (pool == null)
					throw new NullPointerException("webxPool data not found");

				pool.setAvailableFlag(true);
				webExPoolRepo.save(pool);

			} else {

				teacher.setIdWebexPool(null);
			}
			
//			List<TeacherAvailability> availabilityList = createTeacherRequest.getTeacher().getTeacherAvailability();

			teacher.setSearchKey(teacher.getTeacherName() + teacher.getPrimarySubject() + teacher.getTeacherDesc());
			teacher.setCreatedAt(PrevData.getCreatedAt());
			teacher.setCreatedBy(PrevData.getCreatedBy());
			teacher.setUser(user);

			Teacher techResp = teacherRepository.save(teacher);

			List<TeacherSubject> teachersubList = new ArrayList<TeacherSubject>();

			if (!createTeacherRequest.getTeacherSubject().isEmpty()) {
				teachersubList = createTeacherRequest.getTeacherSubject();
			}

			// Delete all the subjects related to that idTeacher

			List<TeacherSubject> techSub = teacherSubjectRepository
					.findByTeacher_IdTeacher(createTeacherRequest.getTeacher().getIdTeacher());

			if (!techSub.isEmpty()) {
				teacherSubjectRepository.deleteInBatch(techSub);
			}

			/* readd all the Subjects */

			for (TeacherSubject ts : teachersubList) {
				ts.setTeacher(techResp);
				teacherSubjectRepository.save(ts);

			}

			result.setData(techResp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull");
			return result;
		} catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("This  username is already taken.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}
	}

	@Override
	public Document<String> uploadTeacherIntroVideo(MultipartFile teacherIntroVideo, String teacherName) {

		Document<String> result = new Document<String>();

		File tempFile =fileUploadService. convertMultiPartFileToFile(teacherIntroVideo);
		try {

			String random = Long.toString(System.currentTimeMillis());

			String extension = "";

			int i = tempFile.getName().lastIndexOf('.');
			if (i > 0) {
				extension = tempFile.getName().substring(i + 1);
			}

			String getUrl = uploadFileToS3Bucket(bucketName + "/staff/intro-videos", tempFile,
					random + "." + extension);

			result.setData(getUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		} finally {
			boolean isDeletedTempFile = tempFile.delete();
			log.info("Temp file deleted from the system : " +isDeletedTempFile );
		}
		return result;
	}

	

	private String uploadFileToS3Bucket(String bucketName, File file, String fileName) throws Exception {

		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName, fileName).toString();
		return s3Url;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getTeacherByCategory(String Category) {
		Document result = new Document();
		try {

			List<Teacher> teacherlist = teacherRepository.findByCategory(Category);

			if (teacherlist.isEmpty())
				throw new NullPointerException("Teacherlist data not found");

			List<TeacherResponseDTO> temp = new ArrayList<TeacherResponseDTO>();

			teacherlist.stream().forEach(t -> {

				TeacherResponseDTO tid = new TeacherResponseDTO();

				tid.setExpLevel(t.getExpLevel());
				tid.setIdTeacher(t.getIdTeacher());
				tid.setTeacherDesc(t.getTeacherDesc());
				tid.setTeacherName(t.getTeacherName());
				tid.setUserImage(t.getTeacherImage());
				tid.setPrimarySubejct(t.getPrimarySubject());
				tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
				tid.setTeacherHeader(t.getTeacherHeader());

				List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

				if (ts.isEmpty())
					throw new NullPointerException("TeacherSubject data not found");

				List<TeacherSubjectDTO> tsd = new ArrayList<>();

				if (!ts.isEmpty()) {
					ts.stream().forEach(tempSub -> {

						TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

						tsTemp.setProficiency(tempSub.getProficiency());
						tsTemp.setSubjectName(
								subjectRepository.findByIdSubject(tempSub.getIdSubject()).getSubjectName());
						tsTemp.setSyllabusName(
								syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus()).getSyllabusName());
						tsd.add(tsTemp);
					});
				}
				tid.setTeacherSubjects(tsd);
				temp.add(tid);
			});

			result.setData(temp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<Page<TeacherInfoDTO>> searchTeacherByCategory(String category, String searchTerm, int pageNumber) {

		Document<Page<TeacherInfoDTO>> result = new Document<>();

		List<TeacherInfoDTO> temp = new ArrayList<>();

		Page<TeacherInfoDTO> page = null;

		try {
			List<Teacher> teachers = new ArrayList<>();
			Pageable pageable = PageRequest.of(pageNumber, 12);

			List<Batch> batchList = batchRepository.findAll();
			if (batchList.isEmpty())
				throw new NullPointerException("Batch List Empty.");
			if (category.equalsIgnoreCase("EXTRA_CUR")) {
                //List<Batch> finalbatchList = new ArrayList<>();
                System.out.println("Fetching all teachers for extracurricular");
                // hardcoded the value of idclassStandard and idSyllabus to NA applicable value
                List<Product> productList = productRepository.findByIdClassStandardAndIdSyllabusAndActiveFlag(
                		4L, 4L,Boolean.TRUE);
                if (productList.isEmpty())
                    throw new NullPointerException("No Academic Products Available for Particular Class Standard.");
/*
                batchList.stream().forEach(b -> {
                    productList.stream().forEach(p -> {
                        if (b.getIdProduct().equals( p.getIdProduct())) {
                            finalbatchList.add(b);
                        }
                    });
                });
                */
/*
                if (finalbatchList.isEmpty())
                    throw new NullPointerException("No Teachers found for the selected class Standard");
*/
                //HashSet<Long> teacherIds = new HashSet<>();
                //finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));
                //List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);
                //teachers = teacherRepository.findByIdTeacherInAndSearchKeyContaining(finalTeacherIdList, searchTerm);
                teachers = teacherRepository.findByCategoryAndSearchKeyContaining(category, searchTerm);
                if (teachers.isEmpty())
                    throw new NullPointerException("Teachers data not found");
                teachers.forEach(t -> {
                    System.out.println(t.toString());
                });
            }

			
			else {

				List<Batch> finalbatchList = new ArrayList<>();
				System.out.println("Fetching all teachers for a particular classes.");

				List<Product> productList = productRepository.findByActiveFlagAndIdClassStandardNot(
						Boolean.TRUE, 4L);

				if (productList.isEmpty())
					throw new NullPointerException("No Academic Products Available for Particular Class Standard.");

				batchList.stream().forEach(b -> {

					productList.stream().forEach(p -> {

						if (b.getIdProduct().equals(p.getIdProduct())) {
							finalbatchList.add(b);
						}

					});
				});

				if (finalbatchList.isEmpty())
					throw new NullPointerException("No Teachers found for the selected class Standard");

				HashSet<Long> teacherIds = new HashSet<>();

				finalbatchList.removeIf(e -> !teacherIds.add(e.getIdTeacher()));

				List<Long> finalTeacherIdList = new ArrayList<>(teacherIds);

				teachers = teacherRepository.findByIdTeacherInAndSearchKeyContaining(finalTeacherIdList, searchTerm);

				if (teachers.isEmpty())
					throw new NullPointerException("Teachers data not found");

			}

			if (teachers.isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Teachers Available.");

			} else if (teachers.isEmpty()) {

				throw new NullPointerException("No Teachers Available.");
			} else {

				teachers.stream().forEach(t -> {

					TeacherInfoDTO tid = new TeacherInfoDTO();
					tid.setExpLevel(t.getExpLevel());
					tid.setIdTeacher(t.getIdTeacher());
					tid.setTeacherDesc(t.getTeacherDesc());
					tid.setTeacherName(t.getTeacherName());
					tid.setUserImage(t.getTeacherImage());
					tid.setPrimarySubejct(t.getPrimarySubject());
					tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
					tid.setTeacherHeader(t.getTeacherHeader());

					List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

					if (ts.isEmpty())
						throw new NullPointerException("TeachersSubject data not found");

					List<co.vistafoundation.vlearning.auth.dto.TeacherSubjectDTO> tsd = new ArrayList<>();

					if (!ts.isEmpty()) {
						ts.stream().forEach(tempSub -> {

							co.vistafoundation.vlearning.auth.dto.TeacherSubjectDTO tsTemp = new co.vistafoundation.vlearning.auth.dto.TeacherSubjectDTO();

							tsTemp.setProficiency(tempSub.getProficiency());
							Subject subject = subjectRepository.findByIdSubject(tempSub.getIdSubject());

							if (subject == null)
								throw new NullPointerException("Subject data not found");

							tsTemp.setSubject(subject.getSubjectName());

							Syllabus syllabus = syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus());

							if (syllabus == null)
								throw new NullPointerException("syllabus data not found");

							tsTemp.setSyllabus(syllabus.getSyllabusName());
							tsd.add(tsTemp);
						});
					}
					tid.setTeacherSubjects(tsd);
					temp.add(tid);
				});

				if (temp.isEmpty())
					throw new AppException("No Teacher's available");

				if ((pageable.getPageSize() * pageable.getPageNumber() >= temp.size())) {
					result.setData(null);
					result.setMessage("No More Teachers Available");
					result.setStatusCode(HttpStatus.OK.value());
				} else {

					int start = (int) pageable.getOffset();
					int end = (start + pageable.getPageSize()) > temp.size() ? temp.size()
							: (start + pageable.getPageSize());
					page = new PageImpl<>(temp.subList(start, end), pageable, temp.size());

					result.setData(page);
					result.setMessage("Request Successfull");
					result.setStatusCode(200);
					
					return result;
				}
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}

		return result;

	}

	@Cacheable(value = "teacherCache")
	@Override
	public Document<List<TeacherResponseDTO>> getLandingTeacherByCategory(String Category) {
		Document<List<TeacherResponseDTO>> result = new Document<>();
		try {

			List<Batch> batchList = batchRepository.findAll();

			if (batchList.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No Live Course are Available");
				return result;
			}

			HashSet<Long> tIds = new HashSet<>();

			batchList.removeIf(e -> !tIds.add(e.getIdTeacher()));

			List<Long> finalTeacherList = new ArrayList<>(tIds);
			List<Teacher> teacherlist =new ArrayList<>() ;
			
			if(Category.equalsIgnoreCase("EXTRA_CUR")) {
				teacherlist= teacherRepository.findAllByCategory(Category);
			}

			else {
			 teacherlist = teacherRepository.findByCategoryAndIdTeacherInAndDisplayInHomepageFlag(Category, finalTeacherList,Boolean.TRUE);
			}

			if (teacherlist.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No Teacher available for selected " + Category);
				return result;
			}

			List<TeacherResponseDTO> temp = new ArrayList<TeacherResponseDTO>();

			teacherlist.stream().forEach(t -> {

				TeacherResponseDTO tid = new TeacherResponseDTO();

				tid.setExpLevel(t.getExpLevel());
				tid.setIdTeacher(t.getIdTeacher());
				tid.setTeacherDesc(t.getTeacherDesc());
				tid.setTeacherName(t.getTeacherName());
				tid.setUserImage(t.getTeacherImage());
				tid.setPrimarySubejct(t.getPrimarySubject());
				tid.setDisplayInHomepageFlag(t.getDisplayInHomepageFlag());
				tid.setTeacherHeader(t.getTeacherHeader());

				List<TeacherSubject> ts = teacherSubjectRepository.findByTeacher(t);

				List<TeacherSubjectDTO> tsd = new ArrayList<>();

				if (!ts.isEmpty()) {
					ts.stream().forEach(tempSub -> {

						TeacherSubjectDTO tsTemp = new TeacherSubjectDTO();

						tsTemp.setProficiency(tempSub.getProficiency());

						Subject subject = subjectRepository.findByIdSubject(tempSub.getIdSubject());

						if (subject == null)
							throw new NullPointerException("Subject data not found");

						tsTemp.setSubjectName(subject.getSubjectName());

						Syllabus syllabus = syllabusRepository.findByIdSyllabus(tempSub.getIdSyllabus());

						if (syllabus == null)
							throw new NullPointerException("syllabus data not found");

						tsTemp.setSyllabusName(syllabus.getSyllabusName());
						tsd.add(tsTemp);
					});
				}
				tid.setTeacherSubjects(tsd);
				temp.add(tid);
			});

			result.setData(temp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<Teacher> createTeacher(CreateTeacherRequest createTeacherRequest) {

		Document<Teacher> doc = new Document<Teacher>();

		try {

			Role teacherRole = roleRepo.findByIdRole(3L);

			Set<Role> role = new HashSet<Role>();
			role.add(teacherRole);

			if (userRepository.existsByUsername(createTeacherRequest.getTeacher().getEmailId()))
				throw new AppException("Email id being used already.");

			if (userRepository.existsByMobileNumber(createTeacherRequest.getMobileNumber()))
				throw new AppException("Mobile number being used already.");

			List<TeacherAvailability> availabilityList = createTeacherRequest.getTeacher().getTeacherAvailability();
			List<TeacherAvailability> duplicateList = new ArrayList<>();
			if (!availabilityList.isEmpty()) {
				HashSet<TeacherAvailability> set = new HashSet<>();

				for (TeacherAvailability availablity : availabilityList) {
					if (!set.add(availablity)) {
						duplicateList.add(availablity);
					}
				}
			}
			if (!duplicateList.isEmpty()) {
				throw new AppException(
						"Teacher Availability Details " + duplicateList.toString() + " are already available.");
			}

			User user = new User(createTeacherRequest.getTeacherFirstName(), createTeacherRequest.getTeacherLastName(),
					createTeacherRequest.getTeacher().getEmailId(), createTeacherRequest.getTeacher().getEmailId(),
					encoder.encode(createTeacherRequest.getPassword()), "Teacher",
					createTeacherRequest.getMobileNumber(), "NA", 0L, role,
					createTeacherRequest.getTeacher().getTeacherImage());

			if (createTeacherRequest.getTeacher() != null) {

				if (createTeacherRequest.getTeacher().getIdWebexPool() != null) {
					WebExPool pool = webExPoolRepo
							.findByIdWebExPool(createTeacherRequest.getTeacher().getIdWebexPool());

					if (pool.getAvailableFlag())
						throw new AppException("Selected webEx Id is already assigned to a teacher.");

					pool.setAvailableFlag(true);
					webExPoolRepo.save(pool);
				}

				Teacher teacher2 = createTeacherRequest.getTeacher();
				teacher2.setSearchKey(
						teacher2.getTeacherName() + teacher2.getPrimarySubject() + teacher2.getTeacherDesc());
				teacher2.setUser(user);

				Teacher save = teacherRepository.save(teacher2);

				List<TeacherSubject> teachersubList = createTeacherRequest.getTeacherSubject();

				for (TeacherSubject ts : teachersubList) {
					ts.setTeacher(save);
					teacherSubjectRepository.save(ts);

				}

				// Send Teacher Credentials to his/her email
				emailService.sendTeacherCredentialsEmail(createTeacherRequest.getTeacher().getEmailId(),
						createTeacherRequest.getPassword(),
						createTeacherRequest.getTeacherFirstName() + " " + createTeacherRequest.getTeacherLastName(),
						createTeacherRequest.getMobileNumber());

				doc.setData(save);
				doc.setMessage("Teacher Created Successfully");
				doc.setStatusCode(201);
			}

		}

		catch (Exception exp)

		{

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					doc.setStatusCode(HttpStatus.CONFLICT.value());
					doc.setMessage("Duplicate Email or MobileNumber");

				}

				else {
					doc.setData(null);
					doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					doc.setMessage(exp.getLocalizedMessage());

				}

			} else {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				doc.setMessage(exp.getLocalizedMessage());

			}

		}
		return doc;
	}

	@Override
	public Document<List<TeacherAvailability>> getTeacherAvailability(Long idTeacher) {

		Document<List<TeacherAvailability>> result = new Document<>();

		try {

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			if (teacher == null)
				throw new NullPointerException("No Teacher found with this Id!");

			List<TeacherAvailability> availablityList = teacherAvailabilityRepository
					.findByIdTeacher(teacher.getIdTeacher());

			if (availablityList.isEmpty())
				throw new AppException("Teacher dosent have any Availablity");

			result.setData(availablityList);
			result.setMessage("Request Successfull");
			result.setStatusCode(200);

		} catch (Exception exp)

		{
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;
	}
}
