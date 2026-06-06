/**
 * 
 */
package co.vistafoundation.vlearning.faq.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.faq.dto.FaqDTO;

/**
 * @author NAVEEN KUMAR A
 *
 */
public interface FaqService {

	public Document<List<FaqDTO>> getFaqInfo();
}
