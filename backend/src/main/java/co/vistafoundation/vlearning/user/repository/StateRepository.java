package co.vistafoundation.vlearning.user.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import co.vistafoundation.vlearning.user.model.State;

/**
 * 
 * @author Sajini
 *
 */

public interface StateRepository extends JpaRepository<State, Long> {


	public State findByIdState(Long idState);
	
	public State findByState(String state);

	public List<State> findByIdStateNot(Long l);

	public List<State> findByIdStateInOrderByDisplayOrderAsc(List<Long> stateIdList);
	
	public List<State> findAllByOrderByDisplayOrderAsc();
	
	
	

}
