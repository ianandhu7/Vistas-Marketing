package co.vistafoundation.vlearning.product.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.product.dto.ProductDTO;
import co.vistafoundation.vlearning.product.dto.ProductGroupDTO;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.model.ProductGroup;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.product.repository.ProductSampleRepository;
import co.vistafoundation.vlearning.product.service.ProductService;

@SpringBootTest
class ProductServiceImpTest {

	@Autowired
	ProductService productService;

	@MockBean
	private ProductRepository productrepository;

	@MockBean
	private ProductSampleRepository productSampleRepository;

	@MockBean
	private ProductGroupRepository productGroupRepository;

	@MockBean
	private ProductLineRepository productLineRepository;

	@BeforeEach
	public void setUp() throws ParseException {

		// productGroup

		List<ProductGroup> pgList = new ArrayList<ProductGroup>();
		ProductGroup pg = new ProductGroup();

		pg.setAnnualSubscrAmt(30000.00f);
		pg.setExtraCurrCategory(null);
		pg.setIdClassStandard(1L);
		pg.setIdProductGroup(1L);
		pg.setIdProductLine(1L);
		pg.setMonthlySubscrAmt(3000f);
		pg.setProductGroupName("BATCH_GROUP_1");
		pg.setQtrSubscrAmt(11000f);
		pg.setIdSyllabus(1L);
		ProductGroup pg1 = new ProductGroup();
		pg1.setAnnualSubscrAmt(30000.00f);
		pg1.setExtraCurrCategory(null);
		pg1.setIdClassStandard(1L);
		pg1.setIdProductGroup(2L);
		pg1.setIdProductLine(2L);
		pg1.setMonthlySubscrAmt(3000f);
		pg1.setProductGroupName("BATCH_GROUP_5");
		pg1.setQtrSubscrAmt(11000f);
		pg1.setIdSyllabus(2L);

		pgList.add(pg);
		pgList.add(pg1);

		Mockito.when(productGroupRepository.getByIdProductLine(Mockito.anyLong())).thenReturn(pgList);
		Mockito.when(productGroupRepository.findByIdClassStandardAndIdProductLine(1L, 1L)).thenReturn(pg);
		
		ProductGroup pg2 = new ProductGroup();
		pg2.setAnnualSubscrAmt(30000.00f);
		pg2.setExtraCurrCategory("Maths");
		pg2.setIdClassStandard(1L);
		pg2.setIdProductGroup(3L);
		pg2.setIdProductLine(3L);
		pg2.setMonthlySubscrAmt(3000f);
		pg2.setProductGroupName("BATCH_GROUP_5");
		pg2.setQtrSubscrAmt(11000f);	
		pg2.setIdSyllabus(3L);
		Mockito.when(productGroupRepository.findByExtraCurrCategoryAndIdProductLine("Maths", 3L)).thenReturn(pg2);
		Mockito.when(productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(1L, 1L, 1L)).thenReturn(pg);
		

		List<Product> productList = new ArrayList<Product>();

		Product p1 = new Product();
		p1.setAgeGroup("14-16");
		p1.setAnnualSubscrAmt(30000f);
		p1.setIdClassStandard(1L);
		p1.setIdProduct(1L);
		p1.setIdProductGroup(1L);
		p1.setIdSubject(1L);
		p1.setMonthlySubcrAmt(3000f);
		p1.setProductCd("BATCH_1_MATHS_10");
		p1.setProductName("Batch of 1 Student, Maths- Class 10");
		p1.setQtrSubscrAmt(10000f);
		p1.setBatchSize(1);
		p1.setIdSyllabus(1L);

		productList.add(p1);

		Product p2 = new Product();
		p2.setAgeGroup("14-16");
		p2.setAnnualSubscrAmt(30000f);
		p2.setIdClassStandard(2L);
		p2.setIdProduct(2L);
		p2.setIdProductGroup(2L);
		p2.setIdSubject(1L);
		p2.setMonthlySubcrAmt(3000f);
		p2.setProductCd("BATCH_1_MATHS_9");
		p2.setProductName("Batch of 1 Student, Maths- Class 9");
		p2.setQtrSubscrAmt(10000f);
		p2.setBatchSize(1);
		p2.setIdSyllabus(2L);

		productList.add(p2);

		Mockito.when(productrepository.findByIdProductGroupAndActiveFlag(1L,true)).thenReturn(productList);
		Mockito.when(productrepository.findByIdProductGroupAndIdClassStandardAndIdSubjectAndActiveFlag(1L, 1L, 1L,Boolean.TRUE)).thenReturn(p1);
		
		List<Product> productList1 = new ArrayList<Product>();

		Product p3 = new Product();
		p3.setAgeGroup("14-16");
		p3.setAnnualSubscrAmt(30000f);
		p3.setIdClassStandard(1L);
		p3.setIdProduct(1L);
		p3.setIdProductGroup(3L);
		p3.setIdSubject(1L);
		p3.setMonthlySubcrAmt(3000f);
		p3.setProductCd("BATCH_1_MATHS_10");
		p3.setProductName("Batch of 1 Student, Maths- Class 10");
		p3.setQtrSubscrAmt(10000f);
		p3.setBatchSize(1);
		p3.setIdSyllabus(3L);

		productList1.add(p3);

		Product p4 = new Product();
		p4.setAgeGroup("14-16");
		p4.setAnnualSubscrAmt(30000f);
		p4.setIdClassStandard(2L);
		p4.setIdProduct(2L);
		p4.setIdProductGroup(3L);
		p4.setIdSubject(1L);
		p4.setMonthlySubcrAmt(3000f);
		p4.setProductCd("BATCH_1_MATHS_9");
		p4.setProductName("Batch of 1 Student, Maths- Class 9");
		p4.setQtrSubscrAmt(10000f);
		p4.setBatchSize(1);
		p4.setIdSyllabus(1L);
		p4.setIdState(1L);

		productList1.add(p4);
		Mockito.when(productrepository.findByIdProductGroupAndActiveFlag(3L,true)).thenReturn(productList);
		Mockito.when(productrepository.findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(
				1L,1L,1L,1L,Boolean.TRUE)).thenReturn(p4);
		Mockito.when(productrepository.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
				1L, 1L, 1L, 1L, 1L,Boolean.TRUE)).thenReturn(p4);

