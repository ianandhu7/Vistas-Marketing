package co.vistafoundation.vlearning.liveclass.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassInfoDTO;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassQuestionAnswerRequestDTO;
import co.vistafoundation.vlearning.liveclass.dto.ResultDTO;
import co.vistafoundation.vlearning.liveclass.dto.YoutubeMasterDataListDTO;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.model.LiveClassCategory;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQndA;
import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;
import co.vistafoundation.vlearning.liveclass.model.LiveClassWatchedHistory;
import co.vistafoundation.vlearning.liveclass.model.NotifyLiveClass;
import co.vistafoundation.vlearning.liveclass.model.UserLiveClassAttended;
import co.vistafoundation.vlearning.liveclass.model.YoutubeMaster;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassCategoryRepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassQndARepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassQuestionRepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassWatchedHistoryRepo;
import co.vistafoundation.vlearning.liveclass.repository.NotifyLiveClassRepository;
import co.vistafoundation.vlearning.liveclass.repository.UserLiveClassAttendedRepository;
import co.vistafoundation.vlearning.liveclass.repository.YoutubeMasterRepository;
import co.vistafoundation.vlearning.liveclass.service.LiveClassService;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionService;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user.util.UserContentAccessValidator;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.Helper;
import co.vistafoundation.vlearning.utils.RandomStringGenerator;
import co.vistafoundation.vlearning.utils.TimeComparison;
import co.vistafoundation.vlearning.websocket.dto.ChatMessage;
import co.vistafoundation.vlearning.websocket.dto.MessageType;

/**
 * 
 * @author Sajini
 *
 */

@Service
public class LiveClassServiceImpl implements LiveClassService {

	@Autowired
	public LiveClassRepository liveClassRepository;

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private LiveClassWatchedHistoryRepo liveClassWatchedHistoryRepo;

	@Autowired
	private LiveClassQuestionRepository liveClassQuestionRepository;

	@Autowired
	private LiveClassQndARepository liveClassQndARepository;

	@Autowired
	private YoutubeMasterRepository youtubeMasterRepository;

	@Autowired
	private LiveClassCategoryRepository liveClassCategoryRepository;

	@Autowired
	private UserLiveClassAttendedRepository userLiveClassAttendedRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	NotifyLiveClassRepository notifyLiveClassRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	RandomStringGenerator randomStringGenerator;

	@Autowired
	UserDeviceRepository userDeviceRepository;
	
	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	
	SyllabusRepository syllabusRepository;
	
    @Autowired
	StateRepository stateRepository;
	
	@Autowired
	Helper helper;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@Value("${aws.s3.live_class_thumbnail}")
	private String bucketFolder;

	@Value("${aws.s3.live_class_pdf}")
	private String pdfBucketFolder;
	
	Long selectedLanguageId = 7L;
	
	@Autowired
	UserContentAccessValidator userContentAccessValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(LiveClassServiceImpl.class);

