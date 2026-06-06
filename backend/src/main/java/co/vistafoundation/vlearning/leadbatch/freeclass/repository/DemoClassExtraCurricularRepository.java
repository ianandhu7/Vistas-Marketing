/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.DemoClassExtraCurricular;

/**
 * @author Ahmed
 *
 */
public interface DemoClassExtraCurricularRepository extends JpaRepository<DemoClassExtraCurricular, Long> {

	List<DemoClassExtraCurricular> findByIdSubjectExtraCurricularAndIdLevelExtraCurricularAndClassScheduleDateAndActiveFlag(
			Long idSubjectExtraCurricular, Long idLevelExtraCurricular, LocalDate choosenDate, Boolean activeFlag);

	DemoClassExtraCurricular findByIdDemoClassExtraCurricular(Long idDemoClassExtraCurricular);

	List<DemoClassExtraCurricular> findByIdTeacher(Long idTeacher);

	public List<DemoClassExtraCurricular> findByIdTeacherAndClassScheduleDateAndActiveFlag(Long IdTeacher,
			LocalDate date, Boolean activeFalg);

	List<DemoClassExtraCurricular> findByIdTeacherAndIdLevelExtraCurricularAndIdSubjectExtraCurricularAndActiveFlag(
			Long idTeacher, Long idLevelExtraCurricular, Long idSubject, Boolean activeFlag);

}
