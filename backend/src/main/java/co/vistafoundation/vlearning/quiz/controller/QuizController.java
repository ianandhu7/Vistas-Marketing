package co.vistafoundation.vlearning.quiz.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.batch.model.BatchQuizMeta;
import co.vistafoundation.vlearning.batch.model.BatchQuizQuestion;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.quiz.dto.AnswerRequestDTO;
import co.vistafoundation.vlearning.quiz.dto.BatchStudentQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.ChatbotQADTO;
import co.vistafoundation.vlearning.quiz.dto.StudentChapterQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentOfflineQuizResponseDTO;
import co.vistafoundation.vlearning.quiz.dto.StudentSubjectQuizDTO;
import co.vistafoundation.vlearning.quiz.model.BatchQuizAssignment;
import co.vistafoundation.vlearning.quiz.model.ChatbotQA;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;
import co.vistafoundation.vlearning.quiz.model.StudentSubjectQuiz;
import co.vistafoundation.vlearning.quiz.service.QuizService;

/**
 * 
 * @author Sajini
 *
 */
@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

	@Autowired
	QuizService quizService;

	/**
	 * 
	 * Save a new quiz question.
	 * 
	 * @param quizId       The ID of the quiz the question belongs to.
	 * @param quizQuestion The quiz question to be saved.
	 * @return ResponseEntity containing the response from the quiz service.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/{quizId}/question/create")
	public ResponseEntity<?> saveQuizQuestion(@PathVariable Long quizId,
			@RequestBody @Valid QuizQuestion quizQuestion) {

		Document<?> reponses = quizService.saveQuiz(quizQuestion, quizId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * 
	 * Get all questions for a particular quiz.
	 * 
	 * @param quizId The ID of the quiz to retrieve questions for.
	 * @return ResponseEntity containing the response from the quiz service.
	 */
	@GetMapping("/{quizId}/questions")
	public ResponseEntity<?> getAllQuizes(@PathVariable Long quizId) {

		Document<?> reponses = quizService.getQuestionsByQuizId(quizId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * 
	 * Update an existing quiz question.
	 * 
	 * @param quizQuestionId The ID of the quiz question to be updated.
	 * @param quizQuestion   The updated quiz question.
	 * @return ResponseEntity containing the response from the quiz service.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PutMapping(value = "/question/{quizQuestionId}/update")
	public ResponseEntity<?> updateQuizQuestion(@PathVariable Long quizQuestionId,
			@RequestBody QuizQuestion quizQuestion) {
		Document<?> reponses = quizService.updateQuizQuestion(quizQuestionId, quizQuestion);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * Retrieves the quiz question with the given ID.
	 * 
	 * @param quizQuestionId The ID of the quiz question to retrieve.
	 * @return A ResponseEntity containing the quiz question.
	 */
	@GetMapping(value = "/question/{quizQuestionId}/read")
	public ResponseEntity<?> readQuizQuestion(@PathVariable Long quizQuestionId) {

		Document<?> reponses = quizService.readQuizQuestion(quizQuestionId);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	/**
	 * Deletes the quiz question with the given ID.
	 * 
	 * @param quizQuestionId The ID of the quiz question to delete.
	 * @return A ResponseEntity containing the result of the deletion.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@DeleteMapping(value = "/question/{quizQuestionId}/delete")
	public ResponseEntity<?> deleteQuizQuestion(@PathVariable Long quizQuestionId) {

		Document<?> responses = quizService.deleteQuizQuestion(quizQuestionId);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Checks the answer for a given question.
	 * 
	 * @param answerRequestDTO The DTO containing the user's answer to the question.
	 * @return A ResponseEntity containing the result of the answer check.
	 */
	@PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
	@GetMapping(value = "/answer/check")
	public ResponseEntity<?> checkAnswer(@RequestBody AnswerRequestDTO answerRequestDTO) {

		Document<?> responses = quizService.checkAnswers(answerRequestDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Checks the objective answer for a given question.
	 * 
	 * @param answerRequestDTO The DTO containing the user's objective answer to the
	 *                         question.
	 * @return A ResponseEntity containing the result of the objective answer check.
	 */
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/answer/check-objective")
	public ResponseEntity<?> checkObjectiveAnswer(@RequestBody AnswerRequestDTO answerRequestDTO) {

		Document<?> responses = quizService.checkObjectiveAnswers(answerRequestDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Saves a new quiz question for the quiz with the given ID.
	 * 
	 * @param quizId       The ID of the quiz to add the question to.
	 * @param quizQuestion The new quiz question to save.
	 * @return A ResponseEntity containing the result of the save operation.
	 */
	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/answer/check-mcq")
	public ResponseEntity<?> checkMultipleAnswer(@RequestBody AnswerRequestDTO answerRequestDTO) {

		Document<?> responses = quizService.checkMultipleAnswers(answerRequestDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Retrieves all the questions for the quiz with the given ID.
	 * 
	 * @param quizId The ID of the quiz to retrieve the questions for.
	 * @return A ResponseEntity containing the list of quiz questions.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/createQuiz/academic/{idClassStandard}/{idProductLine}/")
	public ResponseEntity<?> createaccademicQuiz(@PathVariable Long idClassStandard, @PathVariable Long idProductLine,
			@RequestBody Quiz quiz, @RequestParam Long idSyllabus, @RequestParam Long idState) {
		Document<?> responses = quizService.createofflineQuiz(idClassStandard, idProductLine, quiz, idSyllabus,
				idState);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * Updates an existing quiz question with the given ID.
	 * 
	 * @param quizQuestionId The ID of the quiz question to update.
	 * @param quizQuestion   The updated quiz question.
	 * @return A ResponseEntity containing the result of the update operation.
	 */
	@GetMapping(value = "/product/{idProduct}/subject/{idSubject}/questions")
	public ResponseEntity<?> getAllQuestionsForSubject(@PathVariable Long idProduct, @PathVariable Long idSubject) {

		Document<?> responses = quizService.getAllQuestionsForSubject(idProduct, idSubject);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
	@GetMapping(value = "/product/{idProduct}/subject/{idSubject}/exam-prep-questions")
	public ResponseEntity<?> getQuestionsForExamPrep(@PathVariable Long idProduct, @PathVariable Long idSubject) {

		Document<?> responses = quizService.getQuestionsForExamPrep(idProduct, idSubject);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/product/{idProduct}/subject/{idSubject}/chapter/{idSubjectChapter}/questions")
	public ResponseEntity<?> getAllChapterwiseQuestions(@PathVariable Long idProduct, @PathVariable Long idSubject,
			@PathVariable Long idSubjectChapter) {

		Document<?> responses = quizService.getAllChapterwiseQuestions(idProduct, idSubject, idSubjectChapter);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping(value = "/student/submit")
	public ResponseEntity<?> submittingAnswers(@RequestBody @Valid StudentSubjectQuizDTO studentSubjectQuizDTO) {

		Document<?> responses = quizService.submittingAnswers(studentSubjectQuizDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping(value = "/student/submit-chapterwise")
	public ResponseEntity<?> submittingChapterwiseAnswers(
			@RequestBody @Valid StudentChapterQuizDTO studentChapterQuizDTO) {

		Document<?> responses = quizService.submittingChapterwiseAnswers(studentChapterQuizDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@PostMapping(value = "/assignQuiz")
	public ResponseEntity<?> assignQuiz(@RequestBody BatchQuizAssignment batchQuizAssignment) {
		Document<?> responses = quizService.assignQuiz(batchQuizAssignment);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT','ROLE_TEACHER','ROLE_ADMIN')")
	@GetMapping(value = "/batch/{idBatch}/subcription/{idStudentScription}")
	public ResponseEntity<?> getQuizDetails(@PathVariable Long idBatch, @PathVariable Long idStudentSubscription) {

		Document<?> responses = quizService.getQuizDetails(idBatch, idStudentSubscription);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_PARENT')")
	@GetMapping(value = "/getStudentSubjectWiseScore")
	public ResponseEntity<?> getStudentSubjectWiseScore(@RequestParam(required = true) Long idSubject,
			@RequestParam(required = true) Long idStudentSubcription) {
		Document<?> reponses = quizService.getStudentSubjectWiseScore(idSubject, idStudentSubcription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_PARENT')")
	@GetMapping(value = "/getStudentChapterWiseScore")
	public ResponseEntity<?> getStudentChapterWiseScore(@RequestParam(required = true) Long idChapter,
			@RequestParam(required = true) Long idStudentSubscription) {
		Document<?> reponses = quizService.getStudentChapterWiseScore(idChapter, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/getBatchStudentQuiz")
	public ResponseEntity<?> getBatchStudentQuiz(@RequestParam(required = true) Long idStudentSubscription,
			@RequestParam(required = true) Long idBatchQuizAssignment) {
		Document<?> reponses = quizService.getBatchQuizDetails(idBatchQuizAssignment, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/getQuizReview")
	public ResponseEntity<?> getQuizReview(@RequestParam("idStudentSubjectQuiz") Long idStudentSubjectQuiz) {
		Document<?> reponses = quizService.getQuizReview(idStudentSubjectQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@PostMapping(value = "/teacher/add-batchQuiz")
	public ResponseEntity<?> addingBatchQuiz(@RequestBody @Valid BatchQuizMeta batchQuizMeta) {
		Document<?> responses = quizService.saveBatchQuiz(batchQuizMeta);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/teacher/getBatchQuizquestions/{idBatchQuiz}")
	public ResponseEntity<?> getBatchQuizquestions(@PathVariable Long idBatchQuiz) {
		Document<?> responses = quizService.getBatchQuestionsByBatchQuizId(idBatchQuiz);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@DeleteMapping(value = "/teacher/deleteBatchQuizquestions/{idBatchQuizQuestion}")
	public ResponseEntity<?> deleteBatchQuizquestions(@PathVariable Long idBatchQuizQuestion) {
		Document<?> responses = quizService.deleteBatchQuizQuestion(idBatchQuizQuestion);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@PostMapping(value = "/teacher/add-batchQuizQuestions/{idBatchQuiz}")
	public ResponseEntity<?> addingBatchQuizQuetions(@RequestBody @Valid BatchQuizQuestion batchQuizQuestion,
			@PathVariable Long idBatchQuiz) {
		Document<?> responses = quizService.saveBatchQuizQuestions(batchQuizQuestion, idBatchQuiz);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_TEACHER')")
	@GetMapping(value = "/teacher/gettingListQuizes/{idTeacher}")
	public ResponseEntity<?> gettingListQuizes(@PathVariable Long idTeacher) {
		Document<?> responses = quizService.fetchBatchQuizListByIdTeacher(idTeacher);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping(value = "/getChapterWiseQuizReview")
	public ResponseEntity<?> getChapterWiseQuizReview(@RequestParam("idStudentChapterQuiz") Long idStudentChapterQuiz) {
		Document<?> reponses = quizService.getChapterWiseQuizReview(idStudentChapterQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@GetMapping("/batch/quiz/questions")
	public ResponseEntity<?> getAllBatchQuiz(@RequestParam("idBatchQuizAssignment") Long idBatchQuizAssignment) {
		Document<?> reponses = quizService.getQuestionsByBatchQuizId(idBatchQuizAssignment);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping(value = "/batch/student/submit")
	public ResponseEntity<?> batchQuizSubmittingAnswers(@RequestBody @Valid BatchStudentQuizDTO batchStudentQuizDTO) {

		Document<?> responses = quizService.batchQuizSubmittingAnswers(batchStudentQuizDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER','ROLE_PARENT' )")
	@GetMapping(value = "/getBatchQuizDetailsByIdBatch")
	public ResponseEntity<?> getBatchQuizDetailsByIdBatch(@RequestParam("idBatch") Long idBatch) {
		Document<?> reponses = quizService.getBatchQuizDetailsByIdBatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT' )")
	@GetMapping(value = "/getStudentBatchQuizResult")
	public ResponseEntity<?> getBatchQuizResult(@RequestParam("idBatch") Long idBatch,
			@RequestParam("idStudentSubscription") Long idStudentSubscription) {
		Document<?> reponses = quizService.getBatchQuizResult(idBatch, idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER','ROLE_PARENT','ROLE_ADMIN' )")
	@GetMapping(value = "/getQuizDetails/academic/{idClassStandard}/{idProductLine}")
	public ResponseEntity<?> getAcademicQuizDetails(@PathVariable Long idClassStandard,
			@PathVariable Long idProductLine, @RequestParam("idSubject") Long idSubject,
			@RequestParam("idSubjectChapter") Long idSubjectChapter, @RequestParam Long idSyllabus,
			@RequestParam Long idState, @RequestParam Long idOfflineVideoCourse) {
		Document<?> responses = quizService.getAcademicQuizDetails(idClassStandard, idProductLine, idSubject,
				idSubjectChapter, idSyllabus, idState, idOfflineVideoCourse);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PARENT')")
	@GetMapping(value = "/subject/analytics")
	public ResponseEntity<?> getStudentSubjectAnalytics(@RequestParam(required = true) Long idSubject,
			@RequestParam(required = true) Long idStudentSubcription) {
		Document<List<StudentSubjectQuiz>> reponses = quizService.getStudentSubjectWiseScoreForAnalytics(idSubject,
				idStudentSubcription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
	@GetMapping(value = "/getAllOfflineQuizQuestions")
	public ResponseEntity<?> getAllOfflineQuizQuestions(@RequestParam Long idSubject,
			@RequestParam Long idSubjectChapter, @RequestParam Long idOfflineVideoCourse) {

		Document<?> responses = quizService.getAllOfflineQuizQuestions(idSubject, idSubjectChapter,
				idOfflineVideoCourse);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping(value = "/submitOfflineQuiz")
	public ResponseEntity<?> submittingOfflineQuizAnswers(@RequestBody StudentOfflineQuizDTO studentOfflineQuizDTO) {
		Document<?> responses = quizService.submittingOfflineQuizAnswers(studentOfflineQuizDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * @author Naveen Kumar
	 * @param idSubject
	 * @param idChapter
	 * @param idStudentSubcription
	 * @return list of chapter wise quiz result to particular studentsubscriprtion
	 * 
	 * 
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/student-offline-subject-wise-score")
	public ResponseEntity<?> getStudentOfflineSubjectWiseScore(@RequestParam Long idSubject,
			@RequestParam Long idChapter, @RequestParam Long idOfflineVideoCourse,
			@RequestParam Long idStudentSubcription) {
		Document<List<StudentOfflineQuizResponseDTO>> reponses = quizService
				.getStudentOfflineSubjectWiseScore(idSubject, idChapter, idOfflineVideoCourse, idStudentSubcription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * Retrieves a student's chapter-wise score for an offline quiz.
	 *
	 * @param idSubjectChapter      The ID of the subject chapter for which to
	 *                              retrieve the score.
	 * @param idStudentSubscription The ID of the student subscription for which to
	 *                              retrieve the score.
	 * @return A ResponseEntity object containing the score data wrapped in a
	 *         Document object, with an HTTP status code indicating success or
	 *         failure.
	 */

	@GetMapping(value = "/student-chapter-wise-score")
	public ResponseEntity<?> getStudentOfflineChaptertWiseScore(@RequestParam Long idSubjectChapter,
			@RequestParam Long idStudentSubscription) {
		Document<List<StudentChapterQuiz>> reponses = quizService.getStudentOfflineChaptertWiseScore(idSubjectChapter,
				idStudentSubscription);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * @author Naveen Kumar
	 * @param idStudentOfflineQuiz
	 * @return Quiz review question and answer data , for the idStudentOfflineQuiz.
	 */

	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/student-offline-quiz-review")
	public ResponseEntity<?> getStudentOfflineQuizReview(@RequestParam Long idStudentOfflineQuiz) {
		Document<Map<String, Object>> reponses = quizService.getStudentofflineQuizReview(idStudentOfflineQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Retrieves the review of a chapter quiz taken by a student or parent.
	 * 
	 * @param idStudentChapterQuiz The ID of the student's chapter quiz to retrieve.
	 * @return A ResponseEntity object containing the student's chapter quiz review
	 *         in a Document object.
	 */
	@PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_PARENT')")
	@GetMapping(value = "/student-chapter-quiz-review")
	public ResponseEntity<?> getStudentChapterQuizReview(@RequestParam Long idStudentChapterQuiz) {
		Document<Map<String, Object>> reponses = quizService.getStudentChapterQuizReview(idStudentChapterQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Creates an exam preparation quiz.
	 * 
	 * @param idClassStandard The ID of the class standard for the quiz.
	 * @param idSubject       The ID of the subject for the quiz.
	 * @param idSyllabus      The ID of the syllabus for the quiz.
	 * @param idState         The ID of the state for the quiz.
	 * @param category        The category of the quiz.
	 * @param quiz            The Quiz object representing the quiz to be created.
	 * @return A ResponseEntity object containing the response from the quizService
	 *         in a Document object.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN' )")
	@PostMapping(value = "/create-exam-preparation")
	public ResponseEntity<?> createExamPreparationQuiz(@RequestParam Long idClassStandard, @RequestParam Long idSubject,
			@RequestParam Long idSyllabus, @RequestParam Long idState, @RequestParam String category,
			@RequestBody Quiz quiz) {
		Document<?> responses = quizService.createExamPreparationQuiz(idClassStandard, idSubject, idSyllabus, idState,
				quiz, category);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * 
	 * Retrieves the details of an exam preparation quiz.
	 * 
	 * @param idClassStandard The ID of the class standard for the quiz.
	 * @param idSubject       The ID of the subject for the quiz.
	 * @param idSyllabus      The ID of the syllabus for the quiz.
	 * @param idState         The ID of the state for the quiz.
	 * @param category        The category of the quiz.
	 * @return A ResponseEntity object containing the response from the quizService
	 *         in a Document object.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN' )")
	@GetMapping(value = "/exam-preparation-details")
	public ResponseEntity<?> getExamPreparationQuizDetails(@RequestParam Long idClassStandard,
			@RequestParam Long idSubject, @RequestParam Long idSyllabus, @RequestParam Long idState,
			@RequestParam String category) {
		Document<?> responses = quizService.getExamPreparationQuizDetails(idClassStandard, idSubject, idSyllabus,
				idState, category);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * 
	 * Retrieves the questions for a VCT quiz.
	 * 
	 * @author Naveen kumar
	 * 
	 * @param idProduct The ID of the product for the VCT quiz.
	 * 
	 * @return A ResponseEntity object containing the response from the quizService
	 *         in a Document object.
	 */

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/vct-questions")
	public ResponseEntity<?> getQuestionsForVCT(@RequestParam Long idProduct) {

		Document<?> responses = quizService.getVCTQuizQuestions(idProduct);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@PostMapping(value = "/student/vct-submit")
	public ResponseEntity<?> submittingVCTAnswers(@RequestBody @Valid StudentSubjectQuizDTO studentSubjectQuizDTO) {

		Document<?> responses = quizService.vctQuizSubmition(studentSubjectQuizDTO);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/student/vct-review")
	public ResponseEntity<?> vctQuizLatestReviewByProduct(@RequestParam Long idProduct) {

		Document<?> responses = quizService.vctQuizLatestResult(idProduct);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/student/vct-subject-review")
	public ResponseEntity<?> vctReviewByIdStudentSubjectQuiz(@RequestParam Long idStudentSubjectQuiz) {

		Document<?> responses = quizService.vctQuizResultByStudentIdSubejctQuiz(idStudentSubjectQuiz);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/student/vct-report-card")
	public ResponseEntity<?> vctReportCard() {

		Document<?> responses = quizService.vctQuizLatestReportCard();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/student/vct-report-is-exist")
	public ResponseEntity<?> checkVCTExamDataAvailablity() {
		Document<?> responses = quizService.checkStudentAttemtedVCT();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(value = "/student/vct-result-and-ranking")
	public ResponseEntity<?> checkVCTExamResultAndRanking() {
		Document<?> responses = quizService.getVCTStudentResultAndRanking();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	/**
	 * 
	 * validates the csv files data Only users with the role "ROLE_ADMIN" are
	 * authorized to access this endpoint.
	 * 
	 * @author Abdul Elahi
	 * @param batchFile The batch file containing the quiz questions to upload.
	 * @param idQuiz    The ID of the quiz to upload the questions to.
	 * @return A ResponseEntity containing a Document object with the status code
	 *         and any relevant message.
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/validate-batch-upload-files")
	public ResponseEntity<?> validateBatchUploadFile(@RequestParam MultipartFile batchFile, @RequestParam Long idQuiz) {
		Document<?> reponses = quizService.validateBatchUploadCSV(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Uploads a batch of academic quiz questions to a quiz with the specified ID.
	 * Only users with the role "ROLE_ADMIN" are authorized to access this endpoint.
	 * 
	 * @author Abdul Elahi
	 * @param batchFile The batch file containing the quiz questions to upload.
	 * @param idQuiz    The ID of the quiz to upload the questions to.
	 * @return A ResponseEntity containing a Document object with the status code
	 *         and any relevant message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-quiz-upload")
	public ResponseEntity<?> uploadBatchQuizQuestions(@RequestParam MultipartFile batchFile,
			@RequestParam Long idQuiz) {
		Document<?> reponses = quizService.uploadBatchAcademicQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Uploads a batch of ECA quiz questions to a quiz with the specified ID. Only
	 * users with the role "ROLE_ADMIN" are authorized to access this endpoint.
	 * 
	 * @author Abdul Elahi
	 * @param batchFile The batch file containing the quiz questions to upload.
	 * @param idQuiz    The ID of the quiz to upload the questions to.
	 * @return A ResponseEntity containing a Document object with the status code
	 *         and any relevant message.
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-eca-quiz-upload")
	public ResponseEntity<?> UploadBatchQuizForECA(@RequestParam MultipartFile batchFile, @RequestParam Long idQuiz) {
		Document<?> reponses = quizService.uploadBatchECAQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-subject-quiz-analytics")
	public ResponseEntity<?> studentSubjectQuizAnalytics(@RequestParam Long idSubject,
			@RequestParam @Nullable Long idClassStandard,@RequestParam @Nullable Long idState, 
			@RequestParam @Nullable Long idSyllabus, @RequestParam @Nullable Instant from, 
			@RequestParam @Nullable Instant to, @RequestParam @Nullable String type) {
		Document<?> reponses = quizService.getStudentSubjectQuizAnalytics(idClassStandard,idState,idSyllabus, idSubject, from, to, type);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-chapter-quiz-analytics")
	public ResponseEntity<?> studentChapterQuizAnalytics(@RequestParam Long idSubjectChapter,
			@RequestParam @Nullable Instant from, @RequestParam @Nullable Instant to, @RequestParam String type) {
		Document<?> reponses = quizService.getStudentSubjectChapterQuizAnalytics(idSubjectChapter, from, to, type);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-subject-quiz-analytics-data")
	public ResponseEntity<?> getStudentSubjectQuizData(@RequestParam Long idSubject,
			@RequestParam @Nullable Long idClassStandard,@RequestParam @Nullable Long idState, 
			@RequestParam @Nullable Long idSyllabus, @RequestParam @Nullable Instant from, 
			@RequestParam @Nullable Instant to, int page, int size) {
		Document<?> reponses = quizService.getStudentSubjectQuizData(idClassStandard,idState,idSyllabus, idSubject, from, to, page, size);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-chapter-quiz-analytics-data")
	public ResponseEntity<?> getStudentSubjectChapterQuizData(@RequestParam Long idSubjectChapter,
			@RequestParam @Nullable Instant from, @RequestParam @Nullable Instant to, int page, int size) {
		Document<?> reponses = quizService.getStudentSubjectChapterQuizAnalytics(idSubjectChapter, from, to, page,size);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	
	@PostMapping(value = "/save-chatbot-qa")
	public ResponseEntity<Document<ChatbotQADTO>> saveChatBotQuestionAnswer(@RequestBody ChatbotQADTO chatbotQADTO) {

		Document<ChatbotQADTO> reponses = quizService.saveChatBotQuestionAnswer(chatbotQADTO);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	@GetMapping("/get-chatbot-qa")
	public ResponseEntity<Document<Page<ChatbotQA>>> getChatbotQa(  @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String direction) {
		Document<Page<ChatbotQA>> reponses = quizService.getChatbotQa(page,size,direction);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@DeleteMapping("/delete-chatbot-qa")
	public ResponseEntity<Document<String>> deleteChatbotQa(@RequestParam Long   idChatbotQa) {
		Document<String> reponses = quizService.deleteChatbotQa(idChatbotQa);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}
