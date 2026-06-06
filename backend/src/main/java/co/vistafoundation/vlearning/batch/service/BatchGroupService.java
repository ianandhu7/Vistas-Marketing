/**
 * 
 */
package co.vistafoundation.vlearning.batch.service;

import java.util.List;

import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.common.response.Document;

/**
 * @author NAVEEN
 *
 */
public interface BatchGroupService {

	public Document<List<BatchGroup>> getAllBatchGroup();

	public Document<List<Object>> getAllBatchesForBatchGroup(Long idBatchGroup);
	
	public Document<BatchGroup> createBatchGroup(String groupName);

}
