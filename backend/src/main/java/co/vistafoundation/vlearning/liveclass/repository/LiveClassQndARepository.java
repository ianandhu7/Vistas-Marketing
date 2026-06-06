/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.liveclass.model.LiveClassQndA;

/**
 * @author vk
 *
 */
public interface LiveClassQndARepository extends JpaRepository<LiveClassQndA, Long>{

	public List<LiveClassQndA> findByIdLiveClass(Long idLiveClass);
}
