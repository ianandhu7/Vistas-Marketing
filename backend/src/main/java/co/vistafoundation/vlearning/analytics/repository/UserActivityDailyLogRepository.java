package co.vistafoundation.vlearning.analytics.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.analytics.model.UserActivityDailyLog;

public interface UserActivityDailyLogRepository extends JpaRepository<UserActivityDailyLog, Long> {

	List<UserActivityDailyLog> findByCreatedAtBetween(Date start, Date end);

	List<UserActivityDailyLog> findTop30ByOrderByCreatedAtDesc();

	List<UserActivityDailyLog> findByCreatedAtBefore(Date cutoffDateAsDate);

	void deleteByCreatedAt(Date cutoffDateAsDate);

	List<UserActivityDailyLog> findAllByCreatedAtBetweenOrderByCreatedAtDesc(Date from, Date to);
 
}
