/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.liveclass.model.NotifyLiveClass;

/**
 * @author Ahmed
 *
 */
public interface NotifyLiveClassRepository extends JpaRepository<NotifyLiveClass, Long> {

	List<NotifyLiveClass> findByNotifiedFlag(Boolean flag);

	List<NotifyLiveClass> findByIdLiveClassInAndNotifiedFlag(List<Long> idList, Boolean false1);

	NotifyLiveClass findByIdLiveClassAndIdVlUser(Long idLiveClass, Long idVlUser);
	
	List<NotifyLiveClass> findByIdLiveClass(Long idLiveClass);

}
