package co.vistafoundation.vlearning.quiz.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.batch.dto.BatchQuizAnswerRequestDTO;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchQuizAnwser;
import co.vistafoundation.vlearning.batch.model.BatchQuizMeta;
import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;
import co.vistafoundation.vlearning.batch.model.BatchStudentQuizAnswer;
import co.vistafoundation.vlearning.batch.model.BatchStudentQuizQuestion;
import co.vistafoundation.vlearning.batch.repository.BatchQuizAnswerRepository;
import co.vistafoundation.vlearning.batch.repository.BatchQuizMetaRepository;
import co.vistafoundation.vlearning.batch.repository.BatchQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.dto.AnswerRequestDTO;
import co.vistafoundation.vlearning.quiz.dto.BatchQuizDetailsDTO;
import co.vistafoundation.vlearning.quiz.dto.BatchQuizResultDTO;
import co.vistafoundation.vlearning.quiz.dto.BatchStudentQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.ChapterQuizReviewDTO;
import co.vistafoundation.vlearning.quiz.dto.ChatbotQADTO;
import co.vistafoundation.vlearning.quiz.dto.QuizDTO;
import co.vistafoundation.vlearning.quiz.dto.QuizQuestionDTO;
import co.vistafoundation.vlearning.quiz.dto.QuizScoreDTO;
import co.vistafoundation.vlearning.quiz.dto.QuizScoreResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentChapterQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentSubjectQuizAnalyticsDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentSubjectQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentVCTMarksDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectQuizRankingAndReviewDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectRankingDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectWiseReportCardDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectWiseReportDTO;
import co.vistafoundation.vlearning.quiz.dto.VCTQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.VCTQuizOverAllReviewDTO;
import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.BatchQuizAssignment;
import co.vistafoundation.vlearning.quiz.model.BatchStudentQuiz;
import co.vistafoundation.vlearning.quiz.model.ChatbotQA;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizAnswer;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuizQuestion;
import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuiz;
import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuizAnswer;
import co.vistafoundation.vlearning.quiz.model.StudentOfflineQuizQuestion;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuiz;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuizAnswer;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuizQuestion;
import co.vistafoundation.vlearning.quiz.repository.AnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.BatchQuizAssignmentRespository;
import co.vistafoundation.vlearning.quiz.repository.BatchStudentQuizRepository;
import co.vistafoundation.vlearning.quiz.repository.ChatbotRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizAnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentOfflineQuizAnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentOfflineQuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentOfflineQuizRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizAnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizRepository;
import co.vistafoundation.vlearning.quiz.service.QuizService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;

/**
 * 
 * @author Sajini
 *
 */
@Service
public class QuizserviceImpl implements QuizService {

	@Autowired
	QuizQuestionRepository quizQuestionRepository;

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	ProductGroupRepository productGroupRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	StudentSubjectQuizRepository studentSubjectQuizRepository;

	@Autowired
	BatchQuizAssignmentRespository batchQuizAssignmentRespository;

	@Autowired
	BatchStudentQuizRepository batchStudentQuizRepository;

	@Autowired
	StudentChapterQuizRepository studentChapterQuizRepository;

	@Autowired
	StudentSubjectQuizQuestionRepository studentSubjectQuizQuestionRepository;

	@Autowired
	StudentChapterQuizAnswerRepository studentChapterQuizAnswerRepository;

	@Autowired
	StudentChapterQuizQuestionRepository studentChapterQuizQuestionRepository;

	@Autowired
	StudentSubjectQuizAnswerRepository studentSubjectQuizAnswerRepository;

	@Autowired
	BatchQuizMetaRepository batchQuizMetaRepository;

	@Autowired
	BatchQuizQuestionRepository batchQuizQuestionRepository;

	@Autowired
	BatchStudentQuizQuestionRepository batchStudentQuizQuestionRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	BatchStudentRepository batchStudentRepository;

	@Autowired
	BatchQuizAnswerRepository batchQuizAnswerRepository;

	@Autowired
	StudentOfflineQuizRepository studentOfflineQuizRepository;

	@Autowired
	StudentOfflineQuizQuestionRepository studentOfflineQuizQuestionRepository;

	@Autowired
	StudentOfflineQuizAnswerRepository studentOfflineQuizAnswerRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageFileUploadService imageUploadService;

	@Autowired
	ChatbotRepository chatbotRepository;

	private static final Logger log = LoggerFactory.getLogger(QuizserviceImpl.class);

	private String startDate = "2023-02-03T00:00:00.197021300Z";

	private String endDate = "2023-02-13T16:00:00.197021300Z";

	@Value("${APP_ANGULAR_URL}")
	private String appAngularUrl;

	@Value("${VCT_AVAILABLE_DATE}")
	private String vctAvailableDate;

