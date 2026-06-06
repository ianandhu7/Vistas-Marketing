/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.service;

import java.time.LocalDate;
import java.util.List;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassFilterDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoClassRequestDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.dto.DemoEcaClassDTO;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClass;
import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;

/**
 * @author vk
 *
 */
public interface DemoClassService {

	public Document<?> save(DemoClassRequestDTO demoClass, String category);
	
	public Document<?> updateTeacher(DemoClass demoClass);

	public Document<?> getDemoClassByTeacher(Long idTeacher);
	
	public Document<?> getDemoClassList(DemoClassFilterDTO demoClassFilterDTO);
	
	public Document<?> getAllDemoClasses(Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter, String date);
	
	public Document<?> getAllStudentsForDemoclass(Long idClassStandard, Long idSyllabus, Long idSubject, Long idSujectChapter,Long idLanguage,Long idAvailableSchedule,Long idState);
	
	public Document<?> assigningLeadBatchLog(Long idDemoClass, Long idBatchDetail);

	public Document<?> createWebExMeetingForDemoClassByTeacher(Long idDemoClass,Long idTeacher);

	public Document<?> getAllLeadBatchLogs(Long idVlUser);

	public Document<?> studentOrParentJoinDemoClass(Long idLeadBatchLog, Long idVlUser, Long idDemoClass);
	
	public Document<?> deActivateDemoClass(Long idDemoClass);

	public Document<?> getLeastFutureDemoClass();
	
	public Document<?> getListofDemoClassTeacherProfile(Long idTeacher);

	public Document<?> getAllExtraCurricularDemoClassForSelectedSubjectAndLevelAndSlectedDate(
			Long idSubjectExtraCurricular, Long idLevelExtraCurricular, LocalDate choosenDate);

	public Document<?> scheduleExtraCurricularDemoClassAndConfirm(Long idVlUser,Long idDemoClassExtraCurricular);

	public Document<?> listExtraCurricularDemoClassByIdTeacher(Long idTeacher);

	public Document<?> createWebExMeetingForExtraCurricularDemoClassByTeacher(Long idDemoClassExtraCurricular,
			Long idTeacher);

	public Document<?> studentOrParentJoinDemoClassExtraCurricular(Long idLeadBatchLogExtraCurricular, Long idVlUser,
			Long idDemoClassExtraCurricular);

	public Document<?> getAllLeadBatchLogsExtraCurricular(Long idVlUser);
	
	
	public Document<List<DemoEcaClassDTO>> getDemoClassListECA(DemoClassFilterDTO demoClassFilterDTO);
	
	public Document<DemoClassExtraCurricular> updateECATeacher(DemoClassExtraCurricular demoClass);
	
	public Document<DemoClassExtraCurricular> deactivateECADemoClass(Long idDemoClass);

	public Document<?> getDemoClassWebExMetadataAttendeeInfo(Long idDemoClass);

	public Document<?> getDemoClassExtraCurricularWebExMetadataAttendeeInfo(Long idDemoClassExtraCurricular);
	
}
