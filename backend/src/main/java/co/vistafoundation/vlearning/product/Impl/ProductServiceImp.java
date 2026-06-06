package co.vistafoundation.vlearning.product.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.product.dto.ProductDTO;
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
import co.vistafoundation.vlearning.product.model.ProductPricing;
import co.vistafoundation.vlearning.product.model.ProductSampleVideo;
import co.vistafoundation.vlearning.product.repository.BatchSampleVideoRepository;
import co.vistafoundation.vlearning.product.repository.FreeVideoRepository;
import co.vistafoundation.vlearning.product.repository.ProductAmountRepository;
import co.vistafoundation.vlearning.product.repository.ProductDurationRepository;
import co.vistafoundation.vlearning.product.repository.ProductGroupRepository;
import co.vistafoundation.vlearning.product.repository.ProductLineRepository;
import co.vistafoundation.vlearning.product.repository.ProductPricingRepository;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.product.repository.ProductSampleRepository;
import co.vistafoundation.vlearning.product.service.ProductService;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionPlanDTO;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.repository.StateRepository;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository productrepository;

	@Autowired
	private ProductSampleRepository productSampleRepository;

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Autowired
	private ProductLineRepository productLineRepository;

	@Autowired
	private FreeVideoRepository freeVideoRepository;

	@Autowired
	private BatchSampleVideoRepository batchSampleVideoRepository;

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SyllabusRepository syllabusRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private ProductDurationRepository productDurationRepository;

	@Autowired
	private ProductAmountRepository productAmountRepository;

	@Autowired
	private ProductPricingRepository productPricingRepository;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	@PersistenceContext
	private EntityManager entityManager;

	public ProductGroupDTO findallproduct(Long classStandardId, Long productLineId, String extraCurrActivity) {

		ProductGroupDTO prodGroup = new ProductGroupDTO();

		ProductGroup productGroup = new ProductGroup();

		// ProductLineId 7 for Free Videos
		if (productLineId == 7)
			productGroup = productGroupRepository.findByIdProductLine(productLineId);

		else if (extraCurrActivity.equalsIgnoreCase(""))
			productGroup = productGroupRepository.findByIdClassStandardAndIdProductLine(classStandardId, productLineId);
		else
			productGroup = productGroupRepository.findByExtraCurrCategoryAndIdProductLine(extraCurrActivity,
					productLineId);

		if (productGroup != null) {
			prodGroup.setIdProductGroup(productGroup.getIdProductGroup());
			prodGroup.setProductGroupName(productGroup.getProductGroupName());
			prodGroup.setMonthlySubscrAmt(productGroup.getMonthlySubscrAmt());
			prodGroup.setQtrSubscrAmt(productGroup.getQtrSubscrAmt());
			prodGroup.setAnnualSubscrAmt(productGroup.getAnnualSubscrAmt());
			prodGroup.setExtraCurrCategory(productGroup.getExtraCurrCategory());

			List<ProductDTO> result = new ArrayList<ProductDTO>();

			List<Product> prodList = productrepository.findByIdProductGroup(productGroup.getIdProductGroup());

			if (!prodList.isEmpty()) {

				for (Product prod : prodList) {
					ProductDTO prdDtoObject = new ProductDTO();
					prdDtoObject.setIdProduct(prod.getIdProduct());
					prdDtoObject.setIdSubject(prod.getIdSubject());
					prdDtoObject.setProductName(prod.getProductName());
					prdDtoObject.setMonthlySubcrAmt(prod.getMonthlySubcrAmt());
					prdDtoObject.setQtrSubscrAmt(prod.getQtrSubscrAmt());
					prdDtoObject.setAnnualSubscrAmt(prod.getAnnualSubscrAmt());

					List<ProductSampleVideo> productSampleVideolist = productSampleRepository.findByProduct(prod);

					if (!productSampleVideolist.isEmpty()) {
						prdDtoObject.setProductSampleVideo(productSampleVideolist);
					}

					else {
					}

					result.add(prdDtoObject);

				}
				prodGroup.setProductDTO(result);
				return prodGroup;

			}

			else {
				throw new NullPointerException("Product data not found");
			}
		} else {
			throw new NullPointerException("Product Group data not found");
		}

	}

	public List<ProductLine> findProductLineCategory(String productCategory) {

		List<ProductLine> productLinelist = productLineRepository.findByProductCategory(productCategory);

		if (!productLinelist.isEmpty()) {
			return productLinelist;
		}

		else {
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document fetchProdcuctGroup() {

		Document doc = new Document<>();
		try {

			List<ProductGroup> list = productGroupRepository.fetchextraActivities();

			if (list.isEmpty()) {
				throw new NullPointerException("No data found");
			} else {
				doc.setData(list);
				doc.setMessage("Request successfull");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document saveFreeVideos(FreeVideo freeVideo) {

		Document doc = new Document<>();

		try {

			if (freeVideo == null) {
				throw new NullPointerException("Video Data Cannot Be Null");
			}

			Product fetchedProduct = productrepository.findByProductCdAndActiveFlag("FREE_VIDEO", Boolean.TRUE);

			if (fetchedProduct == null) {
				throw new NullPointerException(
						"No Product Found For Product Code as FREE_VIDEO...Please Add FREE_VIDEO Product Code to Product Table");
			}

			freeVideo.setProduct(fetchedProduct);
			FreeVideo saved = freeVideoRepository.save(freeVideo);

			doc.setData(saved);
			doc.setMessage("Sample Video Saved Successfully");
			doc.setStatusCode(201);
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setMessage(e.getLocalizedMessage());
			doc.setStatusCode(500);
			return doc;
		}
	}

	@Override
	public Document<List<ProductGroup>> getAllExtracurricularProductGroupByProductLine(Long idProductLine) {

		Document<List<ProductGroup>> doc = new Document<>();
		try {

			List<ProductGroup> list = productGroupRepository.getByIdProductLine(idProductLine);

			if (list.isEmpty())
				throw new NullPointerException("No data found");

			doc.setData(list);
			doc.setMessage("Request successfull");
			doc.setStatusCode(HttpStatus.OK.value());

			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<Product>> getAllProductByProductGroup(Long idProductGroup) {
		Document<List<Product>> doc = new Document<>();
		try {

			List<Product> list = productrepository.findByIdProductGroup(idProductGroup);

			if (list.isEmpty())
				throw new NullPointerException("No Product data found");

			doc.setData(list);
			doc.setMessage("Request successfull");
			doc.setStatusCode(HttpStatus.OK.value());
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<Product> getProductByIdProduct(Long idProductLine, Long idClassStandard, Long idSubject,
			Long idSyllabus, Long idState) {

		Document<Product> doc = new Document<>();
		try {

			ProductGroup pg = productGroupRepository.findByIdClassStandardAndIdProductLineAndIdSyllabus(idClassStandard,
					idProductLine, idSyllabus);

			if (pg == null)
				throw new NullPointerException("No ProductGroup data found");

			Product product = productrepository
					.findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
							pg.getIdProductLine(), idClassStandard, idSubject, idSyllabus, idState, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product data found");

			doc.setData(product);
			doc.setMessage("Request successfull");
			doc.setStatusCode(HttpStatus.OK.value());

			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}

	}

	@Override
	public Document<Object> saveSampleVideo(MultipartFile file, Long idProduct, Long idChapter, Long idBatch,
			String description, String type) {
		Document<Object> result = new Document<>();
		try {

			Product product = productrepository.findByIdProductAndActiveFlag(idProduct, Boolean.TRUE);

			if (product == null)
				throw new NullPointerException("No Product data found");

			final File tempFile = convertMultiPartFileToFile(file);

			if (type.equalsIgnoreCase("academic") || type.equalsIgnoreCase("extracurricular")) {

				String random = UUID.randomUUID().toString();

				String fileName = product.getProductCd() + "_" + product.getIdSubject() + "_" + idChapter + "_" + random
						+ "_" + tempFile.getName();
				String getUrl = uploadFileToS3Bucket(bucketName + "/sample-video/" + product.getProductCd() + "/"
						+ product.getIdSubject() + "/" + idChapter, tempFile, fileName);

				if (getUrl == null || getUrl.equals(""))
					throw new AppException("Something went wrong while uploading your file.");

				ProductSampleVideo ps = new ProductSampleVideo();
				ps.setChapter(idChapter);
				ps.setIdSubject(product.getIdSubject());
				ps.setProduct(product);
				ps.setVideoDescription(description);
				ps.setVideoLink(getUrl);

				result.setData(productSampleRepository.save(ps));
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request successfull");

			} else if (type.equalsIgnoreCase("batch") && idBatch != 0) {

				Batch batch = batchRepository.findByIdBatch(idBatch);

				if (batch == null)
					throw new NullPointerException("No Batch data found");

				String random = UUID.randomUUID().toString();

				String fileName = product.getProductCd() + "_" + product.getIdSubject() + "_" + batch.getBatchName()
						+ "_" + random + "_" + tempFile.getName();

				String getUrl = uploadFileToS3Bucket(bucketName + "/sample-video/" + product.getProductCd() + "/"
						+ product.getIdSubject() + "/" + batch.getIdBatch(), tempFile, fileName);

				if (getUrl == null || getUrl.equals(""))
					throw new AppException("Something went wrong while uploading your file.");

				BatchSampleVideo bsv = new BatchSampleVideo();
				bsv.setIdBatch(batch.getIdBatch());
				bsv.setIdSubject(product.getIdSubject());
				bsv.setVideoDescription(description);
				bsv.setVideoLink(getUrl);

				result.setData(batchSampleVideoRepository.save(bsv));
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request successfull");

			}

			boolean isDeletedFile = tempFile.delete();
			logger.info("Product Sample video file deleted from the system : " + isDeletedFile);

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
		return result;
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return file;
	}

	private String uploadFileToS3Bucket(String bucketName, File file, String fileName) throws Exception {

		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3.putObject(putObjectRequest);
		String s3Url = amazonS3.getUrl(bucketName, fileName).toString();
		return s3Url;
	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<BatchSampleVideo> getBatchSampleVideosbasedonBatch(Long idBatch) {

		Document result = new Document();
		try {

			List<BatchSampleVideo> sampleList = batchSampleVideoRepository.findByIdBatch(idBatch);

			if (sampleList.isEmpty())
				throw new NullPointerException("No Sample videos available.");

			result.setData(sampleList);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

			return result;

		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}

	}

	@Override
	public Document<Product> createProduct(Product product) {

		Document<Product> result = new Document<Product>();
		try {

			String category = (product.getExtraCurrCategory() == null) ? "NA" : product.getExtraCurrCategory();

			ProductGroup pg = productGroupRepository
					.findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(
							product.getIdClassStandard(), product.getIdProductLine(), product.getIdSyllabus(),
							category);
			if (pg == null)
				throw new NullPointerException("Product Goup Not Found");
			ProductLine pl = productLineRepository.findByidProductLine(product.getIdProductLine());
			if (pl == null)
				throw new NullPointerException("Product Line  Not Found");
			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());
			if (subject == null)
				throw new NullPointerException("Subject  Not Found");
			ClassStandard classStandard = classRepository.findByIdClassStandard(product.getIdClassStandard());
			if (classStandard == null)
				throw new NullPointerException("Class standard Not Found");
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());
			if (syllabus == null)
				throw new NullPointerException("Syllabus Not Found");

			State state = stateRepository.findByIdState(product.getIdState());

			if (state == null)
				throw new NullPointerException("State Not Found");

			// code generation strategy
			// productlinecode__idstate_idsyllabus_idclassstandard_idsubject

			String productCd = pl.getProductLineCd() + "_" + state.getIdState() + "_" + syllabus.getIdSyllabus() + "_"
					+ classStandard.getIdClassStandard() + "_" + subject.getIdSubject();

			product.setIdProductGroup(pg.getIdProductGroup());
			product.setIdSyllabus(pg.getIdSyllabus());
			product.setIdClassStandard(pg.getIdClassStandard());
			product.setIdProductLine(pg.getIdProductLine());
			product.setProductCd(productCd);
			product.setTotalVideoCount(0);
			product.setExtraCurrCategory(pg.getExtraCurrCategory());
			product.setActiveFlag(Boolean.TRUE);
			Product response = productrepository.save(product);
			result.setData(response);
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

			return result;

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product");
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

	}

	@Override
	public Document<Product> updateProduct(Product product) {

		Document<Product> result = new Document<Product>();
		try {

			String category = (product.getExtraCurrCategory() == null) ? "NA" : product.getExtraCurrCategory();

			ProductGroup pg = productGroupRepository
					.findByIdClassStandardAndIdProductLineAndIdSyllabusAndExtraCurrCategory(
							product.getIdClassStandard(), product.getIdProductLine(), product.getIdSyllabus(),
							category);

			if (pg == null)
				throw new NullPointerException("Product Goup Not Found");
			ProductLine pl = productLineRepository.findByidProductLine(product.getIdProductLine());
			if (pl == null)
				throw new NullPointerException("Product Line  Not Found");
			Subject subject = subjectRepository.findByIdSubject(product.getIdSubject());
			if (subject == null)
				throw new NullPointerException("Subject  Not Found");
			ClassStandard classStandard = classRepository.findByIdClassStandard(product.getIdClassStandard());
			if (classStandard == null)
				throw new NullPointerException("Class standard Not Found");
			Syllabus syllabus = syllabusRepository.findByIdSyllabus(product.getIdSyllabus());
			if (syllabus == null)
				throw new NullPointerException("Syllabus Not Found");

			Product temp = productrepository.findByIdProduct(product.getIdProduct());

			if (temp == null)
				throw new NullPointerException("Product Not Found");

			State state = stateRepository.findByIdState(product.getIdState());

			if (state == null)
				throw new NullPointerException("State Not Found");

			// code generation strategy
			// productlinecode__idstate_idsyllabus_idclassstandard_idsubject

			String productCd = pl.getProductLineCd() + "_" + state.getIdState() + "_" + syllabus.getIdSyllabus() + "_"
					+ classStandard.getIdClassStandard() + "_" + subject.getIdSubject();

			product.setIdProductGroup(pg.getIdProductGroup());
			product.setIdSyllabus(pg.getIdSyllabus());
			product.setIdClassStandard(pg.getIdClassStandard());
			product.setIdProductLine(pg.getIdProductLine());
			product.setProductCd(productCd);
			product.setCreatedAt(temp.getCreatedAt());
			product.setCreatedBy(temp.getCreatedBy());
			int count = (temp.getTotalVideoCount() == null) ? 0 : temp.getTotalVideoCount();
			product.setTotalVideoCount(count);
			product.setActiveFlag(temp.getActiveFlag());
			product.setExtraCurrCategory(pg.getExtraCurrCategory());
			result.setData(productrepository.save(product));
			result.setMessage("Request successfull");
			result.setStatusCode(HttpStatus.OK.value());

			return result;
		}

		catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product");
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
	}

	@Override
	public Document<Product> deleteProduct(Long idProduct) {

		Document<Product> result = new Document<>();
		try {
			Product prod = productrepository.findByIdProduct(idProduct);
			if (prod == null) {
				throw new AppException("Product not found for given Id");
			}

			entityManager.joinTransaction();
			Query query = entityManager.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS student_subscr_temp");
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("CREATE TEMPORARY TABLE student_subscr_temp\r\n"
							+ "SELECT idstudent_subscr  FROM student_subscription where idproduct=:id_product")
					.setParameter("id_product", idProduct);
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_chapter_quiz_detail   where  idstudent_chapter_quiz\r\n"
							+ "IN (select idstudent_chapter_quiz  from \r\n"
							+ "student_chapter_quiz scq where scq.idstudent_subscr \r\n"
							+ "in (SELECT idstudent_subscr from student_subscr_temp))");
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_chapter_quiz_answer   where  idstudent_chapter_quiz_question\r\n"
							+ "IN (select idstudent_chapter_quiz_question from student_chapter_quiz_question scqq\r\n"
							+ "JOIN student_chapter_quiz scq  ON scq.idstudent_chapter_quiz = scqq.idstudent_chapter_quiz\r\n"
							+ "where scq.idstudent_subscr IN (SELECT idstudent_subscr from student_subscr_temp))");
			query.executeUpdate();
			query = entityManager.createNativeQuery("delete from student_chapter_quiz where idstudent_subscr\r\n"
					+ " IN (SELECT idstudent_subscr from student_subscr_temp)");
			query.executeUpdate();
			query = entityManager.createNativeQuery("delete from student_completion_fact where  idstudent_subscr  \r\n"
					+ "IN (SELECT idstudent_subscr from student_subscr_temp)");
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					" delete from student_offline_quiz_answer   where  idstudent_offline_quiz_question\r\n"
							+ "IN (select idstudent_offline_quiz_question from student_offline_quiz_question soqq\r\n"
							+ "JOIN student_offline_quiz soq ON soqq.idstudent_offline_quiz = soq.idstudent_offline_quiz\r\n"
							+ "where soq.idstudent_subscr  IN (SELECT idstudent_subscr from student_subscr_temp))");
			query.executeUpdate();
			query = entityManager.createNativeQuery(
					"delete from student_offline_quiz_answer   where  idstudent_offline_quiz_question\r\n"
							+ "IN (select idstudent_offline_quiz_question from student_offline_quiz_question soqq\r\n"
							+ "JOIN student_offline_quiz soq ON soqq.idstudent_offline_quiz = soq.idstudent_offline_quiz\r\n"
							+ "where soq.idstudent_subscr  IN (SELECT idstudent_subscr from student_subscr_temp) )");
			query.executeUpdate();
			query = entityManager
					.createNativeQuery("delete from student_offline_quiz_question where  idstudent_offline_quiz\r\n"
							+ "IN (select idstudent_offline_quiz from student_offline_quiz soq\r\n"
							+ "where soq.idstudent_subscr  IN (SELECT idstudent_subscr from student_subscr_temp) )");
			query.executeUpdate();
			query = entityManager.createNativeQuery("DELETE FROM student_assigned_course WHERE idproduct=:id_product")
					.setParameter("id_product", idProduct);
			query.executeUpdate();
			query = entityManager.createNativeQuery("DELETE FROM student_subscription where idproduct=:id_product")
					.setParameter("id_product", idProduct);
			query.executeUpdate();

			result.setData(null);
			result.setMessage("Product deleted successfully!");
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
	public Document<List<Product>> getAllProductByPLAndStateAndSyllabus(Long idProductLine, Long idState,
			Long idSyllabus, Long idClassStandard) {
		Document<List<Product>> result = new Document<List<Product>>();
		try {
			List<Product> prodList = productrepository.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdState(
					idProductLine, idClassStandard, idSyllabus, idState);

			if (prodList.isEmpty())
				throw new NullPointerException("No Products Found!");

			result.setData(prodList);
			result.setMessage("Request successfull");
			result.setStatusCode(200);
			return result;

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;

		}

	}

	// @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<ProductLine> getExtraAcademic() {

		Document result = new Document();
		try {
			List<ProductLine> productline = productLineRepository.getExtraAndAcademic();
			if (productline.isEmpty())
				throw new NullPointerException("No Products Found!");
			result.setData(productline);
			result.setMessage("Product deleted successfully!");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<Product> getUserSubscriptionProduct() {
		Document result = new Document();
		try {
			Product product = productrepository.getuserSubscription(11L, true);
			result.setData(product);
			result.setMessage("Product data fetched successfully!");
			result.setStatusCode(200);
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}
	

	@Override
	public Document<List<SubjectProductDTO>> getAllECAProductSubjects() {
		Document<List<SubjectProductDTO>> doc = new Document<>();
		try {

			// all values are hardcoded to ECA products
			List<Product> list = productrepository
					.findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(6L, 4L, 4L, 6L,
							Boolean.TRUE);

			if (list.isEmpty())
				throw new NullPointerException("No Product data found");

			List<SubjectProductDTO> finalList = new ArrayList<SubjectProductDTO>();

			for (Product p : list) {
				SubjectProductDTO spd = new SubjectProductDTO();

				BeanUtils.copyProperties(p, spd);

				Subject sub = subjectRepository.findByIdSubject(p.getIdSubject());

				if (sub == null)
					throw new NullPointerException("Invalid Subject data found");

				spd.setSubjectName(sub.getSubjectName());

				finalList.add(spd);

			}

			doc.setData(finalList);
			doc.setMessage("Request successfull");
			doc.setStatusCode(HttpStatus.OK.value());
			return doc;

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<Product>> getAllProductForExamPreparation(Long idState, Long idSyllabus, Long idClassStandard,
			String categoryCode) {

		Document<List<Product>> doc = new Document<>();
		try {

			List<Product> list = productrepository
					.findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndActiveFlag(
							idState, idSyllabus, idClassStandard, 12L, categoryCode, Boolean.TRUE);

			if (list.isEmpty()) {
				throw new NullPointerException("No Product data found.");
			} else {
				doc.setData(list);
				doc.setMessage("Request successfull");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<List<Product>> getAllProductForVCT(Long idState, Long idSyllabus, Long idClassStandard) {
		Document<List<Product>> doc = new Document<>();
		try {

			List<Product> list = productrepository
					.findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndActiveFlag(
							idState, idSyllabus, idClassStandard, 13L, "VCT", Boolean.TRUE);

			if (list.isEmpty()) {
				throw new NullPointerException("No Product data found.");
			} else {
				doc.setData(list);
				doc.setMessage("Request successfull");
				doc.setStatusCode(200);
				return doc;
			}

		} catch (Exception e) {
			doc.setData(null);
			doc.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			doc.setMessage(e.getLocalizedMessage());
			return doc;
		}
	}

	@Override
	public Document<Product> updateProductActiveFlag(Long idProduct, Boolean activeFlag) {
		Document<Product> result = new Document<>();

		try {

			Product product = productrepository.findByIdProduct(idProduct);

			if (product == null)
				throw new NullPointerException("No product found !");

			product.setActiveFlag(activeFlag);
			product = productrepository.save(product);

			result.setData(product);
			result.setMessage("Successfully updated");
			result.setStatusCode(200);
		} catch (Exception exp) {
			result.setData(null);
			result.setMessage("Internal Server Error");
			result.setStatusCode(500);
		}
		return result;
	}

	@Override
	public Document<List<ProductDuration>> getAllListOfProductDuration() {

		Document<List<ProductDuration>> result = new Document<>();

		try {
			List<ProductDuration> listOfDuratios = productDurationRepository.findAll();
			listOfDuratios.sort(Comparator.comparingInt(ProductDuration::getDuration));
			if (listOfDuratios.isEmpty())
				throw new NullPointerException("No product duration data found.");

			result.setData(listOfDuratios);
			result.setMessage("Successfully updated");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage("Internal Server Error");
			result.setStatusCode(500);
		}

		return result;

	}

	@Override
	public Document<List<ProductAmount>> getAllListOfProductAmounts() {

		Document<List<ProductAmount>> result = new Document<>();

		try {
			List<ProductAmount> listOfDuratios = productAmountRepository.findAll();
			listOfDuratios.sort(Comparator.comparingDouble(ProductAmount::getAmount));
			if (listOfDuratios.isEmpty())
				throw new NullPointerException("No product amount data found.");

			result.setData(listOfDuratios);
			result.setMessage("Successfully updated");
			result.setStatusCode(200);

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage("Internal Server Error");
			result.setStatusCode(500);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<List<NewSubscriptionPlanDTO>> getUserSubscriptionProduct(String productCd) {
		Document<List<NewSubscriptionPlanDTO>> result = new Document();
		try {

			List<Product> productList = productrepository.findByIdProductLineAndActiveFlag(11L, Boolean.TRUE);
            if (productList == null)
                throw new AppException("No product found.");
            List<NewSubscriptionPlanDTO> subscriptionPlansList= new ArrayList<>();
            
            for(Product  p:productList) {

                List<NewSubscriptionPlanDTO>    subscriptionPlans = productrepository.getSubscriptionPlan(p.getIdProduct());
                subscriptionPlansList.addAll(subscriptionPlans);
        
            }
            subscriptionPlansList = subscriptionPlansList.stream()
                    .sorted(Comparator.comparing(NewSubscriptionPlanDTO::getAmount)).collect(Collectors.toList());
            result.setData(subscriptionPlansList);
            result.setMessage("Subscription plan data fetched successfully!");
            result.setStatusCode(200);
            return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document<List<SubscriptionPlanDTO>> getNewUserSubscriptionProduct(String productCd) {
		Document<List<SubscriptionPlanDTO>> result = new Document();
		try {

			Product product = productrepository.findByProductCdAndActiveFlag(productCd, true);
			if (product == null)
				throw new AppException("No product found.");

			List<SubscriptionPlanDTO> subscriptionPlans = productrepository
					.getNewSubscriptionPlan(product.getIdProduct());
			subscriptionPlans = subscriptionPlans.stream()
					.sorted(Comparator.comparing(SubscriptionPlanDTO::getAmount)).collect(Collectors.toList());
			result.setData(subscriptionPlans);
			result.setMessage("Subscription plan data fetched successfully!");
			result.setStatusCode(200);
			return result;
		} catch (Exception e) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(e.getLocalizedMessage());
			return result;
		}
	}

	@Override
	public Document<ProductPricing> createProductPricing(ProductPricing productPricing) {
		Document<ProductPricing> result = new Document<>();
		try {
			ProductPricing createdProductPricing = productPricingRepository.save(productPricing);
			result.setData(createdProductPricing);
			result.setMessage("Product Pricing created successfully!");
			result.setStatusCode(HttpStatus.CREATED.value());
		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Pricing");
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
	public Document<List<ProductPricingDTO>> getAllListOfProductPricing() {
		Document<List<ProductPricingDTO>> result = new Document<>();
		try {
			List<ProductPricingDTO> listOfPricing = productPricingRepository.findAllProductPricing();

			if (listOfPricing.isEmpty())
				throw new AppException("No product pricing data found.");

			result.setData(listOfPricing);
			result.setMessage("Successfully updated");
			result.setStatusCode(HttpStatus.OK.value());

		}

		catch (Exception exp) {
			result.setData(null);
			result.setMessage(exp.getMessage());
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return result;
	}

	@Override
	public Document<List<ProductPricingDTO>> getProductPricingByProductId(Long productId) {
		Document<List<ProductPricingDTO>> document = new Document<>();
		try {
			List<ProductPricingDTO> productPricinglist = productPricingRepository.findProductPricingListById(productId);
			if (productPricinglist.isEmpty())
				throw new AppException("Product pricing not Found");

			document.setData(productPricinglist);
			document.setMessage("Product pricing list fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {
			document.setData(null);
			document.setMessage(" Product pricing Data not found");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		}

		return document;
	}

	@Override
	public Document<ProductPricing> updateProductPricing(Long id, ProductPricing updatedProductPricing) {
		Document<ProductPricing> result = new Document<>();
		try {
			ProductPricing productPricing = productPricingRepository.getByIdProductPricing(id);
			if (productPricing == null)
				throw new AppException("Product pricing not found");

			productPricing.setPromoText(updatedProductPricing.getPromoText());
			productPricing.setPlanDescription(updatedProductPricing.getPlanDescription());
			productPricing.setIdProduct(updatedProductPricing.getIdProduct());
			productPricing.setIdProductDuration(updatedProductPricing.getIdProductDuration());
			productPricing.setIdProductAmount(updatedProductPricing.getIdProductAmount());
			productPricing.setActiveFlag(updatedProductPricing.getActiveFlag());
			ProductPricing updatedProductPricingData = productPricingRepository.save(productPricing);

			result.setData(updatedProductPricingData);
			result.setMessage("Product Pricing updated successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Pricing");
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
	public Document<String> deleteProductPricing(Long id) {
		Document<String> result = new Document<>();
		try {
			if (!productPricingRepository.existsById(id))
				throw new AppException("Product pricing not found");

			productPricingRepository.deleteById(id);
			result.setData("Product Pricing deleted");
			result.setMessage("Product Pricing deleted successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * @author Mohammed Haaris
	 * @param productAmount object
	 * 
	 * @return This mehtod creates the product Amount.
	 */

	@Override
	public Document<ProductAmount> createProductAmount(ProductAmount productAmount) {
		Document<ProductAmount> result = new Document<>();
		try {
			productAmount.setAmountName(productAmount.getAmount().toString());
			ProductAmount createdProductAmount = productAmountRepository.save(productAmount);
			result.setData(createdProductAmount);
			result.setMessage("Product Amount created successfully!");
			result.setStatusCode(HttpStatus.CREATED.value());
		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Amountt");
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

	/**
	 * @author Mohammed Haaris
	 * @param updatedProductAmount object
	 * @return This mehtod updates the product Amount.
	 */
	@Override
	public Document<ProductAmount> updateProductAmount(Long id, ProductAmount updatedProductAmount) {
		Document<ProductAmount> result = new Document<>();
		try {
			ProductAmount productAmount = productAmountRepository.getByIdProductAmount(id);

			if (productAmount == null)
				throw new AppException("product Amount not found");

			productAmount.setAmount(updatedProductAmount.getAmount());
			productAmount.setOldAmount(updatedProductAmount.getOldAmount());
			productAmount.setAmountName(updatedProductAmount.getAmountName());
			productAmount.setAmountCode(updatedProductAmount.getAmountCode());

			ProductAmount updatedProductAmpount = productAmountRepository.save(productAmount);

			result.setData(updatedProductAmpount);
			result.setMessage("Product Amount Updated successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product");
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

	/**
	 * @author Mohammed Haaris
	 * @param id idProductAmunt
	 * @return This mehtod deletes the product Amount.
	 */

	@Override
	public Document<String> deleteProductAmount(Long id) {
		Document<String> result = new Document<>();
		try {
			ProductPricing productPricing = productPricingRepository.findByIdProductAmount(id);

			if (productPricing != null) {
				throw new AppException("The Product Amount is present in the Product Pricing Table");
			}
			if (!productAmountRepository.existsById(id))
				throw new AppException("Product Amount not found");

			productAmountRepository.deleteById(id);
			result.setData("Product Amount deleted successfully!");
			result.setMessage("Product Amount deleted successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * @author Mohammed Haaris
	 * @param productDuration
	 * @return This mehtod creates the product Duration.
	 */
	@Override
	public Document<ProductDuration> createProductDuration(ProductDuration productDuration) {
		Document<ProductDuration> result = new Document<>();
		try {
			ProductDuration createdProductDuration = productDurationRepository.save(productDuration);
			result.setData(createdProductDuration);
			result.setMessage("Product Duration created successfully!");
			result.setStatusCode(HttpStatus.CREATED.value());
		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product duration");
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

	/**
	 * @author Mohammed Haaris
	 * @param updatedProductDuration
	 * @return This mehtod updates the product Duration.
	 */
	@Override
	public Document<ProductDuration> updateProductDuration(Long id, ProductDuration updatedProductDuration) {
		Document<ProductDuration> result = new Document<>();
		try {
			ProductDuration productDuration = productDurationRepository.getByIdProductDuration(id);

			if (productDuration == null)
				throw new AppException("Product duration not found");

			productDuration.setDuration(updatedProductDuration.getDuration());
			productDuration.setDurationName(updatedProductDuration.getDurationName());
			productDuration.setDurationCode(updatedProductDuration.getDurationCode());

			ProductDuration updatedProduct = productDurationRepository.save(productDuration);
			result.setData(updatedProduct);
			result.setMessage("Product Duration updated successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {

			if (exp.getCause() != null) {

				if (exp.getCause().getCause().getLocalizedMessage().substring(0, 15)
						.equalsIgnoreCase("Duplicate Entry")) {
					result.setStatusCode(HttpStatus.CONFLICT.value());
					result.setMessage("Duplicate Product Duration");
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

	/**
	 * @author Mohammed Haaris
	 * @param id ie id
	 * @return This mehtod updates the product Duration.
	 */
	@Override
	public Document<String> deleteProductDuration(Long id) {
		Document<String> result = new Document<>();
		ProductPricing productPricingData = productPricingRepository.findByIdProductDuration(id);
		if (productPricingData != null) {
			throw new AppException("The Product Duration is is present in the Product Pricing Table");
		}
		try {
			if (!productDurationRepository.existsById(id))
				throw new AppException("product duration not found");

			productDurationRepository.deleteById(id);
			result.setData("Product Duration Deleted ");
			result.setMessage("Product Duration deleted successfully!");
			result.setStatusCode(HttpStatus.OK.value());

		} catch (Exception exp) {
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
		}
		return result;
	}

}