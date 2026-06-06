package co.vistafoundation.vlearning.quiz.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.batch.dto.BatchQuizAnswerRequestDTO;
import co.vistafoundation.vlearning.batch.model.BatchQuizMeta;
import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.quiz.dto.AnswerRequestDTO;
import co.vistafoundation.vlearning.quiz.dto.BatchStudentQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.ChatbotQADTO;
import co.vistafoundation.vlearning.quiz.dto.QuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentChapterQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentSubjectQuizAnalyticsDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentSubjectQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.SubjectWiseReportCardDTO;
import co.vistafoundation.vlearning.quiz.model.BatchQuizAssignment;
import co.vistafoundation.vlearning.quiz.model.ChatbotQA;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuiz;

/**
 * 
 * @author Sajini
 *
 */
public interface QuizService {

	@SuppressWarnings("rawtypes")
	public Document getQuestionsByQuizId(Long quizId);

	@SuppressWarnings("rawtypes")
	public Document saveQuiz(QuizQuestion quizQuestion, Long quizId);

	@SuppressWarnings("rawtypes")
	public Document updateQuizQuestion(Long quizQuestionId, QuizQuestion quizQuestion);

	@SuppressWarnings("rawtypes")
	public Document readQuizQuestion(Long quizQuestionId);

	@SuppressWarnings("rawtypes")
	public Document deleteQuizQuestion(Long idQuizQuestion);

	@SuppressWarnings("rawtypes")
	public Document checkAnswers(AnswerRequestDTO answerRequestDTO);

	@SuppressWarnings("rawtypes")
	public Document checkObjectiveAnswers(AnswerRequestDTO answerRequestDTO);

	@SuppressWarnings("rawtypes")
	public Document checkMultipleAnswers(AnswerRequestDTO answerRequestDTO);

	@SuppressWarnings("rawtypes")
	public Document createofflineQuiz(Long idClassStandard, Long idProductLine, Quiz quiz, Long idSyllabus,
			Long idState);

	@SuppressWarnings("rawtypes")
	public Document getAllQuestionsForSubject(Long idProduct, Long idSubject);

	@SuppressWarnings("rawtypes")
	public Document getQuestionsForExamPrep(Long idProduct, Long idSubject);

	@SuppressWarnings("rawtypes")
	public Document submittingAnswers(StudentSubjectQuizDTO studentSubjectQuizDTO);

	@SuppressWarnings("rawtypes")
	public Document submittingChapterwiseAnswers(StudentChapterQuizDTO studentChapterQuizDTO);

	@SuppressWarnings("rawtypes")
	public Document getAllChapterwiseQuestions(Long idProduct, Long idSubject, Long idSubjectChapter);

	@SuppressWarnings("rawtypes")
	public Document assignQuiz(BatchQuizAssignment batchQuizAssignment);

