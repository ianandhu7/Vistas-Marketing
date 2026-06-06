package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.vistafoundation.vlearning.batch.model.BatchRunDetail;

/**
 * 
 * @author Shaikh Ahmed Reza
 *
 */

public interface BatchRunDetailsRepository extends JpaRepository<BatchRunDetail, Long> {

	BatchRunDetail findByIdBatchAndBatchRundate(Long idBatch, String dateSplit);

	List<BatchRunDetail> findByIdBatch(Long idBatch);

	List<BatchRunDetail> findByBatchRundate(String batchRundate);

	

	@Query("SELECT br FROM BatchRunDetail  br inner join Batch b on br.idBatch = b.idBatch inner join StudentSubscription ss on ss.idBatch = b.idBatch inner join ProductLine pl on ss.idproductLine = pl.idProductLine where pl.productCategory = 'Batch' and ss.idStudent = :idStudent and br.batchRundate = :batchRundate")
	public List<BatchRunDetail> getBatchrunDetails(@Param("idStudent") Long idStudent,
			@Param("batchRundate") String batchRundate);

	@Query(value = "SELECT b From BatchRunDetail b where b.idBatch=:idBatch and b.batchRundate=:batchRunDate")
	public BatchRunDetail fetchBatchRunDetailsByIdBatch(@Param("idBatch") Long idBatch,
			@Param("batchRunDate") String batchRunDate);
	
	@Query("SELECT a.idBatch, a.batchRundate, b.batchName FROM BatchRunDetail a inner join Batch b on a.idBatch=b.idBatch where a.idBatch=:idBatch ORDER BY a.batchRundate DESC")
	List<Object[]> findBatchRundateDetail(@Param("idBatch") Long idBatch);
	
	@Query(value=" SELECT brd FROM BatchRunDetail brd left join  BatchRunRecording brr on  brd=brr.batchRunDetail where brr.batchRunDetail is null")
	List<BatchRunDetail> getBatchRunDetailsForNotCreatedBatchRecording();
	

}
