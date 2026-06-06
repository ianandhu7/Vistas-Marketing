/**
 * 
 */
package co.vistafoundation.vlearning.faq.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.faq.dto.FaqDTO;
import co.vistafoundation.vlearning.faq.service.FaqService;

/**
 * @author NAVEEN
 *
 */
@Service
public class FaqServiceImpl implements FaqService {

	@Value("${APP_ANGULAR_URL}")
	private String appAngularUrl;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Document<List<FaqDTO>> getFaqInfo() {

		Document<List<FaqDTO>> result = new Document<>();

		try {
			
			String requestURL = (appAngularUrl.equals("https://vistaslearning.com") || appAngularUrl.equals("https://student.vistaslearning.com"))
					? "https://vlearning-prod.s3.ap-south-1.amazonaws.com/FAQ/faq.json"
					: "https://vlearning-dev.s3.ap-south-1.amazonaws.com/FAQ/faq.json";
			
			
			// unmarshalling the JSON to FaqDTO Object (Deserialization)
			ResponseEntity<FaqDTO[]> responseEntity = restTemplate.getForEntity(requestURL, FaqDTO[].class);
			FaqDTO[] resposes = responseEntity.getBody();

			result.setData(Arrays.asList(resposes));
			result.setStatusCode(HttpStatus.OK.value());
			result.setMessage("Request Sucessfull");

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getMessage());
			return result;
		}

		return result;
	}

}
