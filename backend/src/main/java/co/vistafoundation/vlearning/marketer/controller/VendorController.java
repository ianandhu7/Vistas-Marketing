package co.vistafoundation.vlearning.marketer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.marketer.dto.EditVendorRequestDTO;
import co.vistafoundation.vlearning.marketer.dto.VendorDto;
import co.vistafoundation.vlearning.marketer.service.VendorService;

@RestController
@RequestMapping("api/v1/vendor")
public class VendorController {

	@PreAuthorize("hasAnyRole('ROLE_MARKETER , ROLE_ADMIN')")
	@PostMapping("/create-vendor")
	public ResponseEntity<?> createVendor(@RequestBody VendorDto vendorDto) {
		Document<Object> reponses = vendorService.saveVendor(vendorDto);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}

	@Autowired
	VendorService vendorService;

	/**
	 * @author NAVEEN
	 * 
	 *         This method will return list of vendors for a on boarded marketer
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_MARKETER , ROLE_ADMIN')")
	@GetMapping("")
	public ResponseEntity<?> getAllVendors() {
		Document<?> doc = vendorService.getListOfVendors();
		return ResponseEntity.status(doc.getStatusCode()).body(doc);

	}

	@GetMapping("/{idVendor}/students")
	public ResponseEntity<?> getAllVendors(@PathVariable Long idVendor) {
		Document<?> doc = vendorService.getListOfStudentByVendor(idVendor);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);

	}

 
	@PreAuthorize("hasAnyRole('ROLE_MARKETER , ROLE_ADMIN')")
	@PutMapping("")
	public ResponseEntity<?> updateVendorInfo(@Valid @RequestBody EditVendorRequestDTO request) {
		Document<?> doc = vendorService.editVendorData(request);
		return ResponseEntity.status(doc.getStatusCode()).body(doc);

	}

}
