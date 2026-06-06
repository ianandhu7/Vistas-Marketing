/**
 * 
 */
package co.vistafoundation.vlearning.freeliveclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.freeliveclass.service.FreeLiveClassService;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;

/**
 * @author NAVEEN
 *
 */

@RestController
@RequestMapping("api/v1/free-live-class")
public class FreeLiveClassController {

	@Autowired
	FreeLiveClassService freeLiveClassService;
	
	

	@GetMapping(value = "")
	public ResponseEntity<?> getAllFreeClassVideos(
			@RequestParam(defaultValue = "-1", required = false) Long idLiveClassCategory,
			@RequestParam(defaultValue = "-1", required = false) Long idClassStandard,
			@RequestParam(defaultValue = "-1", required = false) Long idLanguage,
			@RequestParam(defaultValue = "-1", required = false) Long idSubject,
			@RequestParam(defaultValue = "-1", required = false) Long idSyllabus,
			@RequestParam(defaultValue = "-1", required = false) Long idState,
			@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "-1", required = false) Integer currentRunningFlag,
			@RequestParam(defaultValue = "-1", required = false) Integer completionFlag
			) {

		Document<Page<LiveClassDto>> response = freeLiveClassService.getFreeLiveClass(idClassStandard, idLiveClassCategory, idLanguage,
				idSubject, idSyllabus, idState, page,currentRunningFlag,completionFlag);

		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
