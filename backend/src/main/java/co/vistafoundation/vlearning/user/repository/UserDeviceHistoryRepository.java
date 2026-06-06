package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.vistafoundation.vlearning.user.model.UserDeviceHistory;

@Repository
public interface UserDeviceHistoryRepository extends JpaRepository<UserDeviceHistory, Long> {

	
	public UserDeviceHistory findByIdVLUserAndDeviceTypeAndUserAgent(Long IdVLUser,String deviceType,String  userAgent);
	
	public List<UserDeviceHistory> findTop10ByIdVLUserOrderByCreatedAtDesc(Long IdVLUser);
}
