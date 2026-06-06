package co.vistafoundation.vlearning.specialoffer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.specialoffer.model.CouponUserMap;

/**
 * @author Mohan Kumar 
 * 
 **/
public interface CouponUserMapRepository  extends JpaRepository<CouponUserMap,Long>{
	
	public List<CouponUserMap> findByIdVlUser(Long idVlUser);

}
