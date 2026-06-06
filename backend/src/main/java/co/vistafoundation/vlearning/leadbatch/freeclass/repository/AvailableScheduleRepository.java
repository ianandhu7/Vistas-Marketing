package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.AvailableSchedule;

public interface AvailableScheduleRepository extends JpaRepository<AvailableSchedule, Long>{
	public AvailableSchedule findByIdAvailableScheduleAndDayOfWeekAndFromTimeAndToTime(Long idAvailableSchedule,String dayOfWeek, LocalTime fromTime, LocalTime toTime);
	public AvailableSchedule findByIdAvailableSchedule(Long idAvailbleSchedule);
}
