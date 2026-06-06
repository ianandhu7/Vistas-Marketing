/**
 * 
 */
package co.vistafoundation.vlearning.freeliveclass.service;

import org.springframework.data.domain.Page;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;

/**
 * @author NAVEEN
 *
 */
public interface FreeLiveClassService {
	public Document<Page<LiveClassDto>> getFreeLiveClass(Long idClassStandard, Long idLiveClassCategory,
			Long idLanguage, Long idSubject, Long idSyllabus, Long idState,int pageNumber,Integer currentRunningFlag,Integer  completionFlag );

}
