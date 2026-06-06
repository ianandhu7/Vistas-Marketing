/**
 * 
 */
package co.vistafoundation.vlearning.liveclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.liveclass.model.LiveClassQuestion;

/**
 * @author vk
 *
 */
public interface LiveClassQuestionRepository extends JpaRepository<LiveClassQuestion, Long>{

	public List<LiveClassQuestion> findByIdLiveClass(Long idLiveClass);
}
