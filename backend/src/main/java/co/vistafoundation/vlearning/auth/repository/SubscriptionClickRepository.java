package co.vistafoundation.vlearning.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import co.vistafoundation.vlearning.auth.model.SubscriptionClick;


public interface SubscriptionClickRepository extends JpaRepository<SubscriptionClick, Long> {
	
	
	public boolean existsByMobileNumberAndStatus(String mobileNumber,String status);
	
	public Boolean existsByVlUserIdAndStatusIn(Long idVlUser,List<String> status);
	
	public SubscriptionClick findByVlUserIdAndStatus(Long idVlUser,String status);
}
