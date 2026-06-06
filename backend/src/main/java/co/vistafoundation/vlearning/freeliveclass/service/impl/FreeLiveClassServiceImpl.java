/**
 * 
 */
package co.vistafoundation.vlearning.freeliveclass.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.classes.model.ClassStandard;
import co.vistafoundation.vlearning.classes.repository.ClassRepository;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.freeliveclass.service.FreeLiveClassService;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;
import co.vistafoundation.vlearning.leadbatch.freeclass.repository.SyllabusRepository;
import co.vistafoundation.vlearning.liveclass.dto.LiveClassDto;
import co.vistafoundation.vlearning.liveclass.model.LiveClass;
import co.vistafoundation.vlearning.liveclass.model.LiveClassCategory;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassCategoryRepository;
import co.vistafoundation.vlearning.liveclass.repository.LiveClassRepository;
import co.vistafoundation.vlearning.subject.model.Subject;
import co.vistafoundation.vlearning.subject.repo.SubjectRepository;
import co.vistafoundation.vlearning.user.model.Language;
import co.vistafoundation.vlearning.user.model.State;
import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.repository.LanguageRepository;
import co.vistafoundation.vlearning.user.repository.StateRepository;
import co.vistafoundation.vlearning.user.repository.TeacherRepository;

/**
 * @author NAVEEN
 *
 */

@Service
public class FreeLiveClassServiceImpl implements FreeLiveClassService {

	@Autowired
	public LiveClassRepository liveClassRepository;

