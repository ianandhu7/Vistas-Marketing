package co.vistafoundation.vlearning.specialoffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.specialoffer.model.Redemption;

/**
 * @author Mohan Kumar 
 * 
 **/
public interface RedemptionRepository extends JpaRepository<Redemption,Long>{

	//public Boolean existsByIdCoupon(Long idCoupon);
	
	//public Redemption findByIdCoupon(Long idCoupon);
	
	public Redemption findByCouponCodeAndIdVlUser(String couponCode,Long idVlUser);
	
	public Boolean existsByCouponCode(String couponCode);
	
}
