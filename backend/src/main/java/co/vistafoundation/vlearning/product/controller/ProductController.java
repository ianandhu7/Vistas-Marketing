package co.vistafoundation.vlearning.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.dto.ProductPricingDTO;
import co.vistafoundation.vlearning.product.dto.SubjectProductDTO;
import co.vistafoundation.vlearning.product.model.FreeVideo;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductAmount;
import co.vistafoundation.vlearning.product.model.ProductDuration;
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.service.ProductService;

@RestController
@RequestMapping("api/v1/Product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = "")
	public ResponseEntity<?> getAllProduct(@RequestParam(required = false) Long classId,
			@RequestParam(required = true) Long idProductLine, @RequestParam(required = false) String extraCategory) {

		return new ResponseEntity<>(productService.findallproduct(classId, idProductLine, extraCategory),
				HttpStatus.OK);
	}

	@GetMapping(value = "/getProductLine")
	public ResponseEntity<?> getProductLine(@RequestParam(required = true) String productCategory) {

		return new ResponseEntity<>(productService.findProductLineCategory(productCategory), HttpStatus.OK);
	}


	@GetMapping(value = "/getProductGroup")
	public ResponseEntity<?> getProductGroup() {
		Document<?> responses = productService.fetchProdcuctGroup();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}


	@PostMapping(value = "/save-free-videos")
	public ResponseEntity<?> saveFreeVideos(@RequestBody FreeVideo freeVideo) {
		Document<?> responses = productService.saveFreeVideos(freeVideo);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}
	
    /**
     * @author Naveen Kumar A
     * @param idProductLine 
     * 
     * @return This mehtod return product group
     *  for the param provided.
     */

	@GetMapping(value = "/{idProductLine}/product-line")
	public ResponseEntity<?> getProductGroupByIdProductLine(@PathVariable Long idProductLine) {
		Document<?> responses = productService.getAllExtracurricularProductGroupByProductLine(idProductLine);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	
	 /**
     * @author Naveen Kumar A
     * @param idProductLine 
     * 
     * @return This mehtod return list of product 
     *  for the param provided.
     */
	@GetMapping(value = "/{idProductGroup}/product-group")
	public ResponseEntity<Document<List<Product>>> getProductByIdProductGroup(@PathVariable Long idProductGroup) {
		Document<List<Product>> responses = productService.getAllProductByProductGroup(idProductGroup);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@GetMapping(value = "/{idProductLine}/class/{idClassStandard}/subject/{idSubject}/syllabus/{idSyllabus}/state/{idState}")
	public ResponseEntity<Document<Product>> getProductByIdProductLineAndIdClassStandardAndIdSubejctAndIdSyllabusAndIdState(
			@PathVariable Long idProductLine, @PathVariable Long idClassStandard, @PathVariable Long idSubject, @PathVariable Long idSyllabus, @PathVariable Long idState) {
		Document<Product> responses = productService.getProductByIdProduct(idProductLine, idClassStandard, idSubject, idSyllabus, idState);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
    
	 /**
     * @author Naveen Kumar A
     * @param idProductLine 
     * 
     * @return This method will upload sample video 
     *  for the param provided.
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/sample-video")
	public ResponseEntity<?> uploadSampleVideo(@RequestParam MultipartFile file, @RequestParam Long idProduct,
			@RequestParam Long idChapter, @RequestParam(required = false) Long idBatch,
			@RequestParam String description, @RequestParam String type) {
		Document<Object> responses = productService.saveSampleVideo(file, idProduct, idChapter, idBatch, description,
				type);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);

	}


	@GetMapping(value = "/getBatchSampleVideos/{idBatch}")
	public ResponseEntity<?> getBatchSampleVideos(@PathVariable Long idBatch) {
		Document<?> reponses = productService.getBatchSampleVideosbasedonBatch(idBatch);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value= "")
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache"}, allEntries = true)
	public ResponseEntity<?> createProduct(@RequestBody Product product) {

		Document<Product> reponses = productService.createProduct(product);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value= "")
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache"}, allEntries = true)
	public ResponseEntity<?> updateProduct(@RequestBody Product product) {

		Document<Product> reponses = productService.updateProduct(product);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);

	}
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@DeleteMapping(value= "")
//	public ResponseEntity<?> deleteProduct(@PathVariable Long idProduct) {
//
//		Document<Product> reponses = productService.deleteProduct(idProduct);
//		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
//
//	}
//	
	/**
     * @author Naveen Kumar A
     * @param idProductLine 
     * @param idState 
     * @param idSyllabus 
     * @param idClassStandard 
     * 
     * @return This method will return list of  product
     *  for the param provided.
     */
	@GetMapping(value = "/list")
	public ResponseEntity<?> getAllProductListByPLAndSlAndST(@RequestParam Long idProductLine,
			@RequestParam Long idState,
			@RequestParam Long idSyllabus,
			@RequestParam Long idClassStandard
			) {

		Document<List<Product>> reponses = productService.getAllProductByPLAndStateAndSyllabus(idProductLine, idState, idSyllabus, idClassStandard);
		return ResponseEntity.status(reponses.getStatusCode()).body(reponses);
	}
	
	@GetMapping(value = "/getExtraAndAcademic")
	public ResponseEntity<?> getExtraAndAcademic() {
		Document<?> responses = productService.getExtraAcademic();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	
	@GetMapping("/user-subscription-product")
	public ResponseEntity<?> getUserSubscriptionProduct(){
		Document<?> responses = productService.getUserSubscriptionProduct();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@GetMapping("/eca")
	public ResponseEntity<?> getECAProducts(){
		Document<List<SubjectProductDTO>> responses = productService.getAllECAProductSubjects();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	
	@GetMapping(value = "/exam-preparation")
	public ResponseEntity<?> getAllProduct(@RequestParam Long idState, @RequestParam  Long idSyllabus,@RequestParam  Long idClassStandard, @RequestParam String categoryCode) {

		Document<List<Product>> responses = productService.getAllProductForExamPreparation(idState, idSyllabus, idClassStandard, categoryCode);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@GetMapping(value = "/vct")
	public ResponseEntity<?> getAllProduct(@RequestParam Long idState, @RequestParam  Long idSyllabus,@RequestParam  Long idClassStandard) {

		Document<List<Product>> responses = productService.getAllProductForVCT(idState, idSyllabus, idClassStandard);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value="/{idProduct}/active") 
	@CacheEvict(value = { "academicSubjectCache", "extracurricularSubjectCache","chapterCache"}, allEntries = true)
	public ResponseEntity<Document<Product>> updateProductActiveFlag(@PathVariable Long idProduct, @RequestParam Boolean isActive) {

		Document<Product> responses = productService.updateProductActiveFlag(idProduct,isActive);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	/**
     * @author Mohammed Haaris
     * @return This method return list of product  Pricing
     * 
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/product-pricing")
	public ResponseEntity<?> getAllListOfProductPricing() {
		Document<List<ProductPricingDTO>> responses = productService.getAllListOfProductPricing();
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}
	
	/**
     * @author Mohammed Haaris
     * @param idProduct 
     * 
     * @return This method return list of product pricing
     *  for the param provided.
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/{idProduct}/product-pricing")
	public ResponseEntity<Document<List<ProductPricingDTO>>> getProductPricingByProductId(@PathVariable Long idProduct) {
		Document<List<ProductPricingDTO>> responses = productService.getProductPricingByProductId(idProduct);
		return ResponseEntity.status(responses.getStatusCode()).body(responses);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value ="/product-pricing")
    public ResponseEntity<Document<ProductPricing>> createProductPricing(@RequestBody ProductPricing productPricing) {
        Document<ProductPricing> response = productService.createProductPricing(productPricing);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	

	/**
     * @author Mohammed Haaris
     * @param idProduct 
     * 
     * @return This method return list of product pricing
     *  for the param provided.
     */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value="/{idProductPricingId}/product-pricing") 
    public ResponseEntity<Document<ProductPricing>> updateProductPricing(
            @PathVariable Long idProductPricingId,@RequestBody ProductPricing updatedProductPricing) {
        Document<ProductPricing> response = productService.updateProductPricing(idProductPricingId, updatedProductPricing);
        return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value= "/{idProductPricingId}/product-pricing")
    public ResponseEntity<Document<String>> deleteProductPricing(@PathVariable Long idProductPricingId) {
        Document<String> response = productService.deleteProductPricing(idProductPricingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value ="/product-amount")
    public ResponseEntity<Document<ProductAmount>> createProductAmount(@RequestBody ProductAmount productAmount) {
        Document<ProductAmount> response = productService.createProductAmount(productAmount);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value ="/{id}/product-amount")
    public ResponseEntity<Document<ProductAmount>> updateProductAmount(
            @PathVariable Long id,
            @RequestBody ProductAmount updatedProductAmount
    ) {
        Document<ProductAmount> response = productService.updateProductAmount(id, updatedProductAmount);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value ="/{id}/product-amount")
    public ResponseEntity<Document<String>> deleteProductAmount(@PathVariable Long id) {
        Document<String> response = productService.deleteProductAmount(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

	
    
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value ="/product-duration")
    public ResponseEntity<Document<ProductDuration>> createProductDuration(@RequestBody ProductDuration productDuration) {
        Document<ProductDuration> response = productService.createProductDuration(productDuration);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value ="/{id}/product-duration")
    public ResponseEntity<Document<ProductDuration>> updateProductDuration(
            @PathVariable Long id,
            @RequestBody ProductDuration updatedProductDuration
    ) {
        Document<ProductDuration> response = productService.updateProductDuration(id, updatedProductDuration);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value ="/{id}/product-duration")
    public ResponseEntity<Document<String>> deleteProductDuration(@PathVariable Long id) {
        Document<String> response = productService.deleteProductDuration(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@GetMapping(value = "/product-duration")
		public ResponseEntity<Document<List<ProductDuration>>> getAllProductDuration() {
			Document<List<ProductDuration>> responses = productService.getAllListOfProductDuration();
			return ResponseEntity.status(responses.getStatusCode()).body(responses);
		}
		
		@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
		@GetMapping(value = "/product-amount")
		public ResponseEntity<Document<List<ProductAmount>>> getAllProductAmount() {
			Document<List<ProductAmount>> responses = productService.getAllListOfProductAmounts();
			return ResponseEntity.status(responses.getStatusCode()).body(responses);
		}

}
