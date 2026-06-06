package co.vistafoundation.vlearning.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subscription.model.StudentSubscription;

public interface UserSubscriptionAnalyticsRepo extends JpaRepository<StudentSubscription, Long> {

	@Query(value = "SELECT count(*) FROM student_subscription ss where ss.idproduct_line=11 and ss.created_at between ?1 and ?2", nativeQuery = true)
	public Integer getUserSubscriptionCount(String from, String to);

}
