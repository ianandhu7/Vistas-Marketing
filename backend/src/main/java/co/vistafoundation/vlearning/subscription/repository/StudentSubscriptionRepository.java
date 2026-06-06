/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.subscription.dto.KidsSubscriptionDTO;
import co.vistafoundation.vlearning.subscription.dto.ProductDetailDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionBatchDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;

/**
 * @author vk
 *
 */
public interface StudentSubscriptionRepository extends JpaRepository<StudentSubscription, Long> {

	@Query(value = "select new "
			+ "co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionBatchDTO(s.idStudentSubscription,b.idBatch,b.batchName,s.nextPaymentDate,s.idSpecialOffer, s.idStudentOrder) "
			+ "from Batch b inner join StudentSubscription s on b.idBatch = s.idBatch inner join ProductLine pl "
			+ "on pl.idProductLine = s.idproductLine where pl.productCategory = :category "
			+ "and s.idStudent=:idstudent and s.activeFlag =:activeFlag")
	public List<StudentSubscriptionBatchDTO> getAllBatches(@Param("idstudent") Long idstudent,
			@Param("category") String category, @Param("activeFlag") Boolean activeFlag);

	@Query(value = "select new co.vistafoundation.vlearning.subscription.dto.KidsSubscriptionDTO(s.idStudentSubscription,s.idBatch,s.idProductGroup,s.idProduct,s.nextPaymentDate,"
			+ "s.lastPaymentDate,s.subscriptionEndDate,s.purchaseAmount) from StudentSubscription s JOIN ProductLine pl ON (s.idproductLine = pl.idProductLine) where pl.productCategory=:productCategory and s.idStudent=:idstudent and s.activeFlag=:activeFlag")
	public List<KidsSubscriptionDTO> getAllBatchesDetails(@Param("idstudent") Long idstudent,
			@Param("productCategory") String productCategory, @Param("activeFlag") Boolean activeFlag);

	@Query(value = "select s from StudentSubscription s where s.idStudent=:idstudent and s.idproductLine=:idproductLine and s.activeFlag=:activeFlag")
	public List<StudentSubscription> findByproductDetails(@Param("idstudent") Long idstudent,
			@Param("idproductLine") Long idproductLine, @Param("activeFlag") Boolean activeFlag);

	public List<StudentSubscription> findByIdStudent(Long idStudent);

	public List<StudentSubscription> findByIdStudentAndIdproductLine(Long idStudent, Long idProductLine);

	public StudentSubscription findByIdStudentSubscription(Long idStudentSubscription);

	public List<StudentSubscription> findByIdBatch(Long idBatch);

	public StudentSubscription findByIdStudentAndIdBatch(Long idStudent, Long idBatch);

	public List<StudentSubscription> findAllByActiveFlag(Boolean booleanFalg);

	public List<StudentSubscription> findByIdStudentOrder(Long idStudentOrder);

	public StudentSubscription findByIdBatchAndActiveFlagAndIdStudent(Long idBatch, boolean activeFlag, Long idStudent);

	public List<StudentSubscription> findByIdStudentAndActiveFlag(Long idStudent, Boolean activeFlag);

	public List<StudentSubscription> findByIdStudentAndIdproductLineAndActiveFlag(Long idStudent, Long idProductLine,
			boolean activeFlag);

	public StudentSubscription findByIdStudentAndIdProductAndActiveFlag(Long idStudent, Long idProduct,
			Boolean activeFlag);

	@Query("SELECT count(ss) FROM StudentSubscription  ss where activeFlag = true and idBatch in (select b.idBatch from Batch b where b.idTeacher = :idTeacher)")
	int getJoinedStudentCount(@Param("idTeacher") Long idTeacher);

	public List<StudentSubscription> findByIdBatchAndActiveFlag(Long idBatch, Boolean flag);

	public List<StudentSubscription> findAllByActiveFlagAndFreeFlagAndSubscriptionEndDateIsNotNull(boolean activeFlag,
			boolean freeFlag);

	public List<StudentSubscription> findAllByActiveFlagAndFreeFlag(boolean activeFlag, boolean freeFlag);

