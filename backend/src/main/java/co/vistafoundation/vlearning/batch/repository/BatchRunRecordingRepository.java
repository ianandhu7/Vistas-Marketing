package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchRunDetail;
import co.vistafoundation.vlearning.batch.model.BatchRunRecording;

/**
 * 
 * @author Shaikh Ahmed Reza
 *
 */


public interface BatchRunRecordingRepository extends JpaRepository<BatchRunRecording, Long> {
		
	List<BatchRunRecording> findByBatchRunDetail(BatchRunDetail batchRunDetail);

}
