/**
 * 
 */
package co.vistafoundation.vlearning.syllabus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.syllabus.service.SyllabusService;

/**
 * @author NaveenKumar
 *
 */
@RestController
@RequestMapping("/api/v1/syllabus/")
public class SyllabusController {

	@Autowired
	SyllabusService syllabusService;

	/*
	 * @author NaveenKumar
	 * 
	 * @throws NUllPoitner If there was an error retrieving the syllabus.
	 * 
	 * @return A List of syllabus objects.
	 */
	@GetMapping(value = "")
	public ResponseEntity<?> getSyllabusMetaData() {
		Document<?> document = syllabusService.getAllSyllabusForPublic();
		return ResponseEntity.status(document.getStatusCode()).body(document);

	}

}
