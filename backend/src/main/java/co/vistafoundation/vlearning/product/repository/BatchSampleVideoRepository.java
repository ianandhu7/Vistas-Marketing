package co.vistafoundation.vlearning.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.product.model.BatchSampleVideo;

public interface BatchSampleVideoRepository extends JpaRepository<BatchSampleVideo, Long>{
	
	public List<BatchSampleVideo> findByIdBatch(Long idBatch);

}