	@SuppressWarnings("rawtypes")
	public Document getQuizDetails(Long idBatch, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getStudentSubjectWiseScore(Long idSubject, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getStudentChapterWiseScore(Long idChapter, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getBatchQuizDetails(Long idBatchQuizAssignment, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getQuizReview(Long idStudentSubjectQuiz);

	@SuppressWarnings("rawtypes")
	public Document saveBatchQuizQuestions(BatchQuizQuestion batchQuizQuestion, Long batchQuizId);

	@SuppressWarnings("rawtypes")
	public Document getBatchQuestionsByBatchQuizId(Long batchQuizId);

	@SuppressWarnings("rawtypes")
	public Document deleteBatchQuizQuestion(Long quizQuestionId);

	@SuppressWarnings("rawtypes")
	public Document saveBatchQuiz(BatchQuizMeta batchQuizMetaBody);

	@SuppressWarnings("rawtypes")
	Document fetchBatchQuizListByIdTeacher(Long idTeacher);

	@SuppressWarnings("rawtypes")
	public Document getChapterWiseQuizReview(Long idStudentChapterQuiz);

	@SuppressWarnings("rawtypes")
	public Document getQuestionsByBatchQuizId(Long idBatchQuizAssignment);

	@SuppressWarnings("rawtypes")
	public Document batchQuizSubmittingAnswers(@Valid BatchStudentQuizDTO batchStudentQuizDTO);

	@SuppressWarnings("rawtypes")
	Document checkBatchAnswers(BatchQuizAnswerRequestDTO batchQuizAnwserRequestDTO);

	@SuppressWarnings("rawtypes")
	public Document getBatchQuizDetailsByIdBatch(Long idBatch);

	@SuppressWarnings("rawtypes")
	public Document getBatchQuizResult(Long idBatch, Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getAcademicQuizDetails(Long idClassStandard, Long idProductLine, Long idSubject,
			Long idSubjectChapter, Long idSyllbus, Long idState, Long idOfflineVideoCourse);

	public Document<List<StudentSubjectQuiz>> getStudentSubjectWiseScoreForAnalytics(Long idSubject,
			Long idStudentSubscription);

	@SuppressWarnings("rawtypes")
	public Document getAllOfflineQuizQuestions(Long idSubject, Long idSubjectChapter, Long idOfflineVideoCourse);

	@SuppressWarnings("rawtypes")
	public Document submittingOfflineQuizAnswers(StudentOfflineQuizDTO studentOfflineQuizDTO);

	public Document<List<StudentOfflineQuizResponseDTO>> getStudentOfflineSubjectWiseScore(Long idSubject,
			Long idChapter, Long idOfflineVideoCourse, Long idStudentSubscription);

	public Document<Map<String, Object>> getStudentofflineQuizReview(Long idStudentOfflineQuiz);

	public Document<Map<String, Object>> getStudentChapterQuizReview(Long idStudentChapterQuiz);

	public Document<List<StudentChapterQuiz>> getStudentOfflineChaptertWiseScore(Long idSubjectChapter,
			Long idStudentSubscription);

	public Document<Quiz> createExamPreparationQuiz(Long idClassStandard, Long idSubject, Long idSyllbus, Long idState,
			Quiz quiz, String category);

	public Document<QuizDTO> getExamPreparationQuizDetails(Long idClassStandard, Long idSubject, Long idSyllbus,
			Long idState, String category);

	public Document<Map<String, Object>> getVCTQuizQuestions(Long idProduct);

	public Document<Map<String, Object>> vctQuizSubmition(StudentSubjectQuizDTO studentSubjectQuizDTO);

	public Document<Map<String, Object>> vctQuizLatestResult(Long idProduct);

	public Document<SubjectWiseReportCardDTO> vctQuizLatestReportCard();

	public Document<Map<String, Object>> vctQuizResultByStudentIdSubejctQuiz(Long idStudentSubjectQuiz);

	public Document<Boolean> checkStudentAttemtedVCT();

	public Document<Map<String, Object>> getVCTStudentResultAndRanking();

	public Document<?> uploadBatchAcademicQuizQuestion(MultipartFile batchFile, Long idQuiz);

	public Document<?> uploadBatchECAQuizQuestion(MultipartFile batchFile, Long idQuiz);

	public Document<?> validateBatchUploadCSV(MultipartFile batchFile, Long idQuiz);
	

	/**
	 * @param idSubjectChapter
	 * @param from
	 * @param to
	 * @param type
	 * @return
	 */
	public Document<?> getStudentSubjectChapterQuizAnalytics(Long idSubjectChapter, Instant from, Instant to,
			String type);

	/**
	 * @param idSubjectChapter
	 * @param from
	 * @param to
	 * @param page
	 * @param size
	 * @return
	 */
	public Document<?> getStudentSubjectChapterQuizAnalytics(Long idSubjectChapter, Instant from, Instant to, int page,
			int size);

	/**
	 * @param idClassStandard
	 * @param idState
	 * @param idSyllabus
	 * @param idSubject
	 * @param from
	 * @param to
	 * @param page
	 * @param size
	 * @return
	 */
	Document<?> getStudentSubjectQuizData(Long idClassStandard, Long idState, Long idSyllabus, Long idSubject,
			Instant from, Instant to, int page, int size);

	/**
	 * @param idClassStandard
	 * @param idState
	 * @param idSyllabus
	 * @param idSubject
	 * @param from
	 * @param to
	 * @param type
	 * @return
	 */
	Document<List<StudentSubjectQuizAnalyticsDTO>> getStudentSubjectQuizAnalytics(Long idClassStandard, Long idState,
			Long idSyllabus, Long idSubject, Instant from, Instant to, String type);

	public Document<ChatbotQADTO> saveChatBotQuestionAnswer(ChatbotQADTO chatbotQADTO);

	public Document<Page<ChatbotQA> > getChatbotQa(int page, int size,String direction);

	public Document<String> deleteChatbotQa(Long idChatbotQa);


}
