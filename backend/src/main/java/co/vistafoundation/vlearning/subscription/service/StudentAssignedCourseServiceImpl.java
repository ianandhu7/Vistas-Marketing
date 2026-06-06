package co.vistafoundation.vlearning.subscription.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.dto.SACVideoDurationSyncDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.StudentDownloadVideoInfoDTO;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.model.TopicLanguage;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.TopicLanguageRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.dto.QuizStatisticsDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectQuizStatisticsDTO;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.Card;
import co.vistafoundation.vlearning.subscription.dto.StudentSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.SubjectVideoWatchDTO;
import co.vistafoundation.vlearning.subscription.dto.TotalVideoWatchDTO;
import co.vistafoundation.vlearning.subscription.enums.CARD;
import co.vistafoundation.vlearning.subscription.model.VideoStreamingLog;
import co.vistafoundation.vlearning.subscription.repository.VideoStreamingLogRepository;
import co.vistafoundation.vlearning.subscription.utils.VideoStreamingLogUtils;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user_activity.config.DynamoDbHelper;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.UserDownloadRequest;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherLicenseRules;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;


/**
 * 
 * 
 * @author NaveenKumar
 *
 */
@Service
public class StudentAssignedCourseServiceImpl implements StudentAssignedCourseService {

	@Autowired
	StudentAssignedCourseRepository studentAssignedCourseRepository;

	@Autowired
	VideoCipherConfiguration videoCipherConfiguration;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;
	
	@Autowired
	LanguageRepository languageRepository;
	
	@Autowired
	TopicLanguageRepository topicLanguageRepository;
	

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	VideoStreamingLogRepository videoStreamingLogRepository;
	
	@Autowired
	VideoStreamingLogUtils videoStreamingLogUtils;
	
	@Autowired
	DynamoDbHelper  dynamoDbHelper;
	
	@Autowired
	StudentChapterQuizRepository studentChapterQuizRepository;
	
	@Value("${aws.accesskey.id}")
	private String awsAccesskeyId;
	
