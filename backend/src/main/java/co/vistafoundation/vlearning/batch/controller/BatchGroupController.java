/**
 * 
 */
package co.vistafoundation.vlearning.batch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.batch.service.BatchGroupService;
import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author NAVEEN
 *
 */
@Controller
@RequestMapping("/api/v1/batch-group")
public class BatchGroupController {

	@Autowired
	BatchGroupService batchGroupService;

	/**
	 * @author NAVEEN
	 * 
	 *         This method will return list of batch groups available.
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<?> getCartItemByUser() {
		Document<List<BatchGroup>> document = batchGroupService.getAllBatchGroup();
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/{idBatchGroup}/batches")
	public ResponseEntity<?> getCartItemByUser(@PathVariable Long idBatchGroup) {
		Document<List<Object>> document = batchGroupService.getAllBatchesForBatchGroup(idBatchGroup);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
	
	
	/**
	 * @author NAVEEN
	 * 
	 *         This method will create new Batch Group.
	 * 
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createBatchGroup(@RequestParam String groupName) {
		Document<BatchGroup> document = batchGroupService.createBatchGroup(groupName);
		return ResponseEntity.status(document.getStatusCode()).body(document);
	}
}
