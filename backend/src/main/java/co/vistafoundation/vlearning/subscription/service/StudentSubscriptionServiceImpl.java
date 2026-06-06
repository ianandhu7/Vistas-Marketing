/**
 * 
 */
package co.vistafoundation.vlearning.subscription.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.apple.itunes.storekit.model.JWSTransactionDecodedPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.paytm.pg.merchant.PaytmChecksum;

import co.vistafoundation.vlearning.auth.config.PaytmPaymentConfig;
import co.vistafoundation.vlearning.auth.dto.PaytmDetailsDTO;
import co.vistafoundation.vlearning.auth.model.Merchant;
import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.MerchantRepository;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.batch.model.SpecialOfferProduct;
import co.vistafoundation.vlearning.batch.repository.BatchGroupRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.SpecialOfferProductRepository;
import co.vistafoundation.vlearning.batch.service.BatchService;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.notification.dto.PaymentRemainderDTO;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.notification.service.UserNotificationService;
import co.vistafoundation.vlearning.offlinecourse.dto.FreeOfflineVideoCourseResponseDTO;
import co.vistafoundation.vlearning.offlinecourse.dto.OfflineVideoCourseResponsewithOTP;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.model.TopicLanguage;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.offlinecourse.repository.TopicLanguageRepository;
import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductAmount;
import co.vistafoundation.vlearning.product.model.ProductDuration;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.repository.ProductAmountRepository;
import co.vistafoundation.vlearning.product.repository.ProductDurationRepository;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductPricingRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizRepository;
import co.vistafoundation.vlearning.specialoffer.model.Coupon;
import co.vistafoundation.vlearning.specialoffer.model.Redemption;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;
import co.vistafoundation.vlearning.specialoffer.repository.CouponRepository;
import co.vistafoundation.vlearning.specialoffer.repository.RedemptionRepository;
import co.vistafoundation.vlearning.specialoffer.repository.SpecialOfferRepository;
import co.vistafoundation.vlearning.subject.dto.StreamingSubjectChapterDTO;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.BatchSubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.CartSummaryDTO;
import co.vistafoundation.vlearning.subscription.dto.InvoiceCsvLogsDTO;
import co.vistafoundation.vlearning.subscription.dto.InvoiceCsvLogsFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStreamingSubjectChapterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.OrderDTO;
import co.vistafoundation.vlearning.subscription.dto.OrderFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.PaytmParamsRequestDTO;
import co.vistafoundation.vlearning.subscription.dto.ProductDTO;
import co.vistafoundation.vlearning.subscription.dto.PromoCodeDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentAcademicGraphDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentPostLoginDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentStreamingInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubdcribedSubDto;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectProgressDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionBatchDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.InfoFilterDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.dto.UserInfoDto;
import co.vistafoundation.vlearning.subscription.enums.OrderTicketStatus;
import co.vistafoundation.vlearning.subscription.model.InvoiceCsvLogs;
import co.vistafoundation.vlearning.subscription.model.MannualSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.model.MannualUpdateDetailsDTO;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;
import co.vistafoundation.vlearning.subscription.model.StudentOrderTicket;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.model.SubscriptionPaymentHistory;
import co.vistafoundation.vlearning.subscription.repository.InvoiceCsvLogsRepository;
import co.vistafoundation.vlearning.subscription.repository.StagingStudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentOrderTicketRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.subscription.repository.SubscriptionPaymentHistoryRepository;
import co.vistafoundation.vlearning.subscription.utils.InAppPurchase;
import co.vistafoundation.vlearning.user.dto.UserCartRequestDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.UserCart;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.UserCartRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.user.service.UserCartService;
import co.vistafoundation.vlearning.user.service.UserService;
import co.vistafoundation.vlearning.user.util.UserContentAccessValidator;
import co.vistafoundation.vlearning.user.util.UserSubscriptionCheck;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.GenerateTime;
import co.vistafoundation.vlearning.utils.RandomStringGenerator;
import co.vistafoundation.vlearning.utils.TimeComparison;
import co.vistafoundation.vlearning.utils.TimeWatch;
import co.vistafoundation.vlearning.videocipher.config.VideoCipherConfiguration;
import co.vistafoundation.vlearning.videocipher.dto.VideoCipherOTP;

/**
 * @author vk
 *
 */
