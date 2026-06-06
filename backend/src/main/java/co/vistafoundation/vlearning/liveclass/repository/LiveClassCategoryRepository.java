/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.liveclass.model.LiveClassCategory;

/**
 * @author vk
 *
 */
public interface LiveClassCategoryRepository extends JpaRepository<LiveClassCategory, Long>{

	public LiveClassCategory findByIdLiveClassCategory(Long idLiveClassCategory);
}
