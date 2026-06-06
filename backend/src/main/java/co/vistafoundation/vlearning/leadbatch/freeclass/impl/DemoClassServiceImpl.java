/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.email.service.EmailService;
import co.vistafoundation.vlearning.email.service.EmailServiceImpl;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassFilterDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassRequestDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassTimeDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoEcaClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.FutureDemoClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.LeadBatchLogListDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.StudentDemoClassExtraCurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.TeacherDemoClassExtraCurricularDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.TelecallerDemoClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.TelecallerStudentList;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.WebExMetaDataDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricularSchedule;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassSchedule;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.ExtraCurricularLeadAttendedClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadAttendedClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetails;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchDetailsExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLog;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LeadBatchLogExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.AvailableScheduleRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.DemoClassExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.DemoClassRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.DemoClassScheduleExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.DemoClassScheduleRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.ExtraCurricularLeadAttendedClassRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadAttendedClassRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchDetailsExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchDetailsRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchLogExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LeadBatchLogRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.DemoClassService;
import co.vistafoundation.vlearning.notification.service.NotificationService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.UserDevice;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;
import co.vistafoundation.vlearning.user.repository.UserDeviceRepository;
import co.vistafoundation.vlearning.utils.TimeComparison;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.repository.WebExPoolRepository;

/**
 * @author vk
 *
 */
@Service
public class DemoClassServiceImpl implements DemoClassService {

	@Autowired
	DemoClassRepository demoClassRepository;

	@Autowired
	AvailableScheduleRepository availableScheduleRepository;

	@Autowired
	LeadBatchDetailsRepository leadBatchDetailsRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	LeadBatchLogRepository leadBatchLogRepository;

	@Autowired
	WebExPoolRepository webExPoolRepo;

	@Autowired
	DemoClassScheduleRepository demoClassScheduleRepo;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	ClassRepository classStandardRepo;

	@Autowired
	SubjectChapterRepository subjectChapterRepo;

	@Autowired
	SyllabusRepository syllabusRepo;

	@Autowired
	LeadAttendedClassRepository leadAttendedClassRepo;

	@Autowired
	DemoClassExtraCurricularRepository democlassExtraCurricularRepo;

	@Autowired
	LeadBatchLogExtraCurricularRepository leadBatchLogExtraCurricularRepository;

	@Autowired
	LeadBatchDetailsExtraCurricularRepository leadBatchDetailsExtraCurricularRepo;

	@Autowired
	LeadBatchLogExtraCurricularRepository leadBatchLogExtraCurricularRepo;

	@Autowired
	LevelExtraCurricularRepository levelExtraCurricularRepository;

	@Autowired
	DemoClassScheduleExtraCurricularRepository demoClassScheduleExtraCurricularRepo;

	@Autowired
	ExtraCurricularLeadAttendedClassRepository extraCurricularLeadAttendedClassRepo;
	
	@Autowired
	UserDeviceRepository userDeviceRepository;
	
