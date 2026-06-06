/**
 * 
 */
package co.vistafoundation.vlearning.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.service.StateService;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/state")
public class StateController {
	
	@Autowired
	StateService stateService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value="/list")
	public ResponseEntity<Document> list() {
		Document document = new Document();
		List<State> statesList = stateService.getAllStates();
		if (statesList.size()!=0) {
			document.setData(statesList);
			document.setMessage("State list fetched successfully");
			document.setStatusCode(200);
		}else {
			document.setData(null);
			document.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
		}
		return new ResponseEntity<Document>(document, HttpStatus.OK);
	}
}
