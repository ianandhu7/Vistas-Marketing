package co.vistafoundation.vlearning.quiz.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.batch.repository.BatchQuizAnswerRepository;
import co.vistafoundation.vlearning.batch.repository.BatchQuizMetaRepository;
import co.vistafoundation.vlearning.batch.repository.BatchQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentQuizQuestionRepository;
import co.vistafoundation.vlearning.batch.repository.BatchStudentRepository;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.repository.AnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.BatchQuizAssignmentRespository;
import co.vistafoundation.vlearning.quiz.repository.BatchStudentQuizRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizAnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizAnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizQuestionRepository;
import co.vistafoundation.vlearning.quiz.repository.StudentSubjectQuizRepository;
import co.vistafoundation.vlearning.quiz.service.QuizService;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.repository.StudentRepository;

/**
 * @author Sajini
 *
 */

@SpringBootTest
class QuizserviceImplTest {
	
	@Autowired
	private QuizService quizService;

	@MockBean
	QuizQuestionRepository quizQuestionRepository;

	@MockBean
	AnswerRepository answerRepository;

	@MockBean
	ProductGroupRepository productGroupRepository;

	@MockBean
	ProductRepository productRepository;

	@MockBean
	SubjectChapterRepository subjectChapterRepository;

	@MockBean
	QuizRepository quizRepository;

	@MockBean
	StudentSubjectQuizRepository studentSubjectQuizRepository;

	@MockBean
	BatchQuizAssignmentRespository batchQuizAssignmentRespository;

	@MockBean
	BatchStudentQuizRepository batchStudentQuizRepository;

	@MockBean
	StudentChapterQuizRepository studentChapterQuizRepository;

	@MockBean
	StudentSubjectQuizQuestionRepository studentSubjectQuizQuestionRepository;

	@MockBean
	StudentChapterQuizAnswerRepository studentChapterQuizAnswerRepository;

	@MockBean
	StudentChapterQuizQuestionRepository studentChapterQuizQuestionRepository;

	@MockBean
	StudentSubjectQuizAnswerRepository studentSubjectQuizAnswerRepository;

	@MockBean
	BatchQuizMetaRepository batchQuizMetaRepository;

	@MockBean
	BatchQuizQuestionRepository batchQuizQuestionRepository;

	@MockBean
	BatchStudentQuizQuestionRepository batchStudentQuizQuestionRepository;

	@MockBean
	StudentSubscriptionRepository studentSubscriptionRepository;

	@MockBean
	SubjectRepository subjectRepository;

	@MockBean
	ClassRepository classRepository;

	@MockBean
	StudentRepository studentRepository;

	@MockBean
	BatchRepository batchRepository;

	@MockBean
	BatchStudentRepository batchStudentRepository;

	@MockBean
	BatchQuizAnswerRepository batchQuizAnswerRepository;

	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		Quiz quiz = new Quiz();
		quiz.setIdSubject(1L);
		quiz.setIdSubjectChapter(1L);
		quiz.setQuizName("Maths_Quiz");
		quiz.setIdProduct(1L);
		
		Quiz quizOutput = new Quiz();
		quizOutput.setIdSubject(1L);
		quizOutput.setIdSubjectChapter(1L);
		quizOutput.setQuizName("Maths_Quiz");
		quizOutput.setIdQuiz(1L);
		quizOutput.setIdProduct(1L);
		
		
		List<Answer> answersListOutput1 = new ArrayList<Answer>();
		
		Answer ansOutput1 = new Answer();
		ansOutput1.setIdQuizQuestion(1L);
		ansOutput1.setIdAnswer(1L);
		ansOutput1.setFieldId("1");
		ansOutput1.setTextFieldValue("11");
		ansOutput1.setCorrectValueFlag(true);
		
		answersListOutput1.add(ansOutput1);
		
		List<Answer> answersListOutput2 = new ArrayList<Answer>();
		
		
		Answer answer1Output = new Answer();
		answer1Output.setIdQuizQuestion(1L);
		answer1Output.setIdAnswer(1L);
		answer1Output.setFieldId("1");
		answer1Output.setTextFieldValue("11");
		answer1Output.setCorrectValueFlag(true);
		
		Answer answer2Output = new Answer();
		answer2Output.setIdQuizQuestion(1L);
		answer2Output.setIdAnswer(2L);
		answer2Output.setFieldId("2");
		answer2Output.setTextFieldValue("12");
		answer2Output.setCorrectValueFlag(false);
		
		Answer answer3Output = new Answer();
		answer3Output.setIdQuizQuestion(1L);
		answer3Output.setIdAnswer(3L);
		answer3Output.setFieldId("3");
		answer3Output.setTextFieldValue("13");
		answer3Output.setCorrectValueFlag(false);
		
		answersListOutput2.add(answer1Output);
		answersListOutput2.add(answer2Output);
		answersListOutput2.add(answer3Output);
		
		List<Answer> answersListOutput3 = new ArrayList<Answer>();
		
		Answer ansout1 = new Answer();
		ansout1.setIdQuizQuestion(1L);
		ansout1.setIdAnswer(1L);
		ansout1.setFieldId("1");
		ansout1.setTextFieldValue("11");
		ansout1.setCorrectValueFlag(true);
		
