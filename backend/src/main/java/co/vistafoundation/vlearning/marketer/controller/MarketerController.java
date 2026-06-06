package co.vistafoundation.vlearning.marketer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.marketer.model.Marketer;
import co.vistafoundation.vlearning.marketer.model.Vendor;
import co.vistafoundation.vlearning.marketer.model.VendorStudentPayment;
import co.vistafoundation.vlearning.marketer.service.MarketerService;

@RestController
@RequestMapping("api/v1/marketer")
public class MarketerController {

	@Autowired
	MarketerService marketerService;

	/**
	 * @author NAVEEN
	 * 
	 * @param idMarketer
	 * 
	 *                   This method will return list of vendors for a on boarded
	 *                   marketer
	 * 
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MARKETER')")
	@GetMapping("/{idMarketer}/vendors")
	public ResponseEntity<?> getVendors(@PathVariable Long idMarketer) {
		Document<?> doc = marketerService.getVendorsListByMarketer(idMarketer);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get-all-marketers")
	public ResponseEntity<?> getAllMarketers() {
		Document<List<Marketer>> reponses = marketerService.getAllMarketers();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MARKETER')")
	@PutMapping("/make-vendor-payment")
	public ResponseEntity<?> updateVendorPayment(@RequestParam Long idVendor, @RequestParam Long idStudent, @RequestParam Float amount,@RequestParam String status) {
		Document<VendorStudentPayment> reponses = marketerService.makeVendorStudentPayment(idVendor, idStudent, amount,status);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MARKETER')")
	@PutMapping("/make-runner-payment")
	public ResponseEntity<?> updateRunnerPayment(@RequestParam Long idVendor,@RequestParam Float amount,@RequestParam String status) {
		Document<Vendor> reponses = marketerService.makeRunnerPayment(idVendor, amount,status);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
}
