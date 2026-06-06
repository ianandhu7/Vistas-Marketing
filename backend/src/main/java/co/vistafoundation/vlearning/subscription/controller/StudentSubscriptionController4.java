package co.vistafoundation.vlearning.subscription.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentChapterListDTO;
import co.vistafoundation.vlearning.subscription.service.StudentSubscriptionServiceV4;

@RestController
@RequestMapping("/api/v4/subscription")
public class StudentSubscriptionController4 {
	
	@Autowired
	StudentSubscriptionServiceV4 studentSubscriptionServiceImpl;

	@GetMapping(value = "/subject-chapter-list")
	public ResponseEntity<Document<NewStudentSubscriptionSubjectDTO>> getChapterList(@RequestParam Long idProductLine,
			@RequestParam Long idSubject, @RequestParam Long idClassStandard, @RequestParam Long idSyllabus,
			@RequestParam Long idState) {
		Document<NewStudentSubscriptionSubjectDTO> reponses = studentSubscriptionServiceImpl.getChapterList(idProductLine,
				idSubject, idClassStandard, idSyllabus, idState);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
}
