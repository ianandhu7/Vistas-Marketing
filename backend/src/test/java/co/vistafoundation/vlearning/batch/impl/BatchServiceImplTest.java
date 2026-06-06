package co.vistafoundation.vlearning.batch.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.batch.dto.BatchAttendanceDTO;
import co.vistafoundation.vlearning.batch.dto.BatchInfoDTO;
import co.vistafoundation.vlearning.batch.dto.BatchMetaDataDTO;
import co.vistafoundation.vlearning.batch.dto.BatchResonseDTO;
import co.vistafoundation.vlearning.batch.dto.BatchTestNotificationDTO;
import co.vistafoundation.vlearning.batch.dto.CreateBatchRequestDTO;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchCalender;
import co.vistafoundation.vlearning.batch.model.BatchRunDetail;
import co.vistafoundation.vlearning.batch.model.BatchStudentAttendance;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.batch.repository.BatchCalenderRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRunDetailsRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRunRecordingRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentAttendanceRepository;
import co.vistafoundation.vlearning.batch.repository.DayofWeekCodeRepository;
import co.vistafoundation.vlearning.batch.service.BatchService;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.model.BatchQuizAssignment;
import co.vistafoundation.vlearning.quiz.model.BatchStudentQuiz;
import co.vistafoundation.vlearning.quiz.repository.BatchQuizAssignmentRespository;
import co.vistafoundation.vlearning.quiz.repository.BatchStudentQuizRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;

/**
 * 
 * @author Naveen Kumar
 *
 */
@SpringBootTest
class BatchServiceImplTest {

	@Autowired
	private BatchService batchService;

	@MockBean
	ClassRepository classRepository;

	@MockBean
	SubjectRepository subjectRepository;

	@MockBean
	ProductLineRepository productLineRepository;

	@MockBean
	TeacherRepository teacherRepository;

	@MockBean
	ProductRepository productRepository;

	@MockBean
	ProductGroupRepository productGroupRepository;

	@MockBean
	BatchRepository batchRepository;

	@MockBean
	DayofWeekCodeRepository dayofWeekCodeRepository;

	@MockBean
	BatchRunDetailsRepository batchRunDetailRepository;

	@MockBean
	BatchStudentAttendanceRepository batchStudentAttendanceRepository;

	@MockBean
	StudentSubscriptionRepository studentSubscriptionRepository;

	@MockBean
	BatchRunRecordingRepository batchRunRecordingRepository;

	@MockBean
	WebExPoolRepository webExPoolRepo;

	@MockBean
	BatchCalenderRepository batchCalenderRepository;
	
	@MockBean
	BatchQuizAssignmentRespository batchQuizAssignmentRespository;
	
	@MockBean
	BatchStudentQuizRepository batchStudentQuizRepository;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// Initialize subject data
		List<Subject> subList = new ArrayList<Subject>();
		Subject sub1 = new Subject();
		sub1.setSubjectName("Maths");
		sub1.setIdSubject(1L);
		Subject sub2 = new Subject();
		sub2.setSubjectName("Social");
		sub2.setIdSubject(2L);
		Subject sub3 = new Subject();
		sub3.setSubjectName("Science");
		sub3.setIdSubject(3L);
		subList.add(sub1);
		subList.add(sub2);
		subList.add(sub3);
		Mockito.when(subjectRepository.findAll()).thenReturn(subList);
		Mockito.when(subjectRepository.findByIdSubject(1L)).thenReturn(sub1);

		// Initialize ClassStandard data
		List<ClassStandard> classList = new ArrayList<ClassStandard>();
		ClassStandard class1 = new ClassStandard();
		class1.setClassStandadName("Class 1");
		class1.setIdClassStandard(1L);
		ClassStandard class2 = new ClassStandard();
		class2.setClassStandadName("Class 3");
		class2.setIdClassStandard(2L);
		ClassStandard class3 = new ClassStandard();
		class3.setClassStandadName("Class 2");
		class3.setIdClassStandard(3L);
		classList.add(class1);
		classList.add(class2);
		classList.add(class3);
		Mockito.when(classRepository.findAll()).thenReturn(classList);

		Mockito.when(classRepository.findByIdClassStandard(1L)).thenReturn(class1);

		// Initialize ProductLine data
		List<ProductLine> prodList = new ArrayList<ProductLine>();
		ProductLine pl1 = new ProductLine();
		pl1.setProductCategory("BATCH");
		pl1.setProductCategoryCd("Batch_1");
		pl1.setProductLine("Batch of 1 Student");
		pl1.setIdProductLine(1L);
		ProductLine pl2 = new ProductLine();
		pl2.setProductCategory("BATCH");
		pl2.setProductCategoryCd("Batch_5");
		pl2.setProductLine("Batch of 5 Student");
		pl2.setIdProductLine(2L);
		ProductLine pl3 = new ProductLine();
		pl3.setProductCategory("BATCH");
		pl3.setProductCategoryCd("Batch_3");
		pl3.setProductLine("Batch of 3 Student");
		pl3.setIdProductLine(3L);
		ProductLine pl4 = new ProductLine();
		pl4.setProductCategory("BATCH");
		pl4.setProductCategoryCd("Batch_10");
		pl4.setProductLine("Batch of 10 Student");
		pl4.setIdProductLine(4L);
		prodList.add(pl1);
		prodList.add(pl2);
		prodList.add(pl3);
		prodList.add(pl4);
		Mockito.when(productLineRepository.findByProductCategory("BATCH")).thenReturn(prodList);

		ProductGroup pg = new ProductGroup();

		pg.setAnnualSubscrAmt(30000.00f);
		pg.setExtraCurrCategory(null);
		pg.setIdClassStandard(1L);
		pg.setIdProductGroup(1L);
		pg.setIdProductLine(1L);
		pg.setMonthlySubscrAmt(3000f);
		pg.setProductGroupName("BATCH_GROUP_1");
		pg.setQtrSubscrAmt(11000f);
		
