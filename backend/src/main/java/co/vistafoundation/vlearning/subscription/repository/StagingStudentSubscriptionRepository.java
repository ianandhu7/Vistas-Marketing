/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionInfoDTO;
import co.vistafoundation.vlearning.subscription.model.StagingStudentSubscription;

/**
 * @author vk
 *
 */
public interface StagingStudentSubscriptionRepository extends JpaRepository<StagingStudentSubscription, Long> {

	StagingStudentSubscription findByIdStagingStudentSubscription(Long id);
	

	List<StagingStudentSubscription> findByOrderId(String orderId);

	List<StagingStudentSubscription> findByIdStudentOrder(Long idStudentOrder);

	/**
	 * @author NAVEEN KUMAR A
	 * @param orderId
	 * @return
	 */
	@Query("select new co.vistafoundation.vlearning.subscription.dto.StudentSubscriptionInfoDTO"
			+ "(ss.idStudentOrder,ss.purchaseDate," + "ss.nextPaymentDate,ss.lastPaymentDate,ss.subscriptionEndDate,"
			+ "ss.activeFlag,ss.purchaseAmount,ss.subscriptionType,ss.purchaseLevel,"
			+ "ss.purchaseType,ss.userSurId,ss.specialOfferFlag,ss.idSpecialOffer,"
			+ "p.monthlySubcrAmt,p.annualSubscrAmt,p.qtrSubscrAmt,p.ageGroup,"
			+ "p.productName,p.productCd,p.batchSize,p.extraCurrCategory,"
			+ "c.classStandadName,sy.syllabusName,st.state,su.subjectName,b.batchName,b.batchEndDate,"
			+ "b.batchDeactivateDate,b.specialOfferFlag,bg.batchGroupName,ss.bankName,ss.bankTransactionId,ss.orderId,"
			+ "ss.paymentMode,ss.paymentStatus,ss.transactionAmount,ss.transactionDate,ss.idStagingStudentSubscription,so.couponCode,so.actualAmount)"
			+ " from StagingStudentSubscription ss "
			+ " inner join StudentOrder so on ss.orderId = so.orderId"
			+ " inner join Product p on ss.idProduct = p.idProduct"
			+ " inner join ClassStandard c on p.idClassStandard = c.idClassStandard"
			+ " inner join Syllabus sy on p.idSyllabus = sy.idSyllabus"
			+ " inner join State st on p.idState = st.idState" + " inner join Subject su on p.idSubject = su.idSubject"
			+ " left outer join Batch b on ss.idBatch = b.idBatch"
			+ " left outer join BatchGroup bg on b.idBatchGroup=bg.idBatchGroup where ss.orderId=:orderId")
	List<StudentSubscriptionInfoDTO> getStudentSubscriptionForOrderId(String orderId);
	
	StagingStudentSubscription findByUserSurId(Long userSurId);


	StagingStudentSubscription getByOrderId(String orderId);


	StagingStudentSubscription findByUserSurIdAndActiveFlag(Long userSurId, boolean b);


	StagingStudentSubscription findByIdStudentOrderAndActiveFlag(Long idStudentOrder, boolean b);


	StagingStudentSubscription getByIdStudentOrder(Long idStudentOrder);
}
