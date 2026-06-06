package co.vistafoundation.vlearning.firebase.config.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.firebase.config.dto.PageDto;
import co.vistafoundation.vlearning.firebase.config.dto.RequestParameter;
import co.vistafoundation.vlearning.firebase.config.dto.ResponseParameter;
import co.vistafoundation.vlearning.firebase.config.service.FirebaseConfigService;


/**
 * author Mohan Kumar K M
 * 
 **/
@RestController
@RequestMapping("api/v1/firebase/")
public class FirebaseConfigController {

	@Autowired  
	FirebaseConfigService firebaseConfigService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "get-page-list")
	public ResponseEntity<Document<PageDto>> getPageList( @RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize) {
		Document<PageDto> reponses = firebaseConfigService.getPages(pageNumber,pageSize);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	 
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "get-page-parameter-list")
	public ResponseEntity<Document<ResponseParameter>> getPageParameterList(String pageName) {
		Document<ResponseParameter> reponses = firebaseConfigService.getPageParameterList(pageName);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "update-page-parameter")
	public ResponseEntity<Document<String>> updatePageParameter(@RequestParam  String pageName, @RequestBody RequestParameter requestParameter) {
		Document<String> reponses = firebaseConfigService.updatePageParameter(pageName,requestParameter);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
}