@Service
public class StudentSubscriptionServiceImpl implements StudentSubscriptionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubscriptionServiceImpl.class);

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	StagingStudentSubscriptionRepository stagingStudentSubscriptionRepository;

	@Autowired
	private PaytmPaymentConfig paytmPaymentConfig;

	@Autowired
	private StudentRepository studentRepository;

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
	StudentAssignedCourseRepository studentAssignedCourseRepository;

	@Autowired
	OfflineCourseService offlineCourseService;

	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	SubscriptionPaymentHistoryRepository subscriptionPaymentHistoryRepository;

	@Autowired
	StudentOrderRepository studentOrderRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	UserCartService userCartService;

	@Autowired
	UserCartRepository userCartRepository;

	@Autowired
	RandomStringGenerator randomStringGenerator;

	@Autowired
	UserRepository userRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	StudentOrderTicketRepository studentOrderTicketRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	TopicLanguageRepository topicLanguageRepository;

	@Autowired
	UserDeviceRepository userDeviceRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserNotificationService userNotificationService;

	@Autowired

	BatchService batchService;

	@Autowired
	SpecialOfferRepository specialOfferRepository;

	@Autowired
	SpecialOfferProductRepository specialOfferProductRepository;

	@Autowired
	UserService userService;

	@Autowired
	InvoiceCsvLogsService invoiceCsvLogsService;

	@Autowired
	InvoiceCsvLogsRepository invoiceCsvLogsRepository;

	@Autowired
	StudentChapterQuizRepository studentChapterQuizRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	BatchGroupRepository batchGroupRepository;
	

	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	ProductPricingRepository productPricingRepository;
	
	@Autowired
	ProductAmountRepository productAmountRepository;
	
	@Autowired
	ProductDurationRepository productDurationRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private RedemptionRepository redemptionRepository;

	@Autowired
	private InAppPurchase inAppPurchase;	

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");

	@Value("${subscription.grace.period}")
	private int gracePeriod;

	@Value("${paytm.initiate.transaction.url}")
	private String paymentIntiateTransactionUrl;

	@Value("${app.angular.url}")
	private String appAngularUrl;

	@Value("${paytm.payment.vsms.merchant.id}")
	private String merchantId;

	@Autowired
	UserContentAccessValidator userContentAccessValidator;

	@Autowired
	UserSubscriptionCheck userSubscriptionCheck;

	@Autowired
	EmailService emailService;
	
	@Autowired
	VideoCipherConfiguration videoCipherConfiguration;

	@Value("#{${listOfMarketingIds}}")
	private List<String> marketingIds;
	
	@Autowired
	GenerateTime generateTime;

	@Override
	public TreeMap<String, String> intiatePayment(PaytmDetailsDTO paytmDetailsDTO) throws Exception {
		TreeMap<String, String> parameters = paytmPaymentConfig.getRedirect(paytmDetailsDTO);
		return parameters;
	}

	/**
	 * This method will return the data which is required for checkout page and
	 * without saving any data to database to avoid data junk. The data it will
	 * return mainly is cart products, student order, staging subscription and
	 * payment parameters (which is required for payment). And also cumulative
	 * values of net, GST and total prices.
	 * 
	 * @param userCarts
	 * 
	 * @return CartSummaryDTO
	 *
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document saveNewStudentSubscription(UserCartRequestDTO userCartRequestDTO) {
		Document document = new Document();
		CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
		cartSummaryDTO.setPurchaseType("NEW");
		// adds user carts list
		List<UserCartResponseDTO> listUserCarts = new ArrayList<UserCartResponseDTO>();

		// get cart total amount
		Float cartTotalPrice = 0F;
		Float cartNetPrice = 0F;
		Float cartGSTPrice = 0F;
		Float cartTotalDiscount = 0F;

		Long offset = TimeUnit.MILLISECONDS.toMinutes(istTimeZone.getOffset(System.currentTimeMillis()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");

		LocalDateTime dateTimeServer = LocalDateTime.parse(formatter.format(LocalDateTime.now()), formatter);
		LocalDateTime dateTimeClient = LocalDateTime.parse(formatter.format(userCartRequestDTO.getClientDateTime()),
				formatter);

		if (offset != Math.abs(userCartRequestDTO.getClientTimeZone())) {
			document.setData(null);
			document.setMessage("Please set your timezone region default to India");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		} else if (!dateTimeServer.isEqual(dateTimeClient)) {
			document.setData(null);
			document.setMessage("Set date and time to automatic in your device. Server date time is "
					+ formatter.format(dateTimeServer));
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		}

		try {
			if (userCartRequestDTO.getUsercartResponseDTO().size() != 0) {

				// Update by @author NaveenKumar
				// added second level of validation for all the user sur id verification

				User loggedInUser = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
					loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				}

				if (loggedInUser == null)
					throw new AppException("Invalid User");

				for (UserCartResponseDTO userCartValidation : userCartRequestDTO.getUsercartResponseDTO()) {

					if (!userCartValidation.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}

					if (userCartValidation.getIdBatch() != null) {
						StudentSubscription studentSubscription = studentSubscriptionRepository
								.findByIdBatchAndActiveFlagAndIdStudent(userCartValidation.getIdBatch(), true,
										userCartValidation.getIdStudent());
						if (studentSubscription != null) {
							document.setData(null);
							document.setMessage("You have already active subsctiption with batch name "
									+ userCartValidation.getProductName()
									+ ". Please remove this batch for procced to checkout");
							document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
							return document;
						}
					}
				}

				List<Long> idBatches = new ArrayList<>();
				idBatches = userCartRequestDTO.getUsercartResponseDTO().stream().map(UserCartResponseDTO::getIdBatch)
						.distinct().collect(Collectors.toList());
				// added by vk, for checking batch timing conflicts in cart page while applying
				// promo code
				Document<Boolean> responseBatchConflict = batchService.checkConflictsOnBatch(idBatches);
				if (responseBatchConflict.getStatusCode() == 409) {
					document.setData(null);
					document.setMessage(responseBatchConflict.getMessage());
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				}

				if (userCartRequestDTO.getPromoCodeValid() && userCartRequestDTO.getPromoCode().equals("6For2000")) {
					Document<PromoCodeDTO> promoResponse = userCartService
							.applyPromoCode(userCartRequestDTO.getPromoCode());
					if (promoResponse.getStatusCode() == 200) {
						PromoCodeDTO promoCodeDTO = (PromoCodeDTO) promoResponse.getData();
						cartNetPrice = Float.parseFloat(promoCodeDTO.getActualAmount().toString());
						// GST percentage calculated 0% for now
						cartGSTPrice = 0F;
						// cart total from cart purchase amount
						cartTotalPrice = Float.parseFloat(promoCodeDTO.getTotalAmount().toString());
						// cart discount in case of valid coupon code
						Long discount = promoCodeDTO.getActualAmount() - promoCodeDTO.getTotalAmount();
						cartTotalDiscount = Float.parseFloat(discount.toString());
						cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
						cartSummaryDTO.setCartNetPrice(cartNetPrice);
						cartSummaryDTO.setCartTotalDiscount(cartTotalDiscount);
						cartSummaryDTO.setCartTotalPrice(cartTotalPrice);
					}
				} else {
					for (UserCartResponseDTO userCart : userCartRequestDTO.getUsercartResponseDTO()) {
						Product product = productRepository.findByIdProductAndActiveFlag(userCart.getIdProduct(),
								Boolean.TRUE);
						if (product != null) {
							Long itemNetPrice = (long) (userCart.getSubscriptionType().equals("MONTH")
									? product.getMonthlySubcrAmt()
									: userCart.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
											: userCart.getSubscriptionType().equals("ANNUAL")
													? product.getAnnualSubscrAmt()
													: 0L);
							cartNetPrice += itemNetPrice;
							userCart.setPurchaseAmount(Float.parseFloat(itemNetPrice.toString()));
							// GST percentage calculated 0% for now
							cartGSTPrice = 0F;
							cartTotalDiscount = 0F;
							// cart discount in case of valid coupon code
							cartTotalPrice = cartNetPrice + cartGSTPrice;
							cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
							cartSummaryDTO.setCartNetPrice(cartNetPrice);
							cartSummaryDTO.setCartTotalDiscount(cartTotalDiscount);
							cartSummaryDTO.setCartTotalPrice(cartTotalPrice);
						}
					}
				}

				// create order data
				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(userCartRequestDTO.getUsercartResponseDTO().get(0).getUserSurId());
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Pending");
				studentOrder.setNetAmount(cartNetPrice);
				studentOrder.setGstAmount(cartGSTPrice);
				studentOrder.setTotalAmount(cartTotalPrice);

				// set order id
				studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
				// studentOrder = studentOrderRepository.save(studentOrder);
				cartSummaryDTO.setStudentOrder(studentOrder);
				if (studentOrder != null) {
					// save all staging student subscription data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();

					if (userCartRequestDTO.getPromoCode().equals("6For2000")
							&& userCartRequestDTO.getPromoCodeValid()) {
						Document<PromoCodeDTO> pcDTO = userCartService
								.applyPromoCode(userCartRequestDTO.getPromoCode());
						List<UserCartResponseDTO> mergedUCDTO = new ArrayList<UserCartResponseDTO>();
						if (pcDTO.getData() != null) {
							if (!pcDTO.getData().getPromoAppliedList().isEmpty()) {
								mergedUCDTO.addAll(pcDTO.getData().getPromoAppliedList());
								if (!pcDTO.getData().getNonPromoList().isEmpty()) {
									mergedUCDTO.addAll(pcDTO.getData().getNonPromoList());
								}
							}
							for (UserCartResponseDTO userCartResponseDTO : mergedUCDTO) {
								StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
								stagingStudentSubscription.setIdBatch(userCartResponseDTO.getIdBatch());
								stagingStudentSubscription.setIdProduct(userCartResponseDTO.getIdProduct());
								stagingStudentSubscription.setIdProductGroup(userCartResponseDTO.getIdProductGroup());
								if (userCartResponseDTO.getIdProductGroup() != null) {
									ProductGroup productGroup = productGroupRepository
											.findByIdProductGroup(userCartResponseDTO.getIdProductGroup());
									if (productGroup != null) {
										stagingStudentSubscription.setIdproductLine(productGroup.getIdProductLine());
									}
								} else if (userCartResponseDTO.getIdProduct() != null) {
									Product product = productRepository.findByIdProductAndActiveFlag(
											userCartResponseDTO.getIdProduct(), Boolean.TRUE);
									if (product != null) {
										stagingStudentSubscription.setIdproductLine(product.getIdProductLine());
									}
								}
								stagingStudentSubscription.setIdStudent(userCartResponseDTO.getIdStudent());
								stagingStudentSubscription
										.setPurchaseAmount(userCartResponseDTO.getPurchaseAmount().toString());
								stagingStudentSubscription.setPurchaseLevel(userCartResponseDTO.getPurchaseLevel());
								stagingStudentSubscription.setPurchaseType(userCartResponseDTO.getPurchaseType());
								stagingStudentSubscription
										.setSubscriptionType(userCartResponseDTO.getSubscriptionType());
								stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
								stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
								stagingStudentSubscription.setPaymentStatus("Pending");
								stagingStudentSubscription.setUserSurId(studentOrder.getUserSurId());
								SpecialOffer so = specialOfferRepository
										.findByCouponCodeAndActiveFlagAndSpecialOfferEndDateGreaterThanEqual(
												userCartRequestDTO.getPromoCode(), true, LocalDate.now());
								if (so == null)
									throw new AppException("Invalid Promocode");

								Optional<SpecialOfferProduct> sop = specialOfferProductRepository
										.findByIdBatchAndIdSpecialOffer(userCartResponseDTO.getIdBatch(),
												so.getIdSpecialOffer());
								if (userCartResponseDTO.getIdSpecialOffer() != null && sop.isPresent()) {
									stagingStudentSubscription.setIdSpecialOffer(so.getIdSpecialOffer());
									stagingStudentSubscription.setSpecialOfferFlag(true);
								} else {
									stagingStudentSubscription.setIdSpecialOffer(null);
									stagingStudentSubscription.setSpecialOfferFlag(Boolean.FALSE);
								}
								stagingStudentSubscriptions.add(stagingStudentSubscription);
								// stagingStudentSubscriptionRepository.saveAll(stagingStudentSubscriptions);
								cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
							}
						} else {
							document.setData(null);
							document.setMessage(pcDTO.getMessage());
							document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						}
					} else {
						for (UserCartResponseDTO userCart : userCartRequestDTO.getUsercartResponseDTO()) {
							StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
							stagingStudentSubscription.setIdBatch(userCart.getIdBatch());
							stagingStudentSubscription.setIdProduct(userCart.getIdProduct());
							stagingStudentSubscription.setIdProductGroup(userCart.getIdProductGroup());
							if (userCart.getIdProductGroup() != null) {
								ProductGroup productGroup = productGroupRepository
										.findByIdProductGroup(userCart.getIdProductGroup());
								if (productGroup != null) {
									stagingStudentSubscription.setIdproductLine(productGroup.getIdProductLine());
								}
							} else if (userCart.getIdProduct() != null) {
								Product product = productRepository
										.findByIdProductAndActiveFlag(userCart.getIdProduct(), Boolean.TRUE);
								if (product != null) {
									stagingStudentSubscription.setIdproductLine(product.getIdProductLine());
								}
							}
							stagingStudentSubscription.setIdStudent(userCart.getIdStudent());
							stagingStudentSubscription.setPurchaseAmount(userCart.getPurchaseAmount().toString());
							stagingStudentSubscription.setPurchaseLevel(userCart.getPurchaseLevel());
							stagingStudentSubscription.setPurchaseType(userCart.getPurchaseType());
							stagingStudentSubscription.setSubscriptionType(userCart.getSubscriptionType());
							stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
							stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
							stagingStudentSubscription.setPaymentStatus("Pending");
							stagingStudentSubscription.setIdSpecialOffer(null);
							stagingStudentSubscription.setSpecialOfferFlag(Boolean.FALSE);
							stagingStudentSubscription.setUserSurId(studentOrder.getUserSurId());
							stagingStudentSubscriptions.add(stagingStudentSubscription);
							// stagingStudentSubscriptionRepository.saveAll(stagingStudentSubscriptions);
							cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
						}
					}

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
							cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
							document.setData(cartSummaryDTO);
							document.setMessage("Cart summary with payment parameters");
							document.setStatusCode(HttpStatus.OK.value());
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
							document.setData(null);
							document.setMessage("Error while creating cart summary");
							document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						}
					}

					for (UserCartResponseDTO userCart : userCartRequestDTO.getUsercartResponseDTO()) {
						if (userCart.getIdBatch() != null) {
							Batch batchAvailability = batchRepository.findByIdBatch(userCart.getIdBatch());
							if (userCart.getBatch().getVersion() == batchAvailability.getVersion()
									&& (batchAvailability.getPaymentStatus() == null
											&& batchAvailability.getCurrentVacancy() >= 1)) {
								batchAvailability.setPaymentStatus("IN_PROGRESS");
								batchAvailability = batchRepository.save(batchAvailability);
								userCart.setBatch(batchAvailability);
							} else if (batchAvailability != null && batchAvailability.getCurrentVacancy() == 0) {
								document.setData(null);
								document.setMessage(batchAvailability.getBatchName()
										+ " slot full, remove the item from cart and select another batch for purchasing");
								document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
								return document;
							} else if (userCart.getBatch().getVersion() != batchAvailability.getVersion()
									|| batchAvailability.getPaymentStatus() != null) {
								document.setData(null);
								document.setMessage(
										"Other payment is in-progress, please check the cart for product availability and proceed for checkout");
								document.setStatusCode(HttpStatus.CONFLICT.value());
								return document;
							}
						}
						listUserCarts.add(userCart);
					}
					cartSummaryDTO.setUserCarts(listUserCarts);
					// Long pooling task
					CompletableFuture.runAsync(() -> {
						try {
							System.out.println("Inside checkout run async");
							// time watch for exceeding time check
							TimeWatch watch = TimeWatch.start();
							// running timer task as daemon thread
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									System.out.println("Inside checkout timer loop");
									long passedTimeInMinutes = watch.time(TimeUnit.MINUTES);
									// set result after completing task to return response
									StudentOrder studentOrderUpdate = studentOrderRepository
											.findByOrderId(studentOrder.getOrderId());
									if (studentOrderUpdate != null) {
										timer.cancel();
										return;
									} else if (studentOrderUpdate == null && passedTimeInMinutes >= 1) {
										for (UserCartResponseDTO userCart : cartSummaryDTO.getUserCarts()) {
											if (userCart.getIdBatch() != null) {
												Batch batchUpdate = batchRepository
														.findByIdBatch(userCart.getIdBatch());
												batchUpdate.setPaymentStatus(null);
												batchRepository.save(batchUpdate);
											}
										}
										timer.cancel();
										return;
									}
								}
							}, 0, 10000);
						} catch (Exception ex) {
							LOGGER.debug(ex.getMessage());
						}
					});
				}
			} else {
				document.setData(null);
				document.setMessage("Please send user cart data for proceeding to checkout");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			document.setData(null);
			document.setMessage(e.getCause().getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveRenewalStudentSubscription(Long idStudentSubscription, Long userSurId) {
		Document document = new Document();

		try {

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
			cartSummaryDTO.setPurchaseType("RENEWAL");

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!userSurId.equals(loggedInUser.getUserSurId())) {
				document.setData(null);
				document.setMessage("User dosent have access to add this item.");
				document.setStatusCode(HttpStatus.FORBIDDEN.value());
				return document;

			}

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByIdStudentSubscriptionAndUserSurId(idStudentSubscription, userSurId);

			if (studentSubscription == null) {
				document.setData(null);
				document.setMessage("Subscription details not found");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}

			LocalDate dateExist = studentSubscription.getSubscriptionEndDate().atZone(zoneIndia).toLocalDate().minusDays(6);
			LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
			if (dateExist.isBefore(dateToday) && !dateExist.equals(dateToday)) {
				// get cart total amount
				Float cartTotalPrice = 0F;
				Float cartNetPrice = 0F;
				Float cartGSTPrice = 0F;
				cartNetPrice = Float.parseFloat(studentSubscription.getPurchaseAmount());

				// GST percentage calculated 0% for now
				cartGSTPrice = 0F;
				// cart total from cart purchase amount
				cartTotalPrice = cartNetPrice + cartGSTPrice;
				cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
				cartSummaryDTO.setCartNetPrice(cartNetPrice);
				cartSummaryDTO.setCartTotalPrice(cartTotalPrice);
				// create order data
				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(userSurId);
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Pending");
				studentOrder.setNetAmount(cartNetPrice);
				studentOrder.setGstAmount(cartGSTPrice);
				studentOrder.setTotalAmount(cartTotalPrice);
				// set order id
				studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
				cartSummaryDTO.setStudentOrder(studentOrder);
				if (studentOrder != null) {
					// create staging data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					// add product data
					List<UserCartResponseDTO> userCarts = new ArrayList<>();
					StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
					stagingStudentSubscription.setIdBatch(studentSubscription.getIdBatch());
					stagingStudentSubscription.setIdProduct(studentSubscription.getIdProduct());
					stagingStudentSubscription.setIdProductGroup(studentSubscription.getIdProductGroup());
					stagingStudentSubscription.setIdproductLine(studentSubscription.getIdproductLine());
					stagingStudentSubscription.setIdStudent(studentSubscription.getIdStudent());
					stagingStudentSubscription.setPurchaseAmount(cartNetPrice.toString());
					stagingStudentSubscription.setPurchaseLevel(studentSubscription.getPurchaseLevel());
					stagingStudentSubscription.setPurchaseType("RENEWAL");
					stagingStudentSubscription.setSubscriptionType(studentSubscription.getSubscriptionType());
					stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
					stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
					stagingStudentSubscription.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
					stagingStudentSubscription.setPaymentStatus("Pending");
					// here last payment date is next payment date
					stagingStudentSubscription.setLastPaymentDate(studentSubscription.getNextPaymentDate());
					stagingStudentSubscriptions.add(stagingStudentSubscription);
					cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);

					// cart data adding
					UserCartResponseDTO userCart = new UserCartResponseDTO();
					userCart.setPurchaseAmount(Float.parseFloat(stagingStudentSubscription.getPurchaseAmount()));
					userCart.setPurchaseType(stagingStudentSubscription.getPurchaseType());
					userCart.setIdStudent(userSurId);
					userCart.setSubscriptionType(studentSubscription.getSubscriptionType());
					if (studentSubscription.getIdBatch() != null) {
						Batch batch = batchRepository.findByIdBatch(studentSubscription.getIdBatch());
						if (batch != null) {
							userCart.setIdBatch(batch.getIdBatch());
							userCart.setProductName(batch.getBatchName());
							userCart.setIdProduct(batch.getIdProduct());
							userCart.setProductCategory("BATCH");
							userCart.setPurchaseLevel("BATCH");
							/* batch.setPaymentStatus("IN_PROGRESS"); */
							/* batch = batchRepository.save(batch); */
							userCart.setBatch(batch);
						}
					} else if (stagingStudentSubscription.getIdProduct() != null) {
						Product product = productRepository
								.findByIdProductAndActiveFlag(stagingStudentSubscription.getIdProduct(), Boolean.TRUE);
						if (product != null) {
							userCart.setIdProduct(product.getIdProduct());
							userCart.setProductName(product.getProductName());
							userCart.setProductCategory("PRODUCT");
							userCart.setPurchaseLevel("PRODUCT");
						}
					} else if (stagingStudentSubscription.getIdProductGroup() != null) {
						ProductGroup productGroup = productGroupRepository
								.findByIdProductGroup(stagingStudentSubscription.getIdProductGroup());
						if (productGroup != null) {
							userCart.setIdProductGroup(productGroup.getIdProductGroup());
							userCart.setProductName(productGroup.getProductGroupName());
						}
					}
					userCarts.add(userCart);
					cartSummaryDTO.setUserCarts(userCarts);

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
							cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
							document.setData(cartSummaryDTO);
							document.setStatusCode(HttpStatus.OK.value());
							document.setMessage("Renewal summary data fetched successfully");
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
						}
					}
				}
			} else {
				document.setData(null);
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				document.setMessage("Your subscription is not expired yet!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document makePayment(CartSummaryDTO cartSummaryDTO) {

		Document document = new Document();

		try {

			if (!cartSummaryDTO.getUserCarts().isEmpty()) {

				// Update by @author NaveenKumar
				// added second level of validation for all the user sur id verification

				User loggedInUser = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
					loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				}

				if (loggedInUser == null)
					throw new AppException("Invalid User");

				for (UserCartResponseDTO u : cartSummaryDTO.getUserCarts()) {

					if (!u.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				for (StagingStudentSubscription ss : cartSummaryDTO.getStagingStudentSubscriptions()) {

					if (!ss.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				if (!cartSummaryDTO.getStudentOrder().getUserSurId().equals(loggedInUser.getUserSurId())) {
					document.setData(null);
					document.setMessage("User dosent have access to add this item to cart.");
					document.setStatusCode(HttpStatus.FORBIDDEN.value());
					return document;

				}

				for (UserCartResponseDTO userCart : cartSummaryDTO.getUserCarts()) {
					if (userCart.getIdBatch() != null) {
						Batch batchPaymentCheck = batchRepository.findByIdBatch(userCart.getIdBatch());
						if (batchPaymentCheck.getPaymentStatus() == null) {
							document.setData(null);
							document.setStatusCode(HttpStatus.REQUEST_TIMEOUT.value());
							document.setMessage("Your checkout time is expired, go to cart page and checkout again!");
							return document;
						}
					}
				}
				// save student order
				StudentOrder studentOrder = studentOrderRepository.save(cartSummaryDTO.getStudentOrder());
				if (studentOrder != null && cartSummaryDTO.getStagingStudentSubscriptions().size() != 0) {
					// Long pooling task
					CompletableFuture.runAsync(() -> {
						try {
							System.out.println("Inside makepayment run async");
							// time watch for exceeding time check
							TimeWatch watch = TimeWatch.start();
							// running timer task as daemon thread
							Timer timer = new Timer();
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									System.out.println("Inside makepayment timer loop");
									long passedTimeInMinutes = watch.time(TimeUnit.MINUTES);
									System.out.println("Inside passedTimeInMinutes" + passedTimeInMinutes);
									// set result after completing task to return response
									StudentOrder studentOrderUpdate = studentOrderRepository
											.findByOrderId(studentOrder.getOrderId());
									if (studentOrderUpdate.getOrderStatus().equals("Failed")) {
										for (UserCartResponseDTO userCart : cartSummaryDTO.getUserCarts()) {
											if (userCart.getIdBatch() != null) {
												Batch batchUpdate = batchRepository
														.findByIdBatch(userCart.getIdBatch());
												batchUpdate.setPaymentStatus(null);
												batchRepository.save(batchUpdate);
											}
										}
										timer.cancel();
										return;
									} else if (studentOrderUpdate.getOrderStatus().equals("Success")) {
										for (UserCartResponseDTO userCart : cartSummaryDTO.getUserCarts()) {
											if (userCart.getIdBatch() != null) {
												Batch batchUpdate = batchRepository
														.findByIdBatch(userCart.getIdBatch());
												Product product = productRepository.findByIdProductAndActiveFlag(
														batchUpdate.getIdProduct(), Boolean.TRUE);
												if (product == null) {
													throw new NullPointerException("Invalid idProduct");
												}
												// This if block should never execute in the normal user flow, If you
												// see
												// the
												// below exception there must be some security flaw happened.
												if (batchUpdate.getCurrentVacancy() <= 0
														|| batchUpdate.getCurrentOccupancy() > product.getBatchSize()) {
													throw new AppException(
															"No Seates are available for this Live course");
												}
												if (product.getBatchSize() - batchUpdate.getCurrentVacancy() >= 0) {
													batchUpdate.setCurrentVacancy(batchUpdate.getCurrentVacancy() - 1);
												} else if (product.getBatchSize() == 1) {
													batchUpdate.setCurrentVacancy(0);
												}
												if (product.getBatchSize() - batchUpdate.getCurrentOccupancy() >= 0) {
													batchUpdate
															.setCurrentOccupancy(batchUpdate.getCurrentOccupancy() + 1);
													batchUpdate.setPaymentStatus(null);
													batchRepository.save(batchUpdate);
												}
											}
										}
										timer.cancel();
										return;
									}
									if (passedTimeInMinutes >= 2) {
										// set result after completing task to return response
										if (studentOrderUpdate.getOrderStatus().equals("Pending")) {
											studentOrderUpdate.setOrderStatus("Cancel");
											studentOrderUpdate.setSecondaryStatus("System_Canceled");
											studentOrderUpdate = studentOrderRepository.save(studentOrderUpdate);
											for (UserCartResponseDTO userCart : cartSummaryDTO.getUserCarts()) {
												if (userCart.getIdBatch() != null) {
													Batch batchUpdate = batchRepository
															.findByIdBatch(userCart.getIdBatch());
													batchUpdate.setPaymentStatus(null);
													batchRepository.save(batchUpdate);
												}
											}
											List<StagingStudentSubscription> stageList = stagingStudentSubscriptionRepository
													.findByOrderId(studentOrderUpdate.getOrderId());
											for (StagingStudentSubscription stagingStudentSubscription : stageList) {
												stagingStudentSubscription.setPaymentStatus("Cancel");
												stagingStudentSubscription = stagingStudentSubscriptionRepository
														.save(stagingStudentSubscription);
											}
										}
										timer.cancel();
										return;
									}
								}
							}, 0, 10000);
						} catch (Exception ex) {
							LOGGER.debug(ex.getMessage());
							document.setData(null);
							document.setMessage("Something went wrong while making payment");
							document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						}
					});

					cartSummaryDTO.setStudentOrder(studentOrder);
					// save staging table data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					for (StagingStudentSubscription stagingStudentSubscription : cartSummaryDTO
							.getStagingStudentSubscriptions()) {
						stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
						stagingStudentSubscription.setTransactionDate(Instant.now());
						stagingStudentSubscriptions.add(stagingStudentSubscription);
					}
					stagingStudentSubscriptions = stagingStudentSubscriptionRepository
							.saveAll(stagingStudentSubscriptions);
					if (stagingStudentSubscriptions.size() != 0) {
						cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
					} else {
						document.setData(null);
						document.setMessage("Something went wrong while making payment");
						document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return document;
					}
					if (cartSummaryDTO.getUserCarts().size() != 0) {
						List<UserCart> list = new ArrayList<>();
						cartSummaryDTO.getUserCarts().forEach(element -> {
							UserCart userCart = new UserCart();
							if (element.getBatch() != null) {
								BeanUtils.copyProperties(element, userCart, "batch");
							}
							userCart.setIdStudentOrder(studentOrder.getIdStudentOrder());
							list.add(userCart);
						});
						if (!cartSummaryDTO.getPurchaseType().equals("RENEWAL")) {
							userCartRepository.saveAll(list);
						}
					} else {
						document.setData(null);
						document.setMessage("Something went wrong while making payment");
						document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return document;
					}
				} else {
					document.setData(null);
					document.setMessage("Something went wrong while making payment");
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return document;
				}
				document.setData(cartSummaryDTO);
				document.setMessage("Payment In Progress");
				document.setStatusCode(HttpStatus.OK.value());
			} else {
				document.setData(null);
				document.setMessage("Something went wrong while making payment");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}
		} catch (Exception ex) {
			LOGGER.debug(ex.getMessage());
			document.setData(null);
			document.setMessage("Something went wrong while making payment");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document makeRenewalPayment(CartSummaryDTO cartSummaryDTO) {
		Document document = new Document();
		try {

			if (!cartSummaryDTO.getUserCarts().isEmpty()) {

				User loggedInUser = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
					loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				}

				if (loggedInUser == null)
					throw new AppException("Invalid User");

				for (UserCartResponseDTO u : cartSummaryDTO.getUserCarts()) {

					if (!u.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				for (StagingStudentSubscription ss : cartSummaryDTO.getStagingStudentSubscriptions()) {

					if (!ss.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				if (!cartSummaryDTO.getStudentOrder().getUserSurId().equals(loggedInUser.getUserSurId())) {
					document.setData(null);
					document.setMessage("User dosent have access to add this item to cart.");
					document.setStatusCode(HttpStatus.FORBIDDEN.value());
					return document;

				}

				// save student order
				StudentOrder studentOrder = studentOrderRepository.save(cartSummaryDTO.getStudentOrder());
				if (studentOrder != null && cartSummaryDTO.getStagingStudentSubscriptions().size() != 0) {
					cartSummaryDTO.setStudentOrder(studentOrder);
					// save staging table data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					for (StagingStudentSubscription stagingStudentSubscription : cartSummaryDTO
							.getStagingStudentSubscriptions()) {
						stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
						stagingStudentSubscription.setTransactionDate(Instant.now());
						stagingStudentSubscriptions.add(stagingStudentSubscription);
					}
					stagingStudentSubscriptions = stagingStudentSubscriptionRepository
							.saveAll(stagingStudentSubscriptions);
					if (stagingStudentSubscriptions.size() != 0) {
						cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
					} else {
						document.setData(null);
						document.setMessage("Something went wrong while making payment");
						document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return document;
					}
					if (cartSummaryDTO.getUserCarts().size() != 0) {
						List<UserCart> list = new ArrayList<>();
						cartSummaryDTO.getUserCarts().forEach(element -> {
							UserCart userCart = new UserCart();
							if (element.getBatch() != null) {
								BeanUtils.copyProperties(element, userCart, "batch");
							}
							userCart.setIdStudentOrder(studentOrder.getIdStudentOrder());
							list.add(userCart);
						});
					}
				} else {
					document.setData(null);
					document.setMessage("Something went wrong while making payment");
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return document;
				}
				document.setData(cartSummaryDTO);
				document.setMessage("Payment In Progress");
				document.setStatusCode(HttpStatus.OK.value());

			}

		} catch (Exception e) {
			document.setData(null);
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			document.setMessage(e.getLocalizedMessage());
			return document;
		}
		return document;
	}

	@Override
	public Model paymentResponse(HttpServletRequest request, Model model, String callBackURLPayment) throws Exception {
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String paytmChecksum = null;
		if (mapData.containsKey("CHECKSUMHASH")) {
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}

		String receptNum = parameters.get("ORDERID");
		String status = parameters.get("STATUS");
		String respCode = parameters.get("RESPCODE");
		boolean isValideChecksum = false;
		try {
			isValideChecksum = paytmPaymentConfig.validateCheckSum(parameters, paytmChecksum);
			if (isValideChecksum || parameters.containsKey("RESPCODE")) {
				if (respCode.equals("01")) {
					parameters.put("MESSAGE", "Payment Successful");
				} else if (respCode.equals("141")) {
					parameters.put("MESSAGE", "User	has	not	completed transaction");
					// removes parameters which is not required in receipt
					parameters.remove("errorMessage");
					parameters.remove("retryAllowed");
					parameters.remove("errorCode");
					parameters.remove("BANKTXNID");
					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
					if (setStudentOrder != null && setStudentOrder.getOrderStatus().equals("Pending")) {
						// update student order table status
						setStudentOrder.setOrderStatus("Failed");
						setStudentOrder = studentOrderRepository.save(setStudentOrder);
					}
					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(receptNum);

					if (stagingStudentSubscription.size() != 0) {
						for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
							stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
							stagingStudentSubscriptionUpdate
									.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
							stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
									.save(stagingStudentSubscriptionUpdate);

							if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
								Batch batchEdit = batchRepository
										.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
								if (batchEdit != null) {
									batchEdit.setPaymentStatus(null);
									batchRepository.save(batchEdit);
								}
							}
						}
					}
				} else if (respCode.equals("402")) {
					parameters.put("MESSAGE", "	Transaction Session has expired");
					// removes parameters which is not required in receipt
					parameters.remove("errorMessage");
					parameters.remove("retryAllowed");
					parameters.remove("errorCode");
					parameters.remove("BANKTXNID");
					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
					if (setStudentOrder != null && setStudentOrder.getOrderStatus().equals("Pending")) {
						// update student order table status
						setStudentOrder.setOrderStatus("Failed");
						setStudentOrder = studentOrderRepository.save(setStudentOrder);
					}
					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(receptNum);

					if (stagingStudentSubscription.size() != 0) {
						for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
							stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
							stagingStudentSubscriptionUpdate
									.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
							stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
									.save(stagingStudentSubscriptionUpdate);
							if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
								Batch batchEdit = batchRepository
										.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
								if (batchEdit != null) {
									batchEdit.setPaymentStatus(null);
									batchRepository.save(batchEdit);
								}
							}

						}
					}
				} else {
					parameters.put("MESSAGE", parameters.get("RESPMSG"));
					// removes parameters which is not required in receipt
					parameters.remove("errorMessage");
					parameters.remove("retryAllowed");
					parameters.remove("errorCode");
					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
					if (setStudentOrder != null && setStudentOrder.getOrderStatus().equals("Pending")) {
						// update student order table status
						setStudentOrder.setOrderStatus("Failed");
						setStudentOrder = studentOrderRepository.save(setStudentOrder);
					}
					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(receptNum);

					if (stagingStudentSubscription.size() != 0) {
						for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
							stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
							stagingStudentSubscriptionUpdate
									.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
							stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
									.save(stagingStudentSubscriptionUpdate);
							if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
								Batch batchEdit = batchRepository
										.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
								if (batchEdit != null) {
									batchEdit.setPaymentStatus(null);
									batchRepository.save(batchEdit);
								}
							}
						}
					}
				}
			}

			parameters.replace("STATUS", status,
					status.equals("TXN_SUCCESS") ? "Success" : status.equals("TXN_FAILURE") ? "Failed" : status);

			if (!status.equals("TXN_FAILURE") || respCode.equals("227")) {
				// change parameter value using ternary
				if (parameters.get("PAYMENTMODE") != null) {
					parameters.replace("PAYMENTMODE", parameters.get("PAYMENTMODE"),
							parameters.get("PAYMENTMODE").equals("DC") ? "Debit Card"
									: parameters.get("PAYMENTMODE").equals("CC") ? "Credit Card"
											: parameters.get("PAYMENTMODE").equals("NB") ? "Net Banking"
													: parameters.get("PAYMENTMODE").equals("PPI") ? "Paytm Wallet"
															: parameters.get("PAYMENTMODE"));
					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
					if (setStudentOrder != null) {
						if (setStudentOrder.getSecondaryStatus() != null
								&& parameters.get("STATUS").equals("Success")) {
							setStudentOrder.setDisputeFlag(Boolean.TRUE);
						} else {
							setStudentOrder.setDisputeFlag(Boolean.FALSE);
						}
						// update student order table status
						setStudentOrder.setOrderStatus(parameters.get("STATUS"));
						setStudentOrder = studentOrderRepository.save(setStudentOrder);
						// delete cart items if payment is successful
						if (!respCode.equals("141") && setStudentOrder != null) {
							List<UserCart> list = userCartRepository
									.findByIdStudentOrder(setStudentOrder.getIdStudentOrder());
							if (list.size() != 0) {
								userCartRepository.deleteInBatch(list);
							}
						}
					}

					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(receptNum);

					Set<String> regDeviceIdSet = new HashSet<String>();
					List<String> regDeviceIdList = new ArrayList<String>();
					String studentName = "";
					String orderId = "";
					ProductDuration productDuration =null;

					for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
						if (stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
							stagingStudentSubscriptionUpdate
									.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
						} else {
							stagingStudentSubscriptionUpdate.setLastPaymentDate(Instant.now());
						}
						 productDuration = productDurationRepository
								.findByDurationCode(stagingStudentSubscriptionUpdate.getSubscriptionType());
						
						if(productDuration==null)
							throw new AppException("No product duration data found");		
						LocalDateTime duration = stagingStudentSubscriptionUpdate.getLastPaymentDate()
								.atZone(zoneIndia).toLocalDateTime().plusDays(productDuration.getDuration());
						stagingStudentSubscriptionUpdate
						.setNextPaymentDate(duration.atZone(ZoneId.systemDefault()).toInstant());

						LocalDateTime gracePeriodDate = stagingStudentSubscriptionUpdate.getNextPaymentDate()
								.atZone(zoneIndia).toLocalDateTime().plusDays(gracePeriod);
						if (stagingStudentSubscriptionUpdate.getPurchaseLevel().equalsIgnoreCase("AD_FREE_SUBSCRIPTION")) {
							stagingStudentSubscriptionUpdate
									.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
						} else {
							stagingStudentSubscriptionUpdate
									.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
						}
						stagingStudentSubscriptionUpdate.setBankName(parameters.get("BANKNAME"));
						stagingStudentSubscriptionUpdate.setBankTransactionId(parameters.get("BANKTXNID"));
						stagingStudentSubscriptionUpdate.setOrderId(parameters.get("ORDERID"));
						stagingStudentSubscriptionUpdate.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
						stagingStudentSubscriptionUpdate.setPaymentMode(parameters.get("PAYMENTMODE"));
						stagingStudentSubscriptionUpdate.setPaymentStatus(parameters.get("STATUS"));
						stagingStudentSubscriptionUpdate
								.setTransactionAmount(Float.parseFloat(parameters.get("TXNAMOUNT")));
						stagingStudentSubscriptionUpdate
								.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
						stagingStudentSubscriptionUpdate.setPurchaseDate(Instant.now());
						stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
								.save(stagingStudentSubscriptionUpdate);
						if (parameters.get("STATUS").equals("Success") && !setStudentOrder.getDisputeFlag()) {
							StudentSubscription studentSubscription = null;
							if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() == null
									|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("NEW")) {
								studentSubscription = new StudentSubscription();
							} else if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() != null
									|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
								studentSubscription = studentSubscriptionRepository.findByIdStudentSubscription(
										stagingStudentSubscriptionUpdate.getIdStudentSubscription());
							}
							studentSubscription.setIdProduct(stagingStudentSubscriptionUpdate.getIdProduct());
							studentSubscription.setIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
							studentSubscription.setIdProductGroup(stagingStudentSubscriptionUpdate.getIdProductGroup());
							studentSubscription.setIdproductLine(stagingStudentSubscriptionUpdate.getIdproductLine());
							studentSubscription.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
							studentSubscription.setIdStudentOrder(stagingStudentSubscriptionUpdate.getIdStudentOrder());
							studentSubscription
									.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
							studentSubscription
									.setNextPaymentDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
							studentSubscription.setPurchaseAmount(stagingStudentSubscriptionUpdate.getPurchaseAmount());
							studentSubscription.setPurchaseDate(stagingStudentSubscriptionUpdate.getPurchaseDate());
							studentSubscription.setPurchaseLevel(stagingStudentSubscriptionUpdate.getPurchaseLevel());
							studentSubscription
									.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getSubscriptionEndDate());
							studentSubscription
									.setSubscriptionType(stagingStudentSubscriptionUpdate.getSubscriptionType());
							studentSubscription.setPurchaseType(stagingStudentSubscriptionUpdate.getPurchaseType());
							studentSubscription.setActiveFlag(Boolean.TRUE);
							studentSubscription.setFreeFlag(Boolean.FALSE);
							studentSubscription.setUserSurId(stagingStudentSubscriptionUpdate.getUserSurId());
							studentSubscription.setIdSpecialOffer(stagingStudentSubscriptionUpdate.getIdSpecialOffer());
							studentSubscription
									.setSpecialOfferFlag(stagingStudentSubscriptionUpdate.getSpecialOfferFlag());
							StudentSubscription ss = studentSubscriptionRepository
									.findByIdBatchAndIdProductAndIdStudentOrderAndUserSurId(
											stagingStudentSubscriptionUpdate.getIdBatch(),
											stagingStudentSubscriptionUpdate.getIdProduct(),
											stagingStudentSubscriptionUpdate.getIdStudentOrder(),
											stagingStudentSubscriptionUpdate.getUserSurId());
							if (ss == null) {
								studentSubscription = studentSubscriptionRepository.save(studentSubscription);
							} else {
								studentSubscription = null;
							}
							
							if(setStudentOrder.getCouponCode()!=null) {
								
								ProductAmount productAmount = productAmountRepository
										.findByAmount(setStudentOrder.getActualAmount());

								ProductPricing productPricing = productPricingRepository
										.findByIdProductAmountAndIdProductDurationAndIdProduct(
												productAmount.getIdProductAmount(),
												productDuration.getIdProductDuration(),
												studentSubscription.getIdProduct());
								Redemption redemptionSave = new Redemption();
								redemptionSave.setCouponCode(setStudentOrder.getCouponCode());
								redemptionSave.setIdVlUser(setStudentOrder.getUserSurId());
								redemptionSave.setIdProductPricing(productPricing.getIdProductPricing());
								redemptionSave.setRedemptionDate(LocalDateTime.now());
								Coupon coupon = couponRepository.findByCouponCode(setStudentOrder.getCouponCode());
								redemptionSave.setDiscount(coupon.getDiscount());

								redemptionRepository.save(redemptionSave);
								Long updatedCount = coupon.getUsedCount() + 1;
								coupon.setUsedCount(updatedCount);
								couponRepository.save(coupon);
							}

							// orderId = studentSubscription.get;
							if (studentSubscription != null) {
								// adding payment details into SubscriptionPaymentHistory table
								Date paymentDate = Date.from(stagingStudentSubscriptionUpdate.getPurchaseDate());
								SubscriptionPaymentHistory subscriptionPaymentHistory = new SubscriptionPaymentHistory(
										studentSubscription.getIdStudentSubscription(),
										stagingStudentSubscriptionUpdate.getIdStudentOrder(), paymentDate,
										Float.valueOf(stagingStudentSubscriptionUpdate.getPurchaseAmount()));
								subscriptionPaymentHistoryRepository.save(subscriptionPaymentHistory);
								// calling getVideoRecordByIdProduct for StudentAssignedCourse
								if (studentSubscription.getPurchaseType().equals("NEW")) {
									offlineCourseService.getVideoRecordByIdProduct(studentSubscription);
								}
								orderId = stagingStudentSubscriptionUpdate.getOrderId();
								System.out.println("Studentid " + studentSubscription.getIdStudent());
								Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
								studentName = student.getUser().getFirstName();
								if (student != null) {
									Long userSurId = student.getUser().getUserSurId();
									System.out.println("userSurid " + student.getUser().getUserSurId());
									UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId,
											"MOBILE");
									System.out.println("userDevice " + userDevice);
									if (userDevice != null) {
										System.out.println("DeviceId " + userDevice.getDeviceId());
										regDeviceIdSet.add(userDevice.getDeviceId());
										// regDeviceIdList.add(userDevice.getDeviceId());
									}
								}
							}
						}
					}
					if (!regDeviceIdSet.isEmpty()) {
						System.out.println("orderId " + orderId);
						regDeviceIdList.addAll(regDeviceIdSet);
						System.out.println("regDeviceIdList " + regDeviceIdList);
						String message = "Dear " + studentName + " your payment received successfully for orderId "
								+ orderId;
						notificationService.sendNotification(regDeviceIdList, message, "order");
					}
					
					
					
				} else if (parameters.get("PAYMENTMODE") == null && respCode.equals("402")) {
					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
					if (setStudentOrder != null) {
						// update student order table status
						setStudentOrder.setOrderStatus("Failed");
						setStudentOrder = studentOrderRepository.save(setStudentOrder);
					}
					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(receptNum);

					if (stagingStudentSubscription.size() != 0) {
						for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
							stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
							stagingStudentSubscriptionUpdate
									.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
							stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
									.save(stagingStudentSubscriptionUpdate);

						}
					}

				}
			}
		} catch (Exception e) {
			parameters.put("MESSAGE", "Payment Failed");
			// removes parameters which is not required in receipt
			parameters.remove("errorMessage");
			parameters.remove("retryAllowed");
			parameters.remove("errorCode");
			// update student order data
			StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
			if (setStudentOrder != null && setStudentOrder.getOrderStatus().equals("Pending")) {
				// update student order table status
				setStudentOrder.setOrderStatus("Failed");
				setStudentOrder = studentOrderRepository.save(setStudentOrder);
			}
			// save response to staging student subscription and student subscription
			List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
					.findByOrderId(receptNum);

			if (stagingStudentSubscription.size() != 0) {
				for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
					stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
					stagingStudentSubscriptionUpdate
							.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
					stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
							.save(stagingStudentSubscriptionUpdate);

				}
			}
		} finally {
			// update batch status data
			StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(receptNum);
			if (setStudentOrder != null && setStudentOrder.getOrderStatus().equals("Pending")) {
				List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
						.findByOrderId(receptNum);
				if (stagingStudentSubscription.size() != 0) {
					for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
						Batch batchEdit = batchRepository.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
						if (batchEdit != null) {
							batchEdit.setPaymentStatus(null);
							batchRepository.save(batchEdit);
						}
					}
				}
			}
		}

		// remove attribute
		parameters.remove("CHECKSUMHASH");
		parameters.remove("GATEWAYNAME");
		parameters.remove("MID");
		parameters.remove("RESPCODE");
		parameters.remove("RESPMSG");
		parameters.remove("TXNID");

		// send Transaction email to the user
		try {
			emailService.sendInvoceThroughEmail(receptNum);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		// add attribute
		model.addAttribute("result", parameters.get("STATUS"));
		model.addAttribute("receptNum", receptNum);
		model.addAttribute("parameters", parameters);
		model.addAttribute("paymentCallBackURL", callBackURLPayment);
		return model;
	}

	/*
	 * Added by Meghana
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getStudentSubscriptionDetails(Long idStudent, String[] subscriptionType) {
		Document result = new Document();
		try {
			// StudentSubscription studentSubscription = new StudentSubscription();
			List<StudentSubscriptionBatchDTO> studentSubscriptionlist = new ArrayList<StudentSubscriptionBatchDTO>();

			for (String type : subscriptionType) {
				List<StudentSubscriptionBatchDTO> allBatches = studentSubscriptionRepository.getAllBatches(idStudent,
						type, true);
				if (!allBatches.isEmpty()) {
					studentSubscriptionlist.addAll(allBatches);
				}
			}

			if (studentSubscriptionlist.isEmpty())
				throw new NullPointerException("You Have not Subscribed to this course.");

			result.setData(studentSubscriptionlist);
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
	public Document getStudentSubMetaInfo(Long idStudent) {
		Document result = new Document();
		try {
			Student student = studentRepository.findByIdStudent(idStudent);
			if (student == null)
				throw new NullPointerException("Invalid Student Id .");

			ClassStandard standard = classRepository.findByIdClassStandard(student.getIdClassStandard());
			if (standard == null)
				throw new NullPointerException("Invalid ClassStandard Id .");

			List<StudentSubscription> subList = studentSubscriptionRepository.findByIdStudentAndActiveFlag(idStudent,
					true);

			if (subList.isEmpty())
				throw new NullPointerException("No StudentSubscription found.");

			Map<String, Object> endResult = new HashMap<String, Object>();
			Set<ProductLine> offData = new HashSet<ProductLine>();
			Set<ProductLine> bacthData = new HashSet<ProductLine>();

			for (StudentSubscription subscription : subList) {
				ProductLine prodLine = productLineRepository.findByidProductLine(subscription.getIdproductLine());
				if (prodLine == null)
					throw new NullPointerException("Invalid ProductLine Id.");

				if (("OFFLINE_VIDEO").equalsIgnoreCase(prodLine.getProductCategory())) {
					offData.add(prodLine);
				} else if (("Batch").equalsIgnoreCase(prodLine.getProductCategory())) {
					bacthData.add(prodLine);
				}
				// free video has to be added
			}

			List<ProductLine> offResult = offData.stream()
					.sorted((s1, s2) -> s1.getProductLine().compareTo(s2.getProductLine()))
					.collect(Collectors.toList());
			List<ProductLine> batchResult = bacthData.stream()
					.sorted((s1, s2) -> s1.getProductLine().compareTo(s2.getProductLine()))
					.collect(Collectors.toList());

			endResult.put("ClassStandard", standard);
			endResult.put("OFFLINE_VIDEO", offResult);
			endResult.put("Batch", batchResult);
			endResult.put("Free_video", null);
			endResult.put("Subscription", subList);
			result.setData(endResult);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getStudentSubOfflineData(Long idStudent, Long idProductLine) {
		Instant today = Instant.now();
		Document result = new Document();
		try {
			Student student = studentRepository.findByIdStudent(idStudent);
			if (student == null)
				throw new NullPointerException("Invalid Student Id.");

			List<StudentSubscription> subcscriptionList = studentSubscriptionRepository
					.findByIdStudentAndIdproductLineAndActiveFlag(idStudent, idProductLine, true);

			if (subcscriptionList.isEmpty())
				throw new NullPointerException("You Have not Subscribed to this course.");

			List<Object> endResult = new ArrayList<Object>();

			for (int i = 0; i < subcscriptionList.size(); i++) {
				Map<String, Object> wrapperVar = new HashMap<String, Object>();
				// productGroup
				if (subcscriptionList.get(i).getPurchaseLevel().equalsIgnoreCase("GROUP")
						&& subcscriptionList.get(i).getIdProductGroup() != null) {
					ProductGroup pg = productGroupRepository
							.findByIdProductGroup(subcscriptionList.get(i).getIdProductGroup());
					if (pg == null)
						throw new NullPointerException("No ProductGroup Found!");

					wrapperVar.put("Group", pg.getProductGroupName());
					wrapperVar.put("subLastpaymentDate", subcscriptionList.get(i).getLastPaymentDate());
					wrapperVar.put("subNextpaymentDate", subcscriptionList.get(i).getNextPaymentDate());
					wrapperVar.put("subEndDate", subcscriptionList.get(i).getSubscriptionEndDate());
					wrapperVar.put("subscriptionId", subcscriptionList.get(i).getIdStudentSubscription());

					List<Product> prodList = productRepository.findByIdProductGroupAndActiveFlag(pg.getIdProductGroup(),
							Boolean.TRUE);

					if (prodList.isEmpty())
						throw new NullPointerException("No Product List  Found!");

					List<Object> resultTemp = new ArrayList<Object>();

					for (Product product : prodList) {
						Map<String, Object> temp = new HashMap<String, Object>();
						Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());
						if (sub == null)
							throw new NullPointerException("No Subject Found!");
						List<SubjectChapter> chapters = subjectChapterRepository.findByIdSubject(sub.getIdSubject());
						if (chapters.isEmpty())
							throw new NullPointerException("No SubjectChapter Found!");
						temp.put("idProduct", product.getIdProduct());
						temp.put("Subject", sub);
						temp.put("Chapters", chapters);
						temp.put("Completed", this.overAllSubjectCompletion(sub.getIdSubject(),
								subcscriptionList.get(i).getIdStudentSubscription()));
						resultTemp.add(temp);
					}

					if (subcscriptionList.get(i).getSubscriptionEndDate().compareTo(today) < 0) {
						wrapperVar.put("Data", null);
					} else {
						wrapperVar.put("Data", resultTemp);
					}
				} else if (subcscriptionList.get(i).getPurchaseLevel().equalsIgnoreCase("PRODUCT")
						&& subcscriptionList.get(i).getIdProduct() != null) {
					Product prod = productRepository
							.findByIdProductAndActiveFlag(subcscriptionList.get(i).getIdProduct(), Boolean.TRUE);
					if (prod == null)
						throw new NullPointerException("No Product Found!");
					wrapperVar.put("Group", prod.getProductName());
					wrapperVar.put("subLastpaymentDate", subcscriptionList.get(i).getLastPaymentDate());
					wrapperVar.put("subNextpaymentDate", subcscriptionList.get(i).getNextPaymentDate());
					wrapperVar.put("subEndDate", subcscriptionList.get(i).getSubscriptionEndDate());
					wrapperVar.put("subscriptionId", subcscriptionList.get(i).getIdStudentSubscription());

					List<Object> resultTemp = new ArrayList<Object>();

					Map<String, Object> temp = new HashMap<String, Object>();

					Subject sub = subjectRepository.findByIdSubject(prod.getIdSubject());
					if (sub == null)
						throw new NullPointerException("No Subject Found!");

					List<SubjectChapter> chapters = subjectChapterRepository.findByIdSubject(sub.getIdSubject());
					if (chapters.isEmpty())
						throw new NullPointerException("No SubjectChapter Found!");
					temp.put("idProduct", prod.getIdProduct());
					temp.put("Subject", sub);
					temp.put("Chapters", chapters);
					temp.put("Completed", this.overAllSubjectCompletion(sub.getIdSubject(),
							subcscriptionList.get(i).getIdStudentSubscription()));

					resultTemp.add(temp);

					if (subcscriptionList.get(i).getSubscriptionEndDate().compareTo(today) < 0
							&& subcscriptionList.get(i).getActiveFlag()) {

						wrapperVar.put("Data", null);

					} else {
						wrapperVar.put("Data", resultTemp);
					}

				}

				endResult.add(wrapperVar);

			}

			result.setData(endResult);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getStudentSubStreamingByChapter(Long idStudent, Long idProductLine, Long idSubject,
			Long idSubjectChapter, Long idSubscription) {

		Instant today = Instant.now();

		Document result = new Document();

		try {
			// further implementation should be updated for secure streaming
			Student student = studentRepository.findByIdStudent(idStudent);

			if (student == null)
				throw new NullPointerException("Invalid Student Id.");

			StudentSubscription studSub = studentSubscriptionRepository.findByIdStudentSubscription(idSubscription);

			if (studSub == null)
				throw new NullPointerException("No Subscription Found For the Selected Product.");

			else if (studSub.getSubscriptionEndDate().compareTo(today) < 0)
				throw new AppException("Your Subscription got Expired on  " + studSub.getSubscriptionEndDate() + " ");

			List<StudentAssignedCourse> topicList = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdSubjectAndIdSubjectChapterOrderByVideoSeqNumberAsc(idSubscription,
							idSubject, idSubjectChapter);

			if (topicList.isEmpty())
				throw new NullPointerException("Something went Wrong , \n No data found for your subscription. ");

			result.setData(topicList);
			result.setStatusCode(HttpStatus.OK.value());
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

	/**
	 * 
	 * This method return overall Subject Courses completion in percentage
	 * 
	 * @param idSubject
	 * @param idStudentSubscribe
	 * @return String
	 * @throws Exception
	 */

	private String overAllSubjectCompletion(Long idSubject, Long idStudentSubscribe) throws Exception {

		List<String> marketingIds = new ArrayList<String>();
		marketingIds.add("223a45dbb2b94bbabeae667e84c70f3a");
		marketingIds.add("8371301d9367416ca2536302b55ab59a");
		marketingIds.add("4eb7d1b2516640e8970a79d211312482");
		marketingIds.add("776458c7fb5a413ca201f5a43afa36bd");
		marketingIds.add("26bef27760334086aa02be05556a21f0");
		marketingIds.add("7675a846579949b5b8d685742359a785");
		marketingIds.add("4d0ed11baa5142e1a3e9ca33309d9e28");
		marketingIds.add("c9333255a29b4ba7867d0a1f198c819a");
		marketingIds.add("91ff96921ba140939ae93dfe5c7b64c8");
		marketingIds.add("fba4b9a786bb449f9d1e9ce7d9cc8ebe");
		marketingIds.add("55040db5350040e79ee23a108d2a4f72");

		List<StudentAssignedCourse> listVideos = studentAssignedCourseRepository
				.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotIn(idStudentSubscribe, idSubject,
						marketingIds);

		if (listVideos.isEmpty())
			return "0";

		int totalDuration = 0;

		int completedDuration = 0;

		for (StudentAssignedCourse course : listVideos) {

			totalDuration += course.getVideoDuration();
			if (course.getCompleteFlag()) {
				completedDuration += course.getVideoDuration();
			} else {
				completedDuration += course.getVideoCoverageDuration();
			}
		}
        if(totalDuration==0)return "0";
		double meanValue = (double) completedDuration / totalDuration;
		meanValue *= 100;
		return Double.toString(Math.round(meanValue));

	}

	@Override
	@Async
	public void checkSubscriptionExpiry() {
		List<String> result = new ArrayList<>();
		System.out.println("Started Student Subscription Expiry Check.");

		Instant startTime, endTime;

		startTime = Instant.now();
		// total number of de-activation
		int tnoda = 0;

		// deactivate batch counts
		int dabc = 0;

		int errCount = 0;

		result.add("Started Student Subscription Expiry Check.");
		result.add("Started time: " + startTime);
		List<StudentSubscription> studentSubscriptionList = studentSubscriptionRepository
				.findAllByActiveFlagAndFreeFlagAndSubscriptionEndDateIsNotNull(true, false);
		if (studentSubscriptionList.isEmpty()) {
			System.err.println("No Active Student Subscriptions found.");
			result.add("No Active Student Subscriptions found.");
		} else {
			for (StudentSubscription ss : studentSubscriptionList) {
				if (ss.getSubscriptionEndDate().compareTo(Instant.now()) < 0) {
					ss.setActiveFlag(false);
					System.out.println("Deactivating studentSubscription for id:" + ss.getIdStudentSubscription());
					result.add("Deactivating studentSubscription for id:" + ss.getIdStudentSubscription());
					tnoda++;
					if (ss.getIdBatch() != null) {
						Batch batch = batchRepository.findByIdBatch(ss.getIdBatch());
						if (batch == null) {
							System.err.println(
									"Invalid idBatch found in idStudentSubscription: " + ss.getIdStudentSubscription());
							result.add(
									"Invalid idBatch found in idStudentSubscription: " + ss.getIdStudentSubscription());
							errCount++;
						} else {
							System.out.println("Updating the Batch Record, where idBatch:" + batch.getIdBatch());
							result.add("Updating the Batch Record, where idBatch:" + batch.getIdBatch() + ","
									+ "Batch Name: " + batch.getBatchName());
							if (batch.getCurrentOccupancy() > 0) {
								batch.setCurrentOccupancy(batch.getCurrentOccupancy() - 1);
								batch.setCurrentVacancy(batch.getCurrentVacancy() + 1);
								batchRepository.save(batch);
								dabc++;
							}
						}
					}
				}
			}
			studentSubscriptionRepository.saveAll(studentSubscriptionList);
			LOGGER.info("Student Subscription Expiry Check Completed!!!");

		}

		endTime = Instant.now();
		result.add("Total No.of active subscriptions founded: " + studentSubscriptionList.size() + " .");
		result.add("Total No.of subscriptions deactivated: " + tnoda + " .");
		result.add("Total No.of batch counts updated: " + dabc + " .");
		result.add("Total No.of Errors Occured: " + errCount + " .");
		result.add("End Time:" + endTime);
		result.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		result.add("Student Subscription Expiry Check Completed!!!");

		String fileName = "StudentSubscriptionExpiryCheckLog_" + Instant.now() + ".txt";
		// log the file to s3 location
		try(FileWriter writer = new FileWriter(fileName);) {

			for (String str : result) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();

			File file = new File(fileName);

			fileUploadService.uploadFileToS3Bucket("/logs/scheduler/check-student-subscription-expiry", fileName, file);

			boolean isDeletedFile = file.delete();
			LOGGER.info("Log file deleted from the system : " +isDeletedFile );

		} catch (IOException e) {
			
			LOGGER.error(e.getMessage());
		}

		// return result;
	}

	@Override
	public void checkUserSubscriptionExpiry() {
		System.out.println("Started Student Subscription Expiry Check.");
		List<StudentSubscription> studentSubscriptionList = studentSubscriptionRepository
				.findAllByActiveFlagAndFreeFlagAndSubscriptionEndDateIsNotNull(true, true);
		if (studentSubscriptionList.isEmpty()) {
			System.err.println("No Active Student Subscriptions found");
		} else {
			for (StudentSubscription ss : studentSubscriptionList) {
				if (ss.getSubscriptionEndDate().compareTo(Instant.now()) < 0) {
					ss.setActiveFlag(false);
					System.out.println("Deactivating studentSubscription for id:" + ss.getIdStudentSubscription());
				}
			}
			studentSubscriptionRepository.saveAll(studentSubscriptionList);
			System.out.println("Student Subscription Expiry Check Completed!!!");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getOrderDetailsByUserSurId(Long userSurId) {
		Document result = new Document();
		try {

			/*
			 * Updated by @author Naveen Kumar Fetching user data based on session
			 * information instead of parameter which sent through request.
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

			List<OrderDTO> orderList = new ArrayList<OrderDTO>();
			List<StudentOrder> studentOrderList = studentOrderRepository.findByUserSurId(user.getUserSurId());
			studentOrderList.stream()
					.filter(so -> so.getOrderStatus().equals("Pending")
							&& TimeComparison.minutesDifferenceTwoInstant(so.getOrderDate(), Instant.now()) >= 5L)
					.peek(eachOrder -> eachOrder.setOrderStatus("Cancel")).collect(Collectors.toList());

			studentOrderList = studentOrderList.stream()
					.sorted(Comparator.comparing(StudentOrder::getOrderDate).reversed()).collect(Collectors.toList());

			if (!studentOrderList.isEmpty()) {
				for (StudentOrder order : studentOrderList) {
					String oderStatus = order.getOrderStatus();
					if (oderStatus.equalsIgnoreCase("Success")) {
						OrderDTO dto = new OrderDTO();
						dto.setIdStudentOrder(order.getIdStudentOrder());
						dto.setOrderId(order.getOrderId());
						dto.setAmount(order.getTotalAmount());
						dto.setOrderDate(order.getOrderDate());
						dto.setOrderStatus(order.getOrderStatus());
						dto.setExpanded(false);
						Boolean diputeFlag = this.getDistputeStatus(order.getIdStudentOrder());
						dto.setDisputeFlag(diputeFlag);
						dto.setSecondaryStatus(order.getSecondaryStatus());

						List<StagingStudentSubscription> subsctriptionList = stagingStudentSubscriptionRepository
								.findByIdStudentOrder(order.getIdStudentOrder());

						if (subsctriptionList.isEmpty()) {
							dto.setProductList(new ArrayList<ProductDTO>());
						} else {

							List<ProductDTO> productList = new ArrayList<ProductDTO>();
							for (StagingStudentSubscription subscription : subsctriptionList) {
								ProductDTO productDto = new ProductDTO();
								String batchName = "";
								Batch batch = batchRepository.findByIdBatch(subscription.getIdBatch());
								if (batch != null)
									batchName = "(" + batch.getBatchName() + ")";

								if (subscription.getIdProduct() != null) {
									Product product = productRepository
											.findByIdProductAndActiveFlag(subscription.getIdProduct(), Boolean.TRUE);
									if (product != null) {
										productDto.setProductName(product.getProductName() + batchName);
									}
								}
								productDto.setSubscriptionType(subscription.getSubscriptionType());
								productDto.setPurchaseType(subscription.getPurchaseType());
								productDto.setNextPaymentDate(subscription.getNextPaymentDate());
								productList.add(productDto);
							}
							dto.setProductList(productList);
						}

						orderList.add(dto);
					} else {
						OrderDTO dto = new OrderDTO();
						dto.setIdStudentOrder(order.getIdStudentOrder());
						dto.setOrderId(order.getOrderId());
						dto.setAmount(order.getTotalAmount());
						dto.setOrderDate(order.getOrderDate());
						dto.setOrderStatus(order.getOrderStatus());
						dto.setExpanded(false);
						Boolean diputeFlag = this.getDistputeStatus(order.getIdStudentOrder());
						dto.setDisputeFlag(diputeFlag);
						dto.setSecondaryStatus(order.getSecondaryStatus());
						orderList.add(dto);
					}
				}
			}
			result.setData(orderList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully get order details");
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Transactional
	@Override
	public Document<StudentSubscription> checkStudentSubscriptionByIdSubject(Long idSubject) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Document<StudentSubscription> result = new Document<>();

		try {
			Student student = null;

			Long idSyllabus, idClassStandard, idState;

			User user = userRepository.findByUsername(userName);

			idSyllabus = null;
			idClassStandard = null;
			idState = null;

			if (user == null)
				throw new NullPointerException("Invalid user");

			if (user.getRegisteredAs().equalsIgnoreCase("Student")) {
				student = studentRepository.findByUser(user);
			}
			
			if (student == null)
                throw new NullPointerException("Invalid Student info found in user record.");

			idClassStandard = student.getIdClassStandard();
			idSyllabus = student.getIdSyllabus();
			idState = student.getIdState();

			Product prod = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(5L,
							idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("No Product Found!");

			StudentSubscription ss = studentSubscriptionRepository.findFirstByIdStudentAndIdProductAndActiveFlag(
					student.getIdStudent(), prod.getIdProduct(), Boolean.TRUE);

			if (ss != null) {
				result.setData(ss);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully get order details");
			} else {

				StudentSubscription temp = new StudentSubscription();
				temp.setActiveFlag(Boolean.TRUE);
				temp.setIdProduct(prod.getIdProduct());
				temp.setFreeFlag(Boolean.TRUE);
				temp.setIdProductGroup(prod.getIdProductGroup());
				temp.setIdproductLine(prod.getIdProductLine());
				temp.setIdStudent(student.getIdStudent());
				temp.setUserSurId(user.getUserSurId());
				result.setData(studentSubscriptionRepository.save(temp));
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully get order details");
			}

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
	public Document<StudentSubscriptionSubjectDTO> studentSubjectBySubscription(Long idStudentSubscription) {
		Document<StudentSubscriptionSubjectDTO> result = new Document<>();

		try {
			Boolean accessAllowed = false;

			StudentSubscription ss = studentSubscriptionRepository.findByIdStudentSubscription(idStudentSubscription);

			if (ss == null)
				throw new NullPointerException("Invalid Student Subscription");

			Product product = productRepository.findByIdProductAndActiveFlag(ss.getIdProduct(), Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found!");

			ClassStandard standard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (standard == null)
				throw new NullPointerException("No ClassStandard Found!");

			Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

			if (sub == null)
				throw new NullPointerException("No Subject Found!");

			List<SubjectChapter> chapters = subjectChapterRepository.findByIdSubject(sub.getIdSubject());

			if (chapters.isEmpty())
				throw new NullPointerException("No Chapters Found!");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("No Syllabus Found!");

			List<StudentAssignedCourse> sacList = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdProductAndCompleteFlag(idStudentSubscription,
							product.getIdProduct(), Boolean.TRUE);

			StudentSubscriptionSubjectDTO sssDTO = new StudentSubscriptionSubjectDTO();

			if (sacList.isEmpty()) {
				sssDTO.setPercentageCompletion("0");
			} else {
				double meanValue = (double) sacList.size() / product.getTotalVideoCount();
				meanValue *= 100;
				sssDTO.setPercentageCompletion(Double.toString(Math.round(meanValue)));
			}

			List<StreamingSubjectChapterDTO> finalStreamingChapters = new ArrayList<>();
			for (SubjectChapter sc : chapters) {
				StreamingSubjectChapterDTO sscd = new StreamingSubjectChapterDTO();
				sscd.setChapter(sc);
				sscd.setAccessAllowed(accessAllowed);
				finalStreamingChapters.add(sscd);
			}

			sssDTO.setStreamingSubjectChapters(finalStreamingChapters);
			sssDTO.setClassStandardName(standard.getClassStandadName());
			sssDTO.setSyllabusName(syllabus.getSyllabusName());
			sssDTO.setSubjectName(sub.getSubjectName());
			result.setData(sssDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<StudentStreamingInfoDTO> studentFreeSubscriptionStreamingData(Long idSubject, Long idSubjectChapter,
			Long idState, Long idClassStandard, Long idSyllabus, String language, Long idStudentSubscription) {

		Document<StudentStreamingInfoDTO> result = new Document<>();

		StudentStreamingInfoDTO ssiDto = new StudentStreamingInfoDTO();

		try {
			Product prod = null;

			if (idClassStandard == 4 && idSyllabus == 4) {
				prod = productRepository
						.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(6L,
								idClassStandard, idSyllabus, idState, idSubject, Boolean.TRUE);
			} else {
				prod = productRepository
						.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(5L,
								idClassStandard, idSyllabus, idState, idSubject, Boolean.TRUE);
			}

			if (prod == null)
				throw new NullPointerException("No Product found!");

			SubjectChapter sc = subjectChapterRepository.findByIdSubjectChapterAndActiveFlag(idSubjectChapter,Boolean.TRUE);

			if (sc == null)
				throw new NullPointerException("Invalid Subject Chapter found!");

			if (language.equalsIgnoreCase("English")) {

				List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
						.findByIdSubjectAndIdSubjectChapterAndIdProductOrderByVideoSeqNumberAsc(idSubject,
								idSubjectChapter, prod.getIdProduct());

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

				List<Object> _fresult = new ArrayList<>();

				temp3.stream().forEach(t -> {
					List<Object> al;
					al = _ftemp.stream().filter(ts -> ts.getHeading().equalsIgnoreCase(t)).collect(Collectors.toList());
					_fresult.add(al);
				});

				// manipulate header to remove sequence number.
				for (int i = 0; i < _fresult.size(); i++) {
					@SuppressWarnings("unchecked")
					List<FreeOfflineVideoCourseResponseDTO> freeList = (List<FreeOfflineVideoCourseResponseDTO>) _fresult
							.get(i);

					for (FreeOfflineVideoCourseResponseDTO ffvcrDTO : freeList) {
						String heading = ffvcrDTO.getHeading();

						if (heading.contains("|")) {
							String arr[] = heading.split("\\|");

							ffvcrDTO.setHeading(arr[1]);

						}

					}

				}

				ssiDto.setChapterVideoCourseList(_fresult);

			} else {
				Language lang = languageRepository.findByLanguage(language.trim());

				if (lang == null)
					throw new NullPointerException("Invalid Language");

				List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
						.findByIdSubjectAndIdSubjectChapterAndIdProductOrderByVideoSeqNumberAsc(idSubject,
								idSubjectChapter, prod.getIdProduct());

				if (streamingList.isEmpty())
					throw new AppException("No video Course found!!!");

				List<FreeOfflineVideoCourseResponseDTO> temp = new ArrayList<>();
				for (OfflineVideoCourse o : streamingList) {
					TopicLanguage tl = topicLanguageRepository
							.findByIdLanguageAndOfflineVideoCourse(lang.getIdLanguage(), o);

					if (tl == null)
						throw new AppException("No Video Content Available for " + lang.getLanguage() + " .");

					FreeOfflineVideoCourseResponseDTO ffvc = new FreeOfflineVideoCourseResponseDTO();
					BeanUtils.copyProperties(o, ffvc);
					ffvc.setHeading(tl.getHeading());
					ffvc.setQuestion(tl.getQuestion());
					ffvc.setAnswer(tl.getAnswer());
					ffvc.setPdfURL(tl.getPdfURL());
					temp.add(ffvc);
				}

				List<FreeOfflineVideoCourseResponseDTO> _ftemp = new ArrayList<>();

				_ftemp.addAll(temp);

				HashSet<String> headers = new HashSet<>();

				temp.removeIf(e -> !headers.add(e.getHeading()));

				List<String> temp3 = new ArrayList<>(headers);

				Collections.sort(temp3);

				List<Object> _fresult = new ArrayList<>();

				temp3.stream().forEach(t -> {

					List<FreeOfflineVideoCourseResponseDTO> al;
					al = _ftemp.stream().filter(ts -> ts.getHeading().equalsIgnoreCase(t)).collect(Collectors.toList());
					_fresult.add(al);
				});

				for (int i = 0; i < _fresult.size(); i++) {
					@SuppressWarnings("unchecked")
					List<FreeOfflineVideoCourseResponseDTO> freeList = (List<FreeOfflineVideoCourseResponseDTO>) _fresult
							.get(i);

					for (FreeOfflineVideoCourseResponseDTO ffvcrDTO : freeList) {
						String heading = ffvcrDTO.getHeading();

						if (heading.contains("|")) {
							String arr[] = heading.split("\\|");

							ffvcrDTO.setHeading(arr[1]);

						}

					}

				}

				ssiDto.setChapterVideoCourseList(_fresult);

			}
			// check for assessment in using student subscription

			Boolean freeChapterAvailablityFlag = checkChapterAvailablityForFreeStreaming(idSubjectChapter,
					idClassStandard, idSubject, idState, idSyllabus);

			if (freeChapterAvailablityFlag) {
				ssiDto.setPrevChapterAttemptFlag(true);
			} else {

				if (!idStudentSubscription.equals(-1L)) {
					/**
					 * updated by @author NAVEEN KUMAR A
					 * 
					 * This functionality has been added to the new requirement received on 28/12/21
					 * 
					 * It check whether user is subscribed if any user subscription found for the
					 * particular user it keep chapter quiz attempt flag to be true.
					 * 
					 * 
					 */

					UserPrincipal userPrincipal = null;
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

					Boolean accessAllowed = false;

					// check logged in user accessing
					if (!(authentication instanceof AnonymousAuthenticationToken)) {

						userPrincipal = (UserPrincipal) authentication.getPrincipal();

						if (userPrincipal == null)
							throw new AppException("Invalid User");

						Boolean subscriptionFlag = false;

						// check whether user is subscribed.
						SubscribedUserDTO suDTO = checkUserSubscribed(userPrincipal.getUserSurId());

						subscriptionFlag = suDTO != null ? true : false;

						if (subscriptionFlag)
							accessAllowed = true;

					} else {
						accessAllowed = false;

					}

					SubjectChapter prevSC = subjectChapterRepository
							.findTopByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndActiveFlagAndSortOrderLessThanOrderBySortOrderDesc(
									idClassStandard, idSubject, idState, idSyllabus,Boolean.TRUE, sc.getSortOrder());

					if (prevSC == null)
						throw new AppException("Invalid SubjectChapter data.");

					// fetch previous chapter quiz
					List<StudentChapterQuiz> stqList = studentChapterQuizRepository
							.findByIdSubjectChapterAndIdStudentSubscrOrderByQuizDateDesc(prevSC.getIdSubjectChapter(),
									idStudentSubscription);

					Boolean prevAttemptFlag = false;

					prevAttemptFlag = stqList.isEmpty() ? false : true;

					if (accessAllowed) {
						prevAttemptFlag = true;
					} else {
						prevAttemptFlag = stqList.isEmpty() ? false : true;
					}

					ssiDto.setPrevChapterAttemptFlag(prevAttemptFlag);

				} else {
					ssiDto.setPrevChapterAttemptFlag(false);
				}

			}

			result.setData(ssiDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;

	}

	/** Offline Video courses on admin and teacher screen **/

	@Override
	public Document<List<Object>> adminStreamingData(Long idSubject, Long idSubjectChapter, Long idState,
			Long idClassStandard, Long idSyllabus, String language) {

		Document<List<Object>> result = new Document<>();
		try {
			Product prod = null;

			if (idClassStandard == 4 && idSyllabus == 4) {
				prod = productRepository
						.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(6L,
								idClassStandard, idSyllabus, idState, idSubject, Boolean.TRUE);
			} else {
				prod = productRepository
						.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(5L,
								idClassStandard, idSyllabus, idState, idSubject, Boolean.TRUE);
			}

			if (prod == null)
				throw new NullPointerException("No Product found!");

			if (language.equalsIgnoreCase("English")) {

				List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
						.findByIdSubjectAndIdSubjectChapterAndIdProductOrderByVideoSeqNumberAsc(idSubject,
								idSubjectChapter, prod.getIdProduct());

				if (streamingList.isEmpty())
					throw new AppException("No video Course found!!!");

				List<OfflineVideoCourseResponsewithOTP> temp = new ArrayList<>();
				streamingList.stream().forEach(o -> {
					OfflineVideoCourseResponsewithOTP ffvc = new OfflineVideoCourseResponsewithOTP();
					BeanUtils.copyProperties(o, ffvc);
					temp.add(ffvc);
				});

				List<OfflineVideoCourseResponsewithOTP> _ftemp = new ArrayList<>();

				_ftemp.addAll(temp);

				HashSet<String> headers = new HashSet<>();

				temp.removeIf(e -> !headers.add(e.getHeading()));

				List<String> temp3 = new ArrayList<>(headers);

				Collections.sort(temp3);

				List<Object> _fresult = new ArrayList<>();

				temp3.stream().forEach(t -> {
					List<OfflineVideoCourseResponsewithOTP> al;
					al = _ftemp.stream().filter(ts -> ts.getHeading().equalsIgnoreCase(t)).collect(Collectors.toList());
					_fresult.add(al);
				});

				// manipulate header to remove sequence number.
				for (int i = 0; i < _fresult.size(); i++) {
					@SuppressWarnings("unchecked")
					List<OfflineVideoCourseResponsewithOTP> freeList = (List<OfflineVideoCourseResponsewithOTP>) _fresult
							.get(i);

					for (OfflineVideoCourseResponsewithOTP ffvcrDTO : freeList) {
						String heading = ffvcrDTO.getHeading();

						if (heading.contains("|")) {
							String arr[] = heading.split("\\|");

							ffvcrDTO.setHeading(arr[1]);

						}

					}

				}

				result.setData(_fresult);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull Request");
			} else {
				Language lang = languageRepository.findByLanguage(language.trim());

				if (lang == null)
					throw new NullPointerException("Invalid Language");

				List<OfflineVideoCourse> streamingList = offlineVideoCourseRepository
						.findByIdSubjectAndIdSubjectChapterAndIdProductOrderByVideoSeqNumberAsc(idSubject,
								idSubjectChapter, prod.getIdProduct());

				if (streamingList.isEmpty())
					throw new AppException("No video Course found!!!");

				List<OfflineVideoCourseResponsewithOTP> temp = new ArrayList<>();
				for (OfflineVideoCourse o : streamingList) {
					TopicLanguage tl = topicLanguageRepository
							.findByIdLanguageAndOfflineVideoCourse(lang.getIdLanguage(), o);

					if (tl == null)
						throw new AppException("No Video Content Available for " + lang.getLanguage() + " .");

					OfflineVideoCourseResponsewithOTP ffvc = new OfflineVideoCourseResponsewithOTP();
					BeanUtils.copyProperties(o, ffvc);
					ffvc.setHeading(tl.getHeading());
					ffvc.setQuestion(tl.getQuestion());
					ffvc.setAnswer(tl.getAnswer());
					ffvc.setPdfURL(tl.getPdfURL());
					temp.add(ffvc);
				}

				List<OfflineVideoCourseResponsewithOTP> _ftemp = new ArrayList<>();

				_ftemp.addAll(temp);

				HashSet<String> headers = new HashSet<>();

				temp.removeIf(e -> !headers.add(e.getHeading()));

				List<String> temp3 = new ArrayList<>(headers);

				Collections.sort(temp3);

				List<Object> _fresult = new ArrayList<>();

				temp3.stream().forEach(t -> {

					List<OfflineVideoCourseResponsewithOTP> al;
					al = _ftemp.stream().filter(ts -> ts.getHeading().equalsIgnoreCase(t)).collect(Collectors.toList());
					_fresult.add(al);
				});

				for (int i = 0; i < _fresult.size(); i++) {
					@SuppressWarnings("unchecked")
					List<OfflineVideoCourseResponsewithOTP> freeList = (List<OfflineVideoCourseResponsewithOTP>) _fresult
							.get(i);

					for (OfflineVideoCourseResponsewithOTP ffvcrDTO : freeList) {
						String heading = ffvcrDTO.getHeading();

						if (heading.contains("|")) {
							String arr[] = heading.split("\\|");

							ffvcrDTO.setHeading(arr[1]);

						}

					}

				}

				result.setData(_fresult);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull Request");

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
	public Document<StudentAssignedCourse> getStudentAssignedCourse(Long idOfflineVideoCourse,
			Long idStudentSubscription) {

		Document<StudentAssignedCourse> result = new Document<>();

		try {

			StudentAssignedCourse sac = studentAssignedCourseRepository
					.findByIdOfflineVideoCourseAndIdStudentSubscription(idOfflineVideoCourse, idStudentSubscription);

			if (sac == null) {
				OfflineVideoCourse offlineVideoCourse = offlineVideoCourseRepository
						.findByIdOfflineVideoCourse(idOfflineVideoCourse);
				sac = new StudentAssignedCourse();

				Subject subject = subjectRepository.findByIdSubject(offlineVideoCourse.getIdSubject());
				if (subject == null)
					throw new NullPointerException("No Subject Found!");

				SubjectChapter subjectChapter = subjectChapterRepository
						.findByIdSubjectChapter(offlineVideoCourse.getIdSubjectChapter());

				if (subjectChapter == null)
					throw new NullPointerException("No SubjectChapter Found!");

				sac.setIdStudentSubscription(idStudentSubscription);
				sac.setLastAccessedDate(null);
				sac.setCompleteFlag(false);
				sac.setVideoCoverageDuration(0);
				sac.setPctComplete("0");
				sac.setSubjectName(subject.getSubjectName());
				sac.setChapterName(subjectChapter.getChapterName());
				BeanUtils.copyProperties(offlineVideoCourse, sac);
				sac = studentAssignedCourseRepository.save(sac);

			}

			result.setData(sac);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;
	}

	@Override
	public Document<StudentSubscriptionSubjectDTO> studentSubjectDataBySubjectAndClassStandardAndSyllabusAndState(
			Long idProductLine, Long idSubject, Long idClassStandard, Long idSyllabus, Long idState,
			Long idStudentSubscription) {

		Document<StudentSubscriptionSubjectDTO> result = new Document<>();
		try {

			Boolean accessAllowed = false;

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");

				// intalizing the two flag
				Boolean newUserFlag, subscriptionFlag = false;

				// check whether user is subscribed.
				SubscribedUserDTO suDTO = checkUserSubscribed(userPrincipal.getUserSurId());

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
			StudentSubscription ss = null;

			if (idStudentSubscription != null) {
				ss = studentSubscriptionRepository.findByIdStudentSubscription(idStudentSubscription);
			}

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
							idProductLine, idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found!");

			ClassStandard standard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (standard == null)
				throw new NullPointerException("No ClassStandard Found!");

			Subject sub = subjectRepository.findByIdSubject(product.getIdSubject());

			if (sub == null)
				throw new NullPointerException("No Subject Found!");

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

			List<StreamingSubjectChapterDTO> finalStreamingChapters = new ArrayList<>();
			for (SubjectChapter sc : chapters) {
				StreamingSubjectChapterDTO sscd = new StreamingSubjectChapterDTO();
				sscd.setChapter(sc);
				if (accessAllowed) {
					sscd.setAccessAllowed(true);
				} else {

					Boolean freeChapterFlag = checkChapterAvailablityForFreeStreaming(sc.getIdSubjectChapter(),
							idClassStandard, idSubject, idState, idSyllabus);
					sscd.setAccessAllowed(freeChapterFlag);
				}

				finalStreamingChapters.add(sscd);
			}

			StudentSubscriptionSubjectDTO sssDTO = new StudentSubscriptionSubjectDTO();

			if (ss != null) {
				List<StudentAssignedCourse> sacList = studentAssignedCourseRepository
						.findByIdStudentSubscriptionAndIdProductAndCompleteFlag(idStudentSubscription,
								product.getIdProduct(), Boolean.TRUE);

				if (sacList.isEmpty()) {
					sssDTO.setPercentageCompletion("0");
				} else {
					double meanValue = (double) sacList.size() / product.getTotalVideoCount();
					meanValue *= 100;
					sssDTO.setPercentageCompletion(Double.toString(Math.round(meanValue)));
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

	@Override
	@Transactional
	public Document<StudentAssignedCourse> getStudentAssignedCourseByOfflineCourse(Long idOfflineVideoCourse) {

		Document<StudentAssignedCourse> result = new Document<>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();

		try {

			Student student = studentRepository
					.getStudentByUser_UserSurId(userRepository.findByUsername(userName).getUserSurId());

			if (student == null)
				throw new NullPointerException("Invalid Student");

			OfflineVideoCourse offlineVideoCourse = offlineVideoCourseRepository
					.findByIdOfflineVideoCourse(idOfflineVideoCourse);

			if (offlineVideoCourse == null)
				throw new NullPointerException("Invalid OfflineVideoCourse");

			Product prod = productRepository.findByIdProductAndActiveFlag(offlineVideoCourse.getIdProduct(),
					Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("No Product Found!");

			Boolean contentAccessFlag = userContentAccessValidator
					.isAllowedToAccessTheContent(prod.getIdClassStandard(), prod.getIdSyllabus(), prod.getIdState());

			if (!contentAccessFlag) {
				result.setData(null);
				result.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				result.setMessage("User dosent have access to view this content.");
				return result;
			}

			StudentPostLoginDTO splDTO = userSubscriptionCheck
					.checkExistingSubscriptionLogin(student.getUser().getUserSurId());
			// false // false
			if (!splDTO.getSubscribedFlag() && !splDTO.getTrialFlag()) {

				// check whether the video content is subscription content
				Boolean subscribedContent = this.checkChapterAvailablityForFreeStreaming(
						offlineVideoCourse.getIdSubjectChapter(), prod.getIdClassStandard(), prod.getIdSubject(),
						prod.getIdState(), prod.getIdSyllabus());

				if (!subscribedContent) {
					// check the if the user is subscribed
					Boolean validateSubscription = userContentAccessValidator.isAllowedSubscription(subscribedContent);

					if (!validateSubscription) {
						result.setData(null);
						result.setStatusCode(HttpStatus.PAYMENT_REQUIRED.value());
						result.setMessage("Please purchase premium subscription to access this content.");
						return result;
					}

				}

			}

			StudentSubscription ss = studentSubscriptionRepository.findFirstByIdStudentAndIdProductAndActiveFlag(
					student.getIdStudent(), prod.getIdProduct(), Boolean.TRUE);

			if (ss == null) {
				StudentSubscription temp = new StudentSubscription();
				temp.setActiveFlag(Boolean.TRUE);
				temp.setIdProduct(prod.getIdProduct());
				temp.setFreeFlag(Boolean.TRUE);
				temp.setIdProductGroup(prod.getIdProductGroup());
				temp.setIdproductLine(prod.getIdProductLine());
				temp.setIdStudent(student.getIdStudent());
				temp.setUserSurId(student.getUser().getUserSurId());

				ss = studentSubscriptionRepository.save(temp);
			}

			StudentAssignedCourse sac = studentAssignedCourseRepository
					.findFirstByIdOfflineVideoCourseAndIdStudentSubscription(idOfflineVideoCourse,
							ss.getIdStudentSubscription());

			if (sac == null) {

				sac = new StudentAssignedCourse();

				Subject subject = subjectRepository.findByIdSubject(offlineVideoCourse.getIdSubject());
				if (subject == null)
					throw new NullPointerException("Invalid subject");

				SubjectChapter subjectChapter = subjectChapterRepository
						.findByIdSubjectChapter(offlineVideoCourse.getIdSubjectChapter());

				if (subjectChapter == null)
					throw new NullPointerException("Invalid subject chapter");

				sac.setIdStudentSubscription(ss.getIdStudentSubscription());
				sac.setLastAccessedDate(null);
				sac.setCompleteFlag(false);
				sac.setVideoCoverageDuration(0);
				sac.setPctComplete("0");
				sac.setSubjectName(subject.getSubjectName());
				sac.setChapterName(subjectChapter.getChapterName());
				BeanUtils.copyProperties(offlineVideoCourse, sac);
				sac.setVideoOtp(createCourseVideoOtp(sac.getVideoEnLink()));
				sac = studentAssignedCourseRepository.save(sac);

			} else {

				BeanUtils.copyProperties(offlineVideoCourse, sac);
				sac.setVideoOtp(createCourseVideoOtp(sac.getVideoEnLink()));
			}

			result.setData(sac);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull Request");

		}

		catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document checkExistingSubOrCreate(Long idUser, Long idProduct) {
		Document result = new Document();
		try {

			Student stu = studentRepository.getStudentByUser_UserSurId(idUser);
			Product pro = productRepository.findByIdProductAndActiveFlag(idProduct, Boolean.TRUE);

			if (stu == null)
				throw new NullPointerException("No Student Found");

			StudentSubscription StudSub = studentSubscriptionRepository
					.findFirstByIdStudentAndIdProductAndActiveFlag(stu.getIdStudent(), idProduct, true);

			if (StudSub == null) {
				StudentSubscription sub = new StudentSubscription();
				sub.setActiveFlag(true);
				sub.setFreeFlag(true);
				sub.setIdProduct(idProduct);
				sub.setIdProductGroup(pro.getIdProductGroup());
				sub.setIdproductLine(pro.getIdProductLine());
				sub.setIdStudent(stu.getIdStudent());
				sub.setUserSurId(stu.getUser().getUserSurId());
				StudentSubscription res = studentSubscriptionRepository.save(sub);

				result.setData(res);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull");

			} else {
				result.setData(StudSub);
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
	public Document<Page<OrderDTO>> getOrderDetails(OrderFilterDTO orderFilterDTO, String sort, int pageNumber,
			int pageSize) {

		Document<Page<OrderDTO>> result = new Document<>();

		sort = sort.equalsIgnoreCase("latest") ? "latest" : "old";

		Page<OrderDTO> page = null;

		Page<StudentOrder> studentOrderList = null;

		List<String> statusList = orderFilterDTO.getStatusList() == null ? null
				: ((orderFilterDTO.getStatusList().contains("all") || orderFilterDTO.getStatusList().isEmpty()) ? null
						: orderFilterDTO.getStatusList());

		Pageable paging = PageRequest.of(pageNumber, pageSize);

		try {

			if (orderFilterDTO.getEmail() == null && orderFilterDTO.getMobile() == null && statusList == null
					&& orderFilterDTO.getOrderId() == null && orderFilterDTO.getToDate() == null
					&& orderFilterDTO.getFromDate() == null) {
				if (sort.equalsIgnoreCase("latest"))
					studentOrderList = studentOrderRepository.findAllByOrderByIdStudentOrderDesc(paging);
				else
					studentOrderList = studentOrderRepository.findAllByOrderByIdStudentOrderAsc(paging);

			} else {
				if (sort.equalsIgnoreCase("latest"))

					studentOrderList = studentOrderRepository.getAllStudentOrderBasedOnParamDesc(
							orderFilterDTO.getOrderId(), orderFilterDTO.getMobile(), orderFilterDTO.getEmail(),
							statusList, orderFilterDTO.getFromDate(), orderFilterDTO.getToDate(), paging);
				else

					studentOrderList = studentOrderRepository.getAllStudentOrderBasedOnParamAsc(
							orderFilterDTO.getOrderId(), orderFilterDTO.getMobile(), orderFilterDTO.getEmail(),
							statusList, orderFilterDTO.getFromDate(), orderFilterDTO.getToDate(), paging);

			}

			List<OrderDTO> orderList = new ArrayList<OrderDTO>();

			if (pageNumber > 0 && studentOrderList.getContent().isEmpty())
				throw new AppException("Sorry, no more order details available.");

			if (studentOrderList.getContent().isEmpty())
				throw new AppException("No order details found.");

			for (StudentOrder order : studentOrderList) {

				User user = userRepository.findByUserSurId(order.getUserSurId());

				if (user == null)
					throw new NullPointerException("User details not found");

				OrderDTO dto = new OrderDTO();
				dto.setIdStudentOrder(order.getIdStudentOrder());
				dto.setOrderId(order.getOrderId());
				dto.setAmount(order.getTotalAmount());
				dto.setOrderDate(order.getOrderDate());
				dto.setOrderStatus(order.getOrderStatus());
				dto.setUserName(user.getFirstName());
				dto.setDisputeFlag(order.getDisputeFlag());
				dto.setSecondaryStatus(order.getSecondaryStatus());

				orderList.add(dto);

			}

			page = new PageImpl<>(orderList, paging, studentOrderList.getTotalElements());

			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully fetched student order details.");

			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<ProductDTO> getProductDetails(Long idStudentStagingSubscription) {
		Document<ProductDTO> result = new Document<>();
		try {
			StagingStudentSubscription subscription = stagingStudentSubscriptionRepository
					.findByIdStagingStudentSubscription(idStudentStagingSubscription);

			if (subscription == null)
				throw new NullPointerException("No subscription details found");

			Product product = productRepository.findByIdProductAndActiveFlag(subscription.getIdProduct(), Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("Invalid Product details found");

			ClassStandard classStandard = classRepository.findByIdClassStandard(product.getIdClassStandard());

			if (classStandard == null)
				throw new NullPointerException("Invalid classStandard details found");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("Invalid Syllabus details found");

			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());

			if (subject == null)
				throw new NullPointerException("Invalid Subject details found");

			State state = stateRepository.findByIdState(product.getIdState());

			if (state == null)

				throw new NullPointerException("Invalid state details found");

			ProductGroup productGroup = productGroupRepository.findByIdProductGroup(subscription.getIdProductGroup());

			if (productGroup == null)
				throw new NullPointerException("Invalid ProductGroup details found");

			ProductDTO productDto = new ProductDTO();

			if (subscription.getIdBatch() != null) {
				Batch batch = batchRepository.findByIdBatch(subscription.getIdBatch());
				if (batch == null)
					throw new NullPointerException("No batch details found for this subscription");

				productDto.setBatch(batch);

				if (batch.getIdBatchGroup() != null) {
					BatchGroup bg = batchGroupRepository.findByIdBatchGroup(batch.getIdBatchGroup());

					if (bg == null)
						throw new NullPointerException("Invalid BatchGroup");

					productDto.setBatchGroup(bg);

				}

				String batchName = "(" + batch.getBatchName() + ")";

				if (subscription.getIdProduct() != null) {

					if (product != null) {
						productDto.setProductName(product.getProductName() + batchName);
					}
				} else {

					if (productGroup != null) {
						productDto.setProductName(productGroup.getProductGroupName() + batchName);
					}
				}
			} else {
				// create new instant batch inorder to keep ui stable

				productDto.setBatch(new Batch());
			}

			productDto.setClassStandard(classStandard);
			productDto.setState(state);
			productDto.setSubject(subject);
			productDto.setSyllabus(syllabus);
			productDto.setProduct(product);
			productDto.setSubscription(subscription);
			productDto.setSubscriptionType(subscription.getSubscriptionType());
			productDto.setPurchaseType(subscription.getPurchaseType());
			productDto.setNextPaymentDate(subscription.getNextPaymentDate());
			productDto.setSubscriptionActive(subscription.getActiveFlag());

			result.setData(productDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully get product details");

			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Async
	@Override
	public void paymentReminder() {

		List<String> result = new ArrayList<>();
		Instant startTime, endTime;

		startTime = Instant.now();
		// total number of subscription notification sent
		int tnos = 0;

		try {

			result.add("Started Student Next Payment Date Check.");
			result.add("Started time: " + startTime);

			System.out.println("Started Student Next Payment Date Check.");
			List<PaymentRemainderDTO> userList = new ArrayList<PaymentRemainderDTO>();
			List<Object[]> studentSubscriptionList = studentSubscriptionRepository.findSubscriptionByNextPaymentDate();

			if (studentSubscriptionList.isEmpty()) {
				System.out.println("No Active Student Subscriptions found");
				result.add("No Active Student Subscriptions found");

			} else {

				for (Object ss[] : studentSubscriptionList) {

					BatchSubscriptionInfoDTO bsi = new BatchSubscriptionInfoDTO();

					bsi.setBatchName((String) ss[0]);
					bsi.setIdStudentSubscription((BigInteger) ss[1]);
					bsi.setIdVluser((BigInteger) ss[2]);
					bsi.setNextPaymentDate((Timestamp) ss[3]);
					bsi.setSubscriptionEndDate((Timestamp) ss[4]);
					bsi.setEmail((String) ss[5]);
					bsi.setMobileNumber((String) ss[6]);
					bsi.setFirstName((String) ss[7]);
					bsi.setDeviceId((String) ss[8]);

					LocalDate justDate = bsi.getNextPaymentDate().toLocalDateTime().toLocalDate();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

					String nxtPaymentDate = formatter.format(justDate).toString();

					PaymentRemainderDTO remainderDto = new PaymentRemainderDTO();
					remainderDto.setUserName(bsi.getFirstName());
					remainderDto.setNextPaymentDate(nxtPaymentDate);
					remainderDto.setBatchName(bsi.getBatchName());
					remainderDto.setIdVlUser(bsi.getIdVluser().longValue());

					if (bsi.getDeviceId() != null) {

						remainderDto.setDeviceId(bsi.getDeviceId());

					}

					userList.add(remainderDto);

					String message = "Dear " + bsi.getFirstName() + " your payment is due for " + bsi.getBatchName()
							+ ", Next Payment date is " + nxtPaymentDate;
					userNotificationService.createNotifcation("Renewal", message, bsi.getIdVluser().longValue());
					tnos++;

				}
				if (!userList.isEmpty()) {
					userList.stream().forEach(user -> {
						String message = "Dear " + user.getUserName() + " your payment is due for "
								+ user.getBatchName() + ", Next Payment date is " + user.getNextPaymentDate();
						List<String> regDeviceIdList = Arrays.asList(user.getDeviceId());
						notificationService.sendNotification(regDeviceIdList, message, "renewal");
					});
				}

				result.add("Total No.of active subscriptions founded: " + studentSubscriptionList.size() + " .");
				result.add("Total No.of Inapp Notification sent: " + tnos + " .");
				result.add("Total No.of Push Notification sent: " + userList.size() + " .");

			}
		} catch (Exception exp) {
			System.out.println("Payment remainde failer message" + exp.getLocalizedMessage());
			result.add("Error occured:");
			result.add(exp.getCause().getLocalizedMessage());
		}

		endTime = Instant.now();

		result.add("End Time:" + endTime);
		result.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		result.add("Payment remainder check Completed!!!");
		String fileName = "StudentPaymentRemainderNotifcationLog_" + Instant.now() + ".txt";
		// log the file to s3 location
		try (FileWriter writer = new FileWriter(fileName);){

			
			for (String str : result) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();

			File file = new File(fileName);

			fileUploadService.uploadFileToS3Bucket("/logs/scheduler/student-payment-remainder", fileName, file);

			boolean isDeletedFile = file.delete();
			LOGGER.info("Log file deleted from the system : " +isDeletedFile );

		} catch (IOException e) {
			
			LOGGER.error(e.getMessage());
		}

	}

	@Override
	public Document<Map<String, String>> getSubjectCompletionPercentage(Long idStudentSubscription, Long idSubject) {

		Document<Map<String, String>> result = new Document<>();

		try {

			List<String> marketingIds = new ArrayList<String>();
			marketingIds.add("223a45dbb2b94bbabeae667e84c70f3a");
			marketingIds.add("8371301d9367416ca2536302b55ab59a");
			marketingIds.add("4eb7d1b2516640e8970a79d211312482");
			marketingIds.add("776458c7fb5a413ca201f5a43afa36bd");
			marketingIds.add("26bef27760334086aa02be05556a21f0");
			marketingIds.add("7675a846579949b5b8d685742359a785");
			marketingIds.add("4d0ed11baa5142e1a3e9ca33309d9e28");
			marketingIds.add("c9333255a29b4ba7867d0a1f198c819a");
			marketingIds.add("91ff96921ba140939ae93dfe5c7b64c8");
			marketingIds.add("fba4b9a786bb449f9d1e9ce7d9cc8ebe");
			marketingIds.add("55040db5350040e79ee23a108d2a4f72");

			Map<String, String> temp = new HashMap<>();

			StudentSubscription ss = studentSubscriptionRepository.findByIdStudentSubscription(idStudentSubscription);

			if (ss == null)
				throw new AppException("invalid student subscription");

			List<OfflineVideoCourse> offlist = offlineVideoCourseRepository
					.findByIdProductAndVideoEnLinkNotIn(ss.getIdProduct(), marketingIds);

			if (offlist.isEmpty())
				throw new AppException("No Offline Video record found, for this product");
			
			int count = (int) offlist.stream()
					.filter(o -> !o.getVideoEnLink().equalsIgnoreCase("223a45dbb2b94bbabeae667e84c70f3a")).count();

			List<StudentAssignedCourse> listVideos = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotIn(idStudentSubscription, idSubject,
							marketingIds);

			// calculate over all percentage of user watched video

			double overAllPercentage = listVideos.isEmpty() ? 0
					: listVideos.stream().mapToDouble(s -> Double.valueOf(s.getPctComplete())).sum();

			if (overAllPercentage <= 0) {
				temp.put("percentage", "0");
				result.setData(temp);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully Fetched Completion Percentage");
			} else {

				double calculate = (overAllPercentage / (count * 100));
				temp.put("percentage", Double.toString(Math.round(calculate * 100)));
				result.setData(temp);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully Fetched Completion Percentage");

			}

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveNewUserSubscription(UserCartResponseDTO userCart) {
		Document document = new Document();

		try {

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
			cartSummaryDTO.setPurchaseType("NEW");

			// adds user carts list
			List<UserCartResponseDTO> listUserCarts = new ArrayList<UserCartResponseDTO>();

			// get cart total amount
			Float cartTotalPrice = 0F;
			Float cartNetPrice = 0F;
			Float cartGSTPrice = 0F;
			if (userCart != null) {

				if (userCart.getUserSurId() != null) {

					User loggedInUser = null;
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

					if (!(authentication instanceof AnonymousAuthenticationToken)) {
						UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
						loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
					}

					if (loggedInUser == null)
						throw new AppException("Invalid User");

					if (!userCart.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("There is conflict found in user details.");
						document.setStatusCode(HttpStatus.NOT_FOUND.value());
						return document;

					}

					StudentSubscription studentSubscription = studentSubscriptionRepository
							.findByUserSurIdAndIdproductLineAndActiveFlag(userCart.getUserSurId(), 11L, Boolean.TRUE);
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
				}

				Product product = productRepository.findByIdProductAndActiveFlag(userCart.getIdProduct(), Boolean.TRUE);
				if (product != null) {
					Long itemNetPrice = (long) (userCart.getSubscriptionType().equals("MONTH")
							? product.getMonthlySubcrAmt()
							: userCart.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
									: userCart.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
											: 0L);
					cartNetPrice += itemNetPrice;
					userCart.setPurchaseAmount(Float.parseFloat(itemNetPrice.toString()));
				} else {
					document.setData(null);
					document.setMessage("Product data not found");
					document.setStatusCode(HttpStatus.NOT_FOUND.value());
					return document;
				}
				if (userCart.getSubscriptionType().equals("FREE")) {
					LocalDateTime dateMonth = Instant.now().atZone(zoneIndia).toLocalDateTime().plusMonths(1);
					LocalDateTime gracePeriodDate = Instant.now().atZone(zoneIndia).toLocalDateTime().plusMonths(1);

					StudentSubscription studentSubscription = new StudentSubscription();
					studentSubscription.setIdProduct(userCart.getIdProduct());
					studentSubscription.setIdBatch(userCart.getIdBatch());
					studentSubscription.setIdProductGroup(userCart.getIdProductGroup());
					studentSubscription.setIdproductLine(product.getIdProductLine());
					studentSubscription.setIdStudent(userCart.getIdStudent());
					studentSubscription.setIdStudentOrder(null);
					studentSubscription.setLastPaymentDate(Instant.now());
					studentSubscription.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
					studentSubscription.setPurchaseAmount(userCart.getPurchaseAmount().toString());
					studentSubscription.setPurchaseDate(Instant.now());
					studentSubscription.setPurchaseLevel(userCart.getPurchaseLevel());
					studentSubscription
							.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
					studentSubscription.setSubscriptionType(userCart.getSubscriptionType());
					studentSubscription.setPurchaseType(userCart.getPurchaseType());
					studentSubscription.setActiveFlag(Boolean.TRUE);
					studentSubscription.setFreeFlag(Boolean.TRUE);
					studentSubscription.setUserSurId(userCart.getUserSurId());
					studentSubscription = studentSubscriptionRepository.save(studentSubscription);
					document.setData(studentSubscription);
					document.setMessage("Free subscription created with one month free trial");
					document.setStatusCode(HttpStatus.OK.value());
					return document;
				}

				listUserCarts.add(userCart);
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
				studentOrder.setUserSurId(userCart.getUserSurId());
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
					stagingStudentSubscription.setIdBatch(userCart.getIdBatch());
					stagingStudentSubscription.setIdProduct(userCart.getIdProduct());
					stagingStudentSubscription.setIdProductGroup(userCart.getIdProductGroup());
					stagingStudentSubscription.setIdproductLine(product.getIdProductLine());
					stagingStudentSubscription.setIdStudent(userCart.getIdStudent());
					stagingStudentSubscription.setPurchaseAmount(userCart.getPurchaseAmount().toString());
					stagingStudentSubscription.setPurchaseLevel(userCart.getPurchaseLevel());
					stagingStudentSubscription.setPurchaseType(userCart.getPurchaseType());
					stagingStudentSubscription.setSubscriptionType(userCart.getSubscriptionType());
					stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
					stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
					stagingStudentSubscriptions.add(stagingStudentSubscription);
					stagingStudentSubscription.setPaymentStatus("Pending");
					stagingStudentSubscription.setUserSurId(userCart.getUserSurId());
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
			} else {
				document.setData(null);
				document.setMessage("Please send user cart data for proceeding to checkout");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveRenewalUserSubscription(Long idStudentSubscription, String interval, Long userSurId) {
		Document document = new Document();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!userSurId.equals(loggedInUser.getUserSurId())) {
				document.setData(null);
				document.setMessage("There is conflict found in user details.");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;

			}

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
			cartSummaryDTO.setPurchaseType("RENEWAL");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByIdStudentSubscriptionAndUserSurId(idStudentSubscription, userSurId);

			if (studentSubscription == null) {
				document.setData(null);
				document.setMessage("Subscription details not found.");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}

			Long itemNetPrice = 0L;
			Product productPrice = productRepository.findByIdProductAndActiveFlag(studentSubscription.getIdProduct(),
					Boolean.TRUE);
			if (productPrice != null) {
				itemNetPrice = (long) (interval.equals("MONTH") ? productPrice.getMonthlySubcrAmt()
						: interval.equals("QUARTER") ? productPrice.getQtrSubscrAmt()
								: interval.equals("ANNUAL") ? productPrice.getAnnualSubscrAmt() : 0L);
			} else {
				document.setData(null);
				document.setMessage("Product data not found");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}

			LocalDate dateExist = studentSubscription.getSubscriptionEndDate().atZone(zoneIndia).toLocalDate().minusDays(6);
			LocalDate dateNextPaymentDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
			LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
			if (dateExist.isBefore(dateToday) && !dateExist.equals(dateToday)) {
				// get cart total amount
				Float cartTotalPrice = 0F;
				Float cartNetPrice = 0F;
				Float cartGSTPrice = 0F;
				cartNetPrice = Float.parseFloat(itemNetPrice.toString());

				// GST percentage calculated 0% for now

				cartGSTPrice = 0F;

				/* cartGSTPrice = (float) (cartNetPrice * (0.18)); */

				// cart total from cart purchase amount
				cartTotalPrice = cartNetPrice + cartGSTPrice;

				String twoDecimalPrice = String.format("%.2f", cartTotalPrice);

				cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
				cartSummaryDTO.setCartNetPrice(cartNetPrice);
				cartSummaryDTO.setCartTotalPrice(Float.parseFloat(twoDecimalPrice));
				// create order data
				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(userSurId);
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
					// create staging data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					// add product data
					List<UserCartResponseDTO> userCarts = new ArrayList<>();
					StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
					stagingStudentSubscription.setIdBatch(studentSubscription.getIdBatch());
					stagingStudentSubscription.setIdProduct(studentSubscription.getIdProduct());
					stagingStudentSubscription.setIdProductGroup(studentSubscription.getIdProductGroup());
					stagingStudentSubscription.setIdproductLine(studentSubscription.getIdproductLine());
					stagingStudentSubscription.setIdStudent(studentSubscription.getIdStudent());
					stagingStudentSubscription.setPurchaseAmount(cartNetPrice.toString());
					stagingStudentSubscription.setPurchaseLevel(studentSubscription.getPurchaseLevel());
					stagingStudentSubscription.setPurchaseType("RENEWAL");
					stagingStudentSubscription.setSubscriptionType(interval);
					stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
					stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
					stagingStudentSubscription.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
					stagingStudentSubscription.setPaymentStatus("Pending");
					// date checks, last payment date is next payment date if before or same day,
					// but if today date is after next payment date then considering today date is
					// last payment date
					if (dateToday.isAfter(dateNextPaymentDate)) {
						stagingStudentSubscription.setLastPaymentDate(Instant.now());
					} else {
						stagingStudentSubscription.setLastPaymentDate(studentSubscription.getNextPaymentDate());
					}
					stagingStudentSubscription.setUserSurId(studentSubscription.getUserSurId());
					stagingStudentSubscriptions.add(stagingStudentSubscription);
					stagingStudentSubscriptions = stagingStudentSubscriptionRepository
							.saveAll(stagingStudentSubscriptions);
					cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);

					// cart data adding
					UserCartResponseDTO userCart = new UserCartResponseDTO();
					userCart.setPurchaseAmount(Float.parseFloat(stagingStudentSubscription.getPurchaseAmount()));
					userCart.setPurchaseType(stagingStudentSubscription.getPurchaseType());
					userCart.setUserSurId(userSurId);
					userCart.setSubscriptionType(interval);
					if (stagingStudentSubscription.getIdProduct() != null) {
						Product product = productRepository
								.findByIdProductAndActiveFlag(stagingStudentSubscription.getIdProduct(), Boolean.TRUE);
						if (product != null && product.getIdProductLine() == 11) {
							userCart.setIdProduct(product.getIdProduct());
							userCart.setProductName(product.getProductName());
							userCart.setProductCategory("AD_FREE_SUBSCRIPTION");
							userCart.setPurchaseLevel("AD_FREE_SUBSCRIPTION");
						}
					}
					userCarts.add(userCart);
					cartSummaryDTO.setUserCarts(userCarts);

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
							cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
							document.setData(cartSummaryDTO);
							document.setStatusCode(HttpStatus.OK.value());
							document.setMessage("Renewal summary data fetched successfully");
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
							document.setData(null);
							document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
							document.setMessage("Something went wrong while fetching subscription data");
						}
					}
				}
			} else {
				document.setData(null);
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				document.setMessage("Your subscription is not expired yet!");
				return document;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setData(null);
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			document.setMessage("Something went wrong while fetching subscription data");
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document checkFreeAndExistingSubscription(Long userSurId) {
		Document document = new Document();
		Map<String, Object> subscriptionDetails = new HashMap<>();

		/*
		 * Updated by @author Naveen Kumar Fetching user data based on session
		 * information instead of parameter which sent through request.
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

		StudentSubscription studentSubscriptionActive = studentSubscriptionRepository
				.findByUserSurIdAndIdproductLineAndActiveFlag(user.getUserSurId(), 11L, Boolean.TRUE);
		UserCreatedDTO ucDto = userService.getUserCreatedWithinTwoDays(user.getUserSurId());
		if (studentSubscriptionActive == null) {
			// free subscription completed flag
			subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
			subscriptionDetails.put("existingSubscription", false);
			subscriptionDetails.put("expiredSubscription", false);
			subscriptionDetails.put("showRenewal", false);
			subscriptionDetails.put("subscriptionType", null);
			subscriptionDetails.put("idStudentSubscription", null);
			subscriptionDetails.put("nextPaymentDate", null);
			document.setData(subscriptionDetails);
			document.setMessage("Subscription details not found");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		}
		try {
			// two days before show renewal button
			LocalDate dateExist = studentSubscriptionActive.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
			LocalDate dateNextPaymentDate = studentSubscriptionActive.getNextPaymentDate().atZone(zoneIndia)
					.toLocalDate();
			LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
			if (dateExist.isBefore(dateToday) || dateExist.equals(dateToday)) {
				// free subscription completed flag
				subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
				subscriptionDetails.put("existingSubscription", true);
				subscriptionDetails.put("expiredSubscription", dateToday.isAfter(dateNextPaymentDate) ? true : false);
				subscriptionDetails.put("showRenewal", true);
				subscriptionDetails.put("subscriptionType", studentSubscriptionActive.getSubscriptionType());
				subscriptionDetails.put("idStudentSubscription", studentSubscriptionActive.getIdStudentSubscription());
				subscriptionDetails.put("nextPaymentDate", studentSubscriptionActive.getNextPaymentDate());
				document.setData(subscriptionDetails);
				document.setStatusCode(HttpStatus.OK.value());
				document.setMessage("Your subscription is expired!");
			} else {
				// free subscription completed flag
				subscriptionDetails.put("freeSubscription", ucDto != null ? true : false);
				subscriptionDetails.put("existingSubscription", true);
				subscriptionDetails.put("expiredSubscription", false);
				subscriptionDetails.put("showRenewal", false);
				subscriptionDetails.put("subscriptionType", studentSubscriptionActive.getSubscriptionType());
				subscriptionDetails.put("idStudentSubscription", studentSubscriptionActive.getIdStudentSubscription());
				subscriptionDetails.put("nextPaymentDate", studentSubscriptionActive.getNextPaymentDate());
				document.setData(subscriptionDetails);
				document.setStatusCode(HttpStatus.OK.value());
				document.setMessage("Your subscription is not expired yet!");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setData(null);
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			document.setMessage("Something went wrong while fetching subscription data");
		}
		return document;
	}

	@Override
	public StudentPostLoginDTO checkExistingSubscriptionLogin(Long userSurId) {

		StudentPostLoginDTO result = new StudentPostLoginDTO();

		/*
		 * Updated by @author Naveen Kumar Fetching user data based on session
		 * information instead of parameter which sent through request.
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

		UserCreatedDTO ucDTO = userService.getUserCreatedWithinTwoDays(user.getUserSurId());

		boolean trialFlag = (ucDTO != null);

		SubscribedUserDTO su = this.checkUserSubscribed(userSurId);

		boolean subscribedFlag = (su != null);

		result.setSubscribedFlag(subscribedFlag);
		result.setTrialFlag(trialFlag);

		return result;

		/*
		 * StudentSubscription studentSubscriptionActive =
		 * studentSubscriptionRepository.findByUserSurIdAndIdproductLineAndActiveFlag(
		 * userSurId, 11L, Boolean.TRUE); StudentSubscription
		 * studentSubscriptionInActive =
		 * studentSubscriptionRepository.findByUserSurIdAndIdproductLineAndActiveFlag(
		 * userSurId, 11L, Boolean.FALSE); if (studentSubscriptionActive==null &&
		 * studentSubscriptionInActive==null) { return false; } try { LocalDate
		 * dateExist =
		 * studentSubscriptionActive.getNextPaymentDate().atZone(zoneIndia).toLocalDate(
		 * ); LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate(); if
		 * (dateExist.isAfter(dateToday) || dateExist.equals(dateToday)) { return true;
		 * } else { return false; } } catch (Exception e) { LOGGER.error(e.getMessage()); return
		 * false; }
		 */
	}

	@Override
	public Document<HashMap<String, Object>> getTokenInfo(PaytmParamsRequestDTO request) {

		Document<HashMap<String, Object>> result = new Document<>();
		TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
		};
		HashMap<String, Object> mapping = null;
		try {
			JSONObject paytmParams = new JSONObject();

			JSONObject body = new JSONObject();

			body.put("requestType", request.getRequestType());
			body.put("mid", request.getMid());
			body.put("websiteName", request.getWebsiteName());
			body.put("orderId", request.getOrderId());
			body.put("callbackUrl", request.getCallbackUrl());

			JSONObject txnAmount = new JSONObject();
			txnAmount.put("value", request.getValue());
			txnAmount.put("currency", request.getCurrency());

			JSONObject userInfo = new JSONObject();
			userInfo.put("custId", request.getCustId());
			body.put("txnAmount", txnAmount);
			body.put("userInfo", userInfo);

			/*
			 * Generate checksum by parameters we have in body You can get Checksum JAR from
			 * https://developer.paytm.com/docs/checksum/ Find your Merchant Key in your
			 * Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
			 */

			Merchant merchant = merchantRepository.findByMerchantId(merchantId);
			if (merchant == null)
				throw new AppException("No Merchant info found.");

			String checksum = PaytmChecksum.generateSignature(body.toString(), merchant.getMerchantKey());

			JSONObject head = new JSONObject();
			head.put("signature", checksum);

			paytmParams.put("body", body);
			paytmParams.put("head", head);

			String post_data = paytmParams.toString();

			String finalUrl = this.paymentIntiateTransactionUrl + "mid=" + request.getMid() + "&orderId="
					+ request.getOrderId();

			/* for Staging */
			URL url = new URL(finalUrl);

			/* for Production */
			// URL url = new
			// URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			requestWriter.writeBytes(post_data);
			requestWriter.close();
			String responseData = "";
			InputStream is = connection.getInputStream();
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			if ((responseData = responseReader.readLine()) != null) {
				mapping = new ObjectMapper().readValue(responseData, typeRef);
				System.out.append("Response: " + responseData);
			}
			responseReader.close();
			result.setData(mapping);
			result.setMessage("Request sucessfull!");
			result.setStatusCode(200);

		} catch (Exception exception) {
			result.setData(null);
			result.setMessage(exception.getLocalizedMessage());
			result.setStatusCode(500);
			LOGGER.error(exception.getMessage());
		}

		return result;
	}

	/**
	 * @author NAVEEN
	 * @param idSubjectChapter
	 * @param idClassStandard
	 * @param idSubject
	 * @param idState
	 * @param idSyllabus
	 * @return Boolean
	 * @throws NullPointerException
	 * 
	 *                              This method returns Boolean true as result if
	 *                              the provided idSubjectchapter is applicable for
	 *                              free. , else return false. throws Null pointer
	 *                              exception when their is no data found for the
	 *                              request parameters.
	 */
	public Boolean checkChapterAvailablityForFreeStreaming(Long idSubjectChapter, Long idClassStandard, Long idSubject,
			Long idState, Long idSyllabus) throws NullPointerException

	{
		Boolean result = false;

		// limiting the query to 2 results
		List<SubjectChapter> pages = subjectChapterRepository
				.findTop2ByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrderAsc(idClassStandard,
						idSubject, idState, idSyllabus,Boolean.TRUE);

		if (pages.isEmpty())
			throw new NullPointerException("No Subject Chapters data found");

		result = pages.stream().filter(s -> s.getIdSubjectChapter().equals(idSubjectChapter)).findFirst().isPresent();

		return result;
	}

	@Override
	public SubscribedUserDTO checkUserSubscribed(Long userSurId) {

		SubscribedUserDTO subscribedUserDTO = new SubscribedUserDTO();

		/*
		 * Updated by @author Naveen Kumar Fetching user data based on session
		 * information instead of parameter which sent through request.
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

		// trying to find a subscription on a product-line basis.
		StudentSubscription studentSubscription = studentSubscriptionRepository
				.findFirstByUserSurIdAndIdproductLineAndActiveFlag(user.getUserSurId(), 11L, Boolean.TRUE);

		if (studentSubscription == null)
			return subscribedUserDTO = null;

		LocalDate dateNextPaymentDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateEndDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();

		if ((dateNextPaymentDate.isAfter(dateToday) || dateNextPaymentDate.equals(dateToday))
				&& dateEndDate.isAfter(dateToday) || dateEndDate.equals(dateToday)) {
			subscribedUserDTO.setIdProduct(studentSubscription.getIdProduct());
			subscribedUserDTO.setIdStudent(studentSubscription.getIdStudent());
			subscribedUserDTO.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
			subscribedUserDTO.setUserSurId(studentSubscription.getUserSurId());
		} else {
			subscribedUserDTO = null;
		}

		return subscribedUserDTO;
	}

	@Override
	public Document<StudentAcademicGraphDTO> getAcademicProgressSubjectGraphinfo(Long idStudentSubscription,
			Long idSubject) {

		Document<StudentAcademicGraphDTO> result = new Document<>();

		try {

			StudentSubscription ss = studentSubscriptionRepository.findByIdStudentSubscription(idStudentSubscription);

			if (ss == null)
				throw new AppException("invalid student subscription");

			List<OfflineVideoCourse> offlist = offlineVideoCourseRepository
					.findByIdProductAndVideoEnLinkNotIn(ss.getIdProduct(), marketingIds);

			if (offlist.isEmpty())
				throw new AppException("No Offline Video record found, for this product");

			Double offLineVideocount = Double.valueOf(offlist.size());

			List<StudentAssignedCourse> completedVideoList = studentAssignedCourseRepository
					.findByIdStudentSubscriptionAndIdSubjectAndVideoEnLinkNotInAndCompleteFlag(idStudentSubscription,
							idSubject, marketingIds, Boolean.TRUE);

			int completedVideoCount = completedVideoList.isEmpty() ? 0 : completedVideoList.size();

			// calculate over all percentage of user watched video

			StudentAcademicGraphDTO sagDTO = new StudentAcademicGraphDTO();

			sagDTO.setIdStudentSubscription(idStudentSubscription);
			sagDTO.setIdSubject(idSubject);

			if (completedVideoCount <= 0) {
				// check domain is production for fetching image from different bucket
				String imageUrl = appAngularUrl.equals("https://vistaslearning.com")
						? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_pct.jpg"
						: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_pct.jpg";
				sagDTO.setPercentage("0.0");
				sagDTO.setImageUrl(imageUrl);
				result.setData(sagDTO);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully Fetched Completion Percentage");

			}

			else {

				Double calculate = (completedVideoCount / offLineVideocount);
				Double roundValue = Double.valueOf(Math.round(calculate * 100));
				sagDTO.setPercentage(roundValue.toString());

				String imageUrl = null;

				if (roundValue <= 0d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_pct.jpg";

				} else if (roundValue > 0d && roundValue <= 10d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_10_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_0_10_pct.jpg";
				} else if (roundValue > 10d && roundValue <= 20d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_10_20_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_10_20_pct.jpg";
				} else if (roundValue > 20d && roundValue <= 30d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_20_30_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_20_30_pct.jpg";
				} else if (roundValue > 30d && roundValue <= 40d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_30_40_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_30_40_pct.jpg";
				} else if (roundValue > 40d && roundValue <= 60d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_40_60_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_40_60_pct.jpg";
				} else if (roundValue > 60d && roundValue <= 70d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_60_70_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_60_70_pct.jpg";
				} else if (roundValue > 70d && roundValue <= 99d) {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_70_90_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_70_90_pct.jpg";
				} else {
					imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_100_pct.jpg"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/progress-graph-images/graph_100_pct.jpg";
				}

				sagDTO.setImageUrl(imageUrl);
				result.setData(sagDTO);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully Fetched Completion Percentage");

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveRenewalSpecialOfferBatchSubscription(Long idStudentOrder, Long idSpecialOffer, Long userSurId) {
		Document document = new Document();

		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!userSurId.equals(loggedInUser.getUserSurId())) {
				document.setData(null);
				document.setMessage("User dosent have access to add this item to cart.");
				document.setStatusCode(HttpStatus.FORBIDDEN.value());
				return document;

			}

			CartSummaryDTO cartSummaryDTO = new CartSummaryDTO();
			cartSummaryDTO.setPurchaseType("RENEWAL_GROUP");
			List<StudentSubscription> studentSubscriptionList = studentSubscriptionRepository
					.findByIdStudentOrderAndIdSpecialOfferAndUserSurIdAndActiveFlag(idStudentOrder, 1L, userSurId,
							Boolean.TRUE);
			if (studentSubscriptionList.isEmpty()) {
				document.setData(null);
				document.setMessage("Subscription details not found");
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}

			LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();
			// get cart total amount
			Float cartTotalPrice = 0F;
			Float cartNetPrice = 0F;
			Float cartGSTPrice = 0F;
			Float cartTotalDiscount = 0F;
			// since it's special offer batch 6For200, hard coding amount
			cartTotalPrice = 2000F;
			// GST percentage calculated 0% for now
			cartGSTPrice = 0F;
			// cart total from cart purchase amount
			cartSummaryDTO.setCartGSTPrice(cartGSTPrice);
			for (StudentSubscription ss : studentSubscriptionList) {
				Product product = productRepository.findByIdProductAndActiveFlag(ss.getIdProduct(), Boolean.TRUE);
				if (product != null) {
					Long itemNetPrice = (long) (ss.getSubscriptionType().equals("MONTH") ? product.getMonthlySubcrAmt()
							: ss.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
									: ss.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt() : 0L);
					cartNetPrice += itemNetPrice;
				}
			}
			cartTotalDiscount = cartNetPrice - cartTotalPrice;
			cartSummaryDTO.setCartTotalDiscount(cartTotalDiscount);
			cartSummaryDTO.setCartNetPrice(cartNetPrice);
			cartSummaryDTO.setCartTotalPrice(cartTotalPrice);
			// create order data
			StudentOrder studentOrder = new StudentOrder();
			studentOrder.setUserSurId(userSurId);
			studentOrder.setOrderDate(Instant.now());
			studentOrder.setOrderStatus("Pending");
			studentOrder.setNetAmount(cartNetPrice);
			studentOrder.setGstAmount(cartGSTPrice);
			studentOrder.setTotalAmount(cartTotalPrice);
			// set order id
			studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(studentOrder.getUserSurId()));
			cartSummaryDTO.setStudentOrder(studentOrder);

			// create staging data
			List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
			// add product data
			List<UserCartResponseDTO> userCarts = new ArrayList<>();

			for (StudentSubscription ss : studentSubscriptionList) {
				LocalDate dateExist = ss.getSubscriptionEndDate().atZone(zoneIndia).toLocalDate().minusDays(6);
				if (dateExist.isBefore(dateToday) && !dateExist.equals(dateToday)) {
					if (!Objects.isNull(studentOrder)) {
						Product product = productRepository.findByIdProductAndActiveFlag(ss.getIdProduct(),
								Boolean.TRUE);
						if (product != null) {
							Long itemNetPrice = (long) (ss.getSubscriptionType().equals("MONTH")
									? product.getMonthlySubcrAmt()
									: ss.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
											: ss.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
													: 0L);
							StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();
							stagingStudentSubscription.setIdBatch(ss.getIdBatch());
							stagingStudentSubscription.setIdProduct(ss.getIdProduct());
							stagingStudentSubscription.setIdProductGroup(ss.getIdProductGroup());
							stagingStudentSubscription.setIdproductLine(ss.getIdproductLine());
							stagingStudentSubscription.setIdStudent(ss.getIdStudent());
							stagingStudentSubscription.setPurchaseAmount(itemNetPrice.toString());
							stagingStudentSubscription.setPurchaseLevel(ss.getPurchaseLevel());
							stagingStudentSubscription.setPurchaseType("RENEWAL");
							stagingStudentSubscription.setSubscriptionType(ss.getSubscriptionType());
							stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
							stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
							stagingStudentSubscription.setIdStudentSubscription(ss.getIdStudentSubscription());
							stagingStudentSubscription.setPaymentStatus("Pending");
							// here last payment date is next payment date
							stagingStudentSubscription.setLastPaymentDate(ss.getNextPaymentDate());
							stagingStudentSubscription.setSpecialOfferFlag(Boolean.TRUE);
							stagingStudentSubscription.setIdSpecialOffer(ss.getIdSpecialOffer());
							stagingStudentSubscription.setUserSurId(ss.getUserSurId());
							stagingStudentSubscription.setPurchaseDate(Instant.now());
							stagingStudentSubscriptions.add(stagingStudentSubscription);
							cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);

							// cart data adding
							UserCartResponseDTO userCart = new UserCartResponseDTO();
							userCart.setPurchaseAmount(Float.parseFloat(itemNetPrice.toString()));
							userCart.setPurchaseType(stagingStudentSubscription.getPurchaseType());
							userCart.setIdStudent(userSurId);
							userCart.setSubscriptionType(ss.getSubscriptionType());
							if (ss.getIdBatch() != null) {
								Batch batch = batchRepository.findByIdBatch(ss.getIdBatch());
								if (batch != null) {
									userCart.setIdBatch(batch.getIdBatch());
									userCart.setProductName(batch.getBatchName());
									userCart.setIdProduct(batch.getIdProduct());
									userCart.setProductCategory("BATCH");
									userCart.setPurchaseLevel("BATCH");
									userCart.setBatch(batch);
								}
							} else if (stagingStudentSubscription.getIdProduct() != null) {
								Product productGet = productRepository.findByIdProductAndActiveFlag(
										stagingStudentSubscription.getIdProduct(), Boolean.TRUE);
								if (productGet != null) {
									userCart.setIdProduct(productGet.getIdProduct());
									userCart.setProductName(productGet.getProductName());
									userCart.setProductCategory("PRODUCT");
									userCart.setPurchaseLevel("PRODUCT");
								}
							} else if (stagingStudentSubscription.getIdProductGroup() != null) {
								ProductGroup productGroup = productGroupRepository
										.findByIdProductGroup(stagingStudentSubscription.getIdProductGroup());
								if (productGroup != null) {
									userCart.setIdProductGroup(productGroup.getIdProductGroup());
									userCart.setProductName(productGroup.getProductGroupName());
								}
							}
							userCarts.add(userCart);
							cartSummaryDTO.setUserCarts(userCarts);
						}
					}
				} else {
					document.setData(null);
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					document.setMessage("Your subscription is not expired yet!");
					return document;
				}
			}
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
					cartSummaryDTO.setPaymentParameters(responseIntiatePayment);
					document.setData(cartSummaryDTO);
					document.setStatusCode(HttpStatus.OK.value());
					document.setMessage("Renewal summary data fetched successfully");
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document makeRenewalSpecialOfferBatchPayment(CartSummaryDTO cartSummaryDTO) {
		Document document = new Document();

		try {

			if (!cartSummaryDTO.getUserCarts().isEmpty()) {

				User loggedInUser = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (!(authentication instanceof AnonymousAuthenticationToken)) {
					UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
					loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
				}

				if (loggedInUser == null)
					throw new AppException("Invalid User");

				for (UserCartResponseDTO u : cartSummaryDTO.getUserCarts()) {

					if (!u.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				for (StagingStudentSubscription ss : cartSummaryDTO.getStagingStudentSubscriptions()) {

					if (!ss.getUserSurId().equals(loggedInUser.getUserSurId())) {
						document.setData(null);
						document.setMessage("User dosent have access to add this item to cart.");
						document.setStatusCode(HttpStatus.FORBIDDEN.value());
						return document;

					}
				}
				;

				if (!cartSummaryDTO.getStudentOrder().getUserSurId().equals(loggedInUser.getUserSurId())) {
					document.setData(null);
					document.setMessage("User dosent have access to add this item to cart.");
					document.setStatusCode(HttpStatus.FORBIDDEN.value());
					return document;

				}

				// save student order
				StudentOrder studentOrder = studentOrderRepository.save(cartSummaryDTO.getStudentOrder());

				if (studentOrder != null && cartSummaryDTO.getStagingStudentSubscriptions().size() != 0) {
					cartSummaryDTO.setStudentOrder(studentOrder);
					// save staging table data
					List<StagingStudentSubscription> stagingStudentSubscriptions = new ArrayList<>();
					for (StagingStudentSubscription stagingStudentSubscription : cartSummaryDTO
							.getStagingStudentSubscriptions()) {
						stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
						stagingStudentSubscription.setTransactionDate(Instant.now());
						stagingStudentSubscriptions.add(stagingStudentSubscription);
					}
					stagingStudentSubscriptions = stagingStudentSubscriptionRepository
							.saveAll(stagingStudentSubscriptions);
					if (stagingStudentSubscriptions.size() != 0) {
						cartSummaryDTO.setStagingStudentSubscriptions(stagingStudentSubscriptions);
					} else {
						document.setData(null);
						document.setMessage("Something went wrong while making payment");
						document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
						return document;
					}
					if (cartSummaryDTO.getUserCarts().size() != 0) {
						List<UserCart> list = new ArrayList<>();
						cartSummaryDTO.getUserCarts().forEach(element -> {
							UserCart userCart = new UserCart();
							if (element.getBatch() != null) {
								BeanUtils.copyProperties(element, userCart, "batch");
							}
							userCart.setIdStudentOrder(studentOrder.getIdStudentOrder());
							list.add(userCart);
						});
					}
				} else {
					document.setData(null);
					document.setMessage("Something went wrong while making payment");
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return document;
				}
				document.setData(cartSummaryDTO);
				document.setMessage("Payment In Progress");
				document.setStatusCode(HttpStatus.OK.value());
			}

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					document.setStatusCode(HttpStatus.CONFLICT.value());
					document.setMessage("Duplicate Order found!");
					return document;
				}

				else {
					document.setData(null);
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					document.setMessage(exp.getLocalizedMessage());
					return document;
				}

			}

			else {
				document.setData(null);
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				document.setMessage(exp.getLocalizedMessage());
				return document;
			}

		}

		return document;
	}

	@Override
	public Document<List<StudentSubscriptionInfoDTO>> getStudentSubscriptionsByOrder(String orderId) {
		Document<List<StudentSubscriptionInfoDTO>> result = new Document<>();

		try {
			List<StudentSubscriptionInfoDTO> subscriptionDTOList = stagingStudentSubscriptionRepository
					.getStudentSubscriptionForOrderId(orderId);

			if (subscriptionDTOList.isEmpty())
				throw new AppException("No Subscription found for this order id.");

			result.setData(subscriptionDTOList);
			result.setMessage("Request successfull!");
			result.setStatusCode(HttpStatus.OK.value());

		}

		catch (Exception exception) {
			result.setData(null);
			result.setMessage(exception.getLocalizedMessage());
			result.setStatusCode(500);
			LOGGER.error(exception.getMessage());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<StudentSubscription> createStudentSubscriptionManually(Long idStudentOrder) {

		Document<StudentSubscription> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			StudentOrder so = studentOrderRepository.findByIdStudentOrder(idStudentOrder);
			if (so == null) {
				throw new NullPointerException("Student order deatils not found.");
			}

			List<StagingStudentSubscription> ssList = stagingStudentSubscriptionRepository
					.findByIdStudentOrder(so.getIdStudentOrder());

			if (ssList.size() == 0) {
				throw new NullPointerException("Staging deatils for this order not found");
			}

			for (StagingStudentSubscription sss : ssList) {

				StudentSubscription previousSubscription = null;
				if (sss.getIdBatch() != null) {
					previousSubscription = studentSubscriptionRepository
							.findFirstByIdStudentAndIdProductAndIdBatchAndActiveFlag(sss.getIdStudent(),
									sss.getIdProduct(), sss.getIdBatch(), Boolean.TRUE);
				} else {
					previousSubscription = studentSubscriptionRepository.findFirstByIdStudentAndIdproductLineAndActiveFlag(
							sss.getIdStudent(), sss.getIdproductLine(), Boolean.TRUE);
				}

				if (sss.getPurchaseType().equalsIgnoreCase("RENEWAL")) {
					if (previousSubscription == null)
						throw new NullPointerException("Previous Subscription not found.");
					else if (previousSubscription.getActiveFlag())
						throw new AppException("User Already Have Active Subscrption for this product");

					Map<String, Map<String, Object>> response = paytmPaymentConfig
							.paymentPendingStatusCheck(sss.getOrderId());

					Map<String, Object> body = response.get("body");
					Map<String, String> resultInfo = (Map<String, String>) body.get("resultInfo");

					if (resultInfo.get("resultStatus").equals("TXN_SUCCESS")) {
						so.setSecondaryStatus(null);
						so.setDisputeFlag(false);

						sss.setActiveFlag(Boolean.TRUE);
						sss.setBankName(String.valueOf(body.get("bankName")));
						sss.setBankTransactionId(String.valueOf(body.get("bankTxnId")));
						sss.setPaymentMode(String.valueOf(body.get("paymentMode")));
						sss.setTransactionDate(Instant.now());
						sss.setTransactionAmount(Float.parseFloat(String.valueOf(body.get("txnAmount"))));
					}

					previousSubscription.setActiveFlag(true);

					sss.setLastPaymentDate(Instant.now());
					sss.setPaymentMode("Manual");
					sss.setPurchaseDate(Instant.now());

//					if (sss.getSubscriptionType().equals("MONTH")) {
//
//						LocalDateTime dateMonth = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(1);
//
//						sss.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
//
//					} else if (sss.getSubscriptionType().equals("QUARTER")) {
//						LocalDateTime dateQuarter = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(3);
//						sss.setNextPaymentDate(dateQuarter.atZone(ZoneId.systemDefault()).toInstant());
//					} else if (sss.getSubscriptionType().equals("ANNUAL")) {
//						LocalDateTime dateYear = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(12);
//						sss.setNextPaymentDate(dateYear.atZone(ZoneId.systemDefault()).toInstant());
//					}
					
					ProductDuration productDuration = productDurationRepository
							.findByDurationCode(sss.getSubscriptionType());
					
					if(productDuration==null)
						throw new AppException("No product duration data found");
					
					LocalDateTime duration = sss.getLastPaymentDate()
							.atZone(zoneIndia).toLocalDateTime().plusDays(productDuration.getDuration());
					sss
					.setNextPaymentDate(duration.atZone(ZoneId.systemDefault()).toInstant());

					if (sss.getIdBatch() != null) {
						Batch batchAvailability = batchRepository.findByIdBatch(sss.getIdBatch());
						if (batchAvailability.getPaymentStatus() != null) {
							if (batchAvailability.getPaymentStatus().equals("IN_PROGRESS")) {
								result.setData(null);
								result.setMessage(
										"Other payment is in progress, please wait for 3-4 min and try again");
								result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
								return result;
							}
						}
						Product product = productRepository.findByIdProductAndActiveFlag(sss.getIdProduct(),
								Boolean.TRUE);
						if (product == null) {
							throw new AppException("Invalid idProduct");
						}
						if (batchAvailability.getCurrentVacancy() <= 0
								|| batchAvailability.getCurrentOccupancy() > product.getBatchSize()) {
							throw new AppException("No Seates are available for this Live course");
						}
						batchAvailability.setPaymentStatus("IN_PROGRESS");
						batchAvailability = batchRepository.save(batchAvailability);

						LocalDateTime gracePeriodDate = sss.getNextPaymentDate().atZone(zoneIndia).toLocalDateTime()
								.plusDays(gracePeriod);
						sss.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
					} else {
						sss.setSubscriptionEndDate(sss.getNextPaymentDate());
					}

					previousSubscription.setFreeFlag(false);
					previousSubscription.setIdProduct(sss.getIdProduct());
					previousSubscription.setIdProductGroup(sss.getIdProductGroup());
					previousSubscription.setIdproductLine(sss.getIdproductLine());
					previousSubscription.setIdSpecialOffer(sss.getIdSpecialOffer());
					previousSubscription.setIdStudent(sss.getIdStudent());
					previousSubscription.setIdStudentOrder(sss.getIdStudentOrder());
					previousSubscription.setLastPaymentDate(sss.getLastPaymentDate());
					previousSubscription.setNextPaymentDate(sss.getNextPaymentDate());
					previousSubscription.setPurchaseAmount(sss.getPurchaseAmount());
					previousSubscription.setPurchaseDate(sss.getPurchaseDate());
					previousSubscription.setPurchaseLevel(sss.getPurchaseLevel());
					previousSubscription.setPurchaseType(sss.getPurchaseType());
					previousSubscription.setIdBatch(sss.getIdBatch());
					// checking
					Boolean soFlag = sss.getIdSpecialOffer() != null ? sss.getSpecialOfferFlag() : false;
					previousSubscription.setSpecialOfferFlag(soFlag);
					previousSubscription.setSubscriptionEndDate(sss.getSubscriptionEndDate());
					previousSubscription.setSubscriptionType(sss.getSubscriptionType());
					previousSubscription.setUpdatedAt(sss.getUpdatedAt());
					previousSubscription.setUpdatedBy(sss.getUpdatedBy());
					previousSubscription.setUserSurId(sss.getUserSurId());
					sss.setPaymentStatus("Success");
					stagingStudentSubscriptionRepository.save(sss);
					StudentSubscription ss = studentSubscriptionRepository.save(previousSubscription);
					if (ss != null) {
						so.setRemarks("Manual Subscription Created: User id:" + userPrincipal.getUserSurId()
								+ " User Name:" + userPrincipal.getUsername()
								+ ", initiated a Renewal subscription  at " + Instant.now() + ".");
						so.setOrderStatus("Success");
						so = studentOrderRepository.save(so);
						if (ss.getIdBatch() != null) {
							Batch batchUpdate = batchRepository.findByIdBatch(ss.getIdBatch());
							Product product = productRepository.findByIdProductAndActiveFlag(ss.getIdProduct(),
									Boolean.TRUE);
							if (product == null) {
								throw new AppException("Invalid idProduct");
							}
							if (batchUpdate.getCurrentVacancy() <= 0
									|| batchUpdate.getCurrentOccupancy() > product.getBatchSize()) {
								throw new AppException("No Seates are available for this Live course");
							}
							if (product.getBatchSize() - batchUpdate.getCurrentVacancy() >= 0) {
								batchUpdate.setCurrentVacancy(batchUpdate.getCurrentVacancy() - 1);
							} else if (product.getBatchSize() == 1) {
								batchUpdate.setCurrentVacancy(0);
							}
							if (product.getBatchSize() - batchUpdate.getCurrentOccupancy() >= 0) {
								batchUpdate.setCurrentOccupancy(batchUpdate.getCurrentOccupancy() + 1);
								batchUpdate.setPaymentStatus(null);
								batchRepository.save(batchUpdate);
							}
						}

					}
					result.setData(ss);
					result.setMessage("Subscription record updated sucessfully!");
					result.setStatusCode(HttpStatus.OK.value());
				} else {
					if (previousSubscription != null)
						if (previousSubscription.getActiveFlag())
							throw new AppException("User Already Have Active Subscrption for this product");

					Map<String, Map<String, Object>> response = paytmPaymentConfig
							.paymentPendingStatusCheck(sss.getOrderId());

					Map<String, Object> body = response.get("body");
					Map<String, String> resultInfo = (Map<String, String>) body.get("resultInfo");

					if (resultInfo.get("resultStatus").equals("TXN_SUCCESS")) {
						so.setSecondaryStatus(null);
						so.setDisputeFlag(false);

						sss.setPaymentStatus("Success");
						sss.setActiveFlag(Boolean.TRUE);
						sss.setBankName(String.valueOf(body.get("bankName")));
						sss.setBankTransactionId(String.valueOf(body.get("bankTxnId")));
						sss.setPaymentMode(String.valueOf(body.get("paymentMode")));
						sss.setTransactionDate(Instant.now());
						sss.setTransactionAmount(Float.parseFloat(String.valueOf(body.get("txnAmount"))));
					}

					sss.setLastPaymentDate(Instant.now());
					sss.setPaymentMode("Manual");
					sss.setPurchaseDate(Instant.now());

//					if (sss.getSubscriptionType().equals("MONTH")) {
//
//						LocalDateTime dateMonth = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(1);
//
//						sss.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
//
//					} else if (sss.getSubscriptionType().equals("QUARTER")) {
//						LocalDateTime dateQuarter = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(3);
//						sss.setNextPaymentDate(dateQuarter.atZone(ZoneId.systemDefault()).toInstant());
//					} else if (sss.getSubscriptionType().equals("ANNUAL")) {
//						LocalDateTime dateYear = sss.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime()
//								.plusMonths(12);
//						sss.setNextPaymentDate(dateYear.atZone(ZoneId.systemDefault()).toInstant());
//					}
					
					ProductDuration productDuration = productDurationRepository
							.findByDurationCode(sss.getSubscriptionType());
					
					if(productDuration==null)
						throw new AppException("No product duration data found");
					
					LocalDateTime duration = sss.getLastPaymentDate()
							.atZone(zoneIndia).toLocalDateTime().plusDays(productDuration.getDuration());
					sss
					.setNextPaymentDate(duration.atZone(ZoneId.systemDefault()).toInstant());

					if (sss.getIdBatch() != null) {
						Batch batchAvailability = batchRepository.findByIdBatch(sss.getIdBatch());
						if (batchAvailability.getPaymentStatus() != null) {
							if (batchAvailability.getPaymentStatus().equals("IN_PROGRESS")) {
								result.setData(null);
								result.setMessage(
										"Other payment is in progress, please wait for 3-4 min and try again");
								result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
								return result;
							}
						}
						Product product = productRepository.findByIdProductAndActiveFlag(sss.getIdProduct(),
								Boolean.TRUE);
						if (product == null) {
							throw new AppException("Invalid idProduct");
						}
						if (batchAvailability.getCurrentVacancy() <= 0
								|| batchAvailability.getCurrentOccupancy() > product.getBatchSize()) {
							throw new AppException("No Seates are available for this Live course");
						}
						batchAvailability.setPaymentStatus("IN_PROGRESS");
						batchAvailability = batchRepository.save(batchAvailability);

						LocalDateTime gracePeriodDate = sss.getNextPaymentDate().atZone(zoneIndia).toLocalDateTime()
								.plusDays(gracePeriod);
						sss.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
					} else {
						sss.setSubscriptionEndDate(sss.getNextPaymentDate());
					}

					StudentSubscription newStudentSubscription = new StudentSubscription();
					newStudentSubscription.setActiveFlag(true);
					newStudentSubscription.setCreatedAt(sss.getCreatedAt());
					newStudentSubscription.setCreatedBy(sss.getCreatedBy());
					newStudentSubscription.setFreeFlag(false);
					newStudentSubscription.setIdProduct(sss.getIdProduct());
					newStudentSubscription.setIdProductGroup(sss.getIdProductGroup());
					newStudentSubscription.setIdproductLine(sss.getIdproductLine());
					newStudentSubscription.setIdSpecialOffer(sss.getIdSpecialOffer());
					newStudentSubscription.setIdStudent(sss.getIdStudent());
					newStudentSubscription.setIdStudentOrder(sss.getIdStudentOrder());
					newStudentSubscription.setLastPaymentDate(sss.getLastPaymentDate());
					newStudentSubscription.setNextPaymentDate(sss.getNextPaymentDate());
					newStudentSubscription.setPurchaseAmount(sss.getPurchaseAmount());
					newStudentSubscription.setPurchaseDate(sss.getPurchaseDate());
					newStudentSubscription.setPurchaseLevel(sss.getPurchaseLevel());
					newStudentSubscription.setPurchaseType(sss.getPurchaseType());
					newStudentSubscription.setIdBatch(sss.getIdBatch());
					// checking
					Boolean soFlag = sss.getIdSpecialOffer() != null ? sss.getSpecialOfferFlag() : false;
					newStudentSubscription.setSpecialOfferFlag(soFlag);
					newStudentSubscription.setSubscriptionEndDate(sss.getSubscriptionEndDate());
					newStudentSubscription.setSubscriptionType(sss.getSubscriptionType());
					newStudentSubscription.setUpdatedAt(sss.getUpdatedAt());
					newStudentSubscription.setUpdatedBy(sss.getUpdatedBy());
					newStudentSubscription.setUserSurId(sss.getUserSurId());
					sss.setPaymentStatus("Success");
					stagingStudentSubscriptionRepository.save(sss);
					StudentSubscription ss = studentSubscriptionRepository.save(newStudentSubscription);
					if (ss != null) {
						so.setRemarks(" Manual Subscription Created: User id:" + userPrincipal.getUserSurId()
								+ " User Name:" + userPrincipal.getUsername() + ", initiated a new subscription   at "
								+ Instant.now() + ".");
						so.setOrderStatus("Success");
						so = studentOrderRepository.save(so);
						if (ss.getIdBatch() != null) {

							Batch batchUpdate = batchRepository.findByIdBatch(ss.getIdBatch());
							Product product = productRepository.findByIdProductAndActiveFlag(ss.getIdProduct(),
									Boolean.TRUE);
							if (product == null) {
								throw new NullPointerException("Invalid idProduct");
							}
							if (batchUpdate.getCurrentVacancy() <= 0
									|| batchUpdate.getCurrentOccupancy() > product.getBatchSize()) {
								throw new AppException("No Seates are available for this Live course");
							}
							if (product.getBatchSize() - batchUpdate.getCurrentVacancy() >= 0) {
								batchUpdate.setCurrentVacancy(batchUpdate.getCurrentVacancy() - 1);
							} else if (product.getBatchSize() == 1) {
								batchUpdate.setCurrentVacancy(0);
							}
							if (product.getBatchSize() - batchUpdate.getCurrentOccupancy() >= 0) {
								batchUpdate.setCurrentOccupancy(batchUpdate.getCurrentOccupancy() + 1);
								batchUpdate.setPaymentStatus(null);
								batchRepository.save(batchUpdate);
							}
						}

					}
					
					if(so.getCouponCode()!=null) {
						
						ProductAmount productAmount = productAmountRepository
								.findByAmount(so.getActualAmount());

						ProductPricing productPricing = productPricingRepository
								.findByIdProductAmountAndIdProductDurationAndIdProduct(
										productAmount.getIdProductAmount(),
										productDuration.getIdProductDuration(),
										newStudentSubscription.getIdProduct());
						Redemption redemptionSave = new Redemption();
						redemptionSave.setCouponCode(so.getCouponCode());
						redemptionSave.setIdVlUser(so.getUserSurId());
						redemptionSave.setIdProductPricing(productPricing.getIdProductPricing());
						redemptionSave.setRedemptionDate(LocalDateTime.now());
						Coupon coupon = couponRepository.findByCouponCode(so.getCouponCode());
						redemptionSave.setDiscount(coupon.getDiscount());

						redemptionRepository.save(redemptionSave);
						Long updatedCount = coupon.getUsedCount() + 1;
						coupon.setUsedCount(updatedCount);
						couponRepository.save(coupon);
					}

					// send Transaction email to the user
					try {
						emailService.sendInvoceThroughEmail(sss.getOrderId());

					} catch (Exception e) {
						LOGGER.error(e.getMessage());
					}

					result.setData(ss);
					result.setMessage("New Subscription got created");
					result.setStatusCode(HttpStatus.OK.value());
				}

			}
		} catch (Exception exception) {
			result.setData(null);
			result.setMessage(exception.getLocalizedMessage());
			result.setStatusCode(500);
			LOGGER.error(exception.getMessage());
		}

		return result;
	}

	@Override
	public Document<Map<String, Map<String, Object>>> getOrderStatusForOrderId(String orderId) {
		Document<Map<String, Map<String, Object>>> result = new Document<>();
		try {

			Map<String, Map<String, Object>> pors = paytmPaymentConfig.paymentPendingStatusCheck(orderId);

			if (pors == null)
				throw new AppException("Something went wrong while fetching order status");

			StudentOrder sOrder = studentOrderRepository.findByOrderId(orderId);

			String remarks;
			if (sOrder != null) {
				remarks = sOrder.getRemarks();
			} else {
				remarks = null;
			}

			Map<String, Object> body = pors.get("body");
			body.put("remarks", remarks);
			pors.put("body", body);
			result.setData(pors);
			result.setMessage("Request successfull!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exception) {
			result.setData(null);
			result.setMessage(exception.getLocalizedMessage());
			result.setStatusCode(500);
			LOGGER.error(exception.getMessage());
		}
		return result;
	}

	@Transactional
	@Override
	public Document<StudentSubscription> checkStudentSubscriptionByIdSubjectForExtraCurr(Long idSubject) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Document<StudentSubscription> result = new Document<>();

		try {
			Student student = null;

			User user = userRepository.findByUsername(userName);

			if (user == null)
				throw new NullPointerException("Invalid user");

			if (user.getRegisteredAs().equalsIgnoreCase("Student")) 
				student = studentRepository.findByUser(user);

			if (student == null)
				throw new NullPointerException("Invalid Student");
			

			Product prod = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(6L, 4L,
							idSubject, 4L, 6L, Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("No Product Found!");

			StudentSubscription ss = studentSubscriptionRepository.findFirstByIdStudentAndIdProductAndActiveFlag(
					student.getIdStudent(), prod.getIdProduct(), Boolean.TRUE);

			if (ss != null) {
				result.setData(ss);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully get order details");
			} else {

				StudentSubscription temp = new StudentSubscription();
				temp.setActiveFlag(Boolean.TRUE);
				temp.setIdProduct(prod.getIdProduct());
				temp.setFreeFlag(Boolean.TRUE);
				temp.setIdProductGroup(prod.getIdProductGroup());
				temp.setIdproductLine(prod.getIdProductLine());
				temp.setIdStudent(student.getIdStudent());
				result.setData(studentSubscriptionRepository.save(temp));
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfully get order details");
			}

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
	public Document<List<StudentSubdcribedSubDto>> getSubscribedSubStatus(Long stdId) {
		Document<List<StudentSubdcribedSubDto>> result = new Document<>();
		List<StudentSubdcribedSubDto> studSubscDtoList = new ArrayList<>();
		StudentSubdcribedSubDto studentSubdcribedSubDto;
		try {

			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User");
		

			Student student = studentRepository.findByUser_userSurId(userPrincipal.getUserSurId());
			
			if (student == null)
				throw new AppException("Invalid Student information.");
			
			Long studentId = student.getIdStudent();


			List<Object[]> productList = productRepository.getSubscribedProducts(5L, student.getIdClassStandard(),
					student.getIdSyllabus(), student.getIdState(), studentId, Boolean.TRUE);
			
			
			if (productList.isEmpty()) {
				result.setData(studSubscDtoList);
				result.setMessage("No subjects are found.");
				result.setStatusCode(200);
				return result;
			}
				
			
				for (Object product : productList) {
					Object[] res = (Object[]) product;
					if (res == null) {
						throw new NullPointerException("No subjects Found!");
					}
					studentSubdcribedSubDto = new StudentSubdcribedSubDto();
					studentSubdcribedSubDto.setIdProduct(Long.parseLong(res[0].toString()));
					studentSubdcribedSubDto.setIdStudent(studentId);
					Subject s = subjectRepository.findByIdSubject(Long.parseLong(res[2].toString()));
					studentSubdcribedSubDto.setSubjectName(s.getSubjectName());
					studentSubdcribedSubDto.setColor(s.getColor());
					studentSubdcribedSubDto.setIdSubject(Long.parseLong(res[2].toString()));
					String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp";
					studentSubdcribedSubDto.setImageURL(imageUrl);

					if (res[1] != null) {
						
						studentSubdcribedSubDto.setIdStudentSubscription(Long.parseLong(res[1].toString()));
						studentSubdcribedSubDto.setPercentage(0L);

					} else {
						studentSubdcribedSubDto.setIdStudentSubscription(null);
						studentSubdcribedSubDto.setPercentage(0L);

					}
					studSubscDtoList.add(studentSubdcribedSubDto);
				}
				
				
				studSubscDtoList = studSubscDtoList.stream().filter(distinctByKey(StudentSubdcribedSubDto::getIdProduct))
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

	@Override
	public Document<List<StudentSubdcribedSubDto>> getSubscribedExtraCurSubStatus(Long stdId) {
		Document<List<StudentSubdcribedSubDto>> result = new Document<>();
		List<StudentSubdcribedSubDto> studSubscDtoList = new ArrayList<>();
		StudentSubdcribedSubDto studentSubdcribedSubDto;
		try {
			
			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User Session");

            Student student = studentRepository.findByUser_userSurId(userPrincipal.getUserSurId());
			
			if (student == null)
				throw new AppException("Invalid Student information.");
			
			Long studentId = student.getIdStudent();

			List<Object[]> productList = productRepository.getSubscribedExtraCurrProducts(6L, studentId, Boolean.TRUE);
			
			
			if (productList.isEmpty()) {
				result.setData(studSubscDtoList);
				result.setMessage("No subjects are found.");
				result.setStatusCode(200);
				return result;
			}
			
		   for (Object product : productList) {
					Object[] res = (Object[]) product;
					if (res == null) {
						throw new NullPointerException("No subjects Found!");
					}
					studentSubdcribedSubDto = new StudentSubdcribedSubDto();
					studentSubdcribedSubDto.setIdProduct(Long.parseLong(res[0].toString()));
					studentSubdcribedSubDto.setIdStudent(studentId);
					Subject s = subjectRepository.findByIdSubject(Long.parseLong(res[2].toString()));
					studentSubdcribedSubDto.setSubjectName(s.getSubjectName());
					studentSubdcribedSubDto.setColor(s.getColor());
					studentSubdcribedSubDto.setIdSubject(Long.parseLong(res[2].toString()));
					String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ s.getIdSubject() + ".webp";
					studentSubdcribedSubDto.setImageURL(imageUrl);

					if (res[1] != null) {
						studentSubdcribedSubDto.setIdStudentSubscription(Long.parseLong(res[1].toString()));
						studentSubdcribedSubDto.setPercentage(0L);		
					} 
					else {
						studentSubdcribedSubDto.setIdStudentSubscription(null);
						studentSubdcribedSubDto.setPercentage(0L);
					}
					
					studSubscDtoList.add(studentSubdcribedSubDto);
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

	private Boolean getDistputeStatus(Long studentOrderId) {
		Boolean disputeFlag = false;

		StudentOrderTicket sotValidate = studentOrderTicketRepository
				.findFirstByIdStudentOrderAndTicketStatusNot(studentOrderId, OrderTicketStatus.Closed);

		if (sotValidate == null) {
			disputeFlag = Boolean.FALSE;
			return disputeFlag;
		} else {
			if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Open)) {
				disputeFlag = Boolean.TRUE;
				return disputeFlag;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Complete)) {
				disputeFlag = Boolean.TRUE;
				return disputeFlag;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Refund_Initiated)) {
				disputeFlag = Boolean.TRUE;
				return disputeFlag;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Other)) {
				disputeFlag = Boolean.TRUE;
				return disputeFlag;
			} else if (sotValidate.getTicketStatus().equals(OrderTicketStatus.Closed)) {
				disputeFlag = Boolean.TRUE;
				return disputeFlag;
			}
		}

		return disputeFlag;
	}

	@Override
	public Document<?> sendEmailInvoce(String orderId) {
		Document<String> result = new Document<>();
		try {

			emailService.sendInvoceThroughEmail(orderId);

			result.setData("successfully sent invoice");
			result.setStatusCode(200);
			result.setMessage("Request successfull.");

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(500);
		}

		return result;
	}

	/**
	 * @author Abdul Elahi
	 * 
	 *         to generate the csv file based on the filters
	 * 
	 */
	@Override
	public Document<byte[]> generateCSVSubscriptionData(OrderFilterDTO orderFilterDTO, String sort, String fileName,
			String recordType) {

		Document<byte[]> document = new Document<>();
		try {
			User loggedinUser = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedinUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedinUser == null)
				throw new AppException("Invalid User");

			List<StudentOrder> studentOrderList = null;

			sort = sort.equalsIgnoreCase("latest") ? "latest" : "old";

			List<String> statusList = orderFilterDTO.getStatusList() == null ? null
					: ((orderFilterDTO.getStatusList().contains("all") || orderFilterDTO.getStatusList().isEmpty())
							? null
							: orderFilterDTO.getStatusList());

			if (sort.equalsIgnoreCase("latest")) {
				studentOrderList = studentOrderRepository.getAllStudentOrderBasedOnParamDescAsList(
						orderFilterDTO.getOrderId(), orderFilterDTO.getMobile(), orderFilterDTO.getEmail(), statusList,
						orderFilterDTO.getFromDate(), orderFilterDTO.getToDate());
			} else {
				studentOrderList = studentOrderRepository.getAllStudentOrderBasedOnParamAscAsList(
						orderFilterDTO.getOrderId(), orderFilterDTO.getMobile(), orderFilterDTO.getEmail(), statusList,
						orderFilterDTO.getFromDate(), orderFilterDTO.getToDate());
			}

			StringWriter stringWriter = new StringWriter();
			CSVWriter csvWriter = new CSVWriter(stringWriter);
			csvWriter.writeNext(new String[] { "Invoice Number", "Invoice Date", "Invoice Status", "Customer Name",
					"GST Treatment", "GST Identification Number (GSTIN)", "Place of Supply", "TCS Tax Name",
					"TCS Percentage", "TCS Amount", "Nature Of Collection", "TCS Payable Account",
					"TCS Receivable Account", "PurchaseOrder", "Expense Reference ID", "Payment Terms",
					"Payment Terms Label", "Due Date", "Expected Payment Date", "Sales person", "Currency Code",
					"Exchange Rate", "Account", "Item Name", "SKU", "Item Desc", "Item Type", "HSN/SAC", "Quantity",
					"Usage unit", "Item Price", "Item Tax Exemption Reason", "Is Inclusive Tax", "Item Tax",
					"Item Tax Type", "Item Tax %", "Supply Type", "Discount Type", "Is Discount Before Tax",
					"Entity Discount Percent", "Entity Discount Amount", "Discount", "Discount Amount",
					"Shipping Charge", "Adjustment", "Adjustment Description", "PayTm", "Razorpay", "Partial Payments",
					"Template Name", "Notes", "Terms & Conditions", "Is Exported", "Exported Date And Time" });

			for (StudentOrder studentOrder : studentOrderList) {

				User user = userRepository.findByUserSurId(studentOrder.getUserSurId());

				// get subscription type based on plan
				String subsType = null;
				if (studentOrder.getNetAmount() == 999 || studentOrder.getNetAmount() == 999.0)
					subsType = "Annual";
				else
					subsType = "Quarter";

				// formatting the date
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				String invoiceDate = sdf.format(Date.from(studentOrder.getUpdatedAt()));
				String generationDate = sdf.format(Date.from(Instant.now()));
				String lastName = user.getLastName() == null || user.getLastName().equalsIgnoreCase("null") ? ""
						: user.getLastName();
				String customerName = user.getFirstName() + " " + lastName;

				// calculate gst
				float netAmount = studentOrder.getNetAmount();
				float gstAmount = (netAmount * 18) / 100;

				// generating last payment date
				Instant lastPaymentDate = studentOrder.getUpdatedAt();
				LocalDateTime localDateTime = LocalDateTime.ofInstant(lastPaymentDate, ZoneId.systemDefault());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String dueDate = localDateTime.format(formatter);

				csvWriter.writeNext(new String[] { String.valueOf(studentOrder.getOrderId()), // invoice number
						String.valueOf(invoiceDate), // invoice date
						String.valueOf(studentOrder.getOrderStatus()), // invoice status
						String.valueOf(customerName), // customer name
						String.valueOf("Consumer"), // gst treatment
						String.valueOf(""), // gstin
						String.valueOf("KA"), // place of supply
						String.valueOf(""), // tcs tax name
						String.valueOf(""), // tcs percentage
						String.valueOf(gstAmount), // tcs amount
						String.valueOf(""), // nature of collection
						String.valueOf(""), // tcs payable account
						String.valueOf(""), // tcs receivable account
						String.valueOf(""), // purchase order
						String.valueOf(""), // expense reference id
						String.valueOf("0"), // payment terms
						String.valueOf("Due On Recipt"), // payment term label
						String.valueOf(dueDate), // due date
						String.valueOf(dueDate), // expected payment date
						String.valueOf("Vistas Learning"), // sales person
						String.valueOf("INR"), // country code
						String.valueOf("1"), // exchange rate
						String.valueOf("vistas"), // account
						String.valueOf(subsType), // item name
						String.valueOf("AD_FREE_SUBSCRIPTION"), // sku
						String.valueOf(subsType + " premium subscription"), // item description
						String.valueOf("premium subscription"), // item type
						String.valueOf("999294"), // HSN/SAC
						String.valueOf(1), // quantity
						String.valueOf(subsType), // usage unit
						String.valueOf(studentOrder.getNetAmount()), // item price
						String.valueOf(""), // item tax extemption reason
						String.valueOf(true), // is inclusive of tax
						String.valueOf(""), // item tax
						String.valueOf("GST 18"), // item tax type
						String.valueOf("18%"), // item tax %
						String.valueOf("Taxable"), // supply type
						String.valueOf(""), // discount type
						String.valueOf(""), // is discount before tax
						String.valueOf(""), // entity discount percentage
						String.valueOf(""), // Entity discount amount
						String.valueOf(""), // Discount
						String.valueOf(""), // discount amount
						String.valueOf(""), // shipping charges
						String.valueOf(""), // adjustments
						String.valueOf(""), // adjustment description
						String.valueOf(""), // Paypal
						String.valueOf(""), // razorpay
						String.valueOf(""), // partial payment
						String.valueOf("SpreadSheet"), // template name
						String.valueOf("Thank you for your business"), // notes
						String.valueOf(""), // terms and conditions
						String.valueOf(true), String.valueOf(generationDate)

				});
			}

			csvWriter.close();

			byte[] data = stringWriter.toString().getBytes();

			// csv generation log
			if (recordType.equalsIgnoreCase("new")) {
				String status = String.join(",", orderFilterDTO.getStatusList());

				if (orderFilterDTO.getToDate() == null) {

					orderFilterDTO.setToDate(Date.from(Instant.now()));
				}

				InvoiceCsvLogs invoiceCsvLogs = new InvoiceCsvLogs(orderFilterDTO.getMobile(),
						orderFilterDTO.getEmail(), orderFilterDTO.getOrderId(), status, orderFilterDTO.getFromDate(),
						orderFilterDTO.getToDate().toInstant(), fileName);

				invoiceCsvLogs.setCreatedAt(Instant.now());
				invoiceCsvLogs.setUpdatedAt(Instant.now());
				invoiceCsvLogs.setCreatedBy(loggedinUser.getUserSurId());
				invoiceCsvLogs.setUpdatedBy(loggedinUser.getUserSurId());
				invoiceCsvLogsService.save(invoiceCsvLogs);
			}

			document.setData(data);
			document.setMessage("successful");
			document.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setData(null);
			document.setMessage("failed");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return document;
	}

	@Override
	public Document<?> verifyCSVSubscriptionData(OrderFilterDTO orderFilterDTO) {

		Document<Map<String, String>> document = new Document<>();

		List<String> statusList = orderFilterDTO.getStatusList() == null ? null
				: ((orderFilterDTO.getStatusList().contains("all") || orderFilterDTO.getStatusList().isEmpty()) ? null
						: orderFilterDTO.getStatusList());
		try {
			// List<StudentOrder> studentOrderList = studentOrderRepository.findAll();
			List<StudentOrder> studentOrderList = studentOrderRepository.getAllStudentOrderBasedOnParamDescAsList(
					orderFilterDTO.getOrderId(), orderFilterDTO.getMobile(), orderFilterDTO.getEmail(), statusList,
					orderFilterDTO.getFromDate(), orderFilterDTO.getToDate());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HHmmss");
			LocalDateTime now = LocalDateTime.now();
			String csvFileName = "INV_" + dtf.format(now) + ".csv";
			String noOfRecord = studentOrderList.size() + "";

			if (orderFilterDTO.getOrderId() == null || orderFilterDTO.getOrderId().isEmpty()
					|| orderFilterDTO.getOrderId().equalsIgnoreCase("null"))
				orderFilterDTO.setOrderId("All");

			if (orderFilterDTO.getEmail() == null || orderFilterDTO.getEmail().isEmpty()
					|| orderFilterDTO.getEmail().equalsIgnoreCase("null"))
				orderFilterDTO.setEmail("All");

			if (orderFilterDTO.getMobile() == null || orderFilterDTO.getMobile().isEmpty()
					|| orderFilterDTO.getMobile().equalsIgnoreCase("null"))
				orderFilterDTO.setMobile("All");

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

			String fromDateStr;
			if (orderFilterDTO.getFromDate() != null) {

				Date fromDate = orderFilterDTO.getFromDate();
				fromDateStr = dateFormat.format(fromDate);
			} else {
				fromDateStr = "All";
			}

			String toDateStr;
			if (orderFilterDTO.getToDate() != null) {

				Date toDate = orderFilterDTO.getToDate();
				toDateStr = dateFormat.format(toDate);

			} else {
				toDateStr = "All";
			}
			if (orderFilterDTO.getStatusList() == null)
				orderFilterDTO.getStatusList().contains("all");

			Map<String, String> filePopUp = new HashMap<>();
			filePopUp.put("fileName", csvFileName);
			filePopUp.put("noOfRecords", noOfRecord);
			filePopUp.put("orderId", orderFilterDTO.getOrderId());
			filePopUp.put("email", orderFilterDTO.getEmail());
			filePopUp.put("mobile", orderFilterDTO.getMobile());
			filePopUp.put("statusList", orderFilterDTO.getStatusList().toString() == null ? "All"
					: orderFilterDTO.getStatusList().toString());
			filePopUp.put("fromDate", fromDateStr);
			filePopUp.put("toDate", toDateStr);

			document.setData(filePopUp);
			document.setMessage("successful");
			document.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setData(null);
			document.setMessage("failed");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return document;
	}

	@Override
	public Document<Page<InvoiceCsvLogsDTO>> getInvoiceCsvLogs(InvoiceCsvLogsFilterDTO invoiceCsvLogsFilterDTO,
			String sort, int pageNumber, int pageSize) {

		Document<Page<InvoiceCsvLogsDTO>> result = new Document<>();

		sort = sort.equalsIgnoreCase("latest") ? "latest" : "old";

		Page<InvoiceCsvLogs> invoiceCsvLogsList = null;

		Page<InvoiceCsvLogsDTO> page = null;

		try {
			List<String> statusList = invoiceCsvLogsFilterDTO.getStatusList() == null ? null
					: ((invoiceCsvLogsFilterDTO.getStatusList().contains("all")
							|| invoiceCsvLogsFilterDTO.getStatusList().isEmpty()
							|| (invoiceCsvLogsFilterDTO.getStatusList().contains("Success")
									&& invoiceCsvLogsFilterDTO.getStatusList().contains("Failed")
									&& invoiceCsvLogsFilterDTO.getStatusList().contains("Pending"))) ? null
											: invoiceCsvLogsFilterDTO.getStatusList());
			String concatStatusList = null;

			if (statusList != null) {
				concatStatusList = String.join(",", statusList);
			}

			Pageable paging = PageRequest.of(pageNumber, pageSize);

			if (invoiceCsvLogsFilterDTO.getEmail() == null && invoiceCsvLogsFilterDTO.getMobile() == null
					&& statusList == null && invoiceCsvLogsFilterDTO.getOrderId() == null
					&& invoiceCsvLogsFilterDTO.getToDate() == null && invoiceCsvLogsFilterDTO.getFromDate() == null) {
				if (sort.equalsIgnoreCase("latest"))
					invoiceCsvLogsList = invoiceCsvLogsRepository.findAllByOrderByIdInvoiceCsvLogsDesc(paging);
				else
					invoiceCsvLogsList = invoiceCsvLogsRepository.findAllByOrderByIdInvoiceCsvLogsAsc(paging);

			} else {
				
					invoiceCsvLogsList = invoiceCsvLogsRepository.getInvoiceCsvLogsBasedOnParamDesc(
							invoiceCsvLogsFilterDTO.getOrderId(), invoiceCsvLogsFilterDTO.getMobile(),
							invoiceCsvLogsFilterDTO.getEmail(), concatStatusList, invoiceCsvLogsFilterDTO.getFromDate(),
							invoiceCsvLogsFilterDTO.getToDate(), invoiceCsvLogsFilterDTO.getInvoiceName(), paging);
			}

			List<InvoiceCsvLogsDTO> invoiceCsvLogsDTOList = new ArrayList<>();

			for (InvoiceCsvLogs invoiceCsvLogs : invoiceCsvLogsList) {
				InvoiceCsvLogsDTO invoiceCsvLogsDTO = new InvoiceCsvLogsDTO();
				invoiceCsvLogsDTO.setId(invoiceCsvLogs.getIdInvoiceCsvLogs());
				invoiceCsvLogsDTO.setEmail(invoiceCsvLogs.getEmail());
				invoiceCsvLogsDTO.setMobile(invoiceCsvLogs.getMobile());
				invoiceCsvLogsDTO.setInvoiceName(invoiceCsvLogs.getInvoiceName());
				invoiceCsvLogsDTO.setFromDate(invoiceCsvLogs.getFromDate());
				invoiceCsvLogsDTO.setToDate(invoiceCsvLogs.getToDate());
				invoiceCsvLogsDTO.setOrderId(invoiceCsvLogs.getOrderId());
				invoiceCsvLogsDTO.setStatusList(Arrays.asList(invoiceCsvLogs.getStatusList().split(",")));
				invoiceCsvLogsDTO.setCreatedAt(invoiceCsvLogs.getCreatedAt());

				invoiceCsvLogsDTOList.add(invoiceCsvLogsDTO);
			}

			page = new PageImpl<>(invoiceCsvLogsDTOList, paging, invoiceCsvLogsList.getTotalElements());

			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully fetched  Invoice CSV Logs.");

			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
	}

	@Override
	public Document<?> downloadCSVByIdInvoiceCsvLogs(Long idInvoiceCsvLogs, String recordType, String sort) {

		Document<?> document = new Document<>();

		OrderFilterDTO orderFilterDTO = new OrderFilterDTO();

		try {
			InvoiceCsvLogs invoiceCsvLogs = invoiceCsvLogsRepository.findByIdInvoiceCsvLogs(idInvoiceCsvLogs);
			if (invoiceCsvLogs == null)
				throw new AppException("Invalid invoice csv logs info.");

			orderFilterDTO.setEmail(invoiceCsvLogs.getEmail());
			orderFilterDTO.setMobile(invoiceCsvLogs.getMobile());
			orderFilterDTO.setFromDate(invoiceCsvLogs.getFromDate());
			orderFilterDTO.setToDate(Date.from(invoiceCsvLogs.getToDate()));
			orderFilterDTO.setOrderId(invoiceCsvLogs.getOrderId());
			orderFilterDTO.setStatusList(Arrays.asList(invoiceCsvLogs.getStatusList().split(",")));
			document = generateCSVSubscriptionData(orderFilterDTO, sort, invoiceCsvLogs.getInvoiceName(), recordType);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return document;
	}

	public Document<?> upgradeManualSubscription(String phoneNumber, Integer noOfMonth, Float price, String purchaseType) {
		Document<MannualUpdateDetailsDTO> result = new Document<>();

		MannualUpdateDetailsDTO mannualUpdateDetailsDTO = new MannualUpdateDetailsDTO();
		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			// Find the user with the given phone number
			User user = userRepository.findByMobileNumber(phoneNumber);

			if (user == null)
				throw new AppException("User details not found for provided Phone number :" + phoneNumber + "");

			if (price != 1999 && price != 5999 && price != 9999)
				throw new AppException("Currently only quarter annnual and enterprise plans are supported for bulk mannual Subsrcripiton");
			
			if (!purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM") && !purchaseType.equalsIgnoreCase("ENP_AN")
					&& !purchaseType.equalsIgnoreCase("ENP_AN_PLUS") ) {
				throw new AppException("invalid purchase type : "+purchaseType);
			}

			String planType = null;
			float amount = 0;
			if (purchaseType.equalsIgnoreCase("AD_FREE_SUBSCRIPTION") && price==399) {
				planType="QUARTER";
				amount= 399f;
			}else if(purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM")){
				planType="ANNUAL";
				amount = 1999f;;
			}else if (purchaseType.equalsIgnoreCase("ENP_AN")) {
				amount=5999f;
				planType="ANNUAL";
			} else if(purchaseType.equalsIgnoreCase("ENP_AN_PLUS")){
				amount=9999f;
				planType="ANNUAL";
			}else  {
				planType = "unknown";
			}

			Student student = studentRepository.findByUser(user);

			if (student == null)
				throw new AppException(
						"Invalid Student information found for the userSurId: " + user.getUserSurId() + " .");

			Product product = productRepository.findByProductCdAndActiveFlag(purchaseType, Boolean.TRUE);
			if (product == null)
				throw new AppException("Invalid Product Information found for : "+ purchaseType);					
			
			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findFirstByIdStudentAndIdproductLineAndActiveFlag(student.getIdStudent(), 11L, true);
			
			product = studentSubscription== null?product : productRepository.findByProductCdAndActiveFlag(
					purchaseType, Boolean.TRUE);  //FIX ME
			if (product == null)
				throw new AppException("Product Information not found.");

			if (studentSubscription != null) {
				
				if(((studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN") || 
						studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) && purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM")) ||
						(studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) && purchaseType.equalsIgnoreCase("ENP_AN")) {
					
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setType("upgrade");
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO
							.setMessage("Enterprise to premium subscription plan upgradation not possible.");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}

				// fetching student order for the active subscription
				StudentOrder studentOrder = studentOrderRepository
						.findByIdStudentOrder(studentSubscription.getIdStudentOrder());

				if (studentOrder == null) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(noOfMonth);
					mannualUpdateDetailsDTO.setType("upgrade");
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO
							.setMessage("Subscription cannot be Upgrade since the student order doesnot exists.");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}

				studentOrder.setUpdatedAt(Instant.now());
				studentOrder.setNetAmount(amount);
				studentOrder.setTotalAmount(amount);
				studentOrder.setRemarks((studentOrder.getRemarks() == null ? " Bulk Subscription Created By User id:" + userPrincipal.getUserSurId()
				+ " User Name:" + userPrincipal.getUsername() + " at "
				+ Instant.now() + " Subscription Type:" + "Upgrade " +"Plan Type "+purchaseType +" "+amount : " Bulk Subscription Created by User id:" + userPrincipal.getUserSurId()
						+ " User Name:" + userPrincipal.getUsername() + " at "
						+ Instant.now() + " for Subscription Type:" + "Upgrade "+"Plan Type "+purchaseType +" "+amount+ ". \n "+studentOrder.getRemarks()));

				studentOrder = studentOrderRepository.save(studentOrder);

				// fetching student staging subscription order for the student order
				StagingStudentSubscription stagingStudentSubscription = stagingStudentSubscriptionRepository
						.getByIdStudentOrder(studentOrder.getIdStudentOrder());

				if (stagingStudentSubscription == null) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(noOfMonth);
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO.setType("upgrade");
					mannualUpdateDetailsDTO
							.setMessage("Subscription cannot be Upgrade since the staging subsciption doesnot exists.");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}

				LocalDate lastSubscriptionDate = studentSubscription.getLastPaymentDate().atZone(zoneIndia)
						.toLocalDate().plusMonths(noOfMonth);

				Instant extendedSubscriptionDate = lastSubscriptionDate.atStartOfDay(zoneIndia).toInstant();

				stagingStudentSubscription.setIdProduct(product.getIdProduct());
				stagingStudentSubscription.setNextPaymentDate(extendedSubscriptionDate);
				stagingStudentSubscription.setTransactionAmount(amount);
				stagingStudentSubscription.setActiveFlag(true);
				stagingStudentSubscription.setPurchaseAmount(String.valueOf(amount));
				stagingStudentSubscription.setSubscriptionEndDate(extendedSubscriptionDate);
				stagingStudentSubscription.setUpdatedAt(Instant.now());
				stagingStudentSubscription.setPurchaseLevel(purchaseType);
				stagingStudentSubscription.setPurchaseType("RENEWAL");
				stagingStudentSubscription.setSubscriptionType(planType.toUpperCase());

				stagingStudentSubscriptionRepository.save(stagingStudentSubscription);

				mannualUpdateDetailsDTO.setClassStandard(student.getIdClassStandard());
				mannualUpdateDetailsDTO.setIsSubscriptionExists(true);
				mannualUpdateDetailsDTO.setLastSubscriptionEndDate(studentSubscription.getCreatedAt());

				studentSubscription.setIdProduct(product.getIdProduct());
				studentSubscription.setNextPaymentDate(extendedSubscriptionDate);
				studentSubscription.setSubscriptionEndDate(extendedSubscriptionDate);
				studentSubscription.setPurchaseAmount(String.valueOf(amount));
				studentSubscription.setPurchaseType(stagingStudentSubscription.getPurchaseType());
				studentSubscription.setPurchaseLevel(stagingStudentSubscription.getPurchaseLevel());
				studentSubscription.setUpdatedAt(Instant.now());
				studentSubscription.setSubscriptionType(planType);

				studentSubscriptionRepository.save(studentSubscription);

				mannualUpdateDetailsDTO.setMessage("Subscription updated with " + noOfMonth + " months successfully.");
				mannualUpdateDetailsDTO.setName(user.getFirstName());
				mannualUpdateDetailsDTO.setNextSubscriptionEndDate(studentSubscription.getSubscriptionEndDate());
				mannualUpdateDetailsDTO.setNoOfMonths(noOfMonth);
				mannualUpdateDetailsDTO.setPhone(phoneNumber);
				mannualUpdateDetailsDTO.setPlan(amount);
				mannualUpdateDetailsDTO.setPlanType(planType);
				mannualUpdateDetailsDTO.setType("Upgrade");
				mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());

				result.setData(mannualUpdateDetailsDTO);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Subscription updated with " + noOfMonth + " months successfully.");

			} else {

				LocalDate dateExist = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(noOfMonth);
				Instant extendedDate = dateExist.atStartOfDay(zoneIndia).toInstant();

				StudentOrder studentOrder = new StudentOrder();
				studentOrder.setUserSurId(user.getUserSurId());
				studentOrder.setCreatedAt(Instant.now());
				studentOrder.setUpdatedAt(Instant.now());
				studentOrder.setDisputeFlag(false);
				studentOrder.setNetAmount(amount);
				studentOrder.setOrderId(randomStringGenerator.generateRandomOrderId(user.getUserSurId()));
				studentOrder.setOrderDate(Instant.now());
				studentOrder.setOrderStatus("Success");
				studentOrder.setTotalAmount(amount);
				studentOrder.setUpdatedBy(user.getUserSurId());
				studentOrder.setRemarks((studentOrder.getRemarks() == null ? " Bulk Subscription Created by User id:" + userPrincipal.getUserSurId()
						+ " User Name:" + userPrincipal.getUsername() + " at "
						+ Instant.now() + " for Subscription Type:" + "New " +"Plan Type "+purchaseType +" "+amount: " Bulk Subscription Created: User id:" + userPrincipal.getUserSurId()
						+ " User Name:" + userPrincipal.getUsername() + " at "
						+ Instant.now() + " for Subscription Type:" + "New "+"Plan Type "+purchaseType +" "+amount+" \n" +studentOrder.getRemarks()));

				studentOrder = studentOrderRepository.save(studentOrder);

				StagingStudentSubscription stagingStudentSubscription = new StagingStudentSubscription();

				stagingStudentSubscription.setUserSurId(studentOrder.getUserSurId());
				stagingStudentSubscription.setUpdatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedBy(studentOrder.getUserSurId());
				stagingStudentSubscription.setCreatedAt(Instant.now());
				stagingStudentSubscription.setUpdatedAt(Instant.now());
				stagingStudentSubscription.setActiveFlag(true);
				stagingStudentSubscription.setIdProduct(product.getIdProduct());
				stagingStudentSubscription.setIdProductGroup(product.getIdProductGroup());
				stagingStudentSubscription.setIdproductLine(product.getIdProductLine());
				stagingStudentSubscription.setIdStudent(student.getIdStudent());
				stagingStudentSubscription.setIdStudentOrder(studentOrder.getIdStudentOrder());
				stagingStudentSubscription.setLastPaymentDate(Instant.now());
				stagingStudentSubscription.setNextPaymentDate(extendedDate);
				stagingStudentSubscription.setOrderId(studentOrder.getOrderId());
				stagingStudentSubscription.setPaymentMode("Mannually Created");
				stagingStudentSubscription.setPaymentStatus("Success");
				stagingStudentSubscription.setPurchaseAmount(String.valueOf(amount));
				stagingStudentSubscription.setPurchaseDate(Instant.now());
				stagingStudentSubscription.setPurchaseLevel(purchaseType);
				stagingStudentSubscription.setPurchaseType("NEW0");
				stagingStudentSubscription.setSubscriptionEndDate(extendedDate);
				stagingStudentSubscription.setSubscriptionType(planType);
				stagingStudentSubscription.setTransactionAmount(amount);
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

				studentSubscription = studentSubscriptionRepository.save(stuSubscription);

				mannualUpdateDetailsDTO.setClassStandard(student.getIdClassStandard());
				mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
				mannualUpdateDetailsDTO.setLastSubscriptionEndDate(stuSubscription.getCreatedAt());
				mannualUpdateDetailsDTO.setMessage("Subscription created successfully with " + noOfMonth + " months .");
				mannualUpdateDetailsDTO.setName(user.getFirstName());
				mannualUpdateDetailsDTO.setNextSubscriptionEndDate(studentSubscription.getSubscriptionEndDate());
				mannualUpdateDetailsDTO.setNoOfMonths(noOfMonth);
				mannualUpdateDetailsDTO.setPhone(phoneNumber);
				mannualUpdateDetailsDTO.setPlan(amount);
				mannualUpdateDetailsDTO.setPlanType(purchaseType);
				mannualUpdateDetailsDTO.setType("Creation");
				mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());

				result.setData(mannualUpdateDetailsDTO);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Subscription created successfully.");
			}
		} catch (Exception e) {

			mannualUpdateDetailsDTO.setIsValid(false);
			mannualUpdateDetailsDTO.setMessage(e.getLocalizedMessage());
			result.setData(mannualUpdateDetailsDTO);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}
		return result;
	}

	public Document<?> downgradeMannualSubscription(String phoneNumber, Integer noOfMonth, Float amount, String purchaseType) {

		Document<MannualUpdateDetailsDTO> result = new Document<>();

		MannualUpdateDetailsDTO mannualUpdateDetailsDTO = new MannualUpdateDetailsDTO();

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			Integer months = noOfMonth;

			User user = userRepository.findByMobileNumber(phoneNumber);

			if (user == null)
				throw new AppException("User details not found for provided Phone number :" + phoneNumber + "");

			if (amount != 1999 && amount != 5999 && amount != 9999)
				throw new AppException("Currently only quarter annnual and enterprise plans are supported for bulk mannual Subsrcripiton");

			String planType = null;
			if (purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM")&&amount==1999) {
				planType="Annual";
			}else if(purchaseType.equalsIgnoreCase("AD_FREE_SUBSCRIPTION")&&amount==399){
				planType="Quarter";
			}else if (purchaseType.equalsIgnoreCase("ENP_AN")) {
				planType="Annual";
			} else if(purchaseType.equalsIgnoreCase("ENP_AN_PLUS")){
				planType="Annual";
			}else  {
				planType = "unknown";
			}

			Student student = studentRepository.findByUser(user);

			if (student == null)
				throw new AppException(
						"Invalid Student information found for the userSurId: " + user.getUserSurId() + " .");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findFirstByIdStudentAndIdProductAndActiveFlag(student.getIdStudent(), 93L, true);
	
				

			if (studentSubscription == null) {
				mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
				mannualUpdateDetailsDTO.setName(user.getFirstName());
				mannualUpdateDetailsDTO.setPhone(phoneNumber);
				mannualUpdateDetailsDTO.setPlan(amount);
				mannualUpdateDetailsDTO.setPlanType(planType);
				mannualUpdateDetailsDTO.setIsValid(false);
				mannualUpdateDetailsDTO.setNoOfMonths(months);
				mannualUpdateDetailsDTO.setType("downgrade");
				mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
				mannualUpdateDetailsDTO
						.setMessage("Subscription cannot be downgraded since the subsciption doesnot exists.");
				result.setData(mannualUpdateDetailsDTO);
				return result;
			} else {

				mannualUpdateDetailsDTO.setLastSubscriptionEndDate(studentSubscription.getCreatedAt());

				if (noOfMonth > 12 || noOfMonth < 0) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(months);
					mannualUpdateDetailsDTO.setType("downgrade");
					mannualUpdateDetailsDTO.setIsSubscriptionExists(true);
					mannualUpdateDetailsDTO
							.setMessage(" Number of months cannot exceed 12 months or lesser than 0 months");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}		
				
				if(studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN") || 
						studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(months);
					mannualUpdateDetailsDTO.setType("downgrade");
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO
							.setMessage("Enterprise Subscription Plan cannot be downgraded .");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}

				// 12 has been hard coded
				noOfMonth = 12 - noOfMonth;

				LocalDate dateExist = studentSubscription.getCreatedAt().atZone(zoneIndia).toLocalDate()
						.plusMonths(noOfMonth);

				Instant downgradedDate = dateExist.atStartOfDay(zoneIndia).toInstant();
				

				StudentOrder studentOrder = studentOrderRepository
						.findByIdStudentOrder(studentSubscription.getIdStudentOrder());

				if (studentOrder == null) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(planType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(months);
					mannualUpdateDetailsDTO.setType("downgrade");
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO
							.setMessage("Subscription cannot be downgraded since the student order doesnot exists.");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}
				studentOrder.setRemarks(" Bulk Subscription Created: User id:" + userPrincipal.getUserSurId()
						+ " User Name:" + userPrincipal.getUsername() + " at "
						+ Instant.now() + " for Subscription Type:" + "Downgrade");
				studentOrder.setUpdatedAt(Instant.now());
				studentOrder = studentOrderRepository.save(studentOrder);

				StagingStudentSubscription stagingStudentSubscription = stagingStudentSubscriptionRepository
						.getByIdStudentOrder(studentOrder.getIdStudentOrder());

				if (stagingStudentSubscription == null) {
					mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());
					mannualUpdateDetailsDTO.setName(user.getFirstName());
					mannualUpdateDetailsDTO.setPhone(phoneNumber);
					mannualUpdateDetailsDTO.setPlan(amount);
					mannualUpdateDetailsDTO.setPlanType(purchaseType);
					mannualUpdateDetailsDTO.setIsValid(false);
					mannualUpdateDetailsDTO.setNoOfMonths(months);
					mannualUpdateDetailsDTO.setIsSubscriptionExists(false);
					mannualUpdateDetailsDTO.setType("downgrade");
					mannualUpdateDetailsDTO.setMessage(
							"Subscription cannot be downgraded since the staging subsciption doesnot exists.");
					result.setData(mannualUpdateDetailsDTO);
					return result;
				}

				stagingStudentSubscription.setUpdatedAt(Instant.now());
				stagingStudentSubscription.setNextPaymentDate(downgradedDate);
				stagingStudentSubscription.setSubscriptionEndDate(downgradedDate);
				stagingStudentSubscription.setPaymentMode("Mannual");
				stagingStudentSubscription = stagingStudentSubscriptionRepository.save(stagingStudentSubscription);

				studentSubscription.setUpdatedAt(Instant.now());
				studentSubscription.setSubscriptionEndDate(downgradedDate);
				studentSubscription.setNextPaymentDate(downgradedDate);
				studentSubscription = studentSubscriptionRepository.save(studentSubscription);

				mannualUpdateDetailsDTO.setClassStandard(student.getIdClassStandard());
				mannualUpdateDetailsDTO.setIsSubscriptionExists(true);

				mannualUpdateDetailsDTO.setMessage("Subscription downgraded successfully with " + months + " months .");
				mannualUpdateDetailsDTO.setName(user.getFirstName());
				mannualUpdateDetailsDTO.setNextSubscriptionEndDate(studentSubscription.getSubscriptionEndDate());
				mannualUpdateDetailsDTO.setNoOfMonths(months);
				mannualUpdateDetailsDTO.setPhone(phoneNumber);
				mannualUpdateDetailsDTO.setPlan(amount);
				mannualUpdateDetailsDTO.setPlanType(planType);
				mannualUpdateDetailsDTO.setType("downgrade");
				mannualUpdateDetailsDTO.setUserSurId(user.getUserSurId());

				result.setData(mannualUpdateDetailsDTO);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Subscription downgraded successfully.");
			}
		} catch (Exception e) {

			mannualUpdateDetailsDTO.setIsValid(false);
			mannualUpdateDetailsDTO.setMessage(e.getLocalizedMessage());
			result.setData(mannualUpdateDetailsDTO);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	@Override
	public Document<?> validateUploadBatchMannualSubscription(MultipartFile batchFile) {

		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<String> errorLogList = new ArrayList<String>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {

			errorLogList.add("Manual Subscription verification Batch Process started at: " + Instant.now());

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

			// read CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			readAll.forEach(o -> {

				@SuppressWarnings("unchecked")
				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("phone_number")) {

					errorLogList.add(
							"Missing column header 'phone_number'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'phone_number'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("offer_given")) {
					errorLogList.add(
							"Missing column header 'offer_given'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'offer_given'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("plan")) {
					errorLogList
							.add("Missing column header 'plan'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header  'plan'. Please make sure you are using the correct template.\"");
				}

				if (!header.containsKey("type")) {
					errorLogList
							.add("Missing column header 'type'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'type'. Please make sure you are using the correct template.");
				}
				
				if (!header.containsKey("purchase_type")) {
					errorLogList
							.add("Missing column header 'purchase_type'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'purchase_type'. Please make sure you are using the correct template.");
				}
				

				if (!header.containsKey("phone_number") || !header.containsKey("offer_given")
						|| !header.containsKey("plan") || !header.containsKey("type")
						|| !header.containsKey("purchase_type")) {
					throw new AppException("Invalid file or missing header in the uploade csv.");
				}
			});

			List<MannualUpdateDetailsDTO> subscrMap = new ArrayList<>();

			List<MannualSubscriptionDTO> subscriptionList = new ArrayList<>();

			int totalRecords = 0;

			Iterator<Object> readAllIterator = readAll.listIterator();

			while (readAllIterator.hasNext()) {

				MannualUpdateDetailsDTO subscr = new MannualUpdateDetailsDTO();

				@SuppressWarnings("unchecked")
				Map<String, String> row = (Map<String, String>) readAllIterator.next();

				totalRecords++;

				if (row.get("phone_number").isEmpty()) {

					subscr.setIsValid(false);
					subscr.setMessage(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					errorLogList.add(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					subscrMap.add(subscr);
					continue;
				}

				MannualSubscriptionDTO subscription = new MannualSubscriptionDTO();
				subscription.setPhoneNumber(row.get("phone_number").trim());
				subscription.setOfferGiven(Integer.parseInt(row.get("offer_given").trim()));
				subscription.setPlan(Float.parseFloat(row.get("plan").trim()));
				subscription.setType(row.get("type").trim());
				subscription.setPlanType(row.get("purchase_type").trim());

				subscriptionList.add(subscription);

				subscr = verifySubscriptionConstraints(subscription.getPhoneNumber(), subscription.getOfferGiven(),
						subscription.getPlan(), subscription.getType(),subscription.getPlanType());

				subscrMap.add(subscr);
			}

			int sucessfullCount = (int) subscrMap.stream().filter(s -> s.getisValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

			errorLogList.add("Total no of records : " + totalRecords + ".");
			errorLogList.add("Total no of records valid: " + sucessfullCount + ".");
			errorLogList.add("Total no of records invalid: " + rejectedRecords + ".");
			errorLogList.add("Manual Subscription verification Batch Process ended at: " + Instant.now());

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

			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			boolean isDeletedFile = file.delete();
			LOGGER.info("Log file deleted from the system : " +isDeletedFile );
		}
		return result;
	}
	
	public MannualUpdateDetailsDTO verifySubscriptionConstraints(String phoneNumber, Integer noOfMonth, Float amount,
			String type, String purchaseType) {
		MannualUpdateDetailsDTO result = new MannualUpdateDetailsDTO();
		try {
			Integer months = noOfMonth;

			// Find the user with the given phone number

			if (!type.equalsIgnoreCase("upgrade") && !type.equalsIgnoreCase("downgrade")) {
				throw new AppException("invalid type found, Either it has to be upgrade or downgrade.");
			}

			User user = userRepository.findByMobileNumber(phoneNumber);

			if (user == null)
				throw new AppException("User not found for the given phone number " + phoneNumber);

			String plan = null;

			if (amount != 1999 && amount != 5999 && amount != 9999 ) {
				throw new AppException("Currently only  1999, 5999 and 9999 plan are supported for bulk mannual Subsrcripiton");
			}
			if (!purchaseType.equalsIgnoreCase("AD_FREE_SUBSCRIPTION") && !purchaseType.equalsIgnoreCase("ENP_AN")
					&& !purchaseType.equalsIgnoreCase("ENP_AN_PLUS") ) {
				throw new AppException("invalid purchase type : "+purchaseType);
			}

		
			if (purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM")&&amount==1999) {
				plan="ANNUAL";
				amount = 1999f;
			}else if(purchaseType.equalsIgnoreCase("AD_FREE_SUBSCRIPTION")&&amount==399){
				plan="QUARTER";
				amount= 399f;
			}else if (purchaseType.equalsIgnoreCase("ENP_AN")) {
				amount=5999f;
				plan="Enterprise Plan";
			} else if(purchaseType.equalsIgnoreCase("ENP_AN_PLUS")){
				amount=9999f;
				plan="Enterprise Plus Plan";
			}else  {
				plan = "unknown";
				LOGGER.error(plan);
			}
			
			Instant nextSubscriptionDate = null;

			if (type.equalsIgnoreCase("upgrade")) {
				LocalDate subscriptionDate = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(noOfMonth);
				nextSubscriptionDate = subscriptionDate.atStartOfDay(zoneIndia).toInstant();
				result.setMessage("Subscription will be created with " + noOfMonth + " months .");
			} else if (type.equalsIgnoreCase("downgrade")) {
				nextSubscriptionDate = null;
				result.setIsValid(false);
				result.setMessage("Subscription cannot be downgraded since the subsciption doesnot exists.");
			}

			result.setUserSurId(user.getUserSurId());
			result.setName(user.getFirstName());
			result.setPhone(user.getMobileNumber());
			result.setPlan(amount);
			result.setNoOfMonths(months);
			result.setClassStandard(user.getClassStandard());
			result.setPlanType(purchaseType);
			result.setIsSubscriptionExists(false);

			Student student = studentRepository.findByUser(user);

			if (student == null)
				throw new AppException(
						"Invalid Student information found for the userSurId: " + user.getUserSurId() + " .");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findFirstByIdStudentAndIdproductLineAndActiveFlag(student.getIdStudent(), 11L, true);

			if (studentSubscription != null) {

				result.setLastSubscriptionEndDate(studentSubscription.getCreatedAt());

				if (type.equalsIgnoreCase("upgrade")) {
					if(((studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN") || 
							studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) && purchaseType.equalsIgnoreCase("ANNUAL_PREMIUM")) ||
							(studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) && purchaseType.equalsIgnoreCase("ENP_AN")) {
						
						result.setIsValid(false);
						result.setMessage("Enterprise to premium subscription plan upgradation not possible.");

						return result;
					}

					LocalDate subscriptionDate = studentSubscription.getCreatedAt().atZone(zoneIndia).toLocalDate()
							.plusMonths(months);

					nextSubscriptionDate = subscriptionDate.atStartOfDay(zoneIndia).toInstant();

					result.setNextSubscriptionEndDate(nextSubscriptionDate);
					result.setMessage("Subscription will be upgraded with " + months + " months.");

					result.setType("upgrade");

				} else {

					if (months > 12 || months < 0) {
						result.setIsValid(false);
						result.setMessage(" Number of months cannot exceed 12 months or lesser than 0 months");
					} else {
						if(studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN") || 
								studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS")) {

							result.setIsValid(false);
							result.setMessage("Enterprise Subscription cannot be be downgraded  " );
		
							return result;
						}

						// 12 has been hard coded for annual
						noOfMonth = 12 - noOfMonth;

						result.setIsValid(true);

						LocalDate subscriptionDate = studentSubscription.getCreatedAt().atZone(zoneIndia).toLocalDate()
								.plusMonths(noOfMonth);

						nextSubscriptionDate = subscriptionDate.atStartOfDay(zoneIndia).toInstant();

						result.setNextSubscriptionEndDate(nextSubscriptionDate);
						result.setType("downgrade");

						result.setMessage("Subscription will be downgraded with " + months + " months.");

					}

				}

				result.setIsSubscriptionExists(true);

			} else {

				result.setIsSubscriptionExists(false);

				if (type.equalsIgnoreCase("upgrade")) {
					LocalDate subscriptionDate = Instant.now().atZone(zoneIndia).toLocalDate().plusMonths(months);
					result.setLastSubscriptionEndDate(Instant.now());
					nextSubscriptionDate = subscriptionDate.atStartOfDay(zoneIndia).toInstant();
					result.setNextSubscriptionEndDate(nextSubscriptionDate);
					result.setIsValid(true);
					result.setType("creation");
					result.setMessage("Subscription will be created with a validty of  " + noOfMonth + " months.");
				}

				else
					throw new AppException("User cannot downgrade , Since no active subscription found.");

			}

		} catch (Exception e) {

			result.setIsValid(false);
			result.setMessage(e.getLocalizedMessage());

		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> uploadBatchMannualSubscription(MultipartFile batchFile) {

		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<String> errorLogList = new ArrayList<String>();

		File file = fileUploadService.convertMultiPartFileToFile(batchFile);

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// check logged in user accessing
			if (!(authentication instanceof AnonymousAuthenticationToken)) {

				userPrincipal = (UserPrincipal) authentication.getPrincipal();

				if (userPrincipal == null)
					throw new AppException("Invalid User");
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

			// read CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			readAll.forEach(o -> {

				Map<String, String> header = (Map<String, String>) o;

				if (!header.containsKey("phone_number")) {
					errorLogList
							.add("Missing column 'phone_number'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column 'phone_number'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("offer_given")) {
					errorLogList
							.add("Missing column 'offer_given'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column 'offer_given'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("plan")) {
					errorLogList.add("Missing column 'plan'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column 'plan'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("type")) {
					errorLogList.add("Missing column 'type'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column 'type'. Please make sure you are using the correct template.");
				}
				if (!header.containsKey("purchase_type")) {
					errorLogList
							.add("Missing column header 'purchase_type'. Please make sure you are using the correct template.");
					throw new AppException(
							"Missing column header 'purchase_type'. Please make sure you are using the correct template.");
				}

				if (!header.containsKey("phone_number") || !header.containsKey("offer_given")
						|| !header.containsKey("plan") || !header.containsKey("type")
						|| !header.containsKey("purchase_type")) {
					throw new AppException("Invalid file or missing header in the uploade csv.");
				}

			});

			List<MannualUpdateDetailsDTO> subscrMap = new ArrayList<>();
			List<MannualSubscriptionDTO> subscriptionList = new ArrayList<>();
			Iterator<Object> it = readAll.listIterator();

			int totalRecords = 0;
			while (it.hasNext()) {

				Document<MannualUpdateDetailsDTO> subscr = new Document<>();

				Map<String, String> row = (Map<String, String>) it.next();

				totalRecords++;

				if (row.get("phone_number").isEmpty()) {

					MannualUpdateDetailsDTO mpuDetailsDTO = new MannualUpdateDetailsDTO();
					mpuDetailsDTO.setIsValid(false);
					mpuDetailsDTO.setMessage(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					errorLogList.add(
							"Missing 'phone_number' on  row: " + (totalRecords) + ",Please add a valid  phone number.");
					subscrMap.add(mpuDetailsDTO);
					continue;
				}

				MannualSubscriptionDTO subscription = new MannualSubscriptionDTO();
				subscription.setPhoneNumber(row.get("phone_number").trim());
				subscription.setOfferGiven(Integer.parseInt(row.get("offer_given").trim()));
				subscription.setPlan(Float.parseFloat(row.get("plan").trim()));
				subscription.setType(row.get("type").trim());
				subscription.setPlanType(row.get("purchase_type").trim());

				subscriptionList.add(subscription);
				
					if (subscription.getType().equalsIgnoreCase("upgrade")) {
						subscr = (Document<MannualUpdateDetailsDTO>) upgradeManualSubscription(
							subscription.getPhoneNumber(), subscription.getOfferGiven(), subscription.getPlan(),subscription.getPlanType());

					} else if (subscription.getType().equalsIgnoreCase("downgrade")) {
						subscr = (Document<MannualUpdateDetailsDTO>) downgradeMannualSubscription(
							subscription.getPhoneNumber(), subscription.getOfferGiven(), subscription.getPlan(),subscription.getPlanType());
					} else {

						errorLogList.add("invalid type found, Either it has to be upgrade or downgrade.");
						continue;
				
					}
				

				if (!subscr.getData().getisValid()) {

					errorLogList.add(subscr.getData().getMessage());
				} else {

					errorLogList.add("subscription has been " + subscription.getType()
							+ "d for the user having phone number " + subscription.getPhoneNumber() + " for "
							+ subscription.getOfferGiven() + " months");
				}
				subscrMap.add(subscr.getData());

			}
			int sucessfullCount = (int) subscrMap.stream().filter(s -> s.getisValid()).count();

			int rejectedRecords = totalRecords - sucessfullCount;

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
			result.setData(dataMap);
			result.setMessage(e.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			
			
			String fileName = "MannualSubscriptionBatchUploadLog" + Instant.now() + ".txt";


			try(FileWriter writer = new FileWriter(fileName)) {

			
				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				
				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/subscription/", fileName, logFile);

				
				boolean isDeletedFile1 = logFile.delete();
				LOGGER.info("Log file deleted from the system : " +isDeletedFile1 );
				boolean isDeletedFile2 = file.delete();
				LOGGER.info("file deleted from the system : " +isDeletedFile2 );
				
				

			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	@Async
	@Override
	public void paymentPendingStatusUpdate() {

		List<String> infoList = new ArrayList<String>();

		int unUpdatedOrders = 0;
		int successOrders = 0;
		int failedOrders = 0;
		try {
			Instant startTime, endTime;

			startTime = Instant.now();
			infoList.add("Scheduler running to update pending order status  at :" + " " + startTime + " on "
					+ LocalDate.now());

			List<StudentOrder> pendingstatusList = studentOrderRepository.getAllPendingStatusPayments();

			if (pendingstatusList.isEmpty()) {
				infoList.add("No Pending  Student Orders found.");
			} else {

				for (StudentOrder pendingStudentOrder : pendingstatusList) {

					try {
						Map<String, Map<String, Object>> paytmResponse = paytmPaymentConfig
								.paymentPendingStatusCheck(pendingStudentOrder.getOrderId());

						Map<String, Object> body = paytmResponse.get("body");
						Map<String, String> resultInfo = (Map<String, String>) body.get("resultInfo");

						if (resultInfo.get("resultStatus").equals("TXN_SUCCESS")
								|| resultInfo.get("resultCode").equalsIgnoreCase("01")) {

							StudentSubscription subscription = studentSubscriptionRepository
									.findByIdProductAndUserSurIdAndActiveFlag(93L, pendingStudentOrder.getUserSurId(),
											Boolean.TRUE);

							if (subscription != null) {
								pendingStudentOrder.setRemarks(
										"A subscription has already been created for this user on the subscription id:"
												+ subscription.getIdStudentSubscription() + ".");
								pendingStudentOrder.setOrderStatus("Success");
								studentOrderRepository.save(pendingStudentOrder);
								infoList.add("orderId:" + pendingStudentOrder.getOrderId()
										+ "  A subscription has already been created for this user on the subscription id:"
										+ subscription.getIdStudentSubscription() + ".");
							} else {

								String paymentMode = String.valueOf(body.get("paymentMode"));
								// set payment mode
								if (paymentMode != null) {
									paymentMode = paymentMode.equals("DC") ? "Debit Card"
											: paymentMode.equals("CC") ? "Credit Card"
													: paymentMode.equals("NB") ? "Net Banking"
															: paymentMode.equals("PPI") ? "Paytm Wallet" : paymentMode;
								}

								// update student order data
								StudentOrder setStudentOrder = studentOrderRepository
										.findByOrderId(pendingStudentOrder.getOrderId());

								if (setStudentOrder != null) {
									if (setStudentOrder.getSecondaryStatus() != null
											&& resultInfo.get("resultStatus").equals("TXN_SUCCESS")) {
										setStudentOrder.setDisputeFlag(Boolean.TRUE);
									} else {
										setStudentOrder.setDisputeFlag(Boolean.FALSE);
									}
									// update student order table status
									setStudentOrder.setRemarks(
											"The subscription has been successfully created by the system, as the payment response confirms a successful transaction.");
									setStudentOrder.setOrderStatus("Success");
									setStudentOrder = studentOrderRepository.save(setStudentOrder);

									infoList.add("orderId:" + pendingStudentOrder.getOrderId()
											+ " Payment Status is Updated as 'Success' in  Student Order Table ");

									// delete cart items if payment is successful
									if (!resultInfo.get("resultCode").equals("141") && setStudentOrder != null) {
										List<UserCart> list = userCartRepository
												.findByIdStudentOrder(setStudentOrder.getIdStudentOrder());
										if (list.size() != 0) {
											userCartRepository.deleteInBatch(list);
										}
									}
								}

								// save response to staging student subscription and student subscription
								List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
										.findByOrderId(pendingStudentOrder.getOrderId());

								Set<String> regDeviceIdSet = new HashSet<String>();
								List<String> regDeviceIdList = new ArrayList<String>();
								String studentName = "";
								String orderId = "";

								for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
									if (stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
										stagingStudentSubscriptionUpdate.setLastPaymentDate(
												stagingStudentSubscriptionUpdate.getLastPaymentDate());
									} else {
										stagingStudentSubscriptionUpdate.setLastPaymentDate(Instant.now());
									}
									if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("MONTH")) {
										LocalDateTime dateMonth = stagingStudentSubscriptionUpdate.getLastPaymentDate()
												.atZone(zoneIndia).toLocalDateTime().plusMonths(1);
										stagingStudentSubscriptionUpdate.setNextPaymentDate(
												dateMonth.atZone(ZoneId.systemDefault()).toInstant());
									} else if (stagingStudentSubscriptionUpdate.getSubscriptionType()
											.equals("QUARTER")) {
										LocalDateTime dateQuarter = stagingStudentSubscriptionUpdate
												.getLastPaymentDate().atZone(zoneIndia).toLocalDateTime().plusMonths(3);
										stagingStudentSubscriptionUpdate.setNextPaymentDate(
												dateQuarter.atZone(ZoneId.systemDefault()).toInstant());
									} else if (stagingStudentSubscriptionUpdate.getSubscriptionType()
											.equals("ANNUAL")) {
										LocalDateTime dateYear = stagingStudentSubscriptionUpdate.getLastPaymentDate()
												.atZone(zoneIndia).toLocalDateTime().plusMonths(12);
										stagingStudentSubscriptionUpdate.setNextPaymentDate(
												dateYear.atZone(ZoneId.systemDefault()).toInstant());
									}
									LocalDateTime gracePeriodDate = stagingStudentSubscriptionUpdate
											.getNextPaymentDate().atZone(zoneIndia).toLocalDateTime()
											.plusDays(gracePeriod);
									if (stagingStudentSubscriptionUpdate.getPurchaseLevel().equals("AD_FREE_SUBSCRIPTION")) {
										stagingStudentSubscriptionUpdate.setSubscriptionEndDate(
												stagingStudentSubscriptionUpdate.getNextPaymentDate());
									} else {
										stagingStudentSubscriptionUpdate.setSubscriptionEndDate(
												gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
									}
									stagingStudentSubscriptionUpdate.setBankName(String.valueOf(body.get("bankName")));
									stagingStudentSubscriptionUpdate
											.setBankTransactionId(String.valueOf(body.get("bankTxnId")));
									stagingStudentSubscriptionUpdate.setOrderId(String.valueOf(body.get("orderId")));
									stagingStudentSubscriptionUpdate
											.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
									stagingStudentSubscriptionUpdate.setPaymentMode(paymentMode);
									stagingStudentSubscriptionUpdate.setPaymentStatus("Success");
									stagingStudentSubscriptionUpdate.setTransactionAmount(
											Float.parseFloat(String.valueOf(body.get("txnAmount"))));
									stagingStudentSubscriptionUpdate
											.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
									stagingStudentSubscriptionUpdate.setPurchaseDate(Instant.now());
									stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
											.save(stagingStudentSubscriptionUpdate);
									infoList.add("orderId:" + pendingStudentOrder.getOrderId()
											+ " Payment Status is Updated as 'Success' in Staging Student Subcription Table ");
									if (resultInfo.get("resultStatus").equals("TXN_SUCCESS")
											&& !setStudentOrder.getDisputeFlag()) {
										StudentSubscription studentSubscription = null;
										if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() == null
												|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("NEW")) {
											studentSubscription = new StudentSubscription();
										} else if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() != null
												|| stagingStudentSubscriptionUpdate.getPurchaseType()
														.equals("RENEWAL")) {
											studentSubscription = studentSubscriptionRepository
													.findByIdStudentSubscription(stagingStudentSubscriptionUpdate
															.getIdStudentSubscription());
										}
										studentSubscription
												.setIdProduct(stagingStudentSubscriptionUpdate.getIdProduct());
										studentSubscription.setIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
										studentSubscription.setIdProductGroup(
												stagingStudentSubscriptionUpdate.getIdProductGroup());
										studentSubscription
												.setIdproductLine(stagingStudentSubscriptionUpdate.getIdproductLine());
										studentSubscription
												.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
										studentSubscription.setIdStudentOrder(
												stagingStudentSubscriptionUpdate.getIdStudentOrder());
										studentSubscription.setLastPaymentDate(
												stagingStudentSubscriptionUpdate.getLastPaymentDate());
										studentSubscription.setNextPaymentDate(
												stagingStudentSubscriptionUpdate.getNextPaymentDate());
										studentSubscription.setPurchaseAmount(
												stagingStudentSubscriptionUpdate.getPurchaseAmount());
										studentSubscription
												.setPurchaseDate(stagingStudentSubscriptionUpdate.getPurchaseDate());
										studentSubscription
												.setPurchaseLevel(stagingStudentSubscriptionUpdate.getPurchaseLevel());
										studentSubscription.setSubscriptionEndDate(
												stagingStudentSubscriptionUpdate.getSubscriptionEndDate());
										studentSubscription.setSubscriptionType(
												stagingStudentSubscriptionUpdate.getSubscriptionType());
										studentSubscription
												.setPurchaseType(stagingStudentSubscriptionUpdate.getPurchaseType());
										studentSubscription.setActiveFlag(Boolean.TRUE);
										studentSubscription.setFreeFlag(Boolean.FALSE);
										studentSubscription
												.setUserSurId(stagingStudentSubscriptionUpdate.getUserSurId());
										studentSubscription.setIdSpecialOffer(
												stagingStudentSubscriptionUpdate.getIdSpecialOffer());
										studentSubscription.setSpecialOfferFlag(
												stagingStudentSubscriptionUpdate.getSpecialOfferFlag());
										StudentSubscription ss = studentSubscriptionRepository
												.findByIdBatchAndIdProductAndIdStudentOrderAndUserSurId(
														stagingStudentSubscriptionUpdate.getIdBatch(),
														stagingStudentSubscriptionUpdate.getIdProduct(),
														stagingStudentSubscriptionUpdate.getIdStudentOrder(),
														stagingStudentSubscriptionUpdate.getUserSurId());
										if (ss == null) {
											studentSubscription = studentSubscriptionRepository
													.save(studentSubscription);
											infoList.add("Subscription  created for this orderId:"
													+ pendingStudentOrder.getOrderId());
										} else {
											studentSubscription = null;
										}

										// orderId = studentSubscription.get;
										if (studentSubscription != null) {
											// adding payment details into SubscriptionPaymentHistory table
											Date paymentDate = Date
													.from(stagingStudentSubscriptionUpdate.getPurchaseDate());
											SubscriptionPaymentHistory subscriptionPaymentHistory = new SubscriptionPaymentHistory(
													studentSubscription.getIdStudentSubscription(),
													stagingStudentSubscriptionUpdate.getIdStudentOrder(), paymentDate,
													Float.valueOf(
															stagingStudentSubscriptionUpdate.getPurchaseAmount()));
											subscriptionPaymentHistoryRepository.save(subscriptionPaymentHistory);
											infoList.add(
													"Added payment details into Subscription PaymentHistory table orderId:"
															+ pendingStudentOrder.getOrderId());
											// calling getVideoRecordByIdProduct for StudentAssignedCourse
											if (studentSubscription.getPurchaseType().equals("NEW")) {
												offlineCourseService.getVideoRecordByIdProduct(studentSubscription);
											}
											orderId = stagingStudentSubscriptionUpdate.getOrderId();
											System.out.println("Studentid " + studentSubscription.getIdStudent());
											Student student = studentRepository
													.findByIdStudent(studentSubscription.getIdStudent());
											studentName = student.getUser().getFirstName();
											if (student != null) {
												Long userSurId = student.getUser().getUserSurId();
												System.out.println("userSurid " + student.getUser().getUserSurId());
												UserDevice userDevice = userDeviceRepository
														.findByUserSurIdAndDeviceType(userSurId, "MOBILE");
												System.out.println("userDevice " + userDevice);
												if (userDevice != null) {
													System.out.println("DeviceId " + userDevice.getDeviceId());
													regDeviceIdSet.add(userDevice.getDeviceId());
													// regDeviceIdList.add(userDevice.getDeviceId());
												}
											}
										}
									}
								}
								if (!regDeviceIdSet.isEmpty()) {
									System.out.println("orderId " + orderId);
									regDeviceIdList.addAll(regDeviceIdSet);
									System.out.println("regDeviceIdList " + regDeviceIdList);
									String message = "Dear " + studentName
											+ " your payment received successfully for orderId " + orderId;
									notificationService.sendNotification(regDeviceIdList, message, "order");
								}
								successOrders++;

							}
						} // resultStatus success check if block
							// if resultStatus "TXN_FAILURE" enters into else block
						else {

							// update student order data
							StudentOrder setStudentOrder = studentOrderRepository
									.findByOrderId(pendingStudentOrder.getOrderId());

							if (setStudentOrder != null
									&& setStudentOrder.getOrderStatus().equalsIgnoreCase("Pending")) {
								// update student order table status
								setStudentOrder.setOrderStatus("Failed");
								setStudentOrder.setRemarks(
										"The System colud not create subscription due to a failed payment transaction.");
								setStudentOrder = studentOrderRepository.save(setStudentOrder);
								infoList.add("orderId:" + setStudentOrder.getOrderId()
										+ " Order Status is Updated as 'Failed' in Student Order Table  ");
							} else {
								infoList.add("No Record found for this Student Order" + "orderId:"
										+ setStudentOrder.getOrderId());
								continue;
							}
							// save response to staging student subscription and student subscription
							List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
									.findByOrderId(pendingStudentOrder.getOrderId());

							if (stagingStudentSubscription.isEmpty()) {

								infoList.add("No Staging Records found for this Student Order" + "orderId:"
										+ setStudentOrder.getOrderId());
								continue;
							} else {
								for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
									stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
									stagingStudentSubscriptionUpdate
											.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
									stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
											.save(stagingStudentSubscriptionUpdate);
									infoList.add("orderId:" + stagingStudentSubscriptionUpdate.getOrderId()
											+ " Payment Status is Updated as 'Failed' in Staging Student Subcription Table ");

									if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
										Batch batchEdit = batchRepository
												.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
										if (batchEdit != null) {
											batchEdit.setPaymentStatus(null);
											batchRepository.save(batchEdit);
										}
									}
								}
							}
							failedOrders++;

						}

					} catch (Exception e) {
						infoList.add("Erro occured on while updating orderid: " + pendingStudentOrder.getOrderId());
						infoList.add(e.getLocalizedMessage());
						infoList.add("------------ skipping the above Order due to error -------------");
						unUpdatedOrders++;
						continue;
					}

				}
				// for loop end

				infoList.add("Total number of pending student orders:" + pendingstatusList.size() + " .");
				// infoList.add("Total number of pending orders updated : " +
				// (pendingstatusList.size() - unUpdatedOrders)
				// + ".");
				infoList.add("Total number of failure occured during updation: " + unUpdatedOrders + ".");
				infoList.add("Total number of Success Orders Updated: " + successOrders + ".");
				infoList.add("Total number of Failed Orders Updated: " + failedOrders + ".");

			} // else
			endTime = Instant.now();

			infoList.add("Payment Pending Order status update completed!");
			infoList.add("Elapsed duration: " + (endTime.getEpochSecond() - startTime.getEpochSecond()) + " sec.");
		} catch (Exception e) {
			infoList.add("scheduler failed abnormally at: " + Instant.now());
			infoList.add(e.getLocalizedMessage());

		} finally {

			// log the file to s3 location
			LocalDateTime date = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyhhmmss");
			String datetime = formatter.format(date);
			String fileName = "PendingOrderStatus_" + datetime + ".txt";
			System.out.println(fileName);

			
			try(FileWriter writer = new FileWriter(fileName)) {
				
				for (String str : infoList) {
					writer.write(str + System.lineSeparator());
				}

				File file = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/scheduler/payment-pending-status", fileName, file);

				boolean isDeletedFile = file.delete();
				LOGGER.info("Log file deleted from the system : " +isDeletedFile );

			} catch (IOException e) {
				
				LOGGER.error(e.getMessage());

			} // catch

		} // finally

	}// method end

	/**
	 * Retrieves a paginated list of student subscription information based on the
	 * provided filters and sorting options.
	 * 
	 * @author Abdul Elahi
	 *
	 * @param infoFilterDTO An object containing filtering criteria for subscription
	 *                      information.
	 * @param sort          The sorting option for the results. "latest" for
	 *                      descending, "old" for ascending.
	 * @param pageNumber    The page number for pagination.
	 * @param pageSize      The number of items per page.
	 * @return A Document containing a paginated list of SubscriptionInfoDTO
	 *         objects.
	 * @throws AppException if there's an issue while retrieving subscription
	 *                      information or processing data.
	 */
	@Override
	public Document<Page<SubscriptionInfoDTO>> getStudentSubcriptionInfo(InfoFilterDTO infoFilterDTO, String sort,
			int pageNumber, int pageSize) {

		Document<Page<SubscriptionInfoDTO>> result = new Document<>();

		sort = sort.equalsIgnoreCase("latest") ? "latest" : "old";

		Page<SubscriptionInfoDTO> page = null;

		Page<StudentSubscription> studentSubscriptionList = null;

		List<Boolean> statusList = infoFilterDTO.getSubscribedStatus() == null ? null
				: ((infoFilterDTO.getSubscribedStatus().isEmpty()) ? null : infoFilterDTO.getSubscribedStatus());

		Pageable paging = PageRequest.of(pageNumber, pageSize);

		try {

			if (infoFilterDTO.getEmail() == null && infoFilterDTO.getMobile() == null && statusList == null
					&& infoFilterDTO.getUserSurId() == null && infoFilterDTO.getToDate() == null
					&& infoFilterDTO.getFromDate() == null && infoFilterDTO.getSubscriptionMode() == null) {

				studentSubscriptionList = studentSubscriptionRepository
						.findAllByOrderByIdStudentSubscriptionDesc(paging);

			} else if (infoFilterDTO.getSubscriptionMode() == null) { 
				
				studentSubscriptionList =	studentSubscriptionRepository.getAllStudentSubscriptionInfoBasedOnSubscriptionModeNull(infoFilterDTO.getUserSurId(),
						infoFilterDTO.getMobile(), infoFilterDTO.getEmail(), statusList,
						infoFilterDTO.getFromDate(), infoFilterDTO.getToDate(), paging);
			}

			else {
				if (sort.equalsIgnoreCase("latest")) {

					studentSubscriptionList = studentSubscriptionRepository
							.getAllStudentSubscriptionInfoBasedOnParamDesc(infoFilterDTO.getUserSurId(),
									infoFilterDTO.getMobile(), infoFilterDTO.getEmail(), statusList,
									infoFilterDTO.getSubscriptionMode(), infoFilterDTO.getFromDate(),
									infoFilterDTO.getToDate(), paging);

//					studentSubscriptionList = studentSubscriptionList.stream()
//							.filter(distinctByKey(subscription -> subscription.getUserSurId()))
//							.collect(Collectors.toList());
				} else {

					studentSubscriptionList = studentSubscriptionRepository
							.getAllStudentSubscriptionInfoBasedOnParamAsc(infoFilterDTO.getUserSurId(),
									infoFilterDTO.getMobile(), infoFilterDTO.getEmail(), statusList,
									infoFilterDTO.getSubscriptionMode(), infoFilterDTO.getFromDate(),
									infoFilterDTO.getToDate(), paging);
//
//					studentSubscriptionList = studentSubscriptionList.stream()
//							.filter(distinctByKey(subscription -> subscription.getUserSurId()))
//							.collect(Collectors.toList());

				}
			}

			List<SubscriptionInfoDTO> infoList = new ArrayList<SubscriptionInfoDTO>();

			if (pageNumber > 0 && studentSubscriptionList.isEmpty())
				throw new AppException("Sorry, no more details available.");

			if (studentSubscriptionList.isEmpty())
				throw new AppException("No details found.");

			for (StudentSubscription subscrInfo : studentSubscriptionList) {

				User user = userRepository.findByUserSurId(subscrInfo.getUserSurId());
				if (user == null)
					throw new NullPointerException("User details not found");

				Student student = studentRepository.findByUser_userSurId(user.getUserSurId());
				if (student == null)
					throw new NullPointerException("student details not found");

				Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
				State state = stateRepository.findByIdState(student.getIdState());
				ClassStandard classStandard = classRepository.findByIdClassStandard(student.getIdClassStandard());

				SubscriptionInfoDTO dto = new SubscriptionInfoDTO();
				dto.setUserSurId(user.getUserSurId());
				dto.setName(user.getFirstName());
				dto.setMobile(user.getMobileNumber());
				dto.setEmail(user.getEmail());
				dto.setClassStandard(classStandard.getClassStandadName());
				dto.setSyllabus(syllabus.getSyllabusName());
				dto.setState(state.getState());
				dto.setCreatedDate(subscrInfo.getCreatedAt());
				dto.setEndDate(subscrInfo.getSubscriptionEndDate());
				dto.setSubscriptionType(subscrInfo.getSubscriptionType());

				if (!subscrInfo.getActiveFlag()) {
					dto.setSubscriptionStatus("inactive");
					dto.setDaysPending(0);
				} else {
					Instant subscriptionEndDate = subscrInfo.getSubscriptionEndDate() == null ? null
							: subscrInfo.getSubscriptionEndDate();

					long noOfDays = ChronoUnit.DAYS.between(Instant.now(), subscriptionEndDate);
					dto.setSubscriptionStatus("active");
					dto.setDaysPending((int) (noOfDays > 0 ? noOfDays : 0));
				}

				infoList.add(dto);
			}

			page = new PageImpl<>(infoList, paging, studentSubscriptionList.getTotalElements());

			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully fetched student order details.");

			return result;
		} catch (Exception exp) {
		    LOGGER.error(exp.getLocalizedMessage());
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	/**
	 * This method is used to fetch paginated results of basic user information
	 * based on the provided filters and sorting options.
	 * 
	 * @author Abdul Elahi
	 * 
	 * @param infoFilterDTO An object containing filtering criteria for user
	 *                      information.
	 * @param sort          The sorting option for the results. "latest" for
	 *                      descending, "old" for ascending.
	 * @param pageNumber    The page number for pagination.
	 * @param pageSize      The number of items per page.
	 * @return A Document containing a paginated list of UserInfoDto objects
	 *         representing basic user information.
	 * @throws AppException if there's an issue while retrieving user information or
	 *                      processing data.
	 * @since [Date the method was first implemented]
	 */
	@Override
	public Document<?> getAllBasicUsers(InfoFilterDTO infoFilterDTO, String sort, int pageNumber, int pageSize) {
		Document<Page<UserInfoDto>> result = new Document<>();
		Page<User> userDetails = null;
		Page<UserInfoDto> page;
		Pageable paging = PageRequest.of(pageNumber, pageSize);

		try {
			if (sort.equalsIgnoreCase("latest")) {
				userDetails = studentSubscriptionRepository.getAllBasicUserAsAcs(infoFilterDTO.getUserSurId(),
						infoFilterDTO.getMobile(), infoFilterDTO.getEmail(), infoFilterDTO.getFromDate(),
						infoFilterDTO.getToDate(), paging);
			} else {
				userDetails = studentSubscriptionRepository.getAllBasicUserAsDesc(infoFilterDTO.getUserSurId(),
						infoFilterDTO.getMobile(), infoFilterDTO.getEmail(), infoFilterDTO.getFromDate(),
						infoFilterDTO.getToDate(), paging);
			}

			List<UserInfoDto> infoList = new ArrayList<>();
			for (User user : userDetails) {

				Student student = studentRepository.getStudentByUser_UserSurId(user.getUserSurId());
				if (student == null)
					continue;

				Syllabus syllabus = syllabusRepository.findByIdSyllabus(student.getIdSyllabus());
				State state = stateRepository.findByIdState(student.getIdState());
				ClassStandard classStandard = classRepository.findByIdClassStandard(student.getIdClassStandard());

				UserInfoDto dto = new UserInfoDto();
				dto.setUserSurId(student.getUser().getUserSurId());
				dto.setName(student.getUser().getFirstName());
				dto.setMobile(student.getUser().getMobileNumber());
				dto.setEmail(student.getUser().getEmail());
				dto.setClassStandard(classStandard.getClassStandadName());
				dto.setSyllabus(syllabus.getSyllabusName());
				dto.setState(state.getState());
				dto.setSubscriptionStatus("never Subscribed");
				dto.setCreatedDate(user.getCreatedAt());

				infoList.add(dto);
			}
			if (pageNumber > 0 && userDetails.isEmpty()) {
				throw new AppException("Sorry, no more details available.");
			}

			page = new PageImpl<>(infoList, paging, userDetails.getTotalElements());

			result.setData(page);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfully fetched");

		} catch (Exception e) {
		    LOGGER.error(e.getLocalizedMessage());
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Internal Server Error");
		}

		return result;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
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
				Product product = productRepository.findByIdProduct(studentSubscription.getIdProduct());

				if (product == null)
					throw new AppException("Invalid product information found in user subscription.");

				// two days before show renewal button
				LocalDate dateExist = studentSubscription.getSubscriptionEndDate().atZone(zoneIndia).toLocalDate();
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
					subscriptionDetails.put("productInfo", product);
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
					subscriptionDetails.put("productInfo", product);
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

	/**
	 * @author Abdul Elahi
	 * 
	 * @date 12/09/2023
	 * 
	 *       Upgrade a student's subscription plan.
	 *
	 *       This method allows upgrading a student's subscription plan based on the
	 *       provided plan type.
	 *
	 * @param userSurId The ID of the user whose subscription plan is being
	 *                  upgraded.
	 * @param planType  The type of plan to upgrade to. Supported plan types are: -
	 *                  "ENP_AN": Upgrade to Enterprise Plan with Annual
	 *                  Subscription. - "ENP_AN_PLUS": Upgrade to Enterprise Plus
	 *                  Plan with Annual Plus Subscription.
	 *
	 * @return A Document containing information about the upgraded
	 *         StudentSubscription and its status.
	 *
	 * @throws AppException If there is an issue with the upgrade process, such as
	 *                      an invalid plan type, an attempt to downgrade to
	 *                      "AD_FREE," or if the user does not exist. The exception
	 *                      message will provide more details about the error.
	 */
	@Override
	public Document<StudentSubscription> upgradePlan(Long idStudentOrder, String planType) {
		Document<StudentSubscription> result = new Document<>();

		try {
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
				if (userPrincipal == null)
					throw new AppException("Invalid User");
			}

			if (planType == null || planType.isEmpty())
				throw new AppException("Plan type cannot be empty");

			switch (planType) {

			case "ENP_AN":

				upgradeToENPAN(idStudentOrder, result, userPrincipal);
				break;
			case "ENP_AN_PLUS":

				upgradeToENPANPlus(idStudentOrder, result, userPrincipal);
				break;

			default:
				if (planType.equalsIgnoreCase("AD_FREE_SUBSCRIPTION"))
					throw new AppException("Plan Type Cannot be Downgraded.");
				else
					throw new AppException("Invalid plan type");
			}

			return result;

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	private void upgradeToENPAN(Long idStudentOrder, Document<StudentSubscription> result,
			UserPrincipal userPrincipal) {
		StudentSubscription studentSubscription = studentSubscriptionRepository
				.findFirstByIdStudentOrderAndActiveFlag(idStudentOrder, Boolean.TRUE);

		if (studentSubscription == null)
			throw new AppException("Student Subscription not found. ");

		if (studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS"))
			throw new AppException("Already Subscribed to Enterprise Plus Annual Plan, Downgradation not possible");

		if (studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN"))
			throw new AppException("Already Subscribed to Enterprises plan");

		StagingStudentSubscription studentStagingSubscription = stagingStudentSubscriptionRepository
				.getByIdStudentOrder(studentSubscription.getIdStudentOrder());

		if (studentStagingSubscription == null)
			throw new AppException("Subscription not found in Student Staging Subscription.");

		StudentOrder studentOrder = studentOrderRepository
				.findByIdStudentOrder(studentSubscription.getIdStudentOrder());
		if (studentOrder == null)
			throw new AppException("Subscription not found in Student Order.");

		Product product = productRepository.findByProductCdAndActiveFlag("ENP_AN", Boolean.TRUE);

		if (product == null)
			throw new AppException("Product not found.");

		studentSubscription.setPurchaseLevel("ENP_AN");
		studentSubscription.setPurchaseAmount(String.valueOf(product.getAnnualSubscrAmt()));
		studentSubscription.setSubscriptionType("ANNUAL");
		studentSubscription.setIdProduct(product.getIdProduct());
		studentSubscription = studentSubscriptionRepository.save(studentSubscription);

		studentStagingSubscription.setPurchaseLevel("ENP_AN");
		studentStagingSubscription.setPurchaseAmount(String.valueOf(product.getAnnualSubscrAmt()));
		studentStagingSubscription.setTransactionAmount(product.getAnnualSubscrAmt());
		studentStagingSubscription.setSubscriptionType("ANNUAL");
		studentStagingSubscription.setIdProduct(product.getIdProduct());
		stagingStudentSubscriptionRepository.save(studentStagingSubscription);

		studentOrder.setNetAmount(product.getAnnualSubscrAmt());
		studentOrder.setTotalAmount(product.getAnnualSubscrAmt());
		studentOrder.setRemarks((studentOrder.getRemarks() == null ? "" :studentOrder.getRemarks()) + "\n Upgraded to Enterprises plan by "
				+ userPrincipal.getUsername() + " at " + Instant.now());
		studentOrderRepository.save(studentOrder);

		result.setData(studentSubscription);
		result.setStatusCode(HttpStatus.OK.value());
		result.setMessage("Subscription upgraded to Enterprises plan successfully.");
	}

	private void upgradeToENPANPlus(Long idStudentOrder, Document<StudentSubscription> result,
			UserPrincipal userPrincipal) {
		StudentSubscription studentSubscription = studentSubscriptionRepository
				.findFirstByIdStudentOrderAndActiveFlag(idStudentOrder, Boolean.TRUE);
		if (studentSubscription == null)
			throw new AppException("Student Subscription not found. ");

		if (studentSubscription.getPurchaseLevel().equalsIgnoreCase("ENP_AN_PLUS"))
			throw new AppException("Already Subscribed to Enterprise Plus Plan");

		StagingStudentSubscription studentStagingSubscription = stagingStudentSubscriptionRepository
				.getByIdStudentOrder(studentSubscription.getIdStudentOrder());
		if (studentStagingSubscription == null)
			throw new AppException("Subscription not found in Student Staging Subscription.");

		StudentOrder studentOrder = studentOrderRepository
				.findByIdStudentOrder(studentSubscription.getIdStudentOrder());
		if (studentOrder == null)
			throw new AppException("Subscription not found in Student Order.");

		Product product = productRepository.findByProductCdAndActiveFlag("ENP_AN_PLUS", Boolean.TRUE);
		if (product == null)
			throw new AppException("Product not found.");

		studentSubscription.setPurchaseLevel("ENP_AN_PLUS");
		studentSubscription.setPurchaseAmount(String.valueOf(product.getAnnualSubscrAmt()));
		studentSubscription.setSubscriptionType("ANNUAL");
		studentSubscription.setIdProduct(product.getIdProduct());
		studentSubscription = studentSubscriptionRepository.save(studentSubscription);

		studentStagingSubscription.setPurchaseLevel("ENP_AN_PLUS");
		studentStagingSubscription.setPurchaseAmount(String.valueOf(product.getAnnualSubscrAmt()));
		studentStagingSubscription.setTransactionAmount(product.getAnnualSubscrAmt());
		studentStagingSubscription.setSubscriptionType("ANNUAL");
		studentStagingSubscription.setIdProduct(product.getIdProduct());
		stagingStudentSubscriptionRepository.save(studentStagingSubscription);

		studentOrder.setNetAmount(product.getAnnualSubscrAmt());
		studentOrder.setTotalAmount(product.getAnnualSubscrAmt());
		studentOrder.setRemarks((studentOrder.getRemarks() == null ? "" :studentOrder.getRemarks()) + "\n Upgraded to Enterprises plus plan by "
				+ userPrincipal.getUsername() + " at " + Instant.now());
		studentOrderRepository.save(studentOrder);

		result.setData(studentSubscription);
		result.setStatusCode(HttpStatus.OK.value());
		result.setMessage("Subscription upgraded to Enterprises plus plan successfully.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<StudentOrder> paymentRefresh(String pendingOrderId) {

		Document<StudentOrder> result = new Document<>();

		try {

			StudentOrder pendingStudentOrder = studentOrderRepository.findByOrderIdAndOrderStatus(pendingOrderId,"Pending");

			if (pendingStudentOrder == null) {
				throw new AppException("Please verify the order id it may be invalid or either moved to success or failed status.");
				
			}
			Map<String, Map<String, Object>> paytmResponse = paytmPaymentConfig
					.paymentPendingStatusCheck(pendingStudentOrder.getOrderId());

			Map<String, Object> body = paytmResponse.get("body");
			Map<String, String> resultInfo = (Map<String, String>) body.get("resultInfo");
			
			
			//check whether the status still in pending 
			
			if (resultInfo.get("resultStatus").equals("PENDING")
					|| resultInfo.get("resultCode").equalsIgnoreCase("402"))
       
               throw new AppException("We are processing, Your transaction status is still in pending.");


			if (resultInfo.get("resultStatus").equals("TXN_SUCCESS")
					|| resultInfo.get("resultCode").equalsIgnoreCase("01")) {

				StudentSubscription subscription = studentSubscriptionRepository
						.findByIdProductAndUserSurIdAndActiveFlag(93L, pendingStudentOrder.getUserSurId(),
								Boolean.TRUE);

				if (subscription != null) {
					
					pendingStudentOrder.setOrderStatus("Success");
					studentOrderRepository.save(pendingStudentOrder);
					LOGGER.info("orderId:" + pendingStudentOrder.getOrderId()
							+ "  A subscription has already been created for this user on the subscription id:"
							+ subscription.getIdStudentSubscription() + ".");
					result.setData(pendingStudentOrder);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("subscription has already been created for this user");
					
				} else {

					String paymentMode = String.valueOf(body.get("paymentMode"));
					// set payment mode
					if (paymentMode != null) {
						paymentMode = paymentMode.equals("DC") ? "Debit Card"
								: paymentMode.equals("CC") ? "Credit Card"
										: paymentMode.equals("NB") ? "Net Banking"
												: paymentMode.equals("PPI") ? "Paytm Wallet" : paymentMode;
					}

					// update student order data
					StudentOrder setStudentOrder = studentOrderRepository
							.findByOrderId(pendingStudentOrder.getOrderId());

					if (setStudentOrder != null) {
						if (setStudentOrder.getSecondaryStatus() != null
								&& resultInfo.get("resultStatus").equals("TXN_SUCCESS")) {
							setStudentOrder.setDisputeFlag(Boolean.TRUE);
						} else {
							setStudentOrder.setDisputeFlag(Boolean.FALSE);
						}
						// update student order table status
						setStudentOrder.setRemarks(
								"The subscription has been successfully created by the system, as the payment response confirms a successful transaction.");
						setStudentOrder.setOrderStatus("Success");
						setStudentOrder = studentOrderRepository.save(setStudentOrder);

						LOGGER.info("orderId:" + pendingStudentOrder.getOrderId()
								+ " Payment Status is Updated as 'Success' in  Student Order Table ");

						// delete cart items if payment is successful
						if (!resultInfo.get("resultCode").equals("141") && setStudentOrder != null) {
							List<UserCart> list = userCartRepository
									.findByIdStudentOrder(setStudentOrder.getIdStudentOrder());
							if (list.size() != 0) {
								userCartRepository.deleteInBatch(list);
							}
						}
					}

					// save response to staging student subscription and student subscription
					List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
							.findByOrderId(pendingStudentOrder.getOrderId());

					Set<String> regDeviceIdSet = new HashSet<String>();
					List<String> regDeviceIdList = new ArrayList<String>();
					String studentName = "";
					String orderId = "";

					for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
						if (stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
							stagingStudentSubscriptionUpdate
									.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
						} else {
							stagingStudentSubscriptionUpdate.setLastPaymentDate(Instant.now());
						}
						if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("MONTH")) {
							LocalDateTime dateMonth = stagingStudentSubscriptionUpdate.getLastPaymentDate()
									.atZone(zoneIndia).toLocalDateTime().plusMonths(1);
							stagingStudentSubscriptionUpdate
									.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
						} else if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("QUARTER")) {
							LocalDateTime dateQuarter = stagingStudentSubscriptionUpdate.getLastPaymentDate()
									.atZone(zoneIndia).toLocalDateTime().plusMonths(3);
							stagingStudentSubscriptionUpdate
									.setNextPaymentDate(dateQuarter.atZone(ZoneId.systemDefault()).toInstant());
						} else if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("ANNUAL")) {
							LocalDateTime dateYear = stagingStudentSubscriptionUpdate.getLastPaymentDate()
									.atZone(zoneIndia).toLocalDateTime().plusMonths(12);
							stagingStudentSubscriptionUpdate
									.setNextPaymentDate(dateYear.atZone(ZoneId.systemDefault()).toInstant());
						}
						LocalDateTime gracePeriodDate = stagingStudentSubscriptionUpdate.getNextPaymentDate()
								.atZone(zoneIndia).toLocalDateTime().plusDays(gracePeriod);
						if (stagingStudentSubscriptionUpdate.getPurchaseLevel().equalsIgnoreCase("AD_FREE_SUBSCRIPTION")) {
							stagingStudentSubscriptionUpdate
									.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
						} else {
							stagingStudentSubscriptionUpdate
									.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
						}
						stagingStudentSubscriptionUpdate.setBankName(String.valueOf(body.get("bankName")));
						stagingStudentSubscriptionUpdate.setBankTransactionId(String.valueOf(body.get("bankTxnId")));
						stagingStudentSubscriptionUpdate.setOrderId(String.valueOf(body.get("orderId")));
						stagingStudentSubscriptionUpdate.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
						stagingStudentSubscriptionUpdate.setPaymentMode(paymentMode);
						stagingStudentSubscriptionUpdate.setPaymentStatus("Success");
						stagingStudentSubscriptionUpdate
								.setTransactionAmount(Float.parseFloat(String.valueOf(body.get("txnAmount"))));
						stagingStudentSubscriptionUpdate
								.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
						stagingStudentSubscriptionUpdate.setPurchaseDate(Instant.now());
						stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
								.save(stagingStudentSubscriptionUpdate);
						LOGGER.info("orderId:" + pendingStudentOrder.getOrderId()
								+ " Payment Status is Updated as 'Success' in Staging Student Subcription Table ");
						if (resultInfo.get("resultStatus").equals("TXN_SUCCESS") && !setStudentOrder.getDisputeFlag()) {
							StudentSubscription studentSubscription = null;
							if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() == null
									|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("NEW")) {
								studentSubscription = new StudentSubscription();
							} else if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() != null
									|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
								studentSubscription = studentSubscriptionRepository.findByIdStudentSubscription(
										stagingStudentSubscriptionUpdate.getIdStudentSubscription());
							}
							studentSubscription.setIdProduct(stagingStudentSubscriptionUpdate.getIdProduct());
							studentSubscription.setIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
							studentSubscription.setIdProductGroup(stagingStudentSubscriptionUpdate.getIdProductGroup());
							studentSubscription.setIdproductLine(stagingStudentSubscriptionUpdate.getIdproductLine());
							studentSubscription.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
							studentSubscription.setIdStudentOrder(stagingStudentSubscriptionUpdate.getIdStudentOrder());
							studentSubscription
									.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
							studentSubscription
									.setNextPaymentDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
							studentSubscription.setPurchaseAmount(stagingStudentSubscriptionUpdate.getPurchaseAmount());
							studentSubscription.setPurchaseDate(stagingStudentSubscriptionUpdate.getPurchaseDate());
							studentSubscription.setPurchaseLevel(stagingStudentSubscriptionUpdate.getPurchaseLevel());
							studentSubscription
									.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getSubscriptionEndDate());
							studentSubscription
									.setSubscriptionType(stagingStudentSubscriptionUpdate.getSubscriptionType());
							studentSubscription.setPurchaseType(stagingStudentSubscriptionUpdate.getPurchaseType());
							studentSubscription.setActiveFlag(Boolean.TRUE);
							studentSubscription.setFreeFlag(Boolean.FALSE);
							studentSubscription.setUserSurId(stagingStudentSubscriptionUpdate.getUserSurId());
							studentSubscription.setIdSpecialOffer(stagingStudentSubscriptionUpdate.getIdSpecialOffer());
							studentSubscription
									.setSpecialOfferFlag(stagingStudentSubscriptionUpdate.getSpecialOfferFlag());
							StudentSubscription ss = studentSubscriptionRepository
									.findByIdBatchAndIdProductAndIdStudentOrderAndUserSurId(
											stagingStudentSubscriptionUpdate.getIdBatch(),
											stagingStudentSubscriptionUpdate.getIdProduct(),
											stagingStudentSubscriptionUpdate.getIdStudentOrder(),
											stagingStudentSubscriptionUpdate.getUserSurId());
							if (ss == null) {
								studentSubscription = studentSubscriptionRepository.save(studentSubscription);
								LOGGER.info(
										"Subscription  created for this orderId:" + pendingStudentOrder.getOrderId());
							} else {
								studentSubscription = null;
							}

							// orderId = studentSubscription.get;
							if (studentSubscription != null) {
								// adding payment details into SubscriptionPaymentHistory table
								Date paymentDate = Date.from(stagingStudentSubscriptionUpdate.getPurchaseDate());
								SubscriptionPaymentHistory subscriptionPaymentHistory = new SubscriptionPaymentHistory(
										studentSubscription.getIdStudentSubscription(),
										stagingStudentSubscriptionUpdate.getIdStudentOrder(), paymentDate,
										Float.valueOf(stagingStudentSubscriptionUpdate.getPurchaseAmount()));
								subscriptionPaymentHistoryRepository.save(subscriptionPaymentHistory);
								LOGGER.info("Added payment details into Subscription PaymentHistory table orderId:"
										+ pendingStudentOrder.getOrderId());
								// calling getVideoRecordByIdProduct for StudentAssignedCourse
								if (studentSubscription.getPurchaseType().equals("NEW")) {
									offlineCourseService.getVideoRecordByIdProduct(studentSubscription);
								}
								orderId = stagingStudentSubscriptionUpdate.getOrderId();
								System.out.println("Studentid " + studentSubscription.getIdStudent());
								Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
								studentName = student.getUser().getFirstName();
								if (student != null) {
									Long userSurId = student.getUser().getUserSurId();
									System.out.println("userSurid " + student.getUser().getUserSurId());
									UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId,
											"MOBILE");
									System.out.println("userDevice " + userDevice);
									if (userDevice != null) {
										System.out.println("DeviceId " + userDevice.getDeviceId());
										regDeviceIdSet.add(userDevice.getDeviceId());
										// regDeviceIdList.add(userDevice.getDeviceId());
									}
								}
							}
						}
					}
					if (!regDeviceIdSet.isEmpty()) {
						System.out.println("orderId " + orderId);
						regDeviceIdList.addAll(regDeviceIdSet);
						System.out.println("regDeviceIdList " + regDeviceIdList);
						String message = "Dear " + studentName + " your payment received successfully for orderId "
								+ orderId;
						notificationService.sendNotification(regDeviceIdList, message, "order");
					}

					result.setData(setStudentOrder);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Subscription Created Successfully");
				}
				
			} // resultStatus success check if block
				// if resultStatus "TXN_FAILURE" enters into else block
			else {

				// update student order data
				StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(pendingStudentOrder.getOrderId());

				if (setStudentOrder != null && setStudentOrder.getOrderStatus().equalsIgnoreCase("Pending")) {
					// update student order table status
					setStudentOrder.setOrderStatus("Failed");
					setStudentOrder.setRemarks(
							"Subscription creation unsuccessful due to a failed payment transaction.");
					setStudentOrder = studentOrderRepository.save(setStudentOrder);
					LOGGER.info("orderId:" + setStudentOrder.getOrderId()
							+ " Order Status is Updated as 'Failed' in Student Order Table  ");
				} else {
					throw new AppException(
							"No Record found for this Student Order" + "orderId:" + setStudentOrder.getOrderId());

				}
				// save response to staging student subscription and student subscription
				List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
						.findByOrderId(pendingStudentOrder.getOrderId());

				if (stagingStudentSubscription.isEmpty()) {

					throw new AppException("No Staging Records found for this Student Order" + "orderId:"
							+ setStudentOrder.getOrderId());

				} else {
					for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
						stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
						stagingStudentSubscriptionUpdate
								.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
						stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
								.save(stagingStudentSubscriptionUpdate);
						LOGGER.info("orderId:" + stagingStudentSubscriptionUpdate.getOrderId()
								+ " Payment Status is Updated as 'Failed' in Staging Student Subcription Table ");

						if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
							Batch batchEdit = batchRepository
									.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
							if (batchEdit != null) {
								batchEdit.setPaymentStatus(null);
								batchRepository.save(batchEdit);
							}
						}
					}
				}
				result.setData(setStudentOrder);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Subscription creation unsuccessful due to a failed payment transaction.");
			}
			
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;

	}

	@Override
	public Document<CartSummaryDTO> iosOrderCreation(NewSubscriptionPlanDTO newSubscriptionPlanDTO) {
	
	
			Document<CartSummaryDTO> document = new Document<>();

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
					LocalDate dateExist = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
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
				
					
					}
				
				document.setData(cartSummaryDTO);
				document.setMessage("order created successfully");
				document.setStatusCode(HttpStatus.OK.value());
			}
			
			catch (Exception e) {
				LOGGER.error(e.getMessage());
				document.setData(null);
				document.setMessage(e.getLocalizedMessage());
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}
		return document;
	}

	@Override
	public Document<String> iosSubscriptionCreation(String orderId, String transactionId) {
		
		Document<String> document = new Document<>();
		
		try {
			JWSTransactionDecodedPayload response=inAppPurchase
					.inAppPurchaseResponse(transactionId);
			
		
			
			if(response.getInAppOwnershipType().toString().equals("PURCHASED")) {
			
			StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(orderId);
			if (setStudentOrder != null) {
				if (setStudentOrder.getSecondaryStatus() != null
						&& response.getInAppOwnershipType().toString().equals("PURCHASED")) {
					setStudentOrder.setDisputeFlag(Boolean.TRUE);
				} else {
					setStudentOrder.setDisputeFlag(Boolean.FALSE);
				}
				// update student order table status
				setStudentOrder.setOrderStatus("Success");
				setStudentOrder = studentOrderRepository.save(setStudentOrder);

				// delete cart items if payment is successful
				if (!response.getInAppOwnershipType().toString().equals("PURCHASED") && setStudentOrder != null) {
					List<UserCart> list = userCartRepository
							.findByIdStudentOrder(setStudentOrder.getIdStudentOrder());
					if (list.size() != 0) {
						userCartRepository.deleteInBatch(list);
					}
				}
			}
			System.out.println("Student order updated");
			// save response to staging student subscription and student subscription
			List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
					.findByOrderId(orderId);

			Set<String> regDeviceIdSet = new HashSet<String>();
			List<String> regDeviceIdList = new ArrayList<String>();
			String studentName = "";
			String orderId1 = "";

			for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
				if (stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
					stagingStudentSubscriptionUpdate
							.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
				} else {
					stagingStudentSubscriptionUpdate.setLastPaymentDate(Instant.now());
				}
//				if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("MONTH")) {
//					LocalDateTime dateMonth = stagingStudentSubscriptionUpdate.getLastPaymentDate()
//							.atZone(zoneIndia).toLocalDateTime().plusMonths(1);
//					stagingStudentSubscriptionUpdate
//							.setNextPaymentDate(dateMonth.atZone(ZoneId.systemDefault()).toInstant());
//				} else if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("QUARTER")) {
//					LocalDateTime dateQuarter = stagingStudentSubscriptionUpdate.getLastPaymentDate()
//							.atZone(zoneIndia).toLocalDateTime().plusMonths(3);
//					stagingStudentSubscriptionUpdate
//							.setNextPaymentDate(dateQuarter.atZone(ZoneId.systemDefault()).toInstant());
//				} else if (stagingStudentSubscriptionUpdate.getSubscriptionType().equals("ANNUAL")) {
//					LocalDateTime dateYear = stagingStudentSubscriptionUpdate.getLastPaymentDate()
//							.atZone(zoneIndia).toLocalDateTime().plusMonths(12);
//					stagingStudentSubscriptionUpdate
//							.setNextPaymentDate(dateYear.atZone(ZoneId.systemDefault()).toInstant());
//				}
				
				ProductDuration productDuration = productDurationRepository
						.findByDurationCode(stagingStudentSubscriptionUpdate.getSubscriptionType());
				
				if(productDuration==null)
					throw new AppException("No product duration data found");
				
				LocalDateTime duration = stagingStudentSubscriptionUpdate.getLastPaymentDate()
						.atZone(zoneIndia).toLocalDateTime().plusDays(productDuration.getDuration());
				stagingStudentSubscriptionUpdate
				.setNextPaymentDate(duration.atZone(ZoneId.systemDefault()).toInstant());

				LocalDateTime gracePeriodDate = stagingStudentSubscriptionUpdate.getNextPaymentDate()
						.atZone(zoneIndia).toLocalDateTime().plusDays(gracePeriod);
				if (stagingStudentSubscriptionUpdate.getPurchaseLevel().equals("AD_FREE")) {
					stagingStudentSubscriptionUpdate
							.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
				} else {
					stagingStudentSubscriptionUpdate
							.setSubscriptionEndDate(gracePeriodDate.atZone(ZoneId.systemDefault()).toInstant());
				}
			//	stagingStudentSubscriptionUpdate.setBankName(parameters.get("BANKNAME"));
			//	stagingStudentSubscriptionUpdate.setBankTransactionId(parameters.get("BANKTXNID"));
				stagingStudentSubscriptionUpdate.setOrderId(orderId);
				stagingStudentSubscriptionUpdate.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
				stagingStudentSubscriptionUpdate.setPaymentMode("In-App-Purchase");
				stagingStudentSubscriptionUpdate.setPaymentStatus("Success");
//				stagingStudentSubscriptionUpdate
//						.setTransactionAmount(response.getPrice().floatValue());
				stagingStudentSubscriptionUpdate
						.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
				stagingStudentSubscriptionUpdate.setPurchaseDate(Instant.now());
				stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
						.save(stagingStudentSubscriptionUpdate);
				System.out.println("staging StudentSubscription Updated");
				if (response.getInAppOwnershipType().toString().equals("PURCHASED") && !setStudentOrder.getDisputeFlag()) {
					StudentSubscription studentSubscription = null;
					if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() == null
							|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("NEW")) {
						studentSubscription = new StudentSubscription();
					} else if (stagingStudentSubscriptionUpdate.getIdStudentSubscription() != null
							|| stagingStudentSubscriptionUpdate.getPurchaseType().equals("RENEWAL")) {
						studentSubscription = studentSubscriptionRepository.findByIdStudentSubscription(
								stagingStudentSubscriptionUpdate.getIdStudentSubscription());
					}
					studentSubscription.setIdProduct(stagingStudentSubscriptionUpdate.getIdProduct());
					studentSubscription.setIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
					studentSubscription.setIdProductGroup(stagingStudentSubscriptionUpdate.getIdProductGroup());
					studentSubscription.setIdproductLine(stagingStudentSubscriptionUpdate.getIdproductLine());
					studentSubscription.setIdStudent(stagingStudentSubscriptionUpdate.getIdStudent());
					studentSubscription.setIdStudentOrder(stagingStudentSubscriptionUpdate.getIdStudentOrder());
					studentSubscription
							.setLastPaymentDate(stagingStudentSubscriptionUpdate.getLastPaymentDate());
					studentSubscription
							.setNextPaymentDate(stagingStudentSubscriptionUpdate.getNextPaymentDate());
					studentSubscription.setPurchaseAmount(stagingStudentSubscriptionUpdate.getPurchaseAmount());
					studentSubscription.setPurchaseDate(stagingStudentSubscriptionUpdate.getPurchaseDate());
					studentSubscription.setPurchaseLevel(stagingStudentSubscriptionUpdate.getPurchaseLevel());
					studentSubscription
							.setSubscriptionEndDate(stagingStudentSubscriptionUpdate.getSubscriptionEndDate());
					studentSubscription
							.setSubscriptionType(stagingStudentSubscriptionUpdate.getSubscriptionType());
					studentSubscription.setPurchaseType(stagingStudentSubscriptionUpdate.getPurchaseType());
					studentSubscription.setActiveFlag(Boolean.TRUE);
					studentSubscription.setFreeFlag(Boolean.FALSE);
					studentSubscription.setUserSurId(stagingStudentSubscriptionUpdate.getUserSurId());
					studentSubscription.setIdSpecialOffer(stagingStudentSubscriptionUpdate.getIdSpecialOffer());
					studentSubscription
							.setSpecialOfferFlag(stagingStudentSubscriptionUpdate.getSpecialOfferFlag());
					StudentSubscription ss = studentSubscriptionRepository
							.findByIdBatchAndIdProductAndIdStudentOrderAndUserSurId(
									stagingStudentSubscriptionUpdate.getIdBatch(),
									stagingStudentSubscriptionUpdate.getIdProduct(),
									stagingStudentSubscriptionUpdate.getIdStudentOrder(),
									stagingStudentSubscriptionUpdate.getUserSurId());
					if (ss == null) {
						studentSubscription = studentSubscriptionRepository.save(studentSubscription);
					} else {
						studentSubscription = null;
					}
					System.out.println("Student Subscription Updated");
					// orderId = studentSubscription.get;
					if (studentSubscription != null) {
						// adding payment details into SubscriptionPaymentHistory table
						Date paymentDate = Date.from(stagingStudentSubscriptionUpdate.getPurchaseDate());
						SubscriptionPaymentHistory subscriptionPaymentHistory = new SubscriptionPaymentHistory(
								studentSubscription.getIdStudentSubscription(),
								stagingStudentSubscriptionUpdate.getIdStudentOrder(), paymentDate,
								Float.valueOf(stagingStudentSubscriptionUpdate.getPurchaseAmount()));
						subscriptionPaymentHistoryRepository.save(subscriptionPaymentHistory);
						// calling getVideoRecordByIdProduct for StudentAssignedCourse
						if (studentSubscription.getPurchaseType().equals("NEW")) {
							offlineCourseService.getVideoRecordByIdProduct(studentSubscription);
						}
						orderId = stagingStudentSubscriptionUpdate.getOrderId();
						System.out.println("Studentid " + studentSubscription.getIdStudent());
						Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
						studentName = student.getUser().getFirstName();
						if (student != null) {
							Long userSurId = student.getUser().getUserSurId();
							System.out.println("userSurid " + student.getUser().getUserSurId());
							UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId,
									"MOBILE");
							System.out.println("userDevice " + userDevice);
							if (userDevice != null) {
								System.out.println("DeviceId " + userDevice.getDeviceId());
								regDeviceIdSet.add(userDevice.getDeviceId());
								// regDeviceIdList.add(userDevice.getDeviceId());
							}
						}
					}
				}
			}
			if (!regDeviceIdSet.isEmpty()) {
				System.out.println("orderId " + orderId1);
				regDeviceIdList.addAll(regDeviceIdSet);
				System.out.println("regDeviceIdList " + regDeviceIdList);
				String message = "Dear " + studentName + " your payment received successfully for orderId "
						+ orderId1;
				notificationService.sendNotification(regDeviceIdList, message, "order");
			}
			document.setData("Subscription created");
			document.setMessage("Subscription created");
			document.setStatusCode(HttpStatus.OK.value());
		 }else{
				StudentOrder setStudentOrder = studentOrderRepository.findByOrderId(orderId);

					// update student order table status
					setStudentOrder.setOrderStatus("Failed");
					setStudentOrder = studentOrderRepository.save(setStudentOrder);
				
				// save response to staging student subscription and student subscription
				List<StagingStudentSubscription> stagingStudentSubscription = stagingStudentSubscriptionRepository
						.findByOrderId(orderId);

				if (stagingStudentSubscription.size() != 0) {
					for (StagingStudentSubscription stagingStudentSubscriptionUpdate : stagingStudentSubscription) {
						stagingStudentSubscriptionUpdate.setPaymentStatus("Failed");
						stagingStudentSubscriptionUpdate
								.setTransactionDate(stagingStudentSubscriptionUpdate.getTransactionDate());
						stagingStudentSubscriptionUpdate = stagingStudentSubscriptionRepository
								.save(stagingStudentSubscriptionUpdate);

						if (stagingStudentSubscriptionUpdate.getIdBatch() != null) {
							Batch batchEdit = batchRepository
									.findByIdBatch(stagingStudentSubscriptionUpdate.getIdBatch());
							if (batchEdit != null) {
								batchEdit.setPaymentStatus(null);
								batchRepository.save(batchEdit);
							}
						}
					}
				}
				document.setData(null);
				document.setMessage("Subscription creation unsuccessful due to a failed transaction");
				document.setStatusCode(HttpStatus.OK.value());
		 }
		}catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		}
		return document;
	}
	

	@Override
	public Document<NewStudentSubscriptionSubjectDTO> getStudentSubjectProgress(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState, Long idStudentSubscription) {
		

		Document<NewStudentSubscriptionSubjectDTO> result = new Document<>();

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
				
				if(idProductLine==6L)
				{
					idClassStandard=4L;
					idState=6L;
					idSyllabus=4L;
				}

				// check whether user is subscribed.
				SubscribedUserDTO suDTO = this.checkUserSubscribed(userPrincipal.getUserSurId());

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

		
	public String createCourseVideoOtp(String videoId) {

		VideoCipherOTP result = new VideoCipherOTP();
		VideoCipherOTP vco = new VideoCipherOTP();
		
		try {
			
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				 userPrincipal = (UserPrincipal) authentication.getPrincipal();
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
			        otpParameters.put("ttl", 21600);  // set video otp expiration for 6 hours
			        otpParameters.put("userId", userPrincipal.getUserSurId());
			        otpParameters.put("annotate", gson.toJson(new Map[]{annotation}));

			        String jsonBody = gson.toJson(otpParameters);
			
			vco = videoCipherConfiguration.getOTP(videoId, jsonBody);

			
		}

		catch (Exception exp) {
           	exp.printStackTrace();
		}

		return vco.getOtp();

	}


}