	@Autowired
	NotificationService notificationService;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document save(DemoClassRequestDTO demoClassRequestDTO, String category) {
		Document document = new Document();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		List<DemoClass> demoClasses = new ArrayList<DemoClass>();
		List<DemoClassExtraCurricular> demoExtraClasses = new ArrayList<>();

		try {
			// updated by @author Naveen Kumar for creating exttracurricular demo batch
			if (category.equalsIgnoreCase("academic")) {
				LocalDate fromDate = demoClassRequestDTO.getFromDate(), toDate = demoClassRequestDTO.getToDate();
				List<LocalDate> totalDates = new ArrayList<LocalDate>();
				while (fromDate.isBefore(toDate) || fromDate.isEqual(toDate)) {
					boolean validateDay = demoClassRequestDTO.getDaysOfWeek().stream()
							.anyMatch(fromDate.getDayOfWeek().toString()::equalsIgnoreCase);
					if (validateDay) {
						totalDates.add(fromDate);
					}
					fromDate = fromDate.plusDays(1);
				}
				
				Teacher teacher = teacherRepository.findByIdTeacher(demoClassRequestDTO.getIdTeacher());
				
				if(teacher == null) throw new AppException("Invalid Teacher Found.");
				
				if(teacher.getIdWebexPool() == null) throw new NullPointerException("No WebExPool Assigned to the selected teacher");
				
				  for (LocalDate localDate : totalDates) {
					
					
					DemoClass demoClass = new DemoClass(demoClassRequestDTO.getIdTeacher(),
							demoClassRequestDTO.getIdSubject(), demoClassRequestDTO.getIdClassStandard(),
							demoClassRequestDTO.getIdSyllabus(), null, Boolean.FALSE, demoClassRequestDTO.getFromTime(),
							demoClassRequestDTO.getToTime(), demoClassRequestDTO.getMaxStudents(), 0,
							demoClassRequestDTO.getIdSubjectChapter(), Boolean.TRUE,
							demoClassRequestDTO.getDescription());
					demoClass.setClassScheduleDate(localDate);
                    demoClass.setIdState(demoClassRequestDTO.getIdState());
					List<DemoClassTimeDTO> listDateTimes = demoClassRepository.customQueryGetTimes(localDate,
							demoClassRequestDTO.getIdTeacher());
					if (!listDateTimes.isEmpty()) {
						boolean demoClassExists = false;
						for (DemoClassTimeDTO demoClassTimeDTO : listDateTimes) {
							demoClassExists = TimeComparison.checkTimeInBetween(demoClassTimeDTO.getFromTime(),
									demoClassTimeDTO.getToTime(), demoClassRequestDTO.getFromTime().format(timeFormat),
									demoClassRequestDTO.getToTime().format(timeFormat));
						}
						if (demoClassExists) {
							document.setData(null);
							document.setMessage("duplicate demo class entry, with the date: " + localDate);
							document.setStatusCode(HttpStatus.CONFLICT.value());
							return document;
						} else {
							DemoClass demoClassCreate = demoClassRepository.save(demoClass);
							demoClasses.add(demoClassCreate);
						}
					} else {
						DemoClass demoClassCreate = demoClassRepository.save(demoClass);
						demoClasses.add(demoClassCreate);
					}

				}
				if (demoClasses.size() != 0) {
					document.setData(demoClasses);
					document.setMessage("Demo classes created successfully");
					document.setStatusCode(HttpStatus.OK.value());
				}
			} else {
				LocalDate fromDate = demoClassRequestDTO.getFromDate(), toDate = demoClassRequestDTO.getToDate();
				List<LocalDate> totalDates = new ArrayList<LocalDate>();
				while (fromDate.isBefore(toDate) || fromDate.isEqual(toDate)) {
					boolean validateDay = demoClassRequestDTO.getDaysOfWeek().stream()
							.anyMatch(fromDate.getDayOfWeek().toString()::equalsIgnoreCase);
					if (validateDay) {
						totalDates.add(fromDate);
					}
					fromDate = fromDate.plusDays(1);
				}
				
                Teacher teacher = teacherRepository.findByIdTeacher(demoClassRequestDTO.getIdTeacher());
				
				if(teacher == null) throw new AppException("Invalid Teacher Found.");
				
				if(teacher.getIdWebexPool() == null) throw new NullPointerException("No WebExPool  Assigned to the selected teacher");
				

				for (LocalDate localDate : totalDates) {
					DemoClassExtraCurricular dcec = new DemoClassExtraCurricular();

					dcec.setActiveFlag(Boolean.TRUE);
					dcec.setClassConductedFlag(Boolean.FALSE);
					dcec.setClassScheduleDate(localDate);
					dcec.setDescription(demoClassRequestDTO.getDescription());
					dcec.setFromTime(demoClassRequestDTO.getFromTime());
					dcec.setIdLevelExtraCurricular(demoClassRequestDTO.getIdLevelExtraCurricular());
					dcec.setIdSubjectExtraCurricular(demoClassRequestDTO.getIdSubject());
					dcec.setIdTeacher(demoClassRequestDTO.getIdTeacher());
					dcec.setMaxStudents(demoClassRequestDTO.getMaxStudents());
					dcec.setTotalStudentsEnrolled(0);
					dcec.setToTime(demoClassRequestDTO.getToTime());
	
					List<DemoClassExtraCurricular> demoList = democlassExtraCurricularRepo
							.findByIdTeacherAndClassScheduleDateAndActiveFlag(demoClassRequestDTO.getIdTeacher(),
									localDate, Boolean.TRUE);

					if (!demoList.isEmpty()) {
						boolean demoClassExists = false;
						for (DemoClassExtraCurricular demo : demoList) {

							demoClassExists = TimeComparison.checkTimeInBetween(demo.getFromTime().format(timeFormat),
									demo.getToTime().format(timeFormat),
									demoClassRequestDTO.getFromTime().format(timeFormat),
									demoClassRequestDTO.getToTime().format(timeFormat));
						}

						if (demoClassExists) {
							document.setData(null);
							document.setMessage("duplicate demo class entry, with the date: " + localDate);
							document.setStatusCode(HttpStatus.CONFLICT.value());
							return document;
						} else {

							DemoClassExtraCurricular demoClassCreate = democlassExtraCurricularRepo.save(dcec);
							demoExtraClasses.add(demoClassCreate);
						}

					}

					else {
						DemoClassExtraCurricular demoClassCreate = democlassExtraCurricularRepo.save(dcec);
						demoExtraClasses.add(demoClassCreate);
					}

				}

				if (!demoExtraClasses.isEmpty()) {
					document.setData(demoExtraClasses);
					document.setMessage("Demo Extracurricular classes created successfully");
					document.setStatusCode(HttpStatus.OK.value());
				} else {
					throw new AppException("Something went wrong");
				}

			}

		} catch (Exception exp) {
			if (exp.getCause() != null) {
				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					document.setData(null);
					document.setStatusCode(HttpStatus.CONFLICT.value());
					document.setMessage("Duplicate demo class");
					return document;
				} else {
					document.setData(null);
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					document.setMessage(exp.getLocalizedMessage());
					return document;
				}
			} else {
				document.setData(null);
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				document.setMessage(exp.getLocalizedMessage());
				return document;
			}

		}
		return document;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Document updateTeacher(DemoClass demoClass) {
		Document document = new Document<>();
		DemoClass demoClassExist = demoClassRepository.findByIdDemoClass(demoClass.getIdDemoClass());
		if (demoClassExist == null) {
			document.setData(null);
			document.setMessage("Demo class does not exist");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
		demoClassExist.setIdTeacher(demoClass.getIdTeacher());
		demoClassExist = demoClassRepository.save(demoClassExist);
		document.setData(demoClassExist);
		document.setMessage("New teacher assign to this demo class successfully");
		document.setStatusCode(HttpStatus.OK.value());
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getDemoClassByTeacher(Long idTeacher) {
		Document document = new Document();
		List<DemoClassDTO> list = demoClassRepository.findDemoClassDetails(idTeacher);
		if (list.size() != 0) {
			document.setData(list);
			document.setMessage("Demo class list fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
		} else {
			document.setData(null);
			document.setMessage("Data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getDemoClassList(DemoClassFilterDTO demoClassFilterDTO) {
		Document document = new Document();
		List<DemoClassDTO> list = demoClassRepository.findDemoClassList(demoClassFilterDTO.getIdTeacher(),
				demoClassFilterDTO.getIdClassStandard(), demoClassFilterDTO.getIdSyllabus(),
				demoClassFilterDTO.getIdSubject(), demoClassFilterDTO.getIdSubjectChapter(), Boolean.TRUE,demoClassFilterDTO.getIdState());
		if (list.size() != 0) {
			document.setData(list);
			document.setMessage("Demo class list fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
		} else if (list.size() == 0) {
			document.setData(null);
			document.setMessage("No demo class data list found");
			document.setStatusCode(HttpStatus.OK.value());
		} else {
			document.setData(null);
			document.setMessage("Error while fetching data");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })

	public Document getAllStudentsForDemoclass(Long idClassStandard, Long idSyllabus, Long idSubject,
			Long idSujectChapter, Long idLanguage,Long idAvailableSchedule,Long idState) {
		Document document = new Document();
		try {
			List<TelecallerStudentList> list = new ArrayList<TelecallerStudentList>();
			if(idAvailableSchedule != null) {
				List<LeadBatchDetails> batchdetailList =leadBatchDetailsRepository.
						findAllByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguageAndIdAvailableScheduleAndIdState(idClassStandard, idSyllabus, idSubject, idSujectChapter, idLanguage, idAvailableSchedule,idState);
				if(batchdetailList.isEmpty())throw new NullPointerException("No Details found");
			for (LeadBatchDetails leadBatch:batchdetailList) 
			{
				TelecallerStudentList tsl = new TelecallerStudentList();
				User u =userRepository.findByUserSurId(leadBatch.getIdVlUser());
				if (u == null) throw  new NullPointerException("No User Found");
				tsl.setFirstName(u.getFirstName());
				tsl.setMobileNumber(u.getMobileNumber());
				tsl.setIdLeadBatchDetails(leadBatch.getIdLeadBatchDetails());
				list.add(tsl);			
			}
			if(list.isEmpty())throw new NullPointerException("No students Found!"); 
				document.setData(list);
				document.setMessage("List fetched successfully");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
			else{
		     List<LeadBatchDetails> lbdList =leadBatchDetailsRepository.findAllByIdClassStandardAndIdSyllabusAndIdSubjectAndIdSujectChapterAndIdLanguage(idClassStandard, idSyllabus, idSubject, idSujectChapter, idLanguage);
				if(lbdList.isEmpty())throw new NullPointerException("No Details found");
			for (LeadBatchDetails leadBatch:lbdList) 
			{
				TelecallerStudentList tsl = new TelecallerStudentList();
				User u =userRepository.findByUserSurId(leadBatch.getIdVlUser());
				if (u == null) throw  new NullPointerException("No User Found");
				tsl.setFirstName(u.getFirstName());
				tsl.setMobileNumber(u.getMobileNumber());
				tsl.setIdLeadBatchDetails(leadBatch.getIdLeadBatchDetails());
				list.add(tsl);			
			}
			if(list.isEmpty())throw new NullPointerException("No students Found!"); 
				document.setData(list);
				document.setMessage("List fetched successfully");
				document.setStatusCode(HttpStatus.OK.value());
				return document;
			}
		}
			catch(Exception exp) {
				document.setData(null);
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				document.setMessage(exp.getLocalizedMessage());
				return document;
			}
		}	

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getAllDemoClasses(Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter,
			String date) {
		Document document = new Document();

		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final LocalDate dt = LocalDate.parse(date, dtf);

		// List<DemoClass> list =
		// demoClassRepository.findByIdSubjectAndIdClassStandardAndIdSyllabusAndIdSubjectChapterAndClassScheduleDate(idSubject,
		// idClassStandard, idSyllabus, idSujectChapter,date);
		List<DemoClass> list = demoClassRepository.getAllDemoClasses(idSubject, idClassStandard, idSyllabus,
				idSujectChapter, dt);
		
		if (list.isEmpty()) throw new AppException("No Demo list Found");

		List<DemoClassDTO> dtoList = new ArrayList<DemoClassDTO>();

		for (DemoClass demo : list) {
			DemoClassDTO demoDTO = new DemoClassDTO();
			demoDTO.setFromTime(demo.getFromTime());
			demoDTO.setIdDemoClass(demo.getIdDemoClass());
			demoDTO.setToTime(demo.getToTime());
			demoDTO.setSubjectName((subjectRepository.findByIdSubject(demo.getIdSubject())).getSubjectName());
			demoDTO.setTeacherName((teacherRepository.findByIdTeacher(demo.getIdTeacher()).getTeacherName()));
			dtoList.add(demoDTO);
		}

		
		if (list.size() != 0) {
			document.setData(dtoList);
			document.setMessage("List fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
		} else {
			document.setData(null);
			document.setMessage("Data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return document;
	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document assigningLeadBatchLog(Long idDemoClass, Long idBatchDetail) {
		Document document = new Document();

		LeadBatchDetails detail = leadBatchDetailsRepository.findByIdLeadBatchDetails(idBatchDetail);
		
		if (detail == null) throw new AppException("No LeadBatchDetails Found");

		DemoClass demo = demoClassRepository.findByIdDemoClass(idDemoClass);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		
		User teleCaller = userRepository.findByUsername(name);

		LeadBatchLog batchLog = new LeadBatchLog();
		batchLog.setClassScheduleDate(demo.getClassScheduleDate());
		batchLog.setFromTime(demo.getFromTime());
		batchLog.setToTime(demo.getToTime());
		batchLog.setIdClassStandard(demo.getIdClassStandard());
		batchLog.setIdDemoClass(demo.getIdDemoClass());
		batchLog.setIdSubject(detail.getIdSubject());
		batchLog.setIdSubjectChapter(detail.getIdSujectChapter());
		batchLog.setIdSyllabus(detail.getIdSyllabus());
		batchLog.setIdVlUser(detail.getIdVlUser());
		batchLog.setIdTeacher(demo.getIdTeacher());
		batchLog.setTelecallerIdVlUser(teleCaller.getUserSurId());
		batchLog.setIdState(detail.getIdState());

		// Added By Ahmed... removed idLeadBatchDetails from LeadBatchLog Entity
		batchLog.setIdLanguage(detail.getIdLanguage());

		LeadBatchLog log = leadBatchLogRepository.save(batchLog);
		User user = userRepository.findByUserSurId(log.getIdVlUser());

		if (log != null) {
			demo.setTotalStudentEnrolled(demo.getTotalStudentEnrolled() + 1);
			leadBatchDetailsRepository.deleteById(idBatchDetail);
		}
		DemoClass demo1 = demoClassRepository.save(demo);

		emailServiceImpl.sendNotificationEmailwhenDemoClassAssigned(user.getEmail(), user.getFirstName(),
				log.getClassScheduleDate().toString(),
				log.getFromTime().toString() + " - " + log.getToTime().toString(),
				(subjectRepository.findByIdSubject(demo1.getIdSubject())).getSubjectName());

		document.setData(log);
		document.setMessage("List fetched successfully");
		document.setStatusCode(HttpStatus.OK.value());

		return document;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document createWebExMeetingForDemoClassByTeacher(Long idDemoClass, Long idTeacher) {

		Document doc = new Document<>();
		try {

			if (idDemoClass == 0 || idDemoClass == null) {
				throw new NullPointerException("Demo Class Id Cannot be Null");
			}

			if (idTeacher == 0 || idTeacher == null) {
				throw new NullPointerException("Teacher Id cannot be Null");
			}

			// Get WebEx Pool Details from idTeacher
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			
			if (teacher == null) throw new AppException("No Teacher Found");

			WebExPool webExPoolObject = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());
			
			if (teacher == null) throw new AppException("No webExPoolObject Found");

			// Todays Date in MMDDYY HH:mm:ss
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String instantDate = sdf.format(new Date());

			String randomPassword = RandomStringUtils.randomAlphabetic(6);

			// Create WebEx Meeting
			// Create WebEx Meeting
			String xmlBodyForCreateMeetingPart1 = " <?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
					+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + "  <header>\r\n"
					+ "      <securityContext>\r\n" + "          <siteName>vistafoundation</siteName>\r\n"
					+ "          <webExID>" + webExPoolObject.getWebExUserId() + "</webExID>\r\n"
					+ "          <password>" + webExPoolObject.getWebExPassword() + "</password>\r\n"
					+ "      </securityContext>\r\n" + "  </header>\r\n" + "  <body>\r\n" + "      <bodyContent\r\n"
					+ "          xsi:type=\"java:com.webex.service.binding.meeting.CreateMeeting\">\r\n"
					+ "          <metaData>\r\n" + "              <confName>DemoClass-" + idDemoClass
					+ "</confName>\r\n" + "              <agenda>Demo Class Academic</agenda>\r\n"
					+ "          </metaData>\r\n" + "          <participants>\r\n" + "                <attendees>";

			String xmlBodyForCreateMeetingPart2 = "";

			List<LeadBatchLog> listOfAllConfirmedStudents = leadBatchLogRepository.findByIdDemoClass(idDemoClass);

			for (LeadBatchLog leadBatchLog : listOfAllConfirmedStudents) {

				User user = userRepository.findByUserSurId(leadBatchLog.getIdVlUser());
				xmlBodyForCreateMeetingPart2 += "<attendee>\r\n" + "                        <person>\r\n"
						+ "                            <name>" + user.getFirstName() + "</name>\r\n"
						+ "                            <email>" + user.getEmail() + "</email>\r\n"
						+ "                        </person>\r\n" + "                    </attendee>";
			}

			String xmlBodyForCreateMeetingPart3 = "</attendees>\r\n" + "            </participants>\r\n"
					+ "          <enableOptions>\r\n" + "              <chat>true</chat>\r\n"
					+ "              <poll>true</poll>\r\n" + "              <audioVideo>true</audioVideo>\r\n"
					+ "              <supportE2E>TRUE</supportE2E>\r\n"
					+ "              <autoRecord>FALSE</autoRecord>\r\n" + "          </enableOptions>\r\n"
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

			String url = "https://api.webex.com/WBXService/XMLService";

			String xmlBodyForCreateMeeting = xmlBodyForCreateMeetingPart1 + xmlBodyForCreateMeetingPart2
					+ xmlBodyForCreateMeetingPart3;

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

			for (int i = 0; i < meetingKey.getLength(); i++) {
				Node meetingKeyNode = meetingKey.item(i);
				Element serviceTypeElement = (Element) meetingKeyNode;
				System.out.println("------------Meeting Key----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingKeyActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}

			for (int i = 0; i < meetingUUid.getLength(); i++) {
				Node meetingUuidNode = meetingUUid.item(i);
				Element serviceTypeElement = (Element) meetingUuidNode;
				System.out.println("------------Meeting UUID----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingUuidActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
			}

			for (int i = 0; i < meetingPassword.getLength(); i++) {
				Node meetingPasswordNode = meetingPassword.item(i);
				Element serviceTypeElement = (Element) meetingPasswordNode;
				System.out.println("------------Meeting Password----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingPasswordActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
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

			DemoClassSchedule existingDemoCLassSchedule = demoClassScheduleRepo.findByIdDemoClass(idDemoClass);

			if (existingDemoCLassSchedule != null) {
				existingDemoCLassSchedule.setWebExAttendeeJoinMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				existingDemoCLassSchedule.setWebExHostStartMeetingUrl(hostStartMeetingUrlActual);
				existingDemoCLassSchedule.setWebExMeetingGuestUserToken(meetingGuestUserTokenActual);
				existingDemoCLassSchedule.setWebExMeetingKey(meetingKeyActual);
				existingDemoCLassSchedule.setWebExMeetingPassword(meetingPasswordActual);
				existingDemoCLassSchedule.setWebExMeetingUuid(meetingUuidActual);

				demoClassScheduleRepo.save(existingDemoCLassSchedule);
			} else {
				DemoClassSchedule demoClassSchedule = new DemoClassSchedule();
				demoClassSchedule.setIdDemoClass(idDemoClass);
				demoClassSchedule.setWebExAttendeeJoinMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				demoClassSchedule.setWebExHostStartMeetingUrl(hostStartMeetingUrlActual);
				demoClassSchedule.setWebExMeetingGuestUserToken(meetingGuestUserTokenActual);
				demoClassSchedule.setWebExMeetingKey(meetingKeyActual);
				demoClassSchedule.setWebExMeetingPassword(meetingPasswordActual);
				demoClassSchedule.setWebExMeetingUuid(meetingUuidActual);

				// Save the demoClass Schedule to Demo CLass Schedule Table
				demoClassScheduleRepo.save(demoClassSchedule);
			}

			// Update Class Conducted Flag to True
			DemoClass existingDemoClass = demoClassRepository.findByIdDemoClass(idDemoClass);
			if (existingDemoClass != null) {
				existingDemoClass.setClassConductedFlag(Boolean.TRUE);
				demoClassRepository.save(existingDemoClass);
			}

			// Send mail to Students by taking details from lead batch log
			List<LeadBatchLog> leadBatchLogData = leadBatchLogRepository.findByIdDemoClass(idDemoClass);
			
			Set<String> regDeviceIdSet = new HashSet<String>();
			List<String> regDeviceIdList  = new ArrayList<String>();
			
			// Get User Info By IdVlUser
			for (LeadBatchLog leadBatchLog : leadBatchLogData) {
				User userInfo = userRepository.findByUserSurId(leadBatchLog.getIdVlUser());

				emailService.sendEmailToAllStudentsForJoiningDemoClass(userInfo.getFirstName(), userInfo.getEmail(), "",
						idDemoClass, existingDemoClass.getIdClassStandard()!=null ? existingDemoClass.getIdClassStandard() :null, 
						existingDemoClass.getIdSubject(),existingDemoClass.getIdSubjectChapter(), leadBatchLog.getIdSyllabus(),
						existingDemoClass.getFromTime(), existingDemoClass.getToTime());
				
				UserDevice userDevice=userDeviceRepository.findByUserSurIdAndDeviceType(leadBatchLog.getIdVlUser(),"MOBILE");
				if(userDevice!=null) {
					regDeviceIdSet.add(userDevice.getDeviceId());
				}
			}
			
			//sending notification
			if(!regDeviceIdSet.isEmpty()) {
				regDeviceIdList.addAll(regDeviceIdSet);
				String message = "Your Demo class is started";
				notificationService.sendNotification(regDeviceIdList,message,"democlass");
			}
			
			doc.setData(webExMetaDataDTO);
			doc.setMessage("WebEx Meeting Created And The Details Have been Fetched");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAllLeadBatchLogs(Long idVlUser) {

		Document doc = new Document<>();
		try {

			if (idVlUser == 0 || idVlUser == null) {
				throw new NullPointerException("User Sur Id Cannot be Null");
			}

			// Get LeadBatch Log Lists
			List<LeadBatchLog> allRegisteredDemoClasses = leadBatchLogRepository.findByIdVlUser(idVlUser);

			LeadBatchLogListDTO leadBatchLogListDTO = new LeadBatchLogListDTO();

			List<LeadBatchLogListDTO> listOfLeadBatchLogsDetails = new ArrayList<>();

			if (allRegisteredDemoClasses.isEmpty()) {

				doc.setData(new ArrayList<>());
				doc.setMessage("No Demo Class has been Assigned for this User");
				doc.setStatusCode(500);
			} else {

				for (LeadBatchLog leadBatchLog : allRegisteredDemoClasses) {

					DemoClass demoClass = demoClassRepository.findByIdDemoClass(leadBatchLog.getIdDemoClass());

					leadBatchLogListDTO.setDemoClass(demoClass);

					ClassStandard classStandard = classStandardRepo
							.findByIdClassStandard(demoClass.getIdClassStandard());
					
					if (classStandard == null) throw new AppException("No ClassStandard Found");

					leadBatchLogListDTO.setClassStandard(classStandard);

					Subject subject = subjectRepository.findByIdSubject(demoClass.getIdSubject());
					
					if (subject == null) throw new AppException("No Subject Found");


					leadBatchLogListDTO.setSubject(subject);

					SubjectChapter subjectChapter = subjectChapterRepo
							.findByIdSubjectChapter(demoClass.getIdSubjectChapter());
					
					if (subjectChapter == null) throw new AppException("No subjectChapter Found");

					leadBatchLogListDTO.setSubjectChapter(subjectChapter);

					DemoClassSchedule demoClassSchedule = demoClassScheduleRepo
							.findByIdDemoClass(demoClass.getIdDemoClass());
					

					if (demoClassSchedule == null) {
						leadBatchLogListDTO.setMeetingPassword("Not Yet Available");
					} else {
						leadBatchLogListDTO.setMeetingPassword(demoClassSchedule.getWebExMeetingPassword());
					}

					Teacher teacher = teacherRepository.findByIdTeacher(demoClass.getIdTeacher());
					
					if (teacher == null) throw new AppException("No teacher Found");
					
					leadBatchLogListDTO.setTeacher(teacher);

					Syllabus syllabus = syllabusRepo.findByIdSyllabus(demoClass.getIdSyllabus());
					
					if (syllabus == null) throw new AppException("No syllabus Found");
					
					leadBatchLogListDTO.setSyllabus(syllabus);

					leadBatchLogListDTO.setClassScheduleDate(demoClass.getClassScheduleDate());

					leadBatchLogListDTO.setFromTime(demoClass.getFromTime());

					leadBatchLogListDTO.setToTime(demoClass.getToTime());

					leadBatchLogListDTO.setLeadBatchLog(leadBatchLog);

					listOfLeadBatchLogsDetails.add(leadBatchLogListDTO);
				}

				doc.setData(listOfLeadBatchLogsDetails);
				doc.setMessage("All Lead Batch log for this User");
				doc.setStatusCode(200);

			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}
		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document studentOrParentJoinDemoClass(Long idLeadBatchLog, Long idVlUser, Long idDemoClass) {

		Document doc = new Document<>();
		try {

			if (idLeadBatchLog == null || idLeadBatchLog == 0) {
				throw new NullPointerException("Id Lead Batch Log Cannot be Null or Zero");
			}

			if (idVlUser == null || idVlUser == 0) {
				throw new NullPointerException("Id Vl User Cannot be Null or Zero");
			}

			if (idDemoClass == null || idDemoClass == 0) {
				throw new NullPointerException("Id Demo Class Cannot be Null or Zero");
			}

			// Get Demo Class
			DemoClass demoClass = demoClassRepository.findByIdDemoClass(idDemoClass);
			
			if (demoClass == null) throw new AppException("No DemoClass Found");

			// Get Demo Class Scheduled WebEx Info
			DemoClassSchedule demoClassSchedule = demoClassScheduleRepo.findByIdDemoClass(idDemoClass);

			if (demoClassSchedule == null) {
				throw new NullPointerException(
						"Class Not Yet Started By Teacher");
			}

			// Get Teacher From demo Class
			Teacher teacher = teacherRepository.findByIdTeacher(demoClass.getIdTeacher());
			
			if (teacher == null) throw new AppException("No Teacher Found");

			// Get WebEx Pool Details
			WebExPool webExPoolObj = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());
			
			String xmlBodyForGetMeeting = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n" + "<serv:message\r\n"
					+ "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "    xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n" + "    <header>\r\n"
					+ "        <securityContext>\r\n" + "            <siteName>vistafoundation</siteName>\r\n"
					+ "            <webExID>" + webExPoolObj.getWebExUserId() + "</webExID>\r\n"
					+ "            <password>" + webExPoolObj.getWebExPassword() + "</password>\r\n"
					+ "        </securityContext>\r\n" + "    </header>\r\n" + "    <body>\r\n"
					+ "        <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.GetMeeting\">\r\n"
					+ "            <meetingKey>" + demoClassSchedule.getWebExMeetingKey() + "</meetingKey>\r\n"
					+ "        </bodyContent>\r\n" + "    </body>\r\n" + "</serv:message>";

			String url = "https://api.webex.com/WBXService/XMLService";

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> result = restTemplate.postForEntity(url, xmlBodyForGetMeeting, String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStream = new ByteArrayInputStream(result.getBody().getBytes());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			org.w3c.dom.Document document = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStream);

			document.getDocumentElement().normalize();

			Element root = document.getDocumentElement();

			NodeList meetingStatus = document.getElementsByTagName("meet:status");

			String meetingStatusActual = null;

			for (int i = 0; i < meetingStatus.getLength(); i++) {
				Node meetingStatusNode = meetingStatus.item(i);
				Element serviceTypeElement = (Element) meetingStatusNode;
				System.out.println("------------Meeting Status----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingStatusActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}
			
			if (meetingStatusActual == null) 
			{
				doc.setData("INVALID PROGRESS");
				doc.setMessage("INVALID PROGRESS TYPE ");
				doc.setStatusCode(500);
			}

			else if (meetingStatusActual.equals("NOT_INPROGRESS")) {
				doc.setData("NOT_INPROGRESS");
				doc.setMessage("Class Has not Yet Started");
				doc.setStatusCode(200);

			} else if (meetingStatusActual.equals("INPROGRESS")) {

				User user = userRepository.findByUserSurId(idVlUser);

				// Now Get Attendee Join Meeting Url

				String xmlBodyForGettingAttendeeUrl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
						+ "    <header>\r\n" + "        <securityContext>\r\n" + "            <webExID>"
						+ webExPoolObj.getWebExUserId() + "</webExID>\r\n" + "            <password>"
						+ webExPoolObj.getWebExPassword() + "</password>\r\n"
						+ "            <siteID>13696027</siteID>\r\n"
						+ "				<siteName>vistafoundation</siteName>\r\n"
						+ "            <partnerID>I2WNi1i-xdYDHn-HNXJCMA</partnerID>\r\n" + "            <email>"
						+ webExPoolObj.getWebExUserId() + "</email>\r\n" + "        </securityContext>\r\n"
						+ "    </header>\r\n" + "    <body>\r\n" + "        <bodyContent\r\n"
						+ "            xsi:type=\"java:com.webex.service.binding.meeting.GetjoinurlMeeting\">\r\n"
						+ "            <sessionKey>" + demoClassSchedule.getWebExMeetingKey() + "</sessionKey>\r\n"
						+ "            <attendeeName>" + user.getFirstName() + "</attendeeName>\r\n"
						+ "            <attendeeEmail>" + user.getEmail() + "</attendeeEmail>\r\n"
						+ "            <meetingPW>" + demoClassSchedule.getWebExMeetingPassword() + "</meetingPW>\r\n"
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

				demoClassSchedule.setWebExAttendeeJoinMeetingUrl(attendeeJoinMeetingUrlActual);
				demoClassScheduleRepo.save(demoClassSchedule);

				LeadAttendedClass leadAttendedClass = leadAttendedClassRepo.findByIdVlUserAndIdLeadBatchLog(idVlUser,
						idLeadBatchLog);

				if (leadAttendedClass != null) {
					leadAttendedClass.setIdLeadBatchLog(idLeadBatchLog);
					leadAttendedClass.setIdVlUser(idVlUser);
					leadAttendedClassRepo.save(leadAttendedClass);
				} else {
					LeadAttendedClass attendedClass = new LeadAttendedClass();
					attendedClass.setIdLeadBatchLog(idLeadBatchLog);
					attendedClass.setIdVlUser(idVlUser);
					leadAttendedClassRepo.save(attendedClass);
				}

				doc.setData(attendeeJoinMeetingUrlActual);
				doc.setMessage("Lead Attended Class Data Captured");
				doc.setStatusCode(200);
			}else {
				doc.setData("INVALID PROGRESS");
				doc.setMessage("INVALID PROGRESS TYPE ");
				doc.setStatusCode(500);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}
		return doc;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document deActivateDemoClass(Long idDemoClass) {
		Document document = new Document<>();
		DemoClass demoClass = demoClassRepository.findByIdDemoClass(idDemoClass);
		if (demoClass != null) {
			if (demoClass.getClassScheduleDate().isAfter(LocalDate.now())
					|| demoClass.getClassScheduleDate().isEqual(LocalDate.now())) {
				List<LeadBatchLog> leadBatchLog = leadBatchLogRepository.findByIdDemoClass(idDemoClass);
				if (leadBatchLog.size() == 0) {
					demoClass.setActiveFlag(Boolean.FALSE);
					demoClass = demoClassRepository.save(demoClass);
					document.setData(demoClass);
					document.setMessage("Democlass schedule delete successfully!");
					document.setStatusCode(HttpStatus.OK.value());
					return document;
				} else {
					document.setData(null);
					document.setMessage("Can't delete demo class, record found in lead batch logs!");
					document.setStatusCode(HttpStatus.NOT_FOUND.value());
					return document;
				}
			} else {
				document.setData(null);
				document.setMessage("You can't delete schedules of today or previous dates");
				document.setStatusCode(HttpStatus.NOT_FOUND.value());
				return document;
			}
		} else {
			document.setData(null);
			document.setMessage("Demo class data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		}
	}

	@Override
	public Document<?> getLeastFutureDemoClass() {

		Document<FutureDemoClassDTO> doc = new Document<>();

		try {

			DemoClass upcomingDemoClass = demoClassRepository
					.findFirstByClassConductedFlagAndClassScheduleDateAndActiveFlagAndFromTimeAfter(Boolean.FALSE,
							LocalDate.now(), Boolean.TRUE, LocalTime.now());
			if (upcomingDemoClass == null) {
				
				upcomingDemoClass = demoClassRepository
						.findFirstByClassScheduleDateAfterAndFromTimeAfterAndClassConductedFlagAndActiveFlag(
								LocalDate.now(), LocalTime.parse("00:00"), Boolean.FALSE, Boolean.TRUE);
				
				if (upcomingDemoClass == null) {
					throw new NullPointerException("No demo class Available for upcoming time");}
			}

			Teacher teacher = teacherRepository.findByIdTeacher(upcomingDemoClass.getIdTeacher());
			if (teacher == null) {
				throw new NullPointerException("No teacher found");}

			FutureDemoClassDTO futureDemoClassDTO = new FutureDemoClassDTO();

			futureDemoClassDTO.setClassConductedFlag(upcomingDemoClass.getClassConductedFlag());
			futureDemoClassDTO.setClassScheduledDate(upcomingDemoClass.getClassScheduleDate());
			futureDemoClassDTO.setDescription(upcomingDemoClass.getDescription());
			futureDemoClassDTO.setFromTime(upcomingDemoClass.getFromTime());
			futureDemoClassDTO.setToTime(upcomingDemoClass.getToTime());
			futureDemoClassDTO.setTeacherName(teacher.getTeacherName());

			doc.setData(futureDemoClassDTO);
			doc.setMessage("Least Future Demo Class");
			doc.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// @Override
	public Document getListofDemoClassTeacherProfile(Long idTeacher) {
		Document result = new Document();
		try {
			List<DemoClassDTO> dtoList = new ArrayList<DemoClassDTO>();
			List<DemoClass> listDemo = demoClassRepository.findByIdTeacherAndClassConductedFlagAndActiveFlag(idTeacher,
					false, true);

			for (DemoClass dem : listDemo) {
				DemoClassDTO demDto = new DemoClassDTO();
				demDto.setClassScheduleDate(dem.getClassScheduleDate());
				
				Subject subject = subjectRepository.findByIdSubject(dem.getIdSubject());
				
				if (subject == null) throw new AppException("No Subject Found");
				
				demDto.setSubjectName(subject.getSubjectName());
				
				demDto.setDescription(demDto.getDescription());
				
				ClassStandard classStandard = classStandardRepo.findByIdClassStandard(dem.getIdClassStandard());
				
				if(classStandard == null) throw new AppException("No classStandard Found");
					
				demDto.setClassStandardName(classStandard.getClassStandadName());

				dtoList.add(demDto);

			}

			result.setData(dtoList);
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
	public Document<?> getAllExtraCurricularDemoClassForSelectedSubjectAndLevelAndSlectedDate(
			Long idSubjectExtraCurricular, Long idLevelExtraCurricular, LocalDate choosenDate) {

		Document<List<TelecallerDemoClassDTO>> doc = new Document<>();

		try {

			if (idSubjectExtraCurricular == null) {
				throw new NullPointerException("Subject id Cannot be Null. Please Select Subject");
			}

			if (idLevelExtraCurricular == null) {
				throw new NullPointerException("Level id Cannot be Null. Please Select Level");
			}

			if (choosenDate == null) {
				throw new NullPointerException("Date Cannot be Null. please Choose a Date");
			}

			List<DemoClassExtraCurricular> listOfDemoClass = democlassExtraCurricularRepo
					.findByIdSubjectExtraCurricularAndIdLevelExtraCurricularAndClassScheduleDateAndActiveFlag(
							idSubjectExtraCurricular, idLevelExtraCurricular, choosenDate, Boolean.TRUE);

			if (listOfDemoClass.isEmpty()) {
				throw new NullPointerException("No Demo Class Found for this Subject,Level And Choosen Date");
			}

			else {

				List<TelecallerDemoClassDTO> telecallerDemoClassDTOs = new ArrayList<>();

				for (DemoClassExtraCurricular demoClassExtraCurricular : listOfDemoClass) {

					Teacher teacher = teacherRepository.findByIdTeacher(demoClassExtraCurricular.getIdTeacher());
					
					if(teacher == null) throw new AppException("No Teacher Found");

					TelecallerDemoClassDTO telecallerDemoClassDTO = new TelecallerDemoClassDTO();

					telecallerDemoClassDTO.setDemoClassExtraCurricular(demoClassExtraCurricular);
					telecallerDemoClassDTO.setTeacherName(teacher.getTeacherName());

					telecallerDemoClassDTOs.add(telecallerDemoClassDTO);
				}

				doc.setData(telecallerDemoClassDTOs);
				doc.setMessage("DemoClass Lists For the Subject, Level And Date Choosen");
				doc.setStatusCode(200);
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override

	public Document<List<DemoEcaClassDTO>> getDemoClassListECA(DemoClassFilterDTO demoClassFilterDTO) {
		Document<List<DemoEcaClassDTO>> result = new Document<>();

		Long idTeacher = demoClassFilterDTO.getIdTeacher();
		Long idLevelExtraCurricular = demoClassFilterDTO.getIdLevelExtraCurricular();
		Long idSubject = demoClassFilterDTO.getIdSubject();

		List<DemoEcaClassDTO> temp = new ArrayList<>();
		try {

			if (idTeacher == null || idLevelExtraCurricular == null || idSubject == null)
				throw new AppException("Please fill the required filed.");

			List<DemoClassExtraCurricular> demos = democlassExtraCurricularRepo
					.findByIdTeacherAndIdLevelExtraCurricularAndIdSubjectExtraCurricularAndActiveFlag(idTeacher,
							idLevelExtraCurricular, idSubject, Boolean.TRUE);

			if (demos.isEmpty())
				throw new AppException("No Demo Class Found for this Subject,Level And Teacher.");
			for (DemoClassExtraCurricular demo : demos) {
				DemoEcaClassDTO dcd = new DemoEcaClassDTO();
				dcd.setActiveFlag(demo.getActiveFlag());
				dcd.setClassConductedFlag(demo.getClassConductedFlag());
				dcd.setClassScheduleDate(demo.getClassScheduleDate());
				dcd.setDescription(demo.getDescription());
				dcd.setFromTime(demo.getFromTime());
				dcd.setIdDemoClassExtraCurricular(demo.getIdDemoClassExtraCurricular());
				Subject s = subjectRepository.findByIdSubject(demo.getIdSubjectExtraCurricular());
				if (s == null)
					throw new AppException("Invalid Subject id");
				dcd.setSubjectName(s.getSubjectName());
				dcd.setIdSubject(demo.getIdSubjectExtraCurricular());
				dcd.setToTime(demo.getToTime());
				Teacher t = teacherRepository.findByIdTeacher(demo.getIdTeacher());

				if (t == null)
					throw new AppException("Invalid Teacher id");

				dcd.setIdTeacher(demo.getIdTeacher());
				dcd.setTeacherName(t.getTeacherName());
				dcd.setMaxStudents(demo.getMaxStudents());
				dcd.setTotalStudentEnrolled(demo.getTotalStudentsEnrolled());

				temp.add(dcd);

			}

			result.setData(temp);
			result.setMessage("Fetch DemoClass Lists Scucessfull.");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;

	}

	@Override
	public Document<DemoClassExtraCurricular> updateECATeacher(DemoClassExtraCurricular demoClass) {
		Document<DemoClassExtraCurricular> result = new Document<>();
		DemoClassExtraCurricular temp = democlassExtraCurricularRepo
				.findByIdDemoClassExtraCurricular(demoClass.getIdDemoClassExtraCurricular());
		try {

			if (temp == null) {

				result.setData(null);
				result.setMessage("Demo class does not exist");
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				return result;

			}

			temp.setIdTeacher(demoClass.getIdTeacher());
			temp = democlassExtraCurricularRepo.save(temp);
			result.setData(temp);
			result.setMessage("New teacher assign to this demo class successfully");
			result.setStatusCode(HttpStatus.OK.value());
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;

	}

	@Override
	public Document<DemoClassExtraCurricular> deactivateECADemoClass(Long idDemoClassExtraCurricular) {
		Document<DemoClassExtraCurricular> result = new Document<>();
		DemoClassExtraCurricular demoClass = democlassExtraCurricularRepo
				.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);
		if (demoClass != null) {
			if (demoClass.getClassScheduleDate().isAfter(LocalDate.now())
					|| demoClass.getClassScheduleDate().isEqual(LocalDate.now())) {
				List<LeadBatchLogExtraCurricular> leadBatchLog = leadBatchLogExtraCurricularRepository
						.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

				if (leadBatchLog.isEmpty()) {
					demoClass.setActiveFlag(Boolean.FALSE);
					demoClass = democlassExtraCurricularRepo.save(demoClass);
					result.setData(demoClass);
					result.setMessage("Democlass schedule delete successfully!");
					result.setStatusCode(HttpStatus.OK.value());
					return result;
				} else {
					result.setData(null);
					result.setMessage("Can't delete demo class, record found in lead batch logs!");
					result.setStatusCode(HttpStatus.NOT_FOUND.value());
					return result;
				}
			} else {
				result.setData(null);
				result.setMessage("You can't delete schedules of today or previous dates");
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				return result;
			}
		}

		else {
			result.setData(null);
			result.setMessage("Demo class data not found");
			result.setStatusCode(HttpStatus.NOT_FOUND.value());
			return result;
		}

	}

	public Document<?> scheduleExtraCurricularDemoClassAndConfirm(Long idVlUser, Long idDemoClassExtraCurricular) {

		Document<LeadBatchLogExtraCurricular> doc = new Document<>();

		try {

			if (idVlUser == null || idVlUser.equals(0L)) {
				throw new NullPointerException("User Id is Not Found");
			}
			if (idDemoClassExtraCurricular == null || idDemoClassExtraCurricular.equals(0L)) {
				throw new NullPointerException("Demo Class Id Cannot be Null. Please Select Demo Class");
			}

			LeadBatchDetailsExtraCurricular batchDetailsExtraCurricular = leadBatchDetailsExtraCurricularRepo
					.findByIdVlUser(idVlUser);

			if (batchDetailsExtraCurricular == null) {
				throw new NullPointerException("User Data Not Found in Lead Batch Details Extra Curricular");
			} else {

				DemoClassExtraCurricular demoClassExtraCurricular = democlassExtraCurricularRepo
						.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

				if (demoClassExtraCurricular == null) {
					throw new NullPointerException("Demo Class Not Found");
				}
				
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String name = authentication.getName();
				
				User teleCaller = userRepository.findByUsername(name);
				
				if(teleCaller == null) throw new AppException("No User Found");

				LeadBatchLogExtraCurricular batchLogExtraCurricular = new LeadBatchLogExtraCurricular();

				batchLogExtraCurricular.setClassScheduleDate(demoClassExtraCurricular.getClassScheduleDate());
				batchLogExtraCurricular.setFromTime(demoClassExtraCurricular.getFromTime());
				batchLogExtraCurricular.setToTime(demoClassExtraCurricular.getToTime());
				batchLogExtraCurricular.setIdAvailableSlot(batchDetailsExtraCurricular.getIdAvailableSlot());
				batchLogExtraCurricular.setIdDemoClassExtraCurricular(idDemoClassExtraCurricular);
				batchLogExtraCurricular.setIdLanguage(batchDetailsExtraCurricular.getIdLanguage());
				batchLogExtraCurricular.setIdLevelExtraCurricular(demoClassExtraCurricular.getIdLevelExtraCurricular());
				batchLogExtraCurricular
						.setIdSubjectExtraCurricular(demoClassExtraCurricular.getIdSubjectExtraCurricular());
				batchLogExtraCurricular.setIdTeacher(demoClassExtraCurricular.getIdTeacher());
				batchLogExtraCurricular.setIdVlUser(idVlUser);
				batchLogExtraCurricular.setTelecallerIdVlUser(teleCaller.getUserSurId());

				LeadBatchLogExtraCurricular saved = leadBatchLogExtraCurricularRepo.save(batchLogExtraCurricular);

				leadBatchDetailsExtraCurricularRepo.delete(batchDetailsExtraCurricular);

				demoClassExtraCurricular
						.setTotalStudentsEnrolled(demoClassExtraCurricular.getTotalStudentsEnrolled() + 1);
				democlassExtraCurricularRepo.save(demoClassExtraCurricular);

				User user = userRepository.findByUserSurId(idVlUser);
				
				if(user == null) { throw new AppException("No User Found");}

				emailServiceImpl.sendNotificationEmailwhenDemoClassAssigned(user.getEmail(), user.getFirstName(),
						batchLogExtraCurricular.getClassScheduleDate().toString(),
						batchLogExtraCurricular.getFromTime().toString() + " - "
								+ batchLogExtraCurricular.getToTime().toString(),
						(subjectRepository.findByIdSubject(demoClassExtraCurricular.getIdSubjectExtraCurricular()))
								.getSubjectName());

				doc.setData(saved);
				doc.setMessage("Demo Class Has been Assigned to this User");
				doc.setStatusCode(200);
			}

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> listExtraCurricularDemoClassByIdTeacher(Long idTeacher) {

		Document<List<TeacherDemoClassExtraCurricularDTO>> doc = new Document<>();

		try {

			if (idTeacher == null || idTeacher.equals(0L)) {
				throw new NullPointerException("Teacher Id Cannot be Null");
			}

			List<DemoClassExtraCurricular> listOfAllDemoClassAssignedToThisTeacher = democlassExtraCurricularRepo
					.findByIdTeacher(idTeacher);

			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);

			if (teacher == null) {
				throw new NullPointerException("Teacher Details Not Found");
			}

			if (listOfAllDemoClassAssignedToThisTeacher.isEmpty()) {
				throw new NullPointerException("No Extra Curricular Demo Class has been Assigned to this Teacher");
			}

			else {

				List<TeacherDemoClassExtraCurricularDTO> classExtraCurricularDTOs = new ArrayList<>();

				for (DemoClassExtraCurricular demoClassExtraCurricular : listOfAllDemoClassAssignedToThisTeacher) {

					LevelExtraCurricular levelExtraCurricular = levelExtraCurricularRepository
							.findByIdLevelExtraCurricular(demoClassExtraCurricular.getIdLevelExtraCurricular());

					if(levelExtraCurricular == null) throw new AppException("No LevelExtraCurricular Found");
					
					Subject subject = subjectRepository
							.findByIdSubject(demoClassExtraCurricular.getIdSubjectExtraCurricular());
					
					if(subject == null) throw new AppException("No subject Found");

					TeacherDemoClassExtraCurricularDTO teacherDemoClassExtraCurricularDTO = new TeacherDemoClassExtraCurricularDTO();

					teacherDemoClassExtraCurricularDTO.setActiveFlag(demoClassExtraCurricular.getActiveFlag());
					teacherDemoClassExtraCurricularDTO
							.setActualEndTimeStamp(demoClassExtraCurricular.getActualEndTimeStamp());
					teacherDemoClassExtraCurricularDTO
							.setActualStartTimeStamp(demoClassExtraCurricular.getActualStartTimeStamp());
					teacherDemoClassExtraCurricularDTO
							.setClassConductedFlag(demoClassExtraCurricular.getClassConductedFlag());
					teacherDemoClassExtraCurricularDTO
							.setClassScheduleDate(demoClassExtraCurricular.getClassScheduleDate());
					teacherDemoClassExtraCurricularDTO.setDescription(demoClassExtraCurricular.getDescription());
					teacherDemoClassExtraCurricularDTO.setFromTime(demoClassExtraCurricular.getFromTime());
					teacherDemoClassExtraCurricularDTO
							.setIdDemoClassExtraCurricular(demoClassExtraCurricular.getIdDemoClassExtraCurricular());
					teacherDemoClassExtraCurricularDTO.setLevelExtraCurricular(levelExtraCurricular);
					teacherDemoClassExtraCurricularDTO.setTeacher(teacher);
					teacherDemoClassExtraCurricularDTO.setMaxStudents(demoClassExtraCurricular.getMaxStudents());
					teacherDemoClassExtraCurricularDTO.setSubject(subject);
					teacherDemoClassExtraCurricularDTO
							.setTotalStudentsEnrolled(demoClassExtraCurricular.getTotalStudentsEnrolled());
					teacherDemoClassExtraCurricularDTO.setToTime(demoClassExtraCurricular.getToTime());

					classExtraCurricularDTOs.add(teacherDemoClassExtraCurricularDTO);

				}

				doc.setData(classExtraCurricularDTOs);
				doc.setMessage("List Of All Extra Curricular Demo Class Assigned to this Teacher");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@SuppressWarnings("unused")
	@Override
	public Document<?> createWebExMeetingForExtraCurricularDemoClassByTeacher(Long idDemoClassExtraCurricular,
			Long idTeacher) {

		Document<WebExMetaDataDTO> doc = new Document<>();
		try {

			if (idDemoClassExtraCurricular == 0 || idDemoClassExtraCurricular == null) {
				throw new NullPointerException("Demo Class Id Cannot be Null");
			}

			if (idTeacher == 0 || idTeacher == null) {
				throw new NullPointerException("Teacher Id cannot be Null");
			}

			// Get WebEx Pool Details from idTeacher
			Teacher teacher = teacherRepository.findByIdTeacher(idTeacher);
			
			if(teacher == null) throw new AppException("No Teacher Found");

			WebExPool webExPoolObject = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

			
			if(webExPoolObject == null) throw new AppException("No webExPoolObject Found");
			
			
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
					+ "          <metaData>\r\n" + "              <confName>DemoClassExtraCurricular-"
					+ idDemoClassExtraCurricular + "</confName>\r\n"
					+ "              <agenda>Demo CLass Extra Curricular</agenda>\r\n" + "          </metaData>\r\n"
					+ "          <participants>\r\n" + "                <attendees>";

			String xmlBodyForCreateMeetingPart2 = "";

			List<LeadBatchLogExtraCurricular> listOfAllConfirmedStudents = leadBatchLogExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);
			
			if(listOfAllConfirmedStudents.isEmpty()) throw new AppException("No listOfAllConfirmedStudents Found");

			for (LeadBatchLogExtraCurricular leadBatchLogExtraCurricular : listOfAllConfirmedStudents) {

				User user = userRepository.findByUserSurId(leadBatchLogExtraCurricular.getIdVlUser());
				xmlBodyForCreateMeetingPart2 += "<attendee>\r\n" + "                        <person>\r\n"
						+ "                            <name>" + user.getFirstName() + "</name>\r\n"
						+ "                            <email>" + user.getEmail() + "</email>\r\n"
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

			for (int i = 0; i < meetingKey.getLength(); i++) {
				Node meetingKeyNode = meetingKey.item(i);
				Element serviceTypeElement = (Element) meetingKeyNode;
				System.out.println("------------Meeting Key----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingKeyActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}

			for (int i = 0; i < meetingUUid.getLength(); i++) {
				Node meetingUuidNode = meetingUUid.item(i);
				Element serviceTypeElement = (Element) meetingUuidNode;
				System.out.println("------------Meeting UUID----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingUuidActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
			}

			for (int i = 0; i < meetingPassword.getLength(); i++) {
				Node meetingPasswordNode = meetingPassword.item(i);
				Element serviceTypeElement = (Element) meetingPasswordNode;
				System.out.println("------------Meeting Password----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingPasswordActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");
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

			DemoClassExtraCurricularSchedule existingDemoCLassSchedule = demoClassScheduleExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			if (existingDemoCLassSchedule != null) {
				existingDemoCLassSchedule.setWebExAttendeeJoinMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				existingDemoCLassSchedule.setWebExHostStartMeetingUrl(hostStartMeetingUrlActual);
				existingDemoCLassSchedule.setWebExMeetingGuestUserToken(meetingGuestUserTokenActual);
				existingDemoCLassSchedule.setWebExMeetingKey(meetingKeyActual);
				existingDemoCLassSchedule.setWebExMeetingPassword(meetingPasswordActual);
				existingDemoCLassSchedule.setWebExMeetingUuid(meetingUuidActual);

				demoClassScheduleExtraCurricularRepo.save(existingDemoCLassSchedule);
			} else {
				DemoClassExtraCurricularSchedule demoClassExtraCurricularSchedule = new DemoClassExtraCurricularSchedule();
				demoClassExtraCurricularSchedule.setIdDemoClassExtraCurricular(idDemoClassExtraCurricular);
				demoClassExtraCurricularSchedule
						.setWebExAttendeeJoinMeetingUrl(webExMetaDataDTO.getAttendeeJoinMeetingUrl());
				demoClassExtraCurricularSchedule.setWebExHostStartMeetingUrl(hostStartMeetingUrlActual);
				demoClassExtraCurricularSchedule.setWebExMeetingGuestUserToken(meetingGuestUserTokenActual);
				demoClassExtraCurricularSchedule.setWebExMeetingKey(meetingKeyActual);
				demoClassExtraCurricularSchedule.setWebExMeetingPassword(meetingPasswordActual);
				demoClassExtraCurricularSchedule.setWebExMeetingUuid(meetingUuidActual);

				// Save the demoClass Schedule to Extra Curricular Demo CLass Schedule Table
				demoClassScheduleExtraCurricularRepo.save(demoClassExtraCurricularSchedule);
			}

			// Update Class Conducted Flag to True
			DemoClassExtraCurricular existingDemoClass = democlassExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			if (existingDemoClass != null) {
				existingDemoClass.setClassConductedFlag(Boolean.TRUE);
				existingDemoClass.setActualStartTimeStamp(Instant.now());
				existingDemoClass.setActualEndTimeStamp(Instant.now().plus(1, ChronoUnit.HOURS));
				democlassExtraCurricularRepo.save(existingDemoClass);
			}
			else {
				
				 throw new AppException("Invalid exisiting live class demo info");
			}

			// Send mail to Students by taking details from lead batch log
			List<LeadBatchLogExtraCurricular> leadBatchLogData = leadBatchLogExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			// Get User Info By IdVlUser
			for (LeadBatchLogExtraCurricular leadBatchLog : leadBatchLogData) {
				User userInfo = userRepository.findByUserSurId(leadBatchLog.getIdVlUser());

				emailService.sendEmailToAllStudentsForJoiningDemoClassExtraCurricular(userInfo.getFirstName(),
						userInfo.getEmail(), "", idDemoClassExtraCurricular,
						existingDemoClass.getIdSubjectExtraCurricular(), 
						existingDemoClass.getFromTime() ,
					    existingDemoClass.getToTime());
			}

			doc.setData(webExMetaDataDTO);
			doc.setMessage("WebEx Meeting Created And The Details Have been Fetched");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}

		return doc;
	}

	@SuppressWarnings("unused")
	@Override
	public Document<?> studentOrParentJoinDemoClassExtraCurricular(Long idLeadBatchLogExtraCurricular, Long idVlUser,
			Long idDemoClassExtraCurricular) {
		Document<String> doc = new Document<>();
		try {

			if (idLeadBatchLogExtraCurricular == null || idLeadBatchLogExtraCurricular == 0) {
				throw new NullPointerException("Extra Curricular Lead Batch Log Id Cannot be Null or Zero");
			}

			if (idVlUser == null || idVlUser == 0) {
				throw new NullPointerException("Id Vl User Cannot be Null or Zero");
			}

			if (idDemoClassExtraCurricular == null || idDemoClassExtraCurricular == 0) {
				throw new NullPointerException("Extra Curricular Demo Class Id Cannot be Null or Zero");
			}

			// Get Extra Curricular Demo Class
			DemoClassExtraCurricular demoClass = democlassExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);
			
			if(demoClass == null) throw new AppException("No demoClass Found");

			// Get Demo Class Scheduled WebEx Info
			DemoClassExtraCurricularSchedule demoClassSchedule = demoClassScheduleExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			if (demoClassSchedule == null) {
				throw new NullPointerException(
						"No Demo Class Schedule WebEx Info found. Class Not Yet Started By Teacher");
			}

			// Get Teacher From demo Class
			Teacher teacher = teacherRepository.findByIdTeacher(demoClass.getIdTeacher());
			
			if(teacher == null) throw new AppException("No Teacher Found");

			// Get WebEx Pool Details
			WebExPool webExPoolObj = webExPoolRepo.findByIdWebExPool(teacher.getIdWebexPool());

			String xmlBodyForGetMeeting = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n" + "<serv:message\r\n"
					+ "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n"
					+ "    xmlns:serv=\"http://www.webex.com/schemas/2002/06/service\">\r\n" + "    <header>\r\n"
					+ "        <securityContext>\r\n" + "            <siteName>vistafoundation</siteName>\r\n"
					+ "            <webExID>" + webExPoolObj.getWebExUserId() + "</webExID>\r\n"
					+ "            <password>" + webExPoolObj.getWebExPassword() + "</password>\r\n"
					+ "        </securityContext>\r\n" + "    </header>\r\n" + "    <body>\r\n"
					+ "        <bodyContent xsi:type=\"java:com.webex.service.binding.meeting.GetMeeting\">\r\n"
					+ "            <meetingKey>" + demoClassSchedule.getWebExMeetingKey() + "</meetingKey>\r\n"
					+ "        </bodyContent>\r\n" + "    </body>\r\n" + "</serv:message>";

			String url = "https://api.webex.com/WBXService/XMLService";

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> result = restTemplate.postForEntity(url, xmlBodyForGetMeeting, String.class);

			// Converting Response Entity to Input Stream Object
			InputStream targetStream = new ByteArrayInputStream(result.getBody().getBytes());

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			org.w3c.dom.Document document = null;
			builder = factory.newDocumentBuilder();
			document = builder.parse(targetStream);

			document.getDocumentElement().normalize();

//			Element root = document.getDocumentElement();

			NodeList meetingStatus = document.getElementsByTagName("meet:status");

			String meetingStatusActual = null;

			for (int i = 0; i < meetingStatus.getLength(); i++) {
				Node meetingStatusNode = meetingStatus.item(i);
				Element serviceTypeElement = (Element) meetingStatusNode;
				System.out.println("------------Meeting Status----------");
				System.out.println(serviceTypeElement.getTextContent());
				meetingStatusActual = serviceTypeElement.getTextContent();
				System.out.println("----------------------------------------");

			}
			
			if (meetingStatusActual == null) 
			{
				doc.setData("INVALID PROGRESS");
				doc.setMessage("Invalid progress");
				doc.setStatusCode(200);
			}

			else if (meetingStatusActual.equals("NOT_INPROGRESS")) {
				doc.setData("NOT_INPROGRESS");
				doc.setMessage("Class Has not Yet Started");
				doc.setStatusCode(200);

			} else if (meetingStatusActual.equals("INPROGRESS")) {

				User user = userRepository.findByUserSurId(idVlUser);
				
				if(user == null) throw new AppException("No User Found");

				// Now Get Attendee Join Meeting Url

				String xmlBodyForGettingAttendeeUrl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<serv:message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
						+ "    <header>\r\n" + "        <securityContext>\r\n" + "            <webExID>"
						+ webExPoolObj.getWebExUserId() + "</webExID>\r\n" + "            <password>"
						+ webExPoolObj.getWebExPassword() + "</password>\r\n"
						+ "            <siteID>13696027</siteID>\r\n"
						+ "				<siteName>vistafoundation</siteName>\r\n"
						+ "            <partnerID>I2WNi1i-xdYDHn-HNXJCMA</partnerID>\r\n" + "            <email>"
						+ webExPoolObj.getWebExUserId() + "</email>\r\n" + "        </securityContext>\r\n"
						+ "    </header>\r\n" + "    <body>\r\n" + "        <bodyContent\r\n"
						+ "            xsi:type=\"java:com.webex.service.binding.meeting.GetjoinurlMeeting\">\r\n"
						+ "            <sessionKey>" + demoClassSchedule.getWebExMeetingKey() + "</sessionKey>\r\n"
						+ "            <attendeeName>" + user.getFirstName() + "</attendeeName>\r\n"
						+ "            <attendeeEmail>" + user.getEmail() + "</attendeeEmail>\r\n"
						+ "            <meetingPW>" + demoClassSchedule.getWebExMeetingPassword() + "</meetingPW>\r\n"
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

				demoClassSchedule.setWebExAttendeeJoinMeetingUrl(attendeeJoinMeetingUrlActual);
				demoClassScheduleExtraCurricularRepo.save(demoClassSchedule);

				ExtraCurricularLeadAttendedClass leadAttendedClass = extraCurricularLeadAttendedClassRepo
						.findByIdVlUserAndIdLeadBatchLogExtraCurricular(idVlUser, idLeadBatchLogExtraCurricular);

				if (leadAttendedClass != null) {
					leadAttendedClass.setIdLeadBatchLogExtraCurricular(idLeadBatchLogExtraCurricular);
					leadAttendedClass.setIdVlUser(idVlUser);
					extraCurricularLeadAttendedClassRepo.save(leadAttendedClass);
				} else {
					ExtraCurricularLeadAttendedClass extraCurricularLeadAttendedClass = new ExtraCurricularLeadAttendedClass();
					extraCurricularLeadAttendedClass.setIdLeadBatchLogExtraCurricular(idLeadBatchLogExtraCurricular);
					extraCurricularLeadAttendedClass.setIdVlUser(idVlUser);
					extraCurricularLeadAttendedClassRepo.save(extraCurricularLeadAttendedClass);
				}

				doc.setData(demoClassSchedule.getWebExAttendeeJoinMeetingUrl());
				doc.setMessage("Lead Attended Class Data Captured");
				doc.setStatusCode(200);
			}else {
				doc.setData("INVALID PROGRESS");
				doc.setMessage("Invalid progress");
				doc.setStatusCode(200);
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}
		return doc;
	}

	@Override
	public Document<?> getAllLeadBatchLogsExtraCurricular(Long idVlUser) {
		Document<List<StudentDemoClassExtraCurricularDTO>> doc = new Document<>();
		try {

			if (idVlUser == 0 || idVlUser == null) {
				throw new NullPointerException("User Sur Id Cannot be Null");
			}

			List<StudentDemoClassExtraCurricularDTO> studentDemoClassExtraCurricularDTOs = new ArrayList<>();

			// Get LeadBatch Log Extra Curricular
			LeadBatchLogExtraCurricular registeredDemoClassesExtraCurricular = leadBatchLogExtraCurricularRepo
					.findByIdVlUser(idVlUser);

			if (registeredDemoClassesExtraCurricular == null) {
				throw new NullPointerException("No Extra Curricular Demo Class Has been assigned for this user");
			}

			StudentDemoClassExtraCurricularDTO studentDemoClassExtraCurricularDTO = new StudentDemoClassExtraCurricularDTO();

			Teacher teacher = teacherRepository.findByIdTeacher(registeredDemoClassesExtraCurricular.getIdTeacher());

			if (teacher == null) {
				throw new NullPointerException("Teacher Data does not exists");
			}
			Subject subject = subjectRepository
					.findByIdSubject(registeredDemoClassesExtraCurricular.getIdSubjectExtraCurricular());

			if (subject == null) {
				throw new NullPointerException("Subject Not Found");
			}

			LevelExtraCurricular levelExtraCurricular = levelExtraCurricularRepository
					.findByIdLevelExtraCurricular(registeredDemoClassesExtraCurricular.getIdLevelExtraCurricular());

			if (levelExtraCurricular == null) {
				throw new NullPointerException("Level Data Not Found");
			}

			DemoClassExtraCurricular demoClassExtraCurricular = democlassExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(
							registeredDemoClassesExtraCurricular.getIdDemoClassExtraCurricular());
			
			if(demoClassExtraCurricular == null) throw new AppException("No DemoClassExtraCurricular Found");

			DemoClassExtraCurricularSchedule extraCurricularSchedule = demoClassScheduleExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(
							registeredDemoClassesExtraCurricular.getIdDemoClassExtraCurricular());

			if (extraCurricularSchedule == null) {
				studentDemoClassExtraCurricularDTO.setMeetingPassword("Not Yet Available");
			} else {
				studentDemoClassExtraCurricularDTO
						.setMeetingPassword(extraCurricularSchedule.getWebExMeetingPassword());
			}

			studentDemoClassExtraCurricularDTO
					.setClassScheduleDate(registeredDemoClassesExtraCurricular.getClassScheduleDate());
			studentDemoClassExtraCurricularDTO.setDemoClassExtraCurricular(demoClassExtraCurricular);
			studentDemoClassExtraCurricularDTO.setFromTime(registeredDemoClassesExtraCurricular.getFromTime());
			studentDemoClassExtraCurricularDTO.setLeadBatchLogExtraCurricular(registeredDemoClassesExtraCurricular);
			studentDemoClassExtraCurricularDTO.setSubject(subject);
			studentDemoClassExtraCurricularDTO.setTeacher(teacher);
			studentDemoClassExtraCurricularDTO.setToTime(registeredDemoClassesExtraCurricular.getToTime());
			studentDemoClassExtraCurricularDTO.setLevelExtraCurricular(levelExtraCurricular);

			studentDemoClassExtraCurricularDTOs.add(studentDemoClassExtraCurricularDTO);

			doc.setData(studentDemoClassExtraCurricularDTOs);
			doc.setMessage("All Lead Batch log for this User");
			doc.setStatusCode(200);

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
		}
		return doc;

	}

	@Override
	public Document<?> getDemoClassWebExMetadataAttendeeInfo(Long idDemoClass) {

		Document<DemoClassSchedule> doc = new Document<>();

		try {

			if (idDemoClass == null) {
				throw new NullPointerException("Demo Class Id Cannot be Null");
			}

			DemoClass demoClass = demoClassRepository.findByIdDemoClass(idDemoClass);

			if (demoClass == null) {
				throw new NullPointerException("Demo Class Not Found");
			}

			DemoClassSchedule demoClassSchedule = demoClassScheduleRepo.findByIdDemoClass(idDemoClass);

			if (demoClassSchedule == null) {
				throw new NullPointerException("Demo Class Schedule Not Found. Class Not Yet Started By Teacher");
			}

			doc.setData(demoClassSchedule);
			doc.setMessage("Demo Class Schedule Info for Attendees to join Demo Class");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}

	@Override
	public Document<?> getDemoClassExtraCurricularWebExMetadataAttendeeInfo(Long idDemoClassExtraCurricular) {

		Document<DemoClassExtraCurricularSchedule> doc = new Document<>();

		try {

			if (idDemoClassExtraCurricular == null) {
				throw new NullPointerException("Demo Class Id Cannot be Null");
			}

			DemoClassExtraCurricular demoClass = democlassExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			if (demoClass == null) {
				throw new NullPointerException("Demo Class Not Found");
			}

			DemoClassExtraCurricularSchedule demoClassSchedule = demoClassScheduleExtraCurricularRepo
					.findByIdDemoClassExtraCurricular(idDemoClassExtraCurricular);

			if (demoClassSchedule == null) {
				throw new NullPointerException("Demo Class Schedule Not Found. Class Not Yet Started By Teacher");
			}

			doc.setData(demoClassSchedule);
			doc.setMessage("Demo Class Schedule Info for Attendees to join Demo Class");
			doc.setStatusCode(200);

		} catch (Exception exp) {
			doc.setData(null);
			doc.setMessage(exp.getLocalizedMessage());
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return doc;
	}
}
