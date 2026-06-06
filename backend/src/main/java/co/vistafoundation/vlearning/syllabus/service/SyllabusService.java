/**
 * 
 */
package co.vistafoundation.vlearning.syllabus.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;

/**
 * @author NaveenKumar
 *
 */
public interface SyllabusService {
	
	public Document<List<Syllabus>> getAllSyllabusForPublic();

}
