package co.vistafoundation.vlearning.productGroup.service;

import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.model.ProductGroup;

/**
 * 
 * @author Sajini
 *
 */

public interface ProductGroupService {
	
	public Document<ProductGroup> createProductGroup(ProductGroup productGroup);
	
	public Document<List<ProductGroup>> getAllProductGroups();
	
	public Document<ProductGroup> updateProductGroup(ProductGroup productGroup);
	
	public Document<ProductGroup> deleteProductGroup(Long idProductGroup);
	
	public Document<List<ProductGroup>> getAllProductGroupsForExamPrepration(Long idSyllabus, Long idClassStandard );
	
	public Document<List<ProductGroup>> getAllProductGroupsForVCT(Long idSyllabus, Long idClassStandard );

}
