//package co.vistafoundation.vlearning.offlinecourse.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import co.vistafoundation.vlearning.auth.security.CustomUserDetailsService;
//import co.vistafoundation.vlearning.common.response.Document;
//import co.vistafoundation.vlearning.offlinecourse.dto.CreateVideoRequestDTO;
//import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
//import co.vistafoundation.vlearning.offlinecourse.service.OfflineCourseService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = OfflineCoursesController.class)
//
//class OfflineCoursesControllerTest {
//   
////	 @Autowired
////	 private WebApplicationContext context;
//	 
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	CustomUserDetailsService customUserDetailsService;
//
//	@MockBean
//	private OfflineCourseService offlineCourseService;
//
//	String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
//
//	 
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void createVideoTest() throws Exception {
//
//		OfflineVideoCourse expectedResult = new OfflineVideoCourse();
//		expectedResult.setIdOfflineVideoCourse(1L);
//		expectedResult.setIdProduct(1L);
//		expectedResult.setIdSubject(1L);
//		expectedResult.setTopic("Test Topics");
//		expectedResult.setVideoDuration(100);
//		expectedResult.setVideoEnLink("fakelink");
//		expectedResult.setVideoOtp("fakeOtp");
//		expectedResult.setVideoPoster1Location("locationURL");
//		expectedResult.setVideoSeqNumber(1);
//		expectedResult.setVideoTheme("Some Theme");
//		Document doc = new Document();
//		doc.setData(expectedResult);
//		doc.setStatusCode(200);
//		doc.setMessage("Request Sucessfull");
//
//		Mockito.when(offlineCourseService.createOfflineVideoRecord(Mockito.any(CreateVideoRequestDTO.class)))
//				.thenReturn(doc);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/offlineCourse/upload")
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
////		String expected = "{id:Course1,name:Spring,description:10Steps}";
//
//		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K
//		// Students","steps":["Learn Maven","Import Project","First Example","Second
//		// Example"]}
//		MockHttpServletResponse response = result.getResponse();
//
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//	}
//
//}
