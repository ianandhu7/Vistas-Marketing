package co.vistafoundation.vlearning.batch.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.vistafoundation.vlearning.batch.dto.BatchAttendanceDTO;
import co.vistafoundation.vlearning.batch.dto.BatchInfoDTO;
import co.vistafoundation.vlearning.batch.dto.BatchListDTO;
import co.vistafoundation.vlearning.batch.dto.BatchMetaDataDTO;
import co.vistafoundation.vlearning.batch.dto.BatchQuizQuestionDTO;
import co.vistafoundation.vlearning.batch.dto.BatchResonseDTO;
import co.vistafoundation.vlearning.batch.dto.BatchResponseDTO;
import co.vistafoundation.vlearning.batch.dto.BatchScheduleDTO;
import co.vistafoundation.vlearning.batch.dto.BatchSubscriptionExpiryDTO;
import co.vistafoundation.vlearning.batch.dto.BatchTestNotificationDTO;
import co.vistafoundation.vlearning.batch.dto.CreateBatchRequestDTO;
import co.vistafoundation.vlearning.batch.dto.ECAPersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingBatchListResponseDTO;
import co.vistafoundation.vlearning.batch.dto.PersonalCoachingFilterDTO;
import co.vistafoundation.vlearning.batch.dto.TeacherBatchDetailsDTO;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchCalender;
import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.batch.model.BatchQuizAnwser;
import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;
import co.vistafoundation.vlearning.batch.model.BatchRunDetail;
import co.vistafoundation.vlearning.batch.model.BatchRunRecording;
import co.vistafoundation.vlearning.batch.model.BatchStudentAttendance;
import co.vistafoundation.vlearning.batch.model.BatchStudentQuizAnswer;
import co.vistafoundation.vlearning.batch.model.BatchStudentQuizQuestion;
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
import co.vistafoundation.vlearning.batch.model.SpecialOfferProduct;
import co.vistafoundation.vlearning.batch.repository.BatchCalenderRepository;
import co.vistafoundation.vlearning.batch.repository.BatchGroupRepository;
import co.vistafoundation.vlearning.batch.repository.BatchQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRunDetailsRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRunRecordingRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentAttendanceRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentQuizAnswerRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.DayofWeekCodeRepository;
import co.vistafoundation.vlearning.batch.repository.SpecialOfferProductRepository;
import co.vistafoundation.vlearning.batch.service.BatchService;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.WebExMetaDataDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.notification.service.NotificationService;
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
import co.vistafoundation.vlearning.specialoffer.dto.SpecialOfferDTO;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;
import co.vistafoundation.vlearning.specialoffer.repository.SpecialOfferRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionBatchDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherAvailability;
import co.vistafoundation.vlearning.user.model.TeacherSubject;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.user.repository.TeacherAvailabilityRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.TeacherSubjectRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.utils.TimeComparison;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;

/**
 * @author NaveenKumar
 * 
 **/
/**
 * @author vk
 *
 */
@Service
public class BatchServiceImpl implements BatchService {

	@Autowired
	ClassRepository classRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	ProductLineRepository productLineRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	DayofWeekCodeRepository dayofWeekCodeRepository;

	@Autowired
	BatchRunDetailsRepository batchRunDetailRepository;

	@Autowired
	BatchStudentAttendanceRepository batchStudentAttendanceRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	BatchRunRecordingRepository batchRunRecordingRepository;

	@Autowired
	BatchRunDetailsRepository batchRunDetailsRepository;

	@Autowired
	WebExPoolRepository webExPoolRepo;

	@Autowired
	BatchCalenderRepository batchCalenderRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	BatchStudentQuizRepository batchStudentQuizRepository;

	@Autowired
	BatchStudentQuizQuestionRepository batchStudentQuizQuestionRepository;

	@Autowired
	BatchQuizQuestionRepository batchQuizQuestionRepository;

	@Autowired
	BatchStudentQuizAnswerRepository batchStudentQuizAnswerRepository;

	@Autowired
	BatchQuizAssignmentRespository batchQuizAssignmentRespository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	LevelExtraCurricularRepository levelExtraCurricularRepository;

	@Autowired
	TeacherSubjectRepository teacherSubjectRepository;

	@Autowired
	UserDeviceRepository userDeviceRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	TeacherAvailabilityRepository teacherAvailabilityRepository;

	@Autowired
	SpecialOfferRepository specialOfferRepository;