	public StudentSubscription findByIdStudentAndIdBatchAndActiveFlagAndFreeFlag(Long idStudent, Long idBatch,
			Boolean activeFlag, Boolean freeFlag);

	//
	// @Query("SELECT count(ss) FROM StudentSubscription ss where activeFlag = :true
	// and idBatch in (select b.idBatch from Batch b where b.idTeacher =
	// :idTeacher)")
	/**
	 * @Query("SELECT count(ss) FROM StudentSubscription ss inner join Batch b on
	 * br.idBatch = b.idBatch inner join StudentSubscription ss on ss.idBatch =
	 * b.idBatch inner join ProductLine pl on ss.idproductLine = pl.idProductLine
	 * where pl.productCategory = 'Batch' and ss.idStudent = :idStudent and
	 * br.batchRundate = :batchRundate") List<BatchRunDetail>
	 * getBatchrunDetails(@Param("idStudent") Long idStudent, @Param("batchRundate")
	 * String batchRundate);
	 **/

	public StudentSubscription findByUserSurId(Long userSurId);

	public StudentSubscription findByUserSurIdAndIdproductLineAndActiveFlag(Long userSurId, Long idproductLine,
			Boolean activeFlag);

	public StudentSubscription findByUserSurIdAndIdproductLine(Long userSurId, Long idproductLine);

	public List<StudentSubscription> findByUserSurIdAndIdSpecialOfferAndSpecialOfferFlagAndActiveFlag(Long userSurId,
			Long idSpecialOffer, boolean offerFlag, boolean activeFlag);

	public StudentSubscription findByUserSurIdAndIdProductAndActiveFlag(Long userSurId, Long idProduct,
			Boolean activeFlag);


	public StudentSubscription findByIdBatchAndIdProductAndIdStudentOrderAndUserSurId(Long idBatch, Long idProduct,
			Long idStudentOrder, Long userSurId);

	public List<StudentSubscription> findByIdStudentOrderAndIdSpecialOfferAndUserSurIdAndActiveFlag(Long idStudentOrder,
			Long idSpecialOffer, Long userSurId, Boolean activeFlag);

	@Query(value = "select\n" + "b.batch_name,\n" + "ss1.idstudent_subscr,\n" + "ss1.idVL_USER,\n"
			+ "ss1.next_pmt_dt        ,\n" + "ss1.subscr_end_dt      ,\n" + "vlu.email              ,\n"
			+ "vlu.mobile_number ,\n" + "vlu.first_name,\n" + "ud.device_id\n"
			+ "FROM (select b.idbatch_group         idbatch_group,\n" + "min(b.idbatch) idbatch\n" + "from\n"
			+ "batch b\n" + "JOIN student_subscription ss\n" + "ON\n" + "ss.idbatch = b.idbatch\n" + "\n" + "WHERE\n"
			+ "ss.special_offer_flag  is true\n" + "group by\n" + "b.idbatch_group\n" + ")group_subscr\n" + "JOIN\n"
			+ "student_subscription ss1\n" + "ON\n" + "ss1.idbatch = group_subscr.idbatch\n" + "JOIN\n"
			+ "vl_user vlu\n" + "ON\n" + "ss1.idVL_USER   = vlu.user_sur_id\n" + "\n"
			+ "Join batch b on group_subscr.idbatch = b.idbatch\n" + "\n" + "left outer join user_device ud on \n"
			+ "ss1.idVL_USER = ud.idvluser\n" + "\n" + "WHERE\n" + "ss1.active_flag is true AND\n"
			+ "ss1.free_flag is false AND\n" + "ss1.next_pmt_dt <= curdate() + INTERVAL 3 DAY\n" + "union\n"
			+ "select\n" + "b.batch_name,\n" + "ss.idstudent_subscr,\n" + "ss.idVL_USER,\n"
			+ "ss.next_pmt_dt        ,\n" + "ss.subscr_end_dt      ,\n" + "vlu.email              ,\n"
			+ "vlu.mobile_number    ,\n" + "vlu.first_name,\n" + "ud.device_id \n" + "from\n" + "batch b\n" + "JOIN\n"
			+ "student_subscription ss\n" + "ON\n" + "ss.idbatch = b.idbatch \n" + "JOIN\n" + "vl_user vlu\n" + "ON\n"
			+ "ss.idVL_USER           = vlu.user_sur_id\n" + "\n" + "left outer join user_device ud on \n"
			+ "ss.idVL_USER = ud.idvluser\n" + "\n" + "WHERE\n" + "ss.special_offer_flag  is false \n" + "AND\n"
			+ "ss.active_flag is true\n" + "AND\n"
			+ "ss.next_pmt_dt <= curdate() + INTERVAL 3 DAY;", nativeQuery = true)
	public List<Object[]> findSubscriptionByNextPaymentDate();

