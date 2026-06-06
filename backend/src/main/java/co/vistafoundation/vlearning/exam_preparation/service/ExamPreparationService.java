/**
 * 
 */
package co.vistafoundation.vlearning.exam_preparation.service;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author NAVEEN
 *
 */
public interface ExamPreparationService {

	public Document<?> uploadBatchQuizQuestion(MultipartFile batchFile, Long idQuiz);
	
	public Document<?> validateBatchQuizQuestion(MultipartFile batchFile, Long idQuiz);
	
}