		List<ProductGroup> pgList = new ArrayList<ProductGroup>();
		pgList.add(pg);
		Mockito.when(productGroupRepository.findByIdClassStandardAndIdProductLine(1L, 1L)).thenReturn(pg);
		Mockito.when( productGroupRepository.findAll()).thenReturn(pgList);

		
		
		List<Product> productList = new ArrayList<Product>();

		Product p1 = new Product();
		p1.setAgeGroup("14-16");
		p1.setAnnualSubscrAmt(30000f);
		p1.setIdClassStandard(1L);
		p1.setIdProduct(1L);
		p1.setIdProductGroup(1L);
		p1.setIdSubject(1L);
		p1.setMonthlySubcrAmt(3000f);
		p1.setProductCd("BATCH_1_MATHS_10");
		p1.setProductName("Batch of 1 Student, Maths- Class 10");
		p1.setQtrSubscrAmt(10000f);
		p1.setBatchSize(1);

		productList.add(p1);

		Product p2 = new Product();
		p2.setAgeGroup("14-16");
		p2.setAnnualSubscrAmt(30000f);
		p2.setIdClassStandard(2L);
		p2.setIdProduct(2L);
		p2.setIdProductGroup(2L);
		p2.setIdSubject(1L);
		p2.setMonthlySubcrAmt(3000f);
		p2.setProductCd("BATCH_1_MATHS_9");
		p2.setProductName("Batch of 1 Student, Maths- Class 9");
		p2.setQtrSubscrAmt(10000f);
		p2.setBatchSize(1);

		productList.add(p2);

		Mockito.when(productRepository.findByIdProductGroupAndActiveFlag(1L,true)).thenReturn(productList);
		Mockito.when(productRepository.findByIdProductGroupAndIdSubjectAndActiveFlag(1L,1L,Boolean.TRUE)).thenReturn(p1);

		Mockito.when(productRepository.findByIdProductGroupAndIdClassStandardAndIdSubjectAndActiveFlag(1L, 1L, 1L,Boolean.TRUE)).thenReturn(p1);

		Mockito.when(productRepository.findByIdProductAndActiveFlag(1L,Boolean.TRUE)).thenReturn(p1);

		User user = new User();
		user.setFirstName("Meghana");
		user.setLastName("Shetty");

		// Initialize Teacher data
		List<Teacher> teacherList = new ArrayList<Teacher>();
		Teacher teacher1 = new Teacher();
		teacher1.setActiveFlag(Boolean.TRUE);
		teacher1.setEmailId("naveen903612@gmail.com");
		teacher1.setExpLevel("Intermediate");
		teacher1.setIdWebexPool(1L);
		teacher1.setJoinedDate(formatter.parse(formatter.format(new Date())));
		teacher1.setRating(0);
		teacher1.setTeacherAddress("teacher address here.");
		teacher1.setUser(user);
		teacher1.setTeacherDesc("teacher description");
		teacher1.setIdTeacher(1L);
		Teacher teacher2 = new Teacher();
		teacher2.setActiveFlag(Boolean.TRUE);
		teacher2.setEmailId("test@gmail.com");
		teacher2.setExpLevel("Intermediate");
		teacher2.setIdWebexPool(1L);
		teacher2.setJoinedDate(formatter.parse(formatter.format(new Date())));
		teacher2.setRating(0);
		teacher2.setTeacherAddress("teacher address here.");
		teacher2.setUser(new User());
		teacher2.setTeacherDesc("teacher description");
		teacher2.setIdTeacher(2L);
		teacherList.add(teacher1);
		teacherList.add(teacher2);
		Mockito.when(teacherRepository.findAll()).thenReturn(teacherList);
		Mockito.when(teacherRepository.findByIdTeacher(1L)).thenReturn(teacher1);

		List<DayOfWeekCode> daysCodeList = new ArrayList<DayOfWeekCode>();
		DayOfWeekCode dayCode1 = new DayOfWeekCode();
		dayCode1.setDayOfWeekCd("MON_TUE_WED");
		dayCode1.setIdDayofWeekCode(1L);
		DayOfWeekCode dayCode2 = new DayOfWeekCode();
		dayCode2.setDayOfWeekCd("TUE_WED_THU");
		dayCode2.setIdDayofWeekCode(2L);
		DayOfWeekCode dayCode3 = new DayOfWeekCode();
		dayCode3.setDayOfWeekCd("WED_THU_FRI");
		dayCode3.setIdDayofWeekCode(3L);

		daysCodeList.add(dayCode1);
		daysCodeList.add(dayCode2);
		daysCodeList.add(dayCode3);
		Mockito.when(dayofWeekCodeRepository.findAll()).thenReturn(daysCodeList);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yyyyMMdd = sdf.format(new Date());

		List<BatchStudentAttendance> andanceList = new ArrayList<BatchStudentAttendance>();

		BatchStudentAttendance attendace = new BatchStudentAttendance();
		attendace.setAbsentPresentFlag(true);
		attendace.setBatchRunDate(yyyyMMdd);
		attendace.setIdBatch(1L);
		attendace.setIdBatchStudentAttendance(1L);
		attendace.setIdStudentSubscr(1L);

		andanceList.add(attendace);

		Mockito.when(batchStudentAttendanceRepository.findByIdBatchAndIdStudentSubscrAndBatchRunDate(1L, 1L, yyyyMMdd))
				.thenReturn(attendace);
		
		Mockito.when(batchStudentAttendanceRepository.findByIdBatchAndIdStudentSubscr(1L, 1L))
		.thenReturn(andanceList);
		
		List<BatchRunDetail> batchrunList = new ArrayList<BatchRunDetail>();

		BatchRunDetail batchrundetails = new BatchRunDetail();
		batchrundetails.setActualEndTime("01:00:00");
		batchrundetails.setActualStartTime("00:00:00");
		batchrundetails.setAttendeeMeetingUrl("URL");
		batchrundetails.setBatchRundate(yyyyMMdd);
		batchrundetails.setHostMeetingUrl("Host URL");
		batchrundetails.setIdBatch(1L);
		batchrundetails.setIdBatchRunDetail(1L);
		batchrundetails.setMeetingPassword("123");
		batchrundetails.setMeetingDescription("meetingDescription");

