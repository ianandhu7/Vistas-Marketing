/**
 * 
 */
package co.vistafoundation.vlearning.notification.service;

import java.util.List;
import java.util.Map;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.notification.dto.GlobalNotifcationDTO;
import co.vistafoundation.vlearning.notification.model.UserNotification;

/**
 * @author NaveenKumar
 *
 */
public interface UserNotificationService {
	
	public Document<List<UserNotification>> getUserAllNotification();
	
	public Boolean createNotifcation(String Heading , String Message , Long idVlUser);
	
	public void updateNotifcation(Long idUserNotification,Long idVlUser);
	
	public void deleteNotifcation(Long idUserNotification,Long idVlUser);
	
	public Document<Map<String,Object>> sendGlobalNotification(GlobalNotifcationDTO request);
}