	@Value("${aws.secretaccess.key}")
	private String awsSecretAccess;
	
	
	@Override
	public Document<StudentAssignedCourse> updateStudentAssignedCourseById(Long saci, String duration) {

		Document<StudentAssignedCourse> result = new Document<StudentAssignedCourse>();

		try {
			StudentAssignedCourse sac = studentAssignedCourseRepository.findByIdStudentAssignedCourse(saci);

			// check for null availability , if particular not found throw the Exception.
			if (sac == null)
				throw new NullPointerException("Invalid Student Assigened Courses Id.");

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 userPrincipal = (UserPrincipal) authentication.getPrincipal();
				}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
			
			VideoStreamingLog videoStreamingLog = new VideoStreamingLog();

			BeanUtils.copyProperties(sac, videoStreamingLog);
			videoStreamingLog.setIdVlUser(userPrincipal.getUserSurId());
			
			
			int prevDuration = sac.getVideoCoverageDuration();
			int totalDuration = sac.getVideoDuration();
			int currentDuration = Math.round(Float.valueOf(duration));
						
            
			// check if the currentDuration of the video not more the total duration of the
			// video.
			if (currentDuration > totalDuration)
				throw new AppException("Video Duration Exceed the Limit!");

			// check the video already completely streamed if not , this block will execute
			if (!Boolean.TRUE.equals(sac.getCompleteFlag())) {

				if (prevDuration < currentDuration) {

					sac.setVideoCoverageDuration(currentDuration);

					// providing some threshold value for streaming video , this will be useful for
					// easier analytics.
					
					Double thresholdValue = totalDuration * 0.05;
					
					
					if (totalDuration - currentDuration <= thresholdValue.intValue())

					{
						sac.setVideoCoverageDuration(totalDuration);
						sac.setCompleteFlag(Boolean.TRUE);
						sac.setPctComplete("100");
						BeanUtils.copyProperties(sac, videoStreamingLog);
						videoStreamingLog=videoStreamingLogUtils.nextVideoStreamingLog(videoStreamingLog);
						videoStreamingLogUtils.videoStreamingLog(videoStreamingLog);
					} else {
						// calculate the percentage and update the SAC
						double meanValue = (double) currentDuration / totalDuration;
						meanValue *= 100;
						String percent = Double.toString(Math.round(meanValue));
						sac.setPctComplete(percent);

						//video Streaming Log
				        BeanUtils.copyProperties(sac, videoStreamingLog);
				        videoStreamingLogUtils.videoStreamingLog(videoStreamingLog);
						
					}

				}

				StudentAssignedCourse endResult = studentAssignedCourseRepository.save(sac);
				result.setData(endResult);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");

         			}

			else {
				// This block will be executed when complete flag is true , user can stream
				// video as many time . coverage duration will get updated dynamically based on
				// user streaming.
				sac.setVideoCoverageDuration(currentDuration);
				
				StudentAssignedCourse endResult = studentAssignedCourseRepository.save(sac);
				BeanUtils.copyProperties(endResult, videoStreamingLog);
				Double thresholdValue = totalDuration * 0.05;
				if (totalDuration - currentDuration <= thresholdValue.intValue()) {
				videoStreamingLog=videoStreamingLogUtils.nextVideoStreamingLog(videoStreamingLog);
				}			
				videoStreamingLogUtils.videoStreamingLog(videoStreamingLog);
				result.setData(endResult);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");

			}
		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<VideoCipherOTP> getDownloadVideoInfo(String videoId) {

		Document<VideoCipherOTP> result = new Document<VideoCipherOTP>();
		VideoCipherOTP vco = new VideoCipherOTP();

		VideoCipherLicenseRules vclr = new VideoCipherLicenseRules();
		vclr.setCanPersist(true);
		vclr.setRentalDuration(5184000);// defining the otp valid duration for 60 days only

		UserDownloadRequest udr = new UserDownloadRequest();

		try {
			

			udr.setLicenseRules(new ObjectMapper().writeValueAsString(vclr));

			String serialized = new ObjectMapper().writeValueAsString(udr);
			

			vco = videoCipherConfiguration.getOTP(videoId, serialized);

			if (vco == null)
				throw new NullPointerException("Invalid Video Id.");

			result.setData(vco);
			result.setData(vco);
			result.setStatusCode(HttpStatus.CREATED.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<StudentAssignedCourse> createStudentAssigendCourse(Long idOfflineCourse,
			Long idStudentSubcription) {
		Document<StudentAssignedCourse> result = new Document<StudentAssignedCourse>();
		try {

			StudentAssignedCourse sac = studentAssignedCourseRepository
					.findByIdOfflineVideoCourseAndIdStudentSubscription(idOfflineCourse, idStudentSubcription);

			if (sac != null) {
				result.setData(sac);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
			} else {

				sac = new StudentAssignedCourse();

				OfflineVideoCourse offlineVideoCourse = offlineVideoCourseRepository
						.findByIdOfflineVideoCourse(idOfflineCourse);

				if (offlineVideoCourse == null)
					throw new NullPointerException("Invalid Offline Course");
				Subject subject =
				 subjectRepository.findByIdSubject(offlineVideoCourse.getIdSubject());
				
				if (subject == null)
					throw new NullPointerException("Invalid subject");
				
				SubjectChapter subjectChapter = subjectChapterRepository
						.findByIdSubjectChapter(offlineVideoCourse.getIdSubjectChapter());
				if (subjectChapter == null)
					throw new NullPointerException("Invalid subjectChapter");

				sac.setIdStudentSubscription(idStudentSubcription);
				sac.setLastAccessedDate(null);
				sac.setCompleteFlag(false);
				sac.setVideoCoverageDuration(0);
				sac.setPctComplete("0");
				sac.setSubjectName(subject.getSubjectName());
				sac.setChapterName(subjectChapter.getChapterName());
				BeanUtils.copyProperties(offlineVideoCourse, sac);
				
				result.setData(studentAssignedCourseRepository.save(sac));
				result.setStatusCode(HttpStatus.CREATED.value());
				result.setMessage("Request Sucessfull");
			}
		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}
		return result;
	}

	@Override
	public Document<VideoCipherOTP> createCourseVideoOtp(String videoId) {

		Document<VideoCipherOTP> result = new Document<VideoCipherOTP>();
		VideoCipherOTP vco = new VideoCipherOTP();
		
		try {
			
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 userPrincipal = (UserPrincipal) authentication.getPrincipal();
				}
			 
			String body = "{\"ttl\":21600,\"userId\":\"" + userPrincipal.getUserSurId() + "\"}";// set video expiration for 6 hours
		    vco = videoCipherConfiguration.getOTP(videoId, body);
		    
		    OfflineVideoCourse offlineVideoCourse =offlineVideoCourseRepository.findFirstByVideoEnLink(videoId);
		    
		    String s3Path=null;
		    
		    if(offlineVideoCourse.getS3Path()!=null) {
		    	s3Path=this.generateSignedUrl(offlineVideoCourse.getS3Path());
		    }
		    
		    vco.setS3Path(s3Path);
	
			result.setData(vco);
			result.setStatusCode(HttpStatus.CREATED.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<StudentDownloadVideoInfoDTO> getStudentVideoDataInfo(Long idStudentAssignedCourse) {
		
		
		Document<StudentDownloadVideoInfoDTO> result = new Document<>();
		StudentDownloadVideoInfoDTO sdvinfoDto = new StudentDownloadVideoInfoDTO();
		
		try {
			
			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				
			   user = (UserPrincipal) authentication.getPrincipal();
			   
			}
			
			if (user == null) throw new AppException("Invalid User");
			
			
			StudentAssignedCourse sac = studentAssignedCourseRepository.findByIdStudentAssignedCourse(idStudentAssignedCourse);
			
			
			if (sac == null)
				throw new NullPointerException("Invalid Student Assigned Course.");
			
			OfflineVideoCourse ovc  = offlineVideoCourseRepository.findByIdOfflineVideoCourse(sac.getIdOfflineVideoCourse());
			
			if (ovc == null)
				throw new NullPointerException("Invalid Offline Video Course.");
			
			sdvinfoDto.setAnswer(ovc.getAnswer());
			sdvinfoDto.setChapterName(sac.getChapterName());
			sdvinfoDto.setCompleteFlag(sac.getCompleteFlag());
			sdvinfoDto.setHeading(ovc.getHeading());
			sdvinfoDto.setQuestion(ovc.getQuestion());
			sdvinfoDto.setVideoEnLink(ovc.getVideoEnLink());
			if (user.getSecondaryLanguage() != null && user.getSecondaryLanguage() !="NA" )
			{
				Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
				
				if (lang != null) 
				{
					TopicLanguage tl = topicLanguageRepository.findByIdLanguageAndOfflineVideoCourse(lang.getIdLanguage(), ovc);
					sdvinfoDto.setIdLanguage(lang.getIdLanguage());
					sdvinfoDto.setLanguageName(lang.getLanguage());
					if(tl != null) 
					{
					
						sdvinfoDto.setSecondLangAnswer(tl.getAnswer());
						sdvinfoDto.setSecondLangHeading(tl.getHeading());
						sdvinfoDto.setSecondLangQuestion(tl.getQuestion());
						
						switch (lang.getLanguage().toLowerCase()) {

						case "kannada":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoKnLink());
							break;

						case "tamil":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpTM());
							break;
						case "telugu":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpTL());
							break;
						case "hindi":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpHN());
							break;

						case "malayalam":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpML());
							break;

						case "marathi":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpMH());
							break;
						// added case statement for future language link

						default: 
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideo1Link());
							
							}
						}
					}
				}
					
