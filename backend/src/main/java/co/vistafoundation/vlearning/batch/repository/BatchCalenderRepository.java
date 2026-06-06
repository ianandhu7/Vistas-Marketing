package co.vistafoundation.vlearning.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.model.BatchCalender;

public interface BatchCalenderRepository extends JpaRepository<BatchCalender, Long> {

	public List<BatchCalender> findByBatch_idBatch(Long idBatch);

	public List<BatchCalender> findByBatchInAndDayOfWeek(List<Batch> batchList, String dayOfWeek);

	public BatchCalender findByBatchAndIdBatchCalendar(Batch batch, Long idBatchCalender);

	public List<BatchCalender> findByBatch_idBatchInAndDayOfWeekIn(List<Long> tempList,
			List<String> finaldayOfWeekCodesList);

	public List<BatchCalender> findByBatch_idBatchInAndDayOfWeek(List<Long> finalIdBatches, String dayOfWeek);
}
