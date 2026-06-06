package co.vistafoundation.vlearning.subject.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.LevelExtraCurricularRepository;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.dto.QuizDifficulty;
import co.vistafoundation.vlearning.quiz.repository.QuizRepository;
import co.vistafoundation.vlearning.subject.dto.CreateSubjectChapterDTO;
import co.vistafoundation.vlearning.subject.dto.ExamPreparationSubjectDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectCrudDTO;
import co.vistafoundation.vlearning.subject.dto.SubjectResponseDTO;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subject.service.SubjectService;
import co.vistafoundation.vlearning.subscription.dto.NewStreamingSubjectChapterDTO;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentChapterListDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubjectResDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.utils.GenerateTime;
import co.vistafoundation.vlearning.vct.dto.VCTSubjectDTO;

/**
 * @author NaveenKumar
 * 
 **/
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	ClassRepository classStandardRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	LevelExtraCurricularRepository levelExtraCurricularRepository;

	@Autowired
	LiveClassRepository liveClassRepository;

	@Value("${APP_ANGULAR_URL}")
	private String appAngularUrl;
	
	@Value("#{${listOfMarketingIds}}")
	private List<String> marketingIds;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	GenerateTime generateTime;
	
	/**
	 * @author Naveen Kumar
	 * @return list of subjects
	 * @return null when no data found.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document getAllSubject() {

		List<Subject> subList = subjectRepository.findAll();
		Document result = new Document();
		if (!subList.isEmpty()) {
			List<Subject> temp = subList.stream().filter(s -> !s.getSubjectName().equalsIgnoreCase("NA"))
					.collect(Collectors.toList());
			result.setData(temp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");
			return result;
		} else {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage("Something went Wrong!");
			return result;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document<List<SubjectChapter>> getAllChapterBySubject(Long idSubject, Long idClassStandard, Long idSyllabus,
			Long idState) {

		Document<List<SubjectChapter>> result = new Document();

		try {

			List<SubjectChapter> chapterList = subjectChapterRepository
					.findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusOrderBySortOrder(idSubject,
							idClassStandard, idState, idSyllabus);
			if (chapterList.isEmpty())
				throw new NullPointerException("No Chapters found for the selected subject and class standard");

			result.setData(chapterList);
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
	public Document<List<SubjectResponseDTO>> getAcademicSubjectByClassStandard(Long idClassStandard, Long idSyllabus) {

		Document<List<SubjectResponseDTO>> result = new Document<>();

		try {

			ProductGroup pg = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(idClassStandard,
					5L, idSyllabus);

			if (pg == null)
				throw new NullPointerException("Invalid ProductGroup");

			List<Product> prodList = productRepository.findByIdProductGroupAndActiveFlag(pg.getIdProductGroup()
					,Boolean.TRUE);

			if (prodList.isEmpty())
				throw new NullPointerException("No Products found found.");

			List<Subject> subjects = new ArrayList<>();

			for (Product prod : prodList) {
				Subject subTemp = subjectRepository.findByIdSubject(prod.getIdSubject());

				if (subTemp == null)
					throw new NullPointerException("Invalid Subject");

				subjects.add(subTemp);

			}

			if (subjects.isEmpty())
				throw new NullPointerException("No Subject found for selected Class Standard.");

			List<Subject> distinctResult = subjects.stream().distinct().collect(Collectors.toList());

			List<SubjectResponseDTO> finalSubList = new ArrayList<SubjectResponseDTO>();

			for (Subject subject : distinctResult) {
				SubjectResponseDTO srdto = new SubjectResponseDTO();
				BeanUtils.copyProperties(subject, srdto);

				// generate dynamic url for subject images
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";
				srdto.setImageURL(imageUrl);

				finalSubList.add(srdto);
			}

			result.setData(finalSubList);
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
	public Document getSubjectbasedOnCategory(String categoryCode) {

		Document result = new Document();

		try {

			List<Subject> subjectList = subjectRepository.getSubjectBasedOnCategory(categoryCode);
			if (subjectList.isEmpty())
				throw new NullPointerException("No subjects found");

			result.setData(subjectList);
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
	public Document<List<SubjectResponseDTO>> getExtraCurricularSubjects() {

		Document<List<SubjectResponseDTO>> result = new Document<>();

		try {

			List<ProductGroup> pgList = productGroupRepository.getByIdProductLine(6L);

			if (pgList.isEmpty())
				throw new NullPointerException("No Product group found for idProducttLine=6");

			HashSet<Long> pgIds = new HashSet<>();

			pgList.removeIf(e -> !pgIds.add(e.getIdProductGroup()));

			List<Long> finalPgList = new ArrayList<>(pgIds);

			List<Product> pList = productRepository.findByIdProductGroupInAndActiveFlag(
					finalPgList,Boolean.TRUE);

			if (pList.isEmpty())
				throw new NullPointerException("No Product found for listed product group" + finalPgList.toString());

			HashSet<Long> uniqueSubIds = new HashSet<>();

			pList.removeIf(e -> !uniqueSubIds.add(e.getIdSubject()));

			List<Long> finalSubjectList = new ArrayList<>(uniqueSubIds);

			List<Subject> subjects = subjectRepository.findByIdSubjectIn(finalSubjectList);

			if (subjects.isEmpty())
				throw new NullPointerException("No Subject found !");

			List<SubjectResponseDTO> finalSubList = new ArrayList<SubjectResponseDTO>();

			// update by naveen kumar for adding functionality to add image url in
			// functionality
			for (Subject subject : subjects) {
				SubjectResponseDTO srdto = new SubjectResponseDTO();
				BeanUtils.copyProperties(subject, srdto);

				// generate dynamic url for subject images
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";
				srdto.setImageURL(imageUrl);

				finalSubList.add(srdto);
			}

			result.setData(finalSubList);
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
	public Document<List<SubjectResponseDTO>> getBacthSubjectByClassStandard(Long idClassStandard, Long idSyllabus,
			Long idProductLine) {
		Document<List<SubjectResponseDTO>> result = new Document<>();

		try {

			ProductGroup pg = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(idClassStandard,
					idProductLine, idSyllabus);

			if (pg == null)
				throw new NullPointerException("Invalid ProductGroup");

			List<Product> prodList = productRepository.findByIdProductGroupAndActiveFlag(
					pg.getIdProductGroup(),Boolean.TRUE);

			if (prodList.isEmpty())
				throw new NullPointerException("No Products found found.");

			List<Subject> subjects = new ArrayList<>();

			for (Product prod : prodList) {
				Subject subTemp = subjectRepository.findByIdSubject(prod.getIdSubject());

				if (subTemp == null)
					throw new NullPointerException("Invalid Subject");

				subjects.add(subTemp);

			}

			if (subjects.isEmpty())
				throw new NullPointerException("No Subject found for Selected Class Standard.");

			List<Subject> distinctResult = subjects.stream().distinct().collect(Collectors.toList());

			List<SubjectResponseDTO> finalSubList = new ArrayList<SubjectResponseDTO>();

			for (Subject subject : distinctResult) {
				SubjectResponseDTO srdto = new SubjectResponseDTO();
				BeanUtils.copyProperties(subject, srdto);

				// generate dynamic url for subject images
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";
				srdto.setImageURL(imageUrl);

				finalSubList.add(srdto);
			}

			result.setData(finalSubList);
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
	public Document<SubjectChapter> createSubjectChapter(CreateSubjectChapterDTO request) {
		Document<SubjectChapter> result = new Document<>();
		try {

			Subject subject = subjectRepository.findByIdSubject(request.getIdSubject());

			if (subject == null)
				throw new NullPointerException("Invalid subject");

			State state = stateRepository.findByIdState(request.getIdState());

			if (state == null)
				throw new NullPointerException("Invalid state");

			ClassStandard classStandard = classStandardRepository.findByIdClassStandard(request.getIdClassStandard());

			if (classStandard == null)
				throw new NullPointerException("Invalid classStandard");

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(request.getIdSyllabus());

			if (syllabus == null)
				throw new NullPointerException("Invalid syllabus");

			SubjectChapter subjectChapter = new SubjectChapter();

			subjectChapter.setChapterName(request.getChapterName());
			subjectChapter.setIdClassStandard(request.getIdClassStandard());
			subjectChapter.setIdState(request.getIdState());
			subjectChapter.setIdSubject(request.getIdSubject());
			subjectChapter.setIdSyllabus(request.getIdSyllabus());
			subjectChapter.setSortOrder(request.getSortOrder());

			SubjectChapter temp = subjectChapterRepository.save(subjectChapter);

			result.setData(temp);
			result.setStatusCode(HttpStatus.CREATED.value());
			result.setMessage("Subject Chapter create Sucessfully");
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@Override
	public Document<SubjectChapter> updateSubjectChapter(SubjectChapter request) {
		Document<SubjectChapter> result = new Document<>();
		try {

			if (request.getChapterName() == null)
				throw new NullPointerException("Chapter name cannot be empty");

			SubjectChapter sc = subjectChapterRepository.findByIdSubjectChapter(request.getIdSubjectChapter());

			if (sc == null)
				throw new NullPointerException("Invalid SubjectChapter");

			sc.setChapterName(request.getChapterName());
			sc.setSortOrder(request.getSortOrder());
			SubjectChapter temp = subjectChapterRepository.save(sc);

			result.setData(temp);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Subject Chapter Updated Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@Override
	public Document<List<SubjectResponseDTO>> getAcademicSubjectByProduct(Long idClassStandard, Long idSyllabus,
			Long idState) {

		Document<List<SubjectResponseDTO>> result = new Document<>();

		try {

			idClassStandard = idClassStandard != -1 ? idClassStandard : null;
			idSyllabus = idSyllabus != -1 ? idSyllabus : null;
			idState = idState != -1 ? idState : null;

			List<Long> idSubjectList = productRepository
					.getProductBasedOnProductlineAndIdSyllabusAndIdStateAndIdClassStandard(5l, idSyllabus, idState,
							idClassStandard,Boolean.TRUE);

			if (idSubjectList.isEmpty())
				throw new NullPointerException("No Products found found.");

			List<Subject> subjects = new ArrayList<>();

			for (Long idSubject : idSubjectList) {
				Subject subTemp = subjectRepository.findByIdSubject(idSubject);

				if (subTemp == null)
					throw new NullPointerException("Invalid Subject");

				subjects.add(subTemp);

			}

			if (subjects.isEmpty())
				throw new NullPointerException("No Subject found for Selected Class Standard , Syllabus and State .");

			List<SubjectResponseDTO> finalSubList = new ArrayList<SubjectResponseDTO>();

			for (Subject subject : subjects) {
				SubjectResponseDTO srdto = new SubjectResponseDTO();
				BeanUtils.copyProperties(subject, srdto);

				// generate dynamic url for subject images
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";
				srdto.setImageURL(imageUrl);

				finalSubList.add(srdto);
			}

			result.setData(finalSubList);
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
	public Document<ArrayList<Object>> browseCourseBeforeLogin() {
		Document<ArrayList<Object>> result = new Document<ArrayList<Object>>();
		try {

			ArrayList<Object> finalList = new ArrayList<Object>();

			ArrayList<Long> syllabusIds = new ArrayList<Long>();
			syllabusIds.add(4L);
			syllabusIds.add(2L);

			List<Long> syllabusIdList = productRepository.getDistinctSyllabusBasedOnProductLine(5L,Boolean.TRUE);
			if (syllabusIdList.isEmpty())
				throw new NullPointerException("No Syllabus Found !");

			List<Syllabus> syllabusList = syllabusRepository.findByIdSyllabusIn(syllabusIdList);
			if (syllabusList.isEmpty())
				throw new NullPointerException("No syllabusList Found !");
			for (Syllabus syllabus : syllabusList) {

				HashMap<String, Object> syllabusObj = new HashMap<>();
				syllabusObj.put("idSyllabus", syllabus.getIdSyllabus());
				syllabusObj.put("syllabusName", syllabus.getSyllabusName());

				ArrayList<Object> stateDataList = new ArrayList<Object>();

				List<Long> stateIdList = productRepository.getDistinctStateBasedOnProductLineIdSyllabus(5L,
						syllabus.getIdSyllabus(),Boolean.TRUE);
				if (stateIdList.isEmpty())
					throw new NullPointerException("No state Found !");

				List<State> stateList = stateRepository.findByIdStateInOrderByDisplayOrderAsc(stateIdList);
				if (stateList.isEmpty())
					throw new NullPointerException("No stateList Found !");
				for (State state : stateList) {

					HashMap<String, Object> stateObj = new HashMap<>();
					stateObj.put("idState", state.getIdState());
					stateObj.put("stateName", state.getState());

					ArrayList<Object> classDataList = new ArrayList<Object>();

//					 List<ClassStandard> classList = classStandardRepository.findByIdClassStandardNot(4L);
					List<Long> classIdList = productRepository
							.getDistinctClassStandardsBasedOnProductLineIdSyllabusAndIdState(5L,
									syllabus.getIdSyllabus(), state.getIdState(),Boolean.TRUE);
					if (classIdList.isEmpty())
						throw new NullPointerException("No Classes Found !");

					List<ClassStandard> classList = classStandardRepository.findByIdClassStandardIn(classIdList);
					if (classList.isEmpty())
						throw new NullPointerException("No classLists found");

					for (ClassStandard classstandard : classList) {

						HashMap<String, Object> classObj = new HashMap<>();
						classObj.put("idClassStandard", classstandard.getIdClassStandard());
						classObj.put("classStandardName", classstandard.getClassStandadName());

						ArrayList<Object> subjectDataList = new ArrayList<Object>();
						List<Long> subjectIds = productRepository
								.getProductBasedOnProductlineAndIdSyllabusAndIdStateAndIdClassStandard(5L,
										syllabus.getIdSyllabus(), state.getIdState(),
										classstandard.getIdClassStandard(),Boolean.TRUE);

						if (subjectIds.isEmpty()) {
							classObj.put("subjects", subjectDataList);
						} else {
							List<Subject> subList = subjectRepository.findByIdSubjectIn(subjectIds);

							for (Subject subject : subList) {
								HashMap<String, Object> subjectObj = new HashMap<>();
								subjectObj.put("idSubject", subject.getIdSubject());
								subjectObj.put("subjectName", subject.getSubjectName());

								subjectDataList.add(subjectObj);
							}
							classObj.put("subjects", subjectDataList);

						}

						classDataList.add(classObj);
					}
					stateObj.put("classStandards", classDataList);
					stateDataList.add(stateObj);

				}
				syllabusObj.put("states", stateDataList);
				finalList.add(syllabusObj);
			}
			result.setData(finalList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Subject Chapter Updated Sucessfully");

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());

		}

		return result;
	}

	@Override
	public Document<List<Subject>> getECASubjects(Long idProductLine, Long idLevelExtraCurricular) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document<List<ExamPreparationSubjectDTO>> getExamPreparationSubject() {
		Document<List<ExamPreparationSubjectDTO>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			Long idClassStandard = userPrincipal.getIdClassStandard();
			Long idSyllabus = userPrincipal.getIdSyllabus();
			Long idState = userPrincipal.getIdState();

			List<Long> idSubjectList = productRepository
					.getProductBasedOnProductlineAndIdSyllabusAndIdStateAndIdClassStandard(5L, idSyllabus, idState,
							idClassStandard,Boolean.TRUE);

			List<ExamPreparationSubjectDTO> finalSubList = new ArrayList<ExamPreparationSubjectDTO>();

			if (idSubjectList.isEmpty())
				throw new NullPointerException("No Products found found.");

			for (Long idSubject : idSubjectList) {
				ExamPreparationSubjectDTO epsDTO = new ExamPreparationSubjectDTO();

				Subject subTemp = subjectRepository.findByIdSubject(idSubject);

				if (subTemp == null)
					throw new NullPointerException("Invalid Subject");

				BeanUtils.copyProperties(subTemp, epsDTO);

				List<SubjectChapter> chapterList = subjectChapterRepository
						.findTop4ByIdSubjectAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlagOrderBySortOrderAsc(idSubject,
								idClassStandard, idSyllabus, idState,Boolean.TRUE);

				String description;
				if (chapterList.isEmpty()) {
					description = "MCQ's from " + subTemp.getSubjectName() + " are available.";
				} else {
					description = "MCQ's from ";

					String temp = "";
					for (SubjectChapter chapter : chapterList)
						temp = temp + chapter.getChapterName() + ", ";

					description = description + temp + " & etc.";

				}

				epsDTO.setQuizDescription(description);
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/" + idSubject
										+ ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/" + idSubject
										+ ".webp";

				epsDTO.setImageURL(imageUrl);

				List<QuizDifficulty> finalDifficulties = new ArrayList<>();

				List<Object[]> difficulties = productRepository.getSubscribedQuizDifficulty(idSubject, idClassStandard,
						idSyllabus, idState, userPrincipal.getUserSurId(),Boolean.TRUE);

				for (Object[] obj : difficulties) {
					QuizDifficulty qd = new QuizDifficulty();
					qd.setIdProduct(((BigInteger) obj[0]).longValue());
					qd.setIdProductGroup(((BigInteger) obj[1]).longValue());

					qd.setCategory(obj[2].toString());
					qd.setProduct_name(obj[3].toString());
					qd.setIdStudentSubscription(obj[4] != null ? ((BigInteger) obj[4]).longValue() : null);
					qd.setAttempFlag(((Integer) obj[5]) > 0 ? true : false);
					finalDifficulties.add(qd);
				}
				finalDifficulties.sort(Comparator.comparing(QuizDifficulty::getCategory));
				epsDTO.setQuizLevels(finalDifficulties);
				finalSubList.add(epsDTO);

			}

			result.setData(finalSubList);
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
	public Document<List<VCTSubjectDTO>> getVCTSubject() {

		Document<List<VCTSubjectDTO>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			Long idClassStandard = userPrincipal.getIdClassStandard();
			Long idSyllabus = userPrincipal.getIdSyllabus();
			Long idState = userPrincipal.getIdState();

			List<Product> products = productRepository.

					findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndActiveFlag(idState,
							idSyllabus, idClassStandard, 13L, "VCT",Boolean.TRUE);

			List<VCTSubjectDTO> finalSubList = new ArrayList<VCTSubjectDTO>();

			for (Product p : products) {
				VCTSubjectDTO vctDto = new VCTSubjectDTO();

				Subject subject = subjectRepository.findByIdSubject(p.getIdSubject());

				if (subject == null)
					continue;

				vctDto.setColor(subject.getColor());
				vctDto.setIdSubject(subject.getIdSubject());
				vctDto.setSubjectName(subject.getSubjectName());
				vctDto.setIdProduct(p.getIdProduct());

				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";

				vctDto.setImageURL(imageUrl);

				List<String> displayTexts = new ArrayList<String>();

				List<Object[]> questionSets = quizRepository.getVCTQuizQuestionTypesByMarks(p.getIdProduct(),
						Boolean.TRUE);

				if (!questionSets.isEmpty()) {
					int totalMarks = 0;

					for (Object[] obj : questionSets) {
						String displayText = obj[3].toString() + " (" + obj[2] + " mark" + " x " + obj[0] + ")";

						displayTexts.add(displayText);
						totalMarks += (((BigInteger) obj[0]).intValue() * ((Byte) obj[2]));
					}

					vctDto.setTotalMarks(Integer.valueOf(totalMarks));
					vctDto.setQuestionSet(displayTexts);
				} else {
					vctDto.setQuestionSet(displayTexts);
				}

				finalSubList.add(vctDto);

			}

			result.setData(finalSubList);
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

	/*
	 * @author Abdul Elahi
	 * 
	 * CRUD functionality for Subject
	 */
	@Override
	public Document<Subject> saveSubject(SubjectCrudDTO subjectCrudDto) {
	    Document<Subject> result = new Document<>();

	    try {
	    	Subject subject = new Subject();
			subject.setSubjectName(subjectCrudDto.getSubjectName());
			subject.setVideoURL(subjectCrudDto.getVideoURL());
			subject.setHeader(subjectCrudDto.getHeader());
			subject.setDescription(subjectCrudDto.getDescription());
			subject.setColor(subjectCrudDto.getColor());

			Subject savedSubject = subjectRepository.save(subject);

			if (savedSubject != null) {
				result.setData(savedSubject);
				result.setStatusCode(HttpStatus.CREATED.value());
				result.setMessage("Subject Saved Successfully");
	        } else {
	            throw new NullPointerException("Subject creation failed ");
	        }
	    } catch (Exception e) {
	        result.setData(null);
	        result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        result.setMessage( e.getMessage());
	    }

	    return result;
	}


	@Override
	public Document<List<Subject>> getAllSubjects() {
	    Document<List<Subject>> result = new Document<>();
	    try {
	        List<Subject> subjects = subjectRepository.findAll();
	        if (subjects != null) {
	            result.setData(subjects);
	            result.setStatusCode(HttpStatus.OK.value());
	            result.setMessage("Subject Fetched Successfully");
	        } else {
	            throw new NullPointerException("Subjects are empty");
	        }
	    } catch (Exception e) {
	        result.setData(null);
	        result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        result.setMessage(e.getMessage());
	    }
	    return result;
	}

	@Override
	public Document<Subject> getSubjectById(Long idSubject) {
	    Document<Subject> result = new Document<>();
	    try {
	        Subject subject = subjectRepository.findById(idSubject).orElse(null);
	        if (subject != null) {
	            result.setData(subject);
	            result.setStatusCode(HttpStatus.OK.value());
	            result.setMessage("Subject Fetched Successfully");
	        } else {
	            throw new NullPointerException("Subject with Id " + idSubject + " not found");
	        }
	    } catch (Exception ex) {
	        result.setData(null);
	        result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        result.setMessage(ex.getLocalizedMessage());
	    }
	    return result;
	}


	@Override
	public Document<Subject> updateSubject(Long idSubject, SubjectCrudDTO subjectDTO) {
		Document<Subject> result = new Document<>();
		try {
			Subject existingSubject = subjectRepository.findById(idSubject).orElse(null);

			if (existingSubject != null) {

				// Update the existing subject with values from the subjectDTO
				existingSubject.setSubjectName(subjectDTO.getSubjectName());
				existingSubject.setVideoURL(subjectDTO.getVideoURL());
				existingSubject.setHeader(subjectDTO.getHeader());
				existingSubject.setDescription(subjectDTO.getDescription());
				existingSubject.setColor(subjectDTO.getColor());

				Subject updatedSubject = subjectRepository.save(existingSubject);
				result.setData(updatedSubject);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Subject updated successfully");
			} else {
				throw new NullPointerException("Subject with Id " + idSubject + " not found");
			}
		} catch (Exception ex) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(ex.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public Document<String> deleteSubject(Long idSubject) {
	    Document<String> result = new Document<>();


	    try {

		    Subject subject = subjectRepository.getByIdSubject(idSubject);
		    if (subject == null) {
		       throw new NullPointerException("subject not found");
		    }
		    
	        List<Product> products = productRepository.findByIdSubjectAndActiveFlag(idSubject,Boolean.TRUE);
	        if (!products.isEmpty()) {
	            throw new Exception("Subject with Id " + idSubject + " exists in Products and cannot be deleted");
	        }

	        List<SubjectChapter> subjectChapters = subjectChapterRepository.findByIdSubject(idSubject);
	        if (!subjectChapters.isEmpty()) {
	            throw new Exception("Subject with Id " + idSubject + " exists in SubjectChapter and cannot be deleted");

	        }

	        List<LiveClass> liveClasses = liveClassRepository.findByIdSubject(idSubject);
	        if (!liveClasses.isEmpty()) {
	        	 throw new Exception("Subject with Id " + idSubject + " exists in LiveClass and cannot be deleted");
	        }

	        subjectRepository.deleteById(idSubject);
	        result.setData(null);
	        result.setStatusCode(HttpStatus.OK.value());
	        result.setMessage("Subject with Id " + idSubject + " has been deleted");

	    } catch (Exception ex) {
	        result.setData(null);
	        result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        result.setMessage(ex.getMessage());
	    }

	    return result;
	}

	@Override
	@Cacheable(value = "chapterCache")
	public Document<NewStudentSubscriptionSubjectDTO> chapterFilter(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState) {

		Document<NewStudentSubscriptionSubjectDTO> result = new Document<>();

		try {


			int count=0;

		

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
				sscd.setAccessAllowed(Boolean.FALSE);
				finalStreamingChapters.add(sscd);
			}

			Long totalSubjectCount = chaptersVideoCount.values().stream().reduce(0L, Long::sum);

			NewStudentSubscriptionSubjectDTO sssDTO = new NewStudentSubscriptionSubjectDTO();

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
			sssDTO.setClassStandardName(standard.getClassStandadName());			sssDTO.setSyllabusName(syllabus.getSyllabusName());
		    sssDTO.setSubjectName(sub.getSubjectName());			sssDTO.setIdProduct(product.getIdProduct());
			sssDTO.setPercentageCompletion("");
			
			StudentChapterListDTO stdentChapterListDto= new StudentChapterListDTO();
			stdentChapterListDto.setSubjectChapters(finalStreamingChapters);
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

	@Cacheable(value = "extracurricularSubjectCache")
	@Override
	public Document<List<StudentSubjectResDTO>> getSubscribedExtraCurSubStatus() {

		Document<List<StudentSubjectResDTO>> result = new Document<>();
		List<StudentSubjectResDTO> studSubscDtoList = new ArrayList<>();
		StudentSubjectResDTO studentSubjectResDTO;
		try {

			List<Product> productList = productRepository.findByIdProductLineAndActiveFlag(6L, Boolean.TRUE);

			if (productList.isEmpty())
				throw new AppException("No subjects are found.");

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
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<?> updateChapterStatus(Long idSubjectChapter, Boolean status) {

		Document<String> result = new Document<>();

		try {
			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapter(idSubjectChapter);
		     
			if(subjectChapter==null)
				throw new AppException("No Chapter Found");
			
			subjectChapter.setActiveFlag(status);
			
			subjectChapterRepository.save(subjectChapter);

			result.setData("Request Successfull");
			result.setMessage("Success");
			result.setStatusCode(200);

		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}


}