			sdvinfoDto.setIdOfflineVideoCourse(ovc.getIdOfflineVideoCourse());
			sdvinfoDto.setIdStudentAssignedCourse(sac.getIdStudentAssignedCourse());
			sdvinfoDto.setIdSubject(sac.getIdSubject());
			sdvinfoDto.setSubjectName(sac.getSubjectName());
			sdvinfoDto.setIdSubjectChapter(sac.getIdSubjectChapter());
			sdvinfoDto.setChapterName(sac.getChapterName());
			sdvinfoDto.setIdVlUser(user.getUserSurId());
			sdvinfoDto.setLastAccess(sac.getUpdatedAt());
			sdvinfoDto.setVideoCoverageDuration(sac.getVideoCoverageDuration());
			sdvinfoDto.setPctComplete(sac.getPctComplete());
			sdvinfoDto.setCompleteFlag(sac.getCompleteFlag());
			sdvinfoDto.setVideoDuration(ovc.getVideoDuration());
			sdvinfoDto.setIsSynced(Boolean.TRUE);
			result.setData(sdvinfoDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
		}

		catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			
		}
		
		return result;
	}

	

	@Override
	public Document<List<StudentDownloadVideoInfoDTO>> updateSACVideoDataInfo(List<SACVideoDurationSyncDTO> request) {
		
		
		
		Document<List<StudentDownloadVideoInfoDTO>> result = new Document<>();
		List<StudentDownloadVideoInfoDTO> response = new ArrayList<StudentDownloadVideoInfoDTO>();
		List<String> errorLog = new ArrayList<String>();
		
		try {
			
			
		if (request.isEmpty())  throw new AppException("Sync Data List Cannot be empty!.");
			

			UserPrincipal user = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
			   user = (UserPrincipal) authentication.getPrincipal();  
			}
			
			if (user == null) throw new AppException("Invalid User");
			
			for (SACVideoDurationSyncDTO sdvti: request ) 
			{
			
				StudentDownloadVideoInfoDTO sdvinfoDto = new StudentDownloadVideoInfoDTO();
				
			StudentAssignedCourse prevSac = studentAssignedCourseRepository.findByIdStudentAssignedCourse(sdvti.getIdStudentAssignedCourse());
			
			// check for null availability on StudentAssignedCourse , if particular not found throw the Exception.
			if (prevSac == null) {
				
				errorLog.add("Invalid Student Assigened Courses  for the id : "+ sdvti.getIdStudentAssignedCourse()+". \n");
				continue;

			}
				
			
         	OfflineVideoCourse ovc  = offlineVideoCourseRepository.findByIdOfflineVideoCourse(prevSac.getIdOfflineVideoCourse());
			
			if (ovc == null) 
			{
				errorLog.add("Invalid Offline video Courses  for the idSAC : "+ sdvti.getIdStudentAssignedCourse()+". \n");
				continue;
			}
			
			
			
			StudentAssignedCourse sac = null;

			int prevDuration = prevSac.getVideoCoverageDuration();
			int totalDuration = ovc.getVideoDuration();
			int currentDuration = Math.round(Float.valueOf(sdvti.getVideoCoverageDuration()));

			// check if the currentDuration of the video not more the total duration of the
			// video.
			if (currentDuration > totalDuration) 
			{
				errorLog.add("Video Duration Exceed the Limit!,  for the idSAC : "+ sdvti.getIdStudentAssignedCourse()+". \n");
				continue;
			}
				

			// check the video already completely streamed if not , this block will execute
			if (prevSac.getCompleteFlag() != true) {
				
				
                /**
                 * check the prevduration value to current user duration value 
                 * if user duration is greater ,then update the record.
                 */
				if (prevDuration < currentDuration) {

					prevSac.setVideoCoverageDuration(currentDuration);

					// providing some threshold value for streaming video , this will be useful for
					// easier analytics.
					if (totalDuration - currentDuration <= 5)

					{
						prevSac.setVideoCoverageDuration(totalDuration);
						prevSac.setCompleteFlag(Boolean.TRUE);
						prevSac.setPctComplete("100");
					} else {
						// calculate the percentage and update the SAC
						double meanValue = (double) currentDuration / totalDuration;
						meanValue *= 100;
						String percent = Double.toString(Math.round(meanValue));
						prevSac.setPctComplete(percent);
					}

				}

				sac = studentAssignedCourseRepository.save(prevSac);
				

			}

			else {
				 /*This block will be executed when complete flag is true , user can stream
				 video as many time . coverage duration will get updated dynamically based on
				 user streaming.*/
				prevSac.setVideoCoverageDuration(currentDuration);
				sac = studentAssignedCourseRepository.save(prevSac);

			}
			
			
			sdvinfoDto.setAnswer(ovc.getAnswer());
			sdvinfoDto.setChapterName(sac.getChapterName());
			sdvinfoDto.setCompleteFlag(sac.getCompleteFlag());
			sdvinfoDto.setHeading(ovc.getHeading());
			sdvinfoDto.setQuestion(ovc.getQuestion());
			sdvinfoDto.setVideoEnLink(ovc.getVideoEnLink());
			if (user.getSecondaryLanguage() != null && user.getSecondaryLanguage() !="NA" )
			{
				Language lang = languageRepository.findByLanguage(user.getSecondaryLanguage());
				
				if (lang != null) 
				{
					TopicLanguage tl = topicLanguageRepository.findByIdLanguageAndOfflineVideoCourse(lang.getIdLanguage(), ovc);
					sdvinfoDto.setIdLanguage(lang.getIdLanguage());
					sdvinfoDto.setLanguageName(lang.getLanguage());
					if(tl != null) 
					{
					
						sdvinfoDto.setSecondLangAnswer(tl.getAnswer());
						sdvinfoDto.setSecondLangHeading(tl.getHeading());
						sdvinfoDto.setSecondLangQuestion(tl.getQuestion());
						
						switch (lang.getLanguage().toLowerCase()) {

						case "kannada":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoKnLink());
							break;

						case "tamil":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpTM());
							break;
						case "telugu":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpTL());
							break;
						case "hindi":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpHN());
							break;

						case "malayalam":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpML());
							break;

						case "marathi":
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideoOtpMH());
							break;
						// added case statement for future language link

						default: 
							sdvinfoDto.setVideoSecondLangLink(ovc.getVideo1Link());
							
							}
						}
					}
				}
					
			
			sdvinfoDto.setIdOfflineVideoCourse(ovc.getIdOfflineVideoCourse());
			sdvinfoDto.setIdStudentAssignedCourse(sac.getIdStudentAssignedCourse());
			sdvinfoDto.setIdSubject(sac.getIdSubject());
			sdvinfoDto.setSubjectName(sac.getSubjectName());
			sdvinfoDto.setIdSubjectChapter(sac.getIdSubjectChapter());
			sdvinfoDto.setChapterName(sac.getChapterName());
			sdvinfoDto.setIdVlUser(user.getUserSurId());
			sdvinfoDto.setLastAccess(sac.getUpdatedAt());
			sdvinfoDto.setVideoCoverageDuration(sac.getVideoCoverageDuration());
			sdvinfoDto.setPctComplete(sac.getPctComplete());
			sdvinfoDto.setCompleteFlag(sac.getCompleteFlag());
			sdvinfoDto.setVideoDuration(ovc.getVideoDuration());
			sdvinfoDto.setIsSynced(Boolean.TRUE);
            
			response.add(sdvinfoDto);
			}
			
			
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			if (errorLog.isEmpty()) 
			{
				result.setMessage("Video Data Synced Sucessfully");
			}
			else {
				
				String error =  errorLog.stream().collect(Collectors.joining(""));
				result.setMessage(error);
			}
			
			
		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}

		return result;
	}
	

	private boolean isDummyAws() {
		return "dummy".equalsIgnoreCase(awsAccesskeyId) || "dummy".equalsIgnoreCase(awsSecretAccess);
	}

	@Override
	public Document<List<VideoStreamingLog>> getAcademicVideoStreamingLogs() {
		
		Document<List<VideoStreamingLog>> result = new Document<>();
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
			
			if (isDummyAws()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Successful (Dummy AWS)");
				return result;
			}

			List<String>  compositeKeys = dynamoDbHelper.getCompositeKeyForVSL(userPrincipal.getUserSurId(), 5L);

			List<VideoStreamingLog> academicVsl = videoStreamingLogRepository.getVideoStreamingListByPartitionKeyIn(compositeKeys);
			
			if (academicVsl.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No video logs found");
				return result;
			}
			
			//sort based on last updated time  
			academicVsl.sort(Comparator.comparing(VideoStreamingLog::getLastUpdatedTime).reversed());
			result.setData(academicVsl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@Override
	public Document<List<VideoStreamingLog>> getExtraCurricularVideoStreamingLogs() {
		Document<List<VideoStreamingLog>> result = new Document<>();
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
			
			if (isDummyAws()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Successful (Dummy AWS)");
				return result;
			}

			List<String>  compositeKeys = dynamoDbHelper.getCompositeKeyForVSL(userPrincipal.getUserSurId(), 5L);

			List<VideoStreamingLog> extraCurricularVsl = videoStreamingLogRepository.getVideoStreamingListByPartitionKeyIn(compositeKeys);
			
			if (extraCurricularVsl.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No video logs found");
				return result;
			}
			//sort based on last updated time  
			extraCurricularVsl.sort(Comparator.comparing(VideoStreamingLog::getLastUpdatedTime).reversed());
			result.setData(extraCurricularVsl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@Override
	@Cacheable(value = "studentSummary")
	public Document<List<Card>> getStudentSummery(Long idVlUser) {
		Document<List<Card>> result = new Document<>();

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");

			if (!userPrincipal.getUserSurId().equals(idVlUser))
				throw new AppException("Pass valid user id ");

			List<Card> cards = new ArrayList<>();

			TotalVideoWatchDTO totalVideoWatchDTO = studentAssignedCourseRepository
					.getTotalVideoProgress(userPrincipal.getUserSurId());
			List<SubjectVideoWatchDTO> subjectVideoWatchLsit = studentAssignedCourseRepository
					.getSubjectWatchDetails(userPrincipal.getUserSurId());
			QuizStatisticsDTO quizStatisticsDTO = studentChapterQuizRepository
					.getQuizStatistics(userPrincipal.getUserSurId());
			List<SubjectQuizStatisticsDTO> subjectQuizStatisticsDTOList = studentChapterQuizRepository
					.getSubjectQuizStatistics(userPrincipal.getUserSurId());

			if (totalVideoWatchDTO != null) {
				Card card1 = new Card();

				card1.setCardType(CARD.Simple.name());
				card1.setColor("#bf8ab4");
				card1.setImgUrl(
						"https://vlearning-prod.s3.ap-south-1.amazonaws.com/year-end-wrap-up/watch-hours-1.webp");
				card1.setFormattedText(
						"You have watched total <b style=\"font-size:40px;\">" + totalVideoWatchDTO.getTotalVideoCount()
								+ "</b> videos and spent <b style=\"font-size:24px;\">"
								+ totalVideoWatchDTO.getTotalWatchHours() + "</b> on the vistas app");
				card1.setText("You have watched a total " + totalVideoWatchDTO.getTotalVideoCount()
						+ " videos and spent " + totalVideoWatchDTO.getTotalWatchHours() + "on the vistas app");

				if (totalVideoWatchDTO.getTotalVideoCount() > 0)
					cards.add(card1);
			}

			if (subjectVideoWatchLsit.size() > 0) {
				Card card2 = new Card();

				card2.setCardType(CARD.List.name());
				card2.setColor("#324d91");
				card2.setImgUrl(
						"https://vlearning-prod.s3.ap-south-1.amazonaws.com/year-end-wrap-up/watch-hours-3.webp");
				List<List<Object>> listData1 = new ArrayList<>();

				listData1.add(List.of("Subject", "Count", "Hours"));

				for (SubjectVideoWatchDTO subject : subjectVideoWatchLsit) {

					listData1.add(List.of(subject.getSubjectName(), subject.getTotalWatchCount(),
							subject.getTotalWatchHours()));

				}

				card2.setListData(listData1);

				if (subjectVideoWatchLsit.size() > 0)
					cards.add(card2);
			}

			if (subjectQuizStatisticsDTOList.size() > 0) {

				Card card3 = new Card();

				double avgScore = getAvgScore(subjectQuizStatisticsDTOList);

				if (avgScore > 35) {

					quizStatisticsDTO.setAverageQuizScore(avgScore);

					card3.setCardType(CARD.Simple.name());
					card3.setColor("#10113A");
					card3.setImgUrl("https://vlearning-prod.s3.ap-south-1.amazonaws.com/year-end-wrap-up/quiz-1.webp");
					card3.setFormattedText("You have completed a total of <b style=\"font-size:24px;\">"
							+ quizStatisticsDTO.getTotalQuizCount()
							+ "</b> quizzes, with an average score of  <b style=\"font-size:24px;\">"
							+ quizStatisticsDTO.getAverageQuizScore() + "%</b>.");
					card3.setText("You have completed a total of " + quizStatisticsDTO.getTotalQuizCount()
							+ " quizzes, with an average score of " + quizStatisticsDTO.getAverageQuizScore() + "%.");

					cards.add(card3);

					Card card4 = new Card();

					card4.setCardType(CARD.List.name());
					card4.setColor("#348ab7");
					card4.setImgUrl("https://vlearning-prod.s3.ap-south-1.amazonaws.com/year-end-wrap-up/quiz-2.webp");

					List<List<Object>> listData2 = new ArrayList<>();

					listData2.add(List.of("Subject", "Quiz Count", "Score"));

					for (SubjectQuizStatisticsDTO subject : subjectQuizStatisticsDTOList) {

						if (subject.getTotalQuizCount() > 0) {
							listData2.add(
									List.of(subject.getSubjectName(), subject.getTotalQuizCount(), subject.getScore()));
						}
					}
					card4.setListData(listData2);

					if (subjectQuizStatisticsDTOList.size() > 0)
						cards.add(card4);
				}

			}

			if (cards.size() == 0) {

				Card card5 = new Card();

				card5.setCardType(CARD.Simple.name());
				card5.setColor("#3e4241");
				card5.setImgUrl("https://vlearning-prod.s3.ap-south-1.amazonaws.com/year-end-wrap-up/main.png");
				card5.setFormattedText("Hello <b>" + userPrincipal.getFirstName()
						+ "</b>,  We are unable to generate your year-end recap at the moment due to insufficient data. Please continue using the application to build valuable insights for the future.");
				card5.setText("Hello " + userPrincipal.getFirstName()
						+ ",  We are unable to generate your year-end recap at the moment due to insufficient data. Please continue using the application to build valuable insights for the future.");

				cards.add(card5);
				result.setData(cards);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");
				return result;
			}

			result.setData(cards);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;

	}

	@Override
	public Document<List<StudentSummaryDTO>> getAllStudentSummery() {
		
		Document<List<StudentSummaryDTO>> result = new Document<>();
		try {

		   List<Long> userList=	userRepository.findUserIds(1l, 1l, 1l);
		   
		   List<StudentSummaryDTO> list = new ArrayList<>();
		   
		   for(Long    user:userList) {
			   
			   StudentSummaryDTO studentSummaryDTO  = getAdminStudentSummary(user);
			   list.add(studentSummaryDTO);
		   }
			
			
			result.setData(list);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
		
		
	}
	
	
	public StudentSummaryDTO getAdminStudentSummary(Long UserId) {
		
	StudentSummaryDTO	studentSummaryDTO =new StudentSummaryDTO();
	
	
		
	User user=	userRepository.findByUserSurId(UserId);
	
	studentSummaryDTO.setFirstNmae(user.getFirstName());
	
	
	TotalVideoWatchDTO totalVideoWatchDTO = studentAssignedCourseRepository
			.getTotalVideoProgress(UserId);

	QuizStatisticsDTO quizStatisticsDTO = studentChapterQuizRepository
			.getQuizStatistics(UserId);

	
	
	studentSummaryDTO.setWatchedVideoCount(totalVideoWatchDTO.getTotalVideoCount());
	studentSummaryDTO.setTotalAttemptCount(quizStatisticsDTO.getTotalQuizCount());
	
   
    return studentSummaryDTO;

	}

	public double getAvgScore(List<SubjectQuizStatisticsDTO> subjectQuizStatisticsDTOList) {
		
		 subjectQuizStatisticsDTOList.remove(0);
		
		 double averageScore = subjectQuizStatisticsDTOList.stream()
		            .mapToDouble(row -> Double.parseDouble(row.getScore())) // Extract and parse the score
		            .average() // Calculate the average
		            .orElse(0.0); // Default value if the list is empty	
		 return averageScore;
	}
	
	public String generateSignedUrl(String objectKey) {
		// Create S3 client
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccesskeyId, awsSecretAccess);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("ap-south-1")
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

		// Set expiration (6 hours)
		Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6);

		String videosBucket = "vlearning-videos";

		// Generate signed URL
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(videosBucket, objectKey)
				.withMethod(HttpMethod.GET).withExpiration(expiration);

		URL url = s3Client.generatePresignedUrl(request);
		return url.toString();
	}
	
	
}
