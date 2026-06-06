package co.vistafoundation.vlearning.user.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.offlinecourse.model.StudentAssignedCourse;
import co.vistafoundation.vlearning.offlinecourse.model.StudentCompletionFact;
import co.vistafoundation.vlearning.offlinecourse.repository.StudentAssignedCourseRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.repository.StudentCompletionFactRepository;

@Component
public class StudentCompletionFactScheduler {
	@Autowired
	private StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	private StudentAssignedCourseRepository studentAssignedCourseRepository;

	@Autowired
	private StudentCompletionFactRepository studentCompletionFactRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	// @Scheduled(cron = "0 0 0 * * ?")
	public void updateCompletionFact() {

		System.out.println("Student completion Fact Scheduler to be start");
		List<StudentSubscription> subcripList = studentSubscriptionRepository.findAll();

		subcripList.stream().forEach(a -> {
			if (a.getActiveFlag() == true && a.getIdBatch() == null) {
				updateFact(a.getIdStudentSubscription());
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Document updateFact(Long idStudentSubscription) {
		List<StudentCompletionFact> studentCompletionFactList = new ArrayList<>();
		Document result = new Document();
		try {
			// Long complete, cal;
			List<StudentAssignedCourse> assignedList = studentAssignedCourseRepository
					.findByIdStudentSubscription(idStudentSubscription);
			if (!assignedList.isEmpty()) {
				List<StudentAssignedCourse> list = studentAssignedCourseRepository.getAllAssignedCourse();
				if (!list.isEmpty()) {
					StudentCompletionFact studentCompletionFact = new StudentCompletionFact();
					list.stream().forEach(s -> {
						studentCompletionFact.setIdStudentSubscription(idStudentSubscription);
						studentCompletionFact.setIdSubject(s.getIdSubject());
						studentCompletionFact.setAsOnDate(Instant.now());
						Subject subject = subjectRepository.findByIdSubject(s.getIdSubject());
						studentCompletionFact.setSubjectName(subject.getSubjectName());
						Long complete = (Long) assignedList.stream().filter(q -> q.getCompleteFlag() == true).count();
						Long cal = complete * 100 / assignedList.size();
						studentCompletionFact.setCompletionPCT(cal);
						studentCompletionFactList.add(studentCompletionFact);
					});
					StudentCompletionFact date = studentCompletionFactRepository.findByAsOnDateAndIdStudentSubscription(
							studentCompletionFact.getAsOnDate(), idStudentSubscription);
					if (date != null)
						throw new NullPointerException("Same Date Exist .");
					result.setData(studentCompletionFactRepository.save(studentCompletionFact));
					result.setStatusCode(HttpStatus.OK.value());
					result.setMessage("Fact update Sucessfull");
					studentCompletionFactRepository.save(studentCompletionFact);
				}
			}
			return result;
		} catch (Exception exp) {
			result.setData(null);
			result.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			result.setMessage(exp.getLocalizedMessage());
			return result;
		}
	}
}
