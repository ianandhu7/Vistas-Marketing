/**
 * 
 */
package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.user.model.UserCart;

/**
 * @author vk
 *
 */
public interface UserCartRepository extends JpaRepository<UserCart, Long>{
	
	public UserCart findByIdUserCart(Long idUserCart);

	public List<UserCart> findByUserSurId(Long userSurId);
	
	public List<UserCart> findByIdStudentOrder(Long idStudentOrder);
	
	public List<UserCart> findByIdUserCartIn(List<Long> idUserCart);
	
	public List<UserCart> findByIdBatchIn(List<Long> idBatch);

	public List<UserCart> findDistinctByIdBatchIn(List<Long> batchIds);
	  
//	@Query(value = "Select idbatch,count(idbatch) from user_cart group by idbatch having count(idbatch)>1", nativeQuery = true)
//	  List<UserCart> findByUniqueIdBatch();
	
	/**
	 * 
	 * 
	 * @author Sajini S
	 */
	
	@Query(value="with c as(SELECT * , row_number() over(partition by idbatch order by idbatch) s FROM vlearning_dev.user_cart)select *from c where c.s=1",nativeQuery=true)
	 List<UserCart> findByUniqueIdBatch();

	public UserCart findByIdUserCartAndUserSurId(Long idUserCart, Long userSurId);

	public List<UserCart> findByIdUserCartInAndUserSurId(List<Long> idUserCarts, Long userSurId);

}
