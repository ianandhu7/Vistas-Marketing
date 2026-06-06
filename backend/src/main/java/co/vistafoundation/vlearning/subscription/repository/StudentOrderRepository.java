/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import co.vistafoundation.vlearning.subscription.model.StudentOrder;

/**
 * @author vk
 *
 */
public interface StudentOrderRepository extends JpaRepository<StudentOrder, Long> {

	StudentOrder findByOrderId(String orderId);

	List<StudentOrder> findByUserSurId(Long userSurId);

	
	StudentOrder getByUserSurId(Long userSurId);

	Boolean existsByOrderId(String orderId);

	List<StudentOrder> findAll();

	StudentOrder findByOrderIdAndUserSurId(String orderId, Long userSurId);

	StudentOrder findByIdStudentOrder(Long idStudentOrder);

	List<StudentOrder> findByOrderStatus(String orderStatus);

	@Query("select sr from StudentOrder sr where (:orderId is null or sr.orderId = :orderId ) and (:userId is null or sr.userSurId = :userId) order by sr.orderDate Desc")
	List<StudentOrder> fetchAllStudentOrder(String orderId, Long userId);

	Page<StudentOrder> findAllByOrderByIdStudentOrderAsc(Pageable paging);

	Page<StudentOrder> findAllByOrderByIdStudentOrderDesc(Pageable paging);

	@Query(value="select sr from StudentOrder sr inner join User u on u.userSurId = sr.userSurId "
			+ "where (:orderId is null or sr.orderId = :orderId ) and "
			+ "(:mobileNumber is null or u.mobileNumber = :mobileNumber) and "
			+ "(coalesce(:status) is null or sr.orderStatus in (:status) ) and "
			+ "(:email is null or u.email = :email) and "
			+ "(:from is null or DATE(sr.orderDate) >=:from) and "
			+ "(:to is null or DATE(sr.orderDate) <=:to) "
			+ "order by sr.idStudentOrder Desc")
	Page<StudentOrder> getAllStudentOrderBasedOnParamDesc(String orderId, String mobileNumber, String email,
			List<String>status,Date from, Date to,Pageable  paging);

	StudentOrder getByUserSurIdAndDisputeFlag(Long userSurId, boolean b);

	@Query(value="select sr from StudentOrder sr inner join User u on u.userSurId = sr.userSurId "
			+ "where (:orderId is null or sr.orderId = :orderId ) and "
			+ "(:mobileNumber is null or u.mobileNumber = :mobileNumber) and "
			+ "(coalesce(:status) is null or sr.orderStatus in (:status) ) and "
			+ "(:email is null or u.email = :email) and "
			+ "(:from is null or DATE(sr.orderDate) >=:from) and "
			+ "(:to is null or DATE(sr.orderDate) <=:to) "
			+ "order by sr.idStudentOrder Asc")
	Page<StudentOrder> getAllStudentOrderBasedOnParamAsc(String orderId, String mobileNumber, String email,
			List<String>status,Date from, Date to,Pageable  paging);
	

	
	
	@Query(value="select sr from StudentOrder sr inner join User u on u.userSurId = sr.userSurId "
			+ "where (:orderId is null or sr.orderId = :orderId ) and "
			+ "(:mobileNumber is null or u.mobileNumber = :mobileNumber) and "
			+ "(coalesce(:status) is null or sr.orderStatus in (:status) ) and "
			+ "(:email is null or u.email = :email) and "
			+ "(:from is null or DATE(sr.orderDate) >=:from) and "
			+ "(:to is null or DATE(sr.orderDate) <=:to) "
			+ "order by sr.idStudentOrder Desc")
	List<StudentOrder> getAllStudentOrderBasedOnParamDescAsList(String orderId, String mobileNumber, String email,
			List<String>status,Date from, Date to);
	
	
	
	@Query(value="select sr from StudentOrder sr inner join User u on u.userSurId = sr.userSurId "
			+ "where (:orderId is null or sr.orderId = :orderId ) and "
			+ "(:mobileNumber is null or u.mobileNumber = :mobileNumber) and "
			+ "(coalesce(:status) is null or sr.orderStatus in (:status) ) and "
			+ "(:email is null or u.email = :email) and "
			+ "(:from is null or DATE(sr.orderDate) >=:from) and "
			+ "(:to is null or DATE(sr.orderDate) <=:to) "
			+ "order by sr.idStudentOrder Asc")
	List<StudentOrder> getAllStudentOrderBasedOnParamAscAsList(String orderId, String mobileNumber, String email,
			List<String>status,Date from, Date to);
	


	@Query("SELECT so FROM StudentOrder so WHERE so.orderStatus = 'Pending' AND so.createdAt < FUNCTION('SUBTIME', CURRENT_TIMESTAMP(), '00:15:00')")
	List<StudentOrder> getAllPendingStatusPayments();
	
	StudentOrder findByOrderIdAndOrderStatus(String orderId, String orderStatus);
	
}
