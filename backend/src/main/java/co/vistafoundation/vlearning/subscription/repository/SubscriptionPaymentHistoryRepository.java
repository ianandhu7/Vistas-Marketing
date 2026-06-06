/**
 * 
 */
package co.vistafoundation.vlearning.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.subscription.model.SubscriptionPaymentHistory;

/**
 * @author vk
 *
 */
public interface SubscriptionPaymentHistoryRepository extends JpaRepository<SubscriptionPaymentHistory, Long> {

	SubscriptionPaymentHistory findByIdStudentOrder(Long idStudentOrder);

}
