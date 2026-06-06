/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassFilterDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassRequestDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoEcaClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;
import co.vistafoundation.vlearning.leadbatch.freeclass.service.DemoClassService;

/**
 * @author vk
 *
 */
@RestController
@RequestMapping("/api/v1/democlass")
public class DemoClassController {

	@Autowired
	DemoClassService demoClassService;

	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/create")
	public ResponseEntity<?> save(@RequestBody DemoClassRequestDTO demoClassRequestDTO,
			@RequestParam(defaultValue = "academic") String category) {
		Document<?> document = demoClassService.save(demoClassRequestDTO, category);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PostMapping("/update-teacher")
	public ResponseEntity<?> updateTeacher(@RequestBody DemoClass demoClass) {
		Document<?> document = demoClassService.updateTeacher(demoClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/list")
	public ResponseEntity<?> findByTeacher(@RequestParam Long idTeacher) {
		Document<?> document = demoClassService.getDemoClassByTeacher(idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PostMapping("/listbyfilters")
	public ResponseEntity<?> findListByFilters(@RequestBody DemoClassFilterDTO demoClassFilterDTO) {
		Document<?> document = demoClassService.getDemoClassList(demoClassFilterDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Naveen Kumar A
	 * @param demoClassFilterDTO
	 * @return This method will return List of ECA Demo Class
	 * as param of DemoClassFilterDTO.
	 */
	@PostMapping("/listbyfilters-eca")
	public ResponseEntity<?> findListByFiltersECA(@RequestBody DemoClassFilterDTO demoClassFilterDTO) {
		Document<List<DemoEcaClassDTO>> document = demoClassService.getDemoClassListECA(demoClassFilterDTO);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/findAllStudents/{idClassStandard}/{idSyllabus}/{idSubject}/{idSujectChapter}/")
	public ResponseEntity<?> findAllStudents(@PathVariable Long idClassStandard, @PathVariable Long idSyllabus,
			@PathVariable Long idSubject, @PathVariable Long idSujectChapter, @RequestParam Long idLanguage,@RequestParam Long idAvailableSchedule,@RequestParam Long idState) {

		Document<?> document = demoClassService.getAllStudentsForDemoclass(idClassStandard, idSyllabus, idSubject,
				idSujectChapter,idLanguage,idAvailableSchedule,idState);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/findAllStudents/{idClassStandard}/{idSyllabus}/{idSubject}/{idSujectChapter}/{date}")
	public ResponseEntity<?> findAllDemoClasses(@PathVariable Long idClassStandard,
			@PathVariable Long idSyllabus, @PathVariable Long idSubject, @PathVariable Long idSujectChapter,
			@PathVariable String date ) {
		Document<?> document = demoClassService.getAllDemoClasses(idClassStandard, idSyllabus, idSubject, idSujectChapter,
				date);

		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/AssignBatchLeadLog/{idLeadBatchDetail}/{idDemoClass}")
	public ResponseEntity<?> AssignBatchLeadLog(@PathVariable Long idDemoClass,
			@PathVariable Long idLeadBatchDetail) {
		Document<?> document = demoClassService.assigningLeadBatchLog(idDemoClass, idLeadBatchDetail);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idDemoClass
	 * @param idTeacher
	 * @return It will Create a webex Meeting based on idDemoCLass Choosen 
	 * and store the webex info in DemoClass Schedule Table and will return the webex info to the client
	 */

	@GetMapping("/start-demo-class-webex")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	public ResponseEntity<?> createWebExMeetingForDemoClassByTeacher(
			@RequestParam(name = "idDemoClass") Long idDemoClass, @RequestParam(name = "idTeacher") Long idTeacher) {
		Document<?> document = demoClassService.createWebExMeetingForDemoClassByTeacher(idDemoClass, idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @return the confirmed Demo Class Lists to the CLient by Telecaller for the particular student userSurId
	 */
	@GetMapping("/get-all-lead-batch-logs")
	public ResponseEntity<?> getAllLeadBatchLogs(@RequestParam Long idVlUser) {
		Document<?> document = demoClassService.getAllLeadBatchLogs(idVlUser);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @param idDemoClass
	 * @param idLeadBatchLog
	 * @return Attendee Join Url to the client and simultaneously will take the attendance in LeadAttendedClass Table
	 */
	@GetMapping("/student-or-parent-join-demo-class")
	public ResponseEntity<?> studentOrParentJoinDemoClass(
			@RequestParam(name = "idLeadBatchLog") Long idLeadBatchLog, @RequestParam(name = "idVlUser") Long idVlUser,
			@RequestParam(name = "idDemoClass") Long idDemoClass) {
		Document<?> document = demoClassService.studentOrParentJoinDemoClass(idLeadBatchLog, idVlUser, idDemoClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}


	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/delete")
	public ResponseEntity<?> deleteDemoClass(@RequestParam(name = "idDemoClass") Long idDemoClass) {
		Document<?> document = demoClassService.deActivateDemoClass(idDemoClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/getLeastFutureDemoClass")
	public ResponseEntity<?> getLeastFutureDemoClass() {

		Document<?> document = demoClassService.getLeastFutureDemoClass();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@GetMapping("/getAllDemoClases/{idTeacher}")
	public ResponseEntity<?> getAllDemoClases(@PathVariable Long idTeacher) {

		Document<?> document = demoClassService.getListofDemoClassTeacherProfile(idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idSubjectExtraCurricular
	 * @param idLevelExtraCurricular
	 * @param choosenDate
	 * @return List of All demo class for the filter Provided
	 */
	@GetMapping("/getAllExtraCurricularDemoClassForSelectedSubjectAndLevelAndSlectedDate")
	public ResponseEntity<?> getAllExtraCurricularDemoClassForSelectedSubjectAndLevelAndSlectedDate(
			@RequestParam("idSubjectExtraCurricular") Long idSubjectExtraCurricular,
			@RequestParam("idLevelExtraCurricular") Long idLevelExtraCurricular,
			@RequestParam("choosenDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate choosenDate) {

		Document<?> document = demoClassService.getAllExtraCurricularDemoClassForSelectedSubjectAndLevelAndSlectedDate(
				idSubjectExtraCurricular, idLevelExtraCurricular, choosenDate);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@PutMapping("/update-eca-teacher")
	public ResponseEntity<?> updateECATeacher(@RequestBody DemoClassExtraCurricular demoClass) {
		Document<DemoClassExtraCurricular> document = demoClassService.updateECATeacher(demoClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@DeleteMapping("/delete-eca")
	public ResponseEntity<?> deleteECADemoClass(@RequestParam Long idDemoClassExtraCurricular) {
		Document<DemoClassExtraCurricular> document = demoClassService
				.deactivateECADemoClass(idDemoClassExtraCurricular);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @param idDemoClassExtraCurricular
	 * @return Telecaller will confirm the Extra Curricular Demo Class and 
	 * will assign student to one Demo Class ExtraCurricular. Sending the Confirmation Mail After Moving the data from 
	 * Lead Batch Details to Lead Batch Log. 
	 */
	@GetMapping("/scheduleExtraCurricularDemoClassAndConfirm")
	public ResponseEntity<?> scheduleExtraCurricularDemoClassAndConfirm(@RequestParam("idVlUser") Long idVlUser,
			@RequestParam("idDemoClassExtraCurricular") Long idDemoClassExtraCurricular) {

		Document<?> document = demoClassService.scheduleExtraCurricularDemoClassAndConfirm(idVlUser,
				idDemoClassExtraCurricular);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idTeacher
	 * @return list of All Extra Curricular Demo CLass Assigned to this idTeacher Provided
	 */
	@GetMapping("/listExtraCurricularDemoClass")
	public ResponseEntity<?> listExtraCurricularDemoClassByIdTeacher(@RequestParam("idTeacher") Long idTeacher) {

		Document<?> document = demoClassService.listExtraCurricularDemoClassByIdTeacher(idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idDemoClassExtraCurricular
	 * @param idTeacher
	 * @return It will Create a webex Meeting based on idDemoClassExtraCurricular Choosen 
	 * and store the webex info in DemoClassExtraCurricular Schedule Table and will return the webex info to the client
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
	@GetMapping("/start-demo-class-extra-curricular-webex")
	public ResponseEntity<?> createWebExMeetingForExtraCurricularDemoClassByTeacher(
			@RequestParam(name = "idDemoClassExtraCurricular") Long idDemoClassExtraCurricular,
			@RequestParam(name = "idTeacher") Long idTeacher) {
		Document<?> document = demoClassService
				.createWebExMeetingForExtraCurricularDemoClassByTeacher(idDemoClassExtraCurricular, idTeacher);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @param idDemoClassExtraCurricular
	 * @param idLeadBatchLogExtraCurricular
	 * @return Attendee Join Url to the client and 
	 * simultaneously will take the attendance in ExtraCurricularLeadAttendedClass Table
	 */
	@GetMapping("/student-or-parent-join-demo-class-extra-curricular")
	public ResponseEntity<?> studentOrParentJoinDemoClassExtraCurricular(
			@RequestParam(name = "idLeadBatchLogExtraCurricular") Long idLeadBatchLogExtraCurricular,
			@RequestParam(name = "idVlUser") Long idVlUser,
			@RequestParam(name = "idDemoClassExtraCurricular") Long idDemoClassExtraCurricular) {
		Document<?> document = demoClassService.studentOrParentJoinDemoClassExtraCurricular(idLeadBatchLogExtraCurricular,
				idVlUser, idDemoClassExtraCurricular);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @return the confirmed Extra Curricular Demo Class Lists to the CLient by Telecaller for the particular userSurId
	 */
	@GetMapping("/get-all-lead-batch-logs-extra-curricular")
	public ResponseEntity<?> getAllLeadBatchLogsExtraCurricular(@RequestParam Long idVlUser) {
		Document<?> document = demoClassService.getAllLeadBatchLogsExtraCurricular(idVlUser);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @return webex metadata for attendee to join the demo class academic
	 * @deprecated
	 */
	@GetMapping("/getDemoClassWebExMetadataAttendeeInfo")
	public ResponseEntity<?> getDemoClassWebExMetadataAttendeeInfo(
			@RequestParam("idDemoClass") Long idDemoClass) {

		Document<?> document = demoClassService.getDemoClassWebExMetadataAttendeeInfo(idDemoClass);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	/**
	 * @author Ahmed Reza
	 * @param idVlUser
	 * @return webex metadata for attendee to join the demo class extracurricular
	 * @deprecated
	 */
	@GetMapping("/getDemoClassExtraCurricularWebExMetadataAttendeeInfo")
	public ResponseEntity<Document<?>> getDemoClassExtraCurricularWebExMetadataAttendeeInfo(
			@RequestParam("idDemoClassExtraCurricular") Long idDemoClassExtraCurricular) {

		Document<?> document = demoClassService
				.getDemoClassExtraCurricularWebExMetadataAttendeeInfo(idDemoClassExtraCurricular);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
}