	public Document<List<LiveClass>> getAllBroadcastedClassesAcademic(Long idClassStandard, Long idLiveClassCategory) {

		Document<List<LiveClass>> result = new Document<List<LiveClass>>();
		try {
			List<LiveClass> liveClass = liveClassRepository
					.findAllByLiveCompletionFlagAndIdLiveClassCategoryAndIdClassStandardAndActiveFlag(Boolean.TRUE,
							idLiveClassCategory, idClassStandard,Boolean.TRUE);
			if (liveClass.isEmpty())
				throw new NullPointerException("No Broadcasted Classess found!!!");

			result.setData(liveClass);
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

	@Override
	public Document<List<LiveClass>> getAllBroadcastedClassesExtra(Long idLiveClassCategory) {
		Document<List<LiveClass>> result = new Document<List<LiveClass>>();
		try {
			List<LiveClass> liveClass = liveClassRepository
					.findAllByLiveCompletionFlagAndIdLiveClassCategoryAndActiveFlag(Boolean.TRUE, idLiveClassCategory,Boolean.TRUE);
			if (liveClass.isEmpty())
				throw new NullPointerException("No Extracurricular Broadcasted Classess found!!!");

			result.setData(liveClass);
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

	@Override
	public Document<?> checkForUserIdDuplicationinYoutubeMasterTable(String youtubeUserId) {

		Document<Boolean> doc = new Document<>();

		try {

			if (youtubeUserId == null) {
				throw new NullPointerException("Youtube UserId Cannot be Null");
			}

			Boolean exist = youtubeMasterRepository.existsByYoutubeUserId(youtubeUserId);
			if (exist) {
				doc.setData(Boolean.TRUE);
				doc.setMessage("This Youtube User Id has already been assigned");
				doc.setStatusCode(200);
			} else {
				doc.setData(Boolean.FALSE);
				doc.setMessage("This Youtube User Id is Available");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> assignYoutubeCredentialsToTeacher(YoutubeMaster youtubeMaster) {

		Document<YoutubeMaster> doc = new Document<>();

		try {

			if (youtubeMaster == null) {
				throw new NullPointerException("Youtube Master Data cannot be Null");
			}

			YoutubeMaster saved = youtubeMasterRepository.save(youtubeMaster);

			List<LiveClass> listOfAllLiveClasses = liveClassRepository.findByIdTeacherAndActiveFlag(youtubeMaster.getIdTeacher(),Boolean.TRUE);

			if (!listOfAllLiveClasses.isEmpty()) {
				for (LiveClass liveClass : listOfAllLiveClasses) {
					liveClass.setIdYoutubeMaster(youtubeMaster.getIdYoutubeMaster());
					liveClassRepository.save(liveClass);
				}
			}

			doc.setData(saved);
			doc.setMessage("Data saved to DB");
			doc.setStatusCode(201);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> getAllYoutubeMasterData() {

		Document<List<YoutubeMasterDataListDTO>> doc = new Document<>();

		try {

			List<YoutubeMaster> allYoutubeMasterData = youtubeMasterRepository.findAll();

			List<YoutubeMasterDataListDTO> youtubeMasterDataList = new ArrayList<>();

			if (allYoutubeMasterData.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Youtube Master Lists is Empty");
				doc.setStatusCode(200);
			} else {

				for (YoutubeMaster youtubeMaster : allYoutubeMasterData) {

					YoutubeMasterDataListDTO masterData = new YoutubeMasterDataListDTO();

					masterData.setIdYoutubeMaster(youtubeMaster.getIdYoutubeMaster());
					masterData.setYoutubeUserId(youtubeMaster.getYoutubeUserId());

					Teacher teacher = teacherRepository.findByIdTeacher(youtubeMaster.getIdTeacher());

					if (teacher == null) throw new AppException("No teacher data found.");

					masterData.setTeacher(teacher);

					youtubeMasterDataList.add(masterData);

				}

				doc.setData(youtubeMasterDataList);
				doc.setMessage("All Youtube Master Lists");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> deleteYoutubeMaster(Long idYoutubeMaster) {

		Document<Boolean> doc = new Document<>();

		try {

			if (idYoutubeMaster == null || idYoutubeMaster == 0) {
				throw new NullPointerException("idYoutubeMaster Cannot be Null");
			}

			youtubeMasterRepository.deleteById(idYoutubeMaster);

			doc.setData(true);
			doc.setMessage("Deletion Success");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@Override
	public Document<?> reAssignTeacherToYoutubeMaster(Long idSelectedTeacher, Long idYoutubeMaster) {

		Document<YoutubeMaster> doc = new Document<>();

		try {

			if (idSelectedTeacher == null) {
				throw new NullPointerException("Selected Teacher Id Cannot be null");
			}

			if (idYoutubeMaster == null) {
				throw new NullPointerException("Youtube Master Id Cannot be Null");
			}

			YoutubeMaster youtubeMaster = youtubeMasterRepository.findByIdYoutubeMaster(idYoutubeMaster);

			if (youtubeMaster != null) {
				youtubeMaster.setIdTeacher(idSelectedTeacher);
				YoutubeMaster updated = youtubeMasterRepository.save(youtubeMaster);

				doc.setData(updated);
				doc.setMessage("Teacher Re-Assigned Successfully");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<LiveClassDto> getFutureLiveClassDate() {

		Document<LiveClassDto> result = new Document<LiveClassDto>();
		try {

			LiveClass upcomingClass = liveClassRepository.findFirstByLiveCompletionFlagAndClassDateAndActiveFlagAndFromTimeAfter(
					Boolean.FALSE, LocalDate.now(),Boolean.TRUE, LocalTime.now());
			if (upcomingClass == null) {
				upcomingClass = liveClassRepository.findFirstByClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(
						LocalDate.now(), LocalTime.parse("00:00"), Boolean.FALSE,Boolean.TRUE);
				if (upcomingClass == null)
					throw new NullPointerException("No class Available for upcoming time");
			}

			Teacher teacher = teacherRepository.findByIdTeacher(upcomingClass.getIdTeacher());
			if (teacher == null)
				throw new NullPointerException("No teacher found");

			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setClassDate(upcomingClass.getClassDate());
			liveClassDto.setIdLiveClass(upcomingClass.getIdLiveClass());
			liveClassDto.setIdTeacher(upcomingClass.getIdTeacher());
			liveClassDto.setTeacherName(teacher.getTeacherName());
			liveClassDto.setFromTime(upcomingClass.getFromTime());
			liveClassDto.setToTime(upcomingClass.getToTime());
			liveClassDto.setPdfURL(upcomingClass.getPdfURL());
			liveClassDto.setThumbnailURL(upcomingClass.getThumbnailURL());
			// Added By Ahmed
			liveClassDto.setLiveClassDescription(upcomingClass.getLiveClassDesc());
			liveClassDto.setLiveClassHeading(upcomingClass.getLiveClassHeading());

			result.setData(liveClassDto);
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

	@Override
	public Document<?> checkForIsTeacherAlreadyAssignedYoutubeMaster(Long idTeacher) {

		Document<Boolean> doc = new Document<>();

		try {

			if (idTeacher == null) {
				throw new NullPointerException("Teacher Id Not Present for the Selected Teacher");
			}

			YoutubeMaster youtubeMaster = youtubeMasterRepository.findByIdTeacher(idTeacher);

			if (youtubeMaster != null) {
				doc.setData(Boolean.TRUE);
				doc.setMessage("Teacher Has already been assigned");
				doc.setStatusCode(200);
			} else {
				doc.setData(Boolean.FALSE);
				doc.setMessage("Teacher Has not assigned");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClassDetailsByIdLiveClass(Long idLiveClass) {
		Document result = new Document<>();
		LiveClassDto liveClassDto = new LiveClassDto();
		try {
			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
			if (liveClass == null)
				throw new AppException("No Live Classess found!!!");
			
			Language langugae = languageRepository.findByIdLanguage(liveClass.getIdLanguage());
			
			
			if (langugae == null)
				throw new AppException("Incorrect Language info found.");
		
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        UserPrincipal userPrincipal = null;
			
			 if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 
		            userPrincipal = (UserPrincipal) authentication.getPrincipal();
		            
		        	// check for the content authorization
					Boolean contentAccessFlag =  userContentAccessValidator.
							isAllowedToAccessTheContent(liveClass.getIdClassStandard(), liveClass.getIdSyllabus(), liveClass.getIdState());
					
					
					//check whether live class primary languages is not English.
					
					if (!langugae.getLanguage().equalsIgnoreCase("ENGLISH")) 
					{
						if(!userPrincipal.getSecondaryLanguage().equalsIgnoreCase(langugae.getLanguage())) 
						{
							result.setData(null);
							result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
							result.setMessage("User dosent have access to view this content.");
							return result;
						}
					}
					
		            
		            if (!contentAccessFlag) 
					{
						result.setData(null);
						result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
						result.setMessage("User dosent have access to view this content.");
						return result;
					}
		            
		            if (liveClass.isLiveCompletionFlag()) {
						
						
		    			
				        if (!(authentication instanceof AnonymousAuthenticationToken)) {
				            userPrincipal = (UserPrincipal) authentication.getPrincipal();
				        }
				        if (userPrincipal != null) {
				        	
				        	if(liveClass.getClassType().equalsIgnoreCase("premium")) 
				        	{
				        		StudentPostLoginDTO subscribedOrFreeuser = studentSubscriptionService.checkExistingSubscriptionLogin(userPrincipal.getUserSurId());
					        	if (!subscribedOrFreeuser.getSubscribedFlag() && !subscribedOrFreeuser.getTrialFlag()) {
					        		result.setData(null);
					        		result.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
					        		result.setMessage("Please subscribe to access webcasted videos!");
					        		return result;
					        	}
				        	}
				        	
				        }
				        
					}
		            
		        }
			
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setPdfURL(liveClass.getPdfURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			BeanUtils.copyProperties(liveClass, liveClassDto);
			
			ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
			if(classStandard==null)
              throw new NullPointerException("Invalid  idClassStandard found in live class data");				
			    liveClassDto.setClassStandard(classStandard.getClassStandadName());
				liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
			
			
			Language language = languageRepository.findByIdLanguage(liveClass.getIdLanguage());
			
			if (language == null) 
				throw new NullPointerException("Invalid  idLanguage found in live class data");
				liveClassDto.setLanguage(language.getLanguage());
			
			
			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher == null)
				throw new NullPointerException("Invalid  idTeacher found in live class data");
				liveClassDto.setTeacherName(teacher.getTeacherName());
			
			if(liveClass.getIdLiveClassCategory() == 1L) {
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			else {
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			

			result.setData(liveClassDto);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLiveClassQuestion(LiveClassQuestion liveClassQuestion) {
		Document result = new Document<>();
		try {
			if (liveClassQuestion == null)
				throw new AppException("No Live Classess found!!!");
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
			
			if (!liveClassQuestion.getUserSurId().equals(loggedInUser.getUserSurId())) {
				
				result.setData(null);
				result.setMessage("User dosent have access to add this question.");
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				return result;

			}

			LiveClassQuestion res = liveClassQuestionRepository.save(liveClassQuestion);
			result.setData(res);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClassAllQuestionAndAnswer(Long idLiveClass) {
		Document result = new Document<>();
		List<ChatMessage> chatList = new ArrayList<ChatMessage>();
		try {
			Teacher teacher = null;
			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
			if (liveClass==null) {
				throw new AppException("Live class data not found!!!");
			}

			teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher==null) {
				throw new AppException("Teacher data not found in Live Class!!!");
			}

			List<LiveClassQndA> list = liveClassQndARepository.findByIdLiveClass(idLiveClass);
			if (!list.isEmpty()) {
				for (LiveClassQndA liveClassQndA : list) {
					User user = userRepository.findByUserSurId(liveClassQndA.getUserSurId());
					if (user == null) {
						throw new AppException("User data not found in Live Class!!!");
					}
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setCreatedAt(liveClassQndA.getCreatedAt());
					chatMessage.setUpdatedAt(liveClassQndA.getUpdatedAt());
					chatMessage.setType(MessageType.CHAT);
					chatMessage.setAnswer(liveClassQndA.getAnswerText());
					chatMessage.setQuestion(liveClassQndA.getQuestionText());
					chatMessage.setIdLiveClass(liveClassQndA.getIdLiveClass());
					chatMessage.setReceiver(teacher.getTeacherName());
					chatMessage.setReceiverIdVlUser(teacher.getIdTeacher());
					chatMessage.setSenderIdVlUser(liveClassQndA.getUserSurId());
					chatMessage.setSender(user.getFirstName());
					chatList.add(chatMessage);
				}
			}
			result.setData(chatList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClassListByIdTeacher(Long idTeacher) {
		Document result = new Document<>();
		List<LiveClassDto> classList = new ArrayList<LiveClassDto>();
		try {
			if (idTeacher == null || idTeacher == 0)
				throw new AppException("No Live Classess found!!!");

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			if (teacher == null)
				throw new AppException("Could not find any teacher");

			List<LiveClass> list = liveClassRepository.findByIdTeacherAndActiveFlag(idTeacher,Boolean.TRUE);
			if (!list.isEmpty()) {
				for (LiveClass liveClass : list) {
					LiveClassDto liveClassDto = new LiveClassDto();

					liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
					liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
					liveClassDto.setClassDate(liveClass.getClassDate());
					liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
					liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
					classList.add(liveClassDto);
				}
			}

			result.setData(classList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllQuestions(Long idLiveClass) {
		Document result = new Document<>();
		try {
			Teacher teacher = null;
			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
			if (liveClass==null) {
				throw new AppException("Live class data not found!!!");
			}

			teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher==null) {
				throw new AppException("Teacher data not found in Live Class!!!");
			}

			List<LiveClassQuestion> questionsList = liveClassQuestionRepository.findByIdLiveClass(idLiveClass);

			if (questionsList.isEmpty())
				throw new AppException("No Question list Found!!!");

			List<ChatMessage> chatList = new ArrayList<>();

			for (LiveClassQuestion lcq : questionsList) {

				User user = userRepository.findByUserSurId(lcq.getUserSurId());
				if (user == null) {
					throw new AppException("User data not found in Live Class!!!");
				}
				ChatMessage chatMessage = new ChatMessage();
				chatMessage.setType(MessageType.CHAT);
				chatMessage.setAnswer("");
				chatMessage.setQuestion(lcq.getQuestionText());
				chatMessage.setIdLiveClass(lcq.getIdLiveClass());
				chatMessage.setReceiver(teacher.getTeacherName());
				chatMessage.setReceiverIdVlUser(teacher.getIdTeacher());
				chatMessage.setSenderIdVlUser(lcq.getUserSurId());
				chatMessage.setSender(user.getFirstName());
				chatList.add(chatMessage);
			}

			result.setData(chatList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLiveClassQuestionAndAnswer(LiveClassQuestionAnswerRequestDTO request) {
		Document result = new Document<>();
		try {
			if (request == null)
				throw new AppException("No Question And Answer Inserted!!!");

			LiveClassQndA liveClassQndA = new LiveClassQndA(request.getIdLiveClass(), request.getUserSurId(),
					request.getQuestion(), request.getAnswer());

			LiveClassQndA res = liveClassQndARepository.save(liveClassQndA);
			result.setData(res);
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

	@Override
	public Document<?> fetchAllLiveClassCategories() {

		Document<List<LiveClassCategory>> doc = new Document<>();

		try {

			List<LiveClassCategory> listOfAllLiveClassCategory = liveClassCategoryRepository.findAll();

			if (listOfAllLiveClassCategory.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Live Class Category Lists is Empty");
				doc.setStatusCode(200);
			} else {
				doc.setData(listOfAllLiveClassCategory);
				doc.setMessage("List of All Live Class Categories");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> getYoutubeMatserDataByIdTeacher(Long idTeacher) {

		Document<YoutubeMaster> doc = new Document<>();

		try {

			if (idTeacher == null) {
				throw new NullPointerException("Teacher Id Not Found for the selected Teacher");
			}

			YoutubeMaster youtubeMaster = youtubeMasterRepository.findByIdTeacher(idTeacher);

			if (youtubeMaster == null) {
				doc.setData(null);
				doc.setMessage(
						"Youtube Credentials has not been assigned to this Teacher.Please assign Credential for this Teacher");
				doc.setStatusCode(500);
			} else {
				doc.setData(youtubeMaster);
				doc.setMessage("youtube Master Data for the Selected Teacher");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> createLiveClass(LiveClass liveClass) {

		Document<LiveClass> doc = new Document<>();

		try {

			if (liveClass == null) {
				throw new NullPointerException("Live Class Data Cannot be Null");
			}

			if(liveClass.getClassDate()==null){
				LocalDate classDate = LocalDate.of(9999, 9, 9);
				liveClass.setClassDate(classDate);
			}
			//			if(liveClass.getFromTime()==null) {
			//				LocalTime fromTime = LocalTime.of(00, 00, 00);
			//				liveClass.setFromTime(fromTime);
			//			}
			//			
			//			if(liveClass.getToTime()==null) {
			//				LocalTime toTime = LocalTime.of(00, 00, 00);
			//				liveClass.setToTime(toTime);
			//			}

			if(liveClass.getIdTeacher()==null) {
				liveClass.setIdTeacher(0L);
				liveClass.setIdYoutubeMaster(0L);
			}
             
			String classType = liveClass.getClassType() == null ? "premium" : liveClass.getClassType();
			liveClass.setClassType(classType);
			
			liveClass.setActiveFlag(true);
			TimeComparison.averageTime(liveClass.getFromTime(), liveClass.getToTime());

			List<LiveClass> listOfLiveClass = liveClassRepository.findByIdTeacherAndClassDateAndActiveFlag(liveClass.getIdTeacher(),
					liveClass.getClassDate(),Boolean.TRUE);

			if (listOfLiveClass.isEmpty()) {
				LiveClass saved = liveClassRepository.save(liveClass);

				doc.setData(saved);
				doc.setMessage("Live Class Saved Successfully");
				doc.setStatusCode(HttpStatus.CREATED.value());
			}

			else {

//				LocalTime fromTime = liveClass.getFromTime();
//				LocalTime toTime = liveClass.getToTime();

				boolean isInBetween = false;

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

				for (LiveClass liveClass2 : listOfLiveClass) {

					isInBetween = TimeComparison.checkTimeInBetween(liveClass.getFromTime().format(formatter),
							liveClass.getToTime().format(formatter), liveClass2.getFromTime().format(formatter),
							liveClass2.getToTime().format(formatter));
					
					if (isInBetween)
						break;
				}

				if (isInBetween) {
					doc.setData(null);
					doc.setMessage(
							"You have already scheduled Live Class for this teacher between this timing for this date");
					doc.setStatusCode(HttpStatus.CONFLICT.value());
				} else {
					LiveClass saved = liveClassRepository.save(liveClass);

					doc.setData(saved);
					doc.setMessage("Live Class Saved Successfully");
					doc.setStatusCode(HttpStatus.CREATED.value());
				}
			}
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document fetchAllLiveClassByTeacherAndDate(Long idTeacher, LocalDate date) {

			Document doc = new Document<>();

		try {

			List<LiveClass> listOfLiveClassByTeacherAndDate = null;
			System.out.println("idTeacher "+idTeacher);
			System.out.println("date "+date);
			if (idTeacher == 0 && date != null) {
				//throw new NullPointerException("Teacher Id Not found for the Selected Teacher");
				listOfLiveClassByTeacherAndDate = liveClassRepository.findByClassDateAndActiveFlagOrderByClassDateDesc(date,Boolean.TRUE);
			}
			else if(idTeacher != 0 && date == null) {
//				listOfLiveClassByTeacherAndDate = liveClassRepository.findByIdTeacherAndLiveCompletionFlag(idTeacher,false);
				listOfLiveClassByTeacherAndDate = liveClassRepository.findAllByIdTeacherAndActiveFlagOrderByClassDateDesc(idTeacher,Boolean.TRUE);
			}
			else if(idTeacher == 0 && date == null) {
				LocalDate classDate = LocalDate.of(9999, 9, 9);
				System.out.println("classDate "+classDate);
				listOfLiveClassByTeacherAndDate = liveClassRepository.findByClassDateAndActiveFlagOrderByClassDateDesc(classDate,Boolean.TRUE);
			} 
			else{
															
				listOfLiveClassByTeacherAndDate = liveClassRepository.findByIdTeacherAndClassDateAndActiveFlagOrderByClassDateDesc(idTeacher,date,Boolean.TRUE);
			}


			if (date == null) {
				//throw new NullPointerException("Date cannot be Null.Please Select Date");

			}

			if(listOfLiveClassByTeacherAndDate.isEmpty() || listOfLiveClassByTeacherAndDate ==null) {
				doc.setData(new ArrayList<>());
				doc.setMessage("No Live Class has been assigned to this teacher fot the selected date");
				doc.setStatusCode(500);
			}
			else 
			{
				List<LiveClassInfoDTO> infoDTOs = new ArrayList<LiveClassInfoDTO>();

				for (LiveClass liveClass : listOfLiveClassByTeacherAndDate) {

					LiveClassInfoDTO classInfoDTO = new LiveClassInfoDTO();

					ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());

					if (classStandard == null) throw new AppException("No classStandard data found.");
					classInfoDTO.setActualClassEndDate(liveClass.getActualClassEndDate());
					classInfoDTO.setActualClassStartDate(liveClass.getActualClassStartDate());
					classInfoDTO.setClassDate(liveClass.getClassDate());
					classInfoDTO.setClassStandard(classStandard);
					classInfoDTO.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
					classInfoDTO.setFromTime(liveClass.getFromTime());
					classInfoDTO.setToTime(liveClass.getToTime());
					classInfoDTO.setIdLiveClass(liveClass.getIdLiveClass());
					classInfoDTO.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
					classInfoDTO.setIdTeacher(liveClass.getIdTeacher());
					classInfoDTO.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
					classInfoDTO.setLiveClassDesc(liveClass.getLiveClassDesc());
					classInfoDTO.setLiveClassHeading(liveClass.getLiveClassHeading());
					classInfoDTO.setLiveClassURL(liveClass.getLiveClassURL());
					classInfoDTO.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
					classInfoDTO.setThumbnailURL(liveClass.getThumbnailURL());
					classInfoDTO.setPdfURL(liveClass.getPdfURL());
					classInfoDTO.setIntroVideoURL(liveClass.getIntroVideoURL());
					classInfoDTO.setIdLanguage(liveClass.getIdLanguage());
					classInfoDTO.setIdSubject(liveClass.getIdSubject());
					classInfoDTO.setIdSyllabus(liveClass.getIdSyllabus());
					classInfoDTO.setIdState(liveClass.getIdState());
					classInfoDTO.setClassType(liveClass.getClassType());

					infoDTOs.add(classInfoDTO);
				}

				doc.setData(infoDTOs);
				doc.setMessage("List Of All Live Class Assigned to this Teacher fot the selected Date");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document deleteLiveClassByIdLiveClass(Long idLiveClass) {

		Document doc = new Document<>();

		try {

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
			if(liveClass.isCurrentRunningFlag()||liveClass.isLiveCompletionFlag()) {
				
				liveClass.setActiveFlag(false);
				liveClassRepository.save(liveClass);
			}
			else {
				liveClassRepository.deleteById(idLiveClass);
			}
			
			doc.setData(Boolean.TRUE);
			doc.setMessage("Live Class Deleted Successfully");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> reAssignTeacherToLiveClass(Long idSelectedTeacher, Long idLiveClass) {
		Document<LiveClass> doc = new Document<>();

		try {

			if (idSelectedTeacher == null) {
				throw new NullPointerException("Selected Teacher Id Cannot be null");
			}

			if (idLiveClass == null) {
				throw new NullPointerException("Live Class Id Cannot be Null");
			}

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass, Boolean.TRUE);

			if (liveClass != null) {
				liveClass.setIdTeacher(idSelectedTeacher);
				LiveClass updated = liveClassRepository.save(liveClass);

				doc.setData(updated);
				doc.setMessage("Teacher Re-Assigned Successfully");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> fetchAllLiveClassesAssignedToTeacher(Long idTeacher) {

		Document<List<LiveClassInfoDTO>> doc = new Document<>();

		try {

			if (idTeacher == null || idTeacher.equals(0L)) {
				throw new NullPointerException("Teacher Id Not Found. Make Sure You have Logged In");
			}

			List<LiveClass> listOfAllLiveClassesAssignedForThisTeacher = liveClassRepository
					.findByIdTeacherAndClassDateAndActiveFlag(idTeacher, LocalDate.now(),Boolean.TRUE);

			List<LiveClassInfoDTO> infoDTOs = new ArrayList<LiveClassInfoDTO>();

			for (LiveClass liveClass : listOfAllLiveClassesAssignedForThisTeacher) {

				LiveClassInfoDTO classInfoDTO = new LiveClassInfoDTO();

				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());

				if (classStandard == null) throw new AppException("No classStandard data found.");

				classInfoDTO.setActualClassEndDate(liveClass.getActualClassEndDate());
				classInfoDTO.setActualClassStartDate(liveClass.getActualClassStartDate());
				classInfoDTO.setClassDate(liveClass.getClassDate());
				classInfoDTO.setClassStandard(classStandard);
				classInfoDTO.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				classInfoDTO.setFromTime(liveClass.getFromTime());
				classInfoDTO.setToTime(liveClass.getToTime());
				classInfoDTO.setIdLiveClass(liveClass.getIdLiveClass());
				classInfoDTO.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				classInfoDTO.setIdTeacher(liveClass.getIdTeacher());
				classInfoDTO.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				classInfoDTO.setLiveClassDesc(liveClass.getLiveClassDesc());
				classInfoDTO.setLiveClassHeading(liveClass.getLiveClassHeading());
				classInfoDTO.setLiveClassURL(liveClass.getLiveClassURL());
				classInfoDTO.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				classInfoDTO.setThumbnailURL(liveClass.getThumbnailURL());
				classInfoDTO.setPdfURL(liveClass.getPdfURL());
				classInfoDTO.setIntroVideoURL(liveClass.getIntroVideoURL());
				classInfoDTO.setIdLanguage(liveClass.getIdLanguage());
				classInfoDTO.setIdSyllabus(liveClass.getIdSyllabus());
				classInfoDTO.setIdState(liveClass.getIdState());
				infoDTOs.add(classInfoDTO);
			}

			if (listOfAllLiveClassesAssignedForThisTeacher.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("No Live Class Assigned to this Teacher By Admin");
				doc.setStatusCode(200);
			}

			else {
				doc.setData(infoDTOs);
				doc.setMessage("List of All Live Class Assigned to this Teacher By Admin");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> filterLiveClassByDateTeacher(Long idTeacher, LocalDate date) {

		Document<List<LiveClassInfoDTO>> doc = new Document<>();

		try {

			if (idTeacher == null || idTeacher.equals(0L)) {
				throw new NullPointerException("Teacher Id Not Found. Make Sure You have Logged In");
			}

			if (date == null) {
				throw new NullPointerException("Date Cannot be Null. Please Choose a Date");
			}

			List<LiveClass> listOfAllLiveClassesAssignedForThisTeacher = liveClassRepository
					.findByIdTeacherAndClassDateAndActiveFlag(idTeacher, date,Boolean.TRUE);

			List<LiveClassInfoDTO> infoDTOs = new ArrayList<LiveClassInfoDTO>();

			for (LiveClass liveClass : listOfAllLiveClassesAssignedForThisTeacher) {

				LiveClassInfoDTO classInfoDTO = new LiveClassInfoDTO();

				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());

				if (classStandard == null) throw new AppException("No classStandard data found."); 

				classInfoDTO.setActualClassEndDate(liveClass.getActualClassEndDate());
				classInfoDTO.setActualClassStartDate(liveClass.getActualClassStartDate());
				classInfoDTO.setClassDate(liveClass.getClassDate());
				classInfoDTO.setClassStandard(classStandard);
				classInfoDTO.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				classInfoDTO.setFromTime(liveClass.getFromTime());
				classInfoDTO.setToTime(liveClass.getToTime());
				classInfoDTO.setIdLiveClass(liveClass.getIdLiveClass());
				classInfoDTO.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				classInfoDTO.setIdTeacher(liveClass.getIdTeacher());
				classInfoDTO.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				classInfoDTO.setLiveClassDesc(liveClass.getLiveClassDesc());
				classInfoDTO.setLiveClassHeading(liveClass.getLiveClassHeading());
				classInfoDTO.setLiveClassURL(liveClass.getLiveClassURL());
				classInfoDTO.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				classInfoDTO.setThumbnailURL(liveClass.getThumbnailURL());
				classInfoDTO.setPdfURL(liveClass.getPdfURL());
				classInfoDTO.setIntroVideoURL(liveClass.getIntroVideoURL());
				classInfoDTO.setIdLanguage(liveClass.getIdLanguage());
				classInfoDTO.setIdSubject(liveClass.getIdSubject());
				classInfoDTO.setIdSyllabus(liveClass.getIdSyllabus());
				classInfoDTO.setIdState(liveClass.getIdState());
				classInfoDTO.setClassType(liveClass.getClassType());
				infoDTOs.add(classInfoDTO);
			}

			if (listOfAllLiveClassesAssignedForThisTeacher.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("No Live Class Assigned");
				doc.setStatusCode(500);
			}

			else {
				doc.setData(infoDTOs);
				doc.setMessage("List of All Live Class Assigned");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> startLiveWebinarForTeacher(Long idLiveClass) {

		Document<LiveClass> doc = new Document<>();

		try {

			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live CLass Id Cannot be Null");
			}

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (liveClass != null) {
				liveClass.setActualClassStartDate(Instant.now());
				liveClass.setCurrentRunningFlag(Boolean.TRUE);
				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				LiveClass saved = liveClassRepository.save(liveClass);

				Set<String> regDeviceIdSet = new HashSet<String>();
				List<String> regDeviceIdList  = new ArrayList<String>();
				List<NotifyLiveClass> notifyLiveClassList = notifyLiveClassRepo.findByIdLiveClass(idLiveClass);
				System.out.println("notifyLiveClassList "+notifyLiveClassList);
				if(!notifyLiveClassList.isEmpty()) {
					for(NotifyLiveClass notifyLiveClass:notifyLiveClassList) {
						System.out.println("notifyLiveClassuserId "+notifyLiveClass.getIdVlUser());
						UserDevice userDevice=userDeviceRepository.findByUserSurIdAndDeviceType(notifyLiveClass.getIdVlUser(),"MOBILE");
						if(userDevice!=null) {
							System.out.println("deviceId "+userDevice.getDeviceId());
							regDeviceIdSet.add(userDevice.getDeviceId());
						}
						
						User u =  userRepository.findByUserSurId(notifyLiveClass.getIdVlUser());
						
						if (u != null) {
							
							emailService.sendNotificationEmailwhenLiveClassStarts(u.getEmail(),u.getFirstName(),saved.getLiveClassHeading(),liveClass.getIdLiveClass());
						}
						
					}

				}
				//sending notification
				if(!regDeviceIdSet.isEmpty()) {
					regDeviceIdList.addAll(regDeviceIdSet);
					String message = "Your "+liveClass.getLiveClassHeading()+" has started, conducting by "+teacher.getTeacherName();
					notificationService.sendNotification(regDeviceIdList,message,"idLiveClass:"+liveClass.getIdLiveClass());
				}

				doc.setData(saved);
				doc.setMessage("Live Class Details Updated");
				doc.setStatusCode(200);
			} else {
				throw new NullPointerException("Live Class Details is Null");
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> endLiveWebinarForTeacher(Long idLiveClass) {

		Document<LiveClass> doc = new Document<>();

		try {
			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live CLass Id Cannot be Null");
			}

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (liveClass != null) {
				liveClass.setActualClassEndDate(Instant.now());
				liveClass.setCurrentRunningFlag(Boolean.FALSE);
				liveClass.setLiveCompletionFlag(Boolean.TRUE);
				LiveClass saved = liveClassRepository.save(liveClass);
				doc.setData(saved);
				doc.setMessage("Live Class Ended. Details Updated");
				doc.setStatusCode(200);
			} else {
				throw new NullPointerException("Live Class Details is Null");
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@Override
	public Document<?> scheduleLiveClassByTeacher(LiveClass liveClass) {

		Document<LiveClass> doc = new Document<>();

		try {

			if (liveClass == null) {
				throw new NullPointerException("LiveClass Details Cannot be Null");
			}


			if(liveClass.getClassDate()==null){	
				LocalDate classDate = LocalDate.of(9999, 9, 9);
				liveClass.setClassDate(classDate);
			}

			List<LiveClass> listOfLiveClass = liveClassRepository.findByIdTeacherAndClassDateAndActiveFlag(liveClass.getIdTeacher(),
					liveClass.getClassDate(),Boolean.TRUE);

			YoutubeMaster youtubeMaster = youtubeMasterRepository.findByIdTeacher(liveClass.getIdTeacher());

			if (youtubeMaster == null) {
				throw new NullPointerException("YouTube Credentials Not Assigned By Admin");
			}

			liveClass.setIdYoutubeMaster(youtubeMaster.getIdYoutubeMaster());
			liveClass.setActiveFlag(true);			
			
			if (listOfLiveClass.isEmpty()) {

				LiveClass saved = liveClassRepository.save(liveClass);
				doc.setData(saved);
				doc.setMessage("Live Class Saved Successfully");
				doc.setStatusCode(HttpStatus.CREATED.value());
			}

			else {

//				LocalTime fromTime = liveClass.getFromTime();
//				LocalTime toTime = liveClass.getToTime();

				boolean isInBetween = false;

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

				for (LiveClass liveClass2 : listOfLiveClass) {

					isInBetween = TimeComparison.checkTimeInBetween(liveClass.getFromTime().format(formatter),
							liveClass.getToTime().format(formatter), liveClass2.getFromTime().format(formatter),
							liveClass2.getToTime().format(formatter));
				}

				if (isInBetween) {
					doc.setData(null);
					doc.setMessage("You have already scheduled Live Class between this timing for this date");
					doc.setStatusCode(HttpStatus.CONFLICT.value());
				} else {
					LiveClass saved = liveClassRepository.save(liveClass);

					doc.setData(saved);
					doc.setMessage("Live Class Saved Successfully");
					doc.setStatusCode(HttpStatus.CREATED.value());
				}
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> editLiveClassByTeacher(LiveClass liveClass, Long idLiveClass) {

		Document<LiveClass> doc = new Document<>();

		try {

			if (liveClass == null) {
				throw new NullPointerException("LiveClass Details Cannot be Null");
			}

			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live Class Id Not Present");
			}

			LiveClass existingLiveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (existingLiveClass == null) {
				throw new NullPointerException("Live Class Details Found. But Its Null");
			}

			if(liveClass.getClassDate()==null){
				LocalDate classDate = LocalDate.of(9999, 9, 9);
				existingLiveClass.setClassDate(classDate);
			}
			else
			existingLiveClass.setClassDate(liveClass.getClassDate());
			existingLiveClass.setLiveClassHeading(liveClass.getLiveClassHeading());
			existingLiveClass.setLiveClassDesc(liveClass.getLiveClassDesc());
			existingLiveClass.setLiveClassURL(liveClass.getLiveClassURL());
			existingLiveClass.setIntroVideoURL(liveClass.getIntroVideoURL());
			existingLiveClass.setThumbnailURL(liveClass.getThumbnailURL());
			existingLiveClass.setPdfURL(liveClass.getPdfURL());
			existingLiveClass.setIdClassStandard(liveClass.getIdClassStandard());
			existingLiveClass.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			existingLiveClass.setFromTime(liveClass.getFromTime());
			existingLiveClass.setToTime(liveClass.getToTime());
			existingLiveClass.setIdLanguage(liveClass.getIdLanguage());
			existingLiveClass.setIdSyllabus(liveClass.getIdSyllabus());
			existingLiveClass.setClassType(liveClass.getClassType());
			
			LiveClass updated = liveClassRepository.save(existingLiveClass);

			doc.setData(updated);
			doc.setMessage("Live Class Details Updated");
			doc.setStatusCode(201);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClass(Long idClassStandard) {
		Document result = new Document<>();
		try {

			LiveClass liveClass = liveClassRepository
					.findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(idClassStandard,
							Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);

			if (liveClass == null)
				throw new NullPointerException("No Live Class Found!!");

			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setIdTeacher(liveClass.getIdTeacher());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
			liveClassDto.setPdfURL(liveClass.getPdfURL());
			LiveClassCategory category = liveClassCategoryRepository
					.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			if (category != null)
				liveClassDto.setClassCategory(category.getLiveClassCategoryName());

			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher != null)
				liveClassDto.setTeacherName(teacher.getTeacherName());
			
			ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
			if(classStandard!=null) {
				liveClassDto.setClassStandard(classStandard.getClassStandadName());
				liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
			}
			
			Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
			if(subject!=null) {
				liveClassDto.setIdSubject(subject.getIdSubject());
				liveClassDto.setSubjectName(subject.getSubjectName());
			}

			result.setData(liveClassDto);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveAcademicClasses(Long idClassStandard, Long idLiveClassCategory) {
		Document result = new Document<>();
		List<LiveClassDto> academicClassList = new ArrayList<LiveClassDto>();
		try {

			List<LiveClass> liveClassList = liveClassRepository
					.findByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
							idClassStandard, idLiveClassCategory, Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);

			if (liveClassList.isEmpty())
				throw new NullPointerException("No Academic Live Classes Found!!");

			for (LiveClass liveClass : liveClassList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}
				academicClassList.add(liveClassDto);
			}

			result.setData(academicClassList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getUpcomingAcademicClasses(Long idClassStandard, Long idLiveClassCategory) {
		Document result = new Document<>();
		List<LiveClassDto> upcomingClassList = new ArrayList<LiveClassDto>();
		try {

			List<LiveClass> upcomingAcademicClassList = liveClassRepository
					.findByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndClassDateAndCurrentRunningFlagAndActiveFlag(
							idClassStandard, idLiveClassCategory, Boolean.FALSE, LocalDate.now(), Boolean.FALSE,Boolean.TRUE);
			if (upcomingAcademicClassList.isEmpty()) {
				upcomingAcademicClassList = liveClassRepository
						.findByIdClassStandardAndIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(
								idClassStandard, idLiveClassCategory, LocalDate.now(), LocalTime.parse("00:00"),
								Boolean.FALSE,Boolean.TRUE);
				if (upcomingAcademicClassList.isEmpty())
					throw new NullPointerException("No Upcoming Academic Class Available");
			}

			for (LiveClass upcomingAcademicClass : upcomingAcademicClassList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(upcomingAcademicClass.getIdLiveClass());
				liveClassDto.setClassDate(upcomingAcademicClass.getClassDate());
				liveClassDto.setIdTeacher(upcomingAcademicClass.getIdTeacher());
				liveClassDto.setFromTime(upcomingAcademicClass.getFromTime());
				liveClassDto.setToTime(upcomingAcademicClass.getToTime());
				liveClassDto.setLiveClassHeading(upcomingAcademicClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(upcomingAcademicClass.getLiveClassDesc());
				liveClassDto.setLiveClassURL(upcomingAcademicClass.getLiveClassURL());
				liveClassDto.setIdYoutubeMaster(upcomingAcademicClass.getIdYoutubeMaster());
				liveClassDto.setPdfURL(upcomingAcademicClass.getPdfURL());
				liveClassDto.setThumbnailURL(upcomingAcademicClass.getThumbnailURL());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(upcomingAcademicClass.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(upcomingAcademicClass.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}
				upcomingClassList.add(liveClassDto);

			}

			result.setData(upcomingClassList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveExtraCurricularClasses(Long idLiveClassCategory) {
		Document result = new Document<>();
		List<LiveClassDto> academicClassList = new ArrayList<LiveClassDto>();
		try {

			List<LiveClass> liveClassList = liveClassRepository
					.findByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
							idLiveClassCategory, Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);

			if (liveClassList.isEmpty())
				throw new NullPointerException("No Extra Curricular Live Classes Found!!");

			for (LiveClass liveClass : liveClassList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}
				academicClassList.add(liveClassDto);
			}

			result.setData(academicClassList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getUpcomingExtraCurricular(Long idLiveClassCategory) {

		Document result = new Document<>();
		List<LiveClassDto> upcomingExtraCurrList = new ArrayList<LiveClassDto>();
		try {

			List<LiveClass> upcomingExtraCurricularList = liveClassRepository
					.findByIdLiveClassCategoryAndLiveCompletionFlagAndClassDateAndActiveFlagAndFromTimeAfter(idLiveClassCategory,
							Boolean.FALSE, LocalDate.now(),Boolean.TRUE, LocalTime.now());
			if (upcomingExtraCurricularList.isEmpty()) {
				upcomingExtraCurricularList = liveClassRepository
						.findByIdLiveClassCategoryAndClassDateAfterAndFromTimeAfterAndLiveCompletionFlagAndActiveFlag(
								idLiveClassCategory, LocalDate.now(), LocalTime.parse("00:00"), Boolean.FALSE, Boolean.TRUE);
				if (upcomingExtraCurricularList.isEmpty())
					throw new NullPointerException("No Upcoming Extra Curricular Classes Found!!");
			}

			for (LiveClass upcomingExtraCurricular : upcomingExtraCurricularList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(upcomingExtraCurricular.getIdLiveClass());
				liveClassDto.setClassDate(upcomingExtraCurricular.getClassDate());
				liveClassDto.setIdTeacher(upcomingExtraCurricular.getIdTeacher());
				liveClassDto.setFromTime(upcomingExtraCurricular.getFromTime());
				liveClassDto.setToTime(upcomingExtraCurricular.getToTime());
				liveClassDto.setLiveClassHeading(upcomingExtraCurricular.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(upcomingExtraCurricular.getLiveClassDesc());
				liveClassDto.setLiveClassURL(upcomingExtraCurricular.getLiveClassURL());
				liveClassDto.setIdYoutubeMaster(upcomingExtraCurricular.getIdYoutubeMaster());
				liveClassDto.setPdfURL(upcomingExtraCurricular.getPdfURL());
				liveClassDto.setThumbnailURL(upcomingExtraCurricular.getThumbnailURL());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(upcomingExtraCurricular.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(upcomingExtraCurricular.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}
				upcomingExtraCurrList.add(liveClassDto);

			}

			result.setData(upcomingExtraCurrList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClassDetailsByIdClassStandard(Long idClassStandard) {
		Document result = new Document<>();
		List<LiveClassDto> classList = new ArrayList<LiveClassDto>();

		try {
			List<LiveClass> liveClassList = liveClassRepository.findByIdClassStandardAndActiveFlag(idClassStandard,Boolean.TRUE);
			if (liveClassList.isEmpty())
				throw new AppException("No Live Classess found!!!");

			for (LiveClass liveClass : liveClassList) {
				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher != null)
					liveClassDto.setTeacherName(teacher.getTeacherName());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}

				classList.add(liveClassDto);
			}

			result.setData(classList);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveLiveClassAttended(UserLiveClassAttended request) {
		Document doc = new Document<>();
		try {

			UserLiveClassAttended result = null;
			if (request == null) {
				throw new NullPointerException("Live Class Attended Data cannot be Null");
			}

			UserLiveClassAttended classAttended = userLiveClassAttendedRepository
					.findByIdLiveClassAndUserSurId(request.getIdLiveClass(), request.getUserSurId());

			if (classAttended != null) {
				classAttended.setUpdatedAt(Instant.now());
				result = userLiveClassAttendedRepository.save(classAttended);
			} else {
				result = userLiveClassAttendedRepository.save(request);
			}

			doc.setData(result);
			doc.setMessage("Data Saved Sucessfully");
			doc.setStatusCode(HttpStatus.OK.value());
			return doc;

		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<?> uploadThumbnailImageToS3(MultipartFile file) {
		Document<String> doc = new Document<>();
		try {

			final File tempFile = convertMultiPartFileToFile(file);
			String imageLink = uploadFileToS3Bucket(bucketFolder, tempFile);
			System.err.println("S3 Url==>" + imageLink);
			boolean isDeletedLogFile = tempFile.delete();
			logger.info("Thumbnail Image deleted from the system : "+isDeletedLogFile );

			doc.setData(imageLink);
			doc.setMessage("Image Uploaded Successfully");
			doc.setStatusCode(HttpStatus.OK.value());
			return doc;
		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return file;
	}




	private String uploadFileToS3Bucket(final String bucketName, final File file) {
		String uniqueFile=randomStringGenerator.generateTimeStamp();

		// Object is created in PublicRead mode
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketFolder, uniqueFile, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketFolder, uniqueFile).toString();
		return s3Url;
	}

	//After LogedIn
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllLiveClassesByCategory(Long idClassStandard, Long idLiveClassCategory) {
		Document doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {			
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());		

				if (user == null) throw new AppException("No user data found.");
				
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

			}
			
			List<LiveClass> mainList;
			//after login 
			if (userPrincipal != null) 
			{
				 mainList = liveClassRepository.get10ExtraCurricularClasses(lanLists,4L,6L);
			}
			else {
				//before login
				mainList = liveClassRepository.get10ExtraCurricularClassesWithoutLang(4L,6L,4L);
			}

			if (mainList.isEmpty())
				throw new NullPointerException("No Video found!");


			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard!=null) {
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				}
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}


	//After LogedIn
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAcademicClasses(Long idLiveClassCategory) {
		Document doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
		// List<LiveClass> mainList = liveClassRepository
		// .findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(
		// LocalDate.now(), idClassStandard,idLiveClassCategory, Boolean.FALSE,Boolean.TRUE);
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());		

				if (user == null) throw new AppException("No user data found.");
				
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

			}

		List<LiveClass> mainList = liveClassRepository.get10ExtraCurricularClasses(lanLists,4L,6L);

		if (mainList.isEmpty())
		throw new NullPointerException("No Video found!");


		for (LiveClass liveClass : mainList) {

		LiveClassDto liveClassDto = new LiveClassDto();
		liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
		liveClassDto.setIdTeacher(liveClass.getIdTeacher());
		liveClassDto.setClassDate(liveClass.getClassDate());
		liveClassDto.setFromTime(liveClass.getFromTime());
		liveClassDto.setToTime(liveClass.getToTime());
		liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
		liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
		liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
		liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
		liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
		liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
		liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
		liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
		liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
		liveClassDto.setPdfURL(liveClass.getPdfURL());

		LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
		if (category != null)
		liveClassDto.setClassCategory(category.getLiveClassCategoryName());

		Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
		if (teacher == null) 
			throw new NullPointerException("Invalid  idTeacher found in live class data");
		liveClassDto.setTeacherName(teacher.getTeacherName());
		liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
		liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
		
		ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
		if(classStandard!=null) {
		liveClassDto.setClassStandard(classStandard.getClassStandadName());
		liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
		}

		Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
		if(subject==null) 
			throw new NullPointerException("Invalid  idSubject found in live class data");
		liveClassDto.setIdSubject(subject.getIdSubject());
		liveClassDto.setSubjectName(subject.getSubjectName());
		
		liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");

		result.add(liveClassDto);
		}
		doc.setData(result);
		doc.setStatusCode(HttpStatus.OK.value());
		doc.setMessage("Request Sucessfull");
		return doc;
		} catch (Exception exp) {

		doc.setData(null);
		doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		doc.setMessage(exp.getLocalizedMessage());
		return doc;
		}
	}

	//After LogedIn
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<Page<LiveClassDto>> getAllBroadCastVideo(List<Long> IdClassStandards, Long idLanguage, Long idSubject,int pageNumber,Long idSyllabus,Long idState) {
		
		Document<Page<LiveClassDto>> doc = new Document<>();
		
		List<LiveClassDto> result =new ArrayList();
		Page<LiveClassDto> page = null;
		
		try {
			Long newIdClassStandard = IdClassStandards.get(0) == -1 ? null : IdClassStandards.get(0);
			Long newIdSyllabus = idSyllabus == -1 ? null :idSyllabus;
			Long newIdState = idState == -1 ? null : idState;
;			Long newIdSubejct = idSubject == -1 ? null :  idSubject;
			
			if (IdClassStandards.isEmpty())
				throw new NullPointerException("Class Ids can't be blank ");
			
			Pageable paging = PageRequest.of(pageNumber, 12);
			
			Page<LiveClass> classList = null;
			
			classList	 = liveClassRepository.
			getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagWithPaging
			(1L,idLanguage,false,true,newIdClassStandard,newIdSubejct,newIdSyllabus,newIdState,true,paging);
			
			
//			if(idSubject==-1L && idSyllabus == -1 && IdClassStandards.contains(-1L)) {
//			classList = liveClassRepository
//					.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,paging);
//			}
//			else if(!IdClassStandards.contains(-1L) && idSubject==-1L && idSyllabus ==-1L) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,IdClassStandards,paging);
//			}
//			else if(IdClassStandards.contains(-1L) && idSubject !=-1L && idSyllabus ==-1) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idSubject,paging);
//			}
//			
//			else if(!IdClassStandards.contains(-1L) && idSubject !=-1L && idSyllabus ==-1) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdClassStandardInOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idSubject,IdClassStandards,paging);
//			}
//			
//			
//			
//			else if(IdClassStandards.contains(-1L) && idSubject ==-1 && idSyllabus != -1) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idSyllabus,paging);
//			}
//			else if(!IdClassStandards.contains(-1L) && idSubject ==-1 && idSyllabus != -1) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdClassStandardInAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,IdClassStandards,idSyllabus,paging);
//			}
//			else if(IdClassStandards.contains(-1L) && idSubject !=-1 && idSyllabus != -1) {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idSubject,idSyllabus,paging);
//			}
//				
//			else {
//				classList = liveClassRepository
//						.findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardInAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE,idLanguage,idSubject,Boolean.TRUE, IdClassStandards,idSyllabus,paging);
//				
//			}
			
			if (classList.getContent().isEmpty() && pageNumber > 0) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.OK.value());
				doc.setMessage("No More Videos Available.");

			}
			else if (classList.getContent().isEmpty()) {

				throw new NullPointerException("No Videos Available.");
			} else {
			
			for (LiveClass liveClass : classList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());
				

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				if (category != null)

					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
                  
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				result.add(liveClassDto);
				}
			
			if (result.isEmpty())
				throw new AppException("No Videos available");
			
			
			if ((paging.getPageSize() * paging.getPageNumber() >= classList.getTotalElements())) {
				doc.setData(null);
				doc.setMessage("No More Videos Available");
				doc.setStatusCode(HttpStatus.OK.value());
			}
			else {

//				int start = (int) paging.getOffset();
//				int end = (start + paging.getPageSize()) > result.size() ? result.size()
//						: (start + paging.getPageSize());
				page = new PageImpl<>(result, paging, classList.getTotalElements());

				doc.setData(page);
				doc.setMessage("Request Successfull");
				doc.setStatusCode(200);
				
			}

			}
			} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
		}
		return doc;
	}
    
	
	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */
	//Before LogedIn
	@Override
	public Document<Page<LiveClassDto>> getAllBroadCastedVideo(Long idLanguage, Long idSubject,int pageNumber,Long idClassStandard, Long idSyllabus,Long idLiveClassCategory,Long idState) {
		Document<Page<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		Page<LiveClassDto> page = null;
		try {
             
			Long newidClassStandard =  idClassStandard == -1 ? null : idClassStandard;
			Long newidSubject = idSubject == -1 ? null : idSubject;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			//datas are the being hardcoded for the specific purpose of this api in order to fetch only ongoing live class 
			//only active live class data needed to be fetched 
			Boolean activeFlag = true;
			//live class which are running currently 
			Boolean currentRunningFlag = false;
			//live class completion flag needed to false 
			Boolean completionFlag = true;
			
			
			Page<LiveClass> classList = null;
			Pageable paging = PageRequest.of(pageNumber, 12);
			
			classList = liveClassRepository.getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagWithPaging
			(idLiveClassCategory,idLanguage,currentRunningFlag,completionFlag,newidClassStandard,newidSubject,newidSyllabus,newidState,activeFlag,paging);
			
//			if(idClassStandard==-1 && idSubject==-1L && idSyllabus == -1) {
//			classList = liveClassRepository.findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageOrderByClassDateDescIdClassStandardAsc(Boolean.TRUE, Boolean.TRUE,idLanguage,paging);
//
//			}
//			else if(idClassStandard!= -1 && idSubject ==-1 && idSyllabus == -1) {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardOrderByClassDateDesc(Boolean.TRUE, Boolean.TRUE,idLanguage,idClassStandard,paging);
//
//			}
//			else if(idClassStandard == -1 && idSubject !=-1 && idSyllabus == -1) {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByClassDateDescIdClassStandardAsc(Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE,paging);
//			}
//			else if(idClassStandard ==-1 && idSubject ==-1 && idSyllabus != -1) {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdSyllabusOrderByClassDateDescIdClassStandardAsc(Boolean.TRUE, Boolean.TRUE,idLanguage,idSyllabus,paging);
//			}
//			else if(idClassStandard !=-1 && idSubject ==-1 && idSyllabus != -1) {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE, Boolean.TRUE,idLanguage,idClassStandard,idSyllabus,paging);
//			}
//			else if(idClassStandard ==-1 && idSubject !=-1 && idSyllabus != -1) {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusOrderByClassDateDescIdClassStandardAsc(Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE,idSyllabus,paging);
//			}
//			else {
//				classList = liveClassRepository.findAllByLiveCompletionFlagAndActiveFlagAndIdLanguageAndIdClassStandardAndIdSubjectAndIdSyllabusOrderByClassDateDesc(Boolean.TRUE, Boolean.TRUE,idLanguage,idClassStandard,idSubject,idSyllabus, paging);
//
//				
//
//			}
			if (classList.isEmpty() && pageNumber > 0) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.OK.value());
				doc.setMessage("No More Videos Available.");

			}
			else if (classList.getContent().isEmpty()) {

				throw new NullPointerException("No Videos Available.");
			}else {

			for (LiveClass liveClass : classList.getContent()) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				if (category != null)

					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				

				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
			}
			if (result.isEmpty())
				throw new AppException("No Videos available");
			
			if ((paging.getPageSize() * paging.getPageNumber() >= classList.getTotalElements())) {
				doc.setData(null);
				doc.setMessage("No More Videos Available");
				doc.setStatusCode(HttpStatus.OK.value());
			}
			else {
// since paging result is given by jpa we are directly assigning the results to page instant.
//				int start = (int) paging.getOffset();
//				int end = (start + paging.getPageSize()) > result.size() ? result.size()
//						: (start + paging.getPageSize());
				
				page = new PageImpl<>(result, paging, classList.getTotalElements());

				doc.setData(page);
				doc.setMessage("Request Successfull");
				doc.setStatusCode(200);
				
			}

		
			}
			} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
		}
		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllLiveClass() {
		Document doc = new Document<>();
		List<LiveClassDto> classList = new ArrayList<LiveClassDto>();
		try {
//			List<LiveClass> liveClassList = liveClassRepository.
//					findTOP10ByClassDateGreaterThanEqualAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscCurrentRunningFlagDesc(LocalDate.now(), Boolean.FALSE,Boolean.TRUE);

			List<LiveClass> liveClassList = liveClassRepository.get10Classes();
			
			
			if (liveClassList.isEmpty())
				throw new NullPointerException("No Class Found!");

			for (LiveClass liveClass : liveClassList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No Syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());
				
				LiveClassCategory category = liveClassCategoryRepository
						.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				classList.add(liveClassDto);
			}
			doc.setData(classList);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<LiveClassDto>> getAllLiveClassByIdClassStandard(List<Long> IdClassStandards, Long idSyllabus,Long idState) {
		Document<List<LiveClassDto>> doc = new Document<List<LiveClassDto>>();
		List<LiveClassDto> classList = new ArrayList<LiveClassDto>();
		try {
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			if (IdClassStandards.isEmpty())
				throw new NullPointerException("Class Ids can't be blank ");
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());		

				if (user == null) throw new AppException("No user data found.");
				
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

			}

			List<LiveClass> liveClassList = liveClassRepository.get10ClassesByIdClassStandard(IdClassStandards,lanLists,idSyllabus,idState);

			if (liveClassList.isEmpty())
				throw new NullPointerException("No Class Found!");

			for (LiveClass liveClass : liveClassList) {
				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());
				
				LiveClassCategory category = liveClassCategoryRepository
						.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				classList.add(liveClassDto);
			}

			doc.setData(classList);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<?> uploadPdfFileoS3(MultipartFile file) {
		Document<String> doc = new Document<>();
		try {

			final File tempFile = convertMultiPartFileToFile(file);
			String pdfLink = uploadFileToS3Bucket(pdfBucketFolder, tempFile);
			System.err.println("S3 Pdf Url==>" + pdfLink);
			boolean isDeletedLogFile = tempFile.delete();
			logger.info("PDF file deleted from the system : "+isDeletedLogFile );

			doc.setData(pdfLink);
			doc.setMessage("Image Uploaded Successfully");
			doc.setStatusCode(HttpStatus.OK.value());
			return doc;
		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return doc;
		}
	}

	/*@Override
	public Document getBroadCastVideo(Long idClassStandard) {
		Document result = new Document<>();
		try {

			LiveClass liveClass = liveClassRepository
					.findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateLessThanEqualOrderByClassDateDesc(
							idClassStandard, Boolean.TRUE, Boolean.FALSE, LocalDate.now());

			if (liveClass == null)
				throw new NullPointerException("No Live Class Found!!");

			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setIdTeacher(liveClass.getIdTeacher());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
			liveClass.setPdfURL(liveClass.getPdfURL());
			LiveClassCategory category = liveClassCategoryRepository
					.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			if (category != null)
				liveClassDto.setClassCategory(category.getLiveClassCategoryName());

			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher != null)
				liveClassDto.setTeacherName(teacher.getTeacherName());

			result.setData(liveClassDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
	}*/

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getLiveClassForHomePage(Long idLiveClassCategory) {
		Document result = new Document<>();
		try {
			
			List<Long> idLiveClassCategoryList = new ArrayList<Long>();
			//academic will be default category 
			idLiveClassCategoryList.add(1L);
			
			if (idLiveClassCategory != 1) 
			{   //adding extra_curr based on user request
				idLiveClassCategoryList.add(2L);
			}
			
			
			Long classStdId;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				classStdId = user.getClassStandard() == null ? 1L : user.getClassStandard();
			} else {
				classStdId = 1L;
			}
			//fetching current running live class details based on class standard id,completion flag,current running flag and class date as today date
			LiveClass liveClass = liveClassRepository
					.findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(classStdId,
							Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);

			if (liveClass == null) {
				//fetching any running live class details based on completion flag,current running flag and class date as today date order by class standard id
				liveClass = liveClassRepository.findFirstByLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdLiveClassCategoryInOrderByIdClassStandard(
						Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE,idLiveClassCategoryList);

				if (liveClass == null) {
					//fetching broadcast class details based on class standard id,completion flag,current running flag and class date which is less than or equal to today date in descending order of class date
					liveClass = liveClassRepository
							.findFirstByIdClassStandardAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
									classStdId, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE,LocalDate.now());
					if (liveClass == null) {
						//fetching any broadcast class details based on completion flag,current running flag and class date which is less than or equal to today date in descending order of class date and ascending order of class standard id
						liveClass = liveClassRepository
								.findFirstByLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualAndIdLiveClassCategoryInOrderByClassDateDescIdClassStandardAsc(
										Boolean.TRUE, Boolean.FALSE,Boolean.TRUE, LocalDate.now(),idLiveClassCategoryList);
					}
					if (liveClass == null) {
						throw new AppException("No Class Found");
					}
				}

			}

			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setIdTeacher(liveClass.getIdTeacher());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
			liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
			liveClassDto.setPdfURL(liveClass.getPdfURL());
			liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
			
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
			if(syllabus == null) throw new NullPointerException("No syllabus found !");
			
			liveClassDto.setSyllabusName(syllabus.getSyllabusName());
			
			LiveClassCategory category = liveClassCategoryRepository
					.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			if (category != null)
				liveClassDto.setClassCategory(category.getLiveClassCategoryName());

			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher == null) 
				throw new NullPointerException("Invalid  idTeacher found in live class data");
				liveClassDto.setTeacherName(teacher.getTeacherName());
				liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
			
			ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
			if(classStandard==null) 
				throw new NullPointerException("Invalid  idClassStandard found in live class data");
				liveClassDto.setClassStandard(classStandard.getClassStandadName());
				liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
			
			
			Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
			if(subject==null) 
				throw new NullPointerException("Invalid  idSubject found in live class data");
				liveClassDto.setIdSubject(subject.getIdSubject());
				liveClassDto.setSubjectName(subject.getSubjectName());
			
			
			if(liveClass.getIdLiveClassCategory() == 1) {
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			else {
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			

			result.setData(liveClassDto);
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

	@Override
	public Document<?> getMainVideoExtraVideo(Long idLiveClassCategory) {

		Document<LiveClassDto> result = new Document<>();
		try {
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			UserPrincipal userPrincipal = null ;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());		

				if (user == null) throw new AppException("No user data found.");
				
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

			}
			
			LiveClass liveExtra = null;
			
			if(userPrincipal == null) 
			{
				liveExtra = liveClassRepository
						.findFirstByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateOrderByIdLanguageDesc(idLiveClassCategory, Boolean.TRUE,Boolean.TRUE,LocalDate.now());
				
				
				if (liveExtra == null) {
					liveExtra = liveClassRepository
							.findFirstByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDescIdLanguageDesc(
									idLiveClassCategory, Boolean.TRUE, Boolean.FALSE,Boolean.TRUE, LocalDate.now());
				}
			}
			else {
				
				liveExtra = liveClassRepository
						.findFirstByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateAndIdLanguageInOrderByIdLanguageDesc(idLiveClassCategory, Boolean.TRUE,Boolean.TRUE,LocalDate.now(),lanLists);
				
				
				
				if (liveExtra == null) {
					liveExtra = liveClassRepository
							.findFirstByIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndIdLanguageInAndClassDateLessThanEqualOrderByClassDateDesc(
									idLiveClassCategory, Boolean.TRUE, Boolean.FALSE,Boolean.TRUE,lanLists, LocalDate.now());
				}
			}
			
			if (liveExtra == null)
				throw new NullPointerException("No Live Class found.");
			
			
			Teacher teacher = teacherRepository.findByIdTeacher(liveExtra.getIdTeacher());
			if (teacher == null)
				throw new NullPointerException("No teacher found ");

			LiveClassDto live = new LiveClassDto();
			live.setIdLiveClassCategory(liveExtra.getIdLiveClassCategory());
			live.setClassDate(liveExtra.getClassDate());
			live.setFromTime(liveExtra.getFromTime());
			live.setIdTeacher(liveExtra.getIdTeacher());
			live.setToTime(liveExtra.getToTime());
			live.setLiveClassHeading(liveExtra.getLiveClassHeading());
			live.setLiveClassDescription(liveExtra.getLiveClassDesc());
			live.setIdYoutubeMaster(liveExtra.getIdYoutubeMaster());
			live.setLiveClassURL(liveExtra.getLiveClassURL());
			live.setThumbnailURL(liveExtra.getThumbnailURL());
			live.setCurrentRunningFlag(liveExtra.isCurrentRunningFlag());
			live.setLiveCompletionFlag(liveExtra.isLiveCompletionFlag());
			live.setTeacherName(teacher.getTeacherName());
			live.setIdLiveClass(liveExtra.getIdLiveClass());
			live.setLiveClassHeading(liveExtra.getLiveClassHeading());
			live.setPdfURL(liveExtra.getPdfURL());
			
			live.setTextBelowLiveClass(live.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			
			
			result.setData(live);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getMainAcademicVideo(Long idLiveClassCategory) {
		Document result = new Document<>();
		try {
			Long classStdId;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user=userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null)
					throw new NullPointerException("No user found ");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
			}
			else {
				classStdId=1L;
			}
			System.out.println("classid "+classStdId);
			LiveClass liveClass = liveClassRepository
					.findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlag(
							classStdId,idLiveClassCategory,Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);
			System.out.println("liveClass with classid "+liveClass);
			if (liveClass == null) {
				liveClass = liveClassRepository.findFirstByLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagOrderByIdClassStandard(
						Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE);
				System.out.println("liveClass without classid "+liveClass);
				if (liveClass == null) {
					liveClass = liveClassRepository
							.findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDesc(
									classStdId,idLiveClassCategory,Boolean.TRUE, Boolean.FALSE, LocalDate.now(),Boolean.TRUE);
					System.out.println("broadcasted class "+liveClass);
					if (liveClass == null) {
						liveClass = liveClassRepository
								.findFirstByLiveCompletionFlagAndCurrentRunningFlagAndActiveFlagAndClassDateLessThanEqualOrderByClassDateDescIdClassStandardAsc(
										Boolean.TRUE, Boolean.FALSE,Boolean.TRUE, LocalDate.now());
					}
					if (liveClass == null) {
						throw new AppException("No Class Found");
					}
				}

			}

			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setIdTeacher(liveClass.getIdTeacher());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
			liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
			liveClassDto.setPdfURL(liveClass.getPdfURL());
			LiveClassCategory category=liveClassCategoryRepository.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			if(category !=null)
				liveClassDto.setClassCategory(category.getLiveClassCategoryName());

			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher == null) 
				throw new NullPointerException("Invalid  idTeacher found in live class data");
				liveClassDto.setTeacherName(teacher.getTeacherName());
				liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
			
			ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
			if(classStandard==null) 
				throw new NullPointerException("Invalid  idClassStandard found in live class data");
				liveClassDto.setClassStandard(classStandard.getClassStandadName());
				liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
			
			
			Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
			if(subject==null) 
				throw new NullPointerException("Invalid  idSubject found in live class data");
				liveClassDto.setIdSubject(subject.getIdSubject());
				liveClassDto.setSubjectName(subject.getSubjectName());
						liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			

			result.setData(liveClassDto);
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

	@Override
	public Document<?> notifyLiveClass(Long idVlUser, Long idLiveClass) {

		Document<NotifyLiveClass> doc = new Document<>();

		try {

			if (idVlUser == null || idVlUser.equals(0L)) {
				throw new NullPointerException("User Id Cannot be Null");
			}

			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live Class Id Cannot be Null");
			}

			User user = userRepository.findByUserSurId(idVlUser);

			if (user == null) {
				throw new NullPointerException("User Not Found with the ID Provided");
			}

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (liveClass == null) {
				throw new NullPointerException("Live Class Not Found with the Id Provided");
			}

			NotifyLiveClass notifyLiveClass = null;
			notifyLiveClass = notifyLiveClassRepo.findByIdLiveClassAndIdVlUser(idLiveClass,idVlUser);

			if (notifyLiveClass == null ) 
			{   
				notifyLiveClass = new NotifyLiveClass();
				notifyLiveClass.setIdLiveClass(idLiveClass);
				notifyLiveClass.setIdVlUser(idVlUser);
				notifyLiveClass.setNotifiedFlag(false);
				NotifyLiveClass saved = notifyLiveClassRepo.save(notifyLiveClass);

				doc.setData(saved);
				doc.setMessage("Request Successful.");
				doc.setStatusCode(200);
			}
			else {


				doc.setData(null);
				doc.setMessage("User already request , reminder for the selected class");
				doc.setStatusCode(HttpStatus.CONFLICT.value());
			}




		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document<?> getOngoingLiveClasses(Long idLiveClass) {
		Document document = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();

		LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
		if(liveClass==null) {
			throw new AppException("Live class details not foūnd");
		}
		List<LiveClass> liveClasses = liveClassRepository.findByCurrentRunningFlagAndLiveCompletionFlagAndActiveFlag(true, false,Boolean.TRUE);
		document.setData(liveClasses);
		if(liveClasses.isEmpty()) {
			throw new AppException("Live class details not foūnd");
		}

		for(LiveClass liveClassDetail : liveClasses) {
			if(liveClassDetail.getIdLiveClass()!=idLiveClass) {
				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClassDetail.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClassDetail.getIdTeacher());
				liveClassDto.setClassDate(liveClassDetail.getClassDate());
				liveClassDto.setFromTime(liveClassDetail.getFromTime());
				liveClassDto.setToTime(liveClassDetail.getToTime());
				liveClassDto.setLiveClassHeading(liveClassDetail.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClassDetail.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClassDetail.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClassDetail.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClassDetail.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClassDetail.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClassDetail.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(liveClassDetail.getIdLiveClassCategory());
				liveClassDto.setPdfURL(liveClassDetail.getPdfURL());
				LiveClassCategory category=liveClassCategoryRepository.findByIdLiveClassCategory(liveClassDetail.getIdLiveClassCategory());
				if(category !=null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClassDetail.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
				

				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
		}

		document.setData(result);
		document.setMessage("On-going classes fetched successfully");
		document.setStatusCode(200);
		return document;
	}

	private void sendNotificationEmailAtScheduledDateTime() {
		// TODO Auto-generated method stub

	}
	@Override
	public Document<?> editLiveClassByAdmin(LiveClass liveClass, Long idLiveClass) {
		Document<LiveClass> doc = new Document<>();

		try {

			if (liveClass == null) {
				throw new NullPointerException("LiveClass Details Cannot be Null");
			}

			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live Class Id Not Present");
			}

			LiveClass existingLiveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (existingLiveClass == null) {
				throw new NullPointerException("Live class details not foūnd");
			}

			if(liveClass.getClassDate()==null){
				LocalDate classDate = LocalDate.of(9999, 9, 9);
				existingLiveClass.setClassDate(classDate);
			}
			else
				existingLiveClass.setClassDate(liveClass.getClassDate());
			
			
			existingLiveClass.setActiveFlag(true);
//			LocalTime averageTime = TimeComparison.averageTime(existingLiveClass.getFromTime(), existingLiveClass.getToTime());

			if(liveClass.getIdTeacher()==null) {
				existingLiveClass.setIdTeacher(0L);
				existingLiveClass.setIdYoutubeMaster(0L);
			}
			else {
				existingLiveClass.setIdTeacher(liveClass.getIdTeacher());
				existingLiveClass.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			}

			existingLiveClass.setLiveClassHeading(liveClass.getLiveClassHeading());
			existingLiveClass.setLiveClassDesc(liveClass.getLiveClassDesc());
			existingLiveClass.setLiveClassURL(liveClass.getLiveClassURL());
			existingLiveClass.setThumbnailURL(liveClass.getThumbnailURL());
			existingLiveClass.setPdfURL(liveClass.getPdfURL());
			existingLiveClass.setIntroVideoURL(liveClass.getIntroVideoURL());
			existingLiveClass.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			existingLiveClass.setIdClassStandard(liveClass.getIdClassStandard());
			existingLiveClass.setIdSubject(liveClass.getIdSubject());
			existingLiveClass.setIdLanguage(liveClass.getIdLanguage());
			existingLiveClass.setIdSyllabus(liveClass.getIdSyllabus());
			existingLiveClass.setIdState(liveClass.getIdState());
			existingLiveClass.setFromTime(liveClass.getFromTime());
			existingLiveClass.setToTime(liveClass.getToTime());
			
			if(!existingLiveClass.getClassType().equals(liveClass.getClassType())) 
			{
				String classType = liveClass.getClassType() == null ? "premium" : liveClass.getClassType();
				existingLiveClass.setClassType(classType);
			}

			List<LiveClass> updateliveClassList = liveClassRepository.findByIdTeacherAndClassDateAndActiveFlagAndIdLiveClassNot(liveClass.getIdTeacher(),liveClass.getClassDate(),
					Boolean.TRUE, existingLiveClass.getIdLiveClass());
//					liveClassRepository.findByIdLiveClassAndActiveFlag(existingLiveClass.getIdLiveClass(), Boolean.TRUE);
			
			if(updateliveClassList.isEmpty()) {
				LiveClass updated = liveClassRepository.save(existingLiveClass);
				doc.setData(updated);
				doc.setMessage("Live Class Details Updated Successfully");
				doc.setStatusCode(201);

			}
			
			else {
//				LocalTime fromTime = existingLiveClass.getFromTime();
//                LocalTime toTime = existingLiveClass.getToTime();

                boolean isInBetween = false;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                for (LiveClass liveClass2 : updateliveClassList) {

//                    isInBetween = TimeComparison.checkTimeInBetween(liveClass2.getFromTime().format(formatter),
//                    		liveClass2.getToTime().format(formatter), liveClass.getFromTime().format(formatter),
//                    		liveClass.getToTime().format(formatter));
                	
                	isInBetween = TimeComparison.checkTimeInBetween(liveClass.getFromTime().format(formatter),
							liveClass.getToTime().format(formatter), liveClass2.getFromTime().format(formatter),
							liveClass2.getToTime().format(formatter));
                	
                	
                
                    System.out.println("existing from time "+ liveClass2.getFromTime());
                    System.out.println("live from time "+ liveClass.getFromTime());
                    System.out.println("existing to time "+ liveClass2.getToTime());
                    System.out.println("live to time "+ liveClass.getToTime());
                    
                    
                    if (isInBetween) break;
                }

                if (isInBetween) {
                    doc.setData(null);
                    doc.setMessage(
                            "You have already scheduled Live Class for this teacher between this timing for this date");
                    doc.setStatusCode(HttpStatus.CONFLICT.value());
                } else {
                	
                	existingLiveClass.setFromTime(liveClass.getFromTime());
        			existingLiveClass.setToTime(liveClass.getToTime());
                    LiveClass updated = liveClassRepository.save(existingLiveClass);

                    doc.setData(updated);
                    doc.setMessage("Live Class Details Updated Successfully");
                    doc.setStatusCode(HttpStatus.CREATED.value());
                }
			}

			
		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> sendLiveClassNotificationToAll(Long idLiveClass,Long idClassStandard) {

		Document<List<NotifyLiveClass>> doc = new Document<>();
		List<NotifyLiveClass> classList = new ArrayList<NotifyLiveClass>();
		List<String> emailList = new ArrayList<String>();
		try {

			if (idClassStandard == null || idClassStandard.equals(0L)) {
				throw new NullPointerException("Class Standard Cannot be Null");
			}

			if (idLiveClass == null || idLiveClass.equals(0L)) {
				throw new NullPointerException("Live Class Id Cannot be Null");
			}

			List<User> userList = userRepository.findByClassStandard(idClassStandard);

			if (userList.isEmpty()) {
				throw new NullPointerException("User Not Found with the Provided Class Standard");
			}

			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);

			if (liveClass == null) {
				throw new NullPointerException("Live Class Not Found with the Id Provided");
			}

//			LocalDate classDate = liveClass.getClassDate();
//			LocalTime fromTime = liveClass.getFromTime();

			for(User user:userList) {
				if(user.getRegisteredAs().equalsIgnoreCase("Parent") || user.getRegisteredAs().equalsIgnoreCase("Student")) {
					emailList.add(user.getEmail());
					NotifyLiveClass notifyLiveClass = new NotifyLiveClass();
					notifyLiveClass.setIdLiveClass(idLiveClass);
					notifyLiveClass.setIdVlUser(user.getUserSurId());
					notifyLiveClass.setNotifiedFlag(false);
					classList.add(notifyLiveClass);
				}

			}

			List<NotifyLiveClass> result=notifyLiveClassRepo.saveAll(classList);
			// Create a Scheduler which will send the notification email,sms,push
			// notofication at the notified date and time
			sendNotificationEmailAtScheduledDateTime();

			doc.setData(result);
			doc.setMessage("Request Successful.");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	/*@Override
	public Document getGraphData() {
		Document doc = new Document<>();
		try {
			 List<GraphDTO> graphList=userLiveClassAttendedRepository.getGraphData();
			 System.out.println("sizeee "+graphList.size());
			doc.setData(graphList);
			doc.setMessage("Request Successful.");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}*/

	@Override
	public Document<List<ResultDTO>> getGraphData(Long idClassStandard,Long userSurId) {
		Document<List<ResultDTO>> doc = new Document<>();
		List<ResultDTO> resultList =new ArrayList<ResultDTO>();

		try {
			
			List<Object[]> graphList =  liveClassRepository.getStudentLiveClassAttendedCountinfo(idClassStandard, userSurId);
			
			if(graphList.isEmpty()) {
				throw new NullPointerException("No Graph Data Found!");
			}
			
		
		
			List<Object[]> sortedList =  graphList.stream().sorted(( o1,  o2) -> ((Date) o1[0]).compareTo((Date) o2[0])).collect(Collectors.toList());

			for(Object graph:sortedList) {
				Object[] res = (Object[])graph;
				if(res == null) {
					throw new NullPointerException("No Graph Data Found!");
				}
				
				ResultDTO result =  new ResultDTO();
				result.setClassConductedDate(res[0].toString());
				result.setTotalLiveClasses(res[1].toString());
				result.setStudentAttendedCount(res[2].toString());
				resultList.add(result);

			}
			doc.setData(resultList);
			doc.setMessage("Request Successful.");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@Override
	public void sendLiveClassReminderNotificationEmail() {
		
		List <String> result = new ArrayList<>();
		
         Instant startTime, endTime;
		
		startTime = Instant.now();

		System.out.println("Starting  Live Class Early Reminder Notification scheduler");
		result.add("Starting  Live Class Early Reminder Notification scheduler.");
		result.add("Started time: " +startTime);

		try {

			List<LiveClass> liveClasses = liveClassRepository.findByClassDateAndLiveCompletionFlagAndActiveFlagAndFromTimeNotNull(LocalDate.now(),Boolean.FALSE,Boolean.TRUE);

			if (liveClasses.isEmpty()) throw new AppException("No Live Class data Available for a particular date.");

			System.out.println("Total Active LiveClass Records found for the day "+liveClasses.size());
			result.add("Total Active LiveClass Records found for the day ("+LocalDate.now()+") Count:"+liveClasses.size());
			List<LiveClass> finalList = liveClasses.stream().filter(l -> ((l.getFromTime().toSecondOfDay()-LocalTime.now().toSecondOfDay())<=(3600L) && (l.getFromTime().toSecondOfDay()-LocalTime.now().toSecondOfDay()) >=(0))).collect(Collectors.toList());
			System.out.println("Total final Lives class records based on time Count:"+finalList.size());
			result.add("Total No.of Lives class records found for next 1 hour Count:"+finalList.size());
			if (!finalList.isEmpty()) 
			{   

				List<Long> idList = finalList.stream().map(LiveClass::getIdLiveClass).collect(Collectors.toList());

				List <NotifyLiveClass> notifyList = notifyLiveClassRepo.
						findByIdLiveClassInAndNotifiedFlag(idList,Boolean.FALSE);

				if (!notifyList.isEmpty()) 
				{      
					System.out.println("Total Records found for sending notification: "+ notifyList.size());
					result.add("Total Records found for sending notification: "+ notifyList.size());
					int count = 0;

					for(NotifyLiveClass n : notifyList ) {

						n.setNotifiedFlag(true);
						User user = userRepository.findByUserSurId(n.getIdVlUser());

						if (user == null) {
							System.err.println("invalid User with id: "+n.getIdVlUser());
							result.add("invalid User with id: "+n.getIdVlUser());
							continue; 
						}

						LiveClass lc = null;

						for(LiveClass l : finalList) {
							if (l.getIdLiveClass() == n.getIdLiveClass()) 
								lc = l;
						}
						String heading = (lc==null)? "Live Class" : lc.getLiveClassHeading();
						String fromTime = (lc==null)? LocalTime.now().plusMinutes(30).toString() : lc.getFromTime().toString();
						fromTime = LocalTime.parse(fromTime).format(DateTimeFormatter.ofPattern("HH:mm a"));
						emailService.sendLiveClassReminderNotification
						(user.getFirstName(), heading, fromTime, user.getEmail());
						count++;
					}


					notifyLiveClassRepo.saveAll(notifyList);

					System.out.println("Total Number of Emails sent: "+ count);
                     result.add("Total Number of Emails sent: "+ count);
					System.err.println("Total Number of Email failed: "+(notifyList.size()-count));
					result.add("Total Number of Email failed: "+(notifyList.size()-count));

				}

			}

			System.out.println("Notification scheduler finished without any errors.");
		}

		catch(Exception e) 
		{ 
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getStackTrace());
			result.add("Error Occurred : ");
			result.add(e.getLocalizedMessage());
		}
		endTime = Instant.now();
		result.add("End Time:"+endTime);
		result.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		result.add("Live Class Hourly Reminder Notification scheduler Completed.");
		
		String fileName = "LiveClassReminderNotificationCheckLog_"+Instant.now()+".txt";
		//log the file to s3 location
				try(FileWriter writer = new FileWriter(fileName)) {
					
			   
				for(String str: result) {
					  writer.write(str + System.lineSeparator());
					}
					writer.close();
				
					File file = new File(fileName);
					
				    fileUploadService.uploadFileToS3Bucket("/logs/scheduler/liveclass-hourly-remainder", fileName, file);
					
				    boolean isDeletedLogFile = file.delete();
					logger.info("Live class notification reminder log file deleted from the system : "+isDeletedLogFile );
					
				} catch (IOException e) {
					
					logger.error(e.getLocalizedMessage());
				}
				

	}

	@Override
	public Document<List<LiveClassDto>> getAllAcademicLive(Long idLiveClassCategory, Long idLanguage, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try { 
			Long classStdId;
			Long syllabusId = null;
			Long idState ;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
				
				if(user.getRegisteredAs().equalsIgnoreCase("Student")) {
					Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
					
					if(student == null) throw new NullPointerException("No Student found !");
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found ");
					
					syllabusId = syllabus.getIdSyllabus();
					
					State state = stateRepository.findByIdState(student.getIdState());
					if(state == null) throw new NullPointerException("Invalid  state  found in student object ");
					
					idState = state.getIdState();
				}
				else {
					classStdId = 1L;
					syllabusId =1L;
					idState = 1L;
				}
				
			} else {  
				classStdId = 1L;
				syllabusId =1L;
				idState = 1L;
			}
			
			if (idLiveClassCategory != 1) 
			{
				classStdId = 4L;
				syllabusId =4L;
				idState = 6L;
			}
			
			List<LiveClass> mainList = new ArrayList<>();

			if(idSubject==-1L) {
			 mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndIdSyllabusAndIdState
					(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.TRUE, idLanguage,Boolean.TRUE,syllabusId,idState);
			}
			else {
				mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusAndIdState
						(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.TRUE, idLanguage, idSubject,Boolean.TRUE,syllabusId,idState);
			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("Currently No Running Live Class Found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				

				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null)
					throw new NullPointerException("Invalid  idIdclassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
								Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				if(idLiveClassCategory == 1L) {
					liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				else {
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				}
				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

	}

	@Override
	public Document<List<LiveClassDto>> getAllAcademicUpcoming(Long idLiveClassCategory, Long idLanguage, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long classStdId;
			Long syllabusId = null;
		    Long idState ;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
				
				if(user.getRegisteredAs().equalsIgnoreCase("Student")) {
					Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
					
					if(student == null) throw new NullPointerException("No Student found !");
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found ");
					
					syllabusId = syllabus.getIdSyllabus();
					
					State state = stateRepository.findByIdState(student.getIdState());
					
					if(state == null) throw new NullPointerException("Invalid State  found in user object.");
					
					
					idState = state.getIdState();
					
					
				}
				else {
					classStdId = 1L;
					syllabusId =1L;
					idState =1L;
				}
			} else {
				classStdId = 1L;
				syllabusId =1L;
				idState =1L;
			}
			
			if (idLiveClassCategory != 1 ) 
			{
				classStdId = 4L;
				syllabusId =4L;
				idState =6L;
			}
			
			List<LiveClass> mainList = new ArrayList<>();
			if(idSubject==-1L) {
			 mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.FALSE, Boolean.FALSE, idLanguage,Boolean.TRUE,syllabusId,idState);
			}
			else {
				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.FALSE, Boolean.FALSE, idLanguage, idSubject,Boolean.TRUE, syllabusId,idState);
			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Upcoming Classes found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				if(idLiveClassCategory == 1L) {
					liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+" | "+ teacher.getTeacherName()+" ");
				}
				else {
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

	}

	public Document<?> getAllAcademicClasses(Long idLiveClassCategory) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			List<LiveClass> mainList = liveClassRepository
					.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndLiveCompletionFlagAndActiveFlagOrderByClassDateAscIdClassStandardAscCurrentRunningFlagDesc
					(LocalDate.now(), idLiveClassCategory, Boolean.FALSE,Boolean.TRUE);

			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());					
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	//before LogedIn Academic Live
	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */

	public Document<List<LiveClassDto>> beforeLoginAcademicLiveClasses(Long idLiveClassCategory, Long idLanguage,Long idClassStandard, Long idSubject,Long idSyllabus,Long idState) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			
			Long newidClassStandard =  idClassStandard == -1 ? null : idClassStandard;
			Long newidSubject = idSubject == -1 ? null : idSubject;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			//datas are the being hardcoded for the specific purpose of this api in order to fetch only ongoing live class 
			//only active live class data needed to be fetched 
			Boolean activeFlag = true;
			//live class which are running currently 
			Boolean currentRunningFlag = true;
			//live class completion flag needed to false 
			Boolean completionFlag = false;
			
			LocalDate todayDate = LocalDate.now();
			
			List<LiveClass> mainList = new ArrayList<>();
			
			mainList = liveClassRepository.
					getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateOrderbyClassStandardAsc
					(idLiveClassCategory,idLanguage,currentRunningFlag,completionFlag,
							newidClassStandard,newidSubject,newidSyllabus,newidState,activeFlag,todayDate);
			
//			below commented codes will be removed after success full testing of these branch 
//			if(idClassStandard == -1L && idSubject==-1L && idSyllabus ==-1) {
//			 mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateOrderByIdClassStandardAsc(idLiveClassCategory, Boolean.TRUE, idLanguage,Boolean.TRUE,LocalDate.now());
//			System.out.println("class and subject are -1");
//			}
//			else if(idClassStandard !=-1L && idSubject==-1L && idSyllabus ==-1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndClassDate(idLiveClassCategory, Boolean.TRUE, idLanguage,idClassStandard,Boolean.TRUE,LocalDate.now());
//			System.out.println("class not = -1 and subject =-1");
//			}
//			else if(idClassStandard ==-1L && idSubject !=-1L && idSyllabus ==-1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateOrderByIdClassStandardAsc
//						(idLiveClassCategory, Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE,LocalDate.now());			
//			System.out.println("class =-1 and subject not =-1");
//			}
//			
//			
//			else if(idClassStandard !=-1L && idSubject !=-1L && idSyllabus ==-1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateAndIdClassStandard
//						(idLiveClassCategory, Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE,LocalDate.now(),idClassStandard);			
//			System.out.println("class !=-1 and subject not =-1");
//			}
//			
//			
//			
//			else if(idClassStandard ==-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(idLiveClassCategory, Boolean.TRUE, idLanguage,Boolean.TRUE,LocalDate.now(),idSyllabus);
//				System.out.println("class and subject are -1 syllabus not = -1");
//			}
//			else if(idClassStandard !=-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndClassDateAndIdSyllabus(idLiveClassCategory, Boolean.TRUE, idLanguage,idClassStandard,Boolean.TRUE,LocalDate.now(),idSyllabus);
//				System.out.println("class & syllabus not = -1 and subject =-1");
//			}
//			else if(idClassStandard ==-1 && idSubject !=-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc
//						(idLiveClassCategory, Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE,LocalDate.now(),idSyllabus);			
//			System.out.println("class =-1 and subject & syllabus not =-1");
//			}
//			else {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDateAndIdSyllabus(idLiveClassCategory, Boolean.TRUE, idLanguage,idSubject,Boolean.TRUE, idClassStandard,LocalDate.now(), idSyllabus);
//				System.out.println("class,subject and syllabus not =-1");
//			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());					
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No Syllabus found !");
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				if(idLiveClassCategory ==1L) {
					liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				else {
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				
				result.add(liveClassDto);
			}
			
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} 
		
		catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}
	
   
	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */
	//Before Login Aacademic Upcoming 
	public Document<List<LiveClassDto>> beforeLoginAcademicUpcomingClasses(Long idLiveClassCategory, Long idLanguage,Long idClassStandard,Long idSubject, Long idSyllabus,Long idState) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long newidClassStandard =  idClassStandard == -1 ? null : idClassStandard;
			Long newidSubject = idSubject == -1 ? null : idSubject;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			//datas are the being hardcoded for the specific purpose of this api in order to fetch only upcoming live class 
			//only active live class data needed to be fetched 
			Boolean activeFlag = true;
			//live class which are  upcoming
			Boolean currentRunningFlag = false;
			//live class completion flag needed to false 
			Boolean completionFlag = false;
			
			LocalDate todayDate = LocalDate.now();
			
			
			List<LiveClass> mainList = new ArrayList<>();
			
			
			mainList =liveClassRepository.getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateGreaterThanorEqualOrderbyClassDateAsc
			(idLiveClassCategory,idLanguage,currentRunningFlag,completionFlag,newidClassStandard,newidSubject,newidSyllabus,newidState,activeFlag,todayDate);
//			if(idClassStandard== -1L && idSubject==-1L && idSyllabus ==-1) {
//			
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByIdClassStandardAsc
//						(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,Boolean.TRUE);
//			}else if(idClassStandard !=-1L && idSubject==-1L && idSyllabus ==-1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndActiveFlag(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idClassStandard,Boolean.TRUE);
//			}
//			else if(idClassStandard==-1L && idSubject !=-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idSubject,Boolean.TRUE);
//			}
//			else if(idClassStandard!=-1L && idSubject !=-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdClassStandard(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idSubject,Boolean.TRUE, idClassStandard);
//			}
//			
//			else if(idClassStandard ==-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,Boolean.TRUE,idSyllabus);
//			}
//			else if(idClassStandard !=-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndActiveFlagAndIdSyllabus(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idClassStandard,Boolean.TRUE,idSyllabus);
//			}
//			else if(idClassStandard ==-1 && idSubject !=-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdSubjectAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idSubject,Boolean.TRUE,idSyllabus);
//			}
//			
//			else {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndIdClassStandardAndIdSubjectAndActiveFlagAndIdSyllabus(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,idClassStandard, idSubject,Boolean.TRUE,idSyllabus);
//			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());					
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No Syllabus found !");
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				if(idLiveClassCategory == 1L) {
					liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				else {
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}
				
				result.add(liveClassDto);
			}
			
			
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	//After LogedIn Academic Live
	@Override

	public Document<List<LiveClassDto>> afterLoginAcademicLive(Long idLiveClassCategory, Long idLanguage) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long classStdId;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
			} else {
				classStdId = 1L;
			}

			List<LiveClass> mainList = liveClassRepository
					.findByIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlag(
							classStdId,idLiveClassCategory, Boolean.TRUE,Boolean.TRUE);

			if (mainList.isEmpty()) {
				mainList = liveClassRepository
						.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateOrderByIdClassStandardAsc(idLiveClassCategory, Boolean.TRUE, idLanguage,Boolean.TRUE,LocalDate.now());
				if (mainList.isEmpty()) {
					throw new NullPointerException("No Video found!");
				}
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No Syllabus found !");
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	//After LogedIn Academic Upcoming
	@Override

	public Document<List<LiveClassDto>> afterLoginAcademicUpcoming(Long idLiveClassCategory, Long idLanguage) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long classStdId;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
			} else {
				classStdId = 1L;
			}

			List<LiveClass> mainList = liveClassRepository
					.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateAsc(
							LocalDate.now(),classStdId,idLiveClassCategory, Boolean.FALSE,Boolean.FALSE, idLanguage,Boolean.TRUE);

			if (mainList.isEmpty()) {
				mainList = liveClassRepository
						.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idLanguage,Boolean.TRUE);
				if (mainList.isEmpty()) {
					throw new NullPointerException("No Video found!");
				}
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No Syllabus found !");
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}



	public Document<List<LiveClassDto>> getAllUpcomingClassByClass(Long idClassStandard, Long idLiveClassCategory, Long idLanguage) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {			
			List<LiveClass> mainList = liveClassRepository
					.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageAndActiveFlagOrderByClassDateAsc(LocalDate.now(),idClassStandard,idLiveClassCategory, Boolean.FALSE,Boolean.FALSE,idLanguage,Boolean.TRUE);

			if (mainList.isEmpty())
				throw new NullPointerException("No Video found!");


			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<LiveClassDto>> getAllLiveClassByClass(Long idClassStandard, Long idLiveClassCategory, Long idLanguage) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {			
			List<LiveClass> mainList = liveClassRepository
					.findByIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlag(idClassStandard,idLiveClassCategory, Boolean.TRUE,Boolean.TRUE);

			if (mainList.isEmpty())
				throw new NullPointerException("No Video found!");


			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<LiveClassDto> getLiveClassMainVideoByClass(Long idClassStandard, Long idLiveClassCategory, Long idLanguage,Long idSubject, Long idSyllabus,Long idState) {
		
		Document<LiveClassDto> result = new Document<>();
		try {
			Long classStdId = idClassStandard;
//			Long syllabusId = idSyllabus;
			
			ArrayList <Long> idLiveClassCategorys = new ArrayList<>();
			ArrayList <Long> idLiveClassStandards = new ArrayList<>();
			ArrayList <Long> idSyllabusList = new ArrayList<>();
			ArrayList <Long> idStates = new ArrayList<>();
			idLiveClassCategorys.add(1l);
			idLiveClassStandards.add(classStdId);
			idSyllabusList.add(idSyllabus);
			idStates.add(idState);
			if (idLiveClassCategory != 1) 
			{
				idLiveClassCategorys.add(2l);
				idLiveClassStandards.add(4l);
				idSyllabusList.add(4l);
				idStates.add(6l);
			}
			
			ArrayList <Long>  idLanguages = new ArrayList<>();
			
			//Check user is sending any other language apart from english
			if (idLanguage != 7L)
			{
				Language lang = languageRepository.findByIdLanguage(idLanguage);
				
				if (lang == null)
					throw new NullPointerException("Invalid Language id");
				
				idLanguages.add(lang.getIdLanguage());
			}
			else {
				
				idLanguages.add(7L);// added English has a default language
				UserPrincipal userPrincipal = null;
				
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					 userPrincipal = (UserPrincipal) authentication.getPrincipal();
				} 
				
				if (userPrincipal == null)
					throw new AppException("Invalid user data");
				
				Language lang = languageRepository.findByLanguage(userPrincipal.getSecondaryLanguage());
				
				if (lang == null)
					throw new NullPointerException("Invalid Language found in user data");
				
				idLanguages.add(lang.getIdLanguage());
				
				
			}
			
			LiveClass  liveClass=null;
			
			
			if(idSubject != -1L) {
				
			 liveClass = liveClassRepository
					.findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndIdSubjectAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLanguageDesc(
							idLiveClassStandards,idLiveClassCategorys,Boolean.FALSE, Boolean.TRUE, LocalDate.now(),idSubject,Boolean.TRUE,idSyllabusList,idLanguages,idStates);
			System.out.println("liveClass with classid "+liveClass);	
			
			
				if (liveClass == null) {
					liveClass = liveClassRepository
							.findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusInAndIdStateInOrderByClassDateDescIdLanguageDesc(
									idLiveClassStandards,idLiveClassCategorys,Boolean.TRUE, Boolean.FALSE,idLanguages, idSubject,Boolean.TRUE,LocalDate.now(),idSyllabusList,idStates);
					System.out.println("broadcasted class "+liveClass);
					
				}
				
			}
			else {
				liveClass = liveClassRepository
						.findFirstByIdClassStandardInAndIdLiveClassCategoryInAndLiveCompletionFlagAndCurrentRunningFlagAndClassDateAndActiveFlagAndIdSyllabusInAndIdLanguageInAndIdStateInOrderByIdLiveClassCategoryAscIdLanguageDesc(
								idLiveClassStandards,idLiveClassCategorys,Boolean.FALSE, Boolean.TRUE, LocalDate.now(),Boolean.TRUE,idSyllabusList,idLanguages,idStates);
				System.out.println("liveClass with classid "+liveClass);			
				
				
				//below query will provide only academic webcast in the landing main video and live class main video  
				if (liveClass == null) {
					liveClass = liveClassRepository
							.findFirstByIdClassStandardAndIdLiveClassCategoryAndLiveCompletionFlagAndCurrentRunningFlagAndIdLanguageAndActiveFlagAndClassDateLessThanEqualAndIdSyllabusAndIdStateOrderByClassDateDescIdLanguageDesc(
									classStdId,1L,Boolean.TRUE, Boolean.FALSE,idLanguage,Boolean.TRUE,LocalDate.now(),idSyllabus,idState);
					System.out.println("broadcasted class "+liveClass);
					
				}
				
			}
			if (liveClass == null) {
				throw new AppException("No Class Found");
			}
			LiveClassDto liveClassDto = new LiveClassDto();
			liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
			liveClassDto.setIdTeacher(liveClass.getIdTeacher());
			liveClassDto.setClassDate(liveClass.getClassDate());
			liveClassDto.setFromTime(liveClass.getFromTime());
			liveClassDto.setToTime(liveClass.getToTime());
			liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
			liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
			liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
			liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
			liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
			liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
			liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
			liveClassDto.setPdfURL(liveClass.getPdfURL());
			
			liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
			
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
			if(syllabus == null) throw new NullPointerException("No syllabus found");
			
			liveClassDto.setSyllabusName(syllabus.getSyllabusName());
			
			LiveClassCategory category=liveClassCategoryRepository.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
			if(category !=null)
				liveClassDto.setClassCategory(category.getLiveClassCategoryName());

			Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
			if (teacher == null) 
				throw new NullPointerException("Invalid  idTeacher found in live class data");
				liveClassDto.setTeacherName(teacher.getTeacherName());
				liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
				liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
			
			ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
			if(classStandard==null) 
				throw new NullPointerException("Invalid  idClassStandard found in live class data");
				liveClassDto.setClassStandard(classStandard.getClassStandadName());
				liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
			
			
			Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
			if(subject==null) 
				throw new NullPointerException("Invalid  idSubject found in live class data");
				liveClassDto.setIdSubject(subject.getIdSubject());
				liveClassDto.setSubjectName(subject.getSubjectName());
				
			Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
			
			if(language==null)throw new NullPointerException("No Language Found");
			liveClassDto.setLanguage(language.getLanguage());
			System.out.println("language" + language.getLanguage());
			
			
			if(liveClass.getIdLiveClassCategory() ==1L) {
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			else {
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
			}
			

			result.setData(liveClassDto);
			
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

	@Override
	public Document<List<LiveClassDto>> getAllAcademicLiveByLanguage(Long idLiveClassCategory, Long  idLanguage) {
		
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long classStdId;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
			} else {
				classStdId = 1L;
			}
			List<LiveClass> mainList = liveClassRepository. findByIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageAndActiveFlag(classStdId, idLiveClassCategory, Boolean.TRUE, idLanguage,Boolean.TRUE);
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
				
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
			
		}
		catch(Exception exp) {
			
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
			
		}
		
	}
    
	
	
	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */
	@SuppressWarnings("unused")
	@Override
	public Document<List<LiveClassDto>> getAllLiveAndUpcomingBySubjectAndClassAndLan(Long idLiveClassCategory,String language, Long idSubject, Long idClassStandard, Long idSyllabus , Long idState) {
		
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			Long lan = null;
			
			if(language.equals("English")) {
				lan = 7L;
			}
			else {
				Language lang = languageRepository.findByLanguage(language);
				if(lang !=null) {
					 lan =(lang.getIdLanguage());
					}
				else {
					throw new NullPointerException("No language Found !");
				}
			}
			Long classStdId;
			Long syllabusId;
			Long stateId;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
				if(user.getRegisteredAs().equalsIgnoreCase("Student")) {
					Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
					
					if(student == null) throw new NullPointerException("No Student found !");
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found ");
					
					syllabusId = syllabus.getIdSyllabus();
					
					State state = stateRepository.findByIdState(student.getIdState());
					
					if(state == null) throw new NullPointerException("Invalid state found in user object ");
					
					stateId  = state.getIdState();
					
				}
				
			} else {
				classStdId = idClassStandard;
				syllabusId = idSyllabus;
				stateId = idState;
				
			}
			
			List<LiveClass> mainList = new ArrayList<>();
			
			if(idLiveClassCategory == 1) 
			{
				mainList = liveClassRepository.getAcademicLiveByClass(idClassStandard, idSubject, lan, idSyllabus,idState);
			}
			else {
				mainList = liveClassRepository.getExtraCurLiveByClass(idClassStandard, idSubject, lan, idSyllabus);
			}

			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language1 =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language1==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language1.getLanguage());
				System.out.println("language" + language1.getLanguage());
				
				
				if(idLiveClassCategory ==1L) {
					liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" " );
				}
				
				else {
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				}

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

	}
    @Async
	@Override
	public void  deactivateDeletedVideos() {
		
		List <String> result = new ArrayList<>();
		
		
		Instant startTime, endTime;
		
		startTime = Instant.now();
		//deactivate batch counts
//		int dabc = 0; 
		
//		int errCount = 0; 
		
		result.add("Scheduler running to find deleted videos at :"+ " "+LocalTime.now()+ " on "+LocalDate.now());
		
		
		List<LiveClass> liveClassVideos = new ArrayList<LiveClass>();
		List<LiveClass> deletedLiveClassVideos = new ArrayList<LiveClass>();
		URLConnection myURLConnection=null;
		URL myURL=null;
		String tempUrl = "https://www.youtube.com/oembed?";
		BufferedReader reader=null;
		System.out.println("Scheduler running to find deleted videos at :"+ " "+LocalTime.now()+ " on "+LocalDate.now());
		try {
			liveClassVideos=liveClassRepository.findAllByActiveFlagAndLiveClassURLIsNotNull(Boolean.TRUE);
			System.out.println("Total classes available"+ " "+liveClassVideos.size());
			result.add("Total classes available"+ " "+liveClassVideos.size());
			int skippedCount = 0;
			for (LiveClass liveClassDto : liveClassVideos) {
				//System.out.println("test" +" "+liveClassDto.getIntroVideoURL());
				String liveClassUrl=liveClassDto.getLiveClassURL();
				StringBuilder sbPostData= new StringBuilder(tempUrl);
				String vId=helper.extractYTId(liveClassUrl);
				sbPostData.append("url="+"https://www.youtube.com/watch?v="+vId);
				
				
				String mainUrl = sbPostData.toString();
				
				try
				{
				    //prepare connection
				    myURL = new URL(tempUrl);
				    myURLConnection = myURL.openConnection();
				    myURLConnection.connect();
				    reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(mainUrl)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
				    //reading response 
				    @SuppressWarnings("unused")
					String response;
				    while ((response = reader.readLine()) != null) 
				    {
				    	//System.out.println("message-id: "+response);
				    }
				    //print response 
				    
				    
				    //finally close connection
				    reader.close();
				    
				} 
				catch (FileNotFoundException e) 
				{ 
					//e.printStackTrace();
					liveClassDto.setActiveFlag(Boolean.FALSE);
			
					
					deletedLiveClassVideos.add(liveClassDto);
					
				} 
				
				catch(Exception e) {
					System.err.println("Something went wrong while running scheduler at "+LocalTime.now()+" on  "+LocalDate.now()+ " "+e.getLocalizedMessage());
					System.out.println("idLiveClass" + " "+ liveClassDto.getIdLiveClass());
					result.add("Error occured:");
					result.add("Something went wrong while running scheduler at "+LocalTime.now()+" on  "+LocalDate.now()+ " "+e.getLocalizedMessage());
					result.add("idLiveClass" + " "+ liveClassDto.getIdLiveClass());
					skippedCount++;
				}
				
			}
			liveClassRepository.saveAll(deletedLiveClassVideos);
			System.out.println("Total classes deactivated"+ " "+deletedLiveClassVideos.size());
			result.add("Total classes deactivated:"+ " "+deletedLiveClassVideos.size());
			System.out.println("Skipped video counts: "+ skippedCount);
			result.add("Skipped video counts: "+ skippedCount);
			
		}catch(Exception e) {
			result.add("Error occured:");
			result.add("Something went wrong while running scheduler at "+LocalTime.now()+" on  "+LocalDate.now()+ " "+e.getLocalizedMessage());
			System.out.println("Something went wrong while running scheduler at "+LocalTime.now()+" on  "+LocalDate.now()+ " "+e.getLocalizedMessage());
		}
		
		endTime = Instant.now();
	
		result.add("End Time:"+endTime);
		result.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		result.add("Live class youtube deleted video finder scheduler completed!!!");
		
		 String fileName = "LiveClassYoutubeVideoDeletedCheckLog_"+Instant.now()+".txt";
		//log the file to s3 location
		try(FileWriter writer = new FileWriter(fileName) ) {
	
		for(String str: result) {
			  writer.write(str + System.lineSeparator());
			}
			writer.close();
		
			File file = new File(fileName);
			
		    fileUploadService.uploadFileToS3Bucket("/logs/scheduler/deactivated-liveclass", fileName, file);
			
		    boolean isDeletedFile = file.delete();
		    logger.info("Live class deletion log file deleted from the system : "+" - "+isDeletedFile );
			
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			
		}
		
	//	return result;
	}

	@Override
	public Boolean userAllowedToAccessBroadCastedVideo(Long idLiveClass) {
		
		try {
			LiveClass liveClass = liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass,Boolean.TRUE);
			
			if (liveClass == null)
				throw new AppException("No Live Class details found!");
			
			if (liveClass.isLiveCompletionFlag()) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        UserPrincipal userPrincipal = null;
		        if (!(authentication instanceof AnonymousAuthenticationToken)) {
		            userPrincipal = (UserPrincipal) authentication.getPrincipal();
		        }
		        if (userPrincipal != null) {		        	
		        	StudentPostLoginDTO subscribedOrFreeuser = studentSubscriptionService.checkExistingSubscriptionLogin(userPrincipal.getUserSurId());
		        	if (!subscribedOrFreeuser.getSubscribedFlag() && !subscribedOrFreeuser.getTrialFlag()) {
		        		return false;
		        	}
		        }
			}
			return true;
		} catch (Exception exp) {
			throw new AppException("Error while fetching live class access permissions");
		}
	}
	
	
	
	
	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */
	
	public Document<List<LiveClassDto>> otherclassLiveBeforeLogin(Long idLiveClassCategory,Long idClassStandard, Long idSubject, Long idSyllabus,Long idState) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			
			Long newidClassStandard =  idClassStandard == -1 ? null : idClassStandard;
			Long newidSubject = idSubject == -1 ? null : idSubject;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			//datas are the being hardcoded for the specific purpose of this api in order to fetch only ongoing live class 
			//only active live class data needed to be fetched 
			Boolean activeFlag = true;
			//live class which are running currently 
			Boolean currentRunningFlag = true;
			//live class completion flag needed to false 
			Boolean completionFlag = false;
			
			LocalDate todayDate = LocalDate.now();
			
			List<LiveClass> mainList = new ArrayList<>();
			
			mainList = liveClassRepository.getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateOrderbyClassStandardAsc
			(idLiveClassCategory,null,currentRunningFlag,completionFlag,newidClassStandard,newidSubject,newidSyllabus,newidState,activeFlag,todayDate);
			
//			
//			if(idClassStandard == -1L && idSubject==-1L && idSyllabus == -1) {
//			 mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateOrderByIdClassStandardAsc(idLiveClassCategory, Boolean.TRUE,Boolean.TRUE,LocalDate.now());
//			System.out.println("class and subject are -1");
//			}
//			else if(idClassStandard !=-1L && idSubject==-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdClassStandardAndActiveFlagAndClassDate(idLiveClassCategory, Boolean.TRUE,idClassStandard,Boolean.TRUE,LocalDate.now());
//			System.out.println("class not = -1 and subject =-1");
//			}
//			else if(idClassStandard ==-1L && idSubject !=-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndClassDateOrderByIdClassStandardAsc
//						(idLiveClassCategory, Boolean.TRUE,idSubject,Boolean.TRUE,LocalDate.now());			
//			System.out.println("class =-1 and subject not =-1");
//			}
//			else if(idClassStandard ==-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc(idLiveClassCategory, Boolean.TRUE,Boolean.TRUE,LocalDate.now(),idSyllabus);
//			}
//			else if(idClassStandard !=-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdClassStandardAndActiveFlagAndClassDateAndIdSyllabus(idLiveClassCategory, Boolean.TRUE,idClassStandard,Boolean.TRUE,LocalDate.now(),idSyllabus);
//			}
//			else if(idClassStandard ==-1 && idSubject !=-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndClassDateAndIdSyllabusOrderByIdClassStandardAsc
//				(idLiveClassCategory, Boolean.TRUE,idSubject,Boolean.TRUE,LocalDate.now(),idSyllabus);
//			}
//			else {
//				mainList = liveClassRepository.findByIdLiveClassCategoryAndCurrentRunningFlagAndIdSubjectAndActiveFlagAndIdClassStandardEqualsAndClassDateAndIdSyllabus(idLiveClassCategory, Boolean.TRUE,idSubject,Boolean.TRUE, idClassStandard,LocalDate.now(),idSyllabus);
//				System.out.println("class and subject not =-1");
//			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());					
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				 liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found");
					
					liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
			}
			
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} 
		
		catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	/**
	 * 
	 * updated by @author NAVEEN 
	 * for the addition of idState constrain 
	 */
	public Document<List<LiveClassDto>> otherclassUpcomingBeforeLogin(Long idLiveClassCategory,Long idClassStandard,Long idSubject, Long idSyllabus,Long idState) {
		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			
			Long newidClassStandard =  idClassStandard == -1 ? null : idClassStandard;
			Long newidSubject = idSubject == -1 ? null : idSubject;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			//datas are the being hardcoded for the specific purpose of this api in order to fetch only upcoming live class 
			//only active live class data needed to be fetched 
			Boolean activeFlag = true;
			//live class which are  upcoming
			Boolean currentRunningFlag = false;
			//live class completion flag needed to false 
			Boolean completionFlag = false;
			
			LocalDate todayDate = LocalDate.now();
			
			
			List<LiveClass> mainList = new ArrayList<>();
			
			mainList = liveClassRepository.getLiveClassByLiveClassCategoryAndLanguageAndRunningFlagAndCompletionFlagAndClassStandardAndSubjectAndSyllabusAndStateAndActiveFlagAndClassDateGreaterThanorEqualOrderbyClassDateAsc
			(idLiveClassCategory,null,currentRunningFlag,completionFlag,newidClassStandard,newidSubject,newidSyllabus,newidState,activeFlag,todayDate);
			
			
//			
//			if(idClassStandard== -1L && idSubject==-1L && idSyllabus == -1) {
//			
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndActiveFlagOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,Boolean.TRUE);
//			}else if(idClassStandard !=-1L && idSubject==-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndActiveFlag(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idClassStandard,Boolean.TRUE);
//			}
//			else if(idClassStandard==-1L && idSubject !=-1L && idSyllabus == -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdSubjectAndActiveFlagOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idSubject,Boolean.TRUE);
//			}
//			
//			else if(idClassStandard ==-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,Boolean.TRUE,idSyllabus);
//			}
//			else if(idClassStandard !=-1 && idSubject ==-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndActiveFlagAndIdSyllabus(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idClassStandard,Boolean.TRUE,idSyllabus);
//			}
//			else if(idClassStandard ==-1 && idSubject !=-1 && idSyllabus != -1) {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdSubjectAndActiveFlagAndIdSyllabusOrderByIdClassStandardAsc(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idSubject,Boolean.TRUE,idSyllabus);
//			}
//			
//			else {
//				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdClassStandardAndIdSubjectAndActiveFlagAndIdSyllabus(LocalDate.now(),idLiveClassCategory, Boolean.FALSE, Boolean.FALSE,idClassStandard, idSubject,Boolean.TRUE,idSyllabus);
//			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());					
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				
				result.add(liveClassDto);
			}
			
			
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	}

	public Document<List<LiveClassDto>> otherclassAfterLoginLive(Long idLiveClassCategory, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try { 
			
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			Long classStdId;
			Long syllabusId; 
			Long idState ;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
				
				if(user.getRegisteredAs().equalsIgnoreCase("Student")) {
					Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
					
					if(student == null) throw new NullPointerException("No Student found !");
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found ");
					
					syllabusId = syllabus.getIdSyllabus();
					
					State state = stateRepository.findByIdState(student.getIdState());
					
					if(state == null) throw new NullPointerException("invalid state for from user object.");
					
					idState = state.getIdState();
					
				}
				else {
					classStdId = 1L;
					syllabusId =1L;
					idState = 1L;
				}
				
				
			} else {  
				classStdId = 1L;
				syllabusId =1L;
				idState = 1L;
			}
			
			List<LiveClass> mainList = new ArrayList<>();

			if(idSubject==-1L) {
			 mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndIdSyllabusAndIdState
					(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.TRUE, lanLists,Boolean.TRUE,syllabusId,idState);
			}
			else {
				mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabusAndIdState
						(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.TRUE, lanLists, idSubject,Boolean.TRUE,syllabusId,idState);
			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());				
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

		
	}
	
	public Document<List<LiveClassDto>> otherclassAfterLoginUpcoming(Long idLiveClassCategory, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try {
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			Long classStdId;
			Long syllabusId = null;
			Long idState;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}

				classStdId = user.getClassStandard()==null || user.getClassStandard()==4 ?1L:user.getClassStandard();
				
				if(user.getRegisteredAs().equalsIgnoreCase("Student")) {
					Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
					
					if(student == null) throw new NullPointerException("No Student found !");
					
					Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
					if(syllabus == null) throw new NullPointerException("No syllabus found ");
					
					syllabusId = syllabus.getIdSyllabus();
					
					State state = stateRepository.findByIdState(student.getIdState());
					
					if(state == null) throw new NullPointerException("invalid state found in user object");
					
					idState = state.getIdState();
					
				}
				
				else 
				{
					classStdId = 1L;
					syllabusId =1L;
					idState = 1L;
				}
				
			} 
			else {
				classStdId = 1L;
				syllabusId =1L;
				idState = 1L;
			}
			
			List<LiveClass> mainList = new ArrayList<>();
			if(idSubject==-1L) 
			{
			 mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.FALSE, Boolean.FALSE, lanLists,Boolean.TRUE,syllabusId,idState);
			}
			else {
				mainList = liveClassRepository.findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndIdSubjectAndActiveFlagAndIdSyllabusAndIdStateOrderByClassDateAsc(LocalDate.now(), classStdId, idLiveClassCategory, Boolean.FALSE, Boolean.FALSE, lanLists, idSubject,Boolean.TRUE,syllabusId,idState);
			}
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
				if(classStandard==null) 
					throw new NullPointerException("Invalid  idClassStandard found in live class data");
					liveClassDto.setClassStandard(classStandard.getClassStandadName());
					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
					
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				liveClassDto.setTextBelowLiveClass("Class "+classStandard.getClassStandadName() +" | "+ liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				

				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

	}

	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<Page<LiveClassDto>> getAllExtraCurWebCastedVideos(Long idLiveClassCategory, Long idLanguage, Long idSubject,int pageNumber) {
		
		Document<Page<LiveClassDto>> doc = new Document<>();
		
		List<LiveClassDto> result =new ArrayList();
		Page<LiveClassDto> page = null;
		
		try {
			
			
			Page<LiveClass> classList = null;
			
			Pageable paging = PageRequest.of(pageNumber, 12);
			if(idSubject==-1L ) {
			classList = liveClassRepository
					.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdLiveClassCategoryOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idLiveClassCategory,paging);
			}
			
			else{
				classList = liveClassRepository
						.findAllByLiveCompletionFlagAndIdLanguageAndActiveFlagAndIdSubjectAndIdLiveClassCategoryOrderByClassDateDesc(Boolean.TRUE,idLanguage,Boolean.TRUE,idSubject,idLiveClassCategory,paging);
			}
			
			if (classList.getContent().isEmpty() && pageNumber > 0) {
				doc.setData(null);
				doc.setStatusCode(HttpStatus.OK.value());
				doc.setMessage("No More Videos Available.");

			}
			else if (classList.getContent().isEmpty()) {

				throw new NullPointerException("No Videos Available.");
			} else {
			
			for (LiveClass liveClass : classList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				
				Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());
				if(syllabus == null) throw new NullPointerException("No syllabus found");
				
				liveClassDto.setSyllabusName(syllabus.getSyllabusName());
				

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());
				if (category != null)

					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
				if (teacher == null) 
					throw new NullPointerException("Invalid  idTeacher found in live class data");
					liveClassDto.setTeacherName(teacher.getTeacherName());
					liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
					liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
				
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject!=null) {
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());
				}
                  
				liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");
				result.add(liveClassDto);
				}
			
			if (result.isEmpty())
				throw new AppException("No Videos available");
			
			
			if ((paging.getPageSize() * paging.getPageNumber() >= classList.getTotalElements())) {
				doc.setData(null);
				doc.setMessage("No More Videos Available");
				doc.setStatusCode(HttpStatus.OK.value());
			}
			else {

//				int start = (int) paging.getOffset();
//				int end = (start + paging.getPageSize()) > result.size() ? result.size()
//						: (start + paging.getPageSize());
				page = new PageImpl<>(result, paging, classList.getTotalElements());

				doc.setData(page);
				doc.setMessage("Request Successfull");
				doc.setStatusCode(200);
				
			}

			}
			} catch (Exception exp) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
		}
		return doc;
	}
	
	public Document<List<LiveClassDto>> otherExtraCurrclassLive(Long idLiveClassCategory, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try { 
			
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			UserPrincipal userPrincipal = null;
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}
				}
				
				
			
			List<LiveClass> mainList = new ArrayList<>();
			
			if (userPrincipal != null)
			{

				 mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndIdLanguageInAndActiveFlagAndIdSyllabusOrderByIdLanguageDesc
						(LocalDate.now(), 4L, idLiveClassCategory, Boolean.TRUE, lanLists,Boolean.TRUE,4L);
				
			}
			
			else {
				
				 mainList = liveClassRepository.findByClassDateAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndActiveFlagAndIdSyllabusOrderByIdLanguageDesc
							(LocalDate.now(), 4L, idLiveClassCategory, Boolean.TRUE, Boolean.TRUE,4L);
			}
			

		
			
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				 Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
					if (teacher == null) 
						throw new NullPointerException("Invalid  idTeacher found in live class data");
						liveClassDto.setTeacherName(teacher.getTeacherName());
						liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
						liveClassDto.setTeacherDescription(teacher.getTeacherDesc());

//				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
//				if(classStandard!=null) {
//					liveClassDto.setClassStandard(classStandard.getClassStandadName());
//					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
//				}
				
				Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				if(subject==null) 
					throw new NullPointerException("Invalid  idSubject found in live class data");
					liveClassDto.setIdSubject(subject.getIdSubject());
					liveClassDto.setSubjectName(subject.getSubjectName());				
				
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");


				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}

		
	}
	
	public Document<List<LiveClassDto>> otherExtraCurrclassUpcoming(Long idLiveClassCategory, Long idSubject) {

		Document<List<LiveClassDto>> doc = new Document<>();
		List<LiveClassDto> result = new ArrayList<LiveClassDto>();
		try { 
			
			
			List<Long> lanLists = new ArrayList<>();
			lanLists.add(7L);
			
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());

				if (user == null) throw new AppException("No user data found.");
				
				if(user.getSecondaryLanguage()!=null) {
					Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
					
					if(lang != null)					
					
					lanLists.add(lang.getIdLanguage());
				}
				}
				
				
			
			List<LiveClass> mainList = new ArrayList<>();

			
			 mainList = liveClassRepository.
		findByClassDateGreaterThanEqualAndIdClassStandardAndIdLiveClassCategoryAndCurrentRunningFlagAndLiveCompletionFlagAndIdLanguageInAndActiveFlagAndIdSyllabusOrderByClassDateAsc(LocalDate.now(), 4L, idLiveClassCategory, Boolean.FALSE, Boolean.FALSE, lanLists,Boolean.TRUE,4L);
			
			
			if (mainList.isEmpty()) {
				throw new NullPointerException("No Video found!");
			}
			for (LiveClass liveClass : mainList) {

				LiveClassDto liveClassDto = new LiveClassDto();
				liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
				liveClassDto.setIdTeacher(liveClass.getIdTeacher());
				liveClassDto.setClassDate(liveClass.getClassDate());
				liveClassDto.setFromTime(liveClass.getFromTime());
				liveClassDto.setToTime(liveClass.getToTime());
				liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
				liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
				liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
				liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
				liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
				liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
				liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
				liveClassDto.setIdLiveClassCategory(idLiveClassCategory);
				liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
				liveClassDto.setPdfURL(liveClass.getPdfURL());
				
				liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
				

				LiveClassCategory category = liveClassCategoryRepository.findByIdLiveClassCategory(idLiveClassCategory);
				if (category != null)
					liveClassDto.setClassCategory(category.getLiveClassCategoryName());

				 Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());
					if (teacher == null) 
						throw new NullPointerException("Invalid  idTeacher found in live class data");
						liveClassDto.setTeacherName(teacher.getTeacherName());
						liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
						liveClassDto.setTeacherDescription(teacher.getTeacherDesc());

//				ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());
//				if(classStandard!=null) {
//					liveClassDto.setClassStandard(classStandard.getClassStandadName());
//					liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
//				}
				
				 Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());
				     if(subject==null) 
				     throw new NullPointerException("Invalid  idSubject found in live class data");
					 liveClassDto.setIdSubject(subject.getIdSubject());
					 liveClassDto.setSubjectName(subject.getSubjectName());				
							
				Language language =languageRepository.findByIdLanguage(liveClass.getIdLanguage());
				
				if(language==null)throw new NullPointerException("No Language Found");
				liveClassDto.setLanguage(language.getLanguage());
				System.out.println("language" + language.getLanguage());
				
					liveClassDto.setTextBelowLiveClass(liveClass.getLiveClassHeading()+ " | "+ teacher.getTeacherName()+" ");


				result.add(liveClassDto);
			}
			doc.setData(result);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Request Sucessfull");
			return doc;
		} catch (Exception exp) {

			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(exp.getLocalizedMessage());
			return doc;
		}
	
}

	@Override
	public Document<LiveClassWatchedHistory> saveLiveClassWatchedHistory(Long idLiveClass) {
		// TODO Auto-generated method stub
		LiveClassWatchedHistory watchedHistory;
		Document<LiveClassWatchedHistory> result=new Document<>();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				if(user ==null) throw new AppException("No user data found.");
				
				LiveClassWatchedHistory existingHistory=liveClassWatchedHistoryRepo.findByIdLiveClassAndIdVlUser(idLiveClass,user.getUserSurId());
				
				if(existingHistory==null) {
					LiveClass liveClas=liveClassRepository.findByIdLiveClassAndActiveFlag(idLiveClass, Boolean.TRUE);
					
					if(liveClas==null) throw new AppException("No Live class data found.");
					String thumbnailURL=liveClas.getThumbnailURL();
					Student student=studentRepository.findByUser(user);
					Subject subject=subjectRepository.findByIdSubject(liveClas.getIdSubject());

					watchedHistory=new LiveClassWatchedHistory(idLiveClass, thumbnailURL, LocalDateTime.now(), 0L, liveClas.getIdLiveClassCategory(),liveClas.getLiveClassHeading(),liveClas.getLiveClassDesc(),user.getUserSurId(), student.getIdStudent(),liveClas.getIdSubject(),subject.getSubjectName());


					LiveClassWatchedHistory saveHistory=liveClassWatchedHistoryRepo.save(watchedHistory);
					
					result.setData(saveHistory);
					result.setMessage("Successful");
					result.setStatusCode(200);
				}
				else {
					result.setData(null);
					result.setMessage("History already exists");
					result.setStatusCode(500);
				}
				
				
			
			}else {
				result.setData(null);
				result.setMessage("Invalid User");
				result.setStatusCode(500);
			}
		}catch(Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		

		return result;
	}

	@Override
	public Document<Page<Map<String,List<LiveClassWatchedHistory>>>> getAllWatchedHistoy(int pageNumber, Long idSubject , Long idLanguage,String type) {
		// TODO Auto-generated method stub
		Document<Page<Map<String,List<LiveClassWatchedHistory>>>> result=new Document<>();
		try {
			
			List<LiveClassWatchedHistory> historyList=new ArrayList<>();
			
			
			 type = !type.equalsIgnoreCase("free") ? "premium" : "free";
			  
			 idSubject = !idSubject.equals(-1L) ? idSubject : null;
			 idLanguage = !idLanguage.equals(-1L) ? idLanguage : null;

//			Page<Map<String,LiveClassWatchedHistory>> page = null;
			Pageable paging = PageRequest.of(pageNumber, 12);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(!(authentication instanceof AnonymousAuthenticationToken)) {
				
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				if(user ==null) throw new AppException("No user data found.");
				
				 historyList=liveClassWatchedHistoryRepo.findAllByIdVlUserAndIdSubjectAndIdLanguageOrderByLastAccessedTimestampDesc
						 (user.getUserSurId(),idSubject,idLanguage,type, paging);
				 
				 if(!historyList.isEmpty()) {
					 List<LiveClassWatchedHistory> academicHistoryList=new ArrayList<>();
					List<LiveClassWatchedHistory> extraCurrHistoryList=new ArrayList<>();
					List<Map<String,List<LiveClassWatchedHistory>>> finalList=new ArrayList<>();
					 Map<String,List<LiveClassWatchedHistory>> historyMap=new HashMap<>();
					 for(LiveClassWatchedHistory history:historyList) {
							Long idLiveClass=history.getIdLiveClass();
							String thumbnailURL=getLiveClassDetails(idLiveClass);
							history.setThumbnailURL(thumbnailURL);
							if(history.getIdLiveClassCategory().equals(1L)) {
								academicHistoryList.add(history);
							}else extraCurrHistoryList.add(history);
						}
					 historyMap.put("ACADEMIC",academicHistoryList);
					 historyMap.put("EXTRA_CURR", extraCurrHistoryList);
					 finalList.add(historyMap);
					 
					 Page<Map<String,List<LiveClassWatchedHistory>>> list = new PageImpl<>(finalList);
						if (list.isEmpty() && pageNumber > 0) {
							result.setData(null);
							result.setStatusCode(HttpStatus.OK.value());
							result.setMessage("No More live class history Available Right Now!");

						} 
						else {
							result.setData(list);
							result.setMessage("Success");
							result.setStatusCode(200);
						}
				 }
				 else {
					 List<Map<String,List<LiveClassWatchedHistory>>> finalList = new ArrayList<>();
					 Page<Map<String,List<LiveClassWatchedHistory>>> list = new PageImpl<>(finalList);
					 result.setData(list);
					 result.setStatusCode(200);
					 result.setMessage("No history Available Right Now!");
				 }

			}
			else 
			{
				result.setData(null);
				result.setStatusCode(401);
				result.setMessage("Invalid User Authentication access!");
				
			}

			
			
		}catch(Exception e) {
			result.setData(null);
			result.setStatusCode(500);
			result.setMessage(e.getLocalizedMessage());
		}
		
		return result;
	}

	private String getLiveClassDetails(Long idLiveClass) {
		// TODO Auto-generated method stub
		String liveclassThumbnailUrl=liveClassRepository.getThumnailUrlLiveClass(idLiveClass);
		return liveclassThumbnailUrl;
		
	}

	@Override
	public Document<LiveClassWatchedHistory> updateLiveClassWatchedHistory(Long idLiveClass, Long watchedDuration) {
		// TODO Auto-generated method stub
		Document<LiveClassWatchedHistory> result=new Document<>();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				if(user ==null) throw new AppException("No user data found.");
			LiveClassWatchedHistory existingHistory=liveClassWatchedHistoryRepo.findByIdLiveClassAndIdVlUser(idLiveClass,user.getUserSurId());
			if(existingHistory !=null) {
				existingHistory.setVideoCoverageDuration(watchedDuration);
				existingHistory.setLastAccessedTimestamp(LocalDateTime.now());
				LiveClassWatchedHistory updatedHistory=liveClassWatchedHistoryRepo.save(existingHistory);
				result.setData(updatedHistory);
				result.setMessage("Successful");
				result.setStatusCode(200);
			}
			}
		}catch(Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	
}

