package co.vistafoundation.vlearning.productGroup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.productGroup.service.ProductGroupService;

/**
 * 
 * @author Sajini
 *
 */

@RestController
@RequestMapping("api/v1/product-group")
public class ProductGroupController {

	@Autowired
	private ProductGroupService productGroupService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "")
	public ResponseEntity<?> createProductGroup(@RequestBody ProductGroup productGroup) {

		Document<ProductGroup> reponses = productGroupService.createProductGroup(productGroup);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@GetMapping(value = "")
	public ResponseEntity<?> getAllProducts() {

		Document<List<ProductGroup>> reponses = productGroupService.getAllProductGroups();
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "")
	public ResponseEntity<?> updateProductGroup(@RequestBody ProductGroup productGroup) {

		Document<ProductGroup> reponses = productGroupService.updateProductGroup(productGroup);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "")
	public ResponseEntity<?> deleteProductGroup(@PathVariable Long idProductGroup) {

		Document<ProductGroup> reponses = productGroupService.deleteProductGroup(idProductGroup);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	
	@GetMapping(value = "/exam-preparation")
	public ResponseEntity<?> getAllExamPreparationGroups(@RequestParam Long idSyllabus, @RequestParam Long idClassStandard ) {

		Document<List<ProductGroup>> reponses = productGroupService.
				getAllProductGroupsForExamPrepration(idSyllabus, idClassStandard);
		
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	@GetMapping(value = "/vct")
	public ResponseEntity<?> getAllVCTGroups(@RequestParam Long idSyllabus, @RequestParam Long idClassStandard ) {

		Document<List<ProductGroup>> reponses = productGroupService.getAllProductGroupsForVCT(idSyllabus, idClassStandard);
		
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}

}
