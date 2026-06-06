/**
 * 
 */
package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.UserDevice;

/**
 * @author Sarfaraz
 *
 */
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

	public UserDevice findByUserSurIdAndDeviceType(Long userSurId, String deviceType);

	/**
	 * @param userSurId
	 * @return
	 */
	public List<UserDevice> findByUserSurId(Long userSurId);

	/**
	 * @param userSurId
	 * @param device
	 * @return
	 */
	public List<UserDevice> getByUserSurIdAndDeviceType(Long userSurId, String deviceType);

	public UserDevice findByIdUserDevice(Long id);

}