		Answer ansout2 = new Answer();
		ansout2.setIdQuizQuestion(1L);
		ansout2.setIdAnswer(2L);
		ansout2.setFieldId("1");
		ansout2.setTextFieldValue("12");
		ansout2.setCorrectValueFlag(true);
		
		Answer ansout3 = new Answer();
		ansout3.setIdQuizQuestion(1L);
		ansout3.setIdAnswer(3L);
		ansout3.setFieldId("1");
		ansout3.setTextFieldValue("13");
		ansout3.setCorrectValueFlag(false);
		
		Answer ansout4 = new Answer();
		ansout4.setIdQuizQuestion(1L);
		ansout4.setIdAnswer(4L);
		ansout4.setFieldId("1");
		ansout4.setTextFieldValue("13");
		ansout4.setCorrectValueFlag(false);
		
		List<QuizQuestion> quizQuestionListOut = new ArrayList<QuizQuestion>();
		
		QuizQuestion quizQuestionOutput1 = new QuizQuestion();
		quizQuestionOutput1.setAnswers(answersListOutput1);
		quizQuestionOutput1.setAnswerText("11");
		quizQuestionOutput1.setNumberOptions(1L);
		quizQuestionOutput1.setQuestionText("what is the value of pi");
		quizQuestionOutput1.setQuestionType("Subjective");
		quizQuestionOutput1.setQuiz(quizOutput);
		quizQuestionOutput1.setIdQuizQuestion(1L);
		
		QuizQuestion quizQuestionOutput2 = new QuizQuestion();
		quizQuestionOutput2.setAnswers(answersListOutput2);
		quizQuestionOutput2.setAnswerText("12");
		quizQuestionOutput2.setNumberOptions(3L);
		quizQuestionOutput2.setQuestionText("Choose 12");
		quizQuestionOutput2.setQuestionType("Objective");
		quizQuestionOutput2.setQuiz(quizOutput);
		quizQuestionOutput2.setIdQuizQuestion(2L);
		
		QuizQuestion quizQuestionOutput3 = new QuizQuestion();
		quizQuestionOutput3.setAnswers(answersListOutput3);
		quizQuestionOutput3.setAnswerText("11");
		quizQuestionOutput3.setAnswerText("12");
		quizQuestionOutput3.setNumberOptions(4L);
		quizQuestionOutput3.setQuestionText("Choose 1 & 2");
		quizQuestionOutput3.setQuestionType("Multiselect");
		quizQuestionOutput3.setQuiz(quizOutput);
		quizQuestionOutput3.setIdQuizQuestion(3L);
		
		quizQuestionListOut.add(quizQuestionOutput1);
		quizQuestionListOut.add(quizQuestionOutput2);
		quizQuestionListOut.add(quizQuestionOutput3);
		
		
		Mockito.when(quizRepository.findByIdQuiz(1L)).thenReturn(quiz); 
	//	Mockito.when(quizQuestionRepository.findByQuiz_idQuiz(1L)).thenReturn(quizQuestionListOut);
		
		
		
	}

//	@Test
	@SuppressWarnings("unchecked")
	void testGetQuestionsByQuizId() {
		Document<?> doc = quizService.getQuestionsByQuizId(1L);
		List<QuizQuestion> obj = (List<QuizQuestion>) doc.getData();
		
		 assertEquals(200, doc.getStatusCode());
		 assertEquals(3, obj.size());
		 assertEquals("Subjective", obj.get(0).getQuestionType());
		 assertEquals("what is the value of pi", obj.get(0).getQuestionText());
		 assertEquals(1L, obj.get(0).getNumberOptions());
		 assertEquals(1L, obj.get(0).getIdQuizQuestion());
		 assertEquals("11", obj.get(0).getAnswerText());
		 assertEquals(1, obj.get(0).getAnswers().size());
		 assertEquals(1L,obj.get(0).getQuiz().getIdQuiz());
		 assertEquals(1L, obj.get(0).getQuiz().getIdProduct());
		 
		
	}

//	@Test
	void testSaveQuiz() {
		fail("Not yet implemented");
	}

//	@Test
	void testUpdateQuizQuestion() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testReadQuizQuestion() {
		fail("Not yet implemented");
	}
	
//	@Test
	void testDeleteQuizQuestion() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testCheckAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testCheckBatchAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testCheckObjectiveAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testCheckMultipleAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testCreateofflineQuiz() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetAllQuestionsForSubject() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetAllChapterwiseQuestions() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testSubmittingAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testSubmittingChapterwiseAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testAssignQuiz() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetQuizDetails() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetStudentSubjectWiseScore() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetStudentChapterWiseScore() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetBatchQuizDetails() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetQuizReview() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetBatchQuestionsByBatchQuizId() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testDeleteBatchQuizQuestion() {
  		fail("Not yet implemented");
	}

	
//	@Test
	void testSaveBatchQuizQuestions() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testSaveBatchQuiz() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testFetchBatchQuizListByIdTeacher() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetChapterWiseQuizReview() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetQuestionsByBatchQuizId() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testBatchQuizSubmittingAnswers() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetBatchQuizDetailsByIdBatch() {
		fail("Not yet implemented");
	}

	
//	@Test
	void testGetBatchQuizResult() {
		fail("Not yet implemented");
	}

}
