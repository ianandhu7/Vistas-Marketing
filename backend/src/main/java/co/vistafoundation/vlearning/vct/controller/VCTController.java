/**
 * 
 */
package co.vistafoundation.vlearning.vct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.vct.service.VCTService;

@RestController
@RequestMapping("/api/v1/vct")
public class VCTController {

	@Autowired
	VCTService vctService;

	/**
	 * 
	 * Endpoint for batch uploading quiz questions for a quiz with the specified ID.
	 * 
	 * @author naveenkumar.
	 * 
	 *         Requires an authenticated user with the "ROLE_ADMIN" role.
	 * 
	 * @param batchFile The batch file containing the quiz questions to upload.
	 * @param idQuiz    The ID of the quiz to add the questions to.
	 * @return A ResponseEntity containing a Document object with the results of the
	 *         batch upload and a status code.
	 * 
	 */

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-upload")
	public ResponseEntity<?> uploadBatchQuizQuestions(@RequestParam MultipartFile batchFile,
			@RequestParam Long idQuiz) {
		Document<?> reponses = vctService.uploadBatchQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	/**
	 * 
	 * Validates a batch of quiz questions in a CSV file for a specified quiz ID.
	 * 
	 * @param batchFile The CSV file containing the quiz questions.
	 * @param idQuiz    The ID of the quiz to validate the questions for.
	 * @return A ResponseEntity containing a Document object with the validation
	 *         results and a status code.
	 */
	@PostMapping("/batch-validate")
	public ResponseEntity<?> validateBatchQuizQuestions(@RequestParam MultipartFile batchFile,
			@RequestParam Long idQuiz) {
		Document<?> reponses = vctService.validateBatchQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}
