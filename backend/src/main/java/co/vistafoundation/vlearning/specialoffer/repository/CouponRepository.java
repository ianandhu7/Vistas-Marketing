/**
 * 
 */
package co.vistafoundation.vlearning.specialoffer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.specialoffer.model.Coupon;

/**
 * @author Mohan Kumar 
 * 
 **/
public interface CouponRepository extends JpaRepository<Coupon,Long>{

	public List<Coupon> findByBatchName(String batchName);

	public Coupon findByIdCoupon(Long idCoupon);
	
	@Query(" SELECT c FROM Coupon c WHERE c.batchName IS NOT NULL ")
	public List<Coupon> getBatchCoupons();
	
	public Coupon findByCouponCode(String couponCode);
}
