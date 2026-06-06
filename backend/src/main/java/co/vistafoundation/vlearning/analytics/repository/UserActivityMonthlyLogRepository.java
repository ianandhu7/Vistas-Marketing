package co.vistafoundation.vlearning.analytics.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.analytics.model.UserActivityMonthlyLog;

public interface UserActivityMonthlyLogRepository extends JpaRepository<UserActivityMonthlyLog, Long> {

	List<UserActivityMonthlyLog> findAllByOrderByCreatedAtDesc();
	
	List<UserActivityMonthlyLog> findAllByCreatedAtBetweenOrderByCreatedAtDesc(Date from, Date to);

	List<UserActivityMonthlyLog> findAll();
}