		batchrunList.add(batchrundetails);
		Mockito.when(batchRunDetailRepository.fetchBatchRunDetailsByIdBatch(1L, yyyyMMdd)).thenReturn(batchrundetails);
		Mockito.when(batchRunDetailRepository.findByIdBatch(1L)).thenReturn(batchrunList);

		Mockito.when(dayofWeekCodeRepository.findByIdDayofWeekCode(1L)).thenReturn(dayCode1);

		List<Batch> batchList1 = new ArrayList<Batch>();

		Batch batchInput = new Batch();

		batchInput.setBatchFromTime(LocalTime.parse("00:00:00"));
		batchInput.setBatchName("Batch-1-maths");
		batchInput.setBatchToTime(LocalTime.parse("01:00:00"));
		batchInput.setCurrentOccupancy(1);
		batchInput.setCurrentVacancy(1);
		batchInput.setDayOfWeekCode(dayCode1);
		batchInput.setDemoVideoUrl("http://test.com");
		batchInput.setFromTime("00:00:00");
		batchInput.setIdProduct(1L);
		batchInput.setIdTeacher(1L);
		batchInput.setIdWebexPool(1L);
		batchInput.setToTime("01:00:00");

		Batch batch1 = new Batch();

		batch1.setBatchFromTime(LocalTime.parse("00:00:00"));
		batch1.setBatchName("Batch-1-maths");
		batch1.setBatchToTime(LocalTime.parse("01:00:00"));
		batch1.setCurrentOccupancy(1);
		batch1.setCurrentVacancy(1);
		batch1.setDayOfWeekCode(dayCode1);
		batch1.setDemoVideoUrl("http://test.com");
		batch1.setFromTime("00:00:00");
		batch1.setIdBatch(1L);
		batch1.setIdProduct(1L);
		batch1.setIdTeacher(1L);
		batch1.setIdWebexPool(1L);
		batch1.setToTime("01:00:00");

		Batch batch2 = new Batch();

		batch2.setBatchFromTime(LocalTime.parse("02:00:00"));
		batch2.setBatchName("Batch-1-maths");
		batch2.setBatchToTime(LocalTime.parse("03:00:00"));
		batch2.setCurrentOccupancy(1);
		batch2.setCurrentVacancy(1);
		batch2.setDayOfWeekCode(dayCode1);
		batch2.setDemoVideoUrl("http://test.com");
		batch2.setFromTime("00:00:00");
		batch2.setIdBatch(2L);
		batch2.setIdProduct(1L);
		batch2.setIdTeacher(1L);
		batch2.setIdWebexPool(1L);
		batch2.setToTime("01:00:00");
		batchList1.add(batch1);
		batchList1.add(batch2);

