package co.vistafoundation.vlearning.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NaveenKumar
 * 
 **/
import co.vistafoundation.vlearning.batch.model.DayOfWeekCode;
public interface DayofWeekCodeRepository extends JpaRepository<DayOfWeekCode, Long> {
	
	public DayOfWeekCode findByIdDayofWeekCode(Long idDayofWeekCode);

}
