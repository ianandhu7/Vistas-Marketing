package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.user.model.Teacher;

/**
 * @author NaveenKumar
 * 
 **/
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	public Teacher findByIdTeacher(Long idTeacher);

	public List<Teacher> findByCategory(String category);

	public Teacher getTeacherByUser_UserSurId(Long userSurId);

	public List<Teacher> findByPrimarySubject(String subName);

	public List<Teacher> findAllByPrimarySubject(String subName, Pageable pageable);

	public List<Teacher> findAllByCategory(String category, Pageable pageable);

	public List<Teacher> findAllByCategoryAndSearchKeyContaining(String category, String searchKey, Pageable pageable);

	public Page<Teacher> findByIdTeacherIn(List<Long> finalTeacherIdList, Pageable paging);

	public List<Teacher> findByIdTeacherIn(List<Long> finalTeacherIdList);

	public List<Teacher> findAllByCategory(String string);

	public List<Teacher> findAllByPrimarySubject(String subjectName);

	public List<Teacher> findByIdTeacherInAndSearchKeyContaining(List<Long> finalTeacherIdList, String searchTerm);

	public List<Teacher> findByCategoryAndIdTeacherIn(String category, List<Long> finalTeacherList);

	public Teacher findByIdWebexPool(Long idWebExPool);

	public Teacher findByIdTeacherAndActiveFlag(Long idTeacher, boolean activeFlag);
	
	@Query(value ="select t from Teacher t join TeacherSubject ts ON t.idTeacher = ts.teacher.idTeacher where ts.idSubject = :idsubject and ts.idSyllabus= :idsyllabus")
	List <Teacher> findTeacherSubjectsidsubjectAndidSyllabus(Long  idsubject,Long idsyllabus);
	
	public List<Teacher> findByCategoryAndSearchKeyContaining(String category, String searchTerm);

	public List<Teacher> findByCategoryAndIdTeacherInAndDisplayInHomepageFlag(String category, List<Long> finalTeacherList,Boolean displayInHomepageFlag);
}