		Mockito.when(batchRepository.save(any(Batch.class))).thenReturn(batch1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacher(1L, 1L)).thenReturn(batchList1);
		Mockito.when(batchRepository.findAll()).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdTeacher(1L)).thenReturn(batchList1);
		Mockito.when(batchRepository.findByIdProduct(1L)).thenReturn(batchList1);
		Mockito.when(batchRepository.findByIdBatch(1L)).thenReturn(batch1);
		Mockito.when(batchRepository.findByIdBatch(2L)).thenReturn(batch1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(1L, 1L, 1L))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(1L, 1L,
				1L, LocalTime.parse("00:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndAndBatchToTime(1L,
				1L, 1L, LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository
				.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(1L, 1L, 1L,
						LocalTime.parse("00:00:00"), LocalTime.parse("01:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCode(1L, 1L)).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(1L, 1L,
				LocalTime.parse("00:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchToTime(1L, 1L,
				LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(1L,
				1L,LocalTime.parse("00:00:00"), LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchFromTime(1L, LocalTime.parse("00:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchToTime(1L,LocalTime.parse("01:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchFromTimeAndBatchToTime(1L, LocalTime.parse("00:00:00"),
				LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndBatchFromTime(1L, 1L, LocalTime.parse("00:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProduct(1L)).thenReturn(batchList1);
		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(1L, 1L, 1L))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(1L, 1L,
				1L, LocalTime.parse("00:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndAndBatchToTime(1L,
				1L, 1L,LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository
				.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(1L, 1L, 1L,
						LocalTime.parse("00:00:00"),LocalTime.parse("01:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCode(1L, 1L)).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(1L, 1L,
				LocalTime.parse("00:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchToTime(1L, 1L,
				LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(1L,
				1L, LocalTime.parse("00:00:00"), LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchFromTime(1L, LocalTime.parse("00:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchToTime(1L, LocalTime.parse("01:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndBatchFromTimeAndBatchToTime(1L, LocalTime.parse("00:00:00"),
				LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndBatchFromTime(1L, 1L, LocalTime.parse("00:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndBatchToTime(1L, 1L, LocalTime.parse("01:00:00")))
				.thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdProductAndIdTeacherAndBatchFromTimeAndBatchToTime(1L, 1L,
				LocalTime.parse("00:00:00"), LocalTime.parse("01:00:00"))).thenReturn(batchList1);

		Mockito.when(batchRepository.findByIdBatch(1L)).thenReturn(batch1);
		Mockito.when(batchRepository.findByIdBatch(2L)).thenReturn(batch2);

		List<BatchRunDetail> listOfBatchRunDetails = new ArrayList<>();

		// Set Batch Run Details
		BatchRunDetail batchRunDetail = new BatchRunDetail();
		batchRunDetail.setActualEndTime("11:30 AM");
		batchRunDetail.setActualStartTime("10:30 AM");
		batchRunDetail.setAttendeeMeetingUrl("http://test-webex.com");
		batchRunDetail.setBatchRundate(yyyyMMdd);
		batchRunDetail.setCreatedAt(Instant.now());
		batchRunDetail.setCreatedBy(2L);
		batchRunDetail.setHostMeetingUrl("http://test-webex.com");
		batchRunDetail.setIdBatch(1L);
		batchRunDetail.setIdBatchRunDetail(1L);
		batchRunDetail.setMeetingDescription("Testing Batch Run Details By Teacher For Todays Date");
		batchRunDetail.setMeetingGuestUserToken("jkneruihreuhr873487837");
		batchRunDetail.setMeetingId("1256726727");
		batchRunDetail.setMeetingPassword("123456");
		batchRunDetail.setMeetingTitle("Test Title By Ahmed");
		batchRunDetail.setMeetingUuId("jeruo838482239i");
		batchRunDetail.setUpdatedAt(Instant.now());
		batchRunDetail.setUpdatedBy(2L);

		BatchRunDetail batchRunDetail1 = new BatchRunDetail();
		batchRunDetail1.setActualEndTime("12:30 PM");
		batchRunDetail1.setActualStartTime("11:30 AM");
		batchRunDetail1.setAttendeeMeetingUrl("http://test-webex.com");
		batchRunDetail1.setBatchRundate(yyyyMMdd);
		batchRunDetail1.setCreatedAt(Instant.now());
		batchRunDetail1.setCreatedBy(2L);
		batchRunDetail1.setHostMeetingUrl("http://test-webex.com");
		batchRunDetail1.setIdBatch(2L);
		batchRunDetail1.setIdBatchRunDetail(2L);
		batchRunDetail1.setMeetingDescription("Testing Batch Run Details By Teacher For Todays Date");
		batchRunDetail1.setMeetingGuestUserToken("8djhweuy8273823");
		batchRunDetail1.setMeetingId("87328782");

		batchRunDetail1.setMeetingPassword("123456");
		batchRunDetail1.setMeetingTitle("Test Title By Ahmed 2");
		batchRunDetail1.setMeetingUuId("kwjie8238982");
		batchRunDetail1.setUpdatedAt(Instant.now());
		batchRunDetail1.setUpdatedBy(2L);

		listOfBatchRunDetails.add(batchRunDetail);
		listOfBatchRunDetails.add(batchRunDetail1);

		Mockito.when(batchRunDetailRepository.save(any(BatchRunDetail.class))).thenReturn(batchRunDetail);
		Mockito.when(batchRunDetailRepository.findAll()).thenReturn(listOfBatchRunDetails);
		Mockito.when(batchRunDetailRepository.findByIdBatchAndBatchRundate(1L, yyyyMMdd)).thenReturn(batchRunDetail);

		BatchCalender batchCalenderInput = new BatchCalender();
		batchCalenderInput.setBatch(batch1);
		batchCalenderInput.setFromTime("00:00:00");
		batchCalenderInput.setToTime("00:00:00");
		batchCalenderInput.setDayOfWeek("MONDAY");

		BatchCalender batchCalender = new BatchCalender();
		batchCalender.setBatch(batch1);
		batchCalender.setFromTime("00:00:00");
		batchCalender.setToTime("00:00:00");
		batchCalender.setDayOfWeek("MONDAY");
		batchCalender.setIdBatchCalendar(1L);

		Mockito.when(batchCalenderRepository.save(batchCalenderInput)).thenReturn(batchCalender);

//		WebEx Host 

		List<WebExPool> webExPoolList = new ArrayList<>();

		WebExPool webExPool = new WebExPool();
		webExPool.setAvailableFlag(Boolean.TRUE);
		webExPool.setIdWebExPool(1L);
		webExPool.setWebExPassword("E2f780892781@");
		webExPool.setWebExUserId("ahmedreza@zeesense.com");

		WebExPool webExPool1 = new WebExPool();
		webExPool1.setAvailableFlag(Boolean.TRUE);
		webExPool1.setIdWebExPool(2L);
		webExPool1.setWebExPassword("Vista@2020");
		webExPool1.setWebExUserId("samsadhasan@hotmail.com");

		webExPoolList.add(webExPool);
		webExPoolList.add(webExPool1);

		Mockito.when(webExPoolRepo.findAll()).thenReturn(webExPoolList);
		Mockito.when(webExPoolRepo.findByIdWebExPool(1L)).thenReturn(webExPool);
		Mockito.when(webExPoolRepo.findByIdWebExPool(2L)).thenReturn(webExPool1);

		// Student Subscription Lists
		List<StudentSubscription> studentSubscriptionsLists = new ArrayList<>();

		StudentSubscription studentSubscription = new StudentSubscription();

//		ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");
		studentSubscription.setLastPaymentDate(new Date(2021, 1, 18, 0, 0, 0).toInstant());
//		LocalDate dateMonth = studentSubscription.getLastPaymentDate().atZone(zoneIndia).toLocalDate().plusMonths(1);
//		studentSubscription.setNextPaymentDate(dateMonth.atStartOfDay(zoneIndia).toInstant());

		studentSubscription.setActiveFlag(Boolean.TRUE);
		studentSubscription.setIdBatch(1L);
		studentSubscription.setIdProduct(1L);
		studentSubscription.setIdProductGroup(1L);
		studentSubscription.setIdproductLine(1L);
		studentSubscription.setIdStudent(1L);
		studentSubscription.setIdStudentSubscription(1L);
		studentSubscription.setNextPaymentDate(new Date(2021, 12, 18, 0, 0, 0).toInstant());
		studentSubscription.setPurchaseAmount("500");
		studentSubscription.setPurchaseDate(new Date(2021, 1, 18, 0, 0, 0).toInstant());
		studentSubscription.setPurchaseLevel("PRODUCT");
		studentSubscription.setPurchaseType(null);
		studentSubscription.setSubscriptionEndDate(new Date(2021, 12, 25, 0, 0, 0).toInstant());
		studentSubscription.setSubscriptionType("ANNUAL");

		Mockito.when(studentSubscriptionRepository.findAll()).thenReturn(studentSubscriptionsLists);
		Mockito.when(studentSubscriptionRepository.findByIdStudentAndIdBatch(1L, 1L)).thenReturn(studentSubscription);
		Mockito.when(studentSubscriptionRepository.findByIdBatch(1L)).thenReturn(studentSubscriptionsLists);
		
		List<BatchStudentQuiz> listQuiz = new ArrayList<BatchStudentQuiz>();
		
		BatchStudentQuiz batchQuiz = new BatchStudentQuiz();
		batchQuiz.setAttemptDate(Instant.now());
		batchQuiz.setIdBatchQuizAssignment(1L);
		batchQuiz.setIdBatchStudent(1L);
		batchQuiz.setIdBatchStudentQuiz(1L);
		batchQuiz.setIdStudentSubscription(1L);
		batchQuiz.setQuizCompleteFlag(true);
		batchQuiz.setScore(50f);
		
		listQuiz.add(batchQuiz);
		
		Mockito.when(batchStudentQuizRepository.findByIdStudentSubscriptionAndIdBatchQuizAssignment(1L,1L)).thenReturn(listQuiz);

		List<BatchQuizAssignment> quizAssignmentList= new ArrayList<BatchQuizAssignment>();
		
		BatchQuizAssignment quizAssignment = new BatchQuizAssignment();
		quizAssignment.setBatchQuizName("Weekly Quiz");
		quizAssignment.setIdBatch(1L);
		quizAssignment.setIdBatchQuizAssignment(1L);
		quizAssignment.setIdBatchQuizMeta(1L);
		quizAssignment.setQuizDate(Instant.now());
		
		quizAssignmentList.add(quizAssignment);
		
		Mockito.when(batchQuizAssignmentRespository.findByIdBatchQuizAssignment(1L)).thenReturn(quizAssignment);
		Mockito.when(batchQuizAssignmentRespository.findByIdBatch(1L)).thenReturn(quizAssignmentList);
		
	}

//	@Test
	@SuppressWarnings("rawtypes")
	void testGetBatchMetaData() throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Document doc = batchService.getBatchMetaData();

		assertEquals(200, doc.getStatusCode());
		BatchMetaDataDTO bmdDTO = (BatchMetaDataDTO) doc.getData();
		assertEquals(1, bmdDTO.getClassList().size());
		assertEquals(3, bmdDTO.getDayofweek().size());
		assertEquals(2, bmdDTO.getTeacherList().size());
		assertEquals("Class 1", bmdDTO.getClassList().get(0).getClassStandadName());
		assertEquals(1, bmdDTO.getClassList().get(0).getIdClassStandard());
		assertEquals("TUE_WED_THU", bmdDTO.getDayofweek().get(1).getDayOfWeekCd());
		assertEquals(2, bmdDTO.getDayofweek().get(1).getIdDayofWeekCode());
		assertEquals(true, bmdDTO.getTeacherList().get(1).getActiveFlag());
		assertEquals("test@gmail.com", bmdDTO.getTeacherList().get(1).getEmailId());
		assertEquals("Intermediate", bmdDTO.getTeacherList().get(1).getExpLevel());
		assertEquals(1L, bmdDTO.getTeacherList().get(1).getIdWebexPool());
		assertEquals(formatter.parse(formatter.format(new Date())), bmdDTO.getTeacherList().get(1).getJoinedDate());
		assertEquals(0, bmdDTO.getTeacherList().get(1).getRating());
		assertEquals("teacher address here.", bmdDTO.getTeacherList().get(1).getTeacherAddress());
		assertEquals("teacher description", bmdDTO.getTeacherList().get(1).getTeacherDesc());
		assertEquals(2L, bmdDTO.getTeacherList().get(1).getIdTeacher());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Test
	void testGetBatchInfo() {

		List<Object> obj = null;

		// get all batch by product id
		Document doc = batchService.getBatchInfo(1L, 1L, 1L, -1L, -1L, "-1", "-1");

		assertEquals(200, doc.getStatusCode());

		obj = (List<Object>) doc.getData();

		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get batch for particular teacher for all day and timings
		Document doc2 = batchService.getBatchInfo(1L, 1L, 1L, 1L, -1L, "-1", "-1");

		assertEquals(200, doc2.getStatusCode());

		obj = (List<Object>) doc2.getData();

		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get batch for particular teacher for selected day slots and all timings
		obj = null;

		Document doc3 = batchService.getBatchInfo(1L, 1L, 1L, 1L, 1L, "-1", "-1");

		assertEquals(200, doc3.getStatusCode());
		obj = (List<Object>) doc3.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get batch for ,particular teacher for selected day slots and particular start
		// timing
		obj = null;
		Document doc4 = batchService.getBatchInfo(1L, 1L, 1L, 1L, 1L, "00:00:00", "-1");

		assertEquals(200, doc4.getStatusCode());
		obj = (List<Object>) doc4.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;
		// get batch for ,particular teacher for selected day slots and particular end
		// timing
		Document doc5 = batchService.getBatchInfo(1L, 1L, 1L, 1L, 1L, "-1", "01:00:00");

		assertEquals(200, doc5.getStatusCode());
		obj = (List<Object>) doc5.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;
		// get batch for particular teacher for selected day slots and particular start
		// and end timings
		Document doc6 = batchService.getBatchInfo(1L, 1L, 1L, 1L, 1L, "00:00:00", "01:00:00");

		assertEquals(200, doc6.getStatusCode());
		obj = (List<Object>) doc6.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;
		// get batch for all by product id with respective to day slots
		Document doc7 = batchService.getBatchInfo(1L, 1L, 1L, -1L, 1L, "-1", "-1");

		assertEquals(200, doc7.getStatusCode());
		obj = (List<Object>) doc7.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;
		// get all batch by product id with respective to days and start timing
		Document doc8 = batchService.getBatchInfo(1L, 1L, 1L, -1L, 1L, "00:00:00", "-1");

		assertEquals(200, doc8.getStatusCode());
		obj = (List<Object>) doc8.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;
		// get all batch by product id with respective to days and end timing
		Document doc9 = batchService.getBatchInfo(1L, 1L, 1L, -1L, 1L, "-1", "01:00:00");

		assertEquals(200, doc9.getStatusCode());
		obj = (List<Object>) doc9.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;

		// get all batch by product id with respective to day and start timing and end
		// timing
		Document doc10 = batchService.getBatchInfo(1L, 1L, 1L, -1L, 1L, "00:00:00", "01:00:00");

		assertEquals(200, doc10.getStatusCode());
		obj = (List<Object>) doc10.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;

		// get all batch by product id with respective start timing
		Document doc11 = batchService.getBatchInfo(1L, 1L, 1L, -1L, -1L, "00:00:00", "-1");

		assertEquals(200, doc11.getStatusCode());
		obj = (List<Object>) doc11.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;

		// get all by product id and end time
		Document doc12 = batchService.getBatchInfo(1L, 1L, 1L, -1L, -1L, "-1", "01:00:00");

		assertEquals(200, doc12.getStatusCode());
		obj = (List<Object>) doc12.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		obj = null;

		// get all batch by product id , start and end time
		Document doc13 = batchService.getBatchInfo(1L, 1L, 1L, -1L, -1L, "00:00:00", "01:00:00");

		assertEquals(200, doc13.getStatusCode());
		obj = (List<Object>) doc13.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get all batch by product id , teacher and start timing
		Document doc14 = batchService.getBatchInfo(1L, 1L, 1L, 1L, -1L, "00:00:00", "-1");

		assertEquals(200, doc14.getStatusCode());
		obj = (List<Object>) doc14.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get all batch by product id , teacher and end timing
		Document doc15 = batchService.getBatchInfo(1L, 1L, 1L, 1L, -1L, "-1", "01:00:00");

		assertEquals(200, doc15.getStatusCode());
		obj = (List<Object>) doc15.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

		// get batch for all by product id , teacher id , start and end timing
		Document doc16 = batchService.getBatchInfo(1L, 1L, 1L, 1L, -1L, "00:00:00", "01:00:00");

		assertEquals(200, doc16.getStatusCode());
		obj = (List<Object>) doc16.getData();
		assertEquals(2, ((List<BatchResonseDTO>) obj.get(0)).size());
		assertEquals(1L, ((List<BatchResonseDTO>) obj.get(0)).get(0).getClassStandard().getIdClassStandard());
		assertEquals(LocalTime.parse("00:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), ((List<BatchResonseDTO>) obj.get(0)).get(0).getBatchToTime());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentOccupancy());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getCurrentVacancy());
		assertEquals("MON_TUE_WED", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", ((List<BatchResonseDTO>) obj.get(0)).get(0).getDemoVideoUrl());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getIdBatch());
		assertEquals(1, ((List<BatchResonseDTO>) obj.get(0)).get(0).getProduct().getIdProduct());
		assertEquals("Maths", ((List<BatchResonseDTO>) obj.get(0)).get(0).getSubject().getSubjectName());
		assertEquals(0, ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherRating());
		assertEquals("Intermediate", ((List<BatchResonseDTO>) obj.get(0)).get(0).getTeacherExpLevel());

	}

	// @Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void testGetBatchrecordingDetailsForTeacher() {
		Document doc = batchService.getBatchrecordingDetailsForTeacher(1L);
		assertEquals(200, doc.getStatusCode());

		List<BatchInfoDTO> list = (List<BatchInfoDTO>) doc.getData();
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getIdBatch());
		assertEquals(2, list.get(1).getIdBatch());
		assertEquals("Batch-1-maths", list.get(0).getBatchName());
		assertEquals("Batch-1-maths", list.get(1).getBatchName());
		assertEquals("Maths", list.get(0).getSubjectName());
		assertEquals("Maths", list.get(1).getSubjectName());
		assertEquals(1, list.get(0).getIdTeacher());
		assertEquals(1, list.get(1).getIdTeacher());
		assertEquals(1, list.get(0).getBatchSize());
		assertEquals(1, list.get(1).getBatchSize());
		assertEquals("MON_TUE_WED", list.get(0).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("MON_TUE_WED", list.get(1).getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("Meghana Shetty", list.get(0).getTeacherName());
		assertEquals(LocalTime.parse("00:00:00"), list.get(0).getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), list.get(0).getBatchToTime());

	}

	@SuppressWarnings({ "rawtypes" })
//	@Test
	void testGetBatchshortInfo() {

		Document doc = batchService.getBatchshortInfo(1L);
		assertEquals(200, doc.getStatusCode());

		BatchInfoDTO dto = (BatchInfoDTO) doc.getData();
		assertEquals(1, dto.getIdBatch());
		assertEquals("Batch-1-maths", dto.getBatchName());
		assertEquals("URL", dto.getAttendeeMeetingUrl());
		assertEquals("123", dto.getMeetingPassword());
		assertEquals("Maths", dto.getSubjectName());
		assertEquals("Meghana Shetty", dto.getTeacherName());
		assertEquals(LocalTime.parse("00:00:00"), dto.getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), dto.getBatchToTime());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
	void testGetAttendanceInfo() {
		Document doc = batchService.getAttendanceInfo(1L, 1L);
		assertEquals(200, doc.getStatusCode());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yyyyMMdd = sdf.format(new Date());

		List<BatchAttendanceDTO> attendance = (List<BatchAttendanceDTO>) doc.getData();
		assertEquals(1, attendance.size());
		assertEquals(yyyyMMdd, attendance.get(0).getStart());
		assertEquals("Present", attendance.get(0).getTitle());
	}

	@SuppressWarnings("rawtypes")
	//@Test
	void testCreateBatch() {

		Batch batchInput = new Batch();

		batchInput.setBatchName("Batch-1-maths");
		batchInput.setDemoVideoUrl("http://test.com");
		batchInput.setFromTime("00:00:00");
		batchInput.setToTime("01:00:00");

		CreateBatchRequestDTO request = new CreateBatchRequestDTO();
		request.setBatch(batchInput);
		request.setIdClassStandard(1L);
		request.setIdDayofWeekCode(1L);
		request.setIdProductLine(1L);
		request.setIdSubject(1L);
		request.setIdTeacher(1L);
		request.setIdSyllabus(1L);

		Document doc = batchService.createBatch(request);

		assertEquals(200, doc.getStatusCode());

		Batch obj = (Batch) doc.getData();

		assertEquals(1, obj.getIdBatch());
		assertEquals(LocalTime.parse("00:00:00"), obj.getBatchFromTime());
		assertEquals(LocalTime.parse("01:00:00"), obj.getBatchToTime());
		assertEquals(1, obj.getCurrentOccupancy());
		assertEquals(1, obj.getCurrentVacancy());
		assertEquals("MON_TUE_WED", obj.getDayOfWeekCode().getDayOfWeekCd());
		assertEquals("http://test.com", obj.getDemoVideoUrl());
		assertEquals(1L, obj.getIdProduct());
		assertEquals(1L, obj.getIdTeacher());
		assertEquals(1L, obj.getIdWebexPool());

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Test
	void testGetDayofWeekCode() {

		Document doc = batchService.getDayofWeekCode();
		assertEquals(200, doc.getStatusCode());

		List<DayOfWeekCode> obj = (List<DayOfWeekCode>) doc.getData();

		assertEquals(3, obj.size());
		assertEquals(1L, obj.get(0).getIdDayofWeekCode());
		assertEquals("MON_TUE_WED", obj.get(0).getDayOfWeekCd());
		assertEquals(2L, obj.get(1).getIdDayofWeekCode());
		assertEquals("TUE_WED_THU", obj.get(1).getDayOfWeekCd());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetBatchProdcutLine() {

		Document<ProductLine> doc = batchService.getBatchProdcutLine();

		List<ProductLine> obj = (List<ProductLine>) doc.getData();

		assertEquals(200, doc.getStatusCode());
		assertEquals(4, obj.size());
		assertEquals(1L, obj.get(0).getIdProductLine());
		assertEquals("BATCH", obj.get(0).getProductCategory());
		assertEquals("Batch_1", obj.get(0).getProductCategoryCd());
		assertEquals("Batch of 1 Student", obj.get(0).getProductLine());
		assertEquals(2L, obj.get(1).getIdProductLine());
		assertEquals("BATCH", obj.get(1).getProductCategory());
		assertEquals("Batch_5", obj.get(1).getProductCategoryCd());
		assertEquals("Batch of 5 Student", obj.get(1).getProductLine());

	}

	// @Test
	void testGetBatchLists() {
		fail("Not yet implemented"); // TODO
	}

	// @Test
	void testGenerateWebExToken() {
		fail("Not yet implemented"); // TODO
	}

//	@Test
	void testFetchAllBatchesLists() {
		List<Batch> batchList = batchRepository.findAll();

		assertEquals(2, batchList.size());
		assertEquals(false, batchList.isEmpty());
	}

	// @Test
	void testGetBatchrecordingDetails() {
		fail("Not yet implemented"); // TODO
	}

	// @Test
	void testGetBatchrecordingDetailsOndate() {
		fail("Not yet implemented"); // TODO
	}

	// @Test
	void testListAllWebexRecordingXml() {
		fail("Not yet implemented"); // TODO
	}

	//@Test
	void testSaveBatchRunDetails() {

		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		String yymmdd = formatter2.format(new Date());
		// Set Batch Run Details
		BatchRunDetail batchRunDetail = new BatchRunDetail();
		batchRunDetail.setActualEndTime("11:30 AM");
		batchRunDetail.setActualStartTime("10:30 AM");
		batchRunDetail.setAttendeeMeetingUrl("http://test-webex.com");
		batchRunDetail.setBatchRundate(yymmdd);
		batchRunDetail.setCreatedAt(Instant.now());
		batchRunDetail.setCreatedBy(2L);
		batchRunDetail.setHostMeetingUrl("http://test-webex.com");
		batchRunDetail.setIdBatch(1L);
		batchRunDetail.setIdBatchRunDetail(1L);
		batchRunDetail.setMeetingDescription("Testing Batch Run Details By Teacher For Todays Date");
		batchRunDetail.setMeetingGuestUserToken("jkneruihreuhr873487837");
		batchRunDetail.setMeetingId("1256726727");
		batchRunDetail.setMeetingPassword("123456");
		batchRunDetail.setMeetingTitle("Test Title By Ahmed");
		batchRunDetail.setMeetingUuId("jeruo838482239i");
		batchRunDetail.setUpdatedAt(Instant.now());
		batchRunDetail.setUpdatedBy(2L);

		Document<?> doc = batchService.saveBatchRunDetails(batchRunDetail,1L);

		System.err.println("Doc.getData()===>" + doc.getData());

		BatchRunDetail actualBatchRunDetails = (BatchRunDetail) doc.getData();

		assertEquals(201, doc.getStatusCode());
		assertEquals("11:30 AM", actualBatchRunDetails.getActualEndTime());
		assertEquals("10:30 AM", actualBatchRunDetails.getActualStartTime());
		assertEquals("http://test-webex.com", actualBatchRunDetails.getAttendeeMeetingUrl());
		assertEquals(yymmdd, actualBatchRunDetails.getBatchRundate());
//		assertEquals(Instant.now(), actualBatchRunDetails.getCreatedAt());
		assertEquals(2L, actualBatchRunDetails.getCreatedBy());
		assertEquals("http://test-webex.com", actualBatchRunDetails.getHostMeetingUrl());
		assertEquals(1L, actualBatchRunDetails.getIdBatch());
		assertEquals(1L, actualBatchRunDetails.getIdBatchRunDetail());
		assertEquals("Testing Batch Run Details By Teacher For Todays Date",
				actualBatchRunDetails.getMeetingDescription());
		assertEquals("jkneruihreuhr873487837", actualBatchRunDetails.getMeetingGuestUserToken());
		assertEquals("1256726727", actualBatchRunDetails.getMeetingId());
		assertEquals("123456", actualBatchRunDetails.getMeetingPassword());
		assertEquals("Test Title By Ahmed", actualBatchRunDetails.getMeetingTitle());
		assertEquals("jeruo838482239i", actualBatchRunDetails.getMeetingUuId());
//		assertEquals(Instant.now(), actualBatchRunDetails.getUpdatedAt());
		assertEquals(2L, actualBatchRunDetails.getUpdatedBy());
	}

	// @Test
	void testGetBatchInformationByIdBatch() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFetchWebExHostCredentialsByIdWebexPool() {

		List<WebExPool> webExPoolLists = webExPoolRepo.findAll();

		assertEquals(2, webExPoolLists.size());
		assertEquals(false, webExPoolLists.isEmpty());

		WebExPool webExPool1 = webExPoolRepo.findByIdWebExPool(1L);
		assertEquals("E2f780892781@", webExPool1.getWebExPassword());
		assertEquals("ahmedreza@zeesense.com", webExPool1.getWebExUserId());
		assertEquals(Boolean.TRUE, webExPool1.getAvailableFlag());

		WebExPool webExPool2 = webExPoolRepo.findByIdWebExPool(2L);
		assertEquals("Vista@2020", webExPool2.getWebExPassword());
		assertEquals("samsadhasan@hotmail.com", webExPool2.getWebExUserId());
		assertEquals(Boolean.TRUE, webExPool2.getAvailableFlag());
	}

	@SuppressWarnings("deprecation")
	@Test
	void testTakeStudentAttendanceWhileJoiningABatch() {

		StudentSubscription fetchedStudentSubscription = studentSubscriptionRepository.findByIdStudentAndIdBatch(1L,
				1L);

		assertEquals(1L, fetchedStudentSubscription.getIdBatch());
		assertEquals(Boolean.TRUE, fetchedStudentSubscription.getActiveFlag());
		assertEquals(1L, fetchedStudentSubscription.getIdProduct());
		assertEquals(1L, fetchedStudentSubscription.getIdProductGroup());
		assertEquals(1L, fetchedStudentSubscription.getIdproductLine());
		assertEquals(1L, fetchedStudentSubscription.getIdStudent());
		assertEquals(1L, fetchedStudentSubscription.getIdStudentSubscription());
		assertEquals(new Date(2021, 1, 18, 0, 0, 0).toInstant(), fetchedStudentSubscription.getLastPaymentDate());
		assertEquals(new Date(2021, 12, 18, 0, 0, 0).toInstant(), fetchedStudentSubscription.getNextPaymentDate());
		assertEquals("500", fetchedStudentSubscription.getPurchaseAmount());
		assertEquals(new Date(2021, 1, 18, 0, 0, 0).toInstant(), fetchedStudentSubscription.getPurchaseDate());
		assertEquals("PRODUCT", fetchedStudentSubscription.getPurchaseLevel());
		assertEquals(null, fetchedStudentSubscription.getPurchaseType());
		assertEquals("ANNUAL", fetchedStudentSubscription.getSubscriptionType());
		assertEquals(new Date(2021, 12, 25, 0, 0, 0).toInstant(), fetchedStudentSubscription.getSubscriptionEndDate());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yyyyMMdd = sdf.format(new Date());

		System.err.println(sdf + "====>" + yyyyMMdd);

		BatchRunDetail existingBatchRunDetail = batchRunDetailRepository.findByIdBatchAndBatchRundate(1L, yyyyMMdd);

		assertEquals("11:30 AM", existingBatchRunDetail.getActualEndTime());
		assertEquals("10:30 AM", existingBatchRunDetail.getActualStartTime());
		assertEquals("http://test-webex.com", existingBatchRunDetail.getAttendeeMeetingUrl());
		assertEquals(2L, existingBatchRunDetail.getCreatedBy());
		assertEquals("http://test-webex.com", existingBatchRunDetail.getHostMeetingUrl());
		assertEquals(1L, existingBatchRunDetail.getIdBatch());
		assertEquals(1L, existingBatchRunDetail.getIdBatchRunDetail());
		assertEquals("Testing Batch Run Details By Teacher For Todays Date",
				existingBatchRunDetail.getMeetingDescription());
		assertEquals("jkneruihreuhr873487837", existingBatchRunDetail.getMeetingGuestUserToken());
		assertEquals("1256726727", existingBatchRunDetail.getMeetingId());
		assertEquals("123456", existingBatchRunDetail.getMeetingPassword());
		assertEquals("Test Title By Ahmed", existingBatchRunDetail.getMeetingTitle());
		assertEquals("jeruo838482239i", existingBatchRunDetail.getMeetingUuId());
		assertEquals(2L, existingBatchRunDetail.getUpdatedBy());

		BatchStudentAttendance studentAttendance = batchStudentAttendanceRepository
				.findByIdBatchAndIdStudentSubscrAndBatchRunDate(1L, 1L, yyyyMMdd);

//		attendace.setAbsentPresentFlag(true);
//		attendace.setBatchRunDate(yyyyMMdd);
//		attendace.setIdBatch(1L);
//		attendace.setIdBatchStudentAttendance(1L);
//		attendace.setIdStudentSubscr(1L);

		assertEquals(true, studentAttendance.getAbsentPresentFlag());
		assertEquals(yyyyMMdd, studentAttendance.getBatchRunDate());
		assertEquals(1L, studentAttendance.getIdBatch());
		assertEquals(1L, studentAttendance.getIdBatchStudentAttendance());
		assertEquals(1L, studentAttendance.getIdStudentSubscr());

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//fix the testcase @Test
	void testGetNotificationForUpcomingBatchTest() throws ParseException {
		//Mockito.when(batchQuizAssignmentRespository.findByIdBatchQuizAssignment(1L)).thenReturn(quizAssignment);
		
		//BatchQuizAssignment list=batchQuizAssignmentRespository.findByIdBatchQuizAssignment(1L);
		Document doc = batchService.getNotificationForUpcomingBatchTest(1L,1L);
		assertEquals(200, doc.getStatusCode());
		List<BatchTestNotificationDTO> list = (List<BatchTestNotificationDTO>) doc.getData();
		assertEquals(1L, list.size());
		//assertEquals("Weekly Quiz", list.get(0).getBatchQuizName());
		//assertEquals(Instant.now(), list.get(0).getQuizDate());
		//assertEquals(1L, list.get(0).getIdBatchQuizAssignment());
		//assertEquals(1L, list.get(0).getIdBatchStudentQuiz());
		//assertEquals(true, list.get(0).getTakeTest());
	}

}