	public StudentSubscription findByIdStudentAndIdProduct(Long idStudent, Long idProduct);

	public StudentSubscription findFirstByIdStudentAndIdProduct(Long idStudent, Long idProduct);
	
	public StudentSubscription findFirstByIdStudentAndIdProductAndIdBatch(Long idStudent, Long idProduct, Long idBatch);
	
	@Query(value="SELECT count(*) FROM StudentSubscription where activeFlag = true and idProduct=93")
	public Long findTotalCountOfActive999Subscriptions();
	
	@Query(value="SELECT count(*) FROM StudentSubscription where subscriptionEndDate < :now and idProduct=93")
	public Long findTotalCountOfInactive999Subscriptions(Instant now);

	public StudentSubscription findByIdStudentSubscriptionAndUserSurId(Long idStudentSubscription, Long userSurId);
	
	public StudentSubscription findByIdStudentSubscriptionAndIdProductAndUserSurId(Long idStudentSubscription,Long idProduct,Long userSurId);

	public StudentSubscription findByIdProductAndUserSurId(Long idStudentSubscription,Long userSurId );

	public StudentSubscription findByIdProductAndUserSurIdOrIdStudent(Long idProduct, Long userSurId, Long idStudent);
	
	
	@Query("select ss.idStudentSubscription from StudentSubscription ss where ss.idProduct in (:idProducts) and ss.idStudent=:idStudent")
	public List<Long> getIdsByIdProductsAndStudentId(List<Long> idProducts,Long idStudent);

	public StudentSubscription findFirstByIdStudentAndIdProductAndIdBatchAndActiveFlag(Long idStudent, Long idProduct,
			Long idBatch, Boolean true1);

	public StudentSubscription findFirstByIdStudentAndIdProductAndActiveFlag(Long idStudent, Long idProduct,
			Boolean true1);

	public List<StudentSubscription> getByUserSurId(long l);

	public StudentSubscription findByUserSurIdAndActiveFlag(Long userSurId, boolean b);

	public StudentSubscription findByIdProductAndUserSurIdAndActiveFlag(Long idProduct,Long userSurId, Boolean activeFlag);

	@Query(value = "select count(*) from student_subscription where idproduct=93 and active_flag=true", nativeQuery = true)
	public Long getTotalSubscribersCount();

	@Query(value = "SELECT count(distinct idstudent) FROM student_subscription where idproduct=93 and active_flag=false and idstudent not in (select idstudent from student_subscription where idproduct=93 and active_flag=true) ", nativeQuery = true)
	public Long getExpiredSubscribersCount();
	
	Page<StudentSubscription> findAllByOrderByIdStudentSubscriptionAsc(Pageable paging);

	Page<StudentSubscription> findAllByOrderByIdStudentSubscriptionDesc(Pageable paging);
	
