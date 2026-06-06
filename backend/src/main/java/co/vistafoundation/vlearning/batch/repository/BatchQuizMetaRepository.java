package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchQuizMeta;


public interface BatchQuizMetaRepository extends JpaRepository<BatchQuizMeta, Long> {

	BatchQuizMeta findByIdBatchQuizMeta(Long idBatchQuizMeta);
	
	List<BatchQuizMeta> findByIdTeacher (Long idTeacher);
	
	
}
