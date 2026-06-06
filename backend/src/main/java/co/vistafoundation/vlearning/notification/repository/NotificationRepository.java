package co.vistafoundation.vlearning.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.notification.model.Notification;
import co.vistafoundation.vlearning.user.model.Student;

public interface NotificationRepository  extends JpaRepository<Notification, Long>{
	
	 @Query("SELECT distinct s FROM StudentSubscription  su inner join Student s on su.idStudent = s.idStudent where su.idBatch  = :idBatch")
	 List<Student> getStudentsBasedonBatches(@Param("idBatch") Long idBatch);
	 
	 List<Notification>findByToUserId(Long toUserId);
	 
	 Notification findByIdNotification(Long idNotification);
	
	 @Query("SELECT count(n) FROM Notification n  where n.toUserId  = :idUser and n.readStatus = false")
	 public Long  getNotificationCount(@Param("idUser") Long idUser);
	 
	 List<Notification>findByToUserIdAndReadStatusOrderByNotificationDateDesc(Long toUserId,Boolean readStatus);
	 
	 List<Notification>findByFromUserId(Long fromUserId);

}
