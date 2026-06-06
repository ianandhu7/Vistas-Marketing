/**
 * 
 */
package co.vistafoundation.vlearning.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.auth.model.Merchant;

/**
 * @author vk
 *
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long>{
    
    public Merchant findByDefaultMerchant(Boolean defaultMerchant);
    
    public Merchant findByIdMerchant(Long idMerchant);
    
    public Merchant findByMerchantEmail(String merchantEmail);
    
    public Merchant findByMerchantId(String merchantId);

}
