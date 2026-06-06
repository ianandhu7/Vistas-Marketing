/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.subscription.model.StagingUserSubscriptionPayment;

/**
 * @author vk
 *
 */
public interface StagingUserSubscriptionPaymentRepository extends JpaRepository<StagingUserSubscriptionPayment, Long>{

}