	@Value("${SHOW_VCT_RESULT}")
	private Boolean showVCTResult;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getQuestionsByQuizId(Long quizId) {

		Document result = new Document();
		try {
			List<QuizQuestion> questionList = quizQuestionRepository.findByQuiz_idQuizAndQuestionActiveFlag(quizId,
					Boolean.TRUE);

			if (questionList.size() != 0) {
				result.setData(questionList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document saveQuiz(QuizQuestion quizQuestion, Long quizId) {
		Document result = new Document();
		try {

			Quiz quiz = new Quiz();
			quiz = quizRepository.findByIdQuiz(quizId);

			if (quiz == null)
				throw new NullPointerException("No Quiz FOund !");

			if (quizQuestion.getQuestionType().equalsIgnoreCase("TRUE_OR_FALSE")) {
				quizQuestion.setQuestionType("TrueOrFalse");
			}

			quizQuestion.setQuiz((quiz));
			quizQuestion.setQuestionActiveFlag(true);

			QuizQuestion response = quizQuestionRepository.save(quizQuestion);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document updateQuizQuestion(Long quizQuestionId, QuizQuestion quizQuestion) {
		Document result = new Document();
		try {
			QuizQuestion question = quizQuestionRepository.findByIdQuizQuestionAndQuestionActiveFlag(quizQuestionId,
					Boolean.TRUE);
			if (question == null) {
				throw new NullPointerException("Invalid QuizQuestion Id");
			}
			if (quizQuestion.getQuiz() == null) {
				throw new NullPointerException("Quiz Data Cannot be Null");

			}
			QuizQuestion response = quizQuestionRepository.save(quizQuestion);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document readQuizQuestion(Long quizQuestionId) {

		Document result = new Document();
		try {
			QuizQuestion questions = quizQuestionRepository.findByIdQuizQuestionAndQuestionActiveFlag(quizQuestionId,
					Boolean.TRUE);
			if (questions != null) {
				result.setData(questions);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				throw new NullPointerException("Invalid QuizQuestion id");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document deleteQuizQuestion(Long idQuizQuestion) {
		Document result = new Document();
		try {
			QuizQuestion questions = quizQuestionRepository.findByIdQuizQuestionAndQuestionActiveFlag(idQuizQuestion,
					Boolean.TRUE);

			if (questions != null) {

				questions.setQuestionActiveFlag(false);

				QuizQuestion response = quizQuestionRepository.save(questions);
				result.setData(response);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage(" De-Activation Successfull");
			} else {
				throw new NullPointerException("Invalid QuizQuestion id");

			}

		} catch (Exception e) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Document checkAnswers(AnswerRequestDTO answerRequestDTO) {
		Document<Boolean> result = new Document<Boolean>();
		List<Answer> correctAnswer = null;
		try {
			int count = 0;
			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);
			if (questions != null) {
				correctAnswer = questions.getAnswers();
			} else {
				throw new NullPointerException("Invalid Quiz Question");
			}
			List<Answer> answers = answerRequestDTO.getAnswer();
			for (Answer answer : answers) {
				Answer temp = answerRepository.findByIdQuizQuestionAndFieldIdAndTextFieldValue(
						answerRequestDTO.getIdQuizQuestion(), answer.getFieldId(), answer.getTextFieldValue());
				if (temp != null) {
					count++;
				}

			}
			if (correctAnswer.size() == count) {
				result.setData(true);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document checkBatchAnswers(BatchQuizAnswerRequestDTO batchQuizAnswerRequestDTO) {
		Document result = new Document();
		List<BatchQuizAnwser> correctAnswer = null;
		try {
			int count = 0, count1 = 0, count2 = 0;
			int totalMultiCorrectAnswer = 0;
			BatchQuizQuestion questions = batchQuizQuestionRepository
					.findByIdBatchQuizQuestion(batchQuizAnswerRequestDTO.getIdBatchQuizQuestion());

			if (questions == null)
				throw new NullPointerException("No questions found!!");

			totalMultiCorrectAnswer = batchQuizAnswerRepository.countByIdBatchQuizQuestionAndCorrectValueFlag(
					batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), Boolean.TRUE);

			List<BatchQuizAnwser> answers = batchQuizAnswerRequestDTO.getBatchAnswer();
			for (BatchQuizAnwser answer : answers) {
				switch (questions.getQuestionType().toUpperCase()) {

				case "SUBJECTIVE": {
					correctAnswer = batchQuizAnswerRepository
							.findByIdBatchQuizQuestion(batchQuizAnswerRequestDTO.getIdBatchQuizQuestion());
					if (correctAnswer == null)
						throw new NullPointerException("No Correct ANswers !");
					BatchQuizAnwser temp = batchQuizAnswerRepository
							.findByIdBatchQuizQuestionAndFieldIdAndTextFieldValue(
									batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), answer.getFieldId(),
									answer.getTextFieldValue());
					if (temp != null) {
						count++;
					}
					break;
				}

				case "OBJECTIVE": {
					correctAnswer = batchQuizAnswerRepository.findByIdBatchQuizQuestionAndCorrectValueFlag(
							batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), Boolean.TRUE);

					if (correctAnswer == null)
						throw new NullPointerException("No Correct Answer Found !");
					BatchQuizAnwser temp1 = batchQuizAnswerRepository
							.findByIdBatchQuizQuestionAndFieldIdAndCorrectValueFlag(
									batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), answer.getFieldId(),
									Boolean.TRUE);
					if (temp1 != null) {
						count1++;
					}

					break;
				}
				case "MULTISELECT": {
					correctAnswer = batchQuizAnswerRepository.findByIdBatchQuizQuestionAndCorrectValueFlag(
							batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), Boolean.TRUE);

					if (correctAnswer == null)
						throw new NullPointerException("No Correct ANswer Found !");

					BatchQuizAnwser temp2 = batchQuizAnswerRepository
							.findByIdBatchQuizQuestionAndFieldIdAndCorrectValueFlag(
									batchQuizAnswerRequestDTO.getIdBatchQuizQuestion(), answer.getFieldId(),
									Boolean.TRUE);

					if (temp2 != null)
						count2++;

					break;
				}
				default: {

					break;
				}
				}
			}
			if (correctAnswer.size() == (count | count1 | count2)) {
				result.setData(true);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public Document<Boolean> checkObjectiveAnswers(AnswerRequestDTO answerRequestDTO) {
		Document<Boolean> result = new Document<Boolean>();
		List<Answer> correctAnswer = null;
		try {

			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);
			if (questions != null) {
				correctAnswer = questions.getAnswers();
			} else {
				throw new NullPointerException("Invalid Quiz Question");
			}
			List<Answer> answers = answerRequestDTO.getAnswer();

			for (Answer answer : answers) {
				System.out
						.println("Key Combinations:" + answerRequestDTO.getIdQuizQuestion() + "" + answer.getFieldId());
				Answer temp = answerRepository.findByIdQuizQuestionAndFieldIdAndCorrectValueFlag(
						answerRequestDTO.getIdQuizQuestion(), answer.getFieldId(), Boolean.TRUE);
				if (temp != null) {
					result.setData(true);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Successfull request");
					break;
				} else {
					result.setData(false);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Successfull request");
					break;

				}
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public Document checkMultipleAnswers(AnswerRequestDTO answerRequestDTO) {
		Document<Boolean> result = new Document<Boolean>();
		List<Answer> correctAnswer = null;
		int totalMultiCorrectAnswer = 0;
		int count = 0;
		try {

			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);
			if (questions == null)
				throw new NullPointerException("Invalid Quiz Question");

			List<Answer> answers = answerRequestDTO.getAnswer();

			correctAnswer = answerRepository
					.findByIdQuizQuestionAndCorrectValueFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);
			totalMultiCorrectAnswer = answerRepository
					.countByIdQuizQuestionAndCorrectValueFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);
			System.out.println("correct answer size" + correctAnswer);
			if (answers.size() != correctAnswer.size()) {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");

			} else {

				for (Answer answer : answers) {
					Answer temp = answerRepository.findByIdQuizQuestionAndFieldIdAndCorrectValueFlag(
							answerRequestDTO.getIdQuizQuestion(), answer.getFieldId(), Boolean.TRUE);

					if (temp != null)
						count++;

				}

				if (count == correctAnswer.size()) {
					result.setData(true);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Successfull request");

				} else {
					result.setData(false);
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Successfull request");

				}
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document createofflineQuiz(Long idClassStandard, Long idProductLine, Quiz quizs, Long idSyllabus,
			Long idState) {
		Document result = new Document();

		try {

			if (quizs == null)
				throw new NullPointerException("Quiz information cannot be null ");

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(
							idProductLine, idClassStandard, idSyllabus, idState, quizs.getIdSubject(), Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("Product is null");

			Quiz quiz = new Quiz();
			quiz.setIdProduct(product.getIdProduct());
			quiz.setIdSubject(quizs.getIdSubject());
			quiz.setIdSubjectChapter(quizs.getIdSubjectChapter());
			quiz.setQuizName(quizs.getQuizName());
			quiz.setIdOfflineVideoCourse(quizs.getIdOfflineVideoCourse());
			quiz = quizRepository.save(quiz);

			result.setData(quiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

			return result;

		} catch (Exception exp) {
			/**
			 * updated by @author Naveen Kumar
			 */
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage(
							"Quiz is already available for selected combination.Please use edit quiz screen to add more question to the selected combination.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAllQuestionsForSubject(Long idProduct, Long idSubject) {
		Document result = new Document();
		try {
			List<Quiz> quiz = quizRepository.findByIdProductAndIdSubject(idProduct, idSubject);
			if (quiz.isEmpty()) {
				throw new NullPointerException("Invalid ProductID or SubjectID");
			}
			Set<QuizQuestion> questions = new HashSet<QuizQuestion>();

			for (Quiz quizes : quiz) {

				List<QuizQuestion> questionslist = quizQuestionRepository.findByQuiz(quizes);

				if (questionslist.isEmpty())
					throw new NullPointerException("No Questions List found");

				questions.addAll(questionslist);

			}
			if (questions.isEmpty()) {
				throw new NullPointerException("No Questions Found for Respective Quiz");
			}

			questions.stream().forEach(q -> {
				q.getAnswers().stream().forEach(a -> {

					a.setCorrectValueFlag(false);
					if (q.getQuestionType().equalsIgnoreCase("SUBJECTIVE")) {
						a.setTextFieldValue(null);
						// q.setAnswerText(q.getAnswerText().replace(":text","____"));
					}

				});
			});

			result.setData(questions);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAllChapterwiseQuestions(Long idProduct, Long idSubject, Long idSubjectChapter) {
		Document result = new Document();
		try {
//			Quiz quiz = quizRepository.findByIdProductAndIdSubjectAndIdSubjectChapter(idProduct, idSubject,
//					idSubjectChapter);

//			if (quiz == null) {
//				throw new NullPointerException("Invalid ProductID or SubjectID or SubjectChapterID");
//
//			}
			List<QuizQuestion> questions = quizQuestionRepository.getByIdSubjectChapterAndIdSubject(idSubjectChapter,
					idSubject, Boolean.TRUE);
			if (questions.isEmpty()) {
				throw new NullPointerException("No Questions Found for Respective Quiz");
			}
			Set<QuizQuestion> questionset = new HashSet<QuizQuestion>();
			questionset.addAll(questions);

			questionset.stream().forEach(q -> {
				q.getAnswers().stream().forEach(a -> {

					a.setCorrectValueFlag(false);
					if (q.getQuestionType().equalsIgnoreCase("SUBJECTIVE")) {
						a.setTextFieldValue(null);
					}

				});
			});

			result.setData(questionset);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document submittingAnswers(StudentSubjectQuizDTO studentSubjectQuizDTO) {
		Document result = new Document();
		float quizScore = 0f;
		int noOfQuestions = 0;
		try {

			if (studentSubjectQuizDTO.getQuizQuestions().isEmpty())
				throw new AppException("Please answer all the question.");

			StudentSubjectQuiz studentSubjectQuiz = new StudentSubjectQuiz();
			studentSubjectQuiz.setIdStudentSubscr(studentSubjectQuizDTO.getIdStudentSubscr());
			studentSubjectQuiz.setIdSubject(studentSubjectQuizDTO.getIdSubject());

			studentSubjectQuiz.setQuizDate(Instant.now());
			studentSubjectQuiz.setIdQuiz(studentSubjectQuizDTO.getIdQuiz());
			List<StudentSubjectQuizQuestion> ssqqList = new ArrayList<StudentSubjectQuizQuestion>();

			List<QuizQuestion> quizQuestionList = studentSubjectQuizDTO.getQuizQuestions();

			for (QuizQuestion quizQuestion : quizQuestionList) {

				StudentSubjectQuizQuestion ssqq = new StudentSubjectQuizQuestion();

				ssqq.setIdQuizQuestion(quizQuestion.getIdQuizQuestion());
				boolean correctFlag;
				AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(quizQuestion.getIdQuizQuestion(),
						quizQuestion.getAnswers());

				QuizQuestion orginalQuestion = quizQuestionRepository
						.findByIdQuizQuestion(quizQuestion.getIdQuizQuestion());

				switch (orginalQuestion.getQuestionType().toUpperCase()) {

				case "SUBJECTIVE": {
					correctFlag = (boolean) this.checkAnswers(answerRequestDTO).getData();
					if (correctFlag) {
						ssqq.setScoredMarks("1");
						quizScore += 1;
					} else {
						ssqq.setScoredMarks("0");
					}

					break;
				}

				case "OBJECTIVE": {
					correctFlag = (boolean) this.checkObjectiveAnswers(answerRequestDTO).getData();
					if (correctFlag) {
						ssqq.setScoredMarks("1");
						quizScore += 1;
					} else {
						ssqq.setScoredMarks("0");
					}
					break;
				}
				case "MULTISELECT": {
					correctFlag = (boolean) this.checkMultipleAnswers(answerRequestDTO).getData();
					if (correctFlag) {
						ssqq.setScoredMarks("1");
						quizScore += 1;
					} else {
						ssqq.setScoredMarks("0");
					}
					break;
				}
				default: {
					correctFlag = false;
					ssqq.setScoredMarks("0");
					break;
				}

				}
				ssqq.setCorrectFlag(correctFlag);
				List<Answer> answerList = quizQuestion.getAnswers();
				List<StudentSubjectQuizAnswer> ssqaList = new ArrayList<StudentSubjectQuizAnswer>();

				for (Answer answer : answerList) {
					StudentSubjectQuizAnswer ssqa = new StudentSubjectQuizAnswer();

					ssqa.setFieldId(answer.getFieldId());
					ssqa.setTextFieldValue(answer.getTextFieldValue());
					ssqaList.add(ssqa);
				}

				ssqq.setStudentSubjectQuizAnswers(ssqaList);
				ssqqList.add(ssqq);
				noOfQuestions++;
			}

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("totalQuestions", noOfQuestions);
			response.put("totalCorrectAnswers", (int) quizScore);

			studentSubjectQuiz.setStudentSubjectQuizQuestion(ssqqList);

			quizScore = (quizScore / noOfQuestions) * 100;

			response.put("percentage", Math.round(quizScore * 100.0 / 100));
			studentSubjectQuiz.setQuizScore(quizScore);
			studentSubjectQuiz.setTotalMarksScored(((Float) quizScore).toString());
			StudentSubjectQuiz temp = studentSubjectQuizRepository.save(studentSubjectQuiz);

			response.put("studentSubjectQuiz", temp);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document submittingChapterwiseAnswers(StudentChapterQuizDTO studentChapterQuizDTO) {

		Document result = new Document();
		try {
			float quizScore = 0f;
			int noOfQuestions = 0;

			StudentChapterQuiz studentChapterQuiz = new StudentChapterQuiz();
			studentChapterQuiz.setIdSubjectChapter(studentChapterQuizDTO.getIdSubjectChapter());
			studentChapterQuiz.setIdStudentSubscr(studentChapterQuizDTO.getIdStudentSubscr());
			studentChapterQuiz.setQuizDate(Instant.now());

			List<StudentChapterQuizQuestion> scqqList = new ArrayList<StudentChapterQuizQuestion>();

			List<QuizQuestion> questionsList = studentChapterQuizDTO.getQuizQuestions();

			for (QuizQuestion quizQuestion : questionsList) {
				StudentChapterQuizQuestion scqq = new StudentChapterQuizQuestion();
				scqq.setIdQuizQuestion(quizQuestion.getIdQuizQuestion());

				boolean correctFlag;

				AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(quizQuestion.getIdQuizQuestion(),
						quizQuestion.getAnswers());

				QuizQuestion originalQuestion = quizQuestionRepository
						.findByIdQuizQuestion(quizQuestion.getIdQuizQuestion());

				if (originalQuestion == null)
					throw new NullPointerException("No originalQuestion found!");

				switch (originalQuestion.getQuestionType().toUpperCase()) {
				case "SUBJECTIVE": {
					Document<Boolean> temp = this.checkAnswers(answerRequestDTO);
					if (temp.getData() == null)
						throw new NullPointerException(temp.getMessage());

					correctFlag = temp.getData();
					if (correctFlag)
						quizScore += 1;

					break;
				}

				case "OBJECTIVE": {
					Document<Boolean> temp = this.checkObjectiveAnswers(answerRequestDTO);

					if (temp.getData() == null)
						throw new NullPointerException(temp.getMessage());

					correctFlag = temp.getData();

					if (correctFlag)
						quizScore += 1;

					break;
				}
				case "MULTISELECT": {
					Document<Boolean> temp = this.checkMultipleAnswers(answerRequestDTO);
					if (temp.getData() == null)
						throw new NullPointerException(temp.getMessage());

					correctFlag = temp.getData();
					if (correctFlag)
						quizScore += 1;

					break;
				}

				default:
					// if no choices matched, by default answer will be wrong.
					correctFlag = false;
					break;

				}

				scqq.setCorrectFlag(correctFlag);

				List<Answer> ansewersList = quizQuestion.getAnswers();

				List<StudentChapterQuizAnswer> scqaList = new ArrayList<StudentChapterQuizAnswer>();

				for (Answer answer : ansewersList) {
					StudentChapterQuizAnswer scqa = new StudentChapterQuizAnswer();
					scqa.setFieldId(answer.getFieldId());
					scqa.setTextFieldValue(answer.getTextFieldValue());

					scqaList.add(scqa);

				}
				scqq.setStudentChapterQuizAnswer(scqaList);
				scqqList.add(scqq);
				noOfQuestions++;
			}

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("totalQuestions", noOfQuestions);
			response.put("totalCorrectAnswers", (int) quizScore);

			studentChapterQuiz.setStudentChapterQuizQuestion(scqqList);
			quizScore = (quizScore / noOfQuestions) * 100;

			response.put("percentage", Math.round(quizScore * 100.0 / 100));
			studentChapterQuiz.setQuizScore(quizScore);
			StudentChapterQuiz temp = studentChapterQuizRepository.save(studentChapterQuiz);
			response.put("studentChapterQuiz", temp);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public Document assignQuiz(BatchQuizAssignment batchQuizAssignment) {
		Document result = new Document();
		try {

			if (batchQuizAssignment != null) {

				/**
				 * 
				 * List<BatchQuizAssignment> quizAssignments = batchQuizAssignmentRespository
				 * .findByIdBatchAndIdBatchQuizMeta(batchQuizAssignment.getIdBatch(),
				 * batchQuizAssignment.getIdBatchQuizMeta());
				 * 
				 * if (!quizAssignments.isEmpty()) { throw new NullPointerException("Already
				 * assigned this Quiz to the Batch"); }
				 * 
				 **/

				BatchQuizAssignment quizAssignment = new BatchQuizAssignment();

				quizAssignment.setIdBatch(batchQuizAssignment.getIdBatch());
				quizAssignment.setIdBatchQuizMeta(batchQuizAssignment.getIdBatchQuizMeta());
				quizAssignment.setQuizDate(batchQuizAssignment.getQuizDate());
				quizAssignment.setBatchQuizName(batchQuizAssignment.getBatchQuizName());

				BatchQuizAssignment res = batchQuizAssignmentRespository.save(quizAssignment);

				List<StudentSubscription> list = studentSubscriptionRepository
						.findByIdBatch(batchQuizAssignment.getIdBatch());

				for (StudentSubscription sub : list) {
					BatchStudentQuiz batchStudentQuiz = new BatchStudentQuiz();
					batchStudentQuiz.setQuizCompleteFlag(false);
					batchStudentQuiz.setIdBatchQuizAssignment(res.getIdBatchQuizAssignment());
					batchStudentQuiz.setIdStudentSubscription(sub.getIdStudentSubscription());

					BatchStudentQuiz resul = batchStudentQuizRepository.save(batchStudentQuiz);
				}

				result.setData(res);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Quiz Assigned Successfully");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getQuizDetails(Long idBatch, Long idStudentSubscription) {
		Document result = new Document();
		try {
			// List<BatchQuizAssignment> quizAssignment =
			// batchQuizAssignmentRespository.findByIdBatch(idBatch);
			BatchStudentQuiz studentQuiz = batchStudentQuizRepository
					.findByIdStudentSubscription(idStudentSubscription);
			/*
			 * if (quizAssignment.isEmpty()) { throw new
			 * NullPointerException("Quiz not Assigned"); }
			 */
			if (studentQuiz == null) {
				throw new NullPointerException("Quiz not Attempted");
			}
			result.setData(studentQuiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getStudentSubjectWiseScore(Long idSubject, Long idStudentSubscription) {
		Document result = new Document();
		try {
			List<StudentSubjectQuiz> studentSubjectQuiz = studentSubjectQuizRepository
					.findByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(idStudentSubscription, idSubject);
			result.setData(studentSubjectQuiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Student SubjectWise Score Details");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<List<StudentSubjectQuiz>> getStudentSubjectWiseScoreForAnalytics(Long idSubject,
			Long idStudentSubscription) {
		Document<List<StudentSubjectQuiz>> result = new Document<List<StudentSubjectQuiz>>();
		try {
			List<StudentSubjectQuiz> studentSubjectQuiz = studentSubjectQuizRepository
					.findByIdStudentSubscrAndIdSubjectOrderByIdStudentSubjectQuizAsc(idStudentSubscription, idSubject);

			if (studentSubjectQuiz.isEmpty()) {
				throw new NullPointerException("No Student Subject Quiz");
			}
			if (studentSubjectQuiz.size() > 10) {
				studentSubjectQuiz = studentSubjectQuiz.subList(studentSubjectQuiz.size() - 10,
						studentSubjectQuiz.size());
			}

			result.setData(studentSubjectQuiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Student SubjectWise Score Details");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getStudentChapterWiseScore(Long idChapter, Long idStudentSubscription) {
		Document result = new Document();
		try {
			List<StudentChapterQuiz> studentChapterQuiz = studentChapterQuizRepository
					.findByIdStudentSubscrAndIdSubjectChapterOrderByQuizDateDesc(idStudentSubscription, idChapter);
			if (studentChapterQuiz.isEmpty()) {
				throw new NullPointerException("No Student Chapter Quiz");
			}
			result.setData(studentChapterQuiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Student Chapterwise Score Details");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getBatchQuizDetails(Long idBatchQuizAssignment, Long idStudentSubscription) {
		Document result = new Document();
		try {
			List<BatchStudentQuiz> batchStudentQuiz = batchStudentQuizRepository
					.findByIdStudentSubscriptionAndIdBatchQuizAssignment(idStudentSubscription, idBatchQuizAssignment);
			if (batchStudentQuiz.isEmpty()) {
				throw new NullPointerException("No Student Batch Quiz");
			}
			result.setData(batchStudentQuiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Student Batch Quiz Details");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getQuizReview(Long idStudentSubjectQuiz) {
		Document result = new Document();
		List<QuizQuestionDTO> quizQuestionDTOList = new ArrayList<QuizQuestionDTO>();
		Map<String, Object> map = new HashMap<>();
		try {
			List<StudentSubjectQuizQuestion> studentSubjectQuizQuestionList = studentSubjectQuizQuestionRepository
					.findByIdStudentSubjectQuiz(idStudentSubjectQuiz);

			if (studentSubjectQuizQuestionList == null)
				throw new NullPointerException("No studentSubjectQuizQuestionList found!");

			StudentSubjectQuiz studentSubjectQuiz = studentSubjectQuizRepository
					.findByIdStudentSubjectQuiz(idStudentSubjectQuiz);
			if (studentSubjectQuiz == null)
				throw new NullPointerException("No studentSubjectQuiz found !");

			for (StudentSubjectQuizQuestion studentSubjectQuizQuestion : studentSubjectQuizQuestionList) {
				QuizQuestionDTO quizQuestionDTO = new QuizQuestionDTO();
				QuizQuestion quizQuestion = quizQuestionRepository
						.findByIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());

				if (quizQuestion == null)
					throw new NullPointerException("No quizQuestion found!");

				List<StudentSubjectQuizAnswer> studentSubjectQuizAnswer = studentSubjectQuizAnswerRepository
						.findByIdStudentSubjectQuizQuestion(
								studentSubjectQuizQuestion.getIdStudentSubjectQuizQuestion());
				quizQuestionDTO.setIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());
				quizQuestionDTO.setQuestionText(quizQuestion.getQuestionText());
				quizQuestionDTO.setQuestionType(quizQuestion.getQuestionType());
				quizQuestionDTO.setCorrectValueFlag(studentSubjectQuizQuestion.getCorrectFlag());

				List<Answer> answers = new ArrayList<Answer>();
				quizQuestion.getAnswers().stream().forEach(a -> {
					Answer answer = new Answer();
					answer.setFieldId(a.getFieldId());
					answer.setTextFieldValue(a.getTextFieldValue());
					answer.setCorrectValueFlag(a.getCorrectValueFlag());
					answers.add(answer);
				});
				quizQuestionDTO.setCorrectAnswer(answers);
				List<StudentSubjectQuizAnswer> studentAnswers = new ArrayList<StudentSubjectQuizAnswer>();
				studentSubjectQuizAnswer.stream().forEach(an -> {
					StudentSubjectQuizAnswer studentAnswer = new StudentSubjectQuizAnswer();
					studentAnswer.setFieldId(an.getFieldId());
					studentAnswer.setTextFieldValue(an.getTextFieldValue());
					studentAnswers.add(studentAnswer);
				});
				quizQuestionDTO.setStudentAnswer(studentAnswers);
				quizQuestionDTOList.add(quizQuestionDTO);
			}
			map.put("noOfQuestions", studentSubjectQuizQuestionList.size());
			map.put("correctAnswers",
					studentSubjectQuizQuestionList.stream().filter(a -> a.getCorrectFlag() == true).count());
			map.put("percentage", studentSubjectQuiz.getQuizScore());
			map.put("reviewData", quizQuestionDTOList);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Review Quiz Details Successfully");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getBatchQuestionsByBatchQuizId(Long batchQuizId) {

		Document result = new Document();
		try {
			List<BatchQuizQuestion> questionList = batchQuizQuestionRepository
					.findByBatchQuizMeta_IdBatchQuizMeta(batchQuizId);

			if (!questionList.isEmpty()) {
				result.setData(questionList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				throw new NullPointerException("Questions not found");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document deleteBatchQuizQuestion(Long quizQuestionId) {
		Document result = new Document();
		try {
			BatchQuizQuestion batchQuizQuestion = batchQuizQuestionRepository.findByIdBatchQuizQuestion(quizQuestionId);

			if (batchQuizQuestion != null) {
				batchQuizQuestionRepository.deleteById(quizQuestionId);
				result.setData("QuizQuestionId: " + quizQuestionId + " Successfully deleted");
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				throw new NullPointerException("Invalid QuizQuestion");

			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	/* Adding Batch Quiz */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document saveBatchQuizQuestions(BatchQuizQuestion batchQuizQuestion, Long batchQuizId) {
		Document result = new Document();
		try {

			BatchQuizMeta batchQuizMeta = new BatchQuizMeta();
			batchQuizMeta = batchQuizMetaRepository.findByIdBatchQuizMeta(batchQuizId);

			if (batchQuizMeta == null) {
				throw new NullPointerException("Batch Quiz not added");
			}

			batchQuizQuestion.setBatchQuizMeta(batchQuizMeta);

			BatchQuizQuestion response = batchQuizQuestionRepository.save(batchQuizQuestion);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Document saveBatchQuiz(BatchQuizMeta batchQuizMetaBody) {
		Document result = new Document();
		try {

			if (batchQuizMetaBody == null) {
				throw new NullPointerException("Data cannot be null");
			}

			BatchQuizMeta batchQuizMeta = new BatchQuizMeta();
			batchQuizMeta = batchQuizMetaRepository.save(batchQuizMetaBody);
			result.setData(batchQuizMeta);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document fetchBatchQuizListByIdTeacher(Long idTeacher) {

		Document doc = new Document<>();
		try {

			List<BatchQuizMeta> batchQuizMeta = batchQuizMetaRepository.findByIdTeacher(idTeacher);

			if (batchQuizMeta == null)
				throw new NullPointerException("No teacher found!");

			doc.setData(batchQuizMeta);
			doc.setMessage("Student Lists");
			doc.setStatusCode(200);
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getChapterWiseQuizReview(Long idStudentChapterQuiz) {
		Document result = new Document();
		List<ChapterQuizReviewDTO> quizQuestionDTOList = new ArrayList<ChapterQuizReviewDTO>();
		Map<String, Object> map = new HashMap<>();
		try {
			List<StudentChapterQuizQuestion> studentChapterQuizQuestionList = studentChapterQuizQuestionRepository
					.findByIdStudentChapterQuiz(idStudentChapterQuiz);
			if (studentChapterQuizQuestionList.isEmpty())
				throw new NullPointerException("No studentChapterQuizQuestionList found!");

			StudentChapterQuiz studentChapterQuiz = studentChapterQuizRepository
					.findByIdStudentChapterQuiz(idStudentChapterQuiz);

			if (studentChapterQuiz == null)
				throw new NullPointerException("No studentChapterQuiz found!");
			for (StudentChapterQuizQuestion studentChapterQuizQuestion : studentChapterQuizQuestionList) {
				ChapterQuizReviewDTO quizQuestionDTO = new ChapterQuizReviewDTO();
				QuizQuestion quizQuestion = quizQuestionRepository
						.findByIdQuizQuestion(studentChapterQuizQuestion.getIdQuizQuestion());

				if (quizQuestion == null)
					throw new NullPointerException("No Questions found !");
				List<StudentChapterQuizAnswer> studentSubjectQuizAnswer = studentChapterQuizAnswerRepository
						.findByIdStudentChapterQuizQuestion(
								studentChapterQuizQuestion.getIdStudentChapterQuizQuestion());
				if (studentSubjectQuizAnswer.isEmpty())
					throw new NullPointerException("No studentSubjectQuizAnswer found");
				quizQuestionDTO.setIdQuizQuestion(studentChapterQuizQuestion.getIdQuizQuestion());
				quizQuestionDTO.setQuestionText(quizQuestion.getQuestionText());
				quizQuestionDTO.setQuestionType(quizQuestion.getQuestionType());
				quizQuestionDTO.setCorrectValueFlag(studentChapterQuizQuestion.isCorrectFlag());

				List<Answer> answers = new ArrayList<Answer>();
				quizQuestion.getAnswers().stream().forEach(a -> {
					Answer answer = new Answer();
					answer.setFieldId(a.getFieldId());
					answer.setTextFieldValue(a.getTextFieldValue());
					answer.setCorrectValueFlag(a.getCorrectValueFlag());
					answers.add(answer);
				});
				quizQuestionDTO.setCorrectAnswer(answers);
				List<StudentChapterQuizAnswer> studentAnswers = new ArrayList<StudentChapterQuizAnswer>();
				studentSubjectQuizAnswer.stream().forEach(an -> {
					StudentChapterQuizAnswer studentAnswer = new StudentChapterQuizAnswer();
					studentAnswer.setFieldId(an.getFieldId());
					studentAnswer.setTextFieldValue(an.getTextFieldValue());
					studentAnswers.add(studentAnswer);
				});
				quizQuestionDTO.setStudentAnswer(studentAnswers);
				quizQuestionDTOList.add(quizQuestionDTO);
			}
			map.put("noOfQuestions", studentChapterQuizQuestionList.size());
			map.put("correctAnswers",
					studentChapterQuizQuestionList.stream().filter(ans -> ans.isCorrectFlag() == true).count());
			map.put("percentage", studentChapterQuiz.getQuizScore());
			map.put("reviewData", quizQuestionDTOList);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved ChapterWise Review Quiz Details Successfully");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getQuestionsByBatchQuizId(Long idBatchStudentAssignment) {
		Document result = new Document();
		try {
			BatchQuizAssignment batchStudentAssignment = batchQuizAssignmentRespository
					.findByIdBatchQuizAssignment(idBatchStudentAssignment);
			if (batchStudentAssignment == null)
				throw new NullPointerException("No Batch Assignment found");
			List<BatchQuizQuestion> questionList = batchQuizQuestionRepository
					.findByBatchQuizMeta_IdBatchQuizMeta(batchStudentAssignment.getIdBatchQuizMeta());

			if (!questionList.isEmpty()) {
				result.setData(questionList);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				throw new NullPointerException("Invalid Batch");
			}
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document batchQuizSubmittingAnswers(@Valid BatchStudentQuizDTO batchStudentQuizDTO) {

		Document result = new Document();
		float quizScore = 0f;
		int noOfQuestions = 0;

		try {
			BatchStudentQuiz batchStudentQuiz = batchStudentQuizRepository
					.findByIdBatchStudentQuiz(batchStudentQuizDTO.getIdBatchStudentQuiz());
			if (batchStudentQuiz == null)
				throw new NullPointerException("No Batch Quiz found ");

			batchStudentQuiz.setAttemptDate(Instant.now());

			List<BatchStudentQuizQuestion> BatchStudentQuizQuestionList = new ArrayList<BatchStudentQuizQuestion>();

			List<BatchQuizQuestion> batchQuizQuestionList = batchStudentQuizDTO.getBatchQuizQuestions();

			for (BatchQuizQuestion quizQuestion : batchQuizQuestionList) {

				BatchStudentQuizQuestion batchStudentQuizQuestion = new BatchStudentQuizQuestion();
				boolean correctFlag;

				batchStudentQuizQuestion.setIdBatchQuizQuestion(quizQuestion.getIdBatchQuizQuestion());
				batchStudentQuizQuestion.setIdBatchStudentQuiz(batchStudentQuizDTO.getIdBatchStudentQuiz());
				BatchQuizAnswerRequestDTO answerRequestDTO = new BatchQuizAnswerRequestDTO(
						quizQuestion.getIdBatchQuizQuestion(), quizQuestion.getBatchanswers());

				BatchQuizQuestion orginalQuestion = batchQuizQuestionRepository
						.findByIdBatchQuizQuestion(quizQuestion.getIdBatchQuizQuestion());
				if (orginalQuestion == null)
					throw new NullPointerException("No orginalQuestion found");

				switch (orginalQuestion.getQuestionType().toUpperCase()) {

				case "SUBJECTIVE": {
					correctFlag = (boolean) this.checkBatchAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;
					break;
				}

				case "OBJECTIVE": {
					correctFlag = (boolean) this.checkBatchAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;
					break;
				}
				case "MULTISELECT": {
					correctFlag = (boolean) this.checkBatchAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;
					break;
				}
				default: {
					correctFlag = false;
					break;
				}

				}
				batchStudentQuizQuestion.setCorrectFlag(correctFlag);

				List<BatchQuizAnwser> answerList = quizQuestion.getBatchanswers();
				List<BatchStudentQuizAnswer> batchStudentQuizAnswerList = new ArrayList<BatchStudentQuizAnswer>();

				for (BatchQuizAnwser answer : answerList) {
					BatchStudentQuizAnswer batchStudentQuizAnswer = new BatchStudentQuizAnswer();

					batchStudentQuizAnswer.setFieldId(answer.getFieldId());
					batchStudentQuizAnswer.setTextFieldValue(answer.getTextFieldValue());
					batchStudentQuizAnswerList.add(batchStudentQuizAnswer);

				}
				batchStudentQuizQuestion.setBatchStudentQuizAnswer(batchStudentQuizAnswerList);

				@SuppressWarnings("unused")
				BatchStudentQuizQuestion res = batchStudentQuizQuestionRepository.save(batchStudentQuizQuestion);

				BatchStudentQuizQuestionList.add(batchStudentQuizQuestion);
				noOfQuestions++;
			}

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("totalQuestions", noOfQuestions);
			quizScore = (quizScore / noOfQuestions) * 100;
			response.put("percentage", Math.round(quizScore * 100.0 / 100));
			response.put("totalCorrectAnswers", (int) quizScore);

			batchStudentQuiz.setScore(quizScore);
			batchStudentQuiz.setQuizCompleteFlag(true);
			BatchStudentQuiz temp = batchStudentQuizRepository.save(batchStudentQuiz);

			response.put("batchStudentQuiz", temp);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Batch Answer Submitted Successfully");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document getBatchQuizDetailsByIdBatch(Long idBatch) {
		Document result = new Document();
		List<BatchQuizDetailsDTO> quizDetailsList = new ArrayList<BatchQuizDetailsDTO>();

		try {

			List<BatchQuizAssignment> quizAssignmentList = batchQuizAssignmentRespository.findByIdBatch(idBatch);

			if (!quizAssignmentList.isEmpty()) {
				for (BatchQuizAssignment quizAssignment : quizAssignmentList) {

					BatchQuizDetailsDTO dto = new BatchQuizDetailsDTO();

					BatchQuizMeta quizMeta = batchQuizMetaRepository
							.findByIdBatchQuizMeta(quizAssignment.getIdBatchQuizMeta());
					if (quizMeta == null)
						throw new NullPointerException("Batch Quiz not added");

					dto.setQuizDate(quizAssignment.getQuizDate());
					dto.setBatchQuizName(quizAssignment.getBatchQuizName());

					Subject subject = subjectRepository.findByIdSubject(quizMeta.getIdSubject());

					if (subject != null) {
						dto.setSubject(subject.getSubjectName());
						ClassStandard classStandard = classRepository
								.findByIdClassStandard(quizMeta.getIdClassStandard());
						if (classStandard != null) {
							dto.setClassStandard(classStandard.getClassStandadName());
						}
					}
					Batch batch = batchRepository.findByIdBatch(quizAssignment.getIdBatch());

					if (batch == null)
						throw new NullPointerException("No batch found!");

					int count = batchStudentQuizRepository
							.getStudentQuizAttemptedCount(quizAssignment.getIdBatchQuizAssignment());

					// dto.setTotalNoOfStudent(batch.getCurrentOccupancy());
					dto.setStudentAttempted(count);

					// System.out.println("BatchQuiz IdBatchQuizAssignment
					// "+quizAssignment.getIdBatchQuizAssignment());
					List<BatchStudentQuiz> batchStudentQuizList = batchStudentQuizRepository
							.findByIdBatchQuizAssignment(quizAssignment.getIdBatchQuizAssignment());

					if (!batchStudentQuizList.isEmpty()) {
						List<QuizScoreDTO> scoreList = new ArrayList<QuizScoreDTO>();
						for (BatchStudentQuiz batchStudentQuiz : batchStudentQuizList) {

							// System.out.println("studentQuiz IdBatchQuizAssignment
							// "+batchStudentQuiz.getIdBatchQuizAssignment());
							QuizScoreDTO scoreDto = new QuizScoreDTO();

							StudentSubscription subscription = studentSubscriptionRepository
									.findByIdStudentSubscription(batchStudentQuiz.getIdStudentSubscription());
							if (subscription == null)
								throw new NullPointerException("No Student Subscription found");

							Student student = studentRepository.findByIdStudent(subscription.getIdStudent());
							if (student == null)
								throw new NullPointerException("No Student Found");

							User user = student.getUser();
							String studentName = user.getFirstName() + " " + user.getLastName();
							scoreDto.setStudentName(studentName);
							scoreDto.setScore(batchStudentQuiz.getScore());
							scoreDto.setQuizCompleteFlag(batchStudentQuiz.getQuizCompleteFlag());
							scoreDto.setIdBatchStudentQuiz(batchStudentQuiz.getIdBatchStudentQuiz());
							scoreDto.setAttemptDate(batchStudentQuiz.getAttemptDate());
							scoreDto.setIdStudentSubscription(batchStudentQuiz.getIdStudentSubscription());

							scoreList.add(scoreDto);
						}
						// System.out.println("scoreList size "+scoreList.size());
						dto.setTotalNoOfStudent(scoreList.size());
						dto.setQuizScoreList(scoreList);
					}

					quizDetailsList.add(dto);
				}
			}
			result.setData(quizDetailsList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Batch Quiz Details Successfully");

		} catch (Exception e) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getBatchQuizResult(Long idBatch, Long idStudentSubscription) {

		Document result = new Document();
		List<BatchQuizResultDTO> batchQuizResultList = new ArrayList<BatchQuizResultDTO>();
		String studentName = "";

		try {
			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByIdStudentSubscription(idStudentSubscription);

			if (studentSubscription != null) {
				Student student = studentRepository.findByIdStudent(studentSubscription.getIdStudent());
				if (student != null) {
					User user = student.getUser();
					studentName = user.getFirstName() + " " + user.getLastName();
				}
			}
			List<BatchQuizAssignment> quizAssignmentList = batchQuizAssignmentRespository.findByIdBatch(idBatch);

			if (!quizAssignmentList.isEmpty()) {
				for (BatchQuizAssignment quizAssignment : quizAssignmentList) {

					Long batchQuizAssignmentId = quizAssignment.getIdBatchQuizAssignment();
					List<BatchStudentQuiz> batchStudentQuizList = batchStudentQuizRepository
							.findByIdStudentSubscriptionAndIdBatchQuizAssignment(idStudentSubscription,
									batchQuizAssignmentId);

					if (!batchStudentQuizList.isEmpty()) {
						for (BatchStudentQuiz batchStudentQuiz : batchStudentQuizList) {
							BatchQuizResultDTO dto = new BatchQuizResultDTO();
							dto.setStudentName(studentName);
							dto.setBatchQuizName(quizAssignment.getBatchQuizName());
							dto.setAttemptDate(batchStudentQuiz.getAttemptDate());
							dto.setScore(batchStudentQuiz.getScore());
							dto.setQuizCompleteFlag(batchStudentQuiz.getQuizCompleteFlag());
							dto.setIdBatchStudentQuiz(batchStudentQuiz.getIdBatchStudentQuiz());

							batchQuizResultList.add(dto);
						}
					}
				}
			}
			result.setData(batchQuizResultList);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Batch Quiz Result Successfully");
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getAcademicQuizDetails(Long idClassStandard, Long idProductLine, Long idSubject,
			Long idSubjectChapter, Long idSyllabus, Long idState, Long idOfflineVideoCourse) {
		Document result = new Document();
		QuizDTO quizDto = new QuizDTO();
		try {

			Product product = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(
							idProductLine, idClassStandard, idSyllabus, idState, idSubject, Boolean.TRUE);

			if (product == null)

				throw new NullPointerException("No Products founds!");

			Quiz quiz = quizRepository.findFirstByIdProductAndIdSubjectAndIdSubjectChapter(product.getIdProduct(),
					idSubject, idSubjectChapter);
			System.out.println("Quiz Details " + quiz);

			if (quiz != null) {
				quizDto.setIdQuiz(quiz.getIdQuiz());
				quizDto.setIdProduct(quiz.getIdProduct());
				quizDto.setIdSubject(quiz.getIdSubject());
				quizDto.setIdSubjectChapter(quiz.getIdSubjectChapter());
				quizDto.setQuizName(quiz.getQuizName());

				List<QuizQuestion> questionList = quizQuestionRepository.findByProductAndSubjectAndSubjectChapter(
						product.getIdProduct(), idSubject, idSubjectChapter, Boolean.TRUE);

				quizDto.setQuizQuestionList(questionList);
			}

			result.setData(quizDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Quiz Details Successfully");
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
	public Document getAllOfflineQuizQuestions(Long idSubject, Long idSubjectChapter, Long idOfflineVideoCourse) {
		Document result = new Document();
		try {
			Quiz quiz = quizRepository.findByIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourse(idSubject,
					idSubjectChapter, idOfflineVideoCourse);

			if (quiz == null) {
				throw new NullPointerException("No Quiz Details Found!! ");
			}

			List<QuizQuestion> questions = quizQuestionRepository.findByQuizAndQuestionActiveFlag(quiz, Boolean.TRUE);
			if (questions.isEmpty()) {
				throw new NullPointerException("No Questions Found for Respective Quiz");
			}

			questions.stream().forEach(q -> {
				q.getAnswers().stream().forEach(a -> {

					a.setCorrectValueFlag(false);
					if (q.getQuestionType().equalsIgnoreCase("SUBJECTIVE")) {
						a.setTextFieldValue(null);
					}

				});
			});

			result.setData(questions);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document submittingOfflineQuizAnswers(StudentOfflineQuizDTO studentOfflineQuizDTO) {
		Document result = new Document();
		try {
			float quizScore = 0f;
			int noOfQuestions = 0;
			// Instant quizDate, Float quizScore, Long idSubjectChapter, Long
			// idStudentSubscr,
			// Long idOfflineVideoCourse, List<StudentOfflineQuizQuestion>
			// studentOfflineQuizQuestion
			StudentOfflineQuiz studentOfflineQuiz = new StudentOfflineQuiz();
			studentOfflineQuiz.setQuizDate(Instant.now());
			studentOfflineQuiz.setIdSubject(studentOfflineQuizDTO.getIdSubject());
			studentOfflineQuiz.setIdSubjectChapter(studentOfflineQuizDTO.getIdSubjectChapter());
			studentOfflineQuiz.setIdStudentSubscr(studentOfflineQuizDTO.getIdStudentSubscr());
			studentOfflineQuiz.setIdOfflineVideoCourse(studentOfflineQuizDTO.getIdOfflineVideoCourse());

			List<StudentOfflineQuizQuestion> quizQuestionList = new ArrayList<StudentOfflineQuizQuestion>();

			List<QuizQuestion> questionsList = studentOfflineQuizDTO.getQuizQuestions();

			for (QuizQuestion question : questionsList) {

				StudentOfflineQuizQuestion quizQuestion = new StudentOfflineQuizQuestion();
				quizQuestion.setIdQuizQuestion(question.getIdQuizQuestion());

				StudentChapterQuizQuestion scqq = new StudentChapterQuizQuestion();
				scqq.setIdQuizQuestion(quizQuestion.getIdQuizQuestion());

				boolean correctFlag;

				AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(quizQuestion.getIdQuizQuestion(),
						question.getAnswers());

				QuizQuestion originalQuestion = quizQuestionRepository
						.findByIdQuizQuestion(question.getIdQuizQuestion());
				if (originalQuestion == null)
					throw new NullPointerException("No originalQuestion found");

				switch (originalQuestion.getQuestionType().toUpperCase()) {
				case "SUBJECTIVE": {
					correctFlag = (boolean) this.checkAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;

					break;
				}

				case "OBJECTIVE": {
					correctFlag = (boolean) this.checkObjectiveAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;

					break;
				}
				case "MULTISELECT": {
					correctFlag = (boolean) this.checkMultipleAnswers(answerRequestDTO).getData();
					if (correctFlag)
						quizScore += 1;

					break;
				}

				default:
					// if no choices matched, by default answer will be wrong.
					correctFlag = false;
					break;

				}
				quizQuestion.setCorrectFlag(correctFlag);

				List<Answer> ansewersList = question.getAnswers();

				List<StudentOfflineQuizAnswer> quizAnswerList = new ArrayList<StudentOfflineQuizAnswer>();

				for (Answer answer : ansewersList) {
					StudentOfflineQuizAnswer quizAnswer = new StudentOfflineQuizAnswer();
					quizAnswer.setFieldId(Long.valueOf(answer.getFieldId()));
					quizAnswer.setTextFieldValue(answer.getTextFieldValue());
					// quizAnswer.setIdStudentOfflineQuizQuestion(idStudentOfflineQuizQuestion);

					quizAnswerList.add(quizAnswer);

				}
				quizQuestion.setStudentOfflineQuizAnswer(quizAnswerList);
				quizQuestionList.add(quizQuestion);
				noOfQuestions++;
			}
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("totalQuestions", noOfQuestions);
			response.put("totalCorrectAnswers", (int) quizScore);
			quizScore = (quizScore / noOfQuestions) * 100;
			response.put("percentage", Math.round(quizScore * 100.0 / 100));
			studentOfflineQuiz.setStudentOfflineQuizQuestion(quizQuestionList);
			studentOfflineQuiz.setQuizScore(quizScore);

			StudentOfflineQuiz temp = studentOfflineQuizRepository.save(studentOfflineQuiz);
			response.put("studentOfflineQuiz", temp);
			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<List<StudentOfflineQuizResponseDTO>> getStudentOfflineSubjectWiseScore(Long idSubject,
			Long idChapter, Long idOfflineVideoCourse, Long IdStudentSub) {
		Document<List<StudentOfflineQuizResponseDTO>> result = new Document<>();
		try {
			List<StudentOfflineQuizResponseDTO> temp = new ArrayList<>();

			List<StudentOfflineQuiz> studentSubjectQuiz = studentOfflineQuizRepository
					.findByIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourseAndIdStudentSubscrOrderByQuizDateDesc(
							idSubject, idChapter, idOfflineVideoCourse, IdStudentSub);

			if (!studentSubjectQuiz.isEmpty()) {
				studentSubjectQuiz.stream().forEach(s -> {

					StudentOfflineQuizResponseDTO sfqr = new StudentOfflineQuizResponseDTO();

					OfflineVideoCourse ofv = offlineVideoCourseRepository
							.findByIdOfflineVideoCourse(s.getIdOfflineVideoCourse());

					if (ofv == null)
						throw new AppException("Invalid Offline Video id");

					BeanUtils.copyProperties(s, sfqr);

					sfqr.setHeading(ofv.getHeading());

					temp.add(sfqr);
				});
			}

			if (temp.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(200);
				result.setMessage("No Quiz Result Result Found!");
			} else {
				result.setData(temp);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Student SubjectWise Score Details");
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
	public Document<List<StudentChapterQuiz>> getStudentOfflineChaptertWiseScore(Long idSubjectChapter,
			Long idStudentSubscription) {

		Document<List<StudentChapterQuiz>> result = new Document<>();
		try {
			List<StudentChapterQuiz> temp = new ArrayList<>();

			List<StudentChapterQuiz> studentSubjectQuiz = studentChapterQuizRepository
					.findByIdSubjectChapterAndIdStudentSubscrOrderByQuizDateDesc(idSubjectChapter,
							idStudentSubscription);

			if (!studentSubjectQuiz.isEmpty()) {
				studentSubjectQuiz.stream().forEach(s -> {

					StudentChapterQuiz sfqr = new StudentChapterQuiz();

					BeanUtils.copyProperties(s, sfqr);

					temp.add(sfqr);
					System.out.println(temp);
				});
			}

			if (temp.isEmpty()) {
				result.setData(new ArrayList<>());
				result.setStatusCode(200);
				result.setMessage("No Quiz Result Result Found!");
			} else {
				result.setData(temp);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Student SubjectWise Score Details");
			}
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

	}

	public Document<Map<String, Object>> getStudentofflineQuizReview(Long idStudentOfflineQuiz) {

		Document<Map<String, Object>> result = new Document<>();

		List<QuizQuestionDTO> quizQuestionDTOList = new ArrayList<QuizQuestionDTO>();
		Map<String, Object> map = new HashMap<>();
		try {
			List<StudentOfflineQuizQuestion> studentSubjectQuizQuestionList = studentOfflineQuizQuestionRepository
					.findByIdStudentOfflineQuiz(idStudentOfflineQuiz);

			if (studentSubjectQuizQuestionList.isEmpty())
				throw new NullPointerException("No studentSubjectQuizQuestions found");

			StudentOfflineQuiz studentOfflineQuiz = studentOfflineQuizRepository
					.findByIdStudentOfflineQuiz(idStudentOfflineQuiz);
			if (studentOfflineQuiz == null)
				throw new NullPointerException("No studentOfflineQuiz found");

			for (StudentOfflineQuizQuestion studentSubjectQuizQuestion : studentSubjectQuizQuestionList) {

				QuizQuestionDTO quizQuestionDTO = new QuizQuestionDTO();
				QuizQuestion quizQuestion = quizQuestionRepository
						.findByIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());
				if (quizQuestion == null)
					throw new NullPointerException("No quizQuestion found");

				List<StudentOfflineQuizAnswer> studentSubjectQuizAnswer = studentOfflineQuizAnswerRepository
						.findByIdStudentOfflineQuizQuestion(
								studentSubjectQuizQuestion.getIdStudentOfflineQuizQuestion());
				if (studentSubjectQuizAnswer.isEmpty())
					throw new NullPointerException("No studentSubjectQuizAnswer found");

				quizQuestionDTO.setIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());
				quizQuestionDTO.setQuestionText(quizQuestion.getQuestionText());
				quizQuestionDTO.setQuestionType(quizQuestion.getQuestionType());
				quizQuestionDTO.setCorrectValueFlag(studentSubjectQuizQuestion.getCorrectFlag());

				List<Answer> answers = new ArrayList<Answer>();
				quizQuestion.getAnswers().stream().forEach(a -> {
					Answer answer = new Answer();
					answer.setFieldId(a.getFieldId());
					answer.setTextFieldValue(a.getTextFieldValue());
					answer.setCorrectValueFlag(a.getCorrectValueFlag());
					answers.add(answer);
				});

				quizQuestionDTO.setCorrectAnswer(answers);
				List<StudentSubjectQuizAnswer> studentAnswers = new ArrayList<StudentSubjectQuizAnswer>();
				studentSubjectQuizAnswer.stream().forEach(an -> {
					StudentSubjectQuizAnswer studentAnswer = new StudentSubjectQuizAnswer();
					studentAnswer.setFieldId(an.getFieldId().toString());
					studentAnswer.setTextFieldValue(an.getTextFieldValue());
					studentAnswers.add(studentAnswer);
				});
				quizQuestionDTO.setStudentAnswer(studentAnswers);
				quizQuestionDTOList.add(quizQuestionDTO);
			}
			map.put("noOfQuestions", studentSubjectQuizQuestionList.size());
			map.put("correctAnswers",
					studentSubjectQuizQuestionList.stream().filter(a -> a.getCorrectFlag() == true).count());
			map.put("percentage", studentOfflineQuiz.getQuizScore());
			map.put("reviewData", quizQuestionDTOList);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Review Quiz Details Successfully");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	public Document<Map<String, Object>> getStudentChapterQuizReview(Long idStudentChapterQuiz) {

		Document<Map<String, Object>> result = new Document<>();

		List<QuizQuestionDTO> quizQuestionDTOList = new ArrayList<QuizQuestionDTO>();
		Map<String, Object> map = new HashMap<>();
		try {
			List<StudentChapterQuizQuestion> studentSubjectQuizQuestionList = studentChapterQuizQuestionRepository
					.findByIdStudentChapterQuiz(idStudentChapterQuiz);

			if (studentSubjectQuizQuestionList.isEmpty())
				throw new NullPointerException("No studentSubjectQuizQuestions found");

			StudentChapterQuiz studentChapterQuiz = studentChapterQuizRepository
					.findByIdStudentChapterQuiz(idStudentChapterQuiz);
			if (studentChapterQuiz == null)
				throw new NullPointerException("No studentOfflineQuiz found");

			for (StudentChapterQuizQuestion studentSubjectQuizQuestion : studentSubjectQuizQuestionList) {

				QuizQuestionDTO quizQuestionDTO = new QuizQuestionDTO();
				QuizQuestion quizQuestion = quizQuestionRepository
						.findByIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());
				if (quizQuestion == null)
					throw new NullPointerException("No quizQuestion found");

				List<StudentChapterQuizAnswer> studentSubjectQuizAnswer = studentChapterQuizAnswerRepository
						.findByIdStudentChapterQuizQuestion(
								studentSubjectQuizQuestion.getIdStudentChapterQuizQuestion());
				if (studentSubjectQuizAnswer.isEmpty())
					throw new NullPointerException("No studentSubjectQuizAnswer found");

				quizQuestionDTO.setIdQuizQuestion(studentSubjectQuizQuestion.getIdQuizQuestion());
				quizQuestionDTO.setQuestionText(quizQuestion.getQuestionText());
				quizQuestionDTO.setQuestionType(quizQuestion.getQuestionType());
				quizQuestionDTO.setCorrectValueFlag(studentSubjectQuizQuestion.isCorrectFlag());

				List<Answer> answers = new ArrayList<Answer>();
				quizQuestion.getAnswers().stream().forEach(a -> {
					Answer answer = new Answer();
					answer.setFieldId(a.getFieldId());
					answer.setTextFieldValue(a.getTextFieldValue());
					answer.setCorrectValueFlag(a.getCorrectValueFlag());
					answers.add(answer);
				});

				quizQuestionDTO.setCorrectAnswer(answers);
				List<StudentChapterQuizAnswer> studentAnswers = new ArrayList<StudentChapterQuizAnswer>();
				studentSubjectQuizAnswer.stream().forEach(an -> {
					StudentChapterQuizAnswer studentAnswer = new StudentChapterQuizAnswer();
					studentAnswer.setFieldId(an.getFieldId().toString());
					studentAnswer.setTextFieldValue(an.getTextFieldValue());
					studentAnswers.add(studentAnswer);
				});
				quizQuestionDTO.setStudentChapterAnswer(studentAnswers);
				quizQuestionDTOList.add(quizQuestionDTO);
			}
			map.put("noOfQuestions", studentSubjectQuizQuestionList.size());
			map.put("correctAnswers",
					studentSubjectQuizQuestionList.stream().filter(a -> a.isCorrectFlag() == true).count());
			map.put("percentage", studentChapterQuiz.getQuizScore());
			map.put("reviewData", quizQuestionDTOList);
			result.setData(map);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Review Quiz Details Successfully");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<Quiz> createExamPreparationQuiz(Long idClassStandard, Long idSubject, Long idSyllbus, Long idState,
			Quiz quiz, String category) {
		Document<Quiz> result = new Document<>();

		try {

			if (quiz == null)
				throw new NullPointerException("Quiz information cannot be null ");

			Product product = productRepository
					.findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndIdSubjectAndActiveFlag(
							idState, idSyllbus, idClassStandard, 12L, category, idSubject, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("Product is not found.");

			quiz.setIdProduct(product.getIdProduct());
			quiz.setIdSubject(product.getIdSubject());
			// id subjectChapter will be hard-coded as -1 since it will not be valid in exam
			// preparation module
			quiz.setIdSubjectChapter(-1L);

			// id offline video course will be hard-coded as -1 since it will not be valid
			// in exam preparation module
			quiz.setIdOfflineVideoCourse(-1L);

			quiz = quizRepository.save(quiz);

			result.setData(quiz);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

			return result;

		} catch (Exception exp) {
			/**
			 * updated by @author Naveen Kumar
			 */
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage(
							"Quiz is already available for selected combination.Please use edit quiz screen to add more question to the selected combination.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}

		}

	}

	@Override
	public Document<QuizDTO> getExamPreparationQuizDetails(Long idClassStandard, Long idSubject, Long idSyllbus,
			Long idState, String category) {

		Document<QuizDTO> result = new Document<>();

		QuizDTO quizDto = new QuizDTO();

		try {

			Product product = productRepository
					.findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndIdSubjectAndActiveFlag(
							idState, idSyllbus, idClassStandard, 12L, category, idSubject, Boolean.TRUE);
			if (product == null)

				throw new NullPointerException("No Products founds!");

			Quiz quiz = quizRepository.findFirstByIdProductAndIdSubjectAndIdSubjectChapter(product.getIdProduct(),
					idSubject, -1L);

			if (quiz == null)
				throw new AppException("No quiz data available, Please create a quiz for the selected combination.");

			quizDto.setIdQuiz(quiz.getIdQuiz());
			quizDto.setIdProduct(quiz.getIdProduct());
			quizDto.setIdSubject(quiz.getIdSubject());
			quizDto.setIdSubjectChapter(quiz.getIdSubjectChapter());
			quizDto.setQuizName(quiz.getQuizName());

			List<QuizQuestion> questionList = quizQuestionRepository
					.findByQuiz_idQuizAndQuestionActiveFlag(quiz.getIdQuiz(), Boolean.TRUE);

			quizDto.setQuizQuestionList(questionList);

			result.setData(quizDto);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Retrieved Quiz Details Successfully");
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<?> getQuestionsForExamPrep(Long idProduct, Long idSubject) {
		// TODO Auto-generated method stub
		Document<Set<QuizQuestion>> result = new Document<>();
		try {
			List<QuizQuestion> questions = quizQuestionRepository.findByProductAndSubjectAndSubjectChapter(idProduct,
					idSubject, -1L, Boolean.TRUE);
			if (questions.isEmpty()) {
				throw new NullPointerException("No Questions Found for Respective Quiz");
			}
			Set<QuizQuestion> questionset = new HashSet<QuizQuestion>();
			questionset.addAll(questions);

			questionset.stream().forEach(q -> {
				q.getAnswers().stream().forEach(a -> {

					a.setCorrectValueFlag(false);
					if (q.getQuestionType().equalsIgnoreCase("SUBJECTIVE")) {
						a.setTextFieldValue(null);
					}

				});
			});

			Set<QuizQuestion> finalQuestions = new HashSet<QuizQuestion>();

			for (QuizQuestion qq : questions) {
				if (finalQuestions.size() > 24)
					break;

				finalQuestions.add(qq);
			}

			result.setData(finalQuestions);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@Override
	public Document<Map<String, Object>> getVCTQuizQuestions(Long idProduct) {

		Document<Map<String, Object>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate_release = sdf.parse("2023-02-03");
			Date availableDate = sdf.parse(vctAvailableDate);

			if (!currectDate.after(availableDate_release)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage(
						"Don't miss this chance to showcase your skills and knowledge! The exam is waiting for you on Feb 4th. Be prepared and ace it!");
				return result;
			}

			Product product = productRepository.findByIdProductAndIdProductLineAndActiveFlag(idProduct, 13l,
					Boolean.TRUE);

			// validate product
			if (product == null)
				throw new NullPointerException("Invalid Product id provided.");

			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());

			// validate subject
			if (subject == null)
				throw new NullPointerException("Invalid Subject info found.");

			StudentSubscription ss = studentSubscriptionRepository.findByIdProductAndUserSurId(product.getIdProduct(),
					userPrincipal.getUserSurId());

			if (ss != null) {
				StudentSubjectQuiz ssQuiz = studentSubjectQuizRepository
						.findFirstByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(ss.getIdStudentSubscription(),
								subject.getIdSubject());

				if (ssQuiz != null) {
					DateTime dateTime = new DateTime(availableDate).plusDays(1);

					if (!currectDate.after(availableDate)) {
						result.setData(null);
						result.setStatusCode(HttpStatus.TOO_EARLY.value());
						result.setMessage("Attention! You have already taken this exam, kindly wait until "
								+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
								+ dateTime.getDayOfMonth() + " for the next opportunity.");

						return result;
					}
				}

			}

			// fetching list of question type
			List<Object[]> questionSets = quizRepository.getVCTQuizQuestionTypesByMarks(product.getIdProduct(),
					Boolean.TRUE);

			Map<String, Object> finalDataMap = new HashMap<>();

			finalDataMap.put("subjectName", subject.getSubjectName());
			finalDataMap.put("idSubject", subject.getIdSubject());
			finalDataMap.put("color", subject.getColor());
			finalDataMap.put("idProduct", product.getIdProduct());

			if (questionSets.isEmpty())
				throw new AppException("No Question Available for selected subject.");

			List<VCTQuizDTO> vctQuizDTOList = new ArrayList<>();

			// Initialise total marks
			int totalMarks = 0;

			for (Object[] obj : questionSets) {

				VCTQuizDTO vctQuiz = new VCTQuizDTO();
				vctQuiz.setMark(((Byte) obj[2]).toString());
				vctQuiz.setNoOfQuestion(((BigInteger) obj[0]).toString());
				vctQuiz.setQuestionType(obj[1].toString());
				vctQuiz.setQuestionTitle(obj[3].toString());
				totalMarks += (((BigInteger) obj[0]).intValue() * ((Byte) obj[2]));

				System.out.println(((Byte) obj[2]).shortValue());
				System.out.println(obj[1].toString());

				// fetch respective set quiz questions based on question type and marks
				List<QuizQuestion> questions = quizQuestionRepository.findByProductAndQuestionType(idProduct,
						subject.getIdSubject(), -1L, true, obj[1].toString(), ((Byte) obj[2]).shortValue(),
						obj[3].toString());

				// check map contains key idQuiz
				if (!finalDataMap.containsKey("idQuiz"))

					if (!questions.isEmpty())
						finalDataMap.put("idQuiz", questions.get(0).getQuiz().getIdQuiz());

				// removing quiz object and answer values for subjective ,one word, problem
				// solving and descriptive to null

				questions.stream().forEach(q -> {
					q.setQuiz(null);
					q.getAnswers().stream().forEach(a -> {

						a.setCorrectValueFlag(false);
						if (q.getQuestionType().equalsIgnoreCase("SUBJECTIVE")
								|| q.getQuestionType().equalsIgnoreCase("OneWord")
								|| q.getQuestionType().equalsIgnoreCase("Descriptive")) {
							a.setTextFieldValue(null);
						}

					});
				});

				vctQuiz.setQuestions(questions);

				vctQuizDTOList.add(vctQuiz);
			}

			finalDataMap.put("totalMarks", totalMarks);

			finalDataMap.put("quizData", vctQuizDTOList);

			result.setData(finalDataMap);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;

	}

	@Override
	public Document<Map<String, Object>> vctQuizSubmition(StudentSubjectQuizDTO studentSubjectQuizDTO) {

		Document<Map<String, Object>> result = new Document<>();

		float quizScore = 0f;
		int totalMarks = 0;

		try {

			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			if (studentSubjectQuizDTO.getQuizQuestions().isEmpty())
				throw new AppException("Please answer all the question.");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);

			StudentSubscription ss = studentSubscriptionRepository
					.findByIdStudentSubscription(studentSubjectQuizDTO.getIdStudentSubscr());

			if (ss != null) {
				StudentSubjectQuiz ssQuiz = studentSubjectQuizRepository
						.findFirstByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(ss.getIdStudentSubscription(),
								studentSubjectQuizDTO.getIdSubject());

				if (ssQuiz != null) {

					DateTime dateTime = new DateTime(availableDate).plusDays(1);

					if (!currectDate.after(availableDate)) {
						result.setData(null);
						result.setStatusCode(HttpStatus.TOO_EARLY.value());
						result.setMessage("Attention! You have already taken this exam, kindly wait until "
								+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
								+ dateTime.getDayOfMonth() + " for the next opportunity.");

						return result;
					}
				}

			}

			List<StudentSubjectQuizQuestion> ssqqList = new ArrayList<StudentSubjectQuizQuestion>();

			StudentSubjectQuiz studentSubjectQuiz = new StudentSubjectQuiz();
			studentSubjectQuiz.setIdStudentSubscr(studentSubjectQuizDTO.getIdStudentSubscr());
			studentSubjectQuiz.setIdSubject(studentSubjectQuizDTO.getIdSubject());
			studentSubjectQuiz.setQuizDate(Instant.now());
			studentSubjectQuiz.setIdQuiz(studentSubjectQuizDTO.getIdQuiz());

			List<QuizQuestion> quizQuestionList = studentSubjectQuizDTO.getQuizQuestions();

			List<QuizQuestion> questionsList = quizQuestionRepository
					.findByQuiz_idQuizAndQuestionActiveFlag(studentSubjectQuizDTO.getIdQuiz(), Boolean.TRUE);

			if (questionsList.isEmpty())
				throw new AppException("No Question available for the submitted quiz.");

			for (QuizQuestion q : questionsList) {
				// update the total marks
				totalMarks += q.getMarks();

				QuizQuestion quizQuestion = this.findQuizQuestionEnhancedLoop(q.getIdQuizQuestion(), quizQuestionList);

				StudentSubjectQuizQuestion ssqq = new StudentSubjectQuizQuestion();

				if (quizQuestion != null) {

					ssqq.setIdQuizQuestion(quizQuestion.getIdQuizQuestion());

					boolean correctFlag;

					AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(quizQuestion.getIdQuizQuestion(),
							quizQuestion.getAnswers());

//        				QuizQuestion orginalQuestion = quizQuestionRepository
//        						.findByIdQuizQuestion(quizQuestion.getIdQuizQuestion());

					switch (q.getQuestionType().toUpperCase()) {

					case "SUBJECTIVE": {
						correctFlag = (boolean) this.checkAnswers(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}

						break;
					}

					case "OBJECTIVE": {
						correctFlag = (boolean) this.checkObjectiveAnswers(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}

						break;
					}
					case "MULTISELECT": {
						correctFlag = (boolean) this.checkMultipleAnswers(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}
						break;
					}
					case "TRUEORFALSE": {
						correctFlag = (boolean) this.checkTrueOrFalseAnswers(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}
						break;
					}
					case "ONEWORD": {
						correctFlag = (boolean) this.checkOneWordAnswer(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}
						break;
					}
					case "DESCRIPTIVE": {

						double marks = (Double) this.checkDescriptiveAnswer(answerRequestDTO).getData();

						if (marks > 0.0) {
							correctFlag = true;
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							correctFlag = false;
							ssqq.setScoredMarks("0");
						}

						break;
					}

					case "PROBLEMSOLVING": {
						correctFlag = (boolean) this.checkObjectiveAnswers(answerRequestDTO).getData();
						if (correctFlag) {
							quizScore += q.getMarks();
							ssqq.setScoredMarks(q.getMarks().toString());
						} else {
							ssqq.setScoredMarks("0");
						}
						break;
					}

					default: {
						correctFlag = false;
						ssqq.setScoredMarks("0");
						break;
					}

					}

					ssqq.setCorrectFlag(correctFlag);

					List<StudentSubjectQuizAnswer> ssqaList = new ArrayList<StudentSubjectQuizAnswer>();
					StudentSubjectQuizAnswer ssqa = new StudentSubjectQuizAnswer();
					ssqa.setFieldId(quizQuestion.getAnswers().get(0).getFieldId());
					ssqa.setTextFieldValue(quizQuestion.getAnswers().get(0).getTextFieldValue());
					ssqaList.add(ssqa);

					ssqq.setStudentSubjectQuizAnswers(ssqaList);

				}

				else {
					// this block will execute when student missed any question
					// user not answered that question so marks will be zero
					quizScore += 0;

					ssqq.setIdQuizQuestion(q.getIdQuizQuestion());
					ssqq.setScoredMarks("0");
					ssqq.setCorrectFlag(false);

					// creating student subject Question for marking dummy data
					List<StudentSubjectQuizAnswer> ssqaList = new ArrayList<StudentSubjectQuizAnswer>();
					StudentSubjectQuizAnswer ssqa = new StudentSubjectQuizAnswer();
					ssqa.setFieldId("1");
					ssqa.setTextFieldValue("NOT_ANSWERED");
					ssqaList.add(ssqa);
					ssqq.setStudentSubjectQuizAnswers(ssqaList);

				}

				ssqqList.add(ssqq);

			}

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("totalQuestions", questionsList.size());
			response.put("totalMarks", totalMarks);
			response.put("totalScoredMarks", quizScore);

			studentSubjectQuiz.setStudentSubjectQuizQuestion(ssqqList);

			quizScore = (quizScore / totalMarks) * 100;

			response.put("percentage", Math.round(quizScore * 100.0 / 100));

			studentSubjectQuiz.setQuizScore((float) Math.round(quizScore * 100.0 / 100));
			studentSubjectQuiz.setTotalMarksScored(Float.valueOf(quizScore).toString());
			StudentSubjectQuiz temp = studentSubjectQuizRepository.save(studentSubjectQuiz);

			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
						+ dateTime.getDayOfMonth() + "for the big reveal!");

			} else {
				response.put("studentSubjectQuiz", temp);
				result.setData(response);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		} catch (Exception e) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	public QuizQuestion findQuizQuestionEnhancedLoop(Long idQuizQuestion, List<QuizQuestion> quizQuestions) {

		for (QuizQuestion qq : quizQuestions) {
			if (qq.getIdQuizQuestion().equals(idQuizQuestion)) {
				return qq;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	public Document<Boolean> checkTrueOrFalseAnswers(AnswerRequestDTO answerRequestDTO) {

		Document<Boolean> result = new Document<Boolean>();

		List<Answer> correctAnswer = null;

		try {

			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);

			if (questions != null) {
				correctAnswer = questions.getAnswers();
			} else {
				throw new NullPointerException("Invalid Quiz Question");
			}

			List<Answer> answers = answerRequestDTO.getAnswer();

			Answer answer = answers.get(0);

			System.out.println("Key Combinations:" + answerRequestDTO.getIdQuizQuestion() + "" + answer.getFieldId());

			Answer temp = answerRepository.findByIdQuizQuestionAndFieldIdAndCorrectValueFlag(
					answerRequestDTO.getIdQuizQuestion(), answer.getFieldId(), Boolean.TRUE);

			if (temp != null) {

				result.setData(true);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	public Document<Boolean> checkOneWordAnswer(AnswerRequestDTO answerRequestDTO) {

		Document<Boolean> result = new Document<Boolean>();

		List<Answer> correctAnswer = null;

		try {

			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);

			if (questions != null) {
				correctAnswer = questions.getAnswers();
			} else {
				throw new NullPointerException("Invalid Quiz Question");
			}

			List<Answer> answers = answerRequestDTO.getAnswer();

			Answer answer = answers.get(0);

			String studentAnswer = answer.getTextFieldValue() != null ? answer.getTextFieldValue().trim() : "^*|$@%~";
			String noSpaceAnswer = studentAnswer.replaceAll("\\s+", "");

			if (noSpaceAnswer.equalsIgnoreCase(correctAnswer.get(0).getTextFieldValue())) {

				result.setData(true);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");

			} else {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	public Document<Double> checkDescriptiveAnswer(AnswerRequestDTO answerRequestDTO) {

		Document<Double> result = new Document<Double>();

		List<Answer> correctAnswer = null;

		try {

			QuizQuestion questions = quizQuestionRepository
					.findByIdQuizQuestionAndQuestionActiveFlag(answerRequestDTO.getIdQuizQuestion(), Boolean.TRUE);

			if (questions != null) {

				correctAnswer = questions.getAnswers();
			} else {
				throw new NullPointerException("Invalid Quiz Question");
			}

			List<Answer> answers = answerRequestDTO.getAnswer();

			Answer answer = answers.get(0);

			String studentAnwser = answer.getTextFieldValue() != null ? answer.getTextFieldValue().trim() : "^*|$@%~";
			String noSpaceAnswer = studentAnwser.replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
			noSpaceAnswer = noSpaceAnswer.isEmpty() ? "^*|$@%~" : noSpaceAnswer;

			int totalKeyWordsCount = correctAnswer.size();
			int correctKeywordCount = 0;

			for (Answer a : correctAnswer) {
				System.out.println(noSpaceAnswer);

				String orginalKeyword = a.getTextFieldValue().trim().replaceAll("\\s+", "")
						.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
				System.out.println(orginalKeyword);
				System.out.println(noSpaceAnswer.contains(orginalKeyword));

				if (noSpaceAnswer.contains(orginalKeyword))
					correctKeywordCount++;
			}

			float divident = (float) correctKeywordCount / (float) totalKeyWordsCount;
			float marks = (divident * questions.getMarks());
			double roundedValue = ((Math.round(marks) * 2) / 2.0);

			result.setData(roundedValue);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;
	}

	@Override
	public Document<Map<String, Object>> vctQuizLatestResult(Long idProduct) {
		Document<Map<String, Object>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);
			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
						+ dateTime.getDayOfMonth() + " for the big reveal!");

				return result;
			}

			Product product = productRepository.findByIdProductAndIdProductLineAndActiveFlag(idProduct, 13L,
					Boolean.TRUE);

			// validate product
			if (product == null)
				throw new NullPointerException("Invalid Product id provided.");

			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());

			// validate subject
			if (subject == null)
				throw new NullPointerException("Invalid Subject info found.");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByUserSurIdAndIdProductAndActiveFlag(userPrincipal.getUserSurId(), idProduct, Boolean.TRUE);

			if (studentSubscription == null)
				throw new NullPointerException("Invalid Student Subscription info found.");

			// fetching list of question type
			List<Object[]> questionSets = quizRepository.getVCTQuizQuestionTypesByMarks(product.getIdProduct(),
					Boolean.TRUE);

			Map<String, Object> finalDataMap = new HashMap<>();

			finalDataMap.put("subjectName", subject.getSubjectName());
			finalDataMap.put("idSubject", subject.getIdSubject());
			finalDataMap.put("color", subject.getColor());
			finalDataMap.put("idProduct", product.getIdProduct());

			if (questionSets.isEmpty())
				throw new AppException("No Question Available for selected subject.");

			List<VCTQuizOverAllReviewDTO> vctQuizOverAllReviewDTOList = new ArrayList<>();

			// Initialise total marks
			int totalMarks = 0;
			float studentTotalMarks = 0;

			for (Object[] obj : questionSets) {

				VCTQuizOverAllReviewDTO vqoDTO = new VCTQuizOverAllReviewDTO();
				vqoDTO.setQuestionTitle(obj[3].toString() == null ? null : obj[3].toString());
				vqoDTO.setQuestionType(obj[1].toString());
				vqoDTO.setMark(((Byte) obj[2]));
				vqoDTO.setNoOfQuestions(((BigInteger) obj[0]).intValue());
				vqoDTO.setTotalMarks(Integer.valueOf(((BigInteger) obj[0]).intValue() * ((Byte) obj[2])));
				totalMarks += (((BigInteger) obj[0]).intValue() * ((Byte) obj[2]));

				StudentSubjectQuiz ssq = studentSubjectQuizRepository
						.findFirstByIdStudentSubscrAndIdSubjectOrderByQuizDateDesc(
								studentSubscription.getIdStudentSubscription(), subject.getIdSubject());

				if (ssq != null) {
					List<StudentSubjectQuizQuestion> ssqqList = studentSubjectQuizQuestionRepository
							.findByLatestQuizQuestionByCorrectAnswer(ssq.getIdStudentSubjectQuiz(), obj[1].toString(),
									Boolean.TRUE, ((Byte) obj[2]).shortValue(), obj[3].toString());

					vqoDTO.setCorrectAnswer(ssqqList.size());

					if (obj[1].toString().equalsIgnoreCase("Descriptive")) {
						studentTotalMarks += ssqqList.stream().mapToDouble(q -> Float.valueOf(q.getScoredMarks()))
								.sum();
						vqoDTO.setTotalMarksScored(Float.valueOf(
								(float) ssqqList.stream().mapToDouble(q -> Float.valueOf(q.getScoredMarks())).sum()));

					} else {
						vqoDTO.setTotalMarksScored(Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size()));
						studentTotalMarks += Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size());
					}

				} else {
					vqoDTO.setCorrectAnswer(0);
					vqoDTO.setTotalMarksScored(0);
				}

				vctQuizOverAllReviewDTOList.add(vqoDTO);

			}

			float quizScore = (studentTotalMarks / totalMarks) * 100;

			finalDataMap.put("overAllTotalMarks", totalMarks);
			finalDataMap.put("overAllTotalMarksScored", studentTotalMarks);
			finalDataMap.put("overAllpercentage", (float) Math.round(quizScore * 100.0 / 100));

			finalDataMap.put("quizReviewData", vctQuizOverAllReviewDTOList);

			result.setData(finalDataMap);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;

	}

	@Override
	public Document<Map<String, Object>> vctQuizResultByStudentIdSubejctQuiz(Long idStudentSubjectQuiz) {
		Document<Map<String, Object>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);
			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "- "
						+ dateTime.getDayOfMonth() + " for the big reveal!");

				return result;
			}

			StudentSubjectQuiz ssq = studentSubjectQuizRepository.findByIdStudentSubjectQuiz(idStudentSubjectQuiz);

			if (ssq == null)
				throw new AppException("Invalid IdStudentSubjectQuiz.");

			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByIdStudentSubscription(ssq.getIdStudentSubscr());

			if (studentSubscription == null)
				throw new NullPointerException("Invalid Student Subscription info found.");

			Product product = productRepository.findByIdProductAndActiveFlag(studentSubscription.getIdProduct(),
					Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("Invalid Product found in student subscripiton.");

			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());

			if (subject == null)
				throw new NullPointerException(
						"Invalid Subject found in product data, idproduct: " + product.getIdProduct() + " .");

			// fetching list of question type
			List<Object[]> questionSets = quizRepository.getVCTQuizQuestionTypesByMarks(product.getIdProduct(),
					Boolean.TRUE);

			Map<String, Object> finalDataMap = new HashMap<>();

			finalDataMap.put("subjectName", subject.getSubjectName());
			finalDataMap.put("idSubject", subject.getIdSubject());
			finalDataMap.put("color", subject.getColor());
			finalDataMap.put("idProduct", product.getIdProduct());

			if (questionSets.isEmpty())
				throw new AppException("No Question Available for selected subject.");

			List<VCTQuizOverAllReviewDTO> vctQuizOverAllReviewDTOList = new ArrayList<>();

			// Initialise total marks
			int totalMarks = 0;
			float studentTotalMarks = 0;

			for (Object[] obj : questionSets) {

				VCTQuizOverAllReviewDTO vqoDTO = new VCTQuizOverAllReviewDTO();
				vqoDTO.setQuestionTitle(obj[3].toString() == null ? null : obj[3].toString());
				vqoDTO.setQuestionType(obj[1].toString());
				vqoDTO.setMark(((Byte) obj[2]));
				vqoDTO.setNoOfQuestions(((BigInteger) obj[0]).intValue());
				vqoDTO.setTotalMarks(Integer.valueOf(((BigInteger) obj[0]).intValue() * ((Byte) obj[2])));
				totalMarks += (((BigInteger) obj[0]).intValue() * ((Byte) obj[2]));

				if (ssq != null) {
					List<StudentSubjectQuizQuestion> ssqqList = studentSubjectQuizQuestionRepository
							.findByLatestQuizQuestionByCorrectAnswer(ssq.getIdStudentSubjectQuiz(), obj[1].toString(),
									Boolean.TRUE, ((Byte) obj[2]).shortValue(), obj[3].toString());

					vqoDTO.setCorrectAnswer(ssqqList.size());

					if (obj[1].toString().equalsIgnoreCase("Descriptive")) {
						studentTotalMarks += ssqqList.stream().mapToDouble(q -> Float.valueOf(q.getScoredMarks()))
								.sum();
						vqoDTO.setTotalMarksScored(Float.valueOf(
								(float) ssqqList.stream().mapToDouble(q -> Float.valueOf(q.getScoredMarks())).sum()));

					} else {
						vqoDTO.setTotalMarksScored(Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size()));
						studentTotalMarks += Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size());
					}

				} else {
					vqoDTO.setCorrectAnswer(0);
					vqoDTO.setTotalMarksScored(0);
				}

				vctQuizOverAllReviewDTOList.add(vqoDTO);

			}

			float quizScore = (studentTotalMarks / totalMarks) * 100;

			String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
					|| appAngularUrl.equals("https://student.vistaslearning.com"))
							? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ subject.getIdSubject() + ".webp"
							: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
									+ subject.getIdSubject() + ".webp";

			finalDataMap.put("imageUrl", imageUrl);

			finalDataMap.put("overAllTotalMarks", totalMarks);
			finalDataMap.put("overAllTotalMarksScored", studentTotalMarks);
			finalDataMap.put("overAllpercentage", (float) Math.round(quizScore * 100.0 / 100));

			finalDataMap.put("quizReviewData", vctQuizOverAllReviewDTOList);

			result.setData(finalDataMap);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;

		}

		return result;

	}

	@Override
	public Document<SubjectWiseReportCardDTO> vctQuizLatestReportCard() {

		Document<SubjectWiseReportCardDTO> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			Student student = studentRepository.findByUser_userSurId(userPrincipal.getUserSurId());

			if (student == null)
				throw new AppException("Invalid Student data found in user principal");

			ClassStandard classStandard = classRepository.findByIdClassStandard(userPrincipal.getIdClassStandard());

			if (classStandard == null) {
				throw new AppException("Invalid Class Standard Data found in user principal.");
			}

			Syllabus syllabus = syllabusRepository.findByIdSyllabus(userPrincipal.getIdSyllabus());

			if (syllabus == null) {
				throw new AppException("Invalid Syllabus Data found in user principal.");
			}

			State state = stateRepository.findByIdState(userPrincipal.getIdState());

			if (state == null) {
				throw new AppException("Invalid State Data found in user principal.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);

			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
						+ dateTime.getDayOfMonth() + " for the big reveal!");

				return result;
			}

			SubjectWiseReportCardDTO swReportCardDTO = new SubjectWiseReportCardDTO();

			swReportCardDTO.setClassStandard(classStandard.getClassStandadName());

			swReportCardDTO.setReportGenerationDate(Instant.now());

			swReportCardDTO.setState(state.getState());

			swReportCardDTO.setStudentName(userPrincipal.getFirstName());

			swReportCardDTO.setSyllabus(syllabus.getSyllabusName());

			swReportCardDTO.setUserId(userPrincipal.getUserSurId());

			int grandTotalMarks = 0;

			int grandTotalMarksObtained = 0;

			List<Product> userProducts = productRepository
					.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(13L,
							classStandard.getIdClassStandard(), syllabus.getIdSyllabus(), state.getIdState(),
							Boolean.TRUE);

			if (userProducts.isEmpty())
				throw new AppException("For Your profile VCT exams are avilable ,Please contact admin.");

			List<SubjectWiseReportDTO> sWiseReportDTOs = new ArrayList<>();

			for (Product p : userProducts) {
				SubjectWiseReportDTO sReportDTO = new SubjectWiseReportDTO();

				Subject subject = subjectRepository.findByIdSubject(p.getIdSubject());

				if (subject == null)
					throw new AppException("Invalid Subject Data found.");

				Quiz quiz = quizRepository.findByIdProductAndIdSubjectAndIdSubjectChapterAndIdOfflineVideoCourse(
						p.getIdProduct(), p.getIdSubject(), -1L, -1L);

				sReportDTO.setColor(subject.getColor());
				sReportDTO.setIdProduct(p.getIdProduct());
				String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
						|| appAngularUrl.equals("https://student.vistaslearning.com"))
								? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp"
								: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
										+ subject.getIdSubject() + ".webp";

				sReportDTO.setImageUrl(imageUrl);

				sReportDTO.setSubjectName(subject.getSubjectName());

				if (quiz != null) {
					sReportDTO.setIdQuiz(quiz.getIdQuiz());
					int quizTotalMarks = quizQuestionRepository.sumOfMarks(quiz.getIdQuiz());
					grandTotalMarks += quizTotalMarks;
					sReportDTO.setTotalMarks(quizTotalMarks);

					StudentSubscription studentSubscription = studentSubscriptionRepository
							.findFirstByIdStudentAndIdProductAndActiveFlag(student.getIdStudent(), p.getIdProduct(),
									Boolean.TRUE);

					if (studentSubscription == null) {
						sReportDTO.setSecuredMarks(0F);
						sReportDTO.setRemarks("NOT ATTMPTED");
						sReportDTO.setSecuredPercentage("0");
					} else {
						StudentSubjectQuiz ssq = studentSubjectQuizRepository
								.findFirstByIdStudentSubscrAndIdSubjectAndQuizDateBetweenOrderByQuizDateDesc(
										studentSubscription.getIdStudentSubscription(), p.getIdSubject(),
										Instant.parse(startDate), Instant.parse(endDate));

						sReportDTO.setIdStudentSubscr(studentSubscription.getIdStudentSubscription());

						if (ssq == null) {
							sReportDTO.setSecuredMarks(0F);
							sReportDTO.setRemarks("NOT ATTMPTED");
							sReportDTO.setSecuredPercentage("0");
						} else {
							sReportDTO.setIdStudentSubjectQuiz(ssq.getIdStudentSubjectQuiz());
							grandTotalMarksObtained += Float.valueOf(ssq.getTotalMarksScored()).intValue();
							sReportDTO.setSecuredMarks(Float.valueOf(ssq.getTotalMarksScored()));
							if (Float.valueOf(ssq.getTotalMarksScored()) > 0) {
								float quizScore = (Float.valueOf(ssq.getTotalMarksScored()) / quizTotalMarks) * 100;
								sReportDTO
										.setSecuredPercentage(((Long) Math.round(quizScore * 100.0 / 100)).toString());
							} else {
								sReportDTO.setSecuredPercentage("0");
							}

							sReportDTO.setRemarks("ATTMPTED");
						}
					}
				}

				sWiseReportDTOs.add(sReportDTO);

			}
			swReportCardDTO.setSubjectList(sWiseReportDTOs);
			swReportCardDTO.setGrandTotalMarks((float) grandTotalMarks);
			swReportCardDTO.setGrandTotalMarksObtained((float) grandTotalMarksObtained);
			if (grandTotalMarksObtained > 0) {
				float quizScore = (Float.valueOf((float) grandTotalMarksObtained / grandTotalMarks)) * 100;

				swReportCardDTO.setOverAllPercentage(((Long) Math.round(quizScore * 100.0 / 100)).toString());
			} else {
				swReportCardDTO.setOverAllPercentage("0");
			}

			result.setData(swReportCardDTO);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}

		return result;

	}

	@Override
	public Document<Boolean> checkStudentAttemtedVCT() {

		Document<Boolean> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			Student student = studentRepository.findByUser_userSurId(userPrincipal.getUserSurId());

			if (student == null)
				throw new AppException("Invalid Student data found in user principal");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);

			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "- "
						+ dateTime.getDayOfMonth() + " for the big reveal!");

				return result;
			}

			List<Long> userVCTProducts = productRepository
					.getIdByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(13L,
							userPrincipal.getIdClassStandard(), userPrincipal.getIdSyllabus(),
							userPrincipal.getIdState(), Boolean.TRUE);

			if (userVCTProducts.isEmpty())
				throw new AppException("No VCT product Data found for this user profile.");

			List<Long> studentVCTSubscripitonsList = studentSubscriptionRepository
					.getIdsByIdProductsAndStudentId(userVCTProducts, student.getIdStudent());

			if (userVCTProducts.isEmpty()) {
				result.setData(false);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			} else {
				result.setData(
						((studentSubjectQuizRepository.existsByIdStudentSubscrInAndQuizDateBetweenOrderByQuizDateDesc(
								studentVCTSubscripitonsList, Instant.parse(startDate), Instant.parse(endDate)))
								&& showVCTResult));
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Successfull request");
			}

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}

		return result;

	}

	@Override
	public Document<Map<String, Object>> getVCTStudentResultAndRanking() {

		Document<Map<String, Object>> result = new Document<>();

		try {

			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				throw new AppException("Unathorized user access.");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currectDate = sdf.parse(LocalDate.now().toString());
			Date availableDate = sdf.parse(vctAvailableDate);

			DateTime dateTime = new DateTime(availableDate).plusDays(1);

			if (!currectDate.after(availableDate)) {
				result.setData(null);
				result.setStatusCode(HttpStatus.TOO_EARLY.value());
				result.setMessage("Congratulation! Are you excited to see your exam results? Wait until "
						+ Month.of(LocalDate.parse(vctAvailableDate).getMonthValue()).name() + "-"
						+ dateTime.getDayOfMonth() + " for the big reveal!");

				return result;
			}

			Map<String, Object> finalResponseDataMap = new HashMap<>();

			// list of product for the respective data
			List<Long> userVCTProducts = productRepository
					.getIdByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(13L,
							userPrincipal.getIdClassStandard(), userPrincipal.getIdSyllabus(),
							userPrincipal.getIdState(), Boolean.TRUE);

			// total marks will be summed up for all products
			int totalMarks = 300;

			// fetch all user subjects on a quiz
			List<SubjectRankingDTO> studentRanking = studentSubjectQuizRepository.getSubjectListRanking(
					userPrincipal.getIdClassStandard(), userPrincipal.getIdState(), userPrincipal.getIdSyllabus(),
					null);

			// filtered result which contains all subjects and user data.
			List<SubjectRankingDTO> subjectRankingDTOFilteredList = studentRanking.stream()
					.filter(distinctByKey(s -> s.getIdStudentSubscr())).collect(Collectors.toList());

			// fetching unique user id for ranking
			List<Long> uniqueIds = subjectRankingDTOFilteredList.stream().map(SubjectRankingDTO::getIdVlUser).distinct()
					.collect(Collectors.toList());

			// creating ranking List
			List<StudentVCTMarksDTO> rankingList = new ArrayList<>();

			for (Long userId : uniqueIds) {

				Float totalMarksScored = 0f;

				int subjectCount = 0;

				int maxSubjectCount = 6;

				for (SubjectRankingDTO srd : subjectRankingDTOFilteredList) {
					if (srd.getIdVlUser().equals(userId) && subjectCount < maxSubjectCount) {
						totalMarksScored += (srd.getQuizScore() / 2);
						subjectCount++;
					}

					if (subjectCount > maxSubjectCount)
						break;
				}

				SubjectRankingDTO failedSubject = subjectRankingDTOFilteredList.stream()
						.filter(s -> (s.getQuizScore() < 35 && s.getIdVlUser().equals(userId))).findFirst()
						.orElse(null);

				StudentVCTMarksDTO svmDTO = new StudentVCTMarksDTO();

				if (failedSubject != null) {
					svmDTO.setQualiefiedForGlobalRanking(false);
				} else {

					svmDTO.setQualiefiedForGlobalRanking(true);
				}

				svmDTO.setTotalMarks((float) totalMarks);
				svmDTO.setTotalMarkeScored(totalMarksScored);
				svmDTO.setPercentageFloat((Float.valueOf(totalMarksScored / totalMarks) * 100));
				svmDTO.setUserSurId(userId);
				rankingList.add(svmDTO);

			}

			final Long userId = userPrincipal.getUserSurId();
			rankingList.sort(Comparator.comparing(StudentVCTMarksDTO::getPercentageFloat).reversed());

			// List<StudentVCTMarksDTO> passedList = rankingList.stream().filter(s->
			// s.getQualiefiedForGlobalRanking()).collect(Collectors.toList());

			// sorting ranking list based on totalPercentage
			//

			// Getting Global Ranking
			int globalRank = IntStream.range(0, rankingList.size())
					.filter(i -> (rankingList.get(i).getUserSurId().equals(userId))).findFirst().orElse(-1);

			finalResponseDataMap.put("globalRanking", globalRank + 1);

			List<SubjectQuizRankingAndReviewDTO> ssrrList = new ArrayList<>();

			for (Long idProduct : userVCTProducts) {
				SubjectQuizRankingAndReviewDTO sqrrDTO = new SubjectQuizRankingAndReviewDTO();

				List<SubjectRankingDTO> subjectList = subjectRankingDTOFilteredList.stream()
						.filter(p -> (p.getIdProduct().equals(idProduct))).collect(Collectors.toList());

				List<SubjectRankingDTO> subjectRankingList = subjectList.stream().filter(s -> s.getQuizScore() >= 35)
						.collect(Collectors.toList());

				subjectRankingList.sort(Comparator.comparing(SubjectRankingDTO::getQuizScore).reversed());

				int subjectRank = IntStream.range(0, subjectRankingList.size())
						.filter(i -> subjectRankingList.get(i).getIdVlUser().equals(userId)).findFirst().orElse(-1);

				SubjectRankingDTO sr = subjectList.stream().filter(s -> s.getIdVlUser().equals(userId)).findFirst()
						.orElse(null);

				if (sr != null) {
					Subject subject = subjectRepository.findByIdSubject(sr.getIdSubject());
					sqrrDTO.setIdSubject(subject.getIdSubject());
					sqrrDTO.setSubjectName(subject.getSubjectName());
					sqrrDTO.setColor(subject.getColor());

					String imageUrl = (appAngularUrl.equals("https://vistaslearning.com")
							|| appAngularUrl.equals("https://student.vistaslearning.com"))
									? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ subject.getIdSubject() + ".webp"
									: "https://vlearning-preprod.s3.ap-south-1.amazonaws.com/assets/subject/"
											+ subject.getIdSubject() + ".webp";

					sqrrDTO.setImageUrl(imageUrl);
					sqrrDTO.setRanking(subjectRank + 1);
					sqrrDTO.setAttemptedOn(sr.getAttemptedDate());

					List<Object[]> questionSets = quizRepository.getVCTQuizQuestionTypesByMarks(idProduct,
							Boolean.TRUE);

					if (questionSets.isEmpty())
						continue;

					List<VCTQuizOverAllReviewDTO> vctQuizOverAllReviewDTOList = new ArrayList<>();

					// Initialise total marks
					int subjectTotalMarks = 0;
					float studentTotalMarks = 0;

					for (Object[] obj : questionSets) {

						VCTQuizOverAllReviewDTO vqoDTO = new VCTQuizOverAllReviewDTO();
						vqoDTO.setQuestionTitle(obj[3].toString() == null ? null : obj[3].toString());
						vqoDTO.setQuestionType(obj[1].toString());
						vqoDTO.setMark(((Byte) obj[2]));
						vqoDTO.setNoOfQuestions(((BigInteger) obj[0]).intValue());
						vqoDTO.setTotalMarks(Integer.valueOf(((BigInteger) obj[0]).intValue() * ((Byte) obj[2])));
						subjectTotalMarks += (((BigInteger) obj[0]).intValue() * ((Byte) obj[2]));

						if (sr != null) {
							List<StudentSubjectQuizQuestion> ssqqList = studentSubjectQuizQuestionRepository
									.findByLatestQuizQuestionByCorrectAnswer(sr.getIdStudentSubjectQuiz(),
											obj[1].toString(), Boolean.TRUE, ((Byte) obj[2]).shortValue(),
											obj[3].toString());

							vqoDTO.setCorrectAnswer(ssqqList.size());

							if (obj[1].toString().equalsIgnoreCase("Descriptive")) {
								studentTotalMarks += ssqqList.stream()
										.mapToDouble(q -> Float.valueOf(q.getScoredMarks())).sum();
								vqoDTO.setTotalMarksScored(Float.valueOf((float) ssqqList.stream()
										.mapToDouble(q -> Float.valueOf(q.getScoredMarks())).sum()));

							} else {
								vqoDTO.setTotalMarksScored(Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size()));
								studentTotalMarks += Float.valueOf(((Byte) obj[2]) * (float) ssqqList.size());
							}

						} else {
							vqoDTO.setCorrectAnswer(0);
							vqoDTO.setTotalMarksScored(0);
						}

						vctQuizOverAllReviewDTOList.add(vqoDTO);

					}

					sqrrDTO.setOverAllTotalMarks((float) subjectTotalMarks);
					sqrrDTO.setOverAllTotalMarksScored(studentTotalMarks);
					sqrrDTO.setOverAllReview(vctQuizOverAllReviewDTOList);
					ssrrList.add(sqrrDTO);

				}

			}

			finalResponseDataMap.put("subjectReview", ssrrList);
			result.setData(finalResponseDataMap);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

			return result;

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());

		}

		return result;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	// This method created for testing purpose
	// @PostConstruct
	public void generateOverallRankingList() {
		List<String> errorLogList = new ArrayList<>();

		try {

			Long idState = 1L;

			List<ClassStandard> classStandards = classRepository.findAll();

			for (ClassStandard cs : classStandards) {
				List<Syllabus> syllabusList = syllabusRepository.findAll();

				for (Syllabus sy : syllabusList) {
					System.out.println("Hello im ranking");
					List<Long> userVCTProducts = productRepository
							.getIdByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(13L,
									cs.getIdClassStandard(), sy.getIdSyllabus(), idState, Boolean.TRUE);

					if (!userVCTProducts.isEmpty()) {
						int totalMarks = 300;

						List<SubjectRankingDTO> studentRanking = studentSubjectQuizRepository
								.getSubjectListRankingWithMarkConstrain(cs.getIdClassStandard(), idState,
										sy.getIdSyllabus(), null);

						// filtered result which contains all subjects and user data.
						List<SubjectRankingDTO> subjectRankingDTOFilteredList = studentRanking.stream()
								.filter(distinctByKey(s -> s.getIdStudentSubscr())).collect(Collectors.toList());

						// fetching unique user id for ranking
						List<Long> uniqueIds = subjectRankingDTOFilteredList.stream()
								.map(SubjectRankingDTO::getIdVlUser).distinct().collect(Collectors.toList());

						// creating ranking List
						List<StudentVCTMarksDTO> rankingList = new ArrayList<>();

						for (Long userId : uniqueIds) {

							Float totalMarksScored = 0f;

							int subjectCount = 0;

							int maxSubjectCount = 6;

							for (SubjectRankingDTO srd : subjectRankingDTOFilteredList) {
								if (srd.getIdVlUser().equals(userId) && subjectCount < maxSubjectCount) {
									totalMarksScored += (srd.getQuizScore() / 2);
									subjectCount++;
								}

								if (subjectCount > maxSubjectCount)
									break;
							}

							SubjectRankingDTO failedSubject = subjectRankingDTOFilteredList.stream()
									.filter(s -> (s.getQuizScore() < 35 && s.getIdVlUser().equals(userId))).findFirst()
									.orElse(null);

							StudentVCTMarksDTO svmDTO = new StudentVCTMarksDTO();

							if (failedSubject != null) {
								svmDTO.setQualiefiedForGlobalRanking(false);
							} else {

								svmDTO.setQualiefiedForGlobalRanking(true);
							}

							svmDTO.setTotalMarks((float) totalMarks);
							svmDTO.setTotalMarkeScored(totalMarksScored);
							svmDTO.setPercentageFloat((Float.valueOf(totalMarksScored / totalMarks) * 100));
							svmDTO.setUserSurId(userId);
							rankingList.add(svmDTO);

						}

						// sorting ranking list based on totalPercentage
						rankingList.sort(Comparator.comparing(StudentVCTMarksDTO::getPercentageFloat).reversed());

						rankingList.stream().forEach(s -> {
							User u = userRepository.findByUserSurId(s.getUserSurId());
							State st = stateRepository.findByIdState(idState);
							String temp = "|" + u.getFirstName() + "|" + s.getPercentageFloat() + "|" + u.getUsername()
									+ "|" + u.getUserSurId() + "|" + st.getState() + "|" + cs.getClassStandadName()
									+ "|" + sy.getSyllabusName() + "|" + s.getTotalMarkeScored() + "|"
									+ s.getTotalMarks();

							errorLogList.add(temp);
						});
					}

				}

			}

		}

		catch (Exception e) {

		} finally {

			String fileName = "Ranking" + Instant.now() + ".txt";
			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/quiz", fileName, logFile);

				boolean isDeletedLogFile = logFile.delete();
				log.info("Logs file deleted from the system : " + isDeletedLogFile);

			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}

	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	/*
	 * @author Abdul elahi
	 * 
	 * batch upload functionality for Academic Quiz
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> uploadBatchAcademicQuizQuestion(MultipartFile batchFile, Long idQuiz) {

		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		File file = imageUploadService.convertMultiPartFileToFile(batchFile);
		List<String> errorLogList = new ArrayList<String>();

		try {
			errorLogList.add("Quiz Creation Batch Process started at : " + new Date());
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				errorLogList.add("Unathorized user access.");
				throw new AppException("Unathorized user access.");
			}

			errorLogList.add("File Uploaded by user : " + userPrincipal.getUserSurId() + " .");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (fileExtension.isPresent() && !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extentions not supported, Please upload .csv file");
				throw new AppException("Invalid file formate.");
			}

			// fetch respective quiz data
			Quiz quiz = quizRepository.findByIdQuiz(idQuiz);

			// validate quiz data
			if (quiz == null)
				throw new NullPointerException("No Quiz FOund !");

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// generated CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			// CSV header validations
			readAll.forEach(o -> {
				Map<String, String> questionData = (Map<String, String>) o;

				if (!questionData.containsKey("questionId")) {
					errorLogList.add(
							"Missing Column 'questionId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("question")) {
					errorLogList
							.add("Missing Column 'question' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("answer")) {
					errorLogList
							.add("Missing Column 'answer' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("fieldId")) {
					errorLogList
							.add("Missing Column 'fieldId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("correct_flag")) {
					errorLogList.add(
							"Missing Column 'correct_flag' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("questionType")) {
					errorLogList.add(
							"Missing Column 'questionType' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionTitle")) {
					errorLogList.add(
							"Missing Column 'questionTitle' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("numberOptions")) {
					errorLogList.add(
							"Missing Column 'numberOptions' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("image_url")) {
					errorLogList
							.add("Missing Column 'image_url' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionId") || !questionData.containsKey("question")
						|| !questionData.containsKey("answer") || !questionData.containsKey("fieldId")
						|| !questionData.containsKey("correct_flag") || !questionData.containsKey("questionType")
						|| !questionData.containsKey("numberOptions") || !questionData.containsKey("image_url")
						|| !questionData.containsKey("questionTitle")) {
					throw new AppException("Invalid or missing header.");
				}

			});

			for (Object o : readAll) {
				Map<String, Object> temp = (Map<String, Object>) o;
				if (temp.get("questionId").toString().isEmpty()) {
					errorLogList.add(
							"Missing 'questionId' for certain questions  , Please make sure whether all the question contains respective id.");
					throw new AppException("Missing 'questionId'  for certain questions");
				}
			}

			// get unique quiz question id's
			List<Object> uniqueQuestionIdList = readAll.stream().map(o -> ((Map<String, Object>) o).get("questionId"))
					.distinct().collect(Collectors.toList());

			errorLogList.add("Total unique questions found: " + uniqueQuestionIdList.size() + ".");

			List<QuizQuestion> questionList = new ArrayList<>();

			for (Object idQuestion : uniqueQuestionIdList) {
				Integer id = Integer.valueOf((String) idQuestion);

				List<Map<String, String>> tempQuestionList = new ArrayList<Map<String, String>>();

				for (Object rowObject : readAll) {
					Map<String, String> tempRow = (Map<String, String>) rowObject;

					if (tempRow.get("questionId").equals(id.toString())) {
						tempQuestionList.add(tempRow);
					}

				}

				QuizQuestion quizQuestion = new QuizQuestion();
				List<Answer> answerList = new ArrayList<Answer>();

				for (int i = 0; i < tempQuestionList.size(); i++) {

					Map<String, String> questionData = tempQuestionList.get(i);

					if (questionData.get("numberOptions").isEmpty()) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options cannot be empty.");
						quizQuestion = null;
						break;
					}

					if (tempQuestionList.size() != Long.valueOf(questionData.get("numberOptions"))) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options is not same as numbers answers provided. ");
						quizQuestion = null;
						break;
					}

					if (i == 0) {

//						if (questionData.get("marks").isEmpty()) {
//							errorLogList.add("Error occured at " + "questionId:" + id + "." + "Marks cannot be empty.");
//							quizQuestion = null;
//							break;
//						}

						if (questionData.get("questionTitle").isEmpty()) {
							errorLogList.add(
									"Error occured at " + "questionId:" + id + "." + "questionTitle cannot be empty.");
							quizQuestion = null;
							break;
						}

						if (questionData.get("questionType").trim().equalsIgnoreCase("MultiSelect")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Objective")) {

							if (questionData.get("question").isEmpty()) {
								errorLogList.add("Error occured at " + "questionId:" + id
										+ " in Answer field at fieldId:" + (i + 1) + "." + "Question Cannot be empty.");
								quizQuestion = null;
								break;
							}

							quizQuestion.setNumberOptions(Long.valueOf(questionData.get("numberOptions")));

							quizQuestion.setQuestionType(questionData.get("questionType").trim());

							quizQuestion.setQuestionActiveFlag(true);
							quizQuestion.setMarks((short) 1);
							quizQuestion.setQuestionTitle(questionData.get("questionTitle").trim());
							quizQuestion.setQuestionText(questionData.get("question").trim());
							if (questionData.get("image_url") != null) {
								if (!questionData.get("image_url").trim().isEmpty()) {
									quizQuestion.setDiagramLoc(questionData.get("image_url"));
								}
							}
						} else {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Incorrect Question Type.");
							quizQuestion = null;
							break;
						}

					}

					if (questionData.get("answer").length() < 256) {

						if (questionData.get("answer").isEmpty()) {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Answer cannot be empty.");
							quizQuestion = null;
							break;
						}
						Answer answer = new Answer();
						answer.setFieldId(Integer.toString(i));
						answer.setCorrectValueFlag(Boolean.parseBoolean(questionData.get("correct_flag")));
						answer.setTextFieldValue(questionData.get("answer"));

						answerList.add(answer);
					} else {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + " Answer Exceeds Maximum Characters.");
						quizQuestion = null;
						break;
					}

				}

				if (quizQuestion != null) {
					quizQuestion.setQuiz(quiz);
					quizQuestion.setAnswers(answerList);
					questionList.add(quizQuestion);
				}

			}

			List<QuizQuestion> finaList = quizQuestionRepository.saveAll(questionList);

			// errorLogList.add("Total Marks : " + questionList.stream().mapToInt(m ->
			// m.getMarks()).sum() + ".");
			errorLogList.add("Total no of records created: " + finaList.size() + ".");
			errorLogList.add("Total no of records rejected: " + (uniqueQuestionIdList.size() - finaList.size()) + ".");
			errorLogList.add("Quiz Creation Batch Process ended at : " + new Date());
			dataMap.put("created_data", finaList);
			dataMap.put("log", errorLogList);

			if (finaList.size() == 0) {
				result.setData(dataMap);
				result.setMessage("Request Successfull.");
				result.setStatusCode(HttpStatus.OK.value());
			} else {
				result.setData(dataMap);
				result.setMessage("Quiz Questions Created Successfully.");
				result.setStatusCode(HttpStatus.CREATED.value());
			}

		} catch (Exception e) {

			errorLogList.add("Error Occured: " + e.getLocalizedMessage());
			errorLogList.add("Quiz Creation Batch Process exited at : " + new Date());
			dataMap.put("created_data", null);

			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {
			String fileName = "QuizBatchUploadLog" + Instant.now() + ".txt";
			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/quiz", fileName, logFile);

				boolean isDeletedLogFile = logFile.delete();
				log.info("Logs file and batch file deleted from the system : " + isDeletedLogFile);

				boolean isDeletedFile = file.delete();
				log.info(" file deleted from the system : " + isDeletedFile);

			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}

		return result;
	}

	/*
	 * @author Abdul elahi
	 * 
	 * batch upload functionality for ECA Quiz
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> uploadBatchECAQuizQuestion(MultipartFile batchFile, Long idQuiz) {

		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		File file = imageUploadService.convertMultiPartFileToFile(batchFile);
		List<String> errorLogList = new ArrayList<String>();

		try {
			errorLogList.add("Quiz Creation Batch Process started at : " + new Date());
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				errorLogList.add("Unathorized user access.");
				throw new AppException("Unathorized user access.");
			}

			errorLogList.add("File Uploaded by user : " + userPrincipal.getUserSurId() + " .");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (fileExtension.isPresent() && !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extentions not supported, Please upload .csv file");
				throw new AppException("Invalid file formate.");
			}

			// fetch respective quiz data
			Quiz quiz = quizRepository.findByIdQuiz(idQuiz);

			// validate quiz data
			if (quiz == null)
				throw new NullPointerException("No Quiz FOund !");

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// generated CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			// CSV header validations
			readAll.forEach(o -> {
				Map<String, String> questionData = (Map<String, String>) o;

				if (!questionData.containsKey("questionId")) {
					errorLogList.add(
							"Missing Column 'questionId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("question")) {
					errorLogList
							.add("Missing Column 'question' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("answer")) {
					errorLogList
							.add("Missing Column 'answer' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("fieldId")) {
					errorLogList
							.add("Missing Column 'fieldId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("correct_flag")) {
					errorLogList.add(
							"Missing Column 'correct_flag' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("questionType")) {
					errorLogList.add(
							"Missing Column 'questionType' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionTitle")) {
					errorLogList.add(
							"Missing Column 'questionTitle' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("numberOptions")) {
					errorLogList.add(
							"Missing Column 'numberOptions' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("image_url")) {
					errorLogList
							.add("Missing Column 'image_url' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionId") || !questionData.containsKey("question")
						|| !questionData.containsKey("answer") || !questionData.containsKey("fieldId")
						|| !questionData.containsKey("correct_flag") || !questionData.containsKey("questionType")
						|| !questionData.containsKey("numberOptions") || !questionData.containsKey("image_url")
						|| !questionData.containsKey("questionTitle")) {
					throw new AppException("Invalid or missing header.");
				}

			});

			for (Object o : readAll) {
				Map<String, Object> temp = (Map<String, Object>) o;
				if (temp.get("questionId").toString().isEmpty()) {
					errorLogList.add(
							"Missing 'questionId' for certain questions  , Please make sure whether all the question contains respective id.");
					throw new AppException("Missing 'questionId'  for certain questions");
				}
			}

			// get unique quiz question id's
			List<Object> uniqueQuestionIdList = readAll.stream().map(o -> ((Map<String, Object>) o).get("questionId"))
					.distinct().collect(Collectors.toList());

			errorLogList.add("Total unique questions found: " + uniqueQuestionIdList.size() + ".");

			List<QuizQuestion> questionList = new ArrayList<>();

			for (Object idQuestion : uniqueQuestionIdList) {
				Integer id = Integer.valueOf((String) idQuestion);

				List<Map<String, String>> tempQuestionList = new ArrayList<Map<String, String>>();

				for (Object rowObject : readAll) {
					Map<String, String> tempRow = (Map<String, String>) rowObject;

					if (tempRow.get("questionId").equals(id.toString())) {
						tempQuestionList.add(tempRow);
					}

				}

				QuizQuestion quizQuestion = new QuizQuestion();
				List<Answer> answerList = new ArrayList<Answer>();

				for (int i = 0; i < tempQuestionList.size(); i++) {

					Map<String, String> questionData = tempQuestionList.get(i);

					if (questionData.get("numberOptions").isEmpty()) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options cannot be empty.");
						quizQuestion = null;
						break;
					}

					if (tempQuestionList.size() != Long.valueOf(questionData.get("numberOptions"))) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options is not same as numbers answers provided. ");
						quizQuestion = null;
						break;
					}

					if (i == 0) {

						if (questionData.get("questionTitle").isEmpty()) {
							errorLogList.add(
									"Error occured at " + "questionId:" + id + "." + "questionTitle cannot be empty.");
							quizQuestion = null;
							break;
						}

						if (questionData.get("questionType").trim().equalsIgnoreCase("MultiSelect")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Objective")) {

							if (questionData.get("question").isEmpty()) {
								errorLogList.add("Error occured at " + "questionId:" + id
										+ " in Answer field at fieldId:" + (i + 1) + "." + "Question Cannot be empty.");
								quizQuestion = null;
								break;
							}

							quizQuestion.setNumberOptions(Long.valueOf(questionData.get("numberOptions")));

							quizQuestion.setQuestionType(questionData.get("questionType").trim());

							quizQuestion.setQuestionActiveFlag(true);
							quizQuestion.setMarks((short) 1);
							quizQuestion.setQuestionTitle(questionData.get("questionTitle").trim());
							quizQuestion.setQuestionText(questionData.get("question").trim());
							if (questionData.get("image_url") != null) {
								if (!questionData.get("image_url").trim().isEmpty()) {
									quizQuestion.setDiagramLoc(questionData.get("image_url"));
								}
							}
						} else {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Incorrect Question Type.");
							quizQuestion = null;
							break;
						}

					}

					if (questionData.get("answer").length() < 256) {

						if (questionData.get("answer").isEmpty()) {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Answer cannot be empty.");
							quizQuestion = null;
							break;
						}
						Answer answer = new Answer();
						answer.setFieldId(Integer.toString(i));
						answer.setCorrectValueFlag(Boolean.parseBoolean(questionData.get("correct_flag")));
						answer.setTextFieldValue(questionData.get("answer"));

						answerList.add(answer);
					} else {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + " Answer Exceeds Maximum Characters.");
						quizQuestion = null;
						break;
					}

				}

				if (quizQuestion != null) {
					quizQuestion.setQuiz(quiz);
					quizQuestion.setAnswers(answerList);
					questionList.add(quizQuestion);
				}

			}

			List<QuizQuestion> finaList = quizQuestionRepository.saveAll(questionList);

			// errorLogList.add("Total Marks : " + questionList.stream().mapToInt(m ->
			// m.getMarks()).sum() + ".");
			errorLogList.add("Total no of records created: " + finaList.size() + ".");
			errorLogList.add("Total no of records rejected: " + (uniqueQuestionIdList.size() - finaList.size()) + ".");
			errorLogList.add("Quiz Creation Batch Process ended at : " + new Date());
			dataMap.put("created_data", finaList);
			dataMap.put("log", errorLogList);

			if (finaList.size() == 0) {
				result.setData(dataMap);
				result.setMessage("Request Successfull.");
				result.setStatusCode(HttpStatus.OK.value());
			} else {
				result.setData(dataMap);
				result.setMessage("Quiz Questions Created Successfully.");
				result.setStatusCode(HttpStatus.CREATED.value());
			}

		} catch (Exception e) {

			errorLogList.add("Error Occured: " + e.getLocalizedMessage());
			errorLogList.add("Quiz Creation Batch Process exited at : " + new Date());
			dataMap.put("created_data", null);

			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {

			String fileName = "QuizBatchUploadLog" + Instant.now() + ".txt";
			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/quiz", fileName, logFile);

				boolean isDeletedLogFile = logFile.delete();
				boolean isDeletedFile = file.delete();
				log.info("Logs file and batch file deleted from the system : " + isDeletedLogFile + " - "
						+ isDeletedFile);

			} catch (IOException e) {
				log.error(e.getMessage());
			}

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> validateBatchUploadCSV(MultipartFile batchFile, Long idQuiz) {
		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		File file = imageUploadService.convertMultiPartFileToFile(batchFile);
		List<String> errorLogList = new ArrayList<String>();

		try {
			errorLogList.add("Quiz Creation Batch Process started at : " + new Date());
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				errorLogList.add("Unathorized user access.");
				throw new AppException("Unathorized user access.");
			}

			errorLogList.add("File Uploaded by user : " + userPrincipal.getUserSurId() + " .");
			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (fileExtension.isPresent() && !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extentions not supported, Please upload .csv file");
				throw new AppException("Invalid file formate.");
			}

			// fetch respective quiz data
			Quiz quiz = quizRepository.findByIdQuiz(idQuiz);

			// validate quiz data
			if (quiz == null)
				throw new NullPointerException("No Quiz FOund !");

			// creating CSV schema
			CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
			CsvMapper csvMapper = new CsvMapper();

			// generated CSV data
			List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(file).readAll();

			// CSV header validations
			readAll.forEach(o -> {
				Map<String, String> questionData = (Map<String, String>) o;

				if (!questionData.containsKey("questionId")) {
					errorLogList.add(
							"Missing Column 'questionId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("question")) {
					errorLogList
							.add("Missing Column 'question' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("answer")) {
					errorLogList
							.add("Missing Column 'answer' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("fieldId")) {
					errorLogList
							.add("Missing Column 'fieldId' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("correct_flag")) {
					errorLogList.add(
							"Missing Column 'correct_flag' , Please make sure whether your are using valid template");

				}
				if (!questionData.containsKey("questionType")) {
					errorLogList.add(
							"Missing Column 'questionType' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionTitle")) {
					errorLogList.add(
							"Missing Column 'questionTitle' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("numberOptions")) {
					errorLogList.add(
							"Missing Column 'numberOptions' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("image_url")) {
					errorLogList
							.add("Missing Column 'image_url' , Please make sure whether your are using valid template");

				}

				if (!questionData.containsKey("questionId") || !questionData.containsKey("question")
						|| !questionData.containsKey("answer") || !questionData.containsKey("fieldId")
						|| !questionData.containsKey("correct_flag") || !questionData.containsKey("questionType")
						|| !questionData.containsKey("numberOptions") || !questionData.containsKey("image_url")
						|| !questionData.containsKey("questionTitle")) {
					throw new AppException("Invalid or missing header.");
				}

			});

			for (Object o : readAll) {
				Map<String, Object> temp = (Map<String, Object>) o;
				if (temp.get("questionId").toString().isEmpty()) {
					errorLogList.add(
							"Missing 'questionId' for certain questions  , Please make sure whether all the question contains respective id.");
					throw new AppException("Missing 'questionId'  for certain questions");
				}
			}

			// get unique quiz question id's
			List<Object> uniqueQuestionIdList = readAll.stream().map(o -> ((Map<String, Object>) o).get("questionId"))
					.distinct().collect(Collectors.toList());

			errorLogList.add("Total unique questions found: " + uniqueQuestionIdList.size() + ".");

			List<QuizQuestion> questionList = new ArrayList<>();

			for (Object idQuestion : uniqueQuestionIdList) {
				Integer id = Integer.valueOf((String) idQuestion);

				List<Map<String, String>> tempQuestionList = new ArrayList<Map<String, String>>();

				for (Object rowObject : readAll) {
					Map<String, String> tempRow = (Map<String, String>) rowObject;

					if (tempRow.get("questionId").equals(id.toString())) {
						tempQuestionList.add(tempRow);
					}

				}

				QuizQuestion quizQuestion = new QuizQuestion();
				List<Answer> answerList = new ArrayList<Answer>();

				for (int i = 0; i < tempQuestionList.size(); i++) {

					Map<String, String> questionData = tempQuestionList.get(i);

					if (questionData.get("numberOptions").isEmpty()) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options cannot be empty.");
						quizQuestion = null;
						break;
					}

					if (tempQuestionList.size() != Long.valueOf(questionData.get("numberOptions"))) {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + "Number of Options is not same as numbers answers provided. ");
						quizQuestion = null;
						break;
					}

					if (i == 0) {

						if (questionData.get("questionTitle").isEmpty()) {
							errorLogList.add(
									"Error occured at " + "questionId:" + id + "." + "questionTitle cannot be empty.");
							quizQuestion = null;
							break;
						}

						if (questionData.get("questionType").trim().equalsIgnoreCase("MultiSelect")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Objective")) {

							if (questionData.get("question").isEmpty()) {
								errorLogList.add("Error occured at " + "questionId:" + id
										+ " in Answer field at fieldId:" + (i + 1) + "." + "Question Cannot be empty.");
								quizQuestion = null;
								break;
							}

							quizQuestion.setNumberOptions(Long.valueOf(questionData.get("numberOptions")));

							quizQuestion.setQuestionType(questionData.get("questionType").trim());

							quizQuestion.setQuestionActiveFlag(true);
							quizQuestion.setMarks((short) 1);
							quizQuestion.setQuestionTitle(questionData.get("questionTitle").trim());
							quizQuestion.setQuestionText(questionData.get("question").trim());
							if (questionData.get("image_url") != null) {
								if (!questionData.get("image_url").trim().isEmpty()) {
									quizQuestion.setDiagramLoc(questionData.get("image_url"));
								}
							}
						} else {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Incorrect Question Type.");
							quizQuestion = null;
							break;
						}

					}

					if (questionData.get("answer").length() < 256) {

						if (questionData.get("answer").isEmpty()) {
							errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
									+ (i + 1) + "." + " Answer cannot be empty.");
							quizQuestion = null;
							break;
						}
						Answer answer = new Answer();
						answer.setFieldId(Integer.toString(i));
						answer.setCorrectValueFlag(Boolean.parseBoolean(questionData.get("correct_flag")));
						answer.setTextFieldValue(questionData.get("answer"));

						answerList.add(answer);
					} else {
						errorLogList.add("Error occured at " + "questionId:" + id + " in Answer field at fieldId:"
								+ (i + 1) + "." + " Answer Exceeds Maximum Characters.");
						quizQuestion = null;
						break;
					}

				}

				if (quizQuestion != null) {
					quizQuestion.setQuiz(quiz);
					quizQuestion.setAnswers(answerList);
					questionList.add(quizQuestion);
				}

			}

			errorLogList.add("Total Marks : " + questionList.stream().mapToInt(m -> m.getMarks()).sum() + ".");
			errorLogList.add("Total no of records valid: " + questionList.size() + ".");
			errorLogList
					.add("Total no of records Invalid: " + (uniqueQuestionIdList.size() - questionList.size()) + ".");
			errorLogList.add("Quiz validation batch process ended at : " + new Date());
			dataMap.put("log", errorLogList);

			result.setData(dataMap);
			result.setMessage("Quiz Questions Validation Completed.");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception e) {

			errorLogList.add("Error Occured: " + e.getLocalizedMessage());
			errorLogList.add("Quiz Creation Batch Process exited at : " + new Date());
			dataMap.put("created_data", null);

			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {

			String fileName = "QuizBatchUploadLog" + Instant.now() + ".txt";
			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)) {

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/quiz", fileName, logFile);

				boolean isDeletedLogFile = logFile.delete();
				boolean isDeletedFile = file.delete();
				log.info("Logs file and batch file deleted from the system : " + isDeletedLogFile + " - "
						+ isDeletedFile);

			} catch (IOException e) {
				log.error(e.getLocalizedMessage());
			}

		}

		return result;
	}

	@Override
	public Document<List<StudentSubjectQuizAnalyticsDTO>> getStudentSubjectQuizAnalytics(Long idClassStandard,
			Long idState, Long idSyllabus, Long idSubject, Instant from, Instant to, String type) {
		Document<List<StudentSubjectQuizAnalyticsDTO>> result = new Document<>();
		List<StudentSubjectQuizAnalyticsDTO> analyticsList = new ArrayList<>();

		try {
			if (from != null && to != null) {
				ZonedDateTime startDate = from.atZone(ZoneId.systemDefault());
				ZonedDateTime endDate = to.atZone(ZoneId.systemDefault());

				switch (type.toUpperCase()) {
				case "WEEKLY":
					// Iterate over the weeks in the range
					while (!startDate.isAfter(endDate)) {
						ZonedDateTime endOfWeek = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
								.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
						if (endOfWeek.isAfter(endDate)) {
							endOfWeek = endDate;
						}

						Instant startInstant = startDate.toInstant();
						Instant endInstant = endOfWeek.toInstant();

						Long count = studentSubjectQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubject, idClassStandard, idState, idSyllabus);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));

						startDate = endOfWeek.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					}
					break;

				case "MONTHLY":
					// Iterate over the months in the range
					while (!startDate.isAfter(endDate)) {
						ZonedDateTime endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23)
								.withMinute(59).withSecond(59).withNano(999999999);
						if (endOfMonth.isAfter(endDate)) {
							endOfMonth = endDate;
						}

						Instant startInstant = startDate.toInstant();
						Instant endInstant = endOfMonth.toInstant();

						Long count = studentSubjectQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubject, idClassStandard, idState, idSyllabus);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));

						startDate = endOfMonth.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid type: " + type);
				}
			} else {
				ZonedDateTime now = ZonedDateTime.now();

				switch (type.toUpperCase()) {
				case "WEEKLY":
					// Find the last Sunday
					ZonedDateTime lastSunday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).withHour(0)
							.withMinute(0).withSecond(0).withNano(0);

					// Iterate over the last 12 weeks
					for (int i = 10; i >= 0; i--) {
						ZonedDateTime endOfWeek = lastSunday.minusWeeks(i);
						ZonedDateTime startOfWeek = endOfWeek.minusWeeks(1);

						Instant startInstant = startOfWeek.toInstant();
						Instant endInstant = endOfWeek.toInstant();

						Long count = studentSubjectQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubject, idClassStandard, idState, idSyllabus);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));
					}
					break;

				case "MONTHLY":
					// Find the start of the current month
					ZonedDateTime startOfMonth = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

					// Iterate over the last 12 months
					for (int i = 12; i >= 0; i--) {
						ZonedDateTime endOfMonth = startOfMonth.minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
						ZonedDateTime startOfPreviousMonth = endOfMonth.minusMonths(1)
								.with(TemporalAdjusters.firstDayOfMonth());

						Instant startInstant = startOfPreviousMonth.toInstant();
						Instant endInstant = endOfMonth.toInstant();

						Long count = studentSubjectQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubject, idClassStandard, idState, idSyllabus);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid type: " + type);
				}
			}

			result.setData(analyticsList);
			result.setMessage("Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<?> getStudentSubjectChapterQuizAnalytics(Long idSubjectChapter, Instant from, Instant to,
			String type) {

		Document<List<StudentSubjectQuizAnalyticsDTO>> result = new Document<>();
		List<StudentSubjectQuizAnalyticsDTO> analyticsList = new ArrayList<>();
		try {
			if (from != null && to != null) {
				ZonedDateTime startDate = from.atZone(ZoneId.systemDefault());
				ZonedDateTime endDate = to.atZone(ZoneId.systemDefault());

				switch (type.toUpperCase()) {
				case "WEEKLY":
					// Iterate over the weeks in the range
					while (!startDate.isAfter(endDate)) {
						ZonedDateTime endOfWeek = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
								.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
						if (endOfWeek.isAfter(endDate)) {
							endOfWeek = endDate;
						}

						Instant startInstant = startDate.toInstant();
						Instant endInstant = endOfWeek.toInstant();

						Long count = studentChapterQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubjectChapter);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));

						startDate = endOfWeek.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					}
					break;

				case "MONTHLY":
					// Iterate over the months in the range
					while (!startDate.isAfter(endDate)) {
						ZonedDateTime endOfMonth = startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23)
								.withMinute(59).withSecond(59).withNano(999999999);
						if (endOfMonth.isAfter(endDate)) {
							endOfMonth = endDate;
						}

						Instant startInstant = startDate.toInstant();
						Instant endInstant = endOfMonth.toInstant();

						Long count = studentChapterQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubjectChapter);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));

						startDate = endOfMonth.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid type: " + type);
				}
			} else {
				ZonedDateTime now = ZonedDateTime.now();

				switch (type.toUpperCase()) {
				case "WEEKLY":
					// Find the last Sunday
					ZonedDateTime lastSunday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).withHour(0)
							.withMinute(0).withSecond(0).withNano(0);

					// Iterate over the last 12 weeks
					for (int i = 10; i >= 0; i--) {
						ZonedDateTime endOfWeek = lastSunday.minusWeeks(i);
						ZonedDateTime startOfWeek = endOfWeek.minusWeeks(1);

						Instant startInstant = startOfWeek.toInstant();
						Instant endInstant = endOfWeek.toInstant();

						Long count = studentChapterQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubjectChapter);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));
					}
					break;

				case "MONTHLY":
					// Find the start of the current month
					ZonedDateTime startOfMonth = now.withHour(0).withMinute(0).withSecond(0).withNano(0);

					// Iterate over the last 12 months
					for (int i = 12; i >= 0; i--) {
						ZonedDateTime endOfMonth = startOfMonth.minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
						ZonedDateTime startOfPreviousMonth = endOfMonth.minusMonths(1)
								.with(TemporalAdjusters.firstDayOfMonth());

						Instant startInstant = startOfPreviousMonth.toInstant();
						Instant endInstant = endOfMonth.toInstant();

						Long count = studentChapterQuizRepository.findCountByCreatedAtBetweenAndIdSubject(startInstant,
								endInstant, idSubjectChapter);
						analyticsList.add(new StudentSubjectQuizAnalyticsDTO(endInstant, count));
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid type: " + type);
				}
			}

			result.setData(analyticsList);
			result.setMessage("Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<?> getStudentSubjectQuizData(Long idClassStandard, Long idState, Long idSyllabus, Long idSubject,
			Instant from, Instant to, int page, int size) {

		Document<Page<QuizScoreResponseDTO>> result = new Document<>();
		Page<QuizScoreResponseDTO> analyticsPage;
		try {
			Pageable pageable = PageRequest.of(page, size);

			if (from != null && to != null) {
				Instant startInstant = from;
				Instant endInstant = to;

				analyticsPage = studentSubjectQuizRepository.findQuizScoreBetweenCreatedAt(startInstant, endInstant,
						idSubject, idClassStandard, idState, idSyllabus, pageable);
			} else {
				analyticsPage = studentSubjectQuizRepository.findQuizScoreBetweenCreatedAt(idSubject, idClassStandard,
						idState, idSyllabus, pageable);
			}

			result.setData(analyticsPage);
			result.setMessage("Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<?> getStudentSubjectChapterQuizAnalytics(Long idSubjectChapter, Instant from, Instant to, int page,
			int size) {
		Document<Page<QuizScoreResponseDTO>> result = new Document<>();
		Page<QuizScoreResponseDTO> analyticsPage;
		try {
			Pageable pageable = PageRequest.of(page, size);

			if (from != null && to != null) {
				Instant startInstant = from;
				Instant endInstant = to;

				analyticsPage = studentChapterQuizRepository.findQuizScoreBetweenCreatedAt(startInstant, endInstant,
						idSubjectChapter, pageable);
			} else {
				analyticsPage = studentChapterQuizRepository.findQuizScore(idSubjectChapter, pageable);
			}

			result.setData(analyticsPage);
			result.setMessage("Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();

			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@Override
	public Document<ChatbotQADTO> saveChatBotQuestionAnswer(ChatbotQADTO chatbotQADTO) {
		Document<ChatbotQADTO> result = new Document<>();
		try {

			if (chatbotQADTO.getQuestion() == null || chatbotQADTO.getQuestion().trim().isEmpty()) {
				throw new AppException("Question cannot be empty");
			}

			if (chatbotQADTO.getAnswer() == null || chatbotQADTO.getAnswer().trim().isEmpty()) {
				throw new AppException("Answer cannot be empty");
			}

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			ChatbotQA qa = new ChatbotQA();

			qa.setQuestion(chatbotQADTO.getQuestion());
			qa.setAnswer(chatbotQADTO.getAnswer());
			qa.setIdVlUser(loggedInUser.getUserSurId());
			qa.setStatus(Boolean.TRUE);

			qa = chatbotRepository.save(qa);

			ChatbotQADTO response = new ChatbotQADTO();
			response.setIdChatbotQa(qa.getIdChatbotQa());

			result.setData(response);
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Successfull request");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

		return result;
	}

	@Override
	public Document<Page<ChatbotQA>> getChatbotQa(int page, int size, String direction) {
		Document<Page<ChatbotQA>> result = new Document<>();
		Page<ChatbotQA> qaList;
		try {

			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by("createdAt").ascending()
					: Sort.by("createdAt").descending();
			Pageable pageable = PageRequest.of(page, size, sort);

			qaList = chatbotRepository.findByIdVlUserAndStatus(loggedInUser.getUserSurId(), Boolean.TRUE, pageable);

			result.setData(qaList);
			result.setMessage("Fetched Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}

	@Override
	@Transactional
	public Document<String> deleteChatbotQa(Long idChatbotQa) {
		Document<String> result = new Document<>();

		try {

			// chatbotRepository.deleteByIdChatbotQa(idChatbotQa);

			ChatbotQA chatbotQA = chatbotRepository.findByIdChatbotQa(idChatbotQa);

			chatbotQA.setStatus(Boolean.FALSE);

			chatbotRepository.save(chatbotQA);

			result.setData("Deleted Successfully");
			result.setMessage("Deleted Successfully");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return result;

	}

}