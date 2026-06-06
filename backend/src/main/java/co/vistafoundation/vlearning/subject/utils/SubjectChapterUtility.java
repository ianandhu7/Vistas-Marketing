package co.vistafoundation.vlearning.subject.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.quiz.model.StudentChapterQuiz;
import co.vistafoundation.vlearning.quiz.repository.StudentChapterQuizRepository;
import co.vistafoundation.vlearning.subject.model.SubjectChapter;
import co.vistafoundation.vlearning.subject.repo.SubjectChapterRepository;
import co.vistafoundation.vlearning.subscription.dto.SubscribedUserDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;

/**
 * @author Mohan Kumar
 * 
 **/
@Component
public class SubjectChapterUtility {

	private ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");

	TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");

	@Autowired
	UserRepository userRepository;

	@Autowired
	SubjectChapterRepository subjectChapterRepository;

	@Autowired
	StudentChapterQuizRepository studentChapterQuizRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	public SubjectChapter getNextSubjectChapter(Long idSubjectChapter) {

		SubjectChapter result = null;
		try {
			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapterAndActiveFlag(idSubjectChapter,Boolean.TRUE);

			if (subjectChapter == null)
				throw new AppException("invalid subject chapter");

			List<SubjectChapter> chapterList = subjectChapterRepository
					.findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrder(
							subjectChapter.getIdSubject(), subjectChapter.getIdClassStandard(),
							subjectChapter.getIdState(), subjectChapter.getIdSyllabus(),Boolean.TRUE);

			if (chapterList.isEmpty())
				throw new AppException("No Chapter list found");

			result = chapterList.get(chapterList.indexOf(subjectChapter) + 1);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	public SubjectChapter getPreviousSubjectChapter(Long idSubjectChapter) {

		SubjectChapter result = null;
		try {
			SubjectChapter subjectChapter = subjectChapterRepository.findByIdSubjectChapterAndActiveFlag(idSubjectChapter,Boolean.TRUE);

			if (subjectChapter == null)
				throw new AppException("invalid subject chapter");

			List<SubjectChapter> chapterList = subjectChapterRepository
					.findByIdSubjectAndIdClassStandardAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrder(
							subjectChapter.getIdSubject(), subjectChapter.getIdClassStandard(),
							subjectChapter.getIdState(), subjectChapter.getIdSyllabus(),Boolean.TRUE);

			if (chapterList.isEmpty())
				throw new AppException("No Chapter list found");

			result = chapterList.get(chapterList.indexOf(subjectChapter) - 1);
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		return result;
	}

	public Boolean isPreviousChapterQuizAttempted(Long idSubjectChapter, Long idClassStandard, Long idSubject,
			Long idState, Long idSyllabus, Long idStudentSubscription) {

		Boolean isAttempted = false;

		try {

			Boolean freeChapterAvailablityFlag = checkChapterAvailablityForFreeStreaming(idSubjectChapter,
					idClassStandard, idSubject, idState, idSyllabus);

			if (Boolean.TRUE.equals(freeChapterAvailablityFlag)) {
				isAttempted = true;
			} else {

				UserPrincipal userPrincipal = null;
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				Boolean accessAllowed = false;

				// check logged in user accessing
				if (!(authentication instanceof AnonymousAuthenticationToken)) {

					userPrincipal = (UserPrincipal) authentication.getPrincipal();

					if (userPrincipal == null)
						throw new AppException("Invalid User");

					Boolean subscriptionFlag = false;

					// check whether user is subscribed.
					SubscribedUserDTO suDTO = this.checkUserSubscribed(userPrincipal.getUserSurId());

					subscriptionFlag = suDTO != null;

					if (Boolean.TRUE.equals(subscriptionFlag))
						accessAllowed = true;

				}

				SubjectChapter prevSC = this.getPreviousSubjectChapter(idSubjectChapter);

				if (prevSC == null)
					throw new AppException("Invalid SubjectChapter data.");

				// fetch previous chapter quiz
				List<StudentChapterQuiz> stqList = studentChapterQuizRepository
						.findByIdSubjectChapterAndIdStudentSubscrOrderByQuizDateDesc(prevSC.getIdSubjectChapter(),
								idStudentSubscription);

				Boolean prevAttemptFlag = false;

				if (Boolean.TRUE.equals(accessAllowed)) {
					prevAttemptFlag = true;
				} else {
					prevAttemptFlag = !stqList.isEmpty();
				}

				isAttempted = prevAttemptFlag;

			}
		} catch (Exception e) {
			e.printStackTrace();
			isAttempted = false;
		}

		return isAttempted;

	}

	public Boolean checkChapterAvailablityForFreeStreaming(Long idSubjectChapter, Long idClassStandard, Long idSubject,
			Long idState, Long idSyllabus) throws NullPointerException

	{
		Boolean result = false;

		// limiting the query to 2 results
		List<SubjectChapter> pages = subjectChapterRepository
				.findTop2ByIdClassStandardAndIdSubjectAndIdStateAndIdSyllabusAndActiveFlagOrderBySortOrderAsc(idClassStandard,
						idSubject, idState, idSyllabus,Boolean.TRUE);

		if (pages.isEmpty())
			throw new NullPointerException("No Subject Chapters data found");

		result = pages.stream().filter(s -> s.getIdSubjectChapter().equals(idSubjectChapter)).findFirst().isPresent();

		return result;
	}

	public SubscribedUserDTO checkUserSubscribed(Long userSurId) {

		SubscribedUserDTO subscribedUserDTO = new SubscribedUserDTO();

		/*
		 * Updated by @author Naveen Kumar Fetching user data based on session
		 * information instead of parameter which sent through request.
		 * 
		 */
		User user = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			user = userRepository.findByUserSurId(userPrincipal.getUserSurId());
		}

		if (user == null)
			throw new AppException("Invalid User");

		// trying to find a subscription on a product-line basis.
		StudentSubscription studentSubscription = studentSubscriptionRepository
				.findFirstByUserSurIdAndIdproductLineAndActiveFlag(user.getUserSurId(), 11L, Boolean.TRUE);

		if (studentSubscription == null)
			return subscribedUserDTO = null;

		LocalDate dateNextPaymentDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateEndDate = studentSubscription.getNextPaymentDate().atZone(zoneIndia).toLocalDate();
		LocalDate dateToday = Instant.now().atZone(zoneIndia).toLocalDate();

		if ((dateNextPaymentDate.isAfter(dateToday) || dateNextPaymentDate.equals(dateToday))
				&& dateEndDate.isAfter(dateToday) || dateEndDate.equals(dateToday)) {
			subscribedUserDTO.setIdProduct(studentSubscription.getIdProduct());
			subscribedUserDTO.setIdStudent(studentSubscription.getIdStudent());
			subscribedUserDTO.setIdStudentSubscription(studentSubscription.getIdStudentSubscription());
			subscribedUserDTO.setUserSurId(studentSubscription.getUserSurId());
		} else {
			subscribedUserDTO = null;
		}

		return subscribedUserDTO;
	}
}
