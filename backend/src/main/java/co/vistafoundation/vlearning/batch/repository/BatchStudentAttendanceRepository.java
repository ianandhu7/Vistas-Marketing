package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.BatchStudentAttendance;



/**
 * @author Meghana
 * 
 **/

public interface BatchStudentAttendanceRepository extends JpaRepository<BatchStudentAttendance, Long>{

	BatchStudentAttendance findByIdBatchAndIdStudentSubscrAndBatchRunDate(Long idBatch, Long idStudentSubscription, String batchRundate);
	
	List<BatchStudentAttendance> findByIdBatchAndIdStudentSubscr(Long idBatch, Long idStudentSubscription);

}
