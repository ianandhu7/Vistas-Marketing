package co.vistafoundation.vlearning.webex.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.vistafoundation.vlearning.webex.dto.WebExMeetingDetails;
import co.vistafoundation.vlearning.webex.repository.WebExRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class WebExServiceTest {

	@MockBean
	private WebExRepository webExRepository;
	
	@Test
	public void saveMeetingDetails() {
		WebExMeetingDetails meetingDetails = new WebExMeetingDetails();
		meetingDetails.setMeetingGuestToken("1234");
		meetingDetails.setMeetingPassword("123456");
		meetingDetails.setMeetingUUID("8138094345");
		
		Mockito.when(webExRepository.save(meetingDetails)).thenReturn(meetingDetails);
//		assertTrue(WebExService.saveMeetingDetails(meetingDetails)).isEqualTo(meetingDetails);
	}

}
