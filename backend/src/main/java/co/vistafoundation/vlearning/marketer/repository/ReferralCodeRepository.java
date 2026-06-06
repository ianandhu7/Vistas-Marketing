/**
 * 
 */
package co.vistafoundation.vlearning.marketer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.marketer.model.ReferralCode;

/**
 * @author NAVEEN
 *
 */
public interface ReferralCodeRepository extends JpaRepository<ReferralCode, Long> {


	ReferralCode findByReferralCode(String referalCode);

	ReferralCode findByIdVendor(Long idVendor);


}
