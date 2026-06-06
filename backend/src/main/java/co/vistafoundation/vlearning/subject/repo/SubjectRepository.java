package co.vistafoundation.vlearning.subject.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.vistafoundation.vlearning.subject.model.Subject;

/**
 * @author NaveenKumar
 * 
 **/
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	

	public Subject findByIdSubject(Long idSubject);

	public Subject findBySubjectName(String subjectName);

	@Query("select distinct s from Subject s inner join Product p on p.idSubject = s.idSubject "
			+ "inner join ProductLine pl on pl.idProductLine = p.idProductLine where pl.productLineCd = :categoryCode")
	public List<Subject> getSubjectBasedOnCategory(String categoryCode);

	public List<Subject> findByIdSubjectIn(List<Long> idSubjectList);
	public Subject getByIdSubject(Long idSubject);

}
