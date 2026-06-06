package co.vistafoundation.vlearning.subscription.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;

import java.util.Optional;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import co.vistafoundation.vlearning.auth.config.PaytmPaymentConfig;
import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.auth.model.Merchant;
import co.vistafoundation.vlearning.auth.model.SubscriptionClick;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.MerchantRepository;
import co.vistafoundation.vlearning.auth.repository.SubscriptionClickRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
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
import co.vistafoundation.vlearning.product.dto.ProductPricingDTO;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductAmount;
import co.vistafoundation.vlearning.product.model.ProductDuration;
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.repository.ProductAmountRepository;
import co.vistafoundation.vlearning.product.repository.ProductDurationRepository;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductPricingRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.model.Redemption;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.specialoffer.repository.RedemptionRepository;
import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTOV2;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.ProductDetailDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubdcribedSubDto;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTOV2;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionParamsDTO;
import co.vistafoundation.vlearning.subscription.model.MannualSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.model.MannualUpdateDetailsDTO;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.RandomStringGenerator;

@Service
//@CacheConfig(cacheNames = {"customer"})
public class StudentSubscriptionServiceImplV2 implements StudentSubscriptionServiceV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscriptionServiceImplV2.class);

	@Value("#{${listOfMarketingIds}}")
	private List<String> marketingIds;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

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
	StudentOrderRepository studentOrderRepository;

	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	RandomStringGenerator randomStringGenerator;

	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	private PaytmPaymentConfig paytmPaymentConfig;

	@Autowired
	private ProductPricingRepository productPricingRepository;

	@Autowired
	private ProductAmountRepository productAmountRepository;

	@Autowired
	private ProductDurationRepository productDurationRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private RedemptionRepository redemptionRepository;
	
	@Autowired
	private SubscriptionClickRepository subscriptionClickRepository;

	@Value("${paytm.payment.vsms.merchant.id}")
	private String merchantId;

	@Autowired
	FileUploadService fileUploadService;

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	
	@Value("${app.angular.url}")
	private String appAngularUrl;
	

	@Override
	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception {
		TreeMap<String, String> parameters = paytmPaymentConfig.getRedirect(paytmDetailsDTO);
		return parameters;
	}

	@SuppressWarnings("null")
	@Override
	public Document<StudentSubscriptionSubjectDTOV2> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long idClassStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription) {

		Document<StudentSubscriptionSubjectDTOV2> result = new Document<>();

		try {

			Boolean accessAllowed = false;

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Student student = null;

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");

				// intalizing the two flag
				Boolean newUserFlag, subscriptionFlag = false;

				student = studentRepository.getStudentByUser_UserSurId(userPrincipal.getUserSurId());

				if (student == null)
					throw new AppException("Invalid Student information found!");

				// check whether user is subscribed.
				SubscribedUserDTO suDTO = studentSubscriptionServiceImpl
						.checkUserSubscribed(userPrincipal.getUserSurId());

				subscriptionFlag = suDTO != null ? true : false;

				// check whether userprofile created 2 day before for applying 2 days trail
				// period.
				UserCreatedDTO ucDTO = userService.getUserCreatedWithinTwoDays(userPrincipal.getUserSurId());

				newUserFlag = ucDTO != null ? true : false;

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

			ss = studentSubscriptionRepository.findByIdProductAndUserSurId(product.getIdProduct(),
					userPrincipal.getUserSurId());

			if (ss == null)
				ss = studentSubscriptionRepository.findByIdStudentAndIdProduct(student.getIdStudent(),
						product.getIdProduct());

			if (ss == null)
				throw new NullPointerException("User Subscription not found.");

			if (!ss.getIdStudentSubscription().equals(idStudentSubscription)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.FORBIDDEN.value());
				result.setMessage("User dosent have access to this subscription.");
			}

			List<OfflineVideoCourse> ovcList = offlineVideoCourseRepository.findByIdProduct(product.getIdProduct());

			if (ovcList.isEmpty())
				throw new NullPointerException("No Video Data Found for the selected product!");

			HashSet<Long> chIds = new HashSet<>();

			ovcList.removeIf(e -> !chIds.add(e.getIdSubjectChapter()));

			List<Long> finalChList = new ArrayList<>(chIds);

			List<SubjectChapter> chapters = subjectChapterRepository
					.findByIdSubjectChapterInAndActiveFlagOrderBySortOrder(finalChList,Boolean.TRUE);

			if (chapters.isEmpty())
				throw new NullPointerException("No Chapters Found!");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("No Syllabus Found!");

			List<StreamingSubjectChapterDTOV2> finalStreamingChapters = new ArrayList<>();

			for (SubjectChapter sc : chapters) {
				StreamingSubjectChapterDTOV2 sscd = new StreamingSubjectChapterDTOV2();
				sscd.setChapter(sc);
				if (accessAllowed) {
					sscd.setAccessAllowed(true);
				} else {

					Boolean freeChapterFlag = studentSubscriptionServiceImpl.checkChapterAvailablityForFreeStreaming(
							sc.getIdSubjectChapter(), idClassStandard, idSubject, idState, idSyllabus);
					sscd.setAccessAllowed(freeChapterFlag);
				}

				;

				sscd.setCompletionPercentage(this.getChapterCompletionPercentage(sc.getIdSubjectChapter(), ss, sc));

				finalStreamingChapters.add(sscd);

			}

			StudentSubscriptionSubjectDTOV2 sssDTO = new StudentSubscriptionSubjectDTOV2();

			if (ss != null) {

				List<OfflineVideoCourse> offlist = offlineVideoCourseRepository

						.findByIdProductAndVideoEnLinkNotIn(product.getIdProduct(), marketingIds);

				List<StudentAssignedCourse> sacList = studentAssignedCourseRepository
						.findByIdStudentSubscriptionAndIdProductAndCompleteFlag(idStudentSubscription,
								product.getIdProduct(), Boolean.TRUE);

				if (sacList.isEmpty() || offlist.isEmpty()) {
					sssDTO.setPercentageCompletion("0");
				} else {

					Double overAllVideoCount = sacList.isEmpty() ? 0.0 : sacList.size();

					Double calculate = ((overAllVideoCount / offlist.size()) * 100);

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

	public String getChapterCompletionPercentage(Long idSubjectChapter, StudentSubscription studentSubscription,
			SubjectChapter subjectChapter) {

		List<OfflineVideoCourse> offlist = offlineVideoCourseRepository
				.findByIdProductAndVideoEnLinkNotInAndIdSubjectChapter(studentSubscription.getIdProduct(), marketingIds,
						idSubjectChapter);

		if (offlist.isEmpty())
			return "0";

		Integer count = offlist.size();

		List<StudentAssignedCourse> listVideos = studentAssignedCourseRepository
				.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndIdSubjectChapter(
						studentSubscription.getIdStudentSubscription(), subjectChapter.getIdSubject(), marketingIds,
						idSubjectChapter);

		Double overAllPercentage = listVideos.isEmpty() ? 0.0
				: listVideos.stream().mapToDouble(s -> Double.valueOf(s.getPctComplete())).sum();

		Double calculate = (overAllPercentage / (count * 100));

		return Double.valueOf(Math.round(calculate * 100)).toString();

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	public Document<CartSummaryDTO> saveNewUserSubscription(NewSubscriptionPlanDTO newSubscriptionPlanDTO) {
		Document<CartSummaryDTO> document = new Document();

		try {

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
			cartSummaryDTO.setPurchaseType("NEW");

			// adds user carts list
			List<UserCartResponseDTO> listUserCarts = new ArrayList<UserCartResponseDTO>();

			// get cart total amount
			Float cartTotalPrice = 0F;
			Float cartNetPrice = 0F;
			Float cartGSTPrice = 0F;
			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			Student student = studentRepository.getStudentByUser_UserSurId(userPrincipal.getUserSurId());

			if (student == null)
				throw new AppException("No Student Record found ");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByUserSurIdAndIdproductLineAndActiveFlag(userPrincipal.getUserSurId(), 11L, Boolean.TRUE);
			if (studentSubscription != null) {
				LocalDate dateExist = studentSubscription.getSubscriptionEndDate().atZone(zoneIndia).toLocalDate();
				LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
				if (!dateExist.isBefore(dateToday) && !dateExist.equals(dateToday)) {
					document.setData(null);
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					document.setMessage("Your subscription is not expired yet!");
					return document;
				}
			}

			Float itemNetPrice = newSubscriptionPlanDTO.getAmount();
			cartNetPrice += itemNetPrice;

			cartSummaryDTO.setUserCarts(listUserCarts);
			// GST percentage calculated 0% for now

			cartGSTPrice = 0F;

			/* cartGSTPrice = (float) (cartNetPrice * (0.18)); */

			// cart total from cart purchase amount
			cartTotalPrice = cartNetPrice + cartGSTPrice;
			cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
			cartSummaryDTO.setCartNetPrice(cartNetPrice);

			String twoDecimalPrice = String.format("%.2f", cartTotalPrice);
			cartSummaryDTO.setCartTotalPrice(Float.parseFloat(twoDecimalPrice));

			// create order data
			StudentOrder studentOrder = new StudentOrder();
			studentOrder.setUserSurId(userPrincipal.getUserSurId());
			studentOrder.setOrderDate(Instant.now());
			studentOrder.setOrderStatus("Pending");
			studentOrder.setNetAmount(cartNetPrice);
			studentOrder.setGstAmount(cartGSTPrice);
			studentOrder.setTotalAmount(Float.parseFloat(twoDecimalPrice));
			// set order id
			studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
			studentOrder = studentOrderRepository.save(studentOrder);
			cartSummaryDTO.setStudentOrder(studentOrder);
			if (studentOrder != null) {
				// save all staging student subscription data
				List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
				StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
				// stagingStudentSubscription.setIdBatch(newSubscriptionPlanDTO.getIdBatch());
				stagingStudentSubscription.setIdProduct(newSubscriptionPlanDTO.getIdProduct());
				stagingStudentSubscription.setIdProductGroup(newSubscriptionPlanDTO.getIdProductGroup());
				stagingStudentSubscription.setIdproductLine(newSubscriptionPlanDTO.getIdProductLine());
				stagingStudentSubscription.setIdStudent(student.getIdStudent());
				stagingStudentSubscription.setPurchaseAmount(newSubscriptionPlanDTO.getAmount().toString());
				stagingStudentSubscription.setPurchaseLevel(newSubscriptionPlanDTO.getProductCd());// productCd
				stagingStudentSubscription.setPurchaseType(cartSummaryDTO.getPurchaseType());
				stagingStudentSubscription.setSubscriptionType(newSubscriptionPlanDTO.getDurationCode());
				stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
				stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
				stagingStudentSubscriptions.add(stagingStudentSubscription);
				stagingStudentSubscription.setPaymentStatus("Pending");
				stagingStudentSubscription.setUserSurId(userPrincipal.getUserSurId());
				stagingStudentSubscriptionRepository.saveAll(stagingStudentSubscriptions);
				cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
				// get merchant details and call initiate payment method
				TreeMap<String, String> responseIntiatePayment = new TreeMap<>();
				Merchant merchant = merchantRepository.findByMerchantId(merchantId);
				if(merchant==null)
					throw new AppException("No merchant record found for this merchantId :"+merchantId);
				
				if (merchant != null) {
					PaytmDetailsDTO paytmDetailsDTO = new PaytmDetailsDTO();
					paytmDetailsDTO.setCustomerId(studentOrder.getUserSurId().toString());
					paytmDetailsDTO.setMerchantEmail(merchant.getMerchantEmail());
					paytmDetailsDTO.setMerchantId(merchant.getMerchantId());
					paytmDetailsDTO.setMerchantKey(merchant.getMerchantKey());
					paytmDetailsDTO.setMerchantPhone(merchant.getMerchantPhone());
					paytmDetailsDTO.setTransactionAmount(studentOrder.getTotalAmount().toString());
					paytmDetailsDTO.setOrderId(studentOrder.getOrderId());
					try {
						responseIntiatePayment = this.intiatePayment(paytmDetailsDTO);
						cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
						document.setData(cartSummaryDTO);
						document.setMessage("Cart summary with payment parameters");
						document.setStatusCode(HttpStatus.OK.value());
					} catch (Exception e) {
						LOGGER.error(e.getMessage());
						document.setData(null);
						document.setMessage("Error while creating cart summary");
						document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return document;
					}
				}
			}

		}

		catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setData(null);
			document.setMessage("Error while creating cart summary");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		}

		return document;
	}

	@Override
	public Document<Object> getSubscribedProductForUser() {

		Document<Object> result = new Document<>();

		Map<String, Object> subscriptionDetails = new HashMap<>();
		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null)
				throw new AppException("Invalid User info found in session.");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findFirstByUserSurIdAndIdproductLineAndActiveFlag(userPrincipal.getUserSurId(), 11L, Boolean.TRUE);

			UserCreatedDTO ucDto = userService.getUserCreatedWithinTwoDays(userPrincipal.getUserSurId());

			if (studentSubscription == null) {
				// free subscription completed flag
				subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
				subscriptionDetails.put("existingSubscription", false);
				subscriptionDetails.put("expiredSubscription", false);
				subscriptionDetails.put("showRenewal", false);
				subscriptionDetails.put("subscriptionType", null);
				subscriptionDetails.put("idStudentSubscription", null);
				subscriptionDetails.put("nextPaymentDate", null);
				subscriptionDetails.put("productInfo", null);
				result.setData(subscriptionDetails);
				result.setMessage("No active payment subscription found for the current user.");
				result.setStatusCode(HttpStatus.OK.value());

			} else {

				StudentOrder studentOrder = studentOrderRepository
						.findByIdStudentOrder(studentSubscription.getIdStudentOrder());

				if (studentOrder == null)
					throw new AppException("No Student order found.");

				Product product = productRepository.findByIdProduct(studentSubscription.getIdProduct());

				if (product == null)
					throw new AppException("Invalid product information found in user subscription.");

				String description = "##Self-Assessment & Study Materials##Academic Live Classes for all Subjects##Access to all Academic Animated Videos##Learn from Conceptual Learning Videos##Talent Showcasing Platform##Extra Curricular Training##Spoken English Classes";

				ProductPricingDTO productPricingDTO = new ProductPricingDTO();

				if (studentSubscription.getPurchaseLevel().equalsIgnoreCase("AD_FREE_TRIAL")) {
					productPricingDTO.setAmount(0.0F);
					productPricingDTO.setAmountName("0.0");
					productPricingDTO.setDurationName(studentSubscription.getSubscriptionType());
					productPricingDTO.setIdProduct(studentSubscription.getIdProduct());
					productPricingDTO.setIdProductGroup(studentSubscription.getIdProductGroup());
					productPricingDTO.setIdProductLine(studentSubscription.getIdproductLine());
					productPricingDTO.setPlanDescription(description);
					productPricingDTO.setProductName("TRIAL PLAN");

				} else {
					ProductDuration productDuration = productDurationRepository
							.findByDurationCode(studentSubscription.getSubscriptionType());

					if (productDuration == null)
						throw new AppException("No product duration data found.");

					ProductAmount productAmount = productAmountRepository.findByAmount(Float
							.parseFloat(studentOrder.getActualAmount() == null ? studentSubscription.getPurchaseAmount()
									: studentOrder.getActualAmount().toString()));

					if (productAmount == null)
						throw new AppException("No product amount data found.");

					ProductPricing productPricing = null;

					if (productDuration != null && productAmount != null) {
						productPricing = productPricingRepository.findByIdProductAmountAndIdProductDurationAndIdProduct(
								productAmount.getIdProductAmount(), productDuration.getIdProductDuration(),
								studentSubscription.getIdProduct());
					}

					String planDescription = productPricing != null ? productPricing.getPlanDescription()
							: "##Self-Assessment & Study Materials##Academic Live Classes for all Subjects##Access to all Academic Animated Videos##Learn from Conceptual Learning Videos##Talent Showcasing Platform##Extra Curricular Training##Spoken English Classes";

					productPricingDTO.setAmount(productAmount.getAmount());
					productPricingDTO.setAmountName(productAmount.getAmountName());
					productPricingDTO.setDurationName(studentSubscription.getSubscriptionType());
					productPricingDTO.setIdProduct(studentSubscription.getIdProduct());
					productPricingDTO.setIdProductGroup(studentSubscription.getIdProductGroup());
					productPricingDTO.setIdProductLine(studentSubscription.getIdproductLine());
					productPricingDTO.setPlanDescription(planDescription);
					productPricingDTO.setProductName(product.getProductName());
				}
				// two days before show renewal button
				LocalDate dateExist = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
				LocalDate dateNextPaymentDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia)
						.toLocalDate();
				LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
				if (dateExist.isBefore(dateToday) || dateExist.equals(dateToday)) {
					// free subscription completed flag
					subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
					subscriptionDetails.put("existingSubscription", true);
					subscriptionDetails.put("expiredSubscription",
							dateToday.isAfter(dateNextPaymentDate) ? true : false);
					subscriptionDetails.put("showRenewal", true);
					subscriptionDetails.put("subscriptionType", studentSubscription.getSubscriptionType());
					subscriptionDetails.put("idStudentSubscription", studentSubscription.getIdStudentSubscription());
					subscriptionDetails.put("nextPaymentDate", studentSubscription.getNextPaymentDate());
					subscriptionDetails.put("productInfo", productPricingDTO);
					result.setData(subscriptionDetails);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Your product subscription is expired!");
				} else {
					// free subscription completed flag
					subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
					subscriptionDetails.put("existingSubscription", true);
					subscriptionDetails.put("expiredSubscription", false);
					subscriptionDetails.put("showRenewal", false);
					subscriptionDetails.put("subscriptionType", studentSubscription.getSubscriptionType());
					subscriptionDetails.put("idStudentSubscription", studentSubscription.getIdStudentSubscription());
					subscriptionDetails.put("nextPaymentDate", studentSubscription.getNextPaymentDate());
					subscriptionDetails.put("productInfo", productPricingDTO);
					result.setData(subscriptionDetails);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Your product subscription is not expired yet!");
				}

			}

			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public CartSummaryDTO saveNewUserSubscriptionNewSignup(NewSubscriptionPlanDTO newSubscriptionPlanDTO, User user) {
		{

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();

			try {

				cartSummaryDTO.setPurchaseType("NEW");

				// adds user carts list
				List<UserCartResponseDTO> listUserCarts = new ArrayList<UserCartResponseDTO>();

				// get cart total amount
				Float cartTotalPrice = 0F;
				Float cartNetPrice = 0F;
				Float cartGSTPrice = 0F;

				Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());

				if (student == null)
					throw new AppException("No Student Record found ");

				StudentSubscription studentSubscription = studentSubscriptionRepository
						.findByUserSurIdAndIdproductLineAndActiveFlag(user.getUserSurId(), 11L, Boolean.TRUE);
				if (studentSubscription != null) {
					LocalDate dateExist = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
					LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
					if (!dateExist.isBefore(dateToday) && !dateExist.equals(dateToday)) {
						throw new AppException("Your subscription is not expired yet!");
					}
				}

				Float itemNetPrice = newSubscriptionPlanDTO.getAmount();

				if (newSubscriptionPlanDTO.getCouponCode() != null
						&& !newSubscriptionPlanDTO.getCouponCode().isEmpty()) {
					LocalDateTime currentDateTime = LocalDateTime.now();

					Coupon coupon = couponRepository.findByCouponCode(newSubscriptionPlanDTO.getCouponCode());

					Redemption redemption = redemptionRepository.findByCouponCodeAndIdVlUser(coupon.getCouponCode(),
							user.getUserSurId());

					if (Boolean.FALSE.equals(coupon.getIsActive()))
						throw new AppException("Sorry, the coupon code entered is not currently active");

					if (currentDateTime.isAfter(coupon.getEndDate()))
						throw new AppException("Sorry, This coupon has expired.");

					if (redemption != null) {
						if (redemption.getIdVlUser().equals(user.getUserSurId())) {
							throw new AppException("Sorry, This coupon has already been claimed.");
						}
					}
					if (coupon.getTotalCount() < coupon.getUsedCount())
						throw new AppException("Sorry, This coupon has already been claimed.");

					switch (coupon.getCouponType()) {
					case "DISCOUNT_PERCENT":
						Float discountAmount = newSubscriptionPlanDTO.getAmount() * (coupon.getDiscount() / 100);
						itemNetPrice = newSubscriptionPlanDTO.getAmount() - discountAmount;
						break;
					case "DISCOUNT_PRICE":
						itemNetPrice = itemNetPrice - coupon.getDiscount();
						break;
					}

				}

				cartNetPrice += itemNetPrice;

				cartSummaryDTO.setUserCarts(listUserCarts);
				// GST percentage calculated 0% for now

				cartGSTPrice = 0F;

				/* cartGSTPrice = (float) (cartNetPrice * (0.18)); */

				// cart total from cart purchase amount
				cartTotalPrice = cartNetPrice + cartGSTPrice;
				cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
				cartSummaryDTO.setCartNetPrice(cartNetPrice);

				String twoDecimalPrice = String.format("%.2f", cartTotalPrice);
				cartSummaryDTO.setCartTotalPrice(Float.parseFloat(twoDecimalPrice));

				// create order data
				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(user.getUserSurId());
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Pending");
				studentOrder.setNetAmount(cartNetPrice);
				studentOrder.setGstAmount(cartGSTPrice);
				studentOrder.setTotalAmount(Float.parseFloat(twoDecimalPrice));
				if (newSubscriptionPlanDTO.getCouponCode() != null) {
					studentOrder.setCouponCode(newSubscriptionPlanDTO.getCouponCode());
					studentOrder.setActualAmount(newSubscriptionPlanDTO.getAmount());
				}

				// set order id
				studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
				studentOrder = studentOrderRepository.save(studentOrder);
				cartSummaryDTO.setStudentOrder(studentOrder);
				if (studentOrder != null) {
					// save all staging student subscription data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
					// stagingStudentSubscription.setIdBatch(newSubscriptionPlanDTO.getIdBatch());
					stagingStudentSubscription.setIdProduct(newSubscriptionPlanDTO.getIdProduct());
					stagingStudentSubscription.setIdProductGroup(newSubscriptionPlanDTO.getIdProductGroup());
					stagingStudentSubscription.setIdproductLine(newSubscriptionPlanDTO.getIdProductLine());
					stagingStudentSubscription.setIdStudent(student.getIdStudent());
					stagingStudentSubscription.setPurchaseAmount(twoDecimalPrice);
					stagingStudentSubscription.setPurchaseLevel(newSubscriptionPlanDTO.getProductCd());// productCd
					stagingStudentSubscription.setPurchaseType(cartSummaryDTO.getPurchaseType());
					stagingStudentSubscription.setSubscriptionType(newSubscriptionPlanDTO.getDurationCode());
					stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
					stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
					stagingStudentSubscriptions.add(stagingStudentSubscription);
					stagingStudentSubscription.setPaymentStatus("Pending");
					stagingStudentSubscription.setUserSurId(user.getUserSurId());
					stagingStudentSubscriptionRepository.saveAll(stagingStudentSubscriptions);
					cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);

					// get merchant details and call initiate payment method
					TreeMap<String, String> responseIntiatePayment = new TreeMap<>();
					Merchant merchant = merchantRepository.findByMerchantId(merchantId);
					if (merchant != null) {
						PaytmDetailsDTO paytmDetailsDTO = new PaytmDetailsDTO();
						paytmDetailsDTO.setCustomerId(studentOrder.getUserSurId().toString());
						paytmDetailsDTO.setMerchantEmail(merchant.getMerchantEmail());
						paytmDetailsDTO.setMerchantId(merchant.getMerchantId());
						paytmDetailsDTO.setMerchantKey(merchant.getMerchantKey());
						paytmDetailsDTO.setMerchantPhone(merchant.getMerchantPhone());
						paytmDetailsDTO.setTransactionAmount(studentOrder.getTotalAmount().toString());
						paytmDetailsDTO.setOrderId(studentOrder.getOrderId());

						try {
							responseIntiatePayment = this.intiatePayment(paytmDetailsDTO);
							System.out.println(responseIntiatePayment);
							cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
						}
					}

				}

			}

			catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage());

			}
			return cartSummaryDTO;

		}
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}


	@SuppressWarnings("null")
	@Override
	public Document<?> uploadBatchMannualSubscription(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<>();
		List<String> errorLogList = new ArrayList<>();
		
		LocalDate dateExist = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(12);
		Instant extendedDate = dateExist.atStartOfDay(zoneIndia).toInstant();
		

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null) {
					throw new AppException("Invalid User");
				}
			}

			errorLogList.add("Manual Subscription Creation Batch Process started at: " + Instant.now());
			errorLogList
					.add("Manual Subscription Uploaded started by the user : " + userPrincipal.getUserSurId() + ".");

			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			}

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// Read CSV data
			MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
					.readValues(file);

			// read CSV data
			List<Map<String, String>> readAll = mappingIterator.readAll();

			readAll.forEach(o -> {

				Map<String, String> header = o;

				if (!header.containsKey("phone_number")) {
					throw new AppException("Invalid file or missing header phone number in the uploade csv.");
				}
				if (!header.containsKey("offer_given")) {
					throw new AppException("Invalid file or missing header offer given in the uploade csv.");
				}
			});

			List< MannualUpdateDetailsDTO> subscrMap = new ArrayList<>();

			int totalRecords = 0;
			int inValidCount = 0;

			for (Map<String, String> row : readAll) {
				totalRecords++;

				String number = row.get("phone_number").trim();

				MannualUpdateDetailsDTO mpuDetailsDTO = new MannualUpdateDetailsDTO();
				mpuDetailsDTO.setUserPrincipal(userPrincipal);				
				mpuDetailsDTO.setPhone(number);
				mpuDetailsDTO.setNextSubscriptionEndDate(extendedDate);
				mpuDetailsDTO.setNoOfMonths(12);
				mpuDetailsDTO.setLastSubscriptionEndDate(Instant.now());
				mpuDetailsDTO.setType("Annual");
				mpuDetailsDTO.setIsSubscriptionExists(false);
				mpuDetailsDTO.setMessage( inValidCount>0 ? "Subscription can be created" 
						: "Subscription Created");

				if (!"1999".equals(row.get("offer_given")) || !row.get("offer_given").equalsIgnoreCase("1999")) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("only 1999 plan supported");

				}
				
				if (row.get("phone_number").isEmpty()) {

					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					errorLogList.add(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
						
				}

				User user = userRepository.findByMobileNumber(number);
				if (user == null) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("User not found");
				}
				
				Student student = studentRepository.findByUser(user);
				if (student == null) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("Invalid student");
				}
				
				Boolean studentSub = studentSubscriptionRepository.
						existsByIdproductLineAndUserSurIdAndActiveFlag(11L, user.getUserSurId(), true);
				if(studentSub) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("Subscription Already Exists");
					mpuDetailsDTO.setIsSubscriptionExists(true);
				}
				
				mpuDetailsDTO.setName(user.getFirstName());
				mpuDetailsDTO.setPlan(1999f);
				mpuDetailsDTO.setPlanType("ANNUAL_PREMIUM");
				mpuDetailsDTO.setIdStudent(student.getIdStudent());
				mpuDetailsDTO.setUserSurId(user.getUserSurId());
				
				if(!mpuDetailsDTO.getisValid())
					inValidCount++;
				
				subscrMap.add(mpuDetailsDTO);
				
			}
			
			int sucessfullCount = (int) subscrMap.stream().filter(s -> s.getisValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

			if (rejectedRecords <= 0) {
				Document<?> subscrResponse = this.newBulkSubscription(subscrMap);
	        	System.out.println(subscrResponse.getData());
	        	errorLogList.add("Uploaded Successfully.");
			}

			errorLogList.add("Total no of records : " + totalRecords + ".");
			errorLogList.add("Total no of records valid: " + sucessfullCount + ".");
			errorLogList.add("Total no of records invalid: " + rejectedRecords + ".");
			errorLogList.add("Manual Subscription Creation Batch Process ended at: " + Instant.now());

			dataMap.put("updatedRecord", subscrMap);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", sucessfullCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			errorLogList.add("Error: " + e.getMessage());
			errorLogList.add("Manual Subscription Terminated Batch Process  at: " + Instant.now());
			dataMap.put("log", errorLogList);
			e.printStackTrace();
			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {

			String fileName = "MannualSubscriptionBatchUploadLog" + Instant.now() + ".txt";

			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}

				File logFile = new File(fileName);
				
				fileUploadService.uploadFileToS3Bucket("/logs/creation/subscription/", fileName, logFile);

				boolean isDeletedFile1 = logFile.delete();
				LOGGER.info("Log file deleted from the system : " + isDeletedFile1);
				boolean isDeletedFile2 = file.delete();
				LOGGER.info("file deleted from the system : " + isDeletedFile2);

			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return result;

	}


	public Document<MannualUpdateDetailsDTO> newBulkSubscription(List<MannualUpdateDetailsDTO> paramsList) {
		Document<MannualUpdateDetailsDTO> result = new Document<>();
		MannualUpdateDetailsDTO mannualUpdateDetailsDTO = new MannualUpdateDetailsDTO();
		try {

			LocalDate dateExist = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(12);
			Instant extendedDate = dateExist.atStartOfDay(zoneIndia).toInstant();

			for (MannualUpdateDetailsDTO subscriptionParamsDTO : paramsList) {

				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(subscriptionParamsDTO.getUserSurId());
				studentOrder.setCreatedAt(Instant.now());
				studentOrder.setUpdatedAt(Instant.now());
				studentOrder.setDisputeFlag(false);
				studentOrder.setNetAmount(1999f);
				studentOrder.setOrderId(
						randomStringGenerator.generateRandomOrderId(subscriptionParamsDTO.getUserSurId()));
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Success");
				studentOrder.setTotalAmount(1999f);
				studentOrder.setUpdatedBy(subscriptionParamsDTO.getUserSurId());
				studentOrder.setRemarks((studentOrder.getRemarks() == null ? " Bulk Subscription Created by User id:"
						+ subscriptionParamsDTO.getUserPrincipal().getUserSurId() + " User Name:"
						+ subscriptionParamsDTO.getUserPrincipal().getUsername() + " at " + Instant.now()
						+ " for Subscription Type: New  Plan Type ANNUAL_PREMIUM 1999.00"
						: " Bulk Subscription Created: User id:"
								+ subscriptionParamsDTO.getUserPrincipal().getUserSurId() + " User Name:"
								+ subscriptionParamsDTO.getUserPrincipal().getUsername() + " at " + Instant.now()
								+ " for Subscription Type:" + "New "
								+ "Plan Type Plan Type ANNUAL_PREMIUM 1999.00 \n" + studentOrder.getRemarks()));

				studentOrder = studentOrderRepository.save(studentOrder);

				StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();

				stagingStudentSubscription.setUserSurId(studentOrder.getUserSurId());
				stagingStudentSubscription.setUpdatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedAt(Instant.now());
				stagingStudentSubscription.setUpdatedAt(Instant.now());
				stagingStudentSubscription.setActiveFlag(true);
				stagingStudentSubscription.setIdProduct(1222l);
				stagingStudentSubscription.setIdProductGroup(51l);
				stagingStudentSubscription.setIdproductLine(11l);
				stagingStudentSubscription.setIdStudent(subscriptionParamsDTO.getIdStudent());
				stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
				stagingStudentSubscription.setLastPaymentDate(Instant.now());
				stagingStudentSubscription.setNextPaymentDate(extendedDate);
				stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
				stagingStudentSubscription.setPaymentMode("Mannually Created");
				stagingStudentSubscription.setPaymentStatus("Success");
				stagingStudentSubscription.setPurchaseAmount(String.valueOf(1999.00));
				stagingStudentSubscription.setPurchaseDate(Instant.now());
				stagingStudentSubscription.setPurchaseLevel("ANNUAL_PREMIUM");
				stagingStudentSubscription.setPurchaseType("NEW");
				stagingStudentSubscription.setSubscriptionEndDate(extendedDate);
				stagingStudentSubscription.setSubscriptionType("ANNUAL");
				stagingStudentSubscription.setTransactionAmount(1999f);
				stagingStudentSubscription.setTransactionDate(Instant.now());

				stagingStudentSubscription = stagingStudentSubscriptionRepository.save(stagingStudentSubscription);

				StudentSubscription stuSubscription = new StudentSubscription();
				stuSubscription.setUserSurId(stagingStudentSubscription.getUserSurId());
				stuSubscription.setActiveFlag(true);
				stuSubscription.setCreatedAt(Instant.now());
				stuSubscription.setCreatedBy(stagingStudentSubscription.getUserSurId());
				stuSubscription.setUpdatedAt(Instant.now());
				stuSubscription.setIdBatch(stagingStudentSubscription.getIdBatch());
				stuSubscription.setIdProduct(stagingStudentSubscription.getIdProduct());
				stuSubscription.setIdProductGroup(stagingStudentSubscription.getIdProductGroup());
				stuSubscription.setIdproductLine(stagingStudentSubscription.getIdproductLine());
				stuSubscription.setIdStudent(stagingStudentSubscription.getIdStudent());
				stuSubscription.setIdStudentOrder(stagingStudentSubscription.getIdStudentOrder());
				stuSubscription.setLastPaymentDate(stagingStudentSubscription.getLastPaymentDate());
				stuSubscription.setNextPaymentDate(stagingStudentSubscription.getNextPaymentDate());
				stuSubscription.setPurchaseAmount(stagingStudentSubscription.getPurchaseAmount());
				stuSubscription.setPurchaseDate(stagingStudentSubscription.getPurchaseDate());
				stuSubscription.setPurchaseLevel(stagingStudentSubscription.getPurchaseLevel());
				stuSubscription.setPurchaseType(stagingStudentSubscription.getPurchaseType());
				stuSubscription.setSubscriptionEndDate(stagingStudentSubscription.getSubscriptionEndDate());
				stuSubscription.setSubscriptionType(stagingStudentSubscription.getSubscriptionType());
				stuSubscription.setUpdatedAt(stagingStudentSubscription.getUpdatedAt());
				stuSubscription.setUpdatedBy(stagingStudentSubscription.getUpdatedBy());

				stuSubscription = studentSubscriptionRepository.save(stuSubscription);
				
			    SubscriptionClick subscriptionClick=subscriptionClickRepository.findByVlUserIdAndStatus(subscriptionParamsDTO.getUserSurId(), "Pending");

			    if(subscriptionClick!=null) {
			    	
			    	subscriptionClick.setStatus("Completed");
			    	
			    	subscriptionClickRepository.save(subscriptionClick);
			    	
			    	studentOrder.setRemarks("Subscription created as per student request. Created by Admin [ User Name: " 
			    		    + subscriptionParamsDTO.getUserPrincipal().getFirstName() 
			    		    + ",  User ID: " 
			    		    + subscriptionParamsDTO.getUserPrincipal().getUserSurId() + "  ] at " +Instant.now());
			    	
			    	studentOrder = studentOrderRepository.save(studentOrder);
			    }
			    
			    
			    
//				mannualUpdateDetailsDTO.setClassStandard(student.getIdClassStandard());
				mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
				mannualUpdateDetailsDTO.setLastSubscriptionEndDate(stuSubscription.getCreatedAt());
				mannualUpdateDetailsDTO.setMessage("Subscription created successfully with " + 12 + " months .");
//				mannualUpdateDetailsDTO.setName(subscriptionParamsDTO.getFirstName());
				mannualUpdateDetailsDTO.setNextSubscriptionEndDate(stuSubscription.getSubscriptionEndDate());
				mannualUpdateDetailsDTO.setNoOfMonths(12);
				mannualUpdateDetailsDTO.setPhone(subscriptionParamsDTO.getPhone());
				mannualUpdateDetailsDTO.setPlan(1999f);
				mannualUpdateDetailsDTO.setPlanType("ANNUAL_PREMIUM");
				mannualUpdateDetailsDTO.setType("Creation");
				mannualUpdateDetailsDTO.setUserSurId(subscriptionParamsDTO.getUserSurId());

			}

			result.setData(mannualUpdateDetailsDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Subscription created successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			result.setData(mannualUpdateDetailsDTO);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("error occured.");
		}

		return result;
	}

	@Cacheable(value = "academicSubjectCache")
	@Override
	public Document<List<StudentSubjectResDTO>> getSubscribedSubStatus(Long idClassStandard, Long idSyllabus, Long idState) {
		Document<List<StudentSubjectResDTO>> result = new Document<>();
		List<StudentSubjectResDTO> studSubscDtoList = new ArrayList<>();
		StudentSubjectResDTO studentSubjectResDTO;
		try {



			List<Product> productList = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(5L,
							idClassStandard, idSyllabus,idState, Boolean.TRUE);
			
			if (productList.isEmpty()) {
				result.setData(studSubscDtoList);
				result.setMessage("No subjects are found.");
				result.setStatusCode(200);
				return result;
			}
				
			
				for (Product product : productList) {
					
					

					
					studentSubjectResDTO = new StudentSubjectResDTO();
					studentSubjectResDTO.setIdProduct(product.getIdProduct());
					Subject s = subjectRepository.findByIdSubject(product.getIdSubject());
					studentSubjectResDTO.setSubjectName(s.getSubjectName());
					studentSubjectResDTO.setColor(s.getColor());
					studentSubjectResDTO.setIdSubject(product.getIdSubject());
					String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp";
					studentSubjectResDTO.setImageURL(imageUrl);
					studSubscDtoList.add(studentSubjectResDTO);
				}
				
				
				studSubscDtoList = studSubscDtoList.stream().filter(distinctByKey(StudentSubjectResDTO::getIdProduct))
				.sorted((s1, s2) -> s1.getIdSubject().compareTo(s2.getIdSubject())).collect(Collectors.toList());

				result.setData(studSubscDtoList);
				result.setMessage("Success");
				result.setStatusCode(200);
		
			

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}

		return result;
		
	}

	@Cacheable(value = "extracurricularSubjectCache")
	@Override
	public Document<List<StudentSubjectResDTO>> getSubscribedExtraCurSubStatus() {
	
		Document<List<StudentSubjectResDTO>> result = new Document<>();
		List<StudentSubjectResDTO> studSubscDtoList = new ArrayList<>();
		StudentSubjectResDTO studentSubjectResDTO;
		try {
				
			List<Product> productList = productRepository.findByIdProductLineAndActiveFlag(6L,Boolean.TRUE);
			
			
			
			
			if (productList.isEmpty()) {
				result.setData(studSubscDtoList);
				result.setMessage("No subjects are found.");
				result.setStatusCode(200);
				return result;
			}
			
		   for (Product product : productList) {
			   

			   
			   studentSubjectResDTO = new StudentSubjectResDTO();
			   studentSubjectResDTO.setIdProduct(product.getIdProduct());
					Subject s = subjectRepository.findByIdSubject(product.getIdSubject());
					studentSubjectResDTO.setSubjectName(s.getSubjectName());
					studentSubjectResDTO.setColor(s.getColor());
					studentSubjectResDTO.setIdSubject(product.getIdSubject());
					String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp";
					studentSubjectResDTO.setImageURL(imageUrl);

					studSubscDtoList.add(studentSubjectResDTO);
				}
		   
				result.setData(studSubscDtoList);
				result.setMessage("Success");
				result.setStatusCode(200); 
		
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}

		return result;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	@SuppressWarnings("null")
	@Override
	public Document<Map<String, Object>> batchSubscriptionRenewal(MultipartFile batchFile) {
		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<>();
		List<String> errorLogList = new ArrayList<>();

		LocalDate dateExist = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(12);
		Instant extendedDate = dateExist.atStartOfDay(zoneIndia).toInstant();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {

			List<Map<String, String>> readAll = null;
			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null) {
					throw new AppException("Invalid User");
				}
			}

			errorLogList.add("Manual Subscription Renewal Creation Batch Process started at: " + Instant.now());
			errorLogList.add(
					"Manual Subscription Renewal Uploaded started by the user : " + userPrincipal.getUserSurId() + ".");

			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (!fileExtension.isPresent() || !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extensions not supported. Please upload a .csv file.");
				throw new AppException("Invalid file format.");
			}

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// Read CSV data
			MappingIterator<Map<String, String>> mappingIterator = csvMapper.readerFor(Map.class).with(csvSchema)
					.readValues(file);

			// read CSV data
			readAll = mappingIterator.readAll();

			readAll.forEach(o -> {

				Map<String, String> header = o;

				if (!header.containsKey("phone_number")) {
					throw new AppException("Invalid file or missing header phone number in the uploade csv.");
				}
				if (!header.containsKey("id_product")) {
					throw new AppException("Invalid file or missing header offer given in the uploade csv.");
				}

			});

			List<MannualUpdateDetailsDTO> subscrMap = new ArrayList<>();

			int totalRecords = 0;
			int inValidCount = 0;

			List<ProductDetailDTO> activePlans = studentSubscriptionRepository.findActiveProducts();

			Long idProduct = null;
			String number = null;
			for (Map<String, String> row : readAll) {
				totalRecords++;

				number = row.get("phone_number").trim();

				idProduct = Long.parseLong(row.get("id_product"));

				ProductDetailDTO productDetailDTO = getProductDetails(idProduct, activePlans);

				MannualUpdateDetailsDTO mpuDetailsDTO = new MannualUpdateDetailsDTO();
				mpuDetailsDTO.setUserPrincipal(userPrincipal);
				mpuDetailsDTO.setPhone(number);
				mpuDetailsDTO.setNextSubscriptionEndDate(extendedDate);
				mpuDetailsDTO.setNoOfMonths(12);
				mpuDetailsDTO.setLastSubscriptionEndDate(Instant.now());
				mpuDetailsDTO.setType("Annual");
				mpuDetailsDTO.setIsSubscriptionExists(false);
				mpuDetailsDTO.setIsValid(true);
				
			//	mpuDetailsDTO.setMessage(inValidCount >0 ? "Subscription can be created" : "Subscription Created");

				if (productDetailDTO == null) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("No product plans found");
				} else {
					mpuDetailsDTO.setIdProduct(productDetailDTO.getIdProduct());
					mpuDetailsDTO.setPurchaseLevel(productDetailDTO.getSubscriptionPlan());
					mpuDetailsDTO.setPlan(productDetailDTO.getAmount());
					mpuDetailsDTO.setPlanType(productDetailDTO.getSubscriptionPlan());
				}

				if (row.get("phone_number").isEmpty()) {

					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					errorLogList.add(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");

				}

				User user = userRepository.findByMobileNumber(number);
				if (user == null) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("User not found");
				}

				Student student = studentRepository.findByUser(user);
				if (student == null) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("Invalid student");
				}

				Boolean studentSub = studentSubscriptionRepository.existsByIdproductLineAndUserSurIdAndActiveFlag(11L,
						user.getUserSurId(), true);
				if (studentSub) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("Subscription Already Exists");
					mpuDetailsDTO.setIsSubscriptionExists(true);
				}

				Boolean previousSubcription = studentSubscriptionRepository.existsByIdproductLineAndUserSurId(11L,
						user.getUserSurId());
				if (!previousSubcription) {
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage("No previous subscription found");
				}

				mpuDetailsDTO.setName(user.getFirstName());

				mpuDetailsDTO.setIdStudent(student.getIdStudent());
				mpuDetailsDTO.setUserSurId(user.getUserSurId());

				if (!mpuDetailsDTO.getisValid())
					inValidCount++;
				
			    if(mpuDetailsDTO.getisValid()){
					mpuDetailsDTO.setMessage("Subscription can be created");
				}
				

				subscrMap.add(mpuDetailsDTO);

			}

			if(inValidCount==0) 
				subscrMap.stream().forEach(s->s.setMessage("Subscription created"));
				
			
			

			int sucessfullCount = (int) subscrMap.stream().filter(s -> s.getisValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

			if (rejectedRecords <= 0) {
				Document<?> subscrResponse = this.bulkSubscriptionRenewal(subscrMap);
				System.out.println(subscrResponse.getData());
				errorLogList.add("Uploaded Successfully.");
			}

			errorLogList.add("Total no of records : " + totalRecords + ".");
			errorLogList.add("Total no of records valid: " + sucessfullCount + ".");
			errorLogList.add("Total no of records invalid: " + rejectedRecords + ".");
			errorLogList.add("Manual Subscription Creation Batch Process ended at: " + Instant.now());

			dataMap.put("updatedRecord", subscrMap);
			dataMap.put("log", errorLogList);
			dataMap.put("totalRecord", totalRecords);
			dataMap.put("totalSuccessfullRecord", sucessfullCount);
			dataMap.put("totalRejectedRecord", rejectedRecords);

			result.setData(dataMap);
			result.setMessage("Request successful.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			errorLogList.add("Error: " + e.getMessage());
			errorLogList.add("Manual Subscription Terminated Batch Process  at: " + Instant.now());
			dataMap.put("log", errorLogList);
			e.printStackTrace();
			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {

			String fileName = "MannualRenewalBatchUploadLog" + Instant.now() + ".txt";

			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}

				writer.flush();
				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/renewal/", fileName, logFile);

				boolean isDeletedFile1 = logFile.delete();

				LOGGER.info("Log file deleted from the system : " + isDeletedFile1);

				boolean isDeletedFile2 = file.delete();
				LOGGER.info("file deleted from the system : " + isDeletedFile2);

			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return result;

	}
	
	public Document<MannualUpdateDetailsDTO> bulkSubscriptionRenewal(List<MannualUpdateDetailsDTO> paramsList) {
		Document<MannualUpdateDetailsDTO> result = new Document<>();
		MannualUpdateDetailsDTO mannualUpdateDetailsDTO = new MannualUpdateDetailsDTO();
		try {

			LocalDate dateExist = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(12);
			Instant extendedDate = dateExist.atStartOfDay(zoneIndia).toInstant();
			

			for (MannualUpdateDetailsDTO subscriptionParamsDTO : paramsList) {

				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(subscriptionParamsDTO.getUserSurId());
				studentOrder.setCreatedAt(Instant.now());
				studentOrder.setUpdatedAt(Instant.now());
				studentOrder.setDisputeFlag(false);
				studentOrder.setNetAmount(subscriptionParamsDTO.getPlan());
				studentOrder.setOrderId(
						randomStringGenerator.generateRandomOrderId(subscriptionParamsDTO.getUserSurId()));
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Success");
				studentOrder.setTotalAmount(subscriptionParamsDTO.getPlan());
				studentOrder.setUpdatedBy(subscriptionParamsDTO.getUserSurId());
				studentOrder.setRemarks((studentOrder.getRemarks() == null ? " Bulk Subscription Renewal Created by User id:"
						+ subscriptionParamsDTO.getUserPrincipal().getUserSurId() + " User Name:"
						+ subscriptionParamsDTO.getUserPrincipal().getUsername() + " at " + Instant.now()
						+ " for Subscription Type: Renewal  Plan Type ANNUAL_PREMIUM  1999.00"
						: " Bulk Subscription Renewal Created: User id:"
								+ subscriptionParamsDTO.getUserPrincipal().getUserSurId() + " User Name:"
								+ subscriptionParamsDTO.getUserPrincipal().getUsername() + " at " + Instant.now()
								+ " for Subscription Type:" + "Renewal "
								+ "Plan Type Plan Type ANNUAL_PREMIUM 1999.00 \n" + studentOrder.getRemarks()));

				studentOrder = studentOrderRepository.save(studentOrder);

				StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();

				stagingStudentSubscription.setUserSurId(studentOrder.getUserSurId());
				stagingStudentSubscription.setUpdatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedAt(Instant.now());
				stagingStudentSubscription.setUpdatedAt(Instant.now());
				stagingStudentSubscription.setActiveFlag(true);
				stagingStudentSubscription.setIdProduct(subscriptionParamsDTO.getIdProduct());
				stagingStudentSubscription.setIdProductGroup(51l);
				stagingStudentSubscription.setIdproductLine(11l);
				stagingStudentSubscription.setIdStudent(subscriptionParamsDTO.getIdStudent());
				stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
				stagingStudentSubscription.setLastPaymentDate(Instant.now());
				stagingStudentSubscription.setNextPaymentDate(extendedDate);
				stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
				stagingStudentSubscription.setPaymentMode("Mannually Created");
				stagingStudentSubscription.setPaymentStatus("Success");
				stagingStudentSubscription.setPurchaseAmount(String.valueOf(subscriptionParamsDTO.getPlan()));
				stagingStudentSubscription.setPurchaseDate(Instant.now());
				stagingStudentSubscription.setPurchaseLevel(subscriptionParamsDTO.getPurchaseLevel());
				stagingStudentSubscription.setPurchaseType("RENEWAL");
				stagingStudentSubscription.setSubscriptionEndDate(extendedDate);
				stagingStudentSubscription.setSubscriptionType("ANNUAL");
				stagingStudentSubscription.setTransactionAmount(subscriptionParamsDTO.getPlan());
				stagingStudentSubscription.setTransactionDate(Instant.now());

				stagingStudentSubscription = stagingStudentSubscriptionRepository.save(stagingStudentSubscription);

				StudentSubscription stuSubscription = new StudentSubscription();
				stuSubscription.setUserSurId(stagingStudentSubscription.getUserSurId());
				stuSubscription.setActiveFlag(true);
				stuSubscription.setCreatedAt(Instant.now());
				stuSubscription.setCreatedBy(stagingStudentSubscription.getUserSurId());
				stuSubscription.setUpdatedAt(Instant.now());
				stuSubscription.setIdBatch(stagingStudentSubscription.getIdBatch());
				stuSubscription.setIdProduct(stagingStudentSubscription.getIdProduct());
				stuSubscription.setIdProductGroup(stagingStudentSubscription.getIdProductGroup());
				stuSubscription.setIdproductLine(stagingStudentSubscription.getIdproductLine());
				stuSubscription.setIdStudent(stagingStudentSubscription.getIdStudent());
				stuSubscription.setIdStudentOrder(stagingStudentSubscription.getIdStudentOrder());
				stuSubscription.setLastPaymentDate(stagingStudentSubscription.getLastPaymentDate());
				stuSubscription.setNextPaymentDate(stagingStudentSubscription.getNextPaymentDate());
				stuSubscription.setPurchaseAmount(stagingStudentSubscription.getPurchaseAmount());
				stuSubscription.setPurchaseDate(stagingStudentSubscription.getPurchaseDate());
				stuSubscription.setPurchaseLevel(stagingStudentSubscription.getPurchaseLevel());
				stuSubscription.setPurchaseType(stagingStudentSubscription.getPurchaseType());
				stuSubscription.setSubscriptionEndDate(stagingStudentSubscription.getSubscriptionEndDate());
				stuSubscription.setSubscriptionType(stagingStudentSubscription.getSubscriptionType());
				stuSubscription.setUpdatedAt(stagingStudentSubscription.getUpdatedAt());
				stuSubscription.setUpdatedBy(stagingStudentSubscription.getUpdatedBy());

				stuSubscription = studentSubscriptionRepository.save(stuSubscription);
				
				  SubscriptionClick subscriptionClick=subscriptionClickRepository.findByVlUserIdAndStatus(subscriptionParamsDTO.getUserSurId(), "Pending");

                  if(subscriptionClick!=null) {
			    	
			    	subscriptionClick.setStatus("Completed");
			    	
			    	subscriptionClickRepository.save(subscriptionClick);
			    	
			    	studentOrder.setRemarks("Subscription renewed as per student request. Created by Admin [ User Name: " 
			    		    + subscriptionParamsDTO.getUserPrincipal().getFirstName() 
			    		    + ", User ID: " 
			    		    + subscriptionParamsDTO.getUserPrincipal().getUserSurId() + " ] at " +Instant.now());
			    	
			    	studentOrder = studentOrderRepository.save(studentOrder);
			    }

//				mannualUpdateDetailsDTO.setClassStandard(student.getIdClassStandard());
				mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
				mannualUpdateDetailsDTO.setLastSubscriptionEndDate(stuSubscription.getCreatedAt());
				mannualUpdateDetailsDTO.setMessage("Subscription created successfully with " + 12 + " months .");
//				mannualUpdateDetailsDTO.setName(subscriptionParamsDTO.getFirstName());
				mannualUpdateDetailsDTO.setNextSubscriptionEndDate(stuSubscription.getSubscriptionEndDate());
				mannualUpdateDetailsDTO.setNoOfMonths(12);
				mannualUpdateDetailsDTO.setPhone(subscriptionParamsDTO.getPhone());
				mannualUpdateDetailsDTO.setPlan(subscriptionParamsDTO.getPlan());
				mannualUpdateDetailsDTO.setPlanType(subscriptionParamsDTO.getPurchaseLevel());
				mannualUpdateDetailsDTO.setType("Creation");
				mannualUpdateDetailsDTO.setUserSurId(subscriptionParamsDTO.getUserSurId());

			}

			result.setData(mannualUpdateDetailsDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Subscription created successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			result.setData(mannualUpdateDetailsDTO);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("error occured.");
		}

		return result;
	}
	
    public static ProductDetailDTO getProductDetails(Long productId,List<ProductDetailDTO> activePlans) {
        return activePlans.stream()
                .filter(product -> product.getIdProduct().equals(productId))
                .findAny()
                .orElse(null); // Returns null if productId is not found
    }
	

}
