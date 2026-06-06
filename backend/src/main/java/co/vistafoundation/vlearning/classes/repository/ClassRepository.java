package co.vistafoundation.vlearning.classes.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.classes.model.ClassStandard;



public interface ClassRepository extends JpaRepository<ClassStandard, Long> {


	public List<ClassStandard> findAll();

	public ClassStandard findByIdClassStandard(Long idClassStandard);

	public List<ClassStandard> findByIdClassStandardNot(Long l);

	public List<ClassStandard> findByIdClassStandardIn(List<Long> classIdList);

}
