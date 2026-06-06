/**
 * 
 */
package co.vistafoundation.vlearning.exam_preparation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exam_preparation.service.ExamPreparationService;

/**
 * @author NAVEEN
 *
 */
@RestController
@RequestMapping("/api/v1/exam-preparation")
public class ExamPreparationController {

	@Autowired
	ExamPreparationService examPreparationService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/batch-upload")
	public ResponseEntity<?> uploadBatchQuizQuestions(@RequestParam MultipartFile batchFile,
			@RequestParam Long idQuiz) {
		Document<?> reponses = examPreparationService.uploadBatchQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	
	@PostMapping("/batch-validate")
	public ResponseEntity<?> validateBatchQuizQuestions(@RequestParam MultipartFile batchFile,
			@RequestParam Long idQuiz) {
		Document<?> reponses = examPreparationService.validateBatchQuizQuestion(batchFile, idQuiz);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

}
