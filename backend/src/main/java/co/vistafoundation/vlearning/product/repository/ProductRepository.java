package co.vistafoundation.vlearning.product.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.product.dto.NewSubscriptionPlanV2DTO;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO;
import co.vistafoundation.vlearning.subscription.dto.SubscriptionPlanDTO;

public interface ProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findByIdProductGroupAndActiveFlag(Long idProductGroup,Boolean activeFlag);

	
	public Product findByIdProductAndActiveFlag(Long idProduct, Boolean activeFlag);

	public Product findByIdProductGroupAndIdClassStandardAndIdSubjectAndActiveFlag(Long idProductGroup, Long idClassStandard,
			Long idSubject,Boolean activeFlag);

	public Product findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(Long idProductGroup,
			Long idClassStandard, Long idSubject, Long idSyllabus,Boolean activeFlag);

	@Query(value = "SELECT idproduct FROM vlearning_dev.product where idproduct_group=:idProductGroup and active_flag=true", nativeQuery = true)
	public List<Long> findAllIdProduct(@Param("idProductGroup") Long idProductGroup);

	public Product findByProductCdAndActiveFlag(String productCode ,Boolean activeFlag);

	public Product findByIdProductGroupAndIdSubjectAndActiveFlag(Long idProductGroup, Long idSubject, 
			Boolean activeFlag);

	public List<Product> findByIdClassStandardAndActiveFlag(Long idClassStandard,Boolean activeFlag);

	public List<Product> findByIdClassStandardAndIdSubjectAndActiveFlag(
			Long idClassStandard, Long idSubject, Boolean activeFlag);

	// public Product
	// findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdState(Long
	// idProductLine, Long idClassStandard, Long idSubject,Long idSyllabus,Long
	// idState);

	public Product findByIdProductLineAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(long l,
			Long idClassStandard, Long idSubject, Long idsyllabus, Long idState,Boolean activeFlag);

	public List<Product> findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(Long idProductLine,
			Long idClassStandard, Long idSyllabus, Long idState,Boolean activeFlag);

	public Product findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdStateAndActiveFlag(Long idProductGroup,
			Long idClassStandard, Long idSubject, Long idState,Boolean activeFlag);

	public List<Product> findByIdProductLineAndIdClassStandardAndActiveFlag(
			long l, Long idClassStandard, Boolean activeFlag);

	public List<Product> findByIdClassStandardAndIdSubjectAndIdSyllabusAndActiveFlag(Long idClassStandard, Long idSubject,
			Long idSyllabus, Boolean activeFlag);

	public List<Product> findByIdClassStandardAndIdSyllabusAndActiveFlag(
			Long idClassStandard, Long idSyllabus, Boolean activeFlag);

	public Product findByIdProductGroupAndIdSubjectAndIdStateAndActiveFlag(
			Long idProductGroup, Long idSubject, Long idState, Boolean activeFlag);

	public List<Product> findByIdProductGroupInAndActiveFlag(
			List<Long> idProductGroupList,Boolean activeFlag);
	
	public List<Product> findByIdProductIn (List<Long> idProductList);


	public List<Product> findByIdProductLineAndActiveFlag(Long idProductLine,Boolean activeFlag);

	@Query(value = "SELECT * FROM product where idproduct_line=:idProductLine and active_flag=:activeFlag limit 1", nativeQuery = true)
	public Product getuserSubscription(Long idProductLine,Boolean activeFlag);

	public List<Product> findByActiveFlagAndIdClassStandardNot(Boolean activeFlag, Long l);

	public Product findByIdSubjectAndIdProductGroupAndIdProductLineAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
			Long idSubject, Long idProductGroup, Long idProductLine, Long idClassStandard, Long idState,
			Long idSyllabus,Boolean activeFlag);

	public List<Product> findByIdProductGroupAndIdProductLineAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlag(
			Long idProductGroup, Long idProductLine, Long idClassStandard, Long idState, Long idSyllabus,Boolean activeFlag);

	@Query(value = "SELECT product FROM Product product join Subject s ON (product.idSubject=s.idSubject)"
			+ " where product.idProductGroup=:idProductGroup" + " and product.idProductLine=:idProductLine"
			+ " and product.idClassStandard=:idClassStandard" + " and product.idState=:idState"
			+ " and product.idSyllabus=:idSyllabus and product.activeFlag=:activeFlag")
	public List<Product> findByAllSubjectProducts(Long idProductGroup, Long idProductLine, Long idClassStandard,
			Long idState, Long idSyllabus, Boolean activeFlag);

	public List<Product> findByIdClassStandardAndIdSyllabusAndIdProductLineAndActiveFlag(Long idClassStandard, Long idSyllabus,
			Long idProductLine,Boolean activeFlag);

	public Product findByIdProductGroupAndIdSubjectAndIdStateAndExtraCurrCategoryAndActiveFlag(
			Long idProductGroup, Long idSubject,
			Long idState, String extraCurrCategory, Boolean activeFlag);

	public Product findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndIdSubjectAndActiveFlag(long l,
			Long idClassStandard, Long idSyllabus, Long idState, Long idSubject,Boolean activeFlag);


	public List<Product> findByIdProductGroupAndIdStateAndActiveFlag(
			Long idProductGroup, Long idState, Boolean activeFlag);

	
	public Product findByIdProductGroupAndIdClassStandardAndIdSubjectAndIdSyllabusAndIdStateAndActiveFlag(
			Long idProductGroup, Long idClassStandard, 
			Long idSubject, Long idSyllabus, Long idState, Boolean activeFlag);
	
	@Query("select DISTINCT(p.idSubject) from Product p " + "where (p.idProductLine = :idProductLine)"
			+ " and (:idSyllabus is null or p.idSyllabus = :idSyllabus)"
			+ " and (:idState is null or p.idState = :idState)"
			+ " and (:idClassStandard is null or p.idClassStandard = :idClassStandard) and "
			+ "p.activeFlag=:activeFlag")

	public List<Long> getProductBasedOnProductlineAndIdSyllabusAndIdStateAndIdClassStandard(Long idProductLine,
			Long idSyllabus, Long idState, Long idClassStandard, Boolean activeFlag);
	
	@Query("select DISTINCT (idClassStandard) from Product where idProductLine = :idProductLine and idSyllabus = :idSyllabus and idState = :idState and activeFlag = :activeFlag")

	public List<Long> getDistinctClassStandardsBasedOnProductLineIdSyllabusAndIdState(Long idProductLine,
			Long idSyllabus, Long idState, boolean activeFlag);
	
	@Query("select DISTINCT (idSyllabus) from Product  where idProductLine = :idProductLine and activeFlag =:activeFlag")

	public List<Long> getDistinctSyllabusBasedOnProductLine(Long idProductLine,boolean activeFlag);
	
	@Query("select DISTINCT (idState) from Product  where idProductLine = :idProductLine and idSyllabus = :idSyllabus and activeFlag = :activeFlag")
	public List<Long> getDistinctStateBasedOnProductLineIdSyllabus(Long idProductLine,
			Long idSyllabus,boolean activeFlag);
	
	@Query(value="select distinct   p.idproduct , ss.idstudent_subscr , p.idsubject from (select * from product where idproduct_line=:idProductLine and "
			+ "idclass_standard=:idClassStd and idsyllabus=:idSyllabus and idstate=:idState and active_flag=:activeFlag) p "
			+ "left outer join student_subscription ss on p.idproduct=ss.idproduct "
			+ "and ss.idstudent=:idstudent order by ss.idstudent_subscr asc", nativeQuery=true)
	public List<Object[]>  getSubscribedProducts(Long idProductLine,Long idClassStd, Long idSyllabus, Long idState,Long idstudent,boolean activeFlag);


	@Query(value="select distinct   p.idproduct , ss.idstudent_subscr , p.idsubject from (select * from product where idproduct_line=:idProductLine and active_flag=:activeFlag) p "
			+ "left outer join student_subscription ss on p.idproduct=ss.idproduct "
			+ "and ss.idstudent=:idstudent", nativeQuery=true)
	public List<Object[]>  getSubscribedExtraCurrProducts(Long idProductLine,Long idstudent,boolean activeFlag);

	public List<Product> findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndActiveFlag(Long idState,
			Long idSyllabus, Long idClassStandard, long l, String categoryCode,boolean activeFlag);

	public Product findByIdStateAndIdSyllabusAndIdClassStandardAndIdProductLineAndExtraCurrCategoryAndIdSubjectAndActiveFlag(
			Long idState, Long idSyllbus, Long idClassStandard, long l, String category, Long idSubject,boolean activeFlag);
	
	
	@Query(value="select distinct p.idproduct , p.idproduct_group , p.extra_curr_category, q.quiz_name, ss.idstudent_subscr , case when ssq.idstudent_subject_quiz is not null then true else false end"
			+ " FROM product p  left outer join student_subscription ss on p.idproduct=ss.idproduct and ss.idVL_USER=:idVluser"
			+ " inner join quiz q  on p.idproduct= q.idproduct  and q.idsubject=:idSubject "
			+ " left outer join student_subject_quiz ssq on q.idquiz = ssq.idquiz and ssq.idstudent_subscr=ss.idstudent_subscr "
			+ " where p.idclass_standard =:idClassStandard and p.idstate=:idState and p.idsyllabus=:idSyllabus and p.idproduct_line=12 and p.idsubject=:idSubject and p.active_flag=:activeFlag", nativeQuery=true)
	public List<Object[]>  getSubscribedQuizDifficulty(Long idSubject,Long idClassStandard, Long idSyllabus, Long idState, Long idVluser,boolean activeFlag);

	public Product findByIdProductAndIdProductLineAndActiveFlag(Long idProduct, long l,boolean activeFlag);
	
	
	@Query("select p.idProduct from Product p where p.idProductLine=:idProductLine and p.idClassStandard=:idClassStandard and p.idSyllabus=:idSyllabus and p.idState=:idState and p.activeFlag=:activeFlag")
	public List<Long> getIdByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(Long idProductLine,
			Long idClassStandard, Long idSyllabus, Long idState,boolean activeFlag);

	public List<Product> findByIdSubjectAndActiveFlag(Long idSubject,boolean activeFlag);
	
	public Product getByIdProductGroup(Long idProductGroup);

	public List<Product> findByIdProductGroup(Long idProductGroup);

	public Product findByIdProduct(Long idProduct);

	public List<Product> findByIdProductLineAndIdClassStandardAndIdSyllabusAndIdState(Long idProductLine,
			Long idClassStandard, Long idSyllabus, Long idState);


	@Query("select new co.vistafoundation.vlearning.subscription.dto.NewSubscriptionPlanDTO"
			+ "(p.idProduct,p.idProductGroup,p.idProductLine,p.ageGroup,p.productName, p.productCd, p.extraCurrCategory, p.batchSize, "
			+ " pp.idProductPricing,pp.promoText,pp.planDescription, "
			+ " pa.idProductAmount,pa.amount,pa.oldAmount,pa.amountCode,pa.amountName, pa.iosEnabled,"
			+ " pd.idProductDuration,pd.duration,pd.durationCode,pd.durationName) FROM Product as p"
			+ " join ProductPricing as pp on p.idProduct=pp.idProduct join ProductAmount as pa"
			+ " on pp.idProductAmount=pa.idProductAmount join ProductDuration as pd"
			+ " on pp.idProductDuration=pd.idProductDuration where p.idProduct=:idProduct and pp.activeFlag=TRUE")
	public List<NewSubscriptionPlanDTO> getSubscriptionPlan(Long idProduct);
	@Query("select new co.vistafoundation.vlearning.subscription.dto.SubscriptionPlanDTO"
			+ "(p.idProduct,p.idProductGroup,p.idProductLine,p.productName, p.productCd, "
			+ " pp.idProductPricing, "
			+ " pa.idProductAmount,pa.amount,pa.oldAmount,pa.amountName,"
			+ " pd.idProductDuration,pd.duration,pd.durationName) FROM Product as p"
			+ " join ProductPricing as pp on p.idProduct=pp.idProduct join ProductAmount as pa"
			+ " on pp.idProductAmount=pa.idProductAmount join ProductDuration as pd"
			+ " on pp.idProductDuration=pd.idProductDuration where p.idProduct=:idProduct and pp.activeFlag=TRUE")
	public List<SubscriptionPlanDTO> getNewSubscriptionPlan(Long idProduct);

	public Product getByIdProductLineAndActiveFlag(Long idProduct, Boolean true1);

	/**
	 * @param l
	 * @param idClassStandard
	 * @param idSyllabus
	 * @param idState
	 * @param true1
	 * @return
	 */
	public Product getByIdProductLineAndIdClassStandardAndIdSyllabusAndIdStateAndActiveFlag(long l,
			Long idClassStandard, Long idSyllabus, Long idState, Boolean true1);

	
	@Query(value = "select distinct   p.idproduct , ss.idstudent_subscr , p.idsubject from (select * from product where idproduct_line=:idProductLine and "
			+ "idclass_standard=:idClassStd and idsyllabus=:idSyllabus and idstate=:idState and active_flag=:activeFlag) p "
			+ "left outer join student_subscription ss on p.idproduct=ss.idproduct "
			+ "and ss.idstudent=:idstudent order by ss.idstudent_subscr asc", nativeQuery = true)
	public List<Object[]> getSubscribedExtraCurrProducts1(Long idProductLine, Long idClassStd, Long idSyllabus, Long idState,
			Long idstudent, boolean activeFlag);
	
	


	@Query("select new co.vistafoundation.vlearning.product.dto.NewSubscriptionPlanV2DTO"
			+ "(p.idProduct,p.idProductGroup,p.idProductLine,p.ageGroup,p.productName, p.productCd, p.extraCurrCategory, p.batchSize, "
			+ " pp.idProductPricing,pp.promoText,pp.planDescription, "
			+ " pa.idProductAmount,pa.amount,pa.oldAmount,pa.amountCode,pa.amountName, pa.iosEnabled,"
			+ " pd.idProductDuration,pd.duration,pd.durationCode,pd.durationName, pp.isVisible, pp.activeFlag ) FROM Product as p"
			+ " join ProductPricing as pp on p.idProduct=pp.idProduct join ProductAmount as pa"
			+ " on pp.idProductAmount=pa.idProductAmount join ProductDuration as pd"
			+ " on pp.idProductDuration=pd.idProductDuration where p.idProduct=:idProduct ")
	public List<NewSubscriptionPlanV2DTO> getSubscriptionPlanV2(Long idProduct);

}
