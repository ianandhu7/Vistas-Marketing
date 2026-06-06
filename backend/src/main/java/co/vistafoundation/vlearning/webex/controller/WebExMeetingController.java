package co.vistafoundation.vlearning.webex.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.webex.dto.WebExMeetingDetails;
import co.vistafoundation.vlearning.webex.model.WebExPool;
import co.vistafoundation.vlearning.webex.service.WebExService;

@Controller
@RequestMapping("/api/v1/webex")
public class WebExMeetingController {

	@Autowired
	private WebExService webExService;

	@PostMapping(value = "/save-webex-meeting-details")
	public ResponseEntity<?> saveMeetingDetailsToDb(@RequestBody WebExMeetingDetails meetingDetails) {

		Map<String, Object> response = new HashMap<String, Object>();
		Document<WebExMeetingDetails> doc = new Document<WebExMeetingDetails>();

		if (meetingDetails != null) {
			WebExMeetingDetails saveMeetingDetails = webExService.saveMeetingDetails(meetingDetails);
			doc.setData(saveMeetingDetails);
			doc.setMessage("Meeting Details Saved Successfully to DB");
			doc.setStatusCode(201);
			response.put("webex", doc);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			doc.setData(null);
			doc.setMessage("Meeting Details Cannot be Empty");
			doc.setStatusCode(200);
			response.put("webex", doc);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/create-webex-pool")
	public ResponseEntity<?> saveTeacherWebExCredentialsToWebExPool(@RequestBody WebExPool webExPool) {

		Map<String, Object> response = new HashMap<String, Object>();
		Document<WebExPool> doc = new Document<WebExPool>();

		if (webExPool != null) {
			WebExPool saveTeacherWebExCredentialsToWebExPool = webExService
					.saveTeacherWebExCredentialsToWebExPool(webExPool);
			doc.setData(saveTeacherWebExCredentialsToWebExPool);
			doc.setMessage("WebEx Host Credential Added to WebEx Pool");
			doc.setStatusCode(201);
			response.put("response", doc);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			doc.setData(null);
			doc.setMessage("Failed to Add WebEx Host Credential to WebEx Pool");
			doc.setStatusCode(500);
			response.put("error", doc);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/checkForWebExIdExistence")
	public ResponseEntity<?> checkForWebExIdExistence(@RequestParam(name = "webExId") String webExId) {
		Document<?> document = webExService.checkForWebExIdExistence(webExId);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/fetchAllAssignedWebExPoolWithTeacherDetails")
	public ResponseEntity<?> fetchAllAssignedWebExPoolWithTeacherDetails() {
		Document<?> document = webExService.fetchAllAssignedWebExPoolWithTeacherDetails();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/deleteWebExPoolAndAlsoUpdateTeacherRecordAccordingly")
	public ResponseEntity<?> deleteWebExPoolAndAlsoUpdateTeacherRecordAccordingly(
			@RequestParam("idWebExPool") Long idWebExPool) {
		Document<?> document = webExService.deleteWebExPoolAndAlsoUpdateTeacherRecordAccordingly(idWebExPool);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/reAssignWebExPoolToTeacher")
	public ResponseEntity<?> reAssignWebExPoolToTeacher(@RequestParam("idWebExPool") Long idWebExPool,
			@RequestParam("idTeacher") Long idTeacher) {
		Document<?> document = webExService.reAssignWebExPoolToTeacher(idWebExPool, idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping(value = "/get-all-unassigned-teachers")
	public ResponseEntity<?> fetchAllUnAssignedTeacher() {
		Map<String, Object> response = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		Document<WebExPool> doc = new Document<WebExPool>();

		List<WebExPool> fetchAllUnAssignedTeachers = webExService.fetchAllUnAssignedTeachers();
		for (WebExPool webExPool : fetchAllUnAssignedTeachers) {
			webExPool.setWebExPassword(null);
		}
		if (fetchAllUnAssignedTeachers.isEmpty()) {
			response.put("teachersList", new ArrayList<>());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("teachersList", fetchAllUnAssignedTeachers);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/update-webex-pool")
	public ResponseEntity<?> updateWebExPool(@RequestParam("idWebExPool") Long idWebExPool,
			@RequestBody WebExPool webExPool) {
		
		Document<?> document = webExService.updateWebExPool(idWebExPool, webExPool);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping(value = "/fetchAllwebx")
	public ResponseEntity<?> fetchAllwebx() {
		Map<String, Object> response = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		Document<WebExPool> doc = new Document<WebExPool>();

		List<WebExPool> fetchAllUnAssignedTeachers = webExService.fetchAllwebx();
		if (fetchAllUnAssignedTeachers.isEmpty()) {
			response.put("teachersList", new ArrayList<>());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("teachersList", fetchAllUnAssignedTeachers);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/fetch-host-credentials-from-webex-pool")
	public ResponseEntity<?> fetchWebExHostCredentialsByWebExPoolId(@RequestParam("idWebExPool") Long idWebExPool) {

		Map<String, Object> response = new HashMap<String, Object>();
		Document<WebExPool> doc = new Document<WebExPool>();

		if (idWebExPool == 0 || idWebExPool == null) {
			doc.setData(null);
			doc.setMessage("WebExPoolId cannot be Null. Please Add Host to WebEx Pool First");
			doc.setStatusCode(400);
			response.put("error", doc);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			WebExPool webExHostCredential = webExService.fetchHostWebExCredentials(idWebExPool);
			doc.setData(webExHostCredential);
			doc.setMessage("Success");
			doc.setStatusCode(200);
			response.put("webExHost", doc);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
   
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("unassigned-webexpool-includes-teacher")
	public ResponseEntity<?> getUnassignedWebExpoolDataInculdingTeacher(@RequestParam Long idTeacher) 
	{
		Document<List<WebExPool>> document = webExService.fetchAllUnassigedWebExPoolIdInculdingTeacheWebEx(idTeacher);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (document.getData().isEmpty()) {
			response.put("teachersList", new ArrayList<>());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.put("teachersList", document.getData());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		
	}
}
