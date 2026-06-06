/**
 * 
 */
package co.vistafoundation.vlearning.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.MobileOtp;

/**
 * @author vk
 *
 */
public interface MobileOtpRepository extends JpaRepository<MobileOtp, Long>{

	public MobileOtp findByMobileNumber(String mobileNumber);
	
	public boolean existsByMobileNumberAndOtp(String mobileNumber, String otp);

	public MobileOtp findByMobileNumberAndOtp(String mobile, String otp);
	
}
