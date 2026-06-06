/**
 * 
 */
package co.vistafoundation.vlearning.faq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.faq.dto.FaqDTO;
import co.vistafoundation.vlearning.faq.service.FaqService;


/**
 * @author NAVEEN KUMAR A
 *
 */

@RestController
@RequestMapping("/api/v1/faq")
public class FaqController {

	@Autowired
	FaqService faqService;
    
	
	/**
	 * @author NAVEEN 
	 * 
	 * This method will return faq json response from 
	 * s3 based on the angular app url, it choose production 
	 * or dev faq file url dynamically. 
	 * @return
	 */
	@GetMapping("")
	public ResponseEntity<?> getFaqInfo() {
		Document<List<FaqDTO>> doc = faqService.getFaqInfo();
		return ResponseEntity.status(doc.getStatusCode()).body(doc);

	}
	
}
