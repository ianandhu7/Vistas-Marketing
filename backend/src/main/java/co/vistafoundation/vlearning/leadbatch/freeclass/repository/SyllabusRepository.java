/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.Syllabus;

/**
 * @author Shaikh Ahmed Reza
 *
 */
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
	

	Syllabus findByIdSyllabus(Long idSyllabus);

	List<Syllabus> findByIdSyllabusNot(Long l);

	

	List<Syllabus> findByIdSyllabusNotIn(ArrayList<Long> syllabusIds);

	List<Syllabus> findByIdSyllabusIn(List<Long> syllabusIdList);

}