	@Autowired
	SyllabusRepository syllabusRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	LiveClassCategoryRepository liveClassCategoryRepository;

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Override
	public Document<Page<LiveClassDto>> getFreeLiveClass(Long idClassStandard, Long idLiveClassCategory,
			Long idLanguage, Long idSubject, Long idSyllabus, Long idState, int pageNumber, Integer runningFlag,
			Integer completFlag) {


		Document<Page<LiveClassDto>> result = new Document<>();

		try {

			Page<LiveClassDto> finalPage;

			Boolean currentRunningFlag = runningFlag.equals(-1) ? null : (!runningFlag.equals(0) ? true : false);
			Boolean completionFlag = completFlag.equals(-1) ? null : (!completFlag.equals(0) ? true : false);


			UserPrincipal userPrincipal = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				userPrincipal = (UserPrincipal) authentication.getPrincipal();
			}

			if (userPrincipal == null)
				throw new AppException("Invalid User found in the sesison.");

			idLiveClassCategory = idLiveClassCategory.equals(-1L) ? null : idLiveClassCategory;

			idSubject = idSubject.equals(-1L) ? null : idSubject;

			if ((!idClassStandard.equals(-1L) && !idClassStandard.equals(4L))
					&& !idClassStandard.equals(userPrincipal.getIdClassStandard())) {
				result.setData(null);
				result.setStatusCode(403);
				result.setMessage("User dosent have access to view this content.");
				return result;
			} else
				idClassStandard = idClassStandard.equals(4L) ? 4L : userPrincipal.getIdClassStandard();

			if ((!idSyllabus.equals(-1L) && !idSyllabus.equals(4L))
					&& !idSyllabus.equals(userPrincipal.getIdSyllabus())) {
				result.setData(null);
				result.setStatusCode(403);
				result.setMessage("User dosent have access to view this content.");
				return result;
			} else
				idSyllabus = idSyllabus.equals(4L) ? 4L : userPrincipal.getIdSyllabus();

			if ((!idState.equals(-1L) && !idState.equals(6L)) && !idState.equals(userPrincipal.getIdState())) {
				result.setData(null);
				result.setStatusCode(403);
				result.setMessage("User dosent have access to view this content.");
				return result;
			} else
				idState = idState.equals(6L) ? 6L : userPrincipal.getIdState();

			
			Language lang = languageRepository.findByLanguage(userPrincipal.getSecondaryLanguage());
			
			
			if (lang == null)
				throw new AppException("Invalid languages found in user record.");
			
			Long langIds[] = new Long[2];

			if (idLanguage.equals(-1L)) 
			{
				langIds[0] = 7L;
				langIds[1] = lang.getIdLanguage();
				
			}
			else 
			{
				if(idLanguage.equals(7L)|| lang.getIdLanguage().equals(idLanguage)) {
					
					langIds[0]=idLanguage;
				}
				else
				{
					result.setData(null);
					result.setStatusCode(403);
					result.setMessage("User dosent have access to view this content.");
					return result;
				}
			}
			
	        LocalDate today = LocalDate.now();

	        String inputDateStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate inputDate = LocalDate.parse(inputDateStr, formatter);

	        LocalDate minusOneYear = inputDate.minusYears(1);
		
			Pageable paging = PageRequest.of(pageNumber, 12);
			Page<LiveClass> classList ;
			
			
			if (currentRunningFlag == null || completionFlag == null ) 
			{
				classList = liveClassRepository
						.getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageDesc(idClassStandard, idLiveClassCategory,
								langIds, idSubject, idSyllabus, idState, currentRunningFlag, completionFlag,minusOneYear, paging);
			}
			else 
			{
				
				if(completionFlag) 
				{
					classList = liveClassRepository.getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageByDateTimeDesc(idClassStandard, idLiveClassCategory,
							langIds, idSubject, idSyllabus, idState, currentRunningFlag, completionFlag,minusOneYear, paging);
				}
				else 
				{
					classList = liveClassRepository.getFreeLiveClassByIdClassAndIdStateAndIdSyllabusAndIdLanguageAsc(idClassStandard, idLiveClassCategory,
							langIds, idSubject, idSyllabus, idState, currentRunningFlag, completionFlag,minusOneYear, paging);
				}
				
			}
			
			

			if (classList.getContent().isEmpty() && pageNumber > 0) {
				result.setData(null);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("No More Videos Available.");

			} else if (classList.getContent().isEmpty()) {

				throw new NullPointerException("No Videos Available.");
			} else {

				List<LiveClassDto> finalClassList = new ArrayList<>();

				for (LiveClass liveClass : classList.getContent()) {
					LiveClassDto liveClassDto = new LiveClassDto();
					liveClassDto.setIdLiveClass(liveClass.getIdLiveClass());
					liveClassDto.setIdTeacher(liveClass.getIdTeacher());
					liveClassDto.setClassDate(liveClass.getClassDate());
					liveClassDto.setFromTime(liveClass.getFromTime());
					liveClassDto.setToTime(liveClass.getToTime());
					liveClassDto.setLiveClassHeading(liveClass.getLiveClassHeading());
					liveClassDto.setLiveClassDescription(liveClass.getLiveClassDesc());
					liveClassDto.setIdYoutubeMaster(liveClass.getIdYoutubeMaster());
					liveClassDto.setLiveClassURL(liveClass.getLiveClassURL());
					liveClassDto.setThumbnailURL(liveClass.getThumbnailURL());
					liveClassDto.setCurrentRunningFlag(liveClass.isCurrentRunningFlag());
					liveClassDto.setLiveCompletionFlag(liveClass.isLiveCompletionFlag());
					liveClassDto.setIdLiveClassCategory(liveClass.getIdLiveClassCategory());
					liveClassDto.setIntroVideoURL(liveClass.getIntroVideoURL());
					liveClassDto.setPdfURL(liveClass.getPdfURL());

					liveClassDto.setIdSyllabus(liveClass.getIdSyllabus());
					liveClassDto.setIdState(liveClass.getIdState());

					State state = stateRepository.findByIdState(liveClass.getIdState());

					if (state != null)
						liveClassDto.setStateName(state.getState());

					Syllabus syllabus = syllabusRepository.findByIdSyllabus(liveClass.getIdSyllabus());

					if (syllabus != null)
						liveClassDto.setSyllabusName(syllabus.getSyllabusName());

					LiveClassCategory category = liveClassCategoryRepository
							.findByIdLiveClassCategory(liveClass.getIdLiveClassCategory());

					if (category != null)
						liveClassDto.setClassCategory(category.getLiveClassCategoryName());

					Teacher teacher = teacherRepository.findByIdTeacher(liveClass.getIdTeacher());

					if (teacher != null) {
						liveClassDto.setTeacherName(teacher.getTeacherName());
						liveClassDto.setTeacherHeader(teacher.getTeacherHeader());
						liveClassDto.setTeacherDescription(teacher.getTeacherDesc());
					}

					ClassStandard classStandard = classRepository.findByIdClassStandard(liveClass.getIdClassStandard());

					if (classStandard != null) {
						liveClassDto.setClassStandard(classStandard.getClassStandadName());
						liveClassDto.setIdClassStandard(classStandard.getIdClassStandard());
					}

					Subject subject = subjectRepository.findByIdSubject(liveClass.getIdSubject());

					if (subject != null) {
						liveClassDto.setIdSubject(subject.getIdSubject());
						liveClassDto.setSubjectName(subject.getSubjectName());
					}

					Language language = languageRepository.findByIdLanguage(liveClass.getIdLanguage());

					if (language != null)
						liveClassDto.setLanguage(language.getLanguage());

					if (teacher != null && classStandard != null)
						liveClassDto.setTextBelowLiveClass("Class " + classStandard.getClassStandadName() + " | "
								+ liveClass.getLiveClassHeading() + " | " + teacher.getTeacherName() + " ");

					finalClassList.add(liveClassDto);

				}

				finalPage = new PageImpl<>(finalClassList, classList.getPageable(), classList.getTotalElements());

				result.setData(finalPage);
				result.setStatusCode(HttpStatus.OK.value());
				result.setMessage("Request Sucessfull.");
			}

		}

		catch (Exception e) {
			result.setData(null);
			result.setStatusCode(500);
			result.setMessage(e.getLocalizedMessage());
		}

		return result;
	}
}
