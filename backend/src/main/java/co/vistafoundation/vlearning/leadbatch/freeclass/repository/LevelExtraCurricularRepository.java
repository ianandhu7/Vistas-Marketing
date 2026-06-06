/**
 * 
 */
package co.vistafoundation.vlearning.leadbatch.freeclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.leadbatch.freeclass.model.LevelExtraCurricular;

/**
 * @author Ahmed
 *
 */
public interface LevelExtraCurricularRepository extends JpaRepository<LevelExtraCurricular, Long> {

	LevelExtraCurricular findByIdLevelExtraCurricular(Long idLevel);

}
