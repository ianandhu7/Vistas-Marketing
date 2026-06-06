package co.vistafoundation.vlearning.offlinecourse.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.google.gson.Gson;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.dto.CreateVideoRequestDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.DetailsFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.FolderListResponseDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.FreeOfflineVideoCourseResponseDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideoCourseWithRatingDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideoCourseWithRatingListDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideosReportDto;
import co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.RatingFilterInputDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoCourseRatingDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoDetailsDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoLinkDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.VideoRatingInputDTO;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourseRating;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.model.TopicLanguage;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRatingRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.TopicLanguageRepository;
import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subject.utils.SubjectChapterUtility;
import co.vistafoundation.vlearning.subscription.dto.ChapterBasedVideoDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentVideoStreamingDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

@Service
public class OfflineCourseServiceImpl implements OfflineCourseService {

	@Autowired
	public OfflineCourseRepository offlineCourseRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	StudentAssignedCourseRepository studentAssignedCourseRepository;

	@Autowired
	VideoCipherConfiguration videoCipherConfiguration;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	TopicLanguageRepository topicLanguageRepository;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	SubjectChapterUtility subjectChapterUtility;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	UserService userService;

	@Autowired
	OfflineVideoCourseRatingRepository offlineVideoCourseRatingRepository;
	
	@Autowired
	ImageFileUploadService imageUploadService;
	
