/**
 * 
 */
package co.vistafoundation.vlearning.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.notification.model.UserNotification;

/**
 * @author Naveen Kumar
 *
 */

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long>
{

	public List<UserNotification> findByIdVlUserOrderByCreatedAtDesc(Long idVlUser);

	public UserNotification findByidUserNotification(Long idUserNotification);

	public UserNotification findByidUserNotificationAndIdVlUser(Long idUserNotification, Long userSurId);

}
