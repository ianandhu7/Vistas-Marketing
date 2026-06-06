package co.vistafoundation.vlearning.webex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.webex.model.WebExPool;

public interface WebExPoolRepository extends JpaRepository<WebExPool, Long> {

	@Query(value = "From WebExPool wp where wp.availableFlag=false")
	List<WebExPool> fetchAllUnAssignedTeachers();
	
	//@Query(value = "select wp.* From WebExPool wp where wp.availableFlag=false UNION  select wb.* from WebExPool wb inner join teacher t where wb.idWebExPool = t.idWebExPool AND t.idTeacher = :idTecaher")
	//List<WebExPool> fetchunAssignedAndPerticulatTeacherAssignedWebExPool(Long idTecaher);
	
	@Query(value = "select *  from webex_pool  where available_flag = false  union select wb.* from webex_pool wb inner join Teacher t where t.idwebex_pool = wb.idwebex_pool and t.idteacher = :idTecaher" , nativeQuery = true)
	List<WebExPool> fetchunAssignedAndPerticulatTeacherAssignedWebExPool(Long idTecaher);

	WebExPool findByIdWebExPool(Long idWebexPool);

	Boolean existsByWebExUserId(String webExId);

	List<WebExPool> findByAvailableFlag(Boolean flag);

}
