/**
 * 
 */
package co.vistafoundation.vlearning.offlinecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.offlinecourse.model.OfflineVideoCourse;
import co.vistafoundation.vlearning.offlinecourse.model.TopicLanguage;

/**
 * @author Naveen Kumar
 *
 */
public interface TopicLanguageRepository extends JpaRepository<TopicLanguage, Long> {

	public TopicLanguage findByIdLanguageAndOfflineVideoCourse(Long idLanguage, OfflineVideoCourse offlineVideoCourse);

	public List<TopicLanguage> findByOfflineVideoCourse_idOfflineVideoCourse(Long idOfflineVideoCourse);
	

}