	@Query(value="select distinct ss from StudentSubscription ss "
			+ "inner join User u on ss.userSurId = u.userSurId "
		    + "inner join StudentOrder so on ss.idStudentOrder = so.idStudentOrder " // Join with StudentOrder entity
			+ "where (:userSurId is null or ss.userSurId = :userSurId ) and ss.idproductLine=11L and "
			+ "(:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) and "
			+ "(coalesce(:status) is null or ss.activeFlag in (:status) ) and "
			+ "(:email is null or u.email LIKE :email%) and "
			+ "(coalesce(:subscriptionMode) is null or so.remarks LIKE %:subscriptionMode%) and "
			+ "(:from is null or DATE(ss.purchaseDate) >=:from) and "
			+ "(:to is null or DATE(ss.purchaseDate) <=:to) "
			+ "order by ss.idStudentSubscription Asc")
	Page<StudentSubscription> getAllStudentSubscriptionInfoBasedOnParamAsc(Long userSurId, String mobileNumber, String email,
			List<Boolean> status, String subscriptionMode,Date from, Date to,Pageable  paging);
	
	
	@Query(value="select distinct ss from StudentSubscription ss "
			+ "inner join User u on ss.userSurId = u.userSurId "
		    + "inner join StudentOrder so on ss.idStudentOrder = so.idStudentOrder " // Join with StudentOrder entity
			+ "where (:userSurId is null or ss.userSurId = :userSurId ) and ss.idproductLine=11L and "
			+ "(:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) and "
			+ "(coalesce(:status) is null or ss.activeFlag in (:status) ) and "
			+ "(:email is null or u.email LIKE :email%) and "
			+ "(:from is null or DATE(ss.purchaseDate) >=:from) and "
			+ "(:to is null or DATE(ss.purchaseDate) <=:to) "
			+ "order by ss.idStudentSubscription Asc")
	Page<StudentSubscription> getAllStudentSubscriptionInfoBasedOnSubscriptionModeNull(Long userSurId, String mobileNumber, String email,
			List<Boolean> status, Date from, Date to,Pageable  paging);
	
	
	
	@Query(value="select distinct ss from StudentSubscription ss "
			+ "inner join User u on ss.userSurId = u.userSurId "
		    + "inner join StudentOrder so on ss.idStudentOrder = so.idStudentOrder " // Join with StudentOrder entity
			+ "where (:userSurId is null or ss.userSurId = :userSurId ) and ss.idproductLine=11L and "
			+ "(:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) and "
			+ "(coalesce(:status) is null or ss.activeFlag in (:status) ) and "
			+ "(:email is null or u.email LIKE :email%) and "
			+ "(coalesce(:subscriptionMode) is null or so.remarks LIKE %:subscriptionMode%) and "
			+ "(:from is null or DATE(ss.purchaseDate) >=:from) and "
			+ "(:to is null or DATE(ss.purchaseDate) <=:to) "
			+ "order by ss.idStudentSubscription Desc")
	Page<StudentSubscription> getAllStudentSubscriptionInfoBasedOnParamDesc(Long userSurId, String mobileNumber, String email,
			List<Boolean> status,String subscriptionMode,Date from, Date to,Pageable  paging);
	
	@Query(value="select ss from StudentSubscription ss inner join User u on ss.userSurId = u.userSurId "
			+ "where (:userSurId is null or ss.userSurId = :userSurId ) and "
			+ "(:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) and "
			+ "(coalesce(:status) is null or ss.activeFlag in (:status) ) and "
			+ "(:email is null or u.email LIKE :email%) and "
			+ "(:from is null or DATE(ss.purchaseDate) >=:from) and "
			+ "(:to is null or DATE(ss.purchaseDate) <=:to) "
			+ "order by ss.idStudentSubscription Asc")
	List<StudentSubscription> getAllStudentSubscriptionInfoBasedOnParamAscAsList(Long userSurId, String mobileNumber, String email,
			List<Boolean> status,Date from, Date to);
	
	@Query(value="select ss from StudentSubscription ss inner join User u on ss.userSurId = u.userSurId "
			+ "where (:userSurId is null or ss.userSurId = :userSurId ) and "
			+ "(:mobileNumber is null or u.mobileNumber LIKE :mobileNumber%) and "
			+ "(coalesce(:status) is null or ss.activeFlag in (:status) ) and "
			+ "(:email is null or u.email LIKE :email%) and "
			+ "(:from is null or DATE(ss.purchaseDate) >=:from) and "
			+ "(:to is null or DATE(ss.purchaseDate) <=:to) "
			+ "order by ss.idStudentSubscription Desc")
	List<StudentSubscription> getAllStudentSubscriptionInfoBasedOnParamDescAsList(Long userSurId, String mobileNumber, String email,
			List<Boolean> status,Date from, Date to);
	