	@Autowired
	private   AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucket}")
	private  String bucketName;
	
	@Value("${videocipher.secret}")
	private String videocipherSecret;

	private static final Logger logger = LoggerFactory.getLogger(OfflineCourseServiceImpl.class);

	public List<ProductSampleVideo> getAllSampleVideos() {
		return (offlineCourseRepository.findAllSampleVideos());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document createOfflineVideoRecord(CreateVideoRequestDTO request) {

		Document result = new Document();

		try {

			Product prod = productRepository.findByIdProductAndActiveFlag(request.getIdProduct(), Boolean.TRUE);
	
			if (prod == null)
				throw new NullPointerException("Invalid Product Id");

			VideoCipherOTP vco=null;
			
			String status="";
			
			String vdocipherVideoId=null;
			
			HashMap<String, Object> vcm=null;
			
			String body = "{\"ttl\":21600}"; // set video otp expiration for 6 hours
			
			if (request.getVideoUrl() != null) {
				
				if(request.getVideoUrl()==null)
	               	 throw new AppException("Please upload a video.");
					
	            if(request.getFolderId()==null)
	                 throw new AppException("Please select a folder.");

				String url = request.getVideoUrl();
				String fileName = url.substring(url.lastIndexOf("/") + 1);
				
				status="Processing";

				vdocipherVideoId = uploadVideoToVdocipher(request.getVideoUrl(), request.getFolderId(), fileName);

				vco = videoCipherConfiguration.getOTP(vdocipherVideoId, body);

				vcm = videoCipherConfiguration.getVideoMetaData(vdocipherVideoId);

			} else {

				status="Ready";
				
				vco = videoCipherConfiguration.getOTP(request.getVideoEnLink(), body);

				vcm = videoCipherConfiguration.getVideoMetaData(request.getVideoEnLink());

				vdocipherVideoId=request.getVideoEnLink();
			}
			
			if (vcm == null || vco == null)
				throw new AppException("Invalid VideoId Provided.");
			
			OfflineVideoCourse ovc = new OfflineVideoCourse();
			ovc.setIdProduct(prod.getIdProduct());
			ovc.setIdSubject(request.getIdSubject());
			ovc.setIdSubjectChapter(request.getIdSubjectChapter());
			ovc.setTopic(request.getTopic());
			
			ovc.setVideoDescription(request.getDescription());
			// assign all video records
			
			ovc.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
			ovc.setVideoSeqNumber(request.getVideoSeqNumber());
			
			
			ovc.setVideoEnLink(vdocipherVideoId);
			ovc.setVideoOtp(vco.getOtp());
					
			if(request.getVideoUrl()!=null) {
				ovc.setVideoDuration(0);
				ovc.setStatus(status);
			}
			
			if(request.getVideoUrl()==null&&request.getVideoEnLink()!=null) {
			ovc.setVideoDuration((int) vcm.get("duration"));
			ovc.setVideoPoster1Location(
					((HashMap<String, Object>) ((List) vcm.get("posters")).get(0)).get("url").toString());
			ovc.setStatus(status);
			}
			
	//		ovc.setVideoPoster1Location(
	//				((HashMap<String, Object>) ((List) vcm.get("posters")).get(0)).get("url").toString());
			ovc.setHeading(request.getHeading());
			ovc.setQuestion(request.getQuestion());
			ovc.setAnswer(request.getAnswer());
			ovc.setPdfURL(request.getPdfUrl());
			ovc.setTeacherNoteURL(request.getTeacherNoteUrl());
			
			
			

			List<TopicLanguage> tlList = new ArrayList<>();

			if (!request.getVideoLinks().isEmpty()) {

				for (VideoLinkDTO entry : request.getVideoLinks()) {

					vco = null;

					TopicLanguage tl = new TopicLanguage();

					Language lang = languageRepository.findByLanguage(entry.getLanguage());

					if (lang == null)
						throw new NullPointerException("Language Not Found!");
					tlList.add(tl);
					tl.setHeading(entry.getHeading());
					tl.setAnswer(entry.getAnswer());
					tl.setIdLanguage(lang.getIdLanguage());
					tl.setOfflineVideoCourse(ovc);
					tl.setQuestion(entry.getQuestion());
					tl.setPdfURL(entry.getPdfURL());

					switch (lang.getLanguage().toLowerCase()) {

					case "kannada":
						ovc.setVideoKnLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpKN(vco.getOtp());
						break;

					case "tamil":
						ovc.setVideoTmLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpTM(vco.getOtp());
						break;
					case "telugu":
						ovc.setVideoTlLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpTL(vco.getOtp());
						break;
					case "hindi":
						ovc.setVideoHnLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpHN(vco.getOtp());
						break;

					case "malayalam":
						ovc.setVideoMlLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpML(vco.getOtp());
						break;

					case "marathi":
						ovc.setVideoMhLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpMH(vco.getOtp());
						break;
					// added case statement for future language link

					default: // do nothing;
						if (ovc.getVideo1Link() == null) {
							ovc.setVideo1Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp1Link(vco.getOtp());
						} else if (ovc.getVideo2Link() == null) {
							ovc.setVideo2Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp2Link(vco.getOtp());
						} else if (ovc.getVideo3Link() == null) {
							ovc.setVideo3Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp3Link(vco.getOtp());
						} else if (ovc.getVideo4Link() == null) {
							ovc.setVideo4Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp4Link(vco.getOtp());
						} else {
							ovc.setVideo5Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp5Link(vco.getOtp());
						}
					}
				}
			}

			OfflineVideoCourse temp = offlineVideoCourseRepository.save(ovc);
			topicLanguageRepository.saveAll(tlList);

			Product pro = productRepository.findByIdProductAndActiveFlag(temp.getIdProduct(), Boolean.TRUE);
			if (pro == null)
				throw new NullPointerException("Product Not Found!");

			pro.setTotalVideoCount(pro.getTotalVideoCount() + 1);
			productRepository.save(pro);
			result.setData(temp);

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
	public Document getTop10VideoRecords() {
		Document result = new Document();
		try {
			List<OfflineVideoCourse> recentList = offlineVideoCourseRepository
					.findTop10ByOrderByIdOfflineVideoCourseDesc();
			if (recentList.isEmpty())
				throw new NullPointerException("No Video Records found!");

			result.setData(recentList);
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void getVideoRecordByIdProduct(StudentSubscription studentSubscription) {
		try {
			if (studentSubscription.getIdBatch() != null) {
				// do nothing, update batch details happening at make payment api
			}
			// for single product
			else if (studentSubscription.getIdProduct() != null) {
				List<OfflineVideoCourse> offlineVideoCourseList = offlineVideoCourseRepository
						.findByIdProduct(studentSubscription.getIdProduct());

				if (!offlineVideoCourseList.isEmpty()) {
					for (OfflineVideoCourse offlineVideoCourse : offlineVideoCourseList) {
						if (offlineVideoCourse != null) {
							Subject subject = new Subject();
							subject = subjectRepository.findByIdSubject(offlineVideoCourse.getIdSubject());

							if (subject == null)
								throw new NullPointerException("Invalid Idsubject");

							SubjectChapter subjectChapter = new SubjectChapter();
							subjectChapter = subjectChapterRepository
									.findByIdSubjectChapter(offlineVideoCourse.getIdSubjectChapter());
							if (subjectChapter == null)
								throw new NullPointerException("Invalid IdsubjectChapter");

							StudentAssignedCourse studentAssignedCourse = new StudentAssignedCourse();
							studentAssignedCourse
									.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
							studentAssignedCourse.setLastAccessedDate(null);
							studentAssignedCourse.setCompleteFlag(false);
							studentAssignedCourse.setVideoCoverageDuration(0);
							studentAssignedCourse.setPctComplete("0");
							studentAssignedCourse.setSubjectName(subject.getSubjectName());
							studentAssignedCourse.setChapterName(subjectChapter.getChapterName());
							BeanUtils.copyProperties(offlineVideoCourse, studentAssignedCourse);
							studentAssignedCourse = studentAssignedCourseRepository.save(studentAssignedCourse);
						}
					}
				}
			}
			// for product group
			else {
				List<Long> poductIdList = productRepository.findAllIdProduct(studentSubscription.getIdProductGroup());
				for (Long productId : poductIdList) {

					List<OfflineVideoCourse> offlineVideoCourseList = offlineVideoCourseRepository
							.findByIdProduct(productId);
					if (!offlineVideoCourseList.isEmpty()) {
						for (OfflineVideoCourse offlineVideoCourse : offlineVideoCourseList) {

							if (offlineVideoCourse != null) {
								Subject subject = new Subject();
								subject = subjectRepository.findByIdSubject(offlineVideoCourse.getIdSubject());

								if (subject == null)
									throw new NullPointerException("Invalid Idsubject");

								SubjectChapter subjectChapter = new SubjectChapter();
								subjectChapter = subjectChapterRepository
										.findByIdSubjectChapter(offlineVideoCourse.getIdSubjectChapter());

								if (subjectChapter == null)
									throw new NullPointerException("Invalid IdsubjectChapter");

								StudentAssignedCourse studentAssignedCourse = new StudentAssignedCourse();
								studentAssignedCourse
										.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
								studentAssignedCourse.setLastAccessedDate(null);
								studentAssignedCourse.setCompleteFlag(false);
								studentAssignedCourse.setVideoCoverageDuration(0);
								studentAssignedCourse.setPctComplete("0");
								studentAssignedCourse.setSubjectName(subject.getSubjectName());
								studentAssignedCourse.setChapterName(subjectChapter.getChapterName());
								BeanUtils.copyProperties(offlineVideoCourse, studentAssignedCourse);
								studentAssignedCourse = studentAssignedCourseRepository.save(studentAssignedCourse);
							}
						}
					}
				}
			}
		} catch (Exception exp) {
			System.err.println(exp.getLocalizedMessage() + "" + "for id student Subscription: "
					+ studentSubscription.getIdStudentSubscription());
			// should log the trace
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getVideoDataByVideoId(String videoId) {
		Document result = new Document();
		try {
			// request body to make video streaming license available for only 5 minute.
			String reqBody = "{\"ttl\":300}";
			VideoCipherOTP vco = videoCipherConfiguration.getOTP(videoId, reqBody);
			if (vco == null)
				throw new AppException("Invalid VideoId Provided.");

			result.setData(vco);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getOfflinevideoCoursesByProdIdSubId(Long idProduct, Long idSubjectChapter) {
		Document<OfflineVideoCourseWithRatingListDTO> result = new Document();
		try {

			List<OfflineVideoCourseWithRatingDTO> list = new ArrayList<>();

			List<OfflineVideoCourse> offlineVideoCourses = offlineVideoCourseRepository
					.findByIdProductAndIdSubjectChapterOrderByVideoSeqNumberAsc(idProduct, idSubjectChapter);
			if (offlineVideoCourses.isEmpty()) {
				throw new AppException("There are no video courses available");
			}
			long count = 0;
			for (OfflineVideoCourse offlineVideoCourse : offlineVideoCourses) {

				OfflineVideoCourseWithRatingDTO dto = new OfflineVideoCourseWithRatingDTO();

				Double averageRating = offlineVideoCourseRatingRepository
						.calculateAverageRating(offlineVideoCourse.getIdOfflineVideoCourse());
				Long totalRating = offlineVideoCourseRatingRepository
						.totalRatingForVideo(offlineVideoCourse.getIdOfflineVideoCourse());
				dto.setOfflineCourse(offlineVideoCourse);
				dto.setRating(averageRating);
				dto.setTotalRating(totalRating);
				count += totalRating;
				list.add(dto);

			}

			OfflineVideoCourseWithRatingListDTO finalDTO = new OfflineVideoCourseWithRatingListDTO();
			finalDTO.setOfflineVideoCourseWithRating(list);
			finalDTO.setTotalRatings(count);

			result.setData(finalDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successful");
		} catch (Exception exp) {
			exp.printStackTrace();

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document updateVideoCourse(CreateVideoRequestDTO request, Long idOfflineVideoCourse) {
		Document<OfflineVideoCourse> result = new Document<>();

		try {

			Product prod = productRepository.findByIdProductAndActiveFlag(request.getIdProduct(), Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("Invalid Product Id");

			OfflineVideoCourse ovc = offlineVideoCourseRepository.findByIdOfflineVideoCourse(idOfflineVideoCourse);

			if (ovc == null)
				throw new NullPointerException("Invalid Offline Video Course Id");
			
			
	        VideoCipherOTP vco=null;
	        
	        String status="";
			
			String vdocipherVideoId=null;
			
			HashMap<String, Object> vcm=null;
			
			String body = "{\"ttl\":21600}"; // set video otp expiration for 6 hours
			
			if (request.getVideoUrl() != null) {
				
				if(request.getVideoUrl()==null)
               	 throw new AppException("Please upload a video.");
				
                if(request.getFolderId()==null)
                	 throw new AppException("Please select a folder.");
                
                status="Processing";
				String url = request.getVideoUrl();
				String fileName = url.substring(url.lastIndexOf("/") + 1);

				vdocipherVideoId = uploadVideoToVdocipher(request.getVideoUrl(), request.getFolderId(), fileName);

				vco = videoCipherConfiguration.getOTP(vdocipherVideoId, body);

				vcm = videoCipherConfiguration.getVideoMetaData(vdocipherVideoId);


			} else {

				status="Ready";
				vco = videoCipherConfiguration.getOTP(request.getVideoEnLink(), body);

				vcm = videoCipherConfiguration.getVideoMetaData(request.getVideoEnLink());

				vdocipherVideoId=request.getVideoEnLink();
			}
			
			
			if (vcm == null || vco == null)
				throw new AppException("Invalid VideoId Provided.");

			ovc.setIdProduct(prod.getIdProduct());
			ovc.setIdSubject(request.getIdSubject());
			ovc.setIdSubjectChapter(request.getIdSubjectChapter());
			ovc.setTopic(request.getTopic());
			ovc.setVideoEnLink(vdocipherVideoId);
			ovc.setVideoDescription(request.getDescription());
			ovc.setVideoOtp(vco.getOtp());
			ovc.setVideoSeqNumber(request.getVideoSeqNumber());
//			ovc.setVideoDuration(vcm.getDuration());
//			ovc.setVideoPoster1Location(vcm.getPosters().get(0).getUrl());
			
			if(request.getVideoUrl()!=null) {
				ovc.setVideoDuration(0);
				ovc.setStatus(status);
			}
			
			if(request.getVideoUrl()==null&&request.getVideoEnLink()!=null) {
			ovc.setVideoDuration((int) vcm.get("duration"));
			ovc.setVideoPoster1Location(
					((HashMap<String, Object>) ((List) vcm.get("posters")).get(0)).get("url").toString());
			ovc.setStatus(status);
			}
			// clearing previous record
			ovc.setVideoKnLink(null);
			ovc.setVideoTmLink(null);
			ovc.setVideoTlLink(null);
			ovc.setVideoHnLink(null);
			ovc.setVideoMlLink(null);
			ovc.setVideoMhLink(null);
			ovc.setVideo1Link(null);
			ovc.setVideo2Link(null);
			ovc.setVideo3Link(null);
			ovc.setVideo4Link(null);
			ovc.setVideo5Link(null);
			// assign all video records
			ovc.setHeading(request.getHeading());
			ovc.setQuestion(request.getQuestion());
			ovc.setAnswer(request.getAnswer());
			ovc.setPdfURL(request.getPdfUrl());
			
			if(ovc.getTeacherNoteURL()!=null&&request.getTeacherNoteUrl()==null) { 
			String key = ovc.getTeacherNoteURL().substring(ovc.getTeacherNoteURL().indexOf("teacher_note/"));
			deleteFileFromS3(bucketName,key);
			System.out.println(key);	
			}
			ovc.setTeacherNoteURL(request.getTeacherNoteUrl());
			

			List<TopicLanguage> tlList = topicLanguageRepository
					.findByOfflineVideoCourse_idOfflineVideoCourse(ovc.getIdOfflineVideoCourse());

			if (!tlList.isEmpty()) {
				topicLanguageRepository.deleteAll(tlList);
			}

			tlList = new ArrayList<>();

			if (!request.getVideoLinks().isEmpty()) {

				for (VideoLinkDTO entry : request.getVideoLinks()) {

					TopicLanguage tl = new TopicLanguage();

					vco = null;

					Language lang = languageRepository.findByLanguage(entry.getLanguage());

					if (lang == null)
						throw new NullPointerException("Language Not Found!");
					tlList.add(tl);
					tl.setHeading(entry.getHeading());
					tl.setAnswer(entry.getAnswer());
					tl.setIdLanguage(lang.getIdLanguage());
					tl.setOfflineVideoCourse(ovc);
					tl.setQuestion(entry.getQuestion());
					tl.setPdfURL(entry.getPdfURL());
					switch (lang.getLanguage().toLowerCase()) {

					case "kannada":
						ovc.setVideoKnLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpKN(vco.getOtp());
						break;

					case "tamil":
						ovc.setVideoTmLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpTM(vco.getOtp());
						break;
					case "telugu":
						ovc.setVideoTlLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpTL(vco.getOtp());
						break;
					case "hindi":
						ovc.setVideoHnLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpHN(vco.getOtp());
						break;

					case "malayalam":
						ovc.setVideoMlLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpML(vco.getOtp());
						break;

					case "marathi":
						ovc.setVideoMhLink(entry.getLink());
						vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
						ovc.setVideoOtpMH(vco.getOtp());
						break;
					// added case statement for future language link

					default: // do nothing;
						if (ovc.getVideo1Link() == null) {
							ovc.setVideo1Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp1Link(vco.getOtp());
						} else if (ovc.getVideo2Link() == null) {
							ovc.setVideo2Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp2Link(vco.getOtp());
						} else if (ovc.getVideo3Link() == null) {
							ovc.setVideo3Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp3Link(vco.getOtp());
						} else if (ovc.getVideo4Link() == null) {
							ovc.setVideo4Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp4Link(vco.getOtp());
						} else {
							ovc.setVideo5Link(entry.getLink());
							vco = videoCipherConfiguration.getOTP(entry.getLink(), body);
							ovc.setVideoOtp5Link(vco.getOtp());
						}
					}
				}
			}

			ovc.setVideoTheme("9ae8bbe8dd964ddc9bdb932cca1cb59a");
			ovc.setVideoSeqNumber(request.getVideoSeqNumber());
			ovc.setCreatedAt(ovc.getCreatedAt());
			ovc.setCreatedBy(ovc.getCreatedBy());

			OfflineVideoCourse temp = offlineVideoCourseRepository.save(ovc);
			topicLanguageRepository.saveAll(tlList);

			result.setData(temp);
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
	public Document<CreateVideoRequestDTO> getOfflineVideoCourseData(Long idOfflineVideoCourse) {
		Document<CreateVideoRequestDTO> result = new Document<CreateVideoRequestDTO>();
		try {

			CreateVideoRequestDTO cvr = new CreateVideoRequestDTO();
			OfflineVideoCourse offlineVideoCourses = offlineVideoCourseRepository
					.findByIdOfflineVideoCourse(idOfflineVideoCourse);

			if (offlineVideoCourses == null)
				throw new NullPointerException("Invalid idOfflineVideoCourse.");

			cvr.setVideoSeqNumber(offlineVideoCourses.getVideoSeqNumber());
			cvr.setDescription(offlineVideoCourses.getVideoDescription());
			Product product = productRepository.findByIdProductAndActiveFlag(offlineVideoCourses.getIdProduct(),
					Boolean.TRUE);
			if (product == null)
				throw new NullPointerException("Invalid Idproduct");

			cvr.setIdClassStandard(product.getIdClassStandard());
			cvr.setIdSyllabus(product.getIdSyllabus());
			cvr.setIdSubject(offlineVideoCourses.getIdSubject());
			cvr.setIdSubjectChapter(offlineVideoCourses.getIdSubjectChapter());
			cvr.setTopic(offlineVideoCourses.getTopic());
			cvr.setVideoEnLink(offlineVideoCourses.getVideoEnLink());
			cvr.setVideoSeqNumber(offlineVideoCourses.getVideoSeqNumber());
			cvr.setIdProduct(offlineVideoCourses.getIdProduct());
			cvr.setIdState(product.getIdState());
			cvr.setHeading(offlineVideoCourses.getHeading());
			cvr.setQuestion(offlineVideoCourses.getQuestion());
			cvr.setAnswer(offlineVideoCourses.getAnswer());
			cvr.setPdfUrl(offlineVideoCourses.getPdfURL());
			cvr.setTeacherNoteUrl(offlineVideoCourses.getTeacherNoteURL());
			Set<VideoLinkDTO> videoLinks = new HashSet<VideoLinkDTO>();

			if (offlineVideoCourses.getVideo1Link() != null) {
				VideoLinkDTO v1 = new VideoLinkDTO();
				v1.setLanguage("videoLink1");
				v1.setLink(offlineVideoCourses.getVideo1Link());
				videoLinks.add(v1);
			}

			if (offlineVideoCourses.getVideo2Link() != null) {
				VideoLinkDTO v2 = new VideoLinkDTO();
				v2.setLanguage("videoLink2");
				v2.setLink(offlineVideoCourses.getVideo2Link());
				videoLinks.add(v2);
			}

			if (offlineVideoCourses.getVideo3Link() != null) {
				VideoLinkDTO v3 = new VideoLinkDTO();
				v3.setLanguage("videoLink3");
				v3.setLink(offlineVideoCourses.getVideo3Link());
				videoLinks.add(v3);
			}

			if (offlineVideoCourses.getVideo4Link() != null) {
				VideoLinkDTO v4 = new VideoLinkDTO();
				v4.setLanguage("videoLink4");
				v4.setLink(offlineVideoCourses.getVideo4Link());
				videoLinks.add(v4);
			}
			if (offlineVideoCourses.getVideo5Link() != null) {
				VideoLinkDTO v5 = new VideoLinkDTO();
				v5.setLanguage("videoLink5");
				v5.setLink(offlineVideoCourses.getVideo5Link());
				videoLinks.add(v5);
			}

			if (offlineVideoCourses.getVideoHnLink() != null) {
				VideoLinkDTO vhn = new VideoLinkDTO();
				vhn.setLanguage("Hindi");
				vhn.setLink(offlineVideoCourses.getVideoHnLink());
				videoLinks.add(vhn);
			}

			if (offlineVideoCourses.getVideoKnLink() != null) {
				VideoLinkDTO vkn = new VideoLinkDTO();
				vkn.setLanguage("Kannada");
				vkn.setLink(offlineVideoCourses.getVideoKnLink());
				videoLinks.add(vkn);
			}

			if (offlineVideoCourses.getVideoMhLink() != null) {
				VideoLinkDTO vmh = new VideoLinkDTO();
				vmh.setLanguage("Marathi");
				vmh.setLink(offlineVideoCourses.getVideoMhLink());
				videoLinks.add(vmh);
			}

			if (offlineVideoCourses.getVideoMlLink() != null) {
				VideoLinkDTO vml = new VideoLinkDTO();
				vml.setLanguage("Malayalam");
				vml.setLink(offlineVideoCourses.getVideoMlLink());
				videoLinks.add(vml);
			}

			if (offlineVideoCourses.getVideoTlLink() != null) {
				VideoLinkDTO vtl = new VideoLinkDTO();
				vtl.setLanguage("Telugu");
				vtl.setLink(offlineVideoCourses.getVideoTlLink());
				videoLinks.add(vtl);
			}

			if (offlineVideoCourses.getVideoTmLink() != null) {
				VideoLinkDTO vta = new VideoLinkDTO();
				vta.setLanguage("Tamil");
				vta.setLink(offlineVideoCourses.getVideoTmLink());
				videoLinks.add(vta);
			}

			List<TopicLanguage> tlList = topicLanguageRepository
					.findByOfflineVideoCourse_idOfflineVideoCourse(offlineVideoCourses.getIdOfflineVideoCourse());

			if (!tlList.isEmpty()) {

				tlList.stream().forEach(tl -> {

					videoLinks.stream().forEach(v -> {

						if (tl.getIdLanguage() == languageRepository.findByLanguage(v.getLanguage()).getIdLanguage()) {
							v.setAnswer(tl.getAnswer());
							v.setHeading(tl.getHeading());
							v.setQuestion(tl.getQuestion());
							v.setPdfURL(tl.getPdfURL());
						}
					});

				});
			}

			cvr.setVideoLinks(videoLinks);
			result.setData(cvr);
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
	public Document<List<OfflineVideoCourse>> getTop10LatestPosts(String productLineCd) {

		Document<List<OfflineVideoCourse>> result = new Document<List<OfflineVideoCourse>>();
		try {

			List<OfflineVideoCourse> latestPostList = offlineVideoCourseRepository.getProductLineCd(productLineCd);
			if (latestPostList.isEmpty())
				throw new NullPointerException("No videos found");
			result.setData(latestPostList);
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
	public Document<List<OfflineVideoCourse>> getTop10VideosByIdSubject(Long idSubject) {
		Document<List<OfflineVideoCourse>> result = new Document<List<OfflineVideoCourse>>();
		try {
			List<OfflineVideoCourse> topListBySub = offlineVideoCourseRepository
					.findTop10ByIdSubjectOrderByIdOfflineVideoCourseDesc(idSubject);
			if (topListBySub.isEmpty())
				throw new NullPointerException("No Videos Found");
			result.setData(topListBySub);
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
	@Override
	public Document getOfflineCourseonProduct(Long idSubject, Long idSubjectChapter, Long idSyllabus,
			Long idClassStandard, Long idState) {
		Document result = new Document();
		try {

			Product prod = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(5L,
							idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("No Product Found!");

			List<OfflineVideoCourse> latestPostList = offlineVideoCourseRepository
					.findByIdSubjectChapterAndIdProduct(idSubjectChapter, prod.getIdProduct());

			if (latestPostList.isEmpty())
				throw new NullPointerException("No video Found!");

			result.setData(latestPostList);
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
	public Document<List<Object>> getListofHeadingForChapter(Long idProduct, Long idSubjectChapter) {

		Document<List<Object>> result = new Document<>();
		try {

			List<Object> response = new ArrayList<>();

			List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
					.findByIdSubjectChapterAndIdProduct(idSubjectChapter, idProduct);

			if (streamingList.isEmpty())
				throw new AppException("No video Course found!!!");

			List<FreeOfflineVideoCourseResponseDTO> temp = new ArrayList<>();
			streamingList.stream().forEach(o -> {
				FreeOfflineVideoCourseResponseDTO ffvc = new FreeOfflineVideoCourseResponseDTO();
				BeanUtils.copyProperties(o, ffvc);
				temp.add(ffvc);
			});

			List<FreeOfflineVideoCourseResponseDTO> _ftemp = new ArrayList<>();

			_ftemp.addAll(temp);

			HashSet<String> headers = new HashSet<>();

			temp.removeIf(e -> !headers.add(e.getHeading()));

			List<String> temp3 = new ArrayList<>(headers);

			Collections.sort(temp3);

			for (String heading : temp3) {
				Map<String, String> data = new HashMap<>();

				data.put("heading", heading);

				response.add(data);

			}

			result.setData(response);
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
	@Transactional
	@Async
	public void deleteOfflineVideoCourse(Long idOfflineVideoCourse, Long userId) {

		List<String> log = new ArrayList<>();

		try {
			log.add("UserId:" + userId);
			log.add("Time of deletion started:" + Instant.now() + ".");

			OfflineVideoCourse ovc = offlineVideoCourseRepository.findByIdOfflineVideoCourse(idOfflineVideoCourse);

			if (ovc == null)
				throw new AppException("Invalid Offline Video Course information");
			
			List<OfflineVideoCourseRating> ovcRating = offlineVideoCourseRatingRepository.findByidOfflineVideoCourse(idOfflineVideoCourse);
			if(ovcRating!=null )
				offlineVideoCourseRatingRepository.deleteAll(ovcRating);


			log.add("Deleting idOfflineVideoCourse:" + idOfflineVideoCourse);

			List<StudentAssignedCourse> listofAssignedCourse = studentAssignedCourseRepository
					.findByIdOfflineVideoCourse(idOfflineVideoCourse);

			log.add("Total No of SAC Records to deleted:" + listofAssignedCourse.size());

			studentAssignedCourseRepository.deleteInBatch(listofAssignedCourse);

			log.add("SAC records deleted");

			offlineVideoCourseRepository.delete(ovc);

			log.add("OVC record deleted.");

			log.add("Deleting idOfflineVideoCourse completed!!!");

		} catch (Exception exp) {

			log.add("Error Occured:");
			if (exp.getLocalizedMessage() == null)
				return;
			log.add(exp.getLocalizedMessage());
		
		} finally {
			String fileName = "OfflineVideoCourseDeleted" + Instant.now() + ".txt";

			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : log) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File file = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/deletion/offline-video-course", fileName, file);

				boolean isDeletedFile = file.delete();
				logger.info("Logs file e deleted from the system for offline video deletion : " + isDeletedFile);

			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			}
		}

	}

	@Override
	public Document<List<OfflineVideosReportDto>> getTotalUploadedVideoCount(Long idClassStandard, Long idSyllabus,
			Long idState) {

		Document<List<OfflineVideosReportDto>> result = new Document<>();

		try {
			Long newidClassStandard = idClassStandard == -1 ? null : idClassStandard;
			Long newidSyllabus = idSyllabus == -1 ? null : idSyllabus;
			Long newidState = idState == -1 ? null : idState;
			List<OfflineVideosReportDto> offlineVideosReportList = offlineCourseRepository
					.getTotalVideoCount(newidClassStandard, newidSyllabus, newidState);
			result.setData(offlineVideosReportList);
			result.setMessage("successfull");
			result.setStatusCode(200);
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}
		return result;
	}

	@Async
	@Override
	public void updateOfflineVideoSequencingAutomatically(Long userSurId, Long idSubject, Long idClass, Long idSyllabus,
			Long idState, Long idSubjectChapter) {

		List<String> logList = new ArrayList<>();

		String module = "  log ---- ";
		try {

			logger.info("automatic offline video course video sequencing updation process started.");

			Instant startTime = Instant.now();
			logList.add("Automatic Offline Video Course Video Sequencing updation process started at : " + startTime);
			logList.add("Invoked user id  : " + userSurId);
			int totalVideoCount = 0;

			List<SubjectChapter> subjectChapters = new ArrayList<>();

			if (!idSubjectChapter.equals(-1L)) {
				SubjectChapter sc = subjectChapterRepository.findByIdSubjectChapter(idSubjectChapter);

				logList.add(Instant.now() + module
						+ "  Updating Automatic Offline Video Course Video Sequence, Requested for a subject chapter - idSubjectChapter : "
						+ idSubjectChapter + " .");

				if (sc == null)
					throw new AppException("Invalid IdSubjectChapter provided.");

				subjectChapters.add(sc);

			}

			else if (!idSubject.equals(-1L)) {
				if (idClass.equals(-1L) || idSyllabus.equals(-1L) || idState.equals(-1L))
					throw new AppException(
							"Please provide additional information idClassStandard,idSyllabus,idState info include with idsubject.");

				subjectChapters = subjectChapterRepository
						.findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusOrderBySortOrder(idSubject, idClass,
								idState, idSyllabus);

				logList.add(Instant.now() + module
						+ "  Updating Automatic Offline Video Course Video Sequence, Requested for a subject"
						+ " -  \n with requesed combination id of class standard, syllabus, state and subject respectively "
						+ "(" + idClass + "," + idSyllabus + "," + idState + "," + idSubject + ").");
			}

			else {

				logList.add(Instant.now() + module
						+ "  Updating Automatic Offline Video Course Video Sequencing, Requested to updated all chapters.");

				subjectChapters = subjectChapterRepository.findAll();
			}

			if (subjectChapters.isEmpty())
				throw new AppException("No ChapterData available. Please Contact Admin.");

			logList.add(Instant.now() + module + "  Total number of chapter : " + subjectChapters.size());

			for (SubjectChapter sc : subjectChapters) {
				logList.add(
						Instant.now() + module + "  video sequence updation started for " + sc.getChapterName() + " .");

				List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
						.findByIdSubjectAndIdSubjectChapter(sc.getIdSubject(), sc.getIdSubjectChapter());

				if (streamingList.isEmpty()) {
					logList.add(sc.getChapterName() + " Chapter doesnt have any offline video course data.");
					continue;
				}

				logList.add(
						Instant.now() + module + "  Total number of videos uploaded chapter  :" + streamingList.size());

				totalVideoCount += streamingList.size();

				logList.add(Instant.now() + module + "  sorting video based on headers.");

				Collections.sort(streamingList, (s1, s2) -> s1.getHeading().compareToIgnoreCase(s2.getHeading()));

				logList.add(
						Instant.now() + module + "  sorting video completed , initializing video sequence updation.");

				for (int i = 0; i < streamingList.size(); i++) {
					logList.add(Instant.now() + module + "  current video sequence number : "
							+ streamingList.get(i).getVideoSeqNumber() + " updated video sequence number : " + (i + 1)
							+ ".");

					streamingList.get(i).setVideoSeqNumber((i + 1));
				}

				offlineVideoCourseRepository.saveAll(streamingList);

				logList.add(
						Instant.now() + module + "  video sequence updation ended for " + sc.getChapterName() + ".");
				logList.add("------------------------------------");

			}

			logList.add(Instant.now() + module + " Completed video sequencing updated process.");
			logList.add(Instant.now() + module + " Total no of video processed : " + totalVideoCount + ".");
			logList.add("Elapsed duration: " + (Instant.now().getEpochSecond() - startTime.getEpochSecond() + " sec."));

			logger.info("auto offline video course video sequencing updation process completed.");
		}

		catch (Exception e) {
			logList.add("Error Occured: " + e.getLocalizedMessage());
			logList.add("Offline Video Course Video Sequence updation process exited at : " + Instant.now());
		} finally {

			logger.info("video sequencing updates log upload process started.");
			String fileName = "video_sequence_updation_log_" + Instant.now() + ".txt";

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {

				for (String str : logList) {
					writer.write(str + System.lineSeparator());
				}

				writer.flush();

				File file = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/updation/ovc_sequence_updation", fileName, file);

				if (file.delete())
					logger.info("Deleted log file created for video sequence updates.");

				logger.info("video sequence updates log upload process completed.");

			} catch (IOException e) {
				logger.info("Error occured during video sequence updates log upload process.");

				logger.error(e.getLocalizedMessage());

			}
		}

	}

	@Override
	public Document<ChapterBasedVideoDTO> chapterBasedVideo(Long idStudentSubscription, Long idSubjectChapter) {
		Document<ChapterBasedVideoDTO> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;

			Boolean accessAllowed = false;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			} else {
				throw new AppException("Invalid User Authentication information.");
			}

			Student student = studentRepository.findByUser_userSurId(userPrincipal.getUserSurId());

			if (student == null)
				throw new AppException("Student details not found");

			// intalizing the two flag
			Boolean newUserFlag = false;
			Boolean subscriptionFlag = false;

			// check whether user is subscribed.
			SubscribedUserDTO suDTO = subjectChapterUtility.checkUserSubscribed(userPrincipal.getUserSurId());

			subscriptionFlag = suDTO != null;

			// check whether user profile created 2 day before for applying 2 days trail
			// period.
			UserCreatedDTO ucDTO = userService.getUserCreatedWithinTwoDays(userPrincipal.getUserSurId());

			newUserFlag = ucDTO != null;

			if (newUserFlag || subscriptionFlag)
				accessAllowed = true;

			StudentSubscription subscription = studentSubscriptionRepository
					.findByIdStudentSubscriptionAndIdStudent(idStudentSubscription, student.getIdStudent());

			if (subscription == null)
				throw new AppException("Student Subscription does not exists!");

			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapter(idSubjectChapter);

			if (subjectChapter == null)
				throw new AppException("invalid subject chapter");

			Boolean isECAChapter = (subjectChapter.getIdClassStandard().equals(4L)
					|| subjectChapter.getIdState().equals(6L) || subjectChapter.getIdSyllabus().equals(4L));

			Long idClassStandard = Boolean.TRUE.equals(isECAChapter) ? 4L : userPrincipal.getIdClassStandard();

			Long idState = Boolean.TRUE.equals(isECAChapter) ? 6L : userPrincipal.getIdState();

			Long idSyllabus = Boolean.TRUE.equals(isECAChapter) ? 4L : userPrincipal.getIdSyllabus();

			subjectChapter = subjectChapterRepository.findByIdSubjectChapterAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
					idSubjectChapter, idClassStandard, idState, idSyllabus,Boolean.TRUE);

			if (subjectChapter == null) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("User dosent have access to this subscription.");
				return result;
			}

			Subject subject = subjectRepository.findByIdSubject(subjectChapter.getIdSubject());

			if (subject == null)
				throw new AppException("Invalid Idsubject");
		
			
			List<StudentVideoStreamingDTO> studentVideoStreamingList = offlineVideoCourseRepository
					.getStudentVideoStreamingLog(idStudentSubscription, idSubjectChapter);
			
			
			if (studentVideoStreamingList == null)
				throw new AppException("Student Video Streaming data not found");

			List<StudentVideoStreamingDTO> sortedList = new LinkedList<>();
			
			List<Long> idProductList = Arrays.asList(1219L, 1220L);
			
			StudentSubscription  studentSubscription =studentSubscriptionRepository.findFirstByIdProductInAndUserSurIdAndActiveFlag(idProductList,userPrincipal.getUserSurId(), Boolean.TRUE);
			
			if(studentSubscription!=null) {
				
				studentVideoStreamingList.stream()
				.sorted(Comparator.comparingInt(StudentVideoStreamingDTO::getVideoSeqNumber))
				.collect(Collectors.toList()).forEach(s -> {
					s.setCompleteFlag(s.getIdStudentAssignedCourse() == null ? Boolean.FALSE : s.getCompleteFlag());

					sortedList.add(s);

				});
				
			}else {
				
				studentVideoStreamingList.stream()
				.sorted(Comparator.comparingInt(StudentVideoStreamingDTO::getVideoSeqNumber))
				.collect(Collectors.toList()).forEach(s -> {
					s.setCompleteFlag(s.getIdStudentAssignedCourse() == null ? Boolean.FALSE : s.getCompleteFlag());
                    s.setTecherNoteURL(null);
					sortedList.add(s);

				});
				
			}
			

		
			ChapterBasedVideoDTO dto = new ChapterBasedVideoDTO();

			if (Boolean.TRUE.equals(accessAllowed)) {
				dto.setIsAccessAllowed(accessAllowed);
			} else {

				Boolean freeChapterFlag = subjectChapterUtility.checkChapterAvailablityForFreeStreaming(
						idSubjectChapter, idClassStandard, subject.getIdSubject(), idState, idSyllabus);

				if (Boolean.TRUE.equals(freeChapterFlag))
					dto.setIsAccessAllowed(Boolean.TRUE);
				else
					dto.setIsAccessAllowed(Boolean.FALSE);
			}

			int totalSeconds = 0;
			Boolean invokeFlag = true;
			for (StudentVideoStreamingDTO studentVideoStreaming : sortedList) {
				totalSeconds += studentVideoStreaming.getVideoDuration();
				if (Boolean.TRUE.equals(invokeFlag)) {
					if (studentVideoStreaming.getCompleteFlag().equals(Boolean.FALSE)) {
						studentVideoStreaming.setIsCurrentlyStreaming(Boolean.TRUE);
						studentVideoStreaming.setVideoOtp(createCourseVideoOtp(studentVideoStreaming.getVideoEnLink()));
						invokeFlag = false;
					} else {
						studentVideoStreaming.setIsCurrentlyStreaming(Boolean.FALSE);
					}

				} else {

					studentVideoStreaming.setIsCurrentlyStreaming(Boolean.FALSE);
				}

				studentVideoStreaming
						.setVideoCoverageDuration(studentVideoStreaming.getVideoCoverageDuration() == null ? 0
								: studentVideoStreaming.getVideoCoverageDuration());
				studentVideoStreaming.setPctComplete(
						studentVideoStreaming.getPctComplete() == null ? "0" : studentVideoStreaming.getPctComplete());
			}

			dto.setStudentVideoStreamingDTO(sortedList);

			SubjectChapter nextSubjectChapter = subjectChapterUtility.getNextSubjectChapter(idSubjectChapter);
			dto.setNextIdSubjectChapter(nextSubjectChapter == null ? 0L : nextSubjectChapter.getIdSubjectChapter());
			dto.setIsNextChapterAvailable(nextSubjectChapter == null ? Boolean.FALSE : Boolean.TRUE);
			int hours = totalSeconds / 3600;
			int minutes = (totalSeconds % 3600) / 60;
			int seconds = totalSeconds % 60;
			dto.setTotalDuration(hours + "h " + minutes + " min " + seconds + " sec.");

			dto.setTotalContent((long) studentVideoStreamingList.size());
			dto.setChapterName(subjectChapter.getChapterName());
			dto.setIdStudentSubscription(idStudentSubscription);
			dto.setIdSubject(subjectChapter.getIdSubject());
			dto.setSubjectName(subject.getSubjectName());
			dto.setIdSubjectChapter(idSubjectChapter);
			result.setData(dto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successful");

		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}

		return result;
	}

	/**
	 * @Author Abdul Elahi Saves video rating submitted by users.
	 * 
	 * @param rating The VideoRatingInputDTO containing the rating information.
	 * @return Document containing the saved OfflineVideoCourseRating.
	 */
	@Override
	public Document<OfflineVideoCourseRating> saveVideoRating(VideoRatingInputDTO rating) {
		Document<OfflineVideoCourseRating> result = new Document<>();
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			} else {
				throw new AppException("Invalid User Authentication information.");
			}

			if (rating.getRating() < 0 || rating.getRating() > 5)
				throw new AppException("Invalid ratings ");

			if (rating.getIdOfflineVideoCourse() == null)
				throw new AppException("Invalid id offline video course");

			OfflineVideoCourse isExist = offlineVideoCourseRepository
					.findByIdOfflineVideoCourse(rating.getIdOfflineVideoCourse());
			if (isExist == null)
				throw new AppException("invalid id video");

			OfflineVideoCourseRating ovcr = offlineVideoCourseRatingRepository.findByidOfflineVideoCourseAndUserSurId(
					rating.getIdOfflineVideoCourse(), userPrincipal.getUserSurId());
			if (ovcr != null)
				throw new AppException("Already given ratings");

			OfflineVideoCourseRating ovcrRatings = new OfflineVideoCourseRating();
			ovcrRatings.setIdOfflineVideoCourse(rating.getIdOfflineVideoCourse());
			ovcrRatings.setUserSurId(userPrincipal.getUserSurId());
			ovcrRatings.setRating(rating.getRating());
			ovcrRatings.setReview(rating.getReview());
			ovcrRatings.setCreatedAt(Instant.now());
			ovcrRatings.setVisibleFlag(rating.getRating() > 3 ? true : false);

			OfflineVideoCourseRating ovcRating = offlineVideoCourseRatingRepository.save(ovcrRatings);

			result.setData(ovcRating);
			result.setMessage("Rating recorded successfully.");
			result.setStatusCode(HttpStatus.CREATED.value());

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * @Author Abdul Elahi Checks if the current user has already given a rating for
	 *         a specific video.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return Document containing a boolean value indicating whether the user has
	 *         given a rating or not.
	 */
	@Override
	public Document<Boolean> isRatingGiven(Long idOfflineVideoCourse) {
		Document<Boolean> result = new Document<>();
		boolean ratingFlag = false;
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			} else {
				throw new AppException("Invalid User Authentication information.");
			}

			OfflineVideoCourseRating ovcr = offlineVideoCourseRatingRepository
					.findByidOfflineVideoCourseAndUserSurId(idOfflineVideoCourse, userPrincipal.getUserSurId());
			if (ovcr != null)
				ratingFlag = true;

			result.setData(ratingFlag);
			result.setMessage("rating given by user : " + ratingFlag);
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}

	/**
	 * @Author Abdul Elahi Checks if the current user has already given a rating for
	 *         a specific video.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return Document containing a boolean value indicating whether the user has
	 *         given a rating or not.
	 */
	@Override
	public Document<OfflineVideoCourseRating> isRatingVisible(Long idOfflineVideoCourse, Long userSurId,
			Boolean isVisible) {
		Document<OfflineVideoCourseRating> result = new Document<>();

		try {

			OfflineVideoCourseRating ovccRating = offlineVideoCourseRatingRepository
					.findByidOfflineVideoCourseAndUserSurId(idOfflineVideoCourse, userSurId);
			if (ovccRating == null)
				throw new AppException("data doesnot exist for offline video course");

			ovccRating.setVisibleFlag(isVisible);

			ovccRating = offlineVideoCourseRatingRepository.save(ovccRating);

			result.setData(ovccRating);
			result.setMessage("Updated Successfully");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}

	/**
	 * @Author Abdul Elahi Retrieves the rating given by the current user for a
	 *         specific video.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return Document containing the rating given by the user.
	 */
	@Override
	public Document<OfflineVideoCourseRating> getRatingForUser(Long idOfflineVideoCourse) {
		Document<OfflineVideoCourseRating> result = new Document<>();
		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			} else {
				throw new AppException("Invalid User Authentication information.");
			}

			if (idOfflineVideoCourse == null)
				throw new AppException("Please provide valid id offline video");

			OfflineVideoCourseRating ovcr = offlineVideoCourseRatingRepository
					.findByidOfflineVideoCourseAndUserSurId(idOfflineVideoCourse, userPrincipal.getUserSurId());
			if (ovcr == null)
				throw new AppException("No ratings given");

			result.setData(ovcr);
			result.setMessage("Ratings : " + ovcr.getRating());
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * @Author Abdul Elahi Updates existing video ratings.
	 * 
	 * @param updatedRating The updated OfflineVideoCourseRating object.
	 * @return Document containing the updated OfflineVideoCourseRating.
	 */
	@Override
	public Document<OfflineVideoCourseRating> updateVideoRating(OfflineVideoCourseRating updatedRating) {
		Document<OfflineVideoCourseRating> result = new Document<>();
		try {
			OfflineVideoCourseRating existingRating = offlineVideoCourseRatingRepository
					.findById(updatedRating.getIdOfflineVideoCourse()).orElse(null);

			if (existingRating != null) {
				existingRating.setRating(updatedRating.getRating());
				existingRating.setReview(updatedRating.getReview());

				OfflineVideoCourseRating updatedOvcRating = offlineVideoCourseRatingRepository.save(existingRating);

				result.setData(updatedOvcRating);
				result.setMessage("Rating updated successfully.");
				result.setStatusCode(HttpStatus.OK.value());
			} else {
				throw new AppException("Rating with ID " + updatedRating.getIdOfflineVideoCourse() + " not found.");
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * @Author Abdul Elahi Retrieves the average rating for a specific video.
	 * 
	 * @param idOfflineVideoCourse The ID of the offline video course.
	 * @return Document containing the average rating for the video.
	 */
	@Override
	public Document<Double> getAverageRating(Long idOfflineVideoCourse) {
		Document<Double> result = new Document<>();
		try {
			Double averageRating = offlineVideoCourseRatingRepository.calculateAverageRating(idOfflineVideoCourse);

			if (averageRating != null) {
				result.setData(averageRating);
				result.setMessage("Average rating calculated successfully.");
				result.setStatusCode(HttpStatus.OK.value());
			} else {
				throw new AppException("No ratings available for the course with ID " + idOfflineVideoCourse);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * @Author Abdul Elahi Filters video ratings based on various criteria.
	 * 
	 * @param inputDTO The RatingFilterInputDTO containing filtering criteria.
	 * @return Document containing filtered video ratings.
	 */
	@Override
	public Document<?> filterVideoRatings(RatingFilterInputDTO inputDTO) {
		Document<Page<RatingFilterDTO>> result = new Document<>();
		try {
			Pageable paging = PageRequest.of(inputDTO.getPage(), inputDTO.getSize());

			Page<RatingFilterDTO> page = offlineVideoCourseRatingRepository.findRatingsDetails(inputDTO.getUserSurId(),
					inputDTO.getIdClassStandard(), inputDTO.getDateFrom(), inputDTO.getDateTo(), inputDTO.getRatings(),
					inputDTO.getIdSubject(), paging);

			result.setData(page);
			result.setMessage("Successfully Fetched Data.");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * @Author Abdul Elahi Retrieves video details based on various criteria.
	 * 
	 * @param input The DetailsFilterInputDTO containing filtering criteria.
	 * @return Document containing filtered video details.
	 */
	@Override
	public Document<?> getAllVideos(DetailsFilterInputDTO input) {
		Document<Page<VideoDetailsDTO>> result = new Document<>();
		try {
			Pageable paging = PageRequest.of(input.getPage(), input.getSize());

			Long classStandard = input.getIdClassStandard() == -1 ? null : input.getIdClassStandard();
			Long subject = input.getIdSubject() == -1 ? null : input.getIdSubject();
			Long state = input.getIdState() == -1 ? null : input.getIdState();
			Long syllabus = input.getIdSyllabus() == -1 ? null : input.getIdSyllabus();
//			Integer rating = input.getRating() == -1 ? null : input.getRating();
			Page<VideoDetailsDTO> page = offlineVideoCourseRatingRepository.getVideoDetails(classStandard, subject,
					input.getRating(), state, syllabus, paging);

			result.setData(page);
			result.setMessage("Successfully Fetched Data.");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	public String createCourseVideoOtp(String videoId) {

		VideoCipherOTP vco = new VideoCipherOTP();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}
			if (userPrincipal == null) {
				throw new AppException("User principal not available");
			}

			Gson gson = new Gson();

			// Constructing the JSON string with the necessary fields
			Map<String, Object> annotation = new HashMap<>();
			annotation.put("type", "text");
			annotation.put("text", "vistaslearning.com");
			annotation.put("x", "5");
			annotation.put("y", "15");
			annotation.put("alpha", "0.25");
			annotation.put("color", "#ffffff");
			annotation.put("size", "15");

			Map<String, Object> otpParameters = new HashMap<>();
			otpParameters.put("ttl", 21600);// set video otp expiration for 6 hours
			otpParameters.put("userId", userPrincipal.getUserSurId());
			otpParameters.put("annotate", gson.toJson(new Map[] { annotation }));

			String jsonBody = gson.toJson(otpParameters);

			vco = videoCipherConfiguration.getOTP(videoId, jsonBody);

		}

		catch (Exception exp) {
			exp.printStackTrace();
		}

		return vco.getOtp();
	}

	@Override
	public Document<?> getAverageSubjectRating(Long idProduct) {
		Document<Double> result = new Document<>();
		try {
			Double avgRating = offlineVideoCourseRatingRepository.avgSubjectRating(idProduct);
			if (avgRating == null)
				throw new AppException("no average rating available for subject");

			result.setData(avgRating);
			result.setMessage("Average Subject Rating Fetched Successlly");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		return result;
	}

	@Override
	public Document<?> getAverageChapterRating(Long idSubjectChapter) {
		Document<Double> result = new Document<>();
		try {
			Double avgRating = offlineVideoCourseRatingRepository.avgChapterRating(idSubjectChapter);
			if (avgRating == null)
				throw new AppException("no average rating available for subject");

			result.setData(avgRating);
			result.setMessage("Average Chapter Rating Fetched Successlly");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		return result;
	}

	@Override
	public Document<?> getRatingsForVideos(Long idOfflineVideoCourse, Long userSurId, String userName,
			String schoolName, int from, int to,int page,int size) {
		Document<Page<VideoCourseRatingDTO>> result = new Document<>();
		try {
			Pageable paging = PageRequest.of(page,size);

			userSurId = userSurId == -1 ? null : userSurId;
			userName = userName.equalsIgnoreCase("-1") ? null : userName;
			schoolName = schoolName.equalsIgnoreCase("-1") ? null : schoolName;

			Page<VideoCourseRatingDTO> rating = offlineVideoCourseRatingRepository
					.getRatingsForVideos(idOfflineVideoCourse, userSurId, userName, schoolName, from, to, paging);

			result.setData(rating);
			result.setMessage("Average Subject Rating Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setMessage("An error occurred while fetching the ratings.");
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}
	
	public Document<String> uploadVideoS3(MultipartFile video) {

			Document<String> result = new Document<String>();

			File tempFile =fileUploadService.convertMultiPartFileToFile(video);
			try {
				String extension = "";

				int i = tempFile.getName().lastIndexOf('.');
				if (i > 0) {
					extension = tempFile.getName().substring(i + 1);
				}

				if(!extension.equalsIgnoreCase("mp4")&&!extension.equalsIgnoreCase("m4v"))
					 throw new AppException("Upload .mp4 or .m4v video format");
				
				String getUrl = fileUploadService.uploadFileToS3Bucket( "/vdocipher/video",
						tempFile.getName(),tempFile);

				result.setData(getUrl);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request successfull");

			}finally {
				boolean isDeletedTempFile = tempFile.delete();
				logger.info("Temp file deleted from the system : " +isDeletedTempFile );
			}
			return result;
			
	}
	
	public  String uploadVideoToVdocipher(String url, String folderId, String title) throws Exception {
        String urlString = "https://dev.vdocipher.com/api/videos/importUrl";
        URL urlObj = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();

        if(folderId==null)
        	throw new AppException("Please select a folder.");
        
        
        
        // Set the request method to PUT
        conn.setRequestMethod("PUT");

        // Set the request headers
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Apisecret " + videocipherSecret);
        conn.setRequestProperty("Content-Type", "application/json");

        // Enable input and output streams
        conn.setDoOutput(true);

        // Create the JSON payload
        String jsonInputString = String.format(
                "{ \"url\": \"%s\", \"folderId\": \"%s\", \"title\": \"%s\" }",
                url, folderId, title
        );
        
        
        // Write the JSON data to the request body
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = conn.getResponseCode();
        
        if(responseCode==400)
        	    throw new AppException("This video already exists in your VdoCipher.");
       

        // Read the response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse the response JSON to extract the videoId
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getString("id");  // Extract and return the videoId
            }
        } else {
            throw new Exception("Failed to import video. Response code: " + responseCode);
        }
    }

	@Override
	public Document<FolderListResponseDTO> getVdocipherFolders(String folderId) {
		
		Document<FolderListResponseDTO> result = new Document<>();
		try {

		FolderListResponseDTO response =videoCipherConfiguration.getVdocipherFolders(folderId);
			
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successful");
			return result;
		
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	
		
	}

	@Override
	public Document<String> vdocipherWebhook(String payload) {

		Document<String> result = new Document<>();
		try {
			JSONObject json = new JSONObject(payload);
			// Access data
			String hookId = json.getString("hookId");
			String event = json.getString("event");
			long time = json.getLong("time");

			JSONObject payloadObject = json.getJSONObject("payload");
			String videoId = payloadObject.getString("id");
			String title = payloadObject.getString("title");
			double totalSizeMb = payloadObject.getDouble("totalSizeMb");

			OfflineVideoCourse ovc = offlineVideoCourseRepository.findByVideoEnLink(videoId);

			HashMap<String, Object> vcm = null;
			try {
				vcm = videoCipherConfiguration.getVideoMetaData(videoId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (vcm == null)
				throw new AppException("Invalid VideoId Provided.");

			ovc.setVideoDuration((int) vcm.get("duration"));
			ovc.setVideoPoster1Location(
					((HashMap<String, Object>) ((List) vcm.get("posters")).get(0)).get("url").toString());

			ovc.setStatus("Ready");
			offlineVideoCourseRepository.save(ovc);

			String key = "vdocipher/video/" + title;

			System.out.println(key);

			deleteFileFromS3(bucketName, key);
			// Print data

			logger.info("Hook ID: " + hookId);
			logger.info("Event: " + event);
			logger.info("Time: " + time);
			logger.info("Video ID: " + videoId);
			logger.info("Title: " + title);
			logger.info("Total Size MB: " + totalSizeMb);
			
			result.setData("Request successful");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successful");
	

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}
		return result;

	}

	 public  void deleteFileFromS3(String bucketName, String keyName) {
	        try {
	            // Create a DeleteObjectRequest
	            DeleteObjectRequest deleteObjectRequest =new  DeleteObjectRequest(bucketName,keyName);

	            // Delete the object
	            amazonS3.deleteObject(deleteObjectRequest);

	            System.out.println("File deleted successfully: " + keyName);

	        } catch (Exception e) {
	            System.err.println("Failed to delete file: " + e.getLocalizedMessage());
	            e.printStackTrace();
	        }
	    }

	@Override
	public Document<String> uploadVideo(MultipartFile video) {
		Document<String> result = new Document<String>();
		try {

			Document<String> url=  uploadVideoS3(video);
		
			result.setData(url.getData());
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Uploaded Video Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<?> deleteVideo(String key) {
	
		
		Document<String> result = new Document<String>();
		try {

			deleteFileFromS3(bucketName,key);
		
			result.setData("Video deleted Successfully");
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Video deleted Successfully");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

	}
	
	public Document<String> uploadTeacherNote(MultipartFile teacherNote) {
		// TODO Auto-generated method stub
		Document<String> result = new Document<String>();

		File tempFile =fileUploadService.convertMultiPartFileToFile(teacherNote);
		try {

			String random = Long.toString(System.currentTimeMillis());

			String extension = "";

			int i = tempFile.getName().lastIndexOf('.');
			if (i > 0) {
				extension = tempFile.getName().substring(i + 1);
			}

			String getUrl = fileUploadService.uploadFileToS3Bucket( "/teacher_note",
					random+"_"+tempFile.getName(),tempFile);

			result.setData(getUrl);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request successfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		} finally {
			boolean isDeletedTempFile = tempFile.delete();
			logger.info("Temp file deleted from the system : " +isDeletedTempFile );
		}
		return result;
	}
}