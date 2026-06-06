package co.vistafoundation.vlearning.analytics.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.analytics.model.UserActivityWeeklyLog;

public interface UserActivityWeeklyLogRepository extends JpaRepository<UserActivityWeeklyLog, Long> {

	List<UserActivityWeeklyLog> findByCreatedAtBetween(Date start, Date end);

	List<UserActivityWeeklyLog> findTop30ByOrderByCreatedAtDesc();

	List<UserActivityWeeklyLog> findByCreatedAtBefore(Date cutoffDateAsDate);

	List<UserActivityWeeklyLog> findAllByCreatedAtBetweenOrderByCreatedAtDesc(Date from, Date to);
}
