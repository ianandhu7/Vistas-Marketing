package co.vistafoundation.vlearning.quiz.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.vistafoundation.vlearning.quiz.dto.AnswerRequestDTO;
import co.vistafoundation.vlearning.quiz.model.Answer;
import co.vistafoundation.vlearning.quiz.model.Quiz;
import co.vistafoundation.vlearning.quiz.model.QuizQuestion;
import co.vistafoundation.vlearning.quiz.repository.AnswerRepository;
import co.vistafoundation.vlearning.quiz.repository.QuizQuestionRepository;

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)

class QuizControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	QuizQuestionRepository quizQuestionRepository;

	@MockBean
	AnswerRepository answerRepository;
	

	ObjectMapper om = new ObjectMapper();
	
	Quiz quiz = new Quiz(1L, 1L, 1L,"Test",1L);

	@SuppressWarnings("unused")
	//fix the testcase @Test
	public void creatQuizQuestionTest() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer("11", true, "1"); 
		Answer answer2 = new Answer("10", false, "2");
		Answer answer3 = new Answer("12", false, "3");
		Answer answer4 = new Answer("13", false, "4");
		answer1.setIdAnswer(1L);
		answer2.setIdAnswer(2L);
		answer3.setIdAnswer(3L);
		answer4.setIdAnswer(4L);
		
		ans.add(answer1);
		ans.add(answer2);
		ans.add(answer3);
		ans.add(answer4);
		
		quiz.setIdQuiz(1L);
		
		QuizQuestion question = new QuizQuestion();
		question.setIdQuizQuestion(1L);
		question.setQuestionType("Objective");
		question.setQuestionText("What is the value of A");
		question.setAnswerText("11");
		question.setQuiz(quiz);
		question.setAnswers(ans);

		String jsonrequest = om.writeValueAsString(question);
		MvcResult result = mockMvc.perform(post("/api/v1/quiz/1/question/create").content(jsonrequest)
				.contentType("application/json;charset=utf-8")).andExpect(status().isOk()).andReturn();

		System.out.println("First test completed!");

	}
	
	/* Passing incorrect answer */
	
	//fix the testcase @Test
	public void passingIncorrectcredential() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer("11", true, "1");
		Answer answer2 = new Answer("10", false, "1");
		Answer answer3 = new Answer("12", false, "3");
		Answer answer4 = new Answer("13", false, "4");
		ans.add(answer1);
		ans.add(answer2);
		ans.add(answer3);
		ans.add(answer4);
		
		answer1.setIdAnswer(1L);
		answer2.setIdAnswer(2L);
		answer3.setIdAnswer(3L);
		answer4.setIdAnswer(4L);
		
		quiz.setIdQuiz(1L);

		QuizQuestion question1 = new QuizQuestion();
		question1.setIdQuizQuestion(1L);
		question1.setQuestionType("Objective");
		question1.setQuestionText("What is the value of A");
		question1.setAnswerText("11");
		question1.setQuiz(quiz);
		question1.setAnswers(ans);

		String jsonrequest = om.writeValueAsString(question1);
		MvcResult result = mockMvc.perform(post("/api/v1/quiz/1/question/create").content(jsonrequest)
				.contentType("application/json;charset=utf-8")).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(" output " +response);
		System.out.println("second test completed!");

		
		
	}
	
