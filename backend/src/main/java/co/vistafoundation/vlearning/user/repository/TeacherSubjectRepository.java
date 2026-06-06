
package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.Teacher;
import co.vistafoundation.vlearning.user.model.TeacherSubject;

/**
 * @author Naveen Kumar
 *
 */
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject ,Long> {

	List<TeacherSubject> findByTeacher(Teacher teacher);

	List<TeacherSubject> findByTeacher_IdTeacher(Long idTeacher);	
	
	List<TeacherSubject> findByTeacherAndIdSyllabus(Teacher teacher, Long idSyllabus);

	List<TeacherSubject> findByTeacherAndIdSyllabusAndIdSubject(Teacher t, Long idSyllabus, Long idSubject);
	
	TeacherSubject findByTeacher_IdTeacherAndIdSyllabusAndIdSubject(Long idTeacher, Long idSyllabus, Long idSubject);
	
}
