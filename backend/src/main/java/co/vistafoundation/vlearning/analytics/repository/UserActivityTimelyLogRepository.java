package co.vistafoundation.vlearning.analytics.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.analytics.model.UserActivityTimelyLog;

public interface UserActivityTimelyLogRepository extends JpaRepository<UserActivityTimelyLog, Long> {

	List<UserActivityTimelyLog> findByCreatedAtBetween(Date startTimestamp, Date endTimestamp);

	List<UserActivityTimelyLog> findTop24ByOrderByCreatedAtDesc();

	List<UserActivityTimelyLog> findByCreatedAtBefore(Date cutoffDateAsDate);

	List<UserActivityTimelyLog> findByPartitionKeyBetween(String startDateTimeFormatted, String endDateTimeFormatted);

	List<UserActivityTimelyLog> findAllByCreatedAtBetweenOrderByCreatedAtDesc(Date from, Date to);

}