	   @Query("SELECT u FROM User u " +
	           "WHERE u.userSurId NOT IN (SELECT ss.userSurId FROM StudentSubscription ss WHERE idProduct = 93L) " +
	           "AND (:userSurId is null OR u.userSurId = :userSurId) " +
	           "AND (:mobileNumber is null OR u.mobileNumber LIKE :mobileNumber%) " +
	           "AND (:email is null OR u.email LIKE :email%) " +
	           "AND (:from is null OR DATE(u.createdAt) >= :from) " +
	           "AND (:to is null OR DATE(u.createdAt) <= :to) " +
	           "ORDER BY u.userSurId ASC")
	Page<User> getAllBasicUserAsAcs(Long userSurId, String mobileNumber, String email,Date from, Date to,Pageable  paging);


    @Query("SELECT u FROM User u " +
           "WHERE u.userSurId NOT IN (SELECT ss.userSurId FROM StudentSubscription ss WHERE idProduct = 93L) " +
           "AND (:userSurId is null OR u.userSurId = :userSurId) " +
           "AND (:mobileNumber is null OR u.mobileNumber LIKE :mobileNumber%) " +
           "AND (:email is null OR u.email LIKE :email%) " +
           "AND (:from is null OR DATE(u.createdAt) >= :from) " +
           "AND (:to is null OR DATE(u.createdAt) <= :to) " +
           "ORDER BY u.userSurId DESC")
	Page<User> getAllBasicUserAsDesc(Long userSurId, String mobileNumber, String email,Date from, Date to,Pageable  paging);
	public StudentSubscription findFirstByIdProductAndUserSurIdAndActiveFlag(Long idStudentSubscription,Long userSurId,boolean activeFlag);
	

	public StudentSubscription findFirstByUserSurIdAndIdproductLineAndActiveFlag(Long userSurId, Long idProductLine, Boolean activeFlag); 
	
	public StudentSubscription findByUserSurIdAndActiveFlagAndPurchaseLevel(Long userSurId, Boolean activeFlag, String purchaseLevel);
	
	@Query("select distinct p.idSubject from StudentSubscription ss  inner join Product p on  ss.idProduct= p.idProduct and p.activeFlag=:activeFlag  where ss.idproductLine= :idProductLine and ss.userSurId= :userSurId")
	public List<Long> getListofIdSubjectBasedOnSubscription(Long userSurId,Long idProductLine,boolean activeFlag);

	public StudentSubscription findFirstByIdStudentOrderAndActiveFlag(Long idStudentOrder, Boolean true1);

	public StudentSubscription findFirstByIdproductLineAndUserSurIdAndActiveFlag(Long idProductLine, Long userSurId, Boolean activeFlag);

	public StudentSubscription findFirstByIdStudentAndIdproductLineAndActiveFlag(Long idStudent, Long idProductLine,
			Boolean activeFlag);

	public StudentSubscription findByIdStudentSubscriptionAndIdStudent(Long idStudentSubscription, Long idStudent);

	public Boolean existsByIdproductLineAndUserSurIdAndActiveFlag(long l, Long userSurId, boolean b);
	
	public StudentSubscription findFirstByIdProductInAndUserSurIdAndActiveFlag(List<Long> idProductList,Long userSurId,boolean activeFlag);

	public Boolean existsByIdproductLineAndUserSurId(long l, Long userSurId);
	
	
	@Query("SELECT new co.vistafoundation.vlearning.subscription.dto.ProductDetailDTO(p.idProduct,p.productCd ,pa.amount) "
			+ " FROM Product p " + "JOIN ProductPricing pp ON pp.idProduct = p.idProduct "
			+ " JOIN ProductAmount pa ON pa.idProductAmount = pp.idProductAmount" + " WHERE pp.activeFlag = true")
	List<ProductDetailDTO> findActiveProducts();

}
