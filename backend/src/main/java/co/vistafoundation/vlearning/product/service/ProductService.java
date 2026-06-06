package co.vistafoundation.vlearning.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.dto.ProductGroupDTO;
import co.vistafoundation.vlearning.product.dto.ProductPricingDTO;
import co.vistafoundation.vlearning.product.dto.SubjectProductDTO;
import co.vistafoundation.vlearning.product.model.BatchSampleVideo;
import co.vistafoundation.vlearning.product.model.FreeVideo;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductAmount;
import co.vistafoundation.vlearning.product.model.ProductDuration;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.model.ProductLine;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionPlanDTO;
import co.vistafoundation.vlearning.product.model.ProductPricing;

public interface ProductService {

	public ProductGroupDTO findallproduct(Long classStandardId, Long productLineId, String extraCurrActivity);

	public List<ProductLine> findProductLineCategory(String productLine);

	@SuppressWarnings("rawtypes")
	public Document fetchProdcuctGroup();

	@SuppressWarnings("rawtypes")
	public Document saveFreeVideos(FreeVideo freeVideo);

	/**
	 * @author Naveen Kumar
	 * @param idProductLine
	 * @return
	 * 
	 *         Method will list of Product Group for the Extracurricular category
	 * 
	 */
	public Document<List<ProductGroup>> getAllExtracurricularProductGroupByProductLine(Long idProductLine);

	/**
	 * @author Naveen Kumar
	 * @param idProductGroup
	 * @return will return list of product based on product group;
	 */
	public Document<List<Product>> getAllProductByProductGroup(Long idProductGroup);

	/**
	 * @author Naveen Kumar
	 * @param idProductLine,idClassStandard,idSubject,idSyllabus
	 * @return will return product based on idProductLine,idClassStandard,idSubject
	 */
	public Document<Product> getProductByIdProduct(Long idProductLine, Long idClassStandard, Long idSubject,
			Long idSyllabus, Long idState);

	public Document<Object> saveSampleVideo(MultipartFile file, Long idProduct, Long idChapter, Long Idbatch,
			String description, String type);

	public Document<BatchSampleVideo> getBatchSampleVideosbasedonBatch(Long idBatch);

	public Document<Product> createProduct(Product product);

	public Document<Product> updateProduct(Product product);

	public Document<Product> deleteProduct(Long idProduct);

	public Document<List<Product>> getAllProductByPLAndStateAndSyllabus(Long idProductLine, Long idState,
			Long idSyllabus, Long idClassStandard);

	public Document<ProductLine> getExtraAcademic();

	public Document<Product> getUserSubscriptionProduct();

	Document<List<SubjectProductDTO>> getAllECAProductSubjects();

	public Document<List<Product>> getAllProductForExamPreparation(Long idState, Long idSyllabus, Long idClassStandard,
			String categoryCode);

	public Document<List<Product>> getAllProductForVCT(Long idState, Long idSyllabus, Long idClassStandard);

	public Document<Product> updateProductActiveFlag(Long idProduct, Boolean activeFlag);

	public Document<List<ProductDuration>> getAllListOfProductDuration();

	public Document<List<ProductAmount>> getAllListOfProductAmounts();

	public Document<List<ProductPricingDTO>> getProductPricingByProductId(Long idProduct);

	Document<List<ProductPricingDTO>> getAllListOfProductPricing();

	public Document<List<NewSubscriptionPlanDTO>> getUserSubscriptionProduct(String productCd);

	Document<ProductAmount> createProductAmount(ProductAmount productAmount);

	Document<ProductAmount> updateProductAmount(Long id, ProductAmount updatedProductAmount);

	Document<ProductPricing> createProductPricing(ProductPricing productPricing);

	Document<String> deleteProductAmount(Long id);

	Document<ProductDuration> createProductDuration(ProductDuration productDuration);

	Document<ProductDuration> updateProductDuration(Long id, ProductDuration updatedProductDuration);

	Document<String> deleteProductDuration(Long id);

	Document<ProductPricing> updateProductPricing(Long id, ProductPricing updatedProductPricing);

	Document<String> deleteProductPricing(Long id);

	public Document<List<SubscriptionPlanDTO>> getNewUserSubscriptionProduct(String productCd);

}
