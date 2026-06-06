package co.vistafoundation.vlearning.discover.videos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideo;

public interface VideoRepository extends PagingAndSortingRepository<DiscoverVideo, Long> {
	
	List<DiscoverVideo> findByTopicContainingIgnoreCase(String topic,Pageable pageable);

}
