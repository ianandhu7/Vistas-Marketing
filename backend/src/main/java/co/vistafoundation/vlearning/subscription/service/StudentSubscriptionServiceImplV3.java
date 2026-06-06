package co.vistafoundation.vlearning.subscription.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.config.PaytmPaymentConfig;
import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.auth.repository.MerchantRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizQuestionRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subject.utils.SubjectChapterUtility;
import co.vistafoundation.vlearning.subscription.dto.NewStreamingSubjectChapterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StreamingSubjectChapterDTOV3;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV3;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.utils.GenerateTime;
import co.vistafoundation.vlearning.utils.RandomStringGenerator;

/**
 * @author NaveenKumar
 *
 */
@Service
public class StudentSubscriptionServiceImplV3 implements StudentSubscriptionServiceV3 {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscriptionServiceImpl.class);

	
	@Value("#{${listOfMarketingIds}}")
	private List<String> marketingIds;

	@Autowired
	UserService userService;

	@Autowired
	StudentSubscriptionServiceImpl studentSubscriptionServiceImpl;

	@Autowired
	StudentSubscriptionService studentSubscriptionService;

	@Autowired
	ProductLineRepository productLineRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	StudentAssignedCourseRepository studentAssignedCourseRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	QuizQuestionRepository quizQuestionRepository;

	@Autowired
	GenerateTime generateTime;

	@Autowired
	SubjectChapterUtility subjectChapterUtility;
	
	@Autowired
	StudentOrderRepository studentOrderRepository;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
	RandomStringGenerator randomStringGenerator;
	
	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	private PaytmPaymentConfig paytmPaymentConfig;

	@Value("${app.angular.url}")
	private String appAngularUrl;
	
	@Value("${paytm.payment.vsms.merchant.id}")
	private String merchantId;
	
	@SuppressWarnings("unused")
	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	@Override
	public Document<StudentSubscriptionSubjectDTOV3> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long idClassStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription) {

		Document<StudentSubscriptionSubjectDTOV3> result = new Document<>();

		try {

			Boolean accessAllowed = false;

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Student student = null;

			Integer count;

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");

				// intalizing the two flag
				Boolean newUserFlag = false;
				Boolean subscriptionFlag = false;

				student = studentRepository.getStudentByUser_UserSurId(userPrincipal.getUserSurId());

				if (student == null)
					throw new AppException("Invalid Student information found!");

				// check whether user is subscribed.
				SubscribedUserDTO suDTO = studentSubscriptionServiceImpl
						.checkUserSubscribed(userPrincipal.getUserSurId());

				subscriptionFlag = suDTO != null;

				// check whether user profile created 2 day before for applying 2 days trail
				// period.
				UserCreatedDTO ucDTO = userService.getUserCreatedWithinTwoDays(userPrincipal.getUserSurId());

				newUserFlag = ucDTO != null;

				if (newUserFlag || subscriptionFlag)
					accessAllowed = true;

			} else {
				accessAllowed = false;
			}

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
							idProductLine, idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found!");

			StudentSubscription ss = null;

			ClassStandard standard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (standard == null)
				throw new NullPointerException("No ClassStandard Found!");

			Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

			if (sub == null)
				throw new NullPointerException("No Subject Found!");

			ss = studentSubscriptionRepository.findFirstByIdProductAndUserSurIdAndActiveFlag(product.getIdProduct(),
					userPrincipal.getUserSurId(), Boolean.TRUE);

			if (ss == null)
				ss = studentSubscriptionRepository.findFirstByIdStudentAndIdProductAndActiveFlag(student.getIdStudent(),
						product.getIdProduct(), Boolean.TRUE);

			if (ss == null)
				throw new NullPointerException("User Subscription not found.");

			if (!ss.getIdStudentSubscription().equals(idStudentSubscription)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("User dosent have access to this subscription.");
			}

			List<OfflineVideoCourse> ovcFullList = offlineVideoCourseRepository.findByIdProduct(product.getIdProduct());

			List<OfflineVideoCourse> ovcList = offlineVideoCourseRepository
					.findByIdProductAndVideoEnLinkNotIn(product.getIdProduct(), marketingIds);

			count = ovcList.size();

			if (ovcFullList.isEmpty())
				throw new NullPointerException("No Video Data Found for the selected product!");

			List<StudentAssignedCourse> sacs = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndCompleteFlag(
							ss.getIdStudentSubscription(), sub.getIdSubject(), marketingIds, Boolean.TRUE);

			HashSet<Long> chIds = new HashSet<>();

			ovcFullList.removeIf(e -> !chIds.add(e.getIdSubjectChapter()));

			List<Long> finalChList = new ArrayList<>(chIds);

			List<SubjectChapter> chapters = subjectChapterRepository
					.findByIdSubjectChapterInAndActiveFlagOrderBySortOrder(finalChList,Boolean.TRUE);

			if (chapters.isEmpty())
				throw new NullPointerException("No Chapters Found!");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("No Syllabus Found!");

			List<StreamingSubjectChapterDTOV3> finalStreamingChapters = new ArrayList<>();

			int breakCounter = 0;

			for (SubjectChapter sc : chapters) {
				StreamingSubjectChapterDTOV3 sscd = new StreamingSubjectChapterDTOV3();

				sscd.setChapter(sc);

				sscd.setIsQuizAvailable(quizQuestionRepository
						.existsByQuiz_idSubjectChapterAndQuestionActiveFlag(sc.getIdSubjectChapter(), Boolean.TRUE));

				if (Boolean.TRUE.equals(accessAllowed)) {
					sscd.setAccessAllowed(true);
				} else {

					if (breakCounter < 2) {
						Boolean freeChapterFlag = studentSubscriptionServiceImpl
								.checkChapterAvailablityForFreeStreaming(sc.getIdSubjectChapter(), idClassStandard,
										idSubject, idState, idSyllabus);
						sscd.setAccessAllowed(freeChapterFlag);

						if (Boolean.TRUE.equals(freeChapterFlag))
							breakCounter++;

					} else {
						sscd.setAccessAllowed(false);
					}
				}

				if (sacs.isEmpty()) {
					sscd.setCompletionPercentage("0");
				} else {
					sscd.setCompletionPercentage(
							this.getChapterCompletionPercentage(sc.getIdSubjectChapter(), ovcList, sacs));
				}

				finalStreamingChapters.add(sscd);

			}

			StudentSubscriptionSubjectDTOV3 sssDTO = new StudentSubscriptionSubjectDTOV3();

			if (ss != null) {

				if (sacs.isEmpty() || ovcList.isEmpty()) {

					sssDTO.setPercentageCompletion("0");
				} else {

					Double overAllPercentage = sacs.isEmpty() ? 0.0 : sacs.size();

					Double calculate = ((overAllPercentage / count) * 100);

					Double roundValue = Double.valueOf(Math.round(calculate));

					sssDTO.setPercentageCompletion(Double.toString(Math.round(roundValue)));
				}

			}

			sssDTO.setStreamingSubjectChapters(finalStreamingChapters);
			sssDTO.setClassStandardName(standard.getClassStandadName());
			sssDTO.setSyllabusName(syllabus.getSyllabusName());
			sssDTO.setSubjectName(sub.getSubjectName());
			sssDTO.setIdProduct(product.getIdProduct());
			result.setData(sssDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;

	}

	public String getChapterCompletionPercentage(Long idSubjectChapter, List<OfflineVideoCourse> ovcs,
			List<StudentAssignedCourse> sacs) {

		Integer orginalVideoAvailableCount = (int) ovcs.stream().filter(o -> o.getIdSubjectChapter().equals(idSubjectChapter)).count();

		if (orginalVideoAvailableCount < 1)
			return "0";

		Integer completedVideoCount =(int) sacs.stream().filter(s -> s.getIdSubjectChapter().equals(idSubjectChapter)).count();

		if (completedVideoCount < 1)
			return "0";

		Double calculate = ((double) completedVideoCount / orginalVideoAvailableCount) * 100;

		return Double.toString(Math.round(calculate));

	}
	
	@Override
	public Document<StudentSubscriptionSubjectDTO> getStudentChapterList(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState, Long idStudentSubscription) {

		Document<StudentSubscriptionSubjectDTO> result = new Document<>();

		try {

			Boolean accessAllowed = false;

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Student student = null;

			Integer count;

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");

				// intalizing the two flag
				Boolean newUserFlag = false;
				Boolean subscriptionFlag = false;

				student = studentRepository.getStudentByUser_UserSurId(userPrincipal.getUserSurId());

				if (student == null)
					throw new AppException("Invalid Student information found!");

				// check whether user is subscribed.
				SubscribedUserDTO suDTO = studentSubscriptionServiceImpl
						.checkUserSubscribed(userPrincipal.getUserSurId());

				subscriptionFlag = suDTO != null;

				// check whether user profile created 2 day before for applying 2 days trail
				// period.
				UserCreatedDTO ucDTO = userService.getUserCreatedWithinTwoDays(userPrincipal.getUserSurId());

				newUserFlag = ucDTO != null;

				if (newUserFlag || subscriptionFlag)
					accessAllowed = true;

			} else {

				throw new AppException("Invalid User Authentication information.");
			}

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
							idProductLine, idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found!");

			StudentSubscription ss = null;

			ClassStandard standard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (standard == null)
				throw new NullPointerException("No ClassStandard Found!");

			Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

			if (sub == null)
				throw new NullPointerException("No Subject Found!");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("No Syllabus Found!");

			ss = studentSubscriptionRepository.findFirstByIdProductAndUserSurIdAndActiveFlag(product.getIdProduct(),
					userPrincipal.getUserSurId(), Boolean.TRUE);

			if (ss == null)
				ss = studentSubscriptionRepository.findFirstByIdStudentAndIdProductAndActiveFlag(student.getIdStudent(),
						product.getIdProduct(), Boolean.TRUE);

			if (ss == null)
				throw new NullPointerException("User Subscription not found.");

			if (!ss.getIdStudentSubscription().equals(idStudentSubscription)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("User dosent have access to this subscription.");
				return result;
			}

			List<OfflineVideoCourse> ovcFullList = offlineVideoCourseRepository.findByIdProduct(product.getIdProduct());

			Set<Long> uniqueChapterIds = new HashSet<>();

			if (ovcFullList.isEmpty())
				throw new NullPointerException("No Video Data Found for the selected product!");

			List<OfflineVideoCourse> ovcList = new ArrayList<>();

			ovcFullList.stream().forEach(o -> {

				if (!marketingIds.contains(o.getVideoEnLink())) {
					ovcList.add(o);
				}
				uniqueChapterIds.add(o.getIdSubjectChapter());

			});

			count = ovcList.size();

			List<StudentAssignedCourse> sacs = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndCompleteFlag(
							ss.getIdStudentSubscription(), sub.getIdSubject(), marketingIds, Boolean.TRUE);

			List<Long> finalChList = new ArrayList<>(uniqueChapterIds);

			List<SubjectChapter> chapters = subjectChapterRepository
					.findByIdSubjectChapterInAndActiveFlagOrderBySortOrder(finalChList,Boolean.TRUE);

			if (chapters.isEmpty())
				throw new NullPointerException("No Chapters Found!");

			List<NewStreamingSubjectChapterDTO> finalStreamingChapters = new ArrayList<>();

			int breakCounter = 0;

			List<SubjectChapter> topTwoChapters = chapters.stream().limit(2).collect(Collectors.toList());

			Map<Long, Long> chaptersVideoCount = ovcList.stream()
					.collect(Collectors.groupingBy((OfflineVideoCourse::getIdSubjectChapter), Collectors.counting()));

			for (SubjectChapter sc : chapters) {
				NewStreamingSubjectChapterDTO sscd = new NewStreamingSubjectChapterDTO();
				
				sscd.setTotalChapterVideos((chaptersVideoCount.get(sc.getIdSubjectChapter())
						== null ? "0" : chaptersVideoCount.get(sc.getIdSubjectChapter()).toString()));
	
				sscd.setChapter(sc);
				int chapterDuration = ovcList.stream()
						.filter(o -> o.getIdSubjectChapter().equals(sc.getIdSubjectChapter()))
						.mapToInt(OfflineVideoCourse::getVideoDuration).sum();

				sscd.setTotalChapterWatchHours(generateTime.generateTimeFromSeconds(chapterDuration));

				if (Boolean.TRUE.equals(accessAllowed)) {
					sscd.setAccessAllowed(true);
				} else {

					if (breakCounter < 2) {
						Boolean freeChapterFlag = topTwoChapters.contains(sc);
						sscd.setAccessAllowed(freeChapterFlag);

						if (Boolean.TRUE.equals(freeChapterFlag))
							breakCounter++;
					} else {
						sscd.setAccessAllowed(Boolean.FALSE);
					}
				}
				finalStreamingChapters.add(sscd);
			}

			Long totalSubjectCount = chaptersVideoCount.values().stream().reduce(0L, Long::sum);

			NewStudentSubscriptionSubjectDTO sssDTO = new NewStudentSubscriptionSubjectDTO();

			if (ss != null) {

				if (sacs.isEmpty() || ovcList.isEmpty()) {
					sssDTO.setPercentageCompletion("0");
				} else {

					Double overAllPercentage = sacs.isEmpty() ? 0.0 : sacs.size();

					Double calculate = ((overAllPercentage / count) * 100);

					Double roundValue = Double.valueOf(Math.round(calculate));

					sssDTO.setPercentageCompletion(Double.toString(Math.round(roundValue)));
				}

			}
			
			String imageUrl = (appAngularUrl.equals("https://vistaslearning.com") || appAngularUrl.equals("https://student.vistaslearning.com"))
							? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ sub.getIdSubject() + ".webp"
							: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ sub.getIdSubject() + ".webp";
			
			sssDTO.setSubjectThumbnailURL(imageUrl);

			sssDTO.setTotalSubjectwatchHours(generateTime
					.generateTimeFromSeconds(ovcList.stream().mapToInt(OfflineVideoCourse::getVideoDuration).sum()));
			sssDTO.setTotalSubjectVideos(totalSubjectCount.toString());
			sssDTO.setSubjectChapters(finalStreamingChapters);
			sssDTO.setClassStandardName(standard.getClassStandadName());
			sssDTO.setSyllabusName(syllabus.getSyllabusName());
			sssDTO.setSubjectName(sub.getSubjectName());
			sssDTO.setIdProduct(product.getIdProduct());
			result.setData(sssDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;

	}

	

	@Override
	public Document<Map<String, Object>> getQuizAvailblityStatus(Long idSubjectChapter, Long idStudentSubscription) {

		Document<Map<String, Object>> result = new Document<>();

		try {
			SubjectChapter sc = subjectChapterRepository.findByIdSubjectChapter(idSubjectChapter);

			if (sc == null)
				throw new NullPointerException("Invalid Subject Chapter Info!");

			Boolean isAvailableFlag = quizQuestionRepository
					.existsByQuiz_idSubjectChapterAndQuestionActiveFlag(sc.getIdSubjectChapter(), Boolean.TRUE);

			Map<String, Object> finalResponse = new HashMap<>();

			finalResponse.put("isChapterQuizAvailable", isAvailableFlag);
			finalResponse.put("isPreviousQuizAttempted",
					subjectChapterUtility.isPreviousChapterQuizAttempted(idSubjectChapter,
							sc.getIdClassStandard(), sc.getIdSubject(), sc.getIdState(), sc.getIdSyllabus(),
							idStudentSubscription));

			result.setData(finalResponse);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");
		}

		catch (Exception exp) {
			LOGGER.error(exp.getLocalizedMessage());
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}

		return result;
	}
	
	@Override
	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception {
		TreeMap<String, String> parameters = paytmPaymentConfig.getRedirect(paytmDetailsDTO);
		return parameters;
	}


}
	