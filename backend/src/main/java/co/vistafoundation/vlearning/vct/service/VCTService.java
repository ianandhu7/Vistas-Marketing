/**
 * 
 */
package co.vistafoundation.vlearning.vct.service;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author naveenkumar
 *
 */
public interface VCTService {
	
	public Document<?> uploadBatchQuizQuestion(MultipartFile batchFile, Long idQuiz);
	
	public Document<?> validateBatchQuizQuestion(MultipartFile batchFile, Long idQuiz);

}
