package co.vistafoundation.vlearning.productGroup.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.productGroup.service.ProductGroupService;

/**
 * 
 * @author Sajini
 *
 */

@Service
public class ProductGroupServiceImpl implements ProductGroupService {

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Override
	public Document<ProductGroup> createProductGroup(ProductGroup productGroup) {

		Document<ProductGroup> result = new Document<ProductGroup>();
		try {
			String category = (productGroup.getExtraCurrCategory() == null) ? "NA" : productGroup.getExtraCurrCategory();
			productGroup.setExtraCurrCategory(category);
			ProductGroup response = productGroupRepository.save(productGroup);
			result.setData(response);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());
		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Group");
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

	@Override
	public Document<List<ProductGroup>> getAllProductGroups() {

		Document<List<ProductGroup>> result = new Document<List<ProductGroup>>();
		try {

			List<ProductGroup> pgList = productGroupRepository.findAll();

			if (pgList.isEmpty())
				throw new NullPointerException("No product group found");
			result.setData(pgList);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

	@Override
	public Document<ProductGroup> updateProductGroup(ProductGroup productGroup) {
		Document<ProductGroup> result = new Document<ProductGroup>();
		try {

			ProductGroup pg = productGroupRepository.findByIdProductGroup(productGroup.getIdProductGroup());
			if (pg == null)
				throw new NullPointerException("Invalid Product Group");
			
			String category = (productGroup.getExtraCurrCategory() == null) ? "NA" : productGroup.getExtraCurrCategory();
			productGroup.setExtraCurrCategory(category);
			productGroup.setCreatedAt(pg.getCreatedAt());
			productGroup.setCreatedBy(pg.getCreatedBy());

			result.setData(productGroupRepository.save(productGroup));
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Group");
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

	@Override
	public Document<ProductGroup> deleteProductGroup(Long idProductGroup) {
		Document<ProductGroup> result = new Document<ProductGroup>();
		try {
			ProductGroup pg = productGroupRepository.findByIdProductGroup(idProductGroup);
			productGroupRepository.delete(pg);
			result.setData(null);
			result.setMessage("ProductGroup deleted successfully!");
			result.setStatusCode(200);
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

	}

	@Override
	public Document<List<ProductGroup>> getAllProductGroupsForExamPrepration(Long idSyllabus, Long idClassStandard) {
		
		Document<List<ProductGroup>> result = new Document<List<ProductGroup>>();
		try {

			List<ProductGroup> pgList = productGroupRepository.
					findByIdClassStandardAndIdSyllabusAndIdProductLine(idClassStandard, idSyllabus,12L);

			if (pgList.isEmpty())
				throw new NullPointerException("No product group found");
			result.setData(pgList);
			
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
		
	}

	@Override
	public Document<List<ProductGroup>> getAllProductGroupsForVCT(Long idSyllabus, Long idClassStandard) {
		
		Document<List<ProductGroup>> result = new Document<List<ProductGroup>>();
		try {

			List<ProductGroup> pgList = productGroupRepository.
					findByIdClassStandardAndIdSyllabusAndIdProductLine(idClassStandard, idSyllabus,13L);

			if (pgList.isEmpty())
				throw new NullPointerException("No product group found");
			result.setData(pgList);
			
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}
		return result;
	}

}
