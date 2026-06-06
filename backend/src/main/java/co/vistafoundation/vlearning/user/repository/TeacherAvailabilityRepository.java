package co.vistafoundation.vlearning.user.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.TeacherAvailability;

public interface TeacherAvailabilityRepository extends JpaRepository<TeacherAvailability, Long> {

	TeacherAvailability findByIdTeacherAndDayOfWeekAndFromTimeAndToTime(Long idTeacher, String dayOfWeek,
			LocalTime fromTime, LocalTime toTime);

	public List<TeacherAvailability> findByIdTeacher(Long idTeacher);

	List<TeacherAvailability> findByIdTeacherAndDayOfWeek(Long idTeacher, String dayOfWeek);

}
