package co.vistafoundation.vlearning.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.auth.model.User;

public interface UserSignUpAnalyticsRepo extends JpaRepository<User, Long> {

	@Query(value = "SELECT count(*) FROM vl_user where  registered_as='Student'and (created_at between ?1 and ?2)", nativeQuery = true)
	public Integer getUserSignUpCount(String from, String to);

}
