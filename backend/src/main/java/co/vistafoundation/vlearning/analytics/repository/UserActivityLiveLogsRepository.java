package co.vistafoundation.vlearning.analytics.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.vistafoundation.vlearning.analytics.model.UserActivityLiveLogs;

@Repository
public interface UserActivityLiveLogsRepository extends JpaRepository<UserActivityLiveLogs, Long> {

    @Query("SELECT u FROM UserActivityLiveLogs u ORDER BY u.timestamp ASC")
    List<UserActivityLiveLogs> findOldestRecord();
    
    @Query("SELECT COUNT(u) FROM UserActivityLiveLogs u WHERE u.timestamp BETWEEN ?1 AND ?2 ORDER BY u.timestamp DESC")
    long countByTimestampBetween(Instant start, Instant end);

    boolean existsByUserSurIdAndTimestampBetween(Long userSurId, Instant start, Instant end);
}
