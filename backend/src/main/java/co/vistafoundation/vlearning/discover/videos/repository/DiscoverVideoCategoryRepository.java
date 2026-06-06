package co.vistafoundation.vlearning.discover.videos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.discover.videos.models.DiscoverVideoCategory;

public interface DiscoverVideoCategoryRepository extends JpaRepository<DiscoverVideoCategory, Long>{
	
	DiscoverVideoCategory findByIdDiscoverVideoCategory(Long idDiscoverVideoCategory);
	
}
