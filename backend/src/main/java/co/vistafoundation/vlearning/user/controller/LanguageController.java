/**
 * 
 */
package co.vistafoundation.vlearning.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.service.LanguageService;

/**
 * @author sarfaraz
 *
 */
@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {

	@Autowired
	LanguageService languageService;

	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllLanguages() {
		Document<?> reponses = languageService.getAllLanguages();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
}
