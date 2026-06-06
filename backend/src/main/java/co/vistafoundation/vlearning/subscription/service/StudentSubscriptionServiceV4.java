package co.vistafoundation.vlearning.subscription.service;



import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.subscription.dto.NewStudentSubscriptionSubjectDTO;
import co.vistafoundation.vlearning.subscription.dto.StudentChapterListDTO;


public interface StudentSubscriptionServiceV4 {

	
	public Document<NewStudentSubscriptionSubjectDTO> getChapterList(Long idProductLine, Long idSubject,
			Long idClassStandard, Long idSyllabus, Long idState);
}
