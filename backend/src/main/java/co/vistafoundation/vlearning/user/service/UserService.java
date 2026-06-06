/**
 * 
 */
package co.vistafoundation.vlearning.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.dto.LiveUserFilterDTO;
import co.vistafoundation.vlearning.user.dto.LiveUsersDataResponseDTO;
import co.vistafoundation.vlearning.user.dto.UserCreatedDTO;
import co.vistafoundation.vlearning.user.dto.UserDeviceDetailsResponseDTO;
import co.vistafoundation.vlearning.user.model.Student;
import co.vistafoundation.vlearning.user_activity.UserActivity;

/**
 * @author vk
 *
 */
public interface UserService {

	public UserCreatedDTO getUserCreatedWithinTwoDays(Long userSurId);

	public Document<User> updateUserActiveFlag(Long userSurId, Boolean activeFlag);

	public Document<Student> updateStudentEditFlag(Long userSurId);

	public Document<Object> getUserActiveStatus();

	public Document<Map<String, Long>> getUserAnalytics();

	public Document<Map<String, Object>> resetUserPassword(Long idVluser);

	public Document<Map<String, Object>> getUserLiveCountFromActiveStore();
	
	public Document<List<UserActivity>> getListOfUserActivity();

	/**
	 * @param idVlUser
	 * @return
	 */
	Document<?> deleteUser(Long idvlUser);

	/**
	 * @param type
	 * @param classStandardId
	 * @param idstate
	 * @param syllabusId
	 * @param page
	 * @param size
	 * @return
	 */
	Document<Page<LiveUsersDataResponseDTO>> getLiveUsersFromActiveStore(LiveUserFilterDTO filters);

	Document<List<UserDeviceDetailsResponseDTO>> liveUsersLocationDetails(String status);

	/**
	 * @param phoneNumber
	 * @param idClassStandard
	 * @return
	 */
//	public Document<?> updateClassStandard(String phoneNumber, Long idClassStandard);

	/**
	 * @param multipartFile
	 * @return
	 */
	Document<?> updateClassStandardBuk(MultipartFile multipartFile);

//	/**
//	 * @param batchFile
//	 * @return
//	 */
//	Document<?> deactivateIdsBulk(MultipartFile batchFile);


	Document<?> updateClassStandard(String phoneNumber, Long idClassStandard, Long idSyllabus, Long idStudentMedium,
			Long idLanguage, Long idState,  String activeStatus, String newPassword, Long userSurId);

	
	

}
