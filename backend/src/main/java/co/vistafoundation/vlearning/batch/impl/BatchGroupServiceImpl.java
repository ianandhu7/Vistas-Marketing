/**
 * 
 */
package co.vistafoundation.vlearning.batch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchCalender;
import co.vistafoundation.vlearning.batch.model.BatchGroup;
import co.vistafoundation.vlearning.batch.model.SpecialOfferProduct;
import co.vistafoundation.vlearning.batch.repository.BatchCalenderRepository;
import co.vistafoundation.vlearning.batch.repository.BatchGroupRepository;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.SpecialOfferProductRepository;
import co.vistafoundation.vlearning.batch.service.BatchGroupService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.specialoffer.repository.SpecialOfferRepository;

/**
 * @author NAVEEN
 *
 */
@Service
public class BatchGroupServiceImpl implements BatchGroupService {
	@Autowired
	BatchGroupRepository batchGroupRepository;

	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	BatchCalenderRepository batchCalenderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SpecialOfferRepository specialOfferRepository;
	
	@Autowired
	SpecialOfferProductRepository specialOfferProductRepository;
	

	@Override
	public Document<List<BatchGroup>> getAllBatchGroup() {
		Document<List<BatchGroup>> document = new Document<>();

		try {
			List<BatchGroup> result = batchGroupRepository.findAll();

			if (result.isEmpty())
				throw new NullPointerException("No Batch Group data found.");

			document.setData(result);
			document.setMessage("Request Sucessfull.");
			document.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return document;
	}

	@Override
	public Document<List<Object>> getAllBatchesForBatchGroup(Long idBatchGroup) {

		Document<List<Object>> document = new Document<>();
		try {
			List<Object> result = new ArrayList<Object>();
			List<Batch> batches = batchRepository.findByIdBatchGroupAndActiveFlag(idBatchGroup, true);

			if (batches.isEmpty())
				throw new NullPointerException("No Batch Available for the batch group.");
			
			for (Batch batch : batches) 
			{
				if (batch == null)
					throw new NullPointerException("Invalid Batch id.");

				List<BatchCalender> bcList = batchCalenderRepository.findByBatch_idBatch(batch.getIdBatch());

				if (bcList.isEmpty())
					throw new NullPointerException("BatchCalender List Not found.");

				Product product = productRepository.findByIdProductAndActiveFlag(batch.getIdProduct(),Boolean.TRUE);

				if (product == null)
					throw new NullPointerException("Invalid Product id.");

				Map<String, Object> temp = new HashMap<String, Object>();

				temp.put("batch", batch);
				temp.put("batchCalender", bcList);
				temp.put("idClassStandard", product.getIdClassStandard());
				temp.put("idSyllabus", product.getIdSyllabus());
				temp.put("idState", product.getIdState());
				temp.put("idProductLine", product.getIdProductLine());
				temp.put("idSubject", product.getIdSubject());
				
				List<SpecialOfferProduct> soList = specialOfferProductRepository.findByIdBatch(batch.getIdBatch());
				
				if(!soList.isEmpty()) 
				{
					List <Long> idSpecialOffers = soList.stream().map(SpecialOfferProduct::getIdSpecialOffer).collect(Collectors.toList());
					temp.put("idSpecialOffers", idSpecialOffers);
				}
				result.add(temp);
			}

			document.setData(result);
			document.setMessage("Request Sucessfull.");
			document.setStatusCode(HttpStatus.OK.value());
		} catch (Exception e) {
			document.setData(null);
			document.setMessage(e.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return document;
	}

	@Override
	public Document<BatchGroup> createBatchGroup(String groupName) {
		
		Document<BatchGroup> result = new Document<>();
		try {
			BatchGroup bg = new BatchGroup();
			
			bg.setBatchGroupName(groupName);
			BatchGroup temp = batchGroupRepository.save(bg);
		   
			result.setData(temp);
			result.setMessage("Request Sucessfull.");
			result.setStatusCode(HttpStatus.OK.value());
			
		} catch (Exception exp) {
			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Batch Group name already exist.");
					return result;
				}

				else {
					result.setData(null);
					result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					result.setMessage(exp.getLocalizedMessage());
					return result;
				}

			}

			else {
				result.setData(null);
				result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				result.setMessage(exp.getLocalizedMessage());
				return result;
			}
		}

		return result;
	}

}