//	@Test
	public void updateQuizQuestionTest() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer("11", true, "1");
		Answer answer2 = new Answer("10", false, "2");
		Answer answer3 = new Answer("12", true, "3");
		Answer answer4 = new Answer("13", false, "4");
		
		ans.add(answer1);
		ans.add(answer2);
		ans.add(answer3);
		ans.add(answer4);

		QuizQuestion question1 = new QuizQuestion();

		question1.setQuestionType("subjective");
		question1.setQuestionText("What is the value of A ");
		question1.setAnswerText("11");
		question1.setQuiz(quiz);
		question1.setAnswers(ans);
		
		
		List <Answer> savedAnswer = new ArrayList<>();
		Answer savedAnswer1 = new Answer("11", true, "1");
		Answer savedAnswer2 = new Answer("10", true, "2");
		Answer savedAnswer3 = new Answer("12", true, "3");
		Answer savedAnswer4 = new Answer("13", true, "4");
		
		
		savedAnswer1.setIdAnswer(1L);
		savedAnswer2.setIdAnswer(2L);
		savedAnswer3.setIdAnswer(3L);
		savedAnswer4.setIdAnswer(4L);
		
		QuizQuestion savedQuestion1 = new QuizQuestion();

		savedQuestion1.setQuestionType("subjective");
		savedQuestion1.setQuestionText("What is the value of A ");
		savedQuestion1.setAnswerText("11");
		savedQuestion1.setAnswers(savedAnswer);
		savedQuestion1.setIdQuizQuestion(1L);
		
		Quiz savedQuiz = new Quiz();
		savedQuiz.setIdProduct(1L);
		savedQuiz.setIdSubject(1L);
		savedQuiz.setIdSubjectChapter(1L);
		savedQuiz.setIdQuiz(1L);
		
		savedQuestion1.setQuiz(savedQuiz);
		
		Mockito.when(quizQuestionRepository.save(question1)).thenReturn(savedQuestion1);

		String jsonrequest = om.writeValueAsString(question1);
		MvcResult result = mockMvc.perform(put("/api/v1/quiz/question/1/update").content(jsonrequest).with(csrf())
				.contentType("application/json;charset=utf-8")).andExpect(status().is5xxServerError()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(" output " +response);
		System.out.println("Third test completed!");

		
		
	}
	
//	@Test
	public void checkAnswers() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer();
		answer1.setTextFieldValue("11");
		answer1.setFieldId("1");
		
		Answer answer2 = new Answer();
		answer2.setTextFieldValue("12");
		answer2.setFieldId("2");
		
		ans.add(answer1);
		ans.add(answer2);
		
		AnswerRequestDTO answerRequestDto = new AnswerRequestDTO();
		answerRequestDto.setIdQuizQuestion(1L);
		answerRequestDto.setAnswer(ans);
		
		String jsonrequest = om.writeValueAsString(answerRequestDto);
		MvcResult result = mockMvc.perform(get("/api/v1/quiz/answer/check").content(jsonrequest)
				.contentType("application/json;charset=utf-8")).andExpect(status().is5xxServerError()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(" output " +response);
		System.out.println("fifth test completed!");
		
		
	}
//	@Test
	public void checkObjectiveAnswers() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer();
		answer1.setFieldId("1");
		
		ans.add(answer1);
		
		AnswerRequestDTO answerRequestDto = new AnswerRequestDTO();
		answerRequestDto.setIdQuizQuestion(1L);
		answerRequestDto.setAnswer(ans);

		String jsonrequest = om.writeValueAsString(answerRequestDto);
		MvcResult result = mockMvc.perform(get("/api/v1/quiz/answer/check-objective").content(jsonrequest)
				.contentType("application/json;charset=utf-8")).andExpect(status().is5xxServerError()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(" output " +response);
		System.out.println("sixth test completed!");
		
		
	}
	
//	@Test
	public void checkMcqAnswers() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		List<Answer> ans = new ArrayList<Answer>();
		Answer answer1 = new Answer();
		answer1.setFieldId("1");
		
		Answer answer2 = new Answer();
		answer2.setFieldId("2");
		
		ans.add(answer1);
		ans.add(answer2);
		
		AnswerRequestDTO answerRequestDto = new AnswerRequestDTO();
		answerRequestDto.setIdQuizQuestion(1L);
		answerRequestDto.setAnswer(ans);
		

		String jsonrequest = om.writeValueAsString(answerRequestDto);
		MvcResult result = mockMvc.perform(get("/api/v1/quiz/answer/check-mcq").content(jsonrequest)
				.contentType("application/json;charset=utf-8")).andExpect(status().is5xxServerError()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(" output " +response);
		System.out.println("seventh test completed!");
		
		
	}


}
