package co.vistafoundation.vlearning.user.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.offlinecourse.model.StudentCompletionFact;

public interface StudentCompletionFactRepository extends JpaRepository<StudentCompletionFact, Long> {

	StudentCompletionFact findByAsOnDateAndIdStudentSubscription(Instant asOnDate, Long idStudentSubscription);

	List<StudentCompletionFact> findTop10ByIdStudentSubscriptionAndIdSubjectOrderByAsOnDateAsc(Long idStudentSubcription,Long idSubject);

}