		List<ProductSampleVideo> ProductSampleVideoList = new ArrayList<ProductSampleVideo>();
		
		ProductSampleVideo productSampleVideo  = new ProductSampleVideo();
		productSampleVideo.setChapter(1L);
		productSampleVideo.setIdProductSampleVideo(1L);
		productSampleVideo.setIdSubject(1L);
		productSampleVideo.setVideoDescription("Description");
		productSampleVideo.setProduct(p3);
		productSampleVideo.setVideoLink("Link 1");
		
		ProductSampleVideoList.add(productSampleVideo);
		
		ProductSampleVideo productSampleVideo1  = new ProductSampleVideo();
		productSampleVideo1.setChapter(1L);
		productSampleVideo1.setIdProductSampleVideo(2L);
		productSampleVideo1.setIdSubject(1L);
		productSampleVideo1.setVideoDescription("Description1");
		productSampleVideo1.setProduct(p3);
		productSampleVideo1.setVideoLink("Link 2");
		
		ProductSampleVideoList.add(productSampleVideo1);
		Mockito.when(productSampleRepository.findByProduct(Mockito.any(Product.class))).thenReturn(ProductSampleVideoList);
		
		
		

	}

	@Test
	void testFindallproduct() {
		ProductGroupDTO list = productService.findallproduct(1L, 3L, "Maths");
		
		assertEquals(3L, list.getIdProductGroup());
		assertEquals("BATCH_GROUP_5", list.getProductGroupName());
		assertEquals(30000f, list.getAnnualSubscrAmt());
		assertEquals(3000f, list.getMonthlySubscrAmt());
		assertEquals(11000, list.getQtrSubscrAmt());
		assertEquals("Maths", list.getExtraCurrCategory());
		
		List<ProductDTO> ProductDTOlist = list.getProductDTO();
		
		assertEquals(2, ProductDTOlist.size());
		assertEquals(1, ProductDTOlist.get(0).getIdProduct());
		assertEquals(1, ProductDTOlist.get(0).getIdSubject());
		assertEquals("Batch of 1 Student, Maths- Class 10", ProductDTOlist.get(0).getProductName());
		assertEquals(3000f, ProductDTOlist.get(0).getMonthlySubcrAmt());
		
		List<ProductSampleVideo> sampleList = ProductDTOlist.get(0).getProductSampleVideo();
		
		assertEquals(2, sampleList.size());
		
		assertEquals("Description", sampleList.get(0).getVideoDescription());
		assertEquals("Link 1", sampleList.get(0).getVideoLink());
		
		assertEquals("Description1", sampleList.get(1).getVideoDescription());
		assertEquals("Link 2", sampleList.get(1).getVideoLink());
		
		
		
		assertEquals(2, ProductDTOlist.get(1).getIdProduct());
		assertEquals(1, ProductDTOlist.get(1).getIdSubject());
		assertEquals("Batch of 1 Student, Maths- Class 9", ProductDTOlist.get(1).getProductName());
		assertEquals(3000f, ProductDTOlist.get(1).getMonthlySubcrAmt());
		
		
		
		
	}

	// @Test
	void testFindProductLineCategory() {
		fail("Not yet implemented"); // TODO
	}

	// @Test
	void testFetchProdcuctGroup() {
		fail("Not yet implemented"); // TODO
	}

	// @Test
	void testSaveProductSampleFreeYoutubeVideos() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetAllExtracurricularProductGroupByProductLine() {

		Document<List<ProductGroup>> doc = productService.getAllExtracurricularProductGroupByProductLine(1L);

		assertEquals(200, doc.getStatusCode());

		List<ProductGroup> obj = doc.getData();

		assertEquals(2, obj.size());
		assertEquals(1L, obj.get(0).getIdProductGroup());
		assertEquals(30000.00f, obj.get(0).getAnnualSubscrAmt());
		assertEquals(null, obj.get(0).getExtraCurrCategory());
		assertEquals(1L, obj.get(0).getIdClassStandard());
		assertEquals(1L, obj.get(0).getIdProductLine());
		assertEquals(3000f, obj.get(0).getMonthlySubscrAmt());
		assertEquals("BATCH_GROUP_1", obj.get(0).getProductGroupName());
		assertEquals(11000f, obj.get(0).getQtrSubscrAmt());

		assertEquals(2L, obj.get(1).getIdProductGroup());
		assertEquals(30000.00f, obj.get(1).getAnnualSubscrAmt());
		assertEquals(null, obj.get(1).getExtraCurrCategory());
		assertEquals(1L, obj.get(1).getIdClassStandard());
		assertEquals(2L, obj.get(1).getIdProductLine());
		assertEquals(3000f, obj.get(1).getMonthlySubscrAmt());
		assertEquals("BATCH_GROUP_5", obj.get(1).getProductGroupName());
		assertEquals(11000f, obj.get(1).getQtrSubscrAmt());

	}

	@Test
	void testGetAllProductByProductGroup() {

		Document<List<Product>> doc = productService.getAllProductByProductGroup(1L);

		assertEquals(200, doc.getStatusCode());

		List<Product> obj = doc.getData();
		assertEquals(2, obj.size());
		assertEquals("14-16", obj.get(0).getAgeGroup());
		assertEquals(30000f, obj.get(0).getAnnualSubscrAmt());
		assertEquals(1, obj.get(0).getBatchSize());
		assertEquals(1L, obj.get(0).getIdClassStandard());
		assertEquals(1L, obj.get(0).getIdProduct());
		assertEquals(1L, obj.get(0).getIdProductGroup());
		assertEquals(1L, obj.get(0).getIdSubject());
		assertEquals(3000f, obj.get(0).getMonthlySubcrAmt());
		assertEquals("BATCH_1_MATHS_10", obj.get(0).getProductCd());
		assertEquals("Batch of 1 Student, Maths- Class 10", obj.get(0).getProductName());
		assertEquals(10000f, obj.get(0).getQtrSubscrAmt());

		assertEquals("14-16", obj.get(1).getAgeGroup());
		assertEquals(30000f, obj.get(1).getAnnualSubscrAmt());
		assertEquals(1, obj.get(1).getBatchSize());
		assertEquals(2L, obj.get(1).getIdClassStandard());
		assertEquals(2L, obj.get(1).getIdProduct());
		assertEquals(2L, obj.get(1).getIdProductGroup());
		assertEquals(1L, obj.get(1).getIdSubject());
		assertEquals(3000f, obj.get(1).getMonthlySubcrAmt());
		assertEquals("BATCH_1_MATHS_9", obj.get(1).getProductCd());
		assertEquals("Batch of 1 Student, Maths- Class 9", obj.get(1).getProductName());
		assertEquals(10000f, obj.get(1).getQtrSubscrAmt());

		Document<List<Product>> doc1 = productService.getAllProductByProductGroup(2L);

		assertEquals(500, doc1.getStatusCode());
		assertEquals(null, doc1.getData());
		assertEquals("No Product data found", doc1.getMessage());
		

	}

	@Test
	void testGetProductByIdProduct() {

		Document<Product> doc = productService.getProductByIdProduct(1L, 1L, 1L, 1L, 1L);
		
		assertEquals(200, doc.getStatusCode());
		Product obj = doc.getData();

		assertEquals("14-16", obj.getAgeGroup());
		assertEquals(30000f, obj.getAnnualSubscrAmt());
		assertEquals(1, obj.getBatchSize());
		assertEquals(2L, obj.getIdClassStandard());
		assertEquals(2L, obj.getIdProduct());
		assertEquals(3L, obj.getIdProductGroup());
		assertEquals(1L, obj.getIdSubject());
		assertEquals(1L, obj.getIdSyllabus());
		assertEquals(3000f, obj.getMonthlySubcrAmt());
		assertEquals("BATCH_1_MATHS_9", obj.getProductCd());
		assertEquals("Batch of 1 Student, Maths- Class 9", obj.getProductName());
		assertEquals(10000f, obj.getQtrSubscrAmt());

		Document<Product> doc1 = productService.getProductByIdProduct(2L, 1L, 1L, 1L,1L);
		assertEquals(500, doc1.getStatusCode());
		assertEquals(null, doc1.getData());
		assertEquals("No ProductGroup data found", doc1.getMessage());

		Document<Product> doc2 = productService.getProductByIdProduct(1L, 1L, 2L, 1L,1L);
		assertEquals(500, doc2.getStatusCode());
		assertEquals(null, doc2.getData());
		assertEquals("No Product data found", doc2.getMessage());

	}

}
