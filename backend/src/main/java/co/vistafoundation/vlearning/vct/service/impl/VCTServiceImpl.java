/**
 * 
 */
package co.vistafoundation.vlearning.vct.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.offlinecourse.repository.OfflineVideoCourseRepository;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.repository.AnswerRepository;
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
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;
import co.vistafoundation.vlearning.utils.FileUploadService;
import co.vistafoundation.vlearning.utils.ImageFileUploadService;
import co.vistafoundation.vlearning.vct.service.VCTService;

/**
 * @author naveen kumar
 *
 */
@Service
public class VCTServiceImpl implements VCTService {

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
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentOfflineQuizRepository studentOfflineQuizRepository;

	@Autowired
	StudentOfflineQuizQuestionRepository studentOfflineQuizQuestionRepository;

	@Autowired
	StudentOfflineQuizAnswerRepository studentOfflineQuizAnswerRepository;

	@Autowired
	OfflineVideoCourseRepository offlineVideoCourseRepository;

	@Autowired
	ImageFileUploadService imageUploadService;

	@Autowired
	FileUploadService fileUploadService;

	
	private static final Logger logger = LoggerFactory.getLogger(VCTServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Document<?> uploadBatchQuizQuestion(MultipartFile batchFile, Long idQuiz) {

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

				if (!questionData.containsKey("marks")) {
					errorLogList.add("Missing Column 'marks' , Please make sure whether your are using valid template");

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
						|| !questionData.containsKey("marks") || !questionData.containsKey("questionTitle")) {
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

						if (questionData.get("marks").isEmpty()) {
							errorLogList.add("Error occured at " + "questionId:" + id + "." + "Marks cannot be empty.");
							quizQuestion = null;
							break;
						}

						if (questionData.get("questionTitle").isEmpty()) {
							errorLogList.add(
									"Error occured at " + "questionId:" + id + "." + "questionTitle cannot be empty.");
							quizQuestion = null;
							break;
						}
						
						

						if (questionData.get("questionType").trim().equalsIgnoreCase("TrueOrFalse")
								|| questionData.get("questionType").trim().equalsIgnoreCase("TRUE_OR_FALSE")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Objective")
								|| questionData.get("questionType").trim().equalsIgnoreCase("OneWord")
								|| questionData.get("questionType").trim().equalsIgnoreCase("ProblemSolving")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Descriptive")) {

							if (questionData.get("question").isEmpty()) {
								errorLogList.add("Error occured at " + "questionId:" + id
										+ " in Answer field at fieldId:" + (i + 1) + "." + "Question Cannot be empty.");
								quizQuestion = null;
								break;
							}

							quizQuestion.setNumberOptions(Long.valueOf(questionData.get("numberOptions")));
							
							if (questionData.get("questionType").trim().equalsIgnoreCase("TRUE_OR_FALSE")) 
							{
								quizQuestion.setQuestionType("TrueOrFalse");
							}
							else 
							{
								quizQuestion.setQuestionType(questionData.get("questionType").trim());
							}
							
							quizQuestion.setQuestionActiveFlag(true);
							quizQuestion.setMarks(Short.valueOf(questionData.get("marks")));
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
			
			errorLogList.add("Total Marks : " + questionList.stream().mapToInt(m -> m.getMarks()).sum() + ".");
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
			
			errorLogList.add("Error Occured: "+ e.getLocalizedMessage());
			errorLogList.add("Quiz Creation Batch Process exited at : " + new Date());
			dataMap.put("created_data", null);

			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {
			String fileName = "QuizBatchUploadLog" + Instant.now() + ".txt";
			// log the file to s3 location
			try (FileWriter writer = new FileWriter(fileName)){

				for (String str : errorLogList) {
					writer.write(str + System.lineSeparator());
				}
				writer.close();

				File logFile = new File(fileName);

				fileUploadService.uploadFileToS3Bucket("/logs/creation/quiz", fileName, logFile);

				
				
				boolean isDeletedFile1 = logFile.delete();
				logger.info("Log file deleted from the system : " +isDeletedFile1 );
				boolean isDeletedFile2 = file.delete();
				logger.info("file deleted from the system : " +isDeletedFile2 );

			} catch (IOException e) {
				logger.error(e.getMessage());
			}

		}

		return result;
	}

	public Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Document<?> validateBatchQuizQuestion(MultipartFile batchFile, Long idQuiz) {
		Document<Map<String, Object>> result = new Document<>();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		File file = imageUploadService.convertMultiPartFileToFile(batchFile);
		List<String> errorLogList = new ArrayList<String>();

		try {
			errorLogList.add("Quiz Validation Batch Process started at : " + new Date());
			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();

			}

			if (userPrincipal == null) {
				errorLogList.add("Unathorized user access.");
				throw new AppException("Unathorized user access.");
			}

			// get file extension
			Optional<String> fileExtension = this.getExtensionByStringHandling(file.getName());

			// validate file extension
			if (fileExtension.isPresent() && !fileExtension.get().equalsIgnoreCase("csv")) {
				errorLogList.add("File extentions not supported, Please upload .csv file");
				throw new AppException("Invalid file formate.");
			}

			// fetch respective quiz data
			Quiz quiz = new Quiz();
			quiz = quizRepository.findByIdQuiz(idQuiz);

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

				if (!questionData.containsKey("marks")) {
					errorLogList.add("Missing Column 'marks' , Please make sure whether your are using valid template");

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
						|| !questionData.containsKey("marks") || !questionData.containsKey("questionTitle")) {
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

						if (questionData.get("marks").isEmpty()) {
							errorLogList.add("Error occured at " + "questionId:" + id + "." + "Marks cannot be empty.");
							quizQuestion = null;
							break;
						}

						if (questionData.get("questionTitle").isEmpty()) {
							errorLogList.add(
									"Error occured at " + "questionId:" + id + "." + "questionTitle cannot be empty.");
							quizQuestion = null;
							break;
						}
						
						System.out.println(questionData.get("questionType"));
						System.out.println(questionData.get("questionType").equalsIgnoreCase("ProblemSolving"));

						if (questionData.get("questionType").trim().equalsIgnoreCase("TrueOrFalse")
								|| questionData.get("questionType").trim().equalsIgnoreCase("TRUE_OR_FALSE")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Objective")
								|| questionData.get("questionType").trim().equalsIgnoreCase("OneWord")
								|| questionData.get("questionType").trim().equalsIgnoreCase("ProblemSolving")
								|| questionData.get("questionType").trim().equalsIgnoreCase("Descriptive")) {

							if (questionData.get("question").isEmpty()) {
								errorLogList.add("Error occured at " + "questionId:" + id
										+ " in Answer field at fieldId:" + (i + 1) + "." + "Question Cannot be empty.");
								quizQuestion = null;
								break;
							}

							quizQuestion.setNumberOptions(Long.valueOf(questionData.get("numberOptions")));
							
							if (questionData.get("questionType").trim().equalsIgnoreCase("TRUE_OR_FALSE")) 
							{
								quizQuestion.setQuestionType("TrueOrFalse");
							}
							else 
							{
								quizQuestion.setQuestionType(questionData.get("questionType").trim());
							}
							
							quizQuestion.setQuestionActiveFlag(true);
							quizQuestion.setQuestionText(questionData.get("question"));
							quizQuestion.setMarks(Short.valueOf(questionData.get("marks")));
							quizQuestion.setQuestionTitle(questionData.get("questionTitle").trim());

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
			errorLogList.add("Error Occured: "+ e.getLocalizedMessage());
			errorLogList.add("Quiz Validation Batch Process exited at : " + new Date());
			dataMap.put("log", errorLogList);
			result.setData(dataMap);
			result.setMessage(e.getLocalizedMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		} finally {

			boolean isDeletedLogFile = file.delete();
			logger.info("Logs file deleted from the system : " +isDeletedLogFile);

		}

		return result;
	}

}