	@Autowired
	SpecialOfferProductRepository specialOfferProductRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	BatchGroupRepository batchGroupRepository;



	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchMetaData() {

		Document result = new Document();
		try {
			BatchMetaDataDTO batchDto = new BatchMetaDataDTO();

			List<ProductGroup> pgList = productGroupRepository.findAll();

			if (pgList.isEmpty())
				throw new NullPointerException("Product Group is empty!");

			Set<ClassStandard> tempClass = new HashSet<ClassStandard>();

			for (ProductGroup pg : pgList) {

				if (pg.getIdProductLine() != 5 && pg.getIdProductLine() != 6 && pg.getIdProductLine() != 7) {

					ClassStandard temp = classRepository.findByIdClassStandard(pg.getIdClassStandard());

					if (temp == null)
						throw new NullPointerException("invalid IdClassStandard");

					tempClass.add(temp);

				}
			}

			List<ClassStandard> classList = tempClass.stream()
					.sorted((s1, s2) -> s1.getIdClassStandard().compareTo(s2.getIdClassStandard()))
					.collect(Collectors.toList());
			classList = classList.stream().filter(e -> !(e.getClassStandadName().equals("NA")))
					.collect(Collectors.toList());
			batchDto.setClassList(classList);

			List<Teacher> teacherList = teacherRepository.findAll();

			if (teacherList.isEmpty())
				throw new NullPointerException("Teacher list is empty");

			batchDto.setTeacherList(teacherList);

			// day of week slot
			List<DayOfWeekCode> daysCodeList = dayofWeekCodeRepository.findAll();

			if (daysCodeList.isEmpty())
				throw new NullPointerException("No Day of week code found.");

			batchDto.setDayofweek(daysCodeList);

			result.setData(batchDto);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchInfo(Long classId, Long subjectId, Long productLineId, Long teacherId, Long daySlots,
			String fromTime, String toTime) {

		// intalizing required data for batchRespose
		ClassStandard classStandard = null;
		LocalTime batchFromTime = null;
		LocalTime batchToTime = null;
		Teacher teacher = null;
		Subject subject = null;

		Document result = new Document();
		try {

			/// checking all the parameter valid constrain start here
			classStandard = classRepository.findByIdClassStandard(classId);

			if (classStandard == null)
				throw new NullPointerException("Invalid ClassStandard Id provided");

			if (!fromTime.equals("-1"))
				batchFromTime = LocalTime.parse(fromTime);

			if (!toTime.equals("-1"))
				batchToTime = LocalTime.parse(toTime);

			if (teacherId != -1) {
				teacher = teacherRepository.findByIdTeacher(teacherId);

				if (teacher == null)
					throw new NullPointerException("Invalid Teacher Id provided");

			}

			subject = subjectRepository.findByIdSubject(subjectId);

			if (subject == null)
				throw new NullPointerException("Invalid Subject Id provided");

			ProductGroup prodGroup = productGroupRepository.findByIdClassStandardAndIdProductLine(classId,
					productLineId);
			if (prodGroup == null)
				throw new NullPointerException("Invalid ProductLine Id ");

			System.out.println("product group id:" + prodGroup.getIdProductGroup());

			Product prod = productRepository.findByIdProductGroupAndIdSubjectAndActiveFlag(
					prodGroup.getIdProductGroup(),
					subject.getIdSubject(),Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("Invalid ProductGroup Id ");

			List<Object> batch = new ArrayList<Object>();

			// validation ends here

			System.out.println("product id:" + prod.getIdProduct());

			// get batch details for all daySlots and timing for particular Teacher
			if (teacherId != -1 && daySlots == -1 && fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get particular teacher for all day and timings");

				List<Batch> tempList = batchRepository.findByIdProductAndIdTeacher(prod.getIdProduct(), teacherId);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());

					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details for particular daySlots and all timings for a particular
			// Teacher

			else if (teacherId != -1 && daySlots != -1 && fromTime.equals("-1") && toTime.equals("-1")) {
				System.out.println("get particular teacher for selected  day slots  and all timings");

				List<Batch> tempList = batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(
						prod.getIdProduct(), teacherId, daySlots);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());

					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details for particular daySlots and for particular start timings
			// and for a particular Teacher
			else if (teacherId != -1 && daySlots != -1 && !fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get particular teacher for selected  day slots  and  particular start timing");

				List<Batch> tempList = batchRepository
						.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(
								prod.getIdProduct(), teacherId, daySlots, batchFromTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);

			}

			// get batch details for particular daySlots and for particular end timings and
			// for a particular Teacher
			else if (teacherId != -1 && daySlots != -1 && fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get particular teacher for selected  day slots  and  particular end timing");

				List<Batch> tempList = batchRepository
						.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndAndBatchToTime(
								prod.getIdProduct(), teacherId, daySlots, batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}
				batch.add(brdList);
			}

			// get batch details for particular daySlots and for particular start and end
			// timings for a particular Teacher
			else if (teacherId != -1 && daySlots != -1 && !fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println(
						"get particular teacher for selected  day slots  and  particular start and  end timings");

				List<Batch> tempList = batchRepository
						.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(
								prod.getIdProduct(), teacherId, daySlots, batchFromTime, batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);

			}

			// get batch details based on product id and irrespective of timings and days
			// slots
			else if (teacherId == -1 && daySlots == -1 && fromTime.equals("-1") && toTime.equals("-1")) {
				// get all batch on class standard and product , no time slots and days slots
				// filters are not applicable
				System.out.println("get all by product id");
				List<Batch> tempList = batchRepository.findByIdProduct(prod.getIdProduct());

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details based on product id and days slots irrespective of teacher
			// and timings
			else if (teacherId == -1 && daySlots != -1 && fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get all by product id with respective to day slots");
				List<Batch> tempList = batchRepository
						.findByIdProductAndDayOfWeekCode_idDayofWeekCode(prod.getIdProduct(), daySlots);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}
			// get batch details based on product id and days slots , start irrespective of
			// teacher and end-timings
			else if (teacherId == -1 && daySlots != -1 && !fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get all by product id with respective to days and start timing ");
				List<Batch> tempList = batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(
						prod.getIdProduct(), daySlots, batchFromTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}
			// get batch details based on product id and days slots , end timing
			// irrespective of teacher and start-timings
			else if (teacherId == -1 && daySlots != -1 && fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id with respective to days and end timing ");
				List<Batch> tempList = batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchToTime(
						prod.getIdProduct(), daySlots, batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details based on product id and days slots , start-timings &end
			// timing irrespective of teacher and
			else if (teacherId == -1 && daySlots != -1 && !fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id with respective to day and start timing and end timing");
				List<Batch> tempList = batchRepository
						.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(
								prod.getIdProduct(), daySlots, batchFromTime, batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details based on product id , start-timings irrespective of teacher
			// and day_slots and end-timing
			else if (teacherId == -1 && daySlots == -1 && !fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get all by product id with respective start timing");
				List<Batch> tempList = batchRepository.findByIdProductAndBatchFromTime(prod.getIdProduct(),
						batchFromTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			// get batch details based on product id and end timing & irrespective of
			// teacher start-time and day slots
			else if (teacherId == -1 && daySlots == -1 && fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id and end time");
				List<Batch> tempList = batchRepository.findByIdProductAndBatchToTime(prod.getIdProduct(), batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}
			// get batch details based on product id , start-timing and end timing &
			// irrespective of teacher and day slots
			else if (teacherId == -1 && daySlots == -1 && !fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id , start and  end time");
				List<Batch> tempList = batchRepository
						.findByIdProductAndBatchFromTimeAndBatchToTime(prod.getIdProduct(), batchFromTime, batchToTime);

				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			else if (teacherId != -1 && daySlots == -1 && !fromTime.equals("-1") && toTime.equals("-1")) {

				System.out.println("get all by product id ,  teacher  and start timing  ");
				List<Batch> tempList = batchRepository.findByIdProductAndIdTeacherAndBatchFromTime(prod.getIdProduct(),
						teacherId, batchFromTime);
				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			else if (teacherId != -1 && daySlots == -1 && fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id ,  teacher  and end timing  ");
				List<Batch> tempList = batchRepository.findByIdProductAndIdTeacherAndBatchToTime(prod.getIdProduct(),
						teacherId, batchToTime);
				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			else if (teacherId != -1 && daySlots == -1 && !fromTime.equals("-1") && !toTime.equals("-1")) {

				System.out.println("get all by product id ,  teacher id , start and  end timing  ");
				List<Batch> tempList = batchRepository.findByIdProductAndIdTeacherAndBatchFromTimeAndBatchToTime(
						prod.getIdProduct(), teacherId, batchFromTime, batchToTime);
				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			else {
				// this if any above condition not matched
				List<Batch> tempList = batchRepository.findAll();
				System.out.println("No conditions getting matched , Executing else block.");
				if (tempList.isEmpty())
					throw new AppException("No Batch list found.");

				List<BatchResonseDTO> brdList = new ArrayList<BatchResonseDTO>();
				for (Batch bat : tempList) {
					BatchResonseDTO batDto = new BatchResonseDTO();

					batDto.setIdBatch(bat.getIdBatch());
					batDto.setProduct(prod);
					batDto.setClassStandard(classStandard);
					batDto.setSubject(subject);
					teacher = teacherRepository.findByIdTeacher(bat.getIdTeacher());
					batDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
					batDto.setTeacherExpLevel(teacher.getExpLevel());
					batDto.setTeacherRating(teacher.getRating());
					batDto.setBatchFromTime(bat.getBatchFromTime());
					batDto.setBatchToTime(bat.getBatchToTime());
					batDto.setDayOfWeekCode(bat.getDayOfWeekCode());
					batDto.setCurrentOccupancy(bat.getCurrentOccupancy());
					batDto.setCurrentVacancy(bat.getCurrentVacancy());
					batDto.setDemoVideoUrl(bat.getDemoVideoUrl());
					batDto.setBatchName(bat.getBatchName());
					brdList.add(batDto);
				}

				batch.add(brdList);
			}

			result.setData(batch);
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

	/**
	 * @param CreateBatchRequestDTO
	 * @return Batch
	 **/
	@Transactional
	public Document<Batch> createBatch(CreateBatchRequestDTO request) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		Document<Batch> result = new Document<>();
		try {

			Batch response = null;

			if (request.getCategory().equalsIgnoreCase("ACADEMIC")) {

				ProductGroup pg = productGroupRepository
						.findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(
								request.getIdClassStandard(), request.getIdProductLine(), request.getIdSyllabus(),
								"NA");

				if (pg == null)
					throw new NullPointerException("Invalid product line id null.");

				Product prod = productRepository.findByIdProductGroupAndIdSubjectAndIdStateAndExtraCurrCategoryAndActiveFlag(
						pg.getIdProductGroup(), request.getIdSubject(), request.getIdState(),
						pg.getExtraCurrCategory(),Boolean.TRUE);

				if (prod == null)
					throw new NullPointerException("No product found for the product group id provided.");

				Teacher teacher = teacherRepository.findByIdTeacher(request.getIdTeacher());

				if (teacher == null)
					throw new NullPointerException("Invalid Teacher id.");

				if (teacher.getIdWebexPool() == null)
					throw new NullPointerException("No WebExPool Id Assigned to the selected teacher");
				// creating data for 1st product from the index

				TeacherSubject ts = teacherSubjectRepository.findByTeacher_IdTeacherAndIdSyllabusAndIdSubject(
						teacher.getIdTeacher(), request.getIdSyllabus(), request.getIdSubject());

				if (ts == null)
					throw new NullPointerException("Teacher dosent have any proficiency on the selected subject.");

				WebExPool pool = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

				if (pool == null)
					throw new AppException("Invalid webExpool Id.");

				if (request.getScheduleList().isEmpty())
					throw new NullPointerException("Atleast one batch schedule time is needed.");

				Batch temp = request.getBatch();

				temp.setIdTeacher(teacher.getIdTeacher());
				temp.setIdProduct(prod.getIdProduct());
				//temp.setIdWebexPool(pool.getIdWebExPool());
				// set actual batch size from product
				temp.setCurrentOccupancy(0);
				temp.setCurrentVacancy(prod.getBatchSize());
				temp.setDemoVideoUrl(ts.getSubIntroVideo());
				temp.setActiveFlag(Boolean.TRUE);
				temp.setBatchFromTime(null);
				temp.setBatchToTime(null);
				boolean specialOfferFlag = request.getIdSpecialOffers().isEmpty() ? false : true;
				temp.setSpecialOfferFlag(specialOfferFlag);
				temp.setIdBatchGroup(request.getIdBatchGroup());

				List<Batch> batchList = batchRepository.findByIdTeacherAndActiveFlag(teacher.getIdTeacher(),
						Boolean.TRUE);

				if (!batchList.isEmpty()) {

					for (BatchScheduleDTO data : request.getScheduleList()) {

						List<BatchCalender> calenderList = batchCalenderRepository.findByBatchInAndDayOfWeek(batchList,
								data.getDayOfWeek());
						if (!calenderList.isEmpty()) {
							for (BatchCalender bc : calenderList) {
								boolean batchExists = false;

								batchExists = TimeComparison.checkTimeInBetween(
										LocalTime.parse(bc.getFromTime()).format(timeFormat),
										LocalTime.parse(bc.getToTime()).format(timeFormat),
										LocalTime.parse(data.getFromTime()).format(timeFormat),
										LocalTime.parse(data.getToTime()).format(timeFormat));

								if (batchExists) {
									result.setData(null);
									result.setMessage(
											"Duplicate batch entry, with existing teacher at same time and same day");
									result.setStatusCode(HttpStatus.CONFLICT.value());
									return result;
								}

							}
						}
					}

				}
		// second level verification from backend for checking conflicts within batch-group batches.
				if (request.getIdBatchGroup() != null) 
				{   boolean conflictFlag = false;
					List<Batch> bgBatchList = batchRepository.findByIdBatchGroupAndActiveFlag(request.getIdBatchGroup(), true);
					
					if (!bgBatchList.isEmpty()) 
					{
						for (BatchScheduleDTO data : request.getScheduleList()) 
						{
							conflictFlag = checkConflictsOnBatch(bgBatchList, data.getFromTime(), data.getToTime(), data.getDayOfWeek());
							
							if (conflictFlag) {
								result.setData(null);
								result.setMessage(
										"Conflict found with some other batch , with in this Batch Group.");
								result.setStatusCode(HttpStatus.CONFLICT.value());
								return result;
							}
						}
					}
				}

				Batch res = batchRepository.save(temp);

				if (!request.getIdSpecialOffers().isEmpty()) {
					List<SpecialOfferProduct> sopList = new ArrayList<SpecialOfferProduct>();

					for (Long id : request.getIdSpecialOffers()) {
						SpecialOffer so = specialOfferRepository.findByIdSpecialOffer(id);

						if (so == null)
							throw new NullPointerException("Special Offer Not Valid.");

						SpecialOfferProduct sop = new SpecialOfferProduct();
						sop.setIdBatch(res.getIdBatch());
						sop.setIdProduct(res.getIdProduct());
						sop.setIdSpecialOffer(id);
						sopList.add(sop);

					}

					specialOfferProductRepository.saveAll(sopList);

				}

				List<BatchCalender> bcList = new ArrayList<>();
				for (BatchScheduleDTO data : request.getScheduleList()) {
					// Set the Batch Run Calendar
					BatchCalender batchCalender = new BatchCalender();
					batchCalender.setBatch(res);
					batchCalender.setDayOfWeek(data.getDayOfWeek());
					batchCalender.setFromTime(data.getFromTime());
					batchCalender.setToTime(data.getToTime());
					bcList.add(batchCalender);

				}

				batchCalenderRepository.saveAll(bcList);

				response = res;
			}

			else {

				if (request.getIdLevelExtraCurricular() == null)
					throw new NullPointerException("IdLevelExtraCurricular cannot be null");

				LevelExtraCurricular lec = levelExtraCurricularRepository
						.findByIdLevelExtraCurricular(request.getIdLevelExtraCurricular());

				if (lec == null)
					throw new NullPointerException("Invalid IdLevelExtraCurricular.");

				ProductGroup pg = productGroupRepository
						.findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(
								request.getIdClassStandard(), request.getIdProductLine(), request.getIdSyllabus(),
								lec.getLevel());

				if (pg == null)
					throw new NullPointerException(
							"Invalid product group found for the select class standard ,syllabus, productline and category");

				Product prod = productRepository.findByIdProductGroupAndIdSubjectAndIdStateAndExtraCurrCategoryAndActiveFlag(
						pg.getIdProductGroup(), request.getIdSubject(), request.getIdState(),
						pg.getExtraCurrCategory(),Boolean.TRUE);

				if (prod == null)
					throw new NullPointerException("No product found for the product group id provided.");

				Teacher teacher = teacherRepository.findByIdTeacher(request.getIdTeacher());

				if (teacher == null)
					throw new NullPointerException("Invalid Teacher id.");
				// creating data for 1st product from the index

				TeacherSubject ts = teacherSubjectRepository.findByTeacher_IdTeacherAndIdSyllabusAndIdSubject(
						teacher.getIdTeacher(), request.getIdSyllabus(), request.getIdSubject());

				if (ts == null)
					throw new NullPointerException("Teacher dosent have any proficiency on the selected subject.");

				if (teacher.getIdWebexPool() == null)
					throw new NullPointerException("No WebExPool Id Assigned to the selected teacher");

				WebExPool pool = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

				if (pool == null)
					throw new AppException("Invalid webExpool Id.");

				if (request.getScheduleList().isEmpty())
					throw new NullPointerException("Atleast one batch schedule time is needed.");

				Batch temp = request.getBatch();

				temp.setIdTeacher(teacher.getIdTeacher());
				temp.setIdProduct(prod.getIdProduct());
				temp.setDayOfWeekCode(null);
				//temp.setIdWebexPool(pool.getIdWebExPool());
				temp.setActiveFlag(Boolean.TRUE);
				temp.setBatchFromTime(null);
				temp.setBatchToTime(null);
				boolean specialOfferFlag = request.getIdSpecialOffers().isEmpty() ? false : true;
				temp.setSpecialOfferFlag(specialOfferFlag);

				List<Batch> batchList = batchRepository.findByIdTeacherAndActiveFlag(teacher.getIdTeacher(),
						Boolean.TRUE);

				if (!batchList.isEmpty()) {

					for (BatchScheduleDTO data : request.getScheduleList()) {

						List<BatchCalender> calenderList = batchCalenderRepository.findByBatchInAndDayOfWeek(batchList,
								data.getDayOfWeek());
						if (!calenderList.isEmpty()) {
							for (BatchCalender bc : calenderList) {
								boolean batchExists = false;

								batchExists = TimeComparison.checkTimeInBetween(
										LocalTime.parse(bc.getFromTime()).format(timeFormat),
										LocalTime.parse(bc.getToTime()).format(timeFormat),
										LocalTime.parse(data.getFromTime()).format(timeFormat),
										LocalTime.parse(data.getToTime()).format(timeFormat));

								if (batchExists) {
									result.setData(null);
									result.setMessage(
											"Duplicate batch entry, with existing teacher at same time and same day");
									result.setStatusCode(HttpStatus.CONFLICT.value());
									return result;
								}

							}
						}
					}

				}

				Batch res = batchRepository.save(temp);

				if (!request.getScheduleList().isEmpty() && !request.getIdSpecialOffers().isEmpty()) {
					List<SpecialOfferProduct> sopList = new ArrayList<SpecialOfferProduct>();

					for (Long id : request.getIdSpecialOffers()) {
						SpecialOffer so = specialOfferRepository.findByIdSpecialOffer(id);

						if (so == null)
							throw new NullPointerException("Special Offer Not Valid.");

						SpecialOfferProduct sop = new SpecialOfferProduct();
						sop.setIdBatch(res.getIdBatch());
						sop.setIdProduct(res.getIdProduct());
						sop.setIdSpecialOffer(id);
						sopList.add(sop);

					}

					specialOfferProductRepository.saveAll(sopList);

				}

				List<BatchCalender> bcList = new ArrayList<>();
				for (BatchScheduleDTO data : request.getScheduleList()) {
					// Set the Batch Run Calendar
					BatchCalender batchCalender = new BatchCalender();
					batchCalender.setBatch(res);
					batchCalender.setDayOfWeek(data.getDayOfWeek());
					batchCalender.setFromTime(data.getFromTime());
					batchCalender.setToTime(data.getToTime());
					bcList.add(batchCalender);

				}

				batchCalenderRepository.saveAll(bcList);

				response = res;
			}

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception exp) {

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@Override
	public Document<List<DayOfWeekCode>> getDayofWeekCode() {

		Document<List<DayOfWeekCode>> result = new Document<>();

		try {
			List<DayOfWeekCode> codeList = dayofWeekCodeRepository.findAll();

			if (codeList.isEmpty())
				throw new AppException("No Day of week code data found");

			result.setData(codeList);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document getBatchProdcutLine() {
		Document result = new Document();
		try {
			List<ProductLine> prodList = productLineRepository.findByProductCategory("BATCH");
			if (prodList.isEmpty())
				throw new AppException("No Batch Product Line  data found");
			result.setData(prodList);
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
	public List<Batch> getBatchLists(Long idTeacher) {

		Teacher teacher = teacherRepository.getTeacherByUser_UserSurId(idTeacher);

		List<Batch> batchLists = batchRepository.findByIdTeacher(teacher.getIdTeacher());

		return batchLists;
	}

	@Override
	public String generateWebExToken() {
		String token = "MDRkNzBkYTYtYTljZS00MWNlLWE1M2EtY2E3MTk2MGYzMDg2NDU1MDk5MzktMWQ5_P0A1_a29d299b-3ddc-4f93-9618-7c42e86a5223";
		return token;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchshortInfo(Long idBatch) {
		Document result = new Document();

		try {

			BatchInfoDTO batchInfoDTO = new BatchInfoDTO();

			Batch batch = batchRepository.findByIdBatch(idBatch);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yyyyMMdd = sdf.format(new Date());

			BatchRunDetail batchRunDetail = batchRunDetailRepository.fetchBatchRunDetailsByIdBatch(idBatch, yyyyMMdd);

			if (batch == null)
				throw new NullPointerException("Cannot Find the Batch data");
			batchInfoDTO.setIdBatch(batch.getIdBatch());
			batchInfoDTO.setBatchName(batch.getBatchName());
			if (batchRunDetail != null) {
				batchInfoDTO.setAttendeeMeetingUrl(batchRunDetail.getAttendeeMeetingUrl());
				batchInfoDTO.setMeetingPassword(batchRunDetail.getMeetingPassword());
			}

			Product prod = new Product();
			prod = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

			if (prod == null)
				throw new NullPointerException("Cannot Find the Product data");
			
			
			batchInfoDTO.setBatchSize(prod.getBatchSize());
			batchInfoDTO.setCurrentVacancy(batch.getCurrentVacancy());
			
			List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());
			batchInfoDTO.setBatchCalender(bcList);
			
			batchInfoDTO.setIdProduct(prod.getIdProduct());
			batchInfoDTO.setProductName(prod.getProductName());
   
			Subject subject = new Subject();
			subject = subjectRepository.findByIdSubject(prod.getIdSubject());
			
			if (subject == null)
				throw new NullPointerException("Cannot Find the subject data");
			batchInfoDTO.setSubjectName(subject.getSubjectName());

			Teacher teacher = new Teacher();
			teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());
			if (teacher == null)
				throw new NullPointerException("Cannot Find the subject data");
			
			batchInfoDTO.setTeacherName(teacher.getUser().getFirstName() + ' ' + teacher.getUser().getLastName());
			batchInfoDTO.setIdTeacher(teacher.getIdTeacher());
			
			ClassStandard cs = classRepository.findByIdClassStandard(prod.getIdClassStandard());
			
			if (cs == null)
				throw new NullPointerException("Cannot Find the ClassStandard data");
			
			batchInfoDTO.setClassStandard(cs.getClassStandadName());

			result.setData(batchInfoDTO);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAttendanceInfo(Long idBatch, Long idStudentSubscription) {
		Document result = new Document();
		try {

			List<BatchAttendanceDTO> attendance = new ArrayList<BatchAttendanceDTO>();

			List<BatchStudentAttendance> batchStudentAttendance = new ArrayList<BatchStudentAttendance>();

			batchStudentAttendance = batchStudentAttendanceRepository.findByIdBatchAndIdStudentSubscr(idBatch,
					idStudentSubscription);

			if (batchStudentAttendance.isEmpty()) {
				throw new NullPointerException("Cannot Find the Batch Attendance data");
			}

			for (BatchStudentAttendance atte : batchStudentAttendance) {
				BatchAttendanceDTO object = new BatchAttendanceDTO();

				object.setStart(atte.getBatchRunDate());
				if (atte.getAbsentPresentFlag() == true)
					object.setTitle("Present");
				else
					object.setTitle("Absent");

				attendance.add(object);
			}

			result.setData(attendance);
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

	@Override
	public List<Batch> fetchAllBatchesLists() {
		List<Batch> findAll = batchRepository.findAll();
		return findAll;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchrecordingDetails(Long idStudent) {
		Document result = new Document();
		try {

			List<BatchInfoDTO> list = new ArrayList<BatchInfoDTO>();

			List<StudentSubscriptionBatchDTO> studentSubscriptionlist = new ArrayList<StudentSubscriptionBatchDTO>();

			String[] subscriptionType = new String[] { "BATCH", "BATCH_EXTRA_CUR" };

			for (String type : subscriptionType) {
				List<StudentSubscriptionBatchDTO> allBatches = studentSubscriptionRepository.getAllBatches(idStudent,
						type, true);
				if (!allBatches.isEmpty()) {
					studentSubscriptionlist.addAll(allBatches);
				}
			}

			if (studentSubscriptionlist.isEmpty())
				throw new NullPointerException("Cannot Find the data");

			/*
			 * get the batch list for recordings
			 * 
			 */

			for (StudentSubscriptionBatchDTO studentSubscriptionBatchDTO : studentSubscriptionlist) {
				BatchInfoDTO batchInfoDTO = new BatchInfoDTO();
				Batch batch = new Batch();

				batch = batchRepository.findByIdBatch(studentSubscriptionBatchDTO.getIdBatch());
				if (batch == null)
					throw new NullPointerException("Cannot Find the Batch data");
				batchInfoDTO.setIdBatch(batch.getIdBatch());
				batchInfoDTO.setBatchName(batch.getBatchName());
				batchInfoDTO.setNextPaymentDate(studentSubscriptionBatchDTO.getNextPaymentDate());
				batchInfoDTO.setIdSubscription(studentSubscriptionBatchDTO.getIdStudentSubscription());

				Product prod = new Product();
				prod = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

				if (prod == null)
					throw new NullPointerException("Cannot Find the Product data");
				batchInfoDTO.setBatchSize(prod.getBatchSize());

				Subject subject = new Subject();
				subject = subjectRepository.findByIdSubject(prod.getIdSubject());

				if (subject == null)
					throw new NullPointerException("Cannot Find Subject");

				batchInfoDTO.setSubjectName(subject.getSubjectName());

				Teacher teacher = new Teacher();
				teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

				if (teacher == null)
					throw new NullPointerException("Cannot Find teacher");

				batchInfoDTO.setTeacherName(teacher.getUser().getFirstName() + ' ' + teacher.getUser().getLastName());
				batchInfoDTO.setDayOfWeekCode(batch.getDayOfWeekCode());
				batchInfoDTO.setBatchFromTime(batch.getBatchFromTime());
				batchInfoDTO.setBatchToTime(batch.getBatchToTime());

				list.add(batchInfoDTO);
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

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchrecordingDetailsOndate(Long idBatch, String batchRundate) {
		Document result = new Document();
		try {
			List<BatchRunRecording> batchRunRecording = new ArrayList<BatchRunRecording>();
			BatchRunDetail batchRunDetail = new BatchRunDetail();

			batchRunDetail = batchRunDetailRepository.findByIdBatchAndBatchRundate(idBatch, batchRundate);

			if (batchRunDetail == null)
				throw new NullPointerException("Cannot Find the Batch Recording data");

			batchRunRecording = batchRunRecordingRepository.findByBatchRunDetail(batchRunDetail);

			if (batchRunRecording.isEmpty())
				throw new NullPointerException("Cannot Find the Batch Recording data");

			result.setData(batchRunRecording);
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

	@SuppressWarnings("unused")
	@Override
	public Document<?> listAllWebexRecordingXml(String webExUserId, String webExPassword, Long idBatch,String dateOfRecording,BatchRunDetail batchRunDetail) {

		Document<Object> doc = new Document<>();

		try {
			String xmlBodyForListAllRecordings = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n"
					+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n"
					+ "    <header>\r\n" + "        <securityContext>\r\n"
					+ "            <siteName>vistafoundation</siteName>\r\n" + "            <webExID>" + webExUserId
					+ "</webExID>\r\n" + "            <password>" + webExPassword + "</password>\r\n"
					+ "            <partnerID>I2WNi1i-xdYDHn-HNXJCMA</partnerID>\r\n" + "        </securityContext>\r\n"
					+ "    </header>\r\n" + "    <body>\r\n"
					+ "        <bodyContent xsi:type=\"java:com.webex.service.binding.ep.LstRecording\">\r\n"
					+ "            <hostWebExID>" + webExUserId + "</hostWebExID>\r\n"
					+ "            <serviceTypes>\r\n" + "                <serviceType>MeetingCenter</serviceType>\r\n"
					+ "                <serviceType>EventCenter</serviceType>\r\n"
					+ "                <serviceType>TrainingCenter</serviceType>\r\n"
					+ "            </serviceTypes>\r\n"
					+ "            <returnShareToMeRecording>true</returnShareToMeRecording>\r\n"
					+ "        </bodyContent>\r\n" + "    </body>\r\n" + "</serv:message>";

			String corsAnywhereUrl = "https://videoconfcors.vistaslearning.com/";
			String url = "https://api.webex.com/WBXService/XMLService";

			System.err.println(xmlBodyForListAllRecordings);

			if (webExUserId == null || webExPassword == null) {
				throw new NullPointerException("WebEx User Id or Password cannot be null");
			} else if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Id Batch Cannot be zero or Null");
			}

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> result = restTemplate.postForEntity(url, xmlBodyForListAllRecordings, String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStream = new ByteArrayInputStream(result.getBody().getBytes());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			org.w3c.dom.Document document = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStream);

			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();
			NodeList recordings = document.getElementsByTagName("ep:recording");
			NodeList matchingRecords = document.getElementsByTagName("ep:matchingRecords");

			System.out.println("Recordings===>" + recordings);
			System.out.println("Matching Records===>" + matchingRecords);

			for (int i = 0; i < matchingRecords.getLength(); i++) {
				Node matchingRecordnode = matchingRecords.item(i);
				Element serviceTypeElement = (Element) matchingRecordnode;
				System.out.println("-------------Matching Records-----------");
				System.out.println(serviceTypeElement.getTextContent());
				System.out.println("----------------------------------------");
			}

			System.out.println("-------------Recordings------------------");
			
			
			System.out.println("idbatchRundetail"+ batchRunDetail.getIdBatchRunDetail());

			for (int i = 0; i < recordings.getLength(); i++) {
				Node node = recordings.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					String webExRecordingTopicName = element.getElementsByTagName("ep:name").item(0).getTextContent();

					String[] split = webExRecordingTopicName.split(" ");// Splitting the topic by whitespace
					System.out.println("Split String Array===>" + Arrays.toString(split));

					String batchNameToCompare = "Batch-" + idBatch + "-" + dateOfRecording;
					System.out.println(batchNameToCompare);

					if (split[0].equalsIgnoreCase(batchNameToCompare)) {

						System.out.println("Data  matched.");
						BatchRunRecording batchRunRecording = new BatchRunRecording();
						batchRunRecording.setBatchRunDetail(batchRunDetail);
						batchRunRecording
								.setCreateTime(element.getElementsByTagName("ep:createTime").item(0).getTextContent());
						batchRunRecording.setDurationInSecond(
								element.getElementsByTagName("ep:duration").item(0).getTextContent());
						batchRunRecording
								.setFileUrl(element.getElementsByTagName("ep:fileURL").item(0).getTextContent());
						batchRunRecording.setHostStreamUrl(
								element.getElementsByTagName("ep:hostStreamURL").item(0).getTextContent());
						batchRunRecording.setHostWebExId(
								element.getElementsByTagName("ep:hostWebExID").item(0).getTextContent());
						batchRunRecording
								.setPassword(element.getElementsByTagName("ep:password").item(0).getTextContent());
						batchRunRecording
								.setTimeZoneId(element.getElementsByTagName("ep:timeZoneID").item(0).getTextContent());
						batchRunRecording
								.setSizeInBytes(element.getElementsByTagName("ep:size").item(0).getTextContent());
						batchRunRecording
								.setStreamUrl(element.getElementsByTagName("ep:streamURL").item(0).getTextContent());
						batchRunRecording.setHostWebExId(
								element.getElementsByTagName("ep:hostWebExID").item(0).getTextContent());
						batchRunRecording
								.setWebExTopic(element.getElementsByTagName("ep:name").item(0).getTextContent());

						// save the recording
						batchRunRecordingRepository.save(batchRunRecording);

					} else {

						System.out.println("Data Dosent match.");
						System.out.println(split[0]);
					}

				}
			}
             
			System.out.println("Batch Run Recordings Cron Job Success!!!");
			doc.setData("Batch Run Recordings Success for idBatchRunDetail :"+batchRunDetail.getIdBatchRunDetail());
			doc.setMessage("Batch Run Recordings Cron Job Success");
			doc.setStatusCode(200);
			return doc;

		} catch (Exception e1) {
			System.out.println("Batch Run Recordings Cron Job Failed..Exception Occured");
			System.err.println(e1.getLocalizedMessage());
			doc.setData(e1.getStackTrace());
			doc.setMessage("Batch Run Recordings Cron Job Failed..Exception Occured" + e1.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document saveBatchRunDetails(BatchRunDetail batchRunDetail, Long idTeacher) {

		Document doc = new Document<>();
		try {

			if (batchRunDetail == null) {
				throw new NullPointerException("Batch Run Details Cannot be Null");
			}

			if (batchRunDetail.getIdBatch() == null) {

				throw new NullPointerException("Batch Id Not Found");
			}

			Batch batch = batchRepository.findByIdBatch(batchRunDetail.getIdBatch());

			// Get WebEx Pool Details from idTeacher
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null) {
				throw new NullPointerException("invaild idTeacher");
			}

			WebExPool webExPoolObject = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

			if (webExPoolObject == null) {
				throw new NullPointerException("invaild idWebexPool");
			}

			// Todays Date in MMDDYY HH:mm:ss
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String instantDate = sdf.format(new Date());

//			String randomPassword = RandomStringUtils.randomAlphabetic(6);

			// Create WebEx Meeting
			String xmlBodyForCreateMeetingPart1 = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
					+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + "  <header>\r\n"
					+ "      <securityContext>\r\n" + "          <siteName>vistafoundation</siteName>\r\n"
					+ "          <webExID>" + webExPoolObject.getWebExUserId() + "</webExID>\r\n"
					+ "          <password>" + webExPoolObject.getWebExPassword() + "</password>\r\n"
					+ "      </securityContext>\r\n" + "  </header>\r\n" + "  <body>\r\n" + "      <bodyContent\r\n"
					+ "          xsi:type=\"java:com.webex.service.binding.meeting.CreateMeeting\">\r\n"
					+ "          <metaData>\r\n" + "              <confName>Batch-" + batchRunDetail.getIdBatch()
					+ "</confName>\r\n" + "              <agenda>Batch</agenda>\r\n" + "          </metaData>\r\n"
					+ "          <participants>\r\n" + "                <attendees>";

			String xmlBodyForCreateMeetingPart2 = "";

			List<StudentSubscription> listOfAllStudentsSubscriptionBatch = studentSubscriptionRepository
					.findByIdBatch(batchRunDetail.getIdBatch());

			if (listOfAllStudentsSubscriptionBatch.isEmpty()) {
				throw new NullPointerException("No student subscribed for this course.");
			}

			for (StudentSubscription studentSubscription : listOfAllStudentsSubscriptionBatch) {

				Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
				xmlBodyForCreateMeetingPart2 += "<attendee>\r\n" + "                        <person>\r\n"
						+ "                            <name>" + student.getUser().getFirstName() + "</name>\r\n"
						+ "                            <email>" + student.getUser().getEmail() + "</email>\r\n"
						+ "                        </person>\r\n" + "                    </attendee>";

			}
			String xmlBodyForCreateMeetingPart3 = "</attendees>\r\n" + "            </participants>\r\n"
					+ "          <enableOptions>\r\n" + "              <chat>true</chat>\r\n"
					+ "              <poll>true</poll>\r\n" + "              <audioVideo>true</audioVideo>\r\n"
					+ "              <supportE2E>TRUE</supportE2E>\r\n"
					+ "              <autoRecord>TRUE</autoRecord>\r\n" + "          </enableOptions>\r\n"
					+ "          <schedule>\r\n" + "              <startDate>" + instantDate + "</startDate>\r\n"
					+ "              <openTime>900</openTime>\r\n"
					+ "              <joinTeleconfBeforeHost>true</joinTeleconfBeforeHost>\r\n"
					+ "              <duration>180</duration>\r\n" + "              <timeZoneID>41</timeZoneID>\r\n"
					+ "          </schedule>\r\n" + "          <telephony>\r\n"
					+ "          <telephonySupport>CALLIN</telephonySupport>\r\n"
					+ "          <extTelephonyDescription>\r\n"
					+ "              Call 1-800-555-1234, Passcode 98765\r\n"
					+ "          </extTelephonyDescription>\r\n" + "      </telephony>\r\n" + "      </bodyContent>\r\n"
					+ "  </body>\r\n" + "</serv:message>";

			String xmlBodyForCreateMeeting = xmlBodyForCreateMeetingPart1 + xmlBodyForCreateMeetingPart2
					+ xmlBodyForCreateMeetingPart3;

			System.out.println(xmlBodyForCreateMeeting);

			String url = "https://api.webex.com/WBXService/XMLService";

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> result = restTemplate.postForEntity(url, xmlBodyForCreateMeeting, String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStream = new ByteArrayInputStream(result.getBody().getBytes());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			org.w3c.dom.Document document = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStream);

			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			NodeList meetingKey = document.getElementsByTagName("meet:meetingkey");
			NodeList meetingUUid = document.getElementsByTagName("meet:meetingUUID");
			NodeList meetingPassword = document.getElementsByTagName("meet:meetingPassword");
			NodeList meetingGuestUserToken = document.getElementsByTagName("meet:guestToken");

			String meetingKeyActual = null;
			String meetingUuidActual = null;
			String meetingPasswordActual = null;
			String meetingGuestUserTokenActual = null;

			
			if (meetingKey.getLength()<1) 
			{
				doc.setData(result.getBody());
				doc.setMessage("Unable to start the meeting.Please contact admin.");
				doc.setStatusCode(500);
				return doc;
			}
				
			
			for (int i = 0; i < meetingKey.getLength(); i++) {
				Node meetingKeyNode = meetingKey.item(i);
				Element serviceTypeElement = (Element) meetingKeyNode;
				System.out.println("------------Meeting Key----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingKeyActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}
           
		
			if (meetingUUid.getLength()<1) 
			{
				doc.setData(result.getBody());
				doc.setMessage("Unable to start the meeting.Please contact admin.");
				doc.setStatusCode(500);
				return doc;
			}
			
			for (int i = 0; i < meetingUUid.getLength(); i++) {
				Node meetingUuidNode = meetingUUid.item(i);
				Element serviceTypeElement = (Element) meetingUuidNode;
				System.out.println("------------Meeting UUID----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingUuidActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
			}
			
			if (meetingPassword.getLength()<1) 
			{
				doc.setData(result.getBody());
				doc.setMessage("Unable to start the meeting.Please contact admin.");
				doc.setStatusCode(500);
				return doc;
			}

			
			for (int i = 0; i < meetingPassword.getLength(); i++) {
				Node meetingPasswordNode = meetingPassword.item(i);
				Element serviceTypeElement = (Element) meetingPasswordNode;
				System.out.println("------------Meeting Password----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingPasswordActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
			}
			
			if (meetingGuestUserToken.getLength()<1) 
			{
				doc.setData(result.getBody());
				doc.setMessage("Unable to start the meeting.Please contact admin.");
				doc.setStatusCode(500);
				return doc;
			}

			for (int i = 0; i < meetingGuestUserToken.getLength(); i++) {
				Node meetingGuestUserTokenNode = meetingGuestUserToken.item(i);
				Element serviceTypeElement = (Element) meetingGuestUserTokenNode;
				System.out.println("------------Meeting Guest User Token----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingGuestUserTokenActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
			}

			// Now get Host meeting Url for Starting the meeting
			String xmlBodyForGettingHostMeetingUrl = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
					+ "  <serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
					+ "    <header>\r\n" + "        <securityContext>\r\n" + "            <webExID>"
					+ webExPoolObject.getWebExUserId() + "</webExID>\r\n" + "            <password>"
					+ webExPoolObject.getWebExPassword() + "</password>\r\n"
					+ "            <siteID>13696027</siteID>\r\n"
					+ "            <siteName>vistafoundation</siteName>\r\n"
					+ "            <partnerID>I2WNi1i-xdYDHn-HNXJCMA</partnerID>\r\n" + "            <email>"
					+ webExPoolObject.getWebExUserId() + "</email>\r\n" + "        </securityContext>\r\n"
					+ "    </header>\r\n" + "    <body>\r\n" + "        <bodyContent\r\n"
					+ "            xsi:type=\"java:com.webex.service.binding.meeting.GethosturlMeeting\">\r\n"
					+ "            <sessionKey>" + meetingKeyActual + "</sessionKey>\r\n" + "        </bodyContent>\r\n"
					+ "    </body>\r\n" + "  </serv:message>";

			ResponseEntity<String> resultHostMeetingUrl = restTemplate.postForEntity(url,
					xmlBodyForGettingHostMeetingUrl, String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStreamForHost = new ByteArrayInputStream(resultHostMeetingUrl.getBody().getBytes());

			DocumentBuilderFactory factoryHost = DocumentBuilderFactory.newInstance();
			DocumentBuilder builderHost = null;
			org.w3c.dom.Document documentHost = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStreamForHost);

			document.getDocumentElement().normalize();

			Element rootHost = document.getDocumentElement();

			NodeList hostStartMeetingUrl = document.getElementsByTagName("meet:hostMeetingURL");

			String hostStartMeetingUrlActual = null;

			for (int i = 0; i < hostStartMeetingUrl.getLength(); i++) {
				Node hostStartMeetingUrlNode = hostStartMeetingUrl.item(i);
				Element serviceTypeElement = (Element) hostStartMeetingUrlNode;
				System.out.println("------------Host Start Meeting Url----------");
				System.out.println(serviceTypeElement.getTextContent());
				hostStartMeetingUrlActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}

			// Add these details to DTO
			WebExMetaDataDTO webExMetaDataDTO = new WebExMetaDataDTO(meetingKeyActual, meetingPasswordActual,
					meetingUuidActual, meetingGuestUserTokenActual, hostStartMeetingUrlActual, "");

			BatchRunDetail existingBatchRunDetails = batchRunDetailsRepository
					.fetchBatchRunDetailsByIdBatch(batchRunDetail.getIdBatch(), batchRunDetail.getBatchRundate());

			if (existingBatchRunDetails != null) {

				// Update the Batch Run Details Table
				existingBatchRunDetails.setActualEndTime(batchRunDetail.getActualEndTime());
				existingBatchRunDetails.setActualStartTime(batchRunDetail.getActualStartTime());
				existingBatchRunDetails.setAttendeeMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				existingBatchRunDetails.setBatchRundate(batchRunDetail.getBatchRundate());
				existingBatchRunDetails.setIdBatch(batchRunDetail.getIdBatch());
				existingBatchRunDetails.setCreatedAt(existingBatchRunDetails.getCreatedAt());
				existingBatchRunDetails.setCreatedBy(existingBatchRunDetails.getCreatedBy());
				existingBatchRunDetails.setHostMeetingUrl(webExMetaDataDTO.getHostStartMeetingUrl());
				existingBatchRunDetails.setMeetingDescription("");
				existingBatchRunDetails.setMeetingGuestUserToken(webExMetaDataDTO.getMeetingGuestUserToken());
				existingBatchRunDetails.setMeetingId(webExMetaDataDTO.getMeetingKey());
				existingBatchRunDetails.setMeetingPassword(webExMetaDataDTO.getMeetingPassword());
				existingBatchRunDetails.setMeetingTitle("");
				existingBatchRunDetails.setMeetingUuId(webExMetaDataDTO.getMeetingUuid());
				existingBatchRunDetails.setUpdatedAt(existingBatchRunDetails.getUpdatedAt());
				existingBatchRunDetails.setUpdatedBy(existingBatchRunDetails.getUpdatedBy());
				existingBatchRunDetails.setIdWebExPool(webExPoolObject.getIdWebExPool());
				BatchRunDetail updated = batchRunDetailRepository.save(existingBatchRunDetails);

				
 			
				List<StudentSubscription> studentSubscriptionList = studentSubscriptionRepository
						.findByIdBatchAndActiveFlag(batchRunDetail.getIdBatch(),Boolean.TRUE);

				Set<String> regDeviceIdSet = new HashSet<String>();
				List<String> regDeviceIdList = new ArrayList<String>();
				for (StudentSubscription studentSubscription : studentSubscriptionList) {
					
					Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
					if (student != null) {
						Long userSurId = student.getUser().getUserSurId();
						System.out.println("userSurid1 " + student.getUser().getUserSurId());
						UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId,"MOBILE");
						if (userDevice != null) {
							System.out.println("DeviceId1 " + userDevice.getDeviceId());
							regDeviceIdSet.add(userDevice.getDeviceId());
						}
					
					}
				}

				// sending notification
				if (!regDeviceIdSet.isEmpty()) {

					regDeviceIdList.addAll(regDeviceIdSet);
					String message = "Your class is started ";
					notificationService.sendNotification(regDeviceIdList, message, "personalcoaching");
				}

				doc.setData(updated);
				doc.setMessage("Batch Run Details Updated Successfully");
				doc.setStatusCode(201);
				return doc;

			} else {
				// Save the New Batch Run Details
				BatchRunDetail runDetail = new BatchRunDetail();
				runDetail.setActualEndTime(batchRunDetail.getActualEndTime());
				runDetail.setActualStartTime(batchRunDetail.getActualStartTime());
				runDetail.setAttendeeMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				runDetail.setBatchRundate(batchRunDetail.getBatchRundate());
				runDetail.setHostMeetingUrl(webExMetaDataDTO.getHostStartMeetingUrl());
				runDetail.setIdBatch(batchRunDetail.getIdBatch());
				runDetail.setMeetingDescription("");
				runDetail.setMeetingGuestUserToken(webExMetaDataDTO.getMeetingGuestUserToken());
				runDetail.setMeetingId(webExMetaDataDTO.getMeetingKey());
				runDetail.setMeetingPassword(webExMetaDataDTO.getMeetingPassword());
				runDetail.setMeetingTitle("");
				runDetail.setMeetingUuId(webExMetaDataDTO.getMeetingUuid());
				runDetail.setIdWebExPool(webExPoolObject.getIdWebExPool());

				BatchRunDetail saved = batchRunDetailRepository.save(runDetail);

				// INsert into Batch Student Attendance

				List<StudentSubscription> studentSubscriptionList = studentSubscriptionRepository
						.findByIdBatchAndActiveFlag(batchRunDetail.getIdBatch(),Boolean.TRUE);

				List<BatchStudentAttendance> attendanceList = new ArrayList<BatchStudentAttendance>();
				Set<String> regDeviceIdSet = new HashSet<String>();

				List<String> regDeviceIdList = new ArrayList<String>();

				for (StudentSubscription studentSubscription : studentSubscriptionList) {
					BatchStudentAttendance attendance = new BatchStudentAttendance();
					attendance.setBatchRunDate(saved.getBatchRundate());
					attendance.setIdBatch(saved.getIdBatch());
					attendance.setAbsentPresentFlag(Boolean.FALSE);
					attendance.setIdStudentSubscr(studentSubscription.getIdStudentSubscription());
					attendanceList.add(attendance);

					Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
					if (student != null) {
						Long userSurId = student.getUser().getUserSurId();
						System.out.println("userSurid2 " + student.getUser().getUserSurId());
						UserDevice userDevice = userDeviceRepository.findByUserSurIdAndDeviceType(userSurId,"MOBILE");
						//System.out.println("userDevice2 " + userDevice.getDeviceId());
						
						if(student.getUser().getEmail() != null) {
							emailService.sendNotificationEmailWhenBatchStarts(student.getUser().getFirstName(),
									student.getUser().getEmail(),batch.getBatchName(),batch.getIdBatch());
						}
						
						if (userDevice != null) {
							System.out.println("DeviceId2 " + userDevice.getDeviceId());
							regDeviceIdSet.add(userDevice.getDeviceId());
						}
						
					}
				}

				batchStudentAttendanceRepository.saveAll(attendanceList);

				// sending notification
				if (!regDeviceIdSet.isEmpty()) {
					regDeviceIdList.addAll(regDeviceIdSet);
					String message = "Your class is started ";
					notificationService.sendNotification(regDeviceIdList, message, "personalcoaching");
				}

				doc.setData(saved);
				doc.setMessage("Batch Run Details Added Successfully");
				doc.setStatusCode(201);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document getBatchInformationByIdBatch(Long idBatch) {

		Document doc = new Document<>();
		try {
			if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null or Zero");
			} else {
				Batch fetchedBatchInfo = batchRepository.findByIdBatch(idBatch);

				if (fetchedBatchInfo == null)
					throw new AppException("invalid id batch.");

				doc.setData(fetchedBatchInfo);
				doc.setMessage("Success,Batch Details Fetch Success");
				doc.setStatusCode(200);
				return doc;
			}
		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Failed to Get Batch Info");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document fetchWebExHostCredentialsByIdWebexPool(Long idWebExPool) {

		Document doc = new Document<>();
		try {
			if (idWebExPool == 0 || idWebExPool == null) {
				throw new NullPointerException("IdWebExPool is Invalid");
			} else {
				WebExPool fetchedWebExPool = webExPoolRepo.findByIdWebExPool(idWebExPool);

				if (fetchedWebExPool == null)
					throw new AppException("invalid IdWebexpool.");
				doc.setData(fetchedWebExPool);
				doc.setMessage("Success");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Failed to Get Batch Info");
			doc.setStatusCode(500);
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document takeStudentAttendanceWhileJoiningABatch(Long idStudent, Long idBatch) {

		Document doc = new Document<>();
		try {
			if (idStudent == 0 || idStudent == null) {
				throw new NullPointerException("Student Id Cannot be Null");
			} else if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null");
			}

			StudentSubscription fetchedStudentSubscription = studentSubscriptionRepository
					.findByIdStudentAndIdBatch(idStudent, idBatch);

			if (fetchedStudentSubscription == null) {
				throw new NullPointerException(
						"Student Subscription Not Found, Make Sure You have Selected the Correct Batch");
			}

//			Instant date = new java.util.Date().toInstant();
//			String[] dateSplit = date.toString().split("T");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yyyyMMdd = sdf.format(new Date());

//			
//			Date todaysDate = new Date(System.currentTimeMillis());
//			
//			String formattedDate = formatDate(DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT).format(todaysDate));
//			DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM).format(todaysDate)

			BatchRunDetail existingBatchRunInfo = batchRunDetailRepository.fetchBatchRunDetailsByIdBatch(idBatch,
					yyyyMMdd);

			if (existingBatchRunInfo == null) {
				throw new NullPointerException(
						"Batch Run Details Not Found for the Current Date and Selected Batch. May be Class has not yet Started");
			}

			BatchStudentAttendance studentAttendance = batchStudentAttendanceRepository
					.findByIdBatchAndIdStudentSubscrAndBatchRunDate(idBatch,
							fetchedStudentSubscription.getIdStudentSubscription(),
							existingBatchRunInfo.getBatchRundate());

			if (studentAttendance == null) {
				studentAttendance = new BatchStudentAttendance();

				studentAttendance.setAbsentPresentFlag(Boolean.TRUE);
				studentAttendance.setBatchRunDate(yyyyMMdd);
				studentAttendance.setIdBatch(idBatch);
				studentAttendance.setIdStudentSubscr(fetchedStudentSubscription.getIdStudentSubscription());
				BatchStudentAttendance newStudentAttendance = batchStudentAttendanceRepository.save(studentAttendance);
				doc.setData(newStudentAttendance);
				doc.setMessage("Attendance Taken Successfully. You have been Marked as Present");
				doc.setStatusCode(200);
				return doc;

//				throw new NullPointerException(
//						"Error,Make Sure Teacher has Started the Class. Default Student Attendance Cannot be Null");
			}

			if (studentAttendance.getAbsentPresentFlag().equals(Boolean.TRUE)) {
				doc.setData("Attendance Is Already Taken, You have been marked as Present");
				doc.setMessage("Attendance Is Already Taken, You have been marked as Present");
				doc.setStatusCode(200);
				return doc;
			} else {
				studentAttendance.setAbsentPresentFlag(Boolean.TRUE);
				BatchStudentAttendance updated = batchStudentAttendanceRepository.save(studentAttendance);
				doc.setData(updated);
				doc.setMessage("Attendance Taken Successfully. You have been Marked as Present");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setMessage("Failed to Take Student Attendance");
			doc.setStatusCode(500);
			return doc;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchrecordingDetailsForTeacher(Long idTeacher) {
		Document result = new Document();
		try {

			List<BatchInfoDTO> list = new ArrayList<BatchInfoDTO>();

			List<Batch> Batchlist = new ArrayList<Batch>();
			Batchlist = batchRepository.findByIdTeacher(idTeacher);

			if (Batchlist.isEmpty())
				throw new NullPointerException("Cannot Find the Batch for this Teacher");

			/*
			 * get the batch list for recordings
			 * 
			 */

			for (Batch lists : Batchlist) {
				BatchInfoDTO batchInfoDTO = new BatchInfoDTO();
				// batch = new Batch();

				Batch batch = batchRepository.findByIdBatch(lists.getIdBatch());

				if (batch == null)
					throw new AppException("invalid id batch.");

				batchInfoDTO.setIdBatch(lists.getIdBatch());
				batchInfoDTO.setBatchName(lists.getBatchName());

				Product prod = new Product();
				prod = productRepository.findByIdProductAndActiveFlag(lists.getIdProduct(),Boolean.TRUE);

				if (prod == null)
					throw new NullPointerException("Cannot Find the Product data");

				batchInfoDTO.setBatchSize(prod.getBatchSize());

				Subject subject = new Subject();
				subject = subjectRepository.findByIdSubject(prod.getIdSubject());

				if (subject == null)
					throw new AppException("invalid id Subject.");

				batchInfoDTO.setSubjectName(subject.getSubjectName());

				Teacher teacher = new Teacher();
				teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

				if (teacher == null)
					throw new NullPointerException("Cannot Find the Teacher data");

				batchInfoDTO.setIdTeacher(batch.getIdTeacher());
				batchInfoDTO.setBatchSize(prod.getBatchSize());
				batchInfoDTO.setTeacherName(teacher.getUser().getFirstName() + ' ' + teacher.getUser().getLastName());
				batchInfoDTO.setDayOfWeekCode(lists.getDayOfWeekCode());
				batchInfoDTO.setBatchFromTime(lists.getBatchFromTime());
				batchInfoDTO.setBatchToTime(lists.getBatchToTime());

				list.add(batchInfoDTO);

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

	public static String formatDate(String inDate) {
		SimpleDateFormat inSDF = new SimpleDateFormat("mm/dd/yyyy");
		SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");
		String outDate = "";
		if (inDate != null) {
			try {
				Date date = inSDF.parse(inDate);
				outDate = outSDF.format(date);
			} catch (ParseException ex) {
			}
		}
		return outDate;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document fetchListOfStudentsByIdBatch(Long idBatch) {

		Document doc = new Document<>();
		try {

			if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null Or Zero");
			}

			List<StudentSubscription> listOfStudentSubscription = studentSubscriptionRepository.findByIdBatch(idBatch);
			if (listOfStudentSubscription.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Student Subscription Lists is Empty");
				doc.setStatusCode(500);
				return doc;
			}

			List<Student> studentList = new ArrayList<>();

			// Iterate the Student Subscription
			for (StudentSubscription studentSubscription : listOfStudentSubscription) {
				Long studentId = studentSubscription.getIdStudent();

				Student student = studentRepository.findByIdStudent(studentId);

				if (student == null)
					throw new AppException("invalid id Student.");

				studentList.add(student);
			}

			if (studentList.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setMessage("Student Lists is Empty");
				doc.setStatusCode(200);
				return doc;
			} else {
				doc.setData(studentList);
				doc.setMessage("Student Lists");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document fetchBatchRunDetailsByIdbatch(Long idBatch) {

		Document doc = new Document<>();
		try {
			if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null Or Zero");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yyyyMMdd = sdf.format(new Date());

			BatchRunDetail fetchedBatchRunDetails = batchRunDetailsRepository.fetchBatchRunDetailsByIdBatch(idBatch,
					yyyyMMdd);

			if (fetchedBatchRunDetails == null) {
				throw new NullPointerException("Batch Run Details Not Found For Todays Date And Selected Batch");
			} else {
				doc.setData(fetchedBatchRunDetails);
				doc.setMessage("Success");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getBatchQuizReview(Long idBatchStudentQuiz) {
		Document result = new Document();
		List<BatchQuizQuestionDTO> batchQuizQuestionDTOList = new ArrayList<BatchQuizQuestionDTO>();
		Map<String, Object> map = new HashMap<>();
		try {
			BatchStudentQuiz batchStudentQuiz = batchStudentQuizRepository.findByIdBatchStudentQuiz(idBatchStudentQuiz);

			if (batchStudentQuiz == null)
				throw new AppException("invalid bacth id quiz.");

			List<BatchStudentQuizQuestion> batchStudentQuizQuestionList = batchStudentQuizQuestionRepository
					.findByIdBatchStudentQuiz(batchStudentQuiz.getIdBatchStudentQuiz());

			for (BatchStudentQuizQuestion batchStudentQuizQuestion : batchStudentQuizQuestionList) {
				BatchQuizQuestionDTO batchQuizQuestionDTO = new BatchQuizQuestionDTO();

				BatchQuizQuestion batchQuizQuestion = batchQuizQuestionRepository
						.findByIdBatchQuizQuestion(batchStudentQuizQuestion.getIdBatchQuizQuestion());

				if (batchQuizQuestion == null)
					throw new AppException("invalid bacth quiz question.");

				List<BatchStudentQuizAnswer> batchStudentQuizAnswerList = batchStudentQuizAnswerRepository
						.findByIdBatchStudentQuizQuestion(batchQuizQuestion.getIdBatchQuizQuestion());

				if (batchStudentQuizAnswerList.isEmpty())
					throw new AppException("no data found batch quiz question.");

				batchQuizQuestionDTO.setIdBatchQuizQuestion(batchStudentQuizQuestion.getIdBatchQuizQuestion());
				batchQuizQuestionDTO.setQuestionText(batchQuizQuestion.getQuestionText());
				batchQuizQuestionDTO.setQuestionType(batchQuizQuestion.getQuestionType());
				batchQuizQuestionDTO.setCorrectValueFlag(batchStudentQuizQuestion.getCorrectFlag());

				List<BatchQuizAnwser> batchAnswers = new ArrayList<BatchQuizAnwser>();
				batchQuizQuestion.getBatchanswers().stream().forEach(a -> {
					BatchQuizAnwser batchAnswer = new BatchQuizAnwser();
					batchAnswer.setFieldId(a.getFieldId());
					batchAnswer.setTextFieldValue(a.getTextFieldValue());
					batchAnswer.setCorrectValueFlag(a.getCorrectValueFlag());
					batchAnswers.add(batchAnswer);
				});
				batchQuizQuestionDTO.setCorrectAnswer(batchAnswers);
				List<BatchStudentQuizAnswer> studentAnswers = new ArrayList<BatchStudentQuizAnswer>();
				batchStudentQuizAnswerList.stream().forEach(an -> {
					BatchStudentQuizAnswer studentAnswer = new BatchStudentQuizAnswer();
					studentAnswer.setFieldId(an.getFieldId());
					studentAnswer.setTextFieldValue(an.getTextFieldValue());
					studentAnswers.add(studentAnswer);
				});
				batchQuizQuestionDTO.setBatchStudentQuizAnswer(studentAnswers);
				batchQuizQuestionDTOList.add(batchQuizQuestionDTO);
			}
			map.put("noOfQuestions", batchStudentQuizQuestionList.size());
			map.put("correctAnswers",
					batchStudentQuizQuestionList.stream().filter(a -> a.getCorrectFlag() == true).count());
			map.put("percentage", batchStudentQuiz.getScore());
			map.put("reviewData", batchQuizQuestionDTOList);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Batch Quiz Review Details Successfully");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getNotificationForUpcomingBatchTest(Long idBatch, Long idStudentSubscription) {
		Document result = new Document();
		try {

			List<BatchTestNotificationDTO> notificationList = new ArrayList<BatchTestNotificationDTO>();

			List<BatchQuizAssignment> quizAssignmentList = batchQuizAssignmentRespository.findByIdBatch(idBatch);

			if (!quizAssignmentList.isEmpty()) {

				for (BatchQuizAssignment batchQuizAssignment : quizAssignmentList) {

					Long idBatchQuizAssignment = batchQuizAssignment.getIdBatchQuizAssignment();

					List<BatchStudentQuiz> listQuiz = batchStudentQuizRepository
							.findByIdStudentSubscriptionAndIdBatchQuizAssignment(idStudentSubscription,
									idBatchQuizAssignment);

					if (!listQuiz.isEmpty()) {

						for (BatchStudentQuiz quiz : listQuiz) {

							if (quiz.getQuizCompleteFlag() == false) {

								Long BatchQuizAssignmentId = quiz.getIdBatchQuizAssignment();
								BatchQuizAssignment assignment = batchQuizAssignmentRespository
										.findByIdBatchQuizAssignment(BatchQuizAssignmentId);

								BatchTestNotificationDTO notificationDto = new BatchTestNotificationDTO();
								notificationDto.setBatchQuizName(assignment.getBatchQuizName());
								notificationDto.setQuizDate(assignment.getQuizDate());
								notificationDto.setIdBatchQuizAssignment(quiz.getIdBatchQuizAssignment());
								notificationDto.setIdBatchStudentQuiz(quiz.getIdBatchStudentQuiz());

								Date today = new Date();
								Date myDate = Date.from(assignment.getQuizDate());

//								int datenumber = today.compareTo(myDate);

								if (today.compareTo(myDate) > 0)
									notificationDto.setTakeTest(true);
								else
									notificationDto.setTakeTest(false);

								notificationList.add(notificationDto);
							}
						}
					}
				}
			}
			result.setData(notificationList);
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
	public Document<List<Object>> fetchAllBatchLists() {

		Document<List<Object>> doc = new Document<>();
		try {

			List<Batch> batchLists = batchRepository.findByActiveFlagOrderByCreatedAtDesc(Boolean.TRUE);

			List<Object> batchListsResponse = new ArrayList<>();

			for (Batch batch : batchLists) {

				Map<String, Object> temp = new HashMap<>();

				temp.put("idBatch", batch.getIdBatch());
				temp.put("batchName", batch.getBatchName());
				temp.put("batchStartDate", batch.getBatchStartDate());

				// Get Teacher Details By Teacher Id From Each BAtch
				Teacher existingTeacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

				if (existingTeacher == null)
					throw new AppException("invalid idTeacher.");

				temp.put("idTeacher", existingTeacher.getIdTeacher());
				temp.put("teacherName", existingTeacher.getTeacherName());
				temp.put("teacherEmail", existingTeacher.getEmailId());

				// get product line info from product table
				Product productObj = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

				if (productObj == null)
					throw new AppException("invalid idProduct.");

				temp.put("idProduct", productObj.getIdProduct());
				temp.put("batchSize", productObj.getBatchSize());
				temp.put("productName", productObj.getProductName());

				// Get the Class Standard from Product Object
				ClassStandard std = classRepository.findByIdClassStandard(productObj.getIdClassStandard());

				if (std == null)
					throw new AppException("invalid idClassStandard.");

				temp.put("classStandard", std.getClassStandadName());

				// Get the Subject from Product Object
				Subject subject = subjectRepository.findByIdSubject(productObj.getIdSubject());

				if (subject == null)
					throw new AppException("invalid idSubject.");

				temp.put("subject", subject.getSubjectName());

				batchListsResponse.add(temp);
			}

			if (batchLists.isEmpty()) {
				doc.setData(new ArrayList<>());
				doc.setStatusCode(HttpStatus.OK.value());
				doc.setMessage("Batch Lists is Empty");
				return doc;
			} else {

				doc.setData(batchListsResponse);
				doc.setStatusCode(HttpStatus.OK.value());
				doc.setMessage("Batch Lists");
				return doc;
			}
		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}

	}

	@Override
	public Document<Boolean> deactivateBatchByIdBatch(Long idBatch) {

		Document<Boolean> doc = new Document<>();
		Boolean response = false;
		try {

			if (idBatch == 0 || idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null");
			}

			// Get Batch By Id
			Batch batch = batchRepository.findByIdBatchAndActiveFlag(idBatch, Boolean.TRUE);

			if (batch == null) {
				throw new NullPointerException("Batch Not Found");
			}

			List<StudentSubscription> sslist = studentSubscriptionRepository.findByIdBatchAndActiveFlag(idBatch,
					Boolean.TRUE);

			if (!sslist.isEmpty()) {
				throw new AppException("Active Subscription found for this batch, Cannot be deleted. ");
			}

			// deactivating the batch
			batch.setActiveFlag(Boolean.FALSE);
			batch.setBatchDeactivateDate(LocalDate.now());

			batchRepository.save(batch);

			response = true;

			doc.setData(response);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Batch Deleted Successfully");

			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	/**
	 * @author updated by vk
	 * dated 24-12-2021
	 *
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document batchSubscriptionCheckexpiry(Long idSubscription) {
		Document doc = new Document<>();
		try {
			BatchSubscriptionExpiryDTO batchSubscriptionExpiryDTO = new BatchSubscriptionExpiryDTO();
			StudentSubscription studentSub = studentSubscriptionRepository.findByIdStudentSubscription(idSubscription);
			if (studentSub == null)
				throw new AppException("invalid StudentSubscription");
			Date today = new Date();

			Date myDate = Date.from(studentSub.getSubscriptionEndDate());
			if (today.before(myDate)) {
				batchSubscriptionExpiryDTO.setExpiry(false);
				batchSubscriptionExpiryDTO.setSubscriptionEnded(false);
				batchSubscriptionExpiryDTO.setExpirymessage("");
			}

			if (today.after(myDate)) {
				batchSubscriptionExpiryDTO.setExpiry(true);
				batchSubscriptionExpiryDTO.setSubscriptionEnded(true);
				batchSubscriptionExpiryDTO.setExpirymessage("This subscription has expired. Please check available batches for new subcription.");
			}

			Date nextPayDate = Date.from(studentSub.getNextPaymentDate());

			if (today.before(myDate) && today.after(nextPayDate)) {
				SimpleDateFormat dateFormated = new SimpleDateFormat("dd-MM-yyyy");
				batchSubscriptionExpiryDTO.setExpiry(true);
				batchSubscriptionExpiryDTO.setSubscriptionEnded(false);
				batchSubscriptionExpiryDTO.setExpirymessage("Your payment is due, please make payment before "+dateFormated.format(myDate)+" to avoid the interruption.");
			}

			/* Displaying message before 5 days of the last payment date */
			Calendar cal = Calendar.getInstance();
			cal.setTime(nextPayDate);
			cal.add(Calendar.DATE, -5);
			Date dateBeforeExpiry = cal.getTime();
			
			if (today.after(dateBeforeExpiry) && today.before(nextPayDate)) {
				SimpleDateFormat dateFormated = new SimpleDateFormat("dd-MM-yyyy");
				batchSubscriptionExpiryDTO.setExpiry(true);
				batchSubscriptionExpiryDTO.setSubscriptionEnded(false);
				batchSubscriptionExpiryDTO.setExpirymessage("Your subscription is about to end. Please make a payment before "+dateFormated.format(nextPayDate)+" to avoid interruption.");
			}

			doc.setData(batchSubscriptionExpiryDTO);
			doc.setStatusCode(HttpStatus.OK.value());
			doc.setMessage("Successfully");
			return doc;
		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<Subject>> getAllBatchSubject(Long idClassStandard) {
		Document<List<Subject>> result = new Document<List<Subject>>();

		try {

			List<Product> productList = productRepository.findByIdClassStandardAndActiveFlag(
					idClassStandard,Boolean.TRUE);

			if (productList.isEmpty())
				throw new AppException("No Product List found.");

			Set<Subject> tempSubject = new HashSet<Subject>();

			if (productList.isEmpty())
				throw new NullPointerException(" No Product Found , for the selected classStandard!");

			for (Product p : productList) {
				if (p.getIdProductLine() != 5 && p.getIdProductLine() != 6 && p.getIdProductLine() != 7) {
					Subject temp = subjectRepository.findByIdSubject(p.getIdSubject());

					tempSubject.add(temp);
				}

			}

			List<Subject> subjectList = tempSubject.stream()
					.sorted((s1, s2) -> s1.getIdSubject().compareTo(s2.getIdSubject())).collect(Collectors.toList());

			result.setData(subjectList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<List<ProductLine>> getAllProductLineByBatchSubject(Long idClassStandard, Long idSyllabus) {

		Document<List<ProductLine>> result = new Document<List<ProductLine>>();

		try {

			Set<Long> ids = new HashSet<Long>();
			ids.add(2L);
			ids.add(3L);

			List<ProductGroup> productGroupList = productGroupRepository
					.findByIdClassStandardAndIdSyllabusAndIdProductLineIn(idClassStandard, idSyllabus, ids);

			Set<Long> tempProductLine = new HashSet<>();

			if (productGroupList.isEmpty())
				throw new NullPointerException(" No ProductGroup Found , for the selected classStandard!");

			productGroupList.removeIf(e -> !tempProductLine.add(e.getIdProductLine()));

			List<Long> finalProductLineIdList = new ArrayList<>(tempProductLine);

			List<ProductLine> ProductLineList = productLineRepository.findByIdProductLineIn(finalProductLineIdList);

			if (ProductLineList.isEmpty())
				throw new NullPointerException(" No Batch Found !");

			result.setData(ProductLineList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	public Document<List<BatchResponseDTO>> getTeacherScheduledBatch(Long idTeacher, Long idClassStandard) {
		Document<List<BatchResponseDTO>> result = new Document<>();

		try {
			List<Batch> listOfBatch = new ArrayList<>();
			if (idClassStandard!=null) {				
				listOfBatch = batchRepository
						.findByTeacherByClassStandard(idTeacher, Boolean.TRUE, 0, idClassStandard);
			}else {
				listOfBatch = batchRepository
						.findByIdTeacherAndActiveFlagAndCurrentVacancyGreaterThan(idTeacher, Boolean.TRUE, 0);
			}

			List<BatchResponseDTO> respose = new ArrayList<>();

			if (!listOfBatch.isEmpty()) {
				//returning only non-group batches
				listOfBatch = listOfBatch.stream()
						.filter(batch-> batch.getIdBatchGroup()==null).collect(Collectors.toList());
				
				for (Batch batch : listOfBatch) {

					Product product = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

					if (product == null)
						throw new NullPointerException("Cannot find the product details");

					ClassStandard classStandard = classRepository.findByIdClassStandard(product.getIdClassStandard());

					if (classStandard == null)
						throw new NullPointerException("Cannot Find the class standard details");

					Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());
					if (subject == null)
						throw new NullPointerException("Cannot Find the Subject details");

					Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
					if (teacher == null)
						throw new NullPointerException("Cannot Find the Teacher details");

					BatchResponseDTO brd = new BatchResponseDTO();
					brd.setBatchName(batch.getBatchName());
					brd.setClassStandard(classStandard);
					brd.setCurrentOccupancy(batch.getCurrentOccupancy());
					brd.setCurrentVacancy(batch.getCurrentVacancy());
					brd.setDemoVideoUrl(batch.getDemoVideoUrl());
					brd.setEndDate(batch.getBatchEndDate());
					brd.setIdBatch(batch.getIdBatch());
					brd.setProduct(product);
					if (batch.getIdBatch() != null) {
						Set<SpecialOfferDTO> specialOfferDTOList = new HashSet<SpecialOfferDTO>();

						specialOfferDTOList = specialOfferRepository.findSpecialOfferByBatchId(batch.getIdBatch());
						brd.setSpecialOfferDTO(specialOfferDTOList);

					}

					List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());

					if (bcList.isEmpty()) {
						throw new NullPointerException("");
					}

					List<BatchScheduleDTO> scheduleDtos = new ArrayList<>();

					bcList.forEach(bs -> {
						BatchScheduleDTO btdo = new BatchScheduleDTO();

						btdo.setDayOfWeek(bs.getDayOfWeek());
						btdo.setFromTime(bs.getFromTime());
						btdo.setToTime(bs.getToTime());
						scheduleDtos.add(btdo);
					});

					brd.setBatchSchedules(scheduleDtos);

					brd.setStartingDate(batch.getBatchStartDate());
					brd.setSubject(subject);

					teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

					if (teacher == null) {
						throw new NullPointerException("Invalid teacher Id Provided. Please Select Teacher");
					}

					brd.setTeacherExpLevel(teacher.getExpLevel());
					brd.setTeacherName(teacher.getTeacherName());
					brd.setTeacherRating(teacher.getRating());

					respose.add(brd);

				}

				result.setData(respose);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {

				result.setData(null);
				result.setMessage("Batch List is Empty");
				result.setStatusCode(500);
			}
		}

		catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public Document<?> getProductLineForBatchOfOneStudents() {

		Document<ProductLine> doc = new Document<>();

		try {

			ProductLine productLine = productLineRepository.findByidProductLine(1L);

			if (productLine == null) {
				throw new NullPointerException("Product Lie Data Not Found");
			}

			doc.setData(productLine);
			doc.setMessage("Request Successful");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<List<PersonalCoachingBatchListResponseDTO>> getBatchInformationForPersonalCoaching(
			PersonalCoachingFilterDTO personalCoachingFilterDTO) {

		Document<List<PersonalCoachingBatchListResponseDTO>> doc = new Document<>();

		try {

			State state = stateRepository.findByIdState(personalCoachingFilterDTO.getIdState());

			if (state == null) {
				throw new NullPointerException("Invalid State Id Provided. Please Select a State");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(personalCoachingFilterDTO.getIdSyllabus());

			if (syllabus == null) {
				throw new NullPointerException("Invalid Syllabus Id Provided. Please Select Syllabus");
			}

			ClassStandard classStandard = classRepository
					.findByIdClassStandard(personalCoachingFilterDTO.getIdClassStandard());

			if (classStandard == null) {
				throw new NullPointerException(
						"Invalid Class Standard Id Provided. Please Select a Valid Class Standard");
			}

			Subject subject = subjectRepository.findByIdSubject(personalCoachingFilterDTO.getIdSubject());

			if (subject == null) {
				throw new NullPointerException("Invalid Subject Id Provided. Please Select Subject");

			}

			Teacher teacher = null;

			DayOfWeekCode dayOfWeekCode = null;

			if (!(personalCoachingFilterDTO.getIdTeacher() == null)) {

				teacher = teacherRepository.findByIdTeacher(personalCoachingFilterDTO.getIdTeacher());
			}

			if (!(personalCoachingFilterDTO.getIdDayofWeekCode() == null)) {

				dayOfWeekCode = dayofWeekCodeRepository
						.findByIdDayofWeekCode(personalCoachingFilterDTO.getIdDayofWeekCode());
			}

			ProductGroup productGroup = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(
					personalCoachingFilterDTO.getIdClassStandard(), personalCoachingFilterDTO.getIdProductLine(),
					personalCoachingFilterDTO.getIdSyllabus());

			if (productGroup == null) {
				throw new NullPointerException(
						"No Product Group Found For the Selected Class Standard and Syllabus.Contact Admin");

			}

			Product product = productRepository
					.findByIdSubjectAndIdProductGroupAndIdProductLineAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
							
							personalCoachingFilterDTO.getIdSubject(), productGroup.getIdProductGroup(),
							personalCoachingFilterDTO.getIdProductLine(),
							personalCoachingFilterDTO.getIdClassStandard(), personalCoachingFilterDTO.getIdState(),
							personalCoachingFilterDTO.getIdSyllabus(),Boolean.TRUE);

			if (product == null) {
				throw new NullPointerException("No Product Found For the Selections.Contact Admin");
			}

			List<Batch> listOfBatch = new ArrayList<>();

			Long idTeacher = (teacher != null) ? teacher.getIdTeacher() : null;
			LocalTime batchFromTime = (personalCoachingFilterDTO.getFromTime() != null)
					? personalCoachingFilterDTO.getFromTime()
					: null;
			LocalTime batchToTime = (personalCoachingFilterDTO.getToTime() != null)
					? personalCoachingFilterDTO.getToTime()
					: null;

			listOfBatch = batchRepository

					.findPersonalAcademicBatch(product.getIdProduct(), idTeacher, dayOfWeekCode, batchFromTime,
							batchToTime);

			if (!listOfBatch.isEmpty()) {

				List<PersonalCoachingBatchListResponseDTO> batchListResponseDTOs = new ArrayList<PersonalCoachingBatchListResponseDTO>();

				for (Batch batch : listOfBatch) {

					teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

					PersonalCoachingBatchListResponseDTO batchListResponseDTO = new PersonalCoachingBatchListResponseDTO();
					Teacher t = new Teacher();
					t.setIdTeacher(teacher.getIdTeacher());
					t.setIntroVideo(teacher.getIntroVideo());
					t.setTeacherName(teacher.getTeacherName());
					t.setTeacherHeader(teacher.getTeacherHeader());
					t.setTeacherDesc(teacher.getTeacherDesc());
					t.setRating(teacher.getRating());

					batchListResponseDTO.setBatch(batch);
					batchListResponseDTO.setProduct(product);
					batchListResponseDTO.setTeacher(t);

					batchListResponseDTOs.add(batchListResponseDTO);
				}

				doc.setData(batchListResponseDTOs);
				doc.setMessage("List Of Batches For Selected Filters");
				doc.setStatusCode(200);

			}

			else {
				doc.setData(new ArrayList<>());
				doc.setMessage("Batch List is Empty");
				doc.setStatusCode(500);

			}

		}

		catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<List<ProductLine>> getAllProductLineECABatch(Long idClassStandard, Long idSyllabus) {

		Document<List<ProductLine>> result = new Document<List<ProductLine>>();

		try {

			List<ProductLine> plList = productLineRepository.findByProductCategory("BATCH_EXTRA_CURR");

			if (plList.isEmpty())
				throw new NullPointerException("No Product line data found for extra curricular batch.");

			Set<Long> tempIds = new HashSet<>();

			plList.removeIf(e -> !tempIds.add(e.getIdProductLine()));

			List<ProductGroup> productGroupList = productGroupRepository
					.findByIdClassStandardAndIdSyllabusAndIdProductLineIn(idClassStandard, idSyllabus, tempIds);

			Set<Long> tempProductLine = new HashSet<>();

			if (productGroupList.isEmpty())
				throw new NullPointerException(" No ProductGroup Found , for the selected classStandard!");

			productGroupList.removeIf(e -> !tempProductLine.add(e.getIdProductLine()));

			List<Long> finalProductLineIdList = new ArrayList<>(tempProductLine);

			List<ProductLine> ProductLineList = productLineRepository.findByIdProductLineIn(finalProductLineIdList);

			if (ProductLineList.isEmpty())
				throw new NullPointerException(" No Batch Found !");

			result.setData(ProductLineList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<List<Subject>> getAllBatchECASubject(Long idClassStandard, Long idProductLine, Long idSyllabus) {

		Document<List<Subject>> result = new Document<List<Subject>>();

		try {

			List<Product> productList = productRepository
					.findByIdClassStandardAndIdSyllabusAndIdProductLineAndActiveFlag(idClassStandard, idSyllabus, idProductLine,Boolean.TRUE);

			Set<Subject> tempSubject = new HashSet<Subject>();

			if (productList.isEmpty())
				throw new NullPointerException(" No Product Found , for the selected classStandard and Product line !");

			for (Product p : productList) {
				Subject temp = subjectRepository.findByIdSubject(p.getIdSubject());
				tempSubject.add(temp);
			}

			List<Subject> subjectList = tempSubject.stream()
					.sorted((s1, s2) -> s1.getIdSubject().compareTo(s2.getIdSubject())).collect(Collectors.toList());

			result.setData(subjectList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<?> getProductLineForBatchOfOneStudentsExtraCurricular() {

		Document<ProductLine> doc = new Document<>();

		try {

			ProductLine productLine = productLineRepository.findByidProductLine(7L);

			if (productLine == null) {
				throw new NullPointerException("Product Lie Data Not Found");
			}

			doc.setData(productLine);
			doc.setMessage("Request Successful");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<List<PersonalCoachingBatchListResponseDTO>> getBatchInformationForECAPersonalCoaching(
			ECAPersonalCoachingFilterDTO personalCoachingFilterDTO) {

		Document<List<PersonalCoachingBatchListResponseDTO>> doc = new Document<>();

		try {

			// values are been hardcoded since these are not applicable fiellds
			Long idClassStandard, idSyllabus, idState;
			idClassStandard = 4L;
			idSyllabus = 4L;
			idState = 6L;
			Teacher teacher = null;
			DayOfWeekCode dayOfWeekCode = null;
			LocalTime fromTime = personalCoachingFilterDTO.getFromTime();
			LocalTime toTime = personalCoachingFilterDTO.getToTime();

			LevelExtraCurricular lec = levelExtraCurricularRepository
					.findByIdLevelExtraCurricular(personalCoachingFilterDTO.getIdLevelExtraCurricular());

			if (lec == null)
				throw new NullPointerException("Invalid ExtraCurricular level Id Provided. Please Select Level");

			Subject subject = subjectRepository.findByIdSubject(personalCoachingFilterDTO.getIdSubject());

			if (subject == null)
				throw new NullPointerException("Invalid Subject Id Provided. Please Select Subject");

			if (!(personalCoachingFilterDTO.getIdTeacher() == null)) {

				teacher = teacherRepository.findByIdTeacher(personalCoachingFilterDTO.getIdTeacher());
			}

			if (!(personalCoachingFilterDTO.getIdDayofWeekCode() == null)) {

				dayOfWeekCode = dayofWeekCodeRepository
						.findByIdDayofWeekCode(personalCoachingFilterDTO.getIdDayofWeekCode());
			}

			ProductGroup productGroup = productGroupRepository
					.findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(idClassStandard,
							personalCoachingFilterDTO.getIdProductLine(), idSyllabus, lec.getLevel());

			if (productGroup == null)
				throw new NullPointerException("No Product Group Found For the Selected Level .Contact Admin");

			Product product = productRepository.findByIdProductGroupAndIdSubjectAndIdStateAndExtraCurrCategoryAndActiveFlag(
					productGroup.getIdProductGroup(), subject.getIdSubject(), idState,
					productGroup.getExtraCurrCategory(),Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product Found For the Selections Level and subject .Contact Admin");

			List<Batch> listOfBatch = new ArrayList<>();

			// if teacher is not null
			if (teacher != null) {
				if (teacher != null && dayOfWeekCode != null && fromTime != null && toTime != null) {
					listOfBatch = batchRepository
							.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(
									product.getIdProduct(), teacher.getIdTeacher(), dayOfWeekCode.getIdDayofWeekCode(),
									fromTime, toTime);
				} else if (teacher != null && dayOfWeekCode != null && fromTime != null && toTime == null) {
					listOfBatch = batchRepository
							.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(
									product.getIdProduct(), teacher.getIdTeacher(), dayOfWeekCode.getIdDayofWeekCode(),
									fromTime);
				} else if (teacher != null && dayOfWeekCode != null && fromTime == null && toTime != null) {
					listOfBatch = batchRepository
							.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCodeAndAndBatchToTime(
									product.getIdProduct(), teacher.getIdTeacher(), dayOfWeekCode.getIdDayofWeekCode(),
									toTime);
				} else if (teacher != null && dayOfWeekCode != null && fromTime == null && toTime == null) {
					listOfBatch = batchRepository.findByIdProductAndIdTeacherAndDayOfWeekCode_idDayofWeekCode(
							product.getIdProduct(), teacher.getIdTeacher(), dayOfWeekCode.getIdDayofWeekCode());
				} else if (teacher != null && dayOfWeekCode == null && fromTime != null && toTime != null) {
					listOfBatch = batchRepository.findByIdProductAndIdTeacherAndBatchFromTimeAndBatchToTime(
							product.getIdProduct(), teacher.getIdTeacher(), fromTime, toTime);
				} else if (teacher != null && dayOfWeekCode == null && fromTime == null && toTime != null) {
					listOfBatch = batchRepository.findByIdProductAndIdTeacherAndBatchToTime(product.getIdProduct(),
							teacher.getIdTeacher(), toTime);
				} else if (teacher != null && dayOfWeekCode == null && fromTime != null && toTime == null) {
					listOfBatch = batchRepository.findByIdProductAndIdTeacherAndBatchFromTime(product.getIdProduct(),
							teacher.getIdTeacher(), fromTime);
				} else {

					listOfBatch = batchRepository.findByIdProductAndIdTeacher(product.getIdProduct(),
							teacher.getIdTeacher());
				}
			}

			// if day of code is not null

			else if (dayOfWeekCode != null) {
				if (dayOfWeekCode != null && fromTime != null && toTime != null) {
					listOfBatch = batchRepository
							.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTimeAndBatchToTime(
									product.getIdProduct(), dayOfWeekCode.getIdDayofWeekCode(), fromTime, toTime);
				} else if (dayOfWeekCode != null && fromTime != null && toTime == null) {
					listOfBatch = batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchFromTime(
							product.getIdProduct(), dayOfWeekCode.getIdDayofWeekCode(), fromTime);
				} else if (dayOfWeekCode != null && fromTime == null && toTime != null) {
					listOfBatch = batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCodeAndBatchToTime(
							product.getIdProduct(), dayOfWeekCode.getIdDayofWeekCode(), toTime);
				} else {
					listOfBatch = batchRepository.findByIdProductAndDayOfWeekCode_idDayofWeekCode(
							product.getIdProduct(), dayOfWeekCode.getIdDayofWeekCode());
				}

			}
			// if only from time has mentioned
			else if (fromTime != null) {
				if (toTime != null) {
					listOfBatch = batchRepository.findByIdProductAndBatchFromTimeAndBatchToTime(product.getIdProduct(),
							fromTime, toTime);
				} else {
					listOfBatch = batchRepository.findByIdProductAndBatchFromTime(product.getIdProduct(), fromTime);
				}

			} else if (toTime != null) {
				listOfBatch = batchRepository.findByIdProductAndBatchToTime(product.getIdProduct(), toTime);
			}

			else {
				listOfBatch = batchRepository.findByIdProduct(product.getIdProduct());
			}

			List<PersonalCoachingBatchListResponseDTO> batchListResponseDTOs = new ArrayList<PersonalCoachingBatchListResponseDTO>();

			if (!listOfBatch.isEmpty()) {

				for (Batch batch : listOfBatch) {

					teacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());
					PersonalCoachingBatchListResponseDTO batchListResponseDTO = new PersonalCoachingBatchListResponseDTO();
					Teacher t = new Teacher();
					t.setIdTeacher(teacher.getIdTeacher());
					t.setIntroVideo(teacher.getIntroVideo());
					t.setTeacherName(teacher.getTeacherName());
					t.setTeacherHeader(teacher.getTeacherHeader());
					t.setTeacherDesc(teacher.getTeacherDesc());
					t.setRating(teacher.getRating());
					batchListResponseDTO.setBatch(batch);
					batchListResponseDTO.setProduct(product);
					batchListResponseDTO.setTeacher(t);
					batchListResponseDTOs.add(batchListResponseDTO);
				}

				doc.setData(batchListResponseDTOs);
				doc.setMessage("List Of Batches For Selected Filters");
				doc.setStatusCode(200);

			} else {
				doc.setData(batchListResponseDTOs);
				doc.setMessage("Batch List is Empty");
				doc.setStatusCode(500);

			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings("unused")
	@Override
	public Document<?> studentJoinPersonalCoaching(Long idBatch, Long idStudent) {
		Document<String> doc = new Document<>();

		try {

			if (idStudent == null) {
				throw new NullPointerException("Student Details Not Found");
			}

			if (idBatch == null) {
				throw new NullPointerException("Batch Id Not Found");
			}

			Batch batch = batchRepository.findByIdBatch(idBatch);

			if (batch == null) {
				throw new NullPointerException("Batch Info Not Found");
			}
			
			Teacher teacher = teacherRepository.findByIdTeacherAndActiveFlag(batch.getIdTeacher(),true);
			
			if (teacher == null) {
				throw new NullPointerException("Teacher Info Not Found");
			}

			StudentSubscription fetchedStudentSubscription = studentSubscriptionRepository
					.findByIdStudentAndIdBatchAndActiveFlagAndFreeFlag(idStudent, idBatch, Boolean.TRUE, Boolean.FALSE);

			if (fetchedStudentSubscription == null) {
				throw new NullPointerException("Subscription Not Found. Make Sure You have Purchased the Batch");
			}

			WebExPool webExPool = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

			if (webExPool == null) {
				throw new NullPointerException("WebEx Host Credential Not Found");
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todaysDate = simpleDateFormat.format(new Date());

			BatchRunDetail batchRunDetail = batchRunDetailRepository.findByIdBatchAndBatchRundate(idBatch, todaysDate);

			if (batchRunDetail == null) {
//				throw new NullPointerException("No Batch Run Details Info Found for the Selected Batch And Todays Date."
//						+ "Class not Yet Started");
				throw new NullPointerException("Class not Yet Started");
			}

			String url = "https://api.webex.com/WBXService/XMLService";

			RestTemplate restTemplate = new RestTemplate();

			String getWebExMeetingXmlBody = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n"
					+ "                         <serv:message\r\n"
					+ "                         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "                       xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n"
					+ "                       <header>\r\n" + "                           <securityContext>\r\n"
					+ "                               <siteName>vistafoundation</siteName>\r\n"
					+ "                               <webExID>" + webExPool.getWebExUserId() + "</webExID>\r\n"
					+ "                               <password>" + webExPool.getWebExPassword() + "</password>\r\n"
					+ "                           </securityContext>\r\n" + "                       </header>\r\n"
					+ "                       <body>\r\n"
					+ "                           <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.GetMeeting\">\r\n"
					+ "                               <meetingKey>" + batchRunDetail.getMeetingId()
					+ "</meetingKey>\r\n" + "                           </bodyContent>\r\n"
					+ "                       </body>\r\n" + "                   </serv:message>";

			ResponseEntity<String> getMeetingResult = restTemplate.postForEntity(url, getWebExMeetingXmlBody,
					String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStream = new ByteArrayInputStream(getMeetingResult.getBody().getBytes());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			org.w3c.dom.Document document = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStream);

			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			NodeList meetingRunningStatus = document.getElementsByTagName("meet:status");

			String meetingRunningStatusFinal = null;

			for (int i = 0; i < meetingRunningStatus.getLength(); i++) {
				Node meetingRunningStatusNode = meetingRunningStatus.item(i);
				Element serviceTypeElement = (Element) meetingRunningStatusNode;
				System.out.println("------------Meeting Running Status----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingRunningStatusFinal = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}

			if (meetingRunningStatusFinal == null) 
			{
				doc.setData("INVALID PROGRESS");
				doc.setMessage("invalid progress type");
				doc.setStatusCode(500);
				
			}
			
			else if (meetingRunningStatusFinal.equals("NOT_INPROGRESS")) {
				doc.setData("NOT_INPROGRESS");
				doc.setMessage("Class Has not Yet Started");
				doc.setStatusCode(500);

			} else if (meetingRunningStatusFinal.equals("INPROGRESS")) {

				Student student = studentRepository.findByIdStudent(idStudent);

				// Now Get Attendee Join Meeting Url

				String xmlBodyForGettingAttendeeUrl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
						+ "    <header>\r\n" + "        <securityContext>\r\n" + "            <webExID>"
						+ webExPool.getWebExUserId() + "</webExID>\r\n" + "            <password>"
						+ webExPool.getWebExPassword() + "</password>\r\n" + "            <siteID>13696027</siteID>\r\n"
						+ "				<siteName>vistafoundation</siteName>\r\n"
						+ "            <partnerID>I2WNi1i-xdYDHn-HNXJCMA</partnerID>\r\n" + "            <email>"
						+ webExPool.getWebExUserId() + "</email>\r\n" + "        </securityContext>\r\n"
						+ "    </header>\r\n" + "    <body>\r\n" + "        <bodyContent\r\n"
						+ "            xsi:type=\"java:com.webex.service.binding.meeting.GetjoinurlMeeting\">\r\n"
						+ "            <sessionKey>" + batchRunDetail.getMeetingId() + "</sessionKey>\r\n"
						+ "            <attendeeName>" + student.getUser().getFirstName() + "</attendeeName>\r\n"
						+ "            <attendeeEmail>" + student.getUser().getEmail() + "</attendeeEmail>\r\n"
						+ "            <meetingPW>" + batchRunDetail.getMeetingPassword() + "</meetingPW>\r\n"
						+ "        </bodyContent>\r\n" + "    </body>\r\n" + "</serv:message>";

				ResponseEntity<String> resultJoinMeetingUrl = restTemplate.postForEntity(url,
						xmlBodyForGettingAttendeeUrl, String.class);

				// Converting Response Entity to Input Stream Object
				InputStream targetStreamForAttendee = new ByteArrayInputStream(
						resultJoinMeetingUrl.getBody().getBytes());

				DocumentBuilderFactory factoryAttendee = DocumentBuilderFactory.newInstance();
				DocumentBuilder builderAttendee = null;
				org.w3c.dom.Document documentAttendee = null;
				builder = factory.newDocumentBuilder();
				document = builder.parse(targetStreamForAttendee);

				document.getDocumentElement().normalize();

				Element rootAttendee = document.getDocumentElement();

				NodeList attendeeJoinMeetingUrl = document.getElementsByTagName("meet:joinMeetingURL");

				String attendeeJoinMeetingUrlActual = null;

				for (int i = 0; i < attendeeJoinMeetingUrl.getLength(); i++) {
					Node attendeeJoinMeetingUrlNode = attendeeJoinMeetingUrl.item(i);
					Element serviceTypeElement = (Element) attendeeJoinMeetingUrlNode;
					System.out.println("------------Attendee Join Meeting Url----------");
					System.out.println(serviceTypeElement.getTextContent());
					attendeeJoinMeetingUrlActual = serviceTypeElement.getTextContent();
					System.out.println("----------------------------------------");

				}

				batchRunDetail.setAttendeeMeetingUrl(attendeeJoinMeetingUrlActual);
				batchRunDetailRepository.save(batchRunDetail);

				BatchStudentAttendance studentAttendance = batchStudentAttendanceRepository
						.findByIdBatchAndIdStudentSubscrAndBatchRunDate(idBatch,
								fetchedStudentSubscription.getIdStudentSubscription(), todaysDate);

				if (studentAttendance == null) {
					studentAttendance = new BatchStudentAttendance();

					studentAttendance.setAbsentPresentFlag(Boolean.TRUE);
					studentAttendance.setBatchRunDate(todaysDate);
					studentAttendance.setIdBatch(idBatch);
					studentAttendance.setIdStudentSubscr(fetchedStudentSubscription.getIdStudentSubscription());
					BatchStudentAttendance newStudentAttendance = batchStudentAttendanceRepository
							.save(studentAttendance);

					doc.setData(attendeeJoinMeetingUrlActual);
					doc.setMessage(
							"Attendance Taken Successfully. You have been Marked as Present.Redirecting to CLassroom");
					doc.setStatusCode(200);
				}

				else if (studentAttendance.getAbsentPresentFlag().equals(Boolean.TRUE)) {
					doc.setData(attendeeJoinMeetingUrlActual);
					doc.setMessage(
							"Attendance Is Already Taken, You have been marked as Present.Redirecting to CLassroom");
					doc.setStatusCode(200);
				} else {
					studentAttendance.setAbsentPresentFlag(Boolean.TRUE);
					BatchStudentAttendance updated = batchStudentAttendanceRepository.save(studentAttendance);
					doc.setData(attendeeJoinMeetingUrlActual);
					doc.setMessage(
							"Attendance Taken Successfully. You have been Marked as Present.Redirecting to CLassroom");
					doc.setStatusCode(200);
				}
			}
			else {
				doc.setData("INVALID PROGRESS");
				doc.setMessage("invalid progress type");
				doc.setStatusCode(500);
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return doc;
	}

	@Override
	public List<BatchRunDetail> fetchBatchRunDetailsForTodaysDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate = sdf.format(new Date());

		List<BatchRunDetail> listOfBatchRunDetails = batchRunDetailRepository.findByBatchRundate(todaysDate);

		return listOfBatchRunDetails;
	}

	@Override
	public Document<?> getBatchRunDetailWebExInfoForTodaysDate(Long idBatch) {

		Document<BatchRunDetail> doc = new Document<>();

		try {

			if (idBatch == null) {
				throw new NullPointerException("Batch Id Cannot be Null");
			}

			Batch batch = batchRepository.findByIdBatch(idBatch);

			if (batch == null) {
				throw new NullPointerException("Batch Not Found");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todaysDate = sdf.format(new Date());

			BatchRunDetail batchRunDetail = batchRunDetailRepository.findByIdBatchAndBatchRundate(idBatch, todaysDate);

			if (batchRunDetail == null) {
				throw new NullPointerException(
						"No Batch Run Details Info Found for the Selected Batch And Todays Date. Class Not Yet Started");
			}

			doc.setData(batchRunDetail);
			doc.setMessage("Batch Run Detail WebEx Info for Todays Date and Selected Batch");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<List<BatchListDTO>> getAllBatchesByIdTeacher(Long idTeacher) {

		Document<List<BatchListDTO>> result = new Document<>();
		try {

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			if (teacher == null)
				throw new NullPointerException("No Teacher found with this Id!");
			List<Batch> batchList = batchRepository.findAllBatchesByIdTeacher(idTeacher);
			if (batchList.isEmpty())
				throw new NullPointerException("No Batches Found");

			List<BatchListDTO> batchListsResponse = new ArrayList<>();

			for (Batch batch : batchList) {

				BatchListDTO batchListDTO = new BatchListDTO();

				batchListDTO.setBatch(batch);

				// Get Teacher Details By Teacher Id From Each BAtch
				Teacher existingTeacher = teacherRepository.findByIdTeacher(batch.getIdTeacher());

				if (existingTeacher == null)
					throw new NullPointerException("No Teacher found with this Id!");

				batchListDTO.setTeacher(existingTeacher);

				// get product line info from product table
				Product productObj = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

				if (productObj == null)
					throw new NullPointerException("No Product found with this Id!");

				// Get the Class Standard from Product Object
				ClassStandard std = classRepository.findByIdClassStandard(productObj.getIdClassStandard());

				if (std == null)
					throw new NullPointerException("No ClassStandard found with this Id!");

				batchListDTO.setClassStandard(std);

				// Get the Subject from Product Object
				Subject subject = subjectRepository.findByIdSubject(productObj.getIdSubject());

				if (subject == null)
					throw new NullPointerException("No Subject found with this Id!");

				batchListDTO.setSubject(subject);

				// Get the Product Line from ProductObj
				ProductLine productLineObj = productLineRepository.findByidProductLine(productObj.getIdProductLine());

				if (productLineObj == null)
					throw new NullPointerException("No ProductLine found with this Id!");

				batchListDTO.setProduct(productObj);

				batchListsResponse.add(batchListDTO);
			}

			result.setData(batchListsResponse);
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
	public Document<List<TeacherBatchDetailsDTO>> getAllBatchDetailsList(Long idTeacher) {

		Document<List<TeacherBatchDetailsDTO>> result = new Document<>();
		try {
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			if (teacher == null)
				throw new NullPointerException("No Teacher found with this Id!");
			List<Batch> batchList = batchRepository.findAllBatchesByIdTeacher(idTeacher);
			if (batchList.isEmpty())
				throw new NullPointerException("No Batches Found");

			List<TeacherBatchDetailsDTO> tbdDtoList = new ArrayList<>();

			for (Batch batch : batchList) {

				Product product = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);
				if (product == null)
					throw new NullPointerException("No Product found !");
				ClassStandard std = classRepository.findByIdClassStandard(product.getIdClassStandard());
				if (std == null)
					throw new NullPointerException("No Class Found");
				Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());
				if (subject == null)
					throw new NullPointerException("No Subject Found");
				List<BatchCalender> batchCalenderList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());
				if (batchCalenderList.isEmpty())
					throw new NullPointerException("No Calender found");

				TeacherBatchDetailsDTO tbdDto = new TeacherBatchDetailsDTO();
				tbdDto.setIdBatch(batch.getIdBatch());
				tbdDto.setBatchName(batch.getBatchName());
				tbdDto.setClassStandard(std);
				tbdDto.setSubject(subject);
				tbdDto.setBatchCalender(batchCalenderList);

				tbdDtoList.add(tbdDto);
			}
			result.setData(tbdDtoList);
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
	public Document<List<Map<String, Object>>> getAllBatchConductedDetails(Long idBatch) {

		Document<List<Map<String, Object>>> result = new Document<>();
		try {
			List<Object[]> batchRunList = batchRunDetailRepository.findBatchRundateDetail(idBatch);
			if (batchRunList.isEmpty())
				throw new NullPointerException("No batch run details found");

			List<Map<String, Object>> l = new ArrayList<>();

			for (Object[] obj : batchRunList) {
				Map<String, Object> m = new HashMap<>();
				m.put("idBatch", obj[0]);
				m.put("batchName", obj[2]);
				m.put("batchRundate", obj[1]);

				l.add(m);
			}
			if (l.isEmpty())
				throw new NullPointerException("No data found");

			result.setData(l);
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
	public Document<?> getAllBatchRecordingsForParticularBatchSelected(Long idBatch) {

		Document<List<BatchRunRecording>> response = new Document<>();

		try {

			if (idBatch == null) {
				throw new NullPointerException("Please Select a Batch. Invalid Batch Id Provided");
			}

			Batch batch = batchRepository.findByIdBatch(idBatch);

			if (batch == null) {
				throw new NullPointerException("Batch info Not Found for the selected Batch");
			}

			List<BatchRunDetail> listOfAllBatchRunDetailsForThisIdBatch = batchRunDetailRepository
					.findByIdBatch(idBatch);

			if (listOfAllBatchRunDetailsForThisIdBatch.isEmpty()) {
				response.setData(new ArrayList<>());
				response.setMessage("No Batch Run Details Info Found For the Batch Selected");
				response.setStatusCode(200);
			} else {

				List<BatchRunRecording> batchRecordingsDTOs = new ArrayList<>();

				for (BatchRunDetail batchRunDetail : listOfAllBatchRunDetailsForThisIdBatch) {

					List<BatchRunRecording> list = batchRunRecordingRepository.findByBatchRunDetail(batchRunDetail);

					batchRecordingsDTOs.addAll(list);
				}

				response.setData(batchRecordingsDTOs);
				response.setMessage("List Of All Batch Recordings For the Selected Batch");
				response.setStatusCode(200);
			}

		} catch (Exception exp) {

			response.setData(null);
			response.setMessage(exp.getLocalizedMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return response;
	}

	@Override
	public Document<Boolean> checkTeacherAvailablity(BatchScheduleDTO request, Long idTeacher, Long idBatchGroup) {

		Document<Boolean> result = new Document<>();

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		try {
			
			boolean batchAvailablityFlag = false;

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			if (teacher == null)
				throw new NullPointerException("No Teacher found with this Id!");

			List<Batch> batchList = batchRepository.findByIdTeacherAndActiveFlag(teacher.getIdTeacher(), Boolean.TRUE);

			if (!batchList.isEmpty()) {

				List<BatchCalender> calenderList = batchCalenderRepository.findByBatchInAndDayOfWeek(batchList,
						request.getDayOfWeek());

				if (!calenderList.isEmpty()) {
					for (BatchCalender bc : calenderList) {

						boolean batchExists = false;

						batchExists = TimeComparison.checkTimeInBetween(
								LocalTime.parse(bc.getFromTime()).format(timeFormat),
								LocalTime.parse(bc.getToTime()).format(timeFormat),
								LocalTime.parse(request.getFromTime()).format(timeFormat),
								LocalTime.parse(request.getToTime()).format(timeFormat));

						if (batchExists) {
							result.setData(null);
							result.setMessage("Duplicate batch entry, with existing teacher at same time and same day");
							result.setStatusCode(HttpStatus.CONFLICT.value());
							return result;
						}

					}
				}
			}

			List<TeacherAvailability> taList = teacherAvailabilityRepository.findByIdTeacherAndDayOfWeek(idTeacher,
					request.getDayOfWeek());

			boolean availablityFlag = false;

			for (TeacherAvailability ta : taList) {

				LocalTime fromTime = LocalTime.parse(request.getFromTime());
				LocalTime toTime = LocalTime.parse(request.getToTime());

				// verifying from and to time as per request and teacher available on same
				// scehdule
				if (ta.getFromTime().compareTo(fromTime) == 0 && ta.getToTime().compareTo(toTime) == 0) {
					availablityFlag = true;
					break;
				}

				Boolean checkFromTime = null;

				// verifiying form time is matching with request from timing
				if (ta.getFromTime().compareTo(fromTime) == 0) {
					checkFromTime = true;
				} else {
					checkFromTime = (fromTime.isAfter(ta.getFromTime()) && fromTime.isBefore(ta.getToTime()));
				}

				Boolean checkToTime = null;
				// verifiying to time is matching with request to timing
				if (ta.getToTime().compareTo(toTime) == 0) {
					checkToTime = true;
				} else {
					checkToTime = (toTime.isAfter(ta.getFromTime()) && toTime.isBefore(ta.getToTime()));

				}

				if (checkFromTime && checkToTime) {
					availablityFlag = true;
					break;
				}

			}
			// check availablity of batchs for selected batch group
			if (!idBatchGroup.equals(-1L)) 
			{
				List<Batch> bList = batchRepository.findByIdBatchGroupAndActiveFlag(idBatchGroup, true);
 				
				if (!bList.isEmpty()) 
				{
					batchAvailablityFlag = checkConflictsOnBatch(bList,request.getFromTime(),request.getToTime(),request.getDayOfWeek());
				}
			}

			if (batchAvailablityFlag) {

				result.setData(null);
				result.setMessage("Conflict found with some other batch , with in this Batch Group.");
				result.setStatusCode(HttpStatus.CONFLICT.value());

			} 
			else if(availablityFlag) 
			{  
				result.setData(true);
				result.setMessage("Successfull request");
				result.setStatusCode(200);
				
			}
			
			else {

				result.setData(false);
				result.setMessage("Teacher Dosent have availablity schedule for this day of week and timing");
				result.setStatusCode(200);
			}
		} catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<List<BatchResponseDTO>> getBatchDataByFilter(PersonalCoachingFilterDTO request) {
		Document<List<BatchResponseDTO>> result = new Document<>();

		List<BatchResponseDTO> respose = new ArrayList<BatchResponseDTO>();
		try {
			State state = stateRepository.findByIdState(request.getIdState());
			if (state == null) {
				throw new NullPointerException("Invalid State Id Provided. Please Select a State");
			}
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(request.getIdSyllabus());
			if (syllabus == null) {
				throw new NullPointerException("Invalid Syllabus Id Provided. Please Select Syllabus");
			}
			ClassStandard classStandard = classRepository.findByIdClassStandard(request.getIdClassStandard());
			if (classStandard == null) {
				throw new NullPointerException(
						"Invalid Class Standard Id Provided. Please Select a Valid Class Standard");
			}
			// Added by vijay on 25th october to implement all subjects
			if (request.getIdSubject() != -1L) {
				Subject subject = subjectRepository.findByIdSubject(request.getIdSubject());
				if (subject == null) {
					throw new NullPointerException("Invalid Subject Id Provided. Please Select Subject");
				}
			}
			Teacher teacher = null;
			if (request.getIdTeacher() != null) {
				teacher = teacherRepository.findByIdTeacher(request.getIdTeacher());
				if (teacher == null) {
					throw new NullPointerException("Invalid teacher Id Provided. Please Select Teacher");
				}
			}
			ProductGroup productGroup = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(
					request.getIdClassStandard(), request.getIdProductLine(), request.getIdSyllabus());
			if (productGroup == null) {
				throw new NullPointerException(
						"No Product Group Found For the Selected Class Standard and Syllabus.Contact Admin");
			}
			List<Product> products = new ArrayList<Product>();
			if (request.getIdSubject() == -1L) {
				List<Product> productList = productRepository
						.findByIdProductGroupAndIdProductLineAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
								productGroup.getIdProductGroup(), request.getIdProductLine(),
								request.getIdClassStandard(), request.getIdState(), request.getIdSyllabus(),Boolean.TRUE);

				if (productList.isEmpty()) {
					throw new NullPointerException("No Product Found For the Selections.Contact Admin");
				}
				products.addAll(productList);
			} else {
				Product product = productRepository
						.findByIdSubjectAndIdProductGroupAndIdProductLineAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
								request.getIdSubject(), productGroup.getIdProductGroup(), request.getIdProductLine(),
								request.getIdClassStandard(), request.getIdState(), request.getIdSyllabus(),Boolean.TRUE);
				if (product == null) {
					throw new NullPointerException("No Product Found For the Selections.Contact Admin");
				}
				products.add(product);
			}
			
			if (products.isEmpty())
				throw new AppException("No Product Available for the selection.");
			
			   List<Batch> listOfBatch = new ArrayList<>();
			   
               for (Product productfetched : products) {
				
				
				Long idTeacher = (teacher != null) ? teacher.getIdTeacher() : null;
				
				if (idTeacher == null) {
					List<Batch> temp = batchRepository.findByIdProductAndActiveFlagAndCurrentVacancyGreaterThan(
							productfetched.getIdProduct(), Boolean.TRUE, 0);
					
					if(!temp.isEmpty())
					listOfBatch.addAll(temp);
					
				} else {
					List<Batch> temp = batchRepository.findByIdProductAndIdTeacherAndActiveFlagAndCurrentVacancyGreaterThan(
							productfetched.getIdProduct(), idTeacher, Boolean.TRUE, 0);
					listOfBatch.addAll(temp);
					
				
				}
            }
               
               
            if (listOfBatch.isEmpty()) throw new AppException("No Batchs Available for Selections.");
            
        	for (Batch batch : listOfBatch) {
        		
        		//
				BatchResponseDTO brd = new BatchResponseDTO();
				brd.setBatchName(batch.getBatchName());
				brd.setClassStandard(classStandard);
				brd.setCurrentOccupancy(batch.getCurrentOccupancy());
				brd.setCurrentVacancy(batch.getCurrentVacancy());
				brd.setDemoVideoUrl(batch.getDemoVideoUrl());
				brd.setEndDate(batch.getBatchEndDate());
				brd.setIdBatch(batch.getIdBatch());
				 Optional<Product> productOptional = products.stream()
					        .filter(p -> p.getIdProduct().equals(batch.getIdProduct()))
					        .findFirst();

					    if (productOptional.isPresent()) {
					        Product productfetched = productOptional.get();
					        brd.setProduct(productfetched);
					    } else {
					       throw new AppException("error occured");
					    }

					    brd.setStartingDate(batch.getBatchStartDate());		
				brd.setStartingDate(batch.getBatchStartDate());

				Subject subject = subjectRepository.findByIdSubject(brd.getProduct().getIdSubject());
				brd.setSubject(subject);
				// Need To fetch special offer
				if (batch.getIdBatch() != null) {
					Set<SpecialOfferDTO> specialOfferDTOList = new HashSet<SpecialOfferDTO>();

					specialOfferDTOList = specialOfferRepository.findSpecialOfferByBatchId(batch.getIdBatch());
					brd.setSpecialOfferDTO(specialOfferDTOList);

				}
				
				List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());
				
				if (!bcList.isEmpty()) {
					
					List<BatchScheduleDTO> scheduleDtos = new ArrayList<>();
					
					bcList.forEach(bs -> {
						BatchScheduleDTO btdo = new BatchScheduleDTO();
						btdo.setDayOfWeek(bs.getDayOfWeek());
						btdo.setFromTime(bs.getFromTime());
						btdo.setToTime(bs.getToTime());
						scheduleDtos.add(btdo);
					});
					
					brd.setBatchSchedules(scheduleDtos);
				}
			

				Teacher teacherDetails = teacherRepository.findByIdTeacher(batch.getIdTeacher());
				
				if (teacherDetails == null) {
					throw new NullPointerException("Invalid teacher Id Provided. Please Select Teacher");
				}
				
				//teacher details 
				brd.setTeacherExpLevel(teacherDetails.getExpLevel());
				brd.setTeacherName(teacherDetails.getTeacherName());
				brd.setTeacherRating(teacherDetails.getRating());
				
				
				if (batch.getIdBatchGroup()!=null) {
					BatchGroup bg = batchGroupRepository.findByIdBatchGroup(batch.getIdBatchGroup());
					if (bg!=null) {
						brd.setIdBatchGroup(bg.getIdBatchGroup());
						brd.setBatchGroupName(bg.getBatchGroupName());
					}
				}
				respose.add(brd);
				System.out.println(respose.size());
			}
        	
        	if (respose.isEmpty()) {
				result.setData(null);
				result.setMessage("No Batchs Available for Selections.");
				result.setStatusCode(500);
			} 
        	
        	else {
				List<BatchResponseDTO> sortedList = new ArrayList<BatchResponseDTO>();
				if (request.getIdSubject()==-1) {
					//sorting the batch list based on newly created batch groups first, when all subject filter is selected
					sortedList = respose.stream()
							.sorted(Comparator.comparing(BatchResponseDTO::getIdBatchGroup, Comparator.nullsFirst(Long::compareTo).reversed()))
							.collect(Collectors.toList());
					
				}else {
					sortedList = respose.stream()
							.filter(batch -> batch.getIdBatchGroup()==null)
							.sorted(Comparator.comparing(BatchResponseDTO::getIdBatch, Comparator.nullsFirst(Long::compareTo).reversed()))
							.collect(Collectors.toList());
				}
				result.setData(sortedList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}
               

		} 
		
		catch (Exception exp) 
		{
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<List<Object>> getBatchDetailsForTeacher(Long idTeacher) {

		Document<List<Object>> result = new Document<>();

		try {
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null)
				throw new NullPointerException("No Teacher found with this Id!");

			List<Batch> batches = batchRepository.findByIdTeacherAndActiveFlag(idTeacher, Boolean.TRUE);

			if (batches.isEmpty()) {
				throw new NullPointerException("No Batch Available for selected Teacher");
			}

			List<Object> response = new ArrayList<>();

			for (Batch batch : batches) {
				List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());

				if (bcList.isEmpty()) {
					throw new NullPointerException("batch calender list not found. ");
				}

				for (BatchCalender bc : bcList)

				{
					Map<String, String> temp = new HashMap<>();
					temp.put("day", bc.getDayOfWeek());
					temp.put("fromTime", bc.getFromTime());
					temp.put("toTime", bc.getToTime());
					temp.put("idBatch", batch.getIdBatch().toString());
					temp.put("batchName", batch.getBatchName());

					response.add(temp);
				}
			}

			Collections.sort(response,
					(a, b) -> ((Map<String, String>) b).get("day").compareTo(((Map<String, String>) a).get("day")));

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		}

		catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<Boolean> deleteBatchCalender(Long idBatch, Long idBatchCalender) {

		Boolean deletedFlag = false;

		Document<Boolean> result = new Document<>();

		try {
			Batch batch = batchRepository.findByIdBatch(idBatch);

			if (batch == null)
				throw new NullPointerException("Invalid Batch id.");

			// cross verifying whether the bacth calender is for respective batch
			BatchCalender batchCalender = batchCalenderRepository.findByBatchAndIdBatchCalendar(batch, idBatchCalender);

			if (batchCalender == null)
				throw new NullPointerException("Invalid BatchCalender id or Batch id.");

			batchCalenderRepository.delete(batchCalender);

			deletedFlag = true;

			result.setData(deletedFlag);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage(" Batch Calender Deleted Successfully.");

		} catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<Map<String, Object>> getBatchDetailByIdBatch(Long idBatch) {

		Document<Map<String, Object>> result = new Document<>();

		try {
			Batch batch = batchRepository.findByIdBatch(idBatch);

			if (batch == null)
				throw new NullPointerException("Invalid Batch id.");

			List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());

			if (bcList.isEmpty())
				throw new NullPointerException("BatchCalender List Not found.");

			Product product = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("Invalid Product id.");

			Map<String, Object> temp = new HashMap<String, Object>();

			temp.put("batch", batch);
			temp.put("batchCalender", bcList);
			temp.put("idClassStandard", product.getIdClassStandard());
			temp.put("idSyllabus", product.getIdSyllabus());
			temp.put("idState", product.getIdState());
			temp.put("idProductLine", product.getIdProductLine());
			temp.put("idSubject", product.getIdSubject());

			List<SpecialOfferProduct> soList = specialOfferProductRepository.findByIdBatch(idBatch);

			if (!soList.isEmpty()) {
				List<Long> idSpecialOffers = soList.stream().map(SpecialOfferProduct::getIdSpecialOffer)
						.collect(Collectors.toList());
				temp.put("idSpecialOffers", idSpecialOffers);
			}

			result.setData(temp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<Batch> updateBatchDetail(CreateBatchRequestDTO request) {

		Document<Batch> result = new Document<>();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		try {

			Batch response = null;

			if (request.getCategory().equalsIgnoreCase("ACADEMIC")) {

				Product prod = productRepository.findByIdProductAndActiveFlag(request.getBatch().getIdProduct(),Boolean.TRUE);

				if (prod == null)
					throw new NullPointerException("No product found for the product group id provided.");

				Teacher teacher = teacherRepository.findByIdTeacher(request.getIdTeacher());

				if (teacher == null)
					throw new NullPointerException("Invalid Teacher id.");

				if (teacher.getIdWebexPool() == null)
					throw new NullPointerException("No WebExPool Id Assigned to the selected teacher");
				// creating data for 1st product from the index

				TeacherSubject ts = teacherSubjectRepository.findByTeacher_IdTeacherAndIdSyllabusAndIdSubject(
						teacher.getIdTeacher(), request.getIdSyllabus(), request.getIdSubject());

				if (ts == null)
					throw new NullPointerException("Teacher dosent have any proficiency on the selected subject.");

				WebExPool pool = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

				if (pool == null)
					throw new AppException("Invalid webExpool Id.");

				if (request.getScheduleList().isEmpty())
					throw new NullPointerException("Atleast one batch schedule time is needed.");

				Batch assigendBatch = batchRepository.findByIdBatch(request.getBatch().getIdBatch());

				if (assigendBatch == null)
					throw new AppException("Invalid batch Id.");

				assigendBatch.setDemoVideoUrl(ts.getSubIntroVideo());
				assigendBatch.setBatchStartDate(request.getBatch().getBatchStartDate());
				assigendBatch.setBatchEndDate(request.getBatch().getBatchEndDate());
				assigendBatch.setIdSpecialOffer(request.getBatch().getIdSpecialOffer());
				assigendBatch.setBatchName(request.getBatch().getBatchName());
				assigendBatch.setUpdatedAt(Instant.now());
				
				List<Batch> batchList = batchRepository.findByIdTeacherAndActiveFlag(teacher.getIdTeacher(),
						Boolean.TRUE);

				if (!batchList.isEmpty()) {

					for (BatchScheduleDTO data : request.getScheduleList()) {

						if (data.getIdBatchCalendar() == null) {
							List<BatchCalender> calenderList = batchCalenderRepository
									.findByBatchInAndDayOfWeek(batchList, data.getDayOfWeek());
							if (!calenderList.isEmpty()) {
								for (BatchCalender bc : calenderList) {
									boolean batchExists = false;

									batchExists = TimeComparison.checkTimeInBetween(
											LocalTime.parse(bc.getFromTime()).format(timeFormat),
											LocalTime.parse(bc.getToTime()).format(timeFormat),
											LocalTime.parse(data.getFromTime()).format(timeFormat),
											LocalTime.parse(data.getToTime()).format(timeFormat));

									if (batchExists) {
										result.setData(null);
										result.setMessage(
												"Duplicate batch entry, with existing teacher at same time and same day");
										result.setStatusCode(HttpStatus.CONFLICT.value());
										return result;
									}

								}
							}
						}

					}

				}
				
				// second level verification from backend for checking conflicts within batch-group batches.
				if (request.getIdBatchGroup() != null) 
				{   boolean conflictFlag = false;
					List<Batch> bgBatchList = batchRepository.findByIdBatchGroupAndActiveFlag(request.getIdBatchGroup(), true);
					
					if (!bgBatchList.isEmpty()) 
					{   //creating temp Batch list by excluding current batch to the consideration.
						List<Batch> tempList = bgBatchList.stream().filter(b-> !b.getIdBatch().equals(assigendBatch.getIdBatch()))
								.collect(Collectors.toList());
						for (BatchScheduleDTO data : request.getScheduleList()) 
						{
							conflictFlag = checkConflictsOnBatch(tempList, data.getFromTime(), data.getToTime(), data.getDayOfWeek());
							
							if (conflictFlag) {
								result.setData(null);
								result.setMessage(
										"Conflict found with some other batch , with in this Batch Group.");
								result.setStatusCode(HttpStatus.CONFLICT.value());
								return result;
							}
						}
					}
				}
				
				assigendBatch.setIdBatchGroup(request.getIdBatchGroup());
				
				List<SpecialOfferProduct> prevSOP = specialOfferProductRepository
						.findByIdBatch(assigendBatch.getIdBatch());

				if (!prevSOP.isEmpty()) {
					specialOfferProductRepository.deleteAll(prevSOP);
				}

				if (!request.getIdSpecialOffers().isEmpty()) {
					List<SpecialOfferProduct> sopList = new ArrayList<SpecialOfferProduct>();

					for (Long id : request.getIdSpecialOffers()) {
						SpecialOffer so = specialOfferRepository.findByIdSpecialOffer(id);

						if (so == null)
							throw new NullPointerException("Special Offer Not Valid.");

						SpecialOfferProduct sop = new SpecialOfferProduct();
						sop.setIdBatch(assigendBatch.getIdBatch());
						sop.setIdProduct(assigendBatch.getIdProduct());
						sop.setIdSpecialOffer(id);
						sopList.add(sop);

					}

					specialOfferProductRepository.saveAll(sopList);

				}

				Batch res = batchRepository.save(assigendBatch);

				List<BatchCalender> bcList = new ArrayList<>();
				for (BatchScheduleDTO data : request.getScheduleList()) {

					if (data.getIdBatchCalendar() == null) {
						// Set the Batch Run Calender
						BatchCalender batchCalender = new BatchCalender();
						batchCalender.setBatch(res);
						batchCalender.setDayOfWeek(data.getDayOfWeek());
						batchCalender.setFromTime(data.getFromTime());
						batchCalender.setToTime(data.getToTime());
						bcList.add(batchCalender);
					}

				}

				if (!bcList.isEmpty())
					batchCalenderRepository.saveAll(bcList);

				response = res;

			}
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Batch Updated Successfully");
		}

		catch (Exception exp) {

			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	/**
	 * @author NAVEEN
	 * @param List of Batch 
	 * @return true if any conflicts of timing happened in between batch.
	 */
	public Document<Boolean> checkConflictsOnBatch(List<Long> idBatches) {
		Boolean result = false;
		
		Document<Boolean> response = new Document<Boolean>();

		for (Long idBatch : idBatches) {
			Batch batchGet = batchRepository.findByIdBatch(idBatch);
			if (batchGet==null)
				throw new AppException("Invalid Batch found");
			
			List<BatchCalender> verfiyBatchList = batchCalenderRepository.findByBatch_idBatch(idBatch);

			if (verfiyBatchList.isEmpty())
				throw new AppException("Invalid Batch found");

			HashSet<String> dayOfWeekCodes = new HashSet<>();

			verfiyBatchList.removeIf(e -> !dayOfWeekCodes.add(e.getDayOfWeek()));

			List<String> finaldayOfWeekCodesList = new ArrayList<>(dayOfWeekCodes);

			// removing iterated batch from batch list for getting batch calendar
			List<Long> tempList = idBatches.stream().filter(b -> !b.equals(idBatch))
					.map(Long::longValue).collect(Collectors.toList());

			List<BatchCalender> finalBatchCalenderList = batchCalenderRepository
					.findByBatch_idBatchInAndDayOfWeekIn(tempList, finaldayOfWeekCodesList);

			if (!finalBatchCalenderList.isEmpty()) {
				for (BatchCalender bc : verfiyBatchList) {
					result = checkTimingConflicts(bc.getFromTime(), bc.getToTime(), finalBatchCalenderList);

					if (result) 
					{
						response.setData(result);
						response.setMessage("Conflict Occured on the batch:"+ batchGet.getBatchName());
						response.setStatusCode(409);
						return response;
					}
						
				}
			}

		}
		
		response.setData(result);
		response.setMessage("No Conflict found!");
		response.setStatusCode(200);

		return response;
	}
   
	/**
	 * @author NAVEEN
	 * @param batches
	 * @param fromTime
	 * @param toTime
	 * @param dayOfWeek
	 * @return true if any batch found with 
	 * confilct with timing.
	 */
	public Boolean checkConflictsOnBatch(List<Batch> batches, String fromTime, String toTime, String dayOfWeek) {
		
		Boolean result = false;

		HashSet<Long> idBatches = new HashSet<>();

		batches.removeIf(e -> !idBatches.add(e.getIdBatch()));

		List<Long> finalIdBatches = new ArrayList<>(idBatches);

		List<BatchCalender> finalBatchCalenderList = batchCalenderRepository
				.findByBatch_idBatchInAndDayOfWeek(finalIdBatches, dayOfWeek);

		result = checkTimingConflicts(fromTime, toTime, finalBatchCalenderList);

		return result;

	}

	public Boolean checkTimingConflicts(String fromTime, String toTime, List<BatchCalender> calenderList) {

		Boolean result = false;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		for (BatchCalender bc : calenderList) {

			result = TimeComparison.checkTimeInBetween(LocalTime.parse(bc.getFromTime()).format(timeFormat),
					LocalTime.parse(bc.getToTime()).format(timeFormat), LocalTime.parse(fromTime).format(timeFormat),
					LocalTime.parse(toTime).format(timeFormat));

			if (result) {
				return true;
			}

		}

		return result;
	}
 
	/**
	 * @author NAVEEN
	 *  This method return list 
	 *  of batch run details for the record, Which dosen't 
	 *  have any batch run recording.
	 */
	@Override
	public List<BatchRunDetail> fetchBatchRunDetailForBatchRecording() {
		
		List<BatchRunDetail>  finalBatchRunList = batchRunDetailsRepository.getBatchRunDetailsForNotCreatedBatchRecording();
		
		return finalBatchRunList;
	}

}
