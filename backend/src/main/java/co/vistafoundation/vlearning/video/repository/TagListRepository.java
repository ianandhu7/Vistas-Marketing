package co.vistafoundation.vlearning.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.video.model.TagList;
/**
 * 
 * @author NaveenKumar 
 *
 */
public interface TagListRepository extends JpaRepository<TagList, Long> {
	
	public TagList findByTagName(String tagName);

}